package hy.plat.bo;

/**
 * SCode entity. @author MyEclipse Persistence Tools
 */

public class SCode implements BaseBean {

    // Fields

    private String codeKey;
    private String codeID;
    private String codePID;
    private Integer codeNumber;
    private String codeValue;
    private String codeStatus;
    private String codeDesc;
    private String codeSn;
    private String isLeaf;
    private String groupSn;
    private String iconPath;
    private String codetype;//类别:FL ,行业:HY

    // Constructors

    /**
     * default constructor
     */
    public SCode() {
    }

    /**
     * minimal constructor
     */
    public SCode(String codeID, String codePID, Integer codeNumber,
                 String codeValue, String codeStatus) {
        this.codeID = codeID;
        this.codePID = codePID;
        this.codeNumber = codeNumber;
        this.codeValue = codeValue;
        this.codeStatus = codeStatus;
    }

    /**
     * full constructor
     */
    public SCode(String codeID, String codePID, Integer codeNumber,
                 String codeValue, String codeStatus, String codeDesc, String codeSn) {
        this.codeID = codeID;
        this.codePID = codePID;
        this.codeNumber = codeNumber;
        this.codeValue = codeValue;
        this.codeStatus = codeStatus;
        this.codeDesc = codeDesc;
        this.codeSn = codeSn;
    }

    // Property accessors

    public String getCodetype() {
        return codetype;
    }

    public void setCodetype(String codetype) {
        this.codetype = codetype;
    }

    public String getCodeKey() {
        return this.codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    public String getCodeID() {
        return this.codeID;
    }

    public void setCodeID(String codeID) {
        this.codeID = codeID;
    }

    public String getCodePID() {
        return this.codePID;
    }

    public void setCodePID(String codePID) {
        this.codePID = codePID;
    }

    public Integer getCodeNumber() {
        return this.codeNumber;
    }

    public void setCodeNumber(Integer codeNumber) {
        this.codeNumber = codeNumber;
    }

    public String getCodeValue() {
        return this.codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodeStatus() {
        return this.codeStatus;
    }

    public void setCodeStatus(String codeStatus) {
        this.codeStatus = codeStatus;
    }

    public String getCodeDesc() {
        return this.codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public String getCodeSn() {
        return this.codeSn;
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

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

}