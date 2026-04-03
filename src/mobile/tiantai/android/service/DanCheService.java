package mobile.tiantai.android.service;

/**
 * Created by Administrator on 2017/5/22 0022.
 */
public interface DanCheService {

      public Boolean jiShi(String deviceid);

      public Boolean zhiFuQiXing(String staffid, String deviceid);

      public Boolean closeSuo(String deviceid, String staffid);
}
