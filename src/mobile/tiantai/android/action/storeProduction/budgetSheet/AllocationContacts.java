package mobile.tiantai.android.action.storeProduction.budgetSheet;

/**
 * AllocationContacts
 *
 * @author zch
 */
public class AllocationContacts {
    private String name;
    private String phone;
    private String staffId;
    private String sccId;

    public AllocationContacts() {
    }
    public AllocationContacts(String name, String phone, String staffId, String sccId) {
        this.name = name;
        this.phone = phone;
        this.staffId = staffId;
        this.sccId = sccId;
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
    public String getStaffId() {
        return staffId;
    }
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
    public String getSccId() {
        return sccId;
    }
    public void setSccId(String sccId) {
        this.sccId = sccId;
    }
}
