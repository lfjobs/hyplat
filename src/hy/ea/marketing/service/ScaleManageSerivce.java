package hy.ea.marketing.service;


import com.tiantai.wfj.bo.PresetKey;
import com.tiantai.wfj.bo.PresetPage;
import com.tiantai.wfj.bo.Scale;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;


public interface ScaleManageSerivce
{
    //添加称
    public  void  addOrUpdate(Scale scale, String companyID, String staffID);

    //删除称
    public  void  delete(String scalekey);


    public Map<String,Object> getGoodsList(String companyID);

    //添加预置键
    public  void  addPreOrUpdate(Map<String, PresetKey> presetKeyMap, PresetPage presetPage,String companyID, String staffID);


    //删除预置键
    public  void  deletePreset(String scpId);

    //根据父查子
    public List<BaseBean> getPreKeyList(String scpId);

    /**
     * 查询热销称重商品
     * @param companyID
     * @return
     */
    public List<BaseBean> findHotSaleGoods(String companyID);
    /**
     * 查询称重商品
     * @param companyID
     * @return
     */
    public List<BaseBean> findGoods(String companyID,String parameter);

    /***
     * 查询有产品的称重的产品分类
     * @return
     */
    public List<BaseBean> findScaleGoodsCate(String companyID);

    /**
     * 根据一级分类获取二级分类
     * @param companyID
     * @param codePID
     * @return
     */
    public List<BaseBean> findSecondCate(String companyID,String codePID);

    /**
     *
     * 根据分类查询商品
     * @param companyID
     * @param codeID
     * @param lxType 查询类型，0：其他入口进入1：批发商城进入
     * @return
     */
    public PageForm findProductByCate(String companyID, String codeID,String parameter,int pageNumber,int pageSize,int lxType);


    /**
     *
     * 根据plu查询
     * @param companyID
     * @return
     */
    public Object findProductPLU(String companyID, String plu);

    /**
     *
     * 移动端根据分类查询商品
     * @param companyID 公司id
     * @param codeID  类目id
     * @param ppname  产品名称
     * @param searchtype 查询类型  0或null:一上架产品  1：全部产品
     */
    PageForm findProductByCatePhone(String companyID, String codeID,String ppname,String searchtype,String parameter,int pageNumber,int pageSize);
}
