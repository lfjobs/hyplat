package hy.ea.bo.finance.BenDis;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 订单收发货表  DtOrderBillAdd entity. @author MyEclipse Persistence Tools
 */

public class DtOrderBillAdd implements BaseBean {

	// Fields

	private String oaKey;
	private String oaId;
	private String oaComId;//公司id
	private String oaSccId;//用户账号外键

	private String oaBillId;//订单id
	private String oaBillJounum;//订单编号
	private String oaGysId;//供应商id
	
	private Date xdDate; //下单时间
	private Date fkDate; //付款时间
	private Date fhDate; //发货时间
	private Date shDate; //收货时间
	//收货人信息
	private String receivename;//收货人姓名
	private String receivetel;//收货人电话
	private String receivecode;//收货人邮编
	private String receiveaddress;//收货人完整地址
	
	//发货人信息
	private String sendname;//发货人姓名
	private String sendtel;//发货人电话
	private String sendcode;//发货人邮编
	private String sendaddress;//发货人完整地址
	
	private String yjid;//分佣金方案id
	private String extendedTime; //收货延长时间
	private String waybillno;//运单号 
	private String exCode;//快递公司标识 顺丰sf
	//发货操作人员
	private String sendId;//操作发货人的id
	private String sName;//操作发货人的姓名

	// Constructors

	/** default constructor */
	public DtOrderBillAdd() {
	}

	/** full constructor */
	public DtOrderBillAdd(String oaId, String oaComId, String oaSccId,
			String oaAddShId, String oaAddFhId, String oaBillId,
			String oaBillJounum, String oaGysId) {
		this.oaId = oaId;
		this.oaComId = oaComId;
		this.oaSccId = oaSccId;

		this.oaBillId = oaBillId;
		this.oaBillJounum = oaBillJounum;
		this.oaGysId = oaGysId;
	}

	// Property accessors

	public String getOaKey() {
		return this.oaKey;
	}

	public void setOaKey(String oaKey) {
		this.oaKey = oaKey;
	}

	public String getOaId() {
		return this.oaId;
	}

	public void setOaId(String oaId) {
		this.oaId = oaId;
	}

	public String getOaComId() {
		return this.oaComId;
	}

	public void setOaComId(String oaComId) {
		this.oaComId = oaComId;
	}

	public String getOaSccId() {
		return this.oaSccId;
	}

	public void setOaSccId(String oaSccId) {
		this.oaSccId = oaSccId;
	}

	

	public String getReceivename() {
		return receivename;
	}

	public void setReceivename(String receivename) {
		this.receivename = receivename;
	}

	public String getReceivetel() {
		return receivetel;
	}

	public void setReceivetel(String receivetel) {
		this.receivetel = receivetel;
	}

	public String getReceivecode() {
		return receivecode;
	}

	public void setReceivecode(String receivecode) {
		this.receivecode = receivecode;
	}

	public String getReceiveaddress() {
		return receiveaddress;
	}

	public void setReceiveaddress(String receiveaddress) {
		this.receiveaddress = receiveaddress;
	}

	public String getSendname() {
		return sendname;
	}

	public void setSendname(String sendname) {
		this.sendname = sendname;
	}

	public String getSendtel() {
		return sendtel;
	}

	public void setSendtel(String sendtel) {
		this.sendtel = sendtel;
	}

	public String getSendcode() {
		return sendcode;
	}

	public void setSendcode(String sendcode) {
		this.sendcode = sendcode;
	}

	public String getSendaddress() {
		return sendaddress;
	}

	public void setSendaddress(String sendaddress) {
		this.sendaddress = sendaddress;
	}

	public String getOaBillId() {
		return this.oaBillId;
	}

	public void setOaBillId(String oaBillId) {
		this.oaBillId = oaBillId;
	}

	public String getOaBillJounum() {
		return this.oaBillJounum;
	}

	public void setOaBillJounum(String oaBillJounum) {
		this.oaBillJounum = oaBillJounum;
	}

	public String getOaGysId() {
		return this.oaGysId;
	}

	public void setOaGysId(String oaGysId) {
		this.oaGysId = oaGysId;
	}

	public String getYjid() {
		return yjid;
	}

	public void setYjid(String yjid) {
		this.yjid = yjid;
	}

	public String getExtendedTime() {
		return extendedTime;
	}

	public void setExtendedTime(String extendedTime) {
		this.extendedTime = extendedTime;
	}


	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}

	


	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public Date getXdDate() {
		return xdDate;
	}

	public void setXdDate(Date xdDate) {
		this.xdDate = xdDate;
	}

	public Date getFkDate() {
		return fkDate;
	}

	public void setFkDate(Date fkDate) {
		this.fkDate = fkDate;
	}

	public Date getFhDate() {
		return fhDate;
	}

	public void setFhDate(Date fhDate) {
		this.fhDate = fhDate;
	}

	public Date getShDate() {
		return shDate;
	}

	public void setShDate(Date shDate) {
		this.shDate = shDate;
	}

	public String getWaybillno() {
		return waybillno;
	}

	public void setWaybillno(String waybillno) {
		this.waybillno = waybillno;
	}

	public String getExCode() {
		return exCode;
	}

	public void setExCode(String exCode) {
		this.exCode = exCode;
	}

}