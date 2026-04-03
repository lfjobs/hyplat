package hy.ea.invoicing.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.brokerage.PWholesale;
import hy.ea.bo.invoicing.InitializeBill;
import hy.ea.bo.invoicing.InitializeGoods;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.invoicing.service.InitializeService;
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
public class InitializeServiceImpl implements InitializeService {
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
        DetachedCriteria dc = DetachedCriteria.forClass(InitializeBill.class);
        dc.add(Restrictions.eq("companyid", companyid));
        if (parameter != null && !parameter.equals("")) {
            dc.add(Restrictions.or(Restrictions.like("journalNum", parameter),
                    Restrictions.like("companyName", parameter)));
        }
        dc.addOrder(Order.desc("adddate"));
        pageForm = baseBeanService.getPageFormByDC(
                (null != pageForm ? pageForm.getPageNumber() : 1), 4, dc);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        return map;
    }

    public void SaveInitialize(String formjum, String serializeJson) throws Exception {
        List<BaseBean> baseBeans = new ArrayList<BaseBean>();
        if (formjum != null && !formjum.equals("")) {
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(formjum);
            InitializeBill bill = new InitializeBill(
                    serverService.getServerID("initBill"),
                    getString(jsonObject.getString("jum")),
                    getString(jsonObject.getString("companyid")),
                    getString(jsonObject.getString("staffid")),
                    getString(jsonObject.getString("staffname")),
                    getString(jsonObject.getString("codeid")),
                    getString(jsonObject.getString("codename")),
                    Utilities.getDateFromString(jsonObject.getString("inidate"), "yyyy-MM-dd hh:mm:ss"),
                    "01");
            baseBeans.add(bill);
            if (serializeJson != null && !serializeJson.equals("")) {
                JSONArray serializeArray = JSON.parseArray(serializeJson);
                for (int i = 0; i < serializeArray.size(); i++) {
                    System.out.println(serializeArray.getJSONObject(i).get("goodsname"));
                    InitializeGoods goods = new InitializeGoods(
                            serverService.getServerID("initGoods"),
                            bill.getInitializeid(),
                            getString(serializeArray.getJSONObject(i).get("kfid")),
                            getString(serializeArray.getJSONObject(i).get("kfname")),
                            getString(serializeArray.getJSONObject(i).get("goodsid")),
                            getString(serializeArray.getJSONObject(i).get("goodsname")),
                            getString(serializeArray.getJSONObject(i).get("variableid")),
                            getString(serializeArray.getJSONObject(i).get("cskc")),
                            getString(serializeArray.getJSONObject(i).get("codeid")),
                            getString(serializeArray.getJSONObject(i).get("codevalue")),
                            getString(serializeArray.getJSONObject(i).get("ppid")),
                            getString(serializeArray.getJSONObject(i).get("fbtype")));
                    baseBeans.add(goods);
                    addInv(bill, goods, baseBeans);
                }
            }
            baseBeanService.executeHqlsByParamsList(baseBeans, null, null);
        }
    }

    public void addInv(InitializeBill bill, InitializeGoods goods, List<BaseBean> baseBeans) throws Exception {
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

    public Map<String, Object> getConditionQuery(String companyid, String sdate, String edate, String pemer, String title, String type, PageForm pageForm, int pageNumber) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT G.INITGOODSID,B.INITIALIZEID,G.PPID,G.GOODNAME,G.WAREHOUSENAME,");
        sql.append(" G.QUANTITY,G.SHOWWEIXIN,G.CODENAME,B.JOURNALNUM,B.STAFFNAME,to_char(B.ADDDATE,'yyyy-MM-dd HH24:mi:ss'),C.COMPANYNAME");
        sql.append(" FROM DT_INITIALIZE_GOODS G");
        sql.append(" LEFT JOIN DT_INITIALIZE_BILL B ON G.INITIALIZEID = B.INITIALIZEID");
        sql.append(" LEFT JOIN DTCOMPANY C ON C.COMPANYID=B.COMPANYID");
        sql.append(" WHERE 1=1");
        if (sdate != null && !sdate.equals("") && edate != null && !edate.equals("")) {
            sql.append(" and B.ADDDATE BETWEEN ? AND ?");
            params.add(Utilities.getDateFromString(sdate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            params.add(Utilities.getDateFromString(edate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }else if (pemer != null && !pemer.equals("") && title != null && !title.equals("")) {
            //OR G.CODEID=?
            if (title.equals("单号")) {
                sql.append(" and B.JOURNALNUM LIKE ?");
            } else if (title.equals("类别")) {
                sql.append(" and G.CODENAME LIKE ?");
            } else if (title.equals("责任人")) {
                sql.append(" and B.STAFFNAME LIKE ?");
            }
            params.add("%" + pemer + "%");
        }
        sql.append(" AND B.COMPANYID=?");
        params.add(companyid);

        if (type.equals("P")) {
            map.put("pageForm", baseBeanService.getPageFormBySQL(
                    pageNumber, 20, sql.toString(), "select count(*) from (" + sql + ")",
                    params.toArray()));
        } else {
            map.put("list", baseBeanService.getListBeanBySqlAndParams(sql.toString(), params.toArray()));
        }
        return map;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String getString(Object val) {
        return (val == null ? "" : val).toString();
    }
}
