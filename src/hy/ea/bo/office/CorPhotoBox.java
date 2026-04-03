package hy.ea.bo.office;
import hy.plat.bo.BaseBean;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件管理
 * @author Administrator
 *
 */
public class CorPhotoBox implements BaseBean{
	private String key;
	private String photoBoxID;
    private String organizationID;
	private String companyID;
	private String companyName;
	private String photoBoxCode;				//编码
	private String photoBoxName;				//名称
	private String pbnShort;
	private String photoBoxDepict;				//题片主题描述
	private Date createTime;				//上传时间
	private String creatorID;
	private String creatorName;
	private int photoNumber;
	private String remark;//备注
	private String coverUrl;
	private String sortType;
	private Set<CorPhoto> corPhotos=new HashSet<CorPhoto>();
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPhotoBoxID() {
		return photoBoxID;
	}
	public void setPhotoBoxID(String photoBoxID) {
		this.photoBoxID = photoBoxID;
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
	public String getPhotoBoxCode() {
		return photoBoxCode;
	}
	public void setPhotoBoxCode(String photoBoxCode) {
		this.photoBoxCode = photoBoxCode;
	}
	public String getPhotoBoxName() {
		return photoBoxName;
	}
	public void setPhotoBoxName(String photoBoxName) {
		this.photoBoxName = photoBoxName;
	}
	public String getPhotoBoxDepict() {
		return photoBoxDepict;
	}
	public void setPhotoBoxDepict(String photoBoxDepict) {
		this.photoBoxDepict = photoBoxDepict;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date uploadTime) {
		this.createTime = uploadTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Set<CorPhoto> getCorPhotos() {
		return corPhotos;
	}
	public void setCorPhotos(Set<CorPhoto> corPhotos) {
		this.corPhotos = corPhotos;
	}
	public int getPhotoNumber() {
		return photoNumber;
	}
	public void setPhotoNumber(int photoNumber) {
		this.photoNumber = photoNumber;
	}
	public String getCreatorID() {
		return creatorID;
	}
	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public String getPbnShort() {
		return pbnShort;
	}
	public void setPbnShort(String pbnShort) {
		this.pbnShort = pbnShort;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	
	

	
}
