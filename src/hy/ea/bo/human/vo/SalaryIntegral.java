package hy.ea.bo.human.vo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 考评
 * 
 * @author zgzg
 * 
 */
public class SalaryIntegral implements BaseBean, ExcelBean {
	private String logBookKey;
	private String staffID;
	private String staffName;
	private String companyID;
	private String companyName;
	private Date todaydate;
	private String postname;
	private String categoryname;
	/**
	 * 职务职责积分
	 */
	private String funzioneIntegral;
	/**
	 * 基本积分
	 */
	private String basicIntegral;
	/**
	 * 目标任务考核计分
	 */
	private String targetTaskIntegral;
	/**
	 * 周末加班积分
	 */
	private String weekendIntegral;
	/**
	 * 晚上加班积分
	 */
	private String workNightIntegral;
	/**
	 * 节假日加班积分
	 */
	private String workHolidaysIntegral;
	/**
	 * 月考评积分
	 */
	private String appraisalIntegral;
	/**
	 * 计件积分
	 */
	//private String pieceIntegral;
	/**
	 * 奖励积分
	 */
	private String rewardIntegral;
	/**
	 * 特殊人才工资积分
	 */
	private String stPay;
	/**
	 * 保密工资积分
	 */
	private String secrecyPay;
	/**
	 * 安全积分
	 */
	private String safetyIntegral;
	/**
	 * 任务得分
	 */
	private String taskIntegral;
	/**
	 * 补助话费
	 */
	//private String phoneSubsidy;
	/**
	 * 生活补助
	 */
	//private String livingSubsidy;
	/**
	 * 个人所得税
	 */
	private String personalDiscount;
	/**
	 * 个人应交社保积分
	 */
	private String personalSocialSecurity;
	/**
	 * 个人应交公积金积分
	 */
	private String personalReservedFunds;
	/**
	 * 扣电话费
	 */
	//private String phoneDiscount;
	/**
	 * 扣生活费
	 */
	//private String liveDiscount;
	/**
	 * 违规折扣
	 */
	private String violationDiscount;
	/**
	 * 任务折扣
	 */
	private String taskDiscount;
	/**
	 * 暂扣安全积分
	 */
	private String safetyDiscount;
	/**
	 * 考勤折扣
	 */
	private String attendanceDiscount;
	
	/**
	 * 生活补助
	 */
	private String living;
	/**
	 * 孝道金
	 */
	private String pietypay;
	/**
	 * 竞职金
	 */
	private String campaignpay;	
	/**
	 * 通讯补助
	 */
	private String telecompay ;
	/**
	 * pk 金
	 */
	private String pkpay;	
	
	
	/**
	 * 公司承担积分
	 */
	private String holidaysIntegral;
	/**
	 * 自定义扣分项字段名
	 */
	private static List<Object> WageFiledCut;
	/**
	 * 自定义加分项字段名
	 */
	private static List<Object> WageFiledAdd;
	/**
	 * 自定义扣分项分数
	 */
	private List<Object> customWageCut;
	/**
	 * 自定义加分项分数
	 */
	private List<Object> customWageAdd;
	/**
	 * 非数据库字段 
	 */
	private static  String arg;
	
	private static String titleDate;

