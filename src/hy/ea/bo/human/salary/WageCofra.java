package hy.ea.bo.human.salary;

import hy.plat.bo.BaseBean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * WageCofra entity. @author MyEclipse Persistence Tools
 */

public class WageCofra implements BaseBean,java.io.Serializable{

	// Fields

	private String cofRaKey;
	private String cofRaID;
	private Integer cofSortSn;
	private Date cofRaDate;
	private String crName;
	private String raNum;
	private BigDecimal sumMoney;
	private Long confRaState;
	private String companyID;
	private String groupCompanySn;
	private String cjUname;
	private Date cjAdate;
	private String cjAname;
	private Date cjUdate;
	
	private String cofSortSnT;
	private String sumMoneyT;
	private String yyyyCofDate;
	
	
	public String getCofRaKey() {
		return cofRaKey;
	}
	public void setCofRaKey(String cofRaKey) {
		this.cofRaKey = cofRaKey;
	}
	public String getCofRaID() {
		return cofRaID;
	}
	public void setCofRaID(String cofRaID) {
		this.cofRaID = cofRaID;
	}
	public Integer getCofSortSn() {
		return cofSortSn;
	}
	public void setCofSortSn(Integer cofSortSn) {
		this.cofSortSn = cofSortSn;
	}
	public Date getCofRaDate() {
		return cofRaDate;
	}
	public void setCofRaDate(Date cofRaDate) {
		this.cofRaDate = cofRaDate;
	}
	public String getCrName() {
		return crName;
	}
	public void setCrName(String crName) {
		this.crName = crName;
	}
	public String getRaNum() {
		return raNum;
	}
	public void setRaNum(String raNum) {
		this.raNum = raNum;
	}
	public BigDecimal getSumMoney() {
		return sumMoney;
	}
	public void setSumMoney(BigDecimal sumMoney) {
		this.sumMoney = sumMoney;
	}
	public Long getConfRaState() {
		return confRaState;
	}
	public void setConfRaState(Long confRaState) {
		this.confRaState = confRaState;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getGroupCompanySn() {
		return groupCompanySn;
	}
	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}
	public String getCjUname() {
		return cjUname;
	}
	public void setCjUname(String cjUname) {
		this.cjUname = cjUname;
	}
	public Date getCjAdate() {
		return cjAdate;
	}
	public void setCjAdate(Date cjAdate) {
		this.cjAdate = cjAdate;
	}
	public String getCjAname() {
		return cjAname;
	}
	public void setCjAname(String cjAname) {
		this.cjAname = cjAname;
	}
	public Date getCjUdate() {
		return cjUdate;
	}
	public void setCjUdate(Date cjUdate) {
		this.cjUdate = cjUdate;
	}
	public String getCofSortSnT() {
		return cofSortSnT;
	}
	public void setCofSortSnT(String cofSortSnT) {
		this.cofSortSnT = cofSortSnT;
	}
	public String getSumMoneyT() {
		return sumMoneyT;
	}
	public void setSumMoneyT(String sumMoneyT) {
		this.sumMoneyT = sumMoneyT;
	}
	public String getYyyyCofDate() {
		return yyyyCofDate;
	}
	public void setYyyyCofDate(String yyyyCofDate) {
		this.yyyyCofDate = yyyyCofDate;
	}


}