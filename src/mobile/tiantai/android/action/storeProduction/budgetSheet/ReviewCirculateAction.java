package mobile.tiantai.android.action.storeProduction.budgetSheet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.CashierBillsExt;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.GoodsBillsExt;
import hy.ea.bo.human.Staff;
import hy.ea.office.service.ContractService;
import hy.ea.office.service.DocCommonService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.bean.payBudget.CostBudgetAddBean;
import mobile.tiantai.android.bo.GoodsBillsExtOpinion;
import mobile.tiantai.android.service.storeProduction.budgetSheet.PayBudgetService;
import mobile.tiantai.android.service.storeProduction.budgetSheet.ReviewCirculateService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * ReviewCirculateAction  出入库传阅审核
 *
 * @author zch
 */
@Controller
@Scope("prototype")
public class ReviewCirculateAction extends HttpServlet {
    @Resource
    private ReviewCirculateService reviewCirculateService;
    @Resource
    private ContractService contractService;
    @Resource
    private ServerService serverService;
    private String result;
    private String companyID;
    private PageForm pageForm;

    private String billsType;
    private String status;
    private CashierBills cashierBills;//收支单管理
    private List<BaseBean> goodBeanslist;//货物集合
    private String cashierBillsId;//预算单id
    private String cashierBillsId1;//预算单id

    private GoodsBillsExtOpinion goodsBillsExtOpinion;

    @Resource
    private PayBudgetService payBudgetService;//预支付预算单service

    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private DocCommonService docCommonService;

    private String selectedItems;
    private GoodsBills goodsBills;
    private CostBudgetAddBean costAddBean;//招标投标单参数bean
    private String search;//模糊查询条件
    @Resource  //ajaxUpdateWareManagement
    private PayBudgetSheetAction payBudgetSheetAction;

    List<CashierBillsExt> cashierBillsExtList;

    public String getNum() {
        Map<String, String> map = new HashMap<>();
        map = reviewCirculateService.getNum(companyID);
        result = JSONObject.fromObject(map).toString();
        return "success";
    }

    public String ajaxCostBudgetBillList() {
        try {
            Map<String, Object> session = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session.get("account");
            //1.将列表信息拼接成DetachedCriteria类
            DetachedCriteria dc = reviewCirculateService.getBudgetBillDc(account.getStaffID(), account.getCompanyID(), status, cashierBillsId);
            //2.获取数据信息
            pageForm = baseBeanService.getPageFormByDC(
                    (null != pageForm ? pageForm.getPageNumber() : 1),
                    (pageForm.getPageSize() == 0 ? 20 : pageForm.getPageSize()), dc);
            //3.将结果放入map返回
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "success";
    }

    public String toCostBudgetDetail() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ? ", new Object[]{cashierBillsId});
            if (cashierBills != null) {
                String hql = " from GoodsBills gb,GoodsBillsExt ext where gb.goodsBillsID=ext.goodsBillsID and gb.cashierBillsID = ? order by ext.orderNum ";
                List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBillsId});

