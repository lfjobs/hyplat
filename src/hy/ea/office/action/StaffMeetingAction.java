package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.MeetingRoomOrder;
import hy.ea.bo.office.SMParticipant;
import hy.ea.bo.office.StaffMeeting;
import hy.ea.bo.office.TelMessage;
import hy.ea.service.CLogBookService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.MobileMessage;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/*
 * 员工会议管理
 * */
@Controller
@Scope("prototype")
public class StaffMeetingAction {
	private static final Logger logger = LoggerFactory.getLogger(StaffMeetingAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;

	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CLogBookService logBookService;
	@Autowired
	private MobileMessage mobileMessage;// 短信接口
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
	private String cancelType;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	// 根据条件查询
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", staffMeeting);
		return getStaffMeeingList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(StaffMeeting.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		dc.addOrder(Order.desc("updateTime"));
		if (search != null && search.equals("search")) {
			staffMeeting = (StaffMeeting) session.get("tablesearch");
			if (!staffMeeting.getMeetingName().equals("")) {
				dc.add(Restrictions.like("meetingName", staffMeeting
						.getMeetingName().trim(), MatchMode.ANYWHERE));
			}
			if (!staffMeeting.getNoticeType().equals("")) {
				dc.add(Restrictions.like("noticeType", staffMeeting
						.getNoticeType(), MatchMode.ANYWHERE));
			}
		}
		return dc;
	}

	// 会议列表
	public String getStaffMeeingList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		Object[] params1 = { companyID };
		String ohql = "from COrganization where companyID=? and Status = '00'";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				params1);
		company = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", params1);
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList());
		return "smeetinglist";
	}



	// 保存

	public String saveSmeeting() {
		try {
			ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			if (photo != null) {
				String path = ServletActionContext.getRequest().getSession()
						.getServletContext().getRealPath("/");
				String photoPath = fileService.savePhoto(path, photoFileName,
						photo, account.getCompanyID(), "/human/informBills/"
								+ Utilities.getDateString(new Date(),
										"yyyy-MM-dd"));
				staffMeeting.setAccessory(photoPath);
			}

			if (staffMeeting.getMeetingID() == null
					|| "".equals(staffMeeting.getMeetingID())) {
				staffMeeting.setMeetingID(serverService
						.getServerID("meetingID"));
				parameter = "添加会议(会议编号:" + staffMeeting.getMeetingNum() + ")";
				staffMeeting.setNoticeType("00");
			} else {
				String hql2 = "from StaffMeeting where meetingID=?";
				StaffMeeting jeom = (StaffMeeting) baseBeanService
						.getBeanByHqlAndParams(hql2, new Object[] {
								staffMeeting.getMeetingID() });
				parameter = "修改会议(会议编号:" + jeom.getMeetingNum() + ")";
			}
			List<BaseBean> beans = new ArrayList<BaseBean>();
			staffMeeting.setOrganizationID(organizationID);
			staffMeeting.setCompanyID(account.getCompanyID());
			
			staffMeeting.setUpdateTime(new Date());

			// 处理日期

			staffMeeting.setStartDate(Utilities.getDateFromString(startDates,
					"yyyy-MM-dd HH:mm:ss"));
			staffMeeting.setEndDate(Utilities.getDateFromString(endDates,
					"yyyy-MM-dd HH:mm:ss"));
			String hqlstaff = "from Staff where staffID = ?";
			Staff pstaff = (Staff) baseBeanService.getBeanByHqlAndParams(
					hqlstaff, new Object[] { account.getStaffID() });
			staffMeeting.setPrincipalID(account.getStaffID());
			staffMeeting.setPrincipal(pstaff.getStaffName());
			staffMeeting.setPrincipaltel(pstaff.getReference());
			
			//将引用的预约会议室 设为已引用，免得还得查
			String hqlorder ="from MeetingRoomOrder where mroomoID = ?";
			MeetingRoomOrder mro = (MeetingRoomOrder) baseBeanService.getBeanByHqlAndParams(hqlorder,new Object[]{staffMeeting.getMroomoID()});
			mro.setQuote("01");
			baseBeanService.update(mro);
			
			
			// 保存参会人员信息
			String staffIDs = smParticipant.getStaffID();
			String staffNames = smParticipant.getStaffName();
			String[] arrayID = staffIDs.split(",");
			String[] arrayName = staffNames.split("\\|");
			SMParticipant smp = null;
			String hqlss="from SMParticipant where staffID = ? and meetingID = ?";
            SMParticipant smpa = null;
            
            
            //判断是否有删除的参会人员
            //先查询原来参加这个会议的人]
            String hqls2 = "from SMParticipant where meetingID = ?";
            List<BaseBean> listpa =  baseBeanService.getListBeanByHqlAndParams(hqls2, new Object[]{staffMeeting.getMeetingID()});
            List<String> staffIds = new ArrayList<String>();
            for (int i = 0; i < arrayID.length; i++) {
				String[] arraysf = arrayID[i].split("-");
				staffIds.add(arraysf[0]);
            }
            String hqlm = "from StaffMeeting where meetingID = ?";
            StaffMeeting smt = (StaffMeeting) baseBeanService.getBeanByHqlAndParams(hqlm,new Object[]{staffMeeting.getMeetingID()});
            if(staffMeeting.getNoticeType()==null){
            	staffMeeting.setNoticeType(smt.getNoticeType());
            }
            for(BaseBean b:listpa){
            	SMParticipant sms = (SMParticipant) b;
            	if(!staffIds.contains(sms.getStaffID())){
            		if(smt.getNoticeType().equals("01")){
            			this.emailNotice("关于<"+smt.getMeetingName()+">的会议，您不需要参加了，特此通知。",sms.getEmail());
            		    this.smsNotice(sms.getPhoneNumber()+",", "关于<"+staffMeeting.getMeetingName()+">的会议，您不需要参加了，特此通知。", sms.getStaffName()+",", smt);
            		}
            		baseBeanService.deleteBeanByKey(SMParticipant.class,sms.getPartiKey());
            	}
            }
            
            
			for (int i = 0; i < arrayID.length; i++) {
				String[] arrayIDD = arrayID[i].split("-");
				smpa = (SMParticipant) baseBeanService.getBeanByHqlAndParams(hqlss, new Object[]{arrayIDD[0],staffMeeting.getMeetingID()});
				if(smpa!=null){
					continue;
				}
				smp = new SMParticipant();
				smp.setPartiID(serverService.getServerID("partiID"));
				smp.setCompanyID(account.getCompanyID());
				smp.setIsAttend("00");
				smp.setIsLate("00");
				smp.setIsSpeak("00");
				smp.setMeetingID(staffMeeting.getMeetingID());
				smp.setStaffName(arrayName[i]);
				
				smp.setStaffID(arrayIDD[0]);

				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hqlstaff, new Object[] { arrayIDD[0] });
				smp.setPhoneNumber(staff.getReference());
				smp.setEmail(staff.getReferenceCode() + "@qq.com");
				smp.setQq(staff.getReferenceCode());

				if (arrayIDD[1] != null && !arrayIDD[1].equals("1")) {
					smp.setOrganizationID(arrayIDD[1]);
					String hql = "from COrganization where organizationID = ?";
					smp.setOrganizationName(((COrganization) baseBeanService
							.getBeanByHqlAndParams(hql,
									new Object[] { arrayIDD[1] }))
							.getOrganizationName());
				} else {
					// 如果部门为空，则根据companyID和staffID查该人员的专岗部门
					String sql = "select c.organizationid,c.organizationname,d.postname"
							+ " from dtcos t"
							+ " left join dtcorganization c on t.organizationid = c.organizationid"
							+ " left join dt_hr_deptpost d on t.deppostid = d.deppostid"
							+ " where t.companyid = ? and t.staffid = ? and t.cosstatus = '50' and t.status = '01'";
					Object[] orgpost = (Object[]) baseBeanService
							.getObjectBySqlAndParams(sql, new Object[] {
									account.getCompanyID(), arrayIDD[0] });
					smp.setOrganizationID(orgpost[0].toString());
					smp.setOrganizationName(orgpost[1].toString());
					if (orgpost[2] != null) {
						smp.setPost(orgpost[2].toString());
					}

				}

				beans.add(smp);
			}

			CLogBook logbook = logBookService.saveCLogBook(organizationID,
					parameter, account);
			beans.add(staffMeeting);
			beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "succ";
	}
	
	
	
	// 保存会议记录

	public String saveMeetingRecord() {
		try {
			ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			CAccount account = (CAccount) session.get("account");
			String hql = "from StaffMeeting where meetingID = ?";
			StaffMeeting sm = (StaffMeeting) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{staffMeeting.getMeetingID()});
			if (photo != null) {
				String path = ServletActionContext.getRequest().getSession()
						.getServletContext().getRealPath("/");
				String photoPath = fileService.savePhoto(path, photoFileName,
						photo, account.getCompanyID(), "/office/meeting/"
								+ Utilities.getDateString(new Date(),
										"yyyy-MM-dd"));
				sm.setMrecordAttach(photoPath);
			}


			sm.setPrincipalID(account.getStaffID());
			sm.setMeetingBrief(staffMeeting.getMeetingBrief());
			sm.setMeetingMinutes(staffMeeting.getMeetingMinutes());
			sm.setMeetingSummary(staffMeeting.getMeetingSummary());
			sm.setMeetingRecord(staffMeeting.getMeetingRecord());

            baseBeanService.update(sm);			
 
			
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "succ";
	}
	
	
	
	/**
	 * 
	 * 上传会议记录录音
	 * @return
	 */
	public String uploadMeetingVideo(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from StaffMeeting where meetingID = ?";
		StaffMeeting sm = (StaffMeeting) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{staffMeeting.getMeetingID()});
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path, photoFileName,
					photo, account.getCompanyID(), "/office/meeting/"
							+ Utilities.getDateString(new Date(),
									"yyyy-MM-dd"));
			logger.info("值：{}", photoPath);
			sm.setRecordfile(photoPath);
		}
		baseBeanService.update(sm);

		
		return "succ";
		
	}
	
	
	
	/**
	 * 
	 * 
	 * 会议记录
	 * @return
	 */
	public String getRecordPage(){
		String hql = "from StaffMeeting where meetingID = ?";
		staffMeeting = (StaffMeeting) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{staffMeeting.getMeetingID()});
		
		return "record";
		
	}



	// 根据companyID 查询所有在职员工

	public String toSearchStaff() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("searchinfo", searchStaff);

		return getAllStaff();

	}

	public String getAllStaff() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String hql = "";
		List<String> params = new ArrayList<String>();
		if (searchType.equals("company")) {
			hql = "from  Staff s where exists(select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ?)";

			params.add(companyID);
			params.add("50");
		} else {
			hql = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and (c.organizationID like ? "
					+ "or exists(select d.depPostID from DepartmentPost d where d.depPostID = c.depPostID and d.leveloneOrgID like ?)))";
			params.add(companyID);
			params.add("50");
			params.add("%" + orgID + "%");
			params.add("%" + orgID + "%");

		}

		if (search != null && search.equals("search")) {
			searchStaff = (Staff) session.get("searchinfo");
			if (searchStaff != null && !searchStaff.equals("")) {

				hql += " and staffName like ?";
				params.add("%" + searchStaff.getStaffName() + "%");

			}

		}

		List<BaseBean> stafflist = baseBeanService.getListBeanByHqlAndParams(
				hql, params.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stafflist", stafflist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";

	}

	// //参会人员///////////////////

	public String toSearchParti() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("searchinfo", searchStaff);

		return getPartiStaffList();

	}

	/**
	 * 
	 * 
	 * 保存参会情况录入
	 * 
	 * @return
	 */
	public String saveSMParti() {
		try {
			String hql = "from SMParticipant where partiID = ?";
			SMParticipant smp = (SMParticipant) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { smParticipant
							.getPartiID() });
			String isAttend = smParticipant.getIsAttend();
			String islate = smParticipant.getIsLate();
			String isSpeak = smParticipant.getIsSpeak();

			smp.setIsAttend(smParticipant.getIsAttend());
			if (isAttend.equals("01")) {// 缺席

				smp.setUnAttendCause(smParticipant.getUnAttendCause());
				smp.setIsLate("02");
				smp.setLateCause(null);
				smp.setIsSpeak("02");
				smp.setSpeakContent(null);
			} else {// 出席
				smp.setUnAttendCause(null);
				smp.setIsLate(smParticipant.getIsLate());
				smp.setIsSpeak(smParticipant.getIsSpeak());

				if (islate.equals("01")) {// 迟到

					smp.setLateCause(smParticipant.getLateCause());

				} else {// 未迟到
					smp.setLateCause(null);
				}

				if (isSpeak.equals("01")) {// 发言了

					smp.setSpeakContent(smParticipant.getSpeakContent());

				} else {// 没发言
					smp.setLateCause(null);
				}

			}
			smp.setRecordDate(new Date());
			baseBeanService.update(smp);

		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return "succ";
	}

	/**
	 * 
	 * 根据会议ID获取参会人员
	 */
	public String getPartiStaffList() {

		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getPartiList());

		String hql = "from StaffMeeting where meetingID = ?";
		staffMeeting = (StaffMeeting) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { meetingID });

		return "listuser";

	}

	public DetachedCriteria getPartiList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc = DetachedCriteria.forClass(SMParticipant.class);
		dc.add(Restrictions.eq("meetingID", meetingID));
		if (search != null && !search.equals("")) {
			searchStaff = (Staff) session.get("searchinfo");
			if (searchStaff != null && !searchStaff.equals("")) {

				dc.add(Restrictions.like("staffName", searchStaff
						.getStaffName(), MatchMode.ANYWHERE));

			}
		}

		return dc;
	}
	
	
	
	
	/**
	 * 
	 * ajajx获取参会人员
	 * @return
	 */
	public String ajaxGetPartiMember(){
		
		String hql = "from SMParticipant where meetingID = ?";
		List<BaseBean> memlist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{staffMeeting.getMeetingID()});	
	    Map<String,Object> map = new HashMap<String, Object>();
	    map.put("result", memlist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
		
	}
	

	/*
	 * 
	 * 
	 * 通知提交 短信通知 邮件通知 QQ通知(待实现)
	 */
	public String toNotice() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String sms = staffMeeting.getSmsNotice();
		String email = staffMeeting.getEmailNotice();
		String qq = staffMeeting.getQqNotice();

		String hqlmeeting = "from StaffMeeting where meetingID = ?";
		StaffMeeting sm = (StaffMeeting) baseBeanService.getBeanByHqlAndParams(
				hqlmeeting, new Object[] { staffMeeting.getMeetingID() });

		String hql = "from SMParticipant where meetingID = ?";
		List<BaseBean> smplist = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { staffMeeting.getMeetingID() });
		String mobiles = "";
		String staffnames = "";
		String emails = "";
		for (BaseBean b : smplist) {

			SMParticipant smp = (SMParticipant) b;
			String number = smp.getPhoneNumber();
			if (smp.getPhoneNumber() == null) {
				number = "1";
			}
			mobiles += number + ";";

			staffnames += smp.getStaffName() + ",";
			emails += smp.getEmail() + ",";
		}
		String d = mobiles.replaceAll("[^0-9-]+", " ").trim();
		String t = d.replaceAll("[ ]+", ",");
		String content = "关于" + sm.getMeetingName() + "会议的通知<br>";
		content += "1、会议主题：" + sm.getMeetingTheme() + "<br>";
		content += "2、会议时间：" + sm.getStartDate() + "——" + sm.getEndDate()
				+ "<br>";
		content += "3、会议地点：" + sm.getMeetingPlace() + "<br>";
		content += "4、参会人员：" + staffnames + "<br>";
		content += "5、会议要求：" + sm.getMeetingRequire() + "<br>";
		content += "6、会议责任人：" + sm.getPrincipal() + ",责任人联系电话："
				+ sm.getPrincipaltel();

		if (sms.equals("on")) {
			content = content.replaceAll("<br>", "");
			smsNotice(t, content, staffnames, sm);
		}
		if (email.equals("on")) {
			emailNotice(content, emails);
		}
