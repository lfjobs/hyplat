package hy.ea.invoicing.action.voucher;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.invoicing.voucher.DtInvCcbsgl;
import hy.ea.bo.invoicing.voucher.DtInvCcpbsgl;
import hy.ea.bo.invoicing.voucher.FiscalPeriod;
import hy.ea.service.CLogBookService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 资产负债表损益表内容设定
 * 
 * @author mz
 */
@Controller
@Scope("prototype")
public class DebtToAssetsAction{
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;

	@Resource
	private CLogBookService logBookService;
	

	private Utilities utilities;

	private PageForm pageForm;
	private int pageNumber;
	private String result;
	private String search;
	private FiscalPeriod  fiscalPeriod;
	private Map<String,DtInvCcbsgl> assetsdebtmap;
	private Map<String,DtInvCcpbsgl> assetsdebtpmap;
	private String parameter;
	private DtInvCcbsgl invCCbsgl;
	private DtInvCcpbsgl invCcpbsgl;
	private String tabPSymbol;

	
	
	
	/**
	 * 
	 * 
	 * 获取资产负债结构树
	 * @return
	 */
	public String getAssetsDebtTree() {
		String hql1 = "from DtInvCcbsgl where comId = ? and tabType = ? and tabPSymbol = ? order by tabSymbol";
//		String hql2 = "from DtInvCcpbsgl t where " +
//				"exists(select d.tabSymbol from DtInvCcbsgl d where d.comId = ? " +
//				"and d.tabPSymbol = ? and t.tabSymbol = d.tabSymbol)";
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if(account==null){
			return "nologin";
		}
		String tabType = invCCbsgl.getTabPSymbol().substring(0,1);
		if(invCCbsgl.getTabPSymbol().length()==1){
			invCCbsgl.setTabPSymbol("00");
		}
		
		List<BaseBean> bsgllist = baseBeanService.getListBeanByHqlAndParams(
				hql1, new Object[] { account.getCompanyID(),tabType,invCCbsgl.getTabPSymbol()});

		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bsgllist", bsgllist);


		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";

	}
	
	
	/**
	 * 
	 * 打开树时初始化树
	 * @return
	 */
	public String initiAssetsDebtTree() {
		String hql1 = "from DtInvCcbsgl where comId = ? and tabPSymbol = ? order by tabSymbol";

		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if(account==null){
			return "nologin";
		}
		List<BaseBean> bsgllist = baseBeanService.getListBeanByHqlAndParams(
				hql1, new Object[] { account.getCompanyID(),"00"});

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bsgllist", bsgllist);

		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";

	}
	
	/**
	 * 
	 * 
	 * 资产负债表总页面
	 * @return
	 */
	public String getTotalAssetsDebt(){
		
		return "total";
		
	}
	
	

	
	/**
	 * 
	 * 
	 * 获取报表
	 * @return
	 */
	public String getDebtAssetsList(){
   
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "assets";
		
	}
	
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(DtInvCcbsgl.class);
		dc.add(Restrictions.eq("comId", companyID));
		
        dc.addOrder(Order.asc("tabSymbol"));
        String tabType = tabPSymbol.substring(0,1);
        
        if(tabPSymbol.length()==1){
        	 dc.add(Restrictions.eq("tabPSymbol","00"));
        }else{
        	 dc.add(Restrictions.eq("tabPSymbol",tabPSymbol));
        }
        dc.add(Restrictions.eq("tabType", tabType));
		
