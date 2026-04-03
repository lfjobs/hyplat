package hy.ea.bo.human.publicreceipts;

import java.util.Date;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * 人事单据子表
 * @author znq
 *
 */
public class PublicreceiptsChild implements BaseBean,ExcelBean{  
	
	private String  orKey;					//主键
	private String  orID;					//逻辑主键 
	private String  prID;     				//外键 
	
	/*********************员工级别************************/ 
	private String rankHumanID;				//人员编号
	private String rankShenzhigrade;		//升降级别   外键
	private String rankNewScale;			//变更后级差
	private String rankEffectivedate;		//生效日期
	private String rankOldlevel;			//原级别明细 外键
	private String rankOldScale;			//原级差
	private String rankStartdate;			//起时间
	private String rankEnddate;				//止时间
	private String rankChangeReason;        //变动理由
	private String rankExamine;				//自我评定(业绩、优缺点) 
	private String rankContent;             //工作内容
	
	/*********************员工加班申请表*********************/ 
	private String overTimePostName;		//岗位
	private String overTimeDays;			//加班天数
	private String overTimeHour;			//加班小时
	private String overTimeSort;			//加班类别
	private String overTimeStartDate;		//起时间
	private String overTimeEndDate;			//止时间
	private String overTimeReason;          //加班事由
	private String overTimeContent;			//加班内容
	private String overtimeWages;			//加班工资评分
	
	/*********************员工请假申请表**********************/  
	private String leavePostName;			//职位
	private String leaveDays;				//请假天数
	private String leaveHour;			    //请假小时
	private String leaveStartDate;			//起时间
	private String leaveEndDate;			//止时间
	private String leaveReceiver;			//工作接管人
	private String leaveType;				//请假类别
	private String leaveReason; 			//请假原因
	private String Signdate;				//报到日期
	private String Terminatedate;			//销假日期 
	private String leavecasual;				//事假累计
	private String leavesick;				//病假累计
	private String checkdiscount; 			//考勤折扣
  
	/*********************奖励/扣分表**********************/ 
	private String rorpJobName;				//职务
	private String rorpPostName;			//岗位
	private String rorpDeductPoint;		    //奖励分/扣分
	private Date   rorpDate;				//奖励/扣分日期
	private String rorpMoney;				//奖励/扣分金额	
	private String rorpMyriad;				//奖励/扣分金额大写
	private String rorpReason;				//奖励/扣分原因
	//private String rorpDeptPrincipal;		部门负责人	
	
	/*********************金币罚单**********************/ 
	private String fineCount;               //惩罚金币数
	private Date   fineDate;                //罚款日期
	private String fineReason;              //罚款原因
	
			
	public static String[] columnRankHeadings() {
		String[] titles = {"序号","公司","部门","凭证号","责任人","单据类型","申请日期","操作人","人员编号","升降级别","生效日期","原级别明细 ","起时间","止时间","变动理由","工作内容","自我评价","部门主管审核人","人力资源部审核人","单据状态"};
		return titles;
	}
	
	public static String[] columnOverHeadings() {
		String[] titles = {"序号","公司","部门","凭证号","责任人","单据类型","申请日期","操作人","岗位","加班类别","起时间","止时间","加班天数","加班小时","加班工资评分","加班事由","加班内容","部门主管审核人","人力资源部审核人","单据状态"};
		return titles;
	}
	public static String[] columnRewardPunishment() {
		String[] titles = {"序号","公司","部门","凭证号","责任人","责任人部门","单据类型","申请日期","操作人","奖励分/扣分","奖励/扣分日期","奖励/扣分金额","奖励/扣分原因","部门主管审核人","人力资源部审核人","总经理审核人","单据状态"};
		return titles;
	}
	public static String[] columnLeaveHeadings(){
		String[] titles = {"序号","公司","部门","凭证号","责任人","单据类型","申请日期","操作人","职位","请假天数","请假小时","事假累计","病假累计","考情折扣","起时间","报到时间","销假时间","止时间","接管人","请假类型","请假事由","部门主管审核人","人力资源部审核人","单据状态"};
		return titles;
	}
	
	public static String[] columnFineHeadings(){
		String[] titles = {"序号","凭证号","公司","责任人部门","责任人","账号信息","账号级别","惩罚金币数量","惩罚原因","制单人","制单日期","部门主管审核部","人力资源审核部","单据状态","日期","附件"};
		return titles;
	}
	
	
	
	@Override
	public String[] properties() { 
		return null;
	}
	
