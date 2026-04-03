package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class CostBudgetBill implements BaseBean, java.io.Serializable {
    private String budgetBillKey; //主键id
    private String budgetBillId; //预算单id
    private String voucherNum; //凭证号
    private String companyId; //所属公司
    private String businessTypeId; //行业类别id
    private String businessTypeName; //行业类别名称
    private String ccompanyId; //父公司id
    private String goodsTypeId; //物品类别id
    private String goodsTypeName; //物品类别名称
    private String projectTypeId; //项目分类id
    private String projectTypeName; //项目分类
    private Date billDate; //制单时间
    private String status; //单据状态码
    private String statusName; //单据状态
    private String billType; //单据类型码
    private String billTypeName; //单据类型
    private String sumAmount; //合计金额
    private String createId; //制单人id
    private String createName; //制单人姓名
    private String updateId; //修改人id
    private String updateName; //修改人姓名
    private Date createTime; //创建时间
    private Date updateTime; //修改时间
    private String address; //地址

    public String getBudgetBillKey() {
        return budgetBillKey;
    }

    public void setBudgetBillKey(String budgetBillKey) {
        this.budgetBillKey = budgetBillKey;
    }

    public String getBudgetBillId() {
        return budgetBillId;
    }

    public void setBudgetBillId(String budgetBillId) {
        this.budgetBillId = budgetBillId;
    }

    public String getVoucherNum() {
        return voucherNum;
    }

    public void setVoucherNum(String voucherNum) {
        this.voucherNum = voucherNum;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public String getCcompanyId() {
        return ccompanyId;
    }

    public void setCcompanyId(String ccompanyId) {
        this.ccompanyId = ccompanyId;
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(String projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillTypeName() {
        return billTypeName;
    }

    public void setBillTypeName(String billTypeName) {
        this.billTypeName = billTypeName;
    }

    public String getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(String sumAmount) {
        this.sumAmount = sumAmount;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}