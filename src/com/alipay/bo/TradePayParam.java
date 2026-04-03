package com.alipay.bo;

/**
 *
 * alipay.trade.pay(统一收单交易支付接口请求参数)
 */
public class TradePayParam {
	private String out_trade_no;//必选64	商户订单号
	private String scene;//必选	32	支付场景 条码支付，取值：bar_code 声波支付，取值：wave_code
	private String auth_code;	//必选	32	支付授权码，25~30开头的长度为16~24位的数字，实际字符串长度以开发者获取的付款码长度为准	28763443825664394
	private String product_code;//可选	32	销售产品码	FACE_TO_FACE_PAYMENT
	private String subject;//必选	256	订单标题
	private String buyer_id;//可选	 28	买家的支付宝用户id，如果为空，会从传入了码值信息中获取买家ID
	private String seller_id;//可选 28	如果该值为空，则默认为商户签约账号对应的支付宝用户ID
	private String total_amount;	// Price	可选	11	订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
	private String trans_currency;//String	可选	8	标价币种,
	private String body;//	可选	128	订单描述	Iphone6 16G
	private String goods_detail;//GoodsDetail[]	可选		订单包含的商品列表信息，json格式，其它说明详见商品明细说明
	private String operator_id;//	String	可选	28	商户操作员编号	yx_001
	private String store_id;	//String	可选	32	商户门店编号	NJ_001
	private String terminal_id;//	String	可选	32	商户机具终端编号	NJ_T_001
	private String extend_params;//	ExtendParams	可选		业务扩展参数
	private String timeout_express;//	String	可选	6	该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m	90m
	private String auth_confirm_mode;	//String	可选	32	预授权确认模式，授权转交易请求中传入，适用于预授权转交易业务使用，目前只支持PRE_AUTH(预授权产品码)
	private String terminal_params;//	String	可选	2048	商户传入终端设备相关信息，具体值要和支付宝约定	{"key":"value"}
	private String promo_params;//	PromoParam	可选		优惠明细参数，通过此属性补充营销参数
	private String advance_payment_type;//		可选	20	支付模式类型,若值为ENJOY_PAY_V2表示当前交易允许走先享后付2.0垫资	ENJOY_PAY_V2

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getAuth_code() {
		return auth_code;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getTrans_currency() {
		return trans_currency;
	}

	public void setTrans_currency(String trans_currency) {
		this.trans_currency = trans_currency;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getGoods_detail() {
		return goods_detail;
	}

	public void setGoods_detail(String goods_detail) {
		this.goods_detail = goods_detail;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}

	public String getExtend_params() {
		return extend_params;
	}

	public void setExtend_params(String extend_params) {
		this.extend_params = extend_params;
	}

	public String getTimeout_express() {
		return timeout_express;
	}

	public void setTimeout_express(String timeout_express) {
		this.timeout_express = timeout_express;
	}

	public String getAuth_confirm_mode() {
		return auth_confirm_mode;
	}

	public void setAuth_confirm_mode(String auth_confirm_mode) {
		this.auth_confirm_mode = auth_confirm_mode;
	}

	public String getTerminal_params() {
		return terminal_params;
	}

	public void setTerminal_params(String terminal_params) {
		this.terminal_params = terminal_params;
	}

	public String getPromo_params() {
		return promo_params;
	}

	public void setPromo_params(String promo_params) {
		this.promo_params = promo_params;
	}

	public String getAdvance_payment_type() {
		return advance_payment_type;
	}

	public void setAdvance_payment_type(String advance_payment_type) {
		this.advance_payment_type = advance_payment_type;
	}
}