	/**
	 * 主键
	 * @return
	 */  
	public String getOrKey() {
		return orKey;
	}
	/**
	 * 主键 
	 * @param orKey
	 */
	public void setOrKey(String orKey) {
		this.orKey = orKey;
	}
	/**
	 * 逻辑主键 
	 * @return
	 */
	public String getOrID() {
		return orID;
	}
	/**
	 * 逻辑主键 
	 * @param orID
	 */
	public void setOrID(String orID) {
		this.orID = orID;
	}
	/**
	 * 外键 
	 * @return
	 */
	public String getPrID() {
		return prID;
	}
	 
	/**
	 * 外键 
	 * @param prID
	 */
	 
	public void setPrID(String prID) {
		this.prID = prID;
	}
	
	
	/**
	 * (员工级别)人员编号
	 * @return
	 */
	public String getRankHumanID() {
		return rankHumanID;
	}
	/**
	 * (员工级别)人员编号
	 * @param rankNumanID
	 */
	public void setRankHumanID(String rankHumanID) {
		this.rankHumanID = rankHumanID;
	}
	/**
	 * (员工级别)升降级别 外键
	 * @return
	 */
	public String getRankShenzhigrade() {
		return rankShenzhigrade;
	}
	/**
	 * (员工级别)升降级别  外键
	 * @param rankShenzhigrade
	 */
	public void setRankShenzhigrade(String rankShenzhigrade) {
		this.rankShenzhigrade = rankShenzhigrade;
	}
	/**
	 * (员工级别)生效日期
	 * @return
	 */
	public String getRankEffectivedate() {
		return rankEffectivedate;
	}
	/**
	 * (员工级别)生效日期
	 * @param rankEffectivedate
	 */
	public void setRankEffectivedate(String rankEffectivedate) {
		this.rankEffectivedate = rankEffectivedate;
	}
	/**
	 * (员工级别)原级别明细  外键
	 * @return
	 */
	public String getRankOldlevel() {
		return rankOldlevel;
	}
	/**
	 * (员工级别)原级别明细  外键
	 * @param rankOldlevel
	 */
	public void setRankOldlevel(String rankOldlevel) {
		this.rankOldlevel = rankOldlevel;
	}
	/**
	 * (员工级别)起时间
	 * @return
	 */
	public String getRankStartdate() {
		return rankStartdate;
	}
	/**
	 * (员工级别)起时间
	 * @param rankStartdate
	 */
	public void setRankStartdate(String rankStartdate) {
		this.rankStartdate = rankStartdate;
	}
	/**
	 * (员工级别)止时间
	 * @return
	 */
	public String getRankEnddate() {
		return rankEnddate;
	}
	/**
	 * (员工级别)止时间
	 * @param rankEnddate
	 */
	public void setRankEnddate(String rankEnddate) {
		this.rankEnddate = rankEnddate;
	}
	
	/**
	 * (员工级别)变动理由
	 * @return
	 */
	public String getRankChangeReason() {
		return rankChangeReason;
	}
    /**
     * (员工级别)变动理由
     * @param rankChangeReason
     */
	public void setRankChangeReason(String rankChangeReason) {
		this.rankChangeReason = rankChangeReason;
	}

	/**
	 * (员工级别)自我评定(业绩、优缺点)
	 * @return
	 */
	public String getRankExamine() {
		return rankExamine;
	}
	/**
	 * (员工级别)自我评定(业绩、优缺点)
	 * @param rankExamine
	 */
	public void setRankExamine(String rankExamine) {
		this.rankExamine = rankExamine;
	}
	
	/**
	 * (员工级别)工作内容
	 * @return
	 */
	public String getRankContent() {
		return rankContent;
	}
	/**
	 * (员工级别)工作内容
	 * @param rankContent
	 */
	public void setRankContent(String rankContent) {
		this.rankContent = rankContent;
	}

	/**
	 * (请假申请)职位
	 * @return
	 */   
	public String getLeavePostName() {
		return leavePostName;
	}
	/**
	 * (请假申请)职位
	 * @param leavePostName
	 */
	public void setLeavePostName(String leavePostName) {
		this.leavePostName = leavePostName;
	}
	/**
	 * 请假天数
	 * @return
	 */
	public String getLeaveDays() {
		return leaveDays;
	}
	/**
	 * 请假天数
	 * @param leaveDays
	 */
	public void setLeaveDays(String leaveDays) {
		this.leaveDays = leaveDays;
	}
	/**
	 * 请假小时
	 * @return
	 */
	public String getLeaveHour() {
		return leaveHour;
	}
	/**
	 * 请假小时
	 * @param leaveHour
	 */
	public void setLeaveHour(String leaveHour) {
		this.leaveHour = leaveHour;
	}
	 
