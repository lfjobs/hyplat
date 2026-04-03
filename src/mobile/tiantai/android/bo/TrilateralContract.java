package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class TrilateralContract implements BaseBean {
    private String key;
    private String templateId;
    private String companyId;
    private String templateName;//公文类型名称
    private String templatePath;//文件的路径
    private String ext;
    private String templateType;//公文类型的代表字母
    private String fileType;//文件类型以前用W,E表示
    private String fileShowName;//模板显示名称
    private String fileSaveName;//模板保存名称，不包括扩展名
    private Date time;
    private String createrID;
    private String createrName;
    private String receiptType;
    private String pdfpath;//转换成PDf格式的路径
    private String sysSet;//预设00
    private String temptId;//模板分类ID
    private Date pdfTime;//生成PDF时间
    private int seq;
    private String module;//类别 比如合同还是公文或者其他
    private String contractType;
    private String contractTypeName;
    private String trilateralDataId;

    public TrilateralContract() {
    }

    public TrilateralContract(String key, String templateId, String companyId, String templateName, String templatePath, String ext, String templateType, String fileType, String fileShowName, String fileSaveName, Date time, String createrID, String createrName, String receiptType, String pdfpath, String sysSet, String temptId, Date pdfTime, int seq, String module, String contractType, String contractTypeName, String trilateralDataId) {
        this.key = key;
        this.templateId = templateId;
        this.companyId = companyId;
        this.templateName = templateName;
        this.templatePath = templatePath;
        this.ext = ext;
        this.templateType = templateType;
        this.fileType = fileType;
        this.fileShowName = fileShowName;
        this.fileSaveName = fileSaveName;
        this.time = time;
        this.createrID = createrID;
        this.createrName = createrName;
        this.receiptType = receiptType;
        this.pdfpath = pdfpath;
        this.sysSet = sysSet;
        this.temptId = temptId;
        this.pdfTime = pdfTime;
        this.seq = seq;
        this.module = module;
        this.contractType = contractType;
        this.contractTypeName = contractTypeName;
        this.trilateralDataId = trilateralDataId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileShowName() {
        return fileShowName;
    }

    public void setFileShowName(String fileShowName) {
        this.fileShowName = fileShowName;
    }

    public String getFileSaveName() {
        return fileSaveName;
    }

    public void setFileSaveName(String fileSaveName) {
        this.fileSaveName = fileSaveName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCreaterID() {
        return createrID;
    }

    public void setCreaterID(String createrID) {
        this.createrID = createrID;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public String getPdfpath() {
        return pdfpath;
    }

    public void setPdfpath(String pdfpath) {
        this.pdfpath = pdfpath;
    }

    public String getSysSet() {
        return sysSet;
    }

    public void setSysSet(String sysSet) {
        this.sysSet = sysSet;
    }

    public String getTemptId() {
        return temptId;
    }

    public void setTemptId(String temptId) {
        this.temptId = temptId;
    }

    public Date getPdfTime() {
        return pdfTime;
    }

    public void setPdfTime(Date pdfTime) {
        this.pdfTime = pdfTime;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    public String getTrilateralDataId() {
        return trilateralDataId;
    }

    public void setTrilateralDataId(String trilateralDataId) {
        this.trilateralDataId = trilateralDataId;
    }
}
