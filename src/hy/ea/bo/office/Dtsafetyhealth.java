package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * 车辆安全卫生信息表
 */

public class Dtsafetyhealth implements BaseBean,ExcelBean {

	// Fields

	private String safetykey;
	private String safetyid;
	private String companyid;
	private String carid;
	private String deppostid;  
	private String organizationid; //部门
	private String adddate;  //添加时间
	private String staffid; //责任人
	private String countrewards;  // 总计奖励分
	private String countpenalty; // 总计处罚分
	private String totleScore;   //总得分
	private String remark;  
	private String createpeople; //  创建人
	private String createtime; // 创建时间
	private String lastupdatatime; //  最后修改时间
	private String lastupname; // 最后修改人

	public static String[] columnHeadings() {
		String[] titles = { "序号", "检查时间","部门","责任人","总奖励分","总扣分", "总计得分" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] titles = { adddate,organizationid,staffid,countrewards,countpenalty,totleScore };
		return titles;
	}
	// Constructors

	/** default constructor */
	public Dtsafetyhealth() {
		
	}

	/** full constructor */
	public Dtsafetyhealth(String safetyid, String companyid,
			String organizationid, String adddate, String staffid,
			String countrewards, String countpenalty, String createpeople,
			String createtime, String lastupdatatime, String lastupname) {
		this.safetyid = safetyid;
		this.companyid = companyid;
		this.organizationid = organizationid;
		this.adddate = adddate;
		this.staffid = staffid;
		this.countrewards = countrewards;
		this.countpenalty = countpenalty;
		this.createpeople = createpeople;
		this.createtime = createtime;
		this.lastupdatatime = lastupdatatime;
		this.lastupname = lastupname;
	}

	// Property accessors

	public String getSafetykey() {
		return this.safetykey;
	}

	public void setSafetykey(String safetykey) {
		this.safetykey = safetykey;
	}

	public String getSafetyid() {
		return this.safetyid;
	}

	public void setSafetyid(String safetyid) {
		this.safetyid = safetyid;
	}

	public String getDeppostid() {
		return deppostid;
	}

	public void setDeppostid(String deppostid) {
		this.deppostid = deppostid;
	}

	public void setCarid(String carid) {
		this.carid = carid;
	}

	public String getCarid() {
		return carid;
	}


	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getAdddate() {
		return this.adddate;
	}

	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getCountrewards() {
		return this.countrewards;
	}

	public void setCountrewards(String countrewards) {
		this.countrewards = countrewards;
	}

	public String getCountpenalty() {
		return this.countpenalty;
	}

	public void setCountpenalty(String countpenalty) {
		this.countpenalty = countpenalty;
	}
	
	public String getCreatepeople() {
		return this.createpeople;
	}

	public void setCreatepeople(String createpeople) {
		this.createpeople = createpeople;
	}

	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getLastupdatatime() {
		return this.lastupdatatime;
	}

	public void setLastupdatatime(String lastupdatatime) {
		this.lastupdatatime = lastupdatatime;
	}

	public String getLastupname() {
		return this.lastupname;
	}

	public void setLastupname(String lastupname) {
		this.lastupname = lastupname;
	}
	

	public String getTotleScore() {
		return totleScore;
	}

	public void setTotleScore(String totleScore) {
		this.totleScore = totleScore;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}