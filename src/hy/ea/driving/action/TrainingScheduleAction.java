package hy.ea.driving.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.driving.DtDrivingPrincipal;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.ProductPriceCategory;
import hy.ea.bo.finance.TPrdcodeRel;
import hy.ea.bo.finance.TPrdcodeRelId;
import hy.ea.bo.human.CustomersForms;
import hy.ea.bo.human.Staff;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.UpLoadFileService;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
/**
 * 	产品包装设计
 */
public class TrainingScheduleAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	@Resource
	private UploadContentToFileService contentToFileService;
	
	private ProductPriceCategory productPriceCategory;
	private String parameter;
	private ProductPackaging productDesign;
	private ProductPackaging productpeijian;
	private PageForm pageForm;
	private PageForm pageFormpeijian;
	private InputStream excelStream;
	private String result;
	private String search;
	private List<BaseBean> staffList;
	private int pageNumber;
	private List<BaseBean> beans;
	private List<BaseBean> productPackagingList;
	private List<BaseBean> productPriceCategoryList;
	private String sdate;
	private String edate;
	private File image;
	private String imageFileName;
	/**
	 * 物品类别
	 */
	private List<CCode> codeList;
	private List<CCode> priceManageList;//价格类别
	private String printDate; //打印时间
	private List<BaseBean> printList;//打印列表
	private Object obj;//打印公司 部门
	private String printname;//责任人
	private GoodsManage   goodsManage;
	
	public String title;
	private String showType;
	private String priceType;
	private CustomersForms customersForms;
	private String produce;
	
	/**
	 * 产品在线编辑内容
	 */
	private String htmlContent;
	private String actionName;
	
	/**
	 * 产品分类
	 */
	private String[] prdCategory;
	
	private String billID;
	private Staff staff;
	private Map<String, ProductPackaging> productPackagingMap;
	private Map<String, ProductPriceCategory> productPriceCategoryMap;
	
	private CCode ccode;
	
	private DtDrivingPrincipal dtDrivingPrincipal;
	
	private Staff cstaff;
	/**
	 * 打印预览
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public String toProPrint(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = (List<Object>)session.get("sqllist");
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		printList = new ArrayList<BaseBean>();
		printList = baseBeanService.getListBeanBySqlAndParams(sql, parms);
		obj = new Object();
		obj = printList.get(0);
		printname = account.getAccountName();
		printDate = new Date().toLocaleString().substring(0, 10);
		return "toProPrint";
	} 
	
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", productDesign);
		return getListProductdesign();
	}

	private List<Object> getList() {
		List<Object> result = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> parms = new ArrayList<Object>();
		String sql="select pp.ppid,cp.companyname,co.organizationname,hs.staffname, pp.packagingdate,gm.goodscoding,pp.goodsname," +
				" gm.variableID,gm.acquiescestandard,pp.quantity,pp.weight,pp.manualurl,pp.propagandaurl,pp.fileurl,gm.goodsid,gm.typeid" +
				// 在goodsid 之后, p.category, p.price,p.money,
				" from dt_productpackaging pp" +
				" left join dtcompany cp on cp.companyid=pp.companyid" +
				" left join dtcorganization co on co.organizationid=pp.organizationid" +
				" left join dt_hr_staff hs on hs.staffid=pp.staffid" +
				" left join dtgoodsmanage gm on gm.goodsid=pp.goodsid " ;
				//+" left join dt_productPriceCategory p on p.ppid=pp.ppid ";
		 
		sql+=" where pp.companyid=?";
		parms.add(account.getCompanyID());
		sql+=" and pp.organizationid=?";
		parms.add(organizationID);
		if (search != null && search.equals("search")) {
			productDesign = (ProductPackaging) session.get("tablesearch");
			if(productDesign.getGoodsName()!=null&&!productDesign.getGoodsName().equals("")){
				sql+=" and pp.goodsname like ?";
				parms.add("%"+productDesign.getGoodsName()+"%");
			}
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				sql += " and pp.packagingdate between ? and ? ";

				parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
						"yyyy-MM-dd hh:mm:ss"));

				parms.add(Utilities.getDateFromString(edate + " 23:59:59",
						"yyyy-MM-dd hh:mm:ss"));
			}
		}
		//sql+=" and p.category='零售价'";
		sql+=" and pp.parentId is null ";
		sql+=" order by pp.packagingdate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
}

	public String getListProductdesign(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList();
		session.put("sqllist", list);
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		codeList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20101014v5zed7cukk0000000002");
		//单价管理
		priceManageList = codeService.getCCodeListByPID(account.getCompanyID(),
		"scode20101101dfs3uhdprp0000000032");
	   pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
			.getPageNumber() : 1), (pageNumber==0?15:pageNumber), sql, "select count(1) "
			+ sql.substring(sql.indexOf("from")), parms);		 
		return "productDesign";
	}
	/**
	 * 进入带有菜单数树的产品包装设计推广页面
	 * @return
	 */
	public String getFilePackageProduct(){
		if(null !=produce && produce.equals("Company")){
			return "cList";
		}
		if(null !=produce && produce.equals("group")){
			return "gList";
		}
		return "list";
	}
