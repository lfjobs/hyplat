package hy.ea.office.service.impl;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.DocComplaint;
import hy.ea.bo.office.DocGsendInfo;
import hy.ea.bo.office.DocPosition;
import hy.ea.bo.office.Document;
import hy.ea.bo.office.TelMessage;
import hy.ea.office.service.ExtralFlowService; 
import hy.ea.util.MobileMessage;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
   
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.ProcessInstanceQuery;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.history.HistoryProcessInstanceQuery;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.history.HistoryTaskQuery;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExtralFlowServiceImpl implements ExtralFlowService {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ExecutionService executionService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private BaseBeanDao baseBeanDao;
	@Autowired
	private MobileMessage mobileMessage;//短信接口 
	
	@SuppressWarnings("unused")
	private Map<String, TelMessage> telMessageMap; 

	// /////////////////////////////////////////////////////////////////////////////////////
	// //////////////////********流程触发*********///////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 发起公文流转流程(第一次发送)
	 * 
	 */
	public String createDocument(Document doc, CAccount account) {
		Date curDate = new Date();
		doc.setStatus("S"); // 设置公文为被发送状态
		doc.setUpdateTime(curDate);
		// 1. 创建公文
		// 2. 创建流程实例,并设置公文起草人变量值
		Map<String, Object> drafterMap = new HashMap<String, Object>();
		drafterMap.put("drafter", doc.getDrafterID());
		ProcessInstance processInstance = executionService
				.startProcessInstanceByKey("ExamineAndApprove", drafterMap, doc
						.getDocId());
		// 3. 公文和流程实例绑定
		doc.setProcessInstanceId(processInstance.getId());
		baseBeanDao.update(doc);

		// 创建保留历史的变量
		executionService.createVariable(processInstance.getId(), "docId", doc
				.getDocId(), true);

		// 设置公文审核人变量值
		executionService.setVariable(processInstance.getId(), "subscriber", doc
				.getSubscriberID());

		// 指定流程从draft任务流转到Examine and Approve任务
		Execution exec = processInstance.findActiveExecutionIn("draft");

		executionService.signalExecutionById(exec.getId(),
				"to Examine and Approve");
		return doc.getDocId();
	}

	/**
	 * 审批公文
	 * 
	 * @param jump:
	 *            reject 驳回，noapprove 不批准，approve 批准，transfer 转他人审批
	 */
	public String examineDocument(Document doc, DocComplaint dc,
			CAccount account, String jump) {
		String instanceID = doc.getProcessInstanceId();
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
			return "";
		if (jump.equals("reject")) {
			doc.setStatus("R"); // 设置公文为被驳回状态
			doc.setUpdateTime(new Date());
			baseBeanDao.update(doc);
			dc.setStatus("reject");
			dc.setStatusPic("images/complaint/reject.jpg");
			baseBeanDao.update(dc);
			taskService.completeTask(curTask.getId(), "Reject");// 完成审批任务，转向驳回
		} else if (jump.equals("dealer")) {
			doc.setStatus("R"); // 设置公文为被驳回状态
			doc.setUpdateTime(new Date());
			baseBeanDao.update(doc);
			dc.setStatus("pass");
			dc.setStatusPic("images/complaint/pass.png");
			baseBeanDao.update(dc);
			taskService.completeTask(curTask.getId(), "Reject");// 完成审批任务，转向驳回
		} else if (jump.equalsIgnoreCase("seal")) {// 审批通过去盖章
			// 设定盖章人
			Date curDate = new Date();// 获取当前时间
			doc.setSealTime(curDate);
			doc.setStatus("A"); // 设置公文为批准状态（未被盖章）
			baseBeanDao.update(doc);
			dc.setAuditTime(curDate);
			dc.setStatus("seal");
			dc.setStatusPic("images/complaint/seal.png");
			baseBeanDao.update(dc);
			// 设置盖章人变量
			executionService.setVariable(instance.getId(), "sealer", doc
					.getSealerID());
			// 完成审批任务，转向盖章
			taskService.completeTask(curTask.getId(), "to Seal");
		} else if (jump.equalsIgnoreCase("transfer")) {
			// 公文表设置最新选定的审批人
			Date curDate = new Date();// 获取当前时间
			dc.setAuditTime(curDate);
			baseBeanDao.update(dc);
			taskService.assignTask(curTask.getId(), doc.getSubscriberID2());// 新的审批人
			taskService.removeTaskParticipatingUser(curTask.getId(), doc // 旧的审批人
					.getSubscriberID(), Participation.CANDIDATE);

			doc.setSubscribeTime(curDate);
			doc.setCompanyIDofSubscriber(doc.getCompanyIDofSubscriber2());
			doc.setDeptIDofSubscriber(doc.getDeptIDofSubscriber2());
			doc.setSubscriberID(doc.getSubscriberID2());
			doc.setStatus("T"); // 设置公文为转他人审批状态
			baseBeanDao.update(doc);

		}
		return "";
	}

	/**
	 * 公文盖章
	 * 
	 * @param jump:
	 *            finish 完成
	 */
	public String sealDocument(Document doc, DocComplaint dc, CAccount account) {
		Document oldDoc = (Document) baseBeanDao.getBeanByKey(Document.class,
				doc.getKey());
		String instanceID = oldDoc.getProcessInstanceId();
		// 查询当前文档所在流程实例的任务列表
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
			return "";
		Date curDate = new Date();// 获取当前时间
		oldDoc.setSealTime(curDate); // 盖章结束返还投诉处理人
		oldDoc.setStatus("F"); // 设置公文为完成状态
		baseBeanDao.update(oldDoc);
		dc.setAuditTime(curDate);
		dc.setStatus("pass");
		dc.setStatusPic("images/complaint/pass.png");
		baseBeanDao.update(dc);

		// 完成盖章任务，转向结束
		taskService.completeTask(curTask.getId(), "to Not Publish");
		return "";
	}

	/**
	 * 获取该登录人员手上待完成的公文分页列表
	 * 
	 * @param staffID
	 *            人员ID
	 * @param type
	 *            任务类型(examine:审批任务。seal:盖章任务)
	 * @return 公文列表
	 */
	public List<BaseBean> findUnFinishedDocument(String staffID, String type,
			String module) {
		// 根据流程定义Key,获取最新的流程定义ID（一个流程定义可以发布多次）
		try {
			ProcessDefinitionQuery pdq = repositoryService
					.createProcessDefinitionQuery().processDefinitionKey(
							"ExamineAndApprove");
			List<ProcessDefinition> pdList = pdq.orderDesc(
					ProcessDefinitionQuery.PROPERTY_VERSION).list();
			ProcessDefinition pd = pdList.get(0);
			TaskQuery tq = null;
			List<Task> taskList = new ArrayList<Task>();
			List<BaseBean> retDocList = new ArrayList<BaseBean>();
			String hql = "from Document where docId = ? and module = ?";

			if (type.equalsIgnoreCase("examine")) {
				tq = taskService.createTaskQuery().processDefinitionId(
						pd.getId()).assignee(staffID).activityName(
						"Examine and Approve").orderDesc(
						TaskQuery.PROPERTY_CREATEDATE);

			} else {
				tq = taskService.createTaskQuery().processDefinitionId(
						pd.getId()).assignee(staffID).activityName("Seal")
						.orderDesc(TaskQuery.PROPERTY_CREATEDATE);
			}

			taskList = tq.list();

			for (Task t : taskList) {
				String docID = (String) executionService.getVariable(t
						.getExecutionId(), "docId");
				Document doc = null;
				doc = (Document) baseBeanDao.getBeanByHqlAndParams(hql,
						new Object[] { docID, module });

				if (doc != null) {
					retDocList.add(doc);
				}

			}

			return retDocList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 获取该登录人员已经完成的公文
	 * 
	 * @param staffID
	 *            人员ID
	 * 
	 */
	public List<BaseBean> findFinishedDocument(String staffID, String type,
			String module) {
		// 根据流程定义Key,获取最新的流程定义ID（一个流程定义可以发布多次）
		ProcessDefinitionQuery pdq = repositoryService
				.createProcessDefinitionQuery().processDefinitionKey(
						"ExamineAndApprove");
		List<ProcessDefinition> pdList = pdq.orderDesc(
				ProcessDefinitionQuery.PROPERTY_VERSION).list();
		ProcessDefinition pd = pdList.get(0);
		// 获取流程定义的历史流程实例
		HistoryProcessInstanceQuery hpiq = historyService
				.createHistoryProcessInstanceQuery().processDefinitionId(
						pd.getId());
		List<HistoryProcessInstance> hpiList = hpiq.list();

		List<HistoryTask> hisTaskList = new ArrayList<HistoryTask>();
		for (HistoryProcessInstance hpi : hpiList) { // 循环流程历史实例
			HistoryTaskQuery htq;
			htq = historyService.createHistoryTaskQuery().assignee(staffID);
			List<HistoryTask> taskList = htq.executionId(
					hpi.getProcessInstanceId()).list();
			hisTaskList.addAll(taskList);
		}

		List<BaseBean> docList = new ArrayList<BaseBean>();

		if (type.equalsIgnoreCase("examine")) {
			try {
				for (HistoryTask ht : hisTaskList) {
					// 根据历史任务的输出跳转，判断任务节点是否为：审批节点
					String outTransfer = ht.getOutcome();
					if (outTransfer == null)
						continue;
					if (outTransfer.equalsIgnoreCase("Reject")
							|| outTransfer.equalsIgnoreCase("to Seal")) {
						String docID = (String) historyService.getVariable(ht
								.getExecutionId(), "docId");
						String hql = "from Document where docId=? and module = ?";
						Document doc = (Document) baseBeanDao
								.getBeanByHqlAndParams(hql, new Object[] {
										docID, module });
						if (doc == null) {
							continue;
						}
						docList.add(doc);

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (type.equalsIgnoreCase("seal")) {
			for (HistoryTask ht : hisTaskList) {
				// 根据历史任务的输出跳转，判断任务节点是否为：盖章节点
				String outTransfer = ht.getOutcome();
				if (outTransfer == null)
					continue;
				if (outTransfer.equalsIgnoreCase("to Not Publish")) {
					String docID = (String) historyService.getVariable(ht
							.getExecutionId(), "docId");
					String hql = "from Document where docId=? and module = ?";
					Document doc = (Document) baseBeanDao
							.getBeanByHqlAndParams(hql, new Object[] { docID,
									module });
					if (doc == null) {
						continue;
					}

					docList.add(doc);
				}

			}
		}

		return docList;
	}

	/**
	 * 
	 * 用于查询公文位置时获得代办公文列表
	 */
	public List<BaseBean> findUnFinishedDocument(String staffID, String type,
			DocPosition docPosition) {
		// 根据流程定义Key,获取最新的流程定义ID（一个流程定义可以发布多次）
		try {
			ProcessDefinitionQuery pdq = repositoryService
					.createProcessDefinitionQuery().processDefinitionKey(
							"ExamineAndApprove");
			List<ProcessDefinition> pdList = pdq.orderDesc(
					ProcessDefinitionQuery.PROPERTY_VERSION).list();
			ProcessDefinition pd = pdList.get(0);
			TaskQuery tq = null;
			List<Task> taskList = new ArrayList<Task>();
			List<BaseBean> retDocList = new ArrayList<BaseBean>();
			if (type.equalsIgnoreCase("read")) {
				// 查询审批流程定义文件的所有审批流程中的read活动中任务列表
				ProcessInstanceQuery piq = executionService
						.createProcessInstanceQuery().processDefinitionId(
								pd.getId());

				List<ProcessInstance> piList = piq.list();
				for (ProcessInstance pi : piList) {

					Execution exec = pi.findActiveExecutionIn("read");
					if (exec != null) {
						for (Execution child : exec.getExecutions()) {
							tq = taskService.createTaskQuery().executionId(
									child.getId()).assignee(staffID);
							List<Task> readList = tq.list();
							if (readList.size() > 0) {
								taskList.addAll(readList);
							}
						}
					}

				}

			} else { // 非阅读任务

				if (type.equalsIgnoreCase("examine")) {
					tq = taskService.createTaskQuery().processDefinitionId(
							pd.getId()).assignee(staffID).activityName(
							"Examine and Approve").orderDesc(
							TaskQuery.PROPERTY_CREATEDATE);

				} else if (type.equalsIgnoreCase("seal")) {
					tq = taskService.createTaskQuery().processDefinitionId(
							pd.getId()).assignee(staffID).activityName("Seal")
							.orderDesc(TaskQuery.PROPERTY_CREATEDATE);
				} else {
					tq = taskService.createTaskQuery().processDefinitionId(
							pd.getId()).assignee(staffID).activityName(
							"Publish").orderDesc(TaskQuery.PROPERTY_CREATEDATE);

				}

				taskList = tq.list();

			}
			String hql = "";
			String hql1 = "";
			String hql2 = "";
			String hql3 = "";
			Object[] params = null;

			for (Task t : taskList) {
				String docID = (String) executionService.getVariable(t
						.getExecutionId(), "docId");
				Document doc = null;
				hql = "from Document where docId=? and module != 'webcomplaint'";
				hql1 = " and docNum like ? and title like ? and module like ?";
				params = new Object[] { docID };

				if (docPosition != null) {
					params = new Object[] { docID,
							"%" + docPosition.getDocNum() + "%",
							"%" + docPosition.getTitle() + "%",
							"%" + docPosition.getModule() + "%",
							"%" + docPosition.getDepartment() + "%",
							"%" + docPosition.getFromMember() + "%" };
					if (type.equals("examine")) {

						hql2 = " and deptIDofSubscriber like ? and drafterID like ?";
					} else if (type.equals("seal")) {
						hql2 = " and deptIDofSealer like ? and subscriberID like ?";

					} else if (type.equals("publish")) {
						hql2 = " and deptIDofPublisher like ? and sealerID like ?";
					} else {
						params = new Object[] { docID,
								"%" + docPosition.getDocNum() + "%",
								"%" + docPosition.getTitle() + "%",
								"%" + docPosition.getModule() + "%" };
					}

					hql = hql + hql1 + hql2;

					doc = (Document) baseBeanDao.getBeanByHqlAndParams(hql,
							params);

					if (doc != null) {
						if (type.equals("read")) {
							if (docPosition.getDepartment() != null
									&& !docPosition.getDepartment().equals("")) {
								hql3 = "from DocGsendInfo where docId = ? and readerID = ? and organizationID like ?";
								DocGsendInfo dgi = (DocGsendInfo) baseBeanDao
										.getBeanByHqlAndParams(
												hql3,
												new Object[] {
														doc.getDocId(),
														staffID,
														"%"
																+ docPosition
																		.getDepartment()
																+ "%" });
								if (dgi != null) {
									retDocList.add(doc);
								}
							} else {
								hql3 = "from DocGsendInfo where docId = ? and readerID = ?";
								DocGsendInfo dgi = (DocGsendInfo) baseBeanDao
										.getBeanByHqlAndParams(
												hql3,
												new Object[] {
														doc.getDocId(),
														staffID,
														"%"
																+ docPosition
																		.getDepartment()
																+ "%" });
								if (dgi != null) {
									retDocList.add(doc);
								}
							}
						} else {
							retDocList.add(doc);
						}
					}
				} else {
					doc = (Document) baseBeanDao.getBeanByHqlAndParams(hql,
							params);

					if (doc != null) {
						if (type.equals("read")) {
							String hql4 = "from DocGsendInfo where docId = ? and readerID = ?";
							DocGsendInfo dgi = (DocGsendInfo) baseBeanDao
									.getBeanByHqlAndParams(hql4, new Object[] {
											doc.getDocId(), staffID });
							if (dgi != null) {
								retDocList.add(doc);
							}

						} else {
							retDocList.add(doc);
						}
					}

				}

			}
			if (type.equalsIgnoreCase("read")) {
				Collections.sort(retDocList, new Comparator<BaseBean>() {
					public int compare(BaseBean one, BaseBean another) {
						Document docOne = (Document) one;
						Document docAnother = (Document) another;
						if (docOne.getPublishTime().after(
								docAnother.getPublishTime()))
							return -1;
						else if (docOne.getPublishTime().compareTo(
								docAnother.getPublishTime()) == 0)
							return 0;
						else
							return 1;
					}
				});

			}
			return retDocList;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

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
		PageForm pageForm = new PageForm();
		if (list.size() != 0) {
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
			pageForm.setPageSize(pageSize);
			pageForm.setRecordCount(count);
			pageForm.setPageCount(pageCount);
			pageForm.setPageNumber(pageNumber);
			pageForm.setList(list);
		}
		return pageForm;
	}

	/**
	 * 
	 * 转移数据：包括投诉处理箱，以及工作流中的麻烦事。
	 */
	public String transferData(String dealerID, String oldDealerID,
			String companyID) {
		if (dealerID.equals(oldDealerID)) {
			return "";
		}
		try {
			String hql = "from Document where drafterID = ? and companyID = ? and module  = ?";
			String hql2 = "from DocComplaint where docId = ?";
			List<BaseBean> doclist = baseBeanDao.getListBeanByHqlAndParams(hql,
					new Object[] { oldDealerID, companyID, "webcomplaint" });
			DocComplaint dc = null;
			if (doclist.size() != 0) {
				for (BaseBean b : doclist) {
					Document doc = (Document) b;
					doc.setDrafterID(dealerID);
					baseBeanDao.update(doc);
					dc = (DocComplaint) baseBeanDao.getBeanByHqlAndParams(hql2,
							new Object[] { doc.getDocId() });

					dc.setResponsibilitor(dealerID);
					baseBeanDao.update(dc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 任务类型(examine:审批任务。seal:盖章任务。publish:出版任务:read:阅读)
	public String sendMessage(String senderID, String telNumber,
			String typemessage) {
		// 登录用户信息
		String hql = "from Staff where staffID = ?";
		Staff userInfo = null;
		if (senderID != null) {
			userInfo = (Staff) baseBeanDao.getBeanByHqlAndParams(hql,
					new Object[] { senderID });
		}
		String content = "";
		if (typemessage.equals("g")) {
			content = "您好，您收到一条网站投诉，请登录http://www.impf2010.com/网址,网站投诉模块进行查收处理！";
		} else if (typemessage.equals("b")) {
			content = "您好，" + userInfo.getStaffName()
					+ "驳回了您发送的投诉，请登录http://www.impf2010.com/网址,网站投诉模块进行查收处理！";
		} else if (typemessage.equals("dealer")) {
			content = "您好，您有一条投诉已经通过审核,请登录http://www.impf2010.com/网址,网站投诉模块进行查收处理！";
		} else if (typemessage.equals("seal")) {
			content = "您收到一条待盖章投诉，请登录http://www.impf2010.com/网址,网站投诉模块进行查收处理！";
		} else if (typemessage.equals("examine")) {
			content = "您收到一条待审批投诉，请登录http://www.impf2010.com/网址,网站投诉模块进行查收处理！";
		} else if (typemessage.equals("reply")) {
			content = "您在网站进行的投诉得到了回复，请登录http://www.ttsw2010.com/网止，投诉模块进行查收";
		} else {
			content = "您好，您有一条投诉已经通过审核并已盖章,请登录http://www.impf2010.com/网址,网站投诉模块进行查收处理！";
		}
		
		String reStr = ""; // 消息发送返回的状态
		if (telNumber != null && !telNumber.equals("")) {

			TelMessage telMessage = new TelMessage();
			telMessage.setContent(content);

			// 发送消息到外部系统
			String tels = telNumber;
			 
			try {
 
				String d = tels.replaceAll("[^0-9-]+", " ").trim();
				String t = d.replaceAll("[ ]+", ","); // 第二个参数是短信发送需要的电话号码分割url。etc.136855,11255
 
				mobileMessage.setMessage(telMessage.getContent());
				mobileMessage.setMobiles(t);
				reStr = mobileMessage.sendMsg();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return reStr;

	} 
}
