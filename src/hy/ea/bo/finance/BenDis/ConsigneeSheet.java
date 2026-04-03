package hy.ea.bo.finance.BenDis;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 
 * 收货单
 * 
 * @author mz
 * 
 */
public class ConsigneeSheet implements BaseBean, ExcelBean ,java.io.Serializable {

	private String cskey;
	private String csid;
	private String orderCode;// 订单编号
	private String cashierBillsID;//订单ID
	private String consigneCode;// 收货单编号
	private String companyID;// 公司ID
	private String companyName;// 公司名称
	private Date orderDate;// 下单时间
	private String userID;// 用户ID
	private String userName;// 用户Name
	private String userAccount;// 用户账号；
	private String vipType;// 会员类别
	private String consigneeName;// 收货人名称
	private String consigneeTel;// 收货电话
	private String postcode;// 邮编
	private String consigneeAddress;// 收货地址
	private Date consignneDate;// 收货时间
	private String senderName;// 发货人名称
	private String senderTel;// 发货电话
	private Date sendDate;// 发货时间
	private String sendAddress;// 发货地址
	private String state;//状态00=待评价11=交易成功  22=作废

	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "收货单编号", "订单编号", "下单时间", "用户姓名", "用户账号",
				"会员类型", "收货人名称", "收货人电话", "邮编", "收货地址", "收货时间", "发货人名称",
				"发货人电话", "发货地址", "发货时间", "公司" };
		return titles;
	}

	@Override
	public String[] properties() {

		String[] properties = { consigneCode, orderCode,
				String.format("%1$tF %1$tT", orderDate), userName, userAccount,
				vipType, consigneeName, consigneeTel, postcode,consigneeAddress,
				String.format("%1$tF %1$tT", consignneDate), senderName,
				senderTel, sendAddress, String.format("%1$tF %1$tT", sendDate),
				companyName };
		return properties;
	}

	public String getCskey() {
		return cskey;
	}

	public void setCskey(String cskey) {
		this.cskey = cskey;
	}

	public String getCsid() {
		return csid;
	}

	public void setCsid(String csid) {
		this.csid = csid;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getConsigneCode() {
		return consigneCode;
	}

	public void setConsigneCode(String consigneCode) {
		this.consigneCode = consigneCode;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getVipType() {
		return vipType;
	}

	public void setVipType(String vipType) {
		this.vipType = vipType;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeTel() {
		return consigneeTel;
	}

	public void setConsigneeTel(String consigneeTel) {
		this.consigneeTel = consigneeTel;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public Date getConsignneDate() {
		return consignneDate;
	}

	public void setConsignneDate(Date consignneDate) {
		this.consignneDate = consignneDate;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderTel() {
		return senderTel;
	}

	public void setSenderTel(String senderTel) {
		this.senderTel = senderTel;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public String getCashierBillsID() {
		return cashierBillsID;
	}

	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


}
