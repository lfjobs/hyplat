package hy.ea.bo.invoicing;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 项目管理项目表
 * @author mz
 *
 */
public class ProjectManage implements BaseBean,ExcelBean,java.io.Serializable{
	
	private String proKey;			        //主键
	private String proID;         			//业务主键
	private String projectName;     		//项目名称
	private String projectCode;//项目编号
	private String xmtype;                 //项目分类编号对应后台code表codeSn
	private String xmtypename;             //项目分类名称对应后台code表codeValue
	private String content;          		//项目内容
	private Date startDate;                 //项目开始时间
	private Date endDate;                   //项目结束时间
	private Date createDate;                //项目创建时间；
	private Date updateDate;//更新时间
	private String companyName;				//当前公司name
	private String companyID;              //公司ID
	private String organizationID;     		//项目所在部门，一级部门
	private String organizationName;       //部门名称
	private String createID;              //项目创建人
	private String createName;           //项目创建人姓名
	private String createCode;           //项目创建人编号
	
	private String staffID;                 //限定部门内的责任人ID  外键
	private String staffName;               //责任人名称
	private String staffCode;				// 责任人编号

	private String ydsocre;//应得分
	private String jfscore;//奖分
	private String kfscore;//扣分
	private String sdscore;//实得分	
	private String attachUrl;			//附件(路径)
	
	
	//规划
	private String titles;
	private String docIds;
	private String pproID;//父项目ID
	private String pprojectName;//父项目名称
	private String status;//审核状态 01 待审 02 通过审核 03 驳回
	private Date submittime;//提交审核时间
	private Date audittime;//审核时间
	private String remark;
	private int levels = 1;//项目层级 1,2,3,4
	private String levelNumber;//0001，001,01,01,01
	private String pcode;//0001，001,01,01,01


	
	
	@Override
	public String[] properties() {
		
		String[] properties = {
				projectName,
				projectCode,
				companyName,
				organizationName,
				staffName,
				String.format("%1$tF",startDate),
				String.format("%1$tF",endDate),
				createName,
				String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS",endDate)

				};
		return properties;
	}
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "项目名称","项目编号", "公司名称","部门","责任人","项目开始日期","项目结束日期","创建人","更新日期"};
		return titles;
	}

	public String getProKey() {
		return proKey;
	}

	public void setProKey(String proKey) {
		this.proKey = proKey;
	}

	public String getProID() {
		return proID;
	}

	public void setProID(String proID) {
		this.proID = proID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getXmtype() {
		return xmtype;
	}

	public void setXmtype(String xmtype) {
		this.xmtype = xmtype;
	}

	public String getXmtypename() {
		return xmtypename;
	}

	public void setXmtypename(String xmtypename) {
		this.xmtypename = xmtypename;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getYdsocre() {
		return ydsocre;
	}

	public void setYdsocre(String ydsocre) {
		this.ydsocre = ydsocre;
	}

	public String getJfscore() {
		return jfscore;
	}

	public void setJfscore(String jfscore) {
		this.jfscore = jfscore;
	}

	public String getKfscore() {
		return kfscore;
	}

	public void setKfscore(String kfscore) {
		this.kfscore = kfscore;
	}

	public String getSdscore() {
		return sdscore;
	}

	public void setSdscore(String sdscore) {
		this.sdscore = sdscore;
	}

	public String getAttachUrl() {
		return attachUrl;
	}

	public void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}

	public String getCreateID() {
		return createID;
	}

	public void setCreateID(String createID) {
		this.createID = createID;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateCode() {
		return createCode;
	}

	public void setCreateCode(String createCode) {
		this.createCode = createCode;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getDocIds() {
		return docIds;
	}

	public void setDocIds(String docIds) {
		this.docIds = docIds;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getPproID() {
		return pproID;
	}

	public void setPproID(String pproID) {
		this.pproID = pproID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getSubmittime() {
		return submittime;
	}

	public void setSubmittime(Date submittime) {
		this.submittime = submittime;
	}

	public Date getAudittime() {
		return audittime;
	}

	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

   

    

	public int getLevels() {
		return levels;
	}

	public void setLevels(int levels) {
		this.levels = levels;
	}

	public String getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(String levelNumber) {
		this.levelNumber = levelNumber;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getPprojectName() {
		return pprojectName;
	}

	public void setPprojectName(String pprojectName) {
		this.pprojectName = pprojectName;
	}

	
   
	
	
}
