package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class CostBudgetItem implements BaseBean, java.io.Serializable {
    private String budgetItemKey; //主键id
    private String budgetItemId; //预算明细id
    private String ppid; //产品id
    private String companyid; //公司id
    private String companyName; //公司名称
    private String organizationid; //部门id
    private String organizationName; //部门名称
    private String goodsname; //物品名称
    private String quantity; //数量
    private String price; //价格(调查价)
    private String stock; //库存
    private String standard; //规格
    private String fiveclear; //5C 1 _人事,2_办公室,3——财务,4——教务处,5-营销
    private String barcode; //条码
    private String brand; //品牌
    private String variableid; //单位
    private String itemStatus; //数据状态
    private String staffid; //责任人id
    private String staffName; //责任人姓名
    private String amendStaffid; //修改人id
    private String amendStaffName; //修改人姓名
    private String gradeid; //产品等级id
    private String gradeName; //产品等级
    private String receiveType; //接收人类型
    private Date createTime; //创建时间
    private Date updateTime; //修改时间
    private String deadline; //截止日期
    private String businessTypeId; //行业类别id
    private String businessTypeName; //行业类别名称
    private String address;//地址


    public String getBudgetItemKey() {
        return budgetItemKey;
    }

    public void setBudgetItemKey(String budgetItemKey) {
        this.budgetItemKey = budgetItemKey;
    }

    public String getBudgetItemId() {
        return budgetItemId;
    }

    public void setBudgetItemId(String budgetItemId) {
        this.budgetItemId = budgetItemId;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrganizationid() {
        return organizationid;
    }

    public void setOrganizationid(String organizationid) {
        this.organizationid = organizationid;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getFiveclear() {
        return fiveclear;
    }

    public void setFiveclear(String fiveclear) {
        this.fiveclear = fiveclear;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getVariableid() {
        return variableid;
    }

    public void setVariableid(String variableid) {
        this.variableid = variableid;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getAmendStaffid() {
        return amendStaffid;
    }

    public void setAmendStaffid(String amendStaffid) {
        this.amendStaffid = amendStaffid;
    }

    public String getAmendStaffName() {
        return amendStaffName;
    }

    public void setAmendStaffName(String amendStaffName) {
        this.amendStaffName = amendStaffName;
    }

    public String getGradeid() {
        return gradeid;
    }

    public void setGradeid(String gradeid) {
        this.gradeid = gradeid;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(String businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}