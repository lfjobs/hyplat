package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

/**
 * mz
 *  分类的点击率搜索率
 */
public class ProductCateHot implements BaseBean,java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4681453137679520260L;
	private String pchKey;  //主键
	private String pchID;
	private String codeID;//分类ID
	private int hotnum;//热度即点击数量
	private String ccompanyID;//各自平台
	private String ccompanyPID;//父平台

	public String getPchKey() {
		return pchKey;
	}

	public void setPchKey(String pchKey) {
		this.pchKey = pchKey;
	}

	public String getPchID() {
		return pchID;
	}

	public void setPchID(String pchID) {
		this.pchID = pchID;
	}

	public String getCodeID() {
		return codeID;
	}

	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}

	public int getHotnum() {
		return hotnum;
	}

	public void setHotnum(int hotnum) {
		this.hotnum = hotnum;
	}

	public String getCcompanyID() {
		return ccompanyID;
	}

	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}

	public String getCcompanyPID() {
		return ccompanyPID;
	}

	public void setCcompanyPID(String ccompanyPID) {
		this.ccompanyPID = ccompanyPID;
	}
}
