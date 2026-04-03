package hy.ea.office.service;

import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

public interface TelMessageService {


	/**
	 *
	 * 增加短信模板
	 * @param qmsID
	 * @param titleName
	 * @param content
	 * @param surl
	 */
	public void addTelTemp(String qmsID,String titleName,String content,String staffID,String surl,String cate,String companyID);

	/**
	 *
	 * 群发短息
	 * @param tels
	 * @param content
	 * @return
	 */
	public String groupSendTel(String tels,String content,String staffID,String sccid,String companyID);


	/**
	 *
	 * 获取短信模板列表不分页
	 * @param staffID
	 * @return
	 */
	public List<BaseBean> getdxTemp(String staffID,String companyID,String parameter,String cate);

	/**
	 *
	 * 删除短信模板
	 * @param qmsID
	 */
	public void deleteTelTemp(String qmsID);


	/**
	 *
	 * 保存短链接
	 * @param surl
	 */
	public String saveShortUrl(String surl,String staffID);


	/**
	 *
	 * 访问短链接
	 * @param surl
	 * @return
	 */
	public String accessSurl(String surl);


	/**
	 *
	 * 获取剩余的免费短信条数
	 * @param staffID
	 * @return
	 */
	public Map<String,Object> getSyMessage(String staffID,String sccid);


	/**
	 *
	 * 短信发送记录
	 * @param pageNumber
	 * @param pageSize
	 * @param parameter
	 * @param staffID
	 * @return
	 */
	public PageForm getDxRecordlist(int pageNumber, int pageSize, String  parameter, String staffID,String companyID);
	/**
	 *
	 * 获取公司
	 * @param pageNumber
	 * @param pageSize
	 * @param parameter

	 * @return
	 */
	public PageForm getDxCompanylist(int pageNumber, int pageSize, String  parameter);



	/**
	 *
	 * 获取平台短信在第三方剩余金额
	 * @return
	 */

	public String getPlatMes();

	/**
	 * 获取高德一级分类
	 *
	 * @return
	 */
	public List<Object> getCateList();

	/**
	 *
	 * 获取手机号分类及往来单位和往来个人
	 * @return
	 */
	public List<Object> getRelatelist(String companyID);

	/***
	 *
	 *
	 * @param ralate
	 * @return
	 */
	public PageForm getTellistByCate(String ralate,String companyID,int pageNumber, int pageSize, String parameter);

	/**
	 *
	 * 获取在职员工电话
	 * @param companyID
	 * @return
	 */
	public PageForm getStaffTelList(String companyID,int pageNumber, int pageSize, String parameter);

}
