package mobile.tiantai.android.action;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.StaffMeeting;
import hy.ea.bo.office.VideoRoom;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mobile.tiantai.android.service.VideoConfreService;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * 手机端接口
 */
@Controller
@Scope("prototype")
public class VideoConfreAction {
	private Object result;

	@Resource
	private VideoConfreService videoconService;
	private String user;// wfj账号；
	private String roomid; // 会议号
	private int pageNumber;
	/** 页数 */
	private String sccid;
	private String companyID;
	private String orgID;
	private String parameter;
	private String meetingID;

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
	 * @param keyCode
	 * @param password_type
	 */
	public String addUserinfo() {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			Staff staff = videoconService.getStaffInfo(user);
			String res = videoconService.addUserinfo(user, "123456wfj", staff
					.getStaffName(), 0, "2", staff.getSex() == "男" ? "0"
					: staff.getSex() == "女" ? "1" : "", user, "", "", 0);
			map.put("result", res);

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";
	}

	/**
	 * /**
	 * 
	 * @param staffMeeting
	 *            会议信息
	 * @param sccid
	 *            创建人微分金账号
	 * @param chID
	 *            参会人拼接staffID,staffName,account#staffID,staffName,account
	 */
	public String addRoominfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		ServletRequest request = ServletActionContext.getRequest();
		String meetingID = request.getParameter("meetingID");
		String meetingName = request.getParameter("meetingName");
		String meetingTheme = request.getParameter("meetingTheme");
		String startDates = request.getParameter("startDates");
		String endDates = request.getParameter("endDates");
		String meetingpsw = request.getParameter("meetingpsw");
		String chairpsw = request.getParameter("chairpsw");
		String chID = request.getParameter("chID");
		String state = request.getParameter("state");
	

		try {
			videoconService.saveMeeting(meetingID, meetingName, meetingTheme,
					startDates, endDates, meetingpsw, chairpsw, sccid, chID,
					state,"","");
			map.put("result", "0000");

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";

	}

	/**
	 * 通过会议室给用户名授权
	 * 
	 * @param arrUserNameandRight
	 * @param roomId
	 */
	public String doUserRightByRoomid() {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String res = videoconService.doUserRightByRoomid(user + ",2",
					Integer.parseInt(roomid));
			map.put("result", res);

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";
	}

	/**
	 * 获取用户有权限的会议室列表（用户名、密钥）
	 * 
	 * @param userName
	 */
	public String getRoominfoByUserName() {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<BaseBean> list = videoconService.getRoominfoByUserName(user);
			
			List<Object> lists = new ArrayList<Object>();
			if (list != null && list.size() > 0) {
				for (int i = 0; i <list.size(); i++) {
					VideoRoom obj = (VideoRoom)list.get(i);
					JSONObject jsonObj = new JSONObject();
					jsonObj.accumulate("roomid", obj.getRoomId());
					jsonObj.accumulate("meetingName", obj.getRoomName());
					jsonObj.accumulate("curusercount", obj.getCurUserCount());//当前
					jsonObj.accumulate("maxusercount", obj.getMaxUserCount());//总共
					jsonObj.accumulate("meetingpsw", videoconService.getRoomPsw(obj.getRoomId()));//会议室密码
					 
					lists.add(jsonObj);
				}
			}
			map.put("result", lists);

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";
	}

	/**
	 * 获取会议室密码
	 */
	public String getRoomPsw() {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String psw = videoconService.getRoomPsw(roomid);
			map.put("result", psw);

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";
	}

