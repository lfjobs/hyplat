package hy.ea.bo.DrivingSchool.elyc;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * mz
 * 驾校放假
 */

public class TbElycSchoolRest implements BaseBean,Serializable {
    private String esrKey;
    private  String esrId;
    private String companyId;//驾校ID
    private Date holidayBegin;//驾校开始时间
    private Date holidayEnd;//驾校结束时间
    private String status;//状态:00:未生效,01:已生效
    private Date createdate;//创建时间
    private Date updatedate;//修改时间
    private String createperson;//创建人员id
    private String updateperson;//修改人员id

    public String getEsrKey() {
        return esrKey;
    }

    public void setEsrKey(String esrKey) {
        this.esrKey = esrKey;
    }

    public String getEsrId() {
        return esrId;
    }

    public void setEsrId(String esrId) {
        this.esrId = esrId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Date getHolidayBegin() {
        return holidayBegin;
    }

    public void setHolidayBegin(Date holidayBegin) {
        this.holidayBegin = holidayBegin;
    }

    public Date getHolidayEnd() {
        return holidayEnd;
    }

    public void setHolidayEnd(Date holidayEnd) {
        this.holidayEnd = holidayEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
