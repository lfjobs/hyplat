package mobile.tiantai.android.service.storeProduction.budgetSheet;

import hy.plat.bo.PageForm;
import mobile.tiantai.android.bo.AfterSales;

/**
 * AfterSalesService
 *
 * @author zch
 */
public interface AfterSalesService {


    PageForm getCashierBillsList(String startTime, String endTime, Integer pageNumber, Integer pageSize);

    AfterSales updAfterSales(String goodsbillsId);

    String updCustomerData(AfterSales salesOld);

    PageForm getCustomerFeedbackList(Integer pageNumber, Integer pageSize);

    PageForm getCashierBillsList1(String startTime, String endTime, Integer pageNumber, Integer pageSize);
}
