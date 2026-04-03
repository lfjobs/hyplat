package hy.ea.production.service;

import hy.ea.bo.company.ConsultingRegistration;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;


public interface ConsultManageService {

	/**
	 * 根据来源查询已提交的咨询人数
	 * @param ppid
	 * @return
	 */
	public String getCountBySource(String ppid);

	/**
	 * 咨询保存
	 * @param consult
	 */
	public void saveConsult(ConsultingRegistration consult);

	/**
	 * 验证是否咨询过该项目
	 * @param tel
	 * @param ppid
	 * @return
	 */
	public  String checkIn(String tel,String ppid);


	/**
	 *
	 * 回访记录
	 * @param consult
	 * @return
	 */
	public void saveReturnVisit(ConsultingRegistration consult);


	/**
	 *
	 * 安卓调用
	 * @param start
	 * @param end
	 * @param isIntentCustomer
	 * @param returnVisit
	 * @param companyId
	 * @param staffId
	 * @param parameter
	 * @return
	 */
	public List<BaseBean> getConsultList(String start, String end, String isIntentCustomer, String returnVisit, String companyId, String staffId, String parameter);


    /**
	 *
	 * 新版查询客户咨询列表暂时只查个人
	 * @param pageNumber
	 * @param pageSize
	 * @param staffID
	 * @param parameter
	 * @param returnVisit
     * @return
     */
	public PageForm getConsultslist(int pageNumber, int pageSize, String staffID,String companyID, String parameter, String returnVisit);


	public List<BaseBean> getConsultForList(String staffID,String companyID,String parameter, String returnVisit);

	/**
	 *
	 * 查看详情
	 * @param crId
	 * @return
     */
    public ConsultingRegistration viewDetail(String crId);

	/**
	 *
	 * 获取报名的这个文章的
	 * @param ppid
	 * @return
     */
	public List<Object> getConBmlist(String ppid);


}
