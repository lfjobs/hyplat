package hy.ea.finance.action;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.BenDis.DtOrderBillAdd;
import hy.ea.bo.finance.*;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.finance.vo.GoodsBillsVO;
import hy.ea.bo.history.vo.HistoryCashierBillVO;
import hy.ea.bo.office.TimingCharging;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 公司出纳单据汇总统计管理：CashierBillsSummaryAction
 *
 * @author yjg
 */
@Controller
@Scope("prototype")
public class CashierBillsSummaryAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private CLogBookService logBookService;
    @Resource
    private ShowExcelService excelService;
    @Resource
    private CompanyService companyserverService;
    @Resource
    private CCodeService codeService;

    public InputStream excelStream;
    private String result;
    private PageForm pageForm;
    private String parameter;
    private int pageNumber;
    private List<BaseBean> BillCheckList;

    private List<BaseBean> goodList;
    /**
     * 责任人
     */
    // private List<BaseBean> stafflist;
    /**
     * 个人往来关系
     */
    private List<CCode> codeRelationList;
    /**
     * 单位往来关系
     */
    private List<CCode> connectionlist;
    /**
     * 单据类别列表
     */
    private List<CCode> billTypes;
    private CashierBillsVO cashierBillsVO;
    private HistoryCashierBillVO hcashierBillsVO;
    private CashierBills cashierBills;
    private CashApplyBills cashApplyBills;
    private Object[] obj;
    private String search;
    private String sdate;
    private String edate;
    private String cc; // 判断公司还是集团
    private String period;

    private Map<String, String> bcheckMap;
    private String commStr; // 用于传递审核已经字符串

    List<String> strList;// 收方付方身份证号
    /**
     * 当前公司ID
     */
    private String currentCompanyID;
    /**
     * 当前部门ID
     */
    private String currentOrgnizationID;
    private ContactCompany comp;
    private DtOrderBillAdd dto;

    /** *******************************公司汇总************************************* */
    /**
     * 根据条件查询(保存条件)
     *
     * @return getCashierTallyList()
     */
    public String toSearch() {
        ActionContext.getContext().getSession()
                .put("tablesearch", cashierBillsVO);
        return getCashierList();
    }

    /**
     * 公司汇总出纳单据统计列表
     *
     * @param : account.getCompanyID(), organizationID
     * @return : list
     */
    public String getCashierList() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
                "scode20110224xpd2t2jvda0000000002");
        codeRelationList = codeService.getCCodeListByPID(
                account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
        billTypes = codeService.getCCodeListByPID(account.getCompanyID(),
                "scode20101101dfs3uhdprp0000000029");
        List<Object> list = getList();
        String hql = (String) list.get(0);
        Object[] parms = (Object[]) list.get(1);
        pageForm = baseBeanService.getPageForm(
                (null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 10 : pageNumber), hql, parms);
        return "companylist";
    }

    /**
     * 根据部门查询部门下的人员
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getStaffList() {
        /*
         * String sql =
		 * "select t.staffID,t.staffName,t.staffCode from dt_hr_staff t where  exists"
		 * +
		 * "(select staffID  from dtcos s where t.staffID=s.staffID and s.cosStatus='50' and  s.companyID='"
		 * + currentCompanyID + "' "; sql +=
		 * " and s.organizationID = ( select e.organizationID from dtCOrganization e where e.organizationID = '"
		 * + currentOrgnizationID + "'))";
		 */
        String sql = "select t.staffID,t.staffName,t.staffCode from dt_hr_staff t,dtcos s  "
                + "where t.staffID=s.staffID and s.cosStatus=? and  s.companyID=? and s.organizationID = ?";
        List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql,
                new Object[]{"50", currentCompanyID, currentOrgnizationID});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("stafflist", list);
        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();
        return "success";
    }

    /**
     * 根据部门查询部门下的人员
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getStaffListforsub() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        // 财务单据查询部门及子部门下所有人员
        String hql = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and (c.organizationID = ? "
                + "or exists(select d.depPostID from DepartmentPost d where d.depPostID = c.depPostID and d.leveloneOrgID = ?)))";
        Object[] params = new Object[]{account.getCompanyID(), "50",
                currentOrgnizationID, session.get("organizationID").toString()};

        List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(hql,
                params);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("stafflist", list);
        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();
        return "success";
    }

    /**
     * /根据公司编号以及部门查询当前部门下的所有在职员工
     *
     * @return
     */
    public String getStaffListfordep() {
        // 财务单据查询部门及子部门下所有人员
        String hql = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and (c.organizationID = ? "
                + "or exists(select d.depPostID from DepartmentPost d where d.depPostID = c.depPostID and d.leveloneOrgID = ?)))";
        Object[] params = new Object[]{currentCompanyID, "50",
                currentOrgnizationID, currentOrgnizationID};
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
                params);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();
        return "success";
    }

    /**
     * 查询公司出纳单据汇总列表（可根据条件查询）被调用
     *
     * @param : CashierBillsVO 查询条件
     * @return ：DetachedCriteria
     */

    public List<Object> getList() {
        List<Object> result = new ArrayList<Object>();
        List<Object> parms = new ArrayList<Object>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        ArrayList<String> cids = companyserverService
                .getCompanyAndItsChildrenIDs(account.getCompanyID());

        String hql = "from CashierBillsVO where 1=1 ";
        if (period != null && period.equals("01")) {
            hql += " and cashierDate between ? and ? ";
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -30);
            parms.add(c.getTime());
            parms.add(new Date());
        }
        if (search != null && search.equals("search")) {
            cashierBillsVO = (CashierBillsVO) session.get("tablesearch");
            if (null != cashierBillsVO.getStaffID()
                    && !"".equals(cashierBillsVO.getStaffID())) {
                hql += " and staffID = ? ";
                parms.add(cashierBillsVO.getStaffID());
            }
            if (null != cashierBillsVO.getCompanyID()
                    && !"".equals(cashierBillsVO.getCompanyID())) {
                hql += " and companyID = ? ";
                parms.add(cashierBillsVO.getCompanyID());
            } else {
                if (cids.size() == 1) {
                    hql += " and companyID = ? ";
                    parms.add(cids.get(0));
                } else {
                    hql += " and (pcompanyID = ? or companyID = ? )";
                    parms.add(account.getCompanyID());
                    parms.add(account.getCompanyID());
                }

            }
            if (null != cashierBillsVO.getDepartmentID()
                    && !"".equals(cashierBillsVO.getDepartmentID())
                    && !cashierBillsVO.getCompanyID().equals(
                    cashierBillsVO.getDepartmentID())) {
                hql += " and (organizationID = ? or departmentID = ? ) ";
                parms.add(cashierBillsVO.getDepartmentID());
                parms.add(cashierBillsVO.getDepartmentID());
            }
            if (null != cashierBillsVO.getBillsType()
                    && !"".equals(cashierBillsVO.getBillsType())) {
                hql += " and billsType = ?";
                parms.add(cashierBillsVO.getBillsType());
            }
            if (null != cashierBillsVO.getJournalNum()
                    && !"".equals(cashierBillsVO.getJournalNum())) {
                hql += " and journalNum like ?";
                parms.add("%" + cashierBillsVO.getJournalNum() + "%");
            }
            if (null != cashierBillsVO.getCcompanyname()
                    && !"".equals(cashierBillsVO.getCcompanyname())) {
                hql += " and ccompanyname like ?";
                parms.add("%" + cashierBillsVO.getCcompanyname() + "%");
            }
            if (null != cashierBillsVO.getStatus().trim()
                    && !"".equals(cashierBillsVO.getStatus().trim())) {
                hql += " and status = ?";
                parms.add(cashierBillsVO.getStatus().trim());
            } else {
                hql += " and status between ? and ?";
                parms.add("04");
                parms.add("10");
            }
            if (null != cashierBillsVO.getTaxstatus()
                    && !"".equals(cashierBillsVO.getTaxstatus())) {
                hql += " and taxstatus = ? ";
                parms.add(cashierBillsVO.getTaxstatus());
            }
            if (null != cashierBillsVO.getContactUserName()
                    && !"".equals(cashierBillsVO.getContactUserName())) {
                hql += " and staffname like ? ";
                parms.add("%" + cashierBillsVO.getContactUserName() + "%");
            }
            if (sdate != null && edate != null && !("").equals(sdate)
                    && !("").equals(edate)) {
                hql += " and cashierDate between ? and ? ";

                parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
                        "yyyy-MM-dd hh:mm:ss"));

                parms.add(Utilities.getDateFromString(edate + " 23:59:59",
                        "yyyy-MM-dd hh:mm:ss"));
            }
            if (null != cashierBillsVO.getDepStatue()
                    && !"".equals(cashierBillsVO.getDepStatue())) {
                if ("00".equals(cashierBillsVO.getDepStatue())) {
                    hql += " and (depStatue = ? or depStatue is null)";
                    parms.add("00");
                } else {
                    hql += " and depStatue = ? ";
                    parms.add(cashierBillsVO.getDepStatue());
                }
            }

        } else {
            if (cids.size() == 1) {
                hql += " and companyID = ? ";
                parms.add(account.getCompanyID());
            } else {
                hql += " and (pcompanyID = ? or companyID= ? ) ";
                parms.add(account.getCompanyID());
                parms.add(account.getCompanyID());
            }
        }
        hql += " order by journalNum desc";
        result.add(hql);
        result.add(parms.toArray());
        return result;
    }

    /**
     * 导出公司汇总出纳单据
     *
     * @param : account organizationID
     * @return : showexcel
     */
    public String showExcel() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String organizationID = (String) session.get("organizationID");
        List<Object> list = getList();
        String hql = (String) list.get(0);
        Object[] parms = (Object[]) list.get(1);
        excelStream = excelService.showExcel(CashierBillsVO.columnHeadings(),
                baseBeanService.getListBeanByHqlAndParams(hql, parms));
        CLogBook logBook = logBookService.saveCLogBook(organizationID,
                "导出公司汇总出纳单据统计", account);
        baseBeanService.update(logBook);
        return "showexcel";
    }

    /**
     * 查看
     *
     * @return
     */
    public String toCashier() {
        if (cashierBillsVO != null) {
            Object[] params1 = {cashierBillsVO.getCashierBillsID()};
            String hql1 = "from GoodsBillsVO where  cashierBillsID=?";
            pageForm = baseBeanService.getPageForm(
                    (null != pageForm ? pageForm.getPageNumber() : 1),
                    (pageNumber == 0 ? 10 : pageNumber), hql1, params1);
            String hql = " from CashierBillsVO where  cashierBillsID=?";
            cashierBillsVO = (CashierBillsVO) baseBeanService
                    .getBeanByHqlAndParams(hql, params1);
            String hql2 = "from CashierBills where  cashierBillsID=?";
            cashierBills = (CashierBills) baseBeanService
                    .getBeanByHqlAndParams(hql2, params1);
        }
        return "edit";
    }


    // 原单据打印方法
    @SuppressWarnings("unchecked")
    public String toprintCashier() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String journalNum = request.getParameter("journalNum");
        if(journalNum!=null&&!journalNum.equals("")){
            CashierBills kk = (CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where jNumOrder = ? and  billsType = '收款单' ",new Object[]{journalNum});
            if(kk!=null) {
                cashierBillsVO.setCashierBillsID(kk.getCashierBillsID());
            }
        }
        String hql1 = " from GoodsBillsVO where cashierBillsID=? ";
        if (cashierBillsVO != null) {
            Object[] params1 = {cashierBillsVO.getCashierBillsID()};
			/* List<BaseBean> goodList=new ArrayList<BaseBean>(); */
            goodList = baseBeanService.getListBeanByHqlAndParams(hql1, params1);

            String hql = " from CashierBillsVO where  cashierBillsID=?";
            cashierBillsVO = (CashierBillsVO) baseBeanService
                    .getBeanByHqlAndParams(hql, params1);

            List<BaseBean> bCheckList = baseBeanService
                    .getListBeanByHqlAndParams(
                            "from BillCheck where newBillsID=? order by audittime",
                            new Object[]{cashierBillsVO.getCashierBillsID()
                                    .substring(0, 40)});
            String hqls = "from ContactCompany where  ccompanyID=?";
            comp = (ContactCompany) baseBeanService.getBeanByHqlAndParams(hqls,
                    new Object[]{cashierBillsVO.getCcompanyID()});
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where cashierBillsID=?", new Object[]{cashierBillsVO.getCashierBillsID()});
            dto = (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams("from DtOrderBillAdd where oaBillId=?", params1);
            if (bCheckList.size() > 0) {
                bcheckMap = new HashMap<String, String>();
                commStr = "";
                for (int i = 0; i < bCheckList.size(); i++) {
                    BillCheck billCheck = (BillCheck) bCheckList.get(i);
                    if (billCheck.getDeptpost() != null) {
                        commStr = commStr + billCheck.getAuditorname() + ":"
                                + billCheck.getComments() + ";";
                        bcheckMap.put(billCheck.getDeptpost(),
                                billCheck.getAuditorname());
                    }
                }
            }
        }
        cashApplyBills = (CashApplyBills) baseBeanService
                .getBeanByHqlAndParams(
                        "from CashApplyBills where cashierBillsID=?",
                        new Object[]{cashierBillsVO.getCashierBillsID()
                                .substring(0, 40)});
        if (cashApplyBills != null) {
            strList = baseBeanService
                    .getListBeanBySqlAndParams(
                            "select c.STAFFIDENTITYCARD from view_conuser c where c.RELATIONID in(?,?)",
                            new Object[]{
                                    cashApplyBills.getAppropriationcompanyID(),
                                    cashApplyBills.getReceivablesID()});
        }

        if (("收款单".equals(cashierBillsVO.getBillsType().trim()) || "积分入库单".equals(cashierBillsVO.getBillsType().trim()))
                || "00".equals(cashierBills.getFkStatus())) {
            String radioType = request.getParameter("radioType");
            if (cashierBills.getjNumOrder() != null && !cashierBills.getjNumOrder().equals("")) {
                CashierBills cashierBills1 = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum=?", new Object[]{cashierBills.getjNumOrder()});
                if ("积分入库单".equals(cashierBillsVO.getBillsType().trim())) {
                    goodList = baseBeanService.getListBeanByHqlAndParams(hql1, new Object[]{cashierBills1.getCashierBillsID()});
                }

                //获取凭证号和订单号一致的公司名字
                StringBuilder sql = new StringBuilder();
                sql.append(" select cb.companyname,dc.responsibletel ");
                sql.append(" from dtcashierbills cb,DT_ccom_com cc,dtContactCompany dc ");
                sql.append(" where cb.companyid=cc.compnay_id and cc.ccompany_id = dc.ccompanyid ");
                sql.append(" and cb.journalnum=cb.JNUMORDER ");
                sql.append(" and cb.JNUMORDER=? ");

                List<Object> obj = baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{cashierBills1.getjNumOrder()});
                for (int i = 0; i < obj.size(); i++) {
                    Object[] para = (Object[]) obj.get(i);
                    request.setAttribute("companyname", para[0]);
                    request.setAttribute("responsibletel", para[1]);
                }

                //判断是否是驾校
                String hql = " from GoodsBills where cashierBillsID=? ";
                List<BaseBean> gs = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBills1.getCashierBillsID()});
                String phql = " from ProductPackaging where ppID=? ";
                List<BaseBean> p=null;
                if (gs!=null&&gs.size() > 0) {
                    p=new ArrayList<BaseBean>();
                    for (int i=0;i<gs.size();i++) {
                        GoodsBills g=(GoodsBills)gs.get(i);
                        ProductPackaging pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(phql, new Object[]{g.getPpID()});
                        p.add(pp);
                    }
                }
                if (p!=null&&p.size() > 0) {
                    for (int i=0;i<p.size();i++) {
                        ProductPackaging pp =(ProductPackaging)p.get(i);
                        if ("z30001汽车驾校".equals(pp.getTradeCode())) {
                            request.setAttribute("tradeCode", pp.getTradeCode());
                            break;
                        }
                    }
                    for (int i=0;i<p.size();i++) {
                        ProductPackaging pp =(ProductPackaging)p.get(i);
                        request.setAttribute("tradeCode", pp.getTradeCode());
                        if ("z30001汽车驾校".equals(pp.getTradeCode())) {
                            request.setAttribute("contactUserName", cashierBills1.getCtUserName());
                            request.setAttribute("tel", cashierBills1.getReference());
                            request.setAttribute("staffIdentityCard", cashierBills1.getStaffIdentityCard());
                            request.setAttribute("ReferrerAddress", cashierBills1.getReferrerAddress());
                        }
                        if (pp.getType() != null && (pp.getType().equals("包天计时") || pp.getType().equals("包月计时")
                                || pp.getType().equals("包年计时"))) {

                            TimingCharging tcg = (TimingCharging) baseBeanService.getBeanByHqlAndParams("from TimingCharging where journalnum = ?", new Object[]{cashierBills.getjNumOrder()});
                            String carNum = "";
                            if (tcg != null) {
                                carNum = tcg.getCarNumber();
                            }
                            request.setAttribute("carNum", carNum);
                        }
                    }
                }
            }
            if (radioType != null && radioType.equals("xp")
                    || radioType != null && radioType.equals("Rxp")) {
                if (goodList.size() > 0) {
                    BigDecimal b = new BigDecimal(0);
                    for (int i = 0; i < goodList.size(); i++) {
                        GoodsBillsVO g = (GoodsBillsVO) goodList.get(i);
                        BigDecimal big = new BigDecimal(g.getRealMoney());
                        b.add(big);
                    }
                    request.setAttribute("b", b);
                }
                if (radioType.equals("xp")) {
                    return "xp";
                } else {
                    return "Rxp";
                }
            } else {
                return "printcashier4CashAccept";
            }
        }
        return "printcashier";
    }

    // 归档单据中打印方法
    public String toprintArchive() {
        if (cashierBillsVO != null) {
            Object[] params1 = {cashierBillsVO.getCashierBillsID()};
            String hql = "from HistoryGoodsBillVO where cashierbillsid=?";
            pageForm = baseBeanService.getPageForm(
                    (null != pageForm ? pageForm.getPageNumber() : 1),
                    (pageNumber == 0 ? 10 : pageNumber), hql, params1);
            String hql2 = " from HistoryCashierBillVO where cashierbillsid=?";
            hcashierBillsVO = (HistoryCashierBillVO) baseBeanService
                    .getBeanByHqlAndParams(hql2, params1);
        }
        return "printcashier2";
    }

    /**
     * ************************************部门出纳记账单汇总****************************
     * **************
     */
    // public List<BaseBean> getStafflist() {
    // return stafflist;
    // }
    //
    // public void setStafflist(List<BaseBean> stafflist) {
    // this.stafflist = stafflist;
    // }
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    public CashierBillsVO getCashierBillsVO() {
        return cashierBillsVO;
    }

    public void setCashierBillsVO(CashierBillsVO cashierBillsVO) {
        this.cashierBillsVO = cashierBillsVO;
    }

    public List<CCode> getCodeRelationList() {
        return codeRelationList;
    }

    public void setCodeRelationList(List<CCode> codeRelationList) {
        this.codeRelationList = codeRelationList;
    }

    public List<CCode> getConnectionlist() {
        return connectionlist;
    }

    public void setConnectionlist(List<CCode> connectionlist) {
        this.connectionlist = connectionlist;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getCurrentCompanyID() {
        return currentCompanyID;
    }

    public void setCurrentCompanyID(String currentCompanyID) {
        this.currentCompanyID = currentCompanyID;
    }

    public List<CCode> getBillTypes() {
        return billTypes;
    }

    public void setBillTypes(List<CCode> billTypes) {
        this.billTypes = billTypes;
    }

    public String getCurrentOrgnizationID() {
        return currentOrgnizationID;
    }

    public void setCurrentOrgnizationID(String currentOrgnizationID) {
        this.currentOrgnizationID = currentOrgnizationID;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public Object[] getObj() {
        return obj;
    }

    public void setObj(Object[] obj) {
        this.obj = obj;
    }

    public CashierBills getCashierBills() {
        return cashierBills;
    }

    public void setCashierBills(CashierBills cashierBills) {
        this.cashierBills = cashierBills;
    }

    public HistoryCashierBillVO getHcashierBillsVO() {
        return hcashierBillsVO;
    }

    public void setHcashierBillsVO(HistoryCashierBillVO hcashierBillsVO) {
        this.hcashierBillsVO = hcashierBillsVO;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<BaseBean> getBillCheckList() {
        return BillCheckList;
    }

    public void setBillCheckList(List<BaseBean> billCheckList) {
        BillCheckList = billCheckList;
    }

    public CashApplyBills getCashApplyBills() {
        return cashApplyBills;
    }

    public void setCashApplyBills(CashApplyBills cashApplyBills) {
        this.cashApplyBills = cashApplyBills;
    }

    public List<String> getStrList() {
        return strList;
    }

    public void setStrList(List<String> strList) {
        this.strList = strList;
    }

    public Map<String, String> getBcheckMap() {
        return bcheckMap;
    }

    public void setBcheckMap(Map<String, String> bcheckMap) {
        this.bcheckMap = bcheckMap;
    }

    public String getCommStr() {
        return commStr;
    }

    public void setCommStr(String commStr) {
        this.commStr = commStr;
    }

    public List<BaseBean> getGoodList() {
        return goodList;
    }

    public void setGoodList(List<BaseBean> goodList) {
        this.goodList = goodList;
    }

    public ContactCompany getComp() {
        return comp;
    }

    public void setComp(ContactCompany comp) {
        this.comp = comp;
    }


}
