package hy.ea.office.service;

import hy.ea.bo.office.FaceRec;
import hy.plat.bo.PageForm;

public interface FaceRecService {
  /**
   *
   * 添加设备
   */
  public void addEquip(FaceRec faceRec,String staffID,String companyID);

  /**
   *
   * 删除设备
   */
  public void deleteEquip(String frkey);

  /**
   *
   * 分页查询人脸闸机设备
   * @param faceRec
   * @param pageSize
   * @param pageNumber
   * @param companyID
   * @return
   */
  public PageForm getListFaceRec(FaceRec faceRec, int pageSize, int pageNumber, String companyID);

  /**
   *
   * 验证人脸编号是否重复
   * @param sn
   * @param frId
   * @return
   */
  public String checkRecSn(String sn,String frId);
}
