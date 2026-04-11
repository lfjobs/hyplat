package com.tiantai.wfj.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.ProductPriceCategory;
import hy.ea.bo.finance.Profitshare;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.service.CCodeService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.ToChineseFirstLetter;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.sun.mail.iap.Response;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.bo.TEshopDaicp;
import com.tiantai.wfj.util.SessionWrap;

@Controller
@Scope("prototype")
public class WfjEcusInfoAction {
	private static final Logger logger = LoggerFactory.getLogger(WfjEcusInfoAction.class);
	private int pageNumber;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CCodeService codeService;
	@Resource
	private UpLoadFileService fileService;
	protected HttpServletRequest request;
	private Staff staff;
	private TEshopCustomer customer;
	private StaffAddress staffAddress;
	private List<BaseBean> listAddress;
	private List<BaseBean> beans;
	private List<BaseBean> noBeans;//未代理产品
	private String addressId;
	private PageForm pageForm;
	private String staffName;
	private String reference;
	private String search;
	private String ppID;
	private String bdText;
	private String bdUrl;
	private String bdPic;
	private Object result;
	private List<CCode> proType;	
	
	//wk
    private GoodsManage goodsManage;
    private ProductPackaging productPackaging;
    private ProductPriceCategory productPriceCategory=new ProductPriceCategory();;
    private Profitshare  profitshare ;
    private List<CCode> codeList;
    private int pagenull;

