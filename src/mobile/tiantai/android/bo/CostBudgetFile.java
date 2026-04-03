package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class CostBudgetFile implements BaseBean, java.io.Serializable {
    private String budgetFileKey; //主键id
    private String budgetFileId; //编号
    private String budgetItemId; //关联的单号或者预算明细编号
    private String filePath; //文件路径
    private String fileName; //文件名称
    private String fileWebName; //文件在服务器的名称
    private String createName; //上传人
    private Date createTime; //创建时间
    private String type; //文件类型
    private int sort; //排序

    public String getBudgetFileKey() {
        return budgetFileKey;
    }

    public void setBudgetFileKey(String budgetFileKey) {
        this.budgetFileKey = budgetFileKey;
    }

    public String getBudgetFileId() {
        return budgetFileId;
    }

    public void setBudgetFileId(String budgetFileId) {
        this.budgetFileId = budgetFileId;
    }

    public String getBudgetItemId() {
        return budgetItemId;
    }

    public void setBudgetItemId(String budgetItemId) {
        this.budgetItemId = budgetItemId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileWebName() {
        return fileWebName;
    }

    public void setFileWebName(String fileWebName) {
        this.fileWebName = fileWebName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}