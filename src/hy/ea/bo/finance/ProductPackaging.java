package hy.ea.bo.finance;

import hy.ea.bo.ExcelBean;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * 产品包装表（产品表）
 *
 * @author lf
 */
public class ProductPackaging implements BaseBean, ExcelBean, java.io.Serializable {
    private String ppKey;  //主键
    private String ppID;  //业务主键
    private String goodsID;   //物品ID
    private String companyID;  //公司ID
    private String organizationID;  //部门ID
    private String staffID;    // 责任人ID
    private String staffName;//责任人名称
    private String amendStaffID;//修改人ID ,xgb
    private String sccid;//账号id,xgb
    private Date packagingDate;  //包装日期
    private String goodsName;   //物品名称
    private String quantity;// 数量
    private String money;// 金额（成本价）
    private String weight;// 重量
    private String price;// 单价
    private String manualUrl;  //说明书url
    private String propagandaUrl;  //宣传url
    private String fileUrl;   //公司文件url
    private String parentId;  //配件的父id
    private String parentName;//配件的父Name
    private String image;     //产品主题图片
    private String photo;      //产品图
    private String showweixin;//是否在微商店显示0否  01是
    private String weiDianType;//商品类别
    private String certificateCost;//考核办证费

    //wk
    private String ppstatus;//0 为 可代理   1为不可代理 2未处理
    private String shangjiastatus;//0已经出售中1为已经下架;
    private String pptype;//01微信的产品发布
    private String ppcestuer;//1未进行产品测试,2测试合格,3测试不合格
    private String ppEnterprise;//企业商城;01
    private String ppDifferential;//微分金商场01
    private String ppDifferentialshop;//微分金店01
    private String ppWeb;//网站商城01
    private String ppOther;//其他发布01


    private String xmtype;
    private String xmtypename;
    private int sorting;//排序

    //新增三个属性，用于微分金
    private int stockSize;//库存数量
    private int monthSales;//月销量
    private String productDetail;//在线宣传

    private String accessories;
    private String category;//00 单产品 01 组装产品

    private String goodsNum;//物品编号
    private String remark;//备注
    private String standard;//规格
    private String type;//产品类型
    private String virtual;//虚拟物品  00 否  01 是
    private String logo;//LOGO图片

    private String departmentState;//部门状态
    private String projectType;//产品类型
    private String fiveClear;//

    private String updateUser;//合格不合格操作人
    private String giveNumber;//赠送积分数
    private Date createTime;//发布时间
    private String ppwhether;//是否可被直接购买
    /****************************佣金设置信息**************************/
    private String yjstatus;//00:未设计佣金  01:已设计佣金[售价] 状态[00:正常/审核通过 01:删除 02:草稿 03:审核中 04:驳回]
    private String wholesaleStatus;//00:未设计佣金 01:已设计佣金[批发价]
    private String vipStatus;//00:未设计佣金 01:已设计佣金[vip价]
    private String activityStatus;//00:未设计佣金 01:已设计佣金[活动价]
    private String yjstaname;//设置佣金责任人name
    private String yjstaid;//设置佣金责任人id
    private Date yjdate;//设置佣金时间
    private String tradeCode;//行业
    private String producttype;//项目产品分类
    private String productCode;//项目产品编号
    private String tradeName;//行业全名
    private String tradeID;//行业ID
    private String barCode;//条码
    private String model;//型号
    private String brand;//品牌
    private String variableID;//单位
    private String subjectName;//科目名称
    private String subjectID;//科目ID
    private String delStatus;//00正常 01删除
    private String productstate;//生产流程状态 初始：00；模拟：01   05：已设置利润率
    private String hierarchical;                //层次结构                    zj
    private String field;                //00： 字段  01：产品  02：组织机构
    //当为字段时，储存的信息格式
    private String primaryField;        //一级字段格式
    private String twoLevelField;    //二级字段格式

    private String assemble;                //存在下级的产品     无值时 说明无下级

    private String temporary;//用于上传图片记忆0：未发布，1：发布成功 ljc
    private String qualified;//用于产品是否合格ljc  0不合格，1合格
    private String review;//用于区分 type='会员分享' 是否展示 00:展示 01：不展示
    private String carModel;//车品牌
    private String categoryId;//关联驾校产品分类表的分类id
    private String categoryName;//关联驾校产品分类表的分类名称
    private String platform;//若该产品是平台，用于存储平台返回值

