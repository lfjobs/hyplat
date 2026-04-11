package hy.ea.invoicing.action.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.voucher.DtInvDyco;
import hy.ea.bo.invoicing.voucher.DtInvMco;
import hy.ea.bo.invoicing.voucher.FiscalPeriod;
import hy.ea.human.service.COrganizationService;
import hy.ea.invoicing.service.McoService;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;


/**
 * 年度结转
 * xyz
 */
@Controller
@Scope("prototype")
public class DyCoTurnAction {
	private static final Logger logger = LoggerFactory.getLogger(DyCoTurnAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private COrganizationService corganizationService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	@Resource
	private McoService mcoService;//年结存储过程方法调用
	
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private String result;
	private DtInvDyco invdyco;//年度结转
	private List<CCode> connectionlist;//单位往来关系
	private List<CCode> currencylist;//币别
	private String organizationname;//部门名称
	private String dyco_ym;//结转年月
	private String dycotype;//结转，退结转，结退
	/**
	 * 凭证录入主页
	 * 
	 */
	public String toDyCoTurn(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		//责任人name
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		session.put("ManStaffName", "("+sta.getStaffCode()+")"+sta.getStaffName());
		session.put("ManStaffId", sta.getStaffID());
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		currencylist = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20150601x66efsem5e0000000078");
		//部门名称
		String orghql = "from COrganization where companyID=? and organizationID=?";
		COrganization cOrganization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(orghql, new Object[] {
						account.getCompanyID(),
						organizationID});
		organizationname = cOrganization.getOrganizationName();
		pageForm= baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber==0?10:pageNumber), getList());
		return "todycoturn";
	}
	/**
	 * 保存前判断年月时间是否有效
	 * @return
	 * @throws ParseException 
	 */
	public String getyearmonth() throws ParseException{
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String groupCompanySn = session.get("groupCompanySn").toString();
		//String organizationID = (String) session.get("organizationID");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String companyID = account.getCompanyID();
		//年度结转 表是否有要结转年月数据
		boolean a=false;
		String hqldyco="from DtInvDyco where groupcsn=? and companyid=?";
		Object[] param = {groupCompanySn,companyID};
		List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(hqldyco, param);
		if(list.size()>0)
		{
			for(int j=0;j<list.size();j++){
				DtInvDyco dyco=(DtInvDyco) list.get(j);
				if(dyco_ym.equals(dyco.getDycoYear())){
					a=true;
					break;
				}
			}
		}
		//判断终止年月是否等于输入的结转年月的上一个月  参数b
		String hqlf="from FiscalPeriod where companyID=? order by year desc";
		FiscalPeriod fPeriod=(FiscalPeriod)baseBeanService.getBeanByHqlAndParams(hqlf, 
				new Object[]{companyID});
		boolean b=false;
		boolean d=false;
		String yymm="";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		Calendar calendar = Calendar.getInstance();
 		try {
 			calendar.setTime(dateFormat.parse(dyco_ym));
 		} catch (ParseException e) {
 			logger.error("操作异常", e);
 		}
 		calendar.add(Calendar.MONTH, -1);
 		yymm=dateFormat.format(calendar.getTime());
		if(fPeriod!=null){
			String stime=fPeriod.getStartDate();
			String etime=fPeriod.getEndDate();
			if(dyco_ym.equals(stime)){
				d=true;
			}
			if(etime.equals(yymm))
			{b=true;}
		}
		//判断在月结表中 yearmonth 有没有比输入年月大的
		String hql="from DtInvMco where companyid=? order by yearmonth desc";
		DtInvMco invmco=(DtInvMco)baseBeanService.getBeanByHqlAndParams(hql, 
				new Object[]{companyID});
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date1 = sdf.parse(dyco_ym);
		boolean c=false;
		if(invmco!=null){
			String yearm=invmco.getYearmonth();
			Date date = sdf.parse(yearm);
			if(date.getTime()>date1.getTime())
			{c=true;}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", a);
		map.put("c", c);
		map.put("b", b);
		map.put("d", d);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 年度结转的 确定作业
	 */
	public String SaveDyCoTurn(){
		HttpServletRequest re=ServletActionContext.getRequest();
		Map<String,Object> session=ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
	    String groupCompanySn=session.get("groupCompanySn").toString();
	    String companyID = account.getCompanyID(); 
	    String stas="";
	    if(dycotype.equals("jiez")){
	    	//结转
	    	stas=mcoService.proDYCO(groupCompanySn, dyco_ym, companyID);
	    }else if(dycotype.equals("jie1")){
	    	//年结(删掉之后月结资料重新月结)
	    	stas=mcoService.DelDMCOProDYCO(groupCompanySn, dyco_ym, companyID);
	    }else if(dycotype.equals("tjiez")){
	    	//退结转
	    	mcoService.delDYCO(dyco_ym, companyID);
	    }else if(dycotype.equals("all")){
	    	//退结转
		    mcoService.delDYCO(dyco_ym, companyID);
		    //结转
		    stas=mcoService.proDYCO(groupCompanySn, dyco_ym, companyID);
	    }else if(dycotype.equals("jie2")){
	    	mcoService.delDYCO(dyco_ym, companyID);
		    mcoService.DelDMCOProDYCO(groupCompanySn, dyco_ym, companyID);
	    }
	    re.setAttribute("stas",stas);
	    if(!"".equals(stas)){
	    	return "todycoturn";
	    }
		return "success";
	}
	/**
	 * 
	 * 查询
	 * @return
	 */
	public String toSearch(){
		ActionContext.getContext().getSession().put("tablesearch", invdyco);
		return toDyCoTurn();
	}
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String groupCompanySn = session.get("groupCompanySn").toString();
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(DtInvDyco.class);
		dc.add(Restrictions.eq("groupcsn", groupCompanySn));
		dc.add(Restrictions.eq("companyid", companyID));
		dc.add(Restrictions.eq("orgid", organizationID));
        dc.addOrder(Order.desc("dycoYear"));
		if (search != null && search.equals("search")) {
			invdyco = (DtInvDyco) session.get("tablesearch");
			 if(invdyco.getDycoYear()!=null&&!invdyco.getDycoYear().equals("")){
			 dc.add(Restrictions.like("dycoYear",invdyco.getDycoYear().trim(), MatchMode.ANYWHERE));
			 }
		}
		return dc;
	}
	public String ajaxorganization(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String orgid=request.getParameter("orgid");
		String comid=request.getParameter("comid");
		//查询部门
		COrganization cor=corganizationService.getCoranizationForOTree(orgid);
		//查询供应商
		String hql="from ContactCompany where ccompanyID=?";
		ContactCompany cc=(ContactCompany) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{comid});
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("org",cor);
		map.put("com",cc);
		JSONObject obj=JSONObject.fromObject(map);
		result=obj.toString();
		return "success";
	}
	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}
	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}
	public ServerService getServerService() {
		return serverService;
	}
	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}
	public CLogBookService getLogBookService() {
		return logBookService;
	}
	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
	}
	public CCodeService getCodeService() {
		return codeService;
	}
	public void setCodeService(CCodeService codeService) {
		this.codeService = codeService;
	}
	public McoService getMcoService() {
		return mcoService;
	}
	public void setMcoService(McoService mcoService) {
		this.mcoService = mcoService;
	}
	public DtInvDyco getInvdyco() {
		return invdyco;
	}
	public void setInvdyco(DtInvDyco invdyco) {
		this.invdyco = invdyco;
	}
	public List<CCode> getConnectionlist() {
		return connectionlist;
	}
	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}
	public List<CCode> getCurrencylist() {
		return currencylist;
	}
	public void setCurrencylist(List<CCode> currencylist) {
		this.currencylist = currencylist;
	}
	public String getOrganizationname() {
		return organizationname;
	}
	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getDyco_ym() {
		return dyco_ym;
	}
	public void setDyco_ym(String dyco_ym) {
		this.dyco_ym = dyco_ym;
	}
	public String getDycotype() {
		return dycotype;
	}
	public void setDycotype(String dycotype) {
		this.dycotype = dycotype;
	}
	
}