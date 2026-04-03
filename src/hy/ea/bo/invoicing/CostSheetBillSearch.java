package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

/**
 * 预算单据父表查询 CostSheetBill
 * @author mz
 *
 */
public class CostSheetBillSearch implements BaseBean,java.io.Serializable{
	
	
	private String csbskey;
	private String csbsid;
	private String zprojectname;//主项目名称
	private String sprojectname;//子项目名称当前物品所在项目名称
	private String goodsnum;//物品编号
	private String companyname;
	private String orgname;
	private String staffname;
	private String ccompanyname;
	private String cstaffname;
	private String start;
	private String end;
	public String getCsbskey() {
		return csbskey;
	}
	public void setCsbskey(String csbskey) {
		this.csbskey = csbskey;
	}
	public String getCsbsid() {
		return csbsid;
	}
	public void setCsbsid(String csbsid) {
		this.csbsid = csbsid;
	}
	public String getZprojectname() {
		return zprojectname;
	}
	public void setZprojectname(String zprojectname) {
		this.zprojectname = zprojectname;
	}
	public String getSprojectname() {
		return sprojectname;
	}
	public void setSprojectname(String sprojectname) {
		this.sprojectname = sprojectname;
	}
	public String getGoodsnum() {
		return goodsnum;
	}
	public void setGoodsnum(String goodsnum) {
		this.goodsnum = goodsnum;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getStaffname() {
		return staffname;
	}
	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}
	public String getCcompanyname() {
		return ccompanyname;
	}
	public void setCcompanyname(String ccompanyname) {
		this.ccompanyname = ccompanyname;
	}
	public String getCstaffname() {
		return cstaffname;
	}
	public void setCstaffname(String cstaffname) {
		this.cstaffname = cstaffname;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	

	
}