    private String isScale;//是否需要电子秤打印条码

    private String invNum;//初始库存

    private String gradeid;//产品等级id
    private String gradeName;//产品等级

    private String ccompanyID;//供应商往来单位ID
    private String ccompanyName;//供应商往来单位名称不存数据库

    /*********************电子货柜自动贩卖机********************/
    private String depotID; //入库仓库id
    private String depotName; //入库仓库名称
    private String depotCoding; //入库仓库编号
    private String stanpro; //是否是标品  0是，1否
    private String singleWeight;//单件重量 单位：KG

    /*
        交易类型
            01：公开交易
                11: 厂家,
                12: 批发,
                13: 零售,
                14: 货柜自提,
                15: 配送电商,
                16: 门店自助
            02：针对交易
                21: 厂家,
                22: 批发,
                23: 零售,
                24: 往来企业,
                25: 往来个人
     */
    private String paytype;


    //非数据库字段
    private String showdate;
    private String newcontent;
    private List<BaseBean> pmlist;

    public ProductPackaging(String ppID, String goodsID, Date packagingDate,
                            String goodsName, String image) {
        super();
        this.ppID = ppID;
        this.goodsID = goodsID;
        this.showdate = Utilities.getDateString(packagingDate, "yyyy-MM-dd HH:mm:ss");
        this.goodsName = goodsName;
        this.image = image;
    }

    public ProductPackaging(Date packagingDate, String goodsName, String url) {
        super();
        this.packagingDate = packagingDate;
        this.goodsName = goodsName;
        this.newcontent = url;
        this.showdate = Utilities.getDateString(packagingDate, "yyyy-MM-dd HH:mm:ss");
    }

    public ProductPackaging() {
        super();
    }

    public ProductPackaging(String ppKey, String ppID, String goodsID,
                            String companyID, String organizationID, String staffID,
                            Date packagingDate, String goodsName, String quantity,
                            String money, String weight, String price, String manual,
                            String propagandaUrl, String fileUrl, String parentId,
                            String parentName, String image, String photo, String showweixin,
                            String weiDianType, String certificateCost, String ppstatus,
                            String shangjiastatus, String pptype, String ppcestuer,
                            String ppEnterprise, String ppDifferential,
                            String ppDifferentialshop, String ppWeb, String ppOther,
                            String xmtype, String xmtypename, int sorting, int stockSize,
                            int monthSales, String productDetail, String category,
                            String goodsNum, String remark, String standard, String type,
                            String virtual, String logo, String departmentState,
                            String projectType, String fiveClear, String updateUser,
                            String giveNumber, Date createTime, String ppwhether,
                            String yjstatus, String yjstaname, String yjstaid, Date yjdate,
                            String tradeCode, String producttype, String productCode,
                            String tradeName, String tradeID, String barCode, String model,
                            String brand, String variableID, String subjectName,
                            String subjectID, String delStatus, String vipStatus,
                            String activityStatus, String productstate, String wholesaleStatus) {
        super();
        this.ppKey = ppKey;
        this.ppID = ppID;
        this.goodsID = goodsID;
        this.companyID = companyID;
        this.organizationID = organizationID;
        this.staffID = staffID;
        this.packagingDate = packagingDate;
        this.goodsName = goodsName;
        this.quantity = quantity;
        this.money = money;
        this.weight = weight;
        this.price = price;
        this.propagandaUrl = propagandaUrl;
        this.fileUrl = fileUrl;
        this.parentId = parentId;
        this.parentName = parentName;
        this.image = image;
        this.photo = photo;
        this.showweixin = showweixin;
        this.weiDianType = weiDianType;
        this.certificateCost = certificateCost;
        this.ppstatus = ppstatus;
        this.shangjiastatus = shangjiastatus;
        this.pptype = pptype;
        this.ppcestuer = ppcestuer;
        this.ppEnterprise = ppEnterprise;
        this.ppDifferential = ppDifferential;
        this.ppDifferentialshop = ppDifferentialshop;
        this.ppWeb = ppWeb;
        this.ppOther = ppOther;
        this.xmtype = xmtype;
        this.xmtypename = xmtypename;
        this.sorting = sorting;
        this.stockSize = stockSize;
        this.monthSales = monthSales;
        this.productDetail = productDetail;
        this.category = category;
        this.goodsNum = goodsNum;
        this.remark = remark;
        this.standard = standard;
        this.type = type;
        this.virtual = virtual;
        this.logo = logo;
        this.departmentState = departmentState;
        this.projectType = projectType;
        this.fiveClear = fiveClear;
        this.updateUser = updateUser;
        this.giveNumber = giveNumber;
        this.createTime = createTime;
        this.ppwhether = ppwhether;
        this.yjstatus = yjstatus;
        this.yjstaname = yjstaname;
        this.yjstaid = yjstaid;
        this.yjdate = yjdate;
        this.tradeCode = tradeCode;
        this.producttype = producttype;
        this.productCode = productCode;
        this.tradeName = tradeName;
        this.tradeID = tradeID;
        this.barCode = barCode;
        this.model = model;
        this.brand = brand;
        this.variableID = variableID;
        this.subjectName = subjectName;
        this.subjectID = subjectID;
        this.delStatus = delStatus;
        this.productstate = productstate;
        this.wholesaleStatus = wholesaleStatus;
        this.vipStatus = vipStatus;
        this.activityStatus = activityStatus;

    }

