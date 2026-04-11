//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hy.ea.production.action.adesign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.ProCateRelate;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.production.BsimTest;
import hy.ea.bo.production.GoodFunction;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UploadContentToFileService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.*;
import org.hibernate.util.StringHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Scope("prototype")
public class ProductionDesignAction {
	private static final Logger logger = LoggerFactory.getLogger(ProductionDesignAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private UploadContentToFileService contentToFileService;
	@Resource
	private CCodeService codeService;
	private InputStream excelStream;
	private PageForm pageForm;
	private String result;
	private int pageNumber;
	private String search;
	private ProductPackaging productPackaging;
	private GoodFunction goodFunction;
	private List<BaseBean> list;
	private Map<Integer, String> maplist;
	private Map<String, String> maplistjs;
	private List<BaseBean> functionlist;
	private String tradeID;
	private String iscall;
	private String parameter;
	private String fiveClear;
	private String type;
	private String typeID;
	private String category;
	private String procate;

	public ProductionDesignAction() {
	}

	public String getProductDesignList() {
		this.pageForm = this.baseBeanService.getPageFormByDC(this.pageForm != null?this.pageForm.getPageNumber():1, this.pageNumber == 0?10:this.pageNumber, this.getLists());
		if(this.iscall != null && this.iscall.equals("call")) {
			HashMap map = new HashMap();
			map.put("pageForm", this.pageForm);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		} else {
			return "designlist";
		}
	}

	private DetachedCriteria getLists() {
		Map session = ActionContext.getContext().getSession();
		CAccount account = (CAccount)session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(ProductPackaging.class, "p");
		DetachedCriteria cri = DetachedCriteria.forClass(BsimTest.class, "b");
		if(this.type.equals("01")) {
			dc.add(Restrictions.eq("p.companyID", account.getCompanyID()));
			dc.add(Restrictions.eq("p.delStatus", "00"));
			dc.add(Restrictions.eq("p.fiveClear", this.fiveClear));
		} else if(this.type.equals("00")) {
			cri.add(Property.forName("b.id").eqProperty("p.ppID"));
			cri.add(Restrictions.eq("b.status", "02"));
			dc.add(Restrictions.eq("p.companyID", account.getCompanyID()));
			dc.add(Restrictions.eq("p.delStatus", "00"));
			dc.add(Restrictions.eq("p.fiveClear", this.fiveClear));
			dc.add(Subqueries.exists(cri.setProjection(Projections.property("b.id"))));
		}

		if(this.type.equals("02")) {
			cri.add(Property.forName("b.id").eqProperty("p.ppID"));
			cri.add(Restrictions.eq("b.status", "02"));
			dc.add(Restrictions.eq("p.companyID", account.getCompanyID()));
			dc.add(Restrictions.eq("p.delStatus", "00"));
			dc.add(Restrictions.eq("p.type", this.typeID));
			dc.add(Subqueries.exists(cri.setProjection(Projections.property("b.id"))));
		}

		dc.addOrder(Order.asc("p.productCode"));
		if(this.search != null && "search".equals(this.search)) {
			if(this.iscall != null && this.iscall.equals("call")) {
				if(this.parameter != null && !this.parameter.equals("")) {
					dc.add(Restrictions.or(Restrictions.like("productCode", this.parameter, MatchMode.ANYWHERE), Restrictions.like("goodsName", this.parameter, MatchMode.ANYWHERE)));
				}
			} else {
				this.productPackaging = (ProductPackaging)session.get("tablesearch");
				if(this.productPackaging.getProductCode() != null && !this.productPackaging.getProductCode().equals("")) {
					dc.add(Restrictions.like("productCode", this.productPackaging.getProductCode(), MatchMode.ANYWHERE));
				}

				if(this.productPackaging.getGoodsName() != null && !this.productPackaging.getGoodsName().equals("")) {
					dc.add(Restrictions.like("goodsName", this.productPackaging.getGoodsName(), MatchMode.ANYWHERE));
				}
				if(this.productPackaging.getBarCode() != null && !this.productPackaging.getBarCode().equals("")) {
					dc.add(Restrictions.like("barCode", this.productPackaging.getBarCode(), MatchMode.EXACT));
				}
			}
		}

		return dc;
	}

	public String toSearch() {
		Map session = ActionContext.getContext().getSession();
		session.put("tablesearch", this.productPackaging);
		return this.getProductDesignList();
	}

	public String getAddPage() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");

		HttpServletRequest request=ServletActionContext.getRequest();
		Company company = (Company)baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?",new Object[]{account.getCompanyID()});
		//行业类目为空或者行业类目不为驾校、餐饮的,显示超市的类目id
		String industryId;
		if(StringHelper.isEmpty(company.getIndustryType()) || (!company.getIndustryType().contains("驾校") && !company.getIndustryType().contains("餐饮")) ){
			industryId = "scode20190415raqvqk3uvs0000000762";//id为综合零售/超级市场零售id
		}else if(company.getIndustryType().contains("驾校") || company.getIndustryType().contains("餐饮")){
			industryId = "";
		}else{
			industryId = company.getIndustryId();
		}
		request.setAttribute("industryId",industryId);
		if(this.productPackaging != null && this.productPackaging.getPpID() != null && !this.productPackaging.getPpID().equals("")) {
			String hql = "from ProductPackaging where ppID = ?";
			this.productPackaging = (ProductPackaging)this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{this.productPackaging.getPpID()});
			if(productPackaging!=null&&productPackaging.getCcompanyID()!=null&&!productPackaging.getCcompanyID().equals("")){
				ContactCompany contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany where ccompanyID = ?",new Object[]{productPackaging.getCcompanyID()});
			    if(contactCompany!=null){
			    	this.productPackaging.setCcompanyName(contactCompany.getCompanyName());

				}
			}

			//产品类目
			String sql = "select c.codeID,c.codeValue from dt_ProCateRelate p left join dtCCode c on c.codeID = p.codeID  and c.companyID = ? where ppID = ?";
			List<BaseBean> procatelist = baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{productPackaging.getCompanyID(),productPackaging.getPpID()});

