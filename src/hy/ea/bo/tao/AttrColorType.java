package hy.ea.bo.tao;

import hy.plat.bo.BaseBean;
/*
 * 
 * 
 * 颜色表
 */
public class AttrColorType implements BaseBean{
	private String actKey;
	private String actID;
	private String colorname;//颜色名称
	private String colorvalue;//rgb
	private String iscolor;//背景色用color值还是图片
	private String colorpic;//背景图片
	private Integer orders;//排序
	public String getActKey() {
		return actKey;
	}
	public void setActKey(String actKey) {
		this.actKey = actKey;
	}
	public String getActID() {
		return actID;
	}
	public void setActID(String actID) {
		this.actID = actID;
	}
	public String getColorname() {
		return colorname;
	}
	public void setColorname(String colorname) {
		this.colorname = colorname;
	}
	public String getColorvalue() {
		return colorvalue;
	}
	public void setColorvalue(String colorvalue) {
		this.colorvalue = colorvalue;
	}
	public String getIscolor() {
		return iscolor;
	}
	public void setIscolor(String iscolor) {
		this.iscolor = iscolor;
	}
	public String getColorpic() {
		return colorpic;
	}
	public void setColorpic(String colorpic) {
		this.colorpic = colorpic;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	
	
    

    
    
    
}