    @Override
    public String[] properties() {
        String[] properties = {tradeCode, barCode, productCode, goodsName, brand, standard, model, subjectName, price, quantity, money, remark
                , String.format("%1$tF", packagingDate)};
        return properties;
    }

    public static String[] columnHeadings() {
        String[] titles = {"序号", "行业", "产品条码", "产品编号", "产品名称", "品牌", "规格", "型号", "科目", "单价", "数量", "金额", "备注", "包装时间"};
        return titles;
    }

    public String getPpKey() {
        return ppKey;
    }

    public void setPpKey(String ppKey) {
        this.ppKey = ppKey;
    }

    public String getPpID() {
        return ppID;
    }

    public void setPpID(String ppID) {
        this.ppID = ppID;
    }

    public String getPpEnterprise() {
        return ppEnterprise;
    }

    public String getInvNum() {
        return invNum;
    }

    public void setInvNum(String invNum) {
        this.invNum = invNum;
    }

    public void setPpEnterprise(String ppEnterprise) {
        this.ppEnterprise = ppEnterprise;
    }


    public String getPpDifferential() {
        return ppDifferential;
    }


    public void setPpDifferential(String ppDifferential) {
        this.ppDifferential = ppDifferential;
    }


    public String getPpDifferentialshop() {
        return ppDifferentialshop;
    }


    public void setPpDifferentialshop(String ppDifferentialshop) {
        this.ppDifferentialshop = ppDifferentialshop;
    }


    public String getPpWeb() {
        return ppWeb;
    }


    public void setPpWeb(String ppWeb) {
        this.ppWeb = ppWeb;
    }


    public String getPpOther() {
        return ppOther;
    }


    public void setPpOther(String ppOther) {
        this.ppOther = ppOther;
    }


    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getManualUrl() {
        return manualUrl;
    }

    public void setManualUrl(String manualUrl) {
        this.manualUrl = manualUrl;
    }

    public String getPropagandaUrl() {
        return propagandaUrl;
    }

