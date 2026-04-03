package hy.ea.bo.invoicing;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;

/**
 * 预算单据父表 CostSheetBill
 * @author lf
 *
 */
public class CostSheetBill implements BaseBean,ExcelBean,Cloneable,java.io.Serializable{
	
	private String csbKey;			        //主键
	private String csbID;         			//业务主键
	private String groupCompanySn;     		//集团公司的标识  外键
	private String companyID;          		//公司  外键
	private String companyName;				//当前公司name
	private String organizationID;     		//单据所在部门  外键
	private String departmentID;       		//子部门ID(限定部门单据) 外键
	private String departmentName;          //部门name 填单选择的部门
	private String PcsbID;					//父单据ID	
	
	private String companyBankNum;     		//公司银行账号

	
	//驳回:10 驳回待修改：11    未审核（00）(保存数据的时候生成),招标前审核中（01）,招标前审核通过(13),确定招标(03),招标后审核通过(16),
	//待修改车辆信息： 28
	private String billStatus;         		
	
	private String cstaffID;            	//往来个人ID  外键
	private String cstaffName;          	//往来个人名称
	private String cstaffRelationship;      //个人往来关系
	private String userAccountNum;			//往来个人银行帐号
	private String reference;				//往来个人电话
	private String staffIdentityCard;		// 身份证
	private String referenceCode;			// qq
	private String referenceOrganization;	// 邮箱
	private String staffAddress;			//家庭地址路径
	
	private String ccompanyID;         		//往来单位ID  外键
	private String ccompanyName;       		//往来单位名称	
	private String ccompanyRelationship;    //往来单位关系
	private String accountNum;				//往来公司银行账号
	private String companyAddr;         	//具体地址
	private String companyTel;          	//公司电话
	private String cresponsible;        	//负责人
	private String responsibleTel;      	//负责人电话
	private String industryType;        	//行业类别
	
	private String staffID;                 //限定部门内的责任人ID  外键
	private String staffName;               //责任人名称
	private String staffCode;				// 责任人编号
	
	private String billsType;                //单据类型
	private String journalNum;               //凭证号（项目编号）
	private Date billsDate;                  //制单日期
	
	private String billStaffID;              //制单人ID  外键
	private String billStaffName;            //制单人名称
	
	
	private String billComppanyID; //制单人公司ID
	private String billCompanyName;//制单人公司名称	
	private String billDeptID;//制单人部门ID
	private String billDeptName;//制单人部门名称
	
	
	// 传阅接收人相关信息
	private String receiverID;
	private String receiverName;
	private String isLeaf;//mz是否是最终项目 00：是 01：否
	
	//规划
	private String titles;
	private String docIds;
	
	//项目树
	private String xmtype;//项目类型
	private String xmtypename;//项目类型名称
	
	//新赠关于得分
			
	private String ydsocre;//应得分
	private String jfscore;//奖分
	private String kfscore;//扣分
	private String sdscore;//实得分
	
	
	
	// 传阅人相关信息；
	private String passerID;
	private String passerDeptID;
	private String passerCompanyID;
	private String passerName;
	private String passerDeptName;
	private String passerCompanyName;
	
	//招标前审核信息
	private String examineID;//招标前当前审核人姓名ID
	private String examineName;//招标前当前审核人姓名
	private String examineComID;
	private String examineComName;
	private String examineorgID;
	private String examineorgName;
	
	private String csreviewedID;			 //审核人ID
	private String csreviewedName;			 //审核人名称
	
	private String projectName;				//项目名称
	private Date startTime;					//起时间
	private Date endTime;					//止时间
	private String costdescription;			//项目内容
	private String budgetAmount;			//项目预算总金额
	private String budgetUpper;			   //项目预算总金额大写
	private String actualAmount;			//项目实际总价(预留)
	private String pJournalNum;             //父凭证号（项目编号）
	
	private String groupJournalNum;// 同一组的项目标志符
	
	private String accessoriesUrl;			//附件(路径)
	private File photo;						//附件文件
	private String PhotoFileName;			//附件名称
	
	

	//车辆设备
	private String carID;//车辆ID
	private String carNum;//车牌号
	private String engineNum;//发动机号
	private String engineType;//发动机型号
	private String carType;//车辆类型
	private String fuelType;//燃油类别
	private String vehicleBrand;//车辆品牌
	private String driveType;//驱动形式
	private String colorPaintNum;//外观及漆号
	private String carFrameNum;//车架号
	
	
	
