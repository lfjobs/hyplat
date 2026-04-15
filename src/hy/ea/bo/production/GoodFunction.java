package hy.ea.bo.production;

import hy.plat.bo.BaseBean;


/**
 * 
 * 物品功能表
 * 
 * @author mz
 *
 */
public class GoodFunction implements BaseBean {

	
    private String gfkey;
    private String gfid;
    private String name;//功能名称
    private String url;//内容文本编辑器保存后的txt链接地址
    private Integer orders;//排序
    private String type;
    private String goodsid;//对应的物品ID
    
	public String getGfkey() {
		return gfkey;
	}
	public void setGfkey(String gfkey) {
		this.gfkey = gfkey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
    
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGfid() {
		return gfid;
	}
	public void setGfid(String gfid) {
		this.gfid = gfid;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	
    
    
}
