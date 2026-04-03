package hy.ea.bo.lottery;

import hy.plat.bo.BaseBean;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class SignScoreBean implements BaseBean{

    private String sckey;
    private String scid;
    private String activityId;
    private Integer score;

    //2017-07-11  jcy
    private Integer count;  //签到次数

    public String getSckey() {
        return sckey;
    }

    public void setSckey(String sckey) {
        this.sckey = sckey;
    }

    public String getScid() {
        return scid;
    }

    public void setScid(String scid) {
        this.scid = scid;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
