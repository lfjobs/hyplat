package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

/**
 * 购物车
 * @author Administrator
 *
 */
public class Cart implements BaseBean{

	private String cartKey;
	private String cartId;
	private String staffId;// 负责人ID  /
	private String pid;// 商品ID
	private String pname;// 商品名称
	private String pic;// 商品图片位置
	private String price;// 商品价格
	private String companyId;// 商品对应的公司ID
	private Integer itemNum;// 商品的数量
	private String stardard;//选择的规格
	private String pos;//0正常购物车 1：代购购物车
	private String  pricetype;//0零售价，1：批发价
	
	private String companyName;
	public Cart() {
		super();
	}
	public Cart(String rePrice,Integer itemNum){
		this.price=rePrice;
		this.itemNum=itemNum;
	}
	public Cart(String cartId, String staffId, String pid, String pname,
			String pic, String price, String companyId, Integer itemNum,
			String companyName,String stardard) {
		super();
		this.cartId = cartId;
		this.staffId = staffId;
		this.pid = pid;
		this.pname = pname;
		this.pic = pic;
		this.price = price;
		this.companyId = companyId;
		this.itemNum = itemNum;
		this.companyName = companyName;
		this.stardard = stardard;
	}


	public Cart(String cartKey, String cartId, String staffId, String pid,
			String pname, String pic, String price, String companyId,
			Integer itemNum) {
		super();
		this.cartKey = cartKey;
		this.cartId = cartId;
		this.staffId = staffId;
		this.pid = pid;
		this.pname = pname;
		this.pic = pic;
		this.price = price;
		this.companyId = companyId;
		this.itemNum = itemNum;
	}
	
	
	public String getCartKey() {
		return cartKey;
	}
	public void setCartKey(String cartKey) {
		this.cartKey = cartKey;
	}
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
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
	public Integer getItemNum() {
		return itemNum;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getStardard() {
		return stardard;
	}
	public void setStardard(String stardard) {
		this.stardard = stardard;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getPricetype() {
		return pricetype;
	}

	public void setPricetype(String pricetype) {
		this.pricetype = pricetype;
	}
}
