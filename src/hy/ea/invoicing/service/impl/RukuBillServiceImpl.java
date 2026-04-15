package hy.ea.invoicing.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import hy.ea.bo.finance.BenDis.ConsigneeSheet;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.brokerage.PWholesale;
import hy.ea.bo.invoicing.*;
import hy.ea.invoicing.service.RukuBillService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RukuBillServiceImpl implements RukuBillService{
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;

    /**
     * 单据列表
     *
     * @param companyid 公司id
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> getInitializeList(String companyid, String parameter, PageForm pageForm) throws Exception {
        DetachedCriteria dc = DetachedCriteria.forClass(Rukubill.class);
        dc.add(Restrictions.eq("companyid", companyid));
        if (parameter != null && !parameter.equals("")) {
            dc.add(Restrictions.or(Restrictions.like("journalNum", parameter),
                    Restrictions.like("companyName", parameter)));
        }
        dc.addOrder(Order.desc("adddate"));
        pageForm = baseBeanService.getPageFormByDC(
                (null != pageForm ? pageForm.getPageNumber() : 1), 10, dc);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        return map;
    }

    /**
     * 单据列表
     *
     * @param userID 帐号人员id
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> getBillList(String userID, String parameter, PageForm pageForm) throws Exception {
        DetachedCriteria dc = DetachedCriteria.forClass(ConsigneeSheet.class);
        dc.add(Restrictions.eq("userID", userID));
        dc.add(Restrictions.ne("state", "22"));
        /*if (parameter != null && !parameter.equals("")) {
            dc.add(Restrictions.or(Restrictions.like("journalNum", parameter),
                    Restrictions.like("companyName", parameter)));
        }*/
        dc.addOrder(Order.desc("orderDate"));
        pageForm = baseBeanService.getPageFormByDC(
                (null != pageForm ? pageForm.getPageNumber() : 1), 10, dc);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        return map;
    }

    public void SaveRuku(String formjum, String serializeJson) throws Exception {
        List<BaseBean> baseBeans = new ArrayList<BaseBean>();
        if (formjum != null && !formjum.equals("")) {
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(formjum);
            //String rkid, String journalnum, String companyid, String staffid, String staffname, String warehouse, String warehousename, Time adddate, String status
            Rukubill bill = new Rukubill(
                    serverService.getServerID("RukuBill"),
                    getString(jsonObject.getString("cid")),
                    getString(jsonObject.getString("tid")),
                    getString(jsonObject.getString("jum")),
                    getString(jsonObject.getString("companyid")),
                    getString(jsonObject.getString("gcname")),
                    getString(jsonObject.getString("gcid")),
                    getString(jsonObject.getString("cgstaffname")),
                    getString(jsonObject.getString("cgstaffid")),
                    getString(jsonObject.getString("inistaffid")),
                    getString(jsonObject.getString("inistaffname")),
                    getString(jsonObject.getString("warehouse")),
                    getString(jsonObject.getString("warehousename")),
                    Utilities.getDateFromString(jsonObject.getString("inidate"), "yyyy-MM-dd hh:mm:ss"),
                    "01");
            baseBeans.add(bill);
            if (serializeJson != null && !serializeJson.equals("")) {
                JSONArray serializeArray = JSON.parseArray(serializeJson);
                for (int i = 0; i < serializeArray.size(); i++) {
                    System.out.println(serializeArray.getJSONObject(i).get("goodsname"));
                    //String rkgoodsid, String rkid, String goodsid, String goodname, String unit, String quantity, String warehouse, String warehousename, String goodstype, String ppid, String ccompanyID, String ccompanyName, String contactUserID, String ctUserName, Date manufactureDate, String codeid, String codename
                    RukuGoods goods = new RukuGoods(
                            serverService.getServerID("RukuGoods"),
                            bill.getRkid(),
                            getString(serializeArray.getJSONObject(i).get("goodsID")),
                            getString(serializeArray.getJSONObject(i).get("goodsName")),
                            getString(serializeArray.getJSONObject(i).get("variableID")),
                            getString(serializeArray.getJSONObject(i).get("rknum")),
                            getString(serializeArray.getJSONObject(i).get("kfid")),
                            getString(serializeArray.getJSONObject(i).get("kfname")),
                            "",
                            getString(serializeArray.getJSONObject(i).get("ppID")),
                            "",
                            "",
                            "",
                            "",
                            Utilities.getDateFromString(getString(serializeArray.getJSONObject(i).get("manufactureDate")), "yyyy-MM-dd"),
                            getString(serializeArray.getJSONObject(i).get("codeid")),
                            getString(serializeArray.getJSONObject(i).get("codename")));
                    baseBeans.add(goods);
                    addInv(bill, goods, baseBeans);
                }
                baseBeans.addAll(SaveShouhuo(formjum, serializeJson));
                baseBeans.addAll(SaveYanhuo(formjum, serializeJson));
            }
            baseBeanService.executeHqlsByParamsList(baseBeans, null, null);
        }
    }

    public List<BaseBean> SaveShouhuo(String formjum, String serializeJson) throws Exception {
        List<BaseBean> baseBeans = new ArrayList<BaseBean>();
        if (formjum != null && !formjum.equals("")) {
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(formjum);
            //String rkid, String journalnum, String companyid, String staffid, String staffname, String warehouse, String warehousename, Time adddate, String status
            Shouhuobill bill = new Shouhuobill(
                    serverService.getServerID("ShouhuoBill"),
                    getString(jsonObject.getString("cid")),
                    getString(jsonObject.getString("tid")),
                    getString(serverService.getBillID(jsonObject.getString("companyid"))),
                    getString(jsonObject.getString("companyid")),
                    getString(jsonObject.getString("gcname")),
                    getString(jsonObject.getString("gcid")),
                    getString(jsonObject.getString("cgstaffname")),
                    getString(jsonObject.getString("cgstaffid")),
                    getString(jsonObject.getString("staffid")),
                    getString(jsonObject.getString("staffname")),
                    Utilities.getDateFromString(jsonObject.getString("inidate"), "yyyy-MM-dd hh:mm:ss"),
                    "01");
            baseBeans.add(bill);
            if (serializeJson != null && !serializeJson.equals("")) {
                JSONArray serializeArray = JSON.parseArray(serializeJson);
                for (int i = 0; i < serializeArray.size(); i++) {
                    System.out.println(serializeArray.getJSONObject(i).get("goodsname"));
                    //String shgoodsid, String shid, String goodsid, String goodname, String unit, String requantity, String goodstype, String ppid, String ccompanyID, String ccompanyName, String contactUserID, String ctUserName, Date manufactureDate, String codeid, String codename
                    ShouhuoGoods goods = new ShouhuoGoods(
                            serverService.getServerID("ShouhuoGoods"),
                            bill.getShid(),
                            getString(serializeArray.getJSONObject(i).get("goodsid")),
                            getString(serializeArray.getJSONObject(i).get("goodsname")),
                            getString(serializeArray.getJSONObject(i).get("variableid")),
                            getString(serializeArray.getJSONObject(i).get("shnum")),
                            getString(serializeArray.getJSONObject(i).get("type")),
                            getString(serializeArray.getJSONObject(i).get("ppID")),
                            "",
                            "",
                            "",
                            "",
                            Utilities.getDateFromString(getString(serializeArray.getJSONObject(i).get("manufactureDate")), "yyyy-MM-dd"),
                            getString(serializeArray.getJSONObject(i).get("codeid")),
                            getString(serializeArray.getJSONObject(i).get("codevalue")));
                    baseBeans.add(goods);
                }
            }
            //baseBeanService.executeHqlsByParamsList(baseBeans, null, null);
        }
        return baseBeans;
    }

    public List<BaseBean> SaveYanhuo(String formjum, String serializeJson) throws Exception {
        List<BaseBean> baseBeans = new ArrayList<BaseBean>();
        if (formjum != null && !formjum.equals("")) {
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(formjum);
            //String rkid, String journalnum, String companyid, String staffid, String staffname, String warehouse, String warehousename, Time adddate, String status
            Yanhuobill bill = new Yanhuobill(
                    serverService.getServerID("Yanhuobill"),
                    getString(jsonObject.getString("cid")),
                    getString(jsonObject.getString("tid")),
                    getString(jsonObject.getString("jum")),
                    getString(jsonObject.getString("companyid")),
                    getString(jsonObject.getString("gcname")),
                    getString(jsonObject.getString("gcid")),
                    getString(jsonObject.getString("cgstaffname")),
                    getString(jsonObject.getString("cgstaffid")),
                    getString(jsonObject.getString("staffid")),
                    getString(jsonObject.getString("staffname")),
                    Utilities.getDateFromString(jsonObject.getString("inidate"), "yyyy-MM-dd"),
                    "01");
            baseBeans.add(bill);
            if (serializeJson != null && !serializeJson.equals("")) {
                JSONArray serializeArray = JSON.parseArray(serializeJson);
                for (int i = 0; i < serializeArray.size(); i++) {
                    System.out.println(serializeArray.getJSONObject(i).get("goodsname"));
                    //String yhgoodsid, String yhid, String goodsid, String goodname, String unit, String isqualify, String goodstype, String ppid, String ccompanyID, String ccompanyName, String contactUserID, String ctUserName, Date manufactureDate, String codeid, String codename
                    YanhuoGoods goods = new YanhuoGoods(
                            serverService.getServerID("YanhuoGoods"),
                            bill.getYhid(),
                            getString(serializeArray.getJSONObject(i).get("goodsid")),
                            getString(serializeArray.getJSONObject(i).get("goodsname")),
                            getString(serializeArray.getJSONObject(i).get("variableid")),
                            getString(serializeArray.getJSONObject(i).get("yhnum")),
                            getString(serializeArray.getJSONObject(i).get("type")),
                            getString(serializeArray.getJSONObject(i).get("ppID")),
                            "",
                            "",
                            "",
                            "",
                            Utilities.getDateFromString(getString(serializeArray.getJSONObject(i).get("manufactureDate")), "yyyy-MM-dd"),
                            getString(serializeArray.getJSONObject(i).get("codeid")),
                            getString(serializeArray.getJSONObject(i).get("codevalue")));
                    baseBeans.add(goods);
                }
            }
            //baseBeanService.executeHqlsByParamsList(baseBeans, null, null);
        }
        return baseBeans;
    }

    public void addInv(Rukubill bill, RukuGoods goods, List<BaseBean> baseBeans) throws Exception {
        ProductPackaging pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams
                ("from ProductPackaging where ppid=?", new Object[]{goods.getPpid()});
        String InventoryId = serverService.getServerID("Inventory");
        Inventory inv = (Inventory) baseBeanService.getBeanByHqlAndParams
                ("from Inventory where productid=? and warehouse=? and companyID=?", new Object[]{goods.getPpid(), goods.getWarehouse(), bill.getCompanyid()});
        //库存表
        if (inv == null) {
            inv = new Inventory();
            inv.setInventoryID(InventoryId);
            inv.setCompanyID(bill.getCompanyid());
            //inv.setOrganizationID(org.getOrganizationID());
            //inv.setDepartmentID(org.getOrganizationID());
            inv.setStaffID(bill.getStaffid());
            inv.setStaffName(bill.getStaffname());
            inv.setWarehouse(goods.getWarehouse());
            inv.setWarehouseName(goods.getWarehousename());
            inv.setGoodsID(goods.getGoodsid());
            inv.setGoodsName(goods.getGoodname());
            inv.setGoodsType(pp.getType());
            inv.setStandard(pp.getStandard());
            inv.setGoodsCoding(pp.getGoodsNum());
            inv.setUnitPrice(pp.getPrice());//物品单价
            inv.setProductId(goods.getPpid());
            inv.setInvenQuantity(goods.getQuantity());    //物品数量
            if (inv.getUnitPrice() == null || inv.getUnitPrice().equals("")) {
                ProSetup ps = (ProSetup) baseBeanService.getBeanByHqlAndParams
                        ("from ProSetup p where p.ppid=?", new Object[]{pp.getPpID()});
                if (ps == null) {
                    PWholesale pw = (PWholesale) baseBeanService.getBeanByHqlAndParams
                            ("from PWholesale p where p.ppid=?", new Object[]{pp.getPpID()});
                    if (pw != null) {
                        inv.setUnitPrice(pw.getFactory().toString());
                    } else {
                        throw new Exception("成本价找不到！");
                    }
                } else {
                    inv.setUnitPrice(ps.getEfPrice());
                }
            }
            BigDecimal sum = new BigDecimal(inv.getInvenQuantity()).multiply(new BigDecimal(inv.getUnitPrice()));
            inv.setSumPrice(sum.toString());            //总价
            inv.setGoodstatus("00");
            inv.setProductId(goods.getPpid());
            inv.setBarcode(pp.getBarCode());
        } else {
            BigDecimal num = new BigDecimal(goods.getQuantity()).add(new BigDecimal(inv.getInvenQuantity()));
            BigDecimal sum = num.multiply(new BigDecimal(inv.getUnitPrice()));
            inv.setInvenQuantity(num.toString());    //物品数量
            inv.setSumPrice(sum.toString());            //总价
        }
        baseBeans.add(inv);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String getString(Object val) {
        return (val == null ? "" : val).toString();
    }
}