package com.tiantai.importdata.exception;

/**
 * 数据导入的根异常类
 * @author zhb
 *
 */
public class ImportException extends Exception {

	private static final long serialVersionUID = -3374090454121237708L;

	private Throwable target;

	public ImportException() {
		super((Throwable) null);
	}

	public ImportException(Throwable target) {
		super((Throwable) null);
		this.target = target;
	}

	public ImportException(Throwable target, String s) {
		super(s, null);
		this.target = target;
	}

	public Throwable getTargetException() {
		return target;
	}

	public Throwable getCause() {
		return target;
	}

}
