package hy.ea.bo.production.scmanage;
import java.util.Date;

import hy.plat.bo.BaseBean;


/**
 * 短视频
 *
 */
public class DSVideo implements BaseBean{
	private String dsKey;  		//主键
	private String videoID;  		//业务主键
	private String ppid;		// 如果是文章中发布的视频会有ppid
	private String vodID;	// 视频点播服务（VOD）videoId
	private String vodProvider; // 视频点播服务提供商
	private String videoURL;//视频URL
    private String staffID;//发布人ID
    private String state;//00：公开；01:好友可见 02：私密：仅自己可以看,09：删除
    private String titleName;//标题 ，如果是文章中发布的视频直接取文章的标题
    private String isTop;//00 不是：01置顶
    private String coverImgUrl;//封面图
    private int readnum;//被阅读量
    private Date createDate;//发布时间
	private String pid;//带货商品ID
	private String location;//定位地址
	public String getDsKey() {
		return dsKey;
	}
	public void setDsKey(String dsKey) {
		this.dsKey = dsKey;
	}
	public String getVideoID() {
		return videoID;
	}
	public void setVideoID(String videoID) {
		this.videoID = videoID;
	}
	public String getPpid() {
		return ppid;
	}
	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public String getVodID() {
		return vodID;
	}

	public void setVodID(String vodID) {
		this.vodID = vodID;
	}

	public String getVodProvider() {
		return vodProvider;
	}

	public void setVodProvider(String vodProvider) {
		this.vodProvider = vodProvider;
	}

	public String getVideoURL() {
		return videoURL;
	}
	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public String getIsTop() {
		return isTop;
	}
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
	public String getCoverImgUrl() {
		return coverImgUrl;
	}
	public void setCoverImgUrl(String coverImgUrl) {
		this.coverImgUrl = coverImgUrl;
	}
	public int getReadnum() {
		return readnum;
	}
	public void setReadnum(int readnum) {
		this.readnum = readnum;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}