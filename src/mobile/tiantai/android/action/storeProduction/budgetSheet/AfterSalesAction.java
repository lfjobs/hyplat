package mobile.tiantai.android.action.storeProduction.budgetSheet;

import hy.plat.bo.PageForm;
import mobile.tiantai.android.bo.AfterSales;
import mobile.tiantai.android.service.storeProduction.budgetSheet.AfterSalesService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServlet;

/**
 * AfterSalesAction
 *
 * @author zch
 */
@Controller
@Scope("prototype")
public class AfterSalesAction extends HttpServlet {
    @Autowired
    private AfterSalesService afterSalesService;
    private PageForm pageForm;
    private int pageNumber;
    private int pageSize;
    private String result;
    private AfterSales afterSales;
    private String goodsbillsId;
    private String startTime;
    private String endTime;


    /**
     * 获取订单
     *
     * @return
     */
    public String getCashierBillsList() {
        pageForm = afterSalesService.getCashierBillsList(startTime, endTime, pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String getCashierBillsList1() {
        pageForm = afterSalesService.getCashierBillsList1(startTime, endTime, pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    /**
     * 修改页面
     *
     * @return
     */
    public String updAfterSales() {
        afterSales = afterSalesService.updAfterSales(goodsbillsId);
        return "updAfterSales";
    }

    public String updAfterSalesData() {
        AfterSales salesOld = this.afterSales;
        result = afterSalesService.updCustomerData(salesOld);
        return "success";
    }

    public String getCustomerFeedbackList() {
        pageForm = afterSalesService.getCustomerFeedbackList(pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }


    //get  set

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public AfterSales getAfterSales() {
        return afterSales;
    }

    public void setAfterSales(AfterSales afterSales) {
        this.afterSales = afterSales;
    }

    public String getGoodsbillsId() {
        return goodsbillsId;
    }

    public void setGoodsbillsId(String goodsbillsId) {
        this.goodsbillsId = goodsbillsId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}