                if (CollectionUtil.isNotEmpty(list)) {
                    goodBeanslist = new ArrayList<>();
                    CostBudgetAddBean info = null;
                    GoodsBills bill = null;
                    GoodsBillsExt ext = null;
                    for (int i = 0; i < list.size(); i++) {
                        JSONArray arr = JSONArray.fromObject(list.get(i));
                        bill = (GoodsBills) JSONObject.toBean(JSONObject.fromObject(arr.get(0)), GoodsBills.class);
                        ext = (GoodsBillsExt) JSONObject.toBean(JSONObject.fromObject(arr.get(1)), GoodsBillsExt.class);
                        info = new CostBudgetAddBean();
                        payBudgetService.getBudgetItemInfo(bill, ext, info);
                        goodsBillsExtOpinion = (GoodsBillsExtOpinion) baseBeanService.getBeanByHqlAndParams(" FROM GoodsBillsExtOpinion WHERE goodsBillsID = ?", new Object[]{bill.getGoodsBillsID()});
                        info.setGoodsBillsExtOpinion(goodsBillsExtOpinion);
                        goodBeanslist.add(info);

                    }
                }
            }
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "costBudgetDetail";
    }

    public String toCostBudgetDetail1() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ? ", new Object[]{cashierBillsId});
            if (cashierBills != null) {
                String hql = " from GoodsBills gb,GoodsBillsExt ext where gb.goodsBillsID=ext.goodsBillsID and gb.cashierBillsID = ? order by ext.orderNum ";
                List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBillsId});

                if (CollectionUtil.isNotEmpty(list)) {
                    goodBeanslist = new ArrayList<>();
                    CostBudgetAddBean info = null;
                    GoodsBills bill = null;
                    GoodsBillsExt ext = null;
                    for (int i = 0; i < list.size(); i++) {
                        JSONArray arr = JSONArray.fromObject(list.get(i));
                        bill = (GoodsBills) JSONObject.toBean(JSONObject.fromObject(arr.get(0)), GoodsBills.class);
                        ext = (GoodsBillsExt) JSONObject.toBean(JSONObject.fromObject(arr.get(1)), GoodsBillsExt.class);
                        info = new CostBudgetAddBean();
                        payBudgetService.getBudgetItemInfo(bill, ext, info);
                        goodsBillsExtOpinion = (GoodsBillsExtOpinion) baseBeanService.getBeanByHqlAndParams(" FROM GoodsBillsExtOpinion WHERE goodsBillsID = ?", new Object[]{bill.getGoodsBillsID()});
                        info.setGoodsBillsExtOpinion(goodsBillsExtOpinion);
                        goodBeanslist.add(info);

                    }
                }
            }
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "costBudgetDetail1";
    }

    public String examineAndVerify() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String selectedData = request.getParameter("selectedItems");
        if (selectedData != null && !selectedData.isEmpty()) {
            JSONArray jsonArray = JSONArray.fromObject(selectedData);
            List<String> ids = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                ids.add(jsonArray.getString(i));
            }
            result = reviewCirculateService.examineAndVerify(ids);
        }
        return result;
    }

    public String examineAndVerify1() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String selectedData = request.getParameter("selectedItems");
        if (selectedData != null && !selectedData.isEmpty()) {
            JSONArray jsonArray = JSONArray.fromObject(selectedData);
            List<String> ids = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                ids.add(jsonArray.getString(i));
            }
            result = reviewCirculateService.examineAndVerify2(ids);
        }
        return result;
    }

    public String toCostBudgetItemDetail() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            String hql = " from GoodsBills gb where gb.goodsBillsID = ?";
            goodsBills = (GoodsBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{search});
            hql = " from GoodsBillsExt ext where ext.goodsBillsID = ?";
            GoodsBillsExt goodsBillsExt = (GoodsBillsExt) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{search});
            costAddBean = new CostBudgetAddBean();
            payBudgetService.getBudgetItemInfo(goodsBills, goodsBillsExt, costAddBean);
            goodsBillsExtOpinion = (GoodsBillsExtOpinion) baseBeanService.getBeanByHqlAndParams(" FROM GoodsBillsExtOpinion WHERE goodsBillsID = ?", new Object[]{search});
            if (goodsBillsExtOpinion == null) {
                goodsBillsExtOpinion = new GoodsBillsExtOpinion();
                CashierBillsExt cashierBillsExtOld = (CashierBillsExt) baseBeanService.getBeanByHqlAndParams(" FROM CashierBillsExt WHERE cashierBillsID = ?", new Object[]{cashierBillsId});
                if (StrUtil.isEmpty(cashierBillsExtOld.getReceiverCompanyID())) {
                    goodsBillsExtOpinion.setReviewNameSource("往来个人");
                } else {
                    // dtcompany   Company
                    Company bean = (Company) baseBeanService.getBeanByHqlAndParams(" FROM Company WHERE companyID = ?", new Object[]{cashierBillsExtOld.getReceiverCompanyID()});
                    goodsBillsExtOpinion.setReviewNameSource(bean.getCompanyName());
                }
                Map<String, Object> session = ActionContext.getContext().getSession();
                CAccount account = (CAccount) session.get("account");
                goodsBillsExtOpinion.setReviewerName(account.getStaffName());
                Staff beanByHqlAndParams = (Staff) baseBeanService.getBeanByHqlAndParams(" FROM Staff WHERE staffID = ?", new Object[]{account.getStaffID()});
                goodsBillsExtOpinion.setReviewerNameCode(beanByHqlAndParams.getStaffCode());
            }

        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "costBudgetItemDetail";
    }

    public String toCostBudgetItemDetail1() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            String hql = " from GoodsBills gb where gb.goodsBillsID = ?";
            goodsBills = (GoodsBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{search});
            hql = " from GoodsBillsExt ext where ext.goodsBillsID = ?";
            GoodsBillsExt goodsBillsExt = (GoodsBillsExt) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{search});
            costAddBean = new CostBudgetAddBean();
            payBudgetService.getBudgetItemInfo(goodsBills, goodsBillsExt, costAddBean);
            goodsBillsExtOpinion = (GoodsBillsExtOpinion) baseBeanService.getBeanByHqlAndParams(" FROM GoodsBillsExtOpinion WHERE goodsBillsID = ?", new Object[]{search});
            if (goodsBillsExtOpinion == null) {
                goodsBillsExtOpinion = new GoodsBillsExtOpinion();
                CashierBillsExt cashierBillsExtOld = (CashierBillsExt) baseBeanService.getBeanByHqlAndParams(" FROM CashierBillsExt WHERE cashierBillsID = ?", new Object[]{cashierBillsId});
                if (StrUtil.isEmpty(cashierBillsExtOld.getReceiverCompanyID())) {
                    goodsBillsExtOpinion.setReviewNameSource("往来个人");
                } else {
                    // dtcompany   Company
                    Company bean = (Company) baseBeanService.getBeanByHqlAndParams(" FROM Company WHERE companyID = ?", new Object[]{cashierBillsExtOld.getReceiverCompanyID()});
                    goodsBillsExtOpinion.setReviewNameSource(bean.getCompanyName());
                }
                Map<String, Object> session = ActionContext.getContext().getSession();
                CAccount account = (CAccount) session.get("account");
                goodsBillsExtOpinion.setReviewerName(account.getStaffName());
                Staff beanByHqlAndParams = (Staff) baseBeanService.getBeanByHqlAndParams(" FROM Staff WHERE staffID = ?", new Object[]{account.getStaffID()});
                goodsBillsExtOpinion.setReviewerNameCode(beanByHqlAndParams.getStaffCode());
            }

        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "costBudgetItemDetailReview";
    }

    //修改状态
    // 修改状态
    public String updateStatus() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String selectedData = request.getParameter("selectedItems");
            String status = request.getParameter("status");
            if (selectedData != null && !selectedData.isEmpty()) {
                // 解析JSON数组字符串
                JSONArray jsonArray = JSONArray.fromObject(selectedData);
                List<String> ids = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    ids.add(jsonArray.getString(i));
                }
                result = reviewCirculateService.updateStatus(ids, status);
            }
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "success";
    }

    public String updateOpinion() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String cashierBillsId = request.getParameter("cashierBillsId");
        String search = request.getParameter("search");
        String billsTypes = request.getParameter("billsTypes");

        result = reviewCirculateService.updateOpinion(cashierBillsId, search, goodsBillsExtOpinion.getReviewOpinion());
        List<String> ids = new ArrayList<>();
        ids.add(cashierBillsId);
        boolean result = reviewCirculateService.examineAndVerify1(ids);
        if (result) {
            if (goodsBillsExtOpinion.getReviewOpinion().equals("同意")) {
                reviewCirculateService.updateStatus(ids, "审核");
            } else {
                reviewCirculateService.updateStatus(ids, "驳回");
            }
        } else {
            reviewCirculateService.updateStatus(ids, "驳回");
        }
        return "success";
    }

    public String getCashierBillsExt() {
        cashierBillsExtList = reviewCirculateService.getCashierBillsExt(cashierBillsId1);
        // 使用 JSONArray 处理数组对象
        result = JSONArray.fromObject(cashierBillsExtList).toString();
        return "success";

    }

    public String circularizeBudgetBill() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        String keyId = request.getParameter("keyId");
        String receiverID = request.getParameter("receiverID");//传阅接收人id
        String receiverDeptID = request.getParameter("receiverDeptID");//传阅接收人部门
        String receiverCompanyID = request.getParameter("receiverCompanyID");//传阅接收人公司
        String source = request.getParameter("source");//04：初始项目——集团内部最近联系人；05：初始项目——往来单位最近联系人；06：初始项目——往来个人最近联系人
        JSONObject jsonObjList = new JSONObject();
        try {

            String hqlstaff = "from Staff where staffID = ?";
            CashierBillsExt billSub = null;
            CashierBills bills = null;


            // 列表传阅
            if (StringUtils.isNotEmpty(keyId)) {
//                String hql = "from CashierBillsExt where cashierBillsID = ?";
//                billSub = (CashierBillsExt) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{keyId});

                String hql = "from CashierBills where cashierBillsID = ?";
                bills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{keyId});
            }

            CAccount account = (CAccount) session.getAttribute("account");

            String staffID = null;
            String checkComID = null;
            if (account != null) {
                staffID = account.getStaffID();
                checkComID = account.getCompanyID();

            }
            String checkOrgID = null;
            if (checkComID != null && !checkComID.equals("")) {

                checkOrgID = contractService.getCheckOrg(staffID, checkComID);
            }
            if (billSub == null) {
                billSub = new CashierBillsExt();
                billSub.setCashierBillsID(bills.getCashierBillsID());
            }
            billSub.setUpdateTime(new Date());
            billSub.setFromMember(account.getStaffID());

            billSub.setReceiverID(receiverID);
            billSub.setReceiverDeptID(receiverDeptID);
            billSub.setReceiverCompanyID(receiverCompanyID);
