package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

/**
 * ImportContacts
 *
 * @author zch
 */
public class ImportContacts implements BaseBean {
    //id
    private String id;
    private String staffId;
    //是否分配
    private String isAssign;
    //导入人
    private String importerId;
    //分配给谁
    private String assignTo;
    //手机号
    private String phone;
    //姓名
    private String name;
    //导入时间
    private String importTime;
    //修改时间
    private String updateTime;
    private String isCall;

    public ImportContacts() {
    }

    public ImportContacts(String id, String staffId, String isAssign, String importerId, String assignTo, String phone, String name, String importTime, String updateTime, String isCall) {
        this.id = id;
        this.staffId = staffId;
        this.isAssign = isAssign;
        this.importerId = importerId;
        this.assignTo = assignTo;
        this.phone = phone;
        this.name = name;
        this.importTime = importTime;
        this.updateTime = updateTime;
        this.isCall = isCall;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getIsAssign() {
        return isAssign;
    }

    public void setIsAssign(String isAssign) {
        this.isAssign = isAssign;
    }

    public String getImporterId() {
        return importerId;
    }

    public void setImporterId(String importerId) {
        this.importerId = importerId;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImportTime() {
        return importTime;
    }

    public void setImportTime(String importTime) {
        this.importTime = importTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsCall() {
        return isCall;
    }

    public void setIsCall(String isCall) {
        this.isCall = isCall;
    }
}
