package hy.ea.bo.production;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;

public class DCheck implements BaseBean, ExcelBean,Serializable {
	private static final long serialVersionUID = 1L;
	private String dcheckkey; // 主键
	private String dcheckId;
	private String id;
	private String industryClassification; // 行业分类
	private String productClassification; // 产品分类
	private String itemNumber; //产品编号
	private String goodBar; // 产品条码
	private String goodName; // 产品名称
	private String goodStandard; // 产品规格
	private String price; // 成本单价
	private String btnumber; // 考核数量
	private String yieldnumber;//合格数量
	private String noyieldnumber;//不合格数量
	private String dcheckTime; // 考核时间
	private String auditor; // 审核人
	private String auditorId; // 审核人id
	private String companyId; // 审核人公司id
	private String companyName; // 审核人公司
	private String organizationId; // 审核人部门id
	private String organizationName; // 审核人部门
	private String auditoroption; // 审核人意见
	private String status; // 考核状态

	public static String[] columnHeadings() {
		String[] titles = { "序号", "行业分类", "项目产品编号", "产品条码", "产品名称",
				"产品规格", "单价", "考核数量","合格数量","不合格数量","审核人", "审核部门", "考核时间", "考核状态" };
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = { industryClassification, 
				itemNumber, goodBar, goodName, goodStandard, price, btnumber,
				yieldnumber,noyieldnumber, auditor, organizationName,dcheckTime, "00".equals(status)?"待考核":("01".equals(status)?"通过":"未通过") };
		return properties;
	}

	public String getIndustryClassification() {
		return industryClassification;
	}

	public void setIndustryClassification(String industryClassification) {
		this.industryClassification = industryClassification;
	}

	public String getProductClassification() {
		return productClassification;
	}

	public void setProductClassification(String productClassification) {
		this.productClassification = productClassification;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getGoodBar() {
		return goodBar;
	}

	public void setGoodBar(String goodBar) {
		this.goodBar = goodBar;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getGoodStandard() {
		return goodStandard;
	}

	public void setGoodStandard(String goodStandard) {
		this.goodStandard = goodStandard;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBtnumber() {
		return btnumber;
	}

	public void setBtnumber(String btnumber) {
		this.btnumber = btnumber;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getAuditoroption() {
		return auditoroption;
	}

	public void setAuditoroption(String auditoroption) {
		this.auditoroption = auditoroption;
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

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getDcheckkey() {
		return dcheckkey;
	}

	public void setDcheckkey(String dcheckkey) {
		this.dcheckkey = dcheckkey;
	}

	public String getDcheckId() {
		return dcheckId;
	}

	public void setDcheckId(String dcheckId) {
		this.dcheckId = dcheckId;
	}

	public String getDcheckTime() {
		return dcheckTime;
	}

	public void setDcheckTime(String dcheckTime) {
		this.dcheckTime = dcheckTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getYieldnumber() {
		return yieldnumber;
	}

	public void setYieldnumber(String yieldnumber) {
		this.yieldnumber = yieldnumber;
	}

	public String getNoyieldnumber() {
		return noyieldnumber;
	}

	public void setNoyieldnumber(String noyieldnumber) {
		this.noyieldnumber = noyieldnumber;
	}
	
    
}