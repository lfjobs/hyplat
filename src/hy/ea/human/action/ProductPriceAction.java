package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.ProductPrice;
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

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * cxf
 * 产品价格定位
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class ProductPriceAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;  
	@Resource
	private CLogBookService logBookService; 
	@Resource
	private ShowExcelService excelService;
	
	private ProductPrice productPrice;
	private String parameter; 
	private PageForm pageForm;
	private String search; 
	private int pageNumber;
	
	private InputStream excelStream;
	private List<BaseBean> beans;
	
	//保存产品价格定位
	public String saveProductPrice(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) map.get("organizationID");
		if(null==productPrice.getProductpriceID()||"".equals(productPrice.getProductpriceID())){
			productPrice.setProductpriceID(serverService.getServerID("productPrice"));
			parameter = "添加产品价格定位(编号:"+productPrice.getGoodsCoding()+")";
		}else{
			String hql = "from ProductPrice where companyID = ? and  productpriceID = ? ";
			ProductPrice c = (ProductPrice) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),productPrice.getProductpriceID()});
			parameter = "修改产品价格定位(编号:"+c.getGoodsCoding()+")";
		}
		productPrice.setCompanyID(companyID);
		productPrice.setOrganizationID(organizationID);
		beans = new ArrayList<BaseBean>();
		beans.add(productPrice);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	//删除产品价格定位
	 public String delProductPrice()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID"); 
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),productPrice.getProductpriceID()};
		    String hql2="from ProductPrice where companyID=?  and productpriceID = ? ";
		    ProductPrice c=(ProductPrice) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook logBook = logBookService.saveCLogBook(organizationID, "删除产品价格定位(编号:"+c.getGoodsCoding()+")", account);
		    beans = new ArrayList<BaseBean>();
			beans.add(logBook);
		    String[] hql={"delete from ProductPrice where companyID=?  and productpriceID=?"};
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
		    return "success";
	    }
	 
	 //根据条件查询产品价格定位
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", productPrice);
			return getProductPriceList();
		}
	 //得到产品价格定位
		public String getProductPriceList() {
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "productPrice";	
		}
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String companyID = account.getCompanyID();
			DetachedCriteria dc = DetachedCriteria.forClass(ProductPrice.class);
			dc.add(Restrictions.eq("companyID", companyID));
			dc.add(Restrictions.eq("organizationID", organizationID));
			if (search != null && search.equals("search")) {
				productPrice = (ProductPrice) session.get("tablesearch");
				if(null!=productPrice.getGoodsCoding()&&!"".equals(productPrice.getGoodsCoding()))
				{
					dc.add(Restrictions.like("goodsCoding", productPrice.getGoodsCoding(), MatchMode.ANYWHERE));
				} 
				if(null!=productPrice.getGoodsName()&&!"".equals(productPrice.getGoodsName()))
				{
					dc.add(Restrictions.like("goodsName", productPrice.getGoodsName(), MatchMode.ANYWHERE));
				}
			} 
			return dc;
		}
		
		// 导出产品价格定位
		public String showProductPriceExcel() {
			Map<String, Object> map = ActionContext.getContext().getSession();
			CAccount account = (CAccount) map.get("account");
			String organizationID = (String) map.get("organizationID");
			excelStream = excelService.showExcel(ProductPrice.columnHeadings(), baseBeanService.getListByDC(getList()));
			CLogBook logBook = logBookService.saveCLogBook(organizationID,"导出产品价格定位", account);
			baseBeanService.update(logBook);
			return "showexcel";
		}
		public String getParameter() {
			return parameter;
		}
		public void setParameter(String parameter) {
			this.parameter = parameter;
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
		public ProductPrice getProductPrice() {
			return productPrice;
		}
		public void setProductPrice(ProductPrice productPrice) {
			this.productPrice = productPrice;
		}
}
