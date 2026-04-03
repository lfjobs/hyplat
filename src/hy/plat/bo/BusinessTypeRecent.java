package hy.plat.bo;

import java.util.Date;
public class BusinessTypeRecent implements BaseBean {
    private String id;
    private String typeId;
    private String typeName;
    private String staffId;
    private String flag;
    private Date createDate;

    public BusinessTypeRecent() {
    }
    public BusinessTypeRecent(String id, String typeId, String typeName, String staffId, String flag, Date createDate) {
        this.id = id;
        this.typeId = typeId;
        this.typeName = typeName;
        this.staffId = staffId;
        this.flag = flag;
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}