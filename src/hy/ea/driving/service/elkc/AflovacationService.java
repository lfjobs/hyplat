package hy.ea.driving.service.elkc;


import hy.ea.bo.CAccount;
import hy.ea.bo.DrivingSchool.TbJpTeacher;
import hy.ea.bo.DrivingSchool.elyc.ElycTrainerLeave;
import hy.ea.bo.DrivingSchool.elyc.ElycTrainerVacation;
import hy.ea.bo.DrivingSchool.elyc.TbElycSchoolRest;
import hy.plat.bo.PageForm;

public interface AflovacationService {

	/**
	 * 获取登录人的公司信息 *
	 * @return
	 * */
	CAccount companyInformation();

	/**
	 * 获取休假列表
	 * @return
	 * */
	PageForm avacation(String companyID, PageForm pageForm, int pageSize, String holidayBegin, String holidayEnd);
	/**
	 * 获取驾校休假详情
	 * @return
	 * */
	Object leaveTheDetails(String esrId);
	/**
	 * 删除驾校休假信息
	 * @return
	 * */
	boolean delLeaveThe(String esrId);
	/**
	 * ajax添加驾校休假信息 *
	 * @return 00:成功,01:时间格式不正确,02:教练已排班无法休假
	 * */
	String addOrUpdateLeaveThe(TbElycSchoolRest tbElycSchoolRest, String beginDate, String endDate, CAccount account);
	/**
	 * 获取教练休班列表
	 * @return
	 * */
	PageForm cVeave(String companyID, PageForm pageForm, int pageSize, String beginDate, TbJpTeacher tbJpTeacher);
	/**
	 * 获取教练休班详情
	 * @return
	 * */
	Object coachVeaveDetails(String etvId);
	/**
	 * 删除教练休班信息
	 * @return
	 * */
	boolean delCoachVeave(String etvId);
	/**
	 * 查询教练员列表
	 * @return
	 * */
	PageForm coachList(String companyID, PageForm pageForm, TbJpTeacher tbJpTeacher);
	/**
	 * 添加/修改教练休假信息 *
	 * @return 00:成功,01:时间格式不正确,02:教练已排班无法休假
	 * */
	String addOrUpdateCoach(ElycTrainerVacation elycTrainerVacation, String beginDate, String endDate, CAccount account);
	/**
	 * 查询教练员请假列表
	 * @return
	 * */
	PageForm cafLeave(String companyID, PageForm pageForm, int pageSize, String beginDate, TbJpTeacher tbJpTeacher);
	/**
	 * 删除教练请假信息
	 * @return
	 * */
	boolean delCafLeave(String etlId);
	/**
	 * 查询教练员请假详情
	 * @return
	 * */
	Object cafLeaveDetails(String etlId);
	/**
	 * 查询替班教练员列表
	 * @return
	 * */
	PageForm offDutyCoachList(String companyID, PageForm pageForm, String beginDate, TbJpTeacher tbJpTeacher);
	/**
	 * 添加/修改教练请假信息 *
	 * @return 00:成功,01:时间格式不正确,02:请勿重复添加请假信息
	 * */
	String addOrUpdateAskForLeave(ElycTrainerLeave elycTrainerLeave, String beginDate, String endDate, String leaveData, CAccount account, String substitutename);
}
