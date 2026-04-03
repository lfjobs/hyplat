package hy.ea.production.action.cprocedure;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.production.FieldDistribution;
import hy.ea.production.service.WarehouseService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;


import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * 场地分配
 * @author zj
 *
 */
public class FieldDistributionAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private WarehouseService warehouseService;
	private PageForm pageForm;
	private int pageNumber;				//每页显示的条数
	private String result;					//ajax返回字段
	private InputStream excelStream;
	private ProductPackaging productPackaging;  //产品表
	private FieldDistribution fieldDistribution; 		 //场地分配表
	private String type;
	private String category;
	private String fiveClear;			//组织机构
	private String grType;
	
	/**
	 * 获取主页面
	 */
	public String getHomePage(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		HttpServletRequest re=ServletActionContext.getRequest();
		String hql="from FieldDistribution where companyId=? and status=? and type=? and category=?";
		List<Object> params=new ArrayList<Object>();
		params.add(account.getCompanyID());params.add("00");
		params.add(grType);params.add(category);
		if(fieldDistribution!=null){
			if(fieldDistribution.getProductCode()!=null&&!"".equals(fieldDistribution.getProductCode())){
				hql+=" and productCode like ?";
				params.add("%"+fieldDistribution.getProductCode()+"%");
			}
			if(fieldDistribution.getPpName()!=null&&!"".equals(fieldDistribution.getPpName())){
				hql+=" and ppName like ?";
				params.add("%"+fieldDistribution.getPpName()+"%");
			}
		}
		if(fiveClear!=null&&!"".equals(fiveClear)){
			hql+=" and fiveClear=?";
			params.add(fiveClear);
		}
		pageForm=baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql+" order by distributionTime desc", params.toArray());
		re.setAttribute("list", pageForm);
		return "homePage";
	}
	/**
	 * 获取添加和修改页面
	 */
	public String getAddPage(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		HttpServletRequest re=ServletActionContext.getRequest();
		SimpleDateFormat  df = new SimpleDateFormat("yyyy-MM-dd");
		if("add".equals(type)){
			re.setAttribute("orgId", (String) session.get("organizationID"));
			re.setAttribute("date", df.format(new Date()));
			re.setAttribute("fiveClear",fiveClear);
			type="添加";
			return "addPage";
		}else{
			String hql="from FieldDistribution where fieldDistributionId=? and companyId=?";
			FieldDistribution fd=(FieldDistribution)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{fieldDistribution.getFieldDistributionId()
											,account.getCompanyID()});
			re.setAttribute("date", df.format(new Date()));
			re.setAttribute("orgId", (String) session.get("organizationID"));
			re.setAttribute("fieldDistribution", fd);
			re.setAttribute("fiveClear",fiveClear);
			type="修改";
			return "editPage";
		}
	}
	
	/**
	 * 获取未分配场地的项目
	 */
	@SuppressWarnings({ "unchecked" })
	public String ajaxNotAllocatedSite(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		List<Object> params =new ArrayList<Object>();
		String sql="select pd.proDocumentsID,pd.batchNumber,p.tradeCode,p.type,p.barCode,p.productCode,p.goodsname,p.brand,p.model,p.standard,p.variableID,g.quantity,p.price,g.money from dtProDocuments pd left join dtgoodsbills g on g.cashierbillsid=pd.proDocumentsID left join dt_ProductPackaging p on p.ppid=g.ppid" +
				" where pd.companyID=? and pd.category=? and pd.type=? and fiveClear=?";
		params.add(account.getCompanyID());params.add(category);
		params.add(grType);params.add(fiveClear);
		if(productPackaging!=null){
			if(null!=productPackaging.getTradeName()&&!"".equals(productPackaging.getTradeName())){
				sql+=" and p.tradeName like ?";
				params.add("%"+productPackaging.getTradeName()+"%");
			}
			if(null!=productPackaging.getProducttype()&&!"".equals(productPackaging.getProducttype())){
				sql+=" and p.producttype like ?";
				params.add("%"+productPackaging.getProducttype()+"%");
			}
			if(null!=productPackaging.getProductCode()&&!"".equals(productPackaging.getProductCode())){
				sql+=" and p.productCode like ?";
				params.add("%"+productPackaging.getProductCode()+"%");
			}
		}
		sql+="and pd.proDocumentsID not in (select fd.proDocumentsID from dtfieldDistribution fd where fd.companyid=?)";
		params.add(account.getCompanyID());
		List<Object[]> list=baseBeanService.getListBeanBySqlAndParams(sql,params.toArray());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list",list);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return Action.SUCCESS;
	}
	
	/**
	 * 归档场地分配内容
	 */
	public String addFieldDistribution(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		if("add".equals(type)){
			fieldDistribution.setFieldDistributionId(serverService.getServerID("FieldDistribution"));
			fieldDistribution.setCompanyId(account.getCompanyID());
			fieldDistribution.setStatus("00");
			baseBeanService.save(fieldDistribution);
		}else{
			fieldDistribution.setCompanyId(account.getCompanyID());
			baseBeanService.update(fieldDistribution);
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 删除
	 */
	public String deleteFieldDistribution(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		String sql="update DTFIELDDISTRIBUTION set status=? where fieldDistributionId=? and companyId=?";
		Object[] obj={"01",fieldDistribution.getFieldDistributionId(),account.getCompanyID()};
		baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql},obj);
		return Action.SUCCESS;
	}
	
	/**
	 * 导出EXCEL表格
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("unchecked")
	public String exportExcelTable() throws UnsupportedEncodingException{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");	
		String sql="select case when f.productcode is null then ' ' else f.productcode end" 
					 + ",case when f.ppname is null then ' ' else f.ppname end" 
				 	 + ",case when f.starttime is null then ' ' else f.starttime end" 
					 + ",case when f.endtime is null then ' ' else f.endtime end" 
					 + ",case when f.siteaddress is null then ' ' else f.siteaddress end" 
					 + ",case when f.staffname is null then ' ' else f.staffname end" 
					 + ",case when f.distributiontime is null then ' ' else f.distributiontime end" 
					 + ",case when f.duty is null then ' ' else f.duty end" 
					 + ",case when f.remarks is null then ' ' else f.remarks end from dtFieldDistribution f" 
					 + " where companyId=? and status=? and fiveClear=?";
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql,  new Object[]{account.getCompanyID(),"00",fiveClear});
		excelStream=warehouseService.OutFieldisExcel(list);
		return "showexcel";
	}
	
	/**
	 * 打印
	 */
	public String toPrint(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		HttpServletRequest re=ServletActionContext.getRequest();
		String hql="from FieldDistribution where  companyId=? and status=?";
		List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),"00"});
		re.setAttribute("list", list);
		return "toPrint";
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public ProductPackaging getProductPackaging() {
		return productPackaging;
	}
	public void setProductPackaging(ProductPackaging productPackaging) {
		this.productPackaging = productPackaging;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public FieldDistribution getFieldDistribution() {
		return fieldDistribution;
	}

	public void setFieldDistribution(FieldDistribution fieldDistribution) {
		this.fieldDistribution = fieldDistribution;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getGrType() {
		return grType;
	}
	public void setGrType(String grType) {
		this.grType = grType;
	}
	public String getFiveClear() {
		return fiveClear;
	}
	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
	
}