//		if (qq.equals("qq")) {
//			// smsNotice(t,content,staffnames,sm);
//		}
		sm.setNoticeType("01");
		baseBeanService.update(sm);

		return "success";

	}

	/**
	 * 
	 * 短信通知
	 */
	public void smsNotice(String t, String content, String staffnames,
			StaffMeeting sm) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		mobileMessage.setMobiles(t);

		mobileMessage.setMessage(content);
		try {
			String reStr = mobileMessage.sendMsg();

			// 短息通知记录
			String[] staffnamea = staffnames.split(",");
			String[] tels = t.split(",");
			for (int i = 0; i < staffnamea.length; i++) {
				TelMessage tmsg = new TelMessage();
				String telID = serverService.getServerID("telmessage");
				tmsg.setTelMessageID(telID);

				tmsg.setCompanyID(sm.getCompanyID());
				tmsg.setOrganizationID((String) session.get("organizationID"));
				tmsg.setReceiverName(staffnamea[i]);
				tmsg.setStatus("01");
				if (tels[i].equals("1")) {
					tmsg.setTelNum("系统无手机号");
					tmsg.setMsgStatus("提交失败");
				} else {
					tmsg.setTelNum(tels[i]);
					tmsg.setMsgStatus(reStr);
				}

				tmsg.setAccountID(account.getAccountID());
				tmsg.setContent(content);
				tmsg.setSendDate(new Date());
				tmsg.setOrgDetail("office");
				baseBeanService.save(tmsg);

			}

		} catch (IOException e) {

			logger.error("操作异常", e);
		}

	}

	/**
	 * 
	 * 哎，无奈之举
	 * 
	 * @return
	 */
	public String getStaffMeetingInfo() {
		String hql = "from StaffMeeting where meetingID = ?";
		StaffMeeting sm = (StaffMeeting) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { meetingID });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", sm);
		startDates = Utilities.getDateString(sm.getStartDate(),
				"yyyy-MM-dd HH:mm:ss");
		endDates = Utilities.getDateString(sm.getEndDate(),
				"yyyy-MM-dd HH:mm:ss");
		map.put("startDate", startDates);
		map.put("endDate", endDates);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";
	}

	public void emailNotice(String content, String emails) {
		String[] array = emails.split(",");
		for (int i = 0; i < array.length; i++) {
			Utilities.getReport("开会通知（重要）", content, array[i]);
		}

	}

	/**
	 * 
	 * 调整提交 包括开会时间调整、开会地点调整、取消会议
	 * 
	 * @param --type:time,type:place,type:cancel
	 * @return
	 */
	public String toSaveTiao() {

		String hql = "from StaffMeeting where meetingID = ?";
		StaffMeeting sm = (StaffMeeting) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { staffMeeting.getMeetingID() });
		
		if (type.equals("Time")) {
			
			sm.setFactStartDate(Utilities.getDateFromString(startDates,
					"yyyy-MM-dd HH:mm:ss"));
			sm.setFactEndDate(Utilities.getDateFromString(endDates,
					"yyyy-MM-dd HH:mm:ss"));
		} else if (type.equals("Place")) {
			
			sm.setFactPlace(staffMeeting.getFactPlace());
		} else {
			String hqls = "from MeetingRoomOrder where mroomoID = ?";
			MeetingRoomOrder mro = (MeetingRoomOrder) baseBeanService.getBeanByHqlAndParams(hqls,new Object[]{sm.getMroomoID()});
			
			//取消会议
			if(cancelType!=null&&cancelType.equals("on")){
				mro.setCancel("01");
				
				
			}
			mro.setQuote(null);
			baseBeanService.update(mro);
			sm.setStatus("01");
		}

		   baseBeanService.update(sm);

		return "success";

	}

	/**
	 * 
	 * 
	 * 为通知准备数据
	 * 
	 * @return
	 */
	public String reparedNotice() {
		
		String hql = "from StaffMeeting where meetingID = ?";
		StaffMeeting sm = (StaffMeeting) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { staffMeeting.getMeetingID() });
		
		
		String content = "关于" + sm.getMeetingName() + "会议的通知<br>";
		
		if (type.equals("Time")) {
			content += "会议时间临时更改为：" + sm.getFactStartDate() + "~"
					+ sm.getFactEndDate() + "请大家准时参加";
			
		} else if (type.equals("Place")) {
			content += "会议地点临时更改为：" + sm.getFactPlace() + "请大家准时参加";
			
		} else {
			content += "会议由于某种原因临时取消，望大家见谅！请大家按照自己的原工作计划进行！";
			
		}
		
		String sms = staffMeeting.getSmsNotice();
		String email = staffMeeting.getEmailNotice();
		String qq = staffMeeting.getQqNotice();

		String hql1 = "from SMParticipant where meetingID = ?";
		List<BaseBean> smplist = baseBeanService.getListBeanByHqlAndParams(
				hql1, new Object[] { staffMeeting.getMeetingID() });
		String mobiles = "";
		String staffnames = "";
		String emails = "";
		for (BaseBean b : smplist) {

			SMParticipant smp = (SMParticipant) b;
			String number = smp.getPhoneNumber();
			if (smp.getPhoneNumber() == null) {
				number = "1";
			}
			mobiles += number + ";";

			staffnames += smp.getStaffName() + ",";
			emails += smp.getEmail() + ",";
		}
		String d = mobiles.replaceAll("[^0-9-]+", " ").trim();
		String t = d.replaceAll("[ ]+", ",");

		if (sms.equals("on")) {
			content = content.replaceAll("<br>", "");
			smsNotice(t, content, staffnames, sm);
		}
		if (email.equals("on")) {
			emailNotice(content, emails);
		}
		if (qq.equals("on")) {

		}

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

	public String getCancelType() {
		return cancelType;
	}

	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}
    
}
