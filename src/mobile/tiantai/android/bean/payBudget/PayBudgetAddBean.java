package mobile.tiantai.android.bean.payBudget;

import java.util.Date;

/**
 * 支出预算单添加bean
 * Created by Administrator on 2019-11-04.
 */
public class PayBudgetAddBean {
    //公司id
    private String companyId;
    //登录人id,暂时没用
    private String staffId;
    //是否是选择全部false全部true单一部门
    private boolean showFlag;
    //所选部门id
    private String departmentID;
    //所选部门名称（-1为所有部门）
    private String departmentName;
    //单据凭证号
    private String billId;
    //公司名称
    private String companyName;
    //项目名称
    private String itemName;
    //项目分类
    private String itemType;
    //项目类型
    private String xmType;
    //项目id
    private String itemId;
    //项目编号
    private String itemCode;
    //负责人id
    private String staffFzrId;
    //负责人名称
    private String staffName;
    //负责人编号
    private String staffCode;
    //制单人名称
    private String singleName;
    //条形码号
    private String barcode;
    //货物信息
    //货物id
    private String goodsId;
    //货物类别
    private String goodsTypeId;
    //货物条形码
    private String goodsBarCode;
    //货物品名编号
    private String goodsGoodsCoding;
    //货物品名名称
    private String goodsGoodsName;
    //货物图片路径
    private String goodsPhotoPath;
    //货物规格
    private String goodsStandard;
    //货物单位
    private String goodsVariableId;
    //库存数量
    private String invInvenQuantity;
    //预算数量
    private String budgetNumber;
    //预算单价
    private String budgetUnitPrice;
    //预算金额
    private String budgetMoney;
    //往来公司id
    private String currentCompanyId;
    //往来公司
    private String currentCompany;
    //往来个人id
    private String currentPersonId;
    //往来个人
    private String currentPerson;
    //跳转标识false后退跳转true提交表单跳转
    private boolean tzFlag = false;
    //跳转页面标识add:添加页面跳转update:修改页面跳转
    private String identification;
    //已添加预算单id（修改用）
    private String cashierBillsId;


    /*GET AND SET METHOD*/
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

    public boolean isShowFlag() {
        return showFlag;
    }

    public void setShowFlag(boolean showFlag) {
        this.showFlag = showFlag;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getSingleName() {
        return singleName;
    }

    public void setSingleName(String singleName) {
        this.singleName = singleName;
    }

    public String getStaffFzrId() {
        return staffFzrId;
    }

    public void setStaffFzrId(String staffFzrId) {
        this.staffFzrId = staffFzrId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public String getGoodsGoodsCoding() {
        return goodsGoodsCoding;
    }

    public void setGoodsGoodsCoding(String goodsGoodsCoding) {
        this.goodsGoodsCoding = goodsGoodsCoding;
    }

    public String getGoodsGoodsName() {
        return goodsGoodsName;
    }

    public void setGoodsGoodsName(String goodsGoodsName) {
        this.goodsGoodsName = goodsGoodsName;
    }

    public String getGoodsStandard() {
        return goodsStandard;
    }

    public void setGoodsStandard(String goodsStandard) {
        this.goodsStandard = goodsStandard;
    }

    public String getGoodsVariableId() {
        return goodsVariableId;
    }

    public void setGoodsVariableId(String goodsVariableId) {
        this.goodsVariableId = goodsVariableId;
    }

    public String getInvInvenQuantity() {
        return invInvenQuantity;
    }

    public void setInvInvenQuantity(String invInvenQuantity) {
        this.invInvenQuantity = invInvenQuantity;
    }

    public String getBudgetNumber() {
        return budgetNumber;
    }

    public void setBudgetNumber(String budgetNumber) {
        this.budgetNumber = budgetNumber;
    }

    public String getBudgetUnitPrice() {
        return budgetUnitPrice;
    }

    public void setBudgetUnitPrice(String budgetUnitPrice) {
        this.budgetUnitPrice = budgetUnitPrice;
    }

    public String getBudgetMoney() {
        return budgetMoney;
    }

    public void setBudgetMoney(String budgetMoney) {
        this.budgetMoney = budgetMoney;
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

    public boolean isTzFlag() {
        return tzFlag;
    }

    public void setTzFlag(boolean tzFlag) {
        this.tzFlag = tzFlag;
    }

    public String getGoodsPhotoPath() {
        return goodsPhotoPath;
    }

    public void setGoodsPhotoPath(String goodsPhotoPath) {
        this.goodsPhotoPath = goodsPhotoPath;
    }

    public String getXmType() {
        return xmType;
    }

    public void setXmType(String xmType) {
        this.xmType = xmType;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getCashierBillsId() {
        return cashierBillsId;
    }

    public void setCashierBillsId(String cashierBillsId) {
        this.cashierBillsId = cashierBillsId;
    }
}
