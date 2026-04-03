package hy.ea.workflow;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

public class AssignDocFlowTask implements AssignmentHandler {

	private static final long serialVersionUID = 1L;

	private String task;

	public void assign(Assignable assignable, OpenExecution execution) {
		if (task.equalsIgnoreCase("approve"))  //在审核、审批任务中
			assignable.setAssignee(execution.getVariable("subscriber")
					.toString());
		else if (task.equalsIgnoreCase("seal"))  //在盖章任务中
			assignable.setAssignee(execution.getVariable("sealer").toString());
		else if (task.equalsIgnoreCase("publish"))  //在公文发放任务中
			assignable.setAssignee(execution.getVariable("publisher")
					.toString());
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

}
