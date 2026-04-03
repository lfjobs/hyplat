package hy.ea.bo.lottery;

import hy.plat.bo.BaseBean;

/**
 * Created by Administrator on 2017/3/15 0015.
 */
public class LotteryRuleBean implements BaseBean
{
    private String ruleId;
    private String lotteryMode;
    private String lotteryStyle;
    private Long freeNum;
    private String activityId;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getLotteryMode() {
        return lotteryMode;
    }

    public void setLotteryMode(String lotteryMode) {
        this.lotteryMode = lotteryMode;
    }

    public String getLotteryStyle() {
        return lotteryStyle;
    }

    public void setLotteryStyle(String lotteryStyle) {
        this.lotteryStyle = lotteryStyle;
    }

    public Long getFreeNum() {
        return freeNum;
    }

    public void setFreeNum(Long freeNum) {
        this.freeNum = freeNum;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

}
