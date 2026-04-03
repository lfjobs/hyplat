package hy.ea.finance.action.response;

import java.util.Date;

/**
 * 成交订单 - 订单详情响应结果
 */
public class OrderDetailResponse {
    /* 订单详情 */
    private String goodsName;//商品名称
    private String standard;//规格
    private String goodsVariableID;//单位
    private String price;//单价
    private String priceSub;//金额
    private String journalNum;//订单编号
    private Date cashierDate;//下单时间
    /* 支付信息 */
    private Date fkDate;//付款时间
    private String payType;//支付方式
    private String attachment;//转款附件
    /* 收货信息 */
    private String receivename;//收货姓名
    private String receivetel;//收货电话
    private String receiveaddress;//收货地址
    private Date shDate;//收货时间
    /*  收款状态 */
    private String skStatus;//状态：已收款 未收款

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSkStatus() {
        return skStatus;
    }

    public void setSkStatus(String skStatus) {
        this.skStatus = skStatus;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getGoodsVariableID() {
        return goodsVariableID;
    }

    public void setGoodsVariableID(String goodsVariableID) {
        this.goodsVariableID = goodsVariableID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceSub() {
        return priceSub;
    }

    public void setPriceSub(String priceSub) {
        this.priceSub = priceSub;
    }

    public String getJournalNum() {
        return journalNum;
    }

    public void setJournalNum(String journalNum) {
        this.journalNum = journalNum;
    }

    public Date getCashierDate() {
        return cashierDate;
    }

    public void setCashierDate(Date cashierDate) {
        this.cashierDate = cashierDate;
    }

    public Date getFkDate() {
        return fkDate;
    }

    public void setFkDate(Date fkDate) {
        this.fkDate = fkDate;
    }

    public Date getShDate() {
        return shDate;
    }

    public void setShDate(Date shDate) {
        this.shDate = shDate;
    }


    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getReceivename() {
        return receivename;
    }

    public void setReceivename(String receivename) {
        this.receivename = receivename;
    }

    public String getReceivetel() {
        return receivetel;
    }

    public void setReceivetel(String receivetel) {
        this.receivetel = receivetel;
    }

    public String getReceiveaddress() {
        return receiveaddress;
    }

    public void setReceiveaddress(String receiveaddress) {
        this.receiveaddress = receiveaddress;
    }
}
