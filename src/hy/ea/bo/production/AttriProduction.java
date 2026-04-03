package hy.ea.bo.production;

import hy.plat.bo.BaseBean;

import java.io.Serializable;


/**
 * 
 * 产品属性表如颜色 尺码
 * 
 * @author mz
 *
 */
public class AttriProduction implements BaseBean, Serializable {
	private static final long serialVersionUID = 5219959677520255278L;
	private String apkey;
    private String apid;
    private String attriname;//属性名 e.g.颜色 ，
    private String attrivalue;//属性值 e.g.白色
    private String imgurl;//图片链接 
    private String goodsid;//物品ID
    private String type;//0尺码,1颜色,2副图 3：视频 4:长宽高
    private int sort;//排序 1是产品主图，其他事副图

	public String getApkey() {
		return apkey;
	}
	public void setApkey(String apkey) {
		this.apkey = apkey;
	}
	public String getApid() {
		return apid;
	}
	public void setApid(String apid) {
		this.apid = apid;
	}
	public String getAttriname() {
		return attriname;
	}
	public void setAttriname(String attriname) {
		this.attriname = attriname;
	}
	public String getAttrivalue() {
		return attrivalue;
	}
	public void setAttrivalue(String attrivalue) {
		this.attrivalue = attrivalue;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "\"AttriProduction\":{" +
				"\"apkey\":\"" + apkey + '\"' +
				", \"apid\":\"" + apid + '\"' +
				", \"attriname\":\"" + attriname + '\"' +
				", \"attrivalue\":\"" + attrivalue + '\"' +
				", \"imgurl\":\"" + imgurl + '\"' +
				", \"goodsid\":\"" + goodsid + '\"' +
				", \"type\":\"" + type + '\"' +
				", \"sort\":" + sort +
				"},";
	}
}
