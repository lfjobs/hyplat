package hy.ea.bo.portmanage;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2018/5/17 0017.
 */
public class PortParticulars implements BaseBean{
    private String portKey;
    private String portId;
    private String portName;            //接口名称
    private String portUrl;             //接口地址
    private String portDescription;     //接口描述
    private String portRequestType;     //请求类型
    private String dataFormat;          //返回值类型
    private String successParameter;    //返回示例
    private String failParameter;       //异常示例
    private String portCreaterId;       //接口创建人id
    private String portCreaterName;     //接口创建人姓名
    private Date portCreatTime;         //接口创建时间
    private String portModifierId;      //接口修改人id
    private String portModifierName;    //接口修改人姓名
    private Date portModificationTime;  //接口修改时间
    private String documentsId;

    public String getDocumentsId() {
        return documentsId;
    }

    public void setDocumentsId(String documentsId) {
        this.documentsId = documentsId;
    }

    public String getPortKey() {
        return portKey;
    }

    public void setPortKey(String portKey) {
        this.portKey = portKey;
    }

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getPortUrl() {
        return portUrl;
    }

    public void setPortUrl(String portUrl) {
        this.portUrl = portUrl;
    }

    public String getPortDescription() {
        return portDescription;
    }

    public void setPortDescription(String portDescription) {
        this.portDescription = portDescription;
    }

    public String getPortRequestType() {
        return portRequestType;
    }

    public void setPortRequestType(String portRequestType) {
        this.portRequestType = portRequestType;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getSuccessParameter() {
        return successParameter;
    }

    public void setSuccessParameter(String successParameter) {
        this.successParameter = successParameter;
    }

    public String getFailParameter() {
        return failParameter;
    }

    public void setFailParameter(String failParameter) {
        this.failParameter = failParameter;
    }

    public String getPortCreaterId() {
        return portCreaterId;
    }

    public void setPortCreaterId(String portCreaterId) {
        this.portCreaterId = portCreaterId;
    }

    public Date getPortCreatTime() {
        return portCreatTime;
    }

    public void setPortCreatTime(Date portCreatTime) {
        this.portCreatTime = portCreatTime;
    }

    public String getPortCreaterName() {
        return portCreaterName;
    }

    public void setPortCreaterName(String portCreaterName) {
        this.portCreaterName = portCreaterName;
    }

    public String getPortModifierId() {
        return portModifierId;
    }

    public void setPortModifierId(String portModifierId) {
        this.portModifierId = portModifierId;
    }

    public String getPortModifierName() {
        return portModifierName;
    }

    public void setPortModifierName(String portModifierName) {
        this.portModifierName = portModifierName;
    }

    public Date getPortModificationTime() {
        return portModificationTime;
    }

    public void setPortModificationTime(Date portModificationTime) {
        this.portModificationTime = portModificationTime;
    }

    /**
     * 有参无参构造
     */
    public PortParticulars() {
    }

    /**
     *
     * @param portKey
     * @param portId
     * @param portName
     * @param portUrl
     * @param portDescription
     * @param portRequestType
     * @param dataFormat
     * @param successParameter
     * @param failParameter
     * @param portCreaterId
     * @param portCreaterName
     * @param portCreatTime
     * @param portModifierId
     * @param portModifierName
     * @param portModificationTime
     * @param documentsId
     */
    public PortParticulars(String portKey, String portId, String portName, String portUrl, String portDescription, String portRequestType, String dataFormat, String successParameter, String failParameter, String portCreaterId, String portCreaterName, Date portCreatTime, String portModifierId, String portModifierName, Date portModificationTime, String documentsId) {
        this.portKey = portKey;
        this.portId = portId;
        this.portName = portName;
        this.portUrl = portUrl;
        this.portDescription = portDescription;
        this.portRequestType = portRequestType;
        this.dataFormat = dataFormat;
        this.successParameter = successParameter;
        this.failParameter = failParameter;
        this.portCreaterId = portCreaterId;
        this.portCreaterName = portCreaterName;
        this.portCreatTime = portCreatTime;
        this.portModifierId = portModifierId;
        this.portModifierName = portModifierName;
        this.portModificationTime = portModificationTime;
        this.documentsId = documentsId;
    }

    @Override
    public String toString() {
        return "PortParticulars{" +
                "portKey='" + portKey + '\'' +
                ", portId='" + portId + '\'' +
                ", portName='" + portName + '\'' +
                ", portUrl='" + portUrl + '\'' +
                ", portDescription='" + portDescription + '\'' +
                ", portRequestType='" + portRequestType + '\'' +
                ", dataFormat='" + dataFormat + '\'' +
                ", successParameter='" + successParameter + '\'' +
                ", failParameter='" + failParameter + '\'' +
                ", portCreaterId='" + portCreaterId + '\'' +
                ", portCreaterName='" + portCreaterName + '\'' +
                ", portCreatTime=" + portCreatTime +
                ", portModifierId='" + portModifierId + '\'' +
                ", portModifierName='" + portModifierName + '\'' +
                ", portModificationTime=" + portModificationTime +
                ", documentsId='" + documentsId + '\'' +
                '}';
    }
}
