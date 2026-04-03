package mobile.tiantai.android.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;
/**
 * 
 * 微信活动表
 * @author 
 */

public class DtActivity implements BaseBean{
	
	private String id;//活动id
	private String theme;//活动主题
	private String content;//活动内容
	private Date starttime;//活动开始时间
	private Date endtime;//活动结束时间
	private String activityType;//活动类型：会议、活动、公告、收费会议
	private String weixinCompanyId;//公司id
	private Date publishTime;//活动发布日期
	private String staffID;//发布活动人的id
	private String ppid;//产品id，和ProductPackaging中ppid对应
	private String inforType;//区分微信活动00 和 公共信息01 02游客发布活动
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	public String getWeixinCompanyId() {
		return weixinCompanyId;
	}
	public void setWeixinCompanyId(String weixinCompanyId) {
		this.weixinCompanyId = weixinCompanyId;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getPpid() {
		return ppid;
	}
	public void setPpid(String ppid) {
		this.ppid = ppid;
	}
	public String getInforType() {
		return inforType;
	}
	public void setInforType(String inforType) {
		this.inforType = inforType;
	}	

	
}