package hy.ea.bo.lottery;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by ljc on 2017/4/28 0028.
 * 活动说明图片
 */
public class PrizeActivityDesc implements BaseBean{
    private String activityDescKey;
    private String activityDescId;
    private String activityDescImage;
    private String activityId;
    private Date activityDescDate;

    public String getActivityDescKey ()
    {
        return activityDescKey;
    }

    public void setActivityDescKey (String activityDescKey)
    {
        this.activityDescKey = activityDescKey;
    }

    public String getActivityDescId ()
    {
        return activityDescId;
    }

    public void setActivityDescId (String activityDescId)
    {
        this.activityDescId = activityDescId;
    }

    public String getActivityDescImage ()
    {
        return activityDescImage;
    }

    public void setActivityDescImage (String activityDescImage)
    {
        this.activityDescImage = activityDescImage;
    }

    public String getActivityId ()
    {
        return activityId;
    }

    public void setActivityId (String activityId)
    {
        this.activityId = activityId;
    }

    public Date getActivityDescDate ()
    {
        return activityDescDate;
    }

    public void setActivityDescDate (Date activityDescDate)
    {
        this.activityDescDate = activityDescDate;
    }
}
