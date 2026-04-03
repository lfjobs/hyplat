package hy.ea.bo.lottery;

import hy.plat.bo.BaseBean;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
public class RelationIntegralProductBean implements BaseBean
{
    private String id;
    private String poolId;
    private Long prizePoint;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public Long getPrizePoint() {
        return prizePoint;
    }

    public void setPrizePoint(Long prizePoint) {
        this.prizePoint = prizePoint;
    }


}
