package hy.ea.office.bo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 考勤类别
 */
public class CheckOnType implements BaseBean, Serializable {
	private static final long serialVersionUID = -6316327516327853823L;
	private String checkOnTypeId;
	private String checkOnTypeName;
	private String checkOnTypeSerial;

	public CheckOnType() {
	}

	public CheckOnType(String checkOnTypeId, String checkOnTypeName, String checkOnTypeSerial) {
		this.checkOnTypeId = checkOnTypeId;
		this.checkOnTypeName = checkOnTypeName;
		this.checkOnTypeSerial = checkOnTypeSerial;
	}

	public String getCheckOnTypeId() {
		return checkOnTypeId;
	}

	public void setCheckOnTypeId(String checkOnTypeId) {
		this.checkOnTypeId = checkOnTypeId;
	}

	public String getCheckOnTypeName() {
		return checkOnTypeName;
	}

	public void setCheckOnTypeName(String checkOnTypeName) {
		this.checkOnTypeName = checkOnTypeName;
	}

	public String getCheckOnTypeSerial() {
		return checkOnTypeSerial;
	}

	public void setCheckOnTypeSerial(String checkOnTypeSerial) {
		this.checkOnTypeSerial = checkOnTypeSerial;
	}
}
