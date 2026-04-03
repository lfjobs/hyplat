package hy.ea.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.Remind;
import hy.ea.bo.SystemRemind;
import hy.ea.bo.human.Staff;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
/**
 * 	系统消息功能
 */
public class SystemRemindAction {
	@Resource
	private BaseBeanService baseBeanService;//基本的方法
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;//操作日志
	private String parameter;
	private SystemRemind sremind;//系统消息实体类
	private PageForm pageForm;
	private String search;
	private int pageNumber;
	private List<BaseBean> beans;
	private String receiveDate;
	private String linetype;//系统接收人区分

	//查询所有提醒信息
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", sremind);
		return getListRemind();
	}
	public String getListRemind(){
		   pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?5:pageNumber),getList());
			return "systemreminds";
	}
	//条件查询的方法 
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		//CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(SystemRemind.class);
		//dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		//dc.add(Restrictions.eq("organizationID", (String) session.get("organizationID")));
		dc.addOrder(Order.desc("saddDate"));
		
		if (search != null && search.equals("search")) {
			sremind = (SystemRemind) session.get("tablesearch");
		if (!"".equals(sremind.getScircularTitle())){
				dc.add(Restrictions.like("scircularTitle",sremind.getScircularTitle(),MatchMode.ANYWHERE));
		}
	}
		return dc;
}
	
	private DetachedCriteria serachHistoryaa() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(SystemRemind.class);
		//dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("staffID", account.getStaffID()));
		dc.add(Restrictions.or(Restrictions.eq("sremindStatus","02"),Restrictions.eq("sremindStatus", "03") ));
		dc.addOrder(Order.desc("saddDate"));
		return dc;
}
	//查看当前责任人的历史信息
	public String serachHistory(){
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?5:pageNumber),serachHistoryaa());
		return "historylist";
		
	}
	//系统消息的添加和修改
	public String addsremind() throws ParseException {
		//Map<String, Object> session = ActionContext.getContext().getSession();
		//CAccount account = (CAccount) session.get("account");
		//String organizationID = (String) session.get("organizationID");
		if (sremind.getSremindID() == null
				|| "".equals(sremind.getSremindID())) {
			sremind.setSremindID(serverService.getServerID("sremind"));
			parameter = "添加系统消息提醒(接收人分类:"+sremind.getSreceiveType()+")";
		}
		beans = new ArrayList<BaseBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(receiveDate);
		sremind.setSreceiveDate(date);
		//后台字符串时间转换为date类型的
	    Date dt=new Date();
        SimpleDateFormat matter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tt=matter.format(dt);
        Date  ttt= matter.parse(tt);
        sremind.setSaddDate(ttt);
        
		String hqlForMan = "from Staff where staffID = ?";
		String hql="";
		if(linetype.equals("zx")){
			sremind.setSreceiveType("01");
			hql = "from CAccount where accountOnLine = '01' and accountStatus = '00' ";
		}else if(linetype.equals("qb")){
			sremind.setSreceiveType("02");
			hql = "from CAccount where accountStatus = '00' ";
		}
		List<BaseBean> calist=new ArrayList<BaseBean>();
		calist = baseBeanService.getListBeanByHqlAndParams(hql, null);
		for(int i=0;i<calist.size();i++){
			CAccount c=(CAccount) calist.get(i);
			Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
					new Object[] { c.getStaffID() });
			Remind remind=new Remind();
			remind.setRemindID(serverService.getServerID("remind"));
			remind.setStaffID(c.getStaffID());
			if(sta!=null){
				remind.setStaffName(sta.getStaffName()+"---"+sta.getStaffCode());
			}else{
				remind.setStaffName(c.getAccountName());
			}
			remind.setCompanyID("");
			remind.setOrganizationID("");
			remind.setCircularType(sremind.getScircularType());
			remind.setCircularTitle(sremind.getScircularTitle());
			remind.setCircularText(sremind.getScircularText());
			remind.setUrlType("");
			remind.setDetailedurl("");
			remind.setReceiveDate(sremind.getSreceiveDate());
			remind.setRemindType(sremind.getSremindType());
			remind.setRemindStatus(sremind.getSremindStatus());
			remind.setAddDate(sremind.getSaddDate());
			beans.add(remind);
		}
		beans.add(sremind);
		//CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		//beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	//删除系统消息提醒
	public String delremind() {
		//Map<String, Object> session = ActionContext.getContext().getSession();
		//CAccount account = (CAccount) session.get("account");
		//String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		Object[] params = {sremind.getSremindID()};
		//String hql2="from SystemRemind where sremindID=?";
		//SystemRemind re=(SystemRemind) baseBeanService.getBeanByHqlAndParams(hql2, params);
	    //CLogBook logBook = logBookService.saveCLogBook(organizationID, "删除系统消息提醒(接收人:"+re.getStaffName()+")", account);
	    // beans.add(logBook);
	    String hql = "delete from SystemRemind where sremindID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
		return "success";
	}
	
	//修改状态
	public String upSremind(String sremindID) {
	 		String hql="update dtSystemRemind r set r.sremindStatus=? where r.sremindID=?";
	 		String[] hqls = new String[] { hql };
	 		Object[] gparams = {"02",sremindID};
	 		List<Object[]> parmsList = new ArrayList<Object[]>();
	 		parmsList.add(gparams);
	 		try {
	 			baseBeanService.executeSqlsByParmsList(null, hqls, parmsList);
	 		} catch (Exception e) {
	 		}
	 		return "systemreminds";
	 }
	public SystemRemind getSremind() {
		return sremind;
	}
	public void setSremind(SystemRemind sremind) {
		this.sremind = sremind;
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
	public String getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getLinetype() {
		return linetype;
	}
	public void setLinetype(String linetype) {
		this.linetype = linetype;
	}
	
}
