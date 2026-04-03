package hy.ea.finance.service;

import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.ProductPackaging;
import hy.plat.bo.PageForm;

import java.util.EnumMap;

/**
 * Created by LG on 2018/11/12.
 */
public interface ReportService {

    enum pageFormAadSum {
        pageForm,sum,count
    }

    /**
     * 根据条件查询销售毛利润、销售成品、销售收入报表
     * @param pageNumber 当前页码
     * @param cashierBills 订单对象
     * @param product 产品对象
     * @param timeInterval 时间区间
     * @param companyID 当前公司
     * @param type 判断查询全部还是分页查询
     * @param reportType 报表类型
     * @return
     */
    EnumMap<pageFormAadSum,Object> getGorssListBySch(int pageNumber, ProductPackaging product, CashierBills cashierBills,String timeInterval,String companyID,String type ,String reportType);


    /**
     * 根据条件查询订单报表
     * @param pageNumber 当前页码
     * @param cashierBills 订单对象
     * @param accountOrName 账号或名字
     * @param timeInterval 时间区间
     * @param companyID 当前公司
     * @param type 判断查询全部还是分页查询
     * @return
     */
    EnumMap<pageFormAadSum,Object> getOrderListBySch(int pageNumber, String accountOrName, CashierBills cashierBills,String timeInterval,String companyID,String type);
}
