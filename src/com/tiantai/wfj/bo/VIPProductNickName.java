package com.tiantai.wfj.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;
/**
 * 会员产品和昵称关系表（各公司可以设置自己的会员昵称）
 * 
 * @author mz
 *
 */
public class VIPProductNickName implements BaseBean {

	private String vpkey;
	private String vpId;
	private String companyID;
	private String ccompanyID;
	private String nickname;// 昵称
	private String ppID;// 会员产品ID
	private Date updateDate;// 修改时间
	private String comstate;// 20大型；21：中型；22:小型 30：微型；40：微小型 50小微 60:省级
							// 61：县级，62：村级

	public String getVpkey() {
		return vpkey;
	}

	public void setVpkey(String vpkey) {
		this.vpkey = vpkey;
	}

	public String getVpId() {
		return vpId;
	}

	public void setVpId(String vpId) {
		this.vpId = vpId;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCcompanyID() {
		return ccompanyID;
	}

	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPpID() {
		return ppID;
	}

	public void setPpID(String ppID) {
		this.ppID = ppID;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getComstate() {
		return comstate;
	}

	public void setComstate(String comstate) {
		this.comstate = comstate;
	}

}