	//------ 招生信息 ---------//
	private String assignsID;//分校id  公司子部门id
	private String assignsName;//分校名称 公司子部门名称
	private Date   signUpDate;//报名时间
	private String referrerName; //推荐人  当前公司在职人员   
	private String referrerID;//推荐ID
	private String referrerPhone; //推荐人电话
	private String referrerIdentityCard; //推荐人身份证
	private String referrerEmail;
	private String referrerAddress;
	
	
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "项目名称","项目编号", "公司名称", "单据类型", "部门","责任人","项目开始日期","项目结束日期","所属项目","制单人","制单日期","公司银行帐号","来往单位","来往个人","审核人","单据状态"};
		return titles;
	}
	
	
	public static String[] columnHeadingsmx() {
		String[] titles = { "序号", "公司","部门", "责任人", "子项目编号", "子项目名称","主项目编号","主项目名称","项目类别","物品编号","物品名称","型号/规格","单位","数量","单价","金额","预算收入","预算支出","预算余额","往来单位","往来个人"};
		return titles;
	}
	
	@Override
	protected  Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
	public Object cloneCostSheetBill() throws CloneNotSupportedException{
		
		return this.clone();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((PcsbID == null) ? 0 : PcsbID.hashCode());
		result = prime * result
				+ ((PhotoFileName == null) ? 0 : PhotoFileName.hashCode());
		result = prime * result
				+ ((accessoriesUrl == null) ? 0 : accessoriesUrl.hashCode());
		result = prime * result
				+ ((accountNum == null) ? 0 : accountNum.hashCode());
		result = prime * result
				+ ((actualAmount == null) ? 0 : actualAmount.hashCode());
		result = prime * result
				+ ((billCompanyName == null) ? 0 : billCompanyName.hashCode());
		result = prime * result
				+ ((billComppanyID == null) ? 0 : billComppanyID.hashCode());
		result = prime * result
				+ ((billDeptID == null) ? 0 : billDeptID.hashCode());
		result = prime * result
				+ ((billDeptName == null) ? 0 : billDeptName.hashCode());
		result = prime * result
				+ ((billStaffID == null) ? 0 : billStaffID.hashCode());
		result = prime * result
				+ ((billStaffName == null) ? 0 : billStaffName.hashCode());
		result = prime * result
				+ ((billStatus == null) ? 0 : billStatus.hashCode());
		result = prime * result
				+ ((billsDate == null) ? 0 : billsDate.hashCode());
		result = prime * result
				+ ((billsType == null) ? 0 : billsType.hashCode());
		result = prime * result
				+ ((budgetAmount == null) ? 0 : budgetAmount.hashCode());
		result = prime * result
				+ ((budgetUpper == null) ? 0 : budgetUpper.hashCode());
		result = prime * result
				+ ((carFrameNum == null) ? 0 : carFrameNum.hashCode());
		result = prime * result + ((carID == null) ? 0 : carID.hashCode());
		result = prime * result + ((carNum == null) ? 0 : carNum.hashCode());
		result = prime * result + ((carType == null) ? 0 : carType.hashCode());
		result = prime * result
				+ ((ccompanyID == null) ? 0 : ccompanyID.hashCode());
		result = prime * result
				+ ((ccompanyName == null) ? 0 : ccompanyName.hashCode());
		result = prime
				* result
				+ ((ccompanyRelationship == null) ? 0 : ccompanyRelationship
						.hashCode());
		result = prime * result
				+ ((colorPaintNum == null) ? 0 : colorPaintNum.hashCode());
		result = prime * result
				+ ((companyAddr == null) ? 0 : companyAddr.hashCode());
		result = prime * result
				+ ((companyBankNum == null) ? 0 : companyBankNum.hashCode());
		result = prime * result
				+ ((companyID == null) ? 0 : companyID.hashCode());
		result = prime * result
				+ ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result
				+ ((companyTel == null) ? 0 : companyTel.hashCode());
		result = prime * result
				+ ((costdescription == null) ? 0 : costdescription.hashCode());
		result = prime * result
				+ ((cresponsible == null) ? 0 : cresponsible.hashCode());
		result = prime * result + ((csbID == null) ? 0 : csbID.hashCode());
		result = prime * result + ((csbKey == null) ? 0 : csbKey.hashCode());
		result = prime * result
				+ ((csreviewedID == null) ? 0 : csreviewedID.hashCode());
		result = prime * result
				+ ((csreviewedName == null) ? 0 : csreviewedName.hashCode());
		result = prime * result
				+ ((cstaffID == null) ? 0 : cstaffID.hashCode());
		result = prime * result
				+ ((cstaffName == null) ? 0 : cstaffName.hashCode());
		result = prime
				* result
				+ ((cstaffRelationship == null) ? 0 : cstaffRelationship
						.hashCode());
		result = prime * result
				+ ((departmentID == null) ? 0 : departmentID.hashCode());
		result = prime * result
				+ ((departmentName == null) ? 0 : departmentName.hashCode());
		result = prime * result + ((docIds == null) ? 0 : docIds.hashCode());
		result = prime * result
				+ ((driveType == null) ? 0 : driveType.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result
				+ ((engineNum == null) ? 0 : engineNum.hashCode());
		result = prime * result
				+ ((engineType == null) ? 0 : engineType.hashCode());
		result = prime * result
				+ ((fuelType == null) ? 0 : fuelType.hashCode());
		result = prime * result
				+ ((groupCompanySn == null) ? 0 : groupCompanySn.hashCode());
		result = prime * result
				+ ((groupJournalNum == null) ? 0 : groupJournalNum.hashCode());
		result = prime * result
				+ ((industryType == null) ? 0 : industryType.hashCode());
		result = prime * result + ((isLeaf == null) ? 0 : isLeaf.hashCode());
		result = prime * result
				+ ((journalNum == null) ? 0 : journalNum.hashCode());
		result = prime * result
				+ ((organizationID == null) ? 0 : organizationID.hashCode());
		result = prime * result
				+ ((pJournalNum == null) ? 0 : pJournalNum.hashCode());
		result = prime * result
				+ ((passerCompanyID == null) ? 0 : passerCompanyID.hashCode());
		result = prime
				* result
				+ ((passerCompanyName == null) ? 0 : passerCompanyName
						.hashCode());
		result = prime * result
				+ ((passerDeptID == null) ? 0 : passerDeptID.hashCode());
		result = prime * result
				+ ((passerDeptName == null) ? 0 : passerDeptName.hashCode());
		result = prime * result
				+ ((passerID == null) ? 0 : passerID.hashCode());
		result = prime * result
				+ ((passerName == null) ? 0 : passerName.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		result = prime * result
				+ ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result
				+ ((receiverID == null) ? 0 : receiverID.hashCode());
		result = prime * result
				+ ((receiverName == null) ? 0 : receiverName.hashCode());
		result = prime * result
				+ ((reference == null) ? 0 : reference.hashCode());
		result = prime * result
				+ ((referenceCode == null) ? 0 : referenceCode.hashCode());
		result = prime
				* result
				+ ((referenceOrganization == null) ? 0 : referenceOrganization
						.hashCode());
		result = prime * result
				+ ((responsibleTel == null) ? 0 : responsibleTel.hashCode());
		result = prime * result
				+ ((staffAddress == null) ? 0 : staffAddress.hashCode());
		result = prime * result
				+ ((staffCode == null) ? 0 : staffCode.hashCode());
		result = prime * result + ((staffID == null) ? 0 : staffID.hashCode());
		result = prime
				* result
				+ ((staffIdentityCard == null) ? 0 : staffIdentityCard
						.hashCode());
		result = prime * result
				+ ((staffName == null) ? 0 : staffName.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((titles == null) ? 0 : titles.hashCode());
		result = prime * result
				+ ((userAccountNum == null) ? 0 : userAccountNum.hashCode());
		result = prime * result
				+ ((vehicleBrand == null) ? 0 : vehicleBrand.hashCode());
		result = prime * result + ((xmtype == null) ? 0 : xmtype.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CostSheetBill other = (CostSheetBill) obj;
		if (PcsbID == null) {
			if (other.PcsbID != null)
				return false;
		} else if (!PcsbID.equals(other.PcsbID))
			return false;
		if (PhotoFileName == null) {
			if (other.PhotoFileName != null)
				return false;
		} else if (!PhotoFileName.equals(other.PhotoFileName))
			return false;
		if (accessoriesUrl == null) {
			if (other.accessoriesUrl != null)
				return false;
		} else if (!accessoriesUrl.equals(other.accessoriesUrl))
			return false;
		if (accountNum == null) {
			if (other.accountNum != null)
				return false;
		} else if (!accountNum.equals(other.accountNum))
			return false;
		if (actualAmount == null) {
			if (other.actualAmount != null)
				return false;
		} else if (!actualAmount.equals(other.actualAmount))
			return false;
		if (billCompanyName == null) {
			if (other.billCompanyName != null)
				return false;
		} else if (!billCompanyName.equals(other.billCompanyName))
			return false;
		if (billComppanyID == null) {
			if (other.billComppanyID != null)
				return false;
		} else if (!billComppanyID.equals(other.billComppanyID))
			return false;
		if (billDeptID == null) {
			if (other.billDeptID != null)
				return false;
		} else if (!billDeptID.equals(other.billDeptID))
			return false;
		if (billDeptName == null) {
			if (other.billDeptName != null)
				return false;
		} else if (!billDeptName.equals(other.billDeptName))
			return false;
		if (billStaffID == null) {
			if (other.billStaffID != null)
				return false;
		} else if (!billStaffID.equals(other.billStaffID))
			return false;
		if (billStaffName == null) {
			if (other.billStaffName != null)
				return false;
		} else if (!billStaffName.equals(other.billStaffName))
			return false;
		if (billStatus == null) {
			if (other.billStatus != null)
				return false;
		} else if (!billStatus.equals(other.billStatus))
			return false;
		if (billsDate == null) {
			if (other.billsDate != null)
				return false;
		} else if (!billsDate.equals(other.billsDate))
			return false;
		if (billsType == null) {
			if (other.billsType != null)
				return false;
		} else if (!billsType.equals(other.billsType))
			return false;
		if (budgetAmount == null) {
			if (other.budgetAmount != null)
				return false;
		} else if (!budgetAmount.equals(other.budgetAmount))
			return false;
		if (budgetUpper == null) {
			if (other.budgetUpper != null)
				return false;
		} else if (!budgetUpper.equals(other.budgetUpper))
			return false;
		if (carFrameNum == null) {
			if (other.carFrameNum != null)
				return false;
		} else if (!carFrameNum.equals(other.carFrameNum))
			return false;
		if (carID == null) {
			if (other.carID != null)
				return false;
		} else if (!carID.equals(other.carID))
			return false;
		if (carNum == null) {
			if (other.carNum != null)
				return false;
		} else if (!carNum.equals(other.carNum))
			return false;
		if (carType == null) {
			if (other.carType != null)
				return false;
		} else if (!carType.equals(other.carType))
			return false;
		if (ccompanyID == null) {
			if (other.ccompanyID != null)
				return false;
		} else if (!ccompanyID.equals(other.ccompanyID))
			return false;
		if (ccompanyName == null) {
			if (other.ccompanyName != null)
				return false;
		} else if (!ccompanyName.equals(other.ccompanyName))
			return false;
		if (ccompanyRelationship == null) {
			if (other.ccompanyRelationship != null)
				return false;
		} else if (!ccompanyRelationship.equals(other.ccompanyRelationship))
			return false;
		if (colorPaintNum == null) {
			if (other.colorPaintNum != null)
				return false;
		} else if (!colorPaintNum.equals(other.colorPaintNum))
			return false;
		if (companyAddr == null) {
			if (other.companyAddr != null)
				return false;
		} else if (!companyAddr.equals(other.companyAddr))
			return false;
		if (companyBankNum == null) {
			if (other.companyBankNum != null)
				return false;
		} else if (!companyBankNum.equals(other.companyBankNum))
			return false;
		if (companyID == null) {
			if (other.companyID != null)
				return false;
		} else if (!companyID.equals(other.companyID))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (companyTel == null) {
			if (other.companyTel != null)
				return false;
		} else if (!companyTel.equals(other.companyTel))
			return false;
		if (costdescription == null) {
			if (other.costdescription != null)
				return false;
		} else if (!costdescription.equals(other.costdescription))
			return false;
		if (cresponsible == null) {
			if (other.cresponsible != null)
				return false;
		} else if (!cresponsible.equals(other.cresponsible))
			return false;
		if (csbID == null) {
			if (other.csbID != null)
				return false;
		} else if (!csbID.equals(other.csbID))
			return false;
		if (csbKey == null) {
			if (other.csbKey != null)
				return false;
		} else if (!csbKey.equals(other.csbKey))
			return false;
		if (csreviewedID == null) {
			if (other.csreviewedID != null)
				return false;
		} else if (!csreviewedID.equals(other.csreviewedID))
			return false;
		if (csreviewedName == null) {
			if (other.csreviewedName != null)
				return false;
		} else if (!csreviewedName.equals(other.csreviewedName))
			return false;
		if (cstaffID == null) {
			if (other.cstaffID != null)
				return false;
		} else if (!cstaffID.equals(other.cstaffID))
			return false;
		if (cstaffName == null) {
			if (other.cstaffName != null)
				return false;
		} else if (!cstaffName.equals(other.cstaffName))
			return false;
		if (cstaffRelationship == null) {
			if (other.cstaffRelationship != null)
				return false;
		} else if (!cstaffRelationship.equals(other.cstaffRelationship))
			return false;
		if (departmentID == null) {
			if (other.departmentID != null)
				return false;
		} else if (!departmentID.equals(other.departmentID))
			return false;
		if (departmentName == null) {
			if (other.departmentName != null)
				return false;
		} else if (!departmentName.equals(other.departmentName))
			return false;
		if (docIds == null) {
			if (other.docIds != null)
				return false;
		} else if (!docIds.equals(other.docIds))
			return false;
		if (driveType == null) {
			if (other.driveType != null)
				return false;
		} else if (!driveType.equals(other.driveType))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (engineNum == null) {
			if (other.engineNum != null)
				return false;
		} else if (!engineNum.equals(other.engineNum))
			return false;
		if (engineType == null) {
			if (other.engineType != null)
				return false;
		} else if (!engineType.equals(other.engineType))
			return false;
		if (fuelType == null) {
			if (other.fuelType != null)
				return false;
		} else if (!fuelType.equals(other.fuelType))
			return false;
		if (groupCompanySn == null) {
			if (other.groupCompanySn != null)
				return false;
		} else if (!groupCompanySn.equals(other.groupCompanySn))
			return false;
		if (groupJournalNum == null) {
			if (other.groupJournalNum != null)
				return false;
		} else if (!groupJournalNum.equals(other.groupJournalNum))
			return false;
		if (industryType == null) {
			if (other.industryType != null)
				return false;
		} else if (!industryType.equals(other.industryType))
			return false;
		if (isLeaf == null) {
			if (other.isLeaf != null)
				return false;
		} else if (!isLeaf.equals(other.isLeaf))
			return false;
		if (journalNum == null) {
			if (other.journalNum != null)
				return false;
		} else if (!journalNum.equals(other.journalNum))
			return false;
		if (organizationID == null) {
			if (other.organizationID != null)
				return false;
		} else if (!organizationID.equals(other.organizationID))
			return false;
		if (pJournalNum == null) {
			if (other.pJournalNum != null)
				return false;
		} else if (!pJournalNum.equals(other.pJournalNum))
			return false;
		if (passerCompanyID == null) {
			if (other.passerCompanyID != null)
				return false;
		} else if (!passerCompanyID.equals(other.passerCompanyID))
			return false;
		if (passerCompanyName == null) {
			if (other.passerCompanyName != null)
				return false;
		} else if (!passerCompanyName.equals(other.passerCompanyName))
			return false;
		if (passerDeptID == null) {
			if (other.passerDeptID != null)
				return false;
		} else if (!passerDeptID.equals(other.passerDeptID))
			return false;
		if (passerDeptName == null) {
			if (other.passerDeptName != null)
				return false;
		} else if (!passerDeptName.equals(other.passerDeptName))
			return false;
		if (passerID == null) {
			if (other.passerID != null)
				return false;
		} else if (!passerID.equals(other.passerID))
			return false;
		if (passerName == null) {
			if (other.passerName != null)
				return false;
		} else if (!passerName.equals(other.passerName))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (receiverID == null) {
			if (other.receiverID != null)
				return false;
		} else if (!receiverID.equals(other.receiverID))
			return false;
		if (receiverName == null) {
			if (other.receiverName != null)
				return false;
		} else if (!receiverName.equals(other.receiverName))
			return false;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		if (referenceCode == null) {
			if (other.referenceCode != null)
				return false;
		} else if (!referenceCode.equals(other.referenceCode))
			return false;
		if (referenceOrganization == null) {
			if (other.referenceOrganization != null)
				return false;
		} else if (!referenceOrganization.equals(other.referenceOrganization))
			return false;
		if (responsibleTel == null) {
			if (other.responsibleTel != null)
				return false;
		} else if (!responsibleTel.equals(other.responsibleTel))
			return false;
		if (staffAddress == null) {
			if (other.staffAddress != null)
				return false;
		} else if (!staffAddress.equals(other.staffAddress))
			return false;
		if (staffCode == null) {
			if (other.staffCode != null)
				return false;
		} else if (!staffCode.equals(other.staffCode))
			return false;
		if (staffID == null) {
			if (other.staffID != null)
				return false;
		} else if (!staffID.equals(other.staffID))
			return false;
		if (staffIdentityCard == null) {
			if (other.staffIdentityCard != null)
				return false;
		} else if (!staffIdentityCard.equals(other.staffIdentityCard))
			return false;
		if (staffName == null) {
			if (other.staffName != null)
				return false;
		} else if (!staffName.equals(other.staffName))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (titles == null) {
			if (other.titles != null)
				return false;
		} else if (!titles.equals(other.titles))
			return false;
		if (userAccountNum == null) {
			if (other.userAccountNum != null)
				return false;
		} else if (!userAccountNum.equals(other.userAccountNum))
			return false;
		if (vehicleBrand == null) {
			if (other.vehicleBrand != null)
				return false;
		} else if (!vehicleBrand.equals(other.vehicleBrand))
			return false;
		if (xmtype == null) {
			if (other.xmtype != null)
				return false;
		} else if (!xmtype.equals(other.xmtype))
			return false;
		return true;
	}

	/**
	 * 主键
	 * @return
	 */
	public String getCsbKey() {
		return csbKey;
	}
	/**
	 * 主键
	 */
	public void setCsbKey(String csbKey) {
		this.csbKey = csbKey;
	}
	/**
	 * 业务主键
	 * @return
	 */
	public String getCsbID() {
		return csbID;
	}
	/**
	 * 业务主键
	 */
	public void setCsbID(String csbID) {
		this.csbID = csbID;
	}
	/**
	 * 集团公司的标识  外键
	 * @return
	 */
	public String getGroupCompanySn() {
		return groupCompanySn;
	}
	/**
	 * 集团公司的标识  外键
	 */
	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}
	/**
	 * 公司  外键
	 * @return
	 */
	public String getCompanyID() {
		return companyID;
	}
	/**
	 * 公司  外键
	 */
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	/**
	 * 单据所在部门  外键
	 * @return
	 */
	public String getOrganizationID() {
		return organizationID;
	}
	/**
	 * 单据所在部门  外键
	 */
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	/**
	 * 公司银行账号
	 * @return
	 */
	public String getCompanyBankNum() {
		return companyBankNum;
	}
	/**
	 * 公司银行账号
	 */
	public void setCompanyBankNum(String companyBankNum) {
		this.companyBankNum = companyBankNum;
	}
	/**
	 * 单据状态  未审核（00）、审核中（01）、审核通过（02）、驳回（10）
	 * @return
	 */
	public String getBillStatus() {
		return billStatus;
	}
	/**
	 * 单据状态   未审核（00）、审核中（01）、审核通过（02）、驳回（10）
	 */
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	/**
	 * 往来个人ID
	 * @return
	 */
	public String getCstaffID() {
		return cstaffID;
	}
	/**
	 * 往来个人ID
	 */
	public void setCstaffID(String cstaffID) {
		this.cstaffID = cstaffID;
	}
	/**
	 * 往来个人姓名
	 * @return
	 */
	public String getCstaffName() {
		return cstaffName;
	}
	/**
	 * 往来个人姓名
	 */
	public void setCstaffName(String cstaffName) {
		this.cstaffName = cstaffName;
	}
	/**
	 * 往来公司ID
	 * @return
	 */
	public String getCcompanyID() {
		return ccompanyID;
	}
	/**
	 * 往来公司ID
	 */
	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}
	/**
	 * 往来公司姓名
	 * @return
	 */
	public String getCcompanyName() {
		return ccompanyName;
	}
	/**
	 * 往来公司姓名
	 */
	public void setCcompanyName(String ccompanyName) {
		this.ccompanyName = ccompanyName;
	}
	/**
	 * 限定部门内的责任人ID  外键
	 * @return
	 */
	public String getStaffID() {
		return staffID;
	}
	/**
	 * 限定部门内的责任人ID  外键
	 */
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	/**
	 * 责任人姓名
	 * @return
	 */
	public String getStaffName() {
		return staffName;
	}
	/**
	 * 责任人姓名
	 */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	/**
	 * 单据类型
	 * @return
	 */
	public String getBillsType() {
		return billsType;
	}
	/**
	 * 单据类型
	 */
	public void setBillsType(String billsType) {
		this.billsType = billsType;
	}
	/**
	 * 凭证号
	 * @return
	 */
	public String getJournalNum() {
		return journalNum;
	}
	/**
	 * 凭证号
	 */
	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}
	/**
	 * 制单时间
	 * @return
	 */
	public Date getBillsDate() {
		return billsDate;
	}
	/**
	 * 制单时间
	 */
	public void setBillsDate(Date billsDate) {
		this.billsDate = billsDate;
	}
	/**
	 * 制单人ID 外键
	 * @return
	 */
	public String getBillStaffID() {
		return billStaffID;
	}
	/**
	 * 制单人ID 外键
	 */
	public void setBillStaffID(String billStaffID) {
		this.billStaffID = billStaffID;
	}
	/**
	 * 制单人姓名
	 * @return
	 */
	public String getBillStaffName() {
		return billStaffName;
	}
	/**
	 * 制单人姓名
	 */
	public void setBillStaffName(String billStaffName) {
		this.billStaffName = billStaffName;
	}
	/**
	 * 审核人人ID
	 * @return
	 */
	public String getCsreviewedID() {
		return csreviewedID;
	}
	/**
	 * 审核人人ID
	 */
	public void setCsreviewedID(String csreviewedID) {
		this.csreviewedID = csreviewedID;
	}
	/**
	 * 审核人人姓名
	 * @return
	 */
	public String getCsreviewedName() {
		return csreviewedName;
	}
	/**
	 * 审核人人姓名
	 */
	public void setCsreviewedName(String csreviewedName) {
		this.csreviewedName = csreviewedName;
	}
	/**
	 * 子部门ID(限定部门单据) 外键
	 * @return
	 */
	public String getDepartmentID() {
		return departmentID;
	}
	/**
	 * 子部门ID(限定部门单据) 外键
	 */
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	/**
	 * 个人往来关系
	 * @return
	 */
	public String getCstaffRelationship() {
		return cstaffRelationship;
	}
	/**
	 * 个人往来关系
	 */
	public void setCstaffRelationship(String cstaffRelationship) {
		this.cstaffRelationship = cstaffRelationship;
	}
	/**
	 * 往来个人银行帐号
	 * @return
	 */
	public String getUserAccountNum() {
		return userAccountNum;
	}
	/**
	 * 往来个人银行帐号
	 */
	public void setUserAccountNum(String userAccountNum) {
		this.userAccountNum = userAccountNum;
	}
	/**
	 * 往来单位关系
	 * @return
	 */
	public String getCcompanyRelationship() {
		return ccompanyRelationship;
	}
	/**
	 * 往来单位关系
	 */
	public void setCcompanyRelationship(String ccompanyRelationship) {
		this.ccompanyRelationship = ccompanyRelationship;
	}
	/**
	 * 往来公司银行账号
	 * @return
	 */
	public String getAccountNum() {
		return accountNum;
	}
	/**
	 * 往来公司银行账号
	 */
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	/**
	 * 附件(路径)
	 * @return
	 */
	public String getAccessoriesUrl() {
		return accessoriesUrl;
	}
	/**
	 * 附件(路径)
	 */
	public void setAccessoriesUrl(String accessoriesUrl) {
		this.accessoriesUrl = accessoriesUrl;
	}
	/**
	 * 附件文件
	 * @return
	 */
	public File getPhoto() {
		return photo;
	}
	/**
	 * 附件文件
	 */
	public void setPhoto(File photo) {
		this.photo = photo;
	}
	/**
	 * 附件名称
	 * @return
	 */
	public String getPhotoFileName() {
		return PhotoFileName;
	}
	/**
	 * 附件名称
	 * @return
	 */
	public void setPhotoFileName(String photoFileName) {
		PhotoFileName = photoFileName;
	}
	/**
	 * 项目名称
	 * @return
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * 项目名称
	 * @param projectName
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * 起时间
	 * @return
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 起时间
	 * @param startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 止时间
	 * @return
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 止时间
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 项目内容
	 * @return
	 */
	public String getCostdescription() {
		return costdescription;
	}
	/**
	 * 项目内容
	 * @param costdescription
	 */
	public void setCostdescription(String costdescription) {
		this.costdescription = costdescription;
	}
	/**
	 * 项目预算总金额
	 * @return
	 */
	public String getBudgetAmount() {
		return budgetAmount;
	}
	/**
	 * 项目预算总金额
	 * @param budgetAmount
	 */
	public void setBudgetAmount(String budgetAmount) {
		this.budgetAmount = budgetAmount;
	}
	/**
	 * 项目实际总价(预留)
	 * @return
	 */
	public String getActualAmount() {
		return actualAmount;
	}
	/**
	 * 项目实际总价(预留)
	 * @param actualAmount
	 */
	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}
	/**
	 * 父单据ID
	 * @return
	 */
	public String getPcsbID() {
		return PcsbID;
	}
	/**
	 * 父单据ID
	 * @param pcsbID
	 */
	public void setPcsbID(String pcsbID) {
		PcsbID = pcsbID;
	}

	public String getpJournalNum() {
		return pJournalNum;
	}

	public void setpJournalNum(String pJournalNum) {
		this.pJournalNum = pJournalNum;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getStaffIdentityCard() {
		return staffIdentityCard;
	}

	public void setStaffIdentityCard(String staffIdentityCard) {
		this.staffIdentityCard = staffIdentityCard;
	}

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public String getReferenceOrganization() {
		return referenceOrganization;
	}

	public void setReferenceOrganization(String referenceOrganization) {
		this.referenceOrganization = referenceOrganization;
	}

	public String getStaffAddress() {
		return staffAddress;
	}

	public void setStaffAddress(String staffAddress) {
		this.staffAddress = staffAddress;
	}

	public String getCompanyAddr() {
		return companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public String getCresponsible() {
		return cresponsible;
	}

	public void setCresponsible(String cresponsible) {
		this.cresponsible = cresponsible;
	}

	public String getResponsibleTel() {
		return responsibleTel;
	}

	public void setResponsibleTel(String responsibleTel) {
		this.responsibleTel = responsibleTel;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getBillComppanyID() {
		return billComppanyID;
	}

	public void setBillComppanyID(String billComppanyID) {
		this.billComppanyID = billComppanyID;
	}

	public String getBillCompanyName() {
		return billCompanyName;
	}

	public void setBillCompanyName(String billCompanyName) {
		this.billCompanyName = billCompanyName;
	}

	public String getBillDeptID() {
		return billDeptID;
	}

	public void setBillDeptID(String billDeptID) {
		this.billDeptID = billDeptID;
	}

	public String getBillDeptName() {
		return billDeptName;
	}

	public void setBillDeptName(String billDeptName) {
		this.billDeptName = billDeptName;
	}

	public String getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(String receiverID) {
		this.receiverID = receiverID;
	}


	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}



	public String getPasserID() {
		return passerID;
	}

	public void setPasserID(String passerID) {
		this.passerID = passerID;
	}

	public String getPasserDeptID() {
		return passerDeptID;
	}

	public void setPasserDeptID(String passerDeptID) {
		this.passerDeptID = passerDeptID;
	}

	public String getPasserCompanyID() {
		return passerCompanyID;
	}

	public void setPasserCompanyID(String passerCompanyID) {
		this.passerCompanyID = passerCompanyID;
	}

	public String getPasserName() {
		return passerName;
	}

	public void setPasserName(String passerName) {
		this.passerName = passerName;
	}

	public String getPasserDeptName() {
		return passerDeptName;
	}

	public void setPasserDeptName(String passerDeptName) {
		this.passerDeptName = passerDeptName;
	}

	public String getPasserCompanyName() {
		return passerCompanyName;
	}

	public void setPasserCompanyName(String passerCompanyName) {
		this.passerCompanyName = passerCompanyName;
	}

	public String getBudgetUpper() {
		return budgetUpper;
	}

	public void setBudgetUpper(String budgetUpper) {
		this.budgetUpper = budgetUpper;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getDocIds() {
		return docIds;
	}

	public void setDocIds(String docIds) {
		this.docIds = docIds;
	}

	public String getPJournalNum() {
		return pJournalNum;
	}

	public void setPJournalNum(String journalNum) {
		pJournalNum = journalNum;
	}

	public String getCarID() {
		return carID;
	}

	public void setCarID(String carID) {
		this.carID = carID;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getEngineNum() {
		return engineNum;
	}

	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}

	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getDriveType() {
		return driveType;
	}

	public void setDriveType(String driveType) {
		this.driveType = driveType;
	}

	public String getColorPaintNum() {
		return colorPaintNum;
	}

	public void setColorPaintNum(String colorPaintNum) {
		this.colorPaintNum = colorPaintNum;
	}

	public String getCarFrameNum() {
		return carFrameNum;
	}

	public void setCarFrameNum(String carFrameNum) {
		this.carFrameNum = carFrameNum;
	}

	public String getXmtype() {
		return xmtype;
	}

	public void setXmtype(String xmtype) {
		this.xmtype = xmtype;
	}

	public String getGroupJournalNum() {
		return groupJournalNum;
	}

	public void setGroupJournalNum(String groupJournalNum) {
		this.groupJournalNum = groupJournalNum;
	}

	public String getXmtypename() {
		return xmtypename;
	}

	public void setXmtypename(String xmtypename) {
		this.xmtypename = xmtypename;
	}

	public String getYdsocre() {
		return ydsocre;
	}

	public void setYdsocre(String ydsocre) {
		this.ydsocre = ydsocre;
	}

	public String getJfscore() {
		return jfscore;
	}

	public void setJfscore(String jfscore) {
		this.jfscore = jfscore;
	}

	public String getKfscore() {
		return kfscore;
	}

	public void setKfscore(String kfscore) {
		this.kfscore = kfscore;
	}

	public String getSdscore() {
		return sdscore;
	}

	public void setSdscore(String sdscore) {
		this.sdscore = sdscore;
	}

	public String getExamineID() {
		return examineID;
	}

	public void setExamineID(String examineID) {
		this.examineID = examineID;
	}

	public String getExamineName() {
		return examineName;
	}

	public void setExamineName(String examineName) {
		this.examineName = examineName;
	}

	public String getExamineComID() {
		return examineComID;
	}

	public void setExamineComID(String examineComID) {
		this.examineComID = examineComID;
	}

	public String getExamineComName() {
		return examineComName;
	}

	public void setExamineComName(String examineComName) {
		this.examineComName = examineComName;
	}

	public String getExamineorgID() {
		return examineorgID;
	}

	public void setExamineorgID(String examineorgID) {
		this.examineorgID = examineorgID;
	}

	public String getExamineorgName() {
		return examineorgName;
	}

	public void setExamineorgName(String examineorgName) {
		this.examineorgName = examineorgName;
	}

	public String getAssignsID() {
		return assignsID;
	}

	public void setAssignsID(String assignsID) {
		this.assignsID = assignsID;
	}

	public String getAssignsName() {
		return assignsName;
	}

	public void setAssignsName(String assignsName) {
		this.assignsName = assignsName;
	}

	public Date getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate(Date signUpDate) {
		this.signUpDate = signUpDate;
	}

	public String getReferrerName() {
		return referrerName;
	}

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}

	public String getReferrerID() {
		return referrerID;
	}

	public void setReferrerID(String referrerID) {
		this.referrerID = referrerID;
	}

	public String getReferrerPhone() {
		return referrerPhone;
	}

	public void setReferrerPhone(String referrerPhone) {
		this.referrerPhone = referrerPhone;
	}

	public String getReferrerIdentityCard() {
		return referrerIdentityCard;
	}

	public void setReferrerIdentityCard(String referrerIdentityCard) {
		this.referrerIdentityCard = referrerIdentityCard;
	}

	public String getReferrerEmail() {
		return referrerEmail;
	}

	public void setReferrerEmail(String referrerEmail) {
		this.referrerEmail = referrerEmail;
	}

	public String getReferrerAddress() {
		return referrerAddress;
	}

	public void setReferrerAddress(String referrerAddress) {
		this.referrerAddress = referrerAddress;
	}
	
	
}
