package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.StaffMeeting;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mobile.tiantai.android.service.VideoConfreService;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
/*
 * 视频会议PC端
 * */
@Controller
@Scope("prototype")
public class VideoMeetingAction {
	private static final Logger logger = LoggerFactory.getLogger(VideoMeetingAction.class);
	@Resource
	private VideoConfreService videoconService;
	@Resource
	private BaseBeanService baseBeanSerivce;
	private List<BaseBean> list;
	private PageForm pageForm;
	private String roomid;
	private String result;
	private String user;
	private String orgID;
	private String companyID;
	private StaffMeeting staffMeeting;
	private String  startDates;
	private String endDates;
	private String chID;//参会人
	private int pageNumber;
	private String sccid;

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

		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Map<String, Object> map = new HashMap<String, Object>();
		if(account==null){

			map.put("not_login","not_login");

		}else{



		try {
			TEshopCusCom tc = videoconService.getUserInfo(account.getStaffID());
			if(tc==null||"".equals(tc.getAccount())){
				map.put("staffID", account.getStaffID());//未绑定微分金账号 跳转到绑定页面

			}else{
				  //第三方注册用户
				 Staff  staff = videoconService.getStaffInfo(tc.getAccount());
			     String res = videoconService.addUserinfo(tc.getAccount(), "123456wfj",
					staff.getStaffName(), 0, "2", staff.getSex()=="男"?"0":staff.getSex()=="女"?"1":"",user, "", "", 0);
				 map.put("user", tc.getAccount());

				 map.put("sccid", tc.getSccId());

			}

		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";
	}

	/**
	 * 获取用户有权限的会议室列表（用户名、密钥）
	 *
	 * @param userName
	 */
	@SuppressWarnings("unused")
	public String getRoominfoByUserName(){

		try {

			 list = videoconService.getRoominfoByUserName(user);
			 pageForm = new PageForm();
			 pageForm.setList(list);
			 pageForm.setPageCount(1);
			 pageForm.setPageSize(100);
			 pageForm.setPageNumber(1);
			 pageForm.setRecordCount(list.size());


		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return "roomlist";
	}



	/**
	* 获取会议室密码
	*/
	public String getRoomPsw() {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String psw  = videoconService.getRoomPsw(roomid);
			map.put("result", psw);

		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";
	}



	/**
	 * 获取会议室以及密码
	 */
	public String getRoom(){

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String psw  = videoconService.getRoom(roomid);
			map.put("result", psw);

		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
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
			logger.error("操作异常", e);
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";
	}
	/**s
	 *
	 * 查人
	 * @return
	 */
    public String getPersonList(){


    	Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<Object> list = videoconService.getPersonList(orgID, companyID, "", sccid);
			map.put("stafflist", list);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();

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

		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		try {
			videoconService.saveMeeting(staffMeeting.getMeetingID(), staffMeeting.getMeetingName(), staffMeeting.getMeetingTheme(),
					startDates, endDates, staffMeeting.getMeetingpsw(), staffMeeting.getChairpsw(),sccid, chID,
					"01",account.getCompanyID(),account.getStaffID());

		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return "succ";

	}

	/**
	 *
	 * 删除视频会议 咱们你服务器假删，好视通服务器真删
	 *
	 * @return
	 */
	public String deleteRoom() {

		try {
			String code = videoconService.delRoominfo(staffMeeting.getMeetingID());

		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "succ";

	}



	/**
	 *
	 * 我创建的视频会议
	 * @return
	 */
	public String myRoomMeeting(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		pageForm  =  videoconService.myCreatedRoom((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), sccid,account.getStaffID(),account.getCompanyID());

		return "myroomlist";

	}

	/**
	 * 邀请会议信息
	 *
	 * @return
	 */
	public String inventMeeting(){
		staffMeeting = videoconService.getRoomInfo(roomid);

		return "invent";



	}

	public List<BaseBean> getList() {
		return list;
	}


	public void setList(List<BaseBean> list) {
		this.list = list;
	}


	public VideoConfreService getVideoconService() {
		return videoconService;
	}


	public void setVideoconService(VideoConfreService videoconService) {
		this.videoconService = videoconService;
	}


	public PageForm getPageForm() {
		return pageForm;
	}


	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}





	public String getRoomid() {
		return roomid;
	}



	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}



	public String getResult() {
		return result;
	}



	public void setResult(String result) {
		this.result = result;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public StaffMeeting getStaffMeeting() {
		return staffMeeting;
	}

	public void setStaffMeeting(StaffMeeting staffMeeting) {
		this.staffMeeting = staffMeeting;
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

	public String getChID() {
		return chID;
	}

	public void setChID(String chID) {
		this.chID = chID;
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

	
	

	
}
