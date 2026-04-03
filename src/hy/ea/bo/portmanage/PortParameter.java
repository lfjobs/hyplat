package hy.ea.bo.portmanage;

import hy.plat.bo.BaseBean;

/**
 * Created by Administrator on 2018/5/17 0017.
 */
public class PortParameter implements BaseBean {
    private String parameterKey;
    private String parameterId;
    private String parameterName;       //参数名称
    private String parameterType;       //参数类型
    private String parameterExplain;    //参数说明
    private String required;            //是否必填
    private String example;             //示例
    private String portId;              //关联接口


    public String getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(String parameterKey) {
        this.parameterKey = parameterKey;
    }

    public String getParameterId() {
        return parameterId;
    }

    public void setParameterId(String parameterId) {
        this.parameterId = parameterId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getParameterExplain() {
        return parameterExplain;
    }

    public void setParameterExplain(String parameterExplain) {
        this.parameterExplain = parameterExplain;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }


    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public PortParameter() {
    }

    public PortParameter(String parameterKey, String parameterId, String parameterName, String parameterType, String parameterExplain, String required, String example, String portId) {

        this.parameterKey = parameterKey;
        this.parameterId = parameterId;
        this.parameterName = parameterName;
        this.parameterType = parameterType;
        this.parameterExplain = parameterExplain;
        this.required = required;
        this.example = example;
        this.portId = portId;
    }

    @Override
    public String toString() {
        return "PortParameter{" +
                "parameterKey='" + parameterKey + '\'' +
                ", parameterId='" + parameterId + '\'' +
                ", parameterName='" + parameterName + '\'' +
                ", parameterType='" + parameterType + '\'' +
                ", parameterExplain='" + parameterExplain + '\'' +
                ", required='" + required + '\'' +
                ", example='" + example + '\'' +
                ", portId='" + portId + '\'' +
                '}';
    }
}
