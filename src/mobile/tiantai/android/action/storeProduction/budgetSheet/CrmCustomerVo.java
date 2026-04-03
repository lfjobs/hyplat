package mobile.tiantai.android.action.storeProduction.budgetSheet;

/**
 * CrmCustomerVo
 *
 * @author zch
 */
public class CrmCustomerVo {
    //名字
    private String name;
    //身份证号
    private String number;
    //电话
    private String contact;
    //地址
    private String address;
    //备注
    private String extend;
    private String social;
    private String unitCompany;

    public CrmCustomerVo() {
    }

    public CrmCustomerVo(String name, String number, String contact, String address, String extend, String social, String unitCompany) {
        this.name = name;
        this.number = number;
        this.contact = contact;
        this.address = address;
        this.extend = extend;
        this.social = social;
        this.unitCompany = unitCompany;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getUnitCompany() {
        return unitCompany;
    }

    public void setUnitCompany(String unitCompany) {
        this.unitCompany = unitCompany;
    }
}