	/**
	 * 微分金客户注册管理
	 * @return
	 */
	 public String  getwfjEcusInfo(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account.getStaffID()==null){
			return "login";
		}
		List<Object> parms = new ArrayList<Object>();
		String sql="select st.staffcode,st.staffName,st.staffIdentityCard,st.reference,te.account "+
		"from dt_hr_Staff st,T_ESHOP_CUSTOMER te  where st.staffID=te.staffid";
		if(search !=null && search.equals("search")){
			staff = (Staff) session.get("tablesearch");
			if(staff.getStaffName()!=null&&!staff.getStaffName().equals("")){
				sql +=" and st.staffName like ?";
				parms.add("%"+staff.getStaffName()+"%");
			}
			if (staff.getReference()!=null&&!staff.getReference().equals("")) {
				sql += " and st.reference like ?";
				parms.add("%"+staff.getReference()+"%");	
			}
		}		
		pageForm =	baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1),(pageNumber==0?15:pageNumber), sql,"select count(1) "
						+ sql.substring(sql.indexOf("from")),parms.toArray());
		return "personal_list";
	}
	 public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", staff);
			return getwfjEcusInfo();
	}
	/**
	 * 个人信息维护
	 * @return
	 */
	public String personalInfor(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer)sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		if (cus == null) {		
			return "login";
		}
		String customerId = cus.getStaffid();//获取消费者id
		String hql1="from TEshopCustomer where  staffid = ? ";
		customer=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hql1,new Object[]{customerId});
		String hql2="from Staff where staffID=?";
		staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{customerId});	   
		String hql3="from StaffAddress where staffid=? and isDefault='是'";
		staffAddress=(StaffAddress) baseBeanService.getBeanByHqlAndParams(hql3, new Object[]{customerId});
			return "editInfor";
	}
	/**
	 * wk添加代理关系
	 * 
	 */
	public String sevedailicp()
	{
		
			HttpSession session = ServletActionContext.getRequest().getSession();
			SessionWrap sw = SessionWrap.getInstance();		
			HttpServletRequest request=ServletActionContext.getRequest();
			
			TEshopCusCom scc= (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);			
			String chenpID=request.getParameter("chenpID");
			Object[] params=chenpID.split(",");
		 	TEshopDaicp daili = new TEshopDaicp();
	    	
			TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
			if (cus == null) {		
				return "login";
			}				
			if(params.length>1)
			{			   
			    for (int j = 0; j < params.length; j++) {	
			   
			    	daili.setCpid(serverService.getServerID("TEshopDaicp"));	
			    	String sql1="from ProductPackaging d  where d.ppID =?";
			    	productPackaging=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(sql1, new Object[]{params[j]});
			         daili.setCpppID(productPackaging.getPpID());
			         daili.setCpuserID(scc.getStaffid());
			         daili.setCpcomdid(productPackaging.getCompanyID());			         
			        if(scc!=null&&scc.getCompanyId()!=null){
			         daili.setCpusercomdid(scc.getCompanyId());
			        }
			        else{			        
			        COrganization cor=(COrganization)baseBeanService.getBeanByHqlAndParams("from  COrganization  d where d.organizationID=? ", new Object[]{scc.getOrganizationID()});
			        daili.setCpusercomdid(cor.getCompanyID());				       
			        }
			        daili.setCpdaidate(new Date());
			        baseBeanService.save(daili);
			    }			
			    }
			else{				
		    	daili.setCpid(serverService.getServerID("TEshopDaicp"));	
				String sql1="from ProductPackaging d  where d.ppID =?";
			    productPackaging=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(sql1, new Object[]{params[0]});		 			     
		    	daili.setCpppID(productPackaging.getPpID());
		    	   daili.setCpuserID(scc.getStaffid());
			         daili.setCpcomdid(productPackaging.getCompanyID());
			         daili.setCpdaidate(new Date());			    
				 if(scc!=null&&scc.getCompanyId()!=null){
			         daili.setCpusercomdid(scc.getCompanyId());
			        }
			        else{			        
			        COrganization cor=(COrganization)baseBeanService.getBeanByHqlAndParams("from  COrganization  d where d.organizationID=? ", new Object[]{scc.getOrganizationID()});
			        daili.setCpusercomdid(cor.getCompanyID());				       
			        }
			     baseBeanService.save(daili);
			}
	return "success";
	
	}
	/**
	 * WK 
	 * 可代理的产品查询
	 * 
	 * 查询所有产品
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public String getDLgetlist()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		if (cus == null) {		
			return "login";
		}		
		String sql="select pp.ppid,pp.goodsname ,dcoma.companyname , pc.money ,pp.quantity,";			
		sql+="pe.agent";		
		sql+=",pp.image from dt_ProductPackaging pp,dt_ProductPriceCategory pc,dtCompany dcoma,dtGoodsManage dg ,PROFITSHARE pe where  pp.goodsid=dg.goodsid and pc.ppid=pp.ppid  and pp.showweixin='01' and pp.ppstatus='0' and dcoma.companyid=pp.companyid and pe.ppid=pp.ppid ";
		//[0] =产品ID [1]产品名字 [2] 可代理产品所属公司 [3]产品价格[4]产品数量[5]供应商代理的佣金[6]产品图片and pp.ppstatus='0'and pp.showweixin='0'
		String sql1=" select  count(*) from dt_ProductPackaging pp,dt_ProductPriceCategory pc,dtCompany dcoma,dtGoodsManage dg ,PROFITSHARE pe where pp.goodsid=dg.goodsid and pc.ppid=pp.ppid  and pp.showweixin='01' and pp.ppstatus='0' and dcoma.companyid=pp.companyid and pe.ppid=pp.ppid ";
	    pageForm=baseBeanService.getPageFormBySQL(pagenull, 10, sql, sql1, null);
	    beans=pageForm.getList();
	    Map<String,Object> map = new HashMap<String, Object>();	
		map.put("pageForm",beans);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		if(pagenull==1)
		return "chanpinkdl";		
		return "success";
	}
	/**
	 * WK
	 * 查询该用户代理的产品
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String getdllist()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom scc= (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);			
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		if(cus==null){
			return "login";
		}
		String customerId = cus.getStaffid();	
		String sql="select dp.ppkey,dp.ppid ,dp.goodsname ,dc.money , ps.agent   ," +
				"dco.companyname , dp.shangjiastatus , dp.quantity ,dp.image " +
				"from dt_ProductPackaging dp ,dt_ProductPriceCategory dc ,PROFITSHARE ps,T_ESHOP_Daicp  dai,dtCompany  dco " +
				"where  dai.cpppID=dp.ppid and dp.showweixin='01'   and ps.ppid=dp.ppid and   dai.cpcomdid=dco.companyid and dp.ppid=dc.ppid " +
		//and dp.showweixin='02
				" and dai.cpuserid=? and dai.cpusercomdid=?";
		
		String hql1="from TEshopCustomer where  staffid = ? ";
		customer=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hql1,new Object[]{customerId});
		String hql2="from Staff where staffID=?";
			staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{customerId});     
			beans = new ArrayList<BaseBean>();
	
			beans= baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{scc.getStaffid(),scc.getCompanyId()});	
		return "chanpindaili";	
	}
	
	/**
	 * ty
	 * 新代理产品页面
	 * */
	@SuppressWarnings("unchecked")
	public String getProductAgency(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom scc= (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);			
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		if(cus==null){
			return "login";
		}
		//代理
		String customerId = cus.getStaffid();	
		String sql="select dp.ppkey,dp.ppid ,dp.goodsname ,dc.money , ps.agent   ," +
				"dco.companyname , dp.shangjiastatus , dp.quantity ,dp.image ,dai.cpid,dp.goodsID ,dp.companyID ,dp.organizationID " +
				"from dt_ProductPackaging dp ,dt_ProductPriceCategory dc ,PROFITSHARE ps,T_ESHOP_Daicp  dai,dtCompany  dco " +
				"where  dai.cpppID=dp.ppid and dp.showweixin='01'   and ps.ppid=dp.ppid and   dai.cpcomdid=dco.companyid and dp.ppid=dc.ppid " +
				" and dai.cpuserid=? and dai.cpusercomdid=?";
		
		String hql1="from TEshopCustomer where  staffid = ? ";
		customer=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hql1,new Object[]{customerId});
		String hql2="from Staff where staffID=?";
		staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{customerId});     
		beans = new ArrayList<BaseBean>();
		beans= baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{scc.getStaffid(),scc.getCompanyId()});
		
		//未代理
		String sql1="select dp.ppid ,dp.goodsname ,dc.money ,dp.image ,dp.goodsID ,dp.companyID ,dp.organizationID from dt_ProductPackaging dp,dt_ProductPriceCategory dc " +
				"where dp.ppid=dc.ppid and dp.ppstatus ='0' and dp.ppid not in(select dp.ppid " +
				"from dt_ProductPackaging dp ,T_ESHOP_Daicp dai " +
				"where  dai.cpppID=dp.ppid and dp.showweixin='01' and dai.cpuserid=? and dai.cpusercomdid=?)";
		noBeans = new ArrayList<BaseBean>();
		noBeans= baseBeanService.getListBeanBySqlAndParams(sql1,new Object[]{scc.getStaffid(),scc.getCompanyId()});
		return "productAgency";
	}
	public String share(){
		
		return "share";
	}
	/**
	 * WK
	 * 个人产品查询
	 * 根据产品添加时候绑定的人的ID 查询他自己 然后 在根据产品的ID查询出钱的信息
	 * 然后 跟产品的ID查询出属于那个公司的信息
	 * 然后 根据产品表里面的物品ID 查询出这个物品的数量以及所剩数量
	 *sql 
	 */
	public String  getlist()
	{
 		HttpServletRequest request=ServletActionContext.getRequest();		
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		TEshopCusCom scc= (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);		
		String httppase=request.getParameter("httppase");
		//01 0 2  03  //01公司  02 店主 03  游客
		if(cus==null){
			return "login";
		}
		String customerId = cus.getStaffid();
		String sql="select  pp.ppID, pp.goodsid ,pp.goodsname as ,pp.shangjiastatus ,pp.quantity ,pc.money ,pp.image " +
				"from  dt_ProductPackaging  pp, dt_ProductPriceCategory  pc ,PROFITSHARE ps,dtGoodsManage gm " +
				"where pp.ppid=pc.ppid and pp.ppid=ps.ppid and pp.showweixin='01'  and pp.goodsid=gm.goodsid ";
		String hql1="from TEshopCustomer where  staffid = ? ";
		customer=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hql1,new Object[]{customerId});
		String hql2="from Staff where staffID=?";
		staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{customerId}); 	
		if( scc!=null&&scc.getCusType()!=null&&"".equals(scc.getCusType()))
		{			
		}
		else if(scc!=null&&"01".equals(scc.getCusType()))
		{
			sql+="and  pp.companyid=? ORDER by pp.shangjiastatus DESC ";		    
			beans= baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{scc.getCompanyId()});	
		}
		else if(scc!=null&&"02".equals(scc.getCusType()))
		{
			sql+="and   pp.organizationid=? ORDER by pp.shangjiastatus DESC ";
			beans= baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{scc.getOrganizationID()});		
		}	
		return "getlist";
		
	}
	/**
	 * 修改个人信息
	 * @return
	 */
	public void savePersonalInfor(){
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		String customerId = cus.getStaffid();
		beans = new ArrayList<BaseBean>();
		String[] hql1={" update Staff set staffName=?,reference=? where staffID=?"};
		String[] hql2={" update TEshopCustomer set password=? where staffid=?"};
		Object[] staffparams={staff.getStaffName(),staff.getReference(),customerId};
		Object[] scParams={customer.getPassword(),customerId};
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql1, staffparams);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql2, scParams);
		try {
			response.sendRedirect((String)session.getAttribute("url"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}		
		//return personalInfor();
	}	
    public String addcpjsp()
  {	
		    	HttpSession session = ServletActionContext.getRequest().getSession();
			SessionWrap sw = SessionWrap.getInstance();
			TEshopCusCom scc= (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);		
			
			TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
			if(cus==null){
				return "login";
			}
			else{
				codeList = codeService.getCCodeListByPID(scc.getCompanyId(), "scode20150601pqmwffduns0000000002");		
			return "chanpingfabu";
			}   	    	  	
    }
    //ty 2015年7月10日 09:48:40 修改产品
    public String updatecpjsp(){
    	HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom scc= (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);		
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		if(cus==null){
			return "login";
		}
		String sql = "select staffName from dt_hr_Staff where staffID = ?";
		staffName = (String) baseBeanService.getObjectBySqlAndParams(sql, new Object[]{scc.getStaffid()});
		String hql = " from ProductPackaging where ppID=?";
		productPackaging = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ppID});
		String hql1 = " from GoodsManage where goodsID = ?";
		goodsManage = (GoodsManage) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{productPackaging.getGoodsID()});
		String hql2 = " from Profitshare where ppid=?";
		profitshare = (Profitshare) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ppID});
		codeList = codeService.getCCodeListByPID(scc.getCompanyId(), "scode20150601pqmwffduns0000000002");
		return "chanpinxiugai";
    }
    public String updateproduct(){
    	HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		if(cus==null){
			return "login";
		}
		String sql="update dt_ProductPackaging set weiDianType=?,ppstatus=?,price=?,money=? where ppID = ?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{sql}, new Object[]{productPackaging.getWeiDianType(),productPackaging.getPpstatus(),
				productPackaging.getPrice(),productPackaging.getMoney(),productPackaging.getPpID()});
		return "success";
    }
	//微信个人产品的删除 跟取消代理 chenpID 进行删除  //支持多选删除
   public String delchenp ()
   {     
		HttpServletRequest request=ServletActionContext.getRequest();			
		String chenpID=request.getParameter("chenpID");	
		String type=request.getParameter("type");
		Object[] params=chenpID.split(",");	
		String hql="delete   ProductPackaging  d where d.ppID=?";
	    String hql1="delete   ProductPriceCategory d where d.ppID=?";
	    String hql2="delete   Profitshare d where d.ppid=?";
	    
	   if(params.length>1)
	   {		   
		   
		   if(type!=""&&!"".equals(type)&&type.equals("1"))
		   {
		   	for(int i=0;i<params.length;i++){
		   		Object[] params1={params[i].toString()};		   			 
		           baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql,hql1,hql2}, params1);
		        }
		   }
		   else
		   {
			   String diyi="delete TEshopDaicp d where d.cpid=?";
				 for(int i=0;i<params.length;i++){
			   		Object[] params1={params[i].toString()};		   			 
			           baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{diyi}, params1);
			        }
		   }
	   }
	   else{
		   if(type!=""&&!"".equals(type)&&type.equals("1"))
		   {		   
		   baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql,hql1,hql2}, params);		
		   }		   
		   else{   
			   String diyi="delete TEshopDaicp d where d.cpid=?";
					baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{diyi}, params);			 
		   }
	   }
	return "success";	
   }
   //新产品代理页面取消代理 ty
   public String delDaiLi ()
   {     
	    HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom scc= (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);		
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		if(cus==null){
			return "login";
		}
		HttpServletRequest request=ServletActionContext.getRequest();			
		String chenpID=request.getParameter("chenpID");	
		Object[] params = {chenpID,scc.getStaffid() };
	    String diyi="delete TEshopDaicp d where d.cpppID=? and d.cpuserID=? ";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{diyi}, params);			 
	return "success";	
   }
   //修改选择产品的下家功能  判断 要是这个产品已经下家了 。 在点击的时候就会 改成上架操作//多选下架 多选回复  注意。
   //
   public String toxia()
   {   
	   HttpServletRequest request=ServletActionContext.getRequest();
		String chenpID=request.getParameter("chenpID");
		//判断他点击的是哪个点击下架传过来一个0 点击恢复传过来一个1
		String type=request.getParameter("type");
		Object[] params=chenpID.split(",");
		//下面是判断产品的状态。0为下架1为上架
		if(type.equals("0"))
		{
			for(int i=0;i<params.length;i++){
	   		Object[] params1={params[i].toString()};		   			 
	   		String hql=" update  ProductPackaging   set shangjiastatus=0 where ppID=?";
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, params1);
	        }			
		}
		else 
		{
			for(int i=0;i<params.length;i++){
		   		Object[] params1={params[i].toString()};		   			 
		   		String hql=" update  ProductPackaging   set shangjiastatus=1 where ppID=?";
				baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, params1);
		        }	
		}
	return "success";   
   }
	//微信上的个人产品发布
	public String add()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		//获取添加物品人的资料
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);			
		TEshopCusCom scc= (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);			
		if (cus == null) {		
			return "login";
		}
		//添加这个页面的时候查询这个人的公司然后查询物品类型
	//	proType = codeService.getCCodeListByPID(staff.getGroupCompanySn(),"scode20150601pqmwffduns0000000002");	
		String customerId = cus.getStaffid();//获取消费者id
		String hql1="from TEshopCustomer where  staffid = ? ";
		customer=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hql1,new Object[]{customerId});
		String hql2="from Staff where staffID=?";
		staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{customerId});
		String hql3="from StaffAddress where staffid=? and isDefault='是'";
		staffAddress=(StaffAddress) baseBeanService.getBeanByHqlAndParams(hql3, new Object[]{customerId});
		//物品获取下一个的ID
		goodsManage.setGoodsID(serverService.getServerID("goodsManage"));		
		//生出产品编号
		String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in (select c.companyID from Company c where c.groupCompanySn=?)  and vt.typeID=? ";
		Object[] params = {staff.getGroupCompanySn(),
				goodsManage.getTypeID() };
		int goodscodingnum = baseBeanService.getConutByByHqlAndParams(hql,
				params);
		// 获取拼音码
		String pinyin = ToChineseFirstLetter.getFirstLetter(goodsManage
				.getTypeID());
		String Upstr = pinyin.toUpperCase();// 转换为大写
		DecimalFormat form = new DecimalFormat("000000");
		String ss = form.format(goodscodingnum + 1);
		goodsManage.setGoodsCoding(Upstr + "_" + ss);
		goodsManage.setGoodsState("00");
		goodsManage.setCompanyID(scc.getCompanyId());
		if (goodsManage.getFilePhoto() != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path,
					goodsManage.getFilePhotoFileName(),
					goodsManage.getFilePhoto(), staff.getGroupCompanySn(),
					"upload_files/ea/chanp/wup");
			goodsManage.setPhotoPath(photoPath);
		}
		if (goodsManage.getFileLogo() != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String logoPath = fileService.savePhoto(path,
					goodsManage.getFileLogoFileName(),
					goodsManage.getFileLogo(), staff.getGroupCompanySn(),
					"upload_files/ea/chanp/wup");
			goodsManage.setLogoPath(logoPath);
		}
		//产品获取下一个的ID
		productPackaging.setPpID(serverService.getServerID("ProductPackaging"));
		productPackaging.setGoodsID(goodsManage.getGoodsID());
		productPackaging.setGoodsName(goodsManage.getGoodsName());
		productPackaging.setLogo(goodsManage.getLogoPath());
		productPackaging.setGoodsNum(goodsManage.getGoodsCoding());
		productPackaging.setPackagingDate(new Date());
		productPackaging.setImage(goodsManage.getPhotoPath());
		productPackaging.setCompanyID(goodsManage.getCompanyID());
	    productPackaging.setStaffID(scc.getStaffid());
		productPackaging.setOrganizationID(scc.getOrganizationID());
		productPackaging.setShowweixin("01");		
	    //价格获取下一个的ID
		productPriceCategory.setPcID(serverService.getServerID("ProductPriceCategory"));
		productPriceCategory.setPpID(productPackaging.getPpID());
		productPriceCategory.setMoney(productPackaging.getMoney());
		productPriceCategory.setPrice(productPackaging.getPrice());
		productPriceCategory.setCategory("零售价");		
		//公司获取下一个IDrR
		profitshare.setId(serverService.getServerID("Profitshare"));
		profitshare.setPpid(productPackaging.getPpID());
		//添加Base数据	
		baseBeanService.save(goodsManage);
		baseBeanService.save(productPackaging);
		baseBeanService.save(productPriceCategory);
		baseBeanService.save(profitshare);
		return "success";
	}	
	/**
	 * 查询收货地址
	 */
	public String getAddress(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		if(cus==null){
			return "login";
		}
		String customerId = cus.getStaffid();//获取消费者id		
		String hql="from StaffAddress where staffid=?";
		listAddress=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{customerId});
		return "address";
	}
	/**
	 * 修改和添加收货地址
	 * @return
	 */
	public String staffAddressEdit(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);		
		String customerId = cus.getStaffid();
		beans = new ArrayList<BaseBean>();
		if("".equals(staffAddress.getAddressID()) || "".equals(staffAddress.getStaffID())){
			staffAddress.setAddressID(serverService.getServerID("address"));
			staffAddress.setStaffID(customerId);
			staffAddress.setCompanyID("company201009046vxdyzy4wg0000000025");
			beans.add(staffAddress);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}else{
			String []addressDetaileds=staffAddress.getAddressDetailed().split(",");
			String []addressIds=staffAddress.getAddressID().split(",");
			for (int i = 0; i < addressIds.length; i++) {
				String[] hql={"update StaffAddress set addressDetailed=? where addressID=?"};
				baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, new Object[]{addressDetaileds[i],addressIds[i]});
			}
		}		
		return "success";
	}
	/**
	 * 修改默认地址
	 * @return
	 */
	public void defaultAddressEdit(){
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		String customerId = cus.getStaffid();
		beans = new ArrayList<BaseBean>();
		String[] hql1={"update StaffAddress set isDefault='否' where staffid=?"};
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql1, new Object[]{customerId});
		String[] hql2={"update StaffAddress set isDefault='是' where staffid=? and addressID=?"};
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql2, new Object[]{customerId,addressId});
		String url=(String) session.getAttribute("url");
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public List<BaseBean> getBeans() {
		return beans;
	}
	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}
	public TEshopCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(TEshopCustomer customer) {
		this.customer = customer;
	}

	public List<BaseBean> getListAddress() {
		return listAddress;
	}
	public void setListAddress(List<BaseBean> listAddress) {
		this.listAddress = listAddress;
	}
	public StaffAddress getStaffAddress() {
		return staffAddress;
	}
	public void setStaffAddress(StaffAddress staffAddress) {
		this.staffAddress = staffAddress;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
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
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getSearch() {
		return search;
	}
	

	public GoodsManage getGoodsManage() {
		return goodsManage;
	}
	public void setGoodsManage(GoodsManage goodsManage) {
		this.goodsManage = goodsManage;
	}
	
	public ProductPackaging getProductPackaging() {
		return productPackaging;
	}
	public void setProductPackaging(ProductPackaging productPackaging) {
		this.productPackaging = productPackaging;
	}
	public ProductPriceCategory getProductPriceCategory() {
		return productPriceCategory;
	}
	public void setProductPriceCategory(ProductPriceCategory productPriceCategory) {
		this.productPriceCategory = productPriceCategory;
	}
	public Profitshare getProfitshare() {
		return profitshare;
	}
	
	public List<CCode> getProType() {
		return proType;
	}
	public void setProType(List<CCode> proType) {
		this.proType = proType;
	}
	public void setProfitshare(Profitshare profitshare) {
		this.profitshare = profitshare;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public int getPagenull() {
		return pagenull;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public void setPagenull(int pagenull) {
		this.pagenull = pagenull;
	}
	public List<CCode> getCodeList() {
		return codeList;
	}
	public void setCodeList(List<CCode> codeList) {
		this.codeList = codeList;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public String getPpID() {
		return ppID;
	}
	public void setPpID(String ppID) {
		this.ppID = ppID;
	}
	public List<BaseBean> getNoBeans() {
		return noBeans;
	}
	public void setNoBeans(List<BaseBean> noBeans) {
		this.noBeans = noBeans;
	}
	public String getBdText() {
		return bdText;
	}
	public void setBdText(String bdText) {
		this.bdText = bdText;
	}
	public String getBdUrl() {
		return bdUrl;
	}
	public void setBdUrl(String bdUrl) {
		this.bdUrl = bdUrl;
	}
	public String getBdPic() {
		return bdPic;
	}
	public void setBdPic(String bdPic) {
		this.bdPic = bdPic;
	}
	

}