	public static String[] columnHeadings() {
		String[] titles = { "序号"};
		String[] titles2={"公司名称", "姓名","员工种类","岗位名称","职务职责积分", "基本积分", "目标任务考核计分",
				"周末加班积分", "晚上加班", "节假日加班积分", "月考评积分", 
				//"计件积分", 
				"奖励积分", "特殊人才积分",
				"保密工资积分", "安全积分", "任务得分","孝道金积分","竞职金积分","生活补助积分","通讯补助积分"};
				//"补助话费", "生活补助",
		String[] titles1={"个人所得税", "个人应交社保",
				"个人应交公积金", 
				//"扣电话费", "扣生活费",
				"违规折扣", "任务折扣", "暂扣安全积分", "考勤折扣" };
		List<String>  arry=new ArrayList<String>();
		arry.add(titles[0]);
		if(arg!=null&&arg.equals("1")){
			arry.add("日期月份");
		}
		for (int i = 0; i < titles2.length; i++) {
			arry.add(titles2[i]);
		}
		if (WageFiledAdd!=null&&WageFiledAdd.size()>0) {
			for (int i = 0; i < WageFiledAdd.size(); i++) {
				arry.add(WageFiledAdd.get(i).toString());
			}
		}
		for (int i = 0; i < titles1.length; i++) {
			arry.add(titles1[i]);
		}
		if (WageFiledCut!=null&&WageFiledCut.size()>0) {
			for (int i = 0; i < WageFiledCut.size(); i++) {
				arry.add(WageFiledCut.get(i).toString());
			}
		}
		arry.add("应得积分");
		arry.add("实得积分");
		arry.add("实得工资");
		arry.add("公司承担积分");
		arry.add("签名");
		arry.add("备注");
		String[] str=new String[arry.size()];
		titles=(String[]) arry.toArray(str);
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = { companyName, staffName, categoryname,postname,funzioneIntegral,
				basicIntegral, targetTaskIntegral, weekendIntegral,
				workNightIntegral, workHolidaysIntegral, appraisalIntegral,
				//pieceIntegral, 
				rewardIntegral, stPay, secrecyPay,
				safetyIntegral, taskIntegral,pietypay,campaignpay,living,telecompay};
				//phoneSubsidy, livingSubsidy,
		String[] properties1={personalDiscount, personalSocialSecurity,
				personalReservedFunds, 
				//phoneDiscount, liveDiscount,
				violationDiscount, taskDiscount, safetyDiscount,
				attendanceDiscount };
		List<String>  arry=new ArrayList<String>();
		if(arg!=null&&arg.equals("1")){
			arry.add(logBookKey);
		}
		for (int i = 0; i < properties.length; i++) {
			arry.add(properties[i]);
		}
		if (customWageAdd!=null&&customWageAdd.size()>0) {
			for (int i = 0; i < customWageAdd.size(); i++) {
				arry.add(customWageAdd.get(i).toString());
			}
		}else{
			arry.add("0");
		}
		for (int i = 0; i < properties1.length; i++) {
			arry.add(properties1[i]);
		}
		if (customWageCut!=null&&customWageCut.size()>0) {
			for (int i = 0; i < customWageCut.size(); i++) {
				arry.add(customWageCut.get(i).toString());
			}
		}
		arry.add( getDueIntegral());
		arry.add(  getObtainedIntegral());
		arry.add( getObtainedMenoy());
		arry.add( holidaysIntegral);
		String[] str=new String[arry.size()];
		properties=(String[]) arry.toArray(str);
		return properties;
	}

	public SalaryIntegral() {

	}

