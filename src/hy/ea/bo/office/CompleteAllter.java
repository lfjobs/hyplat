package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * 整改通知单
 * @author Administrator
 *
 */
public class CompleteAllter implements BaseBean,ExcelBean,java.io.Serializable {
	private String completeAllterID;
	private String completeAllterKey;
	private String companyID;
	private String organizationID;
	private String deptID;
	private String voucherNumber;                                 //任证号
	private String company;                                       //公司
	private String organization;                                  //部门
	private String responsible;                                   //责任人
	private Date   completeAllterDate;                            // 整改通知时间
	private String completeAllterTongZhiCode;                     //整改通知编号
	private String completeAllterCode;                            //整改编号
	private String completeAllterGoods;                           //整改物品名称
	private String completeAllterMan;                             //整改批准人
	private String completeAllterTongZhiCause;                    //整改通知原因
	private String completeAllterBeCauseOne;                      //整改存在问题  一
	private String remarkOne;                                     //备注一
	private String completeAllterBeCauseTwo;                      //整改存在问题 二
	private String remarkTwo;                                     //备注 二
	private String completeAllterBeCauseThree;                    //整改存在问题  三
	private String remarkThree;                                   //备注  三
	private String accessories;                                   //附件
	
	private String manager;                                       //公司经理
	private String president;                                     //总部经理
	private String deptCharge;                                    //部门主管
	private String headCharge;                                    //总部主管
	private String verify;                                        //审核
	private String financeVerify;                                 //财务部审核
	private String accounting;                                    //会计
	private String allAccounting;                                 //总会计
	private String cashier;                                       //出纳
	private String allCashier;                                    //总出纳

	
	private File    photo;
	private String photoContentType;
	
	
	
	public static String[] columnHeadings() {
		String[] titles = { "序号","任证号","公司","部门","责任人","整改通知时间","整改通知编号","整改编号","整改通知原因","整改存在问题一","备注一",
				"整改存在问题二","备注二","整改存在问题三","备注三","公司经理","总部经理",
				"部门主管","总部主管","审核","财务部审核","会计","总会计","出纳","总出纳"};
		return titles;
	}
	
	@Override
	public String[] properties() {
		String[] properties = {voucherNumber,company,oMap.get(deptID),responsible ,String.format("%1$tF", completeAllterDate,0,10),
				completeAllterTongZhiCode,completeAllterCode,completeAllterTongZhiCause,completeAllterBeCauseOne,remarkOne,completeAllterBeCauseTwo,
				remarkTwo,completeAllterBeCauseThree,remarkThree,manager,president,deptCharge,headCharge,verify,
				financeVerify,accounting,allAccounting,cashier,allCashier};
		return properties;
	}
	
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}

	public String getCompleteAllterID() {
		return completeAllterID;
	}

	public void setCompleteAllterID(String completeAllterID) {
		this.completeAllterID = completeAllterID;
	}

	public String getCompleteAllterKey() {
		return completeAllterKey;
	}

	public void setCompleteAllterKey(String completeAllterKey) {
		this.completeAllterKey = completeAllterKey;
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

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public Date getCompleteAllterDate() {
		return completeAllterDate;
	}

	public void setCompleteAllterDate(Date completeAllterDate) {
		this.completeAllterDate = completeAllterDate;
	}

	public String getCompleteAllterTongZhiCode() {
		return completeAllterTongZhiCode;
	}

	public void setCompleteAllterTongZhiCode(String completeAllterTongZhiCode) {
		this.completeAllterTongZhiCode = completeAllterTongZhiCode;
	}

	public String getCompleteAllterCode() {
		return completeAllterCode;
	}

	public void setCompleteAllterCode(String completeAllterCode) {
		this.completeAllterCode = completeAllterCode;
	}

	public String getCompleteAllterTongZhiCause() {
		return completeAllterTongZhiCause;
	}

	public void setCompleteAllterTongZhiCause(String completeAllterTongZhiCause) {
		this.completeAllterTongZhiCause = completeAllterTongZhiCause;
	}

	public String getCompleteAllterBeCauseOne() {
		return completeAllterBeCauseOne;
	}

	public void setCompleteAllterBeCauseOne(String completeAllterBeCauseOne) {
		this.completeAllterBeCauseOne = completeAllterBeCauseOne;
	}

	public String getRemarkOne() {
		return remarkOne;
	}

	public void setRemarkOne(String remarkOne) {
		this.remarkOne = remarkOne;
	}

	public String getCompleteAllterBeCauseTwo() {
		return completeAllterBeCauseTwo;
	}

	public void setCompleteAllterBeCauseTwo(String completeAllterBeCauseTwo) {
		this.completeAllterBeCauseTwo = completeAllterBeCauseTwo;
	}

	public String getRemarkTwo() {
		return remarkTwo;
	}

	public void setRemarkTwo(String remarkTwo) {
		this.remarkTwo = remarkTwo;
	}

	public String getCompleteAllterBeCauseThree() {
		return completeAllterBeCauseThree;
	}

	public void setCompleteAllterBeCauseThree(String completeAllterBeCauseThree) {
		this.completeAllterBeCauseThree = completeAllterBeCauseThree;
	}

	public String getRemarkThree() {
		return remarkThree;
	}

	public void setRemarkThree(String remarkThree) {
		this.remarkThree = remarkThree;
	}

	public String getAccessories() {
		return accessories;
	}

	public void setAccessories(String accessories) {
		this.accessories = accessories;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
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

	public String getDeptID() {
		return deptID;
	}

	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}

	public String getCompleteAllterGoods() {
		return completeAllterGoods;
	}

	public void setCompleteAllterGoods(String completeAllterGoods) {
		this.completeAllterGoods = completeAllterGoods;
	}

	public String getCompleteAllterMan() {
		return completeAllterMan;
	}

	public void setCompleteAllterMan(String completeAllterMan) {
		this.completeAllterMan = completeAllterMan;
	}
			
	
}
