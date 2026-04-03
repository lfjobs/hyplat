package hy.ea.collage.action.dto;

import java.io.Serializable;

public class GoodsDto implements Serializable {
    private String goodsId;
    private String ppId;
    private String goodsName;
    private String image;
    private String price;
    private String companyId;
    private String companyName;
    private String ccompanyId;
    private String priceType;

    public GoodsDto() {
    }

    public GoodsDto(String goodsId, String ppId, String goodsName, String image, String price, String companyId, String companyName, String ccompanyId, String priceType) {
        this.goodsId = goodsId;
        this.ppId = ppId;
        this.goodsName = goodsName;
        this.image = image;
        this.price = price;
        this.companyId = companyId;
        this.ccompanyId = ccompanyId;
        this.priceType = priceType;
    }

    /**
     * 短视频已绑定商品
     * @param cols
     * @return
     */
    public static GoodsDto fromVideoGoodsCols(Object[] cols) {
        GoodsDto dto = new GoodsDto();
        dto.setGoodsId(isNull(cols[0]));
        dto.setPpId(isNull(cols[1]));
        dto.setGoodsName(isNull(cols[2]));
        dto.setImage(isNull(cols[3]));
        dto.setPrice(isNull(cols[4]));
        dto.setCompanyId(isNull(cols[5]));
        dto.setCcompanyId(isNull(cols[6]));
        dto.setPriceType(isNull(cols[7]));
        return dto;
    }

    /**
     * 短视频可绑定商品
     * @param cols
     * @return
     */
    public static GoodsDto fromStoreGoodsCols(Object[] cols) {
        GoodsDto dto = new GoodsDto();
        dto.setGoodsId(isNull(cols[0]));
        dto.setPpId(isNull(cols[1]));
        dto.setGoodsName(isNull(cols[2]));
        dto.setImage(isNull(cols[3]));
        dto.setPrice(isNull(cols[4]));
        dto.setCompanyId(isNull(cols[5]));
        dto.setCcompanyId(isNull(cols[6]));
        dto.setCompanyName(isNull(cols[7]));
        return dto;
    }

    private static String isNull(Object tep) {
        if (tep == null || "null".equals(tep)) {
            return "";
        } else {
            return String.valueOf(tep);
        }
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCcompanyId() {
        return ccompanyId;
    }

    public void setCcompanyId(String ccompanyId) {
        this.ccompanyId = ccompanyId;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }
}
