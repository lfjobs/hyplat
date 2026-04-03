package hy.ea.human.service;

import java.util.List;

import hy.ea.bo.human.attence.AttenceGroup;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;


public interface AttenceManageService {
	
	/**
	 * 
	 * 考勤组列表
	 * @param pageNumber
	 * @param pageSize
	 * @param parameter
	 */
	public PageForm getAttenceGroupList(int pageNumber,int pageSize,String parameter,String companyID);
	
	public void addAttenceGroup(AttenceGroup attenceGroup,String staffID,String companyID);
	
	/**
	 * 
	 * 考勤记录分页
	 * @param pageNumber
	 * @param pageSize
	 * @param parameter
	 */
	public PageForm getAttenceRecordPage(int pageNumber,int pageSize,String signType,String start,String end,String parameter,String companyID);
	
	
	/**
	 * 
	 * 考勤记录列表
	 * @param parameter
	 */
	public List<BaseBean> getAttenceRecordList(String signType,String start,String end,String parameter,String companyID);
	
	
}
