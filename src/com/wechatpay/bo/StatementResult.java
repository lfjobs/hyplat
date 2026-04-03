package com.wechatpay.bo;

/**
 * 对账单请求返回结果
 * 
 * @author mz
 *
 */
public class StatementResult {
   private String billdate;//交易时间
   private String appid;//公众账号ID
   private String mchid;//商户号
   private String submchid;//子商户号
   private String device_info;//设备号
   private String transactionId;//微信订单号
   private String outTradeNo;//商户订单号
   private String openid;//用户标识
   private String tradeType;//交易类型
   private String tradeState;//交易状态
   private String bankType;//付款银行
   private String feeType;//货币种类
   private String totalFee;//总金额
   private String redfee;//企业红包金额
   private String backwxorderId;//微信退款单号
   private String backorderid;//商户退款单号
   private String backtotal;//退款金额
   private String redbacktotal;//企业红包退款金额
   private String backtype;//退款类型
   private String backstate;//退款状态
   private String goodname;//商品名称
   private String mchdata;//商户数据包
   private String sfee;//手续费
   private String feerate;//费率
   
   
public String getBilldate() {
	return billdate;
}
public void setBilldate(String billdate) {
	this.billdate = billdate;
}
public String getAppid() {
	return appid;
}
public void setAppid(String appid) {
	this.appid = appid;
}
public String getMchid() {
	return mchid;
}
public void setMchid(String mchid) {
	this.mchid = mchid;
}
public String getSubmchid() {
	return submchid;
}
public void setSubmchid(String submchid) {
	this.submchid = submchid;
}
public String getDevice_info() {
	return device_info;
}
public void setDevice_info(String device_info) {
	this.device_info = device_info;
}
public String getTransactionId() {
	return transactionId;
}
public void setTransactionId(String transactionId) {
	this.transactionId = transactionId;
}
public String getOutTradeNo() {
	return outTradeNo;
}
public void setOutTradeNo(String outTradeNo) {
	this.outTradeNo = outTradeNo;
}
public String getOpenid() {
	return openid;
}
public void setOpenid(String openid) {
	this.openid = openid;
}
public String getTradeType() {
	return tradeType;
}
public void setTradeType(String tradeType) {
	this.tradeType = tradeType;
}
public String getTradeState() {
	return tradeState;
}
public void setTradeState(String tradeState) {
	this.tradeState = tradeState;
}
public String getBankType() {
	return bankType;
}
public void setBankType(String bankType) {
	this.bankType = bankType;
}
public String getFeeType() {
	return feeType;
}
public void setFeeType(String feeType) {
	this.feeType = feeType;
}
public String getTotalFee() {
	return totalFee;
}
public void setTotalFee(String totalFee) {
	this.totalFee = totalFee;
}
public String getRedfee() {
	return redfee;
}
public void setRedfee(String redfee) {
	this.redfee = redfee;
}
public String getBackwxorderId() {
	return backwxorderId;
}
public void setBackwxorderId(String backwxorderId) {
	this.backwxorderId = backwxorderId;
}
public String getBackorderid() {
	return backorderid;
}
public void setBackorderid(String backorderid) {
	this.backorderid = backorderid;
}
public String getBacktotal() {
	return backtotal;
}
public void setBacktotal(String backtotal) {
	this.backtotal = backtotal;
}
public String getRedbacktotal() {
	return redbacktotal;
}
public void setRedbacktotal(String redbacktotal) {
	this.redbacktotal = redbacktotal;
}
public String getBacktype() {
	return backtype;
}
public void setBacktype(String backtype) {
	this.backtype = backtype;
}
public String getBackstate() {
	return backstate;
}
public void setBackstate(String backstate) {
	this.backstate = backstate;
}
public String getGoodname() {
	return goodname;
}
public void setGoodname(String goodname) {
	this.goodname = goodname;
}
public String getMchdata() {
	return mchdata;
}
public void setMchdata(String mchdata) {
	this.mchdata = mchdata;
}
public String getSfee() {
	return sfee;
}
public void setSfee(String sfee) {
	this.sfee = sfee;
}
public String getFeerate() {
	return feerate;
}
public void setFeerate(String feerate) {
	this.feerate = feerate;
}


    

}




