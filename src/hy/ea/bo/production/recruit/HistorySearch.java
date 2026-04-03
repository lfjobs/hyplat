package hy.ea.bo.production.recruit;



import hy.plat.bo.BaseBean;

import java.util.Date;

public class HistorySearch implements BaseBean{

	private String hskey; // 主键
	private String hsId;
	private String type;//搜索工作的00，还是求职的01
	private String keyword1;//搜索关键词 行业
	private String keyword2; //职位
	private String keyword3;//城市
	private String staffID;//对应的人的搜索
    private Date searchDate;//搜索时间
    private int secount;//搜索次数
    
    
    
	public String getHskey() {
		return hskey;
	}
	public void setHskey(String hskey) {
		this.hskey = hskey;
	}
	public String getHsId() {
		return hsId;
	}
	public void setHsId(String hsId) {
		this.hsId = hsId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}
	public String getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}
	public String getKeyword3() {
		return keyword3;
	}
	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public Date getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}
	public int getSecount() {
		return secount;
	}
	public void setSecount(int secount) {
		this.secount = secount;
	}
	
	
	
    
}