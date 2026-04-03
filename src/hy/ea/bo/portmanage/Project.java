package hy.ea.bo.portmanage;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2018/5/16 0016.
 */
public class Project implements BaseBean {
    private String projectKey;
    private String projectId;
    private String projectName;
    private String projectUrl;
    private String projectDescription;
    private String projectCreaterId;
    private String projectCreaterName;
    private Date projectCreatTime;
    private String projectModifierId;
    private String projectModifierName;
    private Date projectModificationTime;

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectCreaterId() {
        return projectCreaterId;
    }

    public void setProjectCreaterId(String projectCreaterId) {
        this.projectCreaterId = projectCreaterId;
    }

    public Date getProjectCreatTime() {
        return projectCreatTime;
    }

    public void setProjectCreatTime(Date projectCreatTime) {
        this.projectCreatTime = projectCreatTime;
    }

    public String getProjectCreaterName() {
        return projectCreaterName;
    }

    public void setProjectCreaterName(String projectCreaterName) {
        this.projectCreaterName = projectCreaterName;
    }

    public String getProjectModifierId() {
        return projectModifierId;
    }

    public void setProjectModifierId(String projectModifierId) {
        this.projectModifierId = projectModifierId;
    }

    public String getProjectModifierName() {
        return projectModifierName;
    }

    public void setProjectModifierName(String projectModifierName) {
        this.projectModifierName = projectModifierName;
    }

    public Date getProjectModificationTime() {
        return projectModificationTime;
    }

    public void setProjectModificationTime(Date projectModificationTime) {
        this.projectModificationTime = projectModificationTime;
    }

    /**
     *
     * @param projectKey
     * @param projectId
     * @param projectName
     * @param projectUrl
     * @param projectDescription
     * @param projectCreaterId
     * @param projectCreaterName
     * @param projectCreatTime
     * @param projectModifierId
     * @param projectModifierName
     * @param projectModificationTime
     */
    public Project(String projectKey, String projectId, String projectName, String projectUrl, String projectDescription, String projectCreaterId, String projectCreaterName, Date projectCreatTime, String projectModifierId, String projectModifierName, Date projectModificationTime) {
        this.projectKey = projectKey;
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectUrl = projectUrl;
        this.projectDescription = projectDescription;
        this.projectCreaterId = projectCreaterId;
        this.projectCreaterName = projectCreaterName;
        this.projectCreatTime = projectCreatTime;
        this.projectModifierId = projectModifierId;
        this.projectModifierName = projectModifierName;
        this.projectModificationTime = projectModificationTime;
    }

    public Project() {
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectKey='" + projectKey + '\'' +
                ", projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectUrl='" + projectUrl + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                ", projectCreaterId='" + projectCreaterId + '\'' +
                ", projectCreaterName='" + projectCreaterName + '\'' +
                ", projectCreatTime=" + projectCreatTime +
                ", projectModifierId='" + projectModifierId + '\'' +
                ", projectModifierName='" + projectModifierName + '\'' +
                ", projectModificationTime=" + projectModificationTime +
                '}';
    }
}
