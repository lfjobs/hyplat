package hy.ea.bo.human.salary;

import hy.plat.bo.BaseBean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * WageCofi entity. @author MyEclipse Persistence Tools
 */

public class WageCofi implements BaseBean {

	// Fields

	private String wageCofKey;
	private String wageCofId;
	private Long wageCofSortSn;
	private Date wageCofDate;
	private String codeValue;
	private String codeID;
	private String norms;
	private String unit;
	private BigDecimal price;
	private BigDecimal nums;
	private BigDecimal moneys;
	private String wcAname;
	private Date wcAdate;
	private String wcUname;
	private Date wcUdate;
	private Integer wageCofState;
	private Integer wageState;
	private String companyId;
	private String groupCompanySn;
	private Integer addsubState;
	private Integer jicha;
	private Integer wcXslb;
	private Double wcTcxs;

	// Constructors

	/** default constructor */
	public WageCofi() {
	}

	/** full constructor */
	public WageCofi(String wageCofId, Long wageCofSortSn, Date wageCofDate,
			String codeValue, String codeID, String norms, String unit,
			BigDecimal price, BigDecimal nums, BigDecimal moneys,
			String wcAname, Date wcAdate, String wcUname, Date wcUdate,
			Integer wageCofState, Integer wageState, String companyId,
			String groupCompanySn, Integer addsubState, Integer jicha,
			Integer wcXslb, Double wcTcxs) {
		this.wageCofId = wageCofId;
		this.wageCofSortSn = wageCofSortSn;
		this.wageCofDate = wageCofDate;
		this.codeValue = codeValue;
		this.codeID = codeID;
		this.norms = norms;
		this.unit = unit;
		this.price = price;
		this.nums = nums;
		this.moneys = moneys;
		this.wcAname = wcAname;
		this.wcAdate = wcAdate;
		this.wcUname = wcUname;
		this.wcUdate = wcUdate;
		this.wageCofState = wageCofState;
		this.wageState = wageState;
		this.companyId = companyId;
		this.groupCompanySn = groupCompanySn;
		this.addsubState = addsubState;
		this.jicha = jicha;
		this.wcXslb = wcXslb;
		this.wcTcxs = wcTcxs;
	}

	// Property accessors

	public String getWageCofKey() {
		return this.wageCofKey;
	}

	public void setWageCofKey(String wageCofKey) {
		this.wageCofKey = wageCofKey;
	}

	public String getWageCofId() {
		return this.wageCofId;
	}

	public void setWageCofId(String wageCofId) {
		this.wageCofId = wageCofId;
	}

	public Long getWageCofSortSn() {
		return this.wageCofSortSn;
	}

	public void setWageCofSortSn(Long wageCofSortSn) {
		this.wageCofSortSn = wageCofSortSn;
	}

	public Date getWageCofDate() {
		return this.wageCofDate;
	}

	public void setWageCofDate(Date wageCofDate) {
		this.wageCofDate = wageCofDate;
	}

	public String getCodeValue() {
		return this.codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getCodeID() {
		return this.codeID;
	}

	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}

	public String getNorms() {
		return this.norms;
	}

	public void setNorms(String norms) {
		this.norms = norms;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getNums() {
		return this.nums;
	}

	public void setNums(BigDecimal nums) {
		this.nums = nums;
	}

	public BigDecimal getMoneys() {
		return this.moneys;
	}

	public void setMoneys(BigDecimal moneys) {
		this.moneys = moneys;
	}

	public String getWcAname() {
		return this.wcAname;
	}

	public void setWcAname(String wcAname) {
		this.wcAname = wcAname;
	}

	public Date getWcAdate() {
		return this.wcAdate;
	}

	public void setWcAdate(Date wcAdate) {
		this.wcAdate = wcAdate;
	}

	public String getWcUname() {
		return this.wcUname;
	}

	public void setWcUname(String wcUname) {
		this.wcUname = wcUname;
	}

	public Date getWcUdate() {
		return this.wcUdate;
	}

	public void setWcUdate(Date wcUdate) {
		this.wcUdate = wcUdate;
	}

	public Integer getWageCofState() {
		return this.wageCofState;
	}

	public void setWageCofState(Integer wageCofState) {
		this.wageCofState = wageCofState;
	}

	public Integer getWageState() {
		return this.wageState;
	}

	public void setWageState(Integer wageState) {
		this.wageState = wageState;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getGroupCompanySn() {
		return this.groupCompanySn;
	}

	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}

	public Integer getAddsubState() {
		return this.addsubState;
	}

	public void setAddsubState(Integer addsubState) {
		this.addsubState = addsubState;
	}

	public Integer getJicha() {
		return this.jicha;
	}

	public void setJicha(Integer jicha) {
		this.jicha = jicha;
	}

	public Integer getWcXslb() {
		return this.wcXslb;
	}

	public void setWcXslb(Integer wcXslb) {
		this.wcXslb = wcXslb;
	}

	public Double getWcTcxs() {
		return this.wcTcxs;
	}

	public void setWcTcxs(Double wcTcxs) {
		this.wcTcxs = wcTcxs;
	}

}