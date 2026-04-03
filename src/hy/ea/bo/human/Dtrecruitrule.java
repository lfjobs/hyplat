package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * 招聘规则
 * Dtrecruitrule entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Dtrecruitrule implements BaseBean,ExcelBean,java.io.Serializable{

	// Fields

	private String recruitrulekey;
	private String recruitruleid;
	private String  companyID;
	private String deptpostid;          //岗位
	private String organizationid;      //部门
	private String nownumbers;          //现有人数
	private String addnumbers;          //拟增人数
	private String addreason;           //增加原因
	private String cutnumbers;          //拟减人数
	private String cutreason;           //减员原因
	private String inputnumbers;        //拟录用人数
	private String channels;            //拟招聘渠道
	private String starttime;           //起时间
	private String endtime;             //止时间
	private String staffid;             //责任人
	private String tabdate;             //制表日期
	
	private String inputtime;           //创建时间
	private String endmodifier;         //最后修改人
	private String endmodifytime;       //最后修改时间
	private String inputer;             //创建人

	// Constructors
	public static String[] columnHeadings() {
		String[] titles = {"序号" ,"岗位","部门","责任人","起时间","止时间","现有人数", "拟增人数", "增加原因", "拟减人数",
				 "减员原因", "拟录用人数","拟招聘渠道","制表日期"};
		return titles;
	}
	
	@Override
	public String[] properties() {
		String[] properties = {deptpostid,organizationid,staffid,starttime,endtime,nownumbers,addnumbers,addreason, 
				cutnumbers, cutreason, inputnumbers,channels,tabdate};
		return properties;
	}
	/** default constructor */
	public Dtrecruitrule() {
	}

	/** minimal constructor */
	public Dtrecruitrule(String recruitruleid) {
		this.recruitruleid = recruitruleid;
	}

	/** full constructor */
	public Dtrecruitrule(String recruitruleid, String deptpostid, String companyID, 
			String organizationid, String nownumbers, String addnumbers, 
			String addreason, String cutnumbers, String cutreason, 
			String inputnumbers, String channels, String starttime, 
			String endtime, String staffid, String tabdate,String inputtime, 
			String endmodifier, String endmodifytime, String inputer) {
		this.recruitruleid = recruitruleid;
		this.deptpostid = deptpostid;
		this.companyID = companyID;
		this.organizationid = organizationid;
		this.nownumbers = nownumbers;
		this.addnumbers = addnumbers;
		this.addreason = addreason;
		this.cutnumbers = cutnumbers;
		this.cutreason = cutreason;
		this.inputnumbers = inputnumbers;
		this.channels = channels;
		this.starttime = starttime;
		this.endtime = endtime;
		this.staffid = staffid;
		this.tabdate = tabdate;
		this.inputtime = inputtime;
		this.endmodifier = endmodifier;
		this.endmodifytime = endmodifytime;
		this.inputer = inputer;
	}

	// Property accessors

	public String getRecruitrulekey() {
		return this.recruitrulekey;
	}

	public void setRecruitrulekey(String recruitrulekey) {
		this.recruitrulekey = recruitrulekey;
	}

	public String getRecruitruleid() {
		return this.recruitruleid;
	}

	public void setRecruitruleid(String recruitruleid) {
		this.recruitruleid = recruitruleid;
	}

	public String getDeptpostid() {
		return this.deptpostid;
	}

	public void setDeptpostid(String deptpostid) {
		this.deptpostid = deptpostid;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getNownumbers() {
		return this.nownumbers;
	}

	public void setNownumbers(String nownumbers) {
		this.nownumbers = nownumbers;
	}

	public String getAddnumbers() {
		return this.addnumbers;
	}

	public void setAddnumbers(String addnumbers) {
		this.addnumbers = addnumbers;
	}

	public String getAddreason() {
		return this.addreason;
	}

	public void setAddreason(String addreason) {
		this.addreason = addreason;
	}

	public String getCutnumbers() {
		return this.cutnumbers;
	}

	public void setCutnumbers(String cutnumbers) {
		this.cutnumbers = cutnumbers;
	}

	public String getCutreason() {
		return this.cutreason;
	}

	public void setCutreason(String cutreason) {
		this.cutreason = cutreason;
	}

	public String getInputnumbers() {
		return this.inputnumbers;
	}

	public void setInputnumbers(String inputnumbers) {
		this.inputnumbers = inputnumbers;
	}

	public String getChannels() {
		return this.channels;
	}

	public void setChannels(String channels) {
		this.channels = channels;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getTabdate() {
		return tabdate;
	}

	public void setTabdate(String tabdate) {
		this.tabdate = tabdate;
	}

	public String getInputtime() {
		return this.inputtime;
	}

	public void setInputtime(String inputtime) {
		this.inputtime = inputtime;
	}

	public String getEndmodifier() {
		return this.endmodifier;
	}

	public void setEndmodifier(String endmodifier) {
		this.endmodifier = endmodifier;
	}

	public String getEndmodifytime() {
		return this.endmodifytime;
	}

	public void setEndmodifytime(String endmodifytime) {
		this.endmodifytime = endmodifytime;
	}

	public String getInputer() {
		return this.inputer;
	}

	public void setInputer(String inputer) {
		this.inputer = inputer;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	

}