package hy.ea.bo.DrivingSchool;

import hy.plat.bo.BaseBean;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * 驾培机构信息
 * Created by Administrator on 2017/8/11 0011.
 */

public class TbJpCompany implements BaseBean,Serializable {
    private String tbjpcompanykey ; //主键
    private String tbjpcompanyId;  //id
    private String companyId;  //驾校ID（公司id）
    private String inscode;  //全国统一编码
    private String name;  //培训机构名称
    private String shortname;  //培训机构简称
    private String business;  //营业执照注册号
    private String licnum;  //经营许可证编号
    private Date licetime;  //经营许可日期
    private String address; //地址
    private String creditcode;  //统一社会信用代码
    private String taxregcer;  //税务登记证编号
    private String postcode;  //邮政编码
    private String legal;  //法人代表
    private String contact; //联系人
    private String phone;  //联系电话
    private String busiscope;  //经营范围(下列编码可多选，以英文逗号分隔：A1,A2,A3,B1,B2,C1,C2,C3,C4,C5,D,E,F,M,N,P)
    private String busistatus;  //经营状态（采用1 位数字码，定义如下：1:营业2:停业3:整改4:停业整顿5:歇业6:注销9:其他)
    private String checkStatus;  //审核状态
    private String syncType;  //同步类型（1新增；2修改；3删除）
    private String syncStatus;  //同步状态（0待同步1同步成功2同步失败）
    private String companyLevel; //分类等级（1:一级2:二级3:三级）
    private String economicType;  //经济类型
    private Date busisEndDate;  //经营截止日期
    private String district;  //所属行政区域
    private Date firstIssueDate;  //初次发证日期
    private String legalIdcard;  //法人身份证号
    private String registeredFund;  //注册资金
    private String coachnumber;  //教练员总数
    private String grasupvnum;  //考核员总数
    private String safmngnum;  //安全员总数
    private String tracarnum;  //教练车总数
    private String classroom;  //教室总面积
    private String thclassroom;  //理论教室总面积
    private String praticefield;  //教练场总面积
    private String delFlag;  //删除标志
    private String remark;  //备注
    private Date createdate; //创建时间
    private Date updatedate;  //更新时间
    private String createperson;  //创建人
    private String updateperson;  //更新人
    private String syncXlycStatus;  //同步状态（0待同步1同步成功2同步失败）
    private String syncXlgjStatus;  //同步状态（0待同步1同步成功2同步失败）
    private String pkTbJpCompanyid;  //快车同步的id

    public TbJpCompany() {
    }

    public TbJpCompany(String tbjpcompanykey, String tbjpcompanyId, String companyId, String inscode, String name, String shortname, String business, String licnum, Date licetime, String address, String creditcode, String taxregcer, String postcode, String legal, String contact, String phone, String busiscope, String busistatus, String checkStatus, String syncType, String syncStatus, String companyLevel, String economicType, Date busisEndDate, String district, Date firstIssueDate, String legalIdcard, String registeredFund, String coachnumber, String grasupvnum, String safmngnum, String tracarnum, String classroom, String thclassroom, String praticefield, String delFlag, String remark, Date createdate, Date updatedate, String createperson, String updateperson, String syncXlycStatus, String syncXlgjStatus, String pkTbJpCompanyid) {
        this.tbjpcompanykey = tbjpcompanykey;
        this.tbjpcompanyId = tbjpcompanyId;
        this.companyId = companyId;
        this.inscode = inscode;
        this.name = name;
        this.shortname = shortname;
        this.business = business;
        this.licnum = licnum;
        this.licetime = licetime;
        this.address = address;
        this.creditcode = creditcode;
        this.taxregcer = taxregcer;
        this.postcode = postcode;
        this.legal = legal;
        this.contact = contact;
        this.phone = phone;
        this.busiscope = busiscope;
        this.busistatus = busistatus;
        this.checkStatus = checkStatus;
        this.syncType = syncType;
        this.syncStatus = syncStatus;
        this.companyLevel = companyLevel;
        this.economicType = economicType;
        this.busisEndDate = busisEndDate;
        this.district = district;
        this.firstIssueDate = firstIssueDate;
        this.legalIdcard = legalIdcard;
        this.registeredFund = registeredFund;
        this.coachnumber = coachnumber;
        this.grasupvnum = grasupvnum;
        this.safmngnum = safmngnum;
        this.tracarnum = tracarnum;
        this.classroom = classroom;
        this.thclassroom = thclassroom;
        this.praticefield = praticefield;
        this.delFlag = delFlag;
        this.remark = remark;
        this.createdate = createdate;
        this.updatedate = updatedate;
        this.createperson = createperson;
        this.updateperson = updateperson;
        this.syncXlycStatus = syncXlycStatus;
        this.syncXlgjStatus = syncXlgjStatus;
        this.pkTbJpCompanyid = pkTbJpCompanyid;
    }

