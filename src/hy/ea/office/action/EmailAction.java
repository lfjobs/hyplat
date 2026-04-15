package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.UpLoadFile;
import hy.ea.bo.office.Email;
import hy.ea.service.CLogBookService;
import hy.ea.util.statisticaBean;
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
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 邮箱管理
 * 
 * @author 杨金歌
 */
@Controller
@Scope("prototype")
public class EmailAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private CAccount account;
	private Email email;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
	private String status;
	private String emailStatus;
	private String listStatus;// 区分是收件箱，草稿箱，已发送，已删除返回列表
	private String result;
	private String zhType;
	private String reciveID;
	private JFreeChart chart;

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * 公司下所有有邮箱的账号列表
	 * 
	 * @return
	 */
	public String getAccountList() {
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String companyID = request.getParameter("companyID");
		String hql = " from CAccount where companyID = ? and accountStatus = ?";
		List<BaseBean> accountList = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { companyID,"00" });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountList", accountList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 根据当前公司获取和该公司一个集团下所有公司
	 * 
	 * @return
	 */
	public String getAllCompanyByCurrent() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String companyID = account.getCompanyID();
		String hql1 = "from Company where companyID = ?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(hql1,
				new Object[] { companyID });
		String groupCompanySn = company.getGroupCompanySn();

		String hql = " from Company where groupCompanySn = ? ";
		List<BaseBean> companylist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { groupCompanySn });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companylist", companylist);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 根据选中的公司获得该公司下得所有部门（暂不使用）
	 * 
	 * @return
	 */
	public String getAllOrganizations() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String companyID = request.getParameter("companyID");
		String hql1 = "from COrganization where companyID = ? and organizationPID = ? and Status ='00'";
		List<BaseBean> orgaizationlist = baseBeanService
				.getListBeanByHqlAndParams(hql1, new Object[] { companyID,
						companyID });

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgaizationlist", orgaizationlist);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 
	 * 根据当前emailID获得该邮件的所有附件
	 * 
	 * @return
	 */
	public String getAttachOfEmail() {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			CAccount account = (CAccount) session.get("account");
			if (account == null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("nologin", 1);
				JSONObject obj = JSONObject.fromObject(map);
				result = obj.toString();
				return "success";
			}
			HttpServletRequest request = ServletActionContext.getRequest();
			String emailID = request.getParameter("emailID");
			String hql1 = "from UpLoadFile where parmeterID = ?";

			List<BaseBean> attachlist = baseBeanService
					.getListBeanByHqlAndParams(hql1, new Object[] { emailID });

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("attachlist", attachlist);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 用ajax修改状态
	 * 
	 * @return
	 */
	public String ChangeStatus() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "update Email set addresseeStatus = '12' where  companyID = ? and emailID = ?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
				new String[] { hql }, new Object[] { account.getCompanyID(),
						email.getEmailID() });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("st", "1");
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	// 草稿箱
	public String saveEmailDraft() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(Email.class);
		dc.add(Restrictions.eq("companyID", companyID));
		String hql = " from CAccount where companyID = ? order by accountName  ";
		List<BaseBean> accountList = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { account.getCompanyID() });
		Map<String, String> map = new HashMap<String, String>();
		for (BaseBean b : accountList) {
			CAccount acc = (CAccount) b;
			map.put(acc.getAccountID(), acc.getAccountName());
		}
		Email.setOMap(map);
		String hql1 = " from Email where addresserID = ? and addresserStatus = '02' order by emailDate Desc";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hql1, new Object[] { account.getAccountID() });
		return "draft";
	}

	// 发件箱
	public String getSendList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(Email.class);
		dc.add(Restrictions.eq("companyID", companyID));
		String hql = " from CAccount where companyID = ? order by accountName  ";
		List<BaseBean> accountList = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { account.getCompanyID() });
		Map<String, String> map = new HashMap<String, String>();
		for (BaseBean b : accountList) {
			CAccount acc = (CAccount) b;
			map.put(acc.getAccountID(), acc.getAccountName());
		}
		Email.setOMap(map);
		String hql1 = " from Email where addresserID = ? and addresserStatus = '01' order by emailDate Desc";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hql1, new Object[] { account.getAccountID() });
		return "send";
	}

	// 收件箱
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(Email.class);
		dc.add(Restrictions.eq("companyID", companyID));
		String hql = " from CAccount where companyID = ? order by accountName  ";
		List<BaseBean> accountList = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { account.getCompanyID() });
		Map<String, String> map = new HashMap<String, String>();
		for (BaseBean b : accountList) {
			CAccount acc = (CAccount) b;
			map.put(acc.getAccountID(), acc.getAccountName());
		}
		Email.setOMap(map);
		String hql1 = " from Email where addresseeID = ? and (addresseeStatus = '11' or addresseeStatus = '12') order by emailDate Desc";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hql1, new Object[] { account.getAccountID() });
		return "recive";
	}

	// 已删除
	public String getEmailIndex() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(Email.class);
		dc.add(Restrictions.eq("companyID", companyID));
		String hql = " from CAccount where companyID = ? order by accountName  ";
		List<BaseBean> accountList = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { account.getCompanyID() });
		Map<String, String> map = new HashMap<String, String>();
		for (BaseBean b : accountList) {
			CAccount acc = (CAccount) b;
			map.put(acc.getAccountID(), acc.getAccountName());
		}
		Email.setOMap(map);
		String hql1 = " from Email where ( addresserID = ? and addresserStatus = '03' ) or ( addresseeID = ? and  addresseeStatus = '03' ) order by emailDate Desc";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hql1, new Object[] { account.getAccountID(),
						account.getAccountID() });
		return "del";
	}

	// 进入个人邮箱
	public String getEmailList() {
		return "topage";
	}

	// 邮箱保存或改变状态

	public String addEmail() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		email.setAddresserID(account.getAccountID());
		HttpServletRequest request = ServletActionContext.getRequest();
		String hidIdList = request.getParameter("hidIdList");
		String emailID = null;
		if (status.equals("01")) {
			email.setAddresserStatus("01");// 设置发信状态为已发送
			email.setAddresseeStatus("11");// 设置收信状态为未读
		} else {
			email.setAddresserStatus("02");// 设置发信状态为草稿箱
			email.setAddresseeStatus("10");// 设置收信状态为未发送
		}
		email.setCompanyID(account.getCompanyID());
		email.setEmailDate(new Date());
		List<BaseBean> beans = null;
		if (reciveID != null && !"".equals(reciveID)) {

			String[] reciveIDs = reciveID.split(";");
			for (int i = 0; i < reciveIDs.length; i++) {
				beans = new ArrayList<BaseBean>();
				email.setAddresseeID(reciveIDs[i]);
				emailID = serverService.getServerID("email");
				email.setEmailID(emailID);
				beans.add(email);
				String[] filePathArray = hidIdList.split(",");
				if (hidIdList != null && hidIdList.trim().length() > 0
						&& filePathArray.length != 0) {
					UpLoadFile uf = null;
					for (int j = 0; j < filePathArray.length; j++) {
						uf = new UpLoadFile();
						uf.setFileID(serverService.getServerID("uploadfile"));
						uf.setParmeterID(emailID);
						String fileName = filePathArray[j]
								.substring(filePathArray[j].lastIndexOf("/") + 1);
						uf.setFilename(fileName);
						uf.setFilepath(filePathArray[j]);
						baseBeanService.save(uf);
					}
					uf = null;

				}
				parameter = "发送邮件(邮件标题:" + email.getTitle() + ")";
				CLogBook logbook = logBookService.saveCLogBook(null, parameter,
						account);
				beans.add(logbook);
				baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
						null, null);

			}
		}

		return "succ";
	}

	/**
	 * 
	 * 回复或者转发的时候获得原邮件的各项信息
	 * 
	 * 
	 */
	public String getEmaiInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String emailID = request.getParameter("emailID");
		zhType = request.getParameter("zhType");
		System.out.println(zhType);
		String hql = "";
		hql = "from Email where emailID = ?";
		email = (Email) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { emailID });

		hql = "from CAccount where accountID = ?";
		account = (CAccount) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { email.getAddresseeID() });

		return "succ";

	}

	public String delEmail() {
		String hql = "";
		if (listStatus.equalsIgnoreCase("delEmail")) {
			if (reciveID != null && !"".equals(reciveID)) {
				String[] reciveIDs = reciveID.split(";");
				for (int i = 0; i < reciveIDs.length; i++) {
					hql = "from Email where emailID = ?";
					Email email = (Email) baseBeanService
							.getBeanByHqlAndParams(hql,
									new Object[] { reciveIDs[i] });
					if (email.getAddresserStatus().equalsIgnoreCase("03")) {
						hql = "update Email set addresserStatus= ? where emailID = ?";// 发件人
						baseBeanService.saveBeansListAndexecuteHqlsByParams(
								null, new String[] { hql }, new Object[] {
										status, reciveIDs[i] });
					}
					if (email.getAddresseeStatus().equalsIgnoreCase("03")) {
						hql = "update Email set addresseeStatus= ? where emailID = ?";// 发件人
						baseBeanService.saveBeansListAndexecuteHqlsByParams(
								null, new String[] { hql }, new Object[] {
										status, reciveIDs[i] });
					}
				}
			}

			return getEmailIndex();

		}

		if (emailStatus.equals("addresseeStatus")) {
			hql = "update Email set addresseeStatus= ? where emailID = ?";// 收件人
			if (reciveID != null && !"".equals(reciveID)) {
				String[] reciveIDs = reciveID.split(";");
				for (int i = 0; i < reciveIDs.length; i++) {
					baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
							new String[] { hql }, new Object[] { status,
									reciveIDs[i] });
				}
			}
			// return toSearch();
		} else if (emailStatus.equals("addresserStatus")) {
			hql = "update Email set addresserStatus= ? where emailID = ?";// 发件人
			if (reciveID != null && !"".equals(reciveID)) {
				String[] reciveIDs = reciveID.split(";");
				for (int i = 0; i < reciveIDs.length; i++) {
					baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
							new String[] { hql }, new Object[] { status,
									reciveIDs[i] });
				}
			}

		}
		// return getSendList();

		if (listStatus.equalsIgnoreCase("reciveEmail")) {
			return toSearch();
		}
		if (listStatus.equalsIgnoreCase("draftEmail")) {
			return saveEmailDraft();
		}
		if (listStatus.equalsIgnoreCase("sendEmail")) {
			return getSendList();
		}

		return "";

	}

	//
	public String getJchart() {

		chart = ChartFactory.createBarChart3D("个人邮箱中心", "邮箱信息", "信封：件",
				getData(), PlotOrientation.VERTICAL, true, false, false);
		statisticaBean.resetPiePlotByzz(chart);
		return "success";
	}

	/**
	 * 
	 * 取得邮箱信息
	 * 
	 * @return
	 */
	public CategoryDataset getData() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DefaultCategoryDataset defaultData = new DefaultCategoryDataset();
		String hql1 = "select count(*) from Email where companyID= ? and addresserID = ? and addresserStatus= '01'";// 发件箱
		String hql2 = "select count(*) from Email where companyID= ? and addresserID = ? and addresserStatus= '02'";// 草稿箱
		String hql3 = "select count(*) from Email where companyID= ? and addresseeID = ? and addresseeStatus = '12'";// 已读
		String hql4 = "select count(*) from Email where companyID= ? and addresseeID = ? and addresseeStatus = '11'";// 未读
		String hql5 = "select count(*) from Email where companyID= ? and ( addresserID = ? and addresserStatus = '03' ) or ( addresseeID = ? and  addresseeStatus = '03' )";// 已删除
		Object[] parmas = { account.getCompanyID(), account.getAccountID() };
		int re = baseBeanService.getConutByByHqlAndParams(hql1, parmas);
		int draft = baseBeanService.getConutByByHqlAndParams(hql2, parmas);
		int hasRead = baseBeanService.getConutByByHqlAndParams(hql3, parmas);
		int noRead = baseBeanService.getConutByByHqlAndParams(hql4, parmas);
		int del = baseBeanService.getConutByByHqlAndParams(hql5, new Object[] {
				account.getCompanyID(), account.getAccountID(),
				account.getAccountID() });
		defaultData.setValue(re, "已发送" + re + "封", "已发送");
		defaultData.setValue(draft, "草稿" + draft + "封", "草稿箱");
		defaultData.setValue(hasRead, "已读" + hasRead + "封", "已读");
		defaultData.setValue(noRead, "未读" + noRead + "封", "未读");
		defaultData.setValue(del, "已删除" + del + "封", "已删除");
		return defaultData;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getReciveID() {
		return reciveID;
	}

	public void setReciveID(String reciveID) {
		this.reciveID = reciveID;
	}

	public String getListStatus() {
		return listStatus;
	}

	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}

	public CAccount getAccount() {
		return account;
	}

	public void setAccount(CAccount account) {
		this.account = account;
	}

	public String getZhType() {
		return zhType;
	}

	public void setZhType(String zhType) {
		this.zhType = zhType;
	}

}
