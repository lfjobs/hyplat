package hy.ea.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;

public class PersonalRelation implements BaseBean,ExcelBean{

		private String key;
		private String id;
		private String userid;
		private String friendid;
		private Date   addtime;
		
		@Override
		public String[] properties() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUserid() {
			return userid;
		}
		public void setUserid(String userid) {
			this.userid = userid;
		}
		public String getFriendid() {
			return friendid;
		}
		public void setFriendid(String friendid) {
			this.friendid = friendid;
		}

		public Date getAddtime() {
			return addtime;
		}

		public void setAddtime(Date addtime) {
			this.addtime = addtime;
		}
		
		
		
		
}
