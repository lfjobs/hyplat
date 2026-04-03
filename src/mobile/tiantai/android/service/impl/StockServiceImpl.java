package mobile.tiantai.android.service.impl;

import com.tiantai.wfj.service.ProductlaunchService;
import hy.ea.bo.CCode;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.*;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.InvCheckGoods;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.bo.production.AttriProduction;
import hy.ea.production.service.WarehouseService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.ImageCut;
import hy.ea.util.StringUtil;
import hy.ea.util.ToChineseFirstLetter;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.service.StockService;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lyc on 2018-10-08. ********  未使用  ********
 */
@Service
@Transactional
public class StockServiceImpl implements StockService{

    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private CLogBookService logBookService;
    @Resource
    private ServerService serverService;
    @Resource
    private UpLoadFileService fileService;
    @Resource
    private ProductlaunchService productlaunchService;
    @Resource
    private WarehouseService warehouseService;
    @Resource
    private CompanyService companyService;
    DateFormat df=DateFormat.getDateInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public PageForm getGoodsList(String companyId,String goodsName,String goodsType,String inventoryId,int pageNumber,int pageSize) {
        StringBuilder sql=new StringBuilder();
        sql.append("select p.image,d.goodsname,d.unitprice,d.purprice,d.invenquantity,d.unit,d.goodstype,p.barcode,d.invenunderline," +
                "d.invenonline,p.standard,d.warehousename,d.areaname,d.framename,d.positionname,d.inventoryid,p.goodsid,p.goodsnum" +
                " from dt_inv_invt d ,dt_productpackaging p where d.productid=p.ppid and d.companyid= ?");
        List<Object> params = new ArrayList<Object>();
        params.add(companyId);
        if(goodsName!=null&&!"".equals(goodsName)){
            sql.append(" and d.goodsname like ? or d.barcode like ?");
            params.add("%" + goodsName + "%");
            params.add("%" + goodsName + "%");
        }
        if(goodsType!=null&&!"".equals(goodsType)){
            sql.append(" and d.goodstype =?");
            params.add(goodsType);
        }
        if(inventoryId!=null&&!"".equals(inventoryId)){
            sql.append(" and d.inventoryid=?");
            params.add(inventoryId);
        }
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (pageNumber != 0 ? pageNumber : 1), pageSize, sql.toString(), "select count(1) from (" + sql.toString() + ")",
                params.toArray());
        return pageForm;
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public List<Object> getGoodsType(String companyId) {
        String sql="select dc.codevalue from Dtccode dc where dc.codepid= ? and dc.companyid= ?";
        List<Object> typeList=baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{"scode20101014v5zed7cukk0000000002",companyId});
        return typeList;
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public List<BaseBean> getDepots(String companyId, String depotType, String depotPId) {
        StringBuilder sql=new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        sql.append("select dp.depotid,dp.depotname from dtdepotmanage dp where dp.companyid=? and dp.depottype=? ");
        params.add(companyId);
        params.add(depotType);
        //如果不是查库 根据传入的父depotid 查找子元素
        if(!"1".equals(depotPId)&&depotPId!=null&&!"".equals(depotPId)){
            sql.append(" and dp.depotpid=?");
            params.add(depotPId);
        }
        List<BaseBean> depotList=baseBeanService.getListBeanBySqlAndParams(sql.toString(),params.toArray());
        return depotList;
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void saveGoods(File file,String fileFileName,Inventory inv) throws ParseException{
       //物品设计
        List<BaseBean> beans = new ArrayList<BaseBean>();
        GoodsManage gm = new GoodsManage();
        gm.setGoodsID(serverService.getServerID("goodsID"));
        System.out.print(gm.getGoodsID());
        //员工
        String staffHql="from Staff where staffID=?";
        Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams(staffHql, new Object[]{inv.getStaffID()});//获取员工
        // 编号处理
        String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in (select c.companyID from Company c where c.groupCompanySn=?)  and vt.typeID=? ";
        Company company = (Company) baseBeanService.getBeanByHqlAndParams(  //获取公司
                "from Company where companyID= ?",new Object[]{inv.getCompanyID()});
        if(company!=null&&staff!=null){
            Object[] params = {company.getGroupCompanySn(), gm.getTypeID()};
            if(inv.getGoodsType()!=null&&!"".equals(inv.getGoodsType())){ //物品类别不为空
                gm.setTypeID(inv.getGoodsType());
                // 获取拼音码
                String pinyin = ToChineseFirstLetter.getFirstLetter(gm
                        .getTypeID());
                String Upstr = pinyin.toUpperCase();// 转换为大写
                int goodscodingnum = baseBeanService.getConutByByHqlAndParams(
                        hql, params);
                DecimalFormat form = new DecimalFormat("000000");
                String ss = form.format(goodscodingnum + 1);
                gm.setGoodsCoding(Upstr + "_" + ss);
            }
            gm.setGoodsName(inv.getGoodsName());
            gm.setFiveClear("4");
            gm.setCompanyID(inv.getCompanyID());
            gm.setGoodsState("00");
            gm.setCreatedate(new Date());
            gm.setBarCode(inv.getBarcode());// 条码
            /*gm.setTradeID("");
            gm.setTradeName("");
            gm.setTradeCode("");
            gm.setCategoryName("");
            gm.setCategoryId("");*/
            // 保存物品主图
            if (file != null && file.length() > 0)
            {
                String path = ServletActionContext.getRequest()
                        .getSession().getServletContext().getRealPath("/");
                String photopath = "";
                AttriProduction attrp = null;
                photopath = fileService.savePhoto(
                        path,
                        fileFileName,
                        file,
                        inv.getCompanyID(),
                        "/gooddesign/"
                                + Utilities.getDateString(new Date(),
                                "yyyy-MM-dd"));
                String jjPath = photopath.split("\\.")[0] + "small." + photopath.split("\\.")[1];
                ImageCut.scale(path + photopath, path + jjPath, 414, 431);
                //图片保存在goodsmanage表，同时也保存attriproduction表
                gm.setPhotoPath(jjPath);
                attrp = new AttriProduction();
                attrp.setApid(serverService.getServerID("apid"));
                attrp.setType("2");
                attrp.setImgurl(jjPath);
                attrp.setGoodsid(gm.getGoodsID());
                attrp.setSort(1);
                beans.add(attrp);
                beans.add(gm);
            }
            // 产品设计
            if (gm != null && !gm.getGoodsID().equals(""))
            {
                ProductPackaging pp = new ProductPackaging();
                pp.setPpID(serverService.getServerID("p"));
                String hqlcode = "from CCode where codeID = ?";
                CCode code = (CCode) baseBeanService.getBeanByHqlAndParams(
                        hqlcode, new Object[]{gm.getTradeID()});
                String codes = "";
                if (code != null)
                {
                    codes = code.getCodeSn();
                }
                pp.setProductCode(productlaunchService.generateProductCode(codes, ""));
                pp.setGoodsID(gm.getGoodsID());
                pp.setGoodsName(gm.getGoodsName());
                pp.setImage(gm.getPhotoPath());
                pp.setType(inv.getGoodsType());
                pp.setShowweixin("00");//00表示未发布
                pp.setDelStatus("00");
                pp.setProductstate("01");
                pp.setFiveClear("4");
                pp.setCompanyID(inv.getCompanyID());
                pp.setPackagingDate(new Date());
                pp.setYjstatus("00");// 未设置佣金
                pp.setField("01");
                pp.setQualified("1");
                pp.setStandard(inv.getStandard());
                if(inv.getUnitPrice()!=null){
                    pp.setQuantity(inv.getInvenQuantity());
                    pp.setStockSize(Integer.valueOf(inv.getInvenQuantity()));
                    pp.setMoney(String.valueOf(new Integer(inv.getUnitPrice())*new Integer(inv.getInvenQuantity())));
                }
                /*pp.setTradeCode("");
                pp.setTradeID("");
                pp.setTradeName("");
                pp.setCategoryId("");
                pp.setCategoryName("");*/
                pp.setPrice(inv.getUnitPrice());
                beans.add(pp);
                if(pp != null && !pp.getPpID().equals("")){

                    /*UtboundOrderVo ut=null;
                    String hql2="from DepotManage where depotPID=? and companyID=? and depotName=? and depotState=?";
                    if(ut==null){
                        ut=new UtboundOrderVo();
                        ut.setJournalnum(serverService.getBillID(inv.getCompanyID()));
                    }
                    ut.setCashierDate(DateUtil.getCurrentDate("yyyy-MM-dd"));*/
                    String  InventoryId=serverService.getServerID("Inventory");
                    /**String organizationID=(String) session.get("organizationID");*/
                    String financialbillId=serverService.getServerID("FinancialBill");
                    String goodsbillsId=serverService.getServerID("GoodsBills");
                    String cashierbillsId=serverService.getServerID("CashierBills");

                    //存储出库单
                    CashierBills cashier=new CashierBills();
                    cashier.setCashierBillsID(cashierbillsId);
                    System.out.print(cashierbillsId);
                    cashier.setJournalNum(serverService.getBillID(inv.getCompanyID()));
                    cashier.setjNumOrder(cashier.getJournalNum());
                    cashier.setBillsType("调拨入库单");
                    //cashier.setGroupCompanySn(groupCompanySn);
                    cashier.setCompanyID(inv.getCompanyID());
                    /**cashier.setOrganizationID(organizationID);*/
                    cashier.setStaffID(inv.getStaffID());
                    cashier.setInputid(inv.getStaffID());
                    cashier.setStaffName(staff.getStaffName());
                    cashier.setInputName(staff.getStaffName());
                    cashier.setCompanyName(company.getCompanyName());
                    Date date = sdf.parse(Utilities.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    cashier.setCashierDate(date);
                    cashier.setStatus("15");
                    cashier.setFiveClear("4");
                    beans.add(cashier);

                    //添加进销存单据
                    FinancialBill fin=new FinancialBill();
                    fin.setFinancialbillID(financialbillId);
                    fin.setCashierBillsID(cashier.getCashierBillsID());
                    //fin.setGroupCompanySn(groupCompanySn);
                    fin.setCcompanyID(inv.getCompanyID());
                    /**fin.setOrganizationID(organizationID);*/
                    fin.setBillStaffID(inv.getStaffID());
                    fin.setStaffsID(inv.getStaffID());
                    fin.setBillStaffName(staff.getStaffName());
                    fin.setStaffsName(staff.getStaffName());
                    fin.setDepotID("001");
                    fin.setDepotName("生产流水线");
                    fin.setDrdepotID(inv.getWarehouse());
                    fin.setDrdepotName(inv.getWarehouseName());
                    fin.setBillsType("生产入库单");
                    fin.setJournalNum(cashier.getJournalNum());
                    fin.setBillsDate(new Date());
                    fin.setBillStatus("14");

                    beans.add(fin);
                    //添加物品单据
                    GoodsBills goods=new GoodsBills();
                    goods.setGoodsBillsID(goodsbillsId);
                    goods.setCompanyID(inv.getCompanyID());
                    goods.setCashierBillsID(cashier.getCashierBillsID());
                    goods.setPpID(pp.getPpID());
                    goods.setGoodsID(gm.getGoodsID());
                    goods.setTypeID(gm.getTypeID());
                    goods.setGoodsNum(gm.getNum());
                    goods.setGoodsName(gm.getGoodsName());
                    goods.setStandard(pp.getStandard());
                    goods.setPrice(pp.getPrice());
                    goods.setQuantity(pp.getQuantity());
                    goods.setMoney(StringUtil.formatFloatNumber((Double.parseDouble(pp.getQuantity())*Double.parseDouble(pp.getPrice()))));
                    goods.setDepotID(inv.getWarehouse());
                    goods.setDepotName(inv.getWarehouseName());
                    goods.setKcStatus("15");
                    goods.setGoodsStatus("00");
                    goods.setCategory("00");
                    //goods.setProInspectionID(dcheck.getId());
                    beans.add(goods);
                    //库存表
                    inv.setInventoryID(InventoryId);
                    inv.setStaffName(staff.getStaffName());
                    inv.setGroupCompanySn(company.getGroupCompanySn());
                    inv.setGoodsID(goods.getGoodsID());
                    inv.setGoodsName(goods.getGoodsName());
                    inv.setGoodsType(goods.getTypeID());
                    inv.setGoodsCoding(goods.getGoodsNum());
                    inv.setUnitPrice(goods.getPrice());//物品单价
                    inv.setProductId(goods.getPpID());
                    inv.setSumPrice(goods.getMoney());			//总价
                    inv.setGoodstatus("00");
                    beans.add(inv);
                    //盘点
                    stockInv stockinvs = new stockInv();
                    stockinvs.setStockinvID(this.serverService.getServerID("stockInv"));
                    stockinvs.setCompanyID(inv.getCompanyID());
                    stockinvs.setWarehouse(inv.getWarehouse());
                    stockinvs.setWarehouseName(inv.getWarehouseName());
                    stockinvs.setStaffID(inv.getStaffID());
                    stockinvs.setStaffName(staff.getStaffName());
                    stockinvs.setGroupCompanySn(company.getGroupCompanySn());
                    stockinvs.setGoodsID(inv.getGoodsID());
                    stockinvs.setGoodsType(goods.getTypeID());
                    stockinvs.setGoodsName(inv.getGoodsName());
                    stockinvs.setInvenQuantity(inv.getInvenQuantity());


                    //double unitPrice = Double.parseDouble(finbg.getPrice());
                    BigDecimal unitPrice  = new BigDecimal(goods.getPrice());
                    BigDecimal invenQuantitys  = new BigDecimal(inv.getInvenQuantity());
                    stockinvs.setSummoney(String.valueOf(invenQuantitys.multiply(unitPrice)));
                    //stockinvs.setIntime(df.format(new Date()));
                    //stockinvs.setInDate(new Date());
                    stockinvs.setType("00");
                    beans.add(stockinvs);

                    if(gm.getGoodsID()!=null&&!"0".equals(gm.getIsScale())){
                        String[] str={"GoodsNum",inv.getCompanyID(),inv.getInventoryID(),goods.getGoodsBillsID(),goods.getGoodsID(),"03",inv.getInvenQuantity(),goods.getGoodsNum(),inv.getStartDate(),inv.getEndDate()};
                        warehouseService.numberOfGeneratedItems(str);
                    }
                }
            }//产品设计结束
        }//物品设计结束
        baseBeanService.executeHqlsByParamsList(beans,null,null);
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void addStock(String inventoryId,String ppID, String newPrice, String newQuantity) throws ParseException {
        String hql=" from Inventory where inventoryId=? ";
        Inventory  inventory=(Inventory)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{inventoryId});
        String hqlp=" from ProductPackaging where ppID=? ";
        ProductPackaging pp=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(hqlp,new Object[]{ppID});
        int addStock=new Integer(newPrice) -new Integer(inventory.getInvenQuantity());
        if(addStock<0){
            throw new RuntimeException("不能小于原库存");
        }
        inventory.setInvenQuantity(newQuantity);//更新库存
        inventory.setUnitPrice(newPrice);//更新价格
        //更新产品表
        pp.setQuantity(newQuantity);
        pp.setStockSize(Integer.valueOf(newQuantity));
        pp.setMoney(String.valueOf(new Integer(newPrice)*new Integer(newQuantity)));
        //添加物品单据
        /*GoodsBills goods=new GoodsBills();
        goods.setGoodsBillsID(goodsbillsId);
        goods.setCompanyID(inv.getCompanyID());
        goods.setCashierBillsID(cashier.getCashierBillsID());
        goods.setPpID(pp.getPpID());
        goods.setGoodsID(gm.getGoodsID());
        goods.setTypeID(gm.getTypeID());
        goods.setGoodsNum(gm.getNum());
        goods.setGoodsName(gm.getGoodsName());
        goods.setStandard(pp.getStandard());
        goods.setPrice(pp.getPrice());
        goods.setQuantity(pp.getQuantity());
        goods.setMoney(StringUtil.formatFloatNumber((Double.parseDouble(pp.getQuantity())*Double.parseDouble(pp.getPrice()))));
        goods.setDepotID(inv.getWarehouse());
        goods.setDepotName(inv.getWarehouseName());
        goods.setKcStatus("15");
        goods.setGoodsStatus("00");
        goods.setCategory("00");*/
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void checkInv(InvCheckGoods ci, String inventoryId) {
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        //更新库存表
        String hql=" from Inventory where inventoryId=? ";
        Inventory  inventory=(Inventory)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{inventoryId});
        ci.setCheckinvId(this.serverService.getServerID("checkInv"));
        //Company company=companyService.getCompanyByCompanyID(ci.getCompanyID());
        //ci.setGroupCompanySn(company.getGroupCompanySn());
        //ci.setCheckTime(new Date());//日期转字符串
        ci.setGoodsID(inventory.getGoodsID());
        ci.setGoodsType(inventory.getGoodsType());
        ci.setGoodsName(inventory.getGoodsName());
        ci.setInvenOnline(inventory.getInvenOnline());
        ci.setInvenUnderline(inventory.getInvenUnderline());
        ci.setInvenQuantity(inventory.getInvenQuantity());
        //ci.setWarehouse(inventory.getWarehouse());
        //ci.setWarehouseName(inventory.getWarehouseName());
        baseBeanList.add(ci);
        //更新库存表
        if(inventory!=null){
            BigDecimal  b   =   new   BigDecimal(ci.getPrice());//转换类型
            double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();//四舍五入后保留小数点后俩位
            double sumPrice1=f1*new Integer(ci.getRealQuantity());//入库后库存商品总金额
            inventory.setSumPrice(String.valueOf(sumPrice1));
            inventory.setUnitPrice(ci.getPrice());
            inventory.setInvenQuantity(ci.getRealQuantity());
            baseBeanList.add(inventory);
        }
        baseBeanService.executeHqlsByParamsList(baseBeanList,null,null);
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void saveBreak(String breakType,String breakReason, String inventoryId,CashierBills cashier) throws ParseException{
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        String hql=" from Inventory where inventoryId=? ";
        Inventory  inventory=(Inventory)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{inventoryId});
        String goodsbillsId=serverService.getServerID("GoodsBills");
        System.out.print(goodsbillsId);
        String cashierbillsId=serverService.getServerID("CashierBills");
        System.out.print(cashierbillsId);
        cashier.setCashierBillsID(cashierbillsId);
        String staffHql="from Staff where staffID=?";
        Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams(staffHql, new Object[]{cashier.getStaffID()});
        cashier.setStaffName(staff.getStaffName());//报损人
        cashier.setJournalNum(serverService.getBillID(cashier.getCompanyID()));//单号
        cashier.setGoodsName(inventory.getGoodsName());
        cashier.setDiscountMoney(inventory.getWarehouse());//仓库id
        cashier.setAfterDiscount(inventory.getWarehouseName());//仓库名
        Date date = sdf.parse(Utilities.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        cashier.setCashierDate(date);//报损日期
        cashier.setStatus("26");//审核状态 24:报修  26：审核中  27:报损 28:驳回
        cashier.setRemark(breakReason);//原因
        String billType="";
        if("01".equals(breakType)){
            billType="报修单";
        }else if ("02".equals(breakType)){
            billType="报损单";
        }
        cashier.setBillsType(billType);//单据类型
        baseBeanList.add(cashier);
        //库存-1
        inventory.setInvenQuantity(String.valueOf(new Integer(inventory.getInvenQuantity())-1));//提交报损/报修后库存数量减一
        double sumPrice=new Integer(inventory.getUnitPrice())*new Integer(inventory.getInvenQuantity());
        inventory.setSumPrice(String.valueOf(sumPrice));//更新总价
        if(inventory.getBadQuantity()==null||"".equals(inventory.getBadQuantity())){
            inventory.setBadQuantity("0");
        }
        int nBadQuantity=new Integer(inventory.getBadQuantity())+1;
        inventory.setBadQuantity(String.valueOf(nBadQuantity));//损坏库存+1
        baseBeanList.add(inventory);

        GoodsBills goods=new GoodsBills();
        goods.setGoodsID(inventory.getGoodsID());
        goods.setGoodsName(inventory.getGoodsName());
        goods.setGoodsBillsID(goodsbillsId);
        goods.setCompanyID(cashier.getCompanyID());
        goods.setCashierBillsID(cashierbillsId);
        goods.setDepotID(inventory.getWarehouse());
        goods.setDepotName(inventory.getWarehouseName());
        goods.setQuantity("1");
        goods.setPrice(inventory.getUnitPrice());//单价
        goods.setMoney(inventory.getUnitPrice());//总价（个数为1）
        goods.setEntryTime(new Date());//录入时间
        String depotType="";
        if("01".equals(breakType)){
            depotType="报修";
        }else if("02".equals(breakType)){
            depotType="报损";
        }
        goods.setDepotType(depotType);
        baseBeanList.add(goods);
        baseBeanService.executeHqlsByParamsList(baseBeanList,null,null);
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Object getBreak(String companyId, String cashierId) {
        String sql="select cb.cashierbillsid,cb.goodsname,cb.journalnum,cb.remark,bc.comments,cb.afterdiscount,cb.status,cb.cashierdate " +
                "from dtCashierBills cb left join  dtBillCheck bc on cb.cashierbillsid=bc.newbillsid " +
                "where cb.companyid=? and cb.cashierbillsid=?";
        Object obj=baseBeanService.getObjectBySqlAndParams(sql,new Object[]{companyId,cashierId});
        return obj;
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void updateBreak(String companyId,String cashierId,String status,String auditorId,String comments) {
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        String hql=" from CashierBills where cashierBillsID=? and companyId=?";
        CashierBills cashier=(CashierBills)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{cashierId,companyId});//获取单据
        String hql2=" from GoodsBills where cashierBillsID=? and companyId=?";
        GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(hql2,new Object[]{cashierId,companyId});//获取物品单据
        if("02".equals(status)){
            if("报修单".equals(cashier.getBillsType())){
                cashier.setStatus("24");    //报修
            }else if("报损单".equals(cashier.getBillsType())){
                cashier.setStatus("27");    //报损
            }
        }else if("03".equals(status)){
            cashier.setStatus("28");    //驳回
            goods.setCanstatus("00"); //作废
        }
        baseBeanList.add(goods);
        baseBeanList.add(cashier);
        //审核表
        String billcheckId=serverService.getServerID("BillCheck");
        BillCheck check=new BillCheck();
        check.setCheckid(billcheckId);
        System.out.print(billcheckId);
        check.setFirstBillsID(cashier.getCashierBillsID());
        check.setOldBillsID(cashier.getCashierBillsID());
        check.setNewBillsID(cashier.getCashierBillsID());
        check.setStaffID(cashier.getStaffID());
        check.setStaffName(cashier.getStaffName());
        check.setJournalNum(cashier.getJournalNum());//凭证号
        check.setCashierDate(cashier.getCashierDate());//单据日期
        String billType=cashier.getBillsType().substring(0,2);
        check.setBillsType(billType+"审核单");//单据类型
        String staffHql="from Staff where staffID=?";
        check.setAuditorid(auditorId);//审核人id
        Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams(staffHql, new Object[]{auditorId});//获取审核人
        check.setAuditorname(staff.getStaffName());//审核人名
        check.setAuditorstatus(status);//审核状态 '01'未审核 '02'已审核 '03' 驳回
        check.setComments(comments);
        check.setAudittime(new Date());//审核日期
        baseBeanList.add(check);
        baseBeanService.executeHqlsByParamsList(baseBeanList,null,null);
    }


    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void saveCar(String companyId,String staffId,Map<String,String> goodsMap,String goodsIds) throws ParseException{
        Company company = (Company) baseBeanService.getBeanByHqlAndParams(  //获取公司
                "from Company where companyID= ?",new Object[]{companyId});
        String staffHql="from Staff where staffID=?";
        Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams(staffHql, new Object[]{staffId});//获取员工
        List<BaseBean> baseBeanList = new ArrayList();


        //String corhql = "from COrganization where organizationID = ?";
        //COrganization corgan = (COrganization)this.baseBeanService.getBeanByHqlAndParams(corhql, new Object[] { organizationID });
        CashierBills cashierbill = new CashierBills();
        cashierbill.setCashierBillsID(this.serverService.getServerID("cashierTally"));
        cashierbill.setGroupCompanySn(company.getGroupCompanySn());
        cashierbill.setCompanyID(companyId);
        cashierbill.setCompanyName(company.getCompanyName());
        /*cashierbill.setOrganizationID(organizationID);
        cashierbill.setDepartmentID(organizationID);
        cashierbill.setDepartmentName(corgan.getOrganizationName());*/
        cashierbill.setCompanyBankNum("");
        cashierbill.setPcompanyID(companyId);
        cashierbill.setPcompanyName(company.getCompanyName());
        cashierbill.setAfterDiscount("销售库");
        Date date = sdf.parse(Utilities.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        cashierbill.setCashierDate(date);
        cashierbill.setBillsType("销售出库单");
        cashierbill.setJournalNum(serverService.getBillID(companyId));
        cashierbill.setStaffID(staffId);
        String staffhql1 = "from Staff where staffID = ?";
        cashierbill.setStaffName(staff.getStaffName());
        cashierbill.setStaffCode(staff.getStaffCode());
        cashierbill.setInputid(staff.getStaffID());
        cashierbill.setInputName(staff.getStaffName());
        cashierbill.setInputCompanyid(companyId);
        cashierbill.setInputCompanyName(company.getCompanyName());
        String coshql = "from COS where staffID = ?";
        COS cos = (COS)this.baseBeanService.getBeanByHqlAndParams(coshql, new Object[] { staff });
        cashierbill.setInputOrganizationID(cos.getOrganizationID());
        //COrganization cor = (COrganization)this.baseBeanService.getBeanByHqlAndParams(corhql, new Object[] { cos.getOrganizationID() });
        //cashierbill.setInputOrganizationName(cor.getOrganizationName());
        cashierbill.setStatus("22");//保存草稿
        cashierbill.setStatusbill("01");
        baseBeanList.add(cashierbill);

        FinancialBill financialBill=new FinancialBill();
        financialBill.setCashierBillsID(cashierbill.getCashierBillsID());
        financialBill.setFinancialbillID(this.serverService.getServerID("financial"));
        financialBill.setJournalNum(cashierbill.getJournalNum());
        String parameter = ("添加销售出库单（凭证号" + financialBill.getJournalNum() + "）");
        //financialBill.setOrganizationID(organizationID);
        financialBill.setCompanyID(companyId);
        financialBill.setGroupCompanySn(company.getGroupCompanySn());
        financialBill.setBillsType("销售出库单");
        financialBill.setBillStatus("22");//保存草稿
        financialBill.setBillsDate(new Date());
        financialBill.setBillStaffName(staff.getStaffName());
        financialBill.setBillStaffID(staffId);
        /*关联公司 联系方式
        ContactCompanyView contactCompanyView1;
        if (companyId!= null &&!"".equals(companyId))
        {
            String hql3 = " from ContactCompanyView where ccompanyID=? ";
            contactCompanyView1 = (ContactCompanyView)this.baseBeanService 
                    .getBeanByHqlAndParams(hql3, new Object[] {companyId });
            financialBill.setCcompanyName(contactCompanyView1.getCompanyName());
            financialBill.setCcompanyRelationship(contactCompanyView1
                    .getContactConnections());
            financialBill.setCcompanyTel(contactCompanyView1.getCompanyTel());
        }*/
        baseBeanList.add(financialBill);

        String hql=" from Inventory where inventoryId=? ";
        if(goodsMap!=null){

            for(Map.Entry<String,String>entry :goodsMap.entrySet()  ){      //遍历购物车map集合
                Inventory  inventory=(Inventory)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{entry.getValue()});
                GoodsBills goods=new GoodsBills();
                goods.setCashierBillsID(cashierbill.getCashierBillsID());
                goods.setGoodsBillsID(this.serverService.getServerID("goodsbills"));
                goods.setCompanyID(companyId);
                goods.setGoodsID(inventory.getGoodsID());
                goods.setQuantity(entry.getKey());//出货数量
                goods.setPrice(inventory.getUnitPrice());
                double sumPrice=new Integer(inventory.getUnitPrice())*new Integer(goods.getQuantity());
                goods.setMoney(String.valueOf(sumPrice));//总价
                goods.setGoodsNum(inventory.getGoodsCoding());
                goods.setGoodsName(inventory.getGoodsName());
                goods.setStandard(inventory.getStandard());
                goods.setTypeID(inventory.getGoodsType());
                goods.setKcStatus("22");//保存稿
                goods.setServiceWay("mobile");/**服务方式手机端*/
                goods.setGoodstatus("00");
                int invenQuant = Integer.parseInt(inventory.getInvenQuantity());
                int quantity = Integer.parseInt(entry.getKey());    //购买数量
                if(quantity>invenQuant){
                    throw new RuntimeException("购买物品库存"+inventory.getGoodsName()+"不足");
                }
            }
        }
        baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void saveCar2(String companyId,String staffId,String subType,Map<String,String> goodsMap,String goodsIds,String arrgoodsNum) throws ParseException {
        Company company = (Company) baseBeanService.getBeanByHqlAndParams(  //获取公司
                "from Company where companyID= ?",new Object[]{companyId});
        String staffHql="from Staff where staffID=?";
        Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams(staffHql, new Object[]{staffId});//获取员工
        List<BaseBean> baseBeanList = new ArrayList();


        //String corhql = "from COrganization where organizationID = ?";
        //COrganization corgan = (COrganization)this.baseBeanService.getBeanByHqlAndParams(corhql, new Object[] { organizationID });
        CashierBills cashierbill = new CashierBills();
        cashierbill.setCashierBillsID(this.serverService.getServerID("cashierTally"));
        cashierbill.setCompanyID(companyId);
        cashierbill.setGroupCompanySn(company.getGroupCompanySn());
        cashierbill.setCompanyName(company.getCompanyName());
        /*cashierbill.setOrganizationID(organizationID);
        cashierbill.setDepartmentID(organizationID);
        cashierbill.setDepartmentName(corgan.getOrganizationName());*/
        cashierbill.setCompanyBankNum("");
        cashierbill.setPcompanyID(companyId);
        cashierbill.setPcompanyName(company.getCompanyName());
        cashierbill.setAfterDiscount("销售库");
        Date date = sdf.parse(Utilities.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        cashierbill.setCashierDate(date);
        cashierbill.setBillsType("销售出库单");
        cashierbill.setJournalNum(serverService.getBillID(companyId));
        cashierbill.setStaffID(staffId);
        String staffhql1 = "from Staff where staffID = ?";
        cashierbill.setStaffName(staff.getStaffName());
        cashierbill.setStaffCode(staff.getStaffCode());
        cashierbill.setInputid(staff.getStaffID());
        cashierbill.setInputName(staff.getStaffName());
        cashierbill.setInputCompanyid(companyId);
        cashierbill.setInputCompanyName(company.getCompanyName());
        String coshql = "from COS where staffID = ?";
        COS cos = (COS)this.baseBeanService.getBeanByHqlAndParams(coshql, new Object[] { staff });
        cashierbill.setInputOrganizationID(cos.getOrganizationID());
        //COrganization cor = (COrganization)this.baseBeanService.getBeanByHqlAndParams(corhql, new Object[] { cos.getOrganizationID() });
        //cashierbill.setInputOrganizationName(cor.getOrganizationName());
        cashierbill.setStatus("22");//保存草稿
        cashierbill.setStatusbill("01");
        baseBeanList.add(cashierbill);
        
        FinancialBill financialBill=new FinancialBill();
        financialBill.setCashierBillsID(cashierbill.getCashierBillsID());
        financialBill.setFinancialbillID(this.serverService.getServerID("financial"));
        financialBill.setJournalNum(cashierbill.getJournalNum());
        String parameter = ("添加销售出库单（凭证号" + financialBill.getJournalNum() + "）");
        //financialBill.setOrganizationID(organizationID);
        financialBill.setCompanyID(companyId);
        financialBill.setGroupCompanySn(company.getGroupCompanySn());
        financialBill.setBillsType("销售出库单");
        financialBill.setBillStatus("22");//保存草稿
        financialBill.setBillsDate(new Date());
        financialBill.setBillStaffName(staff.getStaffName());
        financialBill.setBillStaffID(staffId);
        /*关联公司 联系方式
        ContactCompanyView contactCompanyView1;
        if (companyId!= null &&!"".equals(companyId))
        {
            String hql3 = " from ContactCompanyView where ccompanyID=? ";
            contactCompanyView1 = (ContactCompanyView)this.baseBeanService
                    .getBeanByHqlAndParams(hql3, new Object[] {companyId });
            financialBill.setCcompanyName(contactCompanyView1.getCompanyName());
            financialBill.setCcompanyRelationship(contactCompanyView1
                    .getContactConnections());
            financialBill.setCcompanyTel(contactCompanyView1.getCompanyTel());
        }*/
        baseBeanList.add(financialBill);

        String hql=" from Inventory where inventoryId=? ";
        if(goodsMap!=null){

            for(Map.Entry<String,String>entry :goodsMap.entrySet()  ){      //遍历购物车map集合
                Inventory  inventory=(Inventory)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{entry.getValue()});
                GoodsBills goods=new GoodsBills();
                goods.setCashierBillsID(cashierbill.getCashierBillsID());
                goods.setGoodsBillsID(this.serverService.getServerID("goodsbills"));
                goods.setCompanyID(companyId);
                goods.setQuantity(entry.getKey());//出货数量
                goods.setPrice(inventory.getUnitPrice());
                double sumPrice=new Integer(inventory.getUnitPrice())*new Integer(goods.getQuantity());
                goods.setMoney(String.valueOf(sumPrice));//总价
                goods.setGoodsName(inventory.getGoodsName());
                goods.setGoodsNum(inventory.getGoodsCoding());
                goods.setStandard(inventory.getStandard());
                goods.setTypeID(inventory.getGoodsType());
                goods.setKcStatus("22");//保存草稿
                goods.setGoodstatus("00");
                int invenQuant = Integer.parseInt(inventory.getInvenQuantity());
                int quantity = Integer.parseInt(entry.getKey());    //购买数量
                if(quantity>invenQuant){
                    throw new RuntimeException("购买物品库存"+inventory.getGoodsName()+"不足");
                }
            }
            /*CLogBook logBook = this.logBookService.saveCLogBook(null, parameter, null);
            baseBeanList.add(logBook);*/
            String[] arrId = goodsIds.split(",");
            dd(baseBeanList,goodsMap,arrId,arrgoodsNum);

        }
        baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);
    }

    private void dd(List<BaseBean> baseBeanList, Map<String, String> goodsmap, String[] arrId, String arrGoodsNum)
    {
        int i = 0;
        String[] arr = arrGoodsNum.split(",");
        for(Map.Entry<String,String>entry :goodsmap.entrySet()){      //遍历购物车map集合
            String inventoryId =entry.getValue();
            String goodsId = arrId[i];
            String hql = " from GoodsNum where cashierBillsID = ? and goodsID = ? and goodsnumber = ?";
            GoodsNum goodsNum = (GoodsNum)this.baseBeanService.getBeanByHqlAndParams(hql, new Object[] { inventoryId, goodsId, arr[i] });
            goodsNum.setStatus("07");//彻底出库
            baseBeanList.add(goodsNum);
            i++;
        }
    }


}