    public void setPropagandaUrl(String propagandaUrl) {
        this.propagandaUrl = propagandaUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Date getPackagingDate() {
        return packagingDate;
    }

    public void setPackagingDate(Date packagingDate) {
        this.packagingDate = packagingDate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShowweixin() {
        return showweixin;
    }

    public void setShowweixin(String showweixin) {
        this.showweixin = showweixin;
    }

    public String getWeiDianType() {
        return weiDianType;
    }

    public void setWeiDianType(String weiDianType) {
        this.weiDianType = weiDianType;
    }

    public String getCertificateCost() {
        return certificateCost;
    }

    public void setCertificateCost(String certificateCost) {
        this.certificateCost = certificateCost;
    }

    public int getStockSize() {
        return stockSize;
    }

    public void setStockSize(int stockSize) {
        this.stockSize = stockSize;
    }

    public int getMonthSales() {
        return monthSales;
    }

    public void setMonthSales(int monthSales) {
        this.monthSales = monthSales;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getXmtype() {
        return xmtype;
    }


    public void setXmtype(String xmtype) {
        this.xmtype = xmtype;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getXmtypename() {
        return xmtypename;
    }


    public void setXmtypename(String xmtypename) {
        this.xmtypename = xmtypename;
    }


    public String getRemark() {
        return remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getGoodsNum() {
        return goodsNum;
    }


    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }


    public String getStandard() {
        return standard;
    }


    public void setStandard(String standard) {
        this.standard = standard;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public String getVirtual() {
        return virtual;
    }


    public void setVirtual(String virtual) {
        this.virtual = virtual;
    }


    public int getSorting() {
        return sorting;
    }


    public void setSorting(int sorting) {
        this.sorting = sorting;
    }


    public String getLogo() {
        return logo;
    }


    public void setLogo(String logo) {
        this.logo = logo;
    }


    public String getPpstatus() {
        return ppstatus;
    }


    public void setPpstatus(String ppstatus) {
        this.ppstatus = ppstatus;
    }


    public String getShangjiastatus() {
        return shangjiastatus;
    }


    public void setShangjiastatus(String shangjiastatus) {
        this.shangjiastatus = shangjiastatus;
    }


    public String getDepartmentState() {
        return departmentState;
    }


    public void setDepartmentState(String departmentState) {
        this.departmentState = departmentState;
    }


    public String getProjectType() {
        return projectType;
    }


    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }


    public String getFiveClear() {
        return fiveClear;
    }


    public void setFiveClear(String fiveClear) {
        this.fiveClear = fiveClear;
    }


    public String getPptype() {
        return pptype;
    }


    public String getPpcestuer() {
        return ppcestuer;
    }


    public void setPpcestuer(String ppcestuer) {
        this.ppcestuer = ppcestuer;
    }


    public void setPptype(String pptype) {
        this.pptype = pptype;
    }


    public String getUpdateUser() {
        return updateUser;
    }


    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }


    public String getPhoto() {
        return photo;
    }


    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public String getGiveNumber() {
        return giveNumber;
    }


    public void setGiveNumber(String giveNumber) {
        this.giveNumber = giveNumber;
    }


    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getPpwhether() {
        return ppwhether;
    }


    public void setPpwhether(String ppwhether) {
        this.ppwhether = ppwhether;
    }


    public String getYjstatus() {
        return yjstatus;
    }


    public void setYjstatus(String yjstatus) {
        this.yjstatus = yjstatus;
    }


    public String getYjstaname() {
        return yjstaname;
    }


    public void setYjstaname(String yjstaname) {
        this.yjstaname = yjstaname;
    }


    public Date getYjdate() {
        return yjdate;
    }


    public void setYjdate(Date yjdate) {
        this.yjdate = yjdate;
    }


    public String getYjstaid() {
        return yjstaid;
    }


    public void setYjstaid(String yjstaid) {
        this.yjstaid = yjstaid;
    }


    public String getTradeCode() {
        return tradeCode;
    }


    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
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


    public String getParentName() {
        return parentName;
    }


    public void setParentName(String parentName) {
        this.parentName = parentName;
    }


    public String getTradeName() {
        return tradeName;
    }


    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }


    public String getBarCode() {
        return barCode;
    }


    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }


    public String getModel() {
        return model;
    }


    public void setModel(String model) {
        this.model = model;
    }


    public String getBrand() {
        return brand;
    }


    public void setBrand(String brand) {
        this.brand = brand;
    }


    public String getVariableID() {
        return variableID;
    }


    public void setVariableID(String variableID) {
        this.variableID = variableID;
    }


    public String getSubjectName() {
        return subjectName;
    }


    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }


