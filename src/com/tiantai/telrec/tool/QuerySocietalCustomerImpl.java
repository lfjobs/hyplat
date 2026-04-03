package com.tiantai.telrec.tool;

import hy.ea.util.SpringContextUtil;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.impl.BaseBeanServiceImpl;

import java.util.List;

public class QuerySocietalCustomerImpl implements QuerySocietalCustomer {

	private static QuerySocietalCustomerImpl impl;
	private BaseBeanService baseBeanService;

	public static QuerySocietalCustomerImpl instance() {
		if (impl == null) {
			impl = new QuerySocietalCustomerImpl();
			impl.baseBeanService = (BaseBeanService) SpringContextUtil.getBean(
					"baseBeanServiceImpl", BaseBeanService.class);
		}
		return impl;
	}

	//@Override
	// public List query(String telno) {
	// String hql = "from Staff where staffID in (select staffID from Contact
	// where contactWay = ?)";
	// Object[] params = { telno };
	//
	// List<BaseBean> staffList = baseBeanService.getListBeanByHqlAndParams(
	// hql, params);
	// // JSONObject oj = JSONObject.fromObject(map);
	// return staffList;
	// }
	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}

	public void setBaseBeanService(BaseBeanServiceImpl baseBeanService) {
		this.baseBeanService = baseBeanService;
	}
	/**
	 * 查找公司客户
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryCompany(String telno) {
		String hql = "from ContactCompany where  companyTel=? or responsibleTel=?";
		Object[] params = { telno, telno };

		List<BaseBean> staffList = baseBeanService.getListBeanByHqlAndParams(
				hql, params);
		return staffList;
	}
	/**
	 * 查找个人客户
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryStaff(String telno) {
		String hql = "from Staff where  reference=?";
		Object[] params = { telno };

		List<BaseBean> staffList = baseBeanService.getListBeanByHqlAndParams(
				hql, params);
		return staffList;
	}

}
