package mobile.tiantai.android.bean.checkInv;

/**
 * Created by Administrator on 2020/1/3.
 */
public class GoodListAddBean {
    private String goodsId;
    //货物类别
    private String goodsType;
    //货物条形码
    private String barCode;
    //货物品名编号
    private String goodsCoding;
    //货物品名名称
    private String goodsName;
    //货物规格
    private String standard;
    //货物单位
    private String variableId;
    //盘库数量
    private String invenQuantity;
    //盘库单价
    private String unitPrice;
    //盘库金额
    //盘库数量
    private String depotNum;
    private String sumprice;
    //往来公司id
    private String currentCompanyId;
    //往来公司
    private String currentCompany;
    //往来个人id
    private String currentPersonId;
    //往来个人
    private String currentPerson;

    private String realQuantity;            //实际数量(盘点数量)
    private int error;

    public String getRealQuantity() {
        return realQuantity;
    }

    public void setRealQuantity(String realQuantity) {
        this.realQuantity = realQuantity;
        this.error = Integer.parseInt(invenQuantity)-Integer.parseInt(realQuantity);
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getGoodsCoding() {
        return goodsCoding;
    }

    public void setGoodsCoding(String goodsCoding) {
        this.goodsCoding = goodsCoding;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void  setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getVariableId() {
        return variableId;
    }

    public void setVariableId(String variableId) {
        this.variableId = variableId;
    }

    public String getInvenQuantity() {
        return invenQuantity;
    }

    public void setInvenQuantity(String invenQuantity) {
        this.invenQuantity = invenQuantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSumprice() {
        return sumprice;
    }

    public void setSumprice(String sumprice) {
        this.sumprice = sumprice;
    }

    public String getCurrentCompanyId() {
        return currentCompanyId;
    }

    public void setCurrentCompanyId(String currentCompanyId) {
        this.currentCompanyId = currentCompanyId;
    }

    public String getCurrentCompany() {
        return currentCompany;
    }

    public void setCurrentCompany(String currentCompany) {
        this.currentCompany = currentCompany;
    }

    public String getCurrentPersonId() {
        return currentPersonId;
    }

    public void setCurrentPersonId(String currentPersonId) {
        this.currentPersonId = currentPersonId;
    }

    public String getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(String currentPerson) {
        this.currentPerson = currentPerson;
    }

    public String getDepotNum() {
        return depotNum;
    }

    public void setDepotNum(String depotNum) {
        this.depotNum = depotNum;
    }
}
