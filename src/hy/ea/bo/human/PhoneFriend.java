package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

/**
 * PhoneFriend entity. @author zg
 */

public class PhoneFriend implements BaseBean {

	private String pfKey;
	private String pfId;
	private String pfUserId;
	private String pfFriendId;
	private String pfState;

	public PhoneFriend() {
	}

	public PhoneFriend(String pfId, String pfUserId, String pfFriendId,
			String pfState) {
		this.pfId = pfId;
		this.pfUserId = pfUserId;
		this.pfFriendId = pfFriendId;
		this.pfState = pfState;
	}

	public String getPfKey() {
		return this.pfKey;
	}

	public void setPfKey(String pfKey) {
		this.pfKey = pfKey;
	}

	public String getPfId() {
		return this.pfId;
	}

	public void setPfId(String pfId) {
		this.pfId = pfId;
	}

	public String getPfUserId() {
		return this.pfUserId;
	}

	public void setPfUserId(String pfUserId) {
		this.pfUserId = pfUserId;
	}

	public String getPfFriendId() {
		return this.pfFriendId;
	}

	public void setPfFriendId(String pfFriendId) {
		this.pfFriendId = pfFriendId;
	}

	public String getPfState() {
		return this.pfState;
	}

	public void setPfState(String pfState) {
		this.pfState = pfState;
	}

}