package hy.plat.bo;

/**
 * Created by Administrator on 2017/12/20 0020.
 */
public class Area {

    private String rid;
    private String areaID;
    private String area;
    private String father;

    public Area() {}

    public Area( String rid, String areaID, String area, String father)
    {
        this.rid = rid;
        this.areaID = areaID;
        this.area = area;
        this.father = father;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getAreaID() {
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }
}
