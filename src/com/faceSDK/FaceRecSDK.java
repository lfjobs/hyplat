package com.faceSDK;

import com.ha.facecamera.configserver.ConfigServer;
import com.ha.facecamera.configserver.ConfigServerConfig;
import com.ha.facecamera.configserver.DataServer;
import com.ha.facecamera.configserver.DataServerConfig;
import com.ha.facecamera.configserver.pojo.Face;
import com.ha.facecamera.configserver.pojo.FacePage;
import com.ha.facecamera.configserver.pojo.ListFaceCriteria;

import static com.ha.facecamera.configserver.ConnectStateInvokeCondition.DeviceNoKnown;

public class FaceRecSDK {
    public static   DataServer ds = null;
    public static   ConfigServer cs = null;

    /**
     * 启动服务器监听
     */
    public static boolean startConfigServer() {

        ConfigServer cs = new ConfigServer();
        System.out.println(cs.getLastErrorCode());
        System.out.println(cs.getLastErrorMsg());
        ConfigServerConfig config = new ConfigServerConfig();

        return  cs.start(10001, config);



    }

    /**
     * 启动数据接收服务器
     */
    public static boolean startDataServer() {

        ds = new DataServer();
        DataServerConfig dsc = new DataServerConfig();
        dsc.connectStateInvokeCondition = DeviceNoKnown;
        return ds.start(10002, dsc);
    }

    /**
     *
     * 重启设备
     * @param deviceID
     * @return
     */
    public static boolean reboot(String deviceID){

         return  cs.reboot(deviceID,5000);

    }
    /**
     *
     * 判断设备是否在线
     * @param deviceID
     * @return
     */
    public static boolean getCameraOnlineState(String deviceID){
        return cs.getCameraOnlineState(deviceID);
    }

    /**
     *
     * 下发人脸
     * @param deviceID
     * @param face
     * @return
     */
    public static boolean addFace(String deviceID, Face face){

//        Face f = new Face();
//        f.setId("xiaohe");
//        f.setName("肖何");
//        f.setRole(1);
//        f.setJpgFilePath(new String[]{"C:\\Users\\LinXing\\Desktop\\肖何.jpg"});
        if(cs.addFace(deviceID,face, 5000)){
            System.out.println("添加人脸成功");
            return true;
        }else{
            System.out.println("添加人脸失败");
            return  false;
        }


    }

    /**
     *
     * 删除设备的人员
     * @param deviceID
     * @param faceId
     * @return
     */
    public static boolean deleteFace(String deviceID,String  faceId){

          return cs.deleteFace(deviceID,faceId,5000);
    }

    /**
     *
     * 获取设备的人脸
     * @param deviceID
     * @param criteria
     * @return
     */
    public FacePage listFace(String deviceID, ListFaceCriteria criteria) {
              return  cs.listFace(deviceID,criteria,5000);
    }
}

