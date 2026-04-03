package hy.ea.collage.service;


import hy.ea.bo.human.Staff;
import hy.ea.bo.office.CarInformation;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

public interface PhlIndexSerivce {

  /**
   * 获取轮播图
   *
   * @return
   */
  public List<BaseBean> getRotationPicList(String ccompanyID, String pos);

  /**
   * 获取拼货拉农贸市场分类
   *
   * @param ccompanyID
   * @return
   */
  public List<BaseBean> getPhlProCate(String ccompanyID);


  /**
   *
   * 查询农贸市场分类
   * @param codeID
   * @return
   */
  public List<BaseBean> getProCate(String ccompanyID,String codeID);



  /**
   *
   * 最近浏览
   * @param staffID
   * @return
   */
  public List<BaseBean> getRecentViewList(String staffID,String ccompanyID);
  
  
  /**
  *
  * 最近浏览添加
  * @param staffID
  * @return
  */
  public void addRecentView(String staffID,String ccompanyID,String codeID,String codeValue);



  /**
   * 获取展馆分类
   *
   * @return
   */
  public Map<String, Object> getExhiProduct(String companyID);
  
  

  /**
   * 获取展馆分类
   *
   * @return
   */
  public  Map<String,String>  getAdvert(String companyID);


  /**
   * 最新资讯
   *
   * @param ccompanyID
   * @return
   */
  public List<BaseBean> getRecentNews(String ccompanyID);


  /**
   *
   * 首页有货拉物流
   */
  public List<BaseBean> getIndexLogisList(String companyID);



  /**
   * 获取首页商家
   * @return
   */
  public List<BaseBean> getIndexBusiComList(String ccomIDPlatform);

  /**
   *
   * 获取首页市场
   * @param industryID
   * @return
   */
  public List<BaseBean> getIndexMarketList(String industryID,String staffID);


  /**
   *
   * 获取素有农贸市场
   * @param industryID
   * @return
   */
  public PageForm  getPageFormMarket(int pageNumber,int pageSize,String industryID,String staffID);


  /**
   *
   * 获取首页采购
   * @param ccompanyID
   * @return
   */
  public List<BaseBean> getIndexPurchaseList(String ccompanyID);



  /**
   *
   * 获取首页分类
   * @return
   */
  public Map<String,Object> getIndexProOrCate(String ccompanyID);




  /**
   *
   * 首页分类商品
   * @param pageNumber
   * @param pageSize
   * @param cate
   * @return
   */
  public PageForm getIndexProduct(int pageNumber,int pageSize,String cate,String industryID,String companyID);


  /**
   * 有车加入
   * @param carInformation
   * @param staff
   * @return
   */
  public String addCarJoin(CarInformation carInformation,Staff staff);

  /**
   * 查询往来单位信息
   * @param ccompanyID
   * @return
   */
  public Object getContactCom(String ccompanyID);
  

		
}