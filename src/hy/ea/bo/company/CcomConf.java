package hy.ea.bo.company;

import hy.plat.bo.BaseBean;

import java.io.File;
import java.io.Serializable;

/**
 * 公司首页配置表
 * @author 
 */
public class CcomConf implements BaseBean,Serializable {
	private String ccomConfKey;
	private String ccomConfId;
	private String ccompanyId;		//往来单位Id FK
	private String picPath;         //图片路径
	private String modalName;	            //模块名称,限制50字
	private String modalRemark;         //详细说明,长度1000汉字
	private String modalType;          //类型0:企业简介,1:关于公司
	private String state;        //状态默认0
	private String sn;      //排序编号
	private File photo; //模块图片
	private String photoFileName;//图片名称
	public String getCcomConfKey() {
		return ccomConfKey;
	}
	public void setCcomConfKey(String ccomConfKey) {
		this.ccomConfKey = ccomConfKey;
	}
	public String getCcomConfId() {
		return ccomConfId;
	}
	public void setCcomConfId(String ccomConfId) {
		this.ccomConfId = ccomConfId;
	}
	public String getCcompanyId() {
		return ccompanyId;
	}
	public void setCcompanyId(String ccompanyId) {
		this.ccompanyId = ccompanyId;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getModalName() {
		return modalName;
	}
	public void setModalName(String modalName) {
		this.modalName = modalName;
	}
	public String getModalRemark() {
		return modalRemark;
	}
	public void setModalRemark(String modalRemark) {
		this.modalRemark = modalRemark;
	}
	public String getModalType() {
		return modalType;
	}
	public void setModalType(String modalType) {
		this.modalType = modalType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public File getPhoto() {
		return photo;
	}
	public void setPhoto(File photo) {
		this.photo = photo;
	}
	public String getPhotoFileName() {
		return photoFileName;
	}
	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}
	
	
}
