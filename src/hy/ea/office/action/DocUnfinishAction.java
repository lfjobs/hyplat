package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.DocPosition;
import hy.ea.bo.office.Document;
import hy.ea.bo.office.DocumentFileAttach;
import hy.ea.bo.office.Document.DocumentSearchInfo;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("deprecation")
@Controller
@Scope("prototype")
/**
 * 公文查询
 */
public class DocUnfinishAction {
	@Resource
	private BaseBeanService baseBeanService;
	private DocPosition docPosition;
	private PageForm pageForm;
	private String result;
	private String search;
	private int pageNumber;
	private String type;
	private DocumentSearchInfo docSearchInfo;

	public String showDocUnfinishModule() {

		return "toFrameu";
	}

	/**
	 * 
	 * 查询公文
	 * 
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", docSearchInfo);
		
		
		return getDocumentPosition();
	}
  
	
	
	/**
	 * 
	 * 
	 * 特办公文查询
	 * @return
	 */
	public String getDocumentPosition() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		if(type!=null&&!type.equals("")){
			
			String hql = "from Document d where d.companyID = ? and  exists(select a.fileType from DocumentFileAttach a where a.document = d.key and a.fileType = ?)";
			List<String> params =  new ArrayList<String>();
			params.add(account.getCompanyID());
			params.add(type);
			docSearchInfo = (DocumentSearchInfo) session.get("tablesearch");
			if(search!=null&&search.equals("search")){
				hql+= " and d.title like ?";
				params.add("%"+docSearchInfo.getTitle().trim()+"%");
			}
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql,params.toArray());
		
		}else{
		
		
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList());
		}
		
        if(pageForm!=null){
        	pageForm = this.getFullData(pageForm);
        }

		return "tounFinish";
	}

	

	public DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		docSearchInfo = (DocumentSearchInfo) session.get("tablesearch");

		String hql1 = "from Company where companyID = ?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(hql1,
				new Object[] { account.getCompanyID() });
		String sql = "select companyID from dtCompany where groupCompanySn = ?";
		List<String> list = baseBeanService.getListBeanBySqlAndParams(sql,
				new Object[] { company.getGroupCompanySn() });
		DetachedCriteria dc = DetachedCriteria.forClass(Document.class);

		dc.add(Restrictions.in("companyID", list));
		dc.add(Restrictions.ne("status", "D"));

		if (search != null) {
			if (docSearchInfo.getDocNum() != null
					&& !docSearchInfo.getDocNum().equals("")) {
				dc.add(Restrictions.like("docNum", docSearchInfo.getDocNum()
						.trim(),MatchMode.ANYWHERE));
			}
			if (docSearchInfo.getTitle() != null
					&& !docSearchInfo.getTitle().equals("")) {
				dc.add(Restrictions.like("title", docSearchInfo.getTitle()
						.trim(),MatchMode.ANYWHERE));
			}
			
		    
		}
		return dc;
	}
	
	/**
	 * 
	 * 获取完整数据
	 * @return
	 */
	public PageForm getFullData(PageForm pageForm){
		Document doc = null;
		DocPosition docPosition = null;
		String hqlcom = "from Company where companyID = ?";
		String hqlorg = "from COrganization where organizationID = ?";
		String hqlstaff = "from Staff where staffID = ?";
		String[] arraymodule = { "doc", "contract", "cg", "dg", "pg", "jg",
				"finace", "complaint", "InduReg", "regime", "AnnNoti", "news",
				"InterDis", "ExterDis", "bulletin", "CountReg", "person" };
		String[] arrayname = { "公文流转单", "企业合同管理", "公司规划管理", "部门规划管理", "个人规划管理",
				"职业规划管理", "财务Excel报表", "投诉处理", "行业法规管理", "制度管理", "公告通知管理", "新闻管理",
				"内部纠纷", "外部纠纷", "简报管理", "国家法规管理", "人事报表传输" };
		List<BaseBean> list = new ArrayList<BaseBean>();

		
		String hqlfile = "from DocumentFileAttach where document = ?";
		
		
		for (BaseBean b : pageForm.getList()) {
			DocumentFileAttach attach = null;
			Staff staff = null;
			Company company = null;
			COrganization org = null;
			String staffID = "";
			String organizationID = "";
			String companyID = "";
			docPosition = new DocPosition();
			doc = (Document) b;
			String status = doc.getStatus();
			docPosition.setDocNum(doc.getDocNum());
			docPosition.setTitle(doc.getTitle());
			docPosition.setDocId(doc.getDocId());
			docPosition.setStatus(doc.getStatus());
			for (int i = 0; i < arraymodule.length; i++) {
				if (arraymodule[i].equals(doc.getModule())) {
					docPosition.setModule(arrayname[i]);
					break;
				}
			}
			
			
			//获取每个的文件路径
			
			List<BaseBean>  attachlist = baseBeanService.getListBeanByHqlAndParams(hqlfile,new Object[]{doc});
			if(attachlist.size()!=0){
			attach = (DocumentFileAttach) attachlist.get(0);
			}
			if(attach!=null){
			String filePath = attach.getFilePath();
			if(filePath!=null&&!filePath.equals("")){
				filePath = filePath.replace("\\", "/");
			}
				docPosition.setFilePath(filePath);
				docPosition.setFileType(attach.getFileType());
			}
			
			// I,R草稿箱，收件箱
			if (status.equals("I") || status.equals("R")) {

				if (doc.getSubscriberID2() != null
						&& !doc.getSubscriberID2().equals("")) {
					staffID = doc.getSubscriberID2();
					organizationID = doc.getDeptIDofSubscriber2();
					companyID = doc.getCompanyIDofSubscriber2();

					docPosition.setPosition("收件箱");

				} else {
					staffID = doc.getDrafterID();
					organizationID = doc.getOrganizationID();
					companyID = doc.getCompanyID();

					if (status.equals("I")) {
						docPosition.setPosition("草稿箱");
					} else {
						docPosition.setPosition("收件箱");
					}

				}
			}

			// Z信息平台
			if (status.equals("Z")) {

				docPosition.setPosition("信息平台");

			}

			// S 未审批
			if (status.equals("S") || status.equals("T") || status.equals("U")) {

				staffID = doc.getSubscriberID();
				organizationID = doc.getDeptIDofSubscriber();
				companyID = doc.getCompanyIDofSubscriber();
				if (status.equals("U")) {
					docPosition.setPosition("已审批公文");
				} else {
					docPosition.setPosition("未审批公文");
				}

			}

			// A 未盖章
			if (status.equals("A") || status.equals("F")) {

				staffID = doc.getSealerID();
				organizationID = doc.getDeptIDofSealer();
				companyID = doc.getCompanyIDofSealer();
				if (status.equals("A")) {
					docPosition.setPosition("未盖章公文");
				} else {
					docPosition.setPosition("已盖章公文");
				}

			}

			// A 未发公文
			if (status.equals("P")) {

				staffID = doc.getPublisherID();
				organizationID = doc.getDeptIDofPublisher();
				companyID = doc.getCompanyIDofPublisher();

				docPosition.setPosition("未发公文");

			}

			// G归档
			if (status.equals("G")) {

				organizationID = doc.getOrganizationID();
				companyID = doc.getCompanyID();

				docPosition.setPosition("已归档");

			}
			if (staffID != null && !staffID.equals("")) {
				staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff,
						new Object[] { staffID });
			}
			if (organizationID != null && !organizationID.equals("")) {
				org = (COrganization) baseBeanService.getBeanByHqlAndParams(
						hqlorg, new Object[] { organizationID });
			}
			if (companyID != null && !companyID.equals("")) {
				company = (Company) baseBeanService.getBeanByHqlAndParams(
						hqlcom, new Object[] { companyID });
			}
			if (staff != null) {
				docPosition.setStaffName(staff.getStaffName());
			}
			if (org != null) {
				docPosition.setDepartment(org.getOrganizationName());
			}
			if (company != null) {
				docPosition.setCompanyName(company.getCompanyName());
			}
			// A 已发公文，到未阅读中
			if (status.equals("O")) {

				String sql = "select d.readstate,s.staffName,o.organizationName,c.companyName from DT_OA_DOC_GSEND d,dt_hr_staff s,dtCOrganization o,dtCompany c where docId = ? and d.readerID=s.staffID and d.organizationID=o.organizationID and d.companyID = c.companyID";

				List<Object[]> readlist = baseBeanService
						.getListBeanBySqlAndParams(sql, new Object[] { doc
								.getDocId() });
				if(readlist.size()!=0){
				Object[] obj = null;
				String staffname = "";
				String orgname = "";
				String comname = "";
				for (int i = 0; i < readlist.size(); i++) {
					obj = readlist.get(i);
					if (obj[0].equals("1")) {
						docPosition.setPosition("未阅读");
					} else {
						docPosition.setPosition("已阅读");
					}
					staffname = docPosition.getStaffName();
					if (staffname == null) {
						staffname = "";
					}
					orgname = docPosition.getDepartment();
					if (orgname == null) {
						orgname = "";
					}
					comname = docPosition.getCompanyName();
					if (comname == null) {
						comname = "";
					}
					docPosition.setStaffName(staffname + obj[1] + ";");
					docPosition.setDepartment(orgname + obj[2] + ";");
					docPosition.setCompanyName(comname + obj[3] + ";");
				}
			  }else{
				  docPosition.setStaffName("旧数据未知");
				  docPosition.setDepartment("旧数据未知");
				  docPosition.setCompanyName("旧数据未知");
				  docPosition.setPosition("阅读(是否阅读未知)");
			  }
			}
			list.add(docPosition);

		}
		pageForm.setList(list);
		
		return pageForm;
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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public DocPosition getDocPosition() {
		return docPosition;
	}

	public void setDocPosition(DocPosition docPosition) {
		this.docPosition = docPosition;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DocumentSearchInfo getDocSearchInfo() {
		return docSearchInfo;
	}

	public void setDocSearchInfo(DocumentSearchInfo docSearchInfo) {
		this.docSearchInfo = docSearchInfo;
	}

}
