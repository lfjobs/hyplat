package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 考场
 * @author xgb
 *
 */
public class ExaminationRoom implements BaseBean {
	private String erkey;
	private String erId;
    private String erNumber;//考场编号
    private String companyId;//所属公司主键
    private String erName;//考场名称
    private String ItsLocation;//所属地点
    private Date erDate;//创建时间
    private String staffId;//考场负责人id
    private String status;//00:正常,01:删除
    private String reviewerStaffId;//审核人id
    private String eastLongitude;//东经
    private String northLatitude;//北纬
    private String whetherStatus;//是否启用,00:是,01:否

    private Integer siteType;//场地类型0:考场,1:练习场
    private String reservedField1;//预留字段1
    private String reservedField2;//预留字段2
    private String reservedField3;//预留字段2



    public String getErkey() {
        return erkey;
    }

    public void setErkey(String erkey) {
        this.erkey = erkey;
    }

    public String getErId() {
        return erId;
    }

    public void setErId(String erId) {
        this.erId = erId;
    }

    public String getErNumber() {
        return erNumber;
    }

    public void setErNumber(String erNumber) {
        this.erNumber = erNumber;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getErName() {
        return erName;
    }

    public void setErName(String erName) {
        this.erName = erName;
    }

    public Date getErDate() {
        return erDate;
    }

    public void setErDate(Date erDate) {
        this.erDate = erDate;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getReviewerStaffId() {
        return reviewerStaffId;
    }

    public void setReviewerStaffId(String reviewerStaffId) {
        this.reviewerStaffId = reviewerStaffId;
    }

    public String getWhetherStatus() {
        return whetherStatus;
    }

    public void setWhetherStatus(String whetherStatus) {
        this.whetherStatus = whetherStatus;
    }

    public String getItsLocation() {
        return ItsLocation;
    }

    public void setItsLocation(String itsLocation) {
        ItsLocation = itsLocation;
    }

    public Integer getSiteType() {
        return siteType;
    }

    public void setSiteType(Integer siteType) {
        this.siteType = siteType;
    }

    public String getReservedField1() {
        return reservedField1;
    }

    public void setReservedField1(String reservedField1) {
        this.reservedField1 = reservedField1;
    }

    public String getReservedField2() {
        return reservedField2;
    }

    public void setReservedField2(String reservedField2) {
        this.reservedField2 = reservedField2;
    }

    public String getReservedField3() {
        return reservedField3;
    }

    public void setReservedField3(String reservedField3) {
        this.reservedField3 = reservedField3;
    }
}
