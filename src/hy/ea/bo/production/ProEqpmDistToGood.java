package hy.ea.bo.production;

import hy.plat.bo.BaseBean;

/**
 * 设备分配关系表
 * @author zgzg
 *
 */
public class ProEqpmDistToGood implements BaseBean {

	private String pedtgKey;
	private String pedtgId;//关系表业务主键
	private String pedId;//设备分配表业务主键
	private String goodId;//物品表业务主键
	private String goodsName;//物品名称
	private String fieId;			//场地分配表ID
	
	public String getPedtgKey() {
		return pedtgKey;
	}
	public void setPedtgKey(String pedtgKey) {
		this.pedtgKey = pedtgKey;
	}
	public String getPedtgId() {
		return pedtgId;
	}
	public void setPedtgId(String pedtgId) {
		this.pedtgId = pedtgId;
	}
	public String getPedId() {
		return pedId;
	}
	public void setPedId(String pedId) {
		this.pedId = pedId;
	}
	public String getGoodId() {
		return goodId;
	}
	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getFieId() {
		return fieId;
	}
	public void setFieId(String fieId) {
		this.fieId = fieId;
	}

}
