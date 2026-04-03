package hy.ea.workflow;

import java.util.List;

import org.jbpm.api.Configuration;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;

public class DeployManage {
	public static String currentPath;
	private static ProcessEngine processEngine;

	public static void main(String[] args) {
		//引用缺省的数据库连接配置文件jbpm.hibernate.cfg.xml
		Configuration cfg = new Configuration();
		// currentPath = System.getProperty("user.dir");
		// read(currentPath + "/src/spring-jbpm4.cfg.xml");
		// File file = new File(currentPath + "/src/spring-jbpm4.cfg.xml");
		// cfg.setResource("spring-jbpm4.cfg.xml");
		// cfg.setFile(file);
		processEngine = cfg.buildProcessEngine();
		DeployManage dm = new DeployManage();
		dm.deployWorkFlow("hy/ea/workflow/ExamineAndApprove.jpdl.xml");
//    	dm.deleteWorkFlow("ExamineAndApprove");
//		dm.deleteLatestedWorkFlow("ExamineAndApprove");
	}

	@SuppressWarnings("unused")
	public void deployWorkFlow(String classPath) {
		RepositoryService repository = processEngine.getRepositoryService();
		// 发布流程定义
		String deploymentId = repository.createDeployment()
				.addResourceFromClasspath(classPath).deploy();
	}

	//删除以某个名称命名的所有流程定义（包括运行数据）
	public void deleteWorkFlow(String processName) {
		String deploymentId = null;
		RepositoryService repository = processEngine.getRepositoryService();

		List<ProcessDefinition> pdlist = repository
				.createProcessDefinitionQuery().processDefinitionName(processName).list();
		for (ProcessDefinition pd : pdlist) {
			deploymentId = pd.getDeploymentId();
			// 删除流程定义
			if (deploymentId != null)
				repository.deleteDeploymentCascade(deploymentId);
		}
	}

	//删除以某个名称命名的最新的流程定义（包括运行数据）
	public void deleteLatestedWorkFlow(String processName) {
		RepositoryService repository = processEngine.getRepositoryService();

		List<ProcessDefinition> pdlist = repository
				.createProcessDefinitionQuery().processDefinitionName(
						processName).orderDesc(
						ProcessDefinitionQuery.PROPERTY_VERSION).list();
		ProcessDefinition pd = pdlist.get(0);
		repository.deleteDeploymentCascade(pd.getDeploymentId());
	}
}
