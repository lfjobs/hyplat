package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 安卓APP版本控制  更新 类
 * @author ttst_zhb
 *
 */
public class AndroidEdition implements BaseBean{
	private String    AEKey;
	private String    AEID;
	private String    AEpahe;//版本路径
	private String    AEEdition;//版本号
	private Date      AEDate;//修改版本时间
	private String    AEEditionTOPUser;//上传版本的人
	private String    AEIOSORANDROID;//判断上传的是IOS 还是 安卓  0安卓1Ios
	private String    APPname;//软件名称 例如：淮鑫私厨
	private String    APPLogo;//软件标识
	private String   content;//更新内容
	private String    forceUpdate;//强制更新 01 强制更新 其他不强制
	public String getAEKey() {
		return AEKey;
	}
	public void setAEKey(String aEKey) {
		AEKey = aEKey;
	}
	public String getAEpahe() {
		return AEpahe;
	}
	
	public String getAEIOSORANDROID() {
		return AEIOSORANDROID;
	}
	public void setAEIOSORANDROID(String aEIOSORANDROID) {
		AEIOSORANDROID = aEIOSORANDROID;
	}
	public String getAEID() {
		return AEID;
	}
	public void setAEID(String aEID) {
		AEID = aEID;
	}
	public void setAEpahe(String aEpahe) {
		AEpahe = aEpahe;
	}
	public String getAEEdition() {
		return AEEdition;
	}
	public void setAEEdition(String aEEdition) {
		AEEdition = aEEdition;
	}
	public Date getAEDate() {
		return AEDate;
	}
	public void setAEDate(Date aEDate) {
		AEDate = aEDate;
	}
	public String getAEEditionTOPUser() {
		return AEEditionTOPUser;
	}
	public void setAEEditionTOPUser(String aEEditionTOPUser) {
		AEEditionTOPUser = aEEditionTOPUser;
	}
	public String getAPPname() {
		return APPname;
	}
	public void setAPPname(String APPLogo) {
		APPname = APPLogo;
	}
	public String getAPPLogo() {
		return APPLogo;
	}
	public void setAPPLogo(String aPPLogo) {
		APPLogo = aPPLogo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getForceUpdate() {
		return forceUpdate;
	}

	public void setForceUpdate(String forceUpdate) {
		this.forceUpdate = forceUpdate;
	}
}
