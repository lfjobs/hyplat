package hy.ea.bo.lottery;

import hy.plat.bo.BaseBean;

/**
 * Created by Administrator on 2017/3/15 0015.
 */
public class RelationPrizeOrderBean implements BaseBean
{
    private String id;
    private String prizeId;
    private String productId;
    private String orderNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

}
