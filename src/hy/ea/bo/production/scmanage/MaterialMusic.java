package hy.ea.bo.production.scmanage;



import java.util.Date;

import hy.plat.bo.BaseBean;


/**
 * 
 * 音乐
 * 
 * @author xgb
 *
 */
public class MaterialMusic implements BaseBean {

	private String mmkey;
	private String mmId;
	private String staffId;//用户id
	private String musicName;//音乐名称
	private String singName;//演唱人
	private String musichash;//音乐hash
	private String musicType;//音乐类型00:搜索历史,01:默认
	private Date createDate;//创建日期
	
	public String getMmkey() {
		return mmkey;
	}
	public void setMmkey(String mmkey) {
		this.mmkey = mmkey;
	}
	public String getMmId() {
		return mmId;
	}
	public void setMmId(String mmId) {
		this.mmId = mmId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getMusicName() {
		return musicName;
	}
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
	public String getSingName() {
		return singName;
	}
	public void setSingName(String singName) {
		this.singName = singName;
	}

	public String getMusichash() {
		return musichash;
	}
	public void setMusichash(String musichash) {
		this.musichash = musichash;
	}
	public String getMusicType() {
		return musicType;
	}
	public void setMusicType(String musicType) {
		this.musicType = musicType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
    
}
