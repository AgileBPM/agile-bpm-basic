package com.dstz.sys.api.constant;


public enum RightsObjectConstants {
	FLOW("FLOW","流程定义"),
	WORKBENCH("WORKBENCH","工作台");
	
	private String key;
	private String label;
	
	RightsObjectConstants(String key,String label){
		this.key = key;
		this.label = label;
	}
	public String key(){
		return key;		
	}
	public String label(){
		return label;		
	}	
	
	public static RightsObjectConstants getByKey(String key){
		for (RightsObjectConstants rights : RightsObjectConstants.values()) {
			if(rights.key.equals(key)){
				return rights;
			}
		}
		throw new RuntimeException(String.format(" key [%s] 对应RightsObjectConstants 不存在的权限常亮定义，请核查！",key));
	}
}
