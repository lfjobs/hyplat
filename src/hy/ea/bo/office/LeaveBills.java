package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Map;
/**
 * 请假单
 * @author Administrator
 *
 */
public class LeaveBills implements BaseBean ,ExcelBean{
	private String leaveID;
	private String leaveKey;
	private String companyID;
	private String organizationID;
	private String deptID;
	private String voucherNum;//凭证号
	private String accessory;//附件
	private String leaveNum;//请假编号
	private String postName;//职位
	private String principal;//请假责任人	
	private String  leaveDate;//请假天数
	private String leaveHour;//请假小时
	private Date startDate;//起时间
	private Date endDate;//止时间
	private String leaveType;//请假类别
	private String leaveReason;//请假原因
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
	private String leaveState;//状态  00申请  01 批准   02销假   03结束
	public static String[] columnHeadings() {
		String[] titles = { "序号","公司名称","部门名称", "凭证号","请假编号", "职位","请假责任人","请假天数","请假小时", "起时间", "止时间", "请假类别","请假原因","综合意见",
				"公司经理","总部经理","主管","总部主管","审核","财务部审核","会计","总会计","出纳","总出纳","假条状态"};
		return titles;
	}

	public String[] properties() {
		String[] properties = { oMap.get(companyID),oMap.get(deptID),voucherNum,leaveNum,postName,principal,leaveDate,leaveHour,String.format("%1$tF", startDate),String.format("%1$tF",endDate),
				leaveType,leaveReason,idea,manager,president,deptCharge,headCharge,verify,financeVerify,accounting,allAccounting,cashier, allCashier,oMap.get(leaveState)};
		return properties;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}

	public String getLeaveID() {
		return leaveID;
	}

	public void setLeaveID(String leaveID) {
		this.leaveID = leaveID;
	}

	public String getLeaveKey() {
		return leaveKey;
	}

	public void setLeaveKey(String leaveKey) {
		this.leaveKey = leaveKey;
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

	public String getLeaveNum() {
		return leaveNum;
	}

	public void setLeaveNum(String leaveNum) {
		this.leaveNum = leaveNum;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getLeaveHour() {
		return leaveHour;
	}

	public void setLeaveHour(String leaveHour) {
		this.leaveHour = leaveHour;
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

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
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

	public String getLeaveState() {
		return leaveState;
	}

	public void setLeaveState(String leaveState) {
		this.leaveState = leaveState;
	}

}
