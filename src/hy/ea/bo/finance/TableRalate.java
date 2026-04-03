package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 表格关联
 * @author lou
 *
 */
public class TableRalate implements java.io.Serializable,BaseBean {
	private String companyID;
	private String trKey;

	private String trID;

	private String type;

	private String tablename;

   private String year;//年份
   private Date dates;
   private int companySeq;//公司序号给公司编个号

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getTrKey() {
		return trKey;
	}

	public void setTrKey(String trKey) {
		this.trKey = trKey;
	}

	public String getTrID() {
		return trID;
	}

	public void setTrID(String trID) {
		this.trID = trID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public Date getDates() {
		return dates;
	}

	public void setDates(Date dates) {
		this.dates = dates;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(int companySeq) {
		this.companySeq = companySeq;
	}
}
