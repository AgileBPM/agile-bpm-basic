package com.dstz.bus.api.model.permission;

import java.util.Map;

public interface IBusTablePermission extends IAbstractPermission {

	String getKey();

	String getComment();

	Map<String, ? extends IBusColumnPermission> getColumnMap();

}
