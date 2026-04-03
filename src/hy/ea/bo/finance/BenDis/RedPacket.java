package hy.ea.bo.finance.BenDis;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 红包
 * 
 */
public class RedPacket implements BaseBean{
    
	private String redPacketId;
	private String redPacketKey;
	private String sccIdS;//发送人的sccId
	private String sccIdR;//接收人的sccId
	private String sender;//发送人
	private String recipient;//接收人
	private int goldNum;//金币数量
	private Date redDate;//红包时间
	private String wfjGuizeId;// 规则外键 WfjGuize

	
	
	public String getRedPacketId() {
		return redPacketId;
	}
	public void setRedPacketId(String redPacketId) {
		this.redPacketId = redPacketId;
	}
	public String getRedPacketKey() {
		return redPacketKey;
	}
	public void setRedPacketKey(String redPacketKey) {
		this.redPacketKey = redPacketKey;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	public int getGoldNum() {
		return goldNum;
	}
	public void setGoldNum(int goldNum) {
		this.goldNum = goldNum;
	}
	public Date getRedDate() {
		return redDate;
	}
	public void setRedDate(Date redDate) {
		this.redDate = redDate;
	}
	public String getSccIdS() {
		return sccIdS;
	}
	public void setSccIdS(String sccIdS) {
		this.sccIdS = sccIdS;
	}
	public String getSccIdR() {
		return sccIdR;
	}
	public void setSccIdR(String sccIdR) {
		this.sccIdR = sccIdR;
	}
	public String getWfjGuizeId() {
		return wfjGuizeId;
	}
	public void setWfjGuizeId(String wfjGuizeId) {
		this.wfjGuizeId = wfjGuizeId;
	}

	
	

    
}
