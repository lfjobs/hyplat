package mobile.tiantai.android.service;

import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.invoicing.InvCheckGoods;
import hy.ea.bo.invoicing.Inventory;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.io.File;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by lyc on 2018-10-08. ********  未使用  ********
 */
public interface StockService {
    /**
     * 货品名、条码 查询货品库存列表/货品库存详情
     * @param companyId
     * @param goodsName 货品名
     * @param goodsType 货品类型
     * @param inventoryId 库存表标识列
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getGoodsList(String companyId,String goodsName,String goodsType,String inventoryId,int pageNumber,int pageSize);

    /**
     *  获取公司物品类型列表
     * @param companyId
     * @return
     */
    List<Object> getGoodsType(String companyId);

    /**
     *
     * @param companyId
     * @param depotType 库房类型
     * @param depotPId  父库房id
     * @return
     */
    List<BaseBean> getDepots(String companyId,String depotType,String depotPId);

    /**
     * 新增货品
     * @param file
     * @param fileFileName
     * @param inv
     */
    void saveGoods(File file, String fileFileName, Inventory inv) throws ParseException;

    void addStock(String inventoryId,String ppID,String newPrice,String newQuantity) throws ParseException ;

    /**
     * 盘点单
     * @param ci
     * @param inventoryId
     */
    void checkInv(InvCheckGoods ci, String inventoryId);

    /**
     * 新增报损/报修
     * @param breakType
     * @param breakReason
     * @param inventoryId
     * @param cashier
     */
    void saveBreak(String breakType,String breakReason,String inventoryId,CashierBills cashier) throws ParseException;

    /**
     * 查看报修/报损单
     * @param companyId
     * @param cashierId
     * @return
     */
    Object getBreak(String companyId,String cashierId);

    /**
     * 报修/报损审核
     * @param companyId
     * @param cashierId
     * @param status
     * @param auditorId
     * @param comments
     */
    void updateBreak(String companyId,String cashierId,String status,String auditorId,String comments);

    void saveCar2(String companyId,String staffId,String subType,Map<String,String> goodsMap,String goodsIds,String arrgoodsNum) throws ParseException;

    void saveCar(String companyId,String staffId,Map<String,String> goodsMap,String goodsIds) throws ParseException;



}
