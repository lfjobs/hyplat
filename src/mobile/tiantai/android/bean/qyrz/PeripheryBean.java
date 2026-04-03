package mobile.tiantai.android.bean.qyrz;

/**
 * 定位周边商家bean
 * Created by Administrator on 2019-11-04.
 */
public class PeripheryBean {
    //定位周边商家信息
    private String sjId;//周边商家id
    private String sjName;//周边商家名称
    private String sjPhotosAll;//周边商家所有图片

    /**
     * GET AND SET METHOD
     */
    public String getSjId() {
        return sjId;
    }

    public void setSjId(String sjId) {
        this.sjId = sjId;
    }

    public String getSjName() {
        return sjName;
    }

    public void setSjName(String sjName) {
        this.sjName = sjName;
    }

    public String getSjPhotosAll() {
        return sjPhotosAll;
    }

    public void setSjPhotosAll(String sjPhotosAll) {
        this.sjPhotosAll = sjPhotosAll;
    }

    /**
     * 无参构造函数
     */
    public PeripheryBean() {
    }

    /**
     * 带参构造函数
     *
     * @param sjId        周边商家id
     * @param sjName      周边商家名称
     * @param sjPhotosAll 周边商家所有图片
     */
    public PeripheryBean(String sjId, String sjName, String sjPhotosAll) {
        this.sjId = sjId;
        this.sjName = sjName;
        this.sjPhotosAll = sjPhotosAll;
    }
}
