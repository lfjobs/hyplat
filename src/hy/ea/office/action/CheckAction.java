package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.office.CarCheckDefect;
import hy.ea.bo.office.CarCheckPlan;
import hy.ea.bo.office.CarCheckPoint;
import hy.ea.bo.office.CarCheckPointItem;
import hy.ea.bo.office.CarCheckTask;
import hy.ea.bo.office.CarCheckTaskItem;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.vo.ViewCarcheckresults;
import hy.ea.office.service.CheckCarService;
import hy.ea.office.service.CheckPlanService;
import hy.ea.office.service.CheckPointItemService;
import hy.ea.office.service.CheckPointService;
import hy.ea.office.service.CheckResultsService;
import hy.ea.office.service.CheckStaffService;
import hy.ea.office.service.CheckTaskItemService;
import hy.ea.office.service.CheckTaskService;
import hy.plat.bo.PageForm;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
 

/**
 * 车辆安全巡检
 * 
 * @author yaloo.yang
 * 
 */
@Controller
@Scope("prototype")
public class CheckAction implements SessionAware{

	@Autowired
	private CheckPlanService checkPlanSvs;
	@Autowired
	private CheckTaskService checkTaskSvs;
	@Autowired
	private CheckTaskItemService checkTaskItemSvs;
	@Autowired
	private CheckPointService checkPointSvs;
	@Autowired
	private CheckPointItemService checkPointItemSvs;
	@Autowired
	private CheckResultsService checkResultsSvs;
	@Autowired
	private CheckCarService checkCarSvs;

	@Autowired
	private CheckStaffService checkStaffSvs;
	@Autowired
	private ServerService serverService;

	private PageForm pageForm = new PageForm();;
	private String search;
	private int pageSize;
	private int pageNumber;
	private String result;
	private CarCheckPlan planbean;
	private CarCheckTask taskbean;
	private CarCheckTaskItem taskitembean;
	private CarCheckPoint pointbean;
	private CarCheckPointItem pointitembean;
	private CarCheckDefect defectbean;
	private ViewCarcheckresults resultsbean;
	private CarInformation carbean;

	private String checkpointid;
	private String type;// type:task用于区分是任务查询point还是检查点查询point
	private String deletepointitemid;// 检查点修改中用于记录被删除的检查项的id
	private String divide = "中国人";
	private String id;

	/***************************************************************************
	 * 巡检计划开始********************************** /** 获取巡检计划列表
	 * 
	 * @return
	 */
	public String plan() { 
		CAccount account = (CAccount) session.get("account");
		if (search != null && search.equals("search")) {
			planbean = (CarCheckPlan) session.get("tablesearch");
		} else {
			planbean = new CarCheckPlan();
		}
		planbean.setCompanyid(account.getCompanyID());
		pageForm = checkPlanSvs.getPageForm(pageNumber, (pageSize == 0 ? 10
				: pageSize), planbean);
		return "plan";
	}

	/**
	 * 
	 * 添加修改巡检计划
	 * 
	 * @return
	 */
	public String addPlan() {
		CAccount account = (CAccount) session.get("account");
		if (planbean.getCheckplanid() != null
				&& !planbean.getCheckplanid().equals("")) {
			CarCheckPlan oldplan = checkPlanSvs.findUniqueBeanById(planbean
					.getCheckplanid());
			oldplan.setCheckplanname(planbean.getCheckplanname());
			oldplan.setPlantype(planbean.getPlantype());
			oldplan.setPrincipal(planbean.getPrincipal());
			oldplan.setPrincipalname(planbean.getPrincipalname());

			oldplan.setStarttime(planbean.getStarttime());
			oldplan.setEndtime(planbean.getEndtime());
			checkPlanSvs.update(oldplan);
		}else{
			planbean.setCheckplanid(serverService.getServerID("planid"));
			planbean.setCreatedatetime(new Date());
			planbean.setCreator(account.getStaffID());
			planbean.setCreatorname(checkStaffSvs.findUniqueBeanById(
					account.getStaffID()).getStaffName());
			planbean.setCompanyid(account.getCompanyID());
			checkPlanSvs.save(planbean);
		}
		return "success";
	}

