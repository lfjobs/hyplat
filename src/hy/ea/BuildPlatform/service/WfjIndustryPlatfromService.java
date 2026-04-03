package hy.ea.BuildPlatform.service;

import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface WfjIndustryPlatfromService {

	String getQueryPlatfrom(String type,String ppid);
	PageForm getQueryAjax(String ppid,String content,PageForm pageForm,String param);
	List<BaseBean> getQueryIndustry(String codePID);
	void getAdd(String ccmomtype,Company company,CDetail cdl,int typeNumber,String path,File pictureList,String pictureListFileName,String ppidUser);
	String Verification(String ppid);
	Map<String, Object> getQuery(String cumname);
	String VerificationCompany(String company);
	PageForm getQueryCompany(int pageNumber,String name,String ssid);
	List<BaseBean> findPlatForm(String staffID,String platFromName);
	void addOrDelPlatForm(String staffID,String ccompanyID, String platformID,String flag);
	List<BaseBean> platformByStaff(String staffID);
}
