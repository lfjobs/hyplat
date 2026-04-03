package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

/**
 * 记录用户免费短信剩余条数 初始为空  第一次发送短信时候赋值
 *
 */
public class FeeMessage implements BaseBean{
    

	private String fmkey;
	private String fmId;
	private String staffID;
	private int surplusNum;//剩余条数
	private int totalNum;//短信总免费条数

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getFmkey() {
		return fmkey;
	}

	public void setFmkey(String fmkey) {
		this.fmkey = fmkey;
	}

	public String getFmId() {
		return fmId;
	}

	public void setFmId(String fmId) {
		this.fmId = fmId;
	}

	public int getSurplusNum() {
		return surplusNum;
	}

	public void setSurplusNum(int surplusNum) {
		this.surplusNum = surplusNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
}
