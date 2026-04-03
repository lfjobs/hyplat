package com.tiantai.webservice.Impl;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
 
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.tiantai.webservice.Check;

import hy.ea.bo.office.CarCheckDefect;
import hy.ea.bo.office.CarCheckPoint;
import hy.ea.bo.office.CarCheckPointItem;
import hy.ea.bo.office.CarCheckTask;
import hy.ea.bo.office.CarCheckTaskItem;
import hy.ea.office.service.CheckDefectService;
import hy.ea.office.service.CheckPointItemService;
import hy.ea.office.service.CheckPointService;
import hy.ea.office.service.CheckTaskItemService;
import hy.ea.office.service.CheckTaskService; 
import hy.plat.service.ServerService;

@WebService(targetNamespace = "http://service.impf2010.com", endpointInterface = "com.tiantai.webservice.Check", name = "Check", serviceName = "Check")
@Component(value="webservice.Check")  
public class CheckImpl implements Check{
	private static Logger log = LoggerFactory.getLogger(CheckImpl.class);
	@Override
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")
	public String getTask(
			@WebParam(name = "uid",targetNamespace="http://service.impf2010.com")String uid,
			@WebParam(name = "cid",targetNamespace="http://service.impf2010.com")String cid) {
		List<CarCheckTask> list = checkTaskService.getNotCheckTask(uid, cid);
		return JSON.toJSONString(list);
	} 
	
	@Override
	@WebResult(name="return",targetNamespace="http://service.impf2010.com") 
	public String getTaskItem(@WebParam(name = "taskId",targetNamespace="http://service.impf2010.com")String taskId) {
		List<CarCheckTaskItem> list = checkTaskItemService.getListByTaskId(taskId);
		return JSON.toJSONString(list);
	}

	@Override
	@WebResult(name="return",targetNamespace="http://service.impf2010.com") 
	public boolean defect(@WebParam(name = "results",targetNamespace="http://service.impf2010.com")String results) {
		
		if(StringUtils.isBlank(results))
		    return false;
		try {
			List<CarCheckDefect> list = JSON.parseArray(results, CarCheckDefect.class);
			for(CarCheckDefect defect : list){			
				defect.setCheckdatetime(new Date());
				defect.setDefectflowid(serverService.getServerID("defectflowid"));		
				checkDefectService.save(defect);
			}
			return true;
		} catch (Exception e) {
			log.error(e.toString());
			return false;
		} 
	}
	
	@Override
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")
	public String getPoint(@WebParam(name = "pointId",targetNamespace="http://service.impf2010.com")String pointId) {
		CarCheckPoint point = checkPointService.findUniqueBeanById(pointId);

        return JSON.toJSONString(point);
	}
	
	@Override
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")
	public String getPointItems(@WebParam(name = "pointId",targetNamespace="http://service.impf2010.com")String pointId) {
		List<CarCheckPointItem> list = checkPointItemService.findBeanByPointId(pointId);
		
		return JSON.toJSONString(list);
	} 
	
	@Override
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")
	public String getPoints(@WebParam(name = "taskId",targetNamespace="http://service.impf2010.com")String taskId) {
		CarCheckTask task = new CarCheckTask();
		task.setChecktaskid(taskId);
		List<CarCheckPoint> list = checkPointService.getPointListByTask(task);
		return JSON.toJSONString(list);
	}
	
	@Autowired
	private CheckTaskService checkTaskService; 
	@Autowired
	private CheckTaskItemService checkTaskItemService;
	@Autowired
	private CheckPointItemService checkPointItemService;
	
	@Autowired
	private CheckPointService checkPointService;
	@Autowired
	private CheckDefectService checkDefectService;
	
	@Autowired
	private ServerService serverService;
	
}
