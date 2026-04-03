package hy.ea.bo.lottery;

import hy.plat.bo.BaseBean;

/**
 * Created by Administrator on 2017/3/15 0015.
 */
public class RelationPrizeCouponBean implements BaseBean
{
    private String id;
    private String couponId;
    private String prizeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }

}