/*	public String showExcel() {
		excelStream = excelService.showExcel(Productdesign.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		CLogBook logBook = logBookService.saveCLogBook(organizationID,"导出产品包装设计", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}*/

	
	public String addProductdesign() {
		
		if ("saveContentToFile".equals(getActionName())){
			return saveContentToFile();
		}
		
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		HttpServletRequest request = ServletActionContext.getRequest();
		beans = new ArrayList<BaseBean>();
		String ppId=serverService.getServerID("productpackaging");
		
		if (productDesign.getPpID() == null
				|| "".equals(productDesign.getPpID())) {			
			productDesign.setPpID(ppId);
			parameter = "添加产品包装设计(商品名称:"+productDesign.getGoodsName()+")";			
			productDesign.setOrganizationID(organizationID);
			productDesign.setCompanyID(account.getCompanyID());
			productDesign.setStaffID(account.getStaffID());				
			//保存图片
			String hidIdList = request.getParameter("hidIdList");
			String[] imageArray = hidIdList.split(",");
			if (imageArray.length >0) {
				productDesign.setImage(imageArray[0]);				
			}
			productDesign.setPackagingDate(new Date());
			beans.add(productDesign);
			
			//添加产品类别
			if (prdCategory!=null && prdCategory.length>0){				
				for (String item:prdCategory){
					TPrdcodeRelId cid = new TPrdcodeRelId(ppId,item);
					TPrdcodeRel tpr = new TPrdcodeRel(cid);
					beans.add(tpr);
				}				
			}
			//结束
		}
		else
		{
			 String hql2="from ProductPackaging where companyID=?  and ppID=?";
			 ProductPackaging aff=(ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ account.getCompanyID(),productDesign.getPpID() });
			 productDesign.setPpKey(aff.getPpKey());
			 parameter = "修改产品包装设计(商品名称:"+aff.getGoodsName()+")";
			
			//修改
			 String hidIdList = request.getParameter("hidIdList");
			 String[] imageArray = hidIdList.split(",");
			 if (imageArray[0] !="") {
				String[] hql22 ={"update ProductPackaging set money=?,quantity=?,image=?,showweixin=?,weiDianType=?,certificateCost=?  where  companyID=?  and ppID=?"};
				Object[] paramss={productDesign.getMoney(),productDesign.getQuantity(),imageArray[0],productDesign.getShowweixin(),productDesign.getWeiDianType(),productDesign.getCertificateCost(),account.getCompanyID(),productDesign.getPpID()};
				baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql22, paramss);
			 }else{
				 String[] hql22={"update ProductPackaging set money=?,quantity=?,showweixin=?,weiDianType=?,certificateCost=? where  companyID=?  and ppID=?"};
				 Object[] paramss={productDesign.getMoney(),productDesign.getQuantity(),productDesign.getShowweixin(),productDesign.getWeiDianType(),productDesign.getCertificateCost(),account.getCompanyID(),productDesign.getPpID()};
				 baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql22, paramss);
			 }
			 
			 Object[] params = { productDesign.getPpID()};
			 String[] hql3 = {"delete from ProductPriceCategory where  ppID=?"};
			 baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql3, params );
			 String[] hql4={"delete  from ProductPackaging where parentId=?"};
			 baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql4, params );
		}
		
		//保存配件
		if(productpeijian!=null && productDesign.getGoodsID().length()>0){
		String[] pgoodsID=productpeijian.getGoodsID().split(",");
		String[] pname=productpeijian.getGoodsName().split(",");
		String[] pweight=productpeijian.getWeight().split(",");
		String[] pquantity=productpeijian.getQuantity().split(",");
		String[] pprice=productpeijian.getPrice().split(",");
		String[] pmoney=productpeijian.getMoney().split(",");
			ProductPackaging products=null;
			for (int i = 0; i < pquantity.length; i++) {
				products=new ProductPackaging();
				products.setOrganizationID(organizationID);
				products.setCompanyID(account.getCompanyID());
				products.setStaffID(account.getStaffID());	
				products.setParentId(productDesign.getPpID());
				products.setGoodsID(pgoodsID[i].trim());
				products.setGoodsName(pname[i].trim());
				products.setWeight(pweight[i].trim());
				products.setQuantity(pquantity[i].trim());
				products.setPrice(pprice[i].trim());
				products.setMoney(pmoney[i].trim());
				products.setPackagingDate(new Date());
				beans.add(products);
			}
		}
		
		//保存价格类型
		String [] category=productPriceCategory.getCategory().split(",");
		String [] price=productPriceCategory.getPrice().split(",");
		String [] money=productPriceCategory.getMoney().split(",");
		ProductPriceCategory priceCategory=null;
			for (int i = 0; i < price.length; i++) {
				priceCategory = new ProductPriceCategory();
				priceCategory.setPcID(serverService.getServerID("productPriceCategory"));
				priceCategory.setCategory(category[i].trim());
				priceCategory.setPrice(price[i].trim());
				priceCategory.setMoney(money[i].trim());
				priceCategory.setPpID(productDesign.getPpID());
				
				beans.add(priceCategory);
			}
	
		CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		if("title".equals(title)){
			showType="edit";
			return getProductdesignAddorEdit();
		}
		return "success";
	}

	public String delProductdesign() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), productDesign.getPpID()};
	    String hql2="from ProductPackaging where companyID=?  and ppID=?";
	    ProductPackaging aff=(ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql2, params);
	    beans = new ArrayList<BaseBean>();
	    CLogBook logBook = logBookService.saveCLogBook(organizationID, "删除商品包装设计(商品名称:"+aff.getGoodsName()+")", account);
	    beans.add(logBook);
	    String[] hql4={"delete  from ProductPackaging where parentId=?"};
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql4,  new Object[] {productDesign.getPpID()} );
	    String[] hql = {"delete from ProductPackaging where companyID=?  and ppID=?"};
	    baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
	    String[] hql3 = {"delete from ProductPriceCategory where  ppID=?"};
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql3, new Object[] {productDesign.getPpID()} );
		//删除产品分类与产品对应关系(t_prdcode_rel)
		String[] hql5 = {"delete from t_prdcode_rel where ppid=?"};
		List<Object[]> parmsList = new ArrayList<Object[]>();
		Object[] objArray = new Object[] {productDesign.getPpID()};
		parmsList.add(objArray);
		baseBeanService.executeSqlsByParmsList(null, hql5, parmsList);
		
		return "success";	
	}
	
	public String getProductdesignAddorEdit() {
		
		if ("getContentFromFile".equals(getActionName())){
			return getContentFromFile();
		}
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		customersForms=(CustomersForms)baseBeanService.getBeanByHqlAndParams(" from CustomersForms where companyID=? ", new Object[]{account.getCompanyID()});
	    if("edit".equals(showType)){
	    	String hql2="from ProductPackaging where companyID=?  and ppID=?";
	    	 Object[] params = { account.getCompanyID(), productDesign.getPpID()};
	    	 productDesign=(ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql2, params);
	    	 
	    	 String hqlgood="from GoodsManage where goodsID=?";
	    	 Object[] paramsgood = { productDesign.getGoodsID()};
	    	 goodsManage=(GoodsManage)baseBeanService.getBeanByHqlAndParams(hqlgood, paramsgood);
	    	 String sqlCategory="select pg.ppid,pg.category,pg.price,pg.money from dt_productPriceCategory pg where pg.ppID=?";
	    	 Object [] paramCategory={ productDesign.getPpID()};
	    	 priceManageList = codeService.getCCodeListByPID(account.getCompanyID(),"scode20101101dfs3uhdprp0000000032");
	    	 pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
	    				.getPageNumber() : 1), (pageNumber==0?15:pageNumber), sqlCategory, "select count(1) "
	    				+ sqlCategory.substring(sqlCategory.indexOf("from")), paramCategory);
	    	//配件
	    	 String sqlpeijian="select g.goodsid, g.goodscoding,g.goodsname,g.variableid,g.standard,g.typeid,p.weight,p.quantity,p.price,p.money " +
	    	 		" from dt_productpackaging p ,dtgoodsmanage g where p.goodsid=g.goodsid and p.parentid=?";
	    	 pageFormpeijian=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
	    				.getPageNumber() : 1), (pageNumber==0?15:pageNumber), sqlpeijian, "select count(1) "
	    				+ sqlpeijian.substring(sqlpeijian.indexOf("from")), paramCategory);
	    }
	    codeList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20101014v5zed7cukk0000000002");
	    if("type".equals(priceType)){
	    	return "productdesignEdit";
	    }
		return "productdesignAddorEdit";
	}
	
	/**
	 * 保存客户分类报表菜单启用项
	 * @return
	 */
	public String saveHumanCollect(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?",
				new Object[] { account.getStaffID()});
		if(customersForms.getCustomersFormsid()== null 
				|| "".equals(customersForms.getCustomersFormsid())){
			customersForms.setCustomersFormsid(serverService.getServerID("customersForms"));
			parameter = staff.getStaffName() + "添加了客户分类报表菜单启用项";
		}else{
			parameter = staff.getStaffName() + "修改了客户分类报表菜单启用项";
		}
		customersForms.setCompanyid(account.getCompanyID());
		beans = new ArrayList<BaseBean>();
		beans.add(customersForms);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	
	/**
	 * 保存在线编辑的产品说明内容到磁盘物理文件。
	 * 数据库的PRODUCTDETAIL字段只记录存放的物理文件位置（相对位置）
	 * @return
	 */
	private String saveContentToFile(){
		String path = ServletActionContext.getServletContext().getRealPath("") + "/upload_files/productDetail";
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GBK");			
		
		try {
			contentToFileService.saveContent(productDesign.getPpID(), getHtmlContent(), path);
			response.getWriter().print("success");
			
			updateProductDetail("upload_files\\\\productDetail\\\\"  + productDesign.getPpID() + UploadContentToFileService.suffix);
			
			return null;
		} catch (IOException e) {			
			e.printStackTrace();			
			return null;
		}		
	}
	/**
	 * 将文件相对路径保存到productdetail字段
	 * @param path
	 */
	private void updateProductDetail(String path)
	{		
		String[] sqlArray = {"update dt_productpackaging dp set dp.productdetail=? where dp.ppid=?"};	
		List<Object[]> list = new ArrayList<Object[]>();
		Object[] objArray = {path,productDesign.getPpID()};
		list.add(objArray);
		baseBeanService.executeSqlsByParmsList(null, sqlArray, list);
	}
	
	/**
	 * 读文件内容，由ajax脚本付给UE控件
	 * @return
	 */
	private String getContentFromFile()
	{
		String path = ServletActionContext.getServletContext().getRealPath("\\") + productDesign.getProductDetail();	
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");	
		try {
			response.getWriter().print(contentToFileService.getContent(path));
			return null;
		} catch (IOException e) {			
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * @author zc
	 * @time 2015-02-02
	 * @describe 产品设计新增与修改
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toAddOrEditProductDesign(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
	
		String hqlddp=" from DtDrivingPrincipal where companyid=? and studentid=?";
		dtDrivingPrincipal=(DtDrivingPrincipal)baseBeanService.getBeanByHqlAndParams(hqlddp, new Object[]{companyID,staff.getStaffID()});
		
		cstaff=(Staff)baseBeanService.getBeanByHqlAndParams(" from Staff where staffID=? ", new Object[]{staff.getStaffID()});
    	 try {
			String hql="from ProductPackaging where companyID=?  and goodsID=?";
			 Object[] params = { account.getCompanyID(), dtDrivingPrincipal.getRegistrationcarid()};
			 productDesign=(ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql, params);
		} catch (Exception e) {
			ActionContext.getContext().put("message", "产品设计重复数据！！请联系管理员");
			return "fail";
		}
    	 
    	  
    	 try {
			 StringBuffer sqljindu=new StringBuffer();
			 sqljindu.append("  select dpp.goodsid,dpp.goodsnum ,dpp.goodsname,dpp.quantity ,vpc.time,dpp.quantity-vpc.time,dpp.ppID,dpp.parentId ");
			 sqljindu.append("  from dt_ProductPackaging dpp ");
			 sqljindu.append("  left join view_progress_css vpc  ");
			 sqljindu.append("  on    dpp.companyid=vpc.companyid  ");
			 sqljindu.append("  and   dpp.goodsname=vpc.subject ");
			 sqljindu.append("  and   vpc.ddsrstudentid=? ");
			 sqljindu.append("  where  dpp.companyid=? ");
			 sqljindu.append("  and dpp.parentid=? ");
			  sqljindu.append("  order by NLSSORT(dpp.goodsname,'NLS_SORT = SCHINESE_STROKE_M') ");
			 Object[] paramspeijian = {cstaff.getStaffID(),account.getCompanyID(), productDesign.getPpID()};
			 productPackagingList=baseBeanService.getListBeanBySqlAndParams(sqljindu.toString(), paramspeijian);
		} catch (Exception e) {
			e.printStackTrace();
			ActionContext.getContext().put("message", "学员绑定的产品不存在于产品设计物品中！！请联系管理员");
			return "fail";
		}

		session.put("session_value", Math.random() + "");
		
		return "trainingSchedule";
	}
	
	
	public String addOrEditProductDesign() {
		
		if ("saveContentToFile".equals(getActionName())){
			return saveContentToFile();
		}
		
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		HttpServletRequest request = ServletActionContext.getRequest();
		beans = new ArrayList<BaseBean>();
		
		if (productDesign.getPpKey() == null|| "".equals(productDesign.getPpKey())) {			
			parameter = "添加产品包装设计(商品名称:"+productDesign.getGoodsName()+")";			
		}
		else
		{
			 String hql2="from ProductPackaging where companyID=?  and ppID=?";
			 ProductPackaging aff=(ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ account.getCompanyID(),productDesign.getPpID() });
			 productDesign.setPpKey(aff.getPpKey());
			 parameter = "修改产品包装设计(商品名称:"+aff.getGoodsName()+")";
		}
		
		productDesign.setOrganizationID(organizationID);
		productDesign.setCompanyID(account.getCompanyID());
		productDesign.setStaffID(account.getStaffID());				
		//保存图片列表
		String hidIdList = request.getParameter("hidIdList");
		if(hidIdList!=null&&!"".equals(hidIdList)){
			String[] imageArray = hidIdList.split(",");
			if(imageArray.length >0){
				productDesign.setImage(imageArray[imageArray.length-1]);	
			}
		}
		productDesign.setPackagingDate(new Date());
		beans.add(productDesign); 
		
		//之前所属删除产品类别数据
		String hqltrp="delete from TPrdcodeRel where  id.ppid=?";
    	Object[] paramstrp = {productDesign.getPpID()};
		
		//保存产品类别
		if (prdCategory!=null && prdCategory.length>0){				
			for (String item:prdCategory){
				TPrdcodeRelId cid = new TPrdcodeRelId(productDesign.getPpID(),item);
				TPrdcodeRel tpr = new TPrdcodeRel(cid);
				beans.add(tpr);
			}				
		}
		
		//保存配件列表
		if(productPackagingMap!=null){
			for (ProductPackaging pp : productPackagingMap.values()) {
				pp.setOrganizationID(organizationID);
				pp.setCompanyID(account.getCompanyID());
				pp.setStaffID(account.getStaffID());	
				pp.setPackagingDate(new Date());
				beans.add(pp);
			}
		}
		
		//保存价格类型
		if(productPriceCategoryMap!=null){
			for (ProductPriceCategory ppc : productPriceCategoryMap.values()) {
				if(ppc.getPcID()==null||"".equals(ppc.getPcID())){
					ppc.setPcID(serverService.getServerID("productPriceCategory"));
				}
				ppc.setPpID(productDesign.getPpID());
				beans.add(ppc);
			}
		}	
	
		CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hqltrp}, paramstrp);
		return "success";
	}
	/**
	 * @time 2015-02-04
	 * @describe ajax根据父ID获得子物品
	 * @return ID
	 */
	public String getProductPackagingListByPID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<BaseBean> ProductPackagingList=null;
		if(productDesign!=null&&productDesign.getPpID()!=null&&!"".equals(productDesign.getPpID())){
			
			 StringBuffer sqljindu=new StringBuffer();
			 sqljindu.append("  select dpp.goodsid,dpp.goodsnum ,dpp.goodsname,dpp.quantity ,vpcp.time,dpp.quantity-vpcp.time,dpp.ppID,dpp.parentId ");
			 sqljindu.append("  from dt_ProductPackaging dpp ");
			 sqljindu.append("  left join view_progress_cssp vpcp  ");
			 sqljindu.append("  on    dpp.companyid=vpcp.companyid  ");
			 sqljindu.append("  and   dpp.goodsname=vpcp.program ");
			 sqljindu.append("  and   vpcp.ddsrstudentid=? ");
			 sqljindu.append("  where  dpp.companyid=? ");
			 sqljindu.append("  and dpp.parentid=? ");
			 Object[] paramspeijian = {cstaff.getStaffID(),account.getCompanyID(), productDesign.getPpID()};
			 ProductPackagingList=baseBeanService.getListBeanBySqlAndParams(sqljindu.toString(), paramspeijian);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productPackagingList", ProductPackagingList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * @time 2015-02-04
	 * @describe ajax删除物品以及物品下的所有子物品
	 * @return ID
	 */
	public String deleteProductPackagingByPID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		if(productDesign!=null&&productDesign.getPpID()!=null&&!"".equals(productDesign.getPpID())){
			deleteProductPackagingByPIDByRecursion(productDesign.getPpID());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "success");
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * @time 2014-02-05
	 * @describe 递归删除 
	 * @author zc
	 * @param PID
	 */
	public void deleteProductPackagingByPIDByRecursion(String PID){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> ProductPackagingList=baseBeanService.getListBeanByHqlAndParams(" from ProductPackaging where companyID=?  and parentId=?", new Object[]{account.getCompanyID(),PID});
		for (BaseBean baseBean : ProductPackagingList) {
			ProductPackaging pp=(ProductPackaging)baseBean;
			baseBeanService.deleteBeanByKey(ProductPackaging.class, pp.getPpKey());
			deleteProductPackagingByPIDByRecursion(pp.getPpID());
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{" delete from ProductPackaging where companyID=?  and ppID=? "}, new Object[]{account.getCompanyID(),PID});
	}
	
	/**
	 * @time 2015-02-04
	 * @describe ajax获得产品设计ID
	 * @return ID
	 */
	public String getServerID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serverID", serverService.getServerID("productpackaging"));
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		System.out.println(result);
		return "success";
	}
	/**
	 * @time 2015-02-04
	 * @describe 删除ajax产品下的价格
	 * @return ID
	 */
	public String deleteProductPriceCategoryByID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		if(productPriceCategory!=null&&productPriceCategory.getPcID()!=null&&!"".equals(productPriceCategory.getPcID())){
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{" delete from ProductPriceCategory   where  pcID=? "}, new Object[]{productPriceCategory.getPcID()});
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "success");
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	public ProductPackaging getProductDesign() {
		return productDesign;
	}

	public void setProductDesign(ProductPackaging productDesign) {
		this.productDesign = productDesign;
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

	public List<BaseBean> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<BaseBean> staffList) {
		this.staffList = staffList;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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

	public List<CCode> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<CCode> codeList) {
		this.codeList = codeList;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getPrintDate() {
		return printDate;
	}

	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	public List<BaseBean> getPrintList() {
		return printList;
	}

	public void setPrintList(List<BaseBean> printList) {
		this.printList = printList;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getPrintname() {
		return printname;
	}

	public void setPrintname(String printname) {
		this.printname = printname;
	}
	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}
	public GoodsManage getGoodsManage() {
		return goodsManage;
	}

	public void setGoodsManage(GoodsManage goodsManage) {
		this.goodsManage = goodsManage;
	}
	public CustomersForms getCustomersForms() {
		return customersForms;
	}

	public void setCustomersForms(CustomersForms customersForms) {
		this.customersForms = customersForms;
	}

	public String getProduce() {
		return produce;
	}

	public void setProduce(String produce) {
		this.produce = produce;
	}

	public List<CCode> getPriceManageList() {
		return priceManageList;
	}

	public void setPriceManageList(List<CCode> priceManageList) {
		this.priceManageList = priceManageList;
	}

	public ProductPriceCategory getProductPriceCategory() {
		return productPriceCategory;
	}

	public void setProductPriceCategory(ProductPriceCategory productPriceCategory) {
		this.productPriceCategory = productPriceCategory;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	

	public List<BaseBean> getProductPackagingList() {
		return productPackagingList;
	}

	public void setProductPackagingList(List<BaseBean> productPackagingList) {
		this.productPackagingList = productPackagingList;
	}

	public List<BaseBean> getProductPriceCategoryList() {
		return productPriceCategoryList;
	}

	public void setProductPriceCategoryList(List<BaseBean> productPriceCategoryList) {
		this.productPriceCategoryList = productPriceCategoryList;
	}

	public ProductPackaging getProductpeijian() {
		return productpeijian;
	}

	public void setProductpeijian(ProductPackaging productpeijian) {
		this.productpeijian = productpeijian;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public PageForm getPageFormpeijian() {
		return pageFormpeijian;
	}

	public void setPageFormpeijian(PageForm pageFormpeijian) {
		this.pageFormpeijian = pageFormpeijian;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String[] getPrdCategory() {
		return prdCategory;
	}

	public void setPrdCategory(String[] prdCategory) {
		this.prdCategory = prdCategory;
	}

	public String getBillID() {
		return billID;
	}

	public void setBillID(String billID) {
		this.billID = billID;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Map<String, ProductPackaging> getProductPackagingMap() {
		return productPackagingMap;
	}

	public void setProductPackagingMap(
			Map<String, ProductPackaging> productPackagingMap) {
		this.productPackagingMap = productPackagingMap;
	}

	public Map<String, ProductPriceCategory> getProductPriceCategoryMap() {
		return productPriceCategoryMap;
	}

	public void setProductPriceCategoryMap(
			Map<String, ProductPriceCategory> productPriceCategoryMap) {
		this.productPriceCategoryMap = productPriceCategoryMap;
	}

	public CCode getCcode() {
		return ccode;
	}

	public void setCcode(CCode ccode) {
		this.ccode = ccode;
	}

	public DtDrivingPrincipal getDtDrivingPrincipal() {
		return dtDrivingPrincipal;
	}

	public void setDtDrivingPrincipal(DtDrivingPrincipal dtDrivingPrincipal) {
		this.dtDrivingPrincipal = dtDrivingPrincipal;
	}

	public Staff getCstaff() {
		return cstaff;
	}

	public void setCstaff(Staff cstaff) {
		this.cstaff = cstaff;
	}
	
	
}
