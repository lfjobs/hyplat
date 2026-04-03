package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class COSAfforest implements BaseBean, ExcelBean,java.io.Serializable {
	private String afforestKey;
	private String afforestID;
	private String companyID;
	private String organizationID;
	private String classes;// 绿化类别
	private String area;// 绿化面积
	private String amount;// 绿化数量
	private String place;// 绿化地点
	private String principal;// 绿化负责人
	private Date afforestDate;// 绿化日期

	public static String[] columnHeadings() {
		String[] titles = { "序号", "绿化类别", "绿化面积", "绿化数量", "绿化地点", "绿化负责人",
				"绿化日期" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = { classes, area, amount, place, principal,
				String.format("%1$tF", afforestDate) };
		return properties;
	}

	public String getAfforestKey() {
		return afforestKey;
	}

	public void setAfforestKey(String afforestKey) {
		this.afforestKey = afforestKey;
	}

	public String getAfforestID() {
		return afforestID;
	}

	public void setAfforestID(String afforestID) {
		this.afforestID = afforestID;
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

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public Date getAfforestDate() {
		return afforestDate;
	}

	public void setAfforestDate(Date afforestDate) {
		this.afforestDate = afforestDate;
	}

}
