package mobile.tiantai.android.service.storeProduction.budgetSheet;

import hy.ea.bo.finance.CashierBillsExt;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;
import java.util.Map;

/**
 * ReviewCirculateService
 *
 * @author zch
 */
public interface ReviewCirculateService {
    Map<String, String> getNum(String companyID);

    DetachedCriteria getBudgetBillDc(String staffID, String companyID, String status, String cashierBillsId) throws Exception;

    String updateStatus(List<String> ids, String status);

    List<CashierBillsExt> getCashierBillsExt(String cashierBillsId);

    String updateOpinion(String cashierBillsId, String search, String reviewOpinion);

    String examineAndVerify(List<String> ids);

    boolean examineAndVerify1(List<String> ids);

    String updateStatus1(List<String> ids, String number);

    DetachedCriteria getBudgetBillDc1(String staffID, String companyID, String status) throws Exception;

    String examineAndVerify2(List<String> ids);
}
