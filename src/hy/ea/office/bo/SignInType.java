package hy.ea.office.bo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;

/**
 * 签到类型
 * @author
 *
 */
public class SignInType implements BaseBean, Serializable {
	private static final long serialVersionUID = 6022080854344620042L;
	private String signInTypeId;
	private String signInTypeName;
	private String signInTypeSerial;

	public SignInType() {
	}

	public SignInType(String signInTypeId, String signInTypeName, String signInTypeSerial) {
		this.signInTypeId = signInTypeId;
		this.signInTypeName = signInTypeName;
		this.signInTypeSerial = signInTypeSerial;
	}

	public String getSignInTypeId() {
		return signInTypeId;
	}

	public void setSignInTypeId(String signInTypeId) {
		this.signInTypeId = signInTypeId;
	}

	public String getSignInTypeName() {
		return signInTypeName;
	}

	public void setSignInTypeName(String signInTypeName) {
		this.signInTypeName = signInTypeName;
	}

	public String getSignInTypeSerial() {
		return signInTypeSerial;
	}

	public void setSignInTypeSerial(String signInTypeSerial) {
		this.signInTypeSerial = signInTypeSerial;
	}
}
