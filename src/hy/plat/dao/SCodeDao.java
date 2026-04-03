package hy.plat.dao;


import hy.plat.bo.SCode;

import java.util.List;

public interface SCodeDao {
	
	/**
	 * 通过ID(Not Key)递归删除
	 * @param codeID
	 */
	void deleteCodeByID(String codeID);
	
	/**
	 * 通过ID(Not Key)查询一个SCode
	 * @param codeID
	 * @return
	 */
	SCode getCodeByID(String codeID);
	
	/**
	 * 根据PID取得子代码
	 * @param codePID
	 * @return
	 */
	List<SCode> getCodeListByPID(String codePID);
	
	/**
	 * 取得所有的SCode，用于用户注册时拷贝
	 * @return
	 */
	List<SCode> getCodeList();
	
}
