package com.wechat.bo;



/**
 * 刷脸支付
 */
public class WxpayFace {

    private String appid;
	private String sub_appid;// 否 特约商户公众账号ID
	private String mch_id; //是 	微信支付分配的商户号
	private String sub_mch_id;//否  特约商户号
	private String device_info;// 否 32 设备号
	private String nonce_str; //是 String(32) 随机字符串
	private String sign;//是签名
	private String body;//是String(128) 格式要求：门店品牌名-城市分店名-实际商品名称
	private String detail;// 否
	private String attach;//否
	private String out_trade_no;// String(32) 商户订单号
	private String total_fee;//是  订单总金额，单位为分，只能为整数
	private String fee_type;//否 String(16) CNY
	private String spbill_create_ip;// 是 String(16) 128.0.0.1 调用微信支付API的机器IP
	private String goods_tag;// 否 String(32) 商品标记，代金券或立减优惠功能
	private String openid;//是 String(128) 用户在商户appid 下的唯一标识
	private String face_code;//是 String(128) 人脸凭证，用于刷脸支付
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSub_appid() {
		return sub_appid;
	}

	public void setSub_appid(String sub_appid) {
		this.sub_appid = sub_appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getFace_code() {
		return face_code;
	}

	public void setFace_code(String face_code) {
		this.face_code = face_code;
	}









}