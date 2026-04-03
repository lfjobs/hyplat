package hy.ea.service;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;

import java.util.List;

public interface CLogBookService {
	
	CLogBook saveCLogBook(String organizationID,String parameter,CAccount account);
	
	/**
	 * 保存多个日志子类对象
	 * @param organizationID 组织ID
	 * @param parameters 信息
	 * @param CAccount 账户信息 
	 */
	void saveLogsListAndexecuteHqlsByParams(String organizationID,List<String> parameters,CAccount account);
}
