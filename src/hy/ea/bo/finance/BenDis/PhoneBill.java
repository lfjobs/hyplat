package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.List;

public class PhoneBill implements BaseBean{
	private String cashierBillsID;
	private String cashierBillsKey;
	private String priceSub;//价钱总和
	private String billsType;// 单据类型
	private String journalNum;// 凭证号
	private String trade_no;//第三方支付交易号
	private String contactUserID;// 往来个人ID
	private String ctUserName;//往来个人name
	private Date cashierdate;//制单时间
	private String sccid;//用户账号id
	private String fkStatus;//状态
	private String companyid;//供应商id
	private String companyName;//供应商名称
	private String projectName;//产品名称
	private String cgscomid;//采购商公司id
	private String cgscomname;//采购商公司名称
	private String sellSccid;//商家账号sccId
	private String sellAccount;//商家账号account
	private String sellStaffName; //商家账户姓名
    private String wfStatus4;//在线支付:00微信支付，01，支付宝支付，02银联支付 03:线下转账
    private String extendedTime;  //收货延长时间
    private String remark;//备注留言
	private String privateRoom;//餐桌
	private String mealNum;//取餐号
	private String supplierstatus;//供应商状态/采购商状态 00:初始订单 01:拣货 02：发货 03：送货 04：物流 05:签收 06：收货 07：验货 08：入库 09：分金币
    private String paystatus;//支付状态 01：未支付 02：欠款 03：已支付
	private String receivename;//收货人姓名
	private String receivetel;//收货人电话
	private String receiveaddress;//收货人完整地址
	private String goodsCoding;//设备编号
	private String goodsName;//设备名称
    
	private String companyLogo;//非数据库字段ljc
	private String tradeCode;//行业类别
	private List<Object[]> goodsList;//产品列表
	private List<Object[]> ptgoodsList;//促销产品列表
	private List<Object[]> contactInfo;//买、卖家信息
	

	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getCashierBillsKey() {
		return cashierBillsKey;
	}
	public void setCashierBillsKey(String cashierBillsKey) {
		this.cashierBillsKey = cashierBillsKey;
	}
	public String getPriceSub() {
		return priceSub;
	}
	public void setPriceSub(String priceSub) {
		this.priceSub = priceSub;
	}
	public String getBillsType() {
		return billsType;
	}
	public void setBillsType(String billsType) {
		this.billsType = billsType;
	}
	public String getJournalNum() {
		return journalNum;
	}
	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getCgscomid() {
		return cgscomid;
	}
	public void setCgscomid(String cgscomid) {
		this.cgscomid = cgscomid;
	}
	public String getCgscomname() {
		return cgscomname;
	}
	public void setCgscomname(String cgscomname) {
		this.cgscomname = cgscomname;
	}
	public String getContactUserID() {
		return contactUserID;
	}
	public void setContactUserID(String contactUserID) {
		this.contactUserID = contactUserID;
	}
	public String getCtUserName() {
		return ctUserName;
	}
	public void setCtUserName(String ctUserName) {
		this.ctUserName = ctUserName;
	}
	public Date getCashierdate() {
		return cashierdate;
	}
	public void setCashierdate(Date cashierdate) {
		this.cashierdate = cashierdate;
	}
	public List<Object[]> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<Object[]> goodsList) {
		this.goodsList = goodsList;
	}
	public String getSccid() {
		return sccid;
	}
	public void setSccid(String sccid) {
		this.sccid = sccid;
	}
	public String getFkStatus() {
		return fkStatus;
	}
	public void setFkStatus(String fkStatus) {
		this.fkStatus = fkStatus;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getWfStatus4() {
		return wfStatus4;
	}
	public void setWfStatus4(String wfStatus4) {
		this.wfStatus4 = wfStatus4;
	}
	public String getExtendedTime() {
		return extendedTime;
	}
	public void setExtendedTime(String extendedTime) {
		this.extendedTime = extendedTime;
	}
	public List<Object[]> getPtgoodsList() {
		return ptgoodsList;
	}
	public void setPtgoodsList(List<Object[]> ptgoodsList) {
		this.ptgoodsList = ptgoodsList;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
    public List<Object[]> getContactInfo()
    {
        return contactInfo;
    }
    public void setContactInfo(List<Object[]> contactInfo)
    {
        this.contactInfo = contactInfo;
    }
	public String getSellSccid() {
		return sellSccid;
	}
	public void setSellSccid(String sellSccid) {
		this.sellSccid = sellSccid;
	}
	public String getSellAccount() {
		return sellAccount;
	}
	public void setSellAccount(String sellAccount) {
		this.sellAccount = sellAccount;
	}
	public String getSellStaffName() {
		return sellStaffName;
	}
	public void setSellStaffName(String sellStaffName) {
		this.sellStaffName = sellStaffName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPrivateRoom() {
		return privateRoom;
	}
	public void setPrivateRoom(String privateRoom) {
		this.privateRoom = privateRoom;
	}
	public String getMealNum() {
		return mealNum;
	}
	public void setMealNum(String mealNum) {
		this.mealNum = mealNum;
	}
	public String getSupplierstatus() {
		return supplierstatus;
	}
	public void setSupplierstatus(String supplierstatus) {
		this.supplierstatus = supplierstatus;
	}
	public String getPaystatus() {
		return paystatus;
	}
	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}
	public String getReceivename() {
		return receivename;
	}
	public void setReceivename(String receivename) {
		this.receivename = receivename;
	}
	public String getReceivetel() {
		return receivetel;
	}
	public void setReceivetel(String receivetel) {
		this.receivetel = receivetel;
	}
	public String getReceiveaddress() {
		return receiveaddress;
	}
	public void setReceiveaddress(String receiveaddress) {
		this.receiveaddress = receiveaddress;
	}

	public String getGoodsCoding() {
		return goodsCoding;
	}

	public void setGoodsCoding(String goodsCoding) {
		this.goodsCoding = goodsCoding;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}
