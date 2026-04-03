package com.tiantai.wfj.vo;

public class CartItem {
   
	//wk 2015年12月9日14:40:43 改  因为没有店主  所以修如下
	//产品id   到 公司ID  不动
	//机构ID  跟机构名字    删除
	
	private String pid;// 商品ID
	private String pname;// 商品名称
	private String pic;// 商品图片位置
	private String price;// 商品价格
	private String companyId;// 公司ID
	private String staffId;// 负责人ID  /
	private String note;// 客户留言
	private String itemNum;// 商品的数量
	private String invenQuantity;//库存数量
		
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getItemNum() {
		return itemNum;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}


	public String getInvenQuantity() {
		return invenQuantity;
	}

	public void setInvenQuantity(String invenQuantity) {
		this.invenQuantity = invenQuantity;
	}

}
