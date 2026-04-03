package com.mysl.bo.administrative;

import java.util.Date;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * 单据审核表
 * @author lou
 *
 */
public class DtMycheck implements java.io.Serializable,BaseBean,ExcelBean{
	
	private String checkkey;  //业务主键
	private String checkid;   //业务主键
	private String id;   //单据id
	private String serialnumber;  //单据编号
	private String receiptType;	 //单据类型
	private String lookOverurl;     //查看页面路径
	private String printurl;     //打印页面路径
	private String listurl;		//列表页面路径
	private String teablename;  //单据所属表名
	private Date addtime;		//申请审核时间
	private String applyerid;	//申请人id
	private String applyername;	//申请人姓名
	private String applyorg;   //申请人部门id
	private String applyorgname;   //申请人部门名称
	private String applycompanyid;  //申请人公司id
	private String applycompanyname;//申请人公司名称
	private Date audittime;		//审核时间
	private String auditorid;  //审核人id
	private String auditorname;  //审核人姓名
	private String auditororgid;//审核人部门ID
	private String auditororgname;//审核人部门名称
	private String auditorcompanyid;  //审核人公司id
	private String auditorcompanyname;  //审核人公司名称
	private String comments;  //审核意见 
	private String auditorstatus;   //审核状态   '01'未审核  '02'已审核 '03'驳回 
	
	
	
	public String[] properties() {
		String[] properties = {  serialnumber, receiptType,applyername, String.format("%1$tF", addtime),
				auditorname, String.format("%1$tF", audittime),
				("01".endsWith(auditorstatus)?"未审核":
				 "02".endsWith(auditorstatus)?"已审核":"驳回")};
		return properties;
	}
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "单据编号", "单据类型", "申请人", "申请审核时间", "审核人", "审核时间","审核状态"};
		return titles;
	}
	
	public DtMycheck() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DtMycheck(String checkkey, String checkid, String id,
			String serialnumber, String receiptType, String lookOverurl,
			String printurl, String listurl, String teablename, Date addtime,
			String applyerid, String applyername, String applyorg,
			String applyorgname, String applycompanyid,
			String applycompanyname, Date audittime, String auditorid,
			String auditorname, String auditororgid, String auditororgname,
			String auditorcompanyid, String auditorcompanyname,
			String comments, String auditorstatus) {
		super();
		this.checkkey = checkkey;
		this.checkid = checkid;
		this.id = id;
		this.serialnumber = serialnumber;
		this.receiptType = receiptType;
		this.lookOverurl = lookOverurl;
		this.printurl = printurl;
		this.listurl = listurl;
		this.teablename = teablename;
		this.addtime = addtime;
		this.applyerid = applyerid;
		this.applyername = applyername;
		this.applyorg = applyorg;
		this.applyorgname = applyorgname;
		this.applycompanyid = applycompanyid;
		this.applycompanyname = applycompanyname;
		this.audittime = audittime;
		this.auditorid = auditorid;
		this.auditorname = auditorname;
		this.auditororgid = auditororgid;
		this.auditororgname = auditororgname;
		this.auditorcompanyid = auditorcompanyid;
		this.auditorcompanyname = auditorcompanyname;
		this.comments = comments;
		this.auditorstatus = auditorstatus;
	}

	public String getCheckkey() {
		return checkkey;
	}
	public void setCheckkey(String checkkey) {
		this.checkkey = checkkey;
	}
	public String getCheckid() {
		return checkid;
	}
	public void setCheckid(String checkid) {
		this.checkid = checkid;
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
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public String getApplyerid() {
		return applyerid;
	}
	public void setApplyerid(String applyerid) {
		this.applyerid = applyerid;
	}
	public String getApplyername() {
		return applyername;
	}
	public void setApplyername(String applyername) {
		this.applyername = applyername;
	}
	public String getApplyorg() {
		return applyorg;
	}
	public void setApplyorg(String applyorg) {
		this.applyorg = applyorg;
	}
	public String getApplyorgname() {
		return applyorgname;
	}
	public void setApplyorgname(String applyorgname) {
		this.applyorgname = applyorgname;
	}
	public String getApplycompanyid() {
		return applycompanyid;
	}
	public void setApplycompanyid(String applycompanyid) {
		this.applycompanyid = applycompanyid;
	}
	public String getApplycompanyname() {
		return applycompanyname;
	}
	public void setApplycompanyname(String applycompanyname) {
		this.applycompanyname = applycompanyname;
	}
	public Date getAudittime() {
		return audittime;
	}
	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}
	public String getAuditorid() {
		return auditorid;
	}
	public void setAuditorid(String auditorid) {
		this.auditorid = auditorid;
	}
	public String getAuditorname() {
		return auditorname;
	}
	public void setAuditorname(String auditorname) {
		this.auditorname = auditorname;
	}
	public String getAuditororgid() {
		return auditororgid;
	}
	public void setAuditororgid(String auditororgid) {
		this.auditororgid = auditororgid;
	}
	public String getAuditororgname() {
		return auditororgname;
	}
	public void setAuditororgname(String auditororgname) {
		this.auditororgname = auditororgname;
	}
	public String getAuditorcompanyid() {
		return auditorcompanyid;
	}
	public void setAuditorcompanyid(String auditorcompanyid) {
		this.auditorcompanyid = auditorcompanyid;
	}
	public String getAuditorcompanyname() {
		return auditorcompanyname;
	}
	public void setAuditorcompanyname(String auditorcompanyname) {
		this.auditorcompanyname = auditorcompanyname;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getAuditorstatus() {
		return auditorstatus;
	}
	public void setAuditorstatus(String auditorstatus) {
		this.auditorstatus = auditorstatus;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getPrinturl() {
		return printurl;
	}

	public void setPrinturl(String printurl) {
		this.printurl = printurl;
	}

	public String getListurl() {
		return listurl;
	}

	public void setListurl(String listurl) {
		this.listurl = listurl;
	}
	
}
