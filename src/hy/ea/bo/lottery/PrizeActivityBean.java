package hy.ea.bo.lottery;

import java.sql.Timestamp;

import hy.plat.bo.BaseBean;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class PrizeActivityBean implements BaseBean{
    private String activityKey;
    private String activityId;

    //活动范围：0全网,1会员
    private String activityRange;

    //活动类型0:抽奖1：签到2:会议活动
    private String activityType;

    //活动名称
    private String activityName;

    //开始时间
    private Timestamp startingTime;

    //结束时间
    private Timestamp endTime;

    //活动状态（0：发布   1：活动结束  9：活动删除）
    private String status;

    //活动公司
    private String companyId;

    //活动图片
    private String activityImg;

    //活动说明
    private String activityDesc;

    //0:完全公开1：公司内公开2：部分公开（扩展表）
    private String showStatus;

    //2017-07-22 会议活动地点
    private String address;

    private Integer bonusPoints;//每次抽奖所需积分

    public String getActivityKey() {
        return activityKey;
    }

    public void setActivityKey(String activityKey) {
        this.activityKey = activityKey;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityRange() {
        return activityRange;
    }

    public void setActivityRange(String activityRange) {
        this.activityRange = activityRange;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Timestamp getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Timestamp startingTime) {
        this.startingTime = startingTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

    public String getActivityImg()
    {
        return activityImg;
    }

    public void setActivityImg(String activityImg)
    {
        this.activityImg = activityImg;
    }

    public String getActivityDesc()
    {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc)
    {
        this.activityDesc = activityDesc;
    }

    public String getShowStatus()
    {
        return showStatus;
    }

    public void setShowStatus(String showStatus)
    {
        this.showStatus = showStatus;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(Integer bonusPoints) {
        this.bonusPoints = bonusPoints;
    }
}
