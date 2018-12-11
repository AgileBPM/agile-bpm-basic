package com.dstz.base.core.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 拼音使用工具类 </br> 功能： 输入汉字取得拼音。 <br>
 * 使用Pinyin4j.jar
 * @author zxh
 */
public class PinyinUtil {

    /**
     * 获取中文汉字拼音 默认输出
     *
     * @param chinese 中文汉字
     * @return
     */
    public static String getPinyin(String chinese) {
        return getPinyinZh_CN(convertStringByChinese(chinese));
    }

    /**
     * 拼音大写输出
     *
     * @param chinese 中文汉字
     * @return
     */
    public static String getPinyinToUpperCase(String chinese) {
        return getPinyinZh_CN(convertStringByChinese(chinese)).toUpperCase();
    }

    /**
     * 拼音小写输出
     *
     * @param chinese 中文汉字
     * @return
     */
    public static String getPinyinToLowerCase(String chinese) {
        return getPinyinZh_CN(convertStringByChinese(chinese)).toLowerCase();
    }

    /**
     * 首字母大写输出
     *
     * @param chinese 中文汉字
     * @return
     */
    public static String getPinyinFirstToUpperCase(String chinese) {
        return getPinyin(chinese);
    }

    /**
     * Default Format 默认输出格式 小或大写、 没有音调数字、u显示
     *
     * @param isLowerCase 是否小写
     * @return
     */
    private static HanyuPinyinOutputFormat getDefaultFormat() {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 没有音调数字
        format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);// u显示
        return format;
    }

    /**
     * 字符集转换
     *
     * @param chinese 中文汉字
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    private static Set<String> convertStringByChinese(String chinese) {
        char[] chars = chinese.toCharArray();
        if (chinese != null && !chinese.trim().equalsIgnoreCase("")) {
            char[] srcChar = chinese.toCharArray();
            String[][] temp = new String[chinese.length()][];
            for (int i = 0; i < srcChar.length; i++) {
                char c = srcChar[i];
                // 是中文（a-z、A-Z）转换拼音
                if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")
                        || String.valueOf(c).matches("[\\u3007]")) {
                    try {
                        temp[i] = PinyinHelper.toHanyuPinyinStringArray(
                                chars[i], getDefaultFormat());

                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                } else {
                    temp[i] = new String[]{String.valueOf(srcChar[i])};
                }
            }
            String[] pingyinArray = exchange(temp);
            Set<String> pinyin = new HashSet<String>();
            for (int i = 0; i < pingyinArray.length; i++) {
                pinyin.add(pingyinArray[i]);
            }
            return pinyin;
        }
        return null;
    }


    /**
     * 递归
     *
     * @param strJaggedArray
     * @return
     */
    private static String[] exchange(String[][] strJaggedArray) {
        String[][] temp = doExchange(strJaggedArray);
        return temp[0];
    }

    /**
     * 递归
     *
     * @param strJaggedArray
     * @return
     */
    private static String[][] doExchange(String[][] strJaggedArray) {
        int len = strJaggedArray.length;
        if (len >= 2) {
            int len1 = strJaggedArray[0].length;
            int len2 = strJaggedArray[1].length;
            int newlen = len1 * len2;
            String[] temp = new String[newlen];
            int index = 0;
            for (int i = 0; i < len1; i++) {
                for (int j = 0; j < len2; j++) {
                    temp[index] = capitalize(strJaggedArray[0][i])
                            + capitalize(strJaggedArray[1][j]);
                    index++;
                }
            }
            String[][] newArray = new String[len - 1][];
            for (int i = 2; i < len; i++) {
                newArray[i - 1] = strJaggedArray[i];
            }
            newArray[0] = temp;
            return doExchange(newArray);
        } else {
            return strJaggedArray;
        }
    }

    /**
     * 首字母大写
     *
     * @param s
     * @return
     */
    private static String capitalize(String s) {
        char[] ch = s.toCharArray();
        if (ch != null && ch.length > 0) {
            if (ch[0] >= 'a' && ch[0] <= 'z')
                ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 字符串集合转换字符串(逗号分隔)
     *
     * @param stringSet
     * @return
     */
    private static String getPinyinZh_CN(Set<String> stringSet) {
        StringBuilder str = new StringBuilder();
        int i = 0;
        for (String s : stringSet) {
            if (i == stringSet.size() - 1) {
                str.append(s);
            } else {
                str.append(s + ",");
            }
            i++;
        }
        return str.toString();
    }


    /**
     * 返回中文的首字母
     *
     * @param chinese
     * @return
     */
    public static String getPinYinHeadChar(String chinese) {
        StringBuilder pinyin = new StringBuilder();
        if (chinese != null && !chinese.trim().equalsIgnoreCase("")) {
            for (int j = 0; j < chinese.length(); j++) {
                char word = chinese.charAt(j);
                String[] pinyinArray = PinyinHelper
                        .toHanyuPinyinStringArray(word);
                if (pinyinArray != null) {
                    pinyin.append(pinyinArray[0].charAt(0));
                } else {
                    pinyin.append(word);
                }
            }
        }
        return pinyin.toString();
    }

    /**
     * 去除特殊字符
     *
     * @param cnStr
     * @return
     */
    public static String strFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String   regEx  =  "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\"]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 返回中文的首字母并过滤特殊字符输出
     *
     * @param chinese
     * @return
     */
    public static String getPinYinHeadCharFilter(String chinese) {
        return strFilter(getPinYinHeadChar(chinese));
    }

    /**
     * Test 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        Log logger = LogFactory.getLog(PinyinUtil.class);
        String str = "〇的输￥￥#s，ldsa";
        logger.error("小写输出：" + getPinyinToLowerCase(str));
        logger.warn("大写输出：" + getPinyinToUpperCase(str));
        logger.warn("首字母大写输出：" + getPinyinFirstToUpperCase(str));
        logger.warn("返回中文的首字母输出：" + getPinYinHeadChar(str));
        logger.warn("返回中文的首字母并过滤特殊字符输出：" + getPinYinHeadCharFilter(str));


    }

}