	/**
	 * 
	 * 删除巡检计划
	 * 
	 * @return
	 */
	public String deletePlan() {
		if (planbean.getCheckplanid() != null
				&& !planbean.getCheckplanid().equals("")) {
			CarCheckPlan oldplan = checkPlanSvs.findUniqueBeanById(planbean
					.getCheckplanid());
			checkPlanSvs.deleteById(oldplan.getCheckplankey());
		}

		return "success";

	}

	public String toSearchPlan() { 
		session.put("tablesearch", planbean);
		return plan();
	}

	/***************************************************************************
	 * 巡检计划完毕**********************************
	 * 
	 * 
	 * 
	 * /************************************巡检任务开始*************************************************
	 * /** 获取巡检任务列表
	 * 
	 * @return
	 */
	public String task() {
		try { 
			CAccount account = (CAccount) session.get("account");

			if (search != null && search.equals("search")) {
				taskbean = (CarCheckTask) session.get("tablesearch");
			} else {
				taskbean = new CarCheckTask();
			}
			taskbean.setCompanyid(account.getCompanyID());
			pageForm = checkTaskSvs.getPageForm(pageNumber, (pageSize == 0 ? 10
					: pageSize), taskbean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "task";
	}

	/**
	 * 
	 * 添加修改巡检计划
	 * 
	 * @return
	 */
	public String addTask() {
		try {
			CAccount account = (CAccount) session.get("account");
			CarCheckTaskItem taskitem = null;
			if (taskbean.getChecktaskid() != null
					&& !taskbean.getChecktaskid().equals("")) {
				CarCheckTask oldtask = checkTaskSvs.findUniqueBeanById(taskbean
						.getChecktaskid());
				oldtask.setChecktaskname(taskbean.getChecktaskname());
				oldtask.setTasktype(taskbean.getTasktype());
				oldtask.setPrincipal(taskbean.getPrincipal());
				oldtask.setPrincipalname(taskbean.getPrincipalname());

				checkTaskSvs.update(oldtask);

				// 更新任务项
				// 增加任务项
				if (pointbean != null) {
					String[] devicetypeids = pointbean.getDevicetypeid().split(
							",");
					List<CarCheckPoint> pointlist = null;

					for (int i = 0; i < devicetypeids.length; i++) {

						pointlist = checkPointSvs.findByProperty(
								"devicetypeid", devicetypeids[i].trim());
						Object[] values = null;
						for (CarCheckPoint point : pointlist) {
							String hql = "from CarCheckTaskItem where pointid = ? and taskid = ?";
							values = new Object[] { point.getCheckpointid(),
									taskbean.getChecktaskid() };
							taskitembean = checkTaskItemSvs.findUnique(hql,
									values);
							if (taskitembean != null) {
								break;
							}
							taskitem = new CarCheckTaskItem();
							taskitem.setTaskitemid(serverService
									.getServerID("taskitemid"));
							taskitem.setCompanyid(account.getCompanyID());
							taskitem.setTaskid(oldtask.getChecktaskid());

							taskitem.setPointid(point.getCheckpointid());
							taskitem.setTaskitemname(point.getCheckpointname());
							taskitem.setCheckflag(0);
							checkTaskItemSvs.save(taskitem);

						}
					}
				}
			} else {
				String taskid = serverService.getServerID("taskid");
				taskbean.setChecktaskid(taskid);
				taskbean.setOperatorid(account.getStaffID());
				taskbean.setOperatorname(checkStaffSvs.findUniqueBeanById(
						account.getStaffID()).getStaffName());
				taskbean.setCompanyid(account.getCompanyID());
				taskbean.setTaskstatus(0);
				taskbean.setCheckflag(0);
				taskbean.setCreatetime(new Date());
				checkTaskSvs.save(taskbean);
				// 增加任务项
				if (pointbean != null) {
					String[] devicetypeids = pointbean.getDevicetypeid().split(
							",");
					List<CarCheckPoint> pointlist = null;

					for (int i = 0; i < devicetypeids.length; i++) {

						pointlist = checkPointSvs.findByProperty(
								"devicetypeid", devicetypeids[i].trim());
						Object[] values = null;
						for (CarCheckPoint point : pointlist) {
							String hql = "from CarCheckTaskItem where pointid = ? and taskid = ?";
							values = new Object[] { point.getCheckpointid(),
									taskbean.getChecktaskid() };
							taskitembean = checkTaskItemSvs.findUnique(hql,
									values);
							if (taskitembean != null) {
								break;
							}
							taskitem = new CarCheckTaskItem();
							taskitem.setTaskitemid(serverService
									.getServerID("taskitemid"));
							taskitem.setCompanyid(account.getCompanyID());
							taskitem.setTaskid(taskid);

							taskitem.setPointid(point.getCheckpointid());
							taskitem.setTaskitemname(point.getCheckpointname());
							taskitem.setCheckflag(0);
							checkTaskItemSvs.save(taskitem);

						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 
	 * 根据车的信息删除任务项
	 * 
	 * @return
	 */
	public String deleteTaskitemByCarID() {
		try {
			List<CarCheckPoint> pointlist = checkPointSvs.findByProperty(
					"devicetypeid", pointbean.getDevicetypeid());
			CarCheckTaskItem taskitembean = null;
			String hql = "from CarCheckTaskItem where pointid = ? and taskid = ?";
			Object[] values = null;
			for (CarCheckPoint point : pointlist) {
				values = new Object[] { point.getCheckpointid(),
						taskbean.getChecktaskid() };
				taskitembean = checkTaskItemSvs.findUnique(hql, values);
				checkPointItemSvs.delete(taskitembean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";

	}

	/**
	 * 
	 * 根据公司获取所有任务
	 */
	public String getTaskByCompany() {
		CAccount account = (CAccount) session.get("account");
		List<CarCheckTask> tasklist = checkTaskSvs.findByProperty("companyid",
				account.getCompanyID());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tasklist", tasklist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 根据公司获取所有计划
	 * 
	 * @return
	 */
	public String getPlanByCompany() {
		CAccount account = (CAccount) session.get("account");
		List<CarCheckPlan> planlist = checkPlanSvs.findByProperty("companyid",
				account.getCompanyID());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planlist", planlist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 
	 * 确认任务
	 * 
	 * @return
	 */
	public String confirmTask() {

		CAccount account = (CAccount) session.get("account");
		if (taskbean.getChecktaskid() != null
				&& !taskbean.getChecktaskid().equals("")) {
			CarCheckTask oldtask = checkTaskSvs.findUniqueBeanById(taskbean
					.getChecktaskid());
			oldtask.setConfimerid(account.getStaffID());
			oldtask.setConfimername(checkStaffSvs.findUniqueBeanById(
					account.getStaffID()).getStaffName());
			checkTaskSvs.update(oldtask);
		}

		return "success";
	}

	/**
	 * 
	 * 任务查询
	 * 
	 * @return
	 */
	public String toSearchTask() { 
		session.put("tablesearch", taskbean);
		return task();
	}

	/**
	 * 
	 * 任务中修改查询任务项根据pointid
	 * 
	 * @return
	 */
	public String taskitem() {

		List<CarCheckPoint> oldpointlist = checkPointSvs
				.getPointListByTask(taskbean);

		List<CarCheckPoint> pointlist = new ArrayList<CarCheckPoint>();
		List<String> carIDs = new ArrayList<String>();
		String devicetypeid = "";
		for (CarCheckPoint point : oldpointlist) {
			devicetypeid = point.getDevicetypeid();
			if (!carIDs.contains(devicetypeid)) {
				carIDs.add(devicetypeid);
				pointlist.add(point);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pointlist", pointlist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/***************************************************************************
	 * 巡检任务完毕********************************
	 * 
	 * 
	 * /** 获取巡检点列表
	 * 
	 * @return
	 */
	public String point() { 
		CAccount account = (CAccount) session.get("account");
		String sql = "";
		Object[] params = null;
		if (search != null && search.equals("search")) {
			sql = "select distinct(devicetypeid),carNum from DT_CAR_CHECKPOINT where companyid = ? and carNum like ?";
			params = new Object[] { account.getCompanyID(),
					"%" + pointbean.getCarNum() + "%" };
		} else {
			// 获取车辆信息
			sql = "select distinct(devicetypeid),carNum from DT_CAR_CHECKPOINT where companyid = ?";
			params = new Object[] { account.getCompanyID() };
		}
		pageForm = checkPointSvs.getPageForm(pageNumber, (pageSize == 0 ? 10
				: pageSize), sql, params, pointbean);
		if (type != null && !type.equals("null") && type.equals("task")) {
			return "point_list";
		}

		return "point";
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public String submitEdit() {
		try {
			CAccount account = (CAccount) session.get("account");
			if (account == null) {
				return "not_login";
			}

			CarCheckPoint point = null;
			CarCheckPointItem pointitem = null;
			String pointid = "";
			String pointitemid = "";
			if (pointbean != null) {
				pointid = pointbean.getCheckpointid();
			}
			if (pointitembean != null) {
				pointitemid = pointitembean.getCheckpointitemid();
			}
			if (pointid != null && !pointid.equals("")) {
				point = checkPointSvs.findUniqueBeanById(pointid);
				point.setCheckpointname(pointbean.getCheckpointname());
				point.setUseflag(pointbean.getUseflag());
				checkPointSvs.update(point);
			} else {
				pointitem = checkPointItemSvs.findUniqueBeanById(pointitemid);
				pointitem.setCheckpointitemname(pointitembean
						.getCheckpointitemname());
				checkPointItemSvs.update(pointitem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
	}

	/**
	 * 
	 * 发芯片
	 * 
	 * @return
	 */
	public String despatchChip() {
		try {
			CAccount account = (CAccount) session.get("account");
			if (account == null) {
				return "not_login";
			}
			String[] checkkeys = pointbean.getCheckkey().split(",");
			String[] chips = pointbean.getChip().split(",");
			for (int i = 0; i < checkkeys.length; i++) {
				pointbean.setCheckkey(checkkeys[i].trim());
				pointbean.setChip(chips[i].trim());
				checkPointSvs.updateDefault(pointbean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/*
	 * 
	 * 检查点里的所有删除
	 */
	public String deletelevel() {
		if (id.indexOf("pointid") > -1) {
			CarCheckPoint point = checkPointSvs.findUniqueBeanById(id);
			List<CarCheckPointItem> pointitemlist = checkPointItemSvs
					.findByProperty("checkpointid", id);
			for (CarCheckPointItem pointitem : pointitemlist) {
				checkPointItemSvs.delete(pointitem);
			}
			checkPointSvs.delete(point);

		} else {
			CarCheckPointItem pointitem = checkPointItemSvs
					.findUniqueBeanById(id);
			checkPointSvs.delete(pointitem);
		}

		return "success";
	}

	/*
	 * 
	 * 根据车ID获取属于该车的所有检查点
	 */
	public String pointListByCar() {
		List<CarCheckPoint> pointlist = checkPointSvs.findByProperty(
				"devicetypeid", pointbean.getDevicetypeid());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pointlist", pointlist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 
	 * 添加修改检查点
	 * 
	 * @return
	 */
	public String addPoint() {
		try {
			CAccount account = (CAccount) session.get("account");
			CarCheckPointItem pointitem = null;
			if (pointbean.getCheckpointid() != null
					&& !pointbean.getCheckpointid().equals("")) {
				//修改暂时不用；
				CarCheckPoint oldPoint = checkPointSvs
						.findUniqueBeanById(pointbean.getCheckpointid());
				oldPoint.setCheckpointname(pointbean.getCheckpointname());
				oldPoint.setDevicetypeid(pointbean.getDevicetypeid());
				oldPoint.setCarNum(pointbean.getCarNum());
				oldPoint.setChip(pointbean.getChip());

				checkPointSvs.update(oldPoint);
				// 更新检查点
				if (pointitembean != null) {
					String[] checkpointitemnames = pointitembean
							.getCheckpointitemname().split(",");
					String[] checkpointitemids = pointitembean
							.getCheckpointitemid().split(",");
					CarCheckPointItem oldPointitem = null;
					for (int i = 0; i < checkpointitemids.length; i++) {
						if (checkpointitemids[i] != null
								&& checkpointitemids[i].trim().equals("add")) {
							// 添加检查点
							pointitem = new CarCheckPointItem();
							pointitem.setCheckpointitemid(serverService
									.getServerID("pointitemid"));
							pointitem
									.setCheckpointitemname(checkpointitemnames[i]
											.trim());
							pointitem.setCompanyid(account.getCompanyID());
							pointitem.setUseflag(0);
							pointitem.setCheckpointid(oldPoint
									.getCheckpointid());
							pointitem.setDatatype("0");
							pointitem.setMaxlen(0);
							pointitem.setMaxdata(0);
							pointitem.setMindata(0);
							pointitem.setListflag(0);
							pointitem.setListtype("0");
							pointitem.setNeedflag(1);
							pointitem.setCreatetime(new Date());
							checkPointItemSvs.save(pointitem);

						} else {
							oldPointitem = checkPointItemSvs
									.findUniqueBeanById(checkpointitemids[i]
											.trim());
							oldPointitem
									.setCheckpointitemname(checkpointitemnames[i]
											.trim());
							checkPointItemSvs.update(oldPointitem);
						}

					}
				}

				// 删除检查点
				if (!deletepointitemid.equals("")) {
					String[] pointitemids = deletepointitemid.split(",");
					CarCheckPointItem oldpointitem = null;
					for (int i = 0; i < pointitemids.length; i++) {
						oldpointitem = checkPointItemSvs
								.findUniqueBeanById(pointitemids[i].trim());
						checkPointItemSvs.deleteById(oldpointitem
								.getCheckpointitemkey());
					}
				}

			} else {
				String[] carNums = pointbean.getCarNum().split(",");
				String[] devicetypeids = pointbean.getDevicetypeid().split(",");
				// 多个车，已车为中心分别为车添加检查点和检查项
				for (int i = 0; i < devicetypeids.length; i++) {
					String checkpointid = serverService.getServerID("pointid");
					pointbean.setCheckpointid(checkpointid);
					pointbean.setDevicetypeid(devicetypeids[i].trim());
					pointbean.setCarNum(carNums[i].trim());
					pointbean.setCompanyid(account.getCompanyID());
					pointbean.setUseflag(0l);
					pointbean.setCreatetime(new Date());
					checkPointSvs.save(pointbean);

					String[] pointitemnames = pointitembean
							.getCheckpointitemname().split(",");
					for (int j = 0; j < pointitemnames.length; j++) {
						pointitem = new CarCheckPointItem();
						pointitem.setCheckpointitemid(serverService
								.getServerID("pointitemid"));
						pointitem.setCheckpointitemname(pointitemnames[j]
								.trim());
						pointitem.setCompanyid(account.getCompanyID());
						pointitem.setUseflag(0);
						pointitem.setCheckpointid(checkpointid);
						pointitem.setDatatype("0");
						pointitem.setMaxlen(0);
						pointitem.setMaxdata(0);
						pointitem.setMindata(0);
						pointitem.setListflag(0);
						pointitem.setListtype("0");
						pointitem.setNeedflag(1);
						pointitem.setCreatetime(new Date());
						checkPointItemSvs.save(pointitem);

					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 
	 * 检查点查询
	 * 
	 * @return
	 */
	public String toSearchPoint() { 
		session.put("tablesearch", pointbean);
		return point();
	}

	/**
	 * 
	 * 获取检查点的所有检查项
	 * 
	 * @return
	 */
	public String getPointItemList() {

		// 获取车的信息
		CarCheckPoint point = checkPointSvs.findUniqueBeanById(checkpointid);

		List<CarCheckPointItem> pointitemlist = checkPointItemSvs
				.findByProperty("checkpointid", checkpointid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pointitemlist", pointitemlist);
		map.put("carID", point.getDevicetypeid());
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 
	 * 获取车辆信息按公司
	 * 
	 * @return
	 */
	public String car() { 
		CAccount account = (CAccount) session.get("account");
		if (search != null && search.equals("search")) {
			carbean = (CarInformation) session.get("tablesearch");
		} else {
			carbean = new CarInformation();
		}
		carbean.setCompanyID(account.getCompanyID());
		pageForm = checkCarSvs.getPageForm(pageNumber, (pageSize == 0 ? 10
				: pageSize), carbean);

		return "car";

	}

	/**
	 * 
	 * 获取车辆信息按公司
	 * 
	 * @return
	 */
	public String carBypoint() { 
		CAccount account = (CAccount) session.get("account");
		if (search != null && search.equals("search")) {
			carbean = (CarInformation) session.get("tablesearch");
		} else {
			carbean = new CarInformation();
		}
		carbean.setCompanyID(account.getCompanyID());
		pageForm = checkCarSvs.getPageForm(pageNumber, (pageSize == 0 ? 10
				: pageSize), carbean);
		return "car";

	}

	/**
	 * 
	 * 检查点查询
	 * 
	 * @return
	 */
	public String toSearchCar() { 
		session.put("tablesearch", carbean);
		return car();
	}

	/** **************************************检查项开始************************************ */

	public String pointitem() {
		try { 
			CAccount account = (CAccount) session.get("account");
			if (search != null && search.equals("search")) {
				pointitembean = (CarCheckPointItem) session.get("tablesearch");
			} else {
				pointitembean = new CarCheckPointItem();
			}
			pointitembean.setCompanyid(account.getCompanyID());
			pageForm = checkPointItemSvs.getPageForm(pageNumber,
					(pageSize == 0 ? 10 : pageSize), pointitembean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "pointitem";
	}

	/**
	 * 
	 * 检查点查询
	 * 
	 * @return
	 */
	public String toSearchPointItem() { 
		session.put("tablesearch", pointitembean);
		return pointitem();
	}

	/**
	 * 
	 * 添加修改检查项
	 * 
	 * @return
	 */
	public String addPointItem() {
		try {
			CAccount account = (CAccount) session.get("account");
			if (pointitembean.getCheckpointitemid() != null
					&& !pointitembean.getCheckpointitemid().equals("")) {
				CarCheckPointItem oldPointitem = checkPointItemSvs
						.findUniqueBeanById(pointitembean.getCheckpointitemid());
				oldPointitem.setCheckpointitemname(pointitembean
						.getCheckpointitemname());
				oldPointitem.setCheckpointid(pointitembean.getCheckpointid());
				checkPointSvs.update(oldPointitem);
			} else {
				pointitembean.setCheckpointitemid(serverService
						.getServerID("pointitemid"));
				pointitembean.setCompanyid(account.getCompanyID());
				pointitembean.setUseflag(0);
				pointitembean.setDatatype("0");
				pointitembean.setMaxlen(0);
				pointitembean.setMaxdata(0);
				pointitembean.setMindata(0);
				pointitembean.setListflag(0);
				pointitembean.setListtype("0");
				pointitembean.setNeedflag(1);
				pointitembean.setCreatetime(new Date());
				checkPointItemSvs.save(pointitembean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 根据公司获取所有检查点ajax
	 * 
	 * @return
	 */
	public String getPointByCompany() {
		CAccount account = (CAccount) session.get("account");
		List<CarCheckPoint> pointlist = checkPointSvs.findByProperty(
				"companyid", account.getCompanyID());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pointlist", pointlist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public String IsChipRepeat() {
		List<CarCheckPoint> pointlist = checkPointSvs.findByProperty("chip",
				pointbean.getChip());
		Map<String, Object> map = new HashMap<String, Object>();
		if (pointlist.size() == 0) {
			map.put("result", "suc");
		} else {
			map.put("result", "fail");
		}
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";

	}

	/**
	 * 获取检查结果列表
	 * 
	 * @return
	 */
	public String results() {
		try { 
			CAccount account = (CAccount) session.get("account");
			if (search != null && search.equals("search")) {
				resultsbean = (ViewCarcheckresults) session.get("tablesearch");
			} else {
				resultsbean = new ViewCarcheckresults();
			}
			resultsbean.setCompanyid(account.getCompanyID());
			pageForm = checkResultsSvs
					.getPageForm((pageNumber == 0 ? 1 : pageNumber),
							(pageSize == 0 ? 10 : pageSize), resultsbean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "results";
	}

	/**
	 * 
	 * 检查结果查询
	 * 
	 * @return
	 */
	public String toSearchResults() { 
		session.put("tablesearch", resultsbean);
		return results();
	}

	/**
	 * 获取检查图标
	 * 
	 * @return
	 */
	/*public String chart() {
		try { 
			CAccount account = (CAccount) session.get("account");
			if (search != null && search.equals("search")) {
				resultsbean = (ViewCarcheckresults) session.get("tablesearch");
			} else {
				resultsbean = new ViewCarcheckresults();
			}
			resultsbean.setCompanyid(account.getCompanyID());
			pageForm = checkResultsSvs
					.getPageForm((pageNumber == 0 ? 1 : pageNumber),
							(pageSize == 0 ? 10 : pageSize), resultsbean);
			List<ViewCarcheckresults> list = checkResultsSvs.findByProperty("companyid", account.getCompanyID());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "chart";
	}*/

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public CarCheckPlan getPlanbean() {
		return planbean;
	}

	public void setPlanbean(CarCheckPlan planbean) {
		this.planbean = planbean;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public CarCheckTask getTaskbean() {
		return taskbean;
	}

	public void setTaskbean(CarCheckTask taskbean) {
		this.taskbean = taskbean;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public CarCheckPoint getPointbean() {
		return pointbean;
	}

	public void setPointbean(CarCheckPoint pointbean) {
		this.pointbean = pointbean;
	}

	public CarCheckPointItem getPointitembean() {
		return pointitembean;
	}

	public void setPointitembean(CarCheckPointItem pointitembean) {
		this.pointitembean = pointitembean;
	}

	public CarCheckDefect getDefectbean() {
		return defectbean;
	}

	public void setDefectbean(CarCheckDefect defectbean) {
		this.defectbean = defectbean;
	}

	public CarInformation getCarbean() {
		return carbean;
	}

	public void setCarbean(CarInformation carbean) {
		this.carbean = carbean;
	}

	public String getCheckpointid() {
		return checkpointid;
	}

	public void setCheckpointid(String checkpointid) {
		this.checkpointid = checkpointid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CarCheckTaskItem getTaskitembean() {
		return taskitembean;
	}

	public void setTaskitembean(CarCheckTaskItem taskitembean) {
		this.taskitembean = taskitembean;
	}

	public String getDeletepointitemid() {
		return deletepointitemid;
	}

	public void setDeletepointitemid(String deletepointitemid) {
		this.deletepointitemid = deletepointitemid;
	}

	public ViewCarcheckresults getResultsbean() {
		return resultsbean;
	}

	public void setResultsbean(ViewCarcheckresults resultsbean) {
		this.resultsbean = resultsbean;
	}

	public String getDivide() {
		return divide;
	}

	public void setDivide(String divide) {
		this.divide = divide;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private Map<String, Object> session;
	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}

}