	public SalaryIntegral(String logBookKey, String companyName,
			String staffID, String staffName,String categoryname,String postname, String funzioneIntegral,
			String basicIntegral, String targetTaskIntegral,
			String weekendIntegral, String workNightIntegral,
			String workHolidaysIntegral, String appraisalIntegral,
			//String pieceIntegral,
			String rewardIntegral, String stPay,
			String secrecyPay, String safetyIntegral, String taskIntegral,
			//String phoneSubsidy, String livingSubsidy, 
			String personalDiscount,
			String personalSocialSecurity, String personalReservedFunds,
			//String phoneDiscount, String liveDiscount,
			String violationDiscount, String taskDiscount,
			String safetyDiscount, String attendanceDiscount,
			String holidaysIntegral, String living, String pietypay,
			String campaignpay, String telecompay) {
		super();

		
		if (null == telecompay || "".equals(telecompay)) {
			telecompay = "0";
		}
		if (null == campaignpay || "".equals(campaignpay)) {
			campaignpay = "0";
		}
		if (null == pietypay || "".equals(pietypay)) {
			pietypay = "0";
		}
		if (null == living || "".equals(living)) {
			living = "0";
		}
		
		if (null == funzioneIntegral || "".equals(funzioneIntegral)) {
			funzioneIntegral = "0";
		}
		if (null == basicIntegral || "".equals(basicIntegral)) {
			basicIntegral = "0";
		}
		if (null == targetTaskIntegral || "".equals(targetTaskIntegral)) {
			targetTaskIntegral = "0";
		}
		if (null == weekendIntegral || "".equals(weekendIntegral)) {
			weekendIntegral = "0";
		}
		if (null == workNightIntegral || "".equals(workNightIntegral)) {
			workNightIntegral = "0";
		}
		if (null == workHolidaysIntegral || "".equals(workHolidaysIntegral)) {
			workHolidaysIntegral = "0";
		}
		if (null == holidaysIntegral || "".equals(holidaysIntegral)) {
			holidaysIntegral = "0";
		}
		/*if (null == phoneSubsidy || "".equals(phoneSubsidy)) {
			phoneSubsidy = "0";
		}*/
		/*if (null == livingSubsidy || "".equals(livingSubsidy)) {
			livingSubsidy = "0";
		}*/
		if (null == appraisalIntegral || "".equals(appraisalIntegral)) {
			appraisalIntegral = "0";
		}
	/*	if (null == pieceIntegral || "".equals(pieceIntegral)) {
			pieceIntegral = "0";
		}*/
		if (null == rewardIntegral || "".equals(rewardIntegral)) {
			rewardIntegral = "0";
		}
		if (null == stPay || "".equals(stPay)) {
			stPay = "0";
		}
		if (null == secrecyPay || "".equals(secrecyPay)) {
			secrecyPay = "0";
		}
		if (null == safetyIntegral || "".equals(safetyIntegral)) {
			safetyIntegral = "0";
		}
		if (null == taskIntegral || "".equals(taskIntegral)) {
			taskIntegral = "0";
		}
		if (null == personalDiscount || "".equals(personalDiscount)) {
			personalDiscount = "0";
		}
		if (null == personalSocialSecurity || "".equals(personalSocialSecurity)) {
			personalSocialSecurity = "0";
		}
		if (null == personalReservedFunds || "".equals(personalReservedFunds)) {
			personalReservedFunds = "0";
		}
		/*if (null == phoneDiscount || "".equals(phoneDiscount)) {
			phoneDiscount = "0";
		}*/
		/*if (null == liveDiscount || "".equals(liveDiscount)) {
			liveDiscount = "0";
		}*/

		if (null == violationDiscount || "".equals(violationDiscount)) {
			violationDiscount = "0";
		}
		if (null == taskDiscount || "".equals(taskDiscount)) {
			taskDiscount = "0";
		}
		if (null == safetyDiscount || "".equals(safetyDiscount)) {
			taskIntegral = "0";
		}
		if (null == attendanceDiscount || "".equals(attendanceDiscount)) {
			attendanceDiscount = "0";
		}
		this.postname =postname;
		this.logBookKey = logBookKey;
		this.companyName = companyName;
		this.staffID = staffID;
		this.staffName = staffName;
		this.categoryname=categoryname;
		this.funzioneIntegral = funzioneIntegral;
		this.basicIntegral = basicIntegral;
		this.targetTaskIntegral = targetTaskIntegral;
		this.weekendIntegral = weekendIntegral;
		this.workNightIntegral = workNightIntegral;
		this.workHolidaysIntegral = workHolidaysIntegral;
		this.holidaysIntegral = holidaysIntegral;
		this.appraisalIntegral = appraisalIntegral;
		//this.pieceIntegral = pieceIntegral;
		this.rewardIntegral = rewardIntegral;
		this.stPay = stPay;
		this.secrecyPay = secrecyPay;
		this.safetyIntegral = safetyIntegral;
		this.taskIntegral = taskIntegral;
		//this.phoneSubsidy = phoneSubsidy;
		//this.livingSubsidy = livingSubsidy;
		this.personalDiscount = personalDiscount;
		this.personalSocialSecurity = personalSocialSecurity;
		this.personalReservedFunds = personalReservedFunds;
		//this.phoneDiscount = phoneDiscount;
		//this.liveDiscount = liveDiscount;
		this.violationDiscount = violationDiscount;
		this.taskDiscount = taskDiscount;
		this.safetyDiscount = safetyDiscount;
		this.attendanceDiscount = attendanceDiscount;
		this.living = living;
		this.pietypay = pietypay;
		this.campaignpay = campaignpay;
		this.telecompay = telecompay;
	}
	
	
	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public Date getTodaydate() {
		return todaydate;
	}

	public void setTodaydate(Date todaydate) {
		this.todaydate = todaydate;
	}

	/**
	 * 职务职责积分
	 * 
	 * @return
	 */
	public String getFunzioneIntegral() {
		if (funzioneIntegral.equals("")) {
			return "0";
		}
		return funzioneIntegral;
	}

	public void setFunzioneIntegral(String funzioneIntegral) {
		this.funzioneIntegral = funzioneIntegral;
	}

	/**
	 * 基本积分
	 * 
	 * @return
	 */
	public String getBasicIntegral() {
		if (basicIntegral.equals("")) {
			return "0";
		}
		return basicIntegral;
	}

