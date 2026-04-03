package com.tiantai.telrec.dao;

import java.util.List;
import java.util.Map;

import com.tiantai.telrec.bean.UserInfo;

public interface UserQueryDao {
	@SuppressWarnings("rawtypes")
	public List queryUserTelRec(String id, String starttime, String endtime);

	public boolean queryUser(String orgsimple, String username, String password);

	public String queryUserId(String companyid, String username, String password);

	public boolean insertUser(UserInfo user);
	@SuppressWarnings("rawtypes")
	public List<Map> queryUserForUseridAndCompanyId(String orgsimple,
			String username);
	@SuppressWarnings("rawtypes")
	public List queryUserForCompanyid(String orgsimple);
}
