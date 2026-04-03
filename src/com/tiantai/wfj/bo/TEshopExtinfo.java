package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;

/**
 * TEshopExtinfo entity. @author MyEclipse Persistence Tools
 */

public class TEshopExtinfo implements BaseBean {

	// Fields

	private String eshopkey;//扩展信息表主键
	private String organizationID;//所属组织机构ID
	private String logo;//店铺Logo地址
	private String titleimage;//主题图
	private String intro;//店铺简介
	private String address;//商铺地址
	private Date regdate;//开店时间
	private String owner;//店铺主人
	private String telephone;//联系电话
	private String qq;//QQ
	private String istuijian;//是否推荐店铺，预留字段
	private long star;//店铺星级(一般是由前端消费者评定)
	private long paiming;//店铺在本类型中的排名，系统预留
	private String payaccount;//支付账号
	private String eshopstatus;//微分金店状态：0 正常 1 欠費 2暫停 3終止
	private String tradeid;//店铺所属行业标志
	private String tradename;//店铺所属行业名称
	private String inused;//是否使用
	
	private File photo;
	private String photoFileName;
	private String photoContentType;
	
	private File phototitle;
	private String phototitleFileName;
	private String phototitleContentType;
	//zll添加
	private String staffID;//店主id
	
	//ty 店铺类型
	private String shopType;//01 店铺   02 产品代理商VIP会员   03 产品代理零售商会员
	
	
	// Constructors

	/** default constructor */
	public TEshopExtinfo() {
	}

	/** full constructor */
	public TEshopExtinfo(String organizationID, String logo,
			String intro, String address, Date regdate, String owner,
			String telephone, String qq, String istuijian, long star,
			long paiming, String payaccount, String eshopstatus,
			String tradeid, String tradename) {
		this.organizationID = organizationID;
		this.logo = logo;
		this.intro = intro;
		this.address = address;
		this.regdate = regdate;
		this.owner = owner;
		this.telephone = telephone;
		this.qq = qq;
		this.istuijian = istuijian;
		this.star = star;
		this.paiming = paiming;
		this.payaccount = payaccount;
		this.eshopstatus = eshopstatus;
		this.tradeid = tradeid;
		this.tradename = tradename;
	}

	// Property accessors

	public String getEshopkey() {
		return this.eshopkey;
	}

	public void setEshopkey(String eshopkey) {
		this.eshopkey = eshopkey;
	}	

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getRegdate() {
		return this.regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getIstuijian() {
		return this.istuijian;
	}

	public void setIstuijian(String istuijian) {
		this.istuijian = istuijian;
	}

	public long getStar() {
		return this.star;
	}

	public void setStar(long star) {
		this.star = star;
	}

	public long getPaiming() {
		return this.paiming;
	}

	public void setPaiming(long paiming) {
		this.paiming = paiming;
	}

	public String getPayaccount() {
		return this.payaccount;
	}

	public void setPayaccount(String payaccount) {
		this.payaccount = payaccount;
	}

	public String getEshopstatus() {
		return this.eshopstatus;
	}

	public void setEshopstatus(String eshopstatus) {
		this.eshopstatus = eshopstatus;
	}

	public String getTradeid() {
		return this.tradeid;
	}

	public void setTradeid(String tradeid) {
		this.tradeid = tradeid;
	}

	public String getTradename() {
		return this.tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
	}

	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
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
	public String getPhotoContentType() {
		return photoContentType;
	}
	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}
	public String getTitleimage() {
		return titleimage;
	}
	public void setTitleimage(String titleimage) {
		this.titleimage = titleimage;
	}
	public File getPhototitle() {
		return phototitle;
	}
	public void setPhototitle(File phototitle) {
		this.phototitle = phototitle;
	}
	public String getPhototitleFileName() {
		return phototitleFileName;
	}
	public void setPhototitleFileName(String phototitleFileName) {
		this.phototitleFileName = phototitleFileName;
	}
	public String getPhototitleContentType() {
		return phototitleContentType;
	}
	public void setPhototitleContentType(String phototitleContentType) {
		this.phototitleContentType = phototitleContentType;
	}
	public String getInused() {
		return inused;
	}
	public void setInused(String inused) {
		this.inused = inused;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	
}