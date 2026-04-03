package hy.ea.finance.action.BenDis.reports;

import com.alipay.util.httpClient.HttpRequest;
import com.opensymphony.xwork2.ActionContext;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.vo.SalaryIntegral;
import hy.ea.finance.service.ReportService;
import hy.ea.human.service.LogBookService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

/**
 * Created by LG on 2018/11/12.
 */

@Controller
@Scope("prototype")
public class reportsAction {
    @Resource
    private ReportService reportService;

    @Resource
    private LogBookService showlogBookService;

    @Resource
    private ShowExcelService excelService;
    @Resource
    private CLogBookService logBookService;
    @Resource
    private BaseBeanService baseBeanService;

    private ProductPackaging product;
    private CashierBills cashierBills;

    private int pageNumber;
    private String result;
    private String timeInterval;
    public InputStream excelStream;
    private String reportType;
    private String type;
    private String accountOrName;


    public String getGorssProState(){
       return  "grossprostate";
    }

    public String getReFingoodsSold(){
        return  "reFingoodsSold";
    }

    public String getSalesOrder(){
        return  "salesOrder";
    }

    public String getSalesRevenue(){
        return  "salesRevenue";
    }

    public String getGorssListBySch(){
        CAccount account = (CAccount) ActionContext.getContext().getSession()
                .get("account");
        //EnumMap用来函数返回多个返回值
        EnumMap<ReportService.pageFormAadSum,Object> pageFormAndSum = reportService.getGorssListBySch(pageNumber,product,cashierBills,timeInterval,account==null?"":account.getCompanyID(),type,reportType);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm",pageFormAndSum.get(ReportService.pageFormAadSum.pageForm));
        map.put("sum",pageFormAndSum.get(ReportService.pageFormAadSum.sum));
        map.put("count",pageFormAndSum.get(ReportService.pageFormAadSum.count));
        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();
        return "success";
    }

    public String getOrderListBySch(){
        CAccount account = (CAccount) ActionContext.getContext().getSession()
                .get("account");
        //EnumMap用来函数返回多个返回值
        EnumMap<ReportService.pageFormAadSum,Object> pageFormAndSum = reportService.getOrderListBySch(pageNumber,accountOrName,cashierBills,timeInterval,account==null?"":account.getCompanyID(),type);
        Map<String, Object> map = new HashMap<String, Object>();
        List<BaseBean> repList = new ArrayList<BaseBean>();
        PageForm pageForm = new PageForm();
        pageForm = (PageForm)pageFormAndSum.get(ReportService.pageFormAadSum.pageForm);
        if(pageForm!=null) {
            repList = pageForm.getList();
            for (Object rep : repList) {
                Object[] repArray = (Object[]) rep;
                if (repArray[6] != null && !"".equals(repArray[6]) && "java.sql.Date".equals(repArray[6].getClass().getName())) {
                    repArray[6] = repArray[6].toString();
                }
                rep = repArray;
            }
            pageForm.setList(repList);
        }
        map.put("pageForm",pageFormAndSum.get(ReportService.pageFormAadSum.pageForm));
        map.put("sum",pageFormAndSum.get(ReportService.pageFormAadSum.sum));
        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();
        return "success";
    }

    public String showExcel() {
        CAccount account = (CAccount) ActionContext.getContext().getSession()
                .get("account");
        Map<String, Object> session = ActionContext.getContext().getSession();
        String organizationID = (String) session.get("organizationID");
        EnumMap<ReportService.pageFormAadSum,Object> pageFormAndSum = null;
        String[] titles = null;
        Object count = null;
        String[] grossTitles ={"序号","名称","条码","单位","销售单价","成本单价","销售数量","销售金额","成本金额","毛利润","订单数","消费人数","供应商"};
        String[] refinTitles ={"序号","名称","条码","单位","成本单价","数量","金额","订单数","消费人数","供应商"};
        String[] salesTitles ={"序号","名称","条码","单位","销售单价","数量","销售收入","价格单价","订单数","消费人数","供应商"};
        String[] orderTitles ={"序号","订单号","用户账号","用户姓名","操作人","销售金额","支付方式","支付时间"};
        if("gross".equals(reportType)){
            //毛利润
            titles = grossTitles;
            pageFormAndSum  =reportService.getGorssListBySch(pageNumber,product,cashierBills,timeInterval,account==null?"":account.getCompanyID(),type,reportType);
        }else if("refin".equals(reportType)){
            //销售成品
            titles = refinTitles;
            pageFormAndSum  =reportService.getGorssListBySch(pageNumber,product,cashierBills,timeInterval,account==null?"":account.getCompanyID(),type,reportType);
        }else if("sales".equals(reportType)){
            //销售收入
            titles = salesTitles;
            pageFormAndSum  =reportService.getGorssListBySch(pageNumber,product,cashierBills,timeInterval,account==null?"":account.getCompanyID(),type,reportType);
        }else if("order".equals(reportType)){
            //销售订单
            titles = orderTitles;
            pageFormAndSum = reportService.getOrderListBySch(pageNumber,accountOrName,cashierBills,timeInterval,account==null?"":account.getCompanyID(),type);
        }
        List<BaseBean> list = (List<BaseBean>)pageFormAndSum.get(ReportService.pageFormAadSum.pageForm);
        Object sum = pageFormAndSum.get(ReportService.pageFormAadSum.sum);
        if(!"oeder".equals(reportType)){
            count = pageFormAndSum.get(ReportService.pageFormAadSum.count);
        }
        excelStream = excelService.showExcelByReport(titles,list,sum,count,reportType);
        CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出报表", account);
        baseBeanService.update(logBook);
        return "showexcel";
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public ProductPackaging getProduct() {
        return product;
    }

    public void setProduct(ProductPackaging product) {
        this.product = product;
    }

    public CashierBills getCashierBills() {
        return cashierBills;
    }

    public void setCashierBills(CashierBills cashierBills) {
        this.cashierBills = cashierBills;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getAccountOrName() {
        return accountOrName;
    }

    public void setAccountOrName(String accountOrName) {
        this.accountOrName = accountOrName;
    }
}
