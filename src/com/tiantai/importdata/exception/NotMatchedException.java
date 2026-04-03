package com.tiantai.importdata.exception;

public class NotMatchedException extends ValidateFaultException {
	
	private static final long serialVersionUID = -5379820024441633910L;
	
	private Throwable target;
	
	public NotMatchedException(Throwable target, String s) {
		super(null,s);
		this.setTarget(target);
	}

	public Throwable getTarget() {
		return target;
	}

	public void setTarget(Throwable target) {
		this.target = target;
	}

}
