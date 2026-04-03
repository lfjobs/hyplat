package hy.ea.collage.service;


import com.tiantai.wfj.bo.TEshopCusCom;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;


public interface SbqIndexSerivce
{
    /**
     * 根据平台获取平台的动态
     * @param pageNumber
     * @param pageSize
     * @param ccomIDPlatform
     * @return
     */
  public Map<String, Object> getPageFormInfo(int pageNumber,int pageSize,String ccomIDPlatform,String staffID);

    /**
     *
     * 给文章点赞
     * @param ppid
     * @param staffId
     * @return
     */
  public String dzopr(String ppid,String goodsId,String staffId);

    /**
     *
     * 获取附近的人
     * @param pageNumber
     * @param pageSize

     * @return
     */
    public PageForm getFjPeo(int pageNumber,int pageSize,String staffid);


  /**
   *
   * 获取行业平台
   * @return
   */
    public PageForm getPlatForm(int pageNumber,int pageSize,String staffid);

  /**
   * 关注平台
   * @return
   */
  public String followPlatForm(TEshopCusCom tc, String ccomIDPlatform);
}
