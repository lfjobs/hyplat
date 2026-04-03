package com.tiantai.telrec.tool; 
import java.util.List;
/**
 * 根据电话号码查詢社會客户信息(公司和个人)
 * @author Administrator
 *
 */
public interface QuerySocietalCustomer {

	/**
	 * 根据电话号码查询社会个人信息
	 * @param telno 电话号码
	 * @return
	 */
	public List<hy.ea.bo.human.Staff> queryStaff(String telno);

	/**
	 * 根据电话号码查询社会公司信息
	 * @param telno 电话号码
	 * @return
	 */
	public List<hy.ea.bo.company.ContactCompany> queryCompany(String telno);
}
