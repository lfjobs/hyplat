package mobile.tiantai.android.bo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 此为自定义的极光推送消息对象
 */
public class JPushMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8627444542625851892L;
	/**
	 * 业务ID
	 */
	private String msgid;
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 消息描述
	 */
	private String body;
	/**
	 * 消息类型
	 */
	private String type;

	/**
	 * masterSecret
	 */
	private String masterSecret;

	/**
	 * appKey
	 */
	private String appKey;

 

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("msgid", msgid);
		map.put("title", title);
		map.put("body", body);
		map.put("type", type);
		return map;
	}

	/**
	 * @return the masterSecret
	 */
	public String getMasterSecret() {
		return masterSecret;
	}

	/**
	 * @param masterSecret
	 *            the masterSecret to set
	 */
	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}

	/**
	 * @return the appKey
	 */
	public String getAppKey() {
		return appKey;
	}

	/**
	 * @param appKey
	 *            the appKey to set
	 */
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
}