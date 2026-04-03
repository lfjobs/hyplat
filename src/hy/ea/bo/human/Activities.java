package hy.ea.bo.human;


import java.io.File;
import java.util.Date;

import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
/*
 * 个人，公司活动ljc       文章、论坛
 * */
public class Activities implements BaseBean{
	private String activitiesKey;
	private String activitiesID;
	private String title;//活动标题
	private String describe;//活动描述
	private String picture;//活动图片
	private Date releaseTime;//发布时间
	private String type;//类型00公司01个人
	private String ccompanyId;//往来单位id
	private String staffId;
	private String author;//作者
	private File pic;
	private String picFileName;
	private String shareLink;    //个人文章分享链接
	private String category;     //类别       00：活动  01：论坛  02：文章  03：生活兴趣 04：风格
	private String txturl;//txt路径
	
	//非数据库字段
	private String filepath;
	private String filedesc;
	private String showtime;
	
	public Activities() {
		super();
	}


	public Activities(String activitiesID, String title, String describe, String picture, String shareLink,String author,Date releaseTime ) {
		super();
		this.activitiesID = activitiesID;
		this.title = title;
		this.describe = describe;
		this.picture = picture;
		this.shareLink = shareLink;
		this.author=author;
		if(releaseTime!=null){
			this.showtime=Utilities.getDateString(releaseTime, "yyyy-MM-dd HH:mm:ss");
		}else{
			this.showtime="";
		}
		
	}
	
	
	public Activities(String activitiesID, String title,String shareLink,String txturl,String author,Date releaseTime) {
		super();
		this.activitiesID = activitiesID;
		this.title = title;		
		this.shareLink = shareLink;	
		this.txturl=txturl;
		this.author=author;
		if(releaseTime!=null){
			this.showtime=Utilities.getDateString(releaseTime, "yyyy-MM-dd HH:mm:ss");
		}else{
			this.showtime="";
		}
	}


	public Activities(String activitiesKey, String activitiesID, String title, String describe, String picture,
			Date releaseTime, String type, String ccompanyId, String staffId, String author, File pic,
			String picFileName, String shareLink, String category) {
		super();
		this.activitiesKey = activitiesKey;
		this.activitiesID = activitiesID;
		this.title = title;
		this.describe = describe;
		this.picture = picture;
		this.releaseTime = releaseTime;
		this.type = type;
		this.ccompanyId = ccompanyId;
		this.staffId = staffId;
		this.author = author;
		this.pic = pic;
		this.picFileName = picFileName;
		this.shareLink = shareLink;
		this.category = category;
	}
	public String getActivitiesKey() {
		return activitiesKey;
	}
	public void setActivitiesKey(String activitiesKey) {
		this.activitiesKey = activitiesKey;
	}
	public String getActivitiesID() {
		return activitiesID;
	}
	public void setActivitiesID(String activitiesID) {
		this.activitiesID = activitiesID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCcompanyId() {
		return ccompanyId;
	}
	public void setCcompanyId(String ccompanyId) {
		this.ccompanyId = ccompanyId;
	}
	public File getPic() {
		return pic;
	}
	public void setPic(File pic) {
		this.pic = pic;
	}
	public String getPicFileName() {
		return picFileName;
	}
	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getShareLink() {
		return shareLink;
	}
	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFiledesc() {
		return filedesc;
	}
	public void setFiledesc(String filedesc) {
		this.filedesc = filedesc;
	}
	public String getShowtime() {
		return showtime;
	}
	public void setShowtime(String showtime) {
		this.showtime = showtime;
	}
	public String getTxturl() {
		return txturl;
	}
	public void setTxturl(String txturl) {
		this.txturl = txturl;
	}
	
	
}
