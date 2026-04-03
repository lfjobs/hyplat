package hy.ea.bo.production;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class StaffTrack implements BaseBean, ExcelBean {

	private String stkey; // 主键
	private String stId;
	private String cashierBillsID;//单据ID
	private Date enrollDate;//报名时间即下单付款时间
	private String ppID;//主产品ID
	private String ppName;//主产品名称
	private String subID;//子产品ID
	private String subName;//子产品Name
	private String staffName;
	private String staffID;
	private String sex;
	private String tel;
	private String identiCard;
	private String headimage;
	private Date startDate;//有效期开始
	private Date endDate;//有效期结束时间
	private String responID;//负责人ID e.g.教练
	private String responName;//负责人Name e.g.教练
	private String remark;
	private String status;
	private String companyID;//驾校公司ID
	private String companyName;//公司name
	private String fiveClear;//人事2办公室3财务4生产5营销
	private String pronum;//项目总数
	private String completerate;//完成率

	

	public static String[] columnHeadings() {
		String[] titles = { "序号", "公司", "报名时间", "客户", "性别","电话","身份证","责任人",
				"开始时间", "截止时间","主项目","子项目","项目总数","完成略","备注"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = { companyName,String.format("%1$tF",enrollDate),staffName,sex,tel,identiCard,responName, 
				String.format("%1$tF",startDate),String.format("%1$tF",endDate),ppName,subName,pronum,completerate,remark};
		return properties;
	}
	public String getStkey() {
		return stkey;
	}
	public void setStkey(String stkey) {
		this.stkey = stkey;
	}
	public String getStId() {
		return stId;
	}
	public void setStId(String stId) {
		this.stId = stId;
	}
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public Date getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}
	public String getPpID() {
		return ppID;
	}
	public void setPpID(String ppID) {
		this.ppID = ppID;
	}
	public String getPpName() {
		return ppName;
	}
	public void setPpName(String ppName) {
		this.ppName = ppName;
	}
	public String getSubID() {
		return subID;
	}
	public void setSubID(String subID) {
		this.subID = subID;
	}
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getIdentiCard() {
		return identiCard;
	}
	public void setIdentiCard(String identiCard) {
		this.identiCard = identiCard;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getResponID() {
		return responID;
	}
	public void setResponID(String responID) {
		this.responID = responID;
	}
	public String getResponName() {
		return responName;
	}
	public void setResponName(String responName) {
		this.responName = responName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getFiveClear() {
		return fiveClear;
	}
	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
	public String getPronum() {
		return pronum;
	}
	public void setPronum(String pronum) {
		this.pronum = pronum;
	}
	public String getCompleterate() {
		return completerate;
	}
	public void setCompleterate(String completerate) {
		this.completerate = completerate;
	}
	public String getHeadimage() {
		return headimage;
	}
	public void setHeadimage(String headimage) {
		this.headimage = headimage;
	}
    
}