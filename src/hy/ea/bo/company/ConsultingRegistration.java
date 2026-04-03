package hy.ea.bo.company;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 咨询登记
 *
 * 
 */

public class ConsultingRegistration implements BaseBean,ExcelBean,Serializable {

	private String crKey;
	private String crId;
	private String companyId;//被咨询的公司id
	private String companyName;//被咨询的公司商家

	private String staffId;//个人项目没有公司
	private String consultantName;//咨询人姓名
	private String consultantPhone;//资讯人电话

	private String consultantContent;//咨询内容 报名 生日 婚庆 其他
	private Date consultingDate;//咨询时间
	private String state;//状态00:正常;01:删除
	private String ccompanyId;//单位网站
	private String ppid;// 文章的ID
	private String goodsname;//文章标题
	private String source;//00文章 01 公司网站
	private String  returnVisit;//00 没有回访 01 回访过了
	private String visitContent;//回访重点内容纪录
	private String visitStaffID;//回访客服ID
	private String visitStaffName;//回访客服姓名
	private String islisten;//是否接听 00 未接听，01 接听
	private Date visitDate;//回访时间
	private String isIntentCustomer;//00 不敢兴趣了，01 意向客户 02 成交客户
	private String  clientType;//客户类型
	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "姓名", "电话", "咨询时间", "咨询内容", "是否回访"};
		return titles;
	}

	public String[] properties() {
		String[] properties = { consultantName, consultantPhone,
				String.format("%1$tF %1tT", consultingDate), consultantContent,
				returnVisit=="00"?"否":"是" };
		return properties;
	}
	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}


	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getReturnVisit() {
		return returnVisit;
	}

	public void setReturnVisit(String returnVisit) {
		this.returnVisit = returnVisit;
	}

	public String getVisitContent() {
		return visitContent;
	}

	public void setVisitContent(String visitContent) {
		this.visitContent = visitContent;
	}

	public String getIslisten() {
		return islisten;
	}

	public void setIslisten(String islisten) {
		this.islisten = islisten;
	}

	public String getIsIntentCustomer() {
		return isIntentCustomer;
	}

	public void setIsIntentCustomer(String isIntentCustomer) {
		this.isIntentCustomer = isIntentCustomer;
	}

	public String  getCrKey() {
		 return crKey;
	}

	public void setCrKey(String crKey) {
		this.crKey = crKey;
	}

	public String getCrId() {
		return crId;
	}

	public void setCrId(String crId) {
		this.crId = crId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getConsultantName() {
		return consultantName;
	}

	public void setConsultantName(String consultantName) {
		this.consultantName = consultantName;
	}

	public String getConsultantPhone() {
		return consultantPhone;
	}

	public void setConsultantPhone(String consultantPhone) {
		this.consultantPhone = consultantPhone;
	}

	public Date getConsultingDate() {
		return consultingDate;
	}

	public void setConsultingDate(Date consultingDate) {
		this.consultingDate = consultingDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getCcompanyId() {
		return ccompanyId;
	}

	public void setCcompanyId(String ccompanyId) {
		this.ccompanyId = ccompanyId;
	}

	public String getVisitStaffID() {
		return visitStaffID;
	}

	public void setVisitStaffID(String visitStaffID) {
		this.visitStaffID = visitStaffID;
	}

	public String getVisitStaffName() {
		return visitStaffName;
	}

	public void setVisitStaffName(String visitStaffName) {
		this.visitStaffName = visitStaffName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getConsultantContent() {
		return consultantContent;
	}

	public void setConsultantContent(String consultantContent) {
		this.consultantContent = consultantContent;
	}
}
