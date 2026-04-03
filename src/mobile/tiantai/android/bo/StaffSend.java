package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

/**
 * StaffSend
 *
 * @author zch
 */
public class StaffSend implements BaseBean {
    private String id;
    private String staffId;
    private String name;
    private String phone;
    private String sendNum;
    private String companyId;

    public StaffSend() {
    }
    public StaffSend(String id, String staffId, String name, String phone, String sendNum, String companyId) {
        this.id = id;
        this.staffId = staffId;
        this.name = name;
        this.phone = phone;
        this.sendNum = sendNum;
        this.companyId = companyId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSendNum() {
        return sendNum;
    }

    public void setSendNum(String sendNum) {
        this.sendNum = sendNum;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}