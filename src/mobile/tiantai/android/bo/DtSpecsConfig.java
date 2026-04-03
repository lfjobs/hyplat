package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

public class DtSpecsConfig implements BaseBean {

    private String id;

    private String specs;

    private String specsLevel;

    private String specsCode;

    private Integer specsOrder;

    private String parentId;
    //0 未删除   1  已删除
    private String state;

    public DtSpecsConfig(String id, String specs, String specsLevel, String specsCode, Integer specsOrder, String parentId, String state) {
        this.id = id;
        this.specs = specs;
        this.specsLevel = specsLevel;
        this.specsCode = specsCode;
        this.specsOrder = specsOrder;
        this.parentId = parentId;
        this.state = state;
    }

    public DtSpecsConfig() {
    }

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

    public String getSpecsLevel() {
        return specsLevel;
    }

    public void setSpecsLevel(String specsLevel) {
        this.specsLevel = specsLevel;
    }

    public String getSpecsCode() {
        return specsCode;
    }

    public void setSpecsCode(String specsCode) {
        this.specsCode = specsCode;
    }

    public Integer getSpecsOrder() {
        return specsOrder;
    }

    public void setSpecsOrder(Integer specsOrder) {
        this.specsOrder = specsOrder;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
