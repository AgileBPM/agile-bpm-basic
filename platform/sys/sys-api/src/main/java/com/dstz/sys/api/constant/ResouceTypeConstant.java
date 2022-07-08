package com.dstz.sys.api.constant;

/**
 * 组织级别
 */
public enum ResouceTypeConstant {
    MENU("menu", "菜单"),
    LINK("link", "链接"),
    BUTTON("button", "按钮");

    private String key;
    private String label;


    ResouceTypeConstant(String key, String label) {
        this.setKey(key);
        this.label = label;
    }

   

    public String label() {
        return label;
    }

    public String getLabel() {
  		return label;
  	}


  	public void setLabel(String label) {
  		this.label = label;
  	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}
}
