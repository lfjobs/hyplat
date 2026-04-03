package mobile.tiantai.android.bean.payBudget;

import hy.plat.bo.BaseBean;
import mobile.tiantai.android.bo.GoodsBillsExtOpinion;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * 招标投标单添加bean
 */
public class CostBudgetAddBean implements BaseBean, Serializable {
    private String goodsBillsKey;
    private String goodsBillsID;
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
    private String barCode;
    //货物信息
    //货物id
    private String goodsId;
    //货物类别
    private String typeID;
    //货物品名编号
    private String goodsCoding;
    //货物品名名称
    private String goodsName;
    //货物图片路径
    private String photoPath;
    //货物规格
    private String standard;
    //货物单位
    private String variableId;
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

    private String isScale;//是否云计称重

    private String billsType;//类型（收入调价、支出发标）

    private String address;//地址
    private String coordinate;//坐标

    private List<BaseBean> photoList;//首页图片

    private List<BaseBean> videoList;//首页视频

    private String photoStr;//首页图片字符拼串
    private String videoStr;//首页视频字符拼串

    private String ppid;//产品id
    private String tradeID;
    private String tradeCode;
    private String tradeName;
    private String brand;//品牌
    private String unitofmeasurecode;//计价单位
    private String producttype;
    private String productCode;
    private String type;
    private String logoPath;//品牌图片路径
    private String gradeid;//等级id
    private String gradeName;//等级名称

    private String price;//调查价
    private String colorvalue;//颜色
    private String sizevalue;//尺码

    private String attrinames;//固定值：颜色（规格属性值）
    private String attrinamec;//固定值：尺码

    private String htl;//宝贝详情描述

    private File[] pic;// 产品描述图片
    private String[] picFileName;
    private String[] functionList;
    private String cashierBillsID;
    private String cashierBillsKey;
    private File fileLogo;// 品牌图片
    private String htlPath;//宝贝详情存储路径
    private String guigeType;//规格类型
    private String guigeTypeId;//规格类型id
    private String attri;//规格明细
    private String attriJson;//规格明细
    private String unitPrice;//单价
    private String amount;//金额
    private String goodsPurpose;//物品用途（页面上的物品类别）
    private String goodsPurposeId;//物品用途id（页面上的物品类别）
    private String guigeTypeValue;//规格类型
    private String accountName;// 往来单位
    private String accountNameId;// 往来单位
    private String accountNum;// 账号
    private String openBank;// 开户行
    private String accountPhone;//账户联系方式

    private String accountFlag;//账户类型：personal个人，company公司
    private String budgetAmount;//预算金额
    private String loanDirection;//借贷方向
    private String accountInfo;//往来单位/个人展示信息
    private String goodsBillsExtId;

    private String accountNameP;// 往来个人
    private String accountNameIdP;// 往来个人ID
    private String accountNumP;// 账号
    private String openBankP;// 开户行
    private String accountPhoneP;//账户联系方式
    private String borrowAmount;//借方金额
    private String loanAmount;//贷方金额
    private String accountShow;//账户展示数据
    private long orderNum;//序号
    private String balance;//余额
    private String initialBalance;//初始余额
    private String specsParentId;//规格对应的父级id

    private String accountNameFrom;//往来单位/个人名称
    private String accountNameIdFrom;//往来单位/个人id
    private String accountNumFrom;//账户号

    private String openBankFrom;//开户行
    private String accountPhoneFrom;//账户联系方式

    private String accountFlagFrom;//账户类型：personal个人，company公司

    private String accountShowFrom;//账户展示数据

    private String accountNameFromP;//往来单位/个人名称
    private String accountNameIdFromP;//往来单位/个人id
    private String accountNumFromP;//账户号

    private String openBankFromP;//开户行
    private String accountPhoneFromP;//账户联系方式

    private String teamReward;//团队奖励
    private String teamRewardUpgrade;//团队奖励明细
    private String teamRewardBonus;

    private String personalReward;//个人奖励
    private String personalRewardUpgrade;//个人奖励明细
    private String personalRewardBonus;//个人奖励明细

    private String teamPunishment;//团队惩罚
    private String teamPunishmentDegrade;//团队惩罚明细
    private String teamPunishmentCut;//团队惩罚明细

    private String personalPunishment;//个人惩罚
    private String personalPunishmentDegrade;//个人惩罚明细
    private String personalPunishmentCut;//个人惩罚明细
    private String connectName;// 往来个人name
    private String serviceStartDate;//做工开始时间
    private String serviceEndDate;//做工结束时间