			if(procatelist!=null&&procatelist.size()!=0){
				String procatename = "";
				for (int j =0;j<procatelist.size();j++){
					Object b = (Object)procatelist.get(j);
					Object[] bb = (Object[])b;
					procate+=bb[0]+",";
					procatename+=bb[1]+",";
				}
				request.setAttribute("procatename",!procatename.equals("")?procatename.substring(0,procatename.length()-1):procatename);
				procate = !procate.equals("")?procate.substring(0,procate.length()-1):procate;
			}
		}






		return "addpage";
	}

	public String getEditOrPrevPage() {
		String hql = "from ProductPackaging where ppID = ?";
		this.productPackaging = (ProductPackaging)this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{this.productPackaging.getPpID()});
		String hqlfun = "from GoodFunction where goodsid = ? order by orders";
		this.functionlist = this.baseBeanService.getListBeanByHqlAndParams(hqlfun, new Object[]{this.productPackaging.getGoodsID()});
		this.maplist = new HashMap();

		for(int i = 0; i < this.functionlist.size(); ++i) {
			GoodFunction goodFun = (GoodFunction)this.functionlist.get(i);
			this.maplist.put(Integer.valueOf(i + 1), this.getContentFromFile(goodFun.getUrl()));
		}

		return "prevpage";
	}

	public String ajaxGoodFunction() {
		String hqlfun = "from GoodFunction where goodsid = ? order by orders";
		this.functionlist = this.baseBeanService.getListBeanByHqlAndParams(hqlfun, new Object[]{this.productPackaging.getGoodsID()});
		this.maplistjs = new HashMap();

		for(int map = 0; map < this.functionlist.size(); ++map) {
			GoodFunction jo = (GoodFunction)this.functionlist.get(map);
			this.maplistjs.put(String.valueOf(map + 1), this.getContentFromFile(jo.getUrl()));
		}

		HashMap var4 = new HashMap();
		var4.put("functionlist", this.functionlist);
		var4.put("maplistjs", this.maplistjs);
		JSONObject var5 = JSONObject.fromObject(var4);
		this.result = var5.toString();
		return "success";
	}

	public String saveProducts() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");

		List<BaseBean> beans = new ArrayList<BaseBean>();
		List<String> hqls = new ArrayList<String>();
		List<Object> parms = new ArrayList<Object>();

		if(this.productPackaging.getPpID() == null || this.productPackaging.getPpID().equals("")) {
			this.productPackaging.setPpID(this.serverService.getServerID("p"));
			String hql = "from CCode where codeID = ?";
			CCode code = (CCode)this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{this.productPackaging.getTradeID()});
			String codes = "";
			if(code != null) {
				codes = code.getCodeSn();
			}
			this.productPackaging.setQualified("1");
			this.productPackaging.setProductCode(this.generateProductCode(codes, this.productPackaging.getParentId()));
			this.productPackaging.setDelStatus("00");
			this.productPackaging.setProductstate("00");
			//产品初始佣金状态设置为:00[未设置佣金]
			this.productPackaging.setYjstatus("00");
			this.productPackaging.setVipStatus("00");
			this.productPackaging.setWholesaleStatus("00");
			this.productPackaging.setActivityStatus("00");

		}else{
			String hql = "delete from ProCateRelate where ppID = ?";
			hqls.add(hql);
			parms.add(productPackaging.getPpID());
		}
		   ProCateRelate proCateRelate = null;
			if(procate!=null&&!procate.equals("")) {

				String[] procates = procate.split(",");
				for (int i = 0;i<procates.length;i++) {
					proCateRelate = new ProCateRelate();
					proCateRelate.setPcrId(serverService.getServerID("pcrid"));
					proCateRelate.setCodeID(procates[i]);
					proCateRelate.setPpID(productPackaging.getPpID());
					proCateRelate.setCompanyID(account.getCompanyID());
					beans.add(proCateRelate);
				}
			}

		this.productPackaging.setCategory(this.category);

		this.productPackaging.setFiveClear(this.fiveClear);
		this.productPackaging.setCompanyID(account.getCompanyID());
		this.productPackaging.setPackagingDate(new Date());
		beans.add(productPackaging);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hqls.toArray(new String[]{}), parms.toArray());
		//this.baseBeanService.update(this.productPackaging);
		return "success";
	}

	public String generateProductCode(String tradeNum, String parentID) {
		String sql = "";
		String fcode = "";
		DecimalFormat format6 = new DecimalFormat("000000");
		DecimalFormat format2 = new DecimalFormat("00");
		if(parentID != null && !parentID.equals("")) {
			String hql1 = "from ProductPackaging where ppID = ?";
			ProductPackaging pp1 = (ProductPackaging)this.baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{parentID});
			sql = " select count(productCode) from dt_ProductPackaging where parentID = ?";
			int a = this.baseBeanService.getConutByBySqlAndParams(sql, new Object[]{parentID});
			int aa = a + 1;
			String nextnum = format2.format((long)aa);
			fcode = pp1.getProductCode() + nextnum;
		} else {
			sql = " select count(productCode) from dt_ProductPackaging where parentID is null and productCode is not null";
			int hql = this.baseBeanService.getConutByBySqlAndParams(sql, (Object[])null);
			int pp = hql + 1;
			fcode = tradeNum + format6.format((long)pp);
		}

		return fcode;
	}

	private String getContentFromFile(String filepath) {
		String path = ServletActionContext.getServletContext().getRealPath("\\") + filepath;
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");

		try {
			return this.contentToFileService.getContent(path);
		} catch (IOException var5) {
			var5.printStackTrace();
			return "";
		}
	}

	public String deleteProduct() {
		StringBuffer hql = new StringBuffer();
		ArrayList obj = new ArrayList();
		hql.append("update ProductPackaging set delStatus=\'01\' where ppID in (?");
		String sql = "select pp.ppID from dt_ProductPackaging pp  connect by parentId= prior ppid start with ppid=?";
		List list = this.baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{this.productPackaging.getPpID()});
		obj.add(list.get(0));

		for(int i = 1; i < list.size(); ++i) {
			hql.append(",?");
			obj.add(list.get(i));
		}

		hql.append(")");
		this.baseBeanService.saveBeansListAndexecuteHqlsByParams((List)null, new String[]{hql.toString()}, obj.toArray());
		return "success";
	}

	public String showExcel() {
		this.excelStream = this.excelService.showExcel(ProductPackaging.columnHeadings(), this.baseBeanService.getListByDC(this.getLists()));
		Map session = ActionContext.getContext().getSession();
		CAccount account = (CAccount)session.get("account");
		CLogBook cLogBook = this.logBookService.saveCLogBook((String)null, "导出产品设计", account);
		this.baseBeanService.update(cLogBook);
		return "showexcel";
	}

	public String printPrev() {
		this.list = this.baseBeanService.getListByDC(this.getLists());
		return "printprev";
	}

	public String getProductPage() {
		return "process";
	}

	public String getProductTreeList() {
		this.pageForm = this.baseBeanService.getPageFormByDC(this.pageForm != null?this.pageForm.getPageNumber():1, this.pageNumber == 0?10:this.pageNumber, this.getListTree());
		HashMap map = new HashMap();
		map.put("pageForm", this.pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	private DetachedCriteria getListTree() {
		Map session = ActionContext.getContext().getSession();
		CAccount account = (CAccount)session.get("account");
		logger.info("account should not null===: {}", account);
		DetachedCriteria dc = DetachedCriteria.forClass(ProductPackaging.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.isNull("parentId"));
		dc.add(Restrictions.isNotNull("goodsName"));
		dc.addOrder(Order.asc("productCode"));
		if(this.parameter != null && !this.parameter.equals("")) {
			dc.add(Restrictions.or(Restrictions.like("productCode", this.parameter, MatchMode.ANYWHERE), Restrictions.like("goodsName", this.parameter, MatchMode.ANYWHERE)));
		}

		return dc;
	}

	public String getSubProduct() {
		String hql = "from ProductPackaging where parentId = ? order by productCode";
		List sublist = this.baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{this.productPackaging.getPpID()});
		HashMap map = new HashMap();
		map.put("sublist", sublist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	public String transferToSim() {
		Map session = ActionContext.getContext().getSession();
		CAccount account = (CAccount)session.get("account");
		String hql = "from ProductPackaging where ppID = ?";
		ProductPackaging pp = (ProductPackaging)this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{this.productPackaging.getPpID()});
		BsimTest bt = new BsimTest();
		bt.setBsimTestId(this.serverService.getServerID("testid"));
		bt.setCompanyId(account.getCompanyID());
		bt.setCompanyName(((Company)session.get("currentcompany")).getCompanyName());
		bt.setGoodBar(pp.getBarCode());
		bt.setGoodName(pp.getGoodsName());
		bt.setGoodStandard(pp.getStandard());
		bt.setIndustryClassification(pp.getTradeCode());
		bt.setPrice(pp.getPrice());
		bt.setMoney(pp.getMoney());
		bt.setBtnumber(pp.getQuantity());
		bt.setItemNumber(pp.getProductCode());
		bt.setStatus("00");
		bt.setId(pp.getPpID());
		bt.setOrganizationId((String)session.get("organizationID"));
		bt.setOrganizationName((String)session.get("organizationName"));
		bt.setCategory(pp.getCategory());
		bt.setAuditTime((new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
		ArrayList beans = new ArrayList();
		pp.setProductstate("01");
		beans.add(pp);
		beans.add(bt);
		this.baseBeanService.executeHqlsByParamsList(beans, (String[])null, (List)null);
		return "success";
	}

	/**
	 *
	 * 获取产品类目
	 * @return
	 */
	public String getProCateList(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String codePID = request.getParameter("codePID");
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		String hql = "from CCode c where c.companyID = ? and (c.codeStatus = '00' or c.codeStatus = '01')  and (c.codePID in (select cc.codeID from CCode cc where cc.codePID = ?) or c.codePID  = ?)  order by c.codeNumber";
		List<BaseBean> codeList = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{account.getCompanyID(),codePID,codePID});
		HashMap map = new HashMap();
		map.put("codeList", codeList);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}



	public PageForm getPageForm() {
		return this.pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getPageNumber() {
		return this.pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSearch() {
		return this.search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public ProductPackaging getProductPackaging() {
		return this.productPackaging;
	}

	public void setProductPackaging(ProductPackaging productPackaging) {
		this.productPackaging = productPackaging;
	}

	public GoodFunction getGoodFunction() {
		return this.goodFunction;
	}

	public void setGoodFunction(GoodFunction goodFunction) {
		this.goodFunction = goodFunction;
	}

	public Map<Integer, String> getMaplist() {
		return this.maplist;
	}

	public void setMaplist(Map<Integer, String> maplist) {
		this.maplist = maplist;
	}

	public InputStream getExcelStream() {
		return this.excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public List<BaseBean> getFunctionlist() {
		return this.functionlist;
	}

	public void setFunctionlist(List<BaseBean> functionlist) {
		this.functionlist = functionlist;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}

	public List<BaseBean> getList() {
		return this.list;
	}

	public Map<String, String> getMaplistjs() {
		return this.maplistjs;
	}

	public void setMaplistjs(Map<String, String> maplistjs) {
		this.maplistjs = maplistjs;
	}

	public String getTradeID() {
		return this.tradeID;
	}

	public void setTradeID(String tradeID) {
		this.tradeID = tradeID;
	}

	public String getIscall() {
		return this.iscall;
	}

	public void setIscall(String iscall) {
		this.iscall = iscall;
	}

	public String getParameter() {
		return this.parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getFiveClear() {
		return this.fiveClear;
	}

	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeID() {
		return this.typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProcate() {
		return procate;
	}

	public void setProcate(String procate) {
		this.procate = procate;
	}
}
