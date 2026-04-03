package hy.ea.production.action.cprocedure;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.production.DCheck;
import hy.ea.bo.production.PTrack;
import hy.ea.bo.production.StaffTrack;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Constant;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 日生产
 */
@Controller
@Scope("prototype")
public class PTrackeAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private PageForm pageForm;
	private InputStream excelStream;
	private String result;
	private String search;

	private int pageNumber;
	private int number;
	private Object[] department;

	private String ptrackId;
	private String trackTime;
	private String type;
	private String ptrackkey;
	private String status;
	private String lots;
	private List<BaseBean> list;
	private DCheck dcheck;
	private StaffTrack staffTrack;

	private static Map<String, Integer> serialmap = new HashMap<String, Integer>();
	private static List<String> numlist = new ArrayList<String>();
	private static String datestring = Utilities.getDateString(new Date(),
			"yyMMdd");
	static {
		for (Integer i = 1; i < 10001; i++) {
			DecimalFormat df = new DecimalFormat("00000");
			numlist.add(df.format(i));
		}
	}
	/**
	 * 生产跟踪
	 */
	private PTrack ptrack;
	/**
	 * 获得session
	 */
	private Map<String, Object> session = ActionContext.getContext()
			.getSession();
	/**
	 * 获得account
	 */
	private CAccount account = (CAccount) ActionContext.getContext()
			.getSession().get("account");
	private String parameter;
	private String buttonType;
	private String ptrackekey;
	private String fiveClear;

	/**
	 * 查询
	 * 
	 * @return
	 */
	public String toSearchByPTrack() {
		session.put("tablePtrack", ptrack);
		return getPtrackList();
	}

	/**
	 * 查询表单
	 * 
	 * @return
	 */
	public String getPtrackList() {


		String hql = " from PTrack where 1=1 and companyID = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(account.getCompanyID());
		if("01".equals(type)&&!"".equals(type)){
		if (search != null && "search".equals(search)) {
			ptrack = (PTrack) session.get("tablePtrack");
			if (ptrack.getProductNumber() != null
					&& !"".equals(ptrack.getProductNumber())) {
				hql += " and  productNumber like ? ";
				params.add("%" + ptrack.getProductNumber() + "%");
			}
			if (ptrack.getProductName() != null
					&& !"".equals(ptrack.getProductName())) {
				hql += " and  productName like ? ";
				params.add("%" + ptrack.getProductName() + "%");
			}
			if (ptrack.getTrackman()!= null
					&& !"".equals(ptrack.getTrackman())) {
				hql += " and  trackman like ? ";
				params.add("%" + ptrack.getTrackman() + "%");
			}
			if (ptrack.getTrackTime() != null
					&& !"".equals(ptrack.getTrackTime())) {
				hql += " and  trackTime like ? ";
				params.add("%" + ptrack.getTrackTime() + "%");
			}
			if (ptrack.getStatus() != null
					&& !"".equals(ptrack.getStatus())) {
				hql += " and  status like ? ";
				params.add("%" + ptrack.getStatus() + "%");
			}
		}
		}
		hql += " order by trackTime desc";
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), hql, params.toArray());

		return "ptracklist";
	}

	/**
	 * 添加与修改
	 * 
	 * @return
	 */
	public String saveOrEditByPtrack() {
       List<BaseBean> list=new ArrayList<BaseBean>();
		if (ptrack.getPtrackeId() == null || "".equals(ptrack.getPtrackeId())) {
			ptrack.setPtrackeId(serverService.getServerID("ptrack"));
			ptrack.setStatus("00");
			ptrack.setTrackmanId(account.getStaffID());
			ptrack.setProductionDepartment((String) session
					.get("organizationName"));
			ptrack.setDepartmentID((String) session.get("organizaitonID"));

		}
		ptrack.setCompanyID(account.getCompanyID());
		
		list.add(ptrack);
		list.add(dcheck);
		baseBeanService.saveBeansListAndexecuteSqlsByParams(list, null, null);

		return "success";
	}

	/**
	 * 提交审核
	 * 
	 * @return
	 */
	public String saveByPtrack() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> list=new ArrayList<BaseBean>();
		ptrack = (PTrack) baseBeanService
				.getBeanByKey(PTrack.class, ptrackekey);
		ptrack.setStatus(status);
		dcheck=new DCheck();
		dcheck.setDcheckId(serverService.getServerID("dcheck"));
		dcheck.setId(ptrack.getPtrackeId());
		dcheck.setGoodName(ptrack.getProductName());
		dcheck.setItemNumber(ptrack.getProductNumber());
		dcheck.setBtnumber(ptrack.getThroughput());
		dcheck.setStatus("00");
		dcheck.setCompanyId(account.getCompanyID());
		list.add(ptrack);
		list.add(dcheck);
		baseBeanService.saveBeansListAndexecuteSqlsByParams(list, null, null);
		return "success";
	}

	/**
	 * 获取添加和修改页面
	 * 
	 * @return
	 */
	public String addByPTrack() {

		if (ptrack != null) {
			if (ptrack.getPtrackekey() != null
					&& !ptrack.getPtrackekey().equals("")) {
				String hql = "from PTrack where ptrackekey = ?";
				ptrack = (PTrack) baseBeanService.getBeanByHqlAndParams(hql,
						new Object[] { ptrack.getPtrackekey() });
			}
		}

		return "addptrack";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String deleteByPTrack() {
		baseBeanService.deleteBeanByKey(PTrack.class, ptrackekey);
		return "success";
	}

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String exportByPTrack() {
	
		String hql = " from PTrack where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		if("01".equals(type)&&!"".equals(type)){
			ptrack = (PTrack) session.get("tablePtrack");
			if(ptrack!=null&!"".equals(ptrack)){
			if (ptrack.getProductNumber() != null
					&& !"".equals(ptrack.getProductNumber())) {
				hql += " and  productNumber like ? ";
				params.add("%" + ptrack.getProductNumber() + "%");
			}
			if (ptrack.getProductName() != null
					&& !"".equals(ptrack.getProductName())) {
				hql += " and  productName like ? ";
				params.add("%" + ptrack.getProductName() + "%");
			}
			if (ptrack.getTrackman()!= null
					&& !"".equals(ptrack.getTrackman())) {
				hql += " and  trackman like ? ";
				params.add("%" + ptrack.getTrackman() + "%");
			}
			if (ptrack.getTrackTime() != null
					&& !"".equals(ptrack.getProductName())) {
				hql += " and  trackTime like ? ";
				params.add("%" + ptrack.getTrackTime() + "%");
			}
			if (ptrack.getStatus() != null
					&& !"".equals(ptrack.getStatus())) {
				hql += " and  status like ? ";
				params.add("%" + ptrack.getStatus() + "%");
			}
			}
			}
			
		hql += " order by trackTime";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
				params.toArray());

		excelStream = excelService.showExcel(PTrack.columnHeadings(), list);

		String organizationID = (String) session.get("organizationID");
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				"生产跟踪管理", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}

	/**
	 * 打印预览
	 * 
	 * @return
	 */
	public String toPrintPreviewByPTrack() {
		String hql = " from PTrack where 1=1";
		List<Object> params = new ArrayList<Object>();
		if("01".equals(type)&&!"".equals(type)){
		ptrack = (PTrack) session.get("tablePtrack");
		if(ptrack!=null&!"".equals(ptrack)){
		if (ptrack.getProductNumber() != null
				&& !"".equals(ptrack.getProductNumber())) {
			hql += " and  productNumber like ? ";
			params.add("%" + ptrack.getProductNumber() + "%");
		}
		if (ptrack.getProductName() != null
				&& !"".equals(ptrack.getProductName())) {
			hql += " and  productName like ? ";
			params.add("%" + ptrack.getProductName() + "%");
		}
		if (ptrack.getTrackman()!= null
				&& !"".equals(ptrack.getTrackman())) {
			hql += " and  trackman like ? ";
			params.add("%" + ptrack.getTrackman() + "%");
		}
		if (ptrack.getTrackTime() != null
				&& !"".equals(ptrack.getProductName())) {
			hql += " and  trackTime like ? ";
			params.add("%" + ptrack.getTrackTime() + "%");
		}
		if (ptrack.getStatus() != null
				&& !"".equals(ptrack.getStatus())) {
			hql += " and  status like ? ";
			params.add("%" + ptrack.getStatus() + "%");
		}
		}
		}
		
		hql += " order by trackTime desc";
		list = baseBeanService.getListBeanByHqlAndParams(hql, params.toArray());
		return "printPreview";
	}

	/**
	 * 获得行业分类
	 * 
	 * @return
	 */
	public String getAllIndustryClassification() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String companyID = account.getCompanyID();

		String hql = " from ptrack where companyId=? ";
		List<BaseBean> industryClassificationlist = baseBeanService
				.getListBeanByHqlAndParams(hql, new Object[] { companyID });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("industryClassificationlist", industryClassificationlist);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";

	}

	/**
	 * JSON取得出单据编号
	 */
	public String getLotNumber() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String BillID = "";
		if (lots != null && !"".equals(lots.trim())) {
			if (Constant.billTypeNumber.get(lots) != null) {
				BillID = getSerialNumber(Constant.billTypeNumber.get(lots));
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("BillID", BillID);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	private synchronized String getSerialNumber(String typeNumber) {
		if (!datestring.equals(Utilities.getDateString(new Date(), "yyMMdd"))) {
			datestring = Utilities.getDateString(new Date(), "yyMMdd");
		}
		if (serialmap.get(typeNumber) == null) {
			serialmap.put(typeNumber, 0);
		}
		String serialNumber = "ST"
				+ Utilities.getDateString(new Date(), "yyMMdd")
				+ numlist.get(serialmap.get(typeNumber));
		serialmap.put(typeNumber, serialmap.get(typeNumber) + 1);
		return serialNumber;
	}
	
	
	
	
	
/****************************************项目跟踪Start*************************************************/
	/**
	 * 
	 * 
	 * 项目跟踪
	 * @return
	 */
	public String findProductTrackList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from StaffTrack  where  fiveClear = ? and companyID = ?";
        List<Object> param = new ArrayList<Object>();
        param.add(fiveClear);
        param.add(account.getCompanyID());
		if(search!=null&&search.equals("search")){
			staffTrack = (StaffTrack) session.get("staffTrack");
			if(staffTrack.getPpName()!=null&&!staffTrack.getPpName().equals("")){
				hql+=" and ppName like ?";
				param.add("%"+staffTrack.getPpName()+"%");
			}
			if(staffTrack.getSubName()!=null&&!staffTrack.getSubName().equals("")){
				hql+=" and subName like ?";
				param.add("%"+staffTrack.getSubName()+"%");
			}
			if(staffTrack.getStaffName()!=null&&!staffTrack.getStaffName().equals("")){
				hql+=" and (staffName like ? or identiCard like ?)";
				param.add("%"+staffTrack.getStaffName()+"%");
				param.add("%"+staffTrack.getStaffName()+"%");
			}
			if(staffTrack.getResponName()!=null&&!staffTrack.getResponName().equals("")){
				hql+=" and responName like ?";
				param.add("%"+staffTrack.getResponName()+"%");
			}
			if(staffTrack.getStartDate()!=null&&!staffTrack.getStartDate().equals("")){
				hql+=" and enrollDate >= ?";
				param.add(staffTrack.getStartDate());
			}
			
			if(staffTrack.getEndDate()!=null&&!staffTrack.getEndDate().equals("")){
				hql+=" and enrollDate <= ?";
				param.add(staffTrack.getEndDate());
			}
		}
		
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 20 : pageNumber), hql, param.toArray());
		return "protrack";
	}
	
	/**
	 * 
	 * 查询项目跟踪
	 * @return
	 */
	public String toSearchBystrack(){
		session.put("staffTrack", staffTrack);
		return findProductTrackList();
		
	}
	/**
	 * 
	 * 导出项目跟踪
	 * @return
	 */
	public String toExcelBystrack(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from StaffTrack  where  fiveClear = ? and companyID = ?";
        List<Object> param = new ArrayList<Object>();
        param.add(fiveClear);
        param.add(account.getCompanyID());
        if(search!=null&&search.equals("search")){
			staffTrack = (StaffTrack) session.get("staffTrack");
			if(staffTrack.getPpName()!=null&&!staffTrack.getPpName().equals("")){
				hql+=" and ppName like ?";
				param.add("%"+staffTrack.getPpName()+"%");
			}
			if(staffTrack.getSubName()!=null&&!staffTrack.getSubName().equals("")){
				hql+=" and subName like ?";
				param.add("%"+staffTrack.getSubName()+"%");
			}
			if(staffTrack.getStaffName()!=null&&!staffTrack.getStaffName().equals("")){
				hql+=" and (staffName like ? or identiCard like ?)";
				param.add("%"+staffTrack.getStaffName()+"%");
				param.add("%"+staffTrack.getStaffName()+"%");
			}
			if(staffTrack.getResponName()!=null&&!staffTrack.getResponName().equals("")){
				hql+=" and responName like ?";
				param.add("%"+staffTrack.getResponName()+"%");
			}
			if(staffTrack.getStartDate()!=null&&!staffTrack.getStartDate().equals("")){
				hql+=" and enrollDate >= ?";
				param.add(staffTrack.getStartDate());
			}
			
			if(staffTrack.getEndDate()!=null&&!staffTrack.getEndDate().equals("")){
				hql+=" and enrollDate <= ?";
				param.add(staffTrack.getEndDate());
			}
		}
		list = baseBeanService.getListBeanByHqlAndParams(hql, param.toArray());
		
		excelStream = excelService.showExcel(StaffTrack.columnHeadings(),list);
		return "showexcel";

	}
	
	

	
	
	/**
	 * 
	 * 项目完成图
	 * @return
	 */
	public String findProductTrackPic(){
        HttpServletRequest request = ServletActionContext.getRequest();
		String hql = "from StaffTrack where stId = ?";
		staffTrack = (StaffTrack) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{staffTrack.getStId()});
		String hqlp = "from ProductPackaging where parentId = ? order by sorting";
		list = baseBeanService.getListBeanByHqlAndParams(hqlp, new Object[]{staffTrack.getPpID()});
		String strin = "(";
		for (int i = 0; i < list.size(); i++) {
			ProductPackaging ts = (ProductPackaging) list.get(i);
			strin+="'"+ts.getPpID()+"'";
			if(i!=list.size()-1){
				strin+=",";
			}
		}
		strin += ")";
		
		String hqlps = "from ProductPackaging s where s.parentId in "+strin+"order by createTime";
		List<BaseBean> listsub = baseBeanService.getListBeanByHqlAndParams(hqlps, null);
		String hqlw = "from ProductionAssembly s where s.productPID in "+strin;
		
		List<BaseBean> listselect = baseBeanService.getListBeanByHqlAndParams(hqlw, null);

		Map<String,Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < listsub.size(); i++) {
			ProductPackaging c = (ProductPackaging) listsub.get(i);
			if(listselect.contains(c)){
				map.put(c.getPpID(),1);
			}else{
				map.put(c.getPpID(),0);
			}
		}
		request.setAttribute("listsub", listsub);
		request.setAttribute("mapselect", map);

		return "propic";
	}

