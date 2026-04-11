package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.InformBills;
import hy.ea.bo.office.MeetingRoom;
import hy.ea.bo.office.MeetingRoomOrder;
import hy.ea.bo.office.SMParticipant;
import hy.ea.bo.office.StaffMeeting;
import hy.ea.service.CLogBookService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/*
 * 员工会议室
 * */
@Controller
@Scope("prototype")
public class MeetingRoomAction {
	private static final Logger logger = LoggerFactory.getLogger(MeetingRoomAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;

	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private StaffMeeting staffMeeting;
	private SMParticipant smParticipant;
	private PageForm pageForm;
	private List<BaseBean> organizationlist;
	private File photo;
	private String photoFileName;
	private String photoContentType;
	private String result;
	private String search;
	private Company company;
	private int pageNumber;
	private String companyID;
	private String orgID;
	private String searchType;
	private Staff searchStaff;
	private String startDates;
	private String endDates;
	private String meetingID;
	private String type;

	private MeetingRoom meetingRoom;
	private MeetingRoomOrder mroomOrder;
	private List<BaseBean> roomlist;
	private List<BaseBean> orderlist;
	private String dates;// 今天
	private String dateType;// last；前一天 next：后一天
	private List<String> timelist;



	/**
	 * 
	 * 
	 * 获得预约页面
	 * 
	 * @return
	 */
	public String OrderMeetingRoom() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		// 获取会议室
		String hql = "from MeetingRoom where companyID = ?";
		roomlist = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { account.getCompanyID() });
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (dates == null || dates.equals("")) {
			// 获取今天的预约
			Date dt = new Date();
			// 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制 yyyy-MM-dd HH:mm:ss aa
			dates = sdf.format(dt);
			try {
				date = sdf.parse(dates);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.error("操作异常", e);
			}
		} else {
			
			
			try {
				date = sdf.parse(dates);
			} catch (ParseException e) {
				logger.error("操作异常", e);
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if (dateType.equals("last")) {
				cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
			}
			if (dateType.equals("next")) {
				cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
			}
			dates = Utilities.getDateString(cal.getTime(), "yyyy-MM-dd");

		}

		// 时间

		timelist = new ArrayList<String>();
		int hour = 9;
		String m = "00";
		for (int j = 1; j < 28; j++) {

			if (j % 2 == 0) {
				m = "30";

			} else {
				m = "00";
				if (j != 1) {
					hour++;
				}
			}
			if (hour == 9) {
				timelist.add("0" + hour + ":" + m);
			} else {
				timelist.add(hour + ":" + m);
			}
		}

//		String hqlorder = "from MeetingRoomOrder where companyID = ? and startDate >= ? and endDate<= ?";
//		orderlist = baseBeanService.getListBeanByHqlAndParams(hqlorder,
//				new Object[] { account.getCompanyID(), date,date});

		return "order";
	}

