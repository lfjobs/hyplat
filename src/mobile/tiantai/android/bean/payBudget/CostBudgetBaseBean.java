package mobile.tiantai.android.bean.payBudget;

import hy.plat.bo.BaseBean;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 招标投标单添加bean
 */
public class CostBudgetBaseBean implements Serializable{

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
    private String xmTypeName;
    //行业类型
    private String tradeId;
    private String tradeName;
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
    //货物类别
    private String typeID;
    //跳转标识false后退跳转true提交表单跳转
    private boolean tzFlag = false;
    //跳转页面标识add:添加页面跳转update:修改页面跳转
    private String identification;
    //已添加预算单id（修改用）
    private String cashierBillsId;

    private String billsType;//类型（收入调价、支出发标）

    private String address;//地址
    private String coordinate;//坐标

    private List<BaseBean> photoList;//首页图片

    private List<BaseBean> videoList;//首页视频

    private String photoStr;//首页图片字符拼串
    private String videoStr;//首页视频字符拼串

    private String tradeID;
    private String tradeCode;
    private String producttype;
    private String productCode;
    private String type;
    private String ccompanyID;          //往来单位ID
    private String ccompanyName;        //往来单位name
    private String ctUserName;          //往来个人name
    private Date startTime;             //起时间
    private Date endTime;               //止时间
    private String priceSub;            //价钱总和
    private String dataDepotID;         //仓库ID
    private String dataDepotName;       //仓库名称

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

    public String getXmType() {
        return xmType;
    }

    public void setXmType(String xmType) {
        this.xmType = xmType;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
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

    public String getCashierBillsId() {
        return cashierBillsId;
    }

    public void setCashierBillsId(String cashierBillsId) {
        this.cashierBillsId = cashierBillsId;
    }

    public String getBillsType() {
        return billsType;
    }

    public void setBillsType(String billsType) {
        this.billsType = billsType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public List<BaseBean> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<BaseBean> photoList) {
        this.photoList = photoList;
    }

    public List<BaseBean> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<BaseBean> videoList) {
        this.videoList = videoList;
    }

    public String getPhotoStr() {
        return photoStr;
    }

    public void setPhotoStr(String photoStr) {
        this.photoStr = photoStr;
    }

    public String getVideoStr() {
        return videoStr;
    }

    public void setVideoStr(String videoStr) {
        this.videoStr = videoStr;
    }

    public String getTradeID() {
        return tradeID;
    }

    public void setTradeID(String tradeID) {
        this.tradeID = tradeID;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getXmTypeName() {
        return xmTypeName;
    }

    public void setXmTypeName(String xmTypeName) {
        this.xmTypeName = xmTypeName;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getCcompanyID() {
        return ccompanyID;
    }

    public void setCcompanyID(String ccompanyID) {
        this.ccompanyID = ccompanyID;
    }

    public String getCcompanyName() {
        return ccompanyName;
    }

    public void setCcompanyName(String ccompanyName) {
        this.ccompanyName = ccompanyName;
    }

    public String getCtUserName() {
        return ctUserName;
    }

    public void setCtUserName(String ctUserName) {
        this.ctUserName = ctUserName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPriceSub() {
        return priceSub;
    }

    public void setPriceSub(String priceSub) {
        this.priceSub = priceSub;
    }

    public String getDataDepotID() {
        return dataDepotID;
    }

    public void setDataDepotID(String dataDepotID) {
        this.dataDepotID = dataDepotID;
    }

    public String getDataDepotName() {
        return dataDepotName;
    }

    public void setDataDepotName(String dataDepotName) {
        this.dataDepotName = dataDepotName;
    }
}
