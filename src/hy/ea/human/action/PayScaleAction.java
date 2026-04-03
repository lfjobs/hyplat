package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.PayScale;
import hy.ea.bo.human.vo.CSPPayScaleVO;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
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

/*
 * 工资级别 PayScaleAction
 */
@Controller
@Scope("prototype")
public class PayScaleAction {
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CompanyService companyserverService;
	private String parameter;
	private PayScale payscale;
	private PageForm pageForm;
	private String result;
	private int pageNumber;
	private CSPPayScaleVO payvo;
	private String search;
	private List<BaseBean> beans;

    /********************************人事生产（工资级别）*****************************/
	/*
	 * （人事生产）
	 * 保存工资级别
	 */

	public String savePayScale() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		beans = new ArrayList<BaseBean>();
		String hql1 = "";
		Object[] objects = null;
		if (null == payscale.getPayScaleID()
				|| "".equals(payscale.getPayScaleID())) {
			payscale.setPayScaleID(serverService.getServerID("payscale"));
			parameter = "添加工资级别(职务:" + payscale.getPosition() + ")";
		
		} else {
			String hql = "from PayScale where companyID = ?  and payScaleID=? ";
			Object[] params = { account.getCompanyID(),
					payscale.getPayScaleID() };
			PayScale pay = (PayScale) baseBeanService.getBeanByHqlAndParams(
					hql, params);
			pay.setPayScalekey(null);
			pay.setPayScaleID(serverService.getServerID("payscale"));			
			pay.setStatus("01");
			hql1= "update StaffAppraisal set payScaleID = ? where companyID = ? and payScaleID = ?";
			objects = new String[]{pay.getPayScaleID(),account.getCompanyID(),payscale.getPayScaleID()};
			parameter = "修改工资级别(职务:" + pay.getPosition() + ")";
			beans.add(pay);
		}
		payscale.setImpotdate(new Date());
		payscale.setCompanyID(account.getCompanyID());
		payscale.setStatus("00");
		beans.add(payscale);
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		if(objects == null){
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,null, null);
		}else{
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql1}, objects);
		}
		
		return "succ";
	}

	/*
	 * （人事生产）
	 * 删除工资级别
	 */

	public String delPayScale() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql = "update PayScale set status = '01' where payScaleID= ? and companyID = ? ";
		Object[] params = { payscale.getPayScaleID(), account.getCompanyID() };
		String hql1 = "from PayScale where payScaleID= ? and companyID = ? ";
		CLogBook logBook = logBookService.saveCLogBook(null, "删除工资级别(职务："
				+ ((PayScale) baseBeanService.getBeanByHqlAndParams(hql1,
						params)).getPosition() + ")", account);
		beans = new ArrayList<BaseBean>();
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
		return "succ";
	}

	/*
	 * （人事生产）
	 * 得到工资级别列表
	 */

	public String getListPayScale() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Object[] params = { account.getCompanyID() };
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				" from PayScale where companyID = ? and status = '00' order by num,scale", params);
		return "list";
	}

	/*
	 * （人事生产）
	 * 导出工资级别
	 */

	public String showExcel() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Company com=companyserverService.getCompanyByCompanyID(account.getCompanyID());
		Map<String, String> omap=new HashMap<String, String>();
		omap.put(com.getCompanyID(), com.getCompanyName());
		PayScale.setOMap(omap);
		Object[] params = { account.getCompanyID() };
		String hql = "from PayScale where companyID = ? and status = '00'  order by scale";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
				params);
		excelStream = excelService.showExcel(PayScale.columnHeadings(), list);
		CLogBook logBook = logBookService.saveCLogBook(null, "导出工资级别", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public String isUniquenessScale() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params = { account.getCompanyID(), result };
		String hql = "select count(*) from PayScale where companyID = ? and status = '00' and scale = ? ";
		baseBeanService.getConutByByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rowcount", baseBeanService.getConutByByHqlAndParams(hql,
				params));
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/********************************人事生产（员工工资级别管理）*****************************/
	
	/**
	 * 根据条件查询员工工资级别
	 * 
	 * @return getStaffPayScaleList()
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", payvo);
		return getStaffPayScaleList();
	}
	
	
	/**
	 * 员工工资级别管理列表(被调用)
	 * 
	 * @return List<BaseBean>
	 */
	public DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(CSPPayScaleVO.class);
		dc.add(Restrictions.eq("cspcompanyid", account.getCompanyID()));
		if (search != null && search.equals("search")) {
			payvo = (CSPPayScaleVO) session.get("tablesearch");
			if (null != payvo.getStaffName()
					&& !"".equals(payvo.getStaffName())) {
				dc.add(Restrictions.like("staffName", payvo.getStaffName(),
						MatchMode.ANYWHERE));
			}
			if (null != payvo.getScale() && !"".equals(payvo.getScale())) {
				dc.add(Restrictions.like("scale", payvo.getScale(),
						MatchMode.ANYWHERE));
			}
			if (null != payvo.getPosition() && !"".equals(payvo.getPosition())) {
				dc.add(Restrictions.like("position", payvo.getPosition(),
						MatchMode.ANYWHERE));
			}
			if (null != payvo.getStaffIdentityCard() && !"".equals(payvo.getStaffIdentityCard())) {
				dc.add(Restrictions.like("staffIdentityCard", payvo.getStaffIdentityCard(),
						MatchMode.ANYWHERE));
			}
			if (null != payvo.getStaffCode()
					&& !"".equals(payvo.getStaffCode())) {
				dc.add(Restrictions.like("staffCode", payvo.getStaffCode(),
						MatchMode.ANYWHERE));
			}
		}
		dc.addOrder(Order.desc("scale"));
		return dc;
	}
	
    /**
     * 员工工资级别列表
     * 
     * @return csplist
     */
	
	public String getStaffPayScaleList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList());
		return "csplist";
	}
	
	/**
	 * 员工工资级别导出
	 * @return
	 */
	
	public String showStaffExcel() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		excelStream = excelService.showExcel(CSPPayScaleVO.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		CLogBook logBook = logBookService.saveCLogBook(null, "导出员工工资级别", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	/** *************************************公司汇总（工资级别）************************************************ */
	
	/**
	 * 工资级别汇总查询
	 * 
	 * @return getPayScaleList()
	 */
	public String toSearchSummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", payscale);
		return getPayScaleList();
	}

	/**
	 * 工资级别汇总列表
	 * 
	 * @return plist
	 */
	public String getPayScaleList() {
		pageForm = baseBeanService.getPageFormByDC(null != pageForm ? pageForm
				.getPageNumber() : 1, (pageNumber == 0 ? 10 : pageNumber),
				getPSList());
		return "plist";
	}
	
	/**
	 * 工资级别汇总列表（被调用）
	 * 
	 * @return List<BaseBean>
	 */
	public DetachedCriteria getPSList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		ArrayList<String> cids = companyserverService.getCompanyAndItsChildrenIDs((String) session.get("companyID"));
		DetachedCriteria dcc = DetachedCriteria.forClass(Company.class);
		dcc.add(Restrictions.in("companyID", cids));
		List<BaseBean> list = baseBeanService.getListByDC(dcc);
		Map<String, String> map = new HashMap<String, String>();
		for (BaseBean bb : list) {
			Company cc = (Company) bb;
			map.put(cc.getCompanyID(), cc.getCompanyName());
		}
		PayScale.setOMap(map);
		DetachedCriteria dc = DetachedCriteria.forClass(PayScale.class);
		dc.add(Restrictions.in("companyID", cids));
		if (null != search && search.equals("search")) {
			payscale = (PayScale) session.get("tablesearch");
			if (null != payscale.getCompanyID()
					&& !"".equals(payscale.getCompanyID())) {
				dc.add(Restrictions.eq("companyID", payscale.getCompanyID()));
			}
			if (null != payscale.getScale() && !"".equals(payscale.getScale())) {
				dc.add(Restrictions.like("scale", payscale.getScale(),MatchMode.ANYWHERE));
			}
			if (null != payscale.getPosition()
					&& !"".equals(payscale.getPosition())) {
				dc.add(Restrictions.like("position", payscale.getPosition(),MatchMode.ANYWHERE));
			}
		}
		dc.add(Restrictions.eq("status", "00"));
		dc.addOrder(Order.asc("companyID"));
		dc.addOrder(Order.desc("scale"));
		return dc;
	}

	/**
	 * 工资级别汇总导出
	 * 
	 * @return
	 */
	
	public String showSummaryExcel() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		excelStream = excelService.showExcel(PayScale.columnHeadings(),baseBeanService.getListByDC(getPSList()));
		CLogBook logBook = logBookService.saveCLogBook(null, "导出公司工资级别汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/******************************公司汇总（员工工资级别）**************************************/
	
	/**
	 * 员工工资级别汇总列表 （被调用）
	 * 
	 * @return List<BaseBean>
	 */
	public DetachedCriteria getSummaryList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount)session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(CSPPayScaleVO.class);
		dc.add(Restrictions.in("cspcompanyid", companyserverService
				.getCompanyAndItsChildrenIDs(account.getCompanyID())));
		if (search != null && search.equals("search")) {
			payvo = (CSPPayScaleVO) session.get("tablesearch");
			if (null != payvo.getStaffName()
					&& !"".equals(payvo.getStaffName())) {
				dc.add(Restrictions.like("staffName", payvo.getStaffName(),
						MatchMode.ANYWHERE));
			}
			if (null != payvo.getScale() && !"".equals(payvo.getScale())) {
				dc.add(Restrictions.like("scale", payvo.getScale(),
						MatchMode.ANYWHERE));
			}
			if (null != payvo.getCspcompanyid()
					&& !"".equals(payvo.getCspcompanyid())) {
				dc.add(Restrictions.eq("cspcompanyid", payvo.getCspcompanyid()));
			}
			if (null != payvo.getPosition() && !"".equals(payvo.getPosition())) {
				dc.add(Restrictions.like("position", payvo.getPosition(),
						MatchMode.ANYWHERE));
			}
			if (null != payvo.getStaffCode()
					&& !"".equals(payvo.getStaffCode())) {
				dc.add(Restrictions.like("staffCode", payvo.getStaffCode(),
						MatchMode.ANYWHERE));
			}
			if (null != payvo.getStaffIdentityCard()
					&& !"".equals(payvo.getStaffIdentityCard())) {
				dc.add(Restrictions.like("staffIdentityCard", payvo.getStaffIdentityCard(),
						MatchMode.ANYWHERE));
			}
		}
		dc.addOrder(Order.asc("cspcompanyid"));
		dc.addOrder(Order.desc("scale"));
		return dc;
	}
	
	/**
	 * 员工工资级别汇总列表
	 * 
	 * @return cspstafflist
	 */
	
	public String getStaffPayScaleSummaryList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getSummaryList());
		return "cspstafflist";
	}
	
	/**
	 * 员工工资级别汇总导出
	 * 
	 * @return showexcel
	 */
	
	public String showStaffSummaryExcel() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		excelStream = excelService.showExcel(CSPPayScaleVO.columnHeadings(),
				baseBeanService.getListByDC(getSummaryList()));
		CLogBook logBook = logBookService.saveCLogBook(null, "导出公司员工级别汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	/**
	 * 员工工资级别汇总查询
	 * 
	 * @return getStaffPayScaleSummaryList()
	 */
	
	public String toSearchStaffSummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", payvo);
		return getStaffPayScaleSummaryList();
	}

	public PayScale getPayscale() {
		return payscale;
	}

	public void setPayscale(PayScale payscale) {
		this.payscale = payscale;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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

	public CSPPayScaleVO getPayvo() {
		return payvo;
	}

	public void setPayvo(CSPPayScaleVO payvo) {
		this.payvo = payvo;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
