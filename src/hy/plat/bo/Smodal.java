/*
 *北京天太世统科技有限公司 010-64164005 
 *author：zg。email：longsky_03@sina.com
 */

package hy.plat.bo;

import java.io.Serializable;

import hy.plat.bo.BaseBean;

/**
 * @author zg longsky_03@sina.com
 * @version 1.0
 * @since 1.0
 */

@SuppressWarnings("serial")
public class Smodal implements Serializable,BaseBean {
    

	/** modalkey */
	private java.lang.String modalkey;
	/** modalid */
	private java.lang.String modalid;
	/** modalname */
	private java.lang.String modalname;
	/** root：根级别  */
	private java.lang.String pmodalid;
	/** sortsn */
	private int sortsn;
	/** murl */
	private java.lang.String murl;
	/** 01: 正常 02：停用*/
	private java.lang.String status;

	/** 树展示类型  默认0 ：菜单展示 1：树形结构展示 */
	private String menuState;
	
	
	public Smodal(){}
	public Smodal(String modalid, String modalname, String pmodalid) {
		super();
		this.modalid = modalid;
		this.modalname = modalname;
		this.pmodalid = pmodalid;
	}

	public java.lang.String getModalkey() {
		return this.modalkey;
	}
	
	public void setModalkey(java.lang.String value) {
		this.modalkey = value;
	}
	
	public java.lang.String getModalid() {
		return this.modalid;
	}
	
	public void setModalid(java.lang.String value) {
		this.modalid = value;
	}
	
	public java.lang.String getModalname() {
		return this.modalname;
	}
	
	public void setModalname(java.lang.String value) {
		this.modalname = value;
	}
	
	public java.lang.String getPmodalid() {
		return this.pmodalid;
	}
	
	public void setPmodalid(java.lang.String value) {
		this.pmodalid = value;
	}
	
	public int getSortsn() {
		return sortsn;
	}

	public void setSortsn(int sortsn) {
		this.sortsn = sortsn;
	}

	public java.lang.String getMurl() {
		return murl;
	}

	public void setMurl(java.lang.String murl) {
		this.murl = murl;
	}

	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public String getMenuState() {
		return menuState;
	}

	public void setMenuState(String menuState) {
		this.menuState = menuState;
	}
	
}

