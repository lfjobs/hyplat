package hy.ea.bo.production.recruit;



import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 
 * 
 * 收藏
 * @author Administrator
 *
 */
public class CollectThing implements BaseBean{

	private String cokey; // 主键
	private String coId;
    private String id;//收藏东西的ID
    private String type;//收藏的类型
    private String staffID;//谁收藏的
    private Date colDate;//收藏时间
	public String getCokey() {
		return cokey;
	}
	public void setCokey(String cokey) {
		this.cokey = cokey;
	}
	public String getCoId() {
		return coId;
	}
	public void setCoId(String coId) {
		this.coId = coId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public Date getColDate() {
		return colDate;
	}
	public void setColDate(Date colDate) {
		this.colDate = colDate;
	}
    

	

	
}