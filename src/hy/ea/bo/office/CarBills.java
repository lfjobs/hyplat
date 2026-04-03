package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Map;
/**
 * 奖励单
 * @author Administrator
 *
 */
public class CarBills implements BaseBean ,ExcelBean,java.io.Serializable{
	private String carBillsID;
	private String carBillsKey;
	private String companyID;
	private String organizationID;
	private String deptID;
	private String voucherNum;//凭证号
	private String accessory;//附件
	private String carBillsNum;//派车编号
	private String applianceCrew;//随车人员
	private String principal;// 派车责任人
	private Date carDate;//派车日期
    private Date startDate;//日期
    private Date endDate;//返回时间
    private String carInterval;//用车区间
    private String carNum;//车号
    private String carDriver;//司机
    private String startKilometer;//出车公里表数
    private String endKilometer;//返回公里表数
    private String remark;//备注
	private String carReason;//出车原因
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
		String[] titles = { "序号","公司名称","部门名称", "凭证号","派车编号","随车人员","派车责任人" , "派车日期", "日期", "返回时间","用车区间","车号",
				"司机","出车公里表数","返回公里表数","备注","出车原因",
				"综合意见","公司经理","总部经理","主管","总部主管","审核","财务部审核","会计","总会计","出纳","总出纳"};
		return titles;
	}

	public String[] properties() {
		String[] properties = { oMap.get(companyID),oMap.get(deptID),voucherNum,carBillsNum,applianceCrew,principal,String.format("%1$tF", carDate),String.format("%1$tF", startDate),String.format("%1$tF", endDate),carInterval,carNum,carDriver,
				startKilometer,endKilometer,remark,carReason,
				idea,manager,president,deptCharge,headCharge,verify,financeVerify,accounting,allAccounting,cashier, allCashier};
		return properties;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}

	public String getCarBillsID() {
		return carBillsID;
	}

	public void setCarBillsID(String carBillsID) {
		this.carBillsID = carBillsID;
	}

	public String getCarBillsKey() {
		return carBillsKey;
	}

	public void setCarBillsKey(String carBillsKey) {
		this.carBillsKey = carBillsKey;
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

	public String getCarBillsNum() {
		return carBillsNum;
	}

	public void setCarBillsNum(String carBillsNum) {
		this.carBillsNum = carBillsNum;
	}

	public String getApplianceCrew() {
		return applianceCrew;
	}

	public void setApplianceCrew(String applianceCrew) {
		this.applianceCrew = applianceCrew;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public Date getCarDate() {
		return carDate;
	}

	public void setCarDate(Date carDate) {
		this.carDate = carDate;
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

	public String getCarInterval() {
		return carInterval;
	}

	public void setCarInterval(String carInterval) {
		this.carInterval = carInterval;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getCarDriver() {
		return carDriver;
	}

	public void setCarDriver(String carDriver) {
		this.carDriver = carDriver;
	}

	public String getStartKilometer() {
		return startKilometer;
	}

	public void setStartKilometer(String startKilometer) {
		this.startKilometer = startKilometer;
	}

	public String getEndKilometer() {
		return endKilometer;
	}

	public void setEndKilometer(String endKilometer) {
		this.endKilometer = endKilometer;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getCarReason() {
		return carReason;
	}

	public void setCarReason(String carReason) {
		this.carReason = carReason;
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
