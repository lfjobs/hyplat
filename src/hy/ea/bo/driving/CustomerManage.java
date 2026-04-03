package hy.ea.bo.driving;

import hy.plat.bo.BaseBean;
/**
 * 客户管理
 * @author Administrator
 *
 */
public class CustomerManage  implements BaseBean{
	private String customerManageKey; 
	private String customerManageID;
	private String companyID;       //公司id
	private String staffID;         //人员id
	private String goodsID;         //物品id
	private String goodsName;       //物品名称
	private String codeID;          //代码id
	private String codeName;        //代码名称
	private String startDate;       //起时间
	private String endDate;         //止时间
	private String interest;        //兴趣类别      00:有兴趣    01：特别有兴趣    02：一般有兴趣
	private String status;          //状态   00：客户分类    01：潜在客户分类    02：客户来源分类   03：客户产品兴趣
	private String customerRemarks;//客户备注
	
	public String getCustomerRemarks() {
		return customerRemarks;
	}

	public void setCustomerRemarks(String customerRemarks) {
		this.customerRemarks = customerRemarks;
	}

	public CustomerManage() {
		super();
	}
	
	public CustomerManage(String customerManageKey, String customerManageID,
			String companyID, String staffID, String goodsID, String goodsName,
			String codeID, String codeName, String startDate, String endDate,
			String interest, String status) {
		super();
		this.customerManageKey = customerManageKey;
		this.customerManageID = customerManageID;
		this.companyID = companyID;
		this.staffID = staffID;
		this.goodsID = goodsID;
		this.goodsName = goodsName;
		this.codeID = codeID;
		this.codeName = codeName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.interest = interest;
		this.status = status;
	}

	public String getCustomerManageKey() {
		return customerManageKey;
	}

	public void setCustomerManageKey(String customerManageKey) {
		this.customerManageKey = customerManageKey;
	}

	public String getCustomerManageID() {
		return customerManageID;
	}

	public void setCustomerManageID(String customerManageID) {
		this.customerManageID = customerManageID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStaffID() {
		return staffID;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getCodeID() {
		return codeID;
	}

	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}