    private GoodsBillsExtOpinion goodsBillsExtOpinion;

    /*GET AND SET METHOD*/

    public String getAccountShow() {

        return getShowName();
    }

    public void setAccountShow(String accountShow) {

        this.accountShow = getShowName();
    }

    public String getAccountNameP() {
        return accountNameP;
    }

    public void setAccountNameP(String accountNameP) {
        this.accountNameP = accountNameP;
    }

    public String getAccountNameIdP() {
        return accountNameIdP;
    }

    public void setAccountNameIdP(String accountNameIdP) {
        this.accountNameIdP = accountNameIdP;
    }

    public String getAccountNumP() {
        return accountNumP;
    }

    public void setAccountNumP(String accountNumP) {
        this.accountNumP = accountNumP;
    }

    public String getOpenBankP() {
        return openBankP;
    }

    public void setOpenBankP(String openBankP) {
        this.openBankP = openBankP;
    }

    public String getAccountPhoneP() {
        return accountPhoneP;
    }

    public void setAccountPhoneP(String accountPhoneP) {
        this.accountPhoneP = accountPhoneP;
    }

    public String getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(String borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getGoodsBillsExtId() {
        return goodsBillsExtId;
    }

    public void setGoodsBillsExtId(String goodsBillsExtId) {
        this.goodsBillsExtId = goodsBillsExtId;
    }

    public String getColorvalue() {
        return colorvalue;
    }

    public void setColorvalue(String colorvalue) {
        this.colorvalue = colorvalue;
    }

    public String getSizevalue() {
        return sizevalue;
    }

    public void setSizevalue(String sizevalue) {
        this.sizevalue = sizevalue;
    }

    public String getAttrinames() {
        return attrinames;
    }

    public void setAttrinames(String attrinames) {
        this.attrinames = attrinames;
    }

    public String getAttrinamec() {
        return attrinamec;
    }

    public void setAttrinamec(String attrinamec) {
        this.attrinamec = attrinamec;
    }

    public String getHtl() {
        return htl;
    }

    public void setHtl(String htl) {
        this.htl = htl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
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

    public String getIsScale() {
        return isScale;
    }

    public void setIsScale(String isScale) {
        this.isScale = isScale;
    }

    public String getBillsType() {
        return billsType;
    }

    public void setBillsType(String billsType) {
        this.billsType = billsType;
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

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUnitofmeasurecode() {
        return unitofmeasurecode;
    }

    public void setUnitofmeasurecode(String unitofmeasurecode) {
        this.unitofmeasurecode = unitofmeasurecode;
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

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
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

    public File[] getPic() {
        return pic;
    }

    public void setPic(File[] pic) {
        this.pic = pic;
    }

    public String[] getPicFileName() {
        return picFileName;
    }

    public void setPicFileName(String[] picFileName) {
        this.picFileName = picFileName;
    }

    public String[] getFunctionList() {
        return functionList;
    }

    public void setFunctionList(String[] functionList) {
        this.functionList = functionList;
    }

    public String getGoodsBillsID() {
        return goodsBillsID;
    }

    public void setGoodsBillsID(String goodsBillsID) {
        this.goodsBillsID = goodsBillsID;
    }

    public String getGoodsBillsKey() {
        return goodsBillsKey;
    }

    public void setGoodsBillsKey(String goodsBillsKey) {
        this.goodsBillsKey = goodsBillsKey;
    }

    public String getCashierBillsID() {
        return cashierBillsID;
    }

    public void setCashierBillsID(String cashierBillsID) {
        this.cashierBillsID = cashierBillsID;
    }

    public String getCashierBillsKey() {
        return cashierBillsKey;
    }

    public void setCashierBillsKey(String cashierBillsKey) {
        this.cashierBillsKey = cashierBillsKey;
    }

    public File getFileLogo() {
        return fileLogo;
    }

    public void setFileLogo(File fileLogo) {
        this.fileLogo = fileLogo;
    }

    public String getHtlPath() {
        return htlPath;
    }

    public void setHtlPath(String htlPath) {
        this.htlPath = htlPath;
    }

    public String getGuigeType() {
        return guigeType;
    }

    public void setGuigeType(String guigeType) {
        this.guigeType = guigeType;
    }

    public String getAttri() {
        return attri;
    }

    public void setAttri(String attri) {
        this.attri = attri;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getGoodsPurpose() {
        return goodsPurpose;
    }

    public void setGoodsPurpose(String goodsPurpose) {
        this.goodsPurpose = goodsPurpose;
    }

    public String getGuigeTypeId() {
        return guigeTypeId;
    }

    public void setGuigeTypeId(String guigeTypeId) {
        this.guigeTypeId = guigeTypeId;
    }

    public String getAttriJson() {
        return attriJson;
    }

    public void setAttriJson(String attriJson) {
        this.attriJson = attriJson;
    }

    public String getGoodsPurposeId() {
        return goodsPurposeId;
    }

    public void setGoodsPurposeId(String goodsPurposeId) {
        this.goodsPurposeId = goodsPurposeId;
    }

    public String getGuigeTypeValue() {
        return guigeTypeValue;
    }

    public void setGuigeTypeValue(String guigeTypeValue) {
        this.guigeTypeValue = guigeTypeValue;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNameId() {
        return accountNameId;
    }

    public void setAccountNameId(String accountNameId) {
        this.accountNameId = accountNameId;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getAccountFlag() {
        return accountFlag;
    }

    public void setAccountFlag(String accountFlag) {
        this.accountFlag = accountFlag;
    }

    public String getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(String budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public String getLoanDirection() {
        return loanDirection;
    }

    public void setLoanDirection(String loanDirection) {
        this.loanDirection = loanDirection;
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(String initialBalance) {
        this.initialBalance = initialBalance;
    }

    public String getSpecsParentId() {
        return specsParentId;
    }

    public void setSpecsParentId(String specsParentId) {
        this.specsParentId = specsParentId;
    }

    public String getAccountNameFrom() {
        return accountNameFrom;
    }

    public void setAccountNameFrom(String accountNameFrom) {
        this.accountNameFrom = accountNameFrom;
    }

    public String getAccountNameIdFrom() {
        return accountNameIdFrom;
    }

    public void setAccountNameIdFrom(String accountNameIdFrom) {
        this.accountNameIdFrom = accountNameIdFrom;
    }

    public String getAccountNumFrom() {
        return accountNumFrom;
    }

    public void setAccountNumFrom(String accountNumFrom) {
        this.accountNumFrom = accountNumFrom;
    }

    public String getOpenBankFrom() {
        return openBankFrom;
    }

    public void setOpenBankFrom(String openBankFrom) {
        this.openBankFrom = openBankFrom;
    }

    public String getAccountPhoneFrom() {
        return accountPhoneFrom;
    }

    public void setAccountPhoneFrom(String accountPhoneFrom) {
        this.accountPhoneFrom = accountPhoneFrom;
    }

    public String getAccountFlagFrom() {
        return accountFlagFrom;
    }

    public void setAccountFlagFrom(String accountFlagFrom) {
        this.accountFlagFrom = accountFlagFrom;
    }

    public String getAccountShowFrom() {

        return getShowNameFrom();
    }

    private String getShowName() {
        String flag = getAccountFlag();
        String num = null;
        String accName = null;
        if ("company".equals(flag) || "companyPlan".equals(flag)) {
            num = getAccountNum();
            accName = getAccountName();
        } else if ("personal".equals(flag) || "personalPlan".equals(flag)) {
            num = getAccountNumP();
            accName = getAccountNameP();
        }

        String showName = null;
        if (StringUtils.isNotEmpty(num) && StringUtils.isNotEmpty(accName)) {
            if (accName.length() < 10) {
                showName = accName;
            } else {
                showName = accName.substring(0, 10) + "**";
            }
            showName += "(" + (num.length() < 4 ? num : num.substring(num.length() - 4)) + ")";
        }
        return showName;
    }

    private String getShowNameFrom() {
        String flag = getAccountFlagFrom();
        String num = null;
        String accName = null;
        if ("company".equals(flag) || "companyPlan".equals(flag)) {
            num = getAccountNumFrom();
            accName = getAccountNameFrom();
        } else if ("personal".equals(flag) || "personalPlan".equals(flag)) {
            num = getAccountNumFromP();
            accName = getAccountNameFromP();
        }
        String showName = null;
        if (StringUtils.isNotEmpty(num) && StringUtils.isNotEmpty(accName)) {
            if (accName.length() < 10) {
                showName = accName;
            } else {
                showName = accName.substring(0, 10) + "**";
            }
            showName += "(" + (num.length() < 4 ? num : num.substring(num.length() - 4)) + ")";
        }
        return showName;
    }

    public void setAccountShowFrom(String accountShowFrom) {

        this.accountShowFrom = getShowNameFrom();
    }

    public String getAccountNameFromP() {
        return accountNameFromP;
    }

    public void setAccountNameFromP(String accountNameFromP) {
        this.accountNameFromP = accountNameFromP;
    }

    public String getAccountNameIdFromP() {
        return accountNameIdFromP;
    }

    public void setAccountNameIdFromP(String accountNameIdFromP) {
        this.accountNameIdFromP = accountNameIdFromP;
    }

    public String getAccountNumFromP() {
        return accountNumFromP;
    }

    public void setAccountNumFromP(String accountNumFromP) {
        this.accountNumFromP = accountNumFromP;
    }

    public String getOpenBankFromP() {
        return openBankFromP;
    }

    public void setOpenBankFromP(String openBankFromP) {
        this.openBankFromP = openBankFromP;
    }

    public String getAccountPhoneFromP() {
        return accountPhoneFromP;
    }

    public void setAccountPhoneFromP(String accountPhoneFromP) {
        this.accountPhoneFromP = accountPhoneFromP;
    }

    public String getTeamReward() {
        return teamReward;
    }

    public void setTeamReward(String teamReward) {
        this.teamReward = teamReward;
    }

    public String getPersonalReward() {
        return personalReward;
    }

    public void setPersonalReward(String personalReward) {
        this.personalReward = personalReward;
    }

    public String getTeamPunishment() {
        return teamPunishment;
    }

    public void setTeamPunishment(String teamPunishment) {
        this.teamPunishment = teamPunishment;
    }

    public String getPersonalPunishment() {
        return personalPunishment;
    }

    public String getTeamRewardUpgrade() {
        return teamRewardUpgrade;
    }

    public void setTeamRewardUpgrade(String teamRewardUpgrade) {
        this.teamRewardUpgrade = teamRewardUpgrade;
    }

    public String getTeamRewardBonus() {
        return teamRewardBonus;
    }

    public void setTeamRewardBonus(String teamRewardBonus) {
        this.teamRewardBonus = teamRewardBonus;
    }

    public String getPersonalRewardUpgrade() {
        return personalRewardUpgrade;
    }

    public void setPersonalRewardUpgrade(String personalRewardUpgrade) {
        this.personalRewardUpgrade = personalRewardUpgrade;
    }

    public String getPersonalRewardBonus() {
        return personalRewardBonus;
    }

    public void setPersonalRewardBonus(String personalRewardBonus) {
        this.personalRewardBonus = personalRewardBonus;
    }

    public String getTeamPunishmentDegrade() {
        return teamPunishmentDegrade;
    }

    public void setTeamPunishmentDegrade(String teamPunishmentDegrade) {
        this.teamPunishmentDegrade = teamPunishmentDegrade;
    }

    public String getTeamPunishmentCut() {
        return teamPunishmentCut;
    }

    public void setTeamPunishmentCut(String teamPunishmentCut) {
        this.teamPunishmentCut = teamPunishmentCut;
    }

    public void setPersonalPunishment(String personalPunishment) {
        this.personalPunishment = personalPunishment;
    }

    public String getPersonalPunishmentDegrade() {
        return personalPunishmentDegrade;
    }

    public void setPersonalPunishmentDegrade(String personalPunishmentDegrade) {
        this.personalPunishmentDegrade = personalPunishmentDegrade;
    }

    public String getPersonalPunishmentCut() {
        return personalPunishmentCut;
    }

    public void setPersonalPunishmentCut(String personalPunishmentCut) {
        this.personalPunishmentCut = personalPunishmentCut;
    }

    public String getConnectName() {
        return connectName;
    }

    public void setConnectName(String connectName) {
        this.connectName = connectName;
    }

    public String getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(String serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    public String getServiceEndDate() {
        return serviceEndDate;
    }

    public void setServiceEndDate(String serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    public GoodsBillsExtOpinion getGoodsBillsExtOpinion() {
        return goodsBillsExtOpinion;
    }

    public void setGoodsBillsExtOpinion(GoodsBillsExtOpinion goodsBillsExtOpinion) {
        this.goodsBillsExtOpinion = goodsBillsExtOpinion;
    }
}
