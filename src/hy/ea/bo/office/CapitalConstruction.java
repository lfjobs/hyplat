package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Map;
/**
 * 基建管理
 * @author Administrator
 *
 */
public class CapitalConstruction implements BaseBean ,ExcelBean,java.io.Serializable{
	private String capitalID;
	private String capitalKey;
	private String companyID;
	private String organizationID;
	private String unitName;//单位名称
	private String unitprincipal;//单位负责人
	private String item;//项目
	private String budgetCost;//预算费用
    private String buildUnit;//承建单位
    private String buildprincipal;//承建负责人
    private Date startDate;//开工时间
    private Date endDate;//竣工时间
    private String capitalType;//基建类型  ‘00’基建  ‘01’维修
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "单位名称", "单位负责人", "项目", "预算费用", "承建单位",
				"承建负责人","开工时间","竣工时间","基建类型"};
		return titles;
	}

	public String[] properties() {
		String[] properties = { unitName,unitprincipal,item,budgetCost,buildUnit,buildprincipal,String.format("%1$tF", startDate),
				 String.format("%1$tF", endDate), oMap.get(capitalType)};
		return properties;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public String getCapitalID() {
		return capitalID;
	}

	public void setCapitalID(String capitalID) {
		this.capitalID = capitalID;
	}

	public String getCapitalKey() {
		return capitalKey;
	}

	public void setCapitalKey(String capitalKey) {
		this.capitalKey = capitalKey;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitprincipal() {
		return unitprincipal;
	}

	public void setUnitprincipal(String unitprincipal) {
		this.unitprincipal = unitprincipal;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getBudgetCost() {
		return budgetCost;
	}

	public void setBudgetCost(String budgetCost) {
		this.budgetCost = budgetCost;
	}

	public String getBuildUnit() {
		return buildUnit;
	}

	public void setBuildUnit(String buildUnit) {
		this.buildUnit = buildUnit;
	}

	public String getBuildprincipal() {
		return buildprincipal;
	}

	public void setBuildprincipal(String buildprincipal) {
		this.buildprincipal = buildprincipal;
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

	public String getCapitalType() {
		return capitalType;
	}

	public void setCapitalType(String capitalType) {
		this.capitalType = capitalType;
	}

}
