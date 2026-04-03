package hy.ea.face.service;

import hy.ea.bo.office.face.OfficeFaceEmpower;
import hy.ea.bo.office.face.OfficeFaceResult;
import hy.ea.bo.office.face.TbFaceDevice;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

public interface FaceService {
    public Integer updateDeviceById(TbFaceDevice tbFaceDevice);

    public PageForm findFaceDevice(String companyName);

    PageForm findPersonEmpower(Integer empoderState,String personName,List<String> list);

    void savePersonEmpower(OfficeFaceEmpower officeFaceEmpower);

    List<Object> findContactCompanyDevice(List<String> listInfo);

    OfficeFaceEmpower findPersonEmpowerByStaffId(String staffId);

    List<TbFaceDevice> findCompanyDeviceByCompanyKey(String erId);

    void updateFaceEmpwer(OfficeFaceEmpower officeFaceEmpower);

    void saveFaceDevice(TbFaceDevice tbFaceDevice);

    List<BaseBean> findCompanyList(String companyName);

    List<BaseBean> findDeviceInfo(String deviceSN);

    void saveFaceResult(OfficeFaceResult faceResult);

    PageForm findFaceResult(String presonName,String pageNumber,String pageSize,String timeNumber);

    List<BaseBean> findFaceResultById(String resultId);

    List<BaseBean> findEmpowerById(String staffKey);

    Map<String,Integer> findFaceResultPersonNumber(String personName, String timeNumber);

    List<BaseBean> findERInfoList(String erId);

    List<String> findCompanyIdByAccount(String sccId);

    String findStaffInfoById(String staffid);

    String findCompanyIdByERId(String erId);

    void deletedEmpowerById(String staffKey);

    List<BaseBean> findDeviceBySiteid(String erId);

    PageForm findPersonEmpowerInfo(Integer emoderState, String personName, List<String> listInfo, String pageNumber, String pageSize, String timeNumber);
}
