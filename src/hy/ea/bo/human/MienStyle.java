package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

/**
 * 风采信息管理
 * @author zj
 */
public class MienStyle implements BaseBean{
	private String mienStyleKey;
	private String mienStyleID;
	private String relateID;	 		//所用人或公司的ID
	private String materialID;		//素材ID
	public String getMienStyleKey() {
		return mienStyleKey;
	}
	public void setMienStyleKey(String mienStyleKey) {
		this.mienStyleKey = mienStyleKey;
	}
	public String getMienStyleID() {
		return mienStyleID;
	}
	public void setMienStyleID(String mienStyleID) {
		this.mienStyleID = mienStyleID;
	}
	public String getRelateID() {
		return relateID;
	}
	public void setRelateID(String relateID) {
		this.relateID = relateID;
	}
	public String getMaterialID() {
		return materialID;
	}
	public void setMaterialID(String materialID) {
		this.materialID = materialID;
	}
}
