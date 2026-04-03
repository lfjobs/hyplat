package hy.ea.bo.ddsr;

import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Ddsrreservationrecord entity. @author MyEclipse Persistence Tools
 */

public class Ddsrreservationrecord implements BaseBean,java.io.Serializable {

	// Fields

	private String rereKey;
	private Ddsrcoach ddsrcoach;
	private String rereCompanyid;
	private Date rereAppdate;
	private Short rereClass;
	private String rereStadate;
	private String rereEnddate;
	private Date rereCreatedate;
	private Date rereUpdatedate;
	private Byte rerePeoplesum;
	private Byte rereSubjects;
	private Set reDssrstudentDdsrresrecords = new HashSet(0);

	private Date  searchStaDate;// 查询起时间 非数据库字段
	private Date  searchEndDate;//查询止时间 非数据库字段
	// Constructors
	
	private int ageOfSchool;//教龄
	
	/**
	 * 根据教练发证时间计算教龄
	 * @return
	 */
	public int getAgeOfSchool() {
		try {
			ageOfSchool=DateUtil.compareDate(DateUtil.toStrDateFromUtilDateByFormat(ddsrcoach.getCoacReleasedate(),"yyyy-MM-dd HH:mm:ss"),DateUtil.getCurrentDate(),2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			ageOfSchool=0;
		}
		return ageOfSchool;
	}

	/** default constructor */
	public Ddsrreservationrecord() {
	}

	/** full constructor */
	public Ddsrreservationrecord(Ddsrcoach ddsrcoach, String rereCompanyid,
			Date rereAppdate, Short rereClass, String rereStadate,
			String rereEnddate, Date rereCreatedate, Date rereUpdatedate,
			Byte rerePeoplesum, Byte rereSubjects,
			Set reDssrstudentDdsrresrecords) {
		this.ddsrcoach = ddsrcoach;
		this.rereCompanyid = rereCompanyid;
		this.rereAppdate = rereAppdate;
		this.rereClass = rereClass;
		this.rereStadate = rereStadate;
		this.rereEnddate = rereEnddate;
		this.rereCreatedate = rereCreatedate;
		this.rereUpdatedate = rereUpdatedate;
		this.rerePeoplesum = rerePeoplesum;
		this.rereSubjects = rereSubjects;
		this.reDssrstudentDdsrresrecords = reDssrstudentDdsrresrecords;
	}

	// Property accessors

	public String getRereKey() {
		return this.rereKey;
	}

	public void setRereKey(String rereKey) {
		this.rereKey = rereKey;
	}

	public Ddsrcoach getDdsrcoach() {
		return this.ddsrcoach;
	}

	public void setDdsrcoach(Ddsrcoach ddsrcoach) {
		this.ddsrcoach = ddsrcoach;
	}

	public String getRereCompanyid() {
		return this.rereCompanyid;
	}

	public void setRereCompanyid(String rereCompanyid) {
		this.rereCompanyid = rereCompanyid;
	}

	public Date getRereAppdate() {
		return this.rereAppdate;
	}

	public void setRereAppdate(Date rereAppdate) {
		this.rereAppdate = rereAppdate;
	}

	public Short getRereClass() {
		return this.rereClass;
	}

	public void setRereClass(Short rereClass) {
		this.rereClass = rereClass;
	}

	public String getRereStadate() {
		return this.rereStadate;
	}

	public void setRereStadate(String rereStadate) {
		this.rereStadate = rereStadate;
	}

	public String getRereEnddate() {
		return this.rereEnddate;
	}

	public void setRereEnddate(String rereEnddate) {
		this.rereEnddate = rereEnddate;
	}

	public Date getRereCreatedate() {
		return this.rereCreatedate;
	}

	public void setRereCreatedate(Date rereCreatedate) {
		this.rereCreatedate = rereCreatedate;
	}

	public Date getRereUpdatedate() {
		return this.rereUpdatedate;
	}

	public void setRereUpdatedate(Date rereUpdatedate) {
		this.rereUpdatedate = rereUpdatedate;
	}

	public Byte getRerePeoplesum() {
		return this.rerePeoplesum;
	}

	public void setRerePeoplesum(Byte rerePeoplesum) {
		this.rerePeoplesum = rerePeoplesum;
	}

	public Byte getRereSubjects() {
		return this.rereSubjects;
	}

	public void setRereSubjects(Byte rereSubjects) {
		this.rereSubjects = rereSubjects;
	}

	public Set getReDssrstudentDdsrresrecords() {
		return this.reDssrstudentDdsrresrecords;
	}

	public void setReDssrstudentDdsrresrecords(Set reDssrstudentDdsrresrecords) {
		this.reDssrstudentDdsrresrecords = reDssrstudentDdsrresrecords;
	}

	public Date getSearchStaDate() {
		return searchStaDate;
	}

	public void setSearchStaDate(Date searchStaDate) {
		this.searchStaDate = searchStaDate;
	}

	public Date getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(Date searchEndDate) {
		this.searchEndDate = searchEndDate;
	}
	
}