package hy.ea.bo.finance.vo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;

public class SpecsConfigListVO implements BaseBean, Serializable {
    private String id;
    /**
     * 单位
     */
    private String specs;

    /**
     * 规格
     */
    private String specsType;

    /**
     * 分组
     */
    private String specsCode;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getSpecsType() {
        return specsType;
    }

    public void setSpecsType(String specsType) {
        this.specsType = specsType;
    }

    public String getSpecsCode() {
        return specsCode;
    }

    public void setSpecsCode(String specsCode) {
        this.specsCode = specsCode;
    }

    public SpecsConfigListVO(String id, String specs, String specsType, String specsCode) {
        this.id = id;
        this.specs = specs;
        this.specsType = specsType;
        this.specsCode = specsCode;
    }

    public SpecsConfigListVO() {
    }

}
