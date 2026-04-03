package hy.ea.collage.service;


import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

public interface PhlBusiSerivce {
  /**
   * 获取省份
   * @return
   */
  public List<BaseBean> getCDistricts();
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
  public PageForm getPageFormBusi(int pageNumber, int pageSize, String industryID,String companymid, String placeCrit, String cateCrit, String disCrit, String saleCrit,String companyName,String staffID);

  
  /**
   * 
   * 查询公司产品
   * @param list
   * @return
   */
  public Map<String,List<BaseBean>> getShowProduct(List<BaseBean> list);
}