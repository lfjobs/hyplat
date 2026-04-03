package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.DtticketBusinessManager;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.opensymphony.xwork2.ActionContext;

public class PiaoWuManagerAction {
//票务管理
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	private String parameter;
	private DtticketBusinessManager piaowumanager;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private String aa;
	private List<BaseBean> beans;
	private int pageNumber;
	private String staffID;
	private String staffName;
	
	//根据条件查询票务信息
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", piaowumanager);
			return getListpiaowu();
		}

		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String companyID = account.getCompanyID();
			DetachedCriteria dc = DetachedCriteria.forClass(DtticketBusinessManager.class);
			dc.add(Restrictions.eq("companyId", companyID));
			dc.add(Restrictions.eq("organizationId", organizationID));
			if (search != null && search.equals("search")) {
				piaowumanager = (DtticketBusinessManager) session.get("tablesearch");
				if(!piaowumanager.getTicketType().equals("")&&!piaowumanager.getTicketType().equals("--请选择--"))
				{
					dc.add(Restrictions.like("ticketType", piaowumanager.getTicketType(),MatchMode.ANYWHERE));
				}
				if(!piaowumanager.getClassOrtrains().equals(""))
				{
					dc.add(Restrictions.like("classOrtrains", piaowumanager.getClassOrtrains(),MatchMode.ANYWHERE));
				}
				if(null!= piaowumanager.getDeparture()&& null!=piaowumanager.getArrivalDate())
				{
					dc.add(Restrictions.between("departure",piaowumanager.getDeparture(),
						piaowumanager.getArrivalDate()));
				}
				dc.addOrder(Order.desc("ticketbusinessid"));
			}
			return dc;
		}

		// 票务管理列表
		public String getListpiaowu() {
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			if(aa.equals("aa")){
				return "getfenpeiListpiaowu";
				
			}if(aa.equals("bb")){
				return "getpiaowubaobiao";
				
			}else{
				return "list";
			}
					
		}
		//保存票务管理信息
				public String savepiaowuInformation() {
					ActionContext actionContext = ActionContext.getContext();
					Map<String, Object> session = actionContext.getSession();
					CAccount account = (CAccount) session.get("account");
					String CompanyId = account.getCompanyID(); 
					String organizationID=(String) session.get("organizationID");
					//保存数据
					String[] newbillName =  piaowumanager.getBillNumber().split("-");
					if(null==piaowumanager.getTicketbusinessid()||"".equals(piaowumanager.getTicketbusinessid())){
						 for (int i = 0; i < newbillName.length; i++) {
								 piaowumanager.setTicketbusinessid(serverService.getServerID("piaowumanager"));
								 piaowumanager.setBillNumber(newbillName[i]);
								 List<BaseBean> beans=new ArrayList<BaseBean>();
								 piaowumanager.setCompanyId(CompanyId);
								 piaowumanager.setOrganizationId(organizationID);
								 CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
								 beans.add(piaowumanager);
								 beans.add(logbook);
								 baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
					        }
						 parameter = "添加票务管理信息(票务类型:"+piaowumanager.getTicketType()+")";
						 return "success";
					}else{
						if(staffID!=null){
							String hql1 = "from DtticketBusinessManager where companyId = ? and  ticketbusinessid = ? ";
							DtticketBusinessManager q0 = (DtticketBusinessManager) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{account.getCompanyID(),piaowumanager.getTicketbusinessid()});
							parameter = "分配票务信息(票务编号:"+ q0.getTicketbusinessid() +")";
							piaowumanager.setStaffId(staffID);
							piaowumanager.setStaffName(staffName);
						}else{
						String hql = "from DtticketBusinessManager where companyId = ? and  ticketbusinessid = ? ";
						DtticketBusinessManager q0 = (DtticketBusinessManager) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),piaowumanager.getTicketbusinessid()});
						parameter = "修改票务管理信息(票务类型:"+ q0.getTicketbusinessid() +")";
						}
					}
					List<BaseBean> beans = new ArrayList<BaseBean>();
					piaowumanager.setCompanyId(CompanyId);
					piaowumanager.setOrganizationId(organizationID);
					beans.add(piaowumanager);
					CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
					beans.add(logbook);
					baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
					return "success";
				}
				/**
				 * 打印预览
				 * @return
				 */
				public String printaccess(){
					Map<String, Object> session = ActionContext.getContext().getSession();
					String organizationID=(String) session.get("organizationID");
					CAccount account = (CAccount) session.get("account");
					String orginazname = account.getCompanyName();
					String hql = "from DtticketBusinessManager where companyId = ? and organizationId = ? ";
					Object[] parms = {account.getCompanyID(),organizationID};
					beans = new ArrayList<BaseBean>();
					beans = baseBeanService.getListBeanByHqlAndParams(hql, parms);
					ServletActionContext.getRequest().getSession().setAttribute(
							"orginazname", orginazname);
					return "information";
					
				}
		/**
		 * 删除
		 * 
		 * @return
		 */
		public String deletepiaojuInformation() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = session.get("organizationID").toString();
			String hql = "delete DtticketBusinessManager where ticketbusinessid = ? and companyId = ?";
			Object[] params = {piaowumanager.getTicketbusinessid(),account.getCompanyID()};
			beans = new ArrayList<BaseBean>();
			//parameter = "删除票据信息：(票据号：" + piaowumanager.getTicketbusinessid() + ")";
			CLogBook logBook = logBookService.saveCLogBook(organizationID,
					parameter, account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
					new String[] { hql }, params);
			return "success";
		}
		//导出excel
		public String showExcel() {
			List<BaseBean> piaowuManagaerList = baseBeanService
					.getListByDC(getList());
			excelStream = excelService.showExcel(DtticketBusinessManager.columnHeadings(),
					piaowuManagaerList);
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			CLogBook cLogBook =logBookService.saveCLogBook(null, "导出票据信息", account);
			baseBeanService.update(cLogBook);
			return "showexcel";
		}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public DtticketBusinessManager getPiaowumanager() {
		return piaowumanager;
	}

	public void setPiaowumanager(DtticketBusinessManager piaowumanager) {
		this.piaowumanager = piaowumanager;
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
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public String getAa() {
		return aa;
	}

	public void setAa(String aa) {
		this.aa = aa;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
}
