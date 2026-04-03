package hy.ea.invoicing.action;

//仓库管理
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.invoicing.Warehouse;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class WareHouseAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private UpLoadFileService fileService;
	private InputStream excelStream;
	private Warehouse warehouse;
	private PageForm pageForm;
	private String parameter;
	private String result;
	private int pageNumber;
	private String search;

	private List<BaseBean> warehouseList;
	private String parentID;

	private File photo;
	private String photoFileName;
	private String photoContentType;
	
	/**
	 * 
	 * @return
	 */
	private String pware;
	/**
	 * 
	 * @return
	 */
	private String parea;
	/**
	 * 
	 * @return
	 */
	private String pframe;
	/**
	 * 
	 * @return
	 */
	private String place;
	
	
	public String getListWareHouse(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql="from Warehouse where companyID=? and wareType='1'";
		warehouseList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()});
		List<Object> result=getSqlAndPrarms();
		String sql=result.get(0).toString();
		Object[] prarms=(Object[])result.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")), prarms);
		
		return "warehouselist";
	}

	public List<Object> getSqlAndPrarms(){
		List<Object>  result=new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String sql=" select wh.wareid,c.companyname,wh1.warename as warename1,wh2.warename as warename2,wh3.warename as warename3,wh.warename as warename4" +
				" from dt_inv_wh wh left join dt_inv_wh wh1 on wh1.wareid=wh.pware " +
				" left join dt_inv_wh wh2 on wh2.wareid=wh.parea " +
				" left join dt_inv_wh wh3 on wh3.wareid=wh.pframe " +
				" left join dtcompany c on c.companyid=wh.companyid " +
				" where wh.waretype=? and wh.companyid=? and wh.states=?";
		parms.add('4');
		parms.add(account.getCompanyID());
		parms.add("00");
		if(search!=null&&search.equals(search)){
			if(session.get("pware")!=null&&!"".equals(session.get("pware").toString().trim())){
				sql+=" and wh1.warename=?";
				parms.add(session.get("pware").toString().trim());
			}
			if(session.get("parea")!=null&&!"".equals(session.get("parea").toString().trim())){
				sql+=" and wh2.warename=?";
				parms.add(session.get("parea").toString().trim());
			}
			if(session.get("pframe")!=null&&!"".equals(session.get("pframe").toString().trim())){
				sql+=" and wh3.warename=?";
				parms.add(session.get("pframe").toString().trim());
			}
			if(session.get("place")!=null&&!"".equals(session.get("place").toString().trim())){
				sql+=" and wh.warename=?";
				parms.add(session.get("place").toString().trim());
			}
		}
		result.add(sql);
		result.add(parms.toArray());
		return result;
		
	}	
	


	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("pware", pware);
		session.put("parea", parea);
		session.put("pframe", pframe);
		session.put("place", place);
		return getListWareHouse();
	}
	

	@SuppressWarnings("unchecked")
	public String showExcel() {
		
		List<Object> result=getSqlAndPrarms();
		String sql=result.get(0).toString();
		sql = "select " + sql.substring(sql.indexOf(",") + 1);
		Object[] prarms=(Object[])result.get(1);
		excelStream = excelService.showExcel(Warehouse.columnHeadings(),
				baseBeanService.getListBeanBySqlAndParams(sql, prarms));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		CLogBook  logbook=logBookService.saveCLogBook(null, "导出仓库列表", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}

	/**
	 * 删除仓库管理
	 */
	public String deleteWareHouse() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql = " update Warehouse set states=? where  wareID = ? and companyID = ?";
		Object[] params = {"01", warehouse.getWareID(), account.getCompanyID() };
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, params);
	
		return "success";
	}

	/**
	 * ajax查询 保存仓库
	 */
	public String saveWareHouseAjax() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Map<String, Object> map = new HashMap<String, Object>();
		if (account == null) {
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Company company1 = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", new Object[] { account
						.getCompanyID() });
		String hql="from Warehouse w where w.groupCompanySn=? and w.companyID=? and w.wareType=? and w.wareName=?";
		List<Object> param = new ArrayList<Object>();
		param.add(company1.getCompanyPID());
		param.add(account.getCompanyID());
		param.add(warehouse.getWareType());
		param.add(warehouse.getWareName());
		if(warehouse.getWareType().equals("2")){
			hql+="  and w.pware=?";
			param.add(warehouse.getPware());
		}else if(warehouse.getWareType().equals("3")){
			hql+=" and parea=?";
			param.add(warehouse.getParea());
		}else if(warehouse.getWareType().equals("4")){
			hql+=" and parea=? and pframe=?";
			param.add(warehouse.getPframe());
		}
		List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(hql, param.toArray());
		if(list.size()>0){
			map.put("alert", "名称为"+warehouse.getWareName()+"已经存在，请重新输入");
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}else{
			map.put("alert", "");
		}
		warehouse.setWareID(serverService.getServerID("warehouse"));
		warehouse.setGroupCompanySn(company1.getCompanyPID());
		warehouse.setCompanyID(account.getCompanyID());
		warehouse.setStates("00");
		baseBeanService.save(warehouse);
		map.put("warehouse", warehouse);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}



	/**
	 * ajax根据父级列别获取子类别
	 */
	public String getListWareHouseZiAjax() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "succ";
		}
		String hql = " from Inventory where companyID=? and states=? and ";
		List<Object> params=new ArrayList<Object>();
		params.add(account.getCompanyID());
		params.add("03");
		
		List<BaseBean> wareHouseList = baseBeanService
				.getListBeanByHqlAndParams(hql, params.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wareHouseList", wareHouseList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
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

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
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

	public ShowExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}

	public UpLoadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UpLoadFileService fileService) {
		this.fileService = fileService;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}


	public Warehouse getWarehouse() {
		return warehouse;
	}


	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}


	public List<BaseBean> getWarehouseList() {
		return warehouseList;
	}


	public void setWarehouseList(List<BaseBean> warehouseList) {
		this.warehouseList = warehouseList;
	}

	public String getPware() {
		return pware;
	}

	public void setPware(String pware) {
		this.pware = pware;
	}

	public String getParea() {
		return parea;
	}

	public void setParea(String parea) {
		this.parea = parea;
	}

	public String getPframe() {
		return pframe;
	}

	public void setPframe(String pframe) {
		this.pframe = pframe;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

}
