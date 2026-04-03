package hy.ea.bo.production.scmanage;

import hy.plat.bo.BaseBean;


/**
 * 视频关联的商品
 *
 */
public class ProductOfVedio implements BaseBean{
	private String pvKey;  		//主键
	private String pvID;  		//业务主键
	private String ppid;		// 如果是文章中发布的视频会有ppid
	private String pricetype;//1 零售 2批发
	private String videoID;//视频ID

	public String getPvKey() {
		return pvKey;
	}

	public void setPvKey(String pvKey) {
		this.pvKey = pvKey;
	}

	public String getPvID() {
		return pvID;
	}

	public void setPvID(String pvID) {
		this.pvID = pvID;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public String getPricetype() {
		return pricetype;
	}

	public void setPricetype(String pricetype) {
		this.pricetype = pricetype;
	}

	public String getVideoID() {
		return videoID;
	}

	public void setVideoID(String videoID) {
		this.videoID = videoID;
	}
}