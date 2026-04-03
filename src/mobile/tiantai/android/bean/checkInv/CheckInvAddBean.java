package mobile.tiantai.android.bean.checkInv;

import hy.ea.bo.invoicing.Inventory;

import java.util.List;

/**
 * Created by Administrator on 2019/12/19.
 * 盘库单
 */
public class CheckInvAddBean {
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
    //负责人id
    private String staffFzrId;
    //负责人名称
    private String staffName;
    //条形码号
    private String barcode;
    //盘库仓库
    private String depotName;
    //盘库id
    private String warehouse;
    //商品信息
    private String ppId;

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    //货物信息
    //货物id
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
    //库存数量
    private String invenQuantity;
    //盘库数量
    private List<String> depotNum;
    //盘库单价
    private String unitPrice;
    //盘库总价
    private double total;
    //盘库金额
    private String sumprice;

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
    //已添加盘库单id（修改用）
    private String fbillid;
    //商品的bean
    private List<GoodListAddBean> goodListAddBean;


    /*GET AND SET METHOD*/

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<String> getDepotNum() {
        return depotNum;
    }

    public void setDepotNum(List<String> depotNum) {
        this.depotNum = depotNum;
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

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getStaffFzrId() {
        return staffFzrId;
    }

    public void setStaffFzrId(String staffFzrId) {
        this.staffFzrId = staffFzrId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getDepotName() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public void setGoodsName(String goodsName) {
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

    public boolean isTzFlag() {
        return tzFlag;
    }

    public void setTzFlag(boolean tzFlag) {
        this.tzFlag = tzFlag;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getFbillid() {
        return fbillid;
    }

    public void setFbillid(String fbillid) {
        this.fbillid = fbillid;
    }

    public List<GoodListAddBean> getGoodListAddBean() {
        return goodListAddBean;
    }

    public void setGoodListAddBean(List<GoodListAddBean> goodListAddBean) {
        this.goodListAddBean = goodListAddBean;
    }
}
