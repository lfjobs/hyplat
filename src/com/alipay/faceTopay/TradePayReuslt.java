package com.alipay.faceTopay;

/**
 *
 * alipay.trade.pay(统一收单交易支付接口结果码)
 */
public class TradePayReuslt {

	private String code; //返回码 10000 支付成功；40004：支付失败；10003：等待用户付款 20000：未知异常
	private String buyer_user_id; //买家ID
	private String trade_no;//支付宝交易订单号
	private String trade_status;//查询交易使用  交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBuyer_user_id() {
		return buyer_user_id;
	}

	public void setBuyer_user_id(String buyer_user_id) {
		this.buyer_user_id = buyer_user_id;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getTrade_status() {
		return trade_status;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
}
