package com.mysl.bo;

import hy.ea.bo.ExcelBean;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;

import java.text.ParseException;
import java.util.Date;

/**
 * DtMytask entity. @author MyEclipse Persistence Tools
 */

public class DtMytask implements java.io.Serializable,BaseBean, ExcelBean {

	// Fields

	private String taskkey;
	private String taskid;
	private String staffid;
	private String staffname;//执行人名字
	//mz后加执行人部门公司信息；
	private String orgid;
	private String orgname;
	private String companyid;
	private String companyname;
	private String distributeid;
	private String attachpath;
	private String filetype;
	private String templateid;
	private String phasestatus;
	private String auditstatus;  //lf审核状态 '00' 未审核  '01'审核中  '02'已审核 '03'驳回
	private String updatestatus;  //申请修改审核状态 '00' 未审核  '01'审核中  '02'已审核 '03'驳回  lf后加
	private String taskcode;
	private String seqnum;//序号；
	private String taskname;
	private String tasktype;
	private String emergency;
	private Date startdate;
	private Date planfinishdate;
	private Date factfinishdate;
	private String proid;//项目ID mz后加的
	private String proName;//项目名称 zc后加的
	private Date createtime;//任务创建时间mz后加的
	private String distributeName;//派发人名称  zc后加的
	private String applyerupdate;  //申请修改状态    '01'正常    '02'申请修改  lf后加
	private String warningStatues;  //  00 任务正常执行  01  距离任务计划完成时间超过 50 %   02    过期  （非数据库字段）  03  任务完成
	private String toCustomer;// 00 未提交客户  默认  01  已提交客户
	private String content;//正文内容
	
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "任务编号", "任务标题", "执行人", "部门", "任务类型", "缓急",
				"开始时间", "计划完成时间", "实际完成时间", "是否下达","审核状态","派发人"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {  taskcode, taskname,staffname, orgname,
				tasktype, emergency, String.format("%1$tF", startdate),  String.format("%1$tF", planfinishdate),  String.format("%1$tF", factfinishdate),
				"00".endsWith(phasestatus)?"未下达":"已下达",
				("01".endsWith(phasestatus)?"生产设计":
				"02".endsWith(phasestatus)?"设计完成":
				"03".endsWith(phasestatus)?"提交成果":
				"04".endsWith(phasestatus)?"项目档案":"任务未下达")
				+"-"+(auditstatus!=null?("01".endsWith(auditstatus)?"审核中":
				"02".endsWith(auditstatus)?"已审核":
				"03".endsWith(auditstatus)?"驳回":"未审核"):"未审核")
				, distributeName};
		return properties;
	}
	// Constructors
	
	/** default constructor */
	public DtMytask() {
	}

	/** full constructor */

	public DtMytask(String taskkey, String taskid, String staffid,
			String staffname, String orgid, String orgname, String companyid,
			String companyname, String distributeid, String attachpath,
			String filetype, String templateid, String phasestatus,
			String auditstatus, String updatestatus, String taskcode,
			String seqnum, String taskname, String tasktype, String emergency,
			Date startdate, Date planfinishdate, Date factfinishdate,
			String proid, String proName, Date createtime,
			String distributeName, String applyerupdate, String warningStatues,
			String toCustomer, String content) {
		super();
		this.taskkey = taskkey;
		this.taskid = taskid;
		this.staffid = staffid;
		this.staffname = staffname;
		this.orgid = orgid;
		this.orgname = orgname;
		this.companyid = companyid;
		this.companyname = companyname;
		this.distributeid = distributeid;
		this.attachpath = attachpath;
		this.filetype = filetype;
		this.templateid = templateid;
		this.phasestatus = phasestatus;
		this.auditstatus = auditstatus;
		this.updatestatus = updatestatus;
		this.taskcode = taskcode;
		this.seqnum = seqnum;
		this.taskname = taskname;
		this.tasktype = tasktype;
		this.emergency = emergency;
		this.startdate = startdate;
		this.planfinishdate = planfinishdate;
		this.factfinishdate = factfinishdate;
		this.proid = proid;
		this.proName = proName;
		this.createtime = createtime;
		this.distributeName = distributeName;
		this.applyerupdate = applyerupdate;
		this.warningStatues = warningStatues;
		this.toCustomer = toCustomer;
		this.content = content;
	}
	


	// Property accessors

	public String getTaskkey() {
		return this.taskkey;
	}
	public void setTaskkey(String taskkey) {
		this.taskkey = taskkey;
	}

	public String getTaskid() {
		return this.taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getDistributeid() {
		return this.distributeid;
	}

	public void setDistributeid(String distributeid) {
		this.distributeid = distributeid;
	}

	public String getAttachpath() {
		return this.attachpath;
	}

	public void setAttachpath(String attachpath) {
		this.attachpath = attachpath;
	}

	public String getFiletype() {
		return this.filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getTemplateid() {
		return this.templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public String getPhasestatus() {
		return this.phasestatus;
	}

	public void setPhasestatus(String phasestatus) {
		this.phasestatus = phasestatus;
	}

	public String getAuditstatus() {
		return this.auditstatus;
	}

	public void setAuditstatus(String auditstatus) {
		this.auditstatus = auditstatus;
	}

	public String getTaskcode() {
		return this.taskcode;
	}

	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}

	public String getTaskname() {
		return this.taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getTasktype() {
		return this.tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public String getEmergency() {
		return this.emergency;
	}

	public void setEmergency(String emergency) {
		this.emergency = emergency;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getPlanfinishdate() {
		return this.planfinishdate;
	}

	public void setPlanfinishdate(Date planfinishdate) {
		this.planfinishdate = planfinishdate;
	}

	public Date getFactfinishdate() {
		return this.factfinishdate;
	}

	public void setFactfinishdate(Date factfinishdate) {
		this.factfinishdate = factfinishdate;
	}

	public String getProid() {
		return proid;
	}

	public void setProid(String proid) {
		this.proid = proid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getDistributeName() {
		return distributeName;
	}

	public void setDistributeName(String distributeName) {
		this.distributeName = distributeName;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}
	
	public String getToCustomer() {
		return toCustomer;
	}
	public void setToCustomer(String toCustomer) {
		this.toCustomer = toCustomer;
	}

	public String getApplyerupdate() {
		return applyerupdate;
	}
	public void setApplyerupdate(String applyerupdate) {
		this.applyerupdate = applyerupdate;
	}

	public String getUpdatestatus() {
		return updatestatus;
	}
	public void setUpdatestatus(String updatestatus) {
		this.updatestatus = updatestatus;
	}
	public String getWarningStatues() {
			warningStatues="00";
			try {
				int usedDays=DateUtil.compareDate(DateUtil.toStrDateFromUtilDateByFormat(startdate, "yyyy-MM-dd"), DateUtil.toStrDateFromUtilDateByFormat(new Date(), "yyyy-MM-dd"), 0);
				int unusedDays=DateUtil.compareDate(DateUtil.toStrDateFromUtilDateByFormat(new Date(), "yyyy-MM-dd"), DateUtil.toStrDateFromUtilDateByFormat(planfinishdate , "yyyy-MM-dd"), 0);
				if(unusedDays<usedDays&&unusedDays!=-1){
					warningStatues="01";
				}
				if(unusedDays==-1){
					warningStatues="02";
				}
				if("02".equals(phasestatus)||"03".equals(phasestatus)||"04".equals(phasestatus)){
					warningStatues="03";
				}
			} catch (ParseException e) {
				System.out.println("时间转换异常");
			}
		return warningStatues;
		
	}
	public void setWarningStatues(String warningStatues) {
		this.warningStatues = warningStatues;
	}
	public String getSeqnum() {
		return seqnum;
	}
	public void setSeqnum(String seqnum) {
		this.seqnum = seqnum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
    
}