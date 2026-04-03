package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

/**
 * IntendedCustomers
 *
 * @author zch
 */
public class IntendedCustomers implements BaseBean {
    private String id;
    private String companyId;
    private String staffId;
    private String name;
    private String phone;
    private String inputPerson;
    private String type1;
    private String creatTime;
    private String updateTime;

    public IntendedCustomers() {
    }
    public IntendedCustomers(String id, String companyId, String staffId, String name, String phone, String inputPerson, String type1, String creatTime, String updateTime) {
        this.id = id;
        this.companyId = companyId;
        this.staffId = staffId;
        this.name = name;
        this.phone = phone;
        this.inputPerson = inputPerson;
        this.type1 = type1;
        this.creatTime = creatTime;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public String getInputPerson() {
        return inputPerson;
    }

    public void setInputPerson(String inputPerson) {
        this.inputPerson = inputPerson;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}

