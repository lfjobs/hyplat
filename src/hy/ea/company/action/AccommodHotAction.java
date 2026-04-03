package hy.ea.company.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.Accommod;
import hy.ea.bo.company.AccommodHot;
import hy.ea.bo.company.RoomNumber;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

/**
 * 分配住宿管理
 * 
 * @author 李伟志
 * 
 */
public class AccommodHotAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	@Resource
	private ShowExcelService excelService;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private List<BaseBean> beans;
	private String parameter;
	private AccommodHot accHot;
	private Accommod accommod;
	private RoomNumber roomNumber;
	private String result;
	private InputStream excelStream;
	
	private String deitnumid;
	private List<AccommodHot> accHotList;
	private List<Accommod> accList;
	
	/**
	 * 删除分配酒店
	 * 
	 * @return
	 */
	public String deleteHot(){
		accHot = selAccHot(accHot.getAccommodHotID());
		accommod = selAcc(accHot.getAccommodID());
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		List<Object[]> parmsList = new ArrayList<Object[]>();
		
		String hql = "delete AccommodHot where accommodHotID = ?";
		parmsList.add(new Object[]{accHot.getAccommodHotID()});

		String hql1 = "update RoomNumber set starts = ? where roomNumID = ? ";
		parmsList.add(new Object[]{"00",accHot.getRoomNum()});
		
		String hql2 = "update Accommod set bedOccNum = ? where accommodID = ?";
		parmsList.add(new Object[]{(Integer.parseInt(accommod.getBedOccNum())-1)+"",accHot.getAccommodID()});
		
		beans = new ArrayList<BaseBean>();
		
		parameter = "删除分配酒店：(人员ID：" + accHot.getStaffID() + ")";
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(logBook);
		
		baseBeanService.executeHqlsByParamsList(beans,new String[]{hql, hql1,hql2}, parmsList);
		return "success";
	}
	
	
	/**
	 * 导出
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showAccHot(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		
		List<Object> list = (List<Object>)ActionContext.getContext().getSession().get("allList");
		String sql = (String) list.get(0);
		String sql1 = sql.substring(0, 138);
		String sql2 = sql.substring(154);
		
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(AccommodHot.columnHeadings(),
				baseBeanService.getListBeanBySqlAndParams(sql1+sql2, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出住宿信息", account);
		baseBeanService.update(logBook);
		
		return "showAccHotExcel";
	}
	
	
	/**
	 * 获取全部
	 * @return
	 */
	public String getAllAccHot(){

		List<Object> list = getListsql();
		String sql = (String) list.get(0);
		ActionContext.getContext().getSession().put("allList",list);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, "select count(*) "
						+ sql.substring(sql.indexOf("from")), parms);
		
		return "getAllAccHot";
	} 
	/**
	 * 单一对象查询
	 * @return
	 */
	public AccommodHot selAccHot(String id){
		
		accHot = new AccommodHot();
		String hql = "from AccommodHot where accommodHotID = ?";
		accHot = (AccommodHot)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{id});
		
		return accHot;
	}
	
	public Accommod selAcc(String id){
		accommod = new Accommod();
		String hql = "from Accommod where accommodID = ?";
		accommod = (Accommod)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{id});
		return accommod;
	}
	public String editval(){
		
		accHot = selAccHot(accHot.getAccommodHotID());
		accommod = selAcc(accHot.getAccommodID());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accHot", accHot);
		map.put("accommod", accommod);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		
		return "success";
	}
	/**
	 * 验证人员
	 * 
	 * @return
	 */
	public String getStaff(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		String organizationID = (String) session.get("organizationID");
		CAccount account = (CAccount) session.get("account");
		String sql = "select count(*) from dt_accommodhot t where t.staffid = ?" +
				" and t.organizationid = ? and t.companyid = ?";
		Object[] params = {accHot.getStaffID(),organizationID,account.getCompanyID()};
		int i = baseBeanService.getConutByBySqlAndParams(sql, params);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("i", i);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		
		return "success";
	}
	
	
	/**
	 * 分配住宿
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("deprecation")
	public String addAccHot() throws UnsupportedEncodingException{
		
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		String otid = accHot.getAccommodHotID();
		if(otid.equals("")){
		
			accHot.setRemarks(URLDecoder.decode(accHot.getRemarks(),"UTF-8"));
			accHot.setAccommodHotID(serverService.getServerID("accHot"));
			accHot.setCreateName(account.getAccountName());
			accHot.setCreateDate(new Date().toLocaleString());
			accHot.setCompanyID(account.getCompanyID());
			accHot.setOrganizationID(organizationID);
			beans.add(accHot);
		}else{
			accHot.setRemarks(URLDecoder.decode(accHot.getRemarks(),"UTF-8"));
			accHot.setUpdateName(account.getAccountName());
			accHot.setUpdateDate(new Date().toLocaleString());
			beans.add(accHot);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,null, null);
		List<Object[]> paramsList = new ArrayList<Object[]>();
		List<String> StringList = new ArrayList<String>();
		if(otid.equals("")){
			String hql = "update RoomNumber r set r.starts = ? where r.roomNumID = ? ";
			Object[] params = {"01",accHot.getRoomNum()};
			paramsList.add(params);
			String hql1 = "update Accommod a set a.bedOccNum = ? where a.accommodID = ?";
			int i = Integer.parseInt(accommod.getBedOccNum())+1;
			Object[] params1 = {i+"",accHot.getAccommodID() };
			paramsList.add(params1);
			StringList.add(hql);
			StringList.add(hql1);
		}else if(accHot.getRoomNum().equals(deitnumid)){}else{
			String hql = "update RoomNumber r set r.starts = ? where r.roomNumID = ? ";
			Object[] params = {"01",accHot.getRoomNum()};
			paramsList.add(params);
			String hql1 = "update RoomNumber r set r.starts = ? where r.roomNumID = ? ";
			Object[] params1 = {"00",deitnumid};
			paramsList.add(params1);
			StringList.add(hql);
			StringList.add(hql1);
			
		}
		baseBeanService.executeHqlsByParamsList(null,StringList.toArray(new String[]{}), paramsList);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ok", "分配住宿成功！");
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	private List<Object> getListsql(){
		List<Object> results = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		
		String sql = "select c.companyname,tc.codevalue,cc.codevalue as cvalue,s.staffname,a.floor"
				+",r.roomnum,a.roomprice,t.roomdisprice,t.roomagrprice"
				+",t.remarks,t.accommodhotid"
				+" from  dt_accommodhot t"
				+" left join DT_Accommod a on t.accommodid = a.accommodid"
				+" left join dtContactCompany c on a.hotelname = c.ccompanyid"
				+" left join dtccode tc on a.stars = tc.codeid"
				+" left join dtccode cc on a.roomtype = cc.codeid"
				+" left join dt_hr_staff s on s.staffid = t.staffid" 
				+" left join dt_roomnumber r on r.roomnumid = t.roomnum"
				+" where r.starts = ? and t.companyid = ? and t.organizationid = ?";
		parms.add("01");
		parms.add(account.getCompanyID());
		parms.add(organizationID);
		
		if(search != null && search.equals("search")){
			if(accHot.getAccommodID()!="" &&
					!accHot.getAccommodID().equals("")){
				sql+=" and c.a.hotelname = ?";
				parms.add(accHot.getAccommodID());
			}
			if(accHot.getStaffID()!="" &&
					!accHot.getStaffID().equals("")){
				sql+=" and s.staffname like ?";
				parms.add("%"+accHot.getStaffID()+"%");
			}
			if(accHot.getRoomNum()!="" &&
					!accHot.getRoomNum().equals("")){
				sql+=" and r.roomnum like ?";
				parms.add("%"+accHot.getRoomNum()+"%");
			}
			if(accommod.getStars()!="" &&
					!accommod.getStars().equals("")){
				sql+=" and a.stars = ?";
				parms.add(accommod.getStars());
			}
			if(accommod.getFloor()!="" &&
					!accommod.getFloor().equals("")){
				sql+=" and a.floor like ?";
				parms.add("%"+accommod.getFloor()+"%");
			}
			if(accommod.getRoomType()!="" &&
					!accommod.getRoomType().equals("")){
				sql+=" and a.roomtype = ?";
				parms.add(accommod.getRoomType());
			}
		}
		
		
		sql+=" order by t.createdate desc";
		results.add(sql);
		results.add(parms.toArray());
		
		return results;
	}
	public String toSeach(){
		ActionContext.getContext().getSession().put("accHot",
				accHot);
		ActionContext.getContext().getSession().put("accommod",
				accommod);
		return getAllAccHot();
	}
	
	
	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}
	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}
	public ServerService getServerService() {
		return serverService;
	}
	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}
	public CLogBookService getLogBookService() {
		return logBookService;
	}
	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
	}
	public CCodeService getCodeService() {
		return codeService;
	}
	public void setCodeService(CCodeService codeService) {
		this.codeService = codeService;
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
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public List<BaseBean> getBeans() {
		return beans;
	}
	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public AccommodHot getAccHot() {
		return accHot;
	}
	public void setAccHot(AccommodHot accHot) {
		this.accHot = accHot;
	}
	public RoomNumber getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(RoomNumber roomNumber) {
		this.roomNumber = roomNumber;
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


	public InputStream getExcelStream() {
		return excelStream;
	}


	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}


	public Accommod getAccommod() {
		return accommod;
	}


	public void setAccommod(Accommod accommod) {
		this.accommod = accommod;
	}


	public List<AccommodHot> getAccHotList() {
		return accHotList;
	}


	public void setAccHotList(List<AccommodHot> accHotList) {
		this.accHotList = accHotList;
	}


	public List<Accommod> getAccList() {
		return accList;
	}


	public void setAccList(List<Accommod> accList) {
		this.accList = accList;
	}


	public String getDeitnumid() {
		return deitnumid;
	}


	public void setDeitnumid(String deitnumid) {
		this.deitnumid = deitnumid;
	}
	
	
	
	
}
