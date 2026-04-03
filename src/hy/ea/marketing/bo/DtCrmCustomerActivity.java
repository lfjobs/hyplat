package hy.ea.marketing.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * DtCrmCustomerActivity entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class DtCrmCustomerActivity implements java.io.Serializable,BaseBean {

	// Fields

	private String activitykey;//数据库主键
	private DtCrmCustomer dtCrmCustomer;
	private String activityid;//业务主键
	private Date activitydate;//活动日期
	private String activitytitle;//主题
	private String status;//客户阶段
	private String ismanual;//说明书
	private String isbrochure;//宣传册
	private String isdemo;//演示版本
	private String isother;//其他1
	private String content1;//其他内容1
	private String issms;//短信
	private String issms2;//电话
	private String isweixin;//微信互动
	private String isvisit;//登门拜访
	private String ismeeting;//会议
	private String isactivity;//活动
	private String content2;//内容2
	private String ddesc;//详情描述
	private Set dtCrmCustomerWaitors = new HashSet(0);//协同人
	
	public static String[] columnHeadings(){
		String[] titles = {"活动日期","主题","客户阶段"/*,"协同人","携带产品资料","跟踪方式"*/};
		return titles;
	}
	public String[] properties() {
		String[] properties = {"activitydate",activitytitle,status};
		return properties;
	}

	// Constructors

	/** default constructor */
	public DtCrmCustomerActivity() {
	}

	/** minimal constructor */
	public DtCrmCustomerActivity(String activityid, Date activitydate) {
		this.activityid = activityid;
		this.activitydate = activitydate;
	}

	/** full constructor */
	public DtCrmCustomerActivity(DtCrmCustomer dtCrmCustomer,
			String activityid, Date activitydate, String activitytitle,
			String status, String ismanual, String isbrochure, String isdemo,
			String isother, String content1, String issms, String issms2,
			String isweixin, String isvisit, String ismeeting,
			String isactivity, String content2, String ddesc,
			Set dtCrmCustomerWaitors) {
		this.dtCrmCustomer = dtCrmCustomer;
		this.activityid = activityid;
		this.activitydate = activitydate;
		this.activitytitle = activitytitle;
		this.status = status;
		this.ismanual = ismanual;
		this.isbrochure = isbrochure;
		this.isdemo = isdemo;
		this.isother = isother;
		this.content1 = content1;
		this.issms = issms;
		this.issms2 = issms2;
		this.isweixin = isweixin;
		this.isvisit = isvisit;
		this.ismeeting = ismeeting;
		this.isactivity = isactivity;
		this.content2 = content2;
		this.ddesc = ddesc;
		this.dtCrmCustomerWaitors = dtCrmCustomerWaitors;
	}

	// Property accessors

	public String getActivitykey() {
		return this.activitykey;
	}

	public void setActivitykey(String activitykey) {
		this.activitykey = activitykey;
	}

	public DtCrmCustomer getDtCrmCustomer() {
		return this.dtCrmCustomer;
	}

	public void setDtCrmCustomer(DtCrmCustomer dtCrmCustomer) {
		this.dtCrmCustomer = dtCrmCustomer;
	}

	public String getActivityid() {
		return this.activityid;
	}

	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}

	public Date getActivitydate() {
		return this.activitydate;
	}

	public void setActivitydate(Date activitydate) {
		this.activitydate = activitydate;
	}

	public String getActivitytitle() {
		return this.activitytitle;
	}

	public void setActivitytitle(String activitytitle) {
		this.activitytitle = activitytitle;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsmanual() {
		return this.ismanual;
	}

	public void setIsmanual(String ismanual) {
		this.ismanual = ismanual;
	}

	public String getIsbrochure() {
		return this.isbrochure;
	}

	public void setIsbrochure(String isbrochure) {
		this.isbrochure = isbrochure;
	}

	public String getIsdemo() {
		return this.isdemo;
	}

	public void setIsdemo(String isdemo) {
		this.isdemo = isdemo;
	}

	public String getIsother() {
		return this.isother;
	}

	public void setIsother(String isother) {
		this.isother = isother;
	}

	public String getContent1() {
		return this.content1;
	}

	public void setContent1(String content1) {
		this.content1 = content1;
	}

	public String getIssms() {
		return this.issms;
	}

	public void setIssms(String issms) {
		this.issms = issms;
	}

	public String getIssms2() {
		return this.issms2;
	}

	public void setIssms2(String issms2) {
		this.issms2 = issms2;
	}

	public String getIsweixin() {
		return this.isweixin;
	}

	public void setIsweixin(String isweixin) {
		this.isweixin = isweixin;
	}

	public String getIsvisit() {
		return this.isvisit;
	}

	public void setIsvisit(String isvisit) {
		this.isvisit = isvisit;
	}

	public String getIsmeeting() {
		return this.ismeeting;
	}

	public void setIsmeeting(String ismeeting) {
		this.ismeeting = ismeeting;
	}

	public String getIsactivity() {
		return this.isactivity;
	}

	public void setIsactivity(String isactivity) {
		this.isactivity = isactivity;
	}

	public String getContent2() {
		return this.content2;
	}

	public void setContent2(String content2) {
		this.content2 = content2;
	}

	public String getDdesc() {
		return this.ddesc;
	}

	public void setDdesc(String ddesc) {
		this.ddesc = ddesc;
	}

	public Set getDtCrmCustomerWaitors() {
		return this.dtCrmCustomerWaitors;
	}

	public void setDtCrmCustomerWaitors(Set dtCrmCustomerWaitors) {
		this.dtCrmCustomerWaitors = dtCrmCustomerWaitors;
	}

}