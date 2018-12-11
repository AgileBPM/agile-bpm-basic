package com.dstz.base.api.exception;

public class SerializeException extends RuntimeException {
	private static final long serialVersionUID = 8847845622247770262L;

	public SerializeException(String msg) {
	        super(msg);
	    }

	    public SerializeException(String msg, Throwable throwable) {
	        super(msg, throwable);
	    }

	    public SerializeException(Throwable throwable) {
	        super(throwable);
	    }
	

}
