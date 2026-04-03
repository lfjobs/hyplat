package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 工资级别 PayScale
 * @author zgzg
 *
 */
public class PayScale implements BaseBean, ExcelBean ,Serializable {
	private String payScalekey;
	private String payScaleID; // ************
	private String companyID;
	private String scale;// 岗位级差 ************
	private String position;// 职务 
	private String positionPay;// 职务职责级别  ************
	private String basePay;// 基本工资
	private String workWeek;// 周末加班
	private String workNight;// 晚上加班
	private String workHolidays;// 节假日加班
	private String pushMoney;// 月考评   ************
	private String timingMoney;// 任务考评  ************
	private String piecerate;// 计件工资
	private String paAward;// 全勤奖
	private String awardPay;//奖励工资  ************
	private String stPay;// 特殊人才工资   ************
	private String secrecyPay;// 保密工资  ************
	private String safetyAward;// 安全奖  ************
	private String expenseAllowances;// 津贴费用
	private String discountedCost;// 折扣费用
	private String premium;// 个人保险费
	private String alimony;// 安全费
	private String piTax;// 个人所得税
	private String status;//01作废 00正常

	private Date impotdate ; // 录入时间
	private String num;//级别序号
	private String montasks; //
	private String goaltasks; //月季年目标
	private String pietypay;	//孝道金
	private String campaignpay;	//竞职金
	private String telecompay ;	//通讯补助	
	private String pkpay;	//pk 金
	private String kkqnpay;	//考勤工资
	private String living; //生活补助
	private String unpremium;// 单位保险费
	
	
	public static String[] columnHeadings() {
		String[] titles = { "序号","公司名称","级别序号","岗位级差", "基本工资","职务职责级别","月考评","月度任务","月季年目标",
				"计件工资", "全勤奖","奖励工资","孝道金","竞职金","通讯补助","生活补助","特殊人才工资","保密工资",
				"安全奖","PK金", "全部都计","周末加班","晚上加班", "节假日加班", "考勤工资","津贴费用", "折扣费用",
				"个人保险费", "单位保险费"};
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = {getCompanyName(),num, scale, basePay,positionPay,pushMoney,timingMoney,goaltasks,
				piecerate,paAward,awardPay,pietypay,campaignpay,telecompay,living,stPay,secrecyPay,
				safetyAward,pkpay,getTotalPay(),workWeek,workNight,workHolidays,kkqnpay,expenseAllowances,discountedCost,
				premium,unpremium};
		return properties;
	}


	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	/**
	 * 通过companyID得到companyName
	 * @return
	 */
	public String getCompanyName(){
		String companyName = "";
		if(oMap != null){
			companyName = oMap.get(companyID);
		}
		return companyName;
	}
	
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getTotalPay() {
		float total = 0;
		if (positionPay != null && !"".equals(positionPay))
			total += Float.parseFloat(positionPay);
		if (basePay != null && !"".equals(basePay))
			total += Float.parseFloat(basePay);
		if (workWeek != null && !"".equals(workWeek))
			total += Float.parseFloat(workWeek);
		if (workNight != null && !"".equals(workNight))
			total += Float.parseFloat(workNight);
		if (workHolidays != null && !"".equals(workHolidays))
			total += Float.parseFloat(workHolidays);
		if (pushMoney != null && !"".equals(pushMoney))
			total += Float.parseFloat(pushMoney);
		if (timingMoney != null && !"".equals(timingMoney))
			total += Float.parseFloat(timingMoney);
		if (piecerate != null && !"".equals(piecerate))
			total += Float.parseFloat(piecerate);
		if (paAward != null && !"".equals(paAward))
			total += Float.parseFloat(paAward);
		if (awardPay != null && !"".equals(awardPay))
			total += Float.parseFloat(awardPay);
		if (stPay != null && !"".equals(stPay))
			total += Float.parseFloat(stPay);
		if (secrecyPay != null && !"".equals(secrecyPay))
			total += Float.parseFloat(secrecyPay);
		if (safetyAward != null && !"".equals(safetyAward))
			total += Float.parseFloat(safetyAward);
		if (expenseAllowances != null && !"".equals(expenseAllowances))
			total += Float.parseFloat(expenseAllowances);
		if (discountedCost != null && !"".equals(discountedCost))
			total += Float.parseFloat(discountedCost);
		if (premium != null && !"".equals(premium))
			total += Float.parseFloat(premium);
		if (alimony != null && !"".equals(alimony))
			total += Float.parseFloat(alimony);
		
		if (montasks != null && !"".equals(montasks))
			total += Float.parseFloat(montasks);
		if (goaltasks != null && !"".equals(goaltasks))
			total += Float.parseFloat(goaltasks);
		if (pietypay != null && !"".equals(pietypay))
			total += Float.parseFloat(pietypay);
		if (campaignpay != null && !"".equals(campaignpay))
			total += Float.parseFloat(campaignpay);
		if (telecompay != null && !"".equals(telecompay))
			total += Float.parseFloat(telecompay);
		if (pkpay != null && !"".equals(pkpay))
			total += Float.parseFloat(pkpay);
		if (kkqnpay != null && !"".equals(kkqnpay))
			total += Float.parseFloat(kkqnpay);
		if (living != null && !"".equals(living))
			total += Float.parseFloat(living);
		if (unpremium != null && !"".equals(unpremium))
			total += Float.parseFloat(unpremium);
		return Float.toString(total);
	}

	
	
	
	public String getUnpremium() {
		return unpremium;
	}
	public void setUnpremium(String unpremium) {
		this.unpremium = unpremium;
	}
	public String getLiving() {
		return living;
	}
	public void setLiving(String living) {
		this.living = living;
	}
	public String getMontasks() {
		return montasks;
	}
	public void setMontasks(String montasks) {
		this.montasks = montasks;
	}
	public String getGoaltasks() {
		return goaltasks;
	}
	public void setGoaltasks(String goaltasks) {
		this.goaltasks = goaltasks;
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
	public String getKkqnpay() {
		return kkqnpay;
	}
	public void setKkqnpay(String kkqnpay) {
		this.kkqnpay = kkqnpay;
	}
	public Date getImpotdate() {
		return impotdate;
	}
	public void setImpotdate(Date impotdate) {
		this.impotdate = impotdate;
	}
	public String getPayScalekey() {
		return payScalekey;
	}

	public void setPayScalekey(String payScalekey) {
		this.payScalekey = payScalekey;
	}

	public String getPayScaleID() {
		return payScaleID;
	}

	public void setPayScaleID(String payScaleID) {
		this.payScaleID = payScaleID;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getBasePay() {
		return basePay;
	}

	public void setBasePay(String basePay) {
		this.basePay = basePay;
	}

	public String getWorkWeek() {
		return workWeek;
	}

	public void setWorkWeek(String workWeek) {
		this.workWeek = workWeek;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getWorkNight() {
		return workNight;
	}

	public void setWorkNight(String workNight) {
		this.workNight = workNight;
	}

	public String getWorkHolidays() {
		return workHolidays;
	}

	public void setWorkHolidays(String workHolidays) {
		this.workHolidays = workHolidays;
	}

	public String getPushMoney() {
		return pushMoney;
	}

	public void setPushMoney(String pushMoney) {
		this.pushMoney = pushMoney;
	}

	public String getTimingMoney() {
		return timingMoney;
	}

	public void setTimingMoney(String timingMoney) {
		this.timingMoney = timingMoney;
	}

	public String getPiecerate() {
		return piecerate;
	}

	public void setPiecerate(String piecerate) {
		this.piecerate = piecerate;
	}

	public String getPaAward() {
		return paAward;
	}

	public void setPaAward(String paAward) {
		this.paAward = paAward;
	}
	/**
	 * 特殊人才工资
	 * @return
	 */
	public String getStPay() {
		return stPay;
	}
	/**
	 * 特殊人才工资
	 * @param stPay
	 */
	public void setStPay(String stPay) {
		this.stPay = stPay;
	}
	/**
	 * 保密工资
	 * @return
	 */
	public String getSecrecyPay() {
		return secrecyPay;
	}
	/**
	 * 保密工资
	 * @param secrecyPay
	 */
	public void setSecrecyPay(String secrecyPay) {
		this.secrecyPay = secrecyPay;
	}

	public String getSafetyAward() {
		return safetyAward;
	}

	public void setSafetyAward(String safetyAward) {
		this.safetyAward = safetyAward;
	}

	public String getExpenseAllowances() {
		return expenseAllowances;
	}

	public void setExpenseAllowances(String expenseAllowances) {
		this.expenseAllowances = expenseAllowances;
	}

	public String getDiscountedCost() {
		return discountedCost;
	}

	public void setDiscountedCost(String discountedCost) {
		this.discountedCost = discountedCost;
	}

	public String getPremium() {
		return premium;
	}

	public void setPremium(String premium) {
		this.premium = premium;
	}

	public String getAlimony() {
		return alimony;
	}

	public void setAlimony(String alimony) {
		this.alimony = alimony;
	}

	public String getPiTax() {
		return piTax;
	}

	public void setPiTax(String piTax) {
		this.piTax = piTax;
	}

	public String getPositionPay() {
		return positionPay;
	}

	public void setPositionPay(String positionPay) {
		this.positionPay = positionPay;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 奖励工资
	 * @return
	 */
	public String getAwardPay() {
		return awardPay;
	}
	/**
	 * 奖励工资
	 * @param awardPay
	 */
	public void setAwardPay(String awardPay) {
		this.awardPay = awardPay;
	}

}