    public String getSubjectID() {
        return subjectID;
    }


    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }


    public String getTradeID() {
        return tradeID;
    }


    public void setTradeID(String tradeID) {
        this.tradeID = tradeID;
    }


    public String getDelStatus() {
        return delStatus;
    }


    public void setDelStatus(String delStatus) {
        this.delStatus = delStatus;
    }


    public String getProductstate() {
        return productstate;
    }

    public void setProductstate(String productstate) {
        this.productstate = productstate;
    }

    public String getShowdate() {
        return showdate;
    }

    public void setShowdate(String showdate) {
        this.showdate = showdate;
    }

    public String getNewcontent() {
        return newcontent;
    }

    public void setNewcontent(String newcontent) {
        this.newcontent = newcontent;
    }

    public String getHierarchical() {
        return hierarchical;
    }

    public void setHierarchical(String hierarchical) {
        this.hierarchical = hierarchical;
    }

    public String getAccessories() {
        return accessories;
    }

    public void setAccessories(String accessories) {
        this.accessories = accessories;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getPrimaryField() {
        return primaryField;
    }

    public void setPrimaryField(String primaryField) {
        this.primaryField = primaryField;
    }

    public String getTwoLevelField() {
        return twoLevelField;
    }

    public void setTwoLevelField(String twoLevelField) {
        this.twoLevelField = twoLevelField;
    }

    public String getAmendStaffID() {
        return amendStaffID;
    }

    public void setAmendStaffID(String amendStaffID) {
        this.amendStaffID = amendStaffID;
    }

    public String getAssemble() {
        return assemble;
    }

    public void setAssemble(String assemble) {
        this.assemble = assemble;
    }

    public String getTemporary() {
        return temporary;
    }

    public void setTemporary(String temporary) {
        this.temporary = temporary;
    }

    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getQualified() {
        return qualified;
    }

    public void setQualified(String qualified) {
        this.qualified = qualified;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public String getIsScale() {
        return isScale;
    }

    public void setIsScale(String isScale) {
        this.isScale = isScale;
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

    public String getWholesaleStatus() {
        return wholesaleStatus;
    }

    public void setWholesaleStatus(String wholesaleStatus) {
        this.wholesaleStatus = wholesaleStatus;
    }

    public String getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(String vipStatus) {
        this.vipStatus = vipStatus;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public List<BaseBean> getPmlist() {
        return pmlist;
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

    public void setPmlist(List<BaseBean> pmlist) {
        this.pmlist = pmlist;
    }

    public String getDepotID() {
        return depotID;
    }

    public void setDepotID(String depotID) {
        this.depotID = depotID;
    }

    public String getDepotName() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName;
    }

    public String getDepotCoding() {
        return depotCoding;
    }

    public void setDepotCoding(String depotCoding) {
        this.depotCoding = depotCoding;
    }

    public String getStanpro() {
        return stanpro;
    }

    public void setStanpro(String stanpro) {
        this.stanpro = stanpro;
    }

    public String getSingleWeight() {
        return singleWeight;
    }

    public void setSingleWeight(String singleWeight) {
        this.singleWeight = singleWeight;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    @Override
    public String toString() {
        return "\"ProductPackaging\":{" +
                "\"ppKey\":\"" + ppKey + '\"' +
                ", \"ppID\":\"" + ppID + '\"' +
                ", \"goodsID\":\"" + goodsID + '\"' +
                ", \"companyID\":\"" + companyID + '\"' +
                ", \"organizationID\":\"" + organizationID + '\"' +
                ", \"staffID\":\"" + staffID + '\"' +
                ", \"staffName\":\"" + staffName + '\"' +
                ", \"amendStaffID\":\"" + amendStaffID + '\"' +
                ", \"sccid\":\"" + sccid + '\"' +
                ", \"packagingDate\":" + packagingDate +
                ", \"goodsName\":\"" + goodsName + '\"' +
                ", \"quantity\":\"" + quantity + '\"' +
                ", \"money\":\"" + money + '\"' +
                ", \"weight\":\"" + weight + '\"' +
                ", \"price\":\"" + price + '\"' +
                ", \"manualUrl\":\"" + manualUrl + '\"' +
                ", \"propagandaUrl\":\"" + propagandaUrl + '\"' +
                ", \"fileUrl\":\"" + fileUrl + '\"' +
                ", \"parentId\":\"" + parentId + '\"' +
                ", \"parentName\":\"" + parentName + '\"' +
                ", \"image\":\"" + image + '\"' +
                ", \"photo\":\"" + photo + '\"' +
                ", \"showweixin\":\"" + showweixin + '\"' +
                ", \"weiDianType\":\"" + weiDianType + '\"' +
                ", \"certificateCost\":\"" + certificateCost + '\"' +
                ", \"ppstatus\":\"" + ppstatus + '\"' +
                ", \"shangjiastatus\":\"" + shangjiastatus + '\"' +
                ", \"pptype\":\"" + pptype + '\"' +
                ", \"ppcestuer\":\"" + ppcestuer + '\"' +
                ", \"ppEnterprise\":\"" + ppEnterprise + '\"' +
                ", \"ppDifferential\":\"" + ppDifferential + '\"' +
                ", \"ppDifferentialshop\":\"" + ppDifferentialshop + '\"' +
                ", \"ppWeb\":\"" + ppWeb + '\"' +
                ", \"ppOther\":\"" + ppOther + '\"' +
                ", \"xmtype\":\"" + xmtype + '\"' +
                ", \"xmtypename\":\"" + xmtypename + '\"' +
                ", \"sorting\":" + sorting +
                ", \"stockSize\":" + stockSize +
                ", \"monthSales\":" + monthSales +
                ", \"productDetail\":\"" + productDetail + '\"' +
                ", \"accessories\":\"" + accessories + '\"' +
                ", \"category\":\"" + category + '\"' +
                ", \"goodsNum\":\"" + goodsNum + '\"' +
                ", \"remark\":\"" + remark + '\"' +
                ", \"standard\":\"" + standard + '\"' +
                ", \"type\":\"" + type + '\"' +
                ", \"virtual\":\"" + virtual + '\"' +
                ", \"logo\":\"" + logo + '\"' +
                ", \"departmentState\":\"" + departmentState + '\"' +
                ", \"projectType\":\"" + projectType + '\"' +
                ", \"fiveClear\":\"" + fiveClear + '\"' +
                ", \"updateUser\":\"" + updateUser + '\"' +
                ", \"giveNumber\":\"" + giveNumber + '\"' +
                ", \"createTime\":" + createTime +
                ", \"ppwhether\":\"" + ppwhether + '\"' +
                ", \"yjstatus\":\"" + yjstatus + '\"' +
                ", \"wholesaleStatus\":\"" + wholesaleStatus + '\"' +
                ", \"vipStatus\":\"" + vipStatus + '\"' +
                ", \"activityStatus\":\"" + activityStatus + '\"' +
                ", \"yjstaname\":\"" + yjstaname + '\"' +
                ", \"yjstaid\":\"" + yjstaid + '\"' +
                ", \"yjdate=" + yjdate +
                ", \"tradeCode\":\"" + tradeCode + '\"' +
                ", \"producttype\":\"" + producttype + '\"' +
                ", \"productCode\":\"" + productCode + '\"' +
                ", \"tradeName\":\"" + tradeName + '\"' +
                ", \"tradeID\":\"" + tradeID + '\"' +
                ", \"barCode\":\"" + barCode + '\"' +
                ", \"model\":\"" + model + '\"' +
                ", \"brand\":\"" + brand + '\"' +
                ", \"variableID\":\"" + variableID + '\"' +
                ", \"subjectName\":\"" + subjectName + '\"' +
                ", \"subjectID\":\"" + subjectID + '\"' +
                ", \"delStatus\":\"" + delStatus + '\"' +
                ", \"productstate\":\"" + productstate + '\"' +
                ", \"hierarchical\":\"" + hierarchical + '\"' +
                ", \"field\":\"" + field + '\"' +
                ", \"primaryField\":\"" + primaryField + '\"' +
                ", \"twoLevelField\":\"" + twoLevelField + '\"' +
                ", \"assemble\":\"" + assemble + '\"' +
                ", \"temporary\":\"" + temporary + '\"' +
                ", \"qualified\":\"" + qualified + '\"' +
                ", \"review\":\"" + review + '\"' +
                ", \"carModel\":\"" + carModel + '\"' +
                ", \"categoryId\":\"" + categoryId + '\"' +
                ", \"categoryName\":\"" + categoryName + '\"' +
                ", \"platform\":\"" + platform + '\"' +
                ", \"isScale\":\"" + isScale + '\"' +
                ", \"invNum\":\"" + invNum + '\"' +
                ", \"gradeid\":\"" + gradeid + '\"' +
                ", \"gradeName\":\"" + gradeName + '\"' +
                ", \"ccompanyID\":\"" + ccompanyID + '\"' +
                ", \"ccompanyName\":\"" + ccompanyName + '\"' +
                ", \"depotID\":\"" + depotID + '\"' +
                ", \"depotName\":\"" + depotName + '\"' +
                ", \"depotCoding\":\"" + depotCoding + '\"' +
                ", \"stanpro\":\"" + stanpro + '\"' +
                ", \"singleWeight\":\"" + singleWeight + '\"' +
                ", \"paytype\":\"" + paytype + '\"' +
                ", \"showdate\":\"" + showdate + '\"' +
                ", \"newcontent\":\"" + newcontent + '\"' +
                ", \"pmlist\":" + pmlist +
                "},";
    }

}
