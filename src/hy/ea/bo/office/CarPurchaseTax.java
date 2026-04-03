package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 购置税发票
 * 
 * @author Administrator
 * 
 */
public class CarPurchaseTax implements BaseBean, ExcelBean ,java.io.Serializable{
	private String purchaseTaxKey;// 数据库主键
	private String purchaseTaxID;// 业务主键
	private String companyID;
	private String organizationID;
	private String carID;// 外键
	private String invoiceCode; // 发票代号
	private Date invoiceDate;// 开票日期
	private String carName;// 车主
	private String brandModel;// 厂牌型号
	private String originType;// 国产/进口
	private String taxprice;// 车辆计税价格
	private String tax;// 缴税金额
	private String penalty;// 滞纳金
	private String dutyPaidNum;// 完税证明号码
	private String collectors;// 代收单位
	private String payee;// 收款人
	private String remarks; // 备注
	
	private String engineNum;//发动机号
	private String carpeople;//车辆责任人
	private String carType;//车辆类型
	private String carNum;//车辆号码
	public static String[] columnHeadings() {
		String[] titles = { "序号",  "车牌号","车型","发动机号","负责人","发票代号", "开票日期", "车主", "厂牌型号", "国产/进口",
				"车辆计税价格", "缴税金额", "滞纳金", "完税证明号码", "代收单位", "收款人", "备注" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = { invoiceCode,
				String.format("%1$tF", invoiceDate), carName, brandModel,
				originType, taxprice, tax, penalty, dutyPaidNum, collectors,
				payee, remarks };
		return properties;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPurchaseTaxKey() {
		return purchaseTaxKey;
	}

	public void setPurchaseTaxKey(String purchaseTaxKey) {
		this.purchaseTaxKey = purchaseTaxKey;
	}

	public String getPurchaseTaxID() {
		return purchaseTaxID;
	}

	public void setPurchaseTaxID(String purchaseTaxID) {
		this.purchaseTaxID = purchaseTaxID;
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

	public String getCarID() {
		return carID;
	}

	public void setCarID(String carID) {
		this.carID = carID;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public String getBrandModel() {
		return brandModel;
	}

	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}

	public String getOriginType() {
		return originType;
	}

	public void setOriginType(String originType) {
		this.originType = originType;
	}

	public String getTaxprice() {
		return taxprice;
	}

	public void setTaxprice(String taxprice) {
		this.taxprice = taxprice;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getPenalty() {
		return penalty;
	}

	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	public String getDutyPaidNum() {
		return dutyPaidNum;
	}

	public void setDutyPaidNum(String dutyPaidNum) {
		this.dutyPaidNum = dutyPaidNum;
	}

	public String getCollectors() {
		return collectors;
	}

	public void setCollectors(String collectors) {
		this.collectors = collectors;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getEngineNum() {
		return engineNum;
	}

	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}

	public String getCarpeople() {
		return carpeople;
	}

	public void setCarpeople(String carpeople) {
		this.carpeople = carpeople;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	
}
