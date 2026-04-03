package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * 企业功臣管理
 *
 */
public class EnterpriseMeritorious implements BaseBean,ExcelBean ,java.io.Serializable {
	private String meritoriouID;
	private String meritoriouKey;
	private String companyID;
	private String organizationID;
	private String staffName;//姓名
	private String post;//职务
	private String rewardName;//获奖名称
	private String rewardContent;//获奖内容
	private String allowance;//津贴
	private String rewardYear;//年度
	private String picture;//图片
	private String remark;//备注
	
	public static String[] columnHeadings() {
		String[] titles = {"序号" , "姓名", "职务","获奖名称","获奖内容","津贴","获奖年度",""};
		return titles;
	}	
	@Override
	public String[] properties() {
		String[]  properties ={staffName,post ,rewardName,rewardContent,allowance,rewardYear};
		return properties;
	}
	
	public String getMeritoriouID() {
		return meritoriouID;
	}
	public void setMeritoriouID(String meritoriouID) {
		this.meritoriouID = meritoriouID;
	}
	public String getMeritoriouKey() {
		return meritoriouKey;
	}
	public void setMeritoriouKey(String meritoriouKey) {
		this.meritoriouKey = meritoriouKey;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getRewardName() {
		return rewardName;
	}
	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}
	public String getRewardContent() {
		return rewardContent;
	}
	public void setRewardContent(String rewardContent) {
		this.rewardContent = rewardContent;
	}
	public String getAllowance() {
		return allowance;
	}
	public void setAllowance(String allowance) {
		this.allowance = allowance;
	}
	public String getRewardYear() {
		return rewardYear;
	}
	public void setRewardYear(String rewardYear) {
		this.rewardYear = rewardYear;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
