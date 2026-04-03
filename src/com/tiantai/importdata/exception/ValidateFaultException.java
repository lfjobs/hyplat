package com.tiantai.importdata.exception;

public class ValidateFaultException extends ImportException {
	
	private static final long serialVersionUID = -8923125329186466114L;
	private Throwable target;
		
	public ValidateFaultException(Throwable target, String s) {
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
