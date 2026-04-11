package mobile.tiantai.android.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hst.App;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.SMParticipant;
import hy.ea.bo.office.StaffMeeting;
import hy.ea.bo.office.VideoRoom;
import hy.ea.util.MobileMessage;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mobile.tiantai.android.service.VideoConfreService;
import mobile.tiantai.android.util.JushMain;
import net.sf.json.JSONObject;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.wechatpay.utils.GetWxOrderno;

@Service
public class VideoConfreServiceImpl implements VideoConfreService {

	// 调用时改为自己服务器地址端口
	private String wsUrl = "https://47.94.154.17:8443/api/v1/";


	@Resource
	private BaseBeanDao baseBeanDao;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Autowired
	private MobileMessage msage;

	/**
	 * 添加用户 用户名、密码、昵称、组织架构、角色、性别、手机号、电话、邮箱、密钥、密码类型
	 * 
	 * @param userName
	 * @param passpwd
	 * @param nickName
	 * @param departID
	 * @param adminRole
	 * @param sex
	 * @param mobile
	 * @param telePhone
	 * @param email

	 * @param password_type
	 */
	public String addUserinfo(String userName, String passpwd, String nickName,
			Integer departID, String adminRole, String sex, String mobile,
			String telePhone, String email, Integer password_type)
			throws Exception {

		String path = "user/add";
      Map<String,Object> map = new HashMap<>();

		map.put("userName",userName);
		map.put("password",passpwd);
		map.put("nickName",nickName);
		map.put("departID",departID);
		map.put("adminRole",adminRole);
		map.put("sex",sex);
		map.put("mobile",mobile);
		map.put("telePhone",telePhone);
		map.put("email",email);
		map.put("password_type",password_type);
		String requestParam = JSON.toJSONString(App.removeMapEmptyValue(map));
		JSONObject jsonObject = App.hstHttpV1(path,"POST",requestParam);
		return jsonObject.getString("code").equals("0")?"0000":jsonObject.getString("code");

	}

	/**
	 * 快捷添加会议室 （房间名、登录校验模型、会议室密码、允许最大用户数、是否允许管理员密码、管理员密码、密钥）
	 * 
	 * @param roomName
	 * @param verifyMode
	 * @param password
	 * @param maxUserCount
	 * @param enableChairPwd
	 * @param chairPassword
	 * 接口：room/addRoomInfo
	 */
	private String addRoominfo(String roomName, String verifyMode,
			String password, Integer maxUserCount, String enableChairPwd,
			String chairPassword) throws Exception {

		String path = "room/addRoomInfo";


		VideoRoom  videoRoom = new VideoRoom();
		videoRoom.setRoomName(roomName);
		videoRoom.setVerifyMode(verifyMode);
		videoRoom.setPassword(password);
		videoRoom.setMaxUserCount(maxUserCount+"");
		videoRoom.setEnableChairPwd(enableChairPwd);
		videoRoom.setChairPassword(chairPassword);


		String requestParam = JSON.toJSONString(videoRoom);

		JSONObject jsonObject = App.hstHttpV1(path,"POST",requestParam);
		JSONObject obj = jsonObject.getJSONObject("data");
		String roomId = obj.getString("roomId");

		return roomId;

	}

	/**
	 * 通过会议室给用户名授权
	 * 
	 * @param arrUserNameandRight
	 * @param roomId

	 */
	public String doUserRightByRoomid(String arrUserNameandRight, Integer roomId)
			throws Exception {
		String path = "room/authUser";

		Map<String,Object> map = new HashMap<>();

		map.put("roomUserStr",arrUserNameandRight);
		map.put("roomId",roomId);

		String requestParam = JSON.toJSONString(App.removeMapEmptyValue(map));
		JSONObject jsonObject = App.hstHttpV1(path,"POST",requestParam);

		return jsonObject.getString("code").equals("0")?"0000":jsonObject.getString("code");


	}