	public void setBasicIntegral(String basicIntegral) {

		this.basicIntegral = basicIntegral;
	}

	/**
	 * 周末加班积分
	 * 
	 * @return
	 */
	public String getWeekendIntegral() {
		if (weekendIntegral.substring(0, 1).equals("."))
			weekendIntegral = "0" + weekendIntegral;
		return weekendIntegral;
	}

	public void setWeekendIntegral(String weekendIntegral) {
		this.weekendIntegral = weekendIntegral;
	}

	/**
	 * 公司承担积分
	 * 
	 * @return
	 */
	public String getHolidaysIntegral() {
		if (holidaysIntegral.equals("")) {
			return "0";
		}
		return holidaysIntegral;
	}

	public void setHolidaysIntegral(String holidaysIntegral) {
		this.holidaysIntegral = holidaysIntegral;
	}

	/**
	 * 月考评积分
	 * 
	 * @return
	 */
	public String getAppraisalIntegral() {
		if (appraisalIntegral.equals("")) {
			return "0";
		}
		return appraisalIntegral;
	}

	public void setAppraisalIntegral(String appraisalIntegral) {
		this.appraisalIntegral = appraisalIntegral;
	}

	/**
	 * 计件积分
	 * 
	 * @return
	 */
	/*public String getPieceIntegral() {
		if (pieceIntegral.equals("")) {
			return "0";
		}
		return pieceIntegral;
	}

	public void setPieceIntegral(String pieceIntegral) {
		this.pieceIntegral = pieceIntegral;
	}*/

	/**
	 * 奖励积分
	 * 
	 * @return
	 */
	public String getRewardIntegral() {
		if (rewardIntegral.equals("")) {
			return "0";
		}
		return rewardIntegral;
	}

	public void setRewardIntegral(String rewardIntegral) {
		this.rewardIntegral = rewardIntegral;
	}

	/**
	 * 安全积分
	 * 
	 * @return
	 */
	public String getSafetyIntegral() {
		if (safetyIntegral.equals("")) {
			return "0";
		}
		return safetyIntegral;
	}

	public void setSafetyIntegral(String safetyIntegral) {
		this.safetyIntegral = safetyIntegral;
	}

	/**
	 * 个人应交
	 * 
	 * @return
	 */
	public String getPersonalDiscount() {
		if (personalDiscount.equals("")) {
			return "0";
		}
		return personalDiscount;
	}

	public void setPersonalDiscount(String personalDiscount) {
		this.personalDiscount = personalDiscount;
	}

	/**
	 * 违规折扣
	 * 
	 * @return
	 */
	public String getViolationDiscount() {
		if (violationDiscount.equals("")) {
			return "0";
		}
		return violationDiscount;
	}

	public void setViolationDiscount(String violationDiscount) {
		this.violationDiscount = violationDiscount;
	}

	/**
	 * 任务折扣
	 * 
	 * @return
	 */
	public String getTaskDiscount() {
		if (taskDiscount.equals("")) {
			return "0";
		}
		return taskDiscount;
	}

	public void setTaskDiscount(String taskDiscount) {
		this.taskDiscount = taskDiscount;
	}

	/**
	 * 暂扣安全积分
	 * 
	 * @return
	 */
	public String getSafetyDiscount() {
		if (safetyDiscount.equals("")) {
			return "0";
		}
		return safetyDiscount;
	}

	public void setSafetyDiscount(String safetyDiscount) {
		this.safetyDiscount = safetyDiscount;
	}

	/**
	 * 考勤折扣
	 * 
	 * @return
	 */
	public String getAttendanceDiscount() {
		if (attendanceDiscount.equals("")) {
			return "0";
		}
		return attendanceDiscount;
	}

	public void setAttendanceDiscount(String attendanceDiscount) {
		this.attendanceDiscount = attendanceDiscount;
	}

