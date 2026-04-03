package com.tiantai.webservice;
   
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
 

@WebService(targetNamespace = "http://service.impf2010.com", name = "Check") 
public interface Check {

	/**
	 * 获取当前任务
	 * 获取未巡查的任务
	 * @param uid 负责人
	 * @param cid 负责人所属公司
	 * @return
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")  
	String getTask(
			@WebParam(name = "uid",targetNamespace="http://service.impf2010.com")String uid,
			@WebParam(name = "cid",targetNamespace="http://service.impf2010.com")String cid);
	
	/**
	 * 获取当前任务项
	 * @param taskId
	 * @return
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com") 
	String getTaskItem(@WebParam(name = "taskId",targetNamespace="http://service.impf2010.com")String taskId);
	
	
	/**
	 * 检查点
	 * 
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com") 
	String getPoint(@WebParam(name = "pointId",targetNamespace="http://service.impf2010.com")String pointId);
		
	/**
	 * 获取当前任务的检查点
	 * 
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com") 
	String getPoints(@WebParam(name = "taskId",targetNamespace="http://service.impf2010.com")String taskId);
	
	/**
	 * 提交巡检结果
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com") 
	boolean  defect(@WebParam(name = "results",targetNamespace="http://service.impf2010.com")String results);
	
	/**
	 * 获取检查项目
	 * @param pointId
	 * @return
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")
	String getPointItems(@WebParam(name = "pointId",targetNamespace="http://service.impf2010.com")String pointId);
}