	/**
	 * 获取用户有权限的会议室列表（用户名、密钥）
	 * 
	 * @param userName

	 */
	public List<BaseBean> getRoominfoByUserName(String userName) {
		List<BaseBean> list = new ArrayList<BaseBean>();
		String path = "room/roomList/";

		JSONObject jsonObject = App.hstHttpV1(path+userName,"GET","");
		net.sf.json.JSONArray jsonArray = jsonObject.getJSONArray("data");

		try {
		for (Object o : jsonArray) {
			JSONObject bo = JSONObject.fromObject(o);
			VideoRoom videoRoom = new VideoRoom();
			videoRoom.setCurUserCount(bo.get("curUserCount")+"");
			videoRoom.setHopeEndTime(bo.getString("hopeEndTime"));
			videoRoom.setHopeStartTime(bo.getString("hopeStartTime"));
			videoRoom.setMaxUserCount(bo.getString("maxUserCount"));
			videoRoom.setRoomId(bo.getString("roomId"));
			videoRoom.setRoomName(bo.getString("roomName"));
			videoRoom.setRoomType(bo.getString("roomType"));
			videoRoom.setVerifyMode(bo.getString("verifyMode"));
			list.add(videoRoom);
		}


		} catch (Exception e) {
			logger.error("操作异常", e);

		}

		return list;
	}

	/**
	 * 获取会议室密码
	 */
	public String getRoomPsw(String roomId) {

		String hql = "from StaffMeeting where roomid = ?";

		StaffMeeting staffMeeting = (StaffMeeting) baseBeanDao
				.getBeanByHqlAndParams(hql, new Object[] { roomId });
		String password = "";
		if (staffMeeting != null && staffMeeting.getMeetingpsw() != null
				&& !staffMeeting.getMeetingpsw().equals("")) {
			password = staffMeeting.getMeetingpsw();
		}

		return password;
	}
	
	
	/**
	 * 获取会议室以及密码
	 */
	public String getRoom(String roomId) {

		String hql = "from StaffMeeting where roomid = ? and noticeType = ?";

		StaffMeeting staffMeeting = (StaffMeeting) baseBeanDao
				.getBeanByHqlAndParams(hql, new Object[] { roomId,"01"});
		String password = null;
		if (staffMeeting != null) {
			if (staffMeeting.getMeetingpsw() != null
					&& !staffMeeting.getMeetingpsw().equals("")) {
				password = staffMeeting.getMeetingpsw();
			}else{
				password = "";
			}
		}

		return password;
	}


	/**
	 * 删除房间根据房间ID
	 * 
	 * @param meetingID
	 * @return
	 */
	@Transactional
	public String delRoominfo(String meetingID) {

		// 微分金服务器
		StaffMeeting stafMeeting = (StaffMeeting) baseBeanDao
				.getBeanByHqlAndParams("from StaffMeeting where meetingID = ?",
						new Object[] { meetingID });
		stafMeeting.setStatus("09");
		baseBeanDao.update(stafMeeting);
		String code = "0000";

		if (stafMeeting.getRoomid() != null
				&& !stafMeeting.getRoomid().equals("")) {
			// 第三方调用
			Map<String, String> mp = null;
			try {
				String path = "room/delRoomInfo";

				Map<String,Object> map = new HashMap<>();

				map.put("roomId",stafMeeting.getRoomid());

				String requestParam = JSON.toJSONString(App.removeMapEmptyValue(map));
				JSONObject jsonObject = App.hstHttpV1(path,"POST",requestParam);


				code = jsonObject.getString("code").equals("0")?"0000":jsonObject.getString("code");
			} catch (Exception e) {
				logger.error("操作异常", e);

			}
		}
		return code;
	}

