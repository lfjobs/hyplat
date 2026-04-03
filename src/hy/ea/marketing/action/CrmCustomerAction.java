package hy.ea.marketing.action;

import hy.base.action.BaseAction;
import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.ea.marketing.bo.DtCrmCustomer;
import hy.ea.marketing.bo.DtCrmCustomerActivity;
import hy.ea.marketing.bo.DtCrmCustomerCompetitive;
import hy.ea.marketing.bo.DtCrmCustomerOffer;
import hy.ea.marketing.bo.DtCrmCustomerProduct;
import hy.ea.marketing.bo.DtCrmCustomermenu;
import hy.ea.marketing.dao.DtCrmCustomerActivityDao;
import hy.ea.marketing.dao.DtCrmCustomerCompetitiveDao;
import hy.ea.marketing.dao.DtCrmCustomerDao;
import hy.ea.marketing.dao.DtCrmCustomerOfferDao;
import hy.ea.marketing.dao.DtCrmCustomerProductDao;
import hy.ea.util.StringUtil;

import java.io.InputStream;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author zhb 2014-10-22 下午4:38:36
 *
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class CrmCustomerAction extends BaseAction<Object>{
	
	private DtCrmCustomer crmCustomer;//个人客户的对象
	private DtCrmCustomermenu crmCustMenu;//客户相关的操作控制菜单(添加页面右上角)
	private String showType;//由于是公用一个页面，所以靠这个标志来区分是添加add、修改edit、查看view
	private String cstaffId;//从社会人力选择框传递过来的社会人力ID号
	private String customerid;//某个个人客户的id
	
	private DtCrmCustomerActivity dtCrmCustAtty;//进度维护
	private DtCrmCustomerProduct dtCrmCustProd;//意向产品
	private DtCrmCustomerOffer dtCrmCustOffer;//报价记录
	private DtCrmCustomerCompetitive dtCrmCustCompet;//精品资料
	

	@Resource
	protected DtCrmCustomerDao crmCustDao;
	@Resource
	protected DtCrmCustomerActivityDao crmCustAttyDao;
	@Resource
	protected DtCrmCustomerProductDao crmCustProdDao;
	@Resource
	protected DtCrmCustomerOfferDao crmCustOfferDao;
	@Resource
	protected DtCrmCustomerCompetitiveDao crmCustCompetDao;
	
	private InputStream excelStream;
	

	/**
	 * 个人客户可以从社会人力选择添加，也可以从手动添加。
	 * 如果是从社会人力添加，需要首先获取社会人力的信息，然后付给DtCrmCustomer对象。
	 */
	public String getHrStaffByID(){
		String hql = " from Staff as bean where bean.staffID = ?";
		Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cstaffId});
		//将Staff对象相关属性添加到DtCrmCustomer对象上
		crmCustomer = new DtCrmCustomer();
		crmCustomer.setCustomerid(serverService.getServerID("crmcus"));
		crmCustomer.setCustomercode("");
		crmCustomer.setCustomername(staff.getStaffName());
		crmCustomer.setStatus("01");//
		crmCustomer.setUsednmae("");
		crmCustomer.setSex(staff.getSex());
		crmCustomer.setIdtype("");
		crmCustomer.setIdentitycard("");
		crmCustomer.setReference(staff.getReference());		
		crmCustomer.setBirthday(staff.getBirthday());		
				
		showType="add";		
		return "add";
	}
	
	/**
	 * 获得一个客户用于查看
	 * @return
	 */
	public String viewCustomer(){
		if (isNotLogin()) {	return "nologin"; }	
		if (!StringUtil.validateNull(customerid)){//不为NULL 或 ""
			crmCustomer = (DtCrmCustomer)crmCustDao.getCustomerById(customerid);
		}
		showType="view";
		return "view";
	}
	
	/**
	 * 获得一个客户用于修改
	 * @return
	 */
	public String getCustomer(){
		if (isNotLogin()) {	return "nologin"; }	
		if (!StringUtil.validateNull(customerid)){//不为NULL 或 ""
			crmCustomer = (DtCrmCustomer)crmCustDao.getCustomerById(customerid);
			getCrmCustomerMenu();//修改前需要将控制菜单获取到
		}
		showType="edit";
		return "edit";
	}
	
	/**
	 * 获得客户列表
	 * @return
	 */
	public String getCustomerList(){		
		if (isNotLogin()) {	return "nologin"; }		
		String hql = " from DtCrmCustomer as model where model.groupcompanysn= ? order by model.verifytime desc";
		Object[] params = new Object[]{getGroupCompanySn()};
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber==0 ? 10 : pageNumber), hql, params);		
		return "list";
	}
	
	/**
	 * 跳转到添加客户
	 * @return
	 */
	public String addCustomer(){
		if (isNotLogin()) {	return "nologin"; }
		getCrmCustomerMenu();//跳转前需要将控制菜单获取到
		showType = "add";
		return "add";
	}
	
	/**
	 * 保存或更新客户
	 * @return
	 */
	public String saveOrUpdateCustomer(){
		if (isNotLogin()) {	return "nologin"; }
		crmCustomer.setCustomerid(serverService.getServerID("crmcus"));
		crmCustomer.setGroupcompanysn(getGroupCompanySn());
		baseBeanService.update(crmCustomer);
		return getCustomer();
	}
	
	/**
	 * 删除客户
	 * @return
	 */
	public String delCustomer(){
		if (isNotLogin()) {	return "nologin"; }
		if (!StringUtil.validateNull(customerid)){//不为NULL 或 ""
			crmCustDao.delCustomerById(customerid);
		}
		return "list";
	}	
	
	/**
	 * 保存个人客户调查的右上角菜单
	 * @return
	 */
	public String saveCrmCustomerMenu(){
		if (isNotLogin()) {	return "nologin"; }
		crmCustMenu.setCustomermenuid(serverService.getServerID("crmcustmenu"));
		crmCustMenu.setCompanyid(getCurrentAccount().getCompanyID());		
		baseBeanService.update(crmCustMenu);
		return "success";
	}
	
	/**
	 * 获取个人客户调查的右上角菜单
	 * @return
	 */
	private String getCrmCustomerMenu(){
		if (isNotLogin()) {	return "nologin"; }
		crmCustMenu = crmCustDao.getCustMenuByCompID(getCurrentAccount().getCompanyID());		
		return "success";
	}
	
	/**
	 * 获取“进度维护”的列表
	 * @return
	 */
	public String getListJd(){
		String hql = "";
		Object[] params = null;
		if (request.getAttribute("tablesearch")==null){
			hql = " from DtCrmCustomerActivity as model where model.dtCrmCustomer.customerkey= ? order by model.activitydate desc";
			params = new Object[]{crmCustomer.getCustomerkey()};
		}else{
			hql = " from DtCrmCustomerActivity as model where model.dtCrmCustomer.customerkey= ? and model.activitytitle like ? order by model.activitydate desc";
			params = new Object[]{crmCustomer.getCustomerkey(),"%" + request.getAttribute("tablesearch").toString() + "%"};//"'%" + request.getAttribute("tablesearch").toString() + "%'"
		}
		
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber==0 ? 10 : pageNumber), hql, params);
		return "01jdwh";
	}
	/**
	 * 查询进度维护
	 * @return
	 */
	public String toSearchJd(){		
		request.setAttribute("tablesearch", dtCrmCustAtty.getActivitytitle());
		return getListJd();
	}
	
	/**
	 * 保存或更新进度维护
	 * @return
	 */
	public String saveOrUpdateJd(){
		if (isNotLogin()) {	return "nologin"; }
		crmCustomer = getDtCrmCustomerByKey();
		
		dtCrmCustAtty.setActivityid(serverService.getServerID("atty"));
		dtCrmCustAtty.setDtCrmCustomer(crmCustomer);	
		
		crmCustAttyDao.save(dtCrmCustAtty);
		return "success";
	}
	
	/**
	 * 删除进度维护
	 * @return
	 */
	public String delCustAtty(){
		crmCustAttyDao.delete(dtCrmCustAtty);
		return getListJd();
	}
	
	
	/**
	 * 获取“意向产品”的列表
	 * @return
	 */
	public String getListYxcp(){
		String hql = "";
		Object[] params = null;
		if (request.getAttribute("tablesearch")==null){
			hql = " from DtCrmCustomerProduct as model where model.dtCrmCustomer.customerkey= ? ";
			params = new Object[]{crmCustomer.getCustomerkey()};
		}else{
			hql = " from DtCrmCustomerProduct as model where model.dtCrmCustomer.customerkey= ? and model.pname like ? ";
			params = new Object[]{crmCustomer.getCustomerkey(),"%" + request.getAttribute("tablesearch").toString() + "%"};
		}
		
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber==0 ? 10 : pageNumber), hql, params);
		return "02yxcp";
	}
	
	/**
	 * 导出进度维护到 excel
	 * @return
	 */
	public String showJdExcel() {
		getListJd();
		setExcelStream(excelService.showExcel(DtCrmCustomerActivity.columnHeadings(), pageForm.getList()));		
		return "showexcel";
	}
	
	/**
	 * 查询意向产品
	 * @return
	 */
	public String toSearchYxcp(){		
		request.setAttribute("tablesearch", dtCrmCustProd.getPname());
		return getListYxcp();
	}
	
	/**
	 * 删除意向产品
	 * @return
	 */
	public String delCustYxcp(){
		crmCustProdDao.delete(dtCrmCustProd);
		return getListYxcp();
	}
	
	/**
	 * 导出意向产品到 excel
	 * @return
	 */
	public String showYxcpExcel() {
		getListYxcp();
		setExcelStream(excelService.showExcel(DtCrmCustomerProduct.columnHeadings(), pageForm.getList()));		
		return "showexcel";
	}
	
	/**
	 * 保存或更新意向产品
	 * @return
	 */
	public String saveOrUpdateYxcp(){
		if (isNotLogin()) {	return "nologin"; }
		crmCustomer = getDtCrmCustomerByKey();
		
		dtCrmCustProd.setProductid(serverService.getServerID("prod"));
		dtCrmCustProd.setDtCrmCustomer(crmCustomer);	
		
		crmCustProdDao.save(dtCrmCustProd);
		return "success";
	}
	
	/**
	 * 获取“报价记录”的列表
	 * @return
	 */
	public String getListBjjl(){
		String hql = "";
		Object[] params = null;
		if (request.getAttribute("tablesearch")==null){
			hql = " from DtCrmCustomerOffer as model where model.dtCrmCustomer.customerkey= ? ";
			params = new Object[]{crmCustomer.getCustomerkey()};
		}else{
			hql = " from DtCrmCustomerOffer as model where model.dtCrmCustomer.customerkey= ? and model.pname like ? ";
			params = new Object[]{crmCustomer.getCustomerkey(),"%" + request.getAttribute("tablesearch").toString() + "%"};
		}
		
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber==0 ? 10 : pageNumber), hql, params);
		return "03bjjl";
	}
	
	/**
	 * 删除报价记录
	 * @return
	 */
	public String delCustBjjl(){
		crmCustOfferDao.delete(dtCrmCustOffer);
		return getListBjjl();
	}
	
	/**
	 * 导出报价记录到 excel
	 * @return
	 */
	public String showBjjlExcel() {
		getListBjjl();
		setExcelStream(excelService.showExcel(DtCrmCustomerOffer.columnHeadings(), pageForm.getList()));		
		return "showexcel";
	}
	
	/**
	 * 保存或更新报价记录
	 * @return
	 */
	public String saveOrUpdateBjjl(){
		if (isNotLogin()) {	return "nologin"; }
		crmCustomer = getDtCrmCustomerByKey();
		
		dtCrmCustOffer.setOfferid(serverService.getServerID("offer"));
		dtCrmCustOffer.setDtCrmCustomer(crmCustomer);	
		
		crmCustOfferDao.save(dtCrmCustOffer);
		return "success";
	}
	
	/**
	 * 查询报价记录
	 * @return
	 */
	public String toSearchBjjl(){		
		request.setAttribute("tablesearch", dtCrmCustOffer.getPname());
		return getListBjjl();
	}
	
	/**
	 * 获取“精品资料”的列表
	 * @return
	 */
	public String getListJpzl(){
		String hql = "";
		Object[] params = null;
		if (request.getAttribute("tablesearch")==null){
			hql = " from DtCrmCustomerCompetitive as model where model.dtCrmCustomer.customerkey= ? ";
			params = new Object[]{crmCustomer.getCustomerkey()};
		}else{
			hql = " from DtCrmCustomerCompetitive as model where model.dtCrmCustomer.customerkey= ? and model.pname like ? ";
			params = new Object[]{crmCustomer.getCustomerkey(),"%" + request.getAttribute("tablesearch").toString() + "%"};
		}
		
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber==0 ? 10 : pageNumber), hql, params);
		return "04jpzl";
	}
	
	/**
	 * 删除竞品资料
	 * @return
	 */
	public String delCustJpzl(){
		crmCustCompetDao.delete(dtCrmCustCompet);
		return getListJpzl();
	}
	
	/**
	 * 导出竞品资料到 excel
	 * @return
	 */
	public String showJpzlExcel() {
		getListJpzl();
		setExcelStream(excelService.showExcel(DtCrmCustomerCompetitive.columnHeadings(), pageForm.getList()));		
		return "showexcel";
	}
	
	/**
	 * 保存或更新竞品资料
	 * @return
	 */
	public String saveOrUpdateJpzl(){
		if (isNotLogin()) {	return "nologin"; }
		crmCustomer = getDtCrmCustomerByKey();
		
		dtCrmCustCompet.setCompetitiveid(serverService.getServerID("compet"));
		dtCrmCustCompet.setDtCrmCustomer(crmCustomer);	
		
		crmCustCompetDao.save(dtCrmCustCompet);
		return "success";
	}
	
	/**
	 * 查询竞品资料
	 * @return
	 */
	public String toSearchJpzl(){		
		request.setAttribute("tablesearch", dtCrmCustCompet.getPname());
		return getListJpzl();
	}
	
	/**
	 * 
	 * @return
	 */
	private DtCrmCustomer getDtCrmCustomerByKey(){
		return (DtCrmCustomer)baseBeanService.getBeanByKey(DtCrmCustomer.class, crmCustomer.getCustomerkey());		
	}
	
	/**
	 * 判断当前是否登录或session有效
	 * @return
	 */
	private boolean isNotLogin(){		
		return null==(CAccount) ActionContext.getContext().getSession().get("account");
	}
	
	

	public DtCrmCustomer getCrmCustomer() {
		return crmCustomer;
	}

	public void setCrmCustomer(DtCrmCustomer crmCustomer) {
		this.crmCustomer = crmCustomer;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}
	public String getCstaffId() {
		return cstaffId;
	}
	public void setCstaffId(String cstaffId) {
		this.cstaffId = cstaffId;
	}
	public DtCrmCustomermenu getCrmCustMenu() {
		return crmCustMenu;
	}

	public void setCrmCustMenu(DtCrmCustomermenu crmCustMenu) {
		this.crmCustMenu = crmCustMenu;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public DtCrmCustomerActivity getDtCrmCustAtty() {
		return dtCrmCustAtty;
	}

	public void setDtCrmCustAtty(DtCrmCustomerActivity dtCrmCustAtty) {
		this.dtCrmCustAtty = dtCrmCustAtty;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public DtCrmCustomerProduct getDtCrmCustProd() {
		return dtCrmCustProd;
	}

	public void setDtCrmCustProd(DtCrmCustomerProduct dtCrmCustProd) {
		this.dtCrmCustProd = dtCrmCustProd;
	}

	public DtCrmCustomerOffer getDtCrmCustOffer() {
		return dtCrmCustOffer;
	}

	public void setDtCrmCustOffer(DtCrmCustomerOffer dtCrmCustOffer) {
		this.dtCrmCustOffer = dtCrmCustOffer;
	}
	
	public DtCrmCustomerCompetitive getDtCrmCustCompet() {
		return dtCrmCustCompet;
	}

	public void setDtCrmCustCompet(DtCrmCustomerCompetitive dtCrmCustCompet) {
		this.dtCrmCustCompet = dtCrmCustCompet;
	}
	
}
