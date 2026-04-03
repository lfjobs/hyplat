package com.tiantai.wfj.service;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;


public interface EarthIndexService {
	
	
	public TEshopCusCom getCusCom(String user);


	public TEshopCustomer getCustomer(String user);


	public PageForm getBrowseCompanyList(int pageNumber,int pageSize,String sccid);


	public PageForm getBrowseNewsList(int pageNumber,int pageSize,String sccid);

	public PageForm getBrowseVideoList(int pageNumber,int pageSize,String sccid);

	public PageForm getBrowseGoodsList(int pageNumber,int pageSize,String sccid);

	public List<BaseBean> getBrowseAppList(String sccid);


	public void addBrowseHistory(String sccid,String appName,String id);



}