		if (search != null && search.equals("search")) {
			invCCbsgl = (DtInvCcbsgl) session.get("tablesearch");
			 if(invCCbsgl.getTabSymbol()!=null&&!invCCbsgl.getTabSymbol().equals("")){
			 dc.add(Restrictions.like("tabSymbol",invCCbsgl.getTabSymbol().trim(), MatchMode.ANYWHERE));
			 }
			
		}
		return dc;
	}
	
	
	/**
	 * 
	 * 
	 * 查询
	 * @return
	 */
	public String toSearch(){
		ActionContext.getContext().getSession().put("tablesearch", invCCbsgl);
		return getDebtAssetsList();
	}
	
	/**
	 * 
	 * 
	 * 获取树的子内容
	 * @return
	 */
	public List<BaseBean> getdATree(String tabSymbol){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		String hql = "from DtInvCcbsgl where tabPSymbol = ? and comId = ?";
		List<BaseBean> invCcbsgllist = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{tabSymbol,account.getCompanyID()});
		 
		return  invCcbsgllist;
		
	}
	
	
	
	
	/**
	 * 
	 * 
	 * 获取报表
	 * @return
	 */
	public String getIncomeList(){

		pageForm= baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getListincome());
		return "income";
		
	}
	
	private DetachedCriteria getListincome(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(DtInvCcpbsgl.class);
		dc.add(Restrictions.eq("comId", companyID));
		dc.add(Restrictions.eq("tabSymbol",invCcpbsgl.getTabSymbol()));
        dc.addOrder(Order.asc("ccpId"));
       
		
		if (search != null && search.equals("search")) {
			invCcpbsgl = (DtInvCcpbsgl) session.get("tablesearch");
			 if(invCcpbsgl.getTabSymbol()!=null&&!invCcpbsgl.getTabSymbol().equals("")){
			 dc.add(Restrictions.like("tabSymbol",invCcpbsgl.getTabSymbol().trim(), MatchMode.ANYWHERE));
			 }
			
		}
		return dc;
	}
	
	
	/**
	 * 
	 * 
	 * 查询
	 * @return
	 */
	public String toSearchIncome(){
		ActionContext.getContext().getSession().put("tablesearch", invCcpbsgl);
		return getDebtAssetsList();
	}
	
	
	
	/**
	 * 
	 * 
	 * 保存资产负债内容设定以及更新
	 * @return
	 */
	public String   saveDebtAssets(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		
		List<BaseBean> baseBeanList = null;
		
		if (assetsdebtmap != null) {

			for (DtInvCcbsgl inv : assetsdebtmap.values()) {
				 baseBeanList = new ArrayList<BaseBean>();
				if (inv.getCcId() == null
						|| "".equals(inv.getCcId())) {
				    inv.setComId(account.getCompanyID());
					inv.setCcId(serverService
							.getServerID("ccId"));
					if(inv.getTabPSymbol().length()==1){
						inv.setTabPSymbol("00");
					}
					//String tabSymbol = getTabSymbolByCom(inv.getTabType(),inv.getComId(),inv.getTabPSymbol());
					
					
					inv.setTabSymbol(inv.getTabType()+inv.getTabSymbol());
					parameter = "新增资产负债表内容设定(报表代号:" + inv.getTabSymbol()+")";
					baseBeanList.add(inv);
				} else {
					String hql = "from DtInvCcbsgl where  ccId = ?";
					DtInvCcbsgl vb = (DtInvCcbsgl) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{inv.getCcId()});
					vb.setTabSymbol(inv.getTabType()+inv.getTabSymbol());
					vb.setBsAtion(inv.getBsAtion());
                    vb.setTabSCaption(inv.getTabSCaption());
                    vb.setCglAtion(inv.getCglAtion());
                    vb.setSequence(inv.getSequence());
					parameter = "修改资产负债表内容设定(报表代号:" + inv.getTabSymbol()+")";
					baseBeanList.add(vb);

				}
				
				
				CLogBook logbook = logBookService.saveCLogBook(organizationID,
						parameter, account);
				baseBeanList.add(logbook);
				baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
						null, null);
			}
			
		}
		return "success";
		
	}
	
	
	
	/**
	 * 
	 * 
	 *判断序号是否重复
	 * @return
	 */
	public String isRepeatBySeq() {
     System.out.println(3333333);
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String re = "suc";
	//	String hqla = "from DtInvCcbsgl where  sequence = ? and comId = ?";
	//	String hqlu = "from DtInvCcbsgl where  sequence = ? and comId = ? and ccId != ?";
		
		
		String hqlap = "from DtInvCcbsgl where  tabSymbol = ? and comId = ?";
		String hqlup = "from DtInvCcbsgl where  tabSymbol = ? and comId = ? and ccId != ?";
		
		String hqlac = "from DtInvCcbsgl where  cglAtion = ? and  comId = ? and ccId != ?";
		String actionid="";
		 List<String> listcg= new ArrayList<String>();
		if (assetsdebtmap != null) {
			System.out.println("ffffffffff");
			//List<String> list = new ArrayList<String>();
			List<String> listtp = new ArrayList<String>();
			List<BaseBean> listps = new ArrayList<BaseBean>();
		//	List<BaseBean> lists = new ArrayList<BaseBean>();
		//	Map<String, String> mps = new HashMap<String, String>();
			Map<String, String> mptps = new HashMap<String, String>();
			int cl = 0;
			for (DtInvCcbsgl inv : assetsdebtmap.values()) {
				if(inv.getTabSymbol()==null||inv.getTabSymbol().equals("")){
					re="blanktp";
					break;
				}
				
				if (listtp.contains(inv.getTabType()+inv.getTabSymbol())) {
					re = "failtp";
					break;
				} else {
					listps.add(inv);
					listtp.add(inv.getTabType()+inv.getTabSymbol());
					mptps.put(inv.getCcId(),inv.getTabType()+inv.getTabSymbol());
				}
				
				if(inv.getSequence()==null||inv.getSequence().equals("")){
					re="blank";
					break;
				}
				
				
				listcg.add(inv.getCcId());
				if(inv.getCglAtion()!=null&&inv.getCglAtion().equals("Y")){
					System.out.println(inv.getCglAtion());
					cl++;
					System.out.println(cl);
					actionid = inv.getCcId();
					if(cl>1){
						re="cl";
						break;
					}
				}
			 

		
				
				
//				if (list.contains(inv.getSequence())) {
//					re = "fail";
//					break;
//				} else {
//					lists.add(inv);
//					list.add(inv.getSequence());
//					mps.put(inv.getCcId(), inv.getSequence());
//				}
			}
			

			
			
			
			if (re.equals("suc")) {
				
				for (BaseBean b : listps) {
					DtInvCcbsgl inv = (DtInvCcbsgl) b;
					if (inv.getCcId() == null || "".equals(inv.getCcId())) {
						List<BaseBean> ylist = baseBeanService
								.getListBeanByHqlAndParams(hqlap,
										new Object[] { inv.getTabType()+inv.getTabSymbol(),
												account.getCompanyID() });
						if (ylist.size() != 0) {
							DtInvCcbsgl d = (DtInvCcbsgl) ylist.get(0);
							String sq = d.getTabSymbol();
							if (mptps.get(d.getCcId()) != null
									&& !mptps.get(d.getCcId()).equals(sq)) {

							} else {
								re = "failtp";
								break;
							}
						}
					} else {
						List<BaseBean> ulist = baseBeanService
								.getListBeanByHqlAndParams(
										hqlup,
										new Object[] { inv.getTabType()+inv.getTabSymbol(),
												account.getCompanyID(),
												inv.getCcId() });
						if (ulist.size() != 0) {
							DtInvCcbsgl d = (DtInvCcbsgl) ulist.get(0);
							String sq = d.getTabSymbol();
							if (mptps.get(d.getCcId()) != null
									&& !mptps.get(d.getCcId()).equals(sq)) {

							} else {
								re = "failtp";
								break;
							}
						}
					}
				}
				
				
				
				//暂时不需要判断序号了
//				for (BaseBean b : lists) {
//					DtInvCcbsgl inv = (DtInvCcbsgl) b;
//					if (inv.getCcId() == null || "".equals(inv.getCcId())) {
//						List<BaseBean> ylist = baseBeanService
//								.getListBeanByHqlAndParams(hqla,
//										new Object[] { inv.getSequence(),
//												account.getCompanyID() });
//						if (ylist.size() != 0) {
//							DtInvCcbsgl d = (DtInvCcbsgl) ylist.get(0);
//							String sq = d.getSequence();
//							if (mps.get(d.getCcId()) != null
//									&& !mps.get(d.getCcId()).equals(sq)) {
//
//							} else {
//								re = "fail";
//								break;
//							}
//						}
//					} else {
//						List<BaseBean> ulist = baseBeanService
//								.getListBeanByHqlAndParams(
//										hqlu,
//										new Object[] { inv.getSequence(),
//												account.getCompanyID(),
//												inv.getCcId() });
//						if (ulist.size() != 0) {
//							DtInvCcbsgl d = (DtInvCcbsgl) ulist.get(0);
//							String sq = d.getSequence();
//							if (mps.get(d.getCcId()) != null
//									&& !mps.get(d.getCcId()).equals(sq)) {
//
//							} else {
//								re = "fail";
//								break;
//							}
//						}
//					}
//				}
			}
			
			//判断数据库是否有
			if(cl==1){
				List<BaseBean> ulist = baseBeanService
						.getListBeanByHqlAndParams(
								hqlac,
								new Object[] {"Y",
										account.getCompanyID(),
										actionid });
				if(ulist.size()!=0){
					DtInvCcbsgl d = (DtInvCcbsgl) ulist.get(0);
					if(!listcg.contains(d.getCcId())){
						re="cl";
					}
					

					
				}
			}

		}

		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(re);
		map.put("result", re);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * 保存资产负债计算设定
	 * @return
	 */
	 public String saveIncomestate(){
		 ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			
			List<BaseBean>  baseBeanList = new ArrayList<BaseBean>();
			
			if (assetsdebtpmap != null) {
				for (DtInvCcpbsgl inv : assetsdebtpmap.values()) {
					
					if (inv.getCcpId() == null
							|| "".equals(inv.getCcpId())) {
					    inv.setComId(account.getCompanyID());
						inv.setCcpId(serverService
								.getServerID("ccpId"));
						
						parameter = "新增资产负债表计算设定(报表ID:" + inv.getCcpId()+")";
						baseBeanList.add(inv);
					} else {
						String hql = "from DtInvCcpbsgl where  ccpId = ?";
						DtInvCcpbsgl vb = (DtInvCcpbsgl) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{inv.getCcpId()});
                        vb.setStContents(inv.getStContents());
                        vb.setPlaType(inv.getPlaType());
                        vb.setPlaMode(inv.getPlaMode());
						parameter = "修改资产负债表计算设定(报表ID:" + inv.getCcpId()+")";
						baseBeanList.add(vb);

					}
					
					
					CLogBook logbook = logBookService.saveCLogBook(organizationID,
							parameter, account);
					baseBeanList.add(logbook);
					
				}
				
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
					null, null);
			return "success";
	 }
	
	
	/**
	 * 
	 * 按公司生成报表代号
	 * tabType :报表类别 A  B生成不同的报表代号：e.g. A01 A0101 A010101 B01 B0101 B0202
	 * @return
	 */
	public String getTabSymbolByCom(String tabType,String companyID,String tabPSymbol){

	   String hql = "from DtInvCcbsgl where tabType = ? and comId = ? and tabPSymbol = ? order by tabSymbol desc";
	   List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{tabType,companyID,tabPSymbol.trim()});
	   String tabsymbol = "";
	   if(tabPSymbol.trim().equals("00")){//说明是一级
		   if(list.size()==0){
		   //第一个报表
		     tabsymbol = tabType+"01";
		   }else{
			   //第二个以后。。。
			   tabsymbol = getMaxSymbol(list,tabType);
		   }
	   }else{
		   //2级以上
		   if(list.size()==0){
			   //说明是这一级的第一个
			   tabsymbol = tabPSymbol+"01";
		   }else{
			   tabsymbol = getMaxSymbol(list,tabType);
		   }
		   
	   }
	   
		
		return tabsymbol;
	}
	
	/**
	 * 
	 * 当不是第一个
	 */
	public String getMaxSymbol(List<BaseBean> list,String tabType){
		String tabsymbol = "";
		//这一级的第二个之后
		   String maxsymbol = ((DtInvCcbsgl) list.get(0)).getTabSymbol();
		   String num = maxsymbol.substring(1);
		    int len = num.length();
		     //说明是一级的报表
		     int maxnum =  Integer.parseInt(num)+1;
		     if(String.valueOf(maxnum).length()<len){
		        tabsymbol = tabType+"0"+maxnum;
		     }else{
		    	 tabsymbol = tabType+maxnum;
		     } 
		     
		     
		     return tabsymbol;
	}

	
	
	
	

	
	/**
	 * 
	 * 删除资产负债内容设定
	 * @return
	 */
	public String delAssetsDebt(){
		try{
		//先删除 DtInvCcbsgl的子对象，然后删除其附表，再删除自己，以及附表
		String hql1 = "delete from DtInvCcpbsgl p where exists(select c.tabSymbol  from DtInvCcbsgl c where (c.tabPSymbol = ? or c.tabSymbol = ?) and c.tabSymbol= p.tabSymbol)";
		String hql2 = "delete from DtInvCcbsgl where tabPSymbol = ? or tabSymbol = ?";
		String[] hqls = {hql1, hql2};
		Object[] params = {invCCbsgl.getTabSymbol(),invCCbsgl.getTabSymbol()};

		List<Object[]> paramsList = new ArrayList<Object[]>();
		paramsList.add(params);
		paramsList.add(params);
		
		baseBeanService.executeHqlsByParamsList(null, hqls, paramsList);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return "success";
	}
	
	
	
	/**
	 * 
	 * 删除资产负债计算设定
	 * @return
	 */
	public String delIncomeState(){
		try{
		String hql = "from  DtInvCcpbsgl where ccpId = ?";
		DtInvCcpbsgl fs = (DtInvCcpbsgl) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{invCcpbsgl.getCcpId()});
		baseBeanService.deleteBeanByKey(DtInvCcpbsgl.class, fs.getCcpKey());
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 
	 * 
	 * 判断添加是否合理 损益类只能存在一笔C和一笔D其他均为E
	 * @return
	 */
	public String isRight(){
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		String hql = "from DtInvCcbsgl where bsAtion = ? and comId = ?";
		List<BaseBean> listC = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{"C",account.getCompanyID()});
		List<BaseBean> listD = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{"D",account.getCompanyID()});
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("listC",listC);
		map.put("listD", listD);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		
		return "success";
		
	}
	
	/**
	 * 
	 * 获取当前公司的所有的报表代号 作废
	 * @return
	 */
	public String getAllTabSymbol(){

		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		String sql = "select tab_Symbol from DT_INV_CCBSGL where COM_ID = ? order by tab_symbol";
		List<String>  list = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{account.getCompanyID()});
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("symbollist",list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
				
		return "success";
		
	}
	
	/**
	 *  
	 * 
	 * @return
	 */
	public String isExistsTabSymbol(){

		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		String hql = "from DtInvCcbsgl where tabSymbol = ? and comId = ?";

	    DtInvCcbsgl ic =  (DtInvCcbsgl) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{invCcpbsgl.getTabSymbol(),account.getCompanyID()});
		Map<String,Object> map = new HashMap<String, Object>();
		if(ic!=null){
			map.put("result","success");
		}else{
			map.put("result","fail");
		}
		
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
				
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

	public FiscalPeriod getFiscalPeriod() {
		return fiscalPeriod;
	}

	public void setFiscalPeriod(FiscalPeriod fiscalPeriod) {
		this.fiscalPeriod = fiscalPeriod;
	}

	
	public Map<String, DtInvCcbsgl> getAssetsdebtmap() {
		return assetsdebtmap;
	}

	public void setAssetsdebtmap(Map<String, DtInvCcbsgl> assetsdebtmap) {
		this.assetsdebtmap = assetsdebtmap;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Utilities getUtilities() {
		return utilities;
	}

	public void setUtilities(Utilities utilities) {
		this.utilities = utilities;
	}




	public DtInvCcbsgl getInvCCbsgl() {
		return invCCbsgl;
	}




	public void setInvCCbsgl(DtInvCcbsgl invCCbsgl) {
		this.invCCbsgl = invCCbsgl;
	}




	public DtInvCcpbsgl getInvCcpbsgl() {
		return invCcpbsgl;
	}




	public void setInvCcpbsgl(DtInvCcpbsgl invCcpbsgl) {
		this.invCcpbsgl = invCcpbsgl;
	}


	public String getTabPSymbol() {
		return tabPSymbol;
	}


	public void setTabPSymbol(String tabPSymbol) {
		this.tabPSymbol = tabPSymbol;
	}


	public Map<String, DtInvCcpbsgl> getAssetsdebtpmap() {
		return assetsdebtpmap;
	}


	public void setAssetsdebtpmap(Map<String, DtInvCcpbsgl> assetsdebtpmap) {
		this.assetsdebtpmap = assetsdebtpmap;
	}
	
	
}
