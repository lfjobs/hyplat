package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 收付码商城子订单详细产品
 * @author mz
 *
 */
public class ClientOrderGoods implements BaseBean{
	
	private String cogkey;
	private String cogID;
	private String coID;//总订单ID
	private String codID;//子订单ID
	private String goodsName;//产品名称
	private String ppid;//产品ID
	private String quantity;//数量
	private String price;//销售单价
	private String money;//总价
	private String standard;//规格

	public String getCogkey() {
		return cogkey;
	}

	public void setCogkey(String cogkey) {
		this.cogkey = cogkey;
	}

	public String getCogID() {
		return cogID;
	}

	public void setCogID(String cogID) {
		this.cogID = cogID;
	}

	public String getCoID() {
		return coID;
	}

	public void setCoID(String coID) {
		this.coID = coID;
	}

	public String getCodID() {
		return codID;
	}

	public void setCodID(String codID) {
		this.codID = codID;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}
}
