package hy.ea.office.action;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.DocDelRecord;
import hy.ea.bo.office.DocGsendInfo;
import hy.ea.bo.office.Document;
import hy.ea.bo.office.DocumentPass;
import hy.ea.bo.office.DocumentTrack;
import hy.ea.bo.office.Document.DocumentSearchInfo;
import hy.ea.office.service.DocCommonService;
import hy.ea.util.MobileMessage;
import hy.ea.util.RandomDatas;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
/**
 * 公文流转管理
 */
public class DocumentCommonAction  extends ActionSupport{
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private DocCommonService docCommonService;
	@Autowired
	private MobileMessage mobileMessage;//短信接口 
	private DocumentSearchInfo documentSearchInfo;
	private ContactCompany contactCompany;
	private Document document;
	private PageForm pageForm;
	private String result;
	private String search;
	private String type1;
	private int pageNumber;
	private String module;
	private Staff searchCStaff;
	private String searchType;
	private RandomDatas randomdatas;

	// 以下正式的
	private String type;
	private String docId;
	private String stage;
	private String titleType;	/**
	 *
	 * 放入回收站
	 *
	 * @return
	 */
	public String putRecycleBin() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		String hql = "from Document where docId = ?";
		String[] docIdarray = docIds.split(",");
		Document doc = null;
		for (int i = 0; i < docIdarray.length; i++) {


			doc = (Document) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { docIdarray[i] });
			String coyhql = "from Company where companyID = ?";
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(
					coyhql, new Object[] { doc.getCompanyID() });
			DocDelRecord ddr = new DocDelRecord();
			ddr.setDelId(serverService.getServerID("delId"));
			ddr.setDocId(docIdarray[i]);
			ddr.setStage(stage);
			ddr.setDelstate("del");
			ddr.setOperator(account.getStaffID());
			ddr.setOperateTime(new Date());
			ddr.setDeptOperate((String) session.get("organizationID"));
			if (company != null) {
				ddr.setOrgmark(company.getCompanyIdentifier());
			}

			baseBeanService.save(ddr);

