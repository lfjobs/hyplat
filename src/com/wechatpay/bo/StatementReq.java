package com.wechatpay.bo;

/**
 * 对账单请求参数
 * 
 * @author mz
 *
 */
public class StatementReq {
	private String appid;//微信分配的公众账号ID（企业号corpid即为此appId）
	private String billdate;//下载对账单的日期，格式：20140603
	private String billtype;//ALL，返回当日所有订单信息，默认值 SUCCESS，返回当日成功支付的订单 REFUND，返回当日退款订单 REVOKED，已撤销的订单
	private String mchid;//微信支付分配的商户号
	private String noncestr;//随机字符串，不长于32位。推荐随机数生成算法
	private String sign;
	private String device_info;//微信支付分配的终端设备号，填写此字段，只下载该设备号的对账单
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getBilldate() {
		return billdate;
	}
	public void setBilldate(String billdate) {
		this.billdate = billdate;
	}
	public String getBilltype() {
		return billtype;
	}
	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}
	public String getMchid() {
		return mchid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	
    

}




