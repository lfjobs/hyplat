package hy.ea.bo;

import hy.plat.bo.BaseBean;


public class CCode implements BaseBean {
    private String codeKey;
    private String codeID;
    private String codePID;
    private String companyID;
    private int codeNumber;
    private String codeValue;
    /**
     * 00不可停用 01可停用 98已停用
     */
    private String codeStatus;
    private String codeDesc;

    /**
     * 自定义工资类别 00加分项    01减分项
     */
    private String wageStatus;

    private String codeSn;
    private String isLeaf;//是否为叶子节点00为叶子节点 空不是；
    private String groupSn;//分组
    private String goodsID;//物品ID
    private String iconPath;//图标路径mz
    private String codetype;//类别:FL ,行业:HY

    public String getCodetype() {
        return codetype;
    }

    public void setCodetype(String codetype) {
        this.codetype = codetype;
    }

    public int getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(int codeNumber) {
        this.codeNumber = codeNumber;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    public String getCodeID() {
        return codeID;
    }

    public void setCodeID(String codeID) {
        this.codeID = codeID;
    }

    public String getCodePID() {
        return codePID;
    }

    public void setCodePID(String codePID) {
        this.codePID = codePID;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    /**
     * 00不可停用 01可停用 98已停用
     */
    public String getCodeStatus() {
        return codeStatus;
    }

    /**
     * 00不可停用 01可停用 98已停用
     */
    public void setCodeStatus(String codeStatus) {
        this.codeStatus = codeStatus;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public String getWageStatus() {
        return wageStatus;
    }

    public void setWageStatus(String wageStatus) {
        this.wageStatus = wageStatus;
    }

    public String getCodeSn() {
        return codeSn;
    }

    public void setCodeSn(String codeSn) {
        this.codeSn = codeSn;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getGroupSn() {
        return groupSn;
    }

    public void setGroupSn(String groupSn) {
        this.groupSn = groupSn;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

}
