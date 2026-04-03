package hy.ea.bo.lottery;

import hy.plat.bo.BaseBean;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/3/15 0015.
 */
public class LotteryRecordBean implements BaseBean
{
    private String recordId;
    private String lotteryMan;
    private Timestamp recordDate;
    private String lotteryResult;
    private String lotteryStyle;
    private String activityId;


    public String getRecordId ()
    {
        return recordId;
    }

    public void setRecordId (String recordId)
    {
        this.recordId = recordId;
    }

    public String getLotteryMan ()
    {
        return lotteryMan;
    }

    public void setLotteryMan (String lotteryMan)
    {
        this.lotteryMan = lotteryMan;
    }

    public Timestamp getRecordDate ()
    {
        return recordDate;
    }

    public void setRecordDate (Timestamp recordDate)
    {
        this.recordDate = recordDate;
    }

    public String getLotteryResult ()
    {
        return lotteryResult;
    }

    public void setLotteryResult (String lotteryResult)
    {
        this.lotteryResult = lotteryResult;
    }

    public String getLotteryStyle ()
    {
        return lotteryStyle;
    }

    public void setLotteryStyle (String lotteryStyle)
    {
        this.lotteryStyle = lotteryStyle;
    }

    public String getActivityId ()
    {
        return activityId;
    }

    public void setActivityId (String activityId)
    {
        this.activityId = activityId;
    }
}
