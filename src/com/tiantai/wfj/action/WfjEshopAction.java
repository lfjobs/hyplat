package com.tiantai.wfj.action;

import hy.base.action.BaseAction;
import hy.ea.bo.CAccount;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopExtinfo;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class WfjEshopAction extends BaseAction<Object> {
	@Resource
	private UpLoadFileService fileService;
	private TEshopExtinfo eshop;//微分金店扩展信息对象
	private Map<String, TEshopExtinfo> eshopsMap;
	/**
	 *微分金店铺活动 ly
	 *查询
	 * @return
	 */
	public String getWfjshopaction(){
		request.getParameter("companyId");
		String sql="select co.ocode,co.organizationname, sh.owner,sh.Telephone,sh.tradename,sh.Address,sh.logo,sh.titleimage,c.companyname"+
				" from dtcorganization co,T_ESHOP_EXTINFO sh,dtcompany c " +
				" where co.organizationid=sh.organizationid and co.companyid=c.companyid  and co.iswfj='是' and sh.eshopstatus='0' and sh.inused='1' and co.Status='00'";
	
		pageForm =	baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
			.getPageNumber() : 1),(pageNumber==0?15:pageNumber), sql,"select count(1) "
					+ sql.substring(sql.indexOf("from")), null);
	    return "wfjshops_list";
	}
	
	
	/**
	 * 列表显示微分金店(扩展信息)
	 * @return
	 */
	public String getWfjEshopList()
	{
		String hql = " from TEshopExtinfo shop where shop.organizationID = ? ";
		Object[] params = new Object[]{eshop.getOrganizationID()};
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber==0 ? 5 : pageNumber), hql, params);
		return "eshop.list";
	}
	/**
	 * 保存微分金店(扩展信息)
	 * @return
	 */
	public String saveWfjEshop()
	{
		if (eshopsMap != null && eshopsMap.size()>0){	
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			CAccount account = (CAccount) session.get("account");
			
			List<BaseBean> beans = new ArrayList<BaseBean>();			
			for(TEshopExtinfo shop : eshopsMap.values()){				
				if (!StringUtils.isEmpty(shop.getPhotoFileName())){
					String logoPath = fileService
							.savePhoto(path, shop.getPhotoFileName(), shop.getPhoto(), account.getCompanyID(),
									"wfjeshop/logo/"
											+ Utilities.getDateString(
													new Date(), "yyyy-MM-dd"));
					shop.setLogo(logoPath);
				}
				
				if (!StringUtils.isEmpty(shop.getPhototitleFileName())){					
					String titleimagePath = fileService
							.savePhoto(path, shop.getPhototitleFileName(), shop.getPhototitle(), account.getCompanyID(),
									"wfjeshop/titleimage/"
											+ Utilities.getDateString(
													new Date(), "yyyy-MM-dd"));
					shop.setTitleimage(titleimagePath);
				}
				if (null != shop.getEshopkey() && !"".equals(shop.getEshopkey()))
				{
					baseBeanService.update(shop);
				}else{
					beans.add(shop);
				}
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,null);
		}
		return "success";
	}
	/**
	 * 删除微分金店(扩展信息)
	 * @return
	 */
	public String delEshop()
	{
		baseBeanService.deleteBeanByKey(TEshopExtinfo.class, eshop.getEshopkey());
		return getWfjEshopList();
	}
	
	/**
	 * 导出微分金店(扩展信息)
	 * @return
	 */
	public String showWfjEshopExcel()
	{
		
		return "success";
	}

	public TEshopExtinfo getEshop() {
		return eshop;
	}

	public void setEshop(TEshopExtinfo eshop) {
		this.eshop = eshop;
	}

	public Map<String, TEshopExtinfo> getEshopsMap() {
		return eshopsMap;
	}

	public void setEshopsMap(Map<String, TEshopExtinfo> eshopsMap) {
		this.eshopsMap = eshopsMap;
	}
}