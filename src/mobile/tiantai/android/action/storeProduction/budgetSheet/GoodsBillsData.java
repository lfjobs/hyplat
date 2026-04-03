package mobile.tiantai.android.action.storeProduction.budgetSheet;

public class GoodsBillsData {
    private String lineNo;
    private String goodsName;
    private String barCode;
    private String specs;//规格
    private String count;
    private String price;
    private String amount;
    private String account;

    public GoodsBillsData() {
    }

    public GoodsBillsData(String lineNo, String goodsName, String barCode, String specs, String count, String price, String amount, String account) {
        this.lineNo = lineNo;
        this.goodsName = goodsName;
        this.barCode = barCode;
        this.specs = specs;
        this.count = count;
        this.price = price;
        this.amount = amount;
        this.account = account;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
