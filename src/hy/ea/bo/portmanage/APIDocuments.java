package hy.ea.bo.portmanage;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2018/5/16 0016.
 */
public class APIDocuments implements BaseBean {
    private String documentsKey;
    private String documentsId;
    private String documentsName;
    private String documentsCreaterId;
    private String documentsCreaterName;
    private Date documentsCreatTime;
    private String documentsModifierId;
    private String documentsModifierName;
    private Date documentsModificationTime;
    private String projectId;

    public APIDocuments() {
    }

    /**
     *
     * @param documentsKey
     * @param documentsId
     * @param documentsName
     * @param documentsCreaterId
     * @param documentsCreaterName
     * @param documentsCreatTime
     * @param documentsModifierId
     * @param documentsModifierName
     * @param documentsModificationTime
     * @param projectId
     */
    public APIDocuments(String documentsKey, String documentsId, String documentsName, String documentsCreaterId, String documentsCreaterName, Date documentsCreatTime, String documentsModifierId, String documentsModifierName, Date documentsModificationTime, String projectId) {
        this.documentsKey = documentsKey;
        this.documentsId = documentsId;
        this.documentsName = documentsName;
        this.documentsCreaterId = documentsCreaterId;
        this.documentsCreaterName = documentsCreaterName;
        this.documentsCreatTime = documentsCreatTime;
        this.documentsModifierId = documentsModifierId;
        this.documentsModifierName = documentsModifierName;
        this.documentsModificationTime = documentsModificationTime;
        this.projectId = projectId;
    }

    public String getDocumentsKey() {

        return documentsKey;
    }

    public void setDocumentsKey(String documentsKey) {
        this.documentsKey = documentsKey;
    }

    public String getDocumentsId() {
        return documentsId;
    }


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setDocumentsId(String documentsId) {
        this.documentsId = documentsId;
    }

    public String getDocumentsName() {
        return documentsName;
    }

    public void setDocumentsName(String documentsName) {
        this.documentsName = documentsName;
    }

    public String getDocumentsCreaterId() {
        return documentsCreaterId;
    }

    public void setDocumentsCreaterId(String documentsCreaterId) {
        this.documentsCreaterId = documentsCreaterId;
    }

    public Date getDocumentsCreatTime() {
        return documentsCreatTime;
    }

    public void setDocumentsCreatTime(Date documentsCreatTime) {
        this.documentsCreatTime = documentsCreatTime;
    }

    public String getDocumentsCreaterName() {
        return documentsCreaterName;
    }

    public void setDocumentsCreaterName(String documentsCreaterName) {
        this.documentsCreaterName = documentsCreaterName;
    }

    public String getDocumentsModifierId() {
        return documentsModifierId;
    }

    public void setDocumentsModifierId(String documentsModifierId) {
        this.documentsModifierId = documentsModifierId;
    }

    public String getDocumentsModifierName() {
        return documentsModifierName;
    }

    public void setDocumentsModifierName(String documentsModifierName) {
        this.documentsModifierName = documentsModifierName;
    }

    public Date getDocumentsModificationTime() {
        return documentsModificationTime;
    }

    public void setDocumentsModificationTime(Date documentsModificationTime) {
        this.documentsModificationTime = documentsModificationTime;
    }

    @Override
    public String toString() {
        return "APIDocuments{" +
                "documentsKey='" + documentsKey + '\'' +
                ", documentsId='" + documentsId + '\'' +
                ", documentsName='" + documentsName + '\'' +
                ", documentsCreaterId='" + documentsCreaterId + '\'' +
                ", documentsCreaterName='" + documentsCreaterName + '\'' +
                ", documentsCreatTime=" + documentsCreatTime +
                ", documentsModifierId='" + documentsModifierId + '\'' +
                ", documentsModifierName='" + documentsModifierName + '\'' +
                ", documentsModificationTime=" + documentsModificationTime +
                ", projectId='" + projectId + '\'' +
                '}';
    }
}
