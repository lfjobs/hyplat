package hy.ea.invoicing.action;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.company.vo.ContactCompanyView;
import hy.ea.bo.company.vo.ContactUser;
import hy.ea.bo.finance.BillCheck;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.GoodsNum;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.FinancialBillsGood;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.ea.util.VOtool;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class InvoicingWarehousingNewAction
{
  @Resource
  private BaseBeanService baseBeanService;
  @Resource
  private CLogBookService logBookService;
  @Resource
  private CCodeService codeService;
  @Resource
  private ServerService serverService;
  @Resource
  private ShowExcelService excelService;
  public InputStream excelStream;
  private CashierBills cashierBills;
  private FinancialBill financialBill;
  private FinancialBillsGood financialBillsGood;
  private GoodsBills fBillsGood;
  private PageForm pageForm;
  private String parameter;
  private String depotid;
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
  private String kutime;
  private stockInv stockinv;
  private Object[] objectny;
  List<Object[]> lists;
  private String zongID;
  private String arrgoodsNum;
  private List<BaseBean> warehouseList;
  private List<BaseBean> billgoodlist;
  private List<GoodsBills> BillsGoodList;
  private String message;
  private ContactUser contactUser1;
  private ContactCompanyView contactCompanyView1;
  private Inventory inventoryParam;
  private stockInv stockinvParam;
  private String sdate;
  private String edate;
  private String state;
  private String one;
  private String print;
  private double camount;
  private String result;
  private String typeID;
  private String subjectsID;
  private String subtype;
  private String deptpost;
  private String remarks;
  private int loginstaffcheck;
  private List<BaseBean> stafflist;
  private String billStatus;
  private CashierBillsVO cashierBillsVO;
  private List<CCode> billsType;
  private String type;
  private List<BaseBean> BillCheckList;
  
  public String getcashierBillstatus()
    throws UnsupportedEncodingException
  {
    CAccount account = 
      (CAccount)ActionContext.getContext().getSession().get("account");
    if (account == null)
    {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("nologin", Integer.valueOf(1));
      JSONObject obj = JSONObject.fromObject(map);
      this.result = obj.toString();
      return "success";
    }
    Object[] params = { this.cashierBillsVO.getCashierBillsID() };
    String hql = "from CashierBills where cashierBillsID= ?";
    CashierBills cashier = (CashierBills)this.baseBeanService.getBeanByHqlAndParams(
      hql, params);
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("getstatus", cashier.getStatus());
    JSONObject obj = JSONObject.fromObject(map);
    this.result = obj.toString();
    return "success";
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
    if ("03".equals(this.type))
    {
      String[] hql = { "update CashierBills set status=10 where cashierBillsID=?" };
      List<Object[]> list = new ArrayList();
      list.add(new Object[] { this.cashierBills.getCashierBillsID() });
      this.baseBeanService.executeHqlsByParamsList(null, hql, list);
    }

    CashierBills cbills=null;
    String hqlcash = "from CashierBills where cashierBillsID = ?";
    if(this.cashierBills!=null){
       cbills = (CashierBills)this.baseBeanService.getBeanByHqlAndParams(hqlcash,
              new Object[] { this.cashierBills.getCashierBillsID() });
    }else{
      String hqlfb = "from FinancialBill where financialbillID=?";
      this.financialBill = ((FinancialBill)this.baseBeanService.getBeanByHqlAndParams(hqlfb, new Object[] {this.financialBill.getFinancialbillID()}));
      cbills = (CashierBills)this.baseBeanService.getBeanByHqlAndParams(hqlcash,
              new Object[] { this.financialBill.getCashierBillsID() });
      this.cashierBills=cbills;
    }


    
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
    billcheck.setAuditorstatus("03".equals(this.type) ? "03" : "02");
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
  
  public String addWareManagement()
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
    String companyID = account.getCompanyID();
    
    this.payTypeList = this.codeService.getCCodeListByPID(companyID,
      "scode20101101dfs3uhdprp0000000031");
    this.logisticsList = this.codeService.getCCodeListByPID(companyID, 
      "scode20110106hfjes5ucxp0000000021");
    
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
    if (this.cashierBills != null)
    {
      String hql = "from CashierBills where groupCompanySn = ? and companyID = ? and organizationID=? and cashierBillsID=?";
      
      this.cashierBills = ((CashierBills)this.baseBeanService.getBeanByHqlAndParams(hql, 
        new Object[] { groupCompanySn, account.getCompanyID(), 
        organizationID, this.cashierBills.getCashierBillsID() }));
      
      String hqlf = "from FinancialBill where cashierBillsID=?";
      this.financialBill = ((FinancialBill)this.baseBeanService.getBeanByHqlAndParams(hqlf, 
        new Object[] { this.cashierBills.getCashierBillsID() }));
      String hqlg = "from GoodsBills where cashierBillsID=? order by goodsNomber";
      Object[] param = { this.cashierBills.getCashierBillsID() };
      this.billgoodlist = this.baseBeanService.getListBeanByHqlAndParams(hqlg, param);
      this.type = "edit";
      
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
    }
    else
    {
      this.type = "add";
    }
    return "wareadd";
  }
  
  public String getInventoryWithGoodsNum()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    if (account == null)
    {
      Map<String, Object> map = new HashMap();
      map.put("nologin", Integer.valueOf(1));
      JSONObject obj = JSONObject.fromObject(map);
      this.result = obj.toString();
      return "success";
    }
    String companyid = account.getCompanyID();
    String sn = session.get("groupCompanySn").toString();
    String sql = "select dgs.cashierbillsid,invt.warehouseName,invt.goodsName,decode(invt.barCode,NULL, ' ',invt.barcode) as barCode,invt.goodscoding,invt.goodsType,1/*invt.invenQuantity*/,decode(invt.unit,NULL,' ',invt.unit) as unit,invt.goodsID,dgs.gnID,dgs.goodsNumber,invt.subjectsName,invt.subjectsID,invt.warehouse,invt.standard,invt.unitPrice from dtgoodsnum dgs left join dt_inv_invt invt on dgs.cashierBillsID = invt.inventoryID where dgs.status<'04' and dgs.companyid = ? and invt.groupCompanySn = ? and invt.warehouse=?";
    if (!StringUtils.isBlank(this.parameter.trim())) {
      sql = sql + " and (invt.barcode = ? or invt.goodscoding = ? or invt.goodsname = ?) ";
    }
    sql = sql + " order by dgs.goodsNumber ";
    List<BaseBean> list = null;
    if (!StringUtils.isBlank(this.parameter.trim())) {
      
    	list = this.baseBeanService.getListBeanBySqlAndParams(sql, new Object[] { companyid, sn, this.depotid, this.parameter, this.parameter,this.parameter });
    } else {
      list = this.baseBeanService.getListBeanBySqlAndParams(sql, new Object[] { companyid, sn, this.depotid });
    }
    Map<String, Object> map = new HashMap();
    map.put("goodlist", list);
    JSONObject oj = JSONObject.fromObject(map);
    this.result = oj.toString();
    return "success";
  }
  
  public String getWareManagementList()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus=? and c.organizationID=? )";
    this.stafflist = this.baseBeanService.getListBeanByHqlAndParams(staffhql, 
      new Object[] { account.getCompanyID(), "50", organizationID });
    
    this.connectionlist = this.codeService.getCCodeListByPID(account.getCompanyID(), 
      "scode20110224xpd2t2jvda0000000002");
    
    this.codeRelationList = this.codeService.getCCodeListByPID(
      account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
    List<Object> list = getlists();
    String hql = (String)list.get(0);
    Object[] parms = (Object[])list.get(1);
    this.pageForm = this.baseBeanService.getPageForm(
      this.pageForm != null ? this.pageForm.getPageNumber() : 1, 
      this.pageNumber == 0 ? 10 : this.pageNumber, hql, parms);
    















    return "warelist";
  }
  
  private List<Object> getlists()
  {
    List<Object> result = new ArrayList();
    List<Object> parms = new ArrayList();
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    String groupCompanySn = session.get("groupCompanySn").toString();
    String Hql = " from CashierBillsVO ";
    Hql = Hql + " where 1=1 ";
    Hql = Hql + " and groupCompanySn = ? ";
    parms.add(groupCompanySn);
    Hql = Hql + " and companyid = ? ";
    parms.add(account.getCompanyID());
    Hql = Hql + " and organizationid = ? ";
    parms.add(organizationID);
    if ((this.search != null) && (this.search.equals("search")))
    {
      this.cashierBillsVO = ((CashierBillsVO)session.get("cashierBillsVO"));
      if (this.cashierBillsVO != null)
      {
        if ((this.cashierBillsVO.getJournalNum().trim() != null) && 
          (!"".equals(this.cashierBillsVO.getJournalNum().trim())))
        {
          Hql = Hql + " and journalNum like ? ";
          parms.add("%" + this.cashierBillsVO.getJournalNum().trim() + "%");
        }
        if ((this.cashierBillsVO.getStaffname().trim() != null) && 
          (!"".equals(this.cashierBillsVO.getStaffname().trim())))
        {
          Hql = Hql + " and staffname = ?";
          parms.add("%" + this.cashierBillsVO.getStaffname().trim() + "%");
        }
        if ((this.sdate != null) && (this.edate != null) && (!"".equals(this.sdate)) && 
          (!"".equals(this.edate)))
        {
          Hql = Hql + " and cashierDate between ? and ? ";
          
          parms.add(Utilities.getDateFromString(this.sdate + " 00:00:00", 
            "yyyy-MM-dd hh:mm:ss"));
          
          parms.add(Utilities.getDateFromString(this.edate + " 23:59:59", 
            "yyyy-MM-dd hh:mm:ss"));
        }
        if ((this.cashierBillsVO.getStatus().trim() != null) && 
          (!"".equals(this.cashierBillsVO.getStatus().trim())))
        {
          Hql = Hql + " and status like ? ";
          parms.add(this.cashierBillsVO.getStatus().trim());
        }
      }
    }
    else
    {
      Hql = Hql + " and (status = ?";
      parms.add("05");
      Hql = Hql + " or status = ?";
      parms.add("22");
      Hql = Hql + " or status = ?)";
      parms.add("16");
    }
    Hql = Hql + " and billsType = ?";
    parms.add("销售出库单");
    Hql = Hql + " and statusbill = ?";
    parms.add("01");
    Hql = Hql + " order by cashierDate desc";
    result.add(Hql);
    result.add(parms.toArray());
    return result;
  }
  
  public String saveWareManagement()
    throws ParseException
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    String corhql = "from COrganization where organizationID = ?";
    COrganization corgan = (COrganization)this.baseBeanService.getBeanByHqlAndParams(corhql, new Object[] { organizationID });
    String groupCompanySn = session.get("groupCompanySn").toString();
    String hqlForMan = "from Staff where staffID = ?";
    Staff sta = (Staff)this.baseBeanService.getBeanByHqlAndParams(hqlForMan, 
      new Object[] { account.getStaffID() });
    List<BaseBean> baseBeanList = new ArrayList();
    
    CashierBills cashierbill = new CashierBills();
    cashierbill.setCashierBillsID(this.serverService.getServerID("cashierTally"));
    cashierbill.setGroupCompanySn(groupCompanySn);
    cashierbill.setCompanyID(account.getCompanyID());
    cashierbill.setCompanyName(account.getCompanyName());
    cashierbill.setOrganizationID(organizationID);
    cashierbill.setDepartmentID(organizationID);
    cashierbill.setDepartmentName(corgan.getOrganizationName());
    cashierbill.setCompanyBankNum("");
    cashierbill.setPcompanyID(account.getCompanyID());
    cashierbill.setPcompanyName(account.getCompanyName());
    cashierbill.setAfterDiscount(this.financialBill.getDepotName());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = sdf.parse(Utilities.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
    cashierbill.setCashierDate(date);
    cashierbill.setBillsType("销售出库单");
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
    COrganization cor = (COrganization)this.baseBeanService.getBeanByHqlAndParams(corhql, new Object[] { cos.getOrganizationID() });
    cashierbill.setInputOrganizationName(cor.getOrganizationName());
    if (this.subtype.equals("save")) {
      cashierbill.setStatus("22");
    } else if (this.subtype.equals("savecheck")) {
      cashierbill.setStatus("05");
    }
    cashierbill.setStatusbill("01");
    







    baseBeanList.add(cashierbill);
    
    this.financialBill.setCashierBillsID(cashierbill.getCashierBillsID());
    this.financialBill.setFinancialbillID(this.serverService.getServerID("financial"));
    this.parameter = ("添加销售出库单（凭证号" + this.financialBill.getJournalNum() + "）");
    this.financialBill.setOrganizationID(organizationID);
    this.financialBill.setCompanyID(account.getCompanyID());
    this.financialBill.setGroupCompanySn(groupCompanySn);
    this.financialBill.setBillsType("销售出库单");
    if (this.subtype.equals("save")) {
      this.financialBill.setBillStatus("22");
    } else if (this.subtype.equals("savecheck")) {
      this.financialBill.setBillStatus("05");
    }
    this.financialBill.setBillsDate(new Date());
    this.financialBill.setBillStaffName(sta.getStaffName());
    this.financialBill.setBillStaffID(account.getStaffID());
    ContactCompanyView contactCompanyView1;
    if ((this.financialBill.getCcompanyID() != null) && 
      (!"".equals(this.financialBill.getCcompanyID())))
    {
      String hql3 = " from ContactCompanyView where ccompanyID=? ";
      contactCompanyView1 = (ContactCompanyView)this.baseBeanService
        .getBeanByHqlAndParams(hql3, new Object[] {this.financialBill
        .getCcompanyID() });
      this.financialBill.setCcompanyName(contactCompanyView1.getCompanyName());
      this.financialBill.setCcompanyRelationship(contactCompanyView1
        .getContactConnections());
      this.financialBill.setCcompanyTel(contactCompanyView1.getCompanyTel());
    }
    baseBeanList.add(this.financialBill);
    if (this.goodsmap != null)
    {
      for (GoodsBills goods : this.goodsmap.values())
      {
        goods.setCashierBillsID(cashierbill.getCashierBillsID());
        goods.setGoodsBillsID(this.serverService.getServerID("goodsbills"));
        goods.setCompanyID(account.getCompanyID());
        String hql = "from GoodsManage g where g.goodsID=?";
        GoodsManage goodsManage = (GoodsManage)this.baseBeanService
          .getBeanByHqlAndParams(hql, new Object[] {goods
          .getGoodsID() });
        goods.setGoodsName(goodsManage.getGoodsName());
        goods.setGoodsNum(goodsManage.getGoodsCoding());
        goods.setStandard(goodsManage.getMnemonicCode());
        goods.setTypeID(goodsManage.getTypeID());
        if (this.subtype.equals("save")) {
          goods.setKcStatus("22");
        } else if (this.subtype.equals("savecheck")) {
          goods.setKcStatus("05");
        }
        goods.setGoodstatus("00");
        if ((goods.getGoodsID() != null) && (!"".equals(goods.getGoodsID())))
        {
          Inventory inventory = new Inventory();
          if ((goods.getDepotName() != null) && (!"".equals(goods.getDepotName())))
          {
            String hql1 = " from Inventory where inventoryID=? and companyID=? and weizhi=?";
            inventory = (Inventory)this.baseBeanService
              .getBeanByHqlAndParams(hql1, new Object[] { goods.getInventoryID(), account.getCompanyID(), goods.getDepotName() });
          }
          else
          {
            String hql1 = " from Inventory where inventoryID=? and companyID=? and weizhi is null ";
            inventory = (Inventory)this.baseBeanService
              .getBeanByHqlAndParams(hql1, 
              new Object[] { goods.getInventoryID(), account.getCompanyID() });
          }
          if (inventory == null)
          {
            this.message = 
              ("所选库房中没有所填物品  " + goodsManage.getGoodsName() + "!  请核对信息...");
            return "failed";
          }
          int invenQuant = Integer.parseInt(inventory.getInvenQuantity());
          int quantity = Integer.parseInt(goods.getQuantity());
          if (invenQuant >= quantity)
          {
            goods.setInventoryID(inventory.getInventoryID());
          }
          else
          {
            this.message = 
            
              ("所选出库物品 " + goodsManage.getGoodsName() + " 的数量不足 " + quantity + ", 库存数量为 " + invenQuant);
            return "failed";
          }
          baseBeanList.add(goods);
        }
      }

      dd(baseBeanList, this.goodsmap, this.arrgoodsNum);
    }
    CLogBook logBook = this.logBookService.saveCLogBook(organizationID, 
      this.parameter, account);
    baseBeanList.add(logBook);
    this.baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);
    return "success";
  }
  
  public String editWareManagement()
    throws ParseException
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    String groupCompanySn = session.get("groupCompanySn").toString();
    if (groupCompanySn == null) {
      return "login";
    }
    List<BaseBean> baseBeanList = new ArrayList();
    
    String hqlcashb = "from CashierBills where cashierBillsID = ?";
    CashierBills cashierbills = (CashierBills)this.baseBeanService.getBeanByHqlAndParams(hqlcashb, new Object[] { this.cashierBills.getCashierBillsID() });
    if (this.subtype.equals("save")) {
      cashierbills.setStatus("22");
    } else if (this.subtype.equals("savecheck")) {
      cashierbills.setStatus("05");
    }
    baseBeanList.add(cashierbills);
    
    String hqlcash = "from FinancialBill where cashierBillsID = ?";
    FinancialBill fiBill = (FinancialBill)this.baseBeanService.getBeanByHqlAndParams(hqlcash, new Object[] { this.cashierBills.getCashierBillsID() });
    this.parameter = ("修改销售出库单据（单据编号" + fiBill.getJournalNum() + "）");
    fiBill.setCcompanyID(this.financialBill.getCcompanyID());
    fiBill.setCcompanyName(this.financialBill.getCcompanyName());
    fiBill.setCcompanyTel(this.financialBill.getCcompanyTel());
    fiBill.setFrontMoney(this.financialBill.getFrontMoney());
    fiBill.setMoneyType(this.financialBill.getMoneyType());
    fiBill.setStaffID(this.financialBill.getStaffID());
    fiBill.setStaffName(this.financialBill.getStaffName());
    fiBill.setStaffPhone(this.financialBill.getStaffPhone());
    fiBill.setPaymentType(this.financialBill.getPaymentType());
    fiBill.setPurchaseDate(this.financialBill.getPurchaseDate());
    fiBill.setSubjectID(this.financialBill.getSubjectID());
    fiBill.setSubjectName(this.financialBill.getSubjectName());
    fiBill.setLogisticsType(this.financialBill.getLogisticsType());
    fiBill.setDepotID(this.financialBill.getDepotID());
    fiBill.setDepotName(this.financialBill.getDepotName());
    fiBill.setStaffsID(this.financialBill.getStaffsID());
    fiBill.setStaffsName(this.financialBill.getStaffsName());
    fiBill.setGoodsmoney(this.financialBill.getGoodsmoney());
    if (this.subtype.equals("save")) {
      fiBill.setBillStatus("22");
    } else if (this.subtype.equals("savecheck")) {
      fiBill.setBillStatus("05");
    }
    baseBeanList.add(fiBill);
    if (this.goodsmapold != null) {
      for (GoodsBills goods : this.goodsmapold.values())
      {
        String hqlgb = "from GoodsBills where goodsBillsID = ?";
        GoodsBills goodsbills = (GoodsBills)this.baseBeanService.getBeanByHqlAndParams(hqlgb, 
          new Object[] { goods.getGoodsBillsID() });
        goodsbills.setGoodsNomber(goods.getGoodsNomber());
        String hql = "from GoodsManage g where g.goodsID=?";
        GoodsManage goodsManage = (GoodsManage)this.baseBeanService
          .getBeanByHqlAndParams(hql, new Object[] {goodsbills
          .getGoodsID() });
        goodsbills.setGoodsName(goodsManage.getGoodsName());
        goodsbills.setGoodsNum(goodsManage.getGoodsCoding());
        goodsbills.setStandard(goodsManage.getMnemonicCode());
        goodsbills.setTypeID(goodsManage.getTypeID());
        if (this.subtype.equals("save")) {
          goodsbills.setKcStatus("22");
        } else if (this.subtype.equals("savecheck")) {
          goodsbills.setKcStatus("05");
        }
        goodsbills.setGoodstatus("00");
        
        String hqlToo = "from Inventory where goodsID=? and warehouse=?";
        Object[] objToo = { goodsbills.getGoodsID(), this.financialBill.getDepotID() };
        Inventory inv = (Inventory)this.baseBeanService.getBeanByHqlAndParams(hqlToo, objToo);
        if (inv != null)
        {
          goodsbills.setInventoryID(inv.getInventoryID());
          goodsbills.setDepotID(this.financialBill.getDepotID());
        }
        if ((goodsbills.getGoodsID() != null) && (!"".equals(goodsbills.getGoodsID())))
        {
          Inventory inventory = new Inventory();
          
          String hql1 = " from Inventory where inventoryID=? and companyID=? and warehouse=? ";
          inventory = (Inventory)this.baseBeanService
            .getBeanByHqlAndParams(hql1, 
            new Object[] { goodsbills.getInventoryID(), account.getCompanyID(), goodsbills.getDepotID() });
          if (inventory == null)
          {
            this.message = 
              ("所选的物品中对应库房中不存在" + goodsManage.getGoodsName() + "!  请核对信息...");
            return "failed";
          }
          int invenQuant = Integer.parseInt(inventory.getInvenQuantity());
          int quantity = Integer.parseInt(goodsbills.getQuantity());
          if (invenQuant >= quantity)
          {
            goodsbills.setInventoryID(inventory.getInventoryID());
          }
          else
          {
            this.message = 
            
              ("所选出库物品 " + goodsManage.getGoodsName() + " 的数量不足 " + quantity + ", 库存数量为 " + invenQuant);
            return "failed";
          }
          baseBeanList.add(goodsbills);
        }
      }
    }
    if (this.goodsmap != null)
    {
      for (GoodsBills goods : this.goodsmap.values())
      {
        goods.setCashierBillsID(this.cashierBills.getCashierBillsID());
        goods.setGoodsBillsID(this.serverService.getServerID("goodsbills"));
        goods.setCompanyID(account.getCompanyID());
        String hql = "from GoodsManage g where g.goodsID=?";
        GoodsManage goodsManage = (GoodsManage)this.baseBeanService
          .getBeanByHqlAndParams(hql, new Object[] {goods
          .getGoodsID() });
        goods.setGoodsName(goodsManage.getGoodsName());
        goods.setGoodsNum(goodsManage.getGoodsCoding());
        goods.setStandard(goodsManage.getMnemonicCode());
        goods.setTypeID(goodsManage.getTypeID());
        if (this.subtype.equals("save")) {
          goods.setKcStatus("22");
        } else if (this.subtype.equals("savecheck")) {
          goods.setKcStatus("05");
        }
        goods.setGoodstatus("00");
        if ((goods.getGoodsID() != null) && (!"".equals(goods.getGoodsID())))
        {
          Inventory inventory = new Inventory();
          if ((goods.getDepotName() != null) && (!"".equals(goods.getDepotName())))
          {
            String hql1 = " from Inventory where inventoryID=? and weizhi=? ";
            inventory = (Inventory)this.baseBeanService
              .getBeanByHqlAndParams(hql1, new Object[] { goods.getInventoryID(), goods.getDepotName() });
          }
          else
          {
            String hql1 = " from Inventory where inventoryID=? and warehouse=? ";
            inventory = (Inventory)this.baseBeanService
              .getBeanByHqlAndParams(hql1, 
              new Object[] { goods.getInventoryID() });
          }
          if (inventory == null)
          {
            this.message = 
              ("所选的物品中对应库房中不存在" + goodsManage.getGoodsName() + "!  请核对信息...");
            return "failed";
          }
          int invenQuant = Integer.parseInt(inventory.getInvenQuantity());
          int quantity = Integer.parseInt(goods.getQuantity());
          if (invenQuant >= quantity)
          {
            goods.setInventoryID(inventory.getInventoryID());
          }
          else
          {
            this.message = 
            
               ("所选出库物品 " + goodsManage.getGoodsName() + " 的数量不足 " + quantity + ", 库存数量为 " + invenQuant);
            return "failed";
          }
          baseBeanList.add(goods);
        }
      }
      dd(baseBeanList, this.goodsmap, this.arrgoodsNum);
    }
    CLogBook logBook = this.logBookService.saveCLogBook(organizationID, this.parameter, account);
    baseBeanList.add(logBook);
    this.baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);
    return "success";
  }
  
  private void dd(List<BaseBean> baseBeanList, Map<String, GoodsBills> goodsmap, String arrGoodsNum)
  {
    int i = 0;
    String[] arr = arrGoodsNum.split(",");
    for (GoodsBills goods : goodsmap.values())
    {
      String goodsId = goods.getGoodsID();
      String inventoryId = goods.getInventoryID();
      String hql = " from GoodsNum where cashierBillsID = ? and goodsID = ? and goodsnumber = ?";
      GoodsNum goodsNum = (GoodsNum)this.baseBeanService.getBeanByHqlAndParams(hql, new Object[] { inventoryId, goodsId, arr[i] });
      goodsNum.setStatus("07");
      baseBeanList.add(goodsNum);
      i++;
    }
  }
  
  public String toWareManagementCashierBills()
  {
    String hqls = "from CashierBills where  journalNum=?";
    this.cashierBills = 
      ((CashierBills)this.baseBeanService.getBeanByHqlAndParams(hqls, new Object[] { this.cashierBills.getJournalNum() }));
    return toWareManagement();
  }
  
  public String toWareManagement()
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
    
    String hqls = "from CashierBills where  cashierBillsID=?";
    this.cashierBills = 
      ((CashierBills)this.baseBeanService.getBeanByHqlAndParams(hqls, new Object[] { this.cashierBills.getCashierBillsID() }));
    
    String hql = "from FinancialBill where groupCompanySn = ? and companyid = ? and organizationID=? and cashierBillsID=?";
    this.financialBill = ((FinancialBill)this.baseBeanService.getBeanByHqlAndParams(
      hql, new Object[] { groupCompanySn, account.getCompanyID(), 
      organizationID, this.cashierBills.getCashierBillsID() }));
    
    this.BillsGoodList = new ArrayList();
    String hqlg = "from GoodsBills where cashierBillsID=? order by goodsNomber";
    List<BaseBean> financialBillsGoodList = this.baseBeanService
      .getListBeanByHqlAndParams(hqlg, new Object[] { this.cashierBills.getCashierBillsID() });
    if (financialBillsGoodList != null)
    {
      int size = financialBillsGoodList.size();
      for (int i = 0; i < size; i++)
      {
        this.fBillsGood = ((GoodsBills)financialBillsGoodList.get(i));
        double amount = Double.parseDouble(this.fBillsGood.getMoney());
        this.camount += amount;
      }
    }
    if ((financialBillsGoodList != null) && 
      (financialBillsGoodList.size() != 0) && 
      (financialBillsGoodList.get(0) != null)) {
      for (int i = 0; i < financialBillsGoodList.size(); i++)
      {
        GoodsBills good = 
          (GoodsBills)financialBillsGoodList.get(i);
        String hql1 = " from Inventory where inventoryID=?";
        Inventory inventory = (Inventory)this.baseBeanService
          .getBeanByHqlAndParams(hql1, 
          new Object[] { good.getInventoryID() });
        if (inventory != null) {
          this.BillsGoodList.add(good);
        } else {
          this.BillsGoodList.add(good);
        }
      }
    }
    String hql2 = " from ContactUser where relationID=? ";
    if (this.financialBill != null)
    {
      this.contactUser1 = ((ContactUser)this.baseBeanService.getBeanByHqlAndParams(
        hql2, new Object[] { this.financialBill.getCstaffID() }));
      
      String hql3 = " from ContactCompanyView where ccompanyID=? ";
      this.contactCompanyView1 = 
        ((ContactCompanyView)this.baseBeanService.getBeanByHqlAndParams(hql3, new Object[] {this.financialBill
        .getCcompanyID() }));
      
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
              this.billcheckmap.put(bc.getDeptpost() + "zt", bc.getAuditorstatus());
              if (bc.getAuditorid().equals(sta.getStaffID())) {
                this.loginstaffcheck += 1;
              }
            }
          }
        }
      }
    }
    if ((this.print != null) && ("print".equals("print"))) {
      return "wareeditprint";
    }
    return "wareedit";
  }

  public String toSearchWare()
  {
    ActionContext.getContext().getSession().put("cashierBillsVO", 
      this.cashierBillsVO);
    ActionContext.getContext().getSession().put("sdate", 
      this.sdate);
    ActionContext.getContext().getSession().put("edate", 
      this.edate);
    return getWareManagementList();
  }
  
  public String updatback()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    String groupCompanySn = session.get("groupCompanySn").toString();
    List<Object[]> list = new ArrayList();
    List<BaseBean> listbabe = new ArrayList();
    
    String hqlc = "from CashierBills where  cashierBillsID=?";
    this.cashierBills = 
      ((CashierBills)this.baseBeanService.getBeanByHqlAndParams(hqlc, new Object[] { this.cashierBills.getCashierBillsID() }));
    
    String hqlf = "from FinancialBill where groupCompanySn = ? and companyid = ? and organizationID=? and cashierBillsID=?";
    this.financialBill = ((FinancialBill)this.baseBeanService.getBeanByHqlAndParams(
      hqlf, new Object[] { groupCompanySn, account.getCompanyID(), 
      organizationID, this.cashierBills.getCashierBillsID() }));
    String warehouse = this.financialBill.getDepotID();
    String warehouseName = this.financialBill.getDepotName();
    
    String uphqlc = "update CashierBills set status=? where cashierBillsID=? ";
    list.add(new Object[] { "16", this.cashierBills.getCashierBillsID() });
    String uphqlf = "update FinancialBill set billStatus=? where cashierBillsID=? ";
    list.add(new Object[] { "16", this.cashierBills.getCashierBillsID() });
    String hql = "update GoodsBills set kcStatus=? where cashierBillsID=? ";
    list.add(new Object[] { "16", this.cashierBills.getCashierBillsID() });
    String[] hqls = { uphqlc, uphqlf, hql };
    this.baseBeanService.executeHqlsByParamsList(null, hqls, list);
    
    String hqlg = "from GoodsBills where cashierBillsID=?";
    List<BaseBean> goodList = this.baseBeanService
      .getListBeanByHqlAndParams(hqlg, new Object[] { this.cashierBills.getCashierBillsID() });
    for (int i = 0; i < goodList.size(); i++)
    {
      GoodsBills good = (GoodsBills)goodList.get(i);
      stockInv stockinvs = new stockInv();
      stockinvs.setStockinvID(this.serverService.getServerID("stockInv"));
      stockinvs.setCompanyID(account.getCompanyID());
      stockinvs.setGroupCompanySn(groupCompanySn);
      stockinvs.setGoodsID(good.getGoodsID());
      stockinvs.setGoodsType(good.getTypeID());
      stockinvs.setGoodsName(good.getGoodsName());
      stockinvs.setInvenQuantity(good.getQuantity());
      
      String hql2 = "from Inventory where inventoryID=? and companyID=? and weizhi=?";
      Inventory Inventorys = (Inventory)this.baseBeanService
        .getBeanByHqlAndParams(hql2, new Object[] { good.getInventoryID(), account.getCompanyID(), good.getDepotName() });
      if (Inventorys != null)
      {
        if (Inventorys.getInvenOnline() != null)
        {
          stockinvs.setInvenOnline(Inventorys.getInvenOnline());
          stockinvs.setInvenUnderline(Inventorys.getInvenUnderline());
        }
        int invenQuant = Integer.parseInt(Inventorys.getInvenQuantity());
        int quantity = Integer.parseInt(good.getQuantity());
        if (invenQuant >= quantity)
        {
          int sum = invenQuant - quantity;
          Inventorys.setInvenQuantity(String.valueOf(sum));
          Inventorys.setSumPrice(String.valueOf(
            Double.parseDouble(Inventorys.getUnitPrice()) * 
            sum));
          listbabe.add(Inventorys);
        }
        else
        {
          this.message = 
          
            ("所选出库物品 " + good.getGoodsName() + " 的数量不足 " + quantity + ", 库存数量为 " + invenQuant);
          return "failed";
        }
      }
      stockinvs.setWarehouse(warehouse);
      stockinvs.setWarehouseName(warehouseName);
      stockinvs.setStaffID(this.financialBill.getBillStaffID());
      stockinvs.setStaffName(this.financialBill.getBillStaffName());
      stockinvs.setSummoney(good.getMoney());
      stockinvs.setIntime(new Date());
      stockinvs.setType("01");
      listbabe.add(stockinvs);
      good.setStockinvID(stockinvs.getStockinvID());
      listbabe.add(good);
    }
    this.parameter = ("员工：" + account.getAccountName() + "，确认出库单据编号是" + this.cashierBills.getJournalNum());
    CLogBook cLogBook = this.logBookService.saveCLogBook(organizationID, 
      this.parameter, account);
    listbabe.add(cLogBook);
    this.baseBeanService.saveBeansListAndexecuteHqlsByParams(listbabe, null, null);
    return getWareManagementList();
  }
  
  public String rebut()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    String groupCompanySn = session.get("groupCompanySn").toString();
    List<Object[]> list = new ArrayList();
    List<BaseBean> listbabe = new ArrayList();
    
    String hqlc = "from CashierBills where  cashierBillsID=?";
    this.cashierBills = 
      ((CashierBills)this.baseBeanService.getBeanByHqlAndParams(hqlc, new Object[] { this.cashierBills.getCashierBillsID() }));
    
    String hqlf = "from FinancialBill where groupCompanySn = ? and companyid = ? and organizationID=? and cashierBillsID=?";
    this.financialBill = ((FinancialBill)this.baseBeanService.getBeanByHqlAndParams(
      hqlf, new Object[] { groupCompanySn, account.getCompanyID(), 
      organizationID, this.cashierBills.getCashierBillsID() }));
    
    this.parameter = ("员工：" + account.getAccountName() + "，出库驳回单据编号是" + this.cashierBills.getJournalNum());
    CLogBook cLogBook = this.logBookService.saveCLogBook(organizationID, 
      this.parameter, account);
    listbabe.add(cLogBook);
    
    String uphqlc = "update CashierBills set status=? where cashierBillsID=? ";
    list.add(new Object[] { "22", this.cashierBills.getCashierBillsID() });
    String uphqlf = "update FinancialBill set billStatus=? where cashierBillsID=? ";
    list.add(new Object[] { "22", this.cashierBills.getCashierBillsID() });
    String hql = "update GoodsBills set kcStatus=? where cashierBillsID=? ";
    list.add(new Object[] { "22", this.cashierBills.getCashierBillsID() });
    String[] hqls = { uphqlc, uphqlf, hql };
    this.baseBeanService.executeHqlsByParamsList(listbabe, hqls, list);
    
    String hqlg = "from GoodsBills where cashierBillsID=?";
    List<BaseBean> goodList = this.baseBeanService
      .getListBeanByHqlAndParams(hqlg, new Object[] { this.cashierBills.getCashierBillsID() });
    for (int i = 0; i < goodList.size(); i++)
    {
      List<Object[]> list1 = new ArrayList();
      List<BaseBean> lbabe = new ArrayList();
      GoodsBills good = (GoodsBills)goodList.get(i);
      
      String hql3 = "delete stockInv where stockinvID=? ";
      this.baseBeanService.saveBeansListAndexecuteHqlsByParams(null, 
        new String[] { hql3 }, new Object[] { good.getStockinvID() });
      good.setStockinvID("");
      lbabe.add(good);
      
      String hqli = "from Inventory where inventoryID=? and companyID=?";
      Inventory inv = (Inventory)this.baseBeanService
        .getBeanByHqlAndParams(hqli, new Object[] {
        good.getInventoryID(), account.getCompanyID() });
      int nums = Integer.parseInt(good.getQuantity()) + 
        Integer.parseInt(inv.getInvenQuantity());
      double prince = Double.parseDouble(inv.getUnitPrice()) * nums;
      String hql1 = "update Inventory set invenQuantity=?,sumPrice=? where  inventoryID=? and companyID=?";
      String[] hhql = { hql1 };
      list1.add(new Object[] { String.valueOf(nums), String.valueOf(prince), 
        good.getInventoryID(), account.getCompanyID() });
      this.baseBeanService.executeHqlsByParamsList(lbabe, hhql, list1);
    }
    return getWareManagementList();
  }
  
  public List<Object> getList()
  {
    List<Object> result = new ArrayList();
    List<Object> parms = new ArrayList();
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    String groupCompanySn = session.get("groupCompanySn").toString();
    String sql = VOtool.getCashierBillVO(4);
    sql = sql + " where f.groupCompanySn = ?";
    parms.add(groupCompanySn);
    sql = sql + " and f.companyid = ? ";
    parms.add(account.getCompanyID());
    sql = sql + " and f.organizationID=?";
    parms.add(organizationID);
    sql = sql + "and f.billStatus=?";
    parms.add((this.billStatus != null) && (this.billStatus.equals("07")) ? "07" : "03");
    if ((this.search != null) && (this.search.equals("search")))
    {
      FinancialBill financialBill = 
        (FinancialBill)session.get("financialBill");
      if ((financialBill.getJournalNum() != null) && 
        (!financialBill.getJournalNum().trim().equals("")))
      {
        sql = sql + " and f.journalNum like ? ";
        parms.add("%" + financialBill.getJournalNum().trim() + "%");
      }
      if ((financialBill.getStaffID() != null) && 
        (!financialBill.getStaffID().equals("")))
      {
        sql = sql + " and f.staffID = ? ";
        parms.add(financialBill.getStaffID());
      }
      if ((financialBill.getCcompanyRelationship() != null) && 
        (!financialBill.getCcompanyRelationship().equals("")))
      {
        sql = sql + " and f.ccompanyRelationship = ? ";
        parms.add(financialBill.getCcompanyRelationship());
      }
      if ((financialBill.getCstaffRelationship() != null) && 
        (!financialBill.getCstaffRelationship().equals("")))
      {
        sql = sql + " and f.cstaffRelationship = ? ";
        parms.add(financialBill.getCstaffRelationship());
      }
      if ((this.sdate != null) && (this.edate != null) && (!"".equals(this.sdate)) && 
        (!"".equals(this.edate)))
      {
        sql = sql + " and to_date(f.billsDate,'yyyy-MM-dd') between ? and ?";
        parms.add(Utilities.getDateFromString(this.sdate, "yyyy-MM-dd"));
        parms.add(Utilities.getDateFromString(this.edate, "yyyy-MM-dd"));
      }
      if ((financialBill.getCcompanyName() != null) && 
        (!financialBill.getCcompanyName().trim().equals("")))
      {
        sql = sql + " and f.ccompanyName like ? ";
        parms.add("%" + financialBill.getCcompanyName().trim() + "%");
      }
      if ((financialBill.getCstaffName() != null) && 
        (!financialBill.getCstaffName().trim().equals("")))
      {
        sql = sql + " and f.cstaffName like ? ";
        parms.add("%" + financialBill.getCstaffName().trim() + "%");
      }
    }
    sql = sql + "order by f.billsDate desc";
    result.add(sql);
    result.add(parms.toArray());
    return result;
  }
  
  public List<Object> getWareList()
  {
    List<Object> result = new ArrayList();
    List<Object> parms = new ArrayList();
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    String groupCompanySn = session.get("groupCompanySn").toString();
    String sql = " select g.financialgoodid,g.goodsnum,g.sortcode,g.goodsname, g.type,g.quantity,g.unitprice, g.amount,case when g.status='00' then '未收货' when g.status='01' then '已收货'  when g.status='02' then '已验库'  when g.status='03' then '已入库' when g.status='07' then '出库' when g.status='08' then '入库失败' when g.status='09' then '出库审核' else '' end,  case when g.goodstatus='00' then '正常' when g.goodstatus='01' then '维修'  when g.goodstatus='02' then '报废' else '' end, f.journalNum, f.billsType,f.StaffName,  case when f.billStatus='00' then '未收货' when f.billStatus='01' then '已收货' when f.billStatus='02' then '已入库'  when f.billStatus='03' then '已出库' when f.billStatus='04' then '市场调查' when f.billStatus='07' then '销售出库' else '' end  from dt_inv_fbg g left join dt_inv_fb f on g.financialbillid=f.financialbillid";
    









    sql = sql + " where f.groupCompanySn = ? ";
    parms.add(groupCompanySn);
    sql = sql + " and f.companyid = ? ";
    parms.add(account.getCompanyID());
    sql = sql + " and f.organizationID=? ";
    parms.add(organizationID);
    sql = sql + " and f.billStatus=? ";
    parms.add((this.billStatus != null) && (this.billStatus.equals("07")) ? "07" : "03");
    if ((this.search != null) && (this.search.equals("search")))
    {
      FinancialBill financialBill = 
        (FinancialBill)session.get("financialBill");
      FinancialBillsGood financialBillsGood = 
        (FinancialBillsGood)session.get("financialBillsGood");
      String sdate = (String)session.get("sdate");
      String edate = (String)session.get("edate");
      if ((financialBillsGood.getGoodsNum() != null) && 
        (!financialBillsGood.getGoodsNum().trim().equals("")))
      {
        sql = sql + " and g.goodsNum like ? ";
        parms.add("%" + financialBillsGood.getGoodsNum().trim() + "%");
      }
      if ((financialBill.getJournalNum() != null) && 
        (!financialBill.getJournalNum().trim().equals("")))
      {
        sql = sql + " and f.journalNum like ? ";
        parms.add("%" + financialBill.getJournalNum().trim() + "%");
      }
      if ((financialBill.getStaffID() != null) && 
        (!financialBill.getStaffID().equals("")))
      {
        sql = sql + " and f.staffID = ? ";
        parms.add(financialBill.getStaffID());
      }
      if ((financialBill.getCcompanyRelationship() != null) && 
        (!financialBill.getCcompanyRelationship().equals("")))
      {
        sql = sql + " and f.ccompanyRelationship = ? ";
        parms.add(financialBill.getCcompanyRelationship());
      }
      if ((financialBill.getCstaffRelationship() != null) && 
        (!financialBill.getCstaffRelationship().equals("")))
      {
        sql = sql + " and f.cstaffRelationship = ? ";
        parms.add(financialBill.getCstaffRelationship());
      }
      if ((sdate != null) && (edate != null) && (!"".equals(sdate)) && 
        (!"".equals(edate)))
      {
        sql = sql + " and to_date(f.billsDate,'yyyy-MM-dd') between ? and ? ";
        parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
        parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
      }
      if ((financialBill.getCcompanyName() != null) && 
        (!financialBill.getCcompanyName().trim().equals("")))
      {
        sql = sql + " and f.ccompanyName like ? ";
        parms.add("%" + financialBill.getCcompanyName().trim() + "%");
      }
      if ((financialBill.getCstaffName() != null) && 
        (!financialBill.getCstaffName().trim().equals("")))
      {
        sql = sql + " and f.cstaffName like ? ";
        parms.add("%" + financialBill.getCstaffName().trim() + "%");
      }
    }
    sql = sql + " order by f.billsDate desc";
    result.add(sql);
    result.add(parms.toArray());
    return result;
  }
  
  public List<Object> getInventoryManagementBySqlAndParams()
  {
    List<Object> result = new ArrayList();
    List<Object> parms = new ArrayList();
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String sql = "select zong.goodsid,zong.vv2,zong.vv3,zong1.qq1,case when zong1.qq1 >= zong.vv4 then '蓝色' when zong1.qq1 <= zong.vv5  then  '红色' when (zong1.qq1 < zong.vv4) and (zong1.qq1 > zong.vv5) then   '黑色' else  '' end,zong1.qq1 cc,zong1.qq2 from (select distinct (goodsid) goodsid,companyid vv1,goodsname vv2,goodstype vv3,invenonline vv4,invenunderline vv5 from dt_inv_stockinvt) zong left join (select s.s1 qq, case when t.d2 is null then s.s2 else s.s2 - t.d2 end qq1, case when t.d3 is null then s.s3 else s.s3 - t.d3 end qq2 from (select t.goodsid s1,sum(t.invenquantity) s2,  sum(t.summoney) s3  from dt_inv_stockinvt t where t.type = '00' group by (t.goodsid)) s left join (select t.goodsid d1, sum(t.invenquantity) d2, sum(t.summoney) d3 from dt_inv_stockinvt t where t.type in( '01','02') group by (t.goodsid)) t on s.s1 = t.d1) zong1  on zong.goodsid = zong1.qq where zong.vv1 =?";
    






    parms.add(account.getCompanyID());
    if ((this.search != null) && (this.search.equals("search")))
    {
      Inventory inven = (Inventory)session.get("tableSearch");
      if ((inven != null) && (inven.getGoodsType() != null) && 
        (!"".equals(inven.getGoodsType().trim())))
      {
        sql = sql + " and zong.vv3 like ?";
        parms.add("%" + inven.getGoodsType().trim() + "%");
      }
      if ((inven != null) && (inven.getGoodsName() != null) && 
        (!"".equals(inven.getGoodsName().trim())))
      {
        sql = sql + " and zong.vv2 like ?";
        parms.add("%" + inven.getGoodsName().trim() + "%");
      }
    }
    result.add(sql);
    result.add(parms.toArray());
    return result;
  }
  
  public String getspan()
  {
    CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
    if (account == null)
    {
      Map<String, Object> map = new HashMap();
      map.put("nologin", Integer.valueOf(1));
      JSONObject obj = JSONObject.fromObject(map);
      this.result = obj.toString();
      return "success";
    }
    List<Object> parms = new ArrayList();
    String sql = "select t.inventoryid ,t.goodsname,t.goodstype,t.barcode,t.invenquantity,t.unitprice,t.sumprice,t.goodstatus,t.unit,s.a3,case when s.a3>=t.invenonline then '蓝色' when s.a3<=invenunderline then '红色' when (s.a3<t.invenonline) and (s.a3>invenunderline)  then '黑色' else '' end,s.a3-invenunderline,t.warehouseName from dt_inv_invt t  left join (select goodsid,sum(invenquantity) a1,sum(badquantity) a2,sum(invenquantity + badquantity) a3 from dt_inv_invt where goodstatus='00' group by (goodsid)) s  on t.goodsid=s.goodsid  where t.companyid=?";
    




    parms.add(account.getCompanyID());
    sql = sql + " and t.status=?";
    parms.add("03");
    sql = sql + " and t.goodsid=?";
    parms.add(this.zongID);
    

    this.pageForm = this.baseBeanService.getPageFormBySQL(
      this.pageForm != null ? this.pageForm.getPageNumber() : 1, 
      this.pageNumber == 0 ? 10 : this.pageNumber, sql, "select count(1) " + 
      sql.substring(sql.indexOf("from")), parms.toArray());
    Map<String, Object> map = new HashMap();
    map.put("pageForm", this.pageForm);
    JSONObject oj = JSONObject.fromObject(map);
    this.result = oj.toString();
    return "success";
  }
  
  public String getInventoryManagementList()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String hql = "from Warehouse where companyID=? and wareType='1'";
    this.warehouseList = this.baseBeanService.getListBeanByHqlAndParams(hql, 
      new Object[] { account.getCompanyID() });
    List<Object> list = getInventoryManagementBySqlAndParams();
    String sql = (String)list.get(0);
    Object[] parms = (Object[])list.get(1);
    this.pageForm = this.baseBeanService.getPageFormBySQL(
      this.pageForm != null ? this.pageForm.getPageNumber() : 1, 
      this.pageNumber == 0 ? 5 : this.pageNumber, sql, "select count(1) " + 
      sql.substring(sql.indexOf("from")), parms);
    
    return "inventoryManagement";
  }
  
  public String getInventoryPoolList()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String hql = "from Warehouse where companyID=? and wareType='1'";
    this.warehouseList = this.baseBeanService.getListBeanByHqlAndParams(hql, 
      new Object[] { account.getCompanyID() });
    List<Object> list = getInventoryPoolBySqlAndParams();
    String sql = (String)list.get(0);
    Object[] parms = (Object[])list.get(1);
    this.pageForm = this.baseBeanService.getPageFormBySQL(
      this.pageForm != null ? this.pageForm.getPageNumber() : 1, 
      this.pageNumber == 0 ? 5 : this.pageNumber, sql, "select count(1) " + 
      sql.substring(sql.indexOf("from")), parms);
    if ((this.print != null) && ("print".equals("print"))) {
      return "inventoryPoolBill";
    }
    return "inventoryPool";
  }
  
  public List<Object> getInventoryPoolBySqlAndParams()
  {
    List<Object> result = new ArrayList();
    List<Object> parms = new ArrayList();
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String sql = "select t.goodsID,t.goodsname,t.goodstype,t.intime,case when t.type='00' then '入库' when t.type='01'then '出库' when t.type='02'then '借出' else '' end,t.invenQuantity,t.summoney from dt_inv_stockinvt t where t.companyid=?";
    

    parms.add(account.getCompanyID());
    if ((this.search != null) && (this.search.equals("search")))
    {
      stockInv stoinv = (stockInv)session.get("tableSearch");
      String sdate = (String)session.get("sdate");
      String edate = (String)session.get("edate");
      if ((stoinv != null) && (stoinv.getGoodsType() != null) && 
        (!"".equals(stoinv.getGoodsType().trim())))
      {
        sql = sql + " and t.goodstype like ?";
        parms.add("%" + stoinv.getGoodsType().trim() + "%");
      }
      if ((sdate != null) && (edate != null) && (!"".equals(sdate)) && 
        (!"".equals(edate)))
      {
        sql = sql + " and to_date(t.intime,'yyyy-MM-dd hh24:mi:ss') between to_date(?,'yyyy-MM-dd hh24:mi:ss') and  to_date(?,'yyyy-MM-dd hh24:mi:ss')";
        

        parms.add(sdate);
        parms.add(edate);
      }
      if ((stoinv != null) && (stoinv.getGoodsName() != null) && 
        (!"".equals(stoinv.getGoodsName().trim())))
      {
        sql = sql + " and t.goodsname like ?";
        parms.add("%" + stoinv.getGoodsName().trim() + "%");
      }
    }
    result.add(sql);
    result.add(parms.toArray());
    return result;
  }
  
  public String getInventoryDetailList()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    List<Object> list = getInventoryDetailBySqlAndParams();
    String sql = (String)list.get(0);
    Object[] parms = (Object[])list.get(1);
    
    this.pageForm = this.baseBeanService.getPageFormBySQL(
      this.pageForm != null ? this.pageForm.getPageNumber() : 1, 
      this.pageNumber == 0 ? 5 : this.pageNumber, sql, "select count(1) " + 
      sql.substring(sql.indexOf("from")), parms);
    
    String sql1 = "select sum(ins.summoney) ss from dt_inv_stockinvt ins where ins.intime<=? and ins.type=? and ins.companyid=?";
    String timeyear = Utilities.getDateString(new Date(), "yyyy-MM");
    String mm = "";
    if (timeyear.substring(5, 6).equals("0")) {
      mm = timeyear.substring(6, 7);
    } else {
      mm = mm + timeyear.substring(5, 7);
    }
    this.lists = new ArrayList();
    for (int i = 1; i <= Integer.parseInt(mm); i++)
    {
      int tt = Utilities.getDayMouth(Integer.parseInt(timeyear.substring(
        0, 4)), i);
      String time = timeyear.substring(0, 4) + "-" + i + "-" + tt + 
        " 23:59:59";
      List<BaseBean> list1 = this.baseBeanService.getListBeanBySqlAndParams(
        sql1, new Object[] { time, "00", account.getCompanyID() });
      List<BaseBean> list2 = this.baseBeanService.getListBeanBySqlAndParams(
        sql1, new Object[] { time, "01", account.getCompanyID() });
      List<BaseBean> list3 = this.baseBeanService.getListBeanBySqlAndParams(
        sql1, new Object[] { time, "01", account.getCompanyID() });
      Object[] obj = new Object[4];
      obj[0] = getMonth(i);
      if (list1.size() > 0) {
        obj[1] = list1.get(0);
      } else {
        obj[1] = null;
      }
      if (list2.size() > 0) {
        obj[2] = list2.get(0);
      } else {
        obj[2] = null;
      }
      if (list3.size() > 0) {
        obj[3] = list3.get(0);
      } else {
        obj[3] = null;
      }
      this.lists.add(obj);
    }
    this.objectny = this.lists.toArray();
    if ((this.print != null) && ("print".equals("print"))) {
      return "inventoryDetailBill";
    }
    return "inventoryDetail";
  }
  
  public List<Object> getInventoryDetailBySqlAndParams()
  {
    List<Object> result = new ArrayList();
    List<Object> parms = new ArrayList();
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String times = new Date().toLocaleString().substring(0, 6) + "%";
    this.kutime = 
      (new Date().toLocaleString().substring(0, 4) + "年" + new Date().toLocaleString().substring(5, 6) + "月");
    String ssd = new Date().toLocaleString().substring(0, 5) + 
      String.valueOf(Integer.parseInt(new Date().toLocaleString()
      .substring(5, 6)) - 1) + "%";
    String sql = "";
    if ((this.state != null) && (this.state.equals("1"))) {
      sql = "select zong.vv1,zong.vv2,zong.vv3, bb.bb1, zong2.aa1,zong2.aa2,zong1.qq1,zong.vv4 ||'~'|| zong.vv5,zong1.qq1 cc,zong1.qq2";
    } else if (this.state == null) {
      sql = "select zong.goodsid,zong.vv1,zong.vv2,zong.vv3, bb.bb1, zong2.aa1,zong2.aa2,zong1.qq1,zong.vv4,zong.vv5,zong1.qq1 cc,zong1.qq2";
    }
    sql = sql + " from (select distinct (goodsid) goodsid,companyid vv1,goodsname vv2,goodstype vv3,invenonline vv4,invenunderline vv5 from dt_inv_stockinvt) zong left join (select s.s1 qq, case when t.d2 is null then s.s2 else s.s2 - t.d2 end qq1, case when t.d3 is null then s.s3 else s.s3 - t.d3 end qq2 from (select t.goodsid s1,sum(t.invenquantity) s2,  sum(t.summoney) s3  from dt_inv_stockinvt t where t.type = '00' group by (t.goodsid)) s left join (select t.goodsid d1, sum(t.invenquantity) d2, sum(t.summoney) d3 from dt_inv_stockinvt t where t.type in( '01','02') group by (t.goodsid)) t on s.s1 = t.d1) zong1  on zong.goodsid = zong1.qq left join (select s.s1 aa, case when c.c2 is null then  s.s2  else  s.s2 - c.c2 end aa1, case  when t.d2 is null then  0 else  t.d2 end aa2  from (select t.goodsid s1, sum(t.invenquantity) s2, sum(t.summoney) s3 from dt_inv_stockinvt t where t.intime like ? and t.type = '00' group by (t.goodsid)) s left join (select t.goodsid d1, sum(t.invenquantity) d2, sum(t.summoney) d3 from dt_inv_stockinvt t where t.intime like ? and t.type = '01' group by (t.goodsid)) t on s.s1 = t.d1 left join (select goodsid c1,sum(invenquantity) c2 from dt_inv_stockinvt where intime like ? and type in ('01','02') group by goodsid) c on c.c1= s.s1) zong2  on zong.goodsid = zong2.aa left join (select ss.ss1 bb,case when tt.dd2 is null then  ss.ss2 when ss.ss2 is null then  0  else ss.ss2 - tt.dd2 end bb1 from (select t.goodsid ss1, sum(t.invenquantity) ss2, sum(t.summoney) ss3 from dt_inv_stockinvt t where t.intime like ? and t.type = '00' group by (t.goodsid)) ss left join (select t.goodsid dd1, sum(t.invenquantity) dd2, sum(t.summoney) dd3 from dt_inv_stockinvt t where t.intime like ? and t.type in('01','02') group by (t.goodsid)) tt on ss.ss1 = tt.dd1) bb on zong.goodsid =  bb.bb where zong.vv1 = ?";
    



















    parms.add(times);
    parms.add(times);
    parms.add(times);
    parms.add(ssd);
    parms.add(ssd);
    parms.add(account.getCompanyID());
    if ((this.search != null) && (this.search.equals("search")))
    {
      stockInv stoinv = (stockInv)session.get("tableSearch");
      if ((stoinv != null) && (stoinv.getGoodsType() != null) && 
        (!"".equals(stoinv.getGoodsType().trim())))
      {
        sql = sql + " and zong.vv3 like ?";
        parms.add("%" + stoinv.getGoodsType().trim() + "%");
      }
      if ((stoinv != null) && (stoinv.getGoodsName() != null) && 
        (!"".equals(stoinv.getGoodsName().trim())))
      {
        sql = sql + " and zong.vv2 like ?";
        parms.add("%" + stoinv.getGoodsName().trim() + "%");
      }
    }
    result.add(sql);
    result.add(parms.toArray());
    return result;
  }
  
  public String toSearchInventoryPool()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    session.put("tableSearch", this.stockinvParam);
    session.put("sdate", this.sdate);
    session.put("edate", this.edate);
    return getInventoryPoolList();
  }
  
  public String toSearchInventoryDetail()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    session.put("tableSearch", this.stockinvParam);
    session.put("sdate", this.sdate);
    session.put("edate", this.edate);
    return getInventoryDetailList();
  }
  
  public String toSearchInventoryManagement()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    session.put("tableSearch", this.inventoryParam);
    return getInventoryManagementList();
  }
  
  public String poolShowExcel()
  {
    List<Object> result = getInventoryPoolBySqlAndParams();
    String sql = result.get(0).toString();
    sql = "select " + sql.substring(sql.indexOf(",") + 1);
    Object[] prarms = (Object[])result.get(1);
    this.excelStream = this.excelService.showExcel(stockInv.columnHeading(), 
      this.baseBeanService.getListBeanBySqlAndParams(sql, prarms));
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    CLogBook logbook = this.logBookService.saveCLogBook(null, "导出进销存明细列表", account);
    this.baseBeanService.update(logbook);
    return "showexcel";
  }
  
  public String detailShowExcel()
  {
    List<Object> result = getInventoryDetailBySqlAndParams();
    String sql = result.get(0).toString();
    sql = "select " + sql.substring(sql.indexOf(",") + 1);
    Object[] prarms = (Object[])result.get(1);
    this.excelStream = this.excelService.showExcel(stockInv.columnHeadings(), 
      this.baseBeanService.getListBeanBySqlAndParams(sql, prarms));
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    CLogBook logbook = this.logBookService.saveCLogBook(null, "导出进销存汇总列表", account);
    this.baseBeanService.update(logbook);
    return "showexcel";
  }
  
  public String showExcel()
  {
    List<Object> result = getInventoryManagementBySqlAndParams();
    String sql = result.get(0).toString();
    sql = "select " + sql.substring(sql.indexOf(",") + 1);
    Object[] prarms = (Object[])result.get(1);
    this.excelStream = this.excelService.showExcel(Inventory.columnHeadings(), 
      this.baseBeanService.getListBeanBySqlAndParams(sql, prarms));
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    CLogBook logbook = this.logBookService.saveCLogBook(null, "导出库房列表", account);
    this.baseBeanService.update(logbook);
    return "showexcel";
  }
  
  public String getInventory()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String sn = session.get("groupCompanySn").toString();
    if (account == null)
    {
      Map<String, Object> map = new HashMap();
      map.put("nologin", Integer.valueOf(1));
      JSONObject obj = JSONObject.fromObject(map);
      this.result = obj.toString();
      return "success";
    }
    DetachedCriteria dc = DetachedCriteria.forClass(Inventory.class);
    dc.add(Restrictions.eq("groupCompanySn", sn));
    dc.add(Restrictions.eq("status", "03"));
    dc.add(Restrictions.eq("companyID", account.getCompanyID()));
    if ((this.typeID != null) && (!"".equals(this.typeID))) {
      dc.add(Restrictions.eq("goodsType", this.typeID));
    }
    if ((this.subjectsID != null) && (!"".equals(this.subjectsID))) {
      dc.add(Restrictions.eq("subjectsID", this.subjectsID));
    }
    dc.add(Restrictions.or(Restrictions.eq("barcode", this.parameter), Restrictions.eq("goodsCoding", this.parameter)));
    
    List<BaseBean> list = this.baseBeanService.getListByDC(dc);
    Map<String, Object> map = new HashMap();
    map.put("goodlist", list);
    JSONObject oj = JSONObject.fromObject(map);
    this.result = oj.toString();
    return "success";
  }
  
  public String QueryArchiveInfo()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    if (account == null)
    {
      Map<String, Object> map = new HashMap();
      map.put("nologin", Integer.valueOf(1));
      JSONObject obj = JSONObject.fromObject(map);
      this.result = obj.toString();
      return "success";
    }
    String hql = "from Inventory where defaultStorage = ?";
    Inventory inventory = (Inventory)this.baseBeanService
      .getBeanByHqlAndParams(hql, new Object[] { this.parameter.trim() });
    Map<String, Object> map = new HashMap();
    map.put("inventory", inventory);
    JSONObject oj = JSONObject.fromObject(map);
    this.result = oj.toString();
    return "success";
  }
  
  public String breakdowngood()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    List<Object[]> list = new ArrayList();
    List<BaseBean> baseBeanList = new ArrayList();
    List<BaseBean> listbabe = new ArrayList();
    this.parameter = ("员工：" + account.getAccountName());
    CLogBook cLogBook = this.logBookService.saveCLogBook(organizationID, 
      this.parameter, account);
    String hql = "from Inventory f where f.inventoryID=? and f.companyID=?";
    Inventory inv = (Inventory)this.baseBeanService.getBeanByHqlAndParams(hql, 
      new Object[] { this.inventoryParam.getInventoryID(), 
      account.getCompanyID() });
    int numbad = 0;
    if (inv.getBadQuantity().equals("")) {
      numbad = Integer.parseInt(this.inventoryParam.getBadQuantity());
    } else {
      numbad = Integer.parseInt(this.inventoryParam.getBadQuantity()) + 
        Integer.parseInt(inv.getBadQuantity());
    }
    int num = Integer.parseInt(inv.getInvenQuantity()) - 
      Integer.parseInt(this.inventoryParam.getBadQuantity());
    double asum = Double.parseDouble(inv.getUnitPrice()) * num;
    String hql1 = "update Inventory set invenQuantity=?, badQuantity=?,sumPrice=?  where inventoryID=? and companyID=?";
    String[] hqls = { hql1 };
    list.add(new Object[] { String.valueOf(num), String.valueOf(numbad), 
      String.valueOf(asum), this.inventoryParam.getInventoryID(), 
      account.getCompanyID() });
    listbabe.add(cLogBook);
    this.baseBeanService.executeHqlsByParamsList(listbabe, hqls, list);
    Inventory inven = new Inventory();
    inven.setInventoryID(this.serverService.getServerID("inventory"));
    inven.setGoodsID(inv.getGoodsID());
    inven.setCompanyID(account.getCompanyID());
    inven.setGroupCompanySn(inv.getGroupCompanySn());
    inven.setInventoryNum(inv.getInventoryNum());
    inven.setWarehouse(inv.getWarehouse());
    inven.setArea(inv.getArea());
    inven.setFrame(inv.getFrame());
    inven.setPosition(inv.getPosition());
    inven.setWeizhi(inv.getWeizhi());
    inven.setStaffID(inv.getStaffID());
    inven.setStaffName(inv.getStaffName());
    inven.setGoodstatus(this.inventoryParam.getGoodstatus());
    inven.setStatus("10");
    inven.setBadQuantity(this.inventoryParam.getBadQuantity());
    inven.setGoodsID(inv.getGoodsID());
    inven.setSubjectsID(inv.getSubjectsID());
    inven.setSubjectsName(inv.getSubjectsName());
    inven.setGoodsType(inv.getGoodsType());
    inven.setGoodsName(inv.getGoodsName());
    inven.setBarcode(inv.getBarcode());
    inven.setStandard(inv.getStandard());
    inven.setUnit(inv.getUnit());
    inven.setUnitPrice(inv.getUnitPrice());
    inven.setInvenQuantity(String.valueOf(num));
    inven.setSumPrice(String.valueOf(asum));
    inven.setInvenOnline(inv.getInvenOnline());
    inven.setInvenUnderline(inv.getInvenUnderline());
    inven.setWarehouseName(inv.getWarehouseName());
    inven.setAreaName(inv.getAreaName());
    inven.setFrameName(inv.getFrameName());
    inven.setPositionName(inv.getPositionName());
    baseBeanList.add(cLogBook);
    baseBeanList.add(inven);
    this.baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, 
      null);
    return "success";
  }
  
  public String record()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String organizationID = (String)session.get("organizationID");
    List<Object[]> list = new ArrayList();
    List<BaseBean> listbabe = new ArrayList();
    String hql = "from Inventory f where f.inventoryID=? and f.companyID=?";
    Inventory inv = (Inventory)this.baseBeanService.getBeanByHqlAndParams(hql, 
      new Object[] { this.inventoryParam.getInventoryID(), 
      account.getCompanyID() });
    this.parameter = ("员工：" + account.getAccountName());
    CLogBook cLogBook = this.logBookService.saveCLogBook(organizationID, 
      this.parameter, account);
    String hql1 = "update Inventory set invenOnline=?,invenUnderline=?  where goodsID=? and companyID=? ";
    String[] hqls = { hql1 };
    list.add(new Object[] { this.inventoryParam.getInvenOnline(), 
      this.inventoryParam.getInvenUnderline(), inv.getGoodsID(), 
      account.getCompanyID() });
    listbabe.add(cLogBook);
    this.baseBeanService.executeHqlsByParamsList(listbabe, hqls, list);
    String hql2 = "update stockInv set invenOnline=?,invenUnderline=?  where goodsID=? and companyID=? ";
    String[] hql2s = { hql2 };
    listbabe.add(cLogBook);
    this.baseBeanService.executeHqlsByParamsList(listbabe, hql2s, list);
    return "success";
  }
  
  public List<Object> panList()
  {
    List<Object> result = new ArrayList();
    List<Object> parms = new ArrayList();
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    String times = new Date().toLocaleString().substring(0, 6) + "%";
    this.kutime = 
      (new Date().toLocaleString().substring(0, 4) + "年" + new Date().toLocaleString().substring(5, 6) + "月");
    String ssd = new Date().toLocaleString().substring(0, 5) + 
      String.valueOf(Integer.parseInt(new Date().toLocaleString()
      .substring(5, 6)) - 1) + "%";
    String sql = "select zong.goodsid,zong.vv1,zong.vv2,zong.vv3, bb.bb1, zong2.aa1,zong2.aa2,zong1.qq1,zong.vv4,zong.vv5,zong1.qq1 cc,zong1.qq2 from (select distinct (goodsid) goodsid,companyid vv1,goodsname vv2,goodstype vv3,invenonline vv4,invenunderline vv5 from dt_inv_stockinvt) zong left join (select s.s1 qq, case when t.d2 is null then s.s2 else s.s2 - t.d2 end qq1, case when t.d3 is null then s.s3 else s.s3 - t.d3 end qq2 from (select t.goodsid s1,sum(t.invenquantity) s2,  sum(t.summoney) s3  from dt_inv_stockinvt t where t.type = '00' group by (t.goodsid)) s left join (select t.goodsid d1, sum(t.invenquantity) d2, sum(t.summoney) d3 from dt_inv_stockinvt t where t.type in( '01','02') group by (t.goodsid)) t on s.s1 = t.d1) zong1  on zong.goodsid = zong1.qq left join (select s.s1 aa, case when c.c2 is null then  s.s2  else  s.s2 - c.c2 end aa1, case  when t.d2 is null then  0 else  t.d2 end aa2  from (select t.goodsid s1, sum(t.invenquantity) s2, sum(t.summoney) s3 from dt_inv_stockinvt t where t.intime like ? and t.type = '00' group by (t.goodsid)) s left join (select t.goodsid d1, sum(t.invenquantity) d2, sum(t.summoney) d3 from dt_inv_stockinvt t where t.intime like ? and t.type = '01' group by (t.goodsid)) t on s.s1 = t.d1 left join (select goodsid c1,sum(invenquantity) c2 from dt_inv_stockinvt where intime like ? and type in ('01','02') group by goodsid) c on c.c1= s.s1) zong2  on zong.goodsid = zong2.aa left join (select ss.ss1 bb,case when tt.dd2 is null then  ss.ss2 when ss.ss2 is null then  0  else ss.ss2 - tt.dd2 end bb1 from (select t.goodsid ss1, sum(t.invenquantity) ss2, sum(t.summoney) ss3 from dt_inv_stockinvt t where t.intime like ? and t.type = '00' group by (t.goodsid)) ss left join (select t.goodsid dd1, sum(t.invenquantity) dd2, sum(t.summoney) dd3 from dt_inv_stockinvt t where t.intime like ? and t.type in('01','02') group by (t.goodsid)) tt on ss.ss1 = tt.dd1) bb on zong.goodsid =  bb.bb where zong.vv1 = ?";
    




















    parms.add(times);
    parms.add(times);
    parms.add(times);
    parms.add(ssd);
    parms.add(ssd);
    parms.add(account.getCompanyID());
    result.add(sql);
    result.add(parms.toArray());
    return result;
  }
  
  public String invprint()
  {
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");
    List<Object> list = panList();
    String sql = (String)list.get(0);
    Object[] parms = (Object[])list.get(1);
    List<BaseBean> listw = this.baseBeanService
      .getListBeanBySqlAndParams(sql, parms);
    this.pageForm = new PageForm();
    this.pageForm.setList(listw);
    
    String sql1 = "select sum(ins.summoney) ss from dt_inv_stockinvt ins where ins.intime<=? and ins.type=? and ins.companyid=?";
    String timeyear = Utilities.getDateString(new Date(), "yyyy-MM");
    String mm = "";
    if (timeyear.substring(5, 6).equals("0")) {
      mm = timeyear.substring(6, 7);
    } else {
      mm = mm + timeyear.substring(5, 7);
    }
    this.lists = new ArrayList();
    for (int i = 1; i <= Integer.parseInt(mm); i++)
    {
      int tt = Utilities.getDayMouth(Integer.parseInt(timeyear.substring(
        0, 4)), i);
      String time = timeyear.substring(0, 4) + "-" + i + "-" + tt + 
        " 23:59:59";
      List<BaseBean> list1 = this.baseBeanService.getListBeanBySqlAndParams(
        sql1, new Object[] { time, "00", account.getCompanyID() });
      List<BaseBean> list2 = this.baseBeanService.getListBeanBySqlAndParams(
        sql1, new Object[] { time, "01", account.getCompanyID() });
      List<BaseBean> list3 = this.baseBeanService.getListBeanBySqlAndParams(
        sql1, new Object[] { time, "01", account.getCompanyID() });
      Object[] obj = new Object[4];
      obj[0] = getMonth(i);
      if (list1.size() > 0) {
        obj[1] = list1.get(0);
      } else {
        obj[1] = null;
      }
      if (list2.size() > 0) {
        obj[2] = list2.get(0);
      } else {
        obj[2] = null;
      }
      if (list3.size() > 0) {
        obj[3] = list3.get(0);
      } else {
        obj[3] = null;
      }
      this.lists.add(obj);
    }
    this.objectny = this.lists.toArray();
    

    return "invprintss";
  }
  
  private String getMonth(int i)
  {
    String mm = "";
    switch (i)
    {
    case 1: 
      mm = "一月";
      break;
    case 2: 
      mm = "二月";
      break;
    case 3: 
      mm = "三月";
      break;
    case 4: 
      mm = "四月";
      break;
    case 5: 
      mm = "五月";
      break;
    case 6: 
      mm = "六月";
      break;
    case 7: 
      mm = "七月";
      break;
    case 8: 
      mm = "八月";
      break;
    case 9: 
      mm = "九月";
      break;
    case 10: 
      mm = "十月";
      break;
    case 11: 
      mm = "十一月";
      break;
    case 12: 
      mm = "十二月";
    }
    return mm;
  }
  
  public String AuditAcquisition()
  {
    String hql = "from BillCheck where NewBillsID=?";
    Object[] obj = { this.cashierBills.getCashierBillsID() };
    List<BaseBean> list = new ArrayList();
    list = this.baseBeanService.getListBeanByHqlAndParams(hql, obj);
    Map<String, Object> map = new HashMap();
    map.put("list", list);
    JSONObject Json = JSONObject.fromObject(map);
    this.result = Json.toString();
    return "success";
  }
  
  public CashierBills getCashierBills()
  {
    return this.cashierBills;
  }
  
  public void setCashierBills(CashierBills cashierBills)
  {
    this.cashierBills = cashierBills;
  }
  
  public FinancialBill getFinancialBill()
  {
    return this.financialBill;
  }
  
  public void setFinancialBill(FinancialBill financialBill)
  {
    this.financialBill = financialBill;
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
  
  public String getDepotid()
  {
    return this.depotid;
  }
  
  public void setDepotid(String depotid)
  {
    this.depotid = depotid;
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
  
  public List<GoodsBills> getBillsGoodList()
  {
    return this.BillsGoodList;
  }
  
  public void setBillsGoodList(List<GoodsBills> billsGoodList)
  {
    this.BillsGoodList = billsGoodList;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
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
  
  public stockInv getStockinvParam()
  {
    return this.stockinvParam;
  }
  
  public void setStockinvParam(stockInv stockinvParam)
  {
    this.stockinvParam = stockinvParam;
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
  
  public String getState()
  {
    return this.state;
  }
  
  public void setState(String state)
  {
    this.state = state;
  }
  
  public String getOne()
  {
    return this.one;
  }
  
  public void setOne(String one)
  {
    this.one = one;
  }
  
  public String getPrint()
  {
    return this.print;
  }
  
  public void setPrint(String print)
  {
    this.print = print;
  }
  
  public double getCamount()
  {
    return this.camount;
  }
  
  public void setCamount(double camount)
  {
    this.camount = camount;
  }
  
  public String getResult()
  {
    return this.result;
  }
  
  public void setResult(String result)
  {
    this.result = result;
  }
  
  public String getTypeID()
  {
    return this.typeID;
  }
  
  public void setTypeID(String typeID)
  {
    this.typeID = typeID;
  }
  
  public List<BaseBean> getStafflist()
  {
    return this.stafflist;
  }
  
  public void setStafflist(List<BaseBean> stafflist)
  {
    this.stafflist = stafflist;
  }
  
  public String getBillStatus()
  {
    return this.billStatus;
  }
  
  public void setBillStatus(String billStatus)
  {
    this.billStatus = billStatus;
  }
  
  public FinancialBillsGood getFinancialBillsGood()
  {
    return this.financialBillsGood;
  }
  
  public void setFinancialBillsGood(FinancialBillsGood financialBillsGood)
  {
    this.financialBillsGood = financialBillsGood;
  }
  
  public GoodsBills getfBillsGood()
  {
    return this.fBillsGood;
  }
  
  public void setfBillsGood(GoodsBills fBillsGood)
  {
    this.fBillsGood = fBillsGood;
  }
  
  public String getKutime()
  {
    return this.kutime;
  }
  
  public void setKutime(String kutime)
  {
    this.kutime = kutime;
  }
  
  public stockInv getStockinv()
  {
    return this.stockinv;
  }
  
  public void setStockinv(stockInv stockinv)
  {
    this.stockinv = stockinv;
  }
  
  public Object[] getObjectny()
  {
    return this.objectny;
  }
  
  public void setObjectny(Object[] objectny)
  {
    this.objectny = objectny;
  }
  
  public List<Object[]> getLists()
  {
    return this.lists;
  }
  
  public void setLists(List<Object[]> lists)
  {
    this.lists = lists;
  }
  
  public String getZongID()
  {
    return this.zongID;
  }
  
  public void setZongID(String zongID)
  {
    this.zongID = zongID;
  }
  
  public String getSubjectsID()
  {
    return this.subjectsID;
  }
  
  public void setSubjectsID(String subjectsID)
  {
    this.subjectsID = subjectsID;
  }
  
  public String getArrgoodsNum()
  {
    return this.arrgoodsNum;
  }
  
  public void setArrgoodsNum(String arrgoodsNum)
  {
    this.arrgoodsNum = arrgoodsNum;
  }
  
  public List<CCode> getTypelist()
  {
    return this.typelist;
  }
  
  public void setTypelist(List<CCode> typelist)
  {
    this.typelist = typelist;
  }
  
  public CashierBillsVO getCashierBillsVO()
  {
    return this.cashierBillsVO;
  }
  
  public void setCashierBillsVO(CashierBillsVO cashierBillsVO)
  {
    this.cashierBillsVO = cashierBillsVO;
  }
  
  public List<CCode> getBillsType()
  {
    return this.billsType;
  }
  
  public void setBillsType(List<CCode> billsType)
  {
    this.billsType = billsType;
  }
  
  public String getSubtype()
  {
    return this.subtype;
  }
  
  public void setSubtype(String subtype)
  {
    this.subtype = subtype;
  }
  
  public List<BaseBean> getBillgoodlist()
  {
    return this.billgoodlist;
  }
  
  public void setBillgoodlist(List<BaseBean> billgoodlist)
  {
    this.billgoodlist = billgoodlist;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public List<BaseBean> getBillCheckList()
  {
    return this.BillCheckList;
  }
  
  public void setBillCheckList(List<BaseBean> billCheckList)
  {
    this.BillCheckList = billCheckList;
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
  
  public Map<String, GoodsBills> getGoodsmapold()
  {
    return this.goodsmapold;
  }
  
  public void setGoodsmapold(Map<String, GoodsBills> goodsmapold)
  {
    this.goodsmapold = goodsmapold;
  }
}
