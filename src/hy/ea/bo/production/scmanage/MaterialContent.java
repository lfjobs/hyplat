package hy.ea.bo.production.scmanage;



import java.util.Date;

import hy.plat.bo.BaseBean;


/**
 * 
 * 素材
 * 
 * @author mz
 *
 */
public class MaterialContent implements BaseBean {

	private String mckey;
	private String mcId;
	private String describe;//描述
	private String staffID;//上传人ID
	private String companyID;//公司ID
	private String groupID;//文件分组
	private String fileType;//文件类型
	private String filepath;//文件路径
	private String filecover;//如果是视频，需要有个截图
	private String state;//素材状态
	private String filesize;//文件大小
	private Date createDate;
	private int sorts;//排序
	
	public String getMckey() {
		return mckey;
	}
	public void setMckey(String mckey) {
		this.mckey = mckey;
	}
	public String getMcId() {
		return mcId;
	}
	public void setMcId(String mcId) {
		this.mcId = mcId;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public int getSorts() {
		return sorts;
	}
	public void setSorts(int sorts) {
		this.sorts = sorts;
	}
	public String getFilecover() {
		return filecover;
	}
	public void setFilecover(String filecover) {
		this.filecover = filecover;
	}
	
}
