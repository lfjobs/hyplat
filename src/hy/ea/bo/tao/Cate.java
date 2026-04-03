package hy.ea.bo.tao;



/**
 * 
 * 类目表
 */

import hy.plat.bo.BaseBean;

public class Cate implements BaseBean{
	private String cateKey;
	private String cateID;
	private String catename;	 //类目名称
	private String parentId;	 //父ID
	private int orders;			 //排序
	private String type;			 //类型
	private String companyId;  //公司ID
	private String status;       //状态 00：可用   01 ： 不可用
	private String hierarchy;   //层次结构
	
	
	
	
	public String getCateKey() {
		return cateKey;
	}
	public void setCateKey(String cateKey) {
		this.cateKey = cateKey;
	}
	public String getCateID() {
		return cateID;
	}
	public void setCateID(String cateID) {
		this.cateID = cateID;
	}
	public String getCatename() {
		return catename;
	}
	public void setCatename(String catename) {
		this.catename = catename;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getOrders() {
		return orders;
	}
	public void setOrders(int orders) {
		this.orders = orders;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHierarchy() {
		return hierarchy;
	}
	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}


	
  
    
    
}
