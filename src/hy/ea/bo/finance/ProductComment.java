package hy.ea.bo.finance;

import java.util.Date;
import hy.plat.bo.BaseBean;


/**
 * 产品评论表
 *
 */
public class ProductComment implements BaseBean{
	private String pcKey;  		//主键
	private String pcID;  		//业务主键
	private String ppid;		//物品表主键
	private String goodsId;   	//产品ID
	private String StaffId;
	private String content;		//评论内容
	private int praise; 		//被赞数
	private Date commentdate;	//评论时间
	private int count;			//评论数
	private String pcPID;		//评论ID
	private String type;		//类型 0评论  1点赞  2收藏  3公告查看
	private String ispraise;	//是否点赞  0未点赞 1已点赞
	private String iscollect;	//是否收藏  0未收藏 1已收藏
	private String isread;		//是否查看过公告  0未查看 1已经查看
	private String toStaffId;   //上级评论人员id
	private String whichFloor;  //第几楼
	private String vedioID;//针对视频的点赞或者评论
	public String getPcKey() {
		return pcKey;
	}
	public void setPcKey(String pcKey) {
		this.pcKey = pcKey;
	}
	public String getPcID() {
		return pcID;
	}
	public void setPcID(String pcID) {
		this.pcID = pcID;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getStaffId() {
		return StaffId;
	}
	public void setStaffId(String staffId) {
		StaffId = staffId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPraise() {
		return praise;
	}
	public void setPraise(int praise) {
		this.praise = praise;
	}
	public Date getCommentdate() {
		return commentdate;
	}
	public void setCommentdate(Date commentdate) {
		this.commentdate = commentdate;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getPcPID() {
		return pcPID;
	}
	public void setPcPID(String pcPID) {
		this.pcPID = pcPID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIspraise() {
		return ispraise;
	}
	public void setIspraise(String ispraise) {
		this.ispraise = ispraise;
	}
	public String getIscollect() {
		return iscollect;
	}
	public void setIscollect(String iscollect) {
		this.iscollect = iscollect;
	}
	public String getIsread() {
		return isread;
	}
	public void setIsread(String isread) {
		this.isread = isread;
	}
	public String getPpid() {
		return ppid;
	}
	public void setPpid(String ppid) {
		this.ppid = ppid;
	}
	public String getToStaffId() {
		return toStaffId;
	}
	public void setToStaffId(String toStaffId) {
		this.toStaffId = toStaffId;
	}
	public String getWhichFloor() {
		return whichFloor;
	}
	public void setWhichFloor(String whichFloor) {
		this.whichFloor = whichFloor;
	}
	public String getVedioID() {
		return vedioID;
	}
	public void setVedioID(String vedioID) {
		this.vedioID = vedioID;
	}
	
	
	
}