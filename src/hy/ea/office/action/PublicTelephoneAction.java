package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.PublicTelephone;
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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/**
 * 公共电话薄管理
 */
@Controller
@Scope("prototype")
public class PublicTelephoneAction {
	private int pageNumber;
	private InputStream excelStream;
	private PageForm pageForm;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private ServerService serverService;

	private String search;
	private String parameter;
	@Resource
	private BaseBeanService baseBeanService;
	private PublicTelephone telephone;
	public boolean excel=false;
	

	//根据条件查询公共电话薄
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", telephone);
		return getaTelephoneList();
	}
	/**
	 * 旧版通讯录列表
	 * @return
	 */
	/*private DetachedCriteria getTList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) session.get("organizationID");
		DetachedCriteria dc = DetachedCriteria.forClass(PublicTelephone.class);
		dc.add(Restrictions.eq("organizationID", organizationID));
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			telephone = (PublicTelephone) session.get("tablesearch");
			dc.add(Restrictions.like("linkmanName", telephone.getLinkmanName(), MatchMode.ANYWHERE));
			dc.add(Restrictions.like("company", telephone.getCompany(),MatchMode.ANYWHERE));
		}
		return  dc;
	}*/
	
	/**
	 * 新版通讯录管理-自动查询获取
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	private PageForm getTList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object>  arag=new ArrayList<Object>();
		String sql="select * from  (select vm.COMPANYNAME  comName ,vm.contactconnections contact ,vm.CRESPONSIBLE peopleName ,vm.RESPONSIBLETEL peoplePhone,vm.REMARK peopleEmail,vm.companyAddr address,vm.companyid from"  
					+" view_concom vm " 
					+" union all "
					+" select c.companyname comName,vs.RELATION contact,vs.STAFFNAME peopleName,vs.REFERENCE peoplePhone,vs.REFERENCEORGANIZATION peopleEmail,vs.STAFFADDRESS address,vs.COMPANYID"
					+" from view_conuser vs "
					+" left join dtcompany c on c.companyid=vs.COMPANYID "
					+" union "
					+" select sc.companyname comName,'员工' as contact ,sc.staffname peopleName,sc.reference peoplePhone,sc.referenceorganization peopleEmail,sc.STAFFADDRESS address,sc.companyid"
					+" from view_sc  sc ) tg where tg.companyid=?";
		arag.add(account.getCompanyID());
		if(search!=null&&"search".equals(search)){
			telephone=(PublicTelephone)session.get("tablesearch");
			if(telephone.getLinkmanName()!=null&&!"".equals(telephone.getLinkmanName().trim())){
				sql+=" and tg.peopleName like ?"; 
				arag.add("%"+telephone.getLinkmanName().trim()+"%");
			}
			if(telephone.getCompany()!=null&&!"".equals(telephone.getCompany().trim())){
				sql+=" and tg.contact like ?"; 
				arag.add("%"+telephone.getCompany().trim()+"%");
			}
		}
		if(!excel){
			pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber():1), (pageNumber==0?10:pageNumber), sql, "select count(1) from ("+sql+")", arag.toArray());	
		}else{
			List<BaseBean> list=baseBeanService.getListBeanBySqlAndParams(sql,arag.toArray());
			pageForm=new PageForm();
			pageForm.setList(list);
		}
		return pageForm;
	}
	/***********************************************/

	//公共电话薄管理列表
	public String getaTelephoneList() {
		//pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber():1), (pageNumber==0?4:pageNumber),getTList());//旧版
		pageForm = getTList();//新版
		return "telephone";	
	}
	
	// 导出公共电话薄管理列表

	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		//excelStream = excelService.showExcel(PublicTelephone.columnHeadings(), baseBeanService.getListByDC(getTList()));//旧版
		excel=true;
		excelStream = excelService.showExcel(PublicTelephone.columnHeadings(), getTList().getList());//新版
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出公告管理列表", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
  //保存公共电话薄管理列表
    
    public String saveTelephone(){
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (telephone.getTelephoneID() == null
				|| "".equals(telephone.getTelephoneID())) {
			telephone.setTelephoneID(serverService.getServerID("telephoneID"));
			parameter = "添加公共电话薄管理列表(联系人姓名:"+telephone.getLinkmanName()+")";
		}
		else
		{
		 String hql2="from PublicTelephone where companyID=?  and telephoneID=?";
		 PublicTelephone aff=(PublicTelephone) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ account.getCompanyID(),telephone.getTelephoneID() });
		 parameter = "修改公共电话薄管理列表(联系人姓名:"+aff.getLinkmanName()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		telephone.setCompanyID(account.getCompanyID());
		telephone.setOrganizationID(organizationID);
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(telephone);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
    }

    //删除公共电话薄管理列表
	 public String delTelephone(){
		 
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
		    Object[] params = {account.getCompanyID(),telephone.getTelephoneID()};
		    String hql1=" from PublicTelephone where companyID=? and telephoneID=?";
			PublicTelephone cf=(PublicTelephone) baseBeanService.getBeanByHqlAndParams(hql1, params);
			CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除公共电话薄管理列表(联系人姓名:"+cf.getTelephoneID()+")", account);
	    	String[] hql={"delete from PublicTelephone where  companyID=? and telephoneID=?"};
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
			return "success";
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

	public PublicTelephone getTelephone() {
		return telephone;
	}

	public void setTelephone(PublicTelephone telephone) {
		this.telephone = telephone;
	}

}