    public String getTbjpcompanykey() {
        return tbjpcompanykey;
    }

    public void setTbjpcompanykey(String tbjpcompanykey) {
        this.tbjpcompanykey = tbjpcompanykey;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getInscode() {
        return inscode;
    }

    public void setInscode(String inscode) {
        this.inscode = inscode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getLicnum() {
        return licnum;
    }

    public void setLicnum(String licnum) {
        this.licnum = licnum;
    }

    public Date getLicetime() {
        return licetime;
    }

    public void setLicetime(Date licetime) {
        this.licetime = licetime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreditcode() {
        return creditcode;
    }

    public void setCreditcode(String creditcode) {
        this.creditcode = creditcode;
    }

    public String getTaxregcer() {
        return taxregcer;
    }

    public void setTaxregcer(String taxregcer) {
        this.taxregcer = taxregcer;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusiscope() {
        return busiscope;
    }

    public void setBusiscope(String busiscope) {
        this.busiscope = busiscope;
    }

    public String getBusistatus() {
        return busistatus;
    }

    public void setBusistatus(String busistatus) {
        this.busistatus = busistatus;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getSyncType() {
        return syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getCompanyLevel() {
        return companyLevel;
    }

    public void setCompanyLevel(String companyLevel) {
        this.companyLevel = companyLevel;
    }

    public String getEconomicType() {
        return economicType;
    }

    public void setEconomicType(String economicType) {
        this.economicType = economicType;
    }

    public Date getBusisEndDate() {
        return busisEndDate;
    }

    public void setBusisEndDate(Date busisEndDate) {
        this.busisEndDate = busisEndDate;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Date getFirstIssueDate() {
        return firstIssueDate;
    }

    public void setFirstIssueDate(Date firstIssueDate) {
        this.firstIssueDate = firstIssueDate;
    }

    public String getLegalIdcard() {
        return legalIdcard;
    }

    public void setLegalIdcard(String legalIdcard) {
        this.legalIdcard = legalIdcard;
    }

    public String getRegisteredFund() {
        return registeredFund;
    }

    public void setRegisteredFund(String registeredFund) {
        this.registeredFund = registeredFund;
    }

    public String getCoachnumber() {
        return coachnumber;
    }

    public void setCoachnumber(String coachnumber) {
        this.coachnumber = coachnumber;
    }

    public String getGrasupvnum() {
        return grasupvnum;
    }

    public void setGrasupvnum(String grasupvnum) {
        this.grasupvnum = grasupvnum;
    }

    public String getSafmngnum() {
        return safmngnum;
    }

    public void setSafmngnum(String safmngnum) {
        this.safmngnum = safmngnum;
    }

    public String getTracarnum() {
        return tracarnum;
    }

    public void setTracarnum(String tracarnum) {
        this.tracarnum = tracarnum;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getThclassroom() {
        return thclassroom;
    }

    public void setThclassroom(String thclassroom) {
        this.thclassroom = thclassroom;
    }

    public String getPraticefield() {
        return praticefield;
    }

    public void setPraticefield(String praticefield) {
        this.praticefield = praticefield;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getCreateperson() {
        return createperson;
    }

    public void setCreateperson(String createperson) {
        this.createperson = createperson;
    }

    public String getUpdateperson() {
        return updateperson;
    }

    public void setUpdateperson(String updateperson) {
        this.updateperson = updateperson;
    }

    public String getSyncXlycStatus() {
        return syncXlycStatus;
    }

    public void setSyncXlycStatus(String syncXlycStatus) {
        this.syncXlycStatus = syncXlycStatus;
    }

    public String getSyncXlgjStatus() {
        return syncXlgjStatus;
    }

    public void setSyncXlgjStatus(String syncXlgjStatus) {
        this.syncXlgjStatus = syncXlgjStatus;
    }

    public String getPkTbJpCompanyid() {
        return pkTbJpCompanyid;
    }

    public void setPkTbJpCompanyid(String pkTbJpCompanyid) {
        this.pkTbJpCompanyid = pkTbJpCompanyid;
    }

    public String getTbjpcompanyId() {
        return tbjpcompanyId;
    }

    public void setTbjpcompanyId(String tbjpcompanyId) {
        this.tbjpcompanyId = tbjpcompanyId;
    }
}
