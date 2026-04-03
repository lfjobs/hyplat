package com.mysl.bo.administrative;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 个人待办表
 * @author lou
 *
 */
public class DtMydo implements java.io.Serializable,BaseBean,ExcelBean {
	private String dokey;  //业务主键
	private String doid;   //业务主键
	private String id;   //单据id
	private String serialnumber;  //单据编号
	private String receiptType;	 //单据类型
	private String lookOverurl;     //查看路径
	private String printurl;     //打印页面路径
	private String teablename;  //单据所属表名
	private Date paymentTime;		//指派时间
	private String paymentid;	//指派人id
	private String paymentname;	//指派人姓名
	private String paymentorg;   //指派人部门id
	private String paymentorgname;   //指派人部门名称
	private String paymentcompanyid;  //指派人公司id
	private String paymentcompanyname;//指派人公司名称
	private Date audittime;		//完成时间
	private String complyid;  //执行人id
	private String complyname;  //执行人姓名
	private String complyorgid;//执行人部门ID
	private String complyorgname;//执行人部门名称
	private String complycompanyid;  //执行人公司id
	private String complycompanyname;  //执行人公司名称
	private String complystatus;   //办理状态   '01'待办  '02'已办 '03'已派发
	
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "单据编号","指派人","待办事项","交办时间","完成时间","状态"};
		return titles;
	}
	
	public String[] properties() {
		String[] properties = {  serialnumber, paymentname,"交办完成"+receiptType, String.format("%1$tF", paymentTime),
				String.format("%1$tF", audittime), ("01".endsWith(complystatus)?"待办":
				"02".endsWith(complystatus)?"已办":"已派发")};
		return properties;
	}
	
	
	public DtMydo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DtMydo(String dokey, String doid, String id, String receiptType,
			String lookOverurl, String printurl, String teablename,
			Date paymentTime, String paymentid, String paymentname,
			String paymentorg, String paymentorgname, String paymentcompanyid,
			String paymentcompanyname, Date audittime, String complyid,
			String complyname, String complyorgid, String complyorgname,
			String complycompanyid, String complycompanyname,
			String complystatus) {
		super();
		this.dokey = dokey;
		this.doid = doid;
		this.id = id;
		this.receiptType = receiptType;
		this.lookOverurl = lookOverurl;
		this.printurl = printurl;
		this.teablename = teablename;
		this.paymentTime = paymentTime;
		this.paymentid = paymentid;
		this.paymentname = paymentname;
		this.paymentorg = paymentorg;
		this.paymentorgname = paymentorgname;
		this.paymentcompanyid = paymentcompanyid;
		this.paymentcompanyname = paymentcompanyname;
		this.audittime = audittime;
		this.complyid = complyid;
		this.complyname = complyname;
		this.complyorgid = complyorgid;
		this.complyorgname = complyorgname;
		this.complycompanyid = complycompanyid;
		this.complycompanyname = complycompanyname;
		this.complystatus = complystatus;
	}

	public String getDokey() {
		return dokey;
	}
	public void setDokey(String dokey) {
		this.dokey = dokey;
	}
	public String getDoid() {
		return doid;
	}
	public void setDoid(String doid) {
		this.doid = doid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReceiptType() {
		return receiptType;
	}
	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}
	public String getLookOverurl() {
		return lookOverurl;
	}
	public void setLookOverurl(String lookOverurl) {
		this.lookOverurl = lookOverurl;
	}
	public String getTeablename() {
		return teablename;
	}
	public void setTeablename(String teablename) {
		this.teablename = teablename;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getPaymentid() {
		return paymentid;
	}

	public void setPaymentid(String paymentid) {
		this.paymentid = paymentid;
	}

	public String getPaymentname() {
		return paymentname;
	}

	public void setPaymentname(String paymentname) {
		this.paymentname = paymentname;
	}

	public String getPaymentorg() {
		return paymentorg;
	}

	public void setPaymentorg(String paymentorg) {
		this.paymentorg = paymentorg;
	}

	public String getPaymentorgname() {
		return paymentorgname;
	}

	public void setPaymentorgname(String paymentorgname) {
		this.paymentorgname = paymentorgname;
	}

	public String getPaymentcompanyid() {
		return paymentcompanyid;
	}

	public void setPaymentcompanyid(String paymentcompanyid) {
		this.paymentcompanyid = paymentcompanyid;
	}

	public String getPaymentcompanyname() {
		return paymentcompanyname;
	}

	public void setPaymentcompanyname(String paymentcompanyname) {
		this.paymentcompanyname = paymentcompanyname;
	}

	public Date getAudittime() {
		return audittime;
	}

	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}
	public String getComplyid() {
		return complyid;
	}
	public void setComplyid(String complyid) {
		this.complyid = complyid;
	}
	public String getComplyname() {
		return complyname;
	}
	public void setComplyname(String complyname) {
		this.complyname = complyname;
	}
	public String getComplyorgid() {
		return complyorgid;
	}
	public void setComplyorgid(String complyorgid) {
		this.complyorgid = complyorgid;
	}
	public String getComplyorgname() {
		return complyorgname;
	}
	public void setComplyorgname(String complyorgname) {
		this.complyorgname = complyorgname;
	}
	public String getComplycompanyid() {
		return complycompanyid;
	}
	public void setComplycompanyid(String complycompanyid) {
		this.complycompanyid = complycompanyid;
	}
	public String getComplycompanyname() {
		return complycompanyname;
	}
	public void setComplycompanyname(String complycompanyname) {
		this.complycompanyname = complycompanyname;
	}
	public String getComplystatus() {
		return complystatus;
	}
	public void setComplystatus(String complystatus) {
		this.complystatus = complystatus;
	}
	public String getPrinturl() {
		return printurl;
	}
	public void setPrinturl(String printurl) {
		this.printurl = printurl;
	}

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
}
