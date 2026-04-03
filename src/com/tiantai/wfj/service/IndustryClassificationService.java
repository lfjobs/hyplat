package com.tiantai.wfj.service;

import java.util.List;
import java.util.Map;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;

import hy.ea.bo.company.ConsultingRegistration;
import hy.ea.bo.company.ContactCompany;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

public interface IndustryClassificationService {
	
	PageForm getAjaxPorductsList(String ccompanyId, Integer pageNumber, String search, String ppId);
	
	PageForm getRecruitIndex(String ccompanyId, Integer pageNumber);
	
	List<BaseBean> getProductCategories(String companyId);
	
	List<BaseBean> searchCategories(String ppid);
	
	/**
	 * @param string 
	 * @Title: 查询
	 * @Description: 查询公司新闻信息
	 * @Description CcompanyId
	 *            :往来单位id
	 * @return 返回的数据
	 */
	List<Object> NewsList(String ccompanyId, String string);

	/**
	 * @Title: 查询
	 * @Description: 查询公司招商信息
	 * @Description CcompanyId
	 *            :往来单位id
	 * @return 返回的数据
	 */
	Map<String, Object> getpk(String ccompanyId);

	/**
	 * @Title: 查询
	 * @Description: 查询用户信息
	 * @param
	 * @return 返回的数据
	 */
	TEshopCustomer inquireUser();
	/**
	 * @Title: 查询
	 * @Description: 查询公司招聘信息
	 * @param
	 * @return 
	 * @return 返回的数据
	 */
	List<BaseBean> attractEngage(String ccompanyId);

	/**
	 * @Title: 查询
	 * @Description: 查询公司详细信息
	 * @param ccompanyId
	 *            :往来单位id
	 * @return 返回的数据
	 */
	Map<String, Object> companyMessage(String ccompanyId);
	
	Map<Integer,String[]> summaryOrCulture(String companyId, String flag);
	/**
	 * @Description miniSystemJudge
	 * @Title: 查询
	 * @Description: 查询公司咨询详情
	 * @param ppId:产品id
	 * @return 
	 * @return 返回的数据
	 */
	
	Object content(String ppId);
	
	List<BaseBean> splendid(String ccompanyId, String ppId, String mini, String fiveClear);
	
	List<BaseBean> toADList(String type, String companyId);

	ContactCompany getCompany(String ccompanyId);

	List<Object> exhibition(String ccompanyId);

	Map<Integer, String[]> informationDetails(String ppId);

	Map<String,Object> getTcc(String ccompanyId);

	Object getField(String ccompanyId);

	Object getStCompany(String ccompanyId);

	String addConsultant(ConsultingRegistration consultingRegistration);

	void saveCompany(ContactCompany contactCompany, TEshopCusCom shopCusCom);

	/**
	 * 添加打赏信息
	 * @param ppid
	 * @param money
	 * @param fbsccid
     * @param rdsccid
	 * @return
	 */
	String saveReward(String ppid,String money,String fbsccid,String rdsccid)throws Exception;

	/**
	 * 查询打赏信息
	 * @param ordernum
	 * @return
	 */
	BaseBean getReward(String ordernum,String tradeStatus);
}
