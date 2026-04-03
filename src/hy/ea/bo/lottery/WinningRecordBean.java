package hy.ea.bo.lottery;

import hy.plat.bo.BaseBean;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/3/15 0015.
 */
public class WinningRecordBean implements BaseBean
{
    private String winningRecordId;
    private String recordId;
    private String poolId;
    private String orderNum;
    private String isDraws;//是否领取 0未领取；1已领取
    private Timestamp winDate;

    public String getWinningRecordId ()
    {
        return winningRecordId;
    }

    public void setWinningRecordId (String winningRecordId)
    {
        this.winningRecordId = winningRecordId;
    }

    public String getRecordId ()
    {
        return recordId;
    }

    public void setRecordId (String recordId)
    {
        this.recordId = recordId;
    }

    public String getPoolId ()
    {
        return poolId;
    }

    public void setPoolId (String poolId)
    {
        this.poolId = poolId;
    }

    public String getOrderNum ()
    {
        return orderNum;
    }

    public void setOrderNum (String orderNum)
    {
        this.orderNum = orderNum;
    }

    public String getIsDraws ()
    {
        return isDraws;
    }

    public void setIsDraws (String isDraws)
    {
        this.isDraws = isDraws;
    }

    public Timestamp getWinDate ()
    {
        return winDate;
    }

    public void setWinDate (Timestamp winDate) {
        this.winDate = winDate;
    }
}
