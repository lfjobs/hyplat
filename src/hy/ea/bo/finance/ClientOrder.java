package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 收付码商城总订单
 * @author mz
 *
 */
public class ClientOrder implements BaseBean{
	
	private String cokey;
	private String coID;
    private String companyID;
    private String orderNum;//订单编号
	private String  boardNo;//餐桌编号  No0001f
	private String caterID;//餐桌表关联
    private Date createDate;//第一次下单时间
	private String balanceID;//结算人ID
	private String balanceName;//结算人姓名
	private Date balanceDate;//结算时间
	private String state;//01未结算，00：已结算
	private String totalMoney;//总金额
	private String cashierBillsID;//结算订单ID
	private String staffID;//付款用户ID
	private String staffName;//付款用户姓名
	private String tel;//联系电话


	public String getCokey() {
		return cokey;
	}

	public void setCokey(String cokey) {
		this.cokey = cokey;
	}

	public String getCoID() {
		return coID;
	}

	public void setCoID(String coID) {
		this.coID = coID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(String boardNo) {
		this.boardNo = boardNo;
	}

	public String getCaterID() {
		return caterID;
	}

	public void setCaterID(String caterID) {
		this.caterID = caterID;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getBalanceID() {
		return balanceID;
	}

	public void setBalanceID(String balanceID) {
		this.balanceID = balanceID;
	}

	public String getBalanceName() {
		return balanceName;
	}

	public void setBalanceName(String balanceName) {
		this.balanceName = balanceName;
	}

	public Date getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getCashierBillsID() {
		return cashierBillsID;
	}

	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
