package hy.ea.bo.production;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class InviteBids implements BaseBean, ExcelBean ,java.io.Serializable{

	private String ibkey; // 主键
	private String ibId;
	private String cashierBillsID;//单据ID
	private String ppID;//主产品ID
	private String journalNum;//凭证号
	private String billsType;//单据类型
	private Date startDate;//有效期开始
	private Date endDate;//有效期结束时间
	private String remark;//招标说明
	private String status; // 发布状态 00未发布  01已发布 02已选标 03:提交资金申请

	private Date publishDate;//发布时间
	private Date cancelDate;//取消发布时间
	private Date selectbidDate;//选标时间
	private Date applyPubDate;//申请发布时间即审核通过时间
	private String staffID;//责任人ID
	private String staffName;//责任人姓名
	private String inputID;//制单人ID
	private String inputName;//制单人姓名
	private String publishID;//发布人ID
	private String publishName;//发布人姓名
	private String publishType;//发布人类型 公司 还是个人
	private String selectID;//选标人ID
	private String selectName;//选标人姓名

	private String companyId; // 审核人公司id
	private String companyName; // 审核人公司
	private String organizationId; // 审核人部门id
	private String organizationName; // 审核人部门
	private String fiveClear;//1人事2办公室3财务4生产5营销
	private String bidcount;//投标次数
	private String type;	// 生产类别   00：订单  01：计划

	public static String[] columnHeadings() {
		String[] titles = { "序号", "凭证号", "单据类型", "公司", "部门","责任人",
				"制单人", "发布招标日期","有效期","选标时间","选标人","发布状态"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = { journalNum, 
				billsType,companyName,organizationName,staffName,inputName,String.format("%1$tF",publishDate),String.format("%1$tF",startDate)+"-"+String.format("%1$tF",endDate),String.format("%1$tF",selectbidDate),selectName, 
				"00".equals(status)?"未发布":("01".equals(status)?"已发布":"已选标") };
		return properties;
	}
   
	public String getIbkey() {
		return ibkey;
	}
	public void setIbkey(String ibkey) {
		this.ibkey = ibkey;
	}
	public String getIbId() {
		return ibId;
	}
	public void setIbId(String ibId) {
		this.ibId = ibId;
	}
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getJournalNum() {
		return journalNum;
	}
	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}
	public String getBillsType() {
		return billsType;
	}
	public void setBillsType(String billsType) {
		this.billsType = billsType;
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
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public Date getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
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
	public String getInputID() {
		return inputID;
	}
	public void setInputID(String inputID) {
		this.inputID = inputID;
	}
	public String getInputName() {
		return inputName;
	}
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public Date getApplyPubDate() {
		return applyPubDate;
	}
	public void setApplyPubDate(Date applyPubDate) {
		this.applyPubDate = applyPubDate;
	}
	public String getPublishID() {
		return publishID;
	}
	public void setPublishID(String publishID) {
		this.publishID = publishID;
	}
	public String getPublishName() {
		return publishName;
	}
	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}
	public String getFiveClear() {
		return fiveClear;
	}
	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
	public Date getSelectbidDate() {
		return selectbidDate;
	}
	public void setSelectbidDate(Date selectbidDate) {
		this.selectbidDate = selectbidDate;
	}
	public String getSelectID() {
		return selectID;
	}
	public void setSelectID(String selectID) {
		this.selectID = selectID;
	}
	public String getSelectName() {
		return selectName;
	}
	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}
	public String getPpID() {
		return ppID;
	}
	public void setPpID(String ppID) {
		this.ppID = ppID;
	}
	public String getBidcount() {
		return bidcount;
	}
	public void setBidcount(String bidcount) {
		this.bidcount = bidcount;
	}
	public String getPublishType() {
		return publishType;
	}
	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
	
	
    
}