package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;
import java.util.Map;
/**
 * 任务通知单管理
 * @author Administrator
 *
 */
public class TaskNotice implements BaseBean,ExcelBean,java.io.Serializable{

	private String taskNoticeID;
	private String taskNoticeKey;
	private String companyID;
	private String organizationID;
	private String deptID;
	private String voucherNum;  //凭证号
	private String enPerson;	//责任人
	private String enTNumber;	//任务通知编号
	private String enType;		//通知类别（00一般通知，01紧急通知）
	private Date enDate;		//通知日期
	private String enContent;	//通知内容
	private String enIdea;		//综合意见
	private String enAuditor;	//审核人
	private String enLeader;	//部门主管
	private String enFilePath;	//附件
	
	private String manager;        //公司经理
	private String president;      //总部经理
	private String headCharge;     //总部主管
	
	private String financeVerify;  //财务部审核
	private String accounting;     //会计
	private String allAccounting;  //总会计
	private String cashier;        //出纳
	private String allCashier;     //总出纳
	
	private File enFile;
	private String enFileFileName;
	private String enFileContentType;
	
	public static Map<String,String> oMap;
	
	public static String[] columnHeadings() {
		String[] titles = { "序号","公司名称","部门名称", "凭证号","责任人","任务通知编号","通知类别","通知日期","通知内容","综合意见","审核人","部门主管"
				,"公司经理","总部经理","总部主管","财务部审核","会计","总会计","出纳","总出纳"
		};
		return titles;
	}
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub 
		String[] properties = {oMap.get(companyID),oMap.get(deptID),voucherNum,enPerson,enTNumber,oMap.get(enType),String.format("%1$tF", enDate),enContent,enIdea,enAuditor,enLeader
				,manager,president,headCharge,financeVerify,accounting,allAccounting,cashier,allCashier};
		return properties;
	}
	public String getTaskNoticeID() {
		return taskNoticeID;
	}
	public void setTaskNoticeID(String taskNoticeID) {
		this.taskNoticeID = taskNoticeID;
	}
	public String getTaskNoticeKey() {
		return taskNoticeKey;
	}
	public void setTaskNoticeKey(String taskNoticeKey) {
		this.taskNoticeKey = taskNoticeKey;
	}
	public String getCompanyID() {
		return companyID;
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
	public String getHeadCharge() {
		return headCharge;
	}
	public void setHeadCharge(String headCharge) {
		this.headCharge = headCharge;
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
	public String getEnPerson() {
		return enPerson;
	}
	public void setEnPerson(String enPerson) {
		this.enPerson = enPerson;
	}
	public String getEnTNumber() {
		return enTNumber;
	}
	public void setEnTNumber(String enTNumber) {
		this.enTNumber = enTNumber;
	}
	public String getEnType() {
		return enType;
	}
	public void setEnType(String enType) {
		this.enType = enType;
	}
	public Date getEnDate() {
		return enDate;
	}
	public void setEnDate(Date enDate) {
		this.enDate = enDate;
	}
	public String getEnContent() {
		return enContent;
	}
	public void setEnContent(String enContent) {
		this.enContent = enContent;
	}
	public String getEnIdea() {
		return enIdea;
	}
	public void setEnIdea(String enIdea) {
		this.enIdea = enIdea;
	}
	public String getEnAuditor() {
		return enAuditor;
	}
	public void setEnAuditor(String enAuditor) {
		this.enAuditor = enAuditor;
	}
	public String getEnLeader() {
		return enLeader;
	}
	public void setEnLeader(String enLeader) {
		this.enLeader = enLeader;
	}
	public String getEnFilePath() {
		return enFilePath;
	}
	public void setEnFilePath(String enFilePath) {
		this.enFilePath = enFilePath;
	}
	public File getEnFile() {
		return enFile;
	}
	public void setEnFile(File enFile) {
		this.enFile = enFile;
	}
	public String getEnFileContentType() {
		return enFileContentType;
	}
	public void setEnFileContentType(String enFileContentType) {
		this.enFileContentType = enFileContentType;
	}
	public static Map<String, String> getOMap() {
		return oMap;
	}
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public String getEnFileFileName() {
		return enFileFileName;
	}
	public void setEnFileFileName(String enFileFileName) {
		this.enFileFileName = enFileFileName;
	}
	
}
