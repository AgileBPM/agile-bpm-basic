package com.dstz.demo.core.dao;
import java.util.List;

import com.dstz.base.dao.BaseDao;
import com.dstz.demo.core.model.DemoSub;

/**
 * Demo子表 DAO接口
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-08-24 18:26:33
 */
public interface DemoSubDao extends BaseDao<String, DemoSub> {

	List<DemoSub> getByFk(String fk);

}
