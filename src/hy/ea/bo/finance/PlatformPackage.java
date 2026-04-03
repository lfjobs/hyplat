package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

/**
 * mz
 *  新闻分享等发布的行业平台
 */
public class PlatformPackage implements BaseBean,java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3904338282719167632L;
	private String pfKey;  //主键
	private String pfID;
	private String ppID;  //新闻等ID即ProductPackaging的ID
	private String ccomIDPlatform;//行业平台对应的往来单位ID；

	public String getPfKey() {
		return pfKey;
	}

	public void setPfKey(String pfKey) {
		this.pfKey = pfKey;
	}

	public String getPfID() {
		return pfID;
	}

	public void setPfID(String pfID) {
		this.pfID = pfID;
	}

	public String getPpID() {
		return ppID;
	}

	public void setPpID(String ppID) {
		this.ppID = ppID;
	}

	public String getCcomIDPlatform() {
		return ccomIDPlatform;
	}

	public void setCcomIDPlatform(String ccomIDPlatform) {
		this.ccomIDPlatform = ccomIDPlatform;
	}
}
