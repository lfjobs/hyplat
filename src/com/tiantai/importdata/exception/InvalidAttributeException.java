package com.tiantai.importdata.exception;

public class InvalidAttributeException extends ImportException {
	
	private static final long serialVersionUID = -896354917149323033L;
	
	private Throwable target;
	
	public InvalidAttributeException(Throwable target, String s) {
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
