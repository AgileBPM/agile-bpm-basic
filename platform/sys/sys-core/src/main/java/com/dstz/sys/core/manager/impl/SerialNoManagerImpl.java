package com.dstz.sys.core.manager.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.manager.impl.BaseManager;
import com.dstz.sys.core.dao.SerialNoDao;
import com.dstz.sys.core.manager.SerialNoManager;
import com.dstz.sys.core.model.SerialNo;

import cn.hutool.core.date.DateUtil;

@Service("serialNoManager")
public class SerialNoManagerImpl extends BaseManager<String, SerialNo> implements SerialNoManager {
    @Resource
    SerialNoDao serialNoDao;


    @Override
    public void create(SerialNo entity) {
        entity.setCurDate(getCurDate());
        super.create(entity);
    }

    /**
     * 判读流水号别名是否已经存在
     *
     * @param id    id为null 表明是新增的流水号，否则为更新流水号
     * @param alias
     * @return
     */
    @Override
    public boolean isAliasExisted(String id, String alias) {
        return serialNoDao.isAliasExisted(id, alias)>0;
    }


    /**
     * 根据流程规则别名获取得当前流水号。
     *
     * @param alias 流水号规则别名。
     * @return
     */
    public String getCurIdByAlias(String alias) {
        SerialNo SerialNo = this.serialNoDao.getByAlias(alias);
        Integer curValue = SerialNo.getCurValue();
        if (curValue == null) curValue = SerialNo.getInitValue();
        String rtn = getByRule(SerialNo.getRegulation(), SerialNo.getNoLength(), curValue);
        return rtn;
    }

    /**
     * 根据规则返回需要显示的流水号。
     *
     * @param rule     流水号规则。
     * @param length   流水号的长度。
     * @param curValue 流水号的当前值。
     * @return
     */
    private String getByRule(String rule, int length, int curValue) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String yearStr = "" + year;
        String shortMonth = "" + month;
        String longMonth = (month < 10) ? "0" + month : "" + month;

        String seqNo = getSeqNo(rule, curValue, length);

        String shortDay = "" + day;
        String longDay = (day < 10) ? "0" + day : "" + day;

        String rtn = rule.replace("{yyyy}", yearStr)
                .replace("{MM}", longMonth)
                .replace("{mm}", shortMonth)
                .replace("{DD}", longDay)
                .replace("{dd}", shortDay)
                .replace("{NO}", seqNo)
                .replace("{no}", seqNo);


        return rtn;
    }

    /**
     * 根据当前流水号的值和流水号显示的长度。
     * <pre>
     * 比如：当前流水号为55 ，显示长度为5那么这个方法返回：00055。
     * </pre>
     *
     * @param curValue 当前流水号的值。
     * @param length   显示的长度。
     * @return
     */
    private static String getSeqNo(String rule, int curValue, int length) {
        String tmp = curValue + "";
        int len = 0;
        if (rule.indexOf("no") > -1) {
            len = length;
        } else {
            len = length - tmp.length();
        }
        String rtn = "";
        switch (len) {
            case 1:
                rtn = "0";
                break;
            case 2:
                rtn = "00";
                break;
            case 3:
                rtn = "000";
                break;
            case 4:
                rtn = "0000";
                break;
            case 5:
                rtn = "00000";
                break;
            case 6:
                rtn = "000000";
                break;
            case 7:
                rtn = "0000000";
                break;
            case 8:
                rtn = "00000000";
                break;
            case 9:
                rtn = "000000000";
                break;
            case 10:
                rtn = "0000000000";
                break;
            case 11:
                rtn = "00000000000";
                break;
            case 12:
                rtn = "000000000000";
                break;
        }
        if (rule.indexOf("no") > -1) {
            return tmp + rtn;
        } else {
            return rtn + tmp;
        }

    }

    /**
     * 根据流程规则别名获取得下一个流水号。
     *
     * @param alias 流水号规则别名。
     * @return
     */
    public synchronized String nextId(String alias) {
        SerialNo SerialNo = serialNoDao.getByAlias(alias);
        if (SerialNo == null) throw new RuntimeException("流水号【" + alias + "】缺失！请联系系统管理员！");

        Result result = genResult(SerialNo);

        int tryTimes = 0;
        while (result.getRtn() == 0) {
            tryTimes++; // 防止在使用中修改步长，导致死循环
            if (tryTimes > 100) throw new RuntimeException("获取流水号失败！ " + SerialNo.getAlias());

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SerialNo.setCurValue(result.getCurValue());
            result = genResult(SerialNo);
        }
        return result.getIdNo();
    }

    public Result genResult(SerialNo SerialNo) {
        String rule = SerialNo.getRegulation();
        int step = SerialNo.getStep();
        int genEveryDay = SerialNo.getGenType();

        //如果失败过一次、使用失败的当前值。没有失败
        Integer curValue = SerialNo.getCurValue();
        /*if(failCurValue != 0) curValue = failCurValue;*/

        if (curValue == 0) curValue = SerialNo.getInitValue();


        // 每天都生成
        if (genEveryDay == 1) {
            String curDate = getCurDate();
            String oldDate = SerialNo.getCurDate();
            if (!curDate.equals(oldDate)) {
                SerialNo.setCurDate(curDate);
                curValue = SerialNo.getInitValue();
            } else {
                curValue = curValue + step;
            }
        } else {
            curValue = curValue + step;
        }
        SerialNo.setNewCurValue(curValue);
        int i = 0;
        i = serialNoDao.updByAlias(SerialNo);
        Result result = new Result(0, "", curValue);
        if (i > 0) {
            String rtn = getByRule(rule, SerialNo.getNoLength(), curValue);
            result.setIdNo(rtn);
            result.setRtn(1);
        }
        return result;
    }

    /**
     * 返回当前日期。格式为 年月日。
     *
     * @return
     */
    public String getCurDate() {
        Date date = new Date();
        return DateUtil.format(date, "yyyyMMdd");

    }

    /**
     * 预览时，获取前十个流水号
     *
     * @param alias
     * @return
     */
    public List<SerialNo> getPreviewIden(String alias) {
        int genNum = 10;
        SerialNo SerialNo = serialNoDao.getByAlias(alias);
        String rule = SerialNo.getRegulation();
        int step = SerialNo.getStep();
        Integer curValue = SerialNo.getCurValue();
        if (curValue == null) curValue = SerialNo.getInitValue();
        List<SerialNo> tempList = new ArrayList<SerialNo>();
        for (int i = 0; i < genNum; i++) {
            SerialNo SerialNotemp = new SerialNo();
            if (i > 0) {
                curValue += step;
            }
            String rtn = getByRule(rule, SerialNo.getNoLength(), curValue);
            SerialNotemp.setId(curValue.toString());
            SerialNotemp.setCurIdenValue(rtn);
            tempList.add(SerialNotemp);
        }
        return tempList;
    }


    public class Result {

        private int rtn = 0;
        private String idNo = "";
        private int curValue = 0;

        public Result(int rtn, String idNo, int curValue) {
            this.rtn = rtn;
            this.idNo = idNo;
            this.setCurValue(curValue);
        }


        public int getRtn() {
            return rtn;
        }

        public void setRtn(int rtn) {
            this.rtn = rtn;
        }

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        public int getCurValue() {
            return curValue;
        }

        public void setCurValue(int curValue) {
            this.curValue = curValue;
        }


    }


}
