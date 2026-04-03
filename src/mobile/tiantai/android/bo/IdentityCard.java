package mobile.tiantai.android.bo;

public class IdentityCard {
    private String name;
    private String sex;
    private String nation;
    private String birthDay;
    private String address;
    private String identityCardNO;

    public IdentityCard() {
    }

    public IdentityCard(String name, String sex, String nation, String birthDay, String address, String identityCardNO) {
        this.name = name;
        this.sex = sex;
        this.nation = nation;
        this.birthDay = birthDay;
        this.address = address;
        this.identityCardNO = identityCardNO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentityCardNO() {
        return identityCardNO;
    }

    public void setIdentityCardNO(String identityCardNO) {
        this.identityCardNO = identityCardNO;
    }
}
