package mobile.tiantai.android.action.storeProduction.budgetSheet;

import hy.plat.bo.BaseBean;

/**
 * IntendedCustomers
 *
 * @author zch
 */
public class IntendedCustomersDto implements BaseBean {
    private String companyId;
    private String staffId;
    private String name;
    private String phone;

    public IntendedCustomersDto() {
    }
    public IntendedCustomersDto(String companyId, String staffId, String name, String phone) {
        this.companyId = companyId;
        this.staffId = staffId;
        this.name = name;
        this.phone = phone;

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

}

