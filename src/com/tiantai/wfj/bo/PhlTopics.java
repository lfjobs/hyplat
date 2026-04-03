package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;

/**
 *  拼货拉广告区主题
 */
public class PhlTopics implements BaseBean,Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2603901883883714750L;
	private String ptKey;
    private String ptID;
	private String companyID;//系统中的ID


    private String topic;  //话题
	private int hotnum;//热度排序
	public String getPtKey() {
		return ptKey;
	}
	public void setPtKey(String ptKey) {
		this.ptKey = ptKey;
	}
	public String getPtID() {
		return ptID;
	}
	public void setPtID(String ptID) {
		this.ptID = ptID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public int getHotnum() {
		return hotnum;
	}
	public void setHotnum(int hotnum) {
		this.hotnum = hotnum;
	}

   

	
}
