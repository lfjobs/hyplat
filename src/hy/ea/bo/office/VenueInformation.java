package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;


/*
 * 场地信息
 * */
public class VenueInformation implements BaseBean{
	
	private String sitekey;
	private String siteId;//主键
	private String siteNumber;//场地编号
	private String companyId;//所属公司主键
	private String siteName;//场地名称
	private Date siteDate;//创建时间
	private String ItsLocation;//所属地点
	private String staffId;//场地负责人id
	private String status;//00:正常,01:删除
	private String siteArea;//总面积
	private Integer fieldCapacity;//容纳数量
    private String eastLongitude;//东经
    private String northLatitude;//北纬

	private String isAudit;// 默认开启00 或者空  01关闭

	private String siteType;//场地类型，1停车场，2人脸识别

	
	
	

    public String getSitekey() {
        return sitekey;
    }
    public void setSitekey(String sitekey) {
        this.sitekey = sitekey;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public Date getSiteDate() {
		return siteDate;
	}
	public void setSiteDate(Date siteDate) {
		this.siteDate = siteDate;
	}
	public String getItsLocation() {
		return ItsLocation;
	}
	public void setItsLocation(String itsLocation) {
		ItsLocation = itsLocation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(String siteNumber) {
		this.siteNumber = siteNumber;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getSiteArea() {
		return siteArea;
	}
	public void setSiteArea(String siteArea) {
		this.siteArea = siteArea;
	}
	public Integer getFieldCapacity() {
		return fieldCapacity;
	}
	public void setFieldCapacity(Integer fieldCapacity) {
		this.fieldCapacity = fieldCapacity;
	}

    public String getEastLongitude() {
        return eastLongitude;
    }

    public void setEastLongitude(String eastLongitude) {
        this.eastLongitude = eastLongitude;
    }

    public String getNorthLatitude() {
        return northLatitude;
    }

    public void setNorthLatitude(String northLatitude) {
        this.northLatitude = northLatitude;
    }

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public String getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}
}
