package com.tiantai.telrec.tool;


/*
 * 目的是进行数据提交前的
 * 对用户进行验证
 *  如果该用户有效的话返回真  
 * */

public class UserCheck {
	private static UserCheck check;

	// private CLoginService loginImpl;
	private UserCheck() {

	}

	public static UserCheck Instance() {
		if (check == null) {
			check = new UserCheck();
		}
		return check;
	}

	/*
	 * public UserCheck getCheck() { return check; }
	 * 
	 * private void setCheck(UserCheck check) { this.check = check; }
	 */
}
