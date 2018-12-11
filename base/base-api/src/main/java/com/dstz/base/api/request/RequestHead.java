package com.dstz.base.api.request;

/**
 * TODO 请求头
 */
public class RequestHead {

    private String sourceSystem;

    private String operator;

    private String memo;

    private String ip;

    private Boolean isEncryptData;

    private String secreKey;
    
    private String traceId ;

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean getIsEncryptData() {
        return isEncryptData;
    }

    public void setIsEncryptData(Boolean isEncryptData) {
        this.isEncryptData = isEncryptData;
    }

    public String getSecreKey() {
        return secreKey;
    }

    public void setSecreKey(String secreKey) {
        this.secreKey = secreKey;
    }

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
}
