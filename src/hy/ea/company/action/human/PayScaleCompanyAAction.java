package hy.ea.company.action.human;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.human.PayScale;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * 集团工作--在职员工级别汇总
 * 
 * @author lwz
 * 
 */
@Controller
@Scope("prototype")
public class PayScaleCompanyAAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private List<Company> comList;
	private InputStream excelStream;
	private PayScale payScale;

	/**
	 * 导出
	 * 
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		List<BaseBean> clist= baseBeanService.getListBeanByHqlAndParams("from Company c where (c.companyID = ? or c.companyPID = ?) and c.companyStatus = ?",new Object[]{ companyID,companyID,"00"});
		Map<String, String> oMap1 =new HashMap<String, String>();
		for (int i = 0; i < clist.size(); i++) {
			oMap1.put(((Company)clist.get(i)).getCompanyID(), ((Company)clist.get(i)).getCompanyName());
		}
		PayScale.setOMap(oMap1);
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		List<BaseBean> lists = baseBeanService.getListBeanByHqlAndParams(hql,params);
		excelStream = excelService.showExcel(PayScale.columnHeadings(), lists);
		return "showexcel";
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch", payScale);
		return getPayScaleaList();
	}

	/**
	 * 加载
	 * 
	 * @return
	 */
	public String getPayScaleaList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		List<BaseBean> clist= baseBeanService.getListBeanByHqlAndParams("from Company c where (c.companyID = ? or c.companyPID = ?) and c.companyStatus = ?",new Object[]{ companyID,companyID,"00"});
		comList = new ArrayList<Company>();
		for(int i = 0 ; i < clist.size() ; i ++){
			comList.add((Company)clist.get(i));
		}
		Map<String, String> oMap1 =new HashMap<String, String>();
		for (int i = 0; i < clist.size(); i++) {
			oMap1.put(((Company)clist.get(i)).getCompanyID(), ((Company)clist.get(i)).getCompanyName());
		}
		PayScale.setOMap(oMap1);
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		
		return "getPayScaleaList";
	}
	private List<Object> getList(Map<String, Object> session,
			CAccount account){
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String hql = " from PayScale j where exists ( select c.companyID from Company c"
				+ " where j.companyID = c.companyID and (c.companyID = ? or c.companyPID = ?)) ";
		parms.add(companyID);
		parms.add(companyID);
		if (search != null && search.equals("search")){
			payScale = (PayScale) session.get("tablesearch");
			//公司
			if (payScale.getCompanyID()!= null
					&& !payScale.getCompanyID().equals("")) {
				hql += " and j.companyID = ? ";
				parms.add(payScale.getCompanyID());
			}
			//职务
			if (payScale.getPosition()!= null
					&& !payScale.getPosition().trim().equals("")) {
				hql += " and j.position like ? ";
				parms.add("%" + payScale.getPosition().trim() + "%");
			}
			//级差
			if (payScale.getScale()!= null
					&& !payScale.getScale().trim().equals("")) {
				hql += " and j.scale like ? ";
				parms.add("%" + payScale.getScale().trim() + "%");
			}
		}
		hql += " order by j.companyID,j.scale";
		results.add(hql);
		results.add(parms.toArray());
		return results;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
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

	public List<Company> getComList() {
		return comList;
	}

	public void setComList(List<Company> comList) {
		this.comList = comList;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public PayScale getPayScale() {
		return payScale;
	}

	public void setPayScale(PayScale payScale) {
		this.payScale = payScale;
	}

	
	
}
