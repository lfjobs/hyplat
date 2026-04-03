package mobile.tiantai.android.bean.wholesaleMall;

/**
 * 产品颜色规格bean
 * Created by Administrator on 2019-11-04.
 */
public class AttriProductionBean {

    private String apid;//产品规格id
    private String attriname;//属性名 e.g.颜色 ，
    private String attrivalue;//属性值 e.g.白色
    private String imgurl;//图片链接
    private String goodsid;//物品ID
    private String type;//0尺码,1颜色,2副图 3：视频
    private int sort;//排序 1是产品主图，其他事副图

    /**
     * GET AND SET METHOD
     */
    public String getApid() {
        return apid;
    }

    public void setApid(String apid) {
        this.apid = apid;
    }

    public String getAttriname() {
        return attriname;
    }

    public void setAttriname(String attriname) {
        this.attriname = attriname;
    }

    public String getAttrivalue() {
        return attrivalue;
    }

    public void setAttrivalue(String attrivalue) {
        this.attrivalue = attrivalue;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    /**
     * 无参构造方法
     */
    public AttriProductionBean() {
    }

    /**
     * 带参构造方法
     *
     * @param apid       产品规格id
     * @param attriname  属性名 e.g.颜色
     * @param attrivalue 属性值 e.g.白色
     * @param imgurl     图片链接
     * @param goodsid    物品ID
     * @param type       0尺码,1颜色,2副图 3：视频
     * @param sort       排序 1是产品主图，其他事副图
     */
    public AttriProductionBean(String apid, String attriname, String attrivalue, String imgurl, String goodsid, String type, int sort) {
        this.apid = apid;
        this.attriname = attriname;
        this.attrivalue = attrivalue;
        this.imgurl = imgurl;
        this.goodsid = goodsid;
        this.type = type;
        this.sort = sort;
    }
}
