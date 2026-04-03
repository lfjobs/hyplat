package hy.ea.company.action.product;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;
/**
 * 集团产品设计汇总信息
 * @author allen
 *
 */
public class ProductdesignGroup {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CompanyService companyserverService;
	private ProductPackaging productDesign;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
	private String sdate;
	private String edate;
	private List<CCode> codeList;
	private String cc;   //判断公司还是集团
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", productDesign);
		return getListProductdesign();
	}
	
	/**
	 *获得集团产品设计列表
	 * @return
	 */
	public String getListProductdesign(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		List<Object> list = getList();
		session.put("sqllist", list);
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
	   pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
			.getPageNumber() : 1), (pageNumber==0?5:pageNumber), sql, "select count(1) "
			+ sql.substring(sql.indexOf("from")), parms);
		return "productlist";
	}
	private List<Object> getList() {
		List<Object> result = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		ArrayList<String> cids = companyserverService
				.getCompanyAndItsChildrenIDs(account.getCompanyID());
		List<Object> parms = new ArrayList<Object>();
		String sql="select pp.ppid,cp.companyname,co.organizationname,hs.staffname, pp.packagingdate,gm.goodscoding,pp.goodsname," +
				" gm.goodsvariable,gm.acquiescestandard,pp.quantity,pp.weight,pp.price,pp.money,pp.manualurl,pp.propagandaurl,pp.fileurl,gm.goodsid" +
				" from dt_productpackaging pp" +
				" left join dtcompany cp on cp.companyid=pp.companyid" +
				" left join dtcorganization co on co.organizationid=pp.organizationid" +
				" left join dt_hr_staff hs on hs.staffid=pp.staffid" +
				" left join dtgoodsmanage gm on gm.goodsid=pp.goodsid ";
		sql+=" where cp.companyid=?";
		parms.add(account.getCompanyID());
		if (search != null && search.equals("search")) {
			productDesign = (ProductPackaging) session.get("tablesearch");
			if(productDesign.getGoodsName()!=null&&!productDesign.getGoodsName().equals("")){
				sql+=" and pp.goodsname like ?";
				parms.add("%"+productDesign.getGoodsName()+"%");
			}if(productDesign.getCompanyID()!=null&&!productDesign.getCompanyID().equals("")){
				sql+=" and cp.companyID  =  ?";
				parms.add(productDesign.getCompanyID());
			}else {
				if (cids.size() == 1) {
					sql += " and cp.companyid = ? ";
					parms.add(cids.get(0));
				}
			}if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				sql += " and pp.packagingdate between ? and ? ";

				parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
						"yyyy-MM-dd hh:mm:ss"));
				parms.add(Utilities.getDateFromString(edate + " 23:59:59",
						"yyyy-MM-dd hh:mm:ss"));
			}
		}
		sql+=" order by pp.packagingdate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
		/**
		 * 导出产品设计集团汇总列表
		 * @return
		 */
	public String showExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "tologin";
		}
		//String organizationID = (String) session.get("organizationID");
		String hql = "from ProductPackaging pp where exists ( select cp.companyID from Company cp where pp.companyID = cp.companyID and exists  "+
				"(select hs.staffID from Staff hs where hs.staffID=pp.staffID and exists " +
				"(select gm.goodsID from GoodsManage gm where gm.goodsID=pp.goodsID and cp.companyID = ?)))"; 
		Object[] params = {account.getCompanyID()};
		excelStream = excelService.showExcel(ProductPackaging.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(hql, params));
		CLogBook cLogBook = logBookService.saveCLogBook(account.getCompanyID(),
				"导出产品设计集团汇总", account);
		baseBeanService.update(cLogBook);
		return"showexcel";
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

		public List<CCode> getCodeList() {
			return codeList;
		}

		public void setCodeList(List<CCode> codeList) {
			this.codeList = codeList;
		}

		public String getCc() {
			return cc;
		}

		public void setCc(String cc) {
			this.cc = cc;
		}

	
}
