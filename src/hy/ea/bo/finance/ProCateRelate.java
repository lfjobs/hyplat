package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

/**
 * 产品和类目关联表
 * mz
 */
public class ProCateRelate implements BaseBean {
	private String pcrKey;  //主键
	private String pcrId;  //业务主键
	private String ppID;//产品ID
	private String codeID;//产品类目ID
	private String companyID;//公司ID

	public String getPcrKey() {
		return pcrKey;
	}

	public void setPcrKey(String pcrKey) {
		this.pcrKey = pcrKey;
	}

	public String getPcrId() {
		return pcrId;
	}

	public void setPcrId(String pcrId) {
		this.pcrId = pcrId;
	}

	public String getPpID() {
		return ppID;
	}

	public void setPpID(String ppID) {
		this.ppID = ppID;
	}

	public String getCodeID() {
		return codeID;
	}

	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
}

