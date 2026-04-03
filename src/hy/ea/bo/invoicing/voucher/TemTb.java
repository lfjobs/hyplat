package hy.ea.bo.invoicing.voucher;

import java.math.BigDecimal;

import hy.plat.bo.BaseBean;

/**
 * 试算结果表
 * @author lou
 *
 */

public class TemTb implements BaseBean {

	// Fields

	private String workKey;
	private String workId;
	private String comId;
	private String orgId;
	private String subjectsid;
	private String curId;
	private String curName;
	private String subjectscode;
	private String subjectsname;
	private BigDecimal dbAmt;
	private BigDecimal cbAmt;
	private BigDecimal DAmt;
	private BigDecimal CAmt;
	private BigDecimal deAmt;
	private BigDecimal ceAmt;
	private BigDecimal dedAmt;
	private BigDecimal cedAmt;
	private BigDecimal dbdAmt;
	private BigDecimal cbdAmt;
	private BigDecimal ddAmt;
	private BigDecimal cdAmt;
	private String accYm;
	private String sort;

	// Constructors

	/** default constructor */
	public TemTb() {
	}

	/** full constructor */
	public TemTb(String workId, String comId, String orgId, String subjectsid,
			String curId, String curName, String subjectsname,String subjectscode, BigDecimal dbAmt,
			BigDecimal cbAmt, BigDecimal DAmt, BigDecimal CAmt, BigDecimal deAmt, BigDecimal ceAmt,
			BigDecimal dedAmt, BigDecimal cedAmt, BigDecimal dbdAmt, BigDecimal cbdAmt,
			BigDecimal ddAmt, BigDecimal cdAmt, String accYm, String sort) {
		this.workId = workId;
		this.comId = comId;
		this.orgId = orgId;
		this.subjectsid = subjectsid;
		this.curId = curId;
		this.curName = curName;
		this.subjectsname = subjectsname;
		this.subjectscode=subjectscode;
		this.dbAmt = dbAmt;
		this.cbAmt = cbAmt;
		this.DAmt = DAmt;
		this.CAmt = CAmt;
		this.deAmt = deAmt;
		this.ceAmt = ceAmt;
		this.dedAmt = dedAmt;
		this.cedAmt = cedAmt;
		this.dbdAmt = dbdAmt;
		this.cbdAmt = cbdAmt;
		this.ddAmt = ddAmt;
		this.cdAmt = cdAmt;
		this.accYm = accYm;
		this.sort = sort;
	}

	// Property accessors

	public String getWorkKey() {
		return this.workKey;
	}

	public void setWorkKey(String workKey) {
		this.workKey = workKey;
	}

	public String getWorkId() {
		return this.workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getComId() {
		return this.comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getSubjectsid() {
		return this.subjectsid;
	}

	public void setSubjectsid(String subjectsid) {
		this.subjectsid = subjectsid;
	}

	public String getCurId() {
		return this.curId;
	}

	public void setCurId(String curId) {
		this.curId = curId;
	}

	public String getCurName() {
		return this.curName;
	}

	public void setCurName(String curName) {
		this.curName = curName;
	}

	public String getSubjectsname() {
		return this.subjectsname;
	}

	public void setSubjectsname(String subjectsname) {
		this.subjectsname = subjectsname;
	}

	public BigDecimal getDbAmt() {
		return this.dbAmt;
	}

	public void setDbAmt(BigDecimal dbAmt) {
		this.dbAmt = dbAmt;
	}

	public BigDecimal getCbAmt() {
		return this.cbAmt;
	}

	public void setCbAmt(BigDecimal cbAmt) {
		this.cbAmt = cbAmt;
	}

	public BigDecimal getDAmt() {
		return this.DAmt;
	}

	public void setDAmt(BigDecimal DAmt) {
		this.DAmt = DAmt;
	}

	public BigDecimal getCAmt() {
		return this.CAmt;
	}

	public void setCAmt(BigDecimal CAmt) {
		this.CAmt = CAmt;
	}

	public BigDecimal getDeAmt() {
		return this.deAmt;
	}

	public void setDeAmt(BigDecimal deAmt) {
		this.deAmt = deAmt;
	}

	public BigDecimal getCeAmt() {
		return this.ceAmt;
	}

	public void setCeAmt(BigDecimal ceAmt) {
		this.ceAmt = ceAmt;
	}

	public BigDecimal getDedAmt() {
		return this.dedAmt;
	}

	public void setDedAmt(BigDecimal dedAmt) {
		this.dedAmt = dedAmt;
	}

	public BigDecimal getCedAmt() {
		return this.cedAmt;
	}

	public void setCedAmt(BigDecimal cedAmt) {
		this.cedAmt = cedAmt;
	}

	public BigDecimal getDbdAmt() {
		return this.dbdAmt;
	}

	public void setDbdAmt(BigDecimal dbdAmt) {
		this.dbdAmt = dbdAmt;
	}

	public BigDecimal getCbdAmt() {
		return this.cbdAmt;
	}

	public void setCbdAmt(BigDecimal cbdAmt) {
		this.cbdAmt = cbdAmt;
	}

	public BigDecimal getDdAmt() {
		return this.ddAmt;
	}

	public void setDdAmt(BigDecimal ddAmt) {
		this.ddAmt = ddAmt;
	}

	public BigDecimal getCdAmt() {
		return this.cdAmt;
	}

	public void setCdAmt(BigDecimal cdAmt) {
		this.cdAmt = cdAmt;
	}

	public String getAccYm() {
		return this.accYm;
	}

	public void setAccYm(String accYm) {
		this.accYm = accYm;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSubjectscode() {
		return subjectscode;
	}

	public void setSubjectscode(String subjectscode) {
		this.subjectscode = subjectscode;
	}

}