//            if ("00".equals(bills.getStatus())) {
//                bills.setStatus("50");
//            }
            billSub.setIsSend("1");
            bills.setStatus("42");
            baseBeanService.save(billSub);
            baseBeanService.update(bills);

//            // 增加已传阅记录
//            DocumentPass dp = new DocumentPass();
//            dp.setPassId(serverService.getServerID("passId"));
//            dp.setDocId(keyId);
//            dp.setPassTime(new Date());
//            dp.setSubscriber2(account.getStaffID());
//            dp.setDeptOfsub2(checkOrgID);
//            dp.setCompanyIDOfsub2(account.getCompanyID());
//            dp.setToSubscriber2(receiverID);
//            dp.setDeptOftoSub2(receiverDeptID);
//            dp.setCompanyIDOftosub2(receiverCompanyID);
//            baseBeanService.save(dp);
//            Staff receiver = null;
//            COrganization org = null;
//            Company company = null;
//            String hqlorg = "from COrganization where organizationID = ?";
//            String hqlcompany = "from Company where companyID = ?";
//            receiver = (Staff) baseBeanService.getBeanByHqlAndParams(
//                    hqlstaff, new Object[]{receiverID});
//
//            org = (COrganization) baseBeanService.getBeanByHqlAndParams(
//                    hqlorg, new Object[]{receiverDeptID});
//
//            company = (Company) baseBeanService
//                    .getBeanByHqlAndParams(hqlcompany, new Object[]{receiverCompanyID});
//            String orgName = "";
//            if (org != null) {
//                orgName = org.getOrganizationName();
//            }
//            String comName = "";
//            if (company != null) {
//                comName = company.getCompanyName();
//            }
//            // 添加对公文的操作记录
//            docCommonService.addTrackRecord(bills.getCashierBillsID(), account
//                            .getStaffID(), checkOrgID,
//                    account.getCompanyID(), "传阅初始项目单至"
//                            + receiver.getStaffName() + "("
//                            + receiver.getStaffCode() + ")["
//                            + orgName + ","
//                            + comName + "]");
//            contractService.addRecentContact(account.getStaffID(), account.getCompanyID(), receiverID, receiverDeptID, receiverCompanyID, source);
            jsonObjList.accumulate("result", "suc");
        } catch (Exception e) {
            jsonObjList.accumulate("result", "fail");
            logger.error("操作异常", e);
        }
        result = jsonObjList.toString();
        return "success";
    }

    public String ajaxCostBudgetBillList1() {
        try {
            Map<String, Object> session = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session.get("account");
            //1.将列表信息拼接成DetachedCriteria类
            DetachedCriteria dc = reviewCirculateService.getBudgetBillDc1(account.getStaffID(), account.getCompanyID(), status);
            //2.获取数据信息
            pageForm = baseBeanService.getPageFormByDC(
                    (null != pageForm ? pageForm.getPageNumber() : 1),
                    (pageForm.getPageSize() == 0 ? 20 : pageForm.getPageSize()), dc);
            //3.将结果放入map返回
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "success";
    }

    //get  set
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getBillsType() {
        return billsType;
    }

    public void setBillsType(String billsType) {
        this.billsType = billsType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CashierBills getCashierBills() {
        return cashierBills;
    }

    public void setCashierBills(CashierBills cashierBills) {
        this.cashierBills = cashierBills;
    }

    public List<BaseBean> getGoodBeanslist() {
        return goodBeanslist;
    }

    public void setGoodBeanslist(List<BaseBean> goodBeanslist) {
        this.goodBeanslist = goodBeanslist;
    }

    public String getCashierBillsId() {
        return cashierBillsId;
    }

    public void setCashierBillsId(String cashierBillsId) {
        this.cashierBillsId = cashierBillsId;
    }

    public String getCashierBillsId1() {
        return cashierBillsId1;
    }

    public void setCashierBillsId1(String cashierBillsId1) {
        this.cashierBillsId1 = cashierBillsId1;
    }

    public GoodsBills getGoodsBills() {
        return goodsBills;
    }

    public void setGoodsBills(GoodsBills goodsBills) {
        this.goodsBills = goodsBills;
    }

    public CostBudgetAddBean getCostAddBean() {
        return costAddBean;
    }

    public void setCostAddBean(CostBudgetAddBean costAddBean) {
        this.costAddBean = costAddBean;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public GoodsBillsExtOpinion getGoodsBillsExtOpinion() {
        return goodsBillsExtOpinion;
    }

    public void setGoodsBillsExtOpinion(GoodsBillsExtOpinion goodsBillsExtOpinion) {
        this.goodsBillsExtOpinion = goodsBillsExtOpinion;
    }

    public List<CashierBillsExt> getCashierBillsExtList() {
        return cashierBillsExtList;
    }

    public void setCashierBillsExtList(List<CashierBillsExt> cashierBillsExtList) {
        this.cashierBillsExtList = cashierBillsExtList;
    }
}
