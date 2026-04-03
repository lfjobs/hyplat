package hy.ea.bo.ddsr;

import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Ddsrcoach entity. @author MyEclipse Persistence Tools
 */

public class Ddsrcoach implements BaseBean,java.io.Serializable {

	// Fields

	private String coacKey;
	private Staff dtHrStaff;
	private String coacCompanyid;
	private Date coacReleasedate;//驾驶证发证日期
	private Date coacCertificateDate;//教练证发证日期
	private String coacIcnumber;
	private String coacLevel;
	private String coacTeachtype;
	private Byte coacStatus;
	private Byte coacStar;
	private Date coacCreatedate;
	private Date coacUpdatedate;
	private String coacPapersCode;
	private String coacCarNumber;
	private Byte coacSatisfaction;
	private Boolean coacIsrecommend;
	private Boolean coacIsrecommendS;
	private Double coacPrice;//教练价格
	private Set ddsrworktimes = new HashSet(0);
	private Set reDdsrcoachDssrsubjects = new HashSet(0);
	private Set ddsrreservationrecords = new HashSet(0);
	
	private Date  searchStaDate;// 查询起时间 非数据库字段
	private Date  searchEndDate;//查询止时间 非数据库字段

	// Constructors

	/** default constructor */
	public Ddsrcoach() {
	}

	/** minimal constructor */
	public Ddsrcoach(Staff dtHrStaff) {
		this.dtHrStaff = dtHrStaff;
	}

	/** full constructor */
	public Ddsrcoach(Staff dtHrStaff, String coacCompanyid,
			Date coacReleasedate, String coacIcnumber, String coacLevel,
			String coacTeachtype, Byte coacStatus, Byte coacStar,
			Date coacCreatedate, Date coacUpdatedate, Set ddsrworktimes,
			Set reDdsrcoachDssrsubjects, Set ddsrreservationrecords) {
		this.dtHrStaff = dtHrStaff;
		this.coacCompanyid = coacCompanyid;
		this.coacReleasedate = coacReleasedate;
		this.coacIcnumber = coacIcnumber;
		this.coacLevel = coacLevel;
		this.coacTeachtype = coacTeachtype;
		this.coacStatus = coacStatus;
		this.coacStar = coacStar;
		this.coacCreatedate = coacCreatedate;
		this.coacUpdatedate = coacUpdatedate;
		this.ddsrworktimes = ddsrworktimes;
		this.reDdsrcoachDssrsubjects = reDdsrcoachDssrsubjects;
		this.ddsrreservationrecords = ddsrreservationrecords;
	}

	// Property accessors

	public String getCoacKey() {
		return this.coacKey;
	}

	public void setCoacKey(String coacKey) {
		this.coacKey = coacKey;
	}

	public Staff getDtHrStaff() {
		return this.dtHrStaff;
	}

	public void setDtHrStaff(Staff dtHrStaff) {
		this.dtHrStaff = dtHrStaff;
	}

	public String getCoacCompanyid() {
		return this.coacCompanyid;
	}

	public void setCoacCompanyid(String coacCompanyid) {
		this.coacCompanyid = coacCompanyid;
	}

	public Date getCoacReleasedate() {
		return this.coacReleasedate;
	}

	public void setCoacReleasedate(Date coacReleasedate) {
		this.coacReleasedate = coacReleasedate;
	}

	public String getCoacIcnumber() {
		return this.coacIcnumber;
	}

	public void setCoacIcnumber(String coacIcnumber) {
		this.coacIcnumber = coacIcnumber;
	}

	public String getCoacLevel() {
		return this.coacLevel;
	}

	public void setCoacLevel(String coacLevel) {
		this.coacLevel = coacLevel;
	}

	public String getCoacTeachtype() {
		return this.coacTeachtype;
	}

	public void setCoacTeachtype(String coacTeachtype) {
		this.coacTeachtype = coacTeachtype;
	}

	public Byte getCoacStatus() {
		return this.coacStatus;
	}

	public void setCoacStatus(Byte coacStatus) {
		this.coacStatus = coacStatus;
	}

	public Byte getCoacStar() {
		return this.coacStar;
	}

	public void setCoacStar(Byte coacStar) {
		this.coacStar = coacStar;
	}

	public Date getCoacCreatedate() {
		return this.coacCreatedate;
	}

	public void setCoacCreatedate(Date coacCreatedate) {
		this.coacCreatedate = coacCreatedate;
	}

	public Date getCoacUpdatedate() {
		return this.coacUpdatedate;
	}

	public void setCoacUpdatedate(Date coacUpdatedate) {
		this.coacUpdatedate = coacUpdatedate;
	}

	public Set getDdsrworktimes() {
		return this.ddsrworktimes;
	}

	public void setDdsrworktimes(Set ddsrworktimes) {
		this.ddsrworktimes = ddsrworktimes;
	}

	public Set getReDdsrcoachDssrsubjects() {
		return this.reDdsrcoachDssrsubjects;
	}

	public void setReDdsrcoachDssrsubjects(Set reDdsrcoachDssrsubjects) {
		this.reDdsrcoachDssrsubjects = reDdsrcoachDssrsubjects;
	}

	public Set getDdsrreservationrecords() {
		return this.ddsrreservationrecords;
	}

	public void setDdsrreservationrecords(Set ddsrreservationrecords) {
		this.ddsrreservationrecords = ddsrreservationrecords;
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

	public String getCoacPapersCode() {
		return coacPapersCode;
	}

	public void setCoacPapersCode(String coacPapersCode) {
		this.coacPapersCode = coacPapersCode;
	}

	public String getCoacCarNumber() {
		return coacCarNumber;
	}

	public void setCoacCarNumber(String coacCarNumber) {
		this.coacCarNumber = coacCarNumber;
	}

	public Byte getCoacSatisfaction() {
		return coacSatisfaction;
	}

	public void setCoacSatisfaction(Byte coacSatisfaction) {
		this.coacSatisfaction = coacSatisfaction;
	}

	public Boolean getCoacIsrecommend() {
		return coacIsrecommend;
	}

	public void setCoacIsrecommend(Boolean coacIsrecommend) {
		this.coacIsrecommend = coacIsrecommend;
	}

	public Boolean getCoacIsrecommendS() {
		return coacIsrecommendS;
	}

	public void setCoacIsrecommendS(Boolean coacIsrecommendS) {
		this.coacIsrecommendS = coacIsrecommendS;
	}

	public Date getCoacCertificateDate() {
		return coacCertificateDate;
	}

	public void setCoacCertificateDate(Date coacCertificateDate) {
		this.coacCertificateDate = coacCertificateDate;
	}

	public Double getCoacPrice() {
		return coacPrice;
	}

	public void setCoacPrice(Double coacPrice) {
		this.coacPrice = coacPrice;
	}
	
}