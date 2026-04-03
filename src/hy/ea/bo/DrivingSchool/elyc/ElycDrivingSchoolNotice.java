package hy.ea.bo.DrivingSchool.elyc;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * xgb
 * 驾校通知
 */
public class ElycDrivingSchoolNotice implements BaseBean, Serializable {
    private String edsnKey;
    private String edsnId;
    private String companyId;//所属驾校
    private String createperson;//发布人staffid
    private String theme;//主题
    private String content;//内容
    private Date createdate;//创建时间
    private String status;//状态,00:正常,01:删除


    public String getEdsnKey() {
        return edsnKey;
    }

    public void setEdsnKey(String edsnKey) {
        this.edsnKey = edsnKey;
    }

    public String getEdsnId() {
        return edsnId;
    }

    public void setEdsnId(String edsnId) {
        this.edsnId = edsnId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCreateperson() {
        return createperson;
    }

    public void setCreateperson(String createperson) {
        this.createperson = createperson;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
