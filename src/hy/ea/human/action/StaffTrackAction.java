package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.vo.ContactUser;
import hy.ea.bo.human.Humancollect;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.TrackRelation;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;

public class StaffTrackAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CCodeService codeService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String search;
	private PageForm pageForm;
	private int pageNumber;
	private Staff cstaff;
	private Humancollect humancollect;
	private TrackRelation trackRelation;
	private List<BaseBean> beans;
	private String parameter;
	private String sdate;
	private String edate;
	private String result;
	private String contactConnectionsVal; //查询时客户类别传参
	private String childopertionID;
	private String showType;//判断状态 add \ edit
	public String toSearchCStaff(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("cstaff", cstaff);
		return getStaffList();
	}
	public String getStaffList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String groupCompanySn = session.get("groupCompanySn").toString();
		String hql_forStaff = "select st.*,tr.contactconnections from dt_hr_staff st left join dttrackrelation tr on tr.foreignkeyid=st.staffid  where st.groupCompanySn = ?";
		List<Object> parms = new ArrayList<Object>();
		parms.add(groupCompanySn);
		hql_forStaff +=" and tr.staffid = ?";
		parms.add(account.getStaffID());
		hql_forStaff +=" and tr.tr.status=?";
		parms.add("01");
		if (search != null && search.equals("search")) {
			cstaff = (Staff) ActionContext.getContext().getSession().get(
					"cstaff");
			if (cstaff.getStaffName() != null
					&& !"".equals(cstaff.getStaffName().trim())) {
				hql_forStaff += " and st.staffName like ? ";
				parms.add("%" + cstaff.getStaffName().trim() + "%");
			}
			if (sdate != null && !("").equals(sdate)
					&& edate != null && !("").equals(edate)) {
				hql_forStaff += " and tr.collectdate  between ? and ?";
				parms.add( Utilities.getDateFromString(sdate+ " 00:00:00","yyyy-MM-dd hh:mm:ss"));
				parms.add(Utilities.getDateFromString(edate+ " 23:59:59","yyyy-MM-dd hh:mm:ss"));
			}
			if (cstaff.getStaffAddress() != null
					&& !"".equals(cstaff.getStaffAddress())) {
				hql_forStaff += " and st.staffAddress like ? ";
				parms.add("%" + cstaff.getStaffAddress()
						+ "%");
			}
			if(null != contactConnectionsVal && !"".equals(contactConnectionsVal)){
				hql_forStaff +=" and tr.contactconnections like ?";
				parms.add("%" + contactConnectionsVal + "%");
			}
		}
		hql_forStaff+=" order by tr.collectdate desc";
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),hql_forStaff,"select count(1) "
				+ hql_forStaff.substring(hql_forStaff.indexOf("from")), parms.toArray());
		return "list";
	}
	
	public String getStaffSearch(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("staff", cstaff);
		return getStaffForCashier();
	}
	
	/**
	 * 支持社会人力的查询
	 * @return
	 */
	public String getStaffForCashier() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc=DetachedCriteria.forClass(ContactUser.class);
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		if (search != null && search.equals("search")) {
			cstaff = (Staff) ActionContext.getContext().getSession().get(
			"staff");
			if (cstaff.getStaffCode() != null&& !"".equals(cstaff.getStaffCode())) {
				dc.add(Restrictions.like("staffCode", cstaff.getStaffCode(),MatchMode.ANYWHERE));
			}
			if(cstaff.getStaffName()!=null&&!"".equals(cstaff.getStaffName())) {
				dc.add(Restrictions.like("staffName", cstaff.getStaffName(),MatchMode.ANYWHERE));
			}
			if(cstaff.getStaffIdentityCard()!=null&&!"".equals(cstaff.getStaffIdentityCard())){
				dc.add(Restrictions.like("staffIdentityCard", cstaff.getStaffIdentityCard(),MatchMode.ANYWHERE));
			}
		}
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber),dc);
		if(pageForm!=null)
		{
			session.put("RecordCount", pageForm.getRecordCount());
		}
		return "staffForCashier";
	}
	
	public String getCompanybyID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		int message=0;
		String [] array=childopertionID.split(",");
		StringBuffer suber=new StringBuffer();
        for(int i=0; i< array.length; i++){
        	suber.append("'");
        	suber.append(array[i]).append("',");
        }   
        suber.deleteCharAt(suber.lastIndexOf(","));
    	String zeren="\'"+account.getStaffID()+"\'";
    	String zeren1="\'"+account.getCompanyID()+"\'";
        String sql="from TrackRelation where staffID="+zeren+" and companyID="+zeren1+" and foreignKeyID in ("+ suber.toString()+")";
        TrackRelation tRelation=(TrackRelation) baseBeanService.getBeanByHqlAndParams(sql, new Object[]{});
		if(tRelation!=null){
			message=1;
		}else{
			cstaff =(Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ? ", new Object[]{childopertionID.split(",")[0]});
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("co", message);
		map.put("cstaff", cstaff);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	/**
	 * 跳转添加、修改页面
	 * @return
	 */
	public String toSaveJsp(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		
		String hql = "from Humancollect where companyid = ?";
		humancollect = (Humancollect)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()});
				
		if(!showType.equals("add")){
			String cstid = "";
			if(cstaff.getStaffID().contains(",")){
				cstid = cstaff.getStaffID().split(",")[0];
			}else{
				cstid = cstaff.getStaffID();
			}
			cstaff =(Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ? ", new Object[]{cstid});
		}
		return "toSaveJsp";
	}
	/**
	 * 保存人事菜单启用项
	 * @return
	 */
	public String saveHumanCollect(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?",
				new Object[] { account.getStaffID()});
		if(humancollect.getHumancollectid() == null 
				|| "".equals(humancollect.getHumancollectid())){
			humancollect.setHumancollectid(serverService.getServerID("humanresource"));
			humancollect.setCname(staff.getStaffName());
			humancollect.setCtime(new Date());
			parameter = staff.getStaffName() + "添加了个人服务办菜单启用项";
		}else{
			humancollect.setUname(staff.getStaffName());
			humancollect.setUtime(new Date());
			parameter = staff.getStaffName() + "修改了个人服务办菜单启用项";
		}
		humancollect.setCompanyid(account.getCompanyID());
		beans = new ArrayList<BaseBean>();
		beans.add(humancollect);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	
	/**
	 * 保存或修改Staff
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String saveStaffTrack() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String [] array=childopertionID.split(",");
		List list=new ArrayList(); 
		for(int i=0; i< array.length; i++){  
            if(!list.contains(array[i]))  
                list.add(array[i]);  
        }
		String [] newStr =(String[]) list.toArray(new String[1]) ;
		
		for(int m=0;m<newStr.length;m++){
			String sql="from Staff dt where dt.staffID=?";
			Staff css=(Staff) baseBeanService.getBeanByHqlAndParams(sql,new Object[]{newStr[m]});
			TrackRelation trackRelation=new TrackRelation();
			trackRelation.setTrackrelationID(serverService.getServerID("trackRelation"));
			trackRelation.setAddress(css.getAddress());
			trackRelation.setCollectdate(new Date());
			trackRelation.setForeignKeyID(newStr[m]);
			trackRelation.setCompanyID(account.getCompanyID());
			trackRelation.setStatus("01");
			trackRelation.setOrganizationID(organizationID);
			trackRelation.setStaffID(account.getStaffID());
			parameter = "添加收集个人（人员姓名：:" + css.getStaffName() + ")";
			CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
			beans = new ArrayList<BaseBean>();
			beans.add(logBook);
			beans.add(trackRelation);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}
		return "success";
	}
	
	/**
	 * 为选择客户类别下拉框赋值
	 * @return
	 */
	public String getSelectLists() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<CCode> codeRelationList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20121015uqn3qtck280000000003");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeRelationList", codeRelationList);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 判断往来个人/往来单位是否已在客户类别
	 * 
	 * @return
	 */
	public String isContactUser() {
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
		String hql = "from TrackRelation where companyID = ? and staffID = ? and foreignKeyID = ?";
		Object[] params = {account.getCompanyID(),account.getStaffID(),trackRelation.getForeignKeyID()};
		TrackRelation track = (TrackRelation)baseBeanService.getBeanByHqlAndParams(hql, params);
		
		if(null == track.getContactConnections() || "".equals(track.getContactConnections())){
			
			map.put("c","1");
		}else{
			map.put("c","2");
		}
		track.setContactConnections(trackRelation.getContactConnections());
		baseBeanService.update(track);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
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

	public Staff getCstaff() {
		return cstaff;
	}

	public void setCstaff(Staff cstaff) {
		this.cstaff = cstaff;
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public TrackRelation getTrackRelation() {
		return trackRelation;
	}
	public void setTrackRelation(TrackRelation trackRelation) {
		this.trackRelation = trackRelation;
	}
	public String getContactConnectionsVal() {
		return contactConnectionsVal;
	}
	public void setContactConnectionsVal(String contactConnectionsVal) {
		this.contactConnectionsVal = contactConnectionsVal;
	}
	public String getChildopertionID() {
		return childopertionID;
	}
	public void setChildopertionID(String childopertionID) {
		this.childopertionID = childopertionID;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	public Humancollect getHumancollect() {
		return humancollect;
	}
	public void setHumancollect(Humancollect humancollect) {
		this.humancollect = humancollect;
	}
	
}
