package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 预算招标的合格率
 * 陈婷
 */
public class cashTime implements BaseBean,java.io.Serializable{
	private String cashKey;        		//主键
	private String cashID;         		//业务主键
	private String companyID;				//公司ID
	private String companyName;				//公司名称
	private Date stime;					//起时间
	private String num;					//预算收入金额
	public String getCashKey() {
		return cashKey;
	}
	public void setCashKey(String cashKey) {
		this.cashKey = cashKey;
	}
	public String getCashID() {
		return cashID;
	}
	public void setCashID(String cashID) {
		this.cashID = cashID;
	}
	public Date getStime() {
		return stime;
	}
	public void setStime(Date stime) {
		this.stime = stime;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
