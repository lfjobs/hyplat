package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 购车发票
 * 
 * @author Administrator
 * 
 */
public class CarInvoice implements BaseBean, ExcelBean,java.io.Serializable {
	private String invoiceKey;// 数据库主键
	private String invoiceID;// 业务主键
	private String companyID;
	private String organizationID;
	private String carID;// 外键
	private String invoiceCode;// 发票代号
	private Date invoiceDate;// 开票日期
	private String purchaseUnits;// 购货单位
	private String purchaseCode;// 身份证号码/组织机构代码
	private String brandModel;// 厂牌型号
	private String origin;// 产地
	private String salesName;// 单价销货单位名称
	private String salesAddress;// 地址
	private String remarks; // 备注
	
	private String carNum;//车牌号
	private String carpeople;//责任人
	private String engineNum;//发动机号码
	private String carType;//车辆类型
	
	public static String[] columnHeadings() {
		String[] titles = { "序号","车牌号","车型","发动机号","责任人", "发票代号", "开票日期", "购货单位", "身份证号码/组织机构代码",
				"厂牌型号", "产地", "单价销货单位名称", "地址", "备注" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = { invoiceCode, invoiceDate.toString(),
				purchaseUnits, purchaseCode, brandModel, origin, salesName,
				salesAddress, remarks };
		return properties;
	}

	public String getInvoiceKey() {
		return invoiceKey;
	}

	public void setInvoiceKey(String invoiceKey) {
		this.invoiceKey = invoiceKey;
	}

	public String getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
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

	public String getPurchaseUnits() {
		return purchaseUnits;
	}

	public void setPurchaseUnits(String purchaseUnits) {
		this.purchaseUnits = purchaseUnits;
	}

	public String getPurchaseCode() {
		return purchaseCode;
	}

	public void setPurchaseCode(String purchaseCode) {
		this.purchaseCode = purchaseCode;
	}

	public String getBrandModel() {
		return brandModel;
	}

	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getSalesAddress() {
		return salesAddress;
	}

	public void setSalesAddress(String salesAddress) {
		this.salesAddress = salesAddress;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getCarpeople() {
		return carpeople;
	}

	public void setCarpeople(String carpeople) {
		this.carpeople = carpeople;
	}

	public String getEngineNum() {
		return engineNum;
	}

	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}
}
