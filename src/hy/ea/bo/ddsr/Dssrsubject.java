package hy.ea.bo.ddsr;

import hy.plat.bo.BaseBean;

import java.util.HashSet;
import java.util.Set;

/**
 * Dssrsubject entity. @author MyEclipse Persistence Tools
 */

public class Dssrsubject implements BaseBean, java.io.Serializable {

	// Fields

	private String subjKey;
	private String subjCompanyid;
	private Byte subjType;
	private String subjContent;
	private String subjNote;
	private Set reDdsrcoachDssrsubjects = new HashSet(0);
	private Set reDssrstudentDssrsubjects = new HashSet(0);

	// Constructors

	/** default constructor */
	public Dssrsubject() {
	}

	/** full constructor */
	public Dssrsubject(String subjCompanyid, Byte subjType, String subjContent,
			String subjNote, Set reDdsrcoachDssrsubjects,
			Set reDssrstudentDssrsubjects) {
		this.subjCompanyid = subjCompanyid;
		this.subjType = subjType;
		this.subjContent = subjContent;
		this.subjNote = subjNote;
		this.reDdsrcoachDssrsubjects = reDdsrcoachDssrsubjects;
		this.reDssrstudentDssrsubjects = reDssrstudentDssrsubjects;
	}

	// Property accessors

	public String getSubjKey() {
		return this.subjKey;
	}

	public void setSubjKey(String subjKey) {
		this.subjKey = subjKey;
	}

	public String getSubjCompanyid() {
		return this.subjCompanyid;
	}

	public void setSubjCompanyid(String subjCompanyid) {
		this.subjCompanyid = subjCompanyid;
	}

	public Byte getSubjType() {
		return this.subjType;
	}

	public void setSubjType(Byte subjType) {
		this.subjType = subjType;
	}

	public String getSubjContent() {
		return this.subjContent;
	}

	public void setSubjContent(String subjContent) {
		this.subjContent = subjContent;
	}

	public String getSubjNote() {
		return this.subjNote;
	}

	public void setSubjNote(String subjNote) {
		this.subjNote = subjNote;
	}

	public Set getReDdsrcoachDssrsubjects() {
		return this.reDdsrcoachDssrsubjects;
	}

	public void setReDdsrcoachDssrsubjects(Set reDdsrcoachDssrsubjects) {
		this.reDdsrcoachDssrsubjects = reDdsrcoachDssrsubjects;
	}

	public Set getReDssrstudentDssrsubjects() {
		return this.reDssrstudentDssrsubjects;
	}

	public void setReDssrstudentDssrsubjects(Set reDssrstudentDssrsubjects) {
		this.reDssrstudentDssrsubjects = reDssrstudentDssrsubjects;
	}

}