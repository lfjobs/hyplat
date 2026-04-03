package hy.ea.bo.production.scmanage;

import hy.plat.bo.BaseBean;


/**
 * 针对视频的点赞评论数记录
 *
 */
public class ProductCommentVedio implements BaseBean{
	private String pcvKey;  		//主键
	private String pcvID;  		//业务主键
	private String ppid;		// 如果是文章中发布的视频会有ppid
	private String videoID;//视频ID
	private int praisev; 		//视频被赞数
	private int plcountv;			//视频评论数 
	private int sharev;//分享视频数
	public String getPcvKey() {
		return pcvKey;
	}
	public void setPcvKey(String pcvKey) {
		this.pcvKey = pcvKey;
	}
	public String getPcvID() {
		return pcvID;
	}
	public void setPcvID(String pcvID) {
		this.pcvID = pcvID;
	}
	public String getPpid() {
		return ppid;
	}
	public void setPpid(String ppid) {
		this.ppid = ppid;
	}
 
	public String getVideoID() {
		return videoID;
	}
	public void setVideoID(String videoID) {
		this.videoID = videoID;
	}
	public int getPraisev() {
		return praisev;
	}
	public void setPraisev(int praisev) {
		this.praisev = praisev;
	}
	public int getPlcountv() {
		return plcountv;
	}
	public void setPlcountv(int plcountv) {
		this.plcountv = plcountv;
	}
	public int getSharev() {
		return sharev;
	}
	public void setSharev(int sharev) {
		this.sharev = sharev;
	}
	

	

}