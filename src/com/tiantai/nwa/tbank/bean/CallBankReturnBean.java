package com.tiantai.nwa.tbank.bean;

import org.jdom.Element;

public class CallBankReturnBean {
	
	private String cCTransCode;//内部交易代码
	private String reqSeqNo;//请求方流水号
	private String respSource;//返回来源
	private String respSeqNo;//应答流水号
	private String respDate;//返回日期
	private String respTime;//返回时间
	private String respCode;//返回码
	private String respInfo;//返回信息
	private String rxtInfo;//返回扩展信息
	private String fileFlag;//数据文件标识 <
	
	private Element cme;//包含RecordNum(记录数)、FieldNum(字段数)
	private Element cmp;//包含RespPrvData(私有数据区)、BatchFileName(批量文件名)	
	
	private Element corp;
	private Element acc;	
	
	private Element channel;
	
	public String getcCTransCode() {
		return cCTransCode;
	}
	public void setcCTransCode(String cCTransCode) {
		this.cCTransCode = cCTransCode;
	}
	public String getReqSeqNo() {
		return reqSeqNo;
	}
	public void setReqSeqNo(String reqSeqNo) {
		this.reqSeqNo = reqSeqNo;
	}
	public String getRespSource() {
		return respSource;
	}
	public void setRespSource(String respSource) {
		this.respSource = respSource;
	}
	public String getRespSeqNo() {
		return respSeqNo;
	}
	public void setRespSeqNo(String respSeqNo) {
		this.respSeqNo = respSeqNo;
	}
	public String getRespDate() {
		return respDate;
	}
	public void setRespDate(String respDate) {
		this.respDate = respDate;
	}
	public String getRespTime() {
		return respTime;
	}
	public void setRespTime(String respTime) {
		this.respTime = respTime;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespInfo() {
		return respInfo;
	}
	public void setRespInfo(String respInfo) {
		this.respInfo = respInfo;
	}
	public String getRxtInfo() {
		return rxtInfo;
	}
	public void setRxtInfo(String rxtInfo) {
		this.rxtInfo = rxtInfo;
	}
	public String getFileFlag() {
		return fileFlag;
	}
	public void setFileFlag(String fileFlag) {
		this.fileFlag = fileFlag;
	}
	public Element getCme() {
		return cme;
	}
	public void setCme(Element cme) {
		this.cme = cme;
	}
	public Element getCmp() {
		return cmp;
	}
	public void setCmp(Element cmp) {
		this.cmp = cmp;
	}
	public Element getCorp() {
		return corp;
	}
	public void setCorp(Element corp) {
		this.corp = corp;
	}
	public Element getAcc() {
		return acc;
	}
	public void setAcc(Element acc) {
		this.acc = acc;
	}
	public Element getChannel() {
		return channel;
	}
	public void setChannel(Element channel) {
		this.channel = channel;
	}
	
}
