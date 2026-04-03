package com.wechatpay.bo;

public class WxPayDto {

	private String orderId;//订单号
	private String totalFee;//金额
	private String spbillCreateIp;//订单生成的机器 IP
	private String notifyUrl;//这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等
	private String body;// 商品描述根据情况修改
	private String openId;//微信用户对一个公众号唯一
	private String companyId;
	private String wechatbz;
	private String transaction_id;//微信订单号
	private String out_trade_no;
	private String time_end;
	private String trade_state;
	private String trade_state_des;
	private String return_code;
	private String return_msg;
	private String result_code;
	private String prepay_id;
	private String err_code;
	private String attach;

	//关于退款的新增
	private String refundno;//退款编号
	private String refundfee;//退款金额


	//关于刷脸
	private String authinfo;
	private int  expires_in;

	private  String sub_mchid;
	private String sp_appid;
	private String 	out_order_no;//商户分帐单号
	private String description;//分账描述

	
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the totalFee
	 */
	public String getTotalFee() {
		return totalFee;
	}
	/**
	 * @param totalFee the totalFee to set
	 */
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	/**
	 * @return the spbillCreateIp
	 */
	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}
	/**
	 * @param spbillCreateIp the spbillCreateIp to set
	 */
	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}
	/**
	 * @return the notifyUrl
	 */
	public String getNotifyUrl() {
		return notifyUrl;
	}
	/**
	 * @param notifyUrl the notifyUrl to set
	 */
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}
	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}
	/**
	 * @return the openId
	 */
	public String getOpenId() {
		return openId;
	}
	/**
	 * @param openId the openId to set
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getWechatbz() {
		return wechatbz;
	}
	public void setWechatbz(String wechatbz) {
		this.wechatbz = wechatbz;
	}
	public String getRefundno() {
		return refundno;
	}
	public void setRefundno(String refundno) {
		this.refundno = refundno;
	}
	public String getRefundfee() {
		return refundfee;
	}
	public void setRefundfee(String refundfee) {
		this.refundfee = refundfee;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public String getTrade_state_des() {
		return trade_state_des;
	}
	public void setTrade_state_des(String trade_state_des) {
		this.trade_state_des = trade_state_des;
	}
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getTrade_state() {
		return trade_state;
	}
	public void setTrade_state(String trade_state) {
		this.trade_state = trade_state;
	}
	public String getPrepay_id() {
		return prepay_id;
	}
	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getAttach()
	{
		return attach;
	}

	public void setAttach(String attach)
	{
		this.attach = attach;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getAuthinfo() {
		return authinfo;
	}

	public void setAuthinfo(String authinfo) {
		this.authinfo = authinfo;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String getSub_mchid() {
		return sub_mchid;
	}

	public void setSub_mchid(String sub_mchid) {
		this.sub_mchid = sub_mchid;
	}

	public String getSp_appid() {
		return sp_appid;
	}

	public void setSp_appid(String sp_appid) {
		this.sp_appid = sp_appid;
	}

	public String getOut_order_no() {
		return out_order_no;
	}

	public void setOut_order_no(String out_order_no) {
		this.out_order_no = out_order_no;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
