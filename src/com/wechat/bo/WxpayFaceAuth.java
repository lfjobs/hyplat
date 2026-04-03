package com.wechat.bo;



/**
 * 人脸调用凭证
 */
public class WxpayFaceAuth {
	private String store_id;  //门店编号， 由商户定义， 各门店唯一。string(32)
	private String store_name;//店名称，由商户定义。（可用于展示）string(128)
	private String device_id;//终端设备编号，由商户定义。string(32)
	private String attach;//附加字段。字段格式使用Json 可选
	private String rawdata;//初始化数据。由微信人脸SDK的接口返回。
	private String appid;//商户号绑定的公众号/小程序 appid string(32)
	private String mch_id;//商户号 string(32)
	private String sub_appid;	//否	string(32)	子商户绑定的公众号/小程序 appid(服务商模式)
	private String sub_mch_id;//		否	string(32)	子商户号(服务商模式)
	private int now;//int	取当前时间，10位unix时间戳。 例如：1239878956
	private String version;//是	string	版本号。固定为1
	private String sign_type;//是	string	签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
	private String  nonce_str;//是	string(32)	随机字符串，不长于32位
	private String sign;//是	string	参数签名。详见微信支付签名算法


	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getRawdata() {
		return rawdata;
	}

	public void setRawdata(String rawdata) {
		this.rawdata = rawdata;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getSub_appid() {
		return sub_appid;
	}

	public void setSub_appid(String sub_appid) {
		this.sub_appid = sub_appid;
	}

	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	public int getNow() {
		return now;
	}

	public void setNow(int now) {
		this.now = now;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
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
}