	/**
	 * 
	 * 调用请求服务器
	 * 
	 * @param object
	 * @param method
	 * @return
	 */
	@SuppressWarnings("unused")
	private String ClientRequest(Object[] object, String method) {
		String code = "";
		Object[] res = null;
		try {
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory
					.newInstance();
			Client client = dcf.createClient(new URL(wsUrl));
			res = client.invoke(method, object);

		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return res[0].toString();
	}

	/**
	 * 
	 * 根据用户微分金账号获取人员信息
	 * 
	 * @param user
	 * @return
	 */
	public Staff getStaffInfo(String user) {
		String hql = "from Staff f where f.staffID = (select t.staffid from TEshopCustomer t where t.account = ?)";
		Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams(hql,
				new Object[] { user });

		return staff;
	}

	/**
	 * 
	 * 根据PC系统登录用户ID查询APP用户信息
	 * 
	 * @param staffID
	 * @return
	 */
	public TEshopCusCom getUserInfo(String staffID) {

		String hql = "from TEshopCusCom t where t.sccId = (select f.sccid from Staff f where f.staffID = ?)";
		TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams(hql,
				new Object[] { staffID });
		return tc;

	}

	/**
	 * 
	 * 我创建的视频会议
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sccid
	 * @return
	 */
	public PageForm myCreatedRoom(int pageNumber, int pageSize, String sccid,
			String staffID, String companyID) {
		String principalID = "";
		PageForm pageForm = null;
		if (staffID == null || staffID.equals("")) {
			TEshopCusCom tc = (TEshopCusCom) baseBeanDao
					.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?",
							new Object[] { sccid });

			List<BaseBean> list = baseBeanDao
					.getListBeanByHqlAndParams(
							"from CAccount t where t.staffID in(select f.staffID from Staff f where f.sccid = ?)",
							new Object[] { sccid });
			if (list.size() == 0) {
				principalID = tc.getStaffid();
			} else if (list.size() == 1) {
				principalID = ((CAccount) list.get(0)).getStaffID();
			} else if (list.size() > 1) {
				for (int i = 0; i < list.size(); i++) {
					CAccount account = (CAccount) list.get(i);
					if (account != null) {
						if (account.getAccountEmail().equals("sa")) {
							principalID = account.getStaffID();
							break;
						}

					}
				}
				if (principalID.equals("")) {
					principalID = tc.getStaffid();
				}
			}

			String hql = "from StaffMeeting where meetingType = '01' and (principalID = ? or principalID = ?)and status = ? order by updateTime desc";
			pageForm = baseBeanService.getPageForm(pageNumber, pageSize, hql,
					new Object[] { principalID, tc.getStaffid(), "00" });
		} else {
			principalID = staffID;

			String hql = "from StaffMeeting where meetingType = '01' and principalID = ? and status = ? and companyID = ? order by updateTime desc";
			pageForm = baseBeanService.getPageForm(pageNumber, pageSize, hql,
					new Object[] { principalID, "00", companyID });
		}

		return pageForm;
	}

	/**
	 * 是否有参会人可选择也就是是否入职公司
	 * 
	 * @param sccid
	 * @return
	 */
	public String haveMeeter(String sccid, String user) {
		List<BaseBean> list = baseBeanDao
				.getListBeanByHqlAndParams(
						"from CAccount t where t.staffID in(select f.staffID from Staff f where f.sccid = ? or f.reference = ?)",
						new Object[] { sccid, user });
		if (list.size() == 0) {
			return "00";// 无公司
		} else {
			return "01";// 有公司
		}

	}

	/**
	 * 
	 * 查询公司列表
	 * 
	 * @param sccid
	 * @return
	 */
	public List<Object> getCompanyList(String sccid) {
		StringBuffer str = new StringBuffer();
		List<String> params = new ArrayList<String>();

		str.append("select y.companyID,y.companyName,cn.logopath from dtCompany y,dt_ccom_com c,Dtcontactcompany cn where y.companyid  = c.compnay_id and c.ccompany_id = cn.ccompanyid and y.groupCompanySn in(");
		str.append(" select com.groupCompanySn from T_ESHOP_CUSCOM cus, dtCompany com where cus.companyid = com.companyid ");
		str.append(" and  cus.sccid = ? union  select com.groupCompanySn from ");
		str.append(" dtCompany com,dtcos cos,T_ESHOP_CUSCOM cus, dt_hr_Staff staff where staff.staffid = cos.staffid and (staff.reference = cus.account or staff.sccid = cus.sccid) and cos.companyid=com.companyid and cos.status='01'");
		str.append("  and cus.sccid = ? )");

		params.add(sccid);
		params.add(sccid);
		List<Object> companylist = baseBeanService.getListBeanBySqlAndParams(
				str.toString(), params.toArray());

		return companylist;
	}

