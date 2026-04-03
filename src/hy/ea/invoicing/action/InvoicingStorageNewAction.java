package hy.ea.invoicing.action;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.company.vo.ContactCompanyView;
import hy.ea.bo.company.vo.ContactUser;
import hy.ea.bo.finance.*;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.FinancialBillsGood;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.production.service.WarehouseService;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.util.Utilities;
import hy.ea.util.VOtool;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONObject;

public class InvoicingStorageNewAction
{
  @Resource
  private BaseBeanService baseBeanService;
  @Resource
  private CLogBookService logBookService;
  @Resource
  private WarehouseService warehouseService;
  @Resource
  private CCodeService codeService;
  @Resource
  private ServerService serverService;
  public InputStream excelStream;
  private FinancialBill financialBill;
  private FinancialBillsGood financialBillsGood;
  private CashierBills cashierBills;
  private CashierBillsVO cashierBillsVO;
  private PageForm pageForm;
  private String parameter;
  private int pageNumber;
  private String search;
  private Map<String, GoodsBills> goodsmapold;
  private Map<String, GoodsBills> goodsmap;
  private Map<String, String> billcheckmap;
  private List<CCode> payTypeList;
  private List<CCode> logisticsList;
  private List<CCode> connectionlist;
  private List<CCode> codeRelationList;
  private List<CCode> typelist;
  private String companyname;
  private String organizationname;
  private String xmtype;
  private List<BaseBean> warehouseList;
  private List<BaseBean> billsgoodlist;
  private List<FinancialBillsGood> BillsGoodList;
  private ContactUser contactUser1;
  private ContactCompanyView contactCompanyView1;
  private Inventory inventoryParam;
  private String sdate;
  private String edate;
  private String print;
  private stockInv stockinv;
  private List<BaseBean> stafflist;
  private int camount;
  private ContactCompany contactCompanyView;
  private String result;
  private String purchaseDate;
  private String type;
  private String deptpost;
  private String remarks;
  private int loginstaffcheck;
  private List<BaseBean> BillCheckList;
  
  public String getcashierBillstatus()
    throws UnsupportedEncodingException
  {
    CAccount account = 
      (CAccount)ActionContext.getContext().getSession().get("account");
    if (account == null)
    {
      Map<String, Object> map = new HashMap();
      map.put("nologin", Integer.valueOf(1));
      JSONObject obj = JSONObject.fromObject(map);
      this.result = obj.toString();
      return "success";
    }
    Object[] params = { this.financialBill.getFinancialbillID() };
    String hql = "from FinancialBill where financialbillID= ?";
    FinancialBill fina = (FinancialBill)this.baseBeanService.getBeanByHqlAndParams(
      hql, params);
    Map<String, Object> map = new HashMap();
    map.put("getstatus", fina.getBillStatus());
    JSONObject obj = JSONObject.fromObject(map);
    this.result = obj.toString();
    return "success";
  }
  
  public String toInvoicingStorage()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    String groupCompanySn = session.get("groupCompanySn").toString();
    
    String hqlForMan = "from Staff where staffID = ?";
    Staff sta = (Staff)this.baseBeanService.getBeanByHqlAndParams(hqlForMan, 
      new Object[] { account.getStaffID() });
    session.put("ManStaffName1", sta.getStaffName() + "---" + sta.getStaffCode());
    session.put("ManStaffName", sta.getStaffName());
    session.put("ManStaffCode", sta.getStaffCode());
    session.put("ManStaffId", sta.getStaffID());
    
    Object[] params = { account.getCompanyID() };
    this.typelist = this.codeService.getCCodeListByPID(account.getCompanyID(), "scode20101014v5zed7cukk0000000004");
    this.pageForm = this.baseBeanService
      .getPageForm(
      this.pageForm != null ? this.pageForm.getPageNumber() : 1, 
      this.pageNumber == 0 ? 10 : this.pageNumber, 
      " from DepotManage where companyID = ? and depotState='00'", 
      params);
    