			if (stage.equals("read")) {
				hql = "from DocGsendInfo where docId = ? and readerID = ? and organizationID = ?";
				DocGsendInfo dgi = (DocGsendInfo) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { docIdarray[i],
								account.getStaffID(),
								(String) session.get("organizationID") });
				dgi.setDelstate("del");
				baseBeanService.update(dgi);
			}
			// 增加操作记录
			docCommonService.addTrackRecord(docIdarray[i], account.getStaffID(),
					(String) session.get("organizationID"), account
							.getCompanyID(), "放入回收站");
		}

		return "success";
	}
	private String docIds;
	
	private File files; //上传的文件
	private String fileName; //文件名称
	private String contentType; //文件类型
	

	/** ***********************************放入回收站功能开始*************************************** */



	/**
	 * 
	 * 回收站查询功能
	 * 
	 * @return
	 */

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", documentSearchInfo);
		return getRecycelBinList();

	}

	public String searchRecycle() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		documentSearchInfo = (DocumentSearchInfo) session.get("tablesearch");
		String sql = "select d.docId,d.docNum,d.title,d.theme,r.operateTime,r.delId,r.key,r.stage,r.orgmark from DT_OA_DOCUMENT d  left join DT_OA_DOC_DELRECORD r on d.docId = r.docId where d.module = ?  and r.operator = ? and r.deptOperate = ? and d.docNum like ? and d.title like ? and  r.stage like ?";
		String sqlcount = "select count(*) from DT_OA_DOCUMENT d left join DT_OA_DOC_DELRECORD r on d.docId = r.docId where d.module = ? and r.operator = ? and r.deptOperate = ? and d.docNum like ? and d.title like ? and r.stage like ?";
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, sqlcount,
				new Object[] { (String) session.get("module"),
						account.getStaffID(),
						(String) session.get("organizationID"),
						"%" + documentSearchInfo.getDocNum().trim() + "%",
						"%" + documentSearchInfo.getTitle().trim() + "%",
						"%" + documentSearchInfo.getDelposition() + "%" });

		return "recyleBin";

	}

	/**
	 * 
	 * 获得回收站的列表按部门，按模块
	 * 
	 * @return
	 */
	public String getRecycelBinList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}

		String sql = "select d.docId,d.docNum,d.title,d.theme,r.operateTime,r.delId,r.key,r.stage,r.orgmark from DT_OA_DOCUMENT d  left join DT_OA_DOC_DELRECORD r on d.docId = r.docId where d.module = ?  and r.operator = ? and r.deptOperate = ? order by r.operateTime desc";
		String sqlcount = "select count(*) from DT_OA_DOCUMENT d left join DT_OA_DOC_DELRECORD r on d.docId = r.docId where d.module = ? and r.operator = ? and r.deptOperate = ?";
		if (search != null && search.equals("search")) {
			searchRecycle();
		} else {

			pageForm = baseBeanService.getPageFormBySQL(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 10 : pageNumber), sql, sqlcount,
					new Object[] { (String) session.get("module"),
							account.getStaffID(),
							(String) session.get("organizationID") });
		}

		return "recyleBin";
	}

	
	/**
	 * 
	 * 手机选择公文文件上传至服务器
	 * 
	 * @return
	 */
	
	 public String uploadFiles() throws Exception {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String companyId = account.getCompanyID();
	        String realpath = ServletActionContext.getServletContext().getRealPath("/document/"+ companyId);
	       // System.out.println("realpath: "+realpath);
	       String filename  =  randomdatas.getRandomString(10);
	        if (files != null) {
	        	String fileType = contentType.substring(contentType.lastIndexOf("."));
	           File savefile = new File(new File(realpath), filename + fileType);
	            if (!savefile.getParentFile().exists())
	                savefile.getParentFile().mkdirs();
	            FileUtils.copyFile(files, savefile);
	            ActionContext.getContext().put("message", "文件上传成功");
	        }
	        return "success";
	    } 
	
	/**
	 * 
	 * 回收站还原
	 * 
	 * @return
	 */
	public String restoreDoc() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String delkey = request.getParameter("delkey");

			DocDelRecord ddr = (DocDelRecord) baseBeanService.getBeanByKey(
					DocDelRecord.class, delkey);
			if (ddr.getStage().equals("read")) {
				String hql = "from DocGsendInfo where docId = ? and readerID = ? and organizationID = ?";
				DocGsendInfo dgi = (DocGsendInfo) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] {
								ddr.getDocId(), ddr.getOperator(),
								ddr.getDeptOperate() });
				dgi.setDelstate(null);
				baseBeanService.update(dgi);
			}

			baseBeanService.deleteBeanByKey(DocDelRecord.class, delkey);

			// 增加操作记录
			docCommonService.addTrackRecord(docId, account.getStaffID(),
					(String) session.get("organizationID"), account
							.getCompanyID(), "从回收站中还原");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/** ***********************************放入回收站功能结束*************************************** */

	/** ***********************************公文转移位置功能开始*************************************** */

	/*
	 * 
	 * 转移公文部门和模块位置
	 */
	@SuppressWarnings("unused")
	public String changePosition() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		
		
		String hql = "from Document where docId = ?";
		String hqlpass = "from DocumentPass where docId = ? and toSubscriber2 = ? order by passTime desc";
		String hqlgsend = "from DocGsendInfo where docId = ? and readerID = ?";
		List<BaseBean> dpbean = null;
		DocumentPass dp = null;
		DocGsendInfo ds = null;
		Document doc = null;
		String[] docIds = null;
		int length = 1;
		if (document != null && !document.getDocId().equals("")) {
			docIds = document.getDocId().split(",");
			length = docIds.length;
		}
		for (int i = 0; i < docIds.length; i++) {
			
		
		 doc = (Document) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { docIds[i].trim()});
		if (type1.equals("draft")) {
			doc.setOrganizationID(document.getOrganizationID());
		}
		if (type1.equals("receiver")) {
			if (doc.getStatus().equals("I")) {
				dpbean = baseBeanService.getListBeanByHqlAndParams(hqlpass,
						new Object[] {docIds[i].trim(),
								account.getStaffID() });
				if (dpbean.size() != 0) {
					dp = (DocumentPass) dpbean.get(0);
					dp.setDeptOftoSub2(document.getOrganizationID());
					baseBeanService.update(dp);
				}

				doc.setDeptIDofSubscriber2(document.getOrganizationID());
			} else {
				doc.setOrganizationID(document.getOrganizationID());
			}

		}
		if (type1.equals("approveno")) {

			doc.setDeptIDofSubscriber(document.getOrganizationID());
		}
		if (type1.equals("seal")) {
			doc.setDeptIDofSealer(document.getOrganizationID());
		}
		if (type1.equals("publish")) {
			doc.setDeptIDofPublisher(document.getOrganizationID());
		}
		if (type1.equals("read")) {
			ds = (DocGsendInfo) baseBeanService.getBeanByHqlAndParams(hqlgsend,
					new Object[] {docIds[i].trim(), account.getStaffID() });
			ds.setOrganizationID(document.getOrganizationID());
			baseBeanService.update(ds);
		}
		doc.setModule(document.getModule());
		baseBeanService.update(doc);

		// 增加操作记录
		docCommonService.addTrackRecord(docIds[i].trim(), account.getStaffID(),
				(String) session.get("organizationID"), account.getCompanyID(),
				"转移位置");
		String content = "您进行了公文位置转移操作具体内容：将";

		docCommonService.sendPhoneMessage(account.getStaffID(), document
				.getOrganizationID(),account.getCompanyID(), account.getStaffID(),account.getCompanyID(), content, doc
				.getTitle(), (String) session.get("module"), "position");
		}

		return "success";
	}

	/** ***********************************公文转移位置功能结束*************************************** */

	/** ***********************************按要求获取公司、部门、人员相关功能开始*************************************** */

	/**
	 * 根据当前公司获取该公司和该公司一个集团下所有公司
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

		String hql = " from Company where groupCompanySn = ? and companyStatus = ?";
		List<BaseBean> companylist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { groupCompanySn, "00" });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companylist", companylist);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	
	/**
	 * 
	 * 根据部门查人(按cos和deparetmentPost两个表查)
	 * 
	 * @return
	 */
	public String getPersonByDept() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String checkOrgID = request.getParameter("checkOrgID");
		String currentCompanyID = request.getParameter("currentCompanyID");
		String hql = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and (c.organizationID = ? "
				+ "or exists(select d.depPostID from DepartmentPost d where d.depPostID = c.depPostID and d.leveloneOrgID = ?)))";

		Object[] params = new Object[] { currentCompanyID, "50", checkOrgID,
				checkOrgID };
		List<BaseBean> stafflist = baseBeanService.getListBeanByHqlAndParams(
				hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stafflist", stafflist);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";

	}

	/**
	 * 
	 * 根据部门查人(按cos和deparetmentPost两个表查)
	 * 
	 * @return
	 */
	public String getPersonByDept2() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		if(search!=null&&search.equals("search")){
		    String staffName = request.getParameter("querytxt");
		    searchMember(account.getCompanyID(),staffName);
		    return "success";
		}

		String checkOrgID = request.getParameter("checkOrgID");
		String currentCompanyID = request.getParameter("currentCompanyID");
		String hqlorg = "from COrganization where organizationID = ?";
		String hqlcom = "from Company where companyID = ?";
		COrganization org = (COrganization) baseBeanService.getBeanByHqlAndParams(hqlorg,new Object[]{checkOrgID});
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(hqlcom,new Object[]{currentCompanyID});
//		String hql = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and (c.organizationID = ? "
//				+ "or exists(select d.depPostID from DepartmentPost d where d.depPostID = c.depPostID and d.leveloneOrgID = ?)))";

		String sql = "select s.staffID,s.staffCode,s.staffName,s.sex,s.staffIdentityCard,s.reference from dt_hr_Staff s where exists (select staffID from dtcos c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and (c.organizationID = ? "
			+ "or exists(select d.depPostID from dt_hr_deptpost d where d.depPostID = c.depPostID and d.leveloneOrgID = ?)))";
        String sqlcount = "select count(*) from dt_hr_Staff s where exists (select staffID from dtcos c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and (c.organizationID = ? "
			+ "or exists(select d.depPostID from dt_hr_deptpost d where d.depPostID = c.depPostID and d.leveloneOrgID = ?)))";
		Object[] params = new Object[] { currentCompanyID, "50", checkOrgID,
				checkOrgID };
//		List<BaseBean> stafflist = baseBeanService.getListBeanByHqlAndParams(
//				hql, params);
//		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
//				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), hql, params);
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql,sqlcount, params);
		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("stafflist", stafflist);//改了暂时不用
		map.put("pageForm", pageForm);
		map.put("companyName", company.getCompanyName());
		map.put("orgName", org.getOrganizationName());
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";

	}

	
	public void searchMember(String companyID, String querytxt) {

		try {

			String sql = "select s.staffID,s.staffCode,s.staffName,s.sex,s.staffIdentityCard,s.reference,o.organizationName,g.companyName,c.organizationID,c.companyId from dtcos c"
					+ " join dtcorganization o on c.organizationID = o.organizationID"
					+ " join dt_hr_staff s on c.staffID = s.staffID join dtCompany g on c.companyId = g.companyID"
					+ " where  c.cosStatus = '50' and s.staffName like ?";		
			String sqlcount = "select count(*) from dtcos c"
				+ " join dtcorganization o on c.organizationID = o.organizationID"
				+ " join dt_hr_staff s on c.staffID = s.staffID join dtCompany g on c.companyId = g.companyID"
				+ " where  c.cosStatus = '50' and s.staffName like ?";
			
			pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					sql, sqlcount,new Object[] { "%" + querytxt + "%" });

			Map<String, Object> map = new HashMap<String, Object>();
             map.put("pageForm", pageForm);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 根据选中的公司获得该公司下得所有部门
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
		if (companyID == null || companyID.equals("")) {
			companyID = account.getCompanyID();
		}
		String hql1 = "from COrganization where companyID = ? and organizationPID = ? and Status ='00' order by organizationNumber";
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
	 * 
	 * 获得人力资源列表
	 * 
	 * @return
	 */
	public String getSocialInfoList() {

		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
				getList());
		return "toSocialInfo";
	}

	/**
	 * 
	 * 人力资源查询功能
	 * 
	 * @return
	 */
	public String toSearchForSocial() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", searchCStaff);
		return getSocialInfoList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from Staff where staffID = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { account.getStaffID() });
		String groupCompanySn = staff.getGroupCompanySn();

		DetachedCriteria dc = DetachedCriteria.forClass(Staff.class);
		dc.add(Restrictions.eq("groupCompanySn", groupCompanySn));
		dc.add(Restrictions.eq("staffStatus", "00"));
		if (search != null && search.equals("search")) {
			searchCStaff = (Staff) session.get("tablesearch");
			if (searchCStaff.getStaffCode() != null
					&& !"".equals(searchCStaff.getStaffCode())) {
				dc.add(Restrictions
						.eq("staffCode", searchCStaff.getStaffCode()));
			}
			if (searchCStaff.getStaffName() != null
					&& !searchCStaff.getStaffName().equals("")) {
				dc.add(Restrictions.like("staffName", searchCStaff
						.getStaffName(), MatchMode.ANYWHERE));
			}
			if (searchCStaff.getStaffIdentityCard() != null
					&& !searchCStaff.getStaffIdentityCard().equals("")) {
				dc.add(Restrictions.like("staffIdentityCard", searchCStaff
						.getStaffIdentityCard(), MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	
	
	
	public String toSearchCCompany(){
		Map<String,Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch",contactCompany);
		return getListContactCompany();
		
	}
	
	/**
	 * 
	 * 往来单位
	 * 
	 * @return
	 * 
	 */
	public String getListContactCompany() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			return "nologin";
		}
		pageForm = baseBeanService.getPageFormByDC(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 5 : pageNumber), getListBYDC());
		return "connectC";
	}
	
	public DetachedCriteria getListBYDC() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc = DetachedCriteria.forClass(ContactCompany.class);
		if (search != null && search.equals("search")) {
			this.contactCompany = (ContactCompany) session.get("tablesearch");
			if (null != contactCompany.getCompanyName()
					&& !"".equals(contactCompany.getCompanyName())) {
				dc.add(Restrictions.like("companyName", contactCompany
						.getCompanyName(), MatchMode.ANYWHERE));
			}

		}
		dc.addOrder(Order.desc("ccompanyID"));
		return dc;
	}

	// //////////////////////////////////////////////////////////////////

	/**
	 * 
	 * 
	 * 获得正式员工列表
	 * 
	 * @return
	 */
	public String toSearchCStaff() {
		ActionContext.getContext().getSession()
				.put("tablesearch", searchCStaff);
		return getStaffformalList();
	}

	public String getStaffformalList() {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();

			CAccount account = (CAccount) session.get("account");
			if (search != null && search.equals("search")) {
				searchStaff(account.getCompanyID());
				return "formallist";
			}

			String hql = "from Staff s where staffID in (select staffID from COS c where c.staffID=s.staffID and cosStatus = ? and companyID = ?) ";
			Object[] params = { "50", account.getCompanyID() };
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
					hql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "formallist";
	}

	/**
	 * 按条件搜索人员输出列表 被调用
	 * 
	 * @param companyID
	 * @return
	 */
	private void searchStaff(String companyID) {
		searchCStaff = (Staff) ActionContext.getContext().getSession().get(
				"tablesearch");
		String hql1 = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and cosStatus = ? and companyID = ?)  and ";
		String hql2 = " staffCode like ? and staffName like ? and staffIdentityCard like ?  order by staffID desc ";
		String hql = "";
		hql = hql1 + hql2;
		try {
			Object[] params = { "50", companyID,
					"%" + searchCStaff.getStaffCode() + "%",
					"%" + searchCStaff.getStaffName() + "%",
					"%" + searchCStaff.getStaffIdentityCard() + "%" };
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
					hql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** ***********************************按要求获取公司、部门、人员相关功能结束*************************************** */

	/** ***********************************进入不同的公文模块功能开始*************************************** */

	/*
	 * 
	 * 显示公文不同模块
	 */
	public String showDocumentModule() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		SessionWrap sw = SessionWrap.getInstance();
		session.remove("module");
		session.put("module", module);

		HttpSession session1 = ServletActionContext.getRequest().getSession();
		if(module==null||module.equals("")){

			TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
					SessionWrap.KEY_SHOPCUSCOM);
			if(tc!=null){
           //把公司ID和staffID存在session中方便后续功能获取
				CAccount account = new CAccount();
				account.setCompanyID("");
				account.setStaffID(tc.getStaffid());
				session1.setAttribute("account",account);

			}


		}
		String bb = request.getParameter("bb");
		if("new".equals(bb)){
			return "newFrame";
		}
		return "toFrame";
	}

	/** ***********************************进入不同的公文模块功能结束*************************************** */

	/** ***********************************获取公文操作记录功能开始*************************************** */

	/**
	 * 
	 * 获得公文的跟踪记录列表
	 */
	public String getDocTrackRecord() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}

		String hql = "from DocumentTrack where docId = ? order by operaterTime";
		List<BaseBean> tracklist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { docId });
		hql = "from Staff where staffID = ?";
		List<BaseBean> stafflist = new ArrayList<BaseBean>();
		Staff staff = null;
		for (BaseBean b : tracklist) {
			DocumentTrack track = (DocumentTrack) b;
			staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { track.getOperatorID() });
			stafflist.add(staff);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tracklist", tracklist);
		map.put("stafflist", stafflist);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";
	}

	/** ***********************************获取公文操作记录功能结束*************************************** */

	/** ***********************************获取不同阶段查询里面的title功能开始*************************************** */

	/**
	 * 
	 * 获取不同阶段的title的查询
	 */
	public String getTitleByStage() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			return "not_login";
		}
		String organizationID = (String) session.get("organizationID");
		String module = (String) session.get("module");
		String staffID = account.getStaffID();
		String hql = "";
		List<BaseBean> titlelist = null;
		// 1.草稿箱的title
		if (titleType.equals("draft")) {
			hql = " select distinct d.title from Document d where d.drafterID=? and d.status = ? and  d.subscriberID2 is null and d.module = ? and d.organizationID = ? and d.docId not in(select docId from DocDelRecord)";
			titlelist = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { staffID, "I", module, organizationID });
		}
		// 2.收件箱的title
		if (titleType.equals("receiver")) {
			hql = "select distinct d.title from Document d where ((d.subscriberID2 = ? and d.deptIDofSubscriber2 = ?)or (d.status = ? and d.drafterID = ? and organizationID = ?)) and d.module = ? and d.docId not in(select docId from DocDelRecord)";
			titlelist = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { staffID, organizationID, "R",
							account.getStaffID(), organizationID, module });
		}

		// 3.已发送的title
		if (titleType.equals("yessend")) {
			hql = "select distinct d.title from Document d where ((d.drafterID=? and d.status!='I' and d.status!='D' and d.organizationID = ?) or (d.docId in (select p.docId from DocumentPass p where p.subscriber2 = ? and p.deptOfsub2 = ?) )) and d.docId not in(select docId from DocDelRecord) and d.module = ?";
			titlelist = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { staffID, organizationID,
							account.getStaffID(), organizationID, module });
		}

		// 4.已归档
		if (titleType.equals("archive")) {
			hql = "select distinct d.title from Document d where d.companyID=? and d.status= ? and d.module = ? and d.docId not in(select docId from DocDelRecord)";
			titlelist = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { account.getCompanyID(), "G", module });
		}

		// 5.共享词
		if (titleType.equals("share")) {
			String hqlcom = "from Company where companyID = ?";
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(
					hqlcom, new Object[] { account.getCompanyID() });
			hql = "select distinct d.title from Document d where d.module = ? and d.docId not in(select docId from DocDelRecord where stage = ? and operator = ?) and docId in(select s.docId from DocumentShare s where (s.companyId = ? and s.shareType = ?) or (s.groupCompanySn = ? and s.shareType = ?))";

			titlelist = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { (String) session.get("module"), titleType,
							account.getStaffID(), account.getCompanyID(),
							"current", company.getGroupCompanySn(), "group" });
		}

		// 6.回收站
		if (titleType.equals("recycle")) {
			hql = "select distinct d.title from Document d where d.docId in(select r.docId from DocDelRecord r where r.operator = ? and r.deptOperate = ?) and d.module = ?";

			titlelist = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { staffID, organizationID, module });

		}

		// 7.未审批，未盖章，未分发，未阅读
		if (titleType.equals("examine")) {
			hql = "select distinct title from ViewUnexaminedoc where assignee = ? and module = ? and activityname = ? and deptidofsubscriber = ?";
			titlelist = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { account.getStaffID(),
							(String) session.get("module"),
							"Examine and Approve",
							(String) session.get("organizationID") });
		}

		if (titleType.equals("seal")) {
			hql = "select distinct title from ViewUnsealdoc where assignee = ? and module = ? and activityname = ? and deptidofsealer = ?";
			titlelist = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { account.getStaffID(),
							(String) session.get("module"), "Seal",
							(String) session.get("organizationID") });
		}

		if (titleType.equals("publish")) {
			hql = "select distinct title from ViewUnpublishdoc where assignee = ? and module = ? and activityname = ? and deptidofpublisher = ?";
			titlelist = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { account.getStaffID(),
							(String) session.get("module"), "Publish",
							(String) session.get("organizationID") });
		}
		if (titleType.equals("read")) {
			hql = "select distinct title from ViewUnreaddoc where readerid = ? and readstate = ? and module = ?  and readorganizationid = ?";
			titlelist = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { account.getStaffID(), "1",
							(String) session.get("module"),
							(String) session.get("organizationID") });
		}

		if (titleType.equals("yesexamine")) {
			hql = "select distinct title from ViewExaminedoc where assignee = ? and module = ?  and deptidofsubscriber = ?";
			titlelist = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { account.getStaffID(),
							(String) session.get("module"),
							(String) session.get("organizationID") });
		}
		if (titleType.equals("yesseal")) {
			hql = "select distinct title from ViewSealdoc where assignee = ? and module = ?  and deptidofsealer = ?";
			titlelist = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { account.getStaffID(),
							(String) session.get("module"),
							(String) session.get("organizationID") });

		}
		if (titleType.equals("yespublish")) {
			hql = "select distinct title from ViewPublishdoc where assignee = ? and module = ?  and deptidofpublisher = ?";
			titlelist = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { account.getStaffID(),
							(String) session.get("module"),
							(String) session.get("organizationID") });

		}
		if (titleType.equals("yesread")) {
			hql = "select distinct title from ViewUnreaddoc where readerid = ? and readstate = ? and module = ?  and readorganizationid = ?";
			titlelist = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { account.getStaffID(), "0",
							(String) session.get("module"),
							(String) session.get("organizationID") });
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("titlelist", titlelist);

		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/** ***********************************获取不同阶段查询里面的title功能结束*************************************** */

	
	
	// 将word打包成Zip返回zip路径
	public String LoadOfZip() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			return "not_login";
		}
		String realpath = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		String zipPath = docCommonService.packZipOfOffice(realpath, document
				.getDocId());// 这里是docIDs
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", zipPath);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	
	
	
	
	/**
	 * 
	 * 
	 * 获取文件审批责任人跟踪表信息
	 * @return
	 */
    public String getfilePersonPrint(){
    	//草稿箱
    	String hql = "from Document where docId = ?";
    	document = (Document) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{docId});
        document = docCommonService.getViewFullDoc(document);
    	return  "persontrack";
    	
    	
    }

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public DocumentSearchInfo getDocumentSearchInfo() {
		return documentSearchInfo;
	}

	public void setDocumentSearchInfo(DocumentSearchInfo documentSearchInfo) {
		this.documentSearchInfo = documentSearchInfo;
	}

	public Staff getSearchCStaff() {
		return searchCStaff;
	}

	public void setSearchCStaff(Staff searchCStaff) {
		this.searchCStaff = searchCStaff;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getTitleType() {
		return titleType;
	}

	public void setTitleType(String titleType) {
		this.titleType = titleType;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getDocIds() {
		return docIds;
	}

	public void setDocIds(String docIds) {
		this.docIds = docIds;
	}

	public ContactCompany getContactCompany() {
		return contactCompany;
	}

	public void setContactCompany(ContactCompany contactCompany) {
		this.contactCompany = contactCompany;
	}
	
	
	/**
	 * 
	 * 每天午夜执行一次 短信通知合同是否到期
	 * @return
	 */
	public void  noticeClient(){
	  String hql = "from Document where module='contract' and  endValidity is not null and tipStatus is null";
	  List<BaseBean> doclist = baseBeanService.getListBeanByHqlAndParams(hql,null);
	  Date curtime = new Date();
	  String hqlstaff = "from Staff where staffID = ?";
	  Staff staff = null;
	  String reStr="";
	  if(doclist.size()!=0){
		  
	try{
	  for(BaseBean b:doclist){
		  Document doc = (Document) b;
		  if(curtime.after(doc.getEndValidity())){
			 staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff,new Object[]{doc.getDrafterID()});
			if(staff.getReference()!=null&&!staff.getReference().equals("")){	
			     mobileMessage.setMessage("您好，您起草的合同["+doc.getTitle()+"]已过期，请做出相应处理！");
				 mobileMessage.setMobiles(staff.getReference());
			 
				  reStr = mobileMessage.sendMsg();
				  System.out.println(reStr);
				  doc.setTipStatus("00");
				
			}
			
			  
		  }
		  
	  }

	     baseBeanService.saveBeansListAndexecuteHqlsByParams(doclist,null,null);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }
		
	}
	
	
	/**
	 * 
	 * 平台提示合同是否到期
	 * @return
	 */
	public String noticePlat(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			return "not_login";
		}
	
		String hql = "from Document where drafterID = ? and module='contract' and  endValidity is not null and tipStatus is null";
		
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getStaffID()});
        StringBuffer sb=new StringBuffer();
  	    Date curtime = new Date();
        for(BaseBean b:list){
        	Document  doc = (Document) b;
        	if(curtime.after(doc.getEndValidity())){
        		sb.append(doc.getTitle());
            	sb.append(",");
        	}
        	
        }
        
		Map<String,Object> map = new HashMap<String,Object>();
		String titles="";
		if(sb.length()>150){
			titles = sb.toString().substring(0,150)+"。。。。。。";
		}
		map.put("result", titles);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		
		return "success";
		
	}

	/**
	 * 搜索公司
	 *
	 * @return
	 */
	public  String  getAllCompany(){

		List<BaseBean> list = docCommonService.getAllCompany(document.getCompanyName());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companylist",list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";

	}

	/**
	 * 搜索人员
	 *
	 * @return
	 */
	public  String  getAllPeople(){
		Map<String,Object> map = docCommonService.getAllPeople(document.getDrafterName(),document.getCompanyID());
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";

	}

	public File getFiles() {
		return files;
	}

	public void setFiles(File files) {
		this.files = files;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public RandomDatas getRandomdatas() {
		return randomdatas;
	}

	public void setRandomdatas(RandomDatas randomdatas) {
		this.randomdatas = randomdatas;
	}
	
	
	

}
