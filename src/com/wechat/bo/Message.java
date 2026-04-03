package com.wechat.bo;

/**
 * 
 * 预览信息
 * @author mz
 *
 */
public class Message {
	private String towxname;//touser 接收消息用户对应该公众号的openid，该字段也可以改为towxname，以实现对微信号的预览
    private String msgtype;//群发的消息类型，图文消息为mpnews，文本消息为text，语音为voice，音乐为music，图片为image，视频为video，卡券为wxcard
     
	public String getTowxname() {
		return towxname;
	}
	public void setTowxname(String towxname) {
		this.towxname = towxname;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
   

}
