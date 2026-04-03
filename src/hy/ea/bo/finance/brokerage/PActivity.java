package hy.ea.bo.finance.brokerage;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2018-12-19.
 */
public class PActivity implements BaseBean {
    private String activityKey;
    private String activityId;//活动id
    private String activityName;//活动名称
    private String activityDescribe;//活动描述
    private String activityPicture;//活动图片url
    private Date startTime;//活动起始时间
    private Date endTime;//活动结束时间
    private String principal;//设置活动责任人
    private Date addTimes;//设置[添加]时间
    private Date updateTimes;//修改时间
    private String state;//活动状态[00:初始 01:已开启 02:暂停 03:结束 04:删除]
    private String type;//活动类型  [00:普通活动  01:特价活动]
    private String companyId;//公司id

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

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescribe() {
        return activityDescribe;
    }

    public void setActivityDescribe(String activityDescribe) {
        this.activityDescribe = activityDescribe;
    }

    public String getActivityPicture() {
        return activityPicture;
    }

    public void setActivityPicture(String activityPicture) {
        this.activityPicture = activityPicture;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Date getAddTimes() {
        return addTimes;
    }

    public void setAddTimes(Date addTimes) {
        this.addTimes = addTimes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Date getUpdateTimes() {
        return updateTimes;
    }

    public void setUpdateTimes(Date updateTimes) {
        this.updateTimes = updateTimes;
    }

    @Override
    public String toString() {
        return "PActivity{" +
                "activityKey='" + activityKey + '\'' +
                ", activityId='" + activityId + '\'' +
                ", activityName='" + activityName + '\'' +
                ", activityDescribe='" + activityDescribe + '\'' +
                ", activityPicture='" + activityPicture + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", principal='" + principal + '\'' +
                ", addTimes=" + addTimes +
                ", updateTimes=" + updateTimes +
                ", state='" + state + '\'' +
                ", type='" + type + '\'' +
                ", companyId='" + companyId + '\'' +
                '}';
    }
}
