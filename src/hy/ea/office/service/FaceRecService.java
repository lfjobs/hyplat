package hy.ea.office.service;

import hy.ea.bo.human.Staff;
import hy.ea.bo.office.FaceRec;
import hy.ea.office.action.response.PersonResponse;
import hy.plat.bo.PageForm;

import java.util.List;

public interface FaceRecService {
  /**
   *
   * 添加设备
   */
  void addEquip(FaceRec faceRec, String staffID, String companyID);

  /**
   *
   * 删除设备
   */
  void deleteEquip(String frkey);

  /**
   *
   * 分页查询人脸闸机设备
   * @param faceRec
   * @param pageSize
   * @param pageNumber
   * @param companyID
   * @return
   */
  PageForm getListFaceRec(FaceRec faceRec, int pageSize, int pageNumber, String companyID);

  /**
   *
   * 验证人脸编号是否重复
   * @param sn
   * @param frId
   * @return
   */
  String checkRecSn(String sn, String frId);

  /**
   * 根据人脸设备传递的用户id查询对应用户
   * @param staffId
   */
  Staff findStaffById(String staffId);

  /**
   * 查询所有人员
   * @return
   */
  List<PersonResponse> findAll();

}
