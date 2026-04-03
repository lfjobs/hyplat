package hy.ea.finance.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.CSubjects;
import hy.ea.bo.invoicing.voucher.Subs;
import hy.ea.bo.invoicing.voucher.startTime;
import hy.ea.finance.service.CSubjectsService;
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
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
/**
 * 科目管理
 * @author lou
 *
 */
@Controller
@Scope("prototype")
public class CSubjectsAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CSubjectsService subjectsService;
	@Resource
	private CLogBookService logBookService;
	private String subjectsID;
	private String result;
	private CSubjects csbjects;
	private String sub;
	private PageForm pageForm;
	private List<CSubjects> csbjectsList;
	private String level;
	private String parameter;
	private int pageNumber;
	private String datess;
	private Subs subs;
	private String sdate;
	private List<BaseBean> report;
	private String time;
	private String category;
	private String direction;

	/**
	 * 进入KEMU树管理
	 * 
	 * @return
	 */
	public String CSubjectsManage() {
		ActionContext.getContext().getSession()
				.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交;
		return "csbjects_manager";
	}

	/**
	 * 根据subjectsID查看代码的详细信息
	 */
	public String editCsubejsts() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		csbjects = (CSubjects) subjectsService.getCSubjectsByID(
				account.getCompanyID(), subjectsID);
		Map<String, CSubjects> map = new HashMap<String, CSubjects>();
		map.put("csbjects", csbjects);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 根据companyID和subjectsID查询其子节点
	 */
	public String getListCsubejstsByPID() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<CSubjects> subjectsList = subjectsService.getCSubjectsListByPID(
				account.getCompanyID(), subjectsID);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectsList", subjectsList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	/**
	 * ajax: 根据comId、subId、页数、行数 查询其子节点
	 */
	public String getAjaxListCsubejstsByPID(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		String hql="from CSubjects where companyID = ? and subjectsPID = ? and subjectsStatus !='02' order by subjectsNumbers";
		Object[] obj={account.getCompanyID(),subjectsID};
		pageForm=baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber),hql,obj);
		String hqlToo="select count(*) from CSubjects where companyID = ? and subjectsPID = ? and subjectsStatus !=02";
		int search=baseBeanService.getConutByByHqlAndParams(hqlToo,obj);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		map.put("search",search);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	/**
	 * 添加或修改一家公司的代码
	 */
	public String saveCsubejsts() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		csbjects.setCompanyID(account.getCompanyID());
		System.out.println(csbjects.getSubjectsCategory());
		System.out.println(csbjects.getSubjectsDirection());
		if (null == csbjects.getSubjectsID()
				|| "".equals(csbjects.getSubjectsID())) {
			csbjects.setSubjectsID(serverService.getServerID("csbjects"));
			csbjects.setCurrentLevel(level);
			csbjects.setSubjectsStatus("01");
			parameter = "保存公司代码(公司代码名称:" + csbjects.getSubjectsName() + ")";
		} else {
			CSubjects csb = (CSubjects) subjectsService.getCSubjectsByID(
					account.getCompanyID(), csbjects.getSubjectsID());
			parameter = "修改公司代码(公司代码名称:" + csb.getSubjectsName() + ")";
		}

		List<BaseBean> beans = new ArrayList<BaseBean>();
		subjectsID = csbjects.getSubjectsPID();
		String hql = "update CSubjectsRule set usedLevel=? where companyID=? and ?>usedLevel";
		Object[] params = { level, account.getCompanyID(), level };
		CLogBook logbook = logBookService
				.saveCLogBook(null, parameter, account);
		beans.add(csbjects);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		return getCsubejstsListAll();
	}

	/**
	 * 递归查询出下面所有的子集代码
	 * 
	 */
	public String getCsubejstsListAll() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Object[] params = { account.getCompanyID(), subjectsID, "00", "01" };
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						" from CSubjects where companyID = ? and subjectsPID = ? and (subjectsStatus = ? or subjectsStatus = ? ) order by subjectsNumbers",
						params);
		return "csbjectslist";
	}

	/**
	 * 删除一家公司的代码
	 */
	public String delCsubejsts() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		List<BaseBean> beans = new ArrayList<BaseBean>();
		CLogBook logbook = logBookService.saveCLogBook(
				null,
				"删除公司代码(公司代码名称："
						+ ((CSubjects) subjectsService.getCSubjectsByID(
								account.getCompanyID(),
								csbjects.getSubjectsID())).getSubjectsName()
						+ ")", account);
		beans.add(logbook);
		String CSubjectsByPIDStr = subjectsService.getCSubjectsByPIDString(
				account.getCompanyID(), csbjects.getSubjectsID(),
				csbjects.getSubjectsID());
		String[] strs = CSubjectsByPIDStr.split("_");
		List<Object[]> parms = new ArrayList<Object[]>();
		String[] hqls = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			hqls[i] = " update  CSubjects set subjectsStatus = ? where companyID = ? and subjectsID = ? ";
			parms.add(new Object[] { "02", account.getCompanyID(), strs[i] });
		}
		baseBeanService.executeHqlsByParamsList(beans, hqls, parms);
		subjectsID = csbjects.getSubjectsPID();
		csbjects.setSubjectsID("");
		return getCsubejstsListAll();
	}

	// ajax判断科目名是否存在
	public String ajaxCsubejstsName() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "select count(*) from CSubjects where companyID=? and subjectsName=? and subjectsPID=? and (subjectsStatus = ? or subjectsStatus = ? )";
		Object[] params = { account.getCompanyID(), csbjects.getSubjectsName(),csbjects.getSubjectsPID(),
				"00", "01" };
		int count = baseBeanService.getConutByByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	// ajax判断编号是否存在
	public String subjectsNumbersorNot() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "select count(*) from CSubjects where companyID=? and subjectsNumbers=? and (subjectsStatus = ? or subjectsStatus = ? )";
		Object[] params = { account.getCompanyID(),
				csbjects.getSubjectsNumbers(), "00", "01" };
		int count = baseBeanService.getConutByByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 根据subjectsNumbers或者subjectsName查询
	 * 
	 * @return
	 */
	public String getSubjectsListBySubjects() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = " from CSubjects where  ( subjectsNumbers like ? or subjectsName like ? ) and companyID = ? and (subjectsStatus = ? or subjectsStatus = ? )";
		Object[] params = { csbjects.getSubjectsName() + "%",
				"%" + csbjects.getSubjectsName() + "%", account.getCompanyID(),
				"00", "01" };
		List<BaseBean> basebeanlist = baseBeanService
				.getListBeanByHqlAndParams(hql, params);
		List<CSubjects> subjectrList = new ArrayList<CSubjects>();
		if (subjectrList != null)
			for (BaseBean baseBean : basebeanlist) {
				CSubjects cs = (CSubjects) baseBean;
				subjectrList.add(cs);
			}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectrList", subjectrList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/************************************************** 科目期初余额 *********************************************/

	/**
	 * 根据subjectsID查看代码的详细信息
	 */
	public String getListBalanceByPID() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql=" from Subs where companyID = ? and subjectsID = ?";
		List<Object> params =new ArrayList<Object>();
		params.add(account.getCompanyID());
		params.add(subjectsID);
		/*if(sdate != null && edate != null && !("").equals(sdate)
				&& !("").equals(edate)){
			hql+=" and datess between ? and ?";
			params.add(Utilities.getDateFromString(sdate,"yyyy-MM"));
			params.add(Utilities.getDateFromString(edate,"yyyy-MM"));
		}*/
		hql+=" order by datess";
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),hql,
						params.toArray());
		return "balancelist";
	}

	/**
	 * ajax判断当年是否添加过期初余额
	 */
	public String ajaxDatess() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "select count(*) from Subs where companyID=? and subjectsID = ?";
		//int year = new Date().getYear() + 1900;
		Object[] params = { account.getCompanyID(), subs.getSubjectsID()};
		int count = baseBeanService.getConutByByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	/**
	 * ajax判断当年是否添加过期初余额
	 */
	public String ajaxStartTime() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "select count(*) from startTime where companyID=?";
		//int year = new Date().getYear() + 1900;
		Object[] params = { account.getCompanyID()};
		int count = baseBeanService.getConutByByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	/**
	 * ajax判断该公司是否结过帐
	 */
	public String ajaxEndAccount() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "select count(*) from endAccount where companyID=?";
		//int year = new Date().getYear() + 1900;
		Object[] params = { account.getCompanyID()};
		int count = baseBeanService.getConutByByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 添加或修改一家公司的代码
	 */
	public String saveSubs() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		startTime startTime= (startTime)baseBeanService.getBeanByHqlAndParams("from startTime where companyID=?", new Object[]{account.getCompanyID()});
		subs.setCompanyID(account.getCompanyID());
		subs.setSubearlyID(serverService.getServerID("subs"));
		subs.setDatess(startTime.getStartTime());
		subs.setStartCash(String.valueOf(Float.parseFloat(subs.getStartCash())));
		subs.setCforLoan("0.0");
		subs.setCloan("0.0");
		parameter = "保存科目期初余额(公司代码名称:" + subs.getSubjectsName() + ")";
		subjectsID = subs.getSubjectsID();
		List<BaseBean> beans = new ArrayList<BaseBean>();
		CLogBook logbook = logBookService
				.saveCLogBook(null, parameter, account);
		beans.add(subs);
		beans.add(logbook);
		String StartCash = subs.getStartCash();
		if (subs.getSdirection().equals("贷")) {
			StartCash = "-" + StartCash;
		}
		saveSubsByPid(beans, subjectsID, StartCash,subs.getDatess());
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return getListBalanceByPID();
	}

	/**
	 * 算父级科目期初余额（递归）
	 * 
	 * @param beans
	 *            实体集合
	 * @param subjectsID
	 *            子级科目id
	 * @param prices
	 *            子集科目期初余额
	 */
	private void saveSubsByPid(List<BaseBean> beans, String subjectsID,
			String prices,Date dd) {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		// 根据子级科目id获取科目父级id
		String hql = "from CSubjects where subjectsID=? and companyID=?";
		csbjects = (CSubjects) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { subjectsID, account.getCompanyID() });
		// 判断父级是否是002（科目树顶级）
		if (csbjects.getSubjectsNumbers().length()>4) {
			// 根据科目父级id查询该父级下所有子集
			String hqllist = "from CSubjects where subjectsPID=? and companyID=? and (subjectsStatus=? or subjectsStatus=?)";
			List<BaseBean> csList = baseBeanService.getListBeanByHqlAndParams(
					hqllist,
					new Object[] { csbjects.getSubjectsPID(),
							account.getCompanyID(),"00","01"});
			float price = Float.valueOf(prices);
			CSubjects cs = new CSubjects();
			// 循环所有子集,期初余额依次相加赋给父级科目
			for (BaseBean baseBean : csList) {
				cs = (CSubjects) baseBean;
				// 如过改子集是该方法产值参数里的子级科目退出该循环
				if (cs.getSubjectsID().equals(subjectsID)) {
					continue;
				}
				// 根据科目id查询该级科目余额信息
				String hqls = "from Subs where subjectsID=? and companyID=? and datess=?";
				subs = (Subs) baseBeanService.getBeanByHqlAndParams(
						hqls,
						new Object[] { cs.getSubjectsID(),
								account.getCompanyID(),dd });
				if (subs != null) {
					if (Float.valueOf(subs.getStartCash()) != 0) {
						if (subs.getSdirection().equals("贷")) {
							subs.setStartCash("-" + subs.getStartCash());
						}
						price = price + Float.valueOf(subs.getStartCash());
					}
				}
			}
			parameter = "";
			CLogBook logbook = new CLogBook();
			csbjects = (CSubjects) baseBeanService.getBeanByHqlAndParams(
					hql,
					new Object[] { csbjects.getSubjectsPID(),
							account.getCompanyID() });
			// 查询该月父级科目是否有期初余额，有则修改该条数据，没有添加一天新数据
			String hqlsu = "from Subs where subjectsID=? and companyID=? and datess=?";
			subs = (Subs) baseBeanService.getBeanByHqlAndParams(
					hqlsu,
					new Object[] {
							csbjects.getSubjectsID(),
							account.getCompanyID(),dd });
			if (subs == null) {
				subs = new Subs();
				subs.setCompanyID(account.getCompanyID());
				subs.setSubearlyID(serverService.getServerID("subs"));
				subs.setDatess(dd);
				subs.setSubjectsID(csbjects.getSubjectsID());
				subs.setSubjectsName(csbjects.getSubjectsName());
				subs.setSubjectsNumbers(csbjects.getSubjectsNumbers());
				subs.setSubjectsPID(csbjects.getSubjectsPID());
				subs.setCforLoan("0.0");
				subs.setCloan("0.0");
				subs.setCurrentLevel(String.valueOf(Integer.parseInt(csbjects.getCurrentLevel())-1));
				parameter = "保存科目期初余额(公司代码名称:" + subs.getSubjectsName() + ")";
				logbook = logBookService.saveCLogBook(null, parameter, account);
			} else {
				parameter = "修改科目期初余额(公司代码名称:" + subs.getSubjectsName() + ")";
				logbook = logBookService.saveCLogBook(null, parameter, account);
			}
			float endCash=price+Float.valueOf(subs.getCloan())-Float.valueOf(subs.getCforLoan());
			String ec = Float.toString(endCash);
			if (ec.substring(0, 1).equals("-")) {
				subs.setEdirection("贷");
				subs.setEndCash(ec.substring(1));
			} else if (ec.equals("0.0")) {
				subs.setEdirection("平");
				subs.setEndCash(Float.toString(endCash));
			} else {
				subs.setEdirection("借");
				subs.setEndCash(Float.toString(endCash));
			}
			String tos = Float.toString(price);
			if (tos.substring(0, 1).equals("-")) {
				subs.setSdirection("贷");
				subs.setStartCash(tos.substring(1));
			} else if (tos.equals("0.0")) {
				subs.setSdirection("平");
				subs.setStartCash(Float.toString(price));
			} else {
				subs.setSdirection("借");
				subs.setStartCash(Float.toString(price));
			}
			beans.add(subs);
			beans.add(logbook);
			saveSubsByPid(beans, csbjects.getSubjectsID(), tos,dd);
		}
	}
	
	/**
	 * 科目余额生成报表
	 * @return
	 */
	public String toStatements() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (sdate != null && !("").equals(sdate)) {
			DetachedCriteria dc = DetachedCriteria.forClass(Subs.class);
			dc.add(Restrictions.eq("datess",Utilities.getDateFromString(sdate + "-01","yyyy-MM-dd")));
			if(level=="allCompany"){
				dc.add(Restrictions.eq("companyID",account.getCompanyID()));
			}else{
				dc.add(Restrictions.eq("companyID",account.getCompanyID()));
			}
			dc.addOrder( Order.asc("subjectsNumbers"));
			report = baseBeanService.getListByDC(dc);
		}
		return "report";
	}
	
	/**
	 * 跳转页面
	 * @return
	 */
	public String toPage(){
		return "topage";
	}
	/**
	 * 跳转页面
	 * @return
	 */
	public String toCPage(){
		return "tocpage";
	}

	public ServerService getServerService() {
		return serverService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	public String getSubjectsID() {
		return subjectsID;
	}

	public void setSubjectsID(String subjectsID) {
		this.subjectsID = subjectsID;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public CSubjects getCsbjects() {
		return csbjects;
	}

	public void setCsbjects(CSubjects csbjects) {
		this.csbjects = csbjects;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public List<CSubjects> getCsbjectsList() {
		return csbjectsList;
	}

	public void setCsbjectsList(List<CSubjects> csbjectsList) {
		this.csbjectsList = csbjectsList;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getDatess() {
		return datess;
	}

	public void setDatess(String datess) {
		this.datess = datess;
	}

	public Subs getSubs() {
		return subs;
	}

	public void setSubs(Subs subs) {
		this.subs = subs;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public List<BaseBean> getReport() {
		return report;
	}

	public void setReport(List<BaseBean> report) {
		this.report = report;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
