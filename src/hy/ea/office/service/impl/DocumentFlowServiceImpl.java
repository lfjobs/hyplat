package hy.ea.office.service.impl;

import hy.ea.bo.CAccount;
import hy.ea.bo.office.DocGsendInfo;
import hy.ea.bo.office.Document;
import hy.ea.office.service.DocCommonService;
import hy.ea.office.service.DocumentFlowService;
import hy.ea.util.Utilities;
import hy.ea.workflow.DocReadInfo;
import hy.ea.workflow.DocReadInfo.OprationAuthority;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import org.jbpm.api.*;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.history.HistoryEvent;
import org.jbpm.pvm.internal.history.events.TaskActivityStart;
import org.jbpm.pvm.internal.history.model.HistoryTaskImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.jbpm.pvm.internal.util.Clock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class DocumentFlowServiceImpl implements DocumentFlowService {
	@Resource
	private ProcessEngine processEngine;
	@Resource
	private ExecutionService executionService;
	@Resource
	private TaskService taskService;
	@Resource
	private BaseBeanDao baseBeanDao;
	@Resource
	private ServerService serverService;
	@Resource
	private DocCommonService docCommpnService;

	// /////////////////////////////////////////////////////////////////////////////////////
	// //////////////////********流程触发*********///////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 发起公文流转流程(第一次发送)
	 * 
	 */
	public String createDocument(Document doc, CAccount account) {
		try {
			doc.setSubscriberID(doc.getReceiverID());
			doc.setDeptIDofSubscriber(doc.getReceiverDeptID());
			doc.setCompanyIDofSubscriber(doc.getReceiverCompanyID());
			doc.setFromMember(account.getStaffID());
			doc.setCreateTime(new Date());
			doc.setUpdateTime(new Date()); // 最后一次发送时间
			if (doc.getAppendComment() != null
					&& !doc.getAppendComment().equals("")) {
				doc.setDrafterComment(doc.getAppendComment());
				doc.setAppendComment("");// 发送后清空附加意见；
			}
			doc.setStatus("S");
			if (doc.getSubscriberID2() != null
					&& !doc.getSubscriberID2().equals("")) {
				doc.setDrafterID(doc.getSubscriberID2());
				doc.setOrganizationID(doc.getDeptIDofSubscriber2());
				doc.setCompanyID(doc.getCompanyIDofSubscriber2());
				doc.setSubscriberID2(null);
				doc.setDeptIDofSubscriber2(null);
				doc.setCompanyIDofSubscriber2(null);
			}

			if (doc.getDocNum() == null || doc.getDocNum().equals("")) {
				if(doc.getCompanyID()==null||doc.getCompanyID().equals("")) {
					doc.setDocNum(docCommpnService.getDocNum(
							account.getStaffID(), "docNum"));
				}else{
					doc.setDocNum(docCommpnService.getDocNum(
							account.getCompanyID(), "docNum"));
				}
			}
			doc.setFormalNum(doc.getNumWord() + "字【" + doc.getNumTime() + "】第");

			// 1. 创建公文
			// 2. 创建流程实例,并设置公文起草人变量值
			Map<String, Object> drafterMap = new HashMap<String, Object>();
			drafterMap.put("drafter", doc.getDrafterID());
			ProcessInstance processInstance = executionService
					.startProcessInstanceByKey("ExamineAndApprove", drafterMap,
							doc.getDocId());
			// 3. 公文和流程实例绑定
			doc.setProcessInstanceId(processInstance.getId());
			baseBeanDao.update(doc);

			// 创建保留历史的变量
			executionService.createVariable(processInstance.getId(), "docId",
					doc.getDocId(), true);

			// 设置公文审核人变量值
			executionService.setVariable(processInstance.getId(), "subscriber",
					doc.getSubscriberID());

			// 设置进入待审批任务的来源
			executionService.setVariable(processInstance.getId(),
					"sourceOfExamine", "first");

			// 指定流程从draft任务流转到Examine and Approve任务
			Execution exec = processInstance.findActiveExecutionIn("draft");

			executionService.signalExecutionById(exec.getId(),
					"to Examine and Approve");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doc.getDocId();
	}

	/**
	 * 公文被驳回后，重新发送
	 * 
	 */
	public String reSendDocument(Document doc, CAccount account) {
		doc.setSubscriberID(doc.getReceiverID());
		doc.setDeptIDofSubscriber(doc.getReceiverDeptID());
		doc.setCompanyIDofSubscriber(doc.getReceiverCompanyID());
		doc.setFromMember(account.getStaffID());
		doc.setUpdateTime(new Date()); // 最后一次发送时间
		if (doc.getAppendComment() != null
				&& !doc.getAppendComment().equals("")) {
			String drafterComment = doc.getDrafterComment();
			if (drafterComment == null) {
				drafterComment = "";
			}
			doc
					.setDrafterComment(drafterComment + ";"
							+ doc.getAppendComment());
			doc.setAppendComment("");// 发送后清空附加意见；
		}
		doc.setStatus("S");
		if (doc.getSubscriberID2() != null
				&& !doc.getSubscriberID2().equals("")) {
			doc.setDrafterID(doc.getSubscriberID2());
			doc.setOrganizationID(doc.getDeptIDofSubscriber2());
			doc.setCompanyID(doc.getCompanyIDofSubscriber2());
			doc.setSubscriberID2(null);
			doc.setDeptIDofSubscriber2(null);
			doc.setCompanyIDofSubscriber2(null);
		}
		baseBeanDao.update(doc);
		try {
			String processInstanceID = doc.getProcessInstanceId();

			// 再设置公文审核人变量值
			executionService.setVariable(processInstanceID, "subscriber", doc
					.getSubscriberID());
			// 设置进入待审批任务的来源
			executionService.setVariable(processInstanceID, "sourceOfExamine",
					"resend");

			// 指定流程从draft任务流转到Examine and Approve任务
			ProcessInstance processInstance = executionService
					.findProcessInstanceById(processInstanceID);
			Execution exec = processInstance.findActiveExecutionIn("draft");
			executionService.signalExecutionById(exec.getId(),
					"to Examine and Approve");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc.getDocId();
	}

	/**
	 * 审批公文
	 * 
	 * @param jump:
	 *            reject 驳回，noapprove 不批准，approve 批准，transfer 转他人审批
	 * @return 0:失败 1；驳回，2：不批准 3：转盖章 4：转审批 5：估计执行不到；
	 */
	public int examineDocument(Document doc, String jump) {
		String hql = "from Document where docId = ?";
		Document oldDoc = (Document) baseBeanDao.getBeanByHqlAndParams(hql,
				new Object[] { doc.getDocId() });
		String instanceID = oldDoc.getProcessInstanceId();
		// 查询当前文档所在流程实例的任务列表
		ProcessInstance instance = executionService
				.findProcessInstanceById(instanceID);
		TaskQuery tq = taskService.createTaskQuery();
		List<Task> taskList = tq.processInstanceId(instanceID).list();
		Task curTask = null;
		for (Task task : taskList) {
			if (task.getName().equalsIgnoreCase("Examine and Approve")) { // 筛选出审批任务
				curTask = task;
				break;
			}
		}
		if (curTask == null)
			return 0;
		String subscritbercomment = oldDoc.getSubscriberComment();
		if (subscritbercomment == null) {
			subscritbercomment = "";
		}
		oldDoc.setSubscriberComment(subscritbercomment
				+ doc.getSubscriberComment());
		oldDoc.setSubscribeTime(new Date()); // 驳回、不批准、批准或最近一次转他人审批时间

		if (jump.equalsIgnoreCase("reject")) {
			oldDoc.setStatus("R");
			oldDoc.setExamineResult("Reject");
			oldDoc.setPassTime(new Date());
			baseBeanDao.update(oldDoc);

			// 完成审批任务，转向驳回
			taskService.completeTask(curTask.getId(), "Reject");

			return 1;
		} else if (jump.equalsIgnoreCase("noapprove")) {
			oldDoc.setStatus("U"); // 设置公文为不批准状态
			oldDoc.setExamineResult("Noapprove");
			baseBeanDao.update(oldDoc);

			// 完成审批任务，转向未批准
			taskService.completeTask(curTask.getId(), "Not Approve");
			return 2;
		} else if (jump.equalsIgnoreCase("approve")) {
			// 设定盖章人
			oldDoc.setSealTime(new Date());
			oldDoc.setSealerID(doc.getReceiverID());
			oldDoc.setDeptIDofSealer(doc.getReceiverDeptID());
			oldDoc.setCompanyIDofSealer(doc.getReceiverCompanyID());
			oldDoc.setStatus("A"); // 设置公文为批准状态（未被盖章）
			oldDoc.setExamineResult("toSeal");
			
			// 生成正式编号中的号    
			String numCode = docCommpnService.getDocNum(oldDoc.getCompanyID()==null?oldDoc.getDrafterID():oldDoc.getCompanyID(),
					"numCode");
			oldDoc.setNumCode(numCode);
			oldDoc.setFormalNum(oldDoc.getFormalNum() + numCode + "号");
			baseBeanDao.update(oldDoc);

			// 设置盖章人变量
			executionService.setVariable(instance.getId(), "sealer", doc
					.getReceiverID());
			// 完成审批任务，转向盖章
			taskService.completeTask(curTask.getId(), "to Seal");
			return 3;
		} else if (jump.equalsIgnoreCase("transfer")) {

			// 移除前一个审批人任务
			taskService.removeTaskParticipatingUser(curTask.getId(), oldDoc
					.getSubscriberID(), Participation.CANDIDATE);
			// 公文表设置最新选定的审批人

			oldDoc.setCompanyIDofSubscriber(doc.getReceiverCompanyID());
			oldDoc.setDeptIDofSubscriber(doc.getReceiverDeptID());
			oldDoc.setSubscriberID(doc.getReceiverID());
			oldDoc.setStatus("T"); // 设置公文为转他人审批状态
			oldDoc.setExamineResult("Transfer");
			baseBeanDao.update(oldDoc);

			// // 设置进入待审批任务的来源
			executionService.setVariable(curTask.getExecutionId(),
					"sourceOfExamine", "transfer");

			taskService.assignTask(curTask.getId(), doc.getReceiverID());
			return 4;

		}
		return 5;
	}

	/**
	 * 公文盖章
	 * 
	 * @param jump:
	 *            finish 完成，publish 出版
	 */
	public int sealDocument(Document doc, String jump) {
		String hql = "from Document where docId = ?";
		Document oldDoc = (Document) baseBeanDao.getBeanByHqlAndParams(hql,
				new Object[] { doc.getDocId() });
		String instanceID = oldDoc.getProcessInstanceId();
		// 查询当前文档所在流程实例的任务列表
		ProcessInstance instance = executionService
				.findProcessInstanceById(instanceID);
		
		

		TaskQuery tq = taskService.createTaskQuery();
		List<Task> taskList = tq.processInstanceId(instanceID).list();
		
		Task curTask = null;
		for (Task task : taskList) {
			if (task.getName().equalsIgnoreCase("Seal")) { // 删选出盖章任务
				curTask = task;
				break;
			}

		}
		if (curTask == null)
			return 0;
		oldDoc.setPublishTime(new Date());
		oldDoc.setPublisherID(doc.getReceiverID());
		oldDoc.setDeptIDofPublisher(doc.getReceiverDeptID());
		oldDoc.setCompanyIDofPublisher(doc.getReceiverCompanyID());
		oldDoc.setSealerComment(doc.getSealerComment());
		oldDoc.setSealTime(new Date()); // 盖章结束或盖章并转发行时间

		// 生成正式编号中的号    改到說
		if(oldDoc.getNumCode()==null||oldDoc.getNumCode().equals("")){
		String numCode = docCommpnService.getDocNum(oldDoc.getCompanyID(),
				"numCode");
		
		    oldDoc.setNumCode(numCode);
		    oldDoc.setFormalNum(oldDoc.getFormalNum() + numCode + "号");
		}
		if (jump.equalsIgnoreCase("finish")) {
			oldDoc.setStatus("F"); // 设置公文为完成状态
			baseBeanDao.update(oldDoc);
			// 完成盖章任务，转向结束
			taskService.completeTask(curTask.getId(), "to Not Publish");
			return 1;

		} else if (jump.equalsIgnoreCase("publish")) {
			oldDoc.setStatus("P"); // 设置公文为待出版状态
			baseBeanDao.update(oldDoc);
			// 设置f发行人变量
			executionService.setVariable(instance.getId(), "publisher", doc
					.getReceiverID());
			// 完成盖章任务，转向发行
			taskService.completeTask(curTask.getId(), "to Publish");
			return 2;
		} else if (jump.equalsIgnoreCase("noSeal")) {
			// 驳回盖章到未审批
		   EnvironmentImpl envImpl = ((EnvironmentFactory)processEngine).openEnvironment();
           try{

			ExecutionImpl e = (ExecutionImpl) executionService
					.findExecutionById(curTask.getExecutionId());
			// 当前活动
			ActivityImpl activityFind = e.getActivity();

			ProcessDefinitionImpl processDefinitionImpl = activityFind
					.getProcessDefinition();

			// 找到审批活动
			ActivityImpl applyActivityImpl = processDefinitionImpl
					.findActivity("Examine and Approve");
			// 给当前活动（盖章）创建个Transition出口
			TransitionImpl transition = activityFind.createOutgoingTransition();
			String transitionName = "撤销";

			transition.setName(transitionName);
			// 设置Transition的元活动也就是从哪个活动（盖章）开始。。。
			transition.setSource(activityFind);
			// 设置transition目的活动也就是审批活动
			transition.setDestination(applyActivityImpl);
			// 完成该Transition.
			this.taskService.completeTask(curTask.getId(), transitionName);
			
			oldDoc.setStatus("S"); // 设置公文为待审批状态
			baseBeanDao.update(oldDoc);
           }catch(Exception e){
        	   e.printStackTrace();
           }
           finally{
   			envImpl.close();
   			
   		   }
			return 4;

		}else{
		
			
			// 移除前一个盖章人任务
			taskService.removeTaskParticipatingUser(curTask.getId(), oldDoc.getSealerID()
					, Participation.CANDIDATE);
			// 公文表设置最新选定的审批人

			oldDoc.setSealTime(new Date());
			oldDoc.setSealerID(doc.getReceiverID());
			oldDoc.setDeptIDofSealer(doc.getReceiverDeptID());
			oldDoc.setCompanyIDofSealer(doc.getReceiverCompanyID());
			oldDoc.setStatus("A"); // 设置公文为批准状态（未被盖章）
			oldDoc.setExamineResult("toSeal");
			baseBeanDao.update(oldDoc);

			taskService.assignTask(curTask.getId(), doc.getReceiverID());
		}
		return 3;
	}

	/**
	 * 发行公文
	 * 
	 * @param readers:
	 *            发行对象的读者ID（STAFFID）列表，
	 */
	public String publishDocument(Document doc, String readers) {
		String hql = "from Document where docId = ?";
		Document oldDoc = (Document) baseBeanDao.getBeanByHqlAndParams(hql,
				new Object[] { doc.getDocId() });
		String instanceID = oldDoc.getProcessInstanceId();
		// 查询当前文档所在流程实例的任务列表
		ProcessInstance instance = executionService
				.findProcessInstanceById(instanceID);

		TaskQuery tq = taskService.createTaskQuery();
		List<Task> taskList = tq.processInstanceId(instanceID).list();
		Task curTask = null;
		for (Task task : taskList) {
			if (task.getName().equalsIgnoreCase("Publish")) { // 删选出发行任务
				curTask = task;
				break;
			}
		}
		if (curTask == null)
			return "";
		oldDoc.setStatus("O"); // 设置公文为已经出版状态
		oldDoc.setPublishTime(new Date());
		oldDoc.setPublisherComment(doc.getPublisherComment());
		baseBeanDao.update(oldDoc);
		// 保存读者信息；包括公司部门人ID
		DocGsendInfo dgi = null;
		for (String infos : readers.split(",")) {
			String[] info = infos.split("-");
			List<String> stafflist = new ArrayList<String>();
			if (!stafflist.contains(info[0])) {
				dgi = new DocGsendInfo();
				dgi.setReaderID(info[0]);
				if(info.length>1) {
					dgi.setCompanyID(info[1]);
					dgi.setOrganizationID(info[2]);
				}
				dgi.setDocId(oldDoc.getDocId());
				dgi.setReadstate("1");
				dgi.setReciveTime(Utilities.getDateString(new Date(),
						"yyyy-MM-dd HH:mm:ss"));
				dgi.setGsId(serverService.getServerID("gsId"));
				dgi.setLoad(doc.getLoad());
				dgi.setShares(doc.getShare());
				dgi.setPrint(doc.getPrint());
				dgi.setPub(doc.getPub());
				dgi.setTransfer(doc.getTransfer());
				baseBeanDao.save(dgi);

				stafflist.add(info[0]);
			}
		}

		// 设置读者列表变量
		List<String> assignees = new ArrayList<String>();
		for (String assignee : readers.split(",")) {
			if (!assignees.contains(assignee.split("-")[0])) {
				assignees.add(assignee.split("-")[0]);
			}
		}
		executionService.setVariable(instance.getId(), "assignees", assignees);
		executionService.setVariable(instance.getId(), "transfer", doc
				.getTransfer());
		executionService.setVariable(instance.getId(), "load", doc.getLoad());
		executionService.setVariable(instance.getId(), "print", doc.getPrint());
		executionService.setVariable(instance.getId(), "share", doc.getShare());
		executionService.setVariable(instance.getId(), "pub", doc.getPub());
		
		// 完成发行任务，转向多人阅读任务
		taskService.completeTask(curTask.getId(), "to read");
		
		
		
		return "1";
	}

	/**
	 * 重新发行公文
	 * 
	 * @param readers:
	 *            发行对象的读者ID（STAFFID）列表，以|分割。
	 * @return 1:流程实例结束，不能再群发。2：此次群发中有已经发送过的人员，返回他们的ID列表 3:此次群发中没有已经发送过的人员，返回 ""
	 */
	@SuppressWarnings("static-access")
	public String rePublishDocument(Document doc, CAccount account,
			String readers) {
		Document oldDoc = (Document) baseBeanDao.getBeanByKey(Document.class,
				doc.getKey());
		String instanceID = oldDoc.getProcessInstanceId();
		EnvironmentImpl env = null;
		String existedId = "";// 此次群发中，已经被发送过的人员的ID，以逗号分割
		try {
			ProcessInstance instance = executionService
					.findProcessInstanceById(instanceID);
			if (instance == null)
				return "fail"; // 流程实例已经结束，不能进行此次群发

			env = ((EnvironmentFactory) processEngine).openEnvironment();
			DbSession dbsession = env.getFromCurrent(DbSession.class);
			// 查询当前文档所在流程实例的任务列表
			TaskQuery tq = taskService.createTaskQuery();
			List<Task> taskList = tq.processInstanceId(instanceID).list();
			// 获取custom节点中的read任务
			Task readTask = null;
			for (Task task : taskList) {
				if (task.getName().equalsIgnoreCase("read")) { // 删选出read任务
					readTask = task;
					break;
				}
			}
			if (readTask == null) // 没有获取到read任务，（一般不可能）
				return "fail"; // 不能进行此次群发
			TaskImpl taskImpl = (TaskImpl) readTask;

			HistoryTaskImpl ht = dbsession.get(HistoryTaskImpl.class, taskImpl
					.getDbid());

			String executionId = readTask.getExecutionId();
			ExecutionImpl execution = (ExecutionImpl) executionService
					.findExecutionById(executionId);
			Map<String, Object> variables = executionService
					.getVariables(executionId, executionService
							.getVariableNames(executionId));
			// 获取流程实例中的读者信息
			DocReadInfo docReadInfo = (DocReadInfo) variables.get(readTask
					.getActivityName()
					+ "_READER_INFO");
			Map<String, OprationAuthority> oprationAuthority = docReadInfo
					.getOprationAuthority();
			// 检查群发的读者是否已经被发过，如未被发过，增加群发对象
			for (String assignee : readers.split(",")) {
				if (!docReadInfo.isReaderExist(assignee.split("-")[0])) {// 读者不存在

					// 保存读者信息；包括公司部门人ID
					DocGsendInfo dgi = null;
					String[] info = assignee.split("-");
					dgi = new DocGsendInfo();
					dgi.setReaderID(info[0]);

					if(info.length>1){
						dgi.setCompanyID(info[1]);
						dgi.setOrganizationID(info[2]);
					}else{
						dgi.setCompanyID("");
						dgi.setOrganizationID("");
					}
					dgi.setDocId(oldDoc.getDocId());
					dgi.setReadstate("1");
					dgi.setReciveTime(Utilities.getDateString(new Date(),
							"yyyy-MM-dd HH:mm:ss"));
					dgi.setGsId(serverService.getServerID("gsId"));
					dgi.setLoad(doc.getLoad());
					dgi.setShares(doc.getShare());
					dgi.setPrint(doc.getPrint());
					dgi.setPub(doc.getPub());
					dgi.setTransfer(doc.getTransfer());
					baseBeanDao.save(dgi);

					docReadInfo.addNewReader(assignee.split("-")[0], false); // 设置读者未阅读过
					createMultiReadSubTask(dbsession, execution, taskImpl,
							assignee.split("-")[0], ht);
					OprationAuthority oa = new OprationAuthority();
					oa.setLoad(doc.getLoad());
					oa.setPrint(doc.getPrint());
					oa.setTransfer(doc.getTransfer());
					oa.setShare(doc.getShare());
					oa.setPrint(doc.getPrint());
					oprationAuthority.put(assignee.split("-")[0], oa); // 设置读者的转发、下载和打印的许可信息
				} else {
					existedId += assignee.split("-")[0] + ",";
				}
			}
			docReadInfo.setOprationAuthority(oprationAuthority);
			String name = execution.getActivityName();
			executionService.setVariable(executionId, name + "_READER_INFO",
					docReadInfo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (env != null) {
				env.close();
			}
		}
		return existedId;
	}

	private void createMultiReadSubTask(DbSession dbsession,
			ExecutionImpl execution, TaskImpl task, String user,
			HistoryTaskImpl ht) {
		TaskImpl subtask = task.createSubTask(); // 为read任务创建并行子任务
		subtask.setName("read_" + user);
		subtask.setAssignee(user);
		subtask.setSignalling(false);
		ExecutionImpl exec = execution.createExecution(); // 创建read任务的execution的并行子execution
		ActivityImpl activity = execution.getActivity();
		TransitionImpl transition = activity.createOutgoingTransition();
		transition.setName("read_end");
		exec.setActivity(activity);
		exec.setState(Execution.STATE_ACTIVE_CONCURRENT);
		exec.setHistoryActivityStart(Clock.getTime());
		subtask.setExecution(exec);
		dbsession.save(subtask);
		HistoryEvent.fire(new TaskActivityStart(subtask), exec);
		ht.addSubTask(dbsession.get(HistoryTaskImpl.class, subtask.getDbid()));
	}

	/**
	 * 完成悦读任务
	 * 
	 * @paramdocId
	 *            当前Document的key
	 * @param reader
	 *            读者ID
	 */
	public String completeRead(String docId, String reader) {
		String hql = "from Document where docId = ?";
		Document oldDoc = (Document) baseBeanDao.getBeanByHqlAndParams(hql,
				new Object[] { docId });
		String instanceID = oldDoc.getProcessInstanceId();
		// 查询当前文档所在的流程实例的活动的read活动
		ProcessInstance instance = executionService
				.findProcessInstanceById(instanceID);
		Execution exec = instance.findActiveExecutionIn("read");

		TaskQuery tq;
		if (exec != null) {
			for (Execution child : exec.getExecutions()) {
				tq = taskService.createTaskQuery().executionId(child.getId())
						.assignee(reader);
				List<Task> readList = tq.list();
				if (readList.size() != 0) {
					Task readerTask = readList.get(0);
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("subTask", readerTask);
					// 此调用会触发MultiReadActivity的signal方法
					executionService.signalExecutionById(readerTask
							.getExecutionId(), parameters);
					break;
				}
			}
		}
		
		oldDoc.setReadTime(new Date());
		baseBeanDao.update(oldDoc);
		
		hql = "from DocGsendInfo where readerID = ? and docId = ?";
		List<BaseBean> listg = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{reader,docId});
		for(BaseBean b:listg){
			DocGsendInfo dgi = (DocGsendInfo) b;
			dgi.setReadstate("0");
			dgi.setReadtime(new Date());
			baseBeanDao.update(dgi);

		}
	   
		return "1";

	}



	/**
	 * 
	 * 
	 * 不能直接查数据库时分页，获得的数据是List数据时，进行分页方法。
	 * 
	 * @param list
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */

	public PageForm getPageForm(List<BaseBean> list, int pageNumber,
			int pageSize) {
		int count = 0;
		int pageCount = 0;
		int firstResult = 0;
		int maxResult = 0;
		count = list.size();
		if (count == 0) {
			return null;
		}
		pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
		if (pageNumber < 1)
			pageNumber = 1;
		if (pageNumber > pageCount)
			pageNumber = pageCount;
		firstResult = pageSize * (pageNumber - 1);
		maxResult = Math.min(pageSize, count - firstResult);

		list = list.subList(firstResult, firstResult + maxResult);
		PageForm pageForm = new PageForm();
		pageForm.setPageSize(pageSize);
		pageForm.setRecordCount(count);
		pageForm.setPageCount(pageCount);
		pageForm.setPageNumber(pageNumber);
		pageForm.setList(list);
		return pageForm;
	}

}
