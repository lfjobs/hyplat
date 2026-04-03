package hy.ea.bo.human.salary.vo;


import java.io.Serializable;

public class WageCofiVO implements Serializable{
	private String wageCofKey;
	private String wageCofId;
	private String wageCofSortSn;
	private String cofDate;//设定时间
	private String codeValue;
	private String codeID;
	private String norms;
	private String unit;
	private String price;
	private String nums;
	private String moneys;//数量* 单价的 
	private String wcAname;
	private String addDate;
	private String wcUname;
	private String updateDate;
	private String wageCofState;//0：正常使用 9：停用
	private String wageState; //基本1-013411、职务2-013412、考评3-013413、考勤4-013414、奖惩5-013417
	private String companyId;
	private String groupCompanySn;
	private String addsubState;   //奖0  惩1
	private String jicha;//判断级差显示; 0不显示1显示
	private String wcXslb;
	private String wcTcxs;
	public String getWageCofKey() {
		return wageCofKey;
	}
	public void setWageCofKey(String wageCofKey) {
		this.wageCofKey = wageCofKey;
	}
	public String getWageCofId() {
		return wageCofId;
	}
	public void setWageCofId(String wageCofId) {
		this.wageCofId = wageCofId;
	}
	public String getWageCofSortSn() {
		return wageCofSortSn;
	}
	public void setWageCofSortSn(String wageCofSortSn) {
		this.wageCofSortSn = wageCofSortSn;
	}
	public String getCodeValue() {
		return codeValue;
	}
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	public String getCodeID() {
		return codeID;
	}
	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}
	public String getNorms() {
		return norms;
	}
	public void setNorms(String norms) {
		this.norms = norms;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getNums() {
		return nums;
	}
	public void setNums(String nums) {
		this.nums = nums;
	}
	public String getMoneys() {
		return moneys;
	}
	public void setMoneys(String moneys) {
		this.moneys = moneys;
	}
	public String getWcAname() {
		return wcAname;
	}
	public void setWcAname(String wcAname) {
		this.wcAname = wcAname;
	}
	public String getWcUname() {
		return wcUname;
	}
	public void setWcUname(String wcUname) {
		this.wcUname = wcUname;
	}
	public String getWageCofState() {
		return wageCofState;
	}
	public void setWageCofState(String wageCofState) {
		this.wageCofState = wageCofState;
	}
	public String getWageState() {
		return wageState;
	}
	public void setWageState(String wageState) {
		this.wageState = wageState;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getGroupCompanySn() {
		return groupCompanySn;
	}
	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}
	public String getAddsubState() {
		return addsubState;
	}
	public void setAddsubState(String addsubState) {
		this.addsubState = addsubState;
	}
	public String getJicha() {
		return jicha;
	}
	public void setJicha(String jicha) {
		this.jicha = jicha;
	}
	public String getCofDate() {
		return cofDate;
	}
	public void setCofDate(String cofDate) {
		this.cofDate = cofDate;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getWcXslb() {
		return wcXslb;
	}
	public void setWcXslb(String wcXslb) {
		this.wcXslb = wcXslb;
	}
	public String getWcTcxs() {
		return wcTcxs;
	}
	public void setWcTcxs(String wcTcxs) {
		this.wcTcxs = wcTcxs;
	}
}
