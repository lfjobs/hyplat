package hy.ea.collage.service;


import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;

public interface PhlProductSerivce {
  /**
   *
   * @param pageNumber
   * @param pageSize
   * @param industryID   行业  农业
   * @param placeCrit   按地区查询
   * @param cateCrit    按货品类别查询
   * @param disCrit     按距离最近查询
   * @param saleCrit   按销量查询
   * @return
   */
  public PageForm getPageFormPro(int pageNumber, int pageSize, String industryID, String placeCrit, String cateCrit, String disCrit, String saleCrit, String priceCrit,String goodsName,String staffID);



  public List<BaseBean> hotCateSearch(String ccompanyID);
}