	/**
	 * 根据公司ID获取一级部门d
	 * 
	 * @param companyID
	 * @return
	 */
	public List<BaseBean> getOrgList(String companyID) {
		String hql1 = "from COrganization where companyID = ? and organizationPID = ? and Status ='00' order by organizationNumber";
		List<BaseBean> orgaizationlist = baseBeanService
				.getListBeanByHqlAndParams(hql1, new Object[] { companyID,
						companyID });

		return orgaizationlist;
	}

	/**
	 * 根据部门查人
	 * 
	 * @param orgID
	 *            全局查询时为空
	 * @param companyID
	 *            不为空
	 * @param parameter
	 *            查询条件 姓名或者手机号
	 * @return
	 */

	public List<Object> getPersonList(String orgID, String companyID,
			String parameter, String sccid) {

		TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams(
				"from TEshopCusCom where sccId = ?", new Object[] { sccid });
		List<Object> params = new ArrayList<Object>();
		List<Object> stafflist = null;
		StringBuffer str = new StringBuffer();
		if (orgID != null && !orgID.equals("")) { // 部门里找人
		//	if(tc!=null) {
//				str.append("select s.staffID,s.staffName,nvl(t.account,s.reference),s.headimage from dt_hr_Staff s left join t_Eshop_Cuscom t on s.sccid = t.sccid  where (s.sccid is not null or s.reference is not null) and s.reference!=? and t.account!=? and exists (select staffID from dtCOS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and (c.organizationID = ? "
//						+ "or exists(select d.depPostID from dt_hr_deptpost d where d.depPostID = c.depPostID and d.leveloneOrgID = ?)))");
//				params.add(tc.getAccount());
//				params.add(tc.getAccount());
		//	}else{
				str.append("select s.staffID,nvl(s.realname,s.staffName),nvl(t.account,s.reference),s.headimage from dt_hr_Staff s left join t_Eshop_Cuscom t on s.sccid = t.sccid  where (s.sccid is not null or s.reference is not null) and  exists (select staffID from dtCOS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and (c.organizationID = ? "
						+ "or exists(select d.depPostID from dt_hr_deptpost d where d.depPostID = c.depPostID and d.leveloneOrgID = ?)))");

			//}
			params.add(companyID);
			params.add("50");
			params.add(orgID);
			params.add(orgID);
			if (parameter != null && !parameter.equals("")) {
				str.append(" and (s.staffName = ? or s.reference = ? or t.account = ?)");
				params.add(parameter);
				params.add(parameter);
				params.add(parameter);
			}

		} else {// 全局查询返回还是在集团
				// sccid 和 parameter 必不为空
			if(tc!=null) {
				str.append("select s.staffID,nvl(s.realname,s.staffName),nvl(t.account,s.reference),s.headimage,yy.companyname,n.organizationname,yy.companyid,n.organizationid from dt_hr_Staff s  left join t_Eshop_Cuscom t on s.sccid = t.sccid");

				str.append(" left join dtCOS coss on coss.staffid = s.staffid");
				str.append(" and coss.Cosstatus ='50' left join dtCompany yy on coss.companyid = yy.companyid  left join Dtcorganization n on n.organizationid = coss.organizationid");

				str.append(" where (s.sccid is not null or s.reference is not null) and exists (select staffID from dtCOS c where c.staffID=s.staffID   and cosStatus = ? and companyID in (");



				str.append("select y.companyID from dtCompany y where y.groupCompanySn in(");
				str.append(" select com.groupCompanySn from T_ESHOP_CUSCOM cus, dtCompany com where cus.companyid = com.companyid ");
				str.append(" and  cus.sccid = ? union  select com.groupCompanySn from");
				str.append(" dtCompany com,dtcos cos,T_ESHOP_CUSCOM cus, dt_hr_Staff staff where staff.staffid = cos.staffid and (staff.reference = cus.account or staff.sccid = cus.sccid) and cos.companyid=com.companyid and cos.status='01'");
				str.append("  and  cus.sccid = ? )))");



				params.add("50");
				params.add(sccid);
				params.add(sccid);

				if (parameter != null && !parameter.equals("")) {
					str.append(" and (s.staffName = ? or s.reference = ? or t.account = ? or s.realname  = ?)");
					params.add(parameter);
					params.add(parameter);
					params.add(parameter);
					params.add(parameter);
				}
			}else{
				if(parameter!=null&&!parameter.equals("")) {
					str.append("select s.staffID, nvl(s.realname,s.staffName), nvl(t.account, s.reference), s.headimage");
					str.append(" from t_Eshop_Cuscom t  left join  dt_hr_Staff s on t.staffid = s.staffid  and length(t.account)=11");
					str.append(" where 1=1");

					if (parameter != null && !parameter.equals("")) {
						str.append(" and (s.staffName = ?  or t.account = ? or s.realname  = ?)");
						params.add(parameter);
						params.add(parameter);
						params.add(parameter);
					}
				}



			}

		}
		stafflist = baseBeanService.getListBeanBySqlAndParams(str.toString(),
				params.toArray());

		return stafflist;
	}