	/**
	 * 应得积分
	 * 
	 * @return
	 */
	public String getDueIntegral() {
		float due = Float.parseFloat(funzioneIntegral)
				+ Float.parseFloat(basicIntegral)
				+ Float.parseFloat(targetTaskIntegral)
				+ Float.parseFloat(weekendIntegral)
				+ Float.parseFloat(workNightIntegral)
				+ Float.parseFloat(workHolidaysIntegral)
				+ Float.parseFloat(appraisalIntegral)
				//+ Float.parseFloat(pieceIntegral)
				+ Float.parseFloat(rewardIntegral) + Float.parseFloat(stPay)
				+ Float.parseFloat(secrecyPay)
				+ Float.parseFloat(safetyIntegral)
				+ Float.parseFloat(taskIntegral)
				+ Float.parseFloat(living)
				+ Float.parseFloat(pietypay)
				+ Float.parseFloat(campaignpay)
				+ Float.parseFloat(telecompay)
//				+ Float.parseFloat(pkpay)
				//+ Float.parseFloat(phoneSubsidy)
				//+ Float.parseFloat(livingSubsidy)
				;
		if(customWageAdd!=null&&customWageAdd.size()!=0){
				for (int i = 0; i < customWageAdd.size(); i++) {
					due+=Float.parseFloat(customWageAdd.get(i)==null||"".equals(customWageAdd.get(i).toString())?"0":customWageAdd.get(i).toString());
				}
		}
		if (due == 0.0) {
			return "0";
		}
		DecimalFormat g=new DecimalFormat("0.000");
		return g.format(Math.round(due * 1000) / 1000f);

	}

	/**
	 * 实的积分
	 * 
	 * @return
	 */
	public String getObtainedIntegral() {

		float obtained = Float.parseFloat(personalDiscount)
				+ Float.parseFloat(personalSocialSecurity)
				+ Float.parseFloat(personalReservedFunds)
				//+ Float.parseFloat(phoneDiscount)
				//+ Float.parseFloat(liveDiscount)
				+ Float.parseFloat(violationDiscount)
				+ Float.parseFloat(taskDiscount)
				+ Float.parseFloat(safetyDiscount)
				+ Float.parseFloat(attendanceDiscount);
		if(customWageCut!=null&&customWageCut.size()!=0){
				for (int i = 0; i < customWageCut.size(); i++) {
					obtained+=Float.parseFloat(customWageCut.get(i)==null||"".equals(customWageCut.get(i).toString())?"0":customWageCut.get(i).toString());
				}
		}
		obtained = Float.parseFloat(getDueIntegral()) - obtained;
		if (obtained == 0.0) {
			return "0";
		}
		DecimalFormat g=new DecimalFormat("0.000");
		return g.format(Math.round(obtained * 1000) / 1000f);
	}

	/**
	 * 实得工资
	 * 
	 * @return
	 */
	public String getObtainedMenoy() {
		float obtained = Float.parseFloat(getObtainedIntegral());
		DecimalFormat g=new DecimalFormat("0.00");
		return g.format(Math.round(obtained * 2000) / 100f);
	}
	/**
	 * 任务得分
	 * 
	 * @return
	 */
	public String getTaskIntegral() {
		if (taskIntegral.equals("")) {
			return "0";
		}
		return taskIntegral;
	}

