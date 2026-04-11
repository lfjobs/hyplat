package hy.ea.workflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.workflow.DocReadInfo.OprationAuthority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.TaskService;
import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.activity.ExternalActivityBehaviour;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.history.HistoryEvent;
import org.jbpm.pvm.internal.history.events.TaskActivityStart;
import org.jbpm.pvm.internal.history.model.HistoryTaskImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.jbpm.pvm.internal.util.Clock;

//为文档工作流custom节点设定外部行为类
public class MultiReadActivity implements ExternalActivityBehaviour {

	private static final long serialVersionUID = 1673162002085098868L;
//	@Resource
//	private ExecutionService executionService;
//	@Resource
//	private TaskService taskService;
//	@Resource
//	private HistoryService historyService;

	@SuppressWarnings("unchecked")
	public void execute(ActivityExecution activityExecution) {
		ExecutionImpl execution = (ExecutionImpl) activityExecution;
		// 获取工作流引擎的dbsession，用于保存新创建的任务和子任务
		DbSession dbsession = EnvironmentImpl.getFromCurrent(DbSession.class);
		ExecutionService executionService = EnvironmentImpl
				.getFromCurrent(ExecutionService.class);
		TaskImpl taskImpl = createMultiReadTask(dbsession, execution);
		HistoryTaskImpl ht = dbsession.get(HistoryTaskImpl.class, taskImpl
				.getDbid());

		//从流程实例中获取信息
		String executionId = activityExecution.getId();
		Map<String, Object> variables = executionService.getVariables(executionId, executionService.getVariableNames(executionId));
//		logger.info("调试信息");
		
		List<String> assignees = (List<String>)variables.get("assignees");
		String transfer = (String)variables.get("transfer");
		String load = (String)variables.get("load");
		String print = (String)variables.get("print");
		String share = (String)variables.get("share");
		String pub = (String)variables.get("pub");
		//生成读者信息
		DocReadInfo docReadInfo = new DocReadInfo();
		Map<String, Boolean> readers = new HashMap<String, Boolean>();
		Map<String, OprationAuthority> oprationAuthority = new HashMap<String, OprationAuthority>();
//		String assignees = (String) executionService.getVariable(execution., "assignees");
		// 为每个读者创建阅读子任务
		for (int i = 0; i < assignees.size(); i++) {
			String reader = assignees.get(i);
			createMultiReadSubTask(dbsession, execution, taskImpl, reader,ht);
			readers.put(reader, false); // 设置每个读者没有阅读过
			OprationAuthority oa = new OprationAuthority();
			oa.setLoad(load);
			oa.setPrint(print);
			oa.setTransfer(transfer);
			oa.setShare(share);
			oa.setPub(pub);
			oprationAuthority.put(reader, oa);	 //设置每个读者是否可以转发、打印和下载的权限信息	
		}
		// 保存所有用户信息
		docReadInfo.setReaders(readers);
		docReadInfo.setOprationAuthority(oprationAuthority);
		String name = activityExecution.getActivityName();

		executionService.setVariable(activityExecution.getId(), name
				+ "_READER_INFO", docReadInfo);

		execution.waitForSignal();
	}
   //在custom节点创建read任务，并赋予节点相同的execution
	private TaskImpl createMultiReadTask(DbSession dbsession,
			ExecutionImpl execution) {
		TaskImpl task = dbsession.createTask();
		String taskName = execution.getActivityName();
		task.setName(taskName);
		task.setExecution(execution); 
		task.setProcessInstance(execution.getProcessInstance());
		task.setSignalling(false);
		dbsession.save(task);
		HistoryEvent.fire(new TaskActivityStart(task), execution);
		return task;
	}

	private void createMultiReadSubTask(DbSession dbsession,
			ExecutionImpl execution, TaskImpl task, String user,
			HistoryTaskImpl ht) {
		TaskImpl subtask = task.createSubTask(); //为read任务创建并行子任务
		subtask.setName("read_" + user);
		subtask.setAssignee(user);
		subtask.setSignalling(false);
		ExecutionImpl exec = execution.createExecution(); //创建read任务的execution的并行子execution
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

	public void signal(ActivityExecution activityExecution, String signalName,
			Map<String, ?> parameters) throws Exception {
		ExecutionImpl execution = (ExecutionImpl) activityExecution;

		String executionId = activityExecution.getId();
		ExecutionService executionService = EnvironmentImpl
				.getFromCurrent(ExecutionService.class);
		TaskService taskService = EnvironmentImpl.getFromCurrent(TaskService.class);

		Map<String, Object> variables = executionService.getVariables(
				executionId, executionService.getVariableNames(executionId));

		// 获取子任务
		Task subTask = (Task) parameters.get("subTask");
		taskService.completeTask(subTask.getId(),"read_end");// 结束子任务

		// String reader = (String) parameters.get("reader");// 获取读者
		String reader = subTask.getAssignee(); // 获取读者
		// 更新读者信息
		DocReadInfo docReadInfo = (DocReadInfo) variables.get(subTask
				.getActivityName()
				+ "_READER_INFO");

		docReadInfo.setReaderStatus(reader, true); // 设置该读者阅读过文章
		if (docReadInfo.isAllRead()) {
//			execution.end(); // 结束
			String execID = execution.getId();
			int dotPos = execID.lastIndexOf('.');
			String parentExecID = execID.substring(0,dotPos);
//			ExecutionImpl parentExec = execution.getParent();
			ExecutionImpl parentExec = (ExecutionImpl)executionService.findExecutionById(parentExecID);
			if (parentExec != null)
				parentExec.end();
		} else { // 没有全部阅读过
			executionService.setVariables(executionId, variables);
			execution.waitForSignal(); // 继续等待
		}
	}

}
