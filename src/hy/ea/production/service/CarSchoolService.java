package hy.ea.production.service;

import hy.ea.bo.production.drive.TeachingContent;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tiantai.wfj.bo.TEshopCusCom;


public interface CarSchoolService {

	Map<String, Object> queryNews(int pageNumber,int pageSize, TEshopCusCom cusCom);
	
	void delNews(String csiIds);
	
	List<Object> queryNewsDetails(String ppID);
	
	void updateNewsDetails(String csiId);
	
	List<Object> queryUserDetails(TEshopCusCom cusCom);

	List<Object> goldAnnouncement();

	PageForm relevanceInformation(int pageNumber, int pageSize, TEshopCusCom cusCom);

	PageForm recommendFriend(int pageNumber, int pageSize, TEshopCusCom cusCom);
	
	String getWeizhangInfoPost(String carInfo);

	String location(String coordinate);

	TEshopCusCom queryUser();

	Object queryGold(TEshopCusCom cusCom);

	List<BaseBean> teaching(String staffcid, Date orderDate);

	Map<String, Object> teachingContent(String ocrId);

	List<BaseBean> queryTrain(String staffid, String teachingDate);

	List<BaseBean> allLog(String teachingDate, String staffid);

	void saveOrUpdateLog(TeachingContent teachingContent, String teachingDate);

	TeachingContent queryLog(String tctKey);
	
}