	public void setTaskIntegral(String taskIntegral) {
		this.taskIntegral = taskIntegral;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLogBookKey() {
		return logBookKey;
	}

	public void setLogBookKey(String logBookKey) {
		this.logBookKey = logBookKey;
	}

	/**
	 * 晚上加班得分
	 * 
	 * @return
	 */
	public String getWorkNightIntegral() {
		return workNightIntegral;
	}

	/**
	 * 晚上加班得分
	 * 
	 * @param workNightIntegral
	 */
	public void setWorkNightIntegral(String workNightIntegral) {
		this.workNightIntegral = workNightIntegral;
	}

	/**
	 * 节假日加班得分
	 * 
	 * @return
	 */
	public String getWorkHolidaysIntegral() {
		return workHolidaysIntegral;
	}

	/**
	 * 节假日加班得分
	 * 
	 * @param workHolidaysIntegral
	 */
	public void setWorkHolidaysIntegral(String workHolidaysIntegral) {
		this.workHolidaysIntegral = workHolidaysIntegral;
	}

	/**
	 * 个人应交社保积分
	 * 
	 * @return
	 */
	public String getPersonalSocialSecurity() {
		return personalSocialSecurity;
	}

	/**
	 * 个人应交社保积分
	 * 
	 * @param personalSocialSecurity
	 */
	public void setPersonalSocialSecurity(String personalSocialSecurity) {
		this.personalSocialSecurity = personalSocialSecurity;
	}

	/**
	 * 个人应交公积金
	 * 
	 * @return
	 */
	public String getPersonalReservedFunds() {
		return personalReservedFunds;
	}

	/**
	 * 个人应交公积金得分
	 * 
	 * @param personalReservedFunds
	 */
	public void setPersonalReservedFunds(String personalReservedFunds) {
		this.personalReservedFunds = personalReservedFunds;
	}

	/**
	 * 扣电话费
	 * 
	 * @return
	 */
/*	public String getPhoneDiscount() {
		return phoneDiscount;
	}

	*//**
	 * 扣电话费
	 * 
	 * @param phoneDiscount
	 *//*
	public void setPhoneDiscount(String phoneDiscount) {
		this.phoneDiscount = phoneDiscount;
	}*/

/*	*//**
	 * 扣生活费
	 * 
	 * @return
	 *//*
	public String getLiveDiscount() {
		return liveDiscount;
	}

	*//**
	 * 扣生活费
	 * 
	 * @param liveDiscount
	 *//*
	public void setLiveDiscount(String liveDiscount) {
		this.liveDiscount = liveDiscount;
	}
*/
	/**
	 * 特殊人才得分
	 * 
	 * @return
	 */
	public String getStPay() {
		return stPay;
	}

	/**
	 * 特殊人才得分
	 * 
	 * @param stPay
	 */
	public void setStPay(String stPay) {
		this.stPay = stPay;
	}

	/**
	 * 保密工资得分
	 * 
	 * @return
	 */
	public String getSecrecyPay() {
		return secrecyPay;
	}

	/**
	 * 保密工资得分
	 * 
	 * @param secrecyPay
	 */
	public void setSecrecyPay(String secrecyPay) {
		this.secrecyPay = secrecyPay;
	}

	/**
	 * 话费补助
	 * 
	 * @return
	 *//*
	public String getPhoneSubsidy() {
		return phoneSubsidy;
	}

	*//**
	 * 话费补助
	 * 
	 * @param phoneSubsidy
	 *//*
	public void setPhoneSubsidy(String phoneSubsidy) {
		this.phoneSubsidy = phoneSubsidy;
	}

	*//**
	 * 生活补助
	 * 
	 * @return
	 *//*
	public String getLivingSubsidy() {
		return livingSubsidy;
	}

	*//**
	 * 生活补助
	 * 
	 * @param livingSubsidy
	 *//*
	public void setLivingSubsidy(String livingSubsidy) {
		this.livingSubsidy = livingSubsidy;
	}*/
	/**
	 * 目标任务考核计分
	 * @return
	 */
	public String getTargetTaskIntegral() {
		return targetTaskIntegral;
	}
	/**
	 * 目标任务考核计分
	 * @param targetTaskIntegral
	 */
	public void setTargetTaskIntegral(String targetTaskIntegral) {
		this.targetTaskIntegral = targetTaskIntegral;
	}
	public List<Object> getCustomWageCut() {
		return customWageCut;
	}

	public void setCustomWageCut(List<Object> customWageCut) {
		this.customWageCut = customWageCut;
	}

	public List<Object> getCustomWageAdd() {
		return customWageAdd;
	}

	public void setCustomWageAdd(List<Object> customWageAdd) {
		this.customWageAdd = customWageAdd;
	}

	public static List<Object> getWageFiledAdd() {
		return WageFiledAdd;
	}

	public static void setWageFiledAdd(List<Object> wageFiledAdd) {
		WageFiledAdd = wageFiledAdd;
	}

	public static List<Object> getWageFiledCut() {
		return WageFiledCut;
	}

	public static void setWageFiledCut(List<Object> wageFiledCut) {
		WageFiledCut = wageFiledCut;
	}

	public static String getArg() {
		return arg;
	}

	public static void setArg(String arg) {
		SalaryIntegral.arg = arg;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public static String getTitleDate() {
		return titleDate;
	}

	public static void setTitleDate(String titleDate) {
		SalaryIntegral.titleDate = titleDate;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getLiving() {
		return living;
	}

	public void setLiving(String living) {
		this.living = living;
	}

	public String getPietypay() {
		return pietypay;
	}

	public void setPietypay(String pietypay) {
		this.pietypay = pietypay;
	}

	public String getCampaignpay() {
		return campaignpay;
	}

	public void setCampaignpay(String campaignpay) {
		this.campaignpay = campaignpay;
	}

	public String getTelecompay() {
		return telecompay;
	}

	public void setTelecompay(String telecompay) {
		this.telecompay = telecompay;
	}

	public String getPkpay() {
		return pkpay;
	}

	public void setPkpay(String pkpay) {
		this.pkpay = pkpay;
	}
	
}
