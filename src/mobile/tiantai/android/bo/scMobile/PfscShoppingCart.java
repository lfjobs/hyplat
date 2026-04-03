package mobile.tiantai.android.bo.scMobile;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 批发商城购物车
 * Created by Administrator on 2019-11-04.
 */
public class PfscShoppingCart implements BaseBean {
    //货物信息
    private String pscKey;//批发商城购物车key
    private String pscId;//批发商城购物车id
    private String codeId;//二级商品分类id
    private String ppid;// 产品id
    private Double wholesale; //批发价
    private Double totalWhoPrice;//使用红包后增幅价格
    private Double allPrice;//总价=批发价+使用红包后增幅价格
    private String goodsid;//货物id
    private String goodsName;//货物名称
    private String image;//图片路径
    private String companyid;//货物所属公司id
    private String standard;//规格
    private int tjNum;//添加数量
    private int tjFlag;//添加标识0:直接添加1：选中产品规格、颜色添加
    //货物对应产品规格、颜色信息
    private String cmStr;//尺码
    private String cmApid;//尺码id
    private String ysStr;//颜色
    private String ysApid;//颜色id
    private String ftStr;//副图
    private String ftApid;//副图id
    private String spStr;//视频
    private String spApid;//视频id
    //暂时无用字段
    private String imgurl;//图片链接
    private String type;//0尺码,1颜色,2副图 3：视频
    private int sort;//排序 1是产品主图，其他事副图
    private Date createDate;//创建时间

    private String staffComId;//登录人公司id
    private String staffId;//登录人id
    private String wholesaleId;//批发价所在表id
    private String barCode;//条码
    private int yqNum;//已取数量

    /**
     * GET AND SET METHOD
     */
    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }


    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Double getWholesale() {
        return wholesale;
    }

    public void setWholesale(Double wholesale) {
        this.wholesale = wholesale;
    }

    public Double getTotalWhoPrice() {
        return totalWhoPrice;
    }

    public void setTotalWhoPrice(Double totalWhoPrice) {
        this.totalWhoPrice = totalWhoPrice;
    }

    public Double getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(Double allPrice) {
        this.allPrice = allPrice;
    }


    public String getCmStr() {
        return cmStr;
    }

    public void setCmStr(String cmStr) {
        this.cmStr = cmStr;
    }

    public String getYsStr() {
        return ysStr;
    }

    public void setYsStr(String ysStr) {
        this.ysStr = ysStr;
    }

    public String getFtStr() {
        return ftStr;
    }

    public void setFtStr(String ftStr) {
        this.ftStr = ftStr;
    }

    public String getSpStr() {
        return spStr;
    }

    public void setSpStr(String spStr) {
        this.spStr = spStr;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCmApid() {
        return cmApid;
    }

    public void setCmApid(String cmApid) {
        this.cmApid = cmApid;
    }

    public String getYsApid() {
        return ysApid;
    }

    public void setYsApid(String ysApid) {
        this.ysApid = ysApid;
    }

    public String getFtApid() {
        return ftApid;
    }

    public void setFtApid(String ftApid) {
        this.ftApid = ftApid;
    }

    public String getSpApid() {
        return spApid;
    }

    public void setSpApid(String spApid) {
        this.spApid = spApid;
    }

    public int getTjNum() {
        return tjNum;
    }

    public void setTjNum(int tjNum) {
        this.tjNum = tjNum;
    }

    public int getTjFlag() {
        return tjFlag;
    }

    public void setTjFlag(int tjFlag) {
        this.tjFlag = tjFlag;
    }

    public String getPscKey() {
        return pscKey;
    }

    public void setPscKey(String pscKey) {
        this.pscKey = pscKey;
    }

    public String getPscId() {
        return pscId;
    }

    public void setPscId(String pscId) {
        this.pscId = pscId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffComId() {
        return staffComId;
    }

    public void setStaffComId(String staffComId) {
        this.staffComId = staffComId;
    }

    public String getWholesaleId() {
        return wholesaleId;
    }

    public void setWholesaleId(String wholesaleId) {
        this.wholesaleId = wholesaleId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public int getYqNum() {
        return yqNum;
    }

    public void setYqNum(int yqNum) {
        this.yqNum = yqNum;
    }
}
