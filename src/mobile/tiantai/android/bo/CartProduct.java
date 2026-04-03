package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;
/**
 * app加入购物车的产品 
 * @author zll
 *
 */
public class CartProduct implements BaseBean{
	private String cartKey;
	private String cartId;
	private String goodsName;
	private String ppId;
	private String price;
	private String pic;
	private String customer;
	private String shopId;
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
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getPpId() {
		return ppId;
	}
	public void setPpId(String ppId) {
		this.ppId = ppId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	
	
}
