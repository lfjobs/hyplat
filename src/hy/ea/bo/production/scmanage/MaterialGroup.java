package hy.ea.bo.production.scmanage;



import java.util.Date;

import hy.plat.bo.BaseBean;


/**
 * 
 * 素材分组，暂时用着，以后改用生产的表结构
 * 
 * @author mz
 *
 */
public class MaterialGroup implements BaseBean {

	private String mgkey;
	private String mgId;
	private String companyID;//公司ID
	private String groupname;//分组名称
	private String groupdesc;//分组描述
	private String groupcover;//分组封面，如没上传文件，设置个默认图片
	private String fileType;//存放的文件类型 00:图片 01：视频 02：音频
	private Date createDate;//创建日期
	private Date updateDate;//更新日期包括删除操作
	private String state;//00：初始；01：09:已删除
	private String createStaffID;//创建人ID
	private String updateStaffID;//更新人ID，包括删除操作
	private String filenum;
	
	public String getMgkey() {
		return mgkey;
	}
	public void setMgkey(String mgkey) {
		this.mgkey = mgkey;
	}
	public String getMgId() {
		return mgId;
	}
	public void setMgId(String mgId) {
		this.mgId = mgId;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getGroupdesc() {
		return groupdesc;
	}
	public void setGroupdesc(String groupdesc) {
		this.groupdesc = groupdesc;
	}
	public String getGroupcover() {
		return groupcover;
	}
	public void setGroupcover(String groupcover) {
		this.groupcover = groupcover;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateStaffID() {
		return createStaffID;
	}
	public void setCreateStaffID(String createStaffID) {
		this.createStaffID = createStaffID;
	}
	public String getUpdateStaffID() {
		return updateStaffID;
	}
	public void setUpdateStaffID(String updateStaffID) {
		this.updateStaffID = updateStaffID;
	}
	public String getFilenum() {
		return filenum;
	}
	public void setFilenum(String filenum) {
		this.filenum = filenum;
	}
	
    
    
    
}
