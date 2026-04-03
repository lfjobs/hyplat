package hy.ea.bo.office;

import hy.plat.bo.BaseBean;


/*
 * 考场收费
 * */
public class ExaminationRoomCharge implements BaseBean{
	
	private String ercID;//主键
	private String erckey;
	private String chargeNumber;//收费编号
	private String erId;//考场id
	private String goodsID;//收费物品id
	private String staffID;//责任人ID
	private String staffName;//责任人
	private String CompanyID;//公司ID
	private String startUsing;//是否启用00:启用,01:未启用,02:已删除

	public String getErcID() {
		return ercID;
	}

	public void setErcID(String ercID) {
		this.ercID = ercID;
	}

	public String getErckey() {
		return erckey;
	}

	public void setErckey(String erckey) {
		this.erckey = erckey;
	}

	public String getChargeNumber() {
		return chargeNumber;
	}

	public void setChargeNumber(String chargeNumber) {
		this.chargeNumber = chargeNumber;
	}

	public String getErId() {
		return erId;
	}

	public void setErId(String erId) {
		this.erId = erId;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getCompanyID() {
		return CompanyID;
	}

	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}

	public String getStartUsing() {
		return startUsing;
	}

	public void setStartUsing(String startUsing) {
		this.startUsing = startUsing;
	}
}
