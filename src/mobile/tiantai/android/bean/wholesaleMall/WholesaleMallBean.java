package mobile.tiantai.android.bean.wholesaleMall;

import java.util.List;

/**
 * 商品信息bean
 * Created by Administrator on 2019-11-04.
 */
public class WholesaleMallBean {
    private String ppid;// 产品id
    private Double wholesale; //批发价
    private String wholesaleId; //批发价id
    private Double totalWhoPrice;//使用红包后增幅价格
    private Double allPrice;//总价=批发价+使用红包后增幅价格
    private String goodsid;//货物id
    private String goodsName;//货物名称
    private String image;//图片路径
    private String companyid;//公司id
    private String standard;//规格
    private List<AttriProductionBean> proBeanList;//产品颜色规格bean
    private String codeId;//二级商品分类id
    private String barCode;//条码

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


    public List<AttriProductionBean> getProBeanList() {
        return proBeanList;
    }

    public void setProBeanList(List<AttriProductionBean> proBeanList) {
        this.proBeanList = proBeanList;
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

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    /**
     * 无参构造函数
     */
    public WholesaleMallBean() {
    }

    /**
     * 带参构造函数
     *
     * @param ppid          产品id
     * @param wholesale     批发价
     * @param goodsid       货物id
     * @param goodsName     货物名称
     * @param image         图片路径
     * @param companyid     公司id
     * @param standard      规格
     * @param totalWhoPrice 使用红包后增幅价格
     * @param allPrice      总价=批发价+使用红包后增幅价格
     */
    public WholesaleMallBean(String ppid, Double wholesale, String goodsid, String goodsName, String image, String companyid, String standard, Double totalWhoPrice, Double allPrice) {
        this.ppid = ppid;
        this.wholesale = wholesale;
        this.goodsid = goodsid;
        this.goodsName = goodsName;
        this.image = image;
        this.companyid = companyid;
        this.standard = standard;
        this.totalWhoPrice = totalWhoPrice;
        this.allPrice = allPrice;
    }

    /**
     * 带参构造函数
     *
     * @param ppid          产品id
     * @param wholesale     批发价
     * @param goodsid       货物id
     * @param goodsName     货物名称
     * @param image         图片路径
     * @param companyid     公司id
     * @param standard      规格
     * @param totalWhoPrice 使用红包后增幅价格
     * @param allPrice      总价=批发价+使用红包后增幅价格
     * @param proBeanList   产品颜色规格bean
     */
    public WholesaleMallBean(String ppid, Double wholesale, String goodsid, String goodsName, String image, String companyid, String standard, Double totalWhoPrice, Double allPrice, List<AttriProductionBean> proBeanList) {
        this.ppid = ppid;
        this.wholesale = wholesale;
        this.goodsid = goodsid;
        this.goodsName = goodsName;
        this.image = image;
        this.companyid = companyid;
        this.standard = standard;
        this.totalWhoPrice = totalWhoPrice;
        this.allPrice = allPrice;
        this.proBeanList = proBeanList;
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
}
