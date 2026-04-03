package hy.ea.collage.service;

import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffCertificate;
import hy.ea.bo.office.CarInformation;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.io.File;
import java.util.List;

/**
 * Created by lyc on 2019-01-21.
 */
public interface PhlJoinService {

    //保存有车加入信息
    void saveCarjoin(CarInformation carInformation, String driverId, String drivingId, TEshopCusCom tc,
                     Staff newStaff,File file,String fileFileName,String marketId);
    //获取车辆列表
    PageForm getPageFormCar(int pageNumber, int pageSize,String industryId,String ccompanyID);
    
    //首页最新车辆
    List<BaseBean> getListFromCar(String industryId,String ccompanyID);
    //获取市场列表
    List<BaseBean> getMarketList(String industryID,String staffId);
    //获取车辆类型/车辆使用性质
    List<BaseBean> getCarTypeOrUse(String companyId,String codeId);
    //获取证件
    StaffCertificate getCertificate(String staffId, String certificateName);
    //验证行驶证、车牌号、发动机号是否已注册
    String checkUnique(String drivingId,String carNum,String engineNum,String industryId);
}
