package hy.ea.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;

public class HelpDocument implements BaseBean,ExcelBean{
	private String hdKey;			        //主键
	private String hdID;         			//业务主键
	private String hdUrl;					//文件(路径)
	private String hdName;					//文件名称
	private Date hdDate;					//添加文件时间
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getHdKey() {
		return hdKey;
	}
	public void setHdKey(String hdKey) {
		this.hdKey = hdKey;
	}
	public String getHdID() {
		return hdID;
	}
	public void setHdID(String hdID) {
		this.hdID = hdID;
	}
	public String getHdUrl() {
		return hdUrl;
	}
	public void setHdUrl(String hdUrl) {
		this.hdUrl = hdUrl;
	}
	public String getHdName() {
		return hdName;
	}
	public void setHdName(String hdName) {
		this.hdName = hdName;
	}
	public Date getHdDate() {
		return hdDate;
	}
	public void setHdDate(Date hdDate) {
		this.hdDate = hdDate;
	}
	
}