	/**
	 * (请假申请)起时间
	 * @return
	 */
	public String getLeaveStartDate() {
		return leaveStartDate;
	}
	/**
	 * (请假申请)起时间
	 * @param leaveStartDate
	 */
	public void setLeaveStartDate(String leaveStartDate) {
		this.leaveStartDate = leaveStartDate;
	}
	/**
	 * (请假申请)止时间
	 * @return
	 */
	public String getLeaveEndDate() {
		return leaveEndDate;
	}
	/**
	 * (请假申请)止时间
	 * @param leaveEndDate
	 */
	public void setLeaveEndDate(String leaveEndDate) {
		this.leaveEndDate = leaveEndDate;
	}
	/**
	 * (请假申请)工作接管人
	 * @return
	 */
	public String getLeaveReceiver() {
		return leaveReceiver;
	}
	/**
	 * (请假申请)工作接管人
	 * @param leaveReceiver
	 */
	public void setLeaveReceiver(String leaveReceiver) {
		this.leaveReceiver = leaveReceiver;
	}
	/**
	 * 请假类别
	 * @return
	 */
	public String getLeaveType() {
		return leaveType;
	}
	/**
	 * 请假类别
	 * @param leaveType
	 */
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	/**
	 * 请假原因
	 * @return
	 */
	public String getLeaveReason() {
		return leaveReason;
	}
	/**
	 * 请假原因
	 * @param leaveReason
	 */
	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}
	/**
	 * 报到日期
	 * @return
	 */
	public String getSigndate() {
		return Signdate;
	}
	/**
	 * 报到日期
	 * @param signdate
	 */
	public void setSigndate(String signdate) {
		Signdate = signdate;
	}
	/**
	 * 销假日期
	 * @return
	 */
	public String getTerminatedate() {
		return Terminatedate;
	}
	/**
	 * 销假日期
	 * @param terminatedate
	 */
	public void setTerminatedate(String terminatedate) {
		Terminatedate = terminatedate;
	}
	/**
	 * 事假累计
	 * @return
	 */
	public String getLeavecasual() {
		return leavecasual;
	}
	/**
	 * 事假累计
	 * @param leavecasual
	 */
	public void setLeavecasual(String leavecasual) {
		this.leavecasual = leavecasual;
	}
	/**
	 * 病假累计
	 * @return
	 */
	public String getLeavesick() {
		return leavesick;
	}
	/**
	 * 病假累计
	 * @param leavesick
	 */
	public void setLeavesick(String leavesick) {
		this.leavesick = leavesick;
	}
	/**
	 * 考勤折扣
	 * @return
	 */
	public String getCheckdiscount() {
		return checkdiscount;
	}
	/**
	 * 考勤折扣
	 * @param checkdiscount
	 */
	public void setCheckdiscount(String checkdiscount) {
		this.checkdiscount = checkdiscount;
	}

	/**
	 * 加班类别
	 * @return
	 */
	public String getOverTimeSort() {
		return overTimeSort;
	}
	/**
	 * 加班类别
	 * @param overTimeSort
	 */
	public void setOverTimeSort(String overTimeSort) {
		this.overTimeSort = overTimeSort;
	}
	
	/**
	 * 加班起时间
	 * @return
	 */
	public String getOverTimeStartDate() {
		return overTimeStartDate;
	}
	/**
	 * 加班起时间
	 * @param overTimeStartDate
	 */
	public void setOverTimeStartDate(String overTimeStartDate) {
		this.overTimeStartDate = overTimeStartDate;
	}
	/**
	 * 加班止时间
	 * @return
	 */
	public String getOverTimeEndDate() {
		return overTimeEndDate;
	}
	/**
	 * 加班止时间
	 * @param overTimeEndDate
	 */
	public void setOverTimeEndDate(String overTimeEndDate) {
		this.overTimeEndDate = overTimeEndDate;
	}
	/**
	 * 加班事由
	 * @return
	 */
	public String getOverTimeReason() {
		return overTimeReason;
	}
	/**
	 * 加班事由
	 * @param overTimeReason
	 */
	public void setOverTimeReason(String overTimeReason) {
		this.overTimeReason = overTimeReason;
	}
	/**
	 * (加班申请)加班内容
	 * @return
	 */
	public String getOverTimeContent() {
		return overTimeContent;
	}
	/**
	 * (加班申请)加班内容
	 * @param overTimeContent
	 */
	public void setOverTimeContent(String overTimeContent) {
		this.overTimeContent = overTimeContent;
	}
	/**
	 * 加班岗位
	 * @return
	 */
	public String getOverTimePostName() {
		return overTimePostName;
	}
	/**
	 * 加班岗位
	 * @param overTimePostName
	 */
	public void setOverTimePostName(String overTimePostName) {
		this.overTimePostName = overTimePostName;
	}
	/**
	 * 加班天数
	 * @return
	 */
	public String getOverTimeDays() {
		return overTimeDays;
	}
	/**
	 * 加班天数
	 * @param overTimeDays
	 */
	public void setOverTimeDays(String overTimeDays) {
		this.overTimeDays = overTimeDays;
	}
	/**
	 * 加班时数
	 * @return
	 */
	public String getOverTimeHour() {
		return overTimeHour;
	}
    /**
     * 加班时数
     * @param overTimeHour
     */
	public void setOverTimeHour(String overTimeHour) {
		this.overTimeHour = overTimeHour;
	} 	
	
	/**
	 * 加班工资评分
	 * @return
	 */
	public String getOvertimeWages() {
		return overtimeWages;
	}
	
	/**
	 * 加班工资评分
	 * @return
	 */
	public void setOvertimeWages(String overtimeWages) {
		this.overtimeWages = overtimeWages;
	}

	/**
	 * (奖励/扣分)职务
	 * @return
	 */
	public String getRorpJobName() {
		return rorpJobName;
	}
	/**
	 * (奖励/扣分)职务
	 * @param rorpJobName
	 */
	public void setRorpJobName(String rorpJobName) {
		this.rorpJobName = rorpJobName;
	}
	/**
	 * (奖励/扣分)岗位
	 * @return
	 */
	public String getRorpPostName() {
		return rorpPostName;
	}
	/**
	 * (奖励/扣分)岗位
	 * @param rorpPostName
	 */
	public void setRorpPostName(String rorpPostName) {
		this.rorpPostName = rorpPostName;
	}
	/**
	 * 奖励分/扣分
	 * @return
	 */
	public String getRorpDeductPoint() {
		return rorpDeductPoint;
	}
	/**
	 * 奖励分/扣分
	 * @param rorpDeductPoint
	 */
	public void setRorpDeductPoint(String rorpDeductPoint) {
		this.rorpDeductPoint = rorpDeductPoint;
	}
	
	/**
	 * 奖励/扣分日期
	 * @return
	 */
	public Date getRorpDate() {
		return rorpDate;
	}
	/**
	 * 奖励/扣分日期
	 * @param rorpDate
	 */
	public void setRorpDate(Date rorpDate) {
		this.rorpDate = rorpDate;
	}
	
	/**
	 * 奖励/扣分金额
	 * @return
	 */
	public String getRorpMoney() {
		return rorpMoney;
	}
	/**
	 * 奖励/扣分金额
	 * @param rorpMoney
	 */
	public void setRorpMoney(String rorpMoney) {
		this.rorpMoney = rorpMoney;
	}
	/**
	 * 奖励/扣分金额大写
	 * @return
	 */
	public String getRorpMyriad() {
		return rorpMyriad;
	}
	/**
	 * 奖励/扣分金额大写
	 * @param rorpMyriad
	 */
	public void setRorpMyriad(String rorpMyriad) {
		this.rorpMyriad = rorpMyriad;
	}
	
	/**
	 * 奖励/扣分原因
	 * @return
	 */
	public String getRorpReason() {
		return rorpReason;
	}
	/**
	 * 奖励/扣分原因
	 * @param rorpReason
	 */
	public void setRorpReason(String rorpReason) {
		this.rorpReason = rorpReason;
	}
	/**
	 * 变更后级差
	 * @return
	 */
	public String getRankNewScale() {
		return rankNewScale;
	}
	/**
	 * 变更后级差
	 * @param rankNewScale
	 */
	public void setRankNewScale(String rankNewScale) {
		this.rankNewScale = rankNewScale;
	}
	/**
	 * 原级差
	 * @return
	 */
	public String getRankOldScale() {
		return rankOldScale;
	}
	/**
	 * 原级差
	 * @param rankOldScale
	 */
	public void setRankOldScale(String rankOldScale) {
		this.rankOldScale = rankOldScale;
	}

	public String getFineCount() {
		return fineCount;
	}

	public void setFineCount(String fineCount) {
		this.fineCount = fineCount;
	}

	public Date getFineDate() {
		return fineDate;
	}

	public void setFineDate(Date fineDate) {
		this.fineDate = fineDate;
	}

	public String getFineReason() {
		return fineReason;
	}

	public void setFineReason(String fineReason) {
		this.fineReason = fineReason;
	}
	
	
}
