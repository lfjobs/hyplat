package hy.plat.bo;

/**
 * BusinessType 行业类型表
 */

public class BusinessType implements BaseBean {

    private String typeKey;
    private String typeId;
    private String typePID;
    private String parentNum;
    private String typeNum;
    private String typeName;
    private String typeShowNum;
    private String typeDesc;
    private int typeLevel;
    private String status;
    private int sortNum;
    private String typePName;//父级项目名称


    public String getTypePName() {
        return typePName;
    }

    public void setTypePName(String typePName) {
        this.typePName = typePName;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypePID() {
        return typePID;
    }

    public void setTypePID(String typePID) {
        this.typePID = typePID;
    }

    public String getParentNum() {
        return parentNum;
    }

    public void setParentNum(String parentNum) {
        this.parentNum = parentNum;
    }

    public String getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(String typeNum) {
        this.typeNum = typeNum;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeShowNum() {
        return typeShowNum;
    }

    public void setTypeShowNum(String typeShowNum) {
        this.typeShowNum = typeShowNum;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public int getTypeLevel() {
        return typeLevel;
    }

    public void setTypeLevel(int typeLevel) {
        this.typeLevel = typeLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}