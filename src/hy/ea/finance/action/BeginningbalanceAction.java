package hy.ea.finance.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.BeginningBalance;
import hy.ea.service.CLogBookService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;

import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * BeginningbalanceAction 初期余额
 * 
 * @author lf
 * 
 */
@Controller
@Scope("prototype")
public class BeginningbalanceAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private BeginningBalance beginningBalance;
	private PageForm pageForm;
	private InputStream excelStream;
	private String result;
	private String search;
	private List<BaseBean> staffList;
	private int pageNumber;
	private String sdate;
	private String zz; // 区分现金余额还是银行余额
	private String balance2;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("beginningBalance", beginningBalance);
		return getListIPaddress();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(BeginningBalance.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		if (search != null && search.equals("search")) {
			if (!"".equals(sdate)) {
				dc.add(Restrictions.eq("Customizedate", Utilities
						.getDateFromString(sdate + "-01", "yyyy-MM-dd")));
			}
		}
		return dc;
	}

	public String getListIPaddress() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = " from Staff where staffID in (select staffID from COS where companyID = ? and cosStatus = '50' ) ";
		Object[] params = { account.getCompanyID() };
		staffList = baseBeanService.getListBeanByHqlAndParams(hql, params);
		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), getList());
		return "list";
	}

	/**
	 * 添加初期余额
	 * 
	 * @return
	 */
	public String addIPaddress() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (beginningBalance.getBeginningBalanceID() == null
				|| "".equals(beginningBalance.getBeginningBalanceID())) {
			beginningBalance.setBeginningBalanceID(serverService
					.getServerID("beginningBalance"));
			parameter = "添加期初余额(责任人:" + account.getAccountName() + ")";
		} else {
			String hql2 = "from BeginningBalance where companyID=? and BeginningBalanceID=?";
			BeginningBalance aff = (BeginningBalance) baseBeanService
					.getBeanByHqlAndParams(hql2,
							new Object[] { account.getCompanyID(),
									beginningBalance.getBeginningBalanceID() });
			beginningBalance.setBeginningBalanceKey(aff.getBeginningBalanceKey());
			parameter = "修改期初余额(责任人:" + account.getAccountName() + ")";
		}
		beginningBalance.setCustomizedate(Utilities.getDateFromString(sdate
				+ "-01", "yyyy-MM-dd"));
		beginningBalance.setCompanyID(account.getCompanyID());
		beginningBalance.setBbtype("01");
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		// baseBeanService.update(addressIP);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeansList.add(beginningBalance);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,
				null, null);
		return "success";
	}

	/**
	 * 报表保存余额
	 * 
	 * @return
	 */
	public String ajaxSava() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		//获取查询月份的下个月份值
		Calendar c1 = Calendar.getInstance();
		c1.setTime(Utilities.getDateFromString(sdate+"-01", "yyyy-MM-dd"));
		c1.add(Calendar.MONTH, 1);
		BeginningBalance beginningBalance = (BeginningBalance) baseBeanService
				.getBeanByHqlAndParams(
						"from BeginningBalance where companyID=? and Customizedate=? ",
						new Object[] {
								account.getCompanyID(),c1.getTime()});
		if (beginningBalance == null) {
			beginningBalance=new BeginningBalance();
			beginningBalance.setBeginningBalanceID(serverService
					.getServerID("beginningBalance"));
			beginningBalance.setCustomizedate(c1.getTime());
			beginningBalance.setCompanyID(account.getCompanyID());
			beginningBalance.setBbtype("00");
		}
		balance2=balance2.substring(0,balance2.indexOf(".")+3);
		if (beginningBalance.getBbtype().equals("00")) {
			if (zz.equals("00")) {
				beginningBalance.setCashBalance(balance2);
			} else if (zz.equals("01")) {
				beginningBalance.setBankBalance(balance2);
			}
		}
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(beginningBalance);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,
				null, null);
		return "success";
	}
	
	/**
	 * 查询设置月日期唯一性
	 * 
	 * @return
	 */
	public String ajaxSearch() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String tt="00";
		beginningBalance=(BeginningBalance)baseBeanService.getBeanByHqlAndParams("from BeginningBalance bb where bb.companyID=? and bb.Customizedate=?", new Object[]{account.getCompanyID(),Utilities.getDateFromString(sdate+"-01", "yyyy-MM-dd")});
		if(beginningBalance!=null){
			tt="01";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tt", tt);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	

	/**
	 * 删除初期余额
	 * 
	 * @return
	 */
	public String delIPaddress() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		Object[] params = { account.getCompanyID(),
				beginningBalance.getBeginningBalanceID() };
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		String hql = "delete from BeginningBalance where companyID=? and BeginningBalanceID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,
				new String[] { hql }, params);
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

	public List<BaseBean> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<BaseBean> staffList) {
		this.staffList = staffList;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public BeginningBalance getBeginningBalance() {
		return beginningBalance;
	}

	public void setBeginningBalance(BeginningBalance beginningBalance) {
		this.beginningBalance = beginningBalance;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getZz() {
		return zz;
	}

	public void setZz(String zz) {
		this.zz = zz;
	}

	public String getBalance2() {
		return balance2;
	}

	public void setBalance2(String balance2) {
		this.balance2 = balance2;
	}
}
