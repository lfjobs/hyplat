package hy.ea.bo.invoicing;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

public class productDepot implements BaseBean, ExcelBean,java.io.Serializable{
    private String pdkey;                    //主键
    private String pdid;                     //业务主键
    private String ppid;                     //产品id
    private String depotid;                  //库房外键
    private String depotcode;                //库房编号
    private String depotname;                //库房名称
    private String proAddRem;                //是否上架
    private String proComid;                 //产品公司id
    private String deoptComid;               //库房公司id


    public String getPdkey() {
        return pdkey;
    }

    public void setPdkey(String pdkey) {
        this.pdkey = pdkey;
    }

    public String getPdid() {
        return pdid;
    }

    public void setPdid(String pdid) {
        this.pdid = pdid;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getDepotid() {
        return depotid;
    }

    public void setDepotid(String depotid) {
        this.depotid = depotid;
    }

    public String getDepotcode() {
        return depotcode;
    }

    public void setDepotcode(String depotcode) {
        this.depotcode = depotcode;
    }

    public String getDepotname() {
        return depotname;
    }

    public void setDepotname(String depotname) {
        this.depotname = depotname;
    }

    public String getProAddRem() {
        return proAddRem;
    }

    public void setProAddRem(String proAddRem) {
        this.proAddRem = proAddRem;
    }

    public String getProComid() {
        return proComid;
    }

    public void setProComid(String proComid) {
        this.proComid = proComid;
    }

    public String getDeoptComid() {
        return deoptComid;
    }

    public void setDeoptComid(String deoptComid) {
        this.deoptComid = deoptComid;
    }

    @Override
    public String toString() {
        return "\"productDepot\":{" +
                "\"pdkey\":\"" +pdkey+ '\"' +
                ", \"pdid\":\"" +pdid+ '\"' +
                ", \"ppid\":\"" +ppid+ '\"' +
                ", \"depotid\":\"" +depotid+ '\"' +
                ", \"depotcode\":\"" +depotcode+ '\"' +
                ", \"depotname\":\"" +depotname+ '\"' +
                ", \"proAddRem\":\"" +proAddRem+ '\"' +
                ", \"proComid\":\"" +proComid+ '\"' +
                ", \"deoptComid\":\"" +deoptComid+ '\"' +
                "},";
    }

    public productDepot() {
        super();
    }

    public productDepot(String pdkey, String pdid, String ppid, String depotid, String depotcode, String depotname, String proAddRem, String proComid, String deoptComid) {
        this.pdkey = pdkey;
        this.pdid = pdid;
        this.ppid = ppid;
        this.depotid = depotid;
        this.depotcode = depotcode;
        this.depotname = depotname;
        this.proAddRem = proAddRem;
        this.proComid = proComid;
        this.deoptComid = deoptComid;
    }

    @Override
    public String[] properties() {
        return new String[0];
    }
}
