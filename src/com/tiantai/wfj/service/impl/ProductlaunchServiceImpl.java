package com.tiantai.wfj.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiantai.wfj.bo.Promotion;
import com.tiantai.wfj.bo.ScaleWeight;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.service.ProductlaunchService;
import com.tiantai.wfj.service.WfjJifenService;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.DrivingSchool.SchProCategory;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.BenDis.JoinFans;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.BenDis.ProSetupSubBackup;
import hy.ea.bo.finance.*;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.InvCheckGoods;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.InvtFbCheck;
import hy.ea.bo.production.view.UtboundOrderVo;
import hy.ea.company.service.DepotManageService;
import hy.ea.util.Constant;
import hy.ea.util.DateUtil;
import hy.ea.util.StringUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProductlaunchServiceImpl implements ProductlaunchService {

    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService idgec;
    @Resource
    private DepotManageService depotManageService;
    private boolean StopRecursion = false; //用于标记递归是否结束
    private String ReturnRecursion; //递归参数
    @Resource
    private WfjJifenService wfjserv;
    /**
     * @param ppid:产品id
     * @return 返回的集合
     * @Title: 查询
     * @Description: 查询产品及其下的促销品
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> productInquiry(String ppid) {
        StringBuilder sb = new StringBuilder();
        sb.append("select p.ppID,p.image,p.goodsName,t.rePrice,c.companyName,p.companyID,c.ccompanyID,p.goodsID ");
        sb.append("from ProductPackaging p,ContactCompany c,CcomCom m,ProSetup t ");
        sb.append("where p.companyID=m.comanyId and c.ccompanyID=m.ccompanyId ");
        sb.append("and t.ppid=p.ppID and  p.ppID = ? ");
        Object product = baseBeanDao.getObjectByHqlAndParams(sb.toString(),
                new Object[]{ppid});

        sb.setLength(0);// 清空sb
        sb.append("select n.ptppId,p.image,p.goodsName,t.rePrice,c.companyName,");
        sb.append("p.companyID,c.ccompanyID,p.goodsID ");
        sb.append("from ProductPackaging p,ContactCompany c,CcomCom m,ProSetup t,Promotion n ");
        sb.append("where p.companyID=m.comanyId and c.ccompanyID=m.ccompanyId ");
        sb.append("and t.ppid=p.ppID and p.ppID=n.ptppId  and p.showweixin = ? ");
        sb.append("and  n.ppId = ?");
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(
                sb.toString(), new Object[]{"01", ppid});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("product", product);
        map.put("list", list);
        return map;
    }

    /**
     * @param ppid :产品id
     * @return 返回的集合
     * @Title: 查询
     * @Description: 查询产品
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> queryPromotionProduct(String ppid) {
        Object[] ppids = ppid.split(",");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ppids.length; i++) {
            sb.append("?,");
        }
        sb.setLength(sb.length() - 1);

        StringBuilder hql = new StringBuilder();
        hql.append("select p.ppID,p.image,p.goodsName,t.rePrice,c.companyName,p.companyID,c.ccompanyID,p.goodsID ");
        hql.append("from ProductPackaging p,ContactCompany c,CcomCom m,ProSetup t ");
        hql.append("where p.companyID=m.comanyId and c.ccompanyID=m.ccompanyId ");
        hql.append("and t.ppid=p.ppID and p.ppID in (" + sb.toString() + ")");

        List<BaseBean> list1 = baseBeanDao.getListBeanByHqlAndParams(
                hql.toString(), ppids);

        return list1;

    }

    /**
     * @param pageNumber :当前页,pageSize:每页显示条数,goodsName:查询条件
     * @return 返回的集合
     * @Title: 模糊查询加分页
     * @Description: 查询产品及其下的促销品
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm allGiftsProducts(int pageNumber, int pageSize,
                                     String goodsName, String ptppId) {
        int count;
        String[] ppId = ptppId.split(",");
        String ss = "";
        List<String> list = new ArrayList<String>();
        list.add("01");
//        list.add("个人会员");
       list.add("公司会员");
        list.add("省级城市");
        list.add("县级城市");
        list.add("乡镇城市");
        list.add("村级新城");
        if (ptppId.length() > 0) {
            ss = "and p.ppID not in(";
            for (int i = 0; i < ppId.length; i++) {
                ss += "?,";
                list.add(ppId[i]);
            }
            ss = ss.substring(0, ss.length() - 1);
            ss += ")";
        }


        List<BaseBean> listBaseBean;
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) ");
        sb.append("from ProductPackaging p,ContactCompany c,CcomCom m,ProSetup t ");
        sb.append("where p.companyID=m.comanyId and c.ccompanyID=m.ccompanyId ");
        sb.append("and t.ppid = p.ppID and p.showweixin = ? and p.type not in(?,?,?,?,?) and p.goodsName not in('国税','地税','VIP客户') ");
        sb.append(ss);
        if (goodsName != null && !goodsName.equals("")) {
            sb.append(" and p.goodsName like ?");
            list.add('%' + goodsName + '%');
            count = baseBeanDao
                    .getConutByByHqlAndParams(sb.toString(), list.toArray()); // 总条数
        } else {
            count = baseBeanDao.getConutByByHqlAndParams(sb.toString(), list.toArray()); // 总条数
        }
        if (count == 0)
            return null;
        int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
        if (pageNumber < 1)
            pageNumber = 1;
        if (pageNumber > pageCount)
            pageNumber = pageCount;
        int firstResult = pageSize * (pageNumber - 1);
        int maxResult = Math.min(pageSize, count - firstResult);
        sb.setLength(0);// 清空sb

        sb.append("select p.image,p.goodsName,t.rePrice,p.companyID,p.ppID,c.ccompanyID,p.goodsID,t.efPrice ");
        sb.append("from ProductPackaging p,ContactCompany c,CcomCom m,ProSetup t ");
        sb.append("where p.companyID=m.comanyId and c.ccompanyID=m.ccompanyId ");
        sb.append("and t.ppid=p.ppID and p.showweixin = ? and p.type not in(?,?,?,?,?) and p.goodsName not in('国税','地税','VIP客户') ");
        sb.append(ss);
        if (goodsName != null && !goodsName.equals("")) {
            sb.append("and p.goodsName like ? ");
            listBaseBean = baseBeanDao.getConutByByHqlAndParamsAndPage(
                    sb.toString(), list.toArray(), firstResult, maxResult);
        } else {
            listBaseBean = baseBeanDao.getConutByByHqlAndParamsAndPage(
                    sb.toString(), list.toArray(),
                    firstResult, maxResult);
        }
        PageForm pageForm = new PageForm();
        pageForm.setPageSize(pageSize);
        pageForm.setRecordCount(count);
        pageForm.setPageCount(pageCount);
        pageForm.setPageNumber(pageNumber);
        pageForm.setList(listBaseBean);
        return pageForm;
    }

    /**
     * 促销套餐详情
     *
     * @param ppId      产品id
     * @param companyId 公司id
     */

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Object> PromotionsDetail(String companyId, String ppId) {
        List<Object> list = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("with a as(select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type,p.monthSales");
        sql.append(" from DT_PRO_SETUP ps, dt_ProductPackaging p");
        sql.append(" where p.ppid = ps.ppid and ps.fcom_id is null");
        sql.append(" and ps.ppid in(select pt.ptppid from dt_promotion pt");
        sql.append(" where pt.companyid = ? and pt.ppid =?))");
        sql.append(" select a.*, count(ar.goodsid)");
        sql.append(" from a left join dtattriproduction ar on ar.goodsid = a.goodsid and (ar.type = ? or ar.type = ?)");
        sql.append(" group by a.ppname,a.ppid,a.re_price,a.image,a.goodsid,a.companyid,a.type,a.monthSales");

        list = getListBeanBySqlAndParams(sql.toString(), new Object[]{
                companyId, ppId, "0", "1"});
        sql.delete(0, sql.length());
        // 主产品信息
        sql.append("with a as(select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type,p.monthSales");
        sql.append(" from DT_PRO_SETUP ps, dt_ProductPackaging p");
        sql.append(" where p.ppid = ps.ppid and ps.fcom_id is null and p.companyid=? and ps.ppid=?)");
        sql.append(" select a.*,count(ar.goodsid) from a left join dtattriproduction ar on ar.goodsid=a.goodsid and (ar.type=? or ar.type=?)");
        sql.append(" group by a.ppname,a.ppid,a.re_price,a.image,a.goodsid,a.companyid,a.type,a.monthSales");
        Object obj = baseBeanDao.getObjectBySqlAndParams(sql.toString(),
                new Object[]{companyId, ppId, "0", "1"});
        list.add(0, obj != null ? obj : null);
        return list;
    }

    /**
     * 产品管理
     *
     * @param user 用户
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public String productsManage(String user) {
        String str = "";
        TEshopCusCom te = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams(
                "from TEshopCusCom where account=? AND logOff=0 and acquiesce=?", new Object[]{user, "01"});
        if (te != null && !te.getStaffid().equals("")) {
            str = te.getCompanyId();
        }
        return str;
    }

    /**
     * 上架，下架
     *
     * @param ppId 产品id
     * @param flag 判断标识 onsale:下架;oncang:上架
     * @return
     */
    @Transactional
    @Override
    public boolean upOrdown(String ppId, String flag) {
        boolean f;
        try {
            f = true;
            String hql = "update ProductPackaging set showweixin=? where ppID=?";
            Object[] obj = new Object[2];
            List<Object[]> params = new ArrayList<Object[]>();
            if (flag != null && flag.equals("onsale")) {
                obj[0] = "00";
            } else if (flag != null && flag.equals("oncang")) {
                obj[0] = "01";
            }
            obj[1] = ppId;
            params.add(obj);
            baseBeanDao.executeHqlsByParmsList(null, new String[]{hql},
                    params);
        } catch (Exception e) {
            f = false;
        }
        return f;
    }

    /**
     * 产品pageForm
     *
     * @param flag       判断标识
     * @param pageNumber 当前页
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public PageForm productsPageForm(String flag,
                                     Integer pageNumber, String search, String companyId) {
        PageForm pageForm = new PageForm();
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select p.goodsname,ps.ppid,ps.re_price,ap.imgurl,p.goodsid,p.companyid,p.type,p.stockSize,p.monthSales");
        sql.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p left join  ");
        //从dtAttriProduction中取第一条图片数据
        sql.append(" (select * from( select b.*,( row_number() over(partition by goodsid order by sort asc)) row_num");
        sql.append(" from dtAttriProduction b ) e where row_num = 1) ap on p.goodsid = ap.goodsid");
        sql.append(" where ps.com_id= ? and p.ppid=ps.ppid and p.showweixin = ?");
        if (companyId != null && !companyId.equals("")) {
            params.add(companyId);
            if (flag != null && flag.equals("onsale")) {
                params.add("01");
            } else if (flag != null && flag.equals("oncang")) {
                params.add("00");
            }
            if (search != null && !search.equals("")) {
                sql.append("  and p.goodsname like  ?");
                params.add("%" + search + "%");
            }
            sql.append(" order by ps.sjdate desc");
            String sqlcount = "select count(*) from (" + sql.toString()
                    + ")";
            int count = baseBeanDao.getConutByBySqlAndParams(sqlcount,
                    params.toArray());// 总条数

            if (count == 0) {
                return null;
            }
            int pageCount = count / 10 + ((count % 10) == 0 ? 0 : 1);
            if (pageNumber < 1)
                pageNumber = 1;
            if (pageNumber > pageCount)
                pageNumber = pageCount;
            int firstResult = 10 * (pageNumber - 1);
            int maxResult = Math.min(10, count - firstResult);
            List<BaseBean> listBaseBean = baseBeanDao
                    .getConutByBySqlAndParamsAndPage(sql.toString(),
                            params.toArray(), firstResult,
                            maxResult);
            pageForm.setPageSize(10);
            pageForm.setRecordCount(count);
            pageForm.setPageCount(pageCount);
            pageForm.setPageNumber(pageNumber);
            pageForm.setList(listBaseBean);
        }
        return pageForm;
    }

    /**
     * * 新移动版库存分页
     * @param pageNumber 当前页数
     * @param pname 公司名称
     * @param depotid 库房id
     * @param hgcode 货柜号
     * @param companyId 公司
     * @return
     */
    public Map<String,Object> ProductManageMobilePageForm(Integer pageNumber,String pname,String depotid,String hgcode, String companyId){
        List<Object> params = new ArrayList<Object>();
        Map<String,Object> map=new HashMap<>();
        try {
            List<BaseBean> dlList = baseBeanService.getListBeanByHqlAndParams
                    ("from ProductPackaging where type=? and goodsname<>? order by sorting",
                            new Object[]{"佣金分配代理类别", "佣金分配代理类别"});
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT P.GOODSNAME,P.BARCODE,P.PPID,TO_CHAR(PS.RE_PRICE * (1 + NVL(SZ.TOTAL_PCT, 0) / 100),'fm999999990.00'),");
            sql.append("PS.RE_PRICE,PS.EF_PRICE,PS.BROKERAGE,PS.PROXY_SUM_PRICE,TO_CHAR(PS.RE_PRICE * (NVL(SZ.TOTAL_PCT, 0) / 100),'fm999999990.00'),");
            /*for (int i = 0; i < dlList.size(); i++) {
                ProductPackaging p=(ProductPackaging) dlList.get(i);
                sql.append("MAX(DECODE(PSS.TYPE_PPID,'");
                sql.append(p.getPpID());
                sql.append("',PSS.AMOUNT)) dl"+i+",");
            }*/
            /*sql.append("MAX(DECODE(PSS.TYPE_PPID,'p20170605KY3VAANZJG0000000003',NVL(TO_CHAR(PSS.AMOUNT / 100, 'FM99999990.099'), 0))),");
            sql.append("MAX(DECODE(PSS.TYPE_PPID,'p20170220ZVZR76B88M0000000018',NVL(TO_CHAR(PSS.AMOUNT / 100, 'FM99999990.099'), 0))),");
            sql.append("MAX(DECODE(PSS.TYPE_PPID,'p20170220ZVZR76B88M0000000019',NVL(TO_CHAR(PSS.AMOUNT / 100, 'FM99999990.099'), 0))),");
            sql.append("MAX(DECODE(PSS.TYPE_PPID,'p20170220ZVZR76B88M0000000020',NVL(TO_CHAR(PSS.AMOUNT / 100, 'FM99999990.099'), 0))),");
            sql.append("MAX(DECODE(PSS.TYPE_PPID,'p20170220ZVZR76B88M0000000022',NVL(TO_CHAR(PSS.AMOUNT / 100, 'FM99999990.099'), 0))),");
            sql.append("MAX(DECODE(PSS.TYPE_PPID,'p20170220ZVZR76B88M0000000017',NVL(TO_CHAR(PSS.AMOUNT / 100, 'FM99999990.099'), 0))),");
            sql.append("MAX(DECODE(PSS.TYPE_PPID,'p20170220ZVZR76B88M0000000016',NVL(TO_CHAR(PSS.AMOUNT / 100, 'FM99999990.099'), 0))),");*/
            sql.append("AP.IMGURL,P.GOODSID,P.COMPANYID,P.TYPE,D.DEPOTCODING,I.WAREHOUSENAME,I.INVENQUANTITY,P.VARIABLEID,P.STANPRO,P.SINGLEWEIGHT,p.paytype,PS.suid,P.SHOWWEIXIN");
            sql.append(" FROM DT_PRODUCTPACKAGING P LEFT JOIN DT_PRO_SETUP PS ON P.PPID = PS.PPID AND PS.COM_ID = P.COMPANYID");
            /*sql.append(" LEFT JOIN DT_PRO_SETUP_SUB PSS ON PSS.SUID = PS.SUID");*/
            //从dtAttriProduction中取第一条图片数据
            sql.append(" LEFT JOIN (SELECT * FROM (SELECT B.*, (ROW_NUMBER() OVER(PARTITION BY GOODSID ORDER BY SORT ASC))");
            sql.append(" ROW_NUM FROM DTATTRIPRODUCTION B) E WHERE ROW_NUM = 1) AP ON P.GOODSID = AP.GOODSID");
            sql.append(" LEFT JOIN DT_CCOM_COM M ON P.COMPANYID = M.COMPNAY_ID");
            sql.append(" LEFT JOIN DTCONTACTCOMPANY Y ON M.CCOMPANY_ID = Y.CCOMPANYID");
            sql.append(" LEFT JOIN DT_SET_SUBSIDIZE SZ ON Y.INDUSTRYTYPE = SZ.GTID AND STUTAS = '01'");
            sql.append(" RIGHT JOIN DT_INV_INVT I ON P.PPID = I.PRODUCTID");
            sql.append(" LEFT JOIN DTDEPOTMANAGE D ON D.DEPOTID = I.WAREHOUSE AND D.COMPANYID=I.COMPANYID");
            sql.append(" WHERE P.COMPANYID = ?");
            params.add(companyId);
            /*sql.append(" AND P.SHOWWEIXIN = ?");
            params.add("01");*/
            sql.append(" AND P.DELSTATUS = ?");
            params.add("00");
            if (pname != null && !pname.equals("")) {
                sql.append("  and (p.goodsname like ? or p.barcode like ?)");
                params.add("%" + pname + "%");
                params.add("%" + pname + "%");
            }
            if (depotid != null && !depotid.equals("")) {
                sql.append(" AND D.DEPOTID =?");
                params.add(depotid);
            }else if (hgcode != null && !hgcode.equals("")) {
                DepotManage d = (DepotManage) depotManageService.getDepotToCoding(hgcode, companyId, 0);
                if (d == null) {
                    map.put("flag",1);
                    map.put("error",2);
                    map.put("msg","没有找到货柜信息");
                    return map;
                }else {
                    List<BaseBean> doorl=depotManageService.getListDepotToPid(d.getDepotID(), companyId,2);
                    if (doorl != null&&doorl.size()>0) {
                        sql.append(" AND D.DEPOTID IN (");
                        for (int j = 0; j < doorl.size(); j++) {
                            DepotManage dd = (DepotManage)doorl.get(j);
                            if (dd.getItemID() != null&&dd.getItemID().equals(Constant.DEPOT_DOOR)) {
                                List<BaseBean> dl=depotManageService.getListDepotToPid(dd.getDepotID(), companyId,3);
                                if (dl != null) {
                                    int i = 0;
                                    for (BaseBean bb : dl) {
                                        i++;
                                        DepotManage dm = (DepotManage) bb;
                                        if (j==doorl.size()&&i == dl.size()) {
                                            sql.append("  ?)");
                                        } else {
                                            sql.append("  ?,");
                                        }
                                        params.add(dm.getDepotID());
                                    }
                                } else {
                                    map.put("msg", "02");
                                    map.put("error", "没有找到秤盘信息");
                                    return map;
                                }
                            }
                        }
                    }else {
                        /*map.put("msg", "03");
                        map.put("error", "没有找到柜门信息");
                        return map;*/

                        if (d.getItemID() != null&&d.getItemID().equals(Constant.DEPOT_VM)) {
                            List<BaseBean> dl=depotManageService.getListDepotToPid(d.getDepotID(), companyId,3);
                            if (dl != null&&dl.size()>0) {
                                int i = 0;
                                sql.append(" AND D.DEPOTID IN (");
                                for (BaseBean bb : dl) {
                                    i++;
                                    DepotManage dm = (DepotManage) bb;
                                    if (i == dl.size()) {
                                        sql.append("  ?)");
                                    } else {
                                        sql.append("  ?,");
                                    }
                                    params.add(dm.getDepotID());
                                }
                            } else {
                                map.put("msg", "02");
                                map.put("error", "没有找到秤盘信息");
                                return map;
                            }
                        }
                    }
                }
            }
            /*sql.append(" GROUP BY P.GOODSNAME,P.BARCODE,P.PPID,TO_CHAR(PS.RE_PRICE * (1 + NVL(SZ.TOTAL_PCT, 0) / 100),'fm999999990.00'),");
            sql.append(" AP.IMGURL,P.GOODSID,P.COMPANYID,P.TYPE,D.DEPOTCODING,I.WAREHOUSENAME,I.INVENQUANTITY,P.VARIABLEID,P.STANPRO,P.SINGLEWEIGHT");*/
            sql.append(" ORDER BY P.PPID DESC");
            PageForm pageForm=baseBeanService.getPageFormBySQL(pageNumber,35,sql.toString(),"select count(*) from ("+sql.toString()+")",params.toArray());
            map.put("pageForm",pageForm);
            map.put("flag",0);
            map.put("dlList", dlList);
        }catch (Exception e){
            map.put("flag",1);
            map.put("error",1);
            map.put("msg",e.getMessage());
        }
        return map;
    }

    /**
     * 产品规格颜色
     *
     * @param ppId 产品id
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Object> getAttr(String ppId) {
        StringBuilder sql = new StringBuilder();
        sql.append("with a as(select ps.ppname,ps.ppid,ps.re_price");
        sql.append(",p.image,p.goodsid as gid,p.companyid,p.monthSales,p.stocksize");
        sql.append(" from DT_PRO_SETUP ps, dt_ProductPackaging p");
        sql.append(" where p.ppid = ps.ppid and ps.fcom_id is null and ps.ppid =?)");
        sql.append(" select a.* ,ap.attrivalue,ap.type,ap.attriname from a");
        sql.append(" left join dtattriproduction ap on ap.goodsid=a.gid and (ap.type=? or ap.type=?)");
        List<Object> list = getListBeanBySqlAndParams(sql.toString(),
                new Object[]{ppId, "0", "1"});
        return list;
    }

    /**
     * 验证商品名称
     *
     * @param ppName
     * @param companyId
     * @return
     */
    public Boolean checkProName(String ppName, String companyId) {
        Boolean b = true;
        StringBuffer sql = new StringBuffer();
        sql.append("select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type,p.stockSize,p.monthSales");
        sql.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p where p.ppid=ps.ppid and ps.com_id= ?");
        sql.append(" and ps.ppname=?");
        List<BaseBean> list = baseBeanDao.getListBeanBySqlAndParams(sql.toString(), new Object[]{companyId, ppName});
        if (list.size() > 0) {
            b = false;
        }
        return b;
    }

    /**
     * 生成编号
     *
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public String generateProductCode(String tradeNum, String parentID) {
        String sql = "";
        String fcode = "";
        DecimalFormat format6 = new DecimalFormat("000000");
        DecimalFormat format2 = new DecimalFormat("00");
        if (parentID == null || parentID.equals("")) {
            sql = " select count(productCode) from dt_ProductPackaging where parentID is null and productCode is not null";
            int a = baseBeanDao.getConutByBySqlAndParams(sql, null);
            int aa = a + 1;
            fcode = tradeNum + format6.format(aa);
        } else {
            String hql = "from ProductPackaging where ppID = ?";
            ProductPackaging pp = (ProductPackaging) baseBeanDao
                    .getBeanByHqlAndParams(hql, new Object[]{parentID});

            sql = " select count(productCode) from dt_ProductPackaging where parentID = ?";
            int a = baseBeanDao.getConutByBySqlAndParams(sql,
                    new Object[]{parentID});
            int aa = a + 1;
            String nextnum = format2.format(aa);
            fcode = pp.getProductCode() + nextnum;
        }
        return fcode;
    }

    @SuppressWarnings("rawtypes")
    public List getListBeanBySqlAndParams(String sql, Object[] params) {
        List list = new ArrayList();
        list = baseBeanDao.getListBeanBySqlAndParams(sql, params);
        return list;
    }

    /**
     * @param promotions :促销品数据
     * @return 无返回值
     * @Title: 添加促销品
     * @Description: 为产品添加促销品套餐
     */
    @Transactional
    public void save(Promotion promotions) {
        String hql = "from Promotion o where o.ppId = ?";
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql,
                new Object[]{promotions.getPpId()});
        if (list != null && list.size() > 0) {
            String sql = "delete from DT_promotion n where n.ppId = ?";
            List<Object[]> parmsList = new ArrayList<Object[]>();
            parmsList.add(new Object[]{promotions.getPpId()});
            baseBeanDao.executeSqlsByParmsList(null, new String[]{sql},
                    parmsList);
        }
        if (promotions.getPtppId() != null && !promotions.getPtppId().equals("")) {
            String[] ptppId = promotions.getPtppId().split(",");
            String[] ptcompanyId = promotions.getPtcompanyId().split(",");
            for (int i = 0; i < ptppId.length; i++) {
                //if (!ptppId[i].equals(promotions.getPpId())) {
                Promotion promotion = new Promotion();
                promotion.setPromotionId(serverService
                        .getServerID("promotionId"));
                promotion.setPpId(promotions.getPpId());
                promotion.setCompanyId(promotions.getCompanyId());
                promotion.setPtppId(ptppId[i]);
                promotion.setPtcompanyId(ptcompanyId[i]);
                promotion.setPromotionDate(promotions.getPromotionDate());
                promotion.setComboGenre(promotions.getComboGenre());
                baseBeanDao.save(promotion);
                //}
            }
        }
    }

    /**
     * 新增产品类别
     *
     * @param companyid:公司id
     */
    @Override
    @Transactional
    public void addProductType(String companyid) {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        String hql = "from SchProCategory s where s.companyId = ? ";
        String hql2 = "from ProductPackaging pp where pp.companyID= ? ";
        String hql3 = "from GoodsManage g where g.companyID= ? ";
        String hql4 = "from CCode c where c.codeValue = ? and c.companyID = ?";
        CCode ccde = (CCode) baseBeanDao.getBeanByHqlAndParams(hql4, new Object[]{"餐饮", companyid});
        CCode ccdes = (CCode) baseBeanDao.getBeanByHqlAndParams(hql4, new Object[]{"餐桌", companyid});
        if (ccdes == null) {
            CCode ccode3 = new CCode();
            ccode3.setCodeID(idgec.getServerID("ccode"));
            ccode3.setCodeValue("餐桌");
            ccode3.setCompanyID(companyid);
            if (ccde == null) {
                CCode ccode1 = new CCode();
                ccode1.setCodeID(idgec.getServerID("ccode"));
                ccode1.setCodeValue("餐饮");
                ccode1.setCompanyID(companyid);
                ccode1.setCodePID("scode20101014v5zed7cukk0000000002");
                ccode1.setCodeNumber(15);
                ccode1.setCodeSn("15");
                ccode1.setCodeStatus("01");
                beans.add(ccode1);
                ccode3.setCodePID(ccode1.getCodePID());
            } else {
                ccode3.setCodePID(ccde.getCodeID());
            }
            ccode3.setCodeNumber(1502);
            ccode3.setCodeSn("1502");
            ccode3.setCodeStatus("01");
            beans.add(ccode3);
        }

        List<BaseBean> categoryList = baseBeanDao.getListBeanByHqlAndParams(hql, new Object[]{companyid});
        if (categoryList.size() == 0) {
            SchProCategory category1 = new SchProCategory();
            category1.setCategoryId(idgec.getServerID("category"));
            category1.setCategoryName("主食");
            category1.setStatus("2");
            category1.setCompanyId(companyid);
            beans.add(category1);

            SchProCategory category2 = new SchProCategory();
            category2.setCategoryId(idgec.getServerID("category"));
            category2.setCategoryName("饮料");
            category2.setStatus("2");
            category2.setCompanyId(companyid);
            beans.add(category2);

            SchProCategory category3 = new SchProCategory();
            category3.setCategoryId(idgec.getServerID("category"));
            category3.setCategoryName("套餐");
            category3.setStatus("2");
            category3.setCompanyId(companyid);
            beans.add(category3);

            SchProCategory category4 = new SchProCategory();
            category4.setCategoryId(idgec.getServerID("category"));
            category4.setCategoryName("盖饭");
            category4.setStatus("2");
            category4.setCompanyId(companyid);
            beans.add(category4);

            SchProCategory category5 = new SchProCategory();
            category5.setCategoryId(idgec.getServerID("category"));
            category5.setCategoryName("小吃");
            category5.setStatus("2");
            category5.setCompanyId(companyid);
            beans.add(category5);

            SchProCategory category6 = new SchProCategory();
            category6 = new SchProCategory();
            category6.setCategoryId(idgec.getServerID("category"));
            category6.setCategoryName("本店特色");
            category6.setStatus("2");
            category6.setCompanyId(companyid);
            beans.add(category6);

            List<BaseBean> packagingList = baseBeanDao.getListBeanByHqlAndParams(hql2, new Object[]{companyid});
            List<BaseBean> goodsList = baseBeanDao.getListBeanByHqlAndParams(hql3, new Object[]{companyid});
            for (int i = 0; i < packagingList.size(); i++) {
                ProductPackaging productPackaging = (ProductPackaging) packagingList.get(i);
                if (productPackaging.getCategoryId() == null || " ".equals(productPackaging.getCategoryId()) || "".equals(productPackaging.getCategoryId())) {
                    if (!"扫码收款".equals(productPackaging.getGoodsName())) {
                        productPackaging.setCategoryId(category6.getCategoryId());
                        productPackaging.setCategoryName(category6.getCategoryName());
                        beans.add(productPackaging);
                    }
                }
            }
            for (int i = 0; i < goodsList.size(); i++) {
                GoodsManage goodsManage = (GoodsManage) goodsList.get(i);
                if (goodsManage.getCategoryId() == null || " ".equals(goodsManage.getCategoryId()) || "".equals(goodsManage.getCategoryId())) {
                    if (!"扫码收款".equals(goodsManage.getGoodsName())) {
                        goodsManage.setCategoryId(category6.getCategoryId());
                        goodsManage.setCategoryName(category6.getCategoryName());
                        beans.add(goodsManage);
                    }
                }
            }
        }

        baseBeanDao.executeSqlsByParmsList(beans, null, null);
    }

    /**
     * 给ProSetupBackup表和ProSetupSubBackup表添加ProSetup表和ProSetupSub表备份数据
     *
     * @param ps      ProSetup表中的数据用来复制给ProSetupBackup表
     * @param pssList ProSetupSub表中的数据用来复制给roSetupSubBackup表
     * @param beans
     */
    public void savePssb(ProSetup ps, List<ProSetupSub> pssList, List<BaseBean> beans) {
        ProSetupBackup psbOld = (ProSetupBackup) baseBeanDao
                .getBeanByHqlAndParams("from ProSetupBackup where ppid = ? and psbState = ?",
                        new Object[]{ps.getPpid(), "00"});
        if (psbOld != null) {
            psbOld.setPsbState("11");
            beans.add(psbOld);
            List<BaseBean> pssbList = baseBeanDao
                    .getListBeanByHqlAndParams("from ProSetupSubBackup where subId = ? and pssbState = ?",
                            new Object[]{psbOld.getSubId(), "00"});
            if (pssbList != null) {
                for (BaseBean bb : pssbList) {
                    ProSetupSubBackup pssb = (ProSetupSubBackup) bb;
                    pssb.setPssbState("11");
                    beans.add(pssb);
                }
            }
        }
        ProSetupBackup psb = new ProSetupBackup();
        psb.setSubId(serverService.getServerID("setupbackup"));
        psb.setBrokerage(ps.getBrokerage());
        psb.setComId(ps.getComId());
        psb.setEfPrice(ps.getEfPrice());
        psb.setPpid(ps.getPpid());
        psb.setPpName(ps.getPpname());
        psb.setProxySumPrice(ps.getProxyprice());
        psb.setPsbState("00");
        psb.setRePrice(ps.getRePrice());
        psb.setSjDate(new Date());
        psb.setTzType(ps.getTzType());
        beans.add(psb);
        for (ProSetupSub pss : pssList) {
            ProSetupSubBackup pssb = new ProSetupSubBackup();
            pssb.setSusbId(serverService.getServerID("prosetupsubbackup"));
            pssb.setAgentState(pssb.getAgentState());
            pssb.setAmount(pss.getAmount());
            pssb.setInvestComId(pss.getInvestComId());
            pssb.setPpid(pss.getPpid());
            pssb.setPssbState("00");
            pssb.setSjDate(new Date());
            pssb.setState(pss.getState());
            pssb.setSubId(psb.getSubId());
            pssb.setTextUrl(pss.getTextUrl());
            pssb.setTypePpid(pss.getTypePpid());
            beans.add(pssb);
        }
    }

    /**
     * 保存秤盘时时重量
     * @param sc 秤盘编码
     * @param cc 货柜编码
     * @param w 重量
     * @param t 时间
     * @throws Exception
     */
    @Override
    @Transactional
    public void updateScaleWeight(String sc,String cc,double w,Date t) /*throws Exception*/ {
        ScaleWeight sw=getWeitht(sc,cc);
        if (sw == null) {
            sw=new ScaleWeight();
            sw.setSwid(serverService.getServerID("sw"));
            sw.setScaleCoding(sc);
            sw.setContainerCoding(cc);
        }
        sw.setWeight(w);
        sw.setTime(t);
        baseBeanService.saveOrUpdate(sw);
    }
    /**
     * 获取秤盘实时重量
     * @param sc 秤盘编号
     * @param cc 货柜编号
     * @return ScaleWeight 秤盘重量记录表
     */
    public ScaleWeight getWeitht(String sc, String cc)/* throws Exception*/ {
        String hql="from ScaleWeight where containerCoding=? and scaleCoding=?";
        ScaleWeight sw= (ScaleWeight) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{cc,sc});
        return sw;
    }


    /**
     * 获取货柜上所有秤盘实时重量
     * @param cc 货柜编号
     * @return list 秤盘重量记录集合
     */
    public List<BaseBean> getWeithts(String cc) /*throws Exception*/ {
        String hql="from ScaleWeight where containerCoding=?";
        List<BaseBean> swl= baseBeanDao.getListBeanByHqlAndParams(hql, new Object[]{cc});
        return swl;
    }

    /**
     * 自动贩卖机库存调整
     * @param beans
     * @param account
     * @param map
     */
    private void getReplenish(List<BaseBean> beans, CAccount account,Map<String, Object> map,String proStr) throws Exception {
        boolean flag = false;
        /*UtboundOrderVo ut = null;
        if (ut == null) {
            ut = new UtboundOrderVo();
            ut.setJournalnum(serverService.getBillID(account.getCompanyID()));
        }
        ut.setCashierDate(DateUtil.getCurrentDate("yyyy-MM-dd"));*/
        COrganization org = (COrganization) baseBeanService.getBeanByHqlAndParams("from COrganization where organizationUrl=? and" +
                " organizationPID=? and companyID=?", new Object[]{"/ea/office/ea_toTeachingAffairsDepartment", account.getCompanyID(), account.getCompanyID()});
        String cashierbillsId = serverService.getServerID("CashierBills");
        String jNumOrder = "";
        //存储出库单
        CashierBills cashier = new CashierBills();
        cashier.setCashierBillsID(cashierbillsId);
        cashier.setJournalNum(serverService.getBillID(account.getCompanyID()));
        cashier.setBillsType("生产入库单");
        cashier.setCompanyID(account.getCompanyID());
        cashier.setCompanyName(account.getCompanyName());
        cashier.setOrganizationID(org.getOrganizationID());
        cashier.setInputOrganizationName(org.getOrganizationName());
        cashier.setCashierDate(new Date());
        cashier.setStaffID(account.getStaffID());
        cashier.setStaffName(account.getStaffName());
        cashier.setInputid(account.getStaffID());
        cashier.setInputName(account.getStaffName());
        cashier.setStatus("15");
        cashier.setjNumOrder(jNumOrder);
        cashier.setFiveClear("4");

        if (proStr == null||proStr.equals("")) {
            map.put("code", "02");
            map.put("msg", "秤盘没有找到商品");
            map.put("flag",1);
        }else {
            String[] str = proStr.split(",");
            for (int j = 0; j < str.length; j++) {
                String str_i = str[j];
                String pid = str_i.substring(0, str_i.indexOf(":"));
                Float num = Float.parseFloat(str_i.substring(str_i.indexOf(":") + 1));
                ProductPackaging pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams("from ProductPackaging where ppID=?", new Object[]{pid});
                if (pp != null && !pp.getPpID().equals("") && (pp.getStanpro() != null || !pp.getStanpro().equals(""))) {
                    DepotManage depot = null;
                    if (pp.getDepotID() == null || pp.getDepotID().equals("")) {
                        depot = (DepotManage) baseBeanService.getBeanByHqlAndParams("from DepotManage where companyID=? and depotName=? and depotState!=?", new Object[]{account.getCompanyID(), "销售库", "01"});
                    } else {
                        depot = (DepotManage) baseBeanService.getBeanByHqlAndParams("from DepotManage where companyID=? and depotID=? and depotState!=?", new Object[]{account.getCompanyID(), pp.getDepotID(),"01"});
                    }

                    String InventoryId = serverService.getServerID("Inventory");
                    String goodsbillsId = serverService.getServerID("GoodsBills");

                    Company c = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", new Object[]{account.getCompanyID()});

                    //库存表
                    Inventory inv = (Inventory) baseBeanService.getBeanByHqlAndParams("from Inventory where warehouse=? and productId=? and companyID=?", new Object[]{depot.getDepotID(), pp.getPpID(), account.getCompanyID()});
                    Float quantity=0f;
                    if (inv == null) {
                        quantity=num;
                        flag=true;
                        //添加物品单据
                        GoodsBills goods = new GoodsBills();
                        goods.setGoodsBillsID(goodsbillsId);
                        goods.setCompanyID(account.getCompanyID());
                        goods.setCashierBillsID(cashier.getCashierBillsID());
                        goods.setPpID(pp.getPpID());
                        goods.setGoodsID(pp.getGoodsID());
                        goods.setTypeID(pp.getType());
                        goods.setGoodsNum(pp.getGoodsNum());
                        goods.setGoodsName(pp.getGoodsName());
                        goods.setStandard(pp.getStandard());
                        goods.setPrice(pp.getPrice());
                        goods.setQuantity(String.valueOf(num));
                        goods.setMoney(StringUtil.formatFloatNumber((Double.parseDouble(goods.getQuantity()) * Double.parseDouble(goods.getPrice()))));
                        goods.setDepotID(depot.getDepotID());
                        goods.setDepotName(depot.getDepotName());
                        goods.setKcStatus("14");
                        goods.setGoodsStatus("00");
                        goods.setCategory("00");
                        goods.setPpID(pp.getPpID());
                        beans.add(goods);

                        inv = new Inventory();
                        inv.setInventoryID(InventoryId);
                        inv.setCompanyID(account.getCompanyID());
                        inv.setOrganizationID(org.getOrganizationID());
                        inv.setDepartmentID(org.getOrganizationID());
                        inv.setStaffID(account.getStaffID());
                        inv.setStaffName(account.getStaffName());
                        inv.setWarehouse(depot.getDepotID());
                        inv.setWarehouseName(depot.getDepotName());
                        inv.setGoodsID(pp.getGoodsID());
                        inv.setGoodsName(pp.getGoodsName());
                        inv.setGoodsType(pp.getType());
                        inv.setStandard(pp.getStandard());
                        inv.setGoodsCoding(pp.getGoodsNum());
                        inv.setProductId(pp.getPpID());
                        inv.setGoodstatus("00");
                        inv.setBarcode(pp.getBarCode());
                    }else {
                        if (quantity>num){
                            quantity=Float.parseFloat(num+"");
                            //盘库
                            InvtFbCheck ifc = new InvtFbCheck();
                            ifc.setFbillid(this.serverService.getServerID("invtFbCheck"));
                            ifc.setCompanyid(account.getCompanyID());
                            ifc.setCompanyName(account.getCompanyName());
                            ifc.setOrganizationid(org.getOrganizationID());
                            ifc.setOrgName(org.getOrganizationName());
                            ifc.setDepartmentid(org.getOrganizationID());
                            ifc.setGroupcompanysn(c.getGroupCompanySn());
                            ifc.setBillsdate(new Date());//日期转字符串
                            ifc.setBillstype("盘库单");
                            ifc.setBillstatus("已调整库存");
                            ifc.setWarehousename(depot.getDepotName());
                            ifc.setWarehouse(depot.getDepotID());
                            ifc.setJournalnum(serverService.getBillID(account.getCompanyID()));
                            ifc.setStaffid(account.getStaffID());
                            ifc.setStaffname(account.getStaffName());
                            beans.add(ifc);

                            InvCheckGoods ci = new InvCheckGoods();
                            ci.setCheckinvId(this.serverService.getServerID("invCheckGoods"));
                            ci.setFBILLID(ifc.getFbillid());
                            ci.setRealQuantity(pp.getInvNum());
                            ci.setPrice(pp.getPrice());
                            ci.setPpID(pp.getPpID());
                            ci.setGoodsID(pp.getGoodsID());
                            ci.setGoodsType(pp.getType());
                            ci.setGoodsName(pp.getGoodsName());
                            ci.setInvenQuantity(inv.getInvenQuantity());
                            ci.setRealQuantity(String.valueOf(quantity));
                            beans.add(ci);

                        }else if (quantity<num){
                            flag=true;
                            quantity=num-quantity;
                            //添加物品单据
                            GoodsBills goods = new GoodsBills();
                            goods.setGoodsBillsID(goodsbillsId);
                            goods.setCompanyID(account.getCompanyID());
                            goods.setCashierBillsID(cashier.getCashierBillsID());
                            goods.setPpID(pp.getPpID());
                            goods.setGoodsID(pp.getGoodsID());
                            goods.setTypeID(pp.getType());
                            goods.setGoodsNum(pp.getGoodsNum());
                            goods.setGoodsName(pp.getGoodsName());
                            goods.setStandard(pp.getStandard());
                            goods.setPrice(pp.getPrice());
                            goods.setQuantity(String.valueOf(quantity));
                            goods.setMoney(StringUtil.formatFloatNumber((Double.parseDouble(goods.getQuantity()) * Double.parseDouble(goods.getPrice()))));
                            goods.setDepotID(depot.getDepotID());
                            goods.setDepotName(depot.getDepotName());
                            goods.setKcStatus("14");
                            goods.setGoodsStatus("00");
                            goods.setCategory("00");
                            goods.setPpID(pp.getPpID());
                            beans.add(goods);
                        }
                    }
                    if (quantity != null&&quantity>0) {
                        inv.setUnitPrice(pp.getPrice());//物品单价
                        inv.setInvenQuantity(String.valueOf(quantity+Float.parseFloat(inv.getInvenQuantity())));//物品数量
                        inv.setSumPrice(String.valueOf((Float.parseFloat(inv.getInvenQuantity()) * Float.parseFloat(pp.getPrice()))));//总价
                        beans.add(inv);
                    }
                    if (flag) {
                        beans.add(cashier);
                    }
                }
            }
        }
    }

    /**
     * 补货
     * @param account
     * @param map
     * @param proStr
     * @throws Exception
     */
    @Override
    @Transactional
    public void calculationNum(CAccount account,Map<String, Object> map,String proStr) throws Exception{
        List<BaseBean> beans = new ArrayList<BaseBean>();
        getReplenish(beans, account, map, proStr);
        baseBeanService.executeSqlsByParmsList(beans, null, null);
        ObjectMapper mapper = new ObjectMapper();
        for (int i = 0; i < beans.size(); i++) {
            try {
                String json = mapper.writeValueAsString(beans.get(i));
                System.out.println("\"" + BaseBean.class.getName() + i + "\":" + json + ",");

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取货柜下的商品
     * 终端机调用
     *
     * @param coding
     * @param companyid
     * @param t         判断是货柜还是秤盘  1：货柜 2:柜门 3:秤盘
     * @return
     */
    @Override
    public JSONObject getProductToDepot(String coding, String companyid, int t) {
        DepotManage d = (DepotManage) depotManageService.getDepotToCoding(coding, companyid, t);
        JSONObject temp = new JSONObject();
        if (d == null) {
            if (t == 1) {
                temp.accumulate("msg", "01");
                temp.accumulate("error", "没有找到货柜信息");
            } else if (t == 2) {
                temp.accumulate("msg", "02");
                temp.accumulate("error", "没有找到柜门信息");
            } else {
                temp.accumulate("msg", "03");
                temp.accumulate("error", "没有找到秤盘信息");
            }
        } else {
            List<Object> parmsList = new ArrayList<Object>();
            String hql = "SELECT P.PPID, P.GOODSNAME, ROUND(PS.RE_PRICE * (1 + NVL(SZ.TOTAL_PCT, 0) / 100), 2) PRICE, ";
            hql += " NVL(P.VARIABLEID,'-'),P.SINGLEWEIGHT,P.STANPRO,p.depotCoding,p.depotName,";
            hql += "NVL(p.image,'-'),p.depotid,D.DEPOTPID";
            hql += " FROM DT_PRO_SETUP PS";
            hql += " INNER JOIN DT_PRODUCTPACKAGING P ON P.PPID = PS.PPID";
            hql += " LEFT JOIN DTDEPOTMANAGE D ON P.DEPOTID=D.DEPOTID";
            hql += " INNER JOIN DT_CCOM_COM CCOM ON P.COMPANYID = CCOM.COMPNAY_ID";
            hql += " INNER JOIN DTCONTACTCOMPANY CC ON CCOM.CCOMPANY_ID = CC.CCOMPANYID";
            hql += " LEFT JOIN DT_SET_SUBSIDIZE SZ ON SZ.GTID = CC.INDUSTRYTYPE AND SZ.STUTAS = '01'";
            hql += " WHERE P.SHOWWEIXIN = ? AND P.DELSTATUS = ? AND PS.STATE = ?";
            parmsList.add("01");
            parmsList.add("00");
            parmsList.add("00");
            List<Object> depotList = new ArrayList<>();
            if (t == 1) {
                depotList = depotManageService.ParenByDepotid(d.getDepotID(), companyid, 1);
                if (depotList != null) {
                    hql += " AND P.DEPOTID IN (";
                    for (int j = 0; j < depotList.size(); j++) {
                        Object[] bb = (Object[]) depotList.get(j);
                        if (j == depotList.size() - 1) {
                            hql += " ?)";
                        } else {
                            hql += " ?,";
                        }
                        parmsList.add(bb[1]);
                    }
                } else {
                    temp.accumulate("msg", "02");
                    temp.accumulate("error", "没有找到秤盘信息");
                }
            } else {
                hql += " AND P.DEPOTID =?";
                parmsList.add(d.getDepotID());
            }
            List<Object> productList = baseBeanService.getListBeanBySqlAndParams(hql, parmsList.toArray());
            if (productList != null) {
                Object[] obj = null;
                List<String> lists = new ArrayList<>();
                if (depotList != null && depotList.size() > 0) {
                    for (int j = 0; j < productList.size(); j++) {
                        obj = (Object[]) productList.get(j);
                        List<Object> o = depotManageService.ParenByDepotid(obj[9].toString(), companyid, 0);
                        for (int i = 0; i < depotList.size(); i++) {
                            Object[] oo = (Object[]) o.get(i);
                            if (obj[10] != null && obj[10].equals(oo[1])) {
                                obj[9] = oo[7];
                                obj[7] = oo[5] + ">" + obj[7];
                                break;
                            }
                        }
                        JSONObject propro = new JSONObject();
                        if (!lists.contains(obj[6])) {
                            lists.add(obj[6].toString());
                            JSONArray a = new JSONArray();
                            //propro.accumulate("depotCoding",obj[6]);
                            for (int k = 0; k < productList.size(); k++) {
                                Object[] obj1 = (Object[]) productList.get(k);
                                if (obj[6].equals(obj1[6])) {
                                    JSONObject pro = new JSONObject();
                                    /*pro.accumulate("ppid", obj1[0]);*/
                                    pro.accumulate("goodsName", obj1[1]);
                                    pro.accumulate("price", obj1[2]);
                                    pro.accumulate("variableId", obj1[3]);
                                    /*pro.accumulate("singleWeight", obj1[4]);
                                    pro.accumulate("stanPro", obj1[5]);*/
                                    pro.accumulate("depotCoding", obj1[6]);
                                    a.add(pro);
                                    //productList.remove(obj1);
                                }
                                if (k == productList.size() - 1) {
                                    propro.put(String.valueOf(obj[6]), a);
                                }
                            }
                            temp.accumulate("data", propro);
                        }

                    }
                    temp.accumulate("productList", productList);
                } else {
                    temp.accumulate("msg", "03");
                    temp.accumulate("error", "秤盘没有找到商品");
                }
            }
        }
        return temp;
    }

    /**
     * 获取秤盘产品信息
     *
     * @param coding
     * @param companyid
     * @return
     */
    public Map<String, Object> getProductTochBalance(String coding, String companyid) {
        DepotManage d = (DepotManage) depotManageService.getDepotToCoding(coding, companyid, 3);
        Map<String, Object> map = new HashMap<>();
        map.put("flag", 0);
        if (d == null) {
            map.put("code", "01");
            map.put("msg", "没有找到秤盘信息");
            map.put("flag", 1);
        } else {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT P.PPID, P.GOODSNAME, P.STANPRO, P.SINGLEWEIGHT, NVL(I.INVENQUANTITY,0),P.SHOWWEIXIN");
            sql.append(" FROM DT_PRODUCTPACKAGING P");
            sql.append(" LEFT JOIN DT_INV_INVT I ON P.PPID = I.PRODUCTID AND I.WAREHOUSE = P.DEPOTID");
            sql.append(" WHERE P.DEPOTID = ? and P.showweixin=?");
            List<Object> params=new ArrayList<Object>();
            params.add(d.getDepotID());
            params.add("01");
            if (companyid != null&&!companyid.equals("")) {
                sql.append(" AND P.COMPANYID = ?");
                params.add(companyid);
            }
            List<Object[]> productList = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params.toArray());
            map.put("productList", productList);
        }
        return map;
    }

    /**
     * 获取秤盘产品信息
     *
     * @param proWeight
     */
    public String calculationNum(Map<String, ScaleWeight> proWeight, CAccount account) {
        Map<String, Object> map;
        for (ScaleWeight sw : proWeight.values()) {
            map = getProductTochBalance(sw.getScaleCoding(), account.getCompanyID());
            Object msg = map.get("msg");
            if (msg == null || msg.equals("")) {
                List<Object[]> productList = (List<Object[]>) map.get("data");
                if (productList == null) {
                    map.put("code", "02");
                    map.put("msg", "秤盘没有找到商品");
                } else {
                    int stanpro = 0;
                    for (int j = 0; j < productList.size(); j++) {
                        Object[] product = productList.get(j);
                        stanpro = Integer.parseInt(product[2].toString());
                    }
                    if (stanpro == 0) {
                        calculationNumRecursion("", new BigDecimal(sw.getWeight()), BigDecimal.ZERO, productList.size(), productList);
                        //getReplenish(beans, account,map,ReturnRecursion);
                    } else {
                        ReturnRecursion=productList.get(0)[0].toString()+":"+sw.getWeight();
                        //getReplenish(beans, account,map,ReturnRecursion);
                    }
                }
            }
        }
        //baseBeanService.executeSqlsByParmsList(beans, null, null);
        return ReturnRecursion;
    }

    /**
     * 递归根据重量计算商品数量
     *
     * @param target     秤盘返回重量
     * @param currentSum 产品数量组合结果
     * @param length     当前考虑的产品种类索引
     * @param b          产品集合
     */
    private void calculationNumRecursion(String prefix, BigDecimal target, BigDecimal currentSum, int length, List<Object[]> b) {
        // 检查数组长度，如果为0则无需执行后续操作
        if (length == 0) {
            return;
        }

        // 获取当前循环下的产品数据
        Object[] temp = b.get(length-1);
        // 确保产品数据非空
        if (temp == null) {
            throw new IllegalArgumentException("产品数据出错");
        }

        // 解析产品数据中的数量为整数，用于后续计算
        int n;
        try {
            n = Integer.parseInt(temp[4].toString())<=0?15:Integer.parseInt(temp[4].toString());
        } catch (NumberFormatException e) {
            // 如果转换失败，抛出异常
            throw new IllegalArgumentException("产品数量出错", e);
        }

        // 检查产品数据中的单重是否为Float类型，用于后续计算
        BigDecimal weight;
        try {
            weight = new BigDecimal(temp[3].toString());
        } catch (NumberFormatException e) {
            // 如果不是Float类型，抛出异常
            throw new IllegalArgumentException("产品单重类型出错",e);
        }

        // 构建前缀字符串，用于记录当前递归层级的信息-产品名称
        String prefixPart = temp[1] + ": ";
        // 遍历可能的值，从0到n，包括n
        for (int i = 0; i <= n; i++) {
            // 如果设置了停止递归的标志，则跳出循环
            if (StopRecursion) break;

            // 计算更新后的和
            BigDecimal updatedBound = currentSum.add(weight.multiply(new BigDecimal(i)));
            // 构建当前层级的字符串表示
            String p = prefixPart + i + ",";

            // 如果更新后的结果重量等于目标重量，则记录当前递归路径并停止递归
            BigDecimal updatedBound2 = updatedBound.add(new BigDecimal(0.005));
            BigDecimal updatedBound3 = updatedBound.subtract(new BigDecimal(0.005));
            if (updatedBound2.compareTo(target)>0&&updatedBound3.compareTo(target)<0) {
                ReturnRecursion = prefix + p;
                StopRecursion = true;
                break;
            }

            // 检查递归深度，如果超过15层，则抛出异常以防止栈溢出
            if (length > 15) {
                throw new StackOverflowError("Recursion depth exceeded");
            }

            // 递归调用，继续处理剩余的元素，更新前缀字符串和和值
            calculationNumRecursion(prefix + p, target, updatedBound, length - 1, b);
        }
    }

    /**
     *
     * 注册个人店铺
     * @param tcc
     * @return
     */
    @Transactional
    public String registerPShop(TEshopCusCom tcc){

        Staff staff = (Staff)baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{tcc.getStaffid()});
        Company company1 = new Company();
        company1.setCompanyIdentifier(tcc.getAccount());
        company1.setCompanyName(staff.getStaffName()+"的数字地球小店");
        CDetail cdl = new CDetail();
        cdl.setCompanyManager(staff.getStaffName());
        cdl.setCompanyPhone(tcc.getAccount());
      //  company1.setShopType("01");
        company1.setCcomtype("6");
        String companyId = "";
        // String companyId=   wfjserv.registerCompanyInfo("6", tcc, company1, cdl);
//        tcc.setShopid(companyId);
//        baseBeanDao.update(tcc);
//        createOrg(companyId);
        return companyId;
    }

    @Transactional
    private  void createOrg(String companyID){

        String phql = "select count(*) from COrganization ";
        int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
        COrganization organization = new COrganization();
        organization.setOcode("JGN" + pcount);
        organization.setOrganizationName("教务处");
        organization.setOrganizationNumber(1);
        organization.setOcode("1");
        organization.setOpostCode("教务处");
        organization.setOdutiesName("教务处");
        organization.setOrganizationPID(companyID);
        organization.setOrganizationUrl("/ea/office/ea_toTeachingAffairsDepartment");
        organization.setOrganizationID(serverService
                .getServerID("organization"));
        organization.setOrganizationCreateDate(new Date());
        organization.setCompanyID(companyID);
        organization.setStatus("00");

        baseBeanDao.save(organization);

    }

    /**
     * 根据产品id查询库存信息
     *
     * @param ppid 产品id
     * @return list 库存记录集合
     * @throws Exception
     */
    public List<BaseBean> getDepotByProid(String ppid, String depotid) throws Exception {
        String hql = "from Inventory where productId=? and warehouse=?";
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{ppid, depotid});
        return list;
    }



    /**
     *
     * 验证会员
     * @param ppid
     * @return
     */
    public String checkVip(String ppid) {
       String hql = "from ProductPackaging c where c.ppID = ?";
        if (ppid != null && !ppid.equals("")) {

            int  k = 0;
              String[] array = ppid.split(",");
              for (int i = 0;i<array.length;i++){
                  ProductPackaging pp = (ProductPackaging) baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{array[i]});
                  String type = pp.getType();

                  if("个人会员".equals(type)){
                      k=k+1;
                  }
              }

              if(k>1){
                  return "1";
              }else {
                  return "0";
              }
        }

        return "0";
      }

    /**
     *
     * 获取主产品 包含会员的
     *
     * @param pageSize
     * @param pageNumber
     * @return
     */
    public PageForm getVipListCompany(int pageSize,int pageNumber,String companyID,String goodsName){
        //主产品是个人会员 以及促销品带个人会员的主产品
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());
        StringBuilder sql = new StringBuilder();

         List<Object> params = new ArrayList<Object>();

        sql.append(" select ps.ppname,ps.ppid,");

        sql.append("case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,");

        sql.append(" p.image,p.model,p.goodsid,p.companyid,cm.companyName,c.ccompany_Id,p.monthSales");
        sql.append(" from dt_pro_setup ps inner join dt_productpackaging p on ps.ppid = p.ppid ");
        sql.append("left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state='00' ");
        sql.append("left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01' and (tpa.state!='02' and tpa.state!='04') ");

        sql.append("and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
        sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state='00'");
        sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00' and (pa.state!='02' and pa.state!='04')");
        sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join Dt_Ccom_Com c on c.compnay_id = p.companyid ");
        sql.append("left join dtContactCompany cm on c.ccompany_id = cm.ccompanyid ");
        sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");

        sql.append(" where (p.model='3' or p.model='4' or p.model='5') and ps.com_id = ? and  p.showweixin = '01' and p.type = '个人会员' and ps.fcom_id is null");
        params.add(currentTime);
        params.add(currentTime);
        params.add(currentTime);
        params.add(currentTime);


        params.add(companyID);

      if(goodsName!=null&&!goodsName.equals("")){
          sql.append(" and p.goodsName like ?");
          params.add("%"+goodsName+"%");
      }
        sql.append(" union");

        sql.append(" select ps.ppname,ps.ppid,");
        sql.append("case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,");

        sql.append(" pp.image,pp.model,pp.goodsid,pp.companyid,cm.companyName,c.ccompany_Id,pp.monthSales");

        sql.append(" from dt_promotion  m inner join dt_pro_setup  ps on m.ppid = ps.ppid inner join dt_productpackaging p on m.ptppId = p.ppid inner join dt_productpackaging pp on ps.ppid = pp.ppid ");

        sql.append("left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state='00' ");
        sql.append("left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01' and (tpa.state!='02' and tpa.state!='04') ");

        sql.append("and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
        sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state='00'");
        sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00' and (pa.state!='02' and pa.state!='04')");
        sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join Dt_Ccom_Com c on c.compnay_id = pp.companyid ");
        sql.append("left join dtContactCompany cm on c.ccompany_id = cm.ccompanyid ");
        sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
        sql.append(" where  pp.companyID = ? and  (p.model='3' or p.model='4' or p.model='5') and m.ppid = pp.ppid and pp.showweixin = '01' and p.showweixin = '01' and m.ptppid = p.ppid and ps.ppid = pp.ppid and p.type = '个人会员'");
        params.add(currentTime);
        params.add(currentTime);
        params.add(currentTime);
        params.add(currentTime);
        params.add(companyID);

        if(goodsName!=null&&!goodsName.equals("")){
            sql.append(" and (pp.goodsName like ? or p.goodsName like ?)");
            params.add("%"+goodsName+"%");
            params.add("%"+goodsName+"%");

        }
        PageForm pageForm=baseBeanService.getPageFormBySQL(pageNumber,pageSize,sql.toString(),"select count(*) from ("+sql.toString()+")",params.toArray());


        return pageForm;
    }


    /**
     *
     * 获取主产品 包含会员的
     *
     * @param pageSize
     * @param pageNumber
     * @return
     */
    public PageForm getVipListALL(int pageSize,int pageNumber,String industryId,String goodsName){
        //主产品是个人会员 以及促销品带个人会员的主产品
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());
        StringBuilder sql = new StringBuilder();

        List<Object> params = new ArrayList<Object>();

        sql.append(" select ps.ppname,ps.ppid,");
        sql.append("case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,");
        sql.append(" p.image,p.model,p.goodsid,p.companyid,cm.companyName,c.ccompany_Id,p.monthSales");

        sql.append(" from dt_pro_setup ps inner join dt_productpackaging p on ps.ppid = p.ppid ");
        sql.append("left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state='00' ");
        sql.append("left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01' and (tpa.state!='02' and tpa.state!='04') ");

        sql.append("and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
        sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state='00'");
        sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00' and (pa.state!='02' and pa.state!='04')");
        sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join Dt_Ccom_Com c on c.compnay_id = p.companyid ");
        sql.append("left join dtContactCompany cm on c.ccompany_id = cm.ccompanyid ");
        sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
        sql.append(" where  (p.model='3' or p.model='4' or p.model='5') and ps.com_id = ? and p.ppid = ps.ppid and p.showweixin = '01' and p.type = '个人会员' and ps.fcom_id is null");
        params.add(currentTime);
        params.add(currentTime);
        params.add(currentTime);
        params.add(currentTime);
        params.add("company201009046vxdyzy4wg0000000025");
        if(industryId!=null&&!industryId.equals("")){
            if(industryId.equals("scode20170721cnjcrn5jm20000001237")){
                sql.append(" and cm.industryType like ?");
                params.add("%餐饮%");
            }else{
                sql.append(" and cm.industryId = ?");
                params.add(industryId);
            }

        }
        if(goodsName!=null&&!goodsName.equals("")){
            sql.append(" and p.goodsName like ?");
            params.add("%"+goodsName+"%");
        }
        sql.append(" union");

        sql.append(" select ps.ppname,ps.ppid,");
        sql.append("case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,");
        sql.append(" pp.image,pp.model,pp.goodsid,pp.companyid,cm.companyName,c.ccompany_Id,pp.monthSales");

        sql.append(" from dt_promotion  m inner join dt_pro_setup  ps on m.ppid = ps.ppid inner join dt_productpackaging p on m.ptppId = p.ppid inner join dt_productpackaging pp on ps.ppid = pp.ppid ");

        sql.append("left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state='00' ");
        sql.append("left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01' and (tpa.state!='02' and tpa.state!='04') ");

        sql.append("and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
        sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state='00'");
        sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00' and (pa.state!='02' and pa.state!='04')");
        sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join Dt_Ccom_Com c on c.compnay_id = pp.companyid ");
        sql.append("left join dtContactCompany cm on c.ccompany_id = cm.ccompanyid ");
        sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
        sql.append(" where  (p.model='3' or p.model='4' or p.model='5') and m.ppid = pp.ppid and pp.showweixin = '01' and p.showweixin = '01' and m.ptppid = p.ppid and ps.ppid = pp.ppid and p.type = '个人会员'");
        params.add(currentTime);
        params.add(currentTime);
        params.add(currentTime);
        params.add(currentTime);
        if(industryId!=null&&!industryId.equals("")){
            sql.append(" and cm.industryId = ?");
            params.add(industryId);
        }
        if(goodsName!=null&&!goodsName.equals("")){
            sql.append(" and (pp.goodsName like ? or p.goodsName like ?)");
            params.add("%"+goodsName+"%");
            params.add("%"+goodsName+"%");

        }
        PageForm pageForm=baseBeanService.getPageFormBySQL(pageNumber,pageSize,sql.toString(),"select count(*) from ("+sql.toString()+")",params.toArray());


        return pageForm;
    }
    /**
     *
     * 获取主产品 包含会员的
     *
     * @param pageSize
     * @param pageNumber
     * @return
     */
    public PageForm getVipList(String sccId,int pageSize,int pageNumber){
         //主产品是个人会员 以及促销品带个人会员的主产品

        StringBuilder sql = new StringBuilder();
        sql.append("select ps.ppname,ps.ppid,ps.re_price,pp.image,pp.model,pp.goodsid,pp.companyid,cm.companyName,c.ccompany_Id,cm.logoPath");
        sql.append(" from dt_promotion  m,dt_pro_setup  ps,dt_productpackaging p,dt_productpackaging pp,DT_ccom_com c,dtContactCompany cm");
        sql.append(" where c.compnay_id = pp.companyID and c.ccompany_Id = cm.ccompanyID and (p.model='3' or p.model='4' or p.model='5') and m.ppid = pp.ppid and pp.showweixin = '01' and p.showweixin = '01' and m.ptppid = p.ppid and ps.ppid = pp.ppid and p.type = '个人会员'");
        sql.append(" and pp.companyid in (select t.companyid from t_eshop_cuscom t where t.sccid != ? and t.companyid is not null  connect by nocycle prior t.suppersccid = t.sccid start with t.sccid = ?)");
        sql.append(" union all");


        sql.append(" select ps.ppname,ps.ppid,ps.re_price,p.image,p.model,p.goodsid,p.companyid,cm.companyName,c.ccompany_Id,cm.logoPath");
        sql.append(" from dt_pro_setup ps, dt_productpackaging p,DT_ccom_com c,dtContactCompany cm");
        sql.append(" where c.compnay_id = p.companyID and c.ccompany_Id = cm.ccompanyID   and (p.model='3' or p.model='4' or p.model='5') and ps.com_id = ? and p.ppid = ps.ppid and p.showweixin = '01' and p.type = '个人会员' and ps.fcom_id is null");
        sql.append(" union all");

        sql.append(" select ps.ppname,ps.ppid,ps.re_price,pp.image,pp.model,pp.goodsid,pp.companyid,cm.companyName,c.ccompany_Id,cm.logoPath");
        sql.append(" from dt_promotion  m,dt_pro_setup  ps,dt_productpackaging p,dt_productpackaging pp,DT_ccom_com c,dtContactCompany cm");
        sql.append(" where  c.compnay_id = pp.companyID and c.ccompany_Id = cm.ccompanyID  and (p.model='3' or p.model='4' or p.model='5') and m.ppid = pp.ppid and pp.showweixin = '01' and p.showweixin = '01' and m.ptppid = p.ppid and ps.ppid = pp.ppid and p.type = '个人会员'");
        sql.append(" and pp.companyid not in (select t.companyid from t_eshop_cuscom t where t.sccid != ? and t.companyid is not null connect by nocycle prior t.suppersccid = t.sccid start with t.sccid = ?)");

        PageForm pageForm=baseBeanService.getPageFormBySQL(pageNumber,pageSize,sql.toString(),"select count(*) from ("+sql.toString()+")",new Object[]{sccId,sccId,"company201009046vxdyzy4wg0000000025",sccId,sccId});


        return pageForm;
      }

    /**
     *
     * 获取主产品 包含会员的
     *
     * @return
     */
    public  Map<String,List<Object>> getproList(PageForm pageForm){
        List<Object> clist =null;
        StringBuilder sql = new StringBuilder();
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());
        //主产品是个人会员 以及促销品带个人会员的主产品

        sql.append("select * from(");
        sql.append(" select ps.ppname,ps.ppid,");
        sql.append("case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,");
        sql.append(" p.image,p.model,p.goodsid,p.companyid,cm.companyName,c.ccompany_Id,cm.logoPath");

        sql.append(" from dt_pro_setup ps inner join dt_productpackaging p on ps.ppid = p.ppid ");
        sql.append("left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state='00' ");
        sql.append("left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01' and (tpa.state!='02' and tpa.state!='04') ");

        sql.append("and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
        sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state='00'");
        sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00' and (pa.state!='02' and pa.state!='04')");
        sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join Dt_Ccom_Com c on c.compnay_id = p.companyid ");
        sql.append("left join dtContactCompany cm on c.ccompany_id = cm.ccompanyid ");
        sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");

        sql.append(" where (p.model='3' or p.model='4' or p.model='5') and  p.ppid = ps.ppid and p.showweixin = '01' and p.type = '个人会员' and ps.fcom_id is null");
        sql.append(" union ");//获取北京天太世统公司的会员产品

        sql.append(" select ps.ppname,ps.ppid,");
        sql.append("case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,");

        sql.append(" pp.image,pp.model,pp.goodsid,pp.companyid,cm.companyName,c.ccompany_Id,cm.logoPath");

        sql.append(" from dt_promotion  m inner join dt_pro_setup  ps on m.ppid = ps.ppid inner join dt_productpackaging p on m.ptppId = p.ppid inner join dt_productpackaging pp on ps.ppid = pp.ppid ");

        sql.append("left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state='00' ");
        sql.append("left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01' and (tpa.state!='02' and tpa.state!='04') ");

        sql.append("and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
        sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state='00'");
        sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00' and (pa.state!='02' and pa.state!='04')");
        sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join Dt_Ccom_Com c on c.compnay_id = pp.companyid ");
        sql.append("left join dtContactCompany cm on c.ccompany_id = cm.ccompanyid ");
        sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");

        sql.append(" where  (p.model='3' or p.model='4' or p.model='5') and pp.showweixin = '01' and p.showweixin = '01' and p.type = '个人会员'");
        sql.append(")zz where zz.companyid = ? and ROWNUM <= 3");

         Map<String,List<Object>> map = new HashMap<String,List<Object>>();
        for(int i = 0;i<pageForm.getList().size();i++){
            Object obj = pageForm.getList().get(i);
            Object[] objs = ( Object[])obj;
            clist= baseBeanDao.getListObjectBySqlAndParams(sql.toString(),new Object[]{currentTime, currentTime, currentTime, currentTime,currentTime, currentTime, currentTime, currentTime,objs[0]});
            map.put(objs[0].toString(),clist);

        }





        return map;
    }
    /**
     *
     * 获取主产品 包含会员的
     *
     * @param pageSize
     * @param pageNumber
     * @return
     */
    public PageForm getVipList1(int pageSize,int pageNumber,String companyID,String productName){
        PageForm pageForm = null;
        StringBuilder sql = new StringBuilder();

                //主产品是个人会员 以及促销品带个人会员的主产品

                sql.append("select ps.ppname,ps.ppid,ps.re_price,pp.image,pp.model,pp.goodsid,pp.companyid,cm.companyName,c.ccompany_Id,cm.logoPath");
                sql.append(" from dt_promotion  m,dt_pro_setup  ps,dt_productpackaging p,dt_productpackaging pp,DT_ccom_com c,dtContactCompany cm");//pp促销品，p主产品
                sql.append(" where c.compnay_id = pp.companyID and c.ccompany_Id = cm.ccompanyID and (p.model='3' or p.model='4' or p.model='5') and m.ppid = pp.ppid and pp.showweixin = '01' and p.showweixin = '01' and m.ptppid = p.ppid and ps.ppid = pp.ppid and p.type = '个人会员'");
                sql.append(" and pp.companyid in (select t.companyid from t_eshop_cuscom t where t.sccid != ? and t.companyid is not null  connect by nocycle prior t.suppersccid = t.sccid start with t.sccid = ?) and rownum <=3");
                sql.append(" union all");//找到当前用户对应的公司绑定会员产品的公司


                sql.append(" select ps.ppname,ps.ppid,ps.re_price,p.image,p.model,p.goodsid,p.companyid,cm.companyName,c.ccompany_Id,cm.logoPath");
                sql.append(" from dt_pro_setup ps, dt_productpackaging p,DT_ccom_com c,dtContactCompany cm");
                sql.append(" where c.compnay_id = p.companyID and c.ccompany_Id = cm.ccompanyID   and (p.model='3' or p.model='4' or p.model='5') and ps.com_id = ? and p.ppid = ps.ppid and p.showweixin = '01' and p.type = '个人会员' and ps.fcom_id is null and rownum <=3");
                sql.append(" union all");//获取北京天太世统公司的会员产品

                sql.append(" select ps.ppname,ps.ppid,ps.re_price,pp.image,pp.model,pp.goodsid,pp.companyid,cm.companyName,c.ccompany_Id,cm.logoPath");
                sql.append(" from dt_promotion  m,dt_pro_setup  ps,dt_productpackaging p,dt_productpackaging pp,DT_ccom_com c,dtContactCompany cm");
                sql.append(" where  c.compnay_id = pp.companyID and c.ccompany_Id = cm.ccompanyID  and (p.model='3' or p.model='4' or p.model='5') and m.ppid = pp.ppid and pp.showweixin = '01' and p.showweixin = '01' and m.ptppid = p.ppid and ps.ppid = pp.ppid and p.type = '个人会员' and rownum <=3");
                sql.append(" and pp.companyid not in (select t.companyid from t_eshop_cuscom t where t.sccid != ? and t.companyid is not null connect by nocycle prior t.suppersccid = t.sccid start with t.sccid = ?)");
                //排除掉不属于用户上级的哦给你个
                 pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", new Object[]{ "company201009046vxdyzy4wg0000000025"});


        return pageForm;
    }

    /**
     * 查询会员产品相关公司
     *
     *
     */
    public PageForm getComViplist(String sccId,int pageSize,int pageNumber,String industryId,String companyName){
                //有会员的公司，以及产品带会员促销品的公司
        PageForm pageForm = null;
        StringBuilder sql = new StringBuilder();
         List<Object> params = new ArrayList<Object>();
        if(industryId==null||industryId.equals("")) {
            //主产品是个人会员 以及促销品带个人会员的主产品

            sql.append("select pp.companyid,cm.companyName,c.ccompany_Id,cm.logoPath,cm.companyAddr,cm.industryType,com.totalSales");
            sql.append(" from dt_promotion  m,dt_pro_setup  ps,dt_productpackaging p,dt_productpackaging pp,DT_ccom_com c,dtContactCompany cm,dtCompany com");//pp促销品，p主产品
            sql.append(" where com.companyID =c.compnay_id and c.compnay_id = pp.companyID and c.ccompany_Id = cm.ccompanyID and (p.model='3' or p.model='4' or p.model='5') and m.ppid = pp.ppid and pp.showweixin = '01' and p.showweixin = '01' and m.ptppid = p.ppid and ps.ppid = pp.ppid and p.type = '个人会员'");
            sql.append(" and pp.companyid in (select t.companyid from t_eshop_cuscom t where t.sccid != ? and t.companyid is not null  connect by nocycle prior t.suppersccid = t.sccid start with t.sccid = ?)");

            params.add(sccId);
            params.add(sccId);

            if(companyName!=null&&!companyName.equals("")){
                sql.append(" and cm.companyName like ?");
                params.add("%"+companyName+"%");
            }
            sql.append(" union");//找到当前用户对应的公司绑定会员产品的公司



            sql.append(" select p.companyid,cm.companyName,c.ccompany_Id,cm.logoPath,cm.companyAddr,cm.industryType,com.totalSales");
            sql.append(" from dt_pro_setup ps, dt_productpackaging p,DT_ccom_com c,dtContactCompany cm,dtCompany com");
            sql.append(" where com.companyID =c.compnay_id and c.compnay_id = p.companyID and c.ccompany_Id = cm.ccompanyID   and (p.model='3' or p.model='4' or p.model='5') and ps.com_id = ? and p.ppid = ps.ppid and p.showweixin = '01' and p.type = '个人会员' and ps.fcom_id is null and rownum <=3");
            params.add("company201009046vxdyzy4wg0000000025");


            if(companyName!=null&&!companyName.equals("")){
                sql.append(" and cm.companyName like ?");
                params.add("%"+companyName+"%");
            }


            sql.append(" union");


            sql.append(" select pp.companyid,cm.companyName,c.ccompany_Id,cm.logoPath,cm.companyAddr,cm.industryType,com.totalSales");
            sql.append(" from dt_promotion  m,dt_pro_setup  ps,dt_productpackaging p,dt_productpackaging pp,DT_ccom_com c,dtContactCompany cm,dtCompany com");
            sql.append(" where  com.companyID =c.compnay_id and c.compnay_id = pp.companyID and c.ccompany_Id = cm.ccompanyID  and (p.model='3' or p.model='4' or p.model='5') and m.ppid = pp.ppid and pp.showweixin = '01' and p.showweixin = '01' and m.ptppid = p.ppid and ps.ppid = pp.ppid and p.type = '个人会员' and rownum <=3");
            sql.append(" and pp.companyid not in (select t.companyid from t_eshop_cuscom t where t.sccid != ? and t.companyid is not null connect by nocycle prior t.suppersccid = t.sccid start with t.sccid = ?)");
            //排除掉不属于用户上级的哦给你个
            params.add(sccId);
            params.add(sccId);
            if(companyName!=null&&!companyName.equals("")){
                sql.append(" and cm.companyName like ?");
                params.add("%"+companyName+"%");
            }

            pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")",params.toArray());

        }else{

            sql.append(" select p.companyid,cm.companyName,c.ccompany_Id,cm.logoPath,cm.companyAddr,cm.industryType,com.totalSales");
            sql.append(" from dt_pro_setup ps, dt_productpackaging p,DT_ccom_com c,dtContactCompany cm,dtCompany com");
            sql.append(" where com.companyID =c.compnay_id and c.compnay_id = p.companyID and c.ccompany_Id = cm.ccompanyID   and (p.model='3' or p.model='4' or p.model='5') and  p.ppid = ps.ppid and p.showweixin = '01' and p.type = '个人会员' and ps.fcom_id is null and rownum <=3");

            if(industryId!=null&&!industryId.equals("")){
                if(industryId.equals("scode20170721cnjcrn5jm20000001237")){
                    sql.append(" and cm.industryType like ?");
                    params.add("%餐饮%");
                }else{
                    sql.append(" and cm.industryId = ?");
                    params.add(industryId);
                }

            }
            if(companyName!=null&&!companyName.equals("")){
                sql.append(" and cm.companyName like ?");
                params.add("%"+companyName+"%");
            }

            sql.append(" union");//获取北京天太世统公司的会员产品

            sql.append(" select pp.companyid,cm.companyName,c.ccompany_Id,cm.logoPath,cm.companyAddr,cm.industryType,com.totalSales");
            sql.append(" from dt_promotion  m,dt_pro_setup  ps,dt_productpackaging p,dt_productpackaging pp,DT_ccom_com c,dtContactCompany cm,dtCompany com");
            sql.append(" where  com.companyID =c.compnay_id and c.compnay_id = pp.companyID and c.ccompany_Id = cm.ccompanyID  and (p.model='3' or p.model='4' or p.model='5') and m.ppid = pp.ppid and pp.showweixin = '01' and p.showweixin = '01' and m.ptppid = p.ppid and ps.ppid = pp.ppid and p.type = '个人会员' and  rownum <=3");

            if(industryId!=null&&!industryId.equals("")){
                if(industryId.equals("scode20170721cnjcrn5jm20000001237")){
                    sql.append(" and cm.industryType like ?");
                    params.add("%餐饮%");
                }else{
                    sql.append(" and cm.industryId = ?");
                    params.add(industryId);
                }

            }
            if(companyName!=null&&!companyName.equals("")){
                sql.append(" and cm.companyName like ?");
                params.add("%"+companyName+"%");
            }
            //排除掉不属于用户上级的哦给你个
            pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", params.toArray());

        }



        return pageForm;

    }

    /**
     *
     * 广告
     * @return
     */
    public List<BaseBean> advList() {

        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();

        sql.append(" select pp.goodsName,pp.PackagingDate,pp.image,pp.goodsID,pp.model,pp.ppid,pp.type,pp.companyid,s.staffname");
        sql.append(" from dt_ProductPackaging pp, dt_hr_staff s");
        sql.append(" where pp.type = ? and pp.review = ? and pp.staffid = s.staffid and pp.companyid is null");
        sql.append(" order by CASE pp.categoryName  WHEN '个人认领' THEN 1 ELSE 2 END ASC,pp.PackagingDate desc");
        objList.add("会员分享");
        objList.add("00");

        ;
       PageForm pageForm = baseBeanService.getPageFormBySQL(
               1, 6, sql.toString(), "select count(*) from (" + sql + ")",
                objList.toArray());
        List<BaseBean> list = pageForm!=null?pageForm.getList():null;
        return list;
    }

    /**
     *
     * 查询促销品
     * @param
     * @return
     */
    public List<Object> getCxList(PageForm pageForm){

          StringBuilder sql = new StringBuilder();
          sql.append("select p.goodsname, m.ptppid, p.image,m.ppid,ps.re_price");
          sql.append(" from dt_promotion  m,dt_productpackaging p,dt_pro_setup  ps");
          sql.append(" where  p.showweixin = '01' and m.ptppid = p.ppid and ps.ppid = m.ptppid");
          sql.append(" and m.ppId in(");


          List<Object> params = new ArrayList<Object>();

          for(int i = 0;i<pageForm.getList().size();i++){
              Object obj = pageForm.getList().get(i);
              Object[] objs = ( Object[])obj;
              if(i!=pageForm.getList().size()-1){
                  sql.append("?,");
              }else{
                  sql.append("?");
              }

              params.add(objs[1]);

          }
          sql.append(")");

          List<Object> list= baseBeanDao.getListObjectBySqlAndParams(sql.toString(),params.toArray());


          return list;
      }

     /* * 查询促销品
     * @param
     * @return
             */
    public List<Object> getCxList(Map<String,List<Object>>  mainlist){
     //   List<Object> mlist = new ArrayList<Object>();

//        for (Map.Entry<String, List<Object>> entry : mainlist.entrySet()) {
//            mlist.add(entry.getValue());
//        }

        StringBuilder sql = new StringBuilder();
        sql.append("select p.goodsname, m.ptppid, p.image,m.ppid,ps.re_price");
        sql.append(" from dt_promotion  m,dt_productpackaging p,dt_pro_setup  ps");
        sql.append(" where  p.showweixin = '01' and m.ptppid = p.ppid and ps.ppid = m.ptppid");
        sql.append(" and m.ppId in(");


        List<Object> params = new ArrayList<Object>();
        for (Map.Entry<String, List<Object>> entry : mainlist.entrySet()) {
         List<Object> mlist = entry.getValue();
            for(int i = 0;i<mlist.size();i++){
                Object obj = mlist.get(i);
                Object[] objs = ( Object[])obj;
                    sql.append("?,");
                    params.add(objs[1]);

            }
       }

        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");

        List<Object> list= baseBeanDao.getListObjectBySqlAndParams(sql.toString(),params.toArray());


        return list;
    }
    //获取店铺信息
    public ContactCompany getShopInfo(String companyID){
        String  hql = "from ContactCompany m where m.ccompanyID = (select c.ccompanyId from CcomCom c where c.comanyId = ?)";

        ContactCompany  contactCompany =  (ContactCompany)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{companyID});


        return contactCompany;
    }

    /**
     *
     * 获取粉丝相关
     * @param sccid
     * @param companyID
     * @return
     */
    public Map<String,String> getJoinFans(String sccid,String companyID){
        Map<String,String> mp = new HashMap<String,String>();

       String hql = "from TEshopCusCom where companyId = ?";
       TEshopCusCom tEshopCusCom = (TEshopCusCom)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{companyID});
       String zsccid = tEshopCusCom.getSccId();
        //判断是否关注了，
        if(sccid==null||sccid.equals("")){
            //没有登陆，显示没有关注
            mp.put("g","0");
        }else{
              //登陆了，查询下
            String hqlc = "from JoinFans where fsccId = ? and zsccId = ?";
            JoinFans joinFans = (JoinFans)baseBeanDao.getBeanByHqlAndParams(hqlc,new Object[]{sccid,zsccid});
            if(joinFans!=null){
                mp.put("g","1");
            }else{
                mp.put("g","0");
            }
        }
        String hqlc = "from JoinFans where zsccId = ?";
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hqlc,new Object[]{zsccid});
        if(list!=null&&list.size()>0){
            mp.put("c",list.size()+"");
        }else{
            mp.put("c","0");
        }
        return mp;
    }

    /**
     *
     * 关注和取消关注
     * @param companyId
     * @param sccId
     */
    @Transactional
    public String addJoinFans(String companyId,String sccId){
       String result = "";
        TEshopCusCom zts = (TEshopCusCom)baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where companyId = ?",new Object[]{companyId});


        String hqlc = "from JoinFans where fsccId = ? and zsccId = ?";
        JoinFans joinFans = (JoinFans)baseBeanDao.getBeanByHqlAndParams(hqlc,new Object[]{sccId,zts.getSccId()});
        if(joinFans!=null){
                //关注过了，取消关注
            baseBeanDao.deleteBeanByKey(JoinFans.class,joinFans.getJfKey());
            result = "0";
        }else{
            TEshopCusCom fts = (TEshopCusCom)baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?",new Object[]{sccId});
            JoinFans jf1 = new JoinFans();
            jf1.setJfID(serverService.getServerID("jfID"));
            jf1.setFaccount(fts.getAccount());
            jf1.setSource("关注");
            jf1.setState("00");
            jf1.setZaccount(zts.getAccount());
            jf1.setZsccId(zts.getSccId());
            jf1.setFsccId(fts.getSccId());
            jf1.setCompany(zts.getPseudoCompanyName());
            jf1.setFansDate(new Date());
            jf1.setStaffid(fts.getStaffid());
            baseBeanDao.update(jf1);
            result = "1";
        }
        return result;

      }

    /**
     * 获取商品信息
     *
     * @param param
     * @param companyId
     * @return
     */
    public Map<String, Object> getProductByParam(String param, String companyId) {
        List<Object> params = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<>();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT P.GOODSNAME,P.BARCODE,P.PPID,P.GOODSID,P.COMPANYID,NVL(I.WAREHOUSENAME,'-'),");
            sql.append("NVL(I.INVENQUANTITY,0),NVL(P.VARIABLEID,'-'),P.STANPRO,NVL(P.SINGLEWEIGHT,'-')");
            sql.append(" FROM DT_PRODUCTPACKAGING P LEFT JOIN DT_INV_INVT I ON P.PPID = I.PRODUCTID");
            sql.append(" WHERE P.COMPANYID = ?");
            params.add(companyId);
            sql.append(" AND P.DELSTATUS = ?");
            params.add("00");
            if (param != null && !param.equals("")) {
                sql.append("  and (p.goodsname like ? or p.barcode like ?)");
                params.add("%" + param + "%");
                params.add("%" + param + "%");
            }
            sql.append(" ORDER BY P.PPID DESC");
            List<Object> list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params.toArray());
            map.put("list", list);
            map.put("flag", 0);
        } catch (Exception e) {
            map.put("flag", 1);
            map.put("error", 1);
            map.put("msg", e.getMessage());
        }
        return map;
    }
}