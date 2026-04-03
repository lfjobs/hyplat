package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

public class COSElectricity implements BaseBean, ExcelBean,java.io.Serializable {
	private String electricityKey;
	private String electricityID;
	private String companyID;
	private String organizationID;
	private String username;// 用户名
	private String amount;// 购电量
	private String usedamount;// 实际用量
	private String examiner;// 核对人

	public static String[] columnHeadings() {
		String[] titles = { "序号", "用户名", "购电量", "实际用量", "核对人"
				 };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = { username, amount+"", usedamount+"", examiner};
		return properties;
	}

	public String getElectricityKey() {
		return electricityKey;
	}

	public void setElectricityKey(String electricityKey) {
		this.electricityKey = electricityKey;
	}

	public String getElectricityID() {
		return electricityID;
	}

	public void setElectricityID(String electricityID) {
		this.electricityID = electricityID;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getUsedamount() {
		return usedamount;
	}

	public void setUsedamount(String usedamount) {
		this.usedamount = usedamount;
	}

	public String getExaminer() {
		return examiner;
	}

	public void setExaminer(String examiner) {
		this.examiner = examiner;
	}


}
