package hy.ea.bo.human.huresume;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Dtresume entity. @author MyEclipse Persistence Tools
 */

public class Resume implements BaseBean ,java.io.Serializable{

	// Fields

	private String resumekey;
	private String companyid;
	private String cname;		//姓名
	private String communicate; //联系方式
	private String sex;			//性别
	private Date birthday;		//出生日期
	private String degree;		//最高学历
	private String nation;		//民族
	private String politics;	//政治面貌
	private String height;		//身高
	private String weight;		//体重
	private String health;		//健康状况
	private String nativeplace;	//籍贯	
	private String residenceplace;	//现居住地
	private String selfassessment;	//自我评价
	private String jobobjective;	//应聘职位
	private Date cdate;				//创建时间
	private String source;			//信息来源	
	private String identity;			//身份证	

	public String getResumekey() {
		return this.resumekey;
	}

	public void setResumekey(String resumekey) {
		this.resumekey = resumekey;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCommunicate() {
		return this.communicate;
	}

	public void setCommunicate(String communicate) {
		this.communicate = communicate;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getPolitics() {
		return this.politics;
	}

	public void setPolitics(String politics) {
		this.politics = politics;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHealth() {
		return this.health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getNativeplace() {
		return this.nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}

	public String getResidenceplace() {
		return this.residenceplace;
	}

	public void setResidenceplace(String residenceplace) {
		this.residenceplace = residenceplace;
	}

	public String getSelfassessment() {
		return this.selfassessment;
	}

	public void setSelfassessment(String selfassessment) {
		this.selfassessment = selfassessment;
	}

	public String getJobobjective() {
		return this.jobobjective;
	}

	public void setJobobjective(String jobobjective) {
		this.jobobjective = jobobjective;
	}

	public Date getCdate() {
		return this.cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}


}