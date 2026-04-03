package hy.ea.production.service;


import hy.ea.bo.CAccount;
import hy.ea.bo.company.GoodsBarcode;
import hy.ea.bo.company.GoodsManage;
import hy.plat.bo.BaseBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface GoodsManageSerivce
{


    /**
     * 添加更多规格条码
     * @param goodsBarcodeMap
     * @param goodsManage
     */
    public  void  addMoreBar(Map<String, GoodsBarcode> goodsBarcodeMap, GoodsManage goodsManage,List<BaseBean> beans,List<String> hqls);

    /**
     *
     * 根据物品ID获取其他规格条码
     * @param goodsID
     * @return
     */
    public List<BaseBean> getGoodsBarList(String goodsID);

    public void jxexcelSetup (CAccount account);

}
