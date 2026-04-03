package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Map;
/**
 * 扣分单
 * @author Administrator
 *
 */
public class GamJeom implements BaseBean ,ExcelBean{
	private String gamJeomID;
	private String gamJeomKey;
	private String companyID;
	private String organizationID;
	private String deptID;
	private String voucherNum;//凭证号
	private String accessory;//附件
	private String gamJeomNum;//扣分编号
	private String jobName;//职务
	private String principal;// 扣分责任人
	private String postName;//岗位
	private String deductPoint;//扣分
	private String execute;//执罚人
	private Date gamJeomDate;//扣分日期
	private String deptPrincipal;//部门负责人
	private String gamJeomMoney;//扣分金额
	private String myriad;//万
	private String thousand;//千
	private String hundred;//百
	private String ten;//十
	private String unit;//元
	private String horn;//角
	private String point;//分
	private String gamJeomReason;//扣分原因
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
		String[] titles = { "序号","公司名称","部门名称", "凭证号","扣分编号","职务","扣分责任人" , "岗位", "扣分", "执罚人","扣分日期","/部门负责人",
				"扣分金额","扣分原因",
				"综合意见","公司经理","总部经理","主管","总部主管","审核","财务部审核","会计","总会计","出纳","总出纳"};
		return titles;
	}

	public String[] properties() {
		String[] properties = { oMap.get(companyID),oMap.get(deptID),voucherNum,gamJeomNum,jobName,principal,postName,deductPoint,execute,String.format("%1$tF", gamJeomDate),deptPrincipal,
				gamJeomMoney,gamJeomReason,
				idea,manager,president,deptCharge,headCharge,verify,financeVerify,accounting,allAccounting,cashier, allCashier};
		return properties;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}

	public String getGamJeomID() {
		return gamJeomID;
	}

	public void setGamJeomID(String gamJeomID) {
		this.gamJeomID = gamJeomID;
	}

	public String getGamJeomKey() {
		return gamJeomKey;
	}

	public void setGamJeomKey(String gamJeomKey) {
		this.gamJeomKey = gamJeomKey;
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

	public String getGamJeomNum() {
		return gamJeomNum;
	}

	public void setGamJeomNum(String gamJeomNum) {
		this.gamJeomNum = gamJeomNum;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getDeductPoint() {
		return deductPoint;
	}

	public void setDeductPoint(String deductPoint) {
		this.deductPoint = deductPoint;
	}

	public String getExecute() {
		return execute;
	}

	public void setExecute(String execute) {
		this.execute = execute;
	}

	public Date getGamJeomDate() {
		return gamJeomDate;
	}

	public void setGamJeomDate(Date gamJeomDate) {
		this.gamJeomDate = gamJeomDate;
	}

	public String getDeptPrincipal() {
		return deptPrincipal;
	}

	public void setDeptPrincipal(String deptPrincipal) {
		this.deptPrincipal = deptPrincipal;
	}

	public String getMyriad() {
		return myriad;
	}

	public void setMyriad(String myriad) {
		this.myriad = myriad;
	}

	public String getThousand() {
		return thousand;
	}

	public void setThousand(String thousand) {
		this.thousand = thousand;
	}

	public String getHundred() {
		return hundred;
	}

	public void setHundred(String hundred) {
		this.hundred = hundred;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getHorn() {
		return horn;
	}

	public void setHorn(String horn) {
		this.horn = horn;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getGamJeomReason() {
		return gamJeomReason;
	}

	public void setGamJeomReason(String gamJeomReason) {
		this.gamJeomReason = gamJeomReason;
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

	public String getGamJeomMoney() {
		return gamJeomMoney;
	}

	public void setGamJeomMoney(String gamJeomMoney) {
		this.gamJeomMoney = gamJeomMoney;
	}

}
