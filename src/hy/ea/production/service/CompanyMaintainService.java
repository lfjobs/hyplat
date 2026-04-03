package hy.ea.production.service;

import hy.ea.bo.company.ContactCompany;
import hy.plat.bo.BaseBean;

import java.util.List;
import java.util.Map;

import com.tiantai.wfj.bo.TEshopCusCom;


public interface CompanyMaintainService {

	boolean delNews(String ppID);

	List<Object> queryNews(String ppID);

	Map<String, Object> saveOrUpdateNews(String ppID, String goodsName, String cropInp,
			String cropInpFileName, String staffid, String companyID,String content);

	TEshopCusCom queryUser();

	Map<String, Object> queryAllNews(int pageNumber, int i, String companyId, String staffid
			);

	ContactCompany queryCompanyDetails(String staffid);

	void androidShoveSend(String message, String type, String body, String id,
			String ssJudge, String accountNumber, String staffid, String companyId);

	List<BaseBean> userFriend(String staffid);
	
	
}
