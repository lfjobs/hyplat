package com.tiantai.nwa.tbank.bean;

public class CallBankRequestBean {
	
	
	private String esx;//银行缩写
	private String innerTransCode;//erp内部交易代码
	
	private String cCTransCode;//银行内部交易代码
	private String corpNo;//企业技监局号码/客户号
	private String OpNo;//操作员号
	private String reqSeqNo;//请求方流水号
	private String reqDate;//请求日期
	private String reqTime;//请求时间
	
	private String dbAccNo;//借方账号
	private String dbProv;//借方省市代码
	private String dbCur;//借方货币号
	
	//交易明细
	private String startDate;
	private String endDate;
	private String lastJrnNo;//末笔日志
	private String startTime;//末笔时间戳
	
	private String isEncrypted = "0";//是否加密
	
	private String server;
	private String port;
	
	
	private String reqDatagram;//请求报文
	
	public String getEsx() {
		return esx;
	}
	public void setEsx(String esx) {
		this.esx = esx;
	}
	public String getInnerTransCode() {
		return innerTransCode;
	}
	public void setInnerTransCode(String innerTransCode) {
		this.innerTransCode = innerTransCode;
	}
	public String getCCTransCode() {
		return cCTransCode;
	}
	public void setCCTransCode(String transCode) {
		cCTransCode = transCode;
	}
	public String getCorpNo() {
		return corpNo;
	}
	public void setCorpNo(String corpNo) {
		this.corpNo = corpNo;
	}
	public String getReqSeqNo() {
		return reqSeqNo;
	}
	public void setReqSeqNo(String reqSeqNo) {
		this.reqSeqNo = reqSeqNo;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getReqTime() {
		return reqTime;
	}
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	public String getDbAccNo() {
		return dbAccNo;
	}
	public void setDbAccNo(String dbAccNo) {
		this.dbAccNo = dbAccNo;
	}
	public String getDbProv() {
		return dbProv;
	}
	public void setDbProv(String dbProv) {
		this.dbProv = dbProv;
	}
	public String getDbCur() {
		return dbCur;
	}
	public void setDbCur(String dbCur) {
		this.dbCur = dbCur;
	}
	public String getReqDatagram() {
		return reqDatagram;
	}
	public void setReqDatagram(String reqDatagram) {
		String str_length  = String.valueOf(reqDatagram.length());
		int space_length = 6 - str_length.length();
		String space = "";
		for (int i = 0; i < space_length; i++) {
			space += " ";
		}
		this.reqDatagram = getIsEncrypted() + str_length + space + reqDatagram;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getLastJrnNo() {
		return lastJrnNo;
	}
	public void setLastJrnNo(String lastJrnNo) {
		this.lastJrnNo = lastJrnNo;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getOpNo() {
		return OpNo;
	}
	public void setOpNo(String opNo) {
		OpNo = opNo;
	}
	public String getIsEncrypted() {
		return isEncrypted;
	}
	public void setIsEncrypted(String isEncrypted) {
		this.isEncrypted = isEncrypted;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	
	
	
}
