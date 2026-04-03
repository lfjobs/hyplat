package hy.ea.finance.service.turnovermanage;

import hy.ea.finance.action.response.OrderDetailResponse;
import hy.plat.bo.PageForm;


/**
 * 成交订单
 */
public interface MakeOrderService {

    /**
     * 成交订单 列表展示
     * @param pageNum
     * @param pageSize
     * @param fkStatus 收款状态
     * @param companyid 公司id
     * @return
     */
    PageForm getList(String pageNum, String pageSize, String fkStatus,String companyid);

    /**
     * 成交订单 订单详情
     * @param cashierBillsID  订单业务键
     * @return
     */
    OrderDetailResponse getOrderDetail(String cashierBillsID);
}
