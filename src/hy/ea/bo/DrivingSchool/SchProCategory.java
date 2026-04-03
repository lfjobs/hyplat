package hy.ea.bo.DrivingSchool;

import hy.plat.bo.BaseBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/12.
 */
public class SchProCategory implements BaseBean,Serializable {
    private String categoryKey;
    private String categoryId;
    private String categoryName;
    private String companyId;
    private String status;//1-驾校学车产品 2 -驾校其他辅助产品

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
