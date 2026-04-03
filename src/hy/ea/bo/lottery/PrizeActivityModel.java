package hy.ea.bo.lottery;

import com.fasterxml.jackson.databind.deser.Deserializers;
import hy.plat.bo.BaseBean;

import java.sql.Timestamp;
import java.util.List;

/**
 * 抽奖活动模型实体
 * @author [zg]
 * @version [v1.0,2017/4/1 ]
 * @since [抽奖模块]eActivityBean
 */
public class PrizeActivityModel implements BaseBean
{
    private String activityKey;
    private String activityId;
    private String activityRange;
    private String activityType;
    private String activityName;
    private Timestamp startingTime;
    private Timestamp endTime;
    private String status;
    private String companyId;
    private String activityImg;
    private String activityDesc;
    private String showStatus;//0:完全公开1：公司内公开2：部分公开（扩展表）

    private String strStartingTime;//开始时间
    private String strEndTime;//结束时间

    private String score;//签到积分数
    private String poolId; //多个奖品池id
    private String imageDesc;//活动说明图片
    private List<BaseBean> prizePool;//奖品池
    private List<BaseBean> prizeDesc;//活动说明图片
    private List<BaseBean> ticket;  //会议活动票券信息
    private String index;
    private String staffId;
    
    //2017-07-02  jcy
    private String count;  //每人每天签到次数
    private String address;  //活动地点

    private Integer bonusPoints;//每次抽奖所需积分

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public String getStrStartingTime()
    {
        return strStartingTime;
    }

    public void setStrStartingTime(String strStartingTime)
    {
        this.strStartingTime = strStartingTime;
    }

    public String getStrEndTime()
    {
        return strEndTime;
    }

    public void setStrEndTime(String strEndTime)
    {
        this.strEndTime = strEndTime;
    }

    public String getShowStatus()
    {
        return showStatus;
    }

    public void setShowStatus(String showStatus)
    {
        this.showStatus = showStatus;
    }

    public String getScore ()
    {
        return score;
    }

    public void setScore (String score)
    {
        this.score = score;
    }

    public String getPoolId ()
    {
        return poolId;
    }

    public void setPoolId (String poolId)
    {
        this.poolId = poolId;
    }

    public String getImageDesc ()
    {
        return imageDesc;
    }

    public void setImageDesc (String imageDesc)
    {
        this.imageDesc = imageDesc;
    }

    public List<BaseBean> getPrizePool ()
    {
        return prizePool;
    }

    public void setPrizePool (List<BaseBean> prizePool)
    {
        this.prizePool = prizePool;
    }

    public List<BaseBean> getPrizeDesc ()
    {
        return prizeDesc;
    }

    public void setPrizeDesc (List<BaseBean> prizeDesc)
    {
        this.prizeDesc = prizeDesc;
    }

    public String getIndex ()
    {
        return index;
    }

    public void setIndex (String index)
    {
        this.index = index;
    }

    public String getStaffId ()
    {
        return staffId;
    }

    public void setStaffId (String staffId)
    {
        this.staffId = staffId;
    }

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

    public List<BaseBean> getTicket() {
        return ticket;
    }

    public void setTicket(List<BaseBean> ticket) {
        this.ticket = ticket;
    }

    public Integer getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(Integer bonusPoints) {
        this.bonusPoints = bonusPoints;
    }
}
