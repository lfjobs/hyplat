package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class COSWater implements BaseBean, ExcelBean ,java.io.Serializable{
	private String waterKey;
	private String waterID;
	private String companyID;
	private String organizationID;
	private String unit;// 单位
	private Date logDate;// 日期
	private String amount;// 计划用水量
	private String usedAmount;// 实际用水量

	public static String[] columnHeadings() {
		String[] titles = { "序号", "单位", "日期", "计划用水量", "实际用水量"};
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = { unit,
				String.format("%1$tF", logDate) ,amount, usedAmount };
		return properties;
	}

	public String getWaterKey() {
		return waterKey;
	}

	public void setWaterKey(String waterKey) {
		this.waterKey = waterKey;
	}

	public String getWaterID() {
		return waterID;
	}

	public void setWaterID(String waterID) {
		this.waterID = waterID;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(String usedAmount) {
		this.usedAmount = usedAmount;
	}





}
