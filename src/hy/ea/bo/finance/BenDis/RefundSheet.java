package hy.ea.bo.finance.BenDis;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * 
 * 退款单
 * 
 * @author mz
 * 
 */
public class RefundSheet implements BaseBean, ExcelBean ,java.io.Serializable{
    //基本信息
	private String rskey;
	private String rsid;
	private String orderCode;// 订单编号
	private String cashierBillsID;//订单ID
	private String refundCode;// 退款单编号
	private String companyID;// 公司ID
	private String companyLogo;//非数据库字段ljc
	private String companyName;// 公司名称
	private Date orderDate;// 下单时间
	//用户信息
	private String userID;// 用户ID
	private String userName;// 用户Name
	private String userAccount;// 用户账号；
	private String vipType;// 会员类别
	
	private String remark;//备注
	
    private String ppid;//关联产品id
	//退款申请字段
	private String refundType;//退货类型 ：00:仅退款  01：退款退货  02：换货 03：维修
	private String refundCause;//退货原因
	private String refundMoney;//退款金额
	private String refundNum;//退货数量
	private String refundRemark;//退款说明
	private String voucherfile1;//凭证图片1路径
	private String voucherfile2;//凭证图片2路径
	private String voucherfile3;//凭证图片3路径
	private Date refundDate;//退货申请时间
	//同意退款后卖家提供退款地址相关信息
	private String refundstate;//退款单状态；00:买家提交退款(退货退款)申请；01：卖家同意退款 （退货退款）02：卖家拒绝退款（退货退款）03:买家退货中 04：卖家确认收货 05：卖家已银行打款 06：买家提交售后申请 07:卖家同意售后 08：买家售后中 09：确认售后  
	private Date dealDate;//卖家同意或者拒绝退款时间
	private String refundAddress;//退货地址
	private String postcode;//退货地址邮编
	private String receiverID;//接收人ID
	private String receiverName;//接收人Name
	private String receiverTel;//接收人电话
	//买家把货通过快递寄出后提供快递信息
	private String express;//快递公司
	private String waybillno;//运单号
	private String expCode;//快递公司标识   例如  顺丰  (sf)
	private String freight;//运费
	private String count;
	private Date backDate;//寄出时间
	
	//卖家收到退回来的货
	private Date confirmConsigneeDate;//卖家确认收到退回来的货的时间
	private String warehouseCode;//接收仓库编号
	private Date remitDate;//银行退款时间
	private String bankName;//银行名称
	private String bankAccount;//银行账号
	private String bankUser;//银行用户名
	private String accountstatus;//到账状态 00处理中，01:成功 02：失败
	
	
	private Object goodsObject;//产品列表
	private Object salesObject;//促销产品列表
	
	
   
	public static String[] columnHeadings() {
		String[] titles = { "序号", "退款单编号", "订单编号", "下单时间", "用户姓名", "用户账号",
				"会员类型", "退款类型", "退货原因", "退款金额", "退货数量", "退款说明", "退货申请时间",
				"处理时间", "状态", "公司" };
		return titles;
	}

	@Override
	public String[] properties() {

		String[] properties = {refundCode,orderCode,String.format("%1$tF %1$tT",orderDate),userName,userAccount,vipType,refundType=="00"?"仅退款":"退款并退货",refundCause,refundMoney,refundNum,refundRemark
				,String.format("%1$tF %1$tT",refundDate),String.format("%1$tF %1$tT",dealDate),getRefundStateValue(refundstate),companyName
				 };
		return properties;
	}
    
	
	private String getRefundStateValue(String refundstate){
		String statevalue = "";
		if(refundstate.equals("00")){
			statevalue = "买家提交退款申请";
		}else if(refundstate.equals("01")){
			statevalue = "卖家同意退款";
			
		}else if(refundstate.equals("02")){
			statevalue = "卖家拒绝退款";
		}else if(refundstate.equals("03")){
			statevalue = "卖家确认收货";
		}else{
			statevalue = "卖家已银行打款";
		}
		
		return statevalue ;
	}
	public String getRskey() {
		return rskey;
	}

	public void setRskey(String rskey) {
		this.rskey = rskey;
	}

	public String getRsid() {
		return rsid;
	}

	public void setRsid(String rsid) {
		this.rsid = rsid;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getCashierBillsID() {
		return cashierBillsID;
	}

	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}

	public String getRefundCode() {
		return refundCode;
	}

	public void setRefundCode(String refundCode) {
		this.refundCode = refundCode;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getRefundCause() {
		return refundCause;
	}

	public void setRefundCause(String refundCause) {
		this.refundCause = refundCause;
	}

	public String getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(String refundMoney) {
		this.refundMoney = refundMoney;
	}

	public String getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(String refundNum) {
		this.refundNum = refundNum;
	}

	public String getRefundRemark() {
		return refundRemark;
	}

	public void setRefundRemark(String refundRemark) {
		this.refundRemark = refundRemark;
	}

	public String getVoucherfile1() {
		return voucherfile1;
	}

	public void setVoucherfile1(String voucherfile1) {
		this.voucherfile1 = voucherfile1;
	}

	public String getVoucherfile2() {
		return voucherfile2;
	}

	public void setVoucherfile2(String voucherfile2) {
		this.voucherfile2 = voucherfile2;
	}

	public String getVoucherfile3() {
		return voucherfile3;
	}

	public void setVoucherfile3(String voucherfile3) {
		this.voucherfile3 = voucherfile3;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public String getRefundstate() {
		return refundstate;
	}

	public void setRefundstate(String refundstate) {
		this.refundstate = refundstate;
	}

	public String getRefundAddress() {
		return refundAddress;
	}

	public void setRefundAddress(String refundAddress) {
		this.refundAddress = refundAddress;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(String receiverID) {
		this.receiverID = receiverID;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverTel() {
		return receiverTel;
	}

	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getWaybillno() {
		return waybillno;
	}

	public void setWaybillno(String waybillno) {
		this.waybillno = waybillno;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public Date getConfirmConsigneeDate() {
		return confirmConsigneeDate;
	}

	public void setConfirmConsigneeDate(Date confirmConsigneeDate) {
		this.confirmConsigneeDate = confirmConsigneeDate;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public Date getRemitDate() {
		return remitDate;
	}

	public void setRemitDate(Date remitDate) {
		this.remitDate = remitDate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankUser() {
		return bankUser;
	}

	public void setBankUser(String bankUser) {
		this.bankUser = bankUser;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public String getAccountstatus() {
		return accountstatus;
	}

	public void setAccountstatus(String accountstatus) {
		this.accountstatus = accountstatus;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public Date getBackDate() {
		return backDate;
	}

	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}

	public String getExpCode() {
		return expCode;
	}

	public void setExpCode(String expCode) {
		this.expCode = expCode;
	}
	
	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public Object getGoodsObject() {
		return goodsObject;
	}

	public void setGoodsObject(Object goodsObject) {
		this.goodsObject = goodsObject;
	}

	public Object getSalesObject() {
		return salesObject;
	}

	public void setSalesObject(Object salesObject) {
		this.salesObject = salesObject;
	}

	

	
	
	
   
	
	

}
