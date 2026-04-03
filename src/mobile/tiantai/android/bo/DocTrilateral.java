package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

public class DocTrilateral implements BaseBean, java.io.Serializable {
    private String key;
    private String id;
    private String docId;
    private String staffID;
    private String companyID;
    private String contractType;
    private String contractTypeName;
    private String status;//状态 00 正常 99 已经删除
    private String contractSource;//01三方
    private String trilateralId;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContractSource() {
        return contractSource;
    }

    public void setContractSource(String contractSource) {
        this.contractSource = contractSource;
    }

    public String getTrilateralId() {
        return trilateralId;
    }

    public void setTrilateralId(String trilateralId) {
        this.trilateralId = trilateralId;
    }
}
