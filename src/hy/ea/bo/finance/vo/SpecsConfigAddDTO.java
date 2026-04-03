package hy.ea.bo.finance.vo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;

public class SpecsConfigAddDTO implements BaseBean, Serializable {

    /**
     * 单位
     */
    private String id;
    /**
     * 规格类型
     */
    private String specsType;

    /**
     * 规格A
     */
    private String specsA;
    /**
     * 规格B
     */
    private String specsB;
    /**
     * 规格C
     */
    private String specsC;
    /**
     * 规格D
     */
    private String specsD;
    private String state;

    /**
     * 分组
     */
    private String specsCode;

    public SpecsConfigAddDTO() {
    }

    public SpecsConfigAddDTO(String id, String specsType, String specsA, String specsB, String specsC, String specsD, String state, String specsCode) {
        this.id = id;
        this.specsType = specsType;
        this.specsA = specsA;
        this.specsB = specsB;
        this.specsC = specsC;
        this.specsD = specsD;
        this.state = state;
        this.specsCode = specsCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecsType() {
        return specsType;
    }

    public void setSpecsType(String specsType) {
        this.specsType = specsType;
    }

    public String getSpecsA() {
        return specsA;
    }

    public void setSpecsA(String specsA) {
        this.specsA = specsA;
    }

    public String getSpecsB() {
        return specsB;
    }

    public void setSpecsB(String specsB) {
        this.specsB = specsB;
    }

    public String getSpecsC() {
        return specsC;
    }

    public void setSpecsC(String specsC) {
        this.specsC = specsC;
    }

    public String getSpecsD() {
        return specsD;
    }

    public void setSpecsD(String specsD) {
        this.specsD = specsD;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSpecsCode() {
        return specsCode;
    }

    public void setSpecsCode(String specsCode) {
        this.specsCode = specsCode;
    }
}
