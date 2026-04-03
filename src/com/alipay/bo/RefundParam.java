package com.alipay.bo;

public class RefundParam {

	//服务器异步通知页面路径 http://商户网关地址/refund_fastpay_by_platform_pwd-JAVA-UTF-8/notify_url.jsp";
	//需http://格式的完整路径，不允许加?id=123这类自定义参数
	private  String  notify_url;
	

	//卖家支付宝帐户= new String(request.getParameter("WIDseller_email").getBytes("ISO-8859-1"),"UTF-8");
	private String seller_email;
	//必填    
	//退款当天日期  new String(request.getParameter("WIDrefund_date").getBytes("ISO-8859-1"),"UTF-8");
	//必填，格式：年[4位]-月[2位]-日[2位] 小时[2位 24小时制]:分[2位]:秒[2位]，如：2007-10-01 13:13:13
	private String refund_date;


	//批次号 new String(request.getParameter("WIDbatch_no").getBytes("ISO-8859-1"),"UTF-8");
	//必填，格式：当天日期[8位]+序列号[3至24位]，如：201008010000001
	private String batch_no;
	

	//退款笔数 new String(request.getParameter("WIDbatch_num").getBytes("ISO-8859-1"),"UTF-8");
	//必填，参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）
	private String batch_num;
	

	//退款详细数据   new String(request.getParameter("WIDdetail_data").getBytes("ISO-8859-1"),"UTF-8");
	//必填，具体格式请参见接口技术文档
	private String detail_data;


	public String getNotify_url() {
		return notify_url;
	}


	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}


	public String getSeller_email() {
		return seller_email;
	}


	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}


	public String getRefund_date() {
		return refund_date;
	}


	public void setRefund_date(String refund_date) {
		this.refund_date = refund_date;
	}


	public String getBatch_no() {
		return batch_no;
	}


	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}


	public String getBatch_num() {
		return batch_num;
	}


	public void setBatch_num(String batch_num) {
		this.batch_num = batch_num;
	}


	public String getDetail_data() {
		return detail_data;
	}


	public void setDetail_data(String detail_data) {
		this.detail_data = detail_data;
	}
	
	
	
	
}
