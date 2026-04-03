package hy.ea.invoicing.action.voucher;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.voucher.FiscalPeriod;
import hy.ea.service.CLogBookService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 会计期间
 * 
 * @author mz
 */
@Controller
@Scope("prototype")
public class FiscalPeriodAction{
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;

	@Resource
	private CLogBookService logBookService;
	

	private Utilities utilities;

	private PageForm pageForm;
	private int pageNumber;
	private String result;
	private String search;
	private FiscalPeriod  fiscalPeriod;
	private Map<String,FiscalPeriod> fiscalPeriodmap;
	private String parameter;

	
	
	
	
	/**
	 * 
	 * 
	 * 获取会计期间列表
	 * @return
	 */
	public String getFiscalPeriodList(){
		
		pageForm= baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "fisperiod";
		
	}
	
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(FiscalPeriod.class);
		dc.add(Restrictions.eq("companyID", companyID));
        dc.addOrder(Order.asc("year"));
		
		if (search != null && search.equals("search")) {
			 fiscalPeriod = (FiscalPeriod) session.get("tablesearch");
			 if(fiscalPeriod.getYear()!=null&&!fiscalPeriod.getYear().equals("")){
			 dc.add(Restrictions.like("year",fiscalPeriod.getYear().trim(), MatchMode.ANYWHERE));
			 }
			
		}
		return dc;
	}
	
	
	/**
	 * 
	 * 
	 * 查询
	 * @return
	 */
	public String toSearch(){
		ActionContext.getContext().getSession().put("tablesearch", fiscalPeriod);
		return getFiscalPeriodList();
	}
	
	
	
	/**
	 * 
	 * 
	 * 保存以及更新
	 * @return
	 */
	public String saveFiscalPeriod(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		
		String hqlstaff = "from Staff where staffID = ?";
		String hqlcom = "from Company where companyID = ?";
		
		if (fiscalPeriodmap != null) {
			for (FiscalPeriod fisproid : fiscalPeriodmap.values()) {
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hqlstaff, new Object[] { account.getStaffID() });

				if (fisproid.getFpID() == null
						|| "".equals(fisproid.getFpID())) {
					
					if (staff != null) {
						fisproid.setCreatorID(account.getStaffID());
						fisproid.setCreatorName(staff.getStaffName());
						
					}

					fisproid.setCreateDate(utilities.getDateString(new Date(), "yyyyMMddHHmmss"));
					Company company = (Company) baseBeanService.getBeanByHqlAndParams(
							hqlcom, new Object[] { account.getCompanyID()});
					fisproid.setGroupCompanyID(company.getGroupCompanySn());
					
					fisproid.setCompanyID(account.getCompanyID());
					if (company != null) {
						fisproid.setCompanyID(account.getCompanyID());
						fisproid.setCompanyName(company.getCompanyName());
					}
					
					fisproid.setFpID(serverService
							.getServerID("fpID"));
					parameter = "新增会计年度(添加人:" + fisproid.getCreatorName()+")";
					baseBeanList.add(fisproid);
				} else {
					String hql = "from FiscalPeriod where fpID = ?";
					FiscalPeriod fp = (FiscalPeriod) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{fisproid.getFpID()});
					if (staff != null) {
						fp.setUpdatorID(account.getStaffID());
						fp.setUpdatorName(staff.getStaffName());
						
						
					 }
                     
					fp.setUpdateDate(utilities.getDateString(new Date(), "yyyyMMddHHmmss"));
					fp.setYear(fisproid.getYear());
					fp.setStartDate(fisproid.getStartDate());
					fp.setEndDate(fisproid.getEndDate());
				
					parameter = "修改会计年度(修改人:" + fp.getUpdatorName()+")";
					baseBeanList.add(fp);

				}
				
				
				CLogBook logbook = logBookService.saveCLogBook(organizationID,
						parameter, account);
				baseBeanList.add(logbook);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
					null, null);
		}
		return "success";
		
	}
	
	/**
	 * 
	 * 判断是否可以修改或删除也就是说判断是否是最大年限
	 * @return
	 */
	public String isCanUpdateOrDelete(){

	   CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
	   String hql = "from FiscalPeriod where companyID = ? order by year desc";
	   FiscalPeriod fis = (FiscalPeriod) baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()}).get(0);
		Map<String,Object> map= new HashMap<String,Object>();
	   
	   if(fis.getFpID().equals(fiscalPeriod.getFpID())){
			map.put("result", "success");
		}else{
			map.put("result","fail");
		}
	   JSONObject jo = JSONObject.fromObject(map);
	   this.result = jo.toString();
		return "success";
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * @return
	 */
	public String getMaxYearByYear(){
		 CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		 String hql = "from FiscalPeriod where companyID = ? and year like ? order by year desc";
		List<BaseBean> listfis = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{account.getCompanyID(),"%"+fiscalPeriod.getYear()+"%"});
		Map<String,Object> map= new HashMap<String,Object>();
		if(listfis.size()!=0){
			
		   FiscalPeriod fp = (FiscalPeriod) listfis.get(0);
		   map.put("result", fp.getYear());
			
		}else{
			  map.put("result", "");
		}
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	
	/**
	 * 
	 * 删除
	 * @return
	 */
	public String delFiscalPeriod(){
		try{
		String hql = "from FiscalPeriod where fpID = ?";
		FiscalPeriod fs = (FiscalPeriod) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{fiscalPeriod.getFpID()});
		baseBeanService.deleteBeanByKey(FiscalPeriod.class, fs.getFpKey());
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return "success";
	}
	

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public FiscalPeriod getFiscalPeriod() {
		return fiscalPeriod;
	}

	public void setFiscalPeriod(FiscalPeriod fiscalPeriod) {
		this.fiscalPeriod = fiscalPeriod;
	}

	public Map<String, FiscalPeriod> getFiscalPeriodmap() {
		return fiscalPeriodmap;
	}

	public void setFiscalPeriodmap(Map<String, FiscalPeriod> fiscalPeriodmap) {
		this.fiscalPeriodmap = fiscalPeriodmap;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Utilities getUtilities() {
		return utilities;
	}

	public void setUtilities(Utilities utilities) {
		this.utilities = utilities;
	}
	
	
}