/****************************************项目跟踪End*************************************************/

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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Object[] getDepartment() {
		return department;
	}

	public void setDepartment(Object[] department) {
		this.department = department;
	}

	public CAccount getAccount() {
		return account;
	}

	public void setAccount(CAccount account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public PTrack getPtrack() {
		return ptrack;
	}

	public void setPtrack(PTrack ptrack) {
		this.ptrack = ptrack;
	}

	public String getPtrackId() {
		return ptrackId;
	}

	public void setPtrackId(String ptrackId) {
		this.ptrackId = ptrackId;
	}

	public String getTrackTime() {
		return trackTime;
	}

	public void setTrackTime(String trackTime) {
		this.trackTime = trackTime;
	}

	public String getPtrackkey() {
		return ptrackkey;
	}

	public void setPtrackkey(String ptrackkey) {
		this.ptrackkey = ptrackkey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getLots() {
		return lots;
	}

	public void setLots(String lots) {
		this.lots = lots;
	}

	public String getButtonType() {
		return buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}

	public String getPtrackekey() {
		return ptrackekey;
	}

	public void setPtrackekey(String ptrackekey) {
		this.ptrackekey = ptrackekey;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}

	public String getFiveClear() {
		return fiveClear;
	}

	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
    
	public StaffTrack getStaffTrack() {
		return staffTrack;
	}

	public void setStaffTrack(StaffTrack staffTrack) {
		this.staffTrack = staffTrack;
	}
}