	/**
	 * 
	 * 我创建的视频会议
	 */
	public String myCreatedRoom() {

		if (pageNumber == 0) {
			pageNumber = 1;
		}
		PageForm pageForm = videoconService
				.myCreatedRoom(pageNumber, 20, sccid,"","");
		JSONObject jsonObjList = new JSONObject();
		List<Object> lists = new ArrayList<Object>();
		if (pageForm != null && pageForm.getList().size() > 0) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
				StaffMeeting obj = (StaffMeeting) pageForm.getList().get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("meetingName", isNull(obj.getMeetingName()));
				jsonObj.accumulate("roomid", isNull(obj.getRoomid() + ""));
				jsonObj.accumulate("startDate", Utilities.getDateString(
						obj.getStartDate(), "yyyy-MM-dd HH:mm:ss"));
				jsonObj.accumulate("endDate", Utilities.getDateString(
						obj.getEndDate(), "yyyy-MM-dd HH:mm:ss"));
				jsonObj.accumulate("meetingID", obj.getMeetingID());
				jsonObj.accumulate("state", obj.getNoticeType());// 00草稿：01:已通知
				lists.add(jsonObj);
			}
		}

		jsonObjList.accumulate("list", lists);
		jsonObjList.accumulate("pageNumber",
				pageForm == null ? "1" : pageForm.getPageNumber());
		jsonObjList.accumulate("pageCount",
				pageForm == null ? "0" : pageForm.getPageCount());
		jsonObjList.accumulate("recordCount",
				pageForm == null ? "0" : pageForm.getRecordCount());

		result = jsonObjList;
		return "success";

	}

	/**
	 * 
	 * 删除视频会议 咱们你服务器假删，好视通服务器真删
	 * 
	 * @return
	 */
	public String deleteRoom() {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String code = videoconService.delRoominfo(meetingID);
			map.put("result", code);

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";

	}

	/**
	 * 是否有参会人可选择也就是是否入职公司
	 * 
	 * @return
	 */
	public String haveMeeter() {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String code = videoconService.haveMeeter(sccid, user);
			map.put("result", code);

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";

	}

	/**
	 * 
	 * 查询公司列表
	 * 
	 * @param sccid
	 * @return
	 */
	public String getCompanyList() {

   if(sccid==null||sccid.equals("")) {
	   HttpSession session = ServletActionContext.getRequest().getSession();
	   SessionWrap sw = SessionWrap.getInstance();

	   TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
			   SessionWrap.KEY_SHOPCUSCOM);
	   if(tc!=null) {
		   sccid = tc.getSccId();
	   }

   }
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<Object> companylist = videoconService.getCompanyList(sccid);
			
			
			List<Object> lists = new ArrayList<Object>();
			if (companylist != null && companylist.size() > 0) {
				for (int i = 0; i <companylist.size(); i++) {
					Object[]  obj = (Object[])companylist.get(i);
					JSONObject jsonObj = new JSONObject();
					jsonObj.accumulate("companyID", isNull(obj[0]));
					jsonObj.accumulate("companyName", isNull(obj[1]));
					jsonObj.accumulate("logo", isNull(obj[2]));
					lists.add(jsonObj);
				}
			}
			map.put("result", lists);
			
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";

	}

	/**
	 * 根据公司ID获取一级部门
	 * 
	 * @param companyID
	 * @return
	 */
	public String getOrgList() {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<BaseBean> orglist = videoconService.getOrgList(companyID);
			List<Object> lists = new ArrayList<Object>();
			if (orglist != null && orglist.size() > 0) {
				for (int i = 0; i <orglist.size(); i++) {
					COrganization ct = (COrganization)orglist.get(i);
					JSONObject jsonObj = new JSONObject();
					jsonObj.accumulate("orgID", isNull(ct.getOrganizationID()));
					jsonObj.accumulate("orgName", isNull(ct.getOrganizationName()));
					lists.add(jsonObj);
				}
			}
			map.put("result", lists);

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";

	}

	/**
	 * 根据部门查人
	 * 
	 * @param orgID
	 *            部门ID
	 * @param companyID
	 *            公司ID
	 * @param sccid
	 *            登录账号ID
	 * @param parameter
	 *            查询条件 姓名 手机号
	 * @return
	 */

	public String getPersonList() {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<Object> stafflist = videoconService.getPersonList(orgID,
					companyID, parameter, sccid);
			List<Object> lists = new ArrayList<Object>();
			if (stafflist != null && stafflist.size() > 0) {
				for (int i = 0; i <stafflist.size(); i++) {
					Object[]  obj = (Object[])stafflist.get(i);
					JSONObject jsonObj = new JSONObject();
					jsonObj.accumulate("staffID", isNull(obj[0]));
					jsonObj.accumulate("staffName", isNull(obj[1]));
					jsonObj.accumulate("tel", isNull(obj[2]));
					jsonObj.accumulate("headimage", isNull(obj[3]));
					if((companyID==null||companyID.equals(""))&&(sccid!=null&&!sccid.equals(""))&&parameter!=null&&!parameter.equals("")) {
						jsonObj.accumulate("companyname", isNull(obj[4]));
						jsonObj.accumulate("organizationname", isNull(obj[5]));
						jsonObj.accumulate("companyid", isNull(obj[6]));
						jsonObj.accumulate("organizationid", isNull(obj[7]));

					}
					lists.add(jsonObj);
				}
			}
			map.put("result", lists);

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";
	}

	/**
	 * 
	 * 发布通知
	 * 
	 * @param meetingID
	 * @return
	 */
	public String pubNotice() {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String roomid = videoconService.pubNotice(meetingID);
			map.put("result", roomid);

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";

	}

	/**
	 * 
	 * 编辑获取原内容
	 * 
	 * @param meetingID
	 * @return
	 */
	public String getEditRoomInfo() {
		Map<String, Object> map = null;

		try {
			map = videoconService.getEditRoomInfo(meetingID);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";

	}

	/**
	 * null转空字符串
	 * 
	 * @param tep
	 * @return
	 */
	private Object isNull(Object tep) {
		if (tep == null || "null".equals(tep)) {
			return "";
		} else {
			return tep;
		}
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSccid() {
		return sccid;
	}

	public void setSccid(String sccid) {
		this.sccid = sccid;
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

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getMeetingID() {
		return meetingID;
	}

	public void setMeetingID(String meetingID) {
		this.meetingID = meetingID;
	}

}
