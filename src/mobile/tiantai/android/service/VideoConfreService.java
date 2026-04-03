package mobile.tiantai.android.service;

import java.util.List;
import java.util.Map;

import com.tiantai.wfj.bo.TEshopCusCom;

import hy.ea.bo.human.Staff;
import hy.ea.bo.office.StaffMeeting;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
public interface VideoConfreService {

	 /**
	   * 添加用户
	 * 用户名、密码、昵称、组织架构、角色、性别、手机号、电话、邮箱、密钥、密码类型
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
	public String addUserinfo(String userName, String passpwd, String nickName, Integer departID, String adminRole,
			String sex, String mobile, String telePhone, String email,Integer password_type)
	      throws Exception;
	    
	
	
  
	
	/**
	 * 
	 * @param staffMeeting  会议信息
	 * @param sccid  创建人微分金账号
	 * @param chID  参会人拼接staffID,staffName,account#staffID,staffName,account
	 */
	public void saveMeeting(String meetingID,String meetingName,String meetingTheme,String startDates,String endDates,String meetingpsw,String chairpsw,String sccid,String chID,String state,String companyID,String staffID);
		
	
	   
	/**
	* 通过会议室给用户名授权
	* @param arrUserNameandRight
	* @param roomId
	*/
	public String doUserRightByRoomid(String arrUserNameandRight, Integer roomId) 
		throws Exception;

	
	
	
	/**
	* 获取用户有权限的会议室列表（用户名、密钥）
	* @param userName
	*/
	public List<BaseBean> getRoominfoByUserName(String userName);
	    
	    
	
    
	   
  /**
  * 获取会议室密码
  */
   public String getRoomPsw(String roomId);
   
	/**
	 * 获取会议室以及密码
	 */
	public String getRoom(String roomId);
   
   
	/**
	 * 
	 * 根据用户微分金账号获取人员信息
	 * @param user
	 * @return
	 */
	public Staff getStaffInfo(String user);
	
	/**
	 * 
	 * 根据PC系统登录用户ID查询APP用户信息
	 * @param staffID
	 * @return
	 */
	public TEshopCusCom getUserInfo(String staffID);
	
	/** 
    * 我创建的视频会议
    * @param pageNumber
    * @param pageSize
    * @param sccid
    * @return
    */
	public PageForm myCreatedRoom(int pageNumber, int pageSize, String sccid,String staffID,String companyID);
	
	   
	/**
	*  删除房间根据房间ID
	* @param meetingID
	* @return
	*/
	public String delRoominfo(String meetingID);
	
	
	/**
	 * 是否有参会人可选择也就是是否入职公司
	 * @param sccid
	 * @return
	 */
	public String haveMeeter(String sccid,String user);

/**
 * 
 * 查询公司列表
 * @param sccid
 * @return
 */
	public List<Object> getCompanyList(String sccid);
	
	/**
	 * 根据公司ID获取一级部门
	 * @param companyID
	 * @return
	 */
	public List<BaseBean> getOrgList(String companyID);
	
	
	/**
	 * 根据部门查人
	 * @param orgID
	 * @param companyID
	 * @return
	 */
	
	public List<Object> getPersonList(String orgID,String companyID,String parameter,String sccid);
	
	
	


	
	 /**
	  * 
	  * 发布通知
	  * @param meetingID
	  * @return
	  */
	 public String pubNotice(String meetingID);
	 
	 
	 /**
	  * 
	  * 编辑获取原内容
	  * @param meetingID
	  * @return
	  */
	 public Map<String, Object> getEditRoomInfo(String meetingID);
	 
	 
	 /**
	  * 返回会议信息
	  * 
	  * @param roomid
	  * @return
	  */
	 public StaffMeeting getRoomInfo(String roomid);
	 

}
