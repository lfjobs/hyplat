package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Map;
/**
 * 加班申请单
 * @author Administrator
 *
 */
public class OverTime implements BaseBean ,ExcelBean{
	private String overID;
	private String overKey;
	private String companyID;
	private String organizationID;
	private String deptID;
	private String voucherNum;//凭证号
	private String accessory;//附件
	private String overNum;//加班编号
	private String overSort;//加班类别
	private String principal;//加班申请责任人
	private String principalNum;//加班申请编号
	private Date  confirmDate;//加班申请时间
	private Date startDate;//起时间
	private Date endDate;//止时间
	private String content;//加班内容
	private String idea;//综合意见
	private String manager;//公司经理
	private String president;//总部经理
	private String deptCharge;//部门主管
	private String headCharge;//总部主管
	private String verify;//审核
	private String financeVerify;//财务部审核
	private String accounting;//会计
	private String allAccounting;//总会计
	private String cashier;//出纳
	private String allCashier;//总出纳
	public static String[] columnHeadings() {
		String[] titles = { "序号","公司名称","部门名称", "凭证号","加班申请责任人", "加班编号","加班类别", "加班申请编号", "加班申请时间", "起时间","止时间","加班内容",
				"综合意见","公司经理","总部经理","主管","总部主管","审核","财务部审核","会计","总会计","出纳","总出纳"};
		return titles;
	}

	public String[] properties() {
		String[] properties = { oMap.get(companyID),oMap.get(deptID),voucherNum,principal,overNum,overSort,principalNum,String.format("%1$tF", confirmDate),String.format("%1$tF", startDate),String.format("%1$tF",endDate),
				content,idea,manager,president,deptCharge,headCharge,verify,financeVerify,accounting,allAccounting,cashier, allCashier};
		return properties;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}

	public String getOverID() {
		return overID;
	}

	public void setOverID(String overID) {
		this.overID = overID;
	}

	public String getOverKey() {
		return overKey;
	}

	public void setOverKey(String overKey) {
		this.overKey = overKey;
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

	public String getDeptID() {
		return deptID;
	}

	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}

	public String getVoucherNum() {
		return voucherNum;
	}

	public void setVoucherNum(String voucherNum) {
		this.voucherNum = voucherNum;
	}

	public String getAccessory() {
		return accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	public String getOverNum() {
		return overNum;
	}

	public void setOverNum(String overNum) {
		this.overNum = overNum;
	}

	public String getOverSort() {
		return overSort;
	}

	public void setOverSort(String overSort) {
		this.overSort = overSort;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getPrincipalNum() {
		return principalNum;
	}

	public void setPrincipalNum(String principalNum) {
		this.principalNum = principalNum;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getPresident() {
		return president;
	}

	public void setPresident(String president) {
		this.president = president;
	}

	public String getDeptCharge() {
		return deptCharge;
	}

	public void setDeptCharge(String deptCharge) {
		this.deptCharge = deptCharge;
	}

	public String getHeadCharge() {
		return headCharge;
	}

	public void setHeadCharge(String headCharge) {
		this.headCharge = headCharge;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getFinanceVerify() {
		return financeVerify;
	}

	public void setFinanceVerify(String financeVerify) {
		this.financeVerify = financeVerify;
	}

	public String getAccounting() {
		return accounting;
	}

	public void setAccounting(String accounting) {
		this.accounting = accounting;
	}

	public String getAllAccounting() {
		return allAccounting;
	}

	public void setAllAccounting(String allAccounting) {
		this.allAccounting = allAccounting;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public String getAllCashier() {
		return allCashier;
	}

	public void setAllCashier(String allCashier) {
		this.allCashier = allCashier;
	}
	
}
