/**
 * Company Organization
 */
package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 组织机构
 * @author zgzg
 *
 */
public class COrganization implements BaseBean,java.io.Serializable{
	private String organizationKey;
	private String organizationID;
	private String companyID;

	private int    organizationNumber;//排序机构树,显示序号
	private String organizationPID;//上级部门ID 

	private String organizationPhone;
	private String organizationUrl;//负责内容
	private String organizationDesc; //职责
	
	private String ocode;                 //下属机构编号
	private String organizationName;      //单位名称
    private String opostCode;             //岗位编号
    private String opostName;             //岗位名称
    private String odutiesID;             //职务编号
	private String odutiesName;           //职务名称
    private String opostRequirements;     //岗位要求
    private String ojobLocation;          //工作地点
	private String organizationManager;   //负责人
    //private String omanagement;           //管理范围
    //private String operation;             //操作
	private String photoUrl;          	//负责工作范围图
	private String orgUrl;			//岗位图
	private String isWfj;			//是否微分金店
	private String storageWFJ;      //表示该部门是否用来存储微分金店  是：00  否：01 
	/**
	 * 00正常 98删除 
	 */
	private String Status;
	private Date   organizationCreateDate;
    /**
     * 层级
     * @return
     */
    private Integer organizationLevel;
	private List<COrganization> children=new ArrayList<COrganization>();//二维树组转树形
	
	public String getOrganizationUrl() {
		return organizationUrl;
	}
	public void setOrganizationUrl(String organizationUrl) {
		this.organizationUrl = organizationUrl;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getOrganizationManager() {
		return organizationManager;
	}
	public void setOrganizationManager(String organizationManager) {
		this.organizationManager = organizationManager;
	}
	public String getOrganizationPhone() {
		return organizationPhone;
	}
	public void setOrganizationPhone(String organizationPhone) {
		this.organizationPhone = organizationPhone;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getOrganizationPID() {
		return organizationPID;
	}
	public void setOrganizationPID(String organizationPID) {
		this.organizationPID = organizationPID;
	}
	public String getOrganizationDesc() {
		return organizationDesc;
	}
	public void setOrganizationDesc(String organizationDesc) {
		this.organizationDesc = organizationDesc;
	}
	public Date getOrganizationCreateDate() {
		return organizationCreateDate;
	}
	public void setOrganizationCreateDate(Date organizationCreateDate) {
		this.organizationCreateDate = organizationCreateDate;
	}
	public String getOrganizationKey() {
		return organizationKey;
	}
	public void setOrganizationKey(String organizationKey) {
		this.organizationKey = organizationKey;
	}
	public int getOrganizationNumber() {
		return organizationNumber;
	}
	public void setOrganizationNumber(int organizationNumber) {
		this.organizationNumber = organizationNumber;
	}
	public String getOcode() {
		return ocode;
	}
	public void setOcode(String ocode) {
		this.ocode = ocode;
	}
	public String getOpostCode() {
		return opostCode;
	}
	public void setOpostCode(String opostCode) {
		this.opostCode = opostCode;
	}
	public String getOpostName() {
		return opostName;
	}
	public void setOpostName(String opostName) {
		this.opostName = opostName;
	}
	public String getOdutiesName() {
		return odutiesName;
	}
	public void setOdutiesName(String odutiesName) {
		this.odutiesName = odutiesName;
	}
	public String getOpostRequirements() {
		return opostRequirements;
	}
	public void setOpostRequirements(String opostRequirements) {
		this.opostRequirements = opostRequirements;
	}
	public String getOjobLocation() {
		return ojobLocation;
	}
	public void setOjobLocation(String ojobLocation) {
		this.ojobLocation = ojobLocation;
	}
	public String getOdutiesID() {
		return odutiesID;
	}
	public void setOdutiesID(String odutiesID) {
		this.odutiesID = odutiesID;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getOrgUrl() {
		return orgUrl;
	}
	public void setOrgUrl(String orgUrl) {
		this.orgUrl = orgUrl;
	}
	public List<COrganization> getChildren() {
		return children;
	}
	public void setChildren(List<COrganization> children) {
		this.children = children;
	}
	public String getIsWfj() {
		return isWfj;
	}
	public void setIsWfj(String isWfj) {
		this.isWfj = isWfj;
	}
	public String getStorageWFJ() {
		return storageWFJ;
	}
	public void setStorageWFJ(String storageWFJ) {
		this.storageWFJ = storageWFJ;
	}

    public Integer getOrganizationLevel() {
        return organizationLevel;
    }

    public void setOrganizationLevel(Integer organizationLevel) {
        this.organizationLevel = organizationLevel;
    }
}
