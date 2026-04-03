package hy.ea.bo.human;

import java.util.Map;

import hy.plat.bo.BaseBean;

/**
 * 部门岗位
 * @author Administrator
 *
 */
public class DepartmentPost implements BaseBean,java.io.Serializable {
	private String depPostKey;
	private String depPostID;
	private String companyID;
	private String leveloneOrgID;             //组织机构树下一级部门ID
	private String SpecialpostNum;            //专岗人数
	private String omppostNum;					// 兼岗人数
	private String postName;                  //岗位名称
	private String postNum;                   //岗位编号
	private String organizationID;            //所属部门
	private String adminNum;                  //辖员人数
	private String postSureNum;               //岗位定员
	private String postResponsibility;        //岗位职责
	private String responsibilityRequire;     //任职要求
	private String remark;                    //备注
	private String organizationName;          //部门名称

	
	
	private static Map<String, String> cMap;//公司名称
	public static void setCMap(Map<String, String> map) {
		cMap = map;
	}
	public String getCompanyName() {
		String cName="";
		if(null!=cMap)
		{
			cName=cMap.get(companyID);
		}
		return cName;
	}
	
	private static Map<String, String> oMap; //部门名称
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public String getOrgName() {
		String oName="";
		if(null!=oMap)
		{
			oName=oMap.get(organizationID);
		}
		return oName;
	}
	
	
	public String getOmppostNum() {
		return omppostNum;
	}
	public void setOmppostNum(String omppostNum) {
		this.omppostNum = omppostNum;
	}
	public String getDepPostKey() {
		return depPostKey;
	}
	public void setDepPostKey(String depPostKey) {
		this.depPostKey = depPostKey;
	}
	public String getDepPostID() {
		return depPostID;
	}
	
	public String getSpecialpostNum() {
		return SpecialpostNum;
	}
	public void setSpecialpostNum(String specialpostNum) {
		SpecialpostNum = specialpostNum;
	}
	public void setDepPostID(String depPostID) {
		this.depPostID = depPostID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	
	/**
	 * 岗位名称
	 * @return
	 */
	public String getPostName() {
		return postName;
	}
	/**
	 * 岗位名称
	 * @param postName
	 */
	public void setPostName(String postName) {
		this.postName = postName;
	}
	
	/**
	 * 岗位编号
	 * @return
	 */
	public String getPostNum() {
		return postNum;
	}
	/**
	 * 岗位编号
	 * @param postNum
	 */
	public void setPostNum(String postNum) {
		this.postNum = postNum;
	}
	
	/**
	 * 所属部门
	 * @return
	 */
	public String getOrganizationID() {
		return organizationID;
	}
	/**
	 * 所属部门
	 * @param organizationID
	 */
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	
	/**
	 * 辖员人数
	 * @return
	 */
	public String getAdminNum() {
		return adminNum;
	}
	/**
	 * 辖员人数
	 * @param adminNum
	 */
	public void setAdminNum(String adminNum) {
		this.adminNum = adminNum;
	}
	
	/**
	 * 岗位定员
	 * @return
	 */
	public String getPostSureNum() {
		return postSureNum;
	}
	/**
	 * 岗位定员
	 * @param postSureNum
	 */
	public void setPostSureNum(String postSureNum) {
		this.postSureNum = postSureNum;
	}
	
	/**
	 * 岗位职责
	 * @return
	 */
	public String getPostResponsibility() {
		return postResponsibility;
	}
	/**
	 * 岗位职责
	 * @param postResponsibility
	 */
	public void setPostResponsibility(String postResponsibility) {
		this.postResponsibility = postResponsibility;
	}
	
	/**
	 * 任职要求
	 * @return
	 */
	public String getResponsibilityRequire() {
		return responsibilityRequire;
	}
	/**
	 * 任职要求
	 * @param responsibilityRequire
	 */
	public void setResponsibilityRequire(String responsibilityRequire) {
		this.responsibilityRequire = responsibilityRequire;
	}
	
	/**
	 * 备注
	 * @return
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 组织机构树下一级部门ID
	 * @return
	 */
	public String getLeveloneOrgID() {
		return leveloneOrgID;
	}
	/**
	 * 组织机构树下一级部门ID
	 * @param leveloneOrgID
	 */
	public void setLeveloneOrgID(String leveloneOrgID) {
		this.leveloneOrgID = leveloneOrgID;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
}