    List<Object> list = getListyh();
    String sql = (String)list.get(0);
    Object[] parms = (Object[])list.get(1);
    List<Object> blist = new ArrayList();
    blist = this.baseBeanService.getListBeanBySqlAndParams(sql, parms);
    if (blist.size() > 0)
    {
      Object[] objs = (Object[])blist.get(0);
      
      String hql = "from FinancialBill where groupCompanySn = ? and companyid = ? and organizationID=? and financialbillID=?";
      this.financialBill = ((FinancialBill)this.baseBeanService.getBeanByHqlAndParams(hql, 
        new Object[] { groupCompanySn, account.getCompanyID(), organizationID, objs[0] }));
      String hqlca = "from CashierBills where cashierBillsID=?";
      this.cashierBills = ((CashierBills)this.baseBeanService.getBeanByHqlAndParams(hqlca, 
        new Object[] { this.financialBill.getCashierBillsID() }));
      
      String hqlg = "from GoodsBills where cashierBillsID=? order by goodsNomber";
      Object[] par = { this.financialBill.getCashierBillsID() };
      this.billsgoodlist = this.baseBeanService.getListBeanByHqlAndParams(hqlg, par);
      
      BillCheck bCheck = (BillCheck)this.baseBeanService.getBeanByHqlAndParams("from BillCheck where newBillsID=?", 
        new Object[] { this.financialBill.getCashierBillsID() });
      if (bCheck != null)
      {
        this.BillCheckList = this.baseBeanService.getListBeanByHqlAndParams("from BillCheck where firstBillsID=? order by audittime desc", 
          new Object[] { bCheck.getFirstBillsID() });
        this.loginstaffcheck = 0;
        if (this.BillCheckList.size() > 0)
        {
          this.billcheckmap = new HashMap();
          for (int i = 0; i < this.BillCheckList.size(); i++)
          {
            BillCheck bc = (BillCheck)this.BillCheckList.get(i);
            if (bc.getDeptpost() != null)
            {
              this.billcheckmap.put(bc.getDeptpost(), bc.getAuditorname());
              if (bc.getAuditorid().equals(sta.getStaffID())) {
                this.loginstaffcheck += 1;
              }
            }
          }
        }
      }
      return "toinvoicingstorage";
    }
    return toExamineGoodsBillList();
  }
  
  public String saveBillCheck()
    throws UnsupportedEncodingException
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    
    String hqlForMan = "from Staff where staffID = ?";
    Staff sta = (Staff)this.baseBeanService.getBeanByHqlAndParams(hqlForMan, 
      new Object[] { account.getStaffID() });
    if (account == null)
    {
      Map<String, Object> map = new HashMap();
      map.put("nologin", Integer.valueOf(1));
      JSONObject obj = JSONObject.fromObject(map);
      this.result = obj.toString();
      return "success";
    }
    String hqlcash = "from CashierBills where cashierBillsID = ?";
    CashierBills cbills = (CashierBills)this.baseBeanService.getBeanByHqlAndParams(hqlcash, 
      new Object[] { this.cashierBills.getCashierBillsID() });
    
    BillCheck billcheck = new BillCheck();
    List<BaseBean> baseBeanList = new ArrayList();
    billcheck.setCheckid(this.serverService.getServerID("billcheck"));
    billcheck.setAuditorcompanyid(cbills.getCompanyID());
    billcheck.setAuditorcompanyname(cbills.getCompanyName());
    billcheck.setAuditororgID(cbills.getDepartmentID());
    billcheck.setAuditororgName(cbills.getDepartmentName());
    billcheck.setNewBillsID(cbills.getCashierBillsID());
    billcheck.setOldBillsID(cbills.getCashierBillsID());
    billcheck.setFirstBillsID(cbills.getCashierBillsID());
    billcheck.setInputid(sta.getStaffID());
    billcheck.setInputname(sta.getStaffName());
    billcheck.setStaffID(sta.getStaffID());
    billcheck.setStaffName(sta.getStaffName());
    billcheck.setJournalNum(cbills.getJournalNum());
    billcheck.setBillsType(cbills.getBillsType());
    billcheck.setProjectName(cbills.getProjectName());
    billcheck.setAudittime(new Date());
    billcheck.setAuditorid(sta.getStaffID());
    billcheck.setAuditorname(sta.getStaffName());
    billcheck.setComments(this.remarks);
    billcheck.setAuditorstatus("02");
    billcheck.setDeptpost(this.deptpost);
    billcheck.setRemark(this.remarks);
    billcheck.setCashierDate(new Date());
    baseBeanList.add(billcheck);
    this.parameter = ("添加审核记录（" + billcheck.getJournalNum() + "）");
    CLogBook logBook = this.logBookService.saveCLogBook(organizationID, this.parameter, account);
    baseBeanList.add(logBook);
    this.baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);
    
    BillCheck bCheck = (BillCheck)this.baseBeanService.getBeanByHqlAndParams("from BillCheck where newBillsID=?", 
      new Object[] { this.cashierBills.getCashierBillsID() });
    if (bCheck != null)
    {
      this.BillCheckList = this.baseBeanService.getListBeanByHqlAndParams("from BillCheck where firstBillsID=? order by audittime desc", 
        new Object[] { bCheck.getFirstBillsID() });
      this.loginstaffcheck = 0;
      if (this.BillCheckList.size() > 0) {
        for (int i = 0; i < this.BillCheckList.size(); i++)
        {
          BillCheck bc = (BillCheck)this.BillCheckList.get(i);
          if ((bc.getDeptpost() != null) && 
            (bc.getAuditorid().equals(sta.getStaffID()))) {
            this.loginstaffcheck += 1;
          }
        }
      }
    }
    Map<String, Object> map = new HashMap();
    map.put("loginstaff", Integer.valueOf(this.loginstaffcheck));
    JSONObject obj = JSONObject.fromObject(map);
    this.result = obj.toString();
    return "success";
  }
  
  public String toSearchEGB()
  {
    ActionContext.getContext().getSession().put("financialBill", 
      this.financialBill);
    return toExamineGoodsBillList();
  }
  
  public String toExamineGoodsBillList()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    List<Object> list = getListyh();
    String sql = (String)list.get(0);
    Object[] parms = (Object[])list.get(1);
    /*String staffhql = "from Staff s where exists ( select staffID from FinancialBill c where c.staffID=s.staffID and c.companyID=? and c.organizationID=? and c.billStatus=? and c.billsType=?)";
    this.stafflist = this.baseBeanService.getListBeanByHqlAndParams(staffhql, new Object[] { account.getCompanyID(), organizationID, "14", "验货单" });
    
    this.connectionlist = this.codeService.getCCodeListByPID(account.getCompanyID(), "scode20110224xpd2t2jvda0000000002");
    
    this.codeRelationList = this.codeService.getCCodeListByPID(account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");*/
    this.pageForm = this.baseBeanService.getPageFormBySQL(
      this.pageForm != null ? this.pageForm.getPageNumber() : 1, 
      this.pageNumber == 0 ? 10 : this.pageNumber, sql, "select count(1) " + 
      sql.substring(sql.indexOf("from")), parms);
    
    return "tostoragelist";
  }
  
  public List<Object> getListyh()
  {
    List<Object> result = new ArrayList();
    List<Object> parms = new ArrayList();
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    String sql = VOtool.getCashierBillVO(4);
    sql = sql + " where 1=1";
    if (((this.xmtype != null ? 1 : 0) & ("".equals(this.xmtype) ? 0 : 1)) != 0)
    {
      sql = sql + " and cs.xmtype like ? ";
      parms.add(this.xmtype + "%");
    }
    if ((this.search != null) && (this.search.equals("search")))
    {
      FinancialBill financialBill = (FinancialBill)session.get("financialBill");
      if ((financialBill.getJournalNum() != null) && 
        (!financialBill.getJournalNum().trim().equals("")))
      {
        sql = sql + " and f.journalNum like ? ";
        parms.add("%" + financialBill.getJournalNum().trim() + "%");
      }
      if ((financialBill.getBillStaffName() != null) && 
        (!financialBill.getBillStaffName().equals("")))
      {
        sql = sql + " and f.billStaffName = ? ";
        parms.add(financialBill.getBillStaffName());
      }
      if ((this.sdate != null) && (this.edate != null) && (!"".equals(this.sdate)) && 
        (!"".equals(this.edate)))
      {
        sql = sql + " and f.billsDate between ? and ?";
        parms.add(Utilities.getDateFromString(this.sdate, "yyyy-MM-dd"));
        parms.add(Utilities.getDateFromString(this.edate, "yyyy-MM-dd"));
      }
      if ((financialBill.getBillsType() != null) && 
        (!financialBill.getBillsType().equals("")))
      {
        sql = sql + " and f.billsType = ? ";
        parms.add(financialBill.getBillsType());
      }
    }
    if ((this.search == null) || (this.search.equals("")))
    {
      sql = sql + "and f.billsType=?";
      parms.add("验货单");
      sql = sql + " and (f.billStatus=?";
      parms.add("14");
      sql = sql + " or f.billStatus=?)";
      parms.add("05");
    }

    sql = sql + " and f.organizationID=?";
    parms.add(organizationID);

    sql = sql + " and f.companyid = ? ";
    parms.add(account.getCompanyID());


    sql = sql + "order by f.billsDate desc";
    result.add(sql);
    result.add(parms.toArray());
    return result;
  }
  
  public String seeExamineGoodsBill()
  {
    toSeegb();
    return "toSeegb";
  }
  
  public String toPrintExamineGoodsBill()
  {
    toSeegb();
    return "toSeegbprint";
  }
  
  private void toSeegb()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    String groupCompanySn = session.get("groupCompanySn").toString();
    
    String hqlForMan = "from Staff where staffID = ?";
    Staff sta = (Staff)this.baseBeanService.getBeanByHqlAndParams(hqlForMan, 
      new Object[] { account.getStaffID() });
    session.put("ManStaffName", sta.getStaffName());
    session.put("ManStaffCode", sta.getStaffCode());
    session.put("ManStaffId", sta.getStaffID());
    String hql = "from FinancialBill where groupCompanySn = ? and companyid = ? and organizationID=? and financialbillID=?";
    this.financialBill = ((FinancialBill)this.baseBeanService.getBeanByHqlAndParams(hql, new Object[] { groupCompanySn, account.getCompanyID(), organizationID, this.financialBill.getFinancialbillID() }));
    String hqlca = "from CashierBills where cashierBillsID=?";
    this.cashierBills = ((CashierBills)this.baseBeanService.getBeanByHqlAndParams(hqlca, 
      new Object[] { this.financialBill.getCashierBillsID() }));
    String hqlg = "from GoodsBills where cashierBillsID=? order by goodsNomber";
    this.warehouseList = this.baseBeanService.getListBeanByHqlAndParams(hqlg, new Object[] { this.financialBill.getCashierBillsID() });
    













    String hql3 = " from ContactCompany where ccompanyID=? ";
    this.contactCompanyView = ((ContactCompany)this.baseBeanService.getBeanByHqlAndParams(hql3, new Object[] { this.financialBill.getCcompanyID() }));
    String comhql = "from Company where companyID=?";
    Company company = (Company)this.baseBeanService.getBeanByHqlAndParams(
      comhql, new Object[] { this.financialBill.getCompanyID() });
    this.companyname = company.getCompanyName();
    String orghql = "from COrganization where companyID=? and organizationID=?";
    COrganization cOrganization = (COrganization)this.baseBeanService
      .getBeanByHqlAndParams(orghql, new Object[] {
      this.financialBill.getCompanyID(), 
      this.financialBill.getOrganizationID() });
    this.organizationname = cOrganization.getOrganizationName();
    
    BillCheck bCheck = (BillCheck)this.baseBeanService.getBeanByHqlAndParams("from BillCheck where newBillsID=?", new Object[] { this.financialBill.getCashierBillsID() });
    if (bCheck != null)
    {
      this.BillCheckList = this.baseBeanService.getListBeanByHqlAndParams("from BillCheck where firstBillsID=? order by audittime desc", 
        new Object[] { bCheck.getFirstBillsID() });
      this.loginstaffcheck = 0;
      if (this.BillCheckList.size() > 0)
      {
        this.billcheckmap = new HashMap();
        for (int i = 0; i < this.BillCheckList.size(); i++)
        {
          BillCheck bc = (BillCheck)this.BillCheckList.get(i);
          if (bc.getDeptpost() != null)
          {
            this.billcheckmap.put(bc.getDeptpost(), bc.getAuditorname());
            if (bc.getAuditorid().equals(sta.getStaffID())) {
              this.loginstaffcheck += 1;
            }
          }
        }
      }
    }
  }
  
  public String toExamineGoodsAddDan()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    String groupCompanySn = session.get("groupCompanySn").toString();
    String hql = "from FinancialBill where groupCompanySn = ? and companyid = ? and organizationID=? and financialbillID=?";
    
    this.financialBill = ((FinancialBill)this.baseBeanService.getBeanByHqlAndParams(hql, new Object[] { groupCompanySn, account.getCompanyID(), organizationID, this.financialBill.getFinancialbillID() }));
    String hqlca = "from CashierBills where cashierBillsID=?";
    this.cashierBills = ((CashierBills)this.baseBeanService.getBeanByHqlAndParams(hqlca, new Object[] { this.financialBill.getCashierBillsID() }));
    
    String hqlg = "from GoodsBills where cashierBillsID=? order by goodsNomber";
    Object[] par = { this.financialBill.getCashierBillsID() };
    this.billsgoodlist = this.baseBeanService.getListBeanByHqlAndParams(hqlg, par);
    
    String hqlForMan = "from Staff where staffID = ?";
    Staff sta = (Staff)this.baseBeanService.getBeanByHqlAndParams(hqlForMan, 
      new Object[] { account.getStaffID() });
    session.put("ManStaffName1", sta.getStaffName() + "---" + sta.getStaffCode());
    session.put("ManStaffName", sta.getStaffName());
    session.put("ManStaffCode", sta.getStaffCode());
    session.put("ManStaffId", sta.getStaffID());
    String companyID = account.getCompanyID();
    



    this.connectionlist = this.codeService.getCCodeListByPID(account.getCompanyID(), 
      "scode20110224xpd2t2jvda0000000002");
    


    Object[] params = { account.getCompanyID() };
    this.typelist = this.codeService.getCCodeListByPID(account.getCompanyID(), "scode20101014v5zed7cukk0000000004");
    this.pageForm = this.baseBeanService
      .getPageForm(
      this.pageForm != null ? this.pageForm.getPageNumber() : 1, 
      this.pageNumber == 0 ? 10 : this.pageNumber, 
      " from DepotManage where companyID = ? and depotState='00'", 
      params);
    
    BillCheck bCheck = (BillCheck)this.baseBeanService.getBeanByHqlAndParams("from BillCheck where newBillsID=?", 
      new Object[] { this.financialBill.getCashierBillsID() });
    if (bCheck != null)
    {
      this.BillCheckList = this.baseBeanService.getListBeanByHqlAndParams("from BillCheck where firstBillsID=? order by audittime desc", 
        new Object[] { bCheck.getFirstBillsID() });
      this.loginstaffcheck = 0;
      if (this.BillCheckList.size() > 0)
      {
        this.billcheckmap = new HashMap();
        for (int i = 0; i < this.BillCheckList.size(); i++)
        {
          BillCheck bc = (BillCheck)this.BillCheckList.get(i);
          if (bc.getDeptpost() != null)
          {
            this.billcheckmap.put(bc.getDeptpost(), bc.getAuditorname());
            if (bc.getAuditorid().equals(sta.getStaffID())) {
              this.loginstaffcheck += 1;
            }
          }
        }
      }
    }
    return "toinvoicingstorage";
  }
  
  private void dd(List<BaseBean> baseBeanList, String Scb, String Sgb, String Sgb2, String Sg, String Sc, String Ss, Boolean Sn, int quantity, String num)
  {
    GoodsNum goodsNum = null;
    String num3 = "";
    Object gval = null;
    if (Sn.booleanValue())
    {
      goodsNum = new GoodsNum();
      goodsNum.setGnID(this.serverService.getServerID("goodsnum"));
      goodsNum.setGnPID(goodsNum.getGnID());
      goodsNum.setCashierBillsID(Scb);
      goodsNum.setGoodsBillsID(Sgb);
      goodsNum.setGoodsID(Sg);
      goodsNum.setCompanyID(Sc);
      goodsNum.setPhaseDate(new Date());
      goodsNum.setStatus(Ss);
      GoodsManage goodsManage = (GoodsManage)this.baseBeanService.getBeanByHqlAndParams("from GoodsManage where goodsID=?", new Object[] { Sg });
      goodsNum.setGoodsCoding(goodsManage.getGoodsCoding());
      if ((num != null) && (!num.equals("")))
      {
        num3 = Integer.toString(Integer.parseInt(num) + 1);
      }
      else
      {
        gval = this.baseBeanService.getObjectBySqlAndParams("select max(goodsnumber) from dtgoodsnum", null);
        if (gval == null) {
          num3 = "0001";
        } else {
          num3 = Integer.toString(Integer.parseInt(gval.toString()) + 1);
        }
      }
      if ((gval != null) || (num3 != null))
      {
        int num1 = num3.length();
        for (int i = 0; i < 4 - num1; i++) {
          num3 = "0" + num3;
        }
      }
      goodsNum.setGoodsnumber(num3);
      baseBeanList.add(goodsNum);
    }
    if (quantity > 1) {
      dd(baseBeanList, Scb, Sgb, Sgb2, Sg, Sc, Ss, Sn, quantity - 1, num3);
    }
  }

  public String saveWareManagement()
          throws ParseException
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    String groupCompanySn = session.get("groupCompanySn").toString();
    List<BaseBean> baseBeanList = new ArrayList();
    String hqlfb = "from FinancialBill where financialbillID = ?";
    FinancialBill finbill = (FinancialBill)this.baseBeanService.getBeanByHqlAndParams(hqlfb, new Object[] { this.financialBill.getFinancialbillID() });
    finbill.setBillStatus("15");
    String hqlcash = "from CashierBills where cashierBillsID = ?";
    CashierBills cbills = (CashierBills)this.baseBeanService.getBeanByHqlAndParams(hqlcash, new Object[] { finbill.getCashierBillsID() });
    cbills.setStatus("15");
    baseBeanList.add(finbill);
    baseBeanList.add(cbills);

    String hqlForMan = "from Staff where staffID = ?";
    Staff sta = (Staff)this.baseBeanService.getBeanByHqlAndParams(hqlForMan, new Object[] { account.getStaffID() });
    CashierBills cashierbill = new CashierBills();
    cashierbill.setCashierBillsID(this.serverService.getServerID("cashierTally"));
    cashierbill.setGroupCompanySn(cbills.getGroupCompanySn());
    cashierbill.setCompanyID(cbills.getCompanyID());
    cashierbill.setCompanyName(cbills.getCompanyName());
    cashierbill.setOrganizationID(cbills.getOrganizationID());
    cashierbill.setDepartmentID(cbills.getDepartmentID());
    cashierbill.setDepartmentName(cbills.getDepartmentName());
    cashierbill.setCompanyBankNum(cbills.getCompanyBankNum());
    cashierbill.setPcompanyID(cbills.getPcompanyID());
    cashierbill.setPcompanyName(cbills.getPcompanyName());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = sdf.parse(Utilities.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
    cashierbill.setCashierDate(date);
    cashierbill.setBillsType("采购入库单");
    cashierbill.setJournalNum(this.financialBill.getJournalNum());

    cashierbill.setStaffID(sta.getStaffID());
    String staffhql1 = "from Staff where staffID = ?";
    Staff staff1 = (Staff)this.baseBeanService.getBeanByHqlAndParams(staffhql1, new Object[] { sta.getStaffID() });
    cashierbill.setStaffName(staff1.getStaffName());
    cashierbill.setStaffCode(staff1.getStaffCode());

    cashierbill.setInputid(sta.getStaffID());
    cashierbill.setInputName(sta.getStaffName());
    cashierbill.setInputCompanyid(account.getCompanyID());
    cashierbill.setInputCompanyName(account.getCompanyName());
    String coshql = "from COS where staffID = ?";
    COS cos = (COS)this.baseBeanService.getBeanByHqlAndParams(coshql, new Object[] { account.getStaffID() });
    cashierbill.setInputOrganizationID(cos.getOrganizationID());
    String corhql = "from COrganization where organizationID = ?";
    COrganization cor = (COrganization)this.baseBeanService.getBeanByHqlAndParams(corhql, new Object[] { cos.getOrganizationID() });
    cashierbill.setInputOrganizationName(cor.getOrganizationName());
    if (this.type.equals("save")) {
      cashierbill.setStatus("15");
    } else if (this.type.equals("savecheck")) {
      cashierbill.setStatus("05");
    }
    cashierbill.setProID(cbills.getProID());
    cashierbill.setProjectName(cbills.getProjectName());
    cashierbill.setXmtype(cbills.getXmtype());
    cashierbill.setXmtypename(cbills.getXmtypename());
    cashierbill.setProjectCode(cbills.getProjectCode());
    cashierbill.setContent(cbills.getContent());
    baseBeanList.add(cashierbill);

    //验货单与采购入库单关联
    RelatedBill rb=new RelatedBill();
    rb.setRbID(this.serverService.getServerID("relatedbill"));
    rb.setCashid(cashierbill.getCashierBillsID());//采购入库单id
    rb.setCashfid(cbills.getCashierBillsID());//关联验货单id
    rb.setBilltype("采购入库单");
    baseBeanList.add(rb);

    this.parameter = ("添加采购入库单（凭证号" + this.financialBill.getJournalNum() + "）");
    this.financialBill.setFinancialbillID(this.serverService.getServerID("financial"));
    this.financialBill.setCashierBillsID(cashierbill.getCashierBillsID());
    this.financialBill.setJournalNum(this.financialBill.getJournalNum());
    this.financialBill.setGroupCompanySn(groupCompanySn);
    this.financialBill.setCompanyID(account.getCompanyID());
    this.financialBill.setOrganizationID(organizationID);
    this.financialBill.setDepartmentID(organizationID);
    this.financialBill.setCcompanyID(finbill.getCcompanyID());
    this.financialBill.setCcompanyName(finbill.getCcompanyName());
    this.financialBill.setCcompanyTel(finbill.getCcompanyTel());
    this.financialBill.setFrontMoney(finbill.getFrontMoney());
    this.financialBill.setMoneyType(finbill.getMoneyType());
    this.financialBill.setStaffPhone(finbill.getStaffPhone());
    this.financialBill.setPurchaseDate(this.purchaseDate);
    this.financialBill.setExaminegoodsDate(finbill.getExaminegoodsDate());
    this.financialBill.setSubjectName(finbill.getSubjectName());
    this.financialBill.setSubjectID(finbill.getSubjectID());
    this.financialBill.setPaymentType(finbill.getPaymentType());
    this.financialBill.setLogisticsType(finbill.getLogisticsType());
    this.financialBill.setGoodsmoney(finbill.getGoodsmoney());
    this.financialBill.setDepotID(finbill.getDepotID());
    this.financialBill.setDepotName(finbill.getDepotName());
    this.financialBill.setStaffID(finbill.getStaffID());
    this.financialBill.setStaffName(finbill.getStaffName());
    this.financialBill.setStaffsID(finbill.getStaffsID());
    this.financialBill.setStaffsName(finbill.getStaffsName());
    this.financialBill.setBillsType("采购入库单");
    this.financialBill.setBillsDate(new Date());
    this.financialBill.setBillStaffName(sta.getStaffName());
    this.financialBill.setBillStaffID(account.getStaffID());
    if (this.type.equals("save")) {
      this.financialBill.setBillStatus("15");
    } else if (this.type.equals("savecheck")) {
      this.financialBill.setBillStatus("05");
    }
    baseBeanList.add(this.financialBill);


    String strInventoryID = null;
    String invenQuantity = null;
    if (this.goodsmapold != null) {
      for (GoodsBills good : this.goodsmapold.values()) {
        if ((good.getGoodsBillsID() != null) && (!"".equals(good.getGoodsBillsID())))
        {
          String hql = "from GoodsBills where goodsBillsID = ?";
          GoodsBills finbg = (GoodsBills)this.baseBeanService.getBeanByHqlAndParams(hql, new Object[] { good.getGoodsBillsID() });
          finbg.setKcStatus("15");
          GoodsBills newgood = new GoodsBills();
          newgood.setGoodsBillsID(this.serverService.getServerID("goodsbills"));
          newgood.setCashierBillsID(cashierbill.getCashierBillsID());
          newgood.setCompanyID(finbg.getCompanyID());
          newgood.setGoodsID(finbg.getGoodsID());
          newgood.setGoodsName(finbg.getGoodsName());
          newgood.setGoodsNomber(finbg.getGoodsNomber());
          newgood.setGoodsNum(finbg.getGoodsNum());
          newgood.setGoodsStatus(finbg.getGoodsStatus());
          newgood.setGoodstatus(finbg.getGoodstatus());
          newgood.setGoodsVariableID(finbg.getGoodsVariableID());
          newgood.setIdentifyingCode(finbg.getIdentifyingCode());
          newgood.setIsQualify(finbg.getIsQualify());
          newgood.setKcStatus(finbg.getKcStatus());
          newgood.setMoney(finbg.getMoney());
          newgood.setPrice(finbg.getPrice());
          newgood.setQuantity(finbg.getQuantity());
          newgood.setReQuantity(finbg.getReQuantity());
          newgood.setStandard(finbg.getStandard());
          newgood.setStorageQuantity(finbg.getStorageQuantity());
          newgood.setTypeID(finbg.getTypeID());
          newgood.setCcompanyID(finbg.getCcompanyID());
          newgood.setCcompanyName(finbg.getCcompanyName());
          newgood.setConnectID(good.getConnectID());
          newgood.setConnectName(good.getConnectName());
          baseBeanList.add(newgood);
          baseBeanList.addAll(addlist("采购入库（更新库房物品",strInventoryID,invenQuantity,finbg,newgood,sta));
        }
      }
    }
    if (this.goodsmap != null) {
      for (GoodsBills goods : this.goodsmap.values())
      {
        goods.setGoodsBillsID(this.serverService.getServerID("goodsbills"));
        goods.setCashierBillsID(cashierbill.getCashierBillsID());
        goods.setKcStatus("15");
        if ((goods.getGoodsID() != null) && (!"".equals(goods.getGoodsID())))
        {
          baseBeanList.addAll(addlist("采购入库（更新（附带）库房物品",strInventoryID,invenQuantity,goods,goods,sta));
        }
      }
    }
    CLogBook logBook = this.logBookService.saveCLogBook(organizationID, this.parameter, account);
    baseBeanList.add(logBook);
    this.baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);
    return "success";
  }

  /**
   *
   * @param strInventoryID
   * @param invenQuantity
   * @param finbg
   * @param newgood
   * @param sta
   * @return
   * @throws Exception
   */
  private List<BaseBean> addlist(String Remarks,String strInventoryID ,String invenQuantity,GoodsBills finbg,GoodsBills newgood,Staff sta) throws ParseException{
    List<BaseBean> backList = new ArrayList<BaseBean>();
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    String groupCompanySn = session.get("groupCompanySn").toString();
    String hql1 = " from Inventory where goodsID=? and warehouse=?";
    Inventory inventory = (Inventory)this.baseBeanService.getBeanByHqlAndParams(hql1,
            new Object[] { finbg.getGoodsID(), this.financialBill.getDepotID() });
    if (inventory == null)
    {
      inventory = new Inventory();
      inventory.setInventoryID(this.serverService.getServerID("inventory"));
      inventory.setCompanyID(account.getCompanyID());
      inventory.setGroupCompanySn(groupCompanySn);
      inventory.setOrganizationID(organizationID);
      inventory.setDepartmentID(organizationID);

      inventory.setWarehouse(this.financialBill.getDepotID());
      inventory.setWarehouseName(this.financialBill.getDepotName());

      inventory.setWeizhi(this.financialBill.getDepotName());
      inventory.setGoodsID(finbg.getGoodsID());
      inventory.setGoodsType(finbg.getTypeID());
      inventory.setGoodsName(finbg.getGoodsName());
      inventory.setBarcode(finbg.getSortCode());
      inventory.setStandard(finbg.getStandard());
      inventory.setGoodsCoding(finbg.getGoodsNum());

      inventory.setBadQuantity("0") ;
      inventory.setUnit(finbg.getGoodsVariableID());
      inventory.setUnitPrice(String.valueOf(Double.parseDouble(finbg.getPrice())));
      inventory.setInvenQuantity(finbg.getIsQualify());
      inventory.setGoodstatus("00");

      double a = Double.parseDouble(finbg.getPrice());
      double b = Double.parseDouble(finbg.getIsQualify());
      inventory.setSumPrice(String.valueOf(a * b));
      inventory.setSubjectsID(finbg.getSubjectsID());
      inventory.setSubjectsName(finbg.getSubjectsName());
      this.parameter = ("员工：" + account.getAccountName() + Remarks+" ID：" + inventory.getGoodsID() + "）");
      CLogBook cLogBook = this.logBookService.saveCLogBook(organizationID, this.parameter, account);
      backList.add(cLogBook);
      backList.add(inventory);

      strInventoryID = inventory.getInventoryID();
      invenQuantity = inventory.getInvenQuantity();
    }else{

      String invenQuan="0";
      if(inventory.getInvenQuantity()!=null&&!"".equals(inventory.getInvenQuantity().trim())){
        invenQuan=inventory.getInvenQuantity().trim();
      }
      BigDecimal invenQuant  = new BigDecimal(invenQuan);

      // int invenQuant = Integer.parseInt(inventory.getInvenQuantity());
      String isQualify="0";
      String up="0";
      if(finbg.getIsQualify()!=null&&!"".equals(finbg.getIsQualify().trim())){
        isQualify=finbg.getIsQualify().trim();
      }
      if(finbg.getPrice()!=null&&!"".equals(finbg.getPrice().trim())){
        up=finbg.getPrice().trim();
      }
      BigDecimal isqualify  = new BigDecimal(isQualify);
 //    int isqualify = Integer.parseInt(finbg.getIsQualify());
      BigDecimal unitprice  = new BigDecimal(up);
    //  double unitprice = Double.parseDouble(finbg.getPrice());
      BigDecimal amount  = unitprice.multiply(isqualify);
    //  double amount = unitprice * isqualify;

      String sp="0";
      if(inventory.getSumPrice()!=null&&!"".equals(inventory.getSumPrice().trim())){
        sp=inventory.getSumPrice().trim();
      }
      BigDecimal sumPrice  = new BigDecimal(sp);

      // double sumPrice = Double.parseDouble(inventory.getSumPrice());
      BigDecimal tp = sumPrice.add(amount).setScale(2,BigDecimal.ROUND_DOWN);
      BigDecimal tq = invenQuant.add(isqualify).setScale(2,BigDecimal.ROUND_DOWN);
      BigDecimal averagePrice = null;
      if(tq.compareTo(new BigDecimal(0))==0){
    	   averagePrice = unitprice.add(new BigDecimal(inventory.getUnitPrice())).divide(new BigDecimal(2));
      }else{
           averagePrice = tp.divide(tq,2,BigDecimal.ROUND_DOWN);
      }
     // double averagePrice = (sumPrice + amount) / (invenQuant + isqualify);
      //BigDecimal b = new BigDecimal(averagePrice);
     // double f1 = b.setScale(2, 4).doubleValue();
      BigDecimal f1  = averagePrice.setScale(2,BigDecimal.ROUND_DOWN);

      BigDecimal sum = invenQuant.add(isqualify);
    //  int sum = invenQuant + isqualify;
     // double sumPrice1 = f1 * sum;
      BigDecimal sumPrice1 = f1.multiply(sum);
      finbg.setDepotID(inventory.getInventoryID());
      inventory.setSumPrice(String.valueOf(sumPrice1));
      inventory.setUnitPrice(String.valueOf(f1));
      inventory.setInvenQuantity(String.valueOf(sum));
      this.parameter = ("员工：" + account.getAccountName() + "采购入库（更新库房物品 ID：" + inventory.getGoodsID() + "）");
      CLogBook cLogBook = this.logBookService.saveCLogBook(organizationID, this.parameter, account);
      backList.add(cLogBook);
      backList.add(inventory);
      strInventoryID = inventory.getInventoryID();
      invenQuantity = inventory.getInvenQuantity();
    }
    if(inventory.getProductId()==null||"".equals(inventory.getProductId())){
      String pphql1 = " from ProductPackaging where goodsID=? and parentid is null";
      ProductPackaging propack = (ProductPackaging)baseBeanService.getBeanByHqlAndParams(pphql1,
              new Object[] { finbg.getGoodsID()});
      if(propack!=null){
        inventory.setProductId(propack.getPpID());
      }
    }

    backList.add(finbg);

    stockInv stockinvs = new stockInv();
    stockinvs.setStockinvID(this.serverService.getServerID("stockInv"));
    stockinvs.setCompanyID(account.getCompanyID());
    stockinvs.setGroupCompanySn(groupCompanySn);
    stockinvs.setGoodsID(finbg.getGoodsID());
    stockinvs.setGoodsType(finbg.getTypeID());
    stockinvs.setGoodsName(finbg.getGoodsName());
    stockinvs.setInvenQuantity(finbg.getIsQualify());


    stockinvs.setWarehouse(this.financialBill.getDepotID());
    stockinvs.setWarehouseName(this.financialBill.getDepotName());
    stockinvs.setStaffID(sta.getStaffID());
    stockinvs.setStaffName(sta.getStaffName());
    //double unitPrice = Double.parseDouble(finbg.getPrice());
    String gp="0";
    if(finbg.getPrice()!=null&&!"".equals(finbg.getPrice().trim())){
      gp=finbg.getPrice().trim();
    }
    BigDecimal unitPrice  = new BigDecimal(gp);
    if(invenQuantity==null||"".equals(invenQuantity.trim())){
      invenQuantity="0";
    }
    BigDecimal invenQuantitys  = new BigDecimal(invenQuantity);

    stockinvs.setSummoney(String.valueOf(invenQuantitys.multiply(unitPrice)));
    stockinvs.setIntime(new Date());
    stockinvs.setType("00");
    backList.add(stockinvs);
    GoodsManage gd = (GoodsManage) baseBeanService.getBeanByHqlAndParams("from GoodsManage where goodsID = ?",new Object[]{newgood.getGoodsID()});
    if(gd!=null&&!"0".equals(gd.getIsScale())){
      String[] str={"GoodsNum",account.getCompanyID(),strInventoryID,newgood.getGoodsBillsID(),newgood.getGoodsID(),"03",newgood.getQuantity(),newgood.getGoodsNum()};
      //warehouseService.numberOfGeneratedItems(str);
    }

    return backList;
  }
  
  public String updateWareManagement()
    throws ParseException
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    List<BaseBean> baseBeanList = new ArrayList();
    
    String hqlfb = "from FinancialBill where financialbillID = ?";
    FinancialBill finbill = (FinancialBill)this.baseBeanService.getBeanByHqlAndParams(hqlfb, new Object[] { this.financialBill.getFinancialbillID() });
    finbill.setBillStatus("15");
    String hqlcash = "from CashierBills where cashierBillsID = ?";
    CashierBills cbills = (CashierBills)this.baseBeanService.getBeanByHqlAndParams(hqlcash, new Object[] { finbill.getCashierBillsID() });
    cbills.setStatus("15");
    baseBeanList.add(finbill);
    baseBeanList.add(cbills);
    
    String hqlg = "from GoodsBills where cashierBillsID = ?";
    Object[] par = { finbill.getCashierBillsID() };
    this.billsgoodlist = this.baseBeanService.getListBeanByHqlAndParams(hqlg, par);
    for (int i = 0; i < this.billsgoodlist.size(); i++)
    {
      GoodsBills finbg = new GoodsBills();
      finbg = (GoodsBills)this.billsgoodlist.get(i);
      finbg.setKcStatus("15");
      baseBeanList.add(finbg);
    }
    this.parameter = ("采购入库单确认入库（凭证号" + this.financialBill.getJournalNum() + "）");
    CLogBook logBook = this.logBookService.saveCLogBook(organizationID, this.parameter, account);
    baseBeanList.add(logBook);
    this.baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);
    this.financialBill.setBillsType("采购入库单");
    ActionContext.getContext().getSession().put("financialBill", 
      this.financialBill);
    this.search = "search";
    return toExamineGoodsBillList();
  }
  
  public InputStream getExcelStream()
  {
    return this.excelStream;
  }
  
  public void setExcelStream(InputStream excelStream)
  {
    this.excelStream = excelStream;
  }
  
  public FinancialBill getFinancialBill()
  {
    return this.financialBill;
  }
  
  public void setFinancialBill(FinancialBill financialBill)
  {
    this.financialBill = financialBill;
  }
  
  public FinancialBillsGood getFinancialBillsGood()
  {
    return this.financialBillsGood;
  }
  
  public void setFinancialBillsGood(FinancialBillsGood financialBillsGood)
  {
    this.financialBillsGood = financialBillsGood;
  }
  
  public CashierBills getCashierBills()
  {
    return this.cashierBills;
  }
  
  public void setCashierBills(CashierBills cashierBills)
  {
    this.cashierBills = cashierBills;
  }
  
  public PageForm getPageForm()
  {
    return this.pageForm;
  }
  
  public void setPageForm(PageForm pageForm)
  {
    this.pageForm = pageForm;
  }
  
  public String getParameter()
  {
    return this.parameter;
  }
  
  public void setParameter(String parameter)
  {
    this.parameter = parameter;
  }
  
  public int getPageNumber()
  {
    return this.pageNumber;
  }
  
  public void setPageNumber(int pageNumber)
  {
    this.pageNumber = pageNumber;
  }
  
  public String getSearch()
  {
    return this.search;
  }
  
  public void setSearch(String search)
  {
    this.search = search;
  }
  
  public Map<String, GoodsBills> getGoodsmap()
  {
    return this.goodsmap;
  }
  
  public void setGoodsmap(Map<String, GoodsBills> goodsmap)
  {
    this.goodsmap = goodsmap;
  }
  
  public Map<String, GoodsBills> getGoodsmapold()
  {
    return this.goodsmapold;
  }
  
  public void setGoodsmapold(Map<String, GoodsBills> goodsmapold)
  {
    this.goodsmapold = goodsmapold;
  }
  
  public List<CCode> getPayTypeList()
  {
    return this.payTypeList;
  }
  
  public void setPayTypeList(List<CCode> payTypeList)
  {
    this.payTypeList = payTypeList;
  }
  
  public List<CCode> getLogisticsList()
  {
    return this.logisticsList;
  }
  
  public void setLogisticsList(List<CCode> logisticsList)
  {
    this.logisticsList = logisticsList;
  }
  
  public List<CCode> getConnectionlist()
  {
    return this.connectionlist;
  }
  
  public void setConnectionlist(List<CCode> connectionlist)
  {
    this.connectionlist = connectionlist;
  }
  
  public List<CCode> getCodeRelationList()
  {
    return this.codeRelationList;
  }
  
  public void setCodeRelationList(List<CCode> codeRelationList)
  {
    this.codeRelationList = codeRelationList;
  }
  
  public String getCompanyname()
  {
    return this.companyname;
  }
  
  public void setCompanyname(String companyname)
  {
    this.companyname = companyname;
  }
  
  public String getOrganizationname()
  {
    return this.organizationname;
  }
  
  public void setOrganizationname(String organizationname)
  {
    this.organizationname = organizationname;
  }
  
  public List<BaseBean> getWarehouseList()
  {
    return this.warehouseList;
  }
  
  public void setWarehouseList(List<BaseBean> warehouseList)
  {
    this.warehouseList = warehouseList;
  }
  
  public List<FinancialBillsGood> getBillsGoodList()
  {
    return this.BillsGoodList;
  }
  
  public void setBillsGoodList(List<FinancialBillsGood> billsGoodList)
  {
    this.BillsGoodList = billsGoodList;
  }
  
  public ContactUser getContactUser1()
  {
    return this.contactUser1;
  }
  
  public void setContactUser1(ContactUser contactUser1)
  {
    this.contactUser1 = contactUser1;
  }
  
  public ContactCompanyView getContactCompanyView1()
  {
    return this.contactCompanyView1;
  }
  
  public void setContactCompanyView1(ContactCompanyView contactCompanyView1)
  {
    this.contactCompanyView1 = contactCompanyView1;
  }
  
  public Inventory getInventoryParam()
  {
    return this.inventoryParam;
  }
  
  public void setInventoryParam(Inventory inventoryParam)
  {
    this.inventoryParam = inventoryParam;
  }
  
  public String getSdate()
  {
    return this.sdate;
  }
  
  public void setSdate(String sdate)
  {
    this.sdate = sdate;
  }
  
  public String getEdate()
  {
    return this.edate;
  }
  
  public void setEdate(String edate)
  {
    this.edate = edate;
  }
  
  public String getPrint()
  {
    return this.print;
  }
  
  public void setPrint(String print)
  {
    this.print = print;
  }
  
  public List<BaseBean> getStafflist()
  {
    return this.stafflist;
  }
  
  public void setStafflist(List<BaseBean> stafflist)
  {
    this.stafflist = stafflist;
  }
  
  public stockInv getStockinv()
  {
    return this.stockinv;
  }
  
  public void setStockinv(stockInv stockinv)
  {
    this.stockinv = stockinv;
  }
  
  public List<CCode> getTypelist()
  {
    return this.typelist;
  }
  
  public void setTypelist(List<CCode> typelist)
  {
    this.typelist = typelist;
  }
  
  public int getCamount()
  {
    return this.camount;
  }
  
  public void setCamount(int camount)
  {
    this.camount = camount;
  }
  
  public ContactCompany getContactCompanyView()
  {
    return this.contactCompanyView;
  }
  
  public void setContactCompanyView(ContactCompany contactCompanyView)
  {
    this.contactCompanyView = contactCompanyView;
  }
  
  public List<BaseBean> getBillsgoodlist()
  {
    return this.billsgoodlist;
  }
  
  public void setBillsgoodlist(List<BaseBean> billsgoodlist)
  {
    this.billsgoodlist = billsgoodlist;
  }
  
  public CashierBillsVO getCashierBillsVO()
  {
    return this.cashierBillsVO;
  }
  
  public void setCashierBillsVO(CashierBillsVO cashierBillsVO)
  {
    this.cashierBillsVO = cashierBillsVO;
  }
  
  public String getResult()
  {
    return this.result;
  }
  
  public void setResult(String result)
  {
    this.result = result;
  }
  
  public List<BaseBean> getBillCheckList()
  {
    return this.BillCheckList;
  }
  
  public void setBillCheckList(List<BaseBean> billCheckList)
  {
    this.BillCheckList = billCheckList;
  }
  
  public String getPurchaseDate()
  {
    return this.purchaseDate;
  }
  
  public void setPurchaseDate(String purchaseDate)
  {
    this.purchaseDate = purchaseDate;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public int getLoginstaffcheck()
  {
    return this.loginstaffcheck;
  }
  
  public void setLoginstaffcheck(int loginstaffcheck)
  {
    this.loginstaffcheck = loginstaffcheck;
  }
  
  public Map<String, String> getBillcheckmap()
  {
    return this.billcheckmap;
  }
  
  public void setBillcheckmap(Map<String, String> billcheckmap)
  {
    this.billcheckmap = billcheckmap;
  }
  
  public String getDeptpost()
  {
    return this.deptpost;
  }
  
  public void setDeptpost(String deptpost)
  {
    this.deptpost = deptpost;
  }
  
  public String getRemarks()
  {
    return this.remarks;
  }
  
  public void setRemarks(String remarks)
  {
    this.remarks = remarks;
  }
  
  public String getXmtype()
  {
    return this.xmtype;
  }
  
  public void setXmtype(String xmtype)
  {
    this.xmtype = xmtype;
  }
}