	/**
	 * 
	 * 
	 * 获取预约信息
	 * 
	 * @return
	 */
	public String ajaxPreOrder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dates);
		} catch (ParseException e) {
			
			logger.error("操作异常", e);
		}
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hqlorder = "from MeetingRoomOrder where cancel is null and companyID = ? and (startDate = ?  or endDate= ? or (startDate<? and endDate>?))";
		orderlist = baseBeanService.getListBeanByHqlAndParams(hqlorder,
				new Object[] { account.getCompanyID(), date,date, date,date });

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderlist", orderlist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";

	}

	// 我的预约查询
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("mroomOrder", mroomOrder);
		return getMyRoomOrder();
	}


	/**
	 * 
	 * 
	 * 我的预约列表
	 * 
	 * @return
	 */

	public String getMyRoomOrder() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		try{
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		String sql = "select o.mroomoID,o.meetingName,o.meetingTheme,o.startDate,o.endDate,o.startTime,o.endTime,r.roomName,r.zone,r.capacity,r.adminName,r.adminTel,r.remark,r.mroomID,o.mroomoKey from dtMeetingRoomOr o left join dtMeetingRoom r on o.mroomID = r.mroomID where o.staffID = ? and o.companyID = ? and o.cancel is null";
        String sqlcount ="select count(*) from dtMeetingRoomOr o left join dtMeetingRoom r on o.mroomID = r.mroomID where o.staffID = ? and o.companyID = ? and o.cancel is null";
		List<String> params = new ArrayList<String>();
		params.add(account.getStaffID());
		params.add(account.getCompanyID());
		
		if(type!=null&&type.equals("inner")){
			sql+=" and o.quote is null";
			sqlcount+=" and o.quote is null";
		}
        if(search!=null&&search.equals("search")){
        	
        	mroomOrder = (MeetingRoomOrder) session.get("mroomOrder");
        	
        	if(mroomOrder.getMroomID()!=null&&!mroomOrder.getMroomID().equals("")){
        		sql+=" and o.mroomID like ?";
        		sqlcount+=" and o.mroomID like ?";
        		params.add("%"+mroomOrder.getMroomID()+"%");
        	}
	       if(mroomOrder.getMeetingName()!=null&&!mroomOrder.getMeetingName().equals("")){
	    	   sql+=" and o.meetingName like ?";
       		   sqlcount+=" and o.meeingName like ?";
       		  params.add("%"+mroomOrder.getMeetingName()+"%");
        	}
        	
        }
        sql+=" order by o.startDate desc";
        sqlcount+=" order by o.startDate desc";
        
        
        
        pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql, sqlcount, params.toArray());
		
		// 获取会议室
		String hql = "from MeetingRoom where companyID = ?";
		roomlist = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { account.getCompanyID() });
		
		// 时间

		timelist = new ArrayList<String>();
		int hour = 9;
		String m = "00";
		for (int j = 1; j < 28; j++) {

			if (j % 2 == 0) {
				m = "30";

			} else {
				m = "00";
				if (j != 1) {
					hour++;
				}
			}
			if (hour == 9) {
				timelist.add("0" + hour + ":" + m);
			} else {
				timelist.add(hour + ":" + m);
			}
		}

		
		}catch(Exception e){
		 logger.error("操作异常", e);
		}
		return "myroomorder";
	}
	
	
	
	/**
	 * 
	 * 取消预约
	 * 
	 * @return
	 */
	public String cancelMyOrder(){
		String hql = "from MeetingRoomOrder where mroomoID = ?";
		MeetingRoomOrder mro = (MeetingRoomOrder) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{mroomOrder.getMroomoID()});
		mro.setCancel("01");
		baseBeanService.update(mro);
		
		return "succ";
		
	}
	
	
	
	/**
	 * 
	 * 验证是否可以取消预约，也就是是否有会议引用了该预约 也可以直接看自己的quote是否被引用
	 * @return
	 */
	public String checkCancelOrder(){
		Map<String,Object> map = new HashMap<String, Object>();
		String hql = "from StaffMeeting where mroomoID = ?";
		List<BaseBean> smlist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{mroomOrder.getMroomoID()});
		
		if(smlist.size()==0){
			map.put("result", "suc");
		}else{
			map.put("result", "fail");
		}
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}



	/**
	 * 
	 * 
	 * 预约保存
	 * 
	 */ 

	public String saveOrderRoom() {
		try {
			ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");

			if (mroomOrder.getMroomoID() == null
					|| "".equals(mroomOrder.getMroomoID())) {
				mroomOrder.setMroomoID(serverService.getServerID("mroomoID"));
				parameter = "预约会议(会议预约ID:" + mroomOrder.getMroomoID() + ")";
			} else {
				String hql2 = "from MeetingRoomOrder where mroomoID=?";
				MeetingRoomOrder mro = (MeetingRoomOrder) baseBeanService
						.getBeanByHqlAndParams(hql2, new Object[] { mroomOrder
								.getMroomoID() });
				parameter = "修改会议预约(会议编号:" + mro.getMroomoID() + ")";
			}
			List<BaseBean> beans = new ArrayList<BaseBean>();
			mroomOrder.setCompanyID(account.getCompanyID());
			mroomOrder.setOrganizationID(organizationID);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				mroomOrder.setStartDate(sdf.parse(startDates));
				mroomOrder.setEndDate(sdf.parse(endDates));
				mroomOrder.setStartDates(startDates);
				mroomOrder.setEndDates(endDates);
				//处理时间
				
				mroomOrder.setStartDay(sdf2.parse(startDates+" "+mroomOrder.getStartTime()+":00"));
				mroomOrder.setEndDay(sdf2.parse(endDates+" "+mroomOrder.getEndTime()+":00"));
				mroomOrder.setStartDays(startDates+" "+mroomOrder.getStartTime()+":00");
				mroomOrder.setEndDays(endDates+" "+mroomOrder.getEndTime()+":00");
				
				
			} catch (ParseException e) {
				logger.error("操作异常", e);
			}
           
			
			
			
			
			String hqlstaff = "from Staff where staffID = ?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
					hqlstaff, new Object[] { account.getStaffID() });
			mroomOrder.setStaffID(account.getStaffID());
			mroomOrder.setStaffName(staff.getStaffName());

			CLogBook logbook = logBookService.saveCLogBook(organizationID,
					parameter, account);
			beans.add(mroomOrder);
			beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "succ";
	}

	/**
	 * 
	 * 
	 * 获取预约信息
	 * 
	 * @return
	 */
	public String getOrderInfo() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from  MeetingRoomOrder where mroomoID = ?";

		MeetingRoomOrder mro = (MeetingRoomOrder) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { mroomOrder
						.getMroomoID() });

		Map<String, Object> map = new HashMap<String, Object>();
		if (account.getStaffID().equals(mro.getStaffID())) {
			map.put("update", "update");
		} else {
			map.put("update", "view");
		}
		map.put("mroomOrder", mro);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";

	}

	/**
	 * 判断会议预约是否冲突
	 * 
	 * @return
	 */
	public String isConflict() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		// 公司的所有的预约
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDay = null;
		Date endDay = null;
		List<BaseBean> list = null;
		try {
		    startDay = sdf.parse(startDates+" "+mroomOrder.getStartTime()+":00");
		    endDay = sdf.parse(endDates+" "+mroomOrder.getEndTime()+":00");
			String hql = "from MeetingRoomOrder where  cancel is null and mroomID = ? and companyID = ? and ((startDay > ? and startDay < ?) or (endDay > ? and endDay < ?) or (startDay<? and endDay>? ))";
			List<Object> params = new ArrayList<Object>();
			params.add(mroomOrder.getMroomID());
			params.add(account.getCompanyID());
			params.add(startDay);
			params.add(endDay);
			params.add(startDay);
			params.add(endDay);
			params.add(startDay);
			params.add(endDay);
//			logger.info("调试信息");
			
		   if(mroomOrder.getMroomoID()!=null&&!mroomOrder.getMroomoID().equals("")){
			   hql+=" and  mroomoID not like ?";
			   params.add("%"+mroomOrder.getMroomoID()+"%");
		   }
			list = baseBeanService.getListBeanByHqlAndParams(hql,params.toArray());

		
		} catch (ParseException e) {
			logger.error("操作异常", e);
		}

	
	
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("result", list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		
		return "success";
	}





	// //////////////////////////////////////会议室///////////////////////////////////////////////////

	// 根据条件查询会议室
	public String toSearchRoom() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", meetingRoom);
		return getMeetingRoomList();
	}

	private DetachedCriteria getListRoom() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(MeetingRoom.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			meetingRoom = (MeetingRoom) session.get("tablesearch");

			if (!meetingRoom.getRoomName().equals("")) {
				dc.add(Restrictions.like("roomName", meetingRoom.getRoomName(),
						MatchMode.ANYWHERE));
			}

		}
		return dc;
	}

	// 会议室列表
	public String getMeetingRoomList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getListRoom());
		return "roomlist";
	}

	/**
	 * 
	 * 添加修改会议室
	 * 
	 * @return
	 */
	public String saveMeetingRoom() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (meetingRoom.getMroomID().equals("")) {
			meetingRoom.setMroomID(serverService.getServerID("mroomID"));
			meetingRoom.setCompanyID(account.getCompanyID());
		}
		baseBeanService.update(meetingRoom);

		return "succ";
	}

	/**
	 * 
	 * 删除会议室 (待完善)
	 * 
	 * @return
	 */
	public String delMeetingRoom() {
		String hql2 = "from MeetingRoom where mroomID=?";
		MeetingRoom mr = (MeetingRoom) baseBeanService.getBeanByHqlAndParams(
				hql2, new Object[] { meetingRoom.getMroomID() });
		baseBeanService.deleteBeanByKey(MeetingRoom.class, mr.getMroomKey());
		
		
		
		return "succ";
	}
	
	
	
	/**
	 * 
	 * 
	 * 检查会议室是否可以删除，即不被预约且预约没有作废
	 * @return
	 */
	public String checkDelRoom(){
		String hql1 = "from MeetingRoomOrder where mroomID = ? and cancel is null";
		List<BaseBean>  list = baseBeanService.getListBeanByHqlAndParams(hql1,new Object[]{meetingRoom.getMroomID()});
		
		Map<String,Object> map = new HashMap<String, Object>();
		if(list.size()==0){
			map.put("result", "suc");
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

	public List<BaseBean> getOrganizationlist() {
		return organizationlist;
	}

	public void setOrganizationlist(List<BaseBean> organizationlist) {
		this.organizationlist = organizationlist;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public StaffMeeting getStaffMeeting() {
		return staffMeeting;
	}

	public void setStaffMeeting(StaffMeeting staffMeeting) {
		this.staffMeeting = staffMeeting;
	}

	public SMParticipant getSmParticipant() {
		return smParticipant;
	}

	public void setSmParticipant(SMParticipant smParticipant) {
		this.smParticipant = smParticipant;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public Staff getSearchStaff() {
		return searchStaff;
	}

	public void setSearchStaff(Staff searchStaff) {
		this.searchStaff = searchStaff;
	}

	public String getStartDates() {
		return startDates;
	}

	public void setStartDates(String startDates) {
		this.startDates = startDates;
	}

	public String getEndDates() {
		return endDates;
	}

	public void setEndDates(String endDates) {
		this.endDates = endDates;
	}

	public String getMeetingID() {
		return meetingID;
	}

	public void setMeetingID(String meetingID) {
		this.meetingID = meetingID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MeetingRoom getMeetingRoom() {
		return meetingRoom;
	}

	public void setMeetingRoom(MeetingRoom meetingRoom) {
		this.meetingRoom = meetingRoom;
	}

	public MeetingRoomOrder getMroomOrder() {
		return mroomOrder;
	}

	public void setMroomOrder(MeetingRoomOrder mroomOrder) {
		this.mroomOrder = mroomOrder;
	}

	public List<BaseBean> getRoomlist() {
		return roomlist;
	}

	public void setRoomlist(List<BaseBean> roomlist) {
		this.roomlist = roomlist;
	}

	public List<BaseBean> getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(List<BaseBean> orderlist) {
		this.orderlist = orderlist;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public List<String> getTimelist() {
		return timelist;
	}

	public void setTimelist(List<String> timelist) {
		this.timelist = timelist;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
