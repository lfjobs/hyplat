/**
 * COIntermediaryResearch中介调查
 */
package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * YJG
 * 人事-中介调查
 * @author Administrator
 */
public class COIntermediaryResearch implements BaseBean,ExcelBean,java.io.Serializable{
 public String intermediaryResearchKey;
 public String intermediaryResearchID;
 public String companyID;
 public String organizationID;
 //单位名称
 public String companyName;
 //单位地点
 public String companyAddress;
 public String address;
 //招聘岗位
 public String invitePost;
 //上班时间
 public String StartworkDate;
 //下班时间
 public String offdutyDate;
 //主要人才
 public String mainTalents;
 //备注
 public String remark;
public String getIntermediaryResearchKey() {
	return intermediaryResearchKey;
}
public void setIntermediaryResearchKey(String intermediaryResearchKey) {
	this.intermediaryResearchKey = intermediaryResearchKey;
}
public String getIntermediaryResearchID() {
	return intermediaryResearchID;
}
public void setIntermediaryResearchID(String intermediaryResearchID) {
	this.intermediaryResearchID = intermediaryResearchID;
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
public String getCompanyName() {
	return companyName;
}
public void setCompanyName(String companyName) {
	this.companyName = companyName;
}
public String getCompanyAddress() {
	return companyAddress;
}
public void setCompanyAddress(String companyAddress) {
	this.companyAddress = companyAddress;
}

public String getInvitePost() {
	return invitePost;
}
public void setInvitePost(String invitePost) {
	this.invitePost = invitePost;
}

public String getMainTalents() {
	return mainTalents;
}
public void setMainTalents(String mainTalents) {
	this.mainTalents = mainTalents;
}

public String getStartworkDate() {
	return StartworkDate;
}
public void setStartworkDate(String startworkDate) {
	StartworkDate = startworkDate;
}
public String getOffdutyDate() {
	return offdutyDate;
}
public void setOffdutyDate(String offdutyDate) {
	this.offdutyDate = offdutyDate;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}

public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
/**
 * 不了不写十次同样的代码!!!!!!!!!!!!!!!!
 */
public static String[] columnHeadings() {
	String[] titles = { "序号", "单位名称", "单位地点", "招聘岗位", "上班时间", "下班时间", "主要人才",
			"备注"};
	return titles;
}
public String[] properties() {
	String[] properties = { companyName, companyAddress, invitePost,StartworkDate,offdutyDate,
			mainTalents, remark};
	return properties;
}
}