	/**
	 * 
	 * @param staffMeeting
	 *            会议信息
	 * @param sccid
	 *            创建人微分金账号
	 * @param chID
	 *            参会人拼接staffID,staffName,account#staffID,staffName,account
	 */
	public void saveMeeting(String meetingID, String meetingName,
			String meetingTheme, String startDates, String endDates,
			String meetingpsw, String chairpsw, String sccid, String chID,
			String state, String companyID, String staffID) {

		try {

			StaffMeeting staffMeeting = null;
			if (meetingID == null || "".equals(meetingID)) {
				staffMeeting = new StaffMeeting();
				staffMeeting.setMeetingID(serverService
						.getServerID("meetingID"));
			} else {
				String hql2 = "from StaffMeeting where meetingID=?";
				staffMeeting = (StaffMeeting) baseBeanService
						.getBeanByHqlAndParams(hql2, new Object[] { meetingID });
			}
			List<BaseBean> beans = new ArrayList<BaseBean>();

			staffMeeting.setNoticeType(state);
			staffMeeting.setStatus("00");

			staffMeeting.setUpdateTime(new Date());
			staffMeeting.setMeetingpsw(meetingpsw);
			staffMeeting.setChairpsw(chairpsw);
			staffMeeting.setMeetingName(meetingName);
			staffMeeting.setMeetingTheme(meetingTheme);
			staffMeeting.setMeetingType("01");
			// 处理日期

			staffMeeting.setStartDate(Utilities.getDateFromString(startDates,
					"yyyy-MM-dd HH:mm:ss"));
			staffMeeting.setEndDate(Utilities.getDateFromString(endDates,
					"yyyy-MM-dd HH:mm:ss"));

			// 创建人微分金账号
			TEshopCusCom tc = (TEshopCusCom) baseBeanDao
					.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?",
							new Object[] { sccid });

			// 账号如果自己创建公司 优先创建到自己的公司里
			if (staffID == null || staffID.equals("")) {
				if (tc.getCompanyId() != null && !tc.getCompanyId().equals("")) {
					staffMeeting.setPrincipalID(tc.getStaffid());
					staffMeeting.setCompanyID(tc.getCompanyId());
				} else {

					// 如果自己没有公司入职到其他公司随便找一个
					StringBuffer str = new StringBuffer();
					List<String> params = new ArrayList<String>();

					str.append(" select staff.staffid,com.companyid from  dt_ccom_com d, ");
					str.append(" dtCompany com,dtcos cos,T_ESHOP_CUSCOM cus, dt_hr_Staff staff where staff.staffid = cos.staffid and (staff.reference = cus.account or staff.sccid = cus.sccid) and cos.companyid=com.companyid and cos.status='01' and com.companyid=d.compnay_id ");
					str.append("  and  cus.sccid = ? ");

					params.add(sccid);
					List<Object> companylist = baseBeanService
							.getListBeanBySqlAndParams(str.toString(),
									params.toArray());

					if (companylist.size() > 0) {
						Object[] obj = (Object[]) companylist.get(0);
						staffMeeting.setPrincipalID(obj[0].toString());
						staffMeeting.setCompanyID(obj[1].toString());
					} else {
						// 如果自己没入职任何公司 就不存储公司信息，以个人名义存储
						staffMeeting.setPrincipalID(tc.getStaffid());
					}
				}
			} else {
				staffMeeting.setPrincipalID(staffID);
				staffMeeting.setCompanyID(companyID);
			}

			String hqlstaff = "from Staff where staffID = ?";
			Staff pstaff = (Staff) baseBeanService.getBeanByHqlAndParams(
					hqlstaff, new Object[] { staffMeeting.getPrincipalID() });

			staffMeeting.setPrincipal(pstaff.getStaffName());
			staffMeeting.setPrincipaltel(tc.getAccount());

			String userRole = tc.getAccount() + ",3#";
			String mobiles = "";
			String hqldelete = "delete SMParticipant where meetingID = ?";
			if (chID != null && !chID.equals("")) {

				String[] arrayID = chID.split("-");

				SMParticipant smp = null;

				for (int i = 0; i < arrayID.length; i++) {
					String[] arrayIDD = arrayID[i].split(",");

					smp = new SMParticipant();
					smp.setPartiID(serverService.getServerID("partiID"));
					smp.setMeetingID(staffMeeting.getMeetingID());
					smp.setStaffID(arrayIDD[0]);
					smp.setStaffName(arrayIDD[1]);
					smp.setPhoneNumber(arrayIDD[2]);

					smp.setIsAttend("00");
					smp.setIsLate("00");
					smp.setIsSpeak("00");

					beans.add(smp);
					userRole += arrayIDD[2] + ",2#";
					mobiles += arrayIDD[2] + ",";
					addUserinfo(arrayIDD[2], "123456wfj",
							arrayIDD[1], 0, "2", "",arrayIDD[2], "", "", 0);
				}

				userRole = userRole.substring(0, userRole.length() - 1);

			}

			// 直接发布通知
			if ("01".equals(state)) {
				// 第三方创建会议
				String roomid = addRoominfo(
						staffMeeting.getMeetingName(),
						meetingpsw != null && !meetingpsw.equals("") ? "2"
								: "1",
						meetingpsw != null && !meetingpsw.equals("") ? meetingpsw
								: "", 100,
						chairpsw != null && !chairpsw.equals("") ? "1" : "0",
						chairpsw != null && !chairpsw.equals("") ? chairpsw
								: "");
				if (!"".equals(roomid)) {
					staffMeeting.setRoomid(roomid);
					if (chID != null && !chID.equals("")) {
						// 给用户授权
						String code = doUserRightByRoomid(userRole,
								Integer.parseInt(roomid));
						if ("0000".equals(code)) {
							// 授权成功消息推送，短信推送通知会议
							pushMessage(tc.getAccount(), mobiles, staffMeeting);
						}
					}
				}

			}

			beans.add(staffMeeting);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
					new String[] { hqldelete },
					new Object[] { staffMeeting.getMeetingID() });
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

	}

	/**
	 * 
	 * 消息通知
	 * 
	 * @param user
	 * @param mobiles
	 * @param sm
	 */
	private void pushMessage(String user, String mobiles, StaffMeeting sm) {

		String startDate = Utilities.getDateString(sm.getStartDate(),
				"yyyy-MM-dd HH:mm:ss");
		String edDate = Utilities.getDateString(sm.getEndDate(),
				"yyyy-MM-dd HH:mm:ss");

		String content = "视频会议的通知:";
		content += "会议名称：" + sm.getMeetingName();
		content += ",会议主题：" + sm.getMeetingTheme();
		content += ",会议时间：" + startDate + "到" + edDate;
		content += ",会议责任人：" + sm.getPrincipal() + ",责任人联系电话：" + user;
		content += "望准时参加，您可以登录数字地球APP进入 我的-视频会议,进入会议";
		System.out.print(content);

		try {
			msage.setMobiles(mobiles);
			msage.setMessage(content);
			msage.sendMsg("【微分金平台】");
		} catch (IOException e) {
			logger.error("操作异常", e);
		}

		List<String> slist = new ArrayList<String>();// 极光推送设备号
		mobiles += user;
		// 保存账号
		String[] arr = mobiles.split(",");
		slist = Arrays.asList(arr);
		// 极光推送
		JushMain.sendjiguangMessage("您有一个视频会议通知会议时间：" + startDate, "会议通知",
				"meeting", "meeting", slist);
	}

	/**
	 * 
	 * 发布通知
	 * 
	 * @param meetingID
	 * @return
	 */
	public String pubNotice(String meetingID) {
		// 第三方创建会议
		String roomid = "";
		List<BaseBean> beans = new ArrayList<BaseBean>();

		String hql = "from StaffMeeting where meetingID = ?";
		StaffMeeting staffMeeting = (StaffMeeting) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { meetingID });
		staffMeeting.setNoticeType("01");
		List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(
				"from SMParticipant where meetingID = ?",
				new Object[] { meetingID });

		String userRole = staffMeeting.getPrincipaltel() + ",3#";
		String mobiles = "";
		for (int i = 0; i < list.size(); i++) {
			SMParticipant sm = (SMParticipant) list.get(i);
			userRole += sm.getPhoneNumber() + ",2#";
			mobiles += sm.getPhoneNumber() + ",";
		}

		userRole = userRole.substring(0, userRole.length() - 1);
		String meetingpsw = staffMeeting.getMeetingpsw();
		String chairpsw = staffMeeting.getChairpsw();

	
		try {
			roomid = addRoominfo(staffMeeting.getMeetingName(),
					meetingpsw != null && !meetingpsw.equals("") ? "2" : "1",
					meetingpsw != null && !meetingpsw.equals("") ? meetingpsw
							: "", 100,
					chairpsw != null && !chairpsw.equals("") ? "1" : "0",
					chairpsw != null && !chairpsw.equals("") ? chairpsw : "");

			if (!"".equals(roomid)) {
				staffMeeting.setRoomid(roomid);
				// 给用户授权
				String code = doUserRightByRoomid(userRole,
						Integer.parseInt(roomid));
				if ("0000".equals(code)) {
					// 授权成功消息推送，短信推送通知会议
					pushMessage(staffMeeting.getPrincipaltel(), mobiles,
							staffMeeting);
				}
			}
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		beans.add(staffMeeting);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
     return roomid;
	}

	/**
	 * 
	 * 编辑获取原内容
	 * 
	 * @param meetingID
	 * @return
	 */
	public Map<String, Object> getEditRoomInfo(String meetingID) {
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from StaffMeeting where meetingID = ?";
		StaffMeeting staffMeeting = (StaffMeeting) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { meetingID });
		List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(
				"from SMParticipant where meetingID = ?",
				new Object[] { meetingID });
		List<Object> lists = new ArrayList<Object>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				SMParticipant smPart = (SMParticipant) list.get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("staffID", smPart.getStaffID());
				jsonObj.accumulate("staffName", smPart.getStaffName());
				jsonObj.accumulate("tel", smPart.getPhoneNumber());
				lists.add(jsonObj);
			}
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.accumulate("meetingName", staffMeeting.getMeetingName());
		jsonObj.accumulate("meetingTheme", staffMeeting.getMeetingTheme());
		jsonObj.accumulate("startDate", Utilities.getDateString(
				staffMeeting.getStartDate(), "yyyy-MM-dd HH:mm:ss"));
		jsonObj.accumulate("endDate", Utilities.getDateString(
				staffMeeting.getEndDate(), "yyyy-MM-dd HH:mm:ss"));
		jsonObj.accumulate("meetingID", staffMeeting.getMeetingID());
		jsonObj.accumulate("state", staffMeeting.getNoticeType());// 00草稿：01:已通知
		jsonObj.accumulate(
				"meetingpsw",
				staffMeeting.getMeetingpsw() != null ? staffMeeting
						.getMeetingpsw() : "");
		jsonObj.accumulate("chairpsw",
				staffMeeting.getChairpsw() != null ? staffMeeting.getChairpsw()
						: "");

		map.put("staffMeeting", jsonObj);
		map.put("smlist", lists);
		return map;

	}
	
	/**
	  * 返回会议信息
	  * 
	  * @param roomid
	  * @return
	  */
	 public StaffMeeting getRoomInfo(String roomid){
		 String hql = "from StaffMeeting where roomid = ?";
		 StaffMeeting staffMeeting = (StaffMeeting)baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{roomid});
	 
	     return staffMeeting;
	 }

}
