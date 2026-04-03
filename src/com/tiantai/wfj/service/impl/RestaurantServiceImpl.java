package com.tiantai.wfj.service.impl;

import hy.ea.bo.CCode;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProductPackaging;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mobile.tiantai.android.bo.KuaiJieTianJia;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tiantai.wfj.bo.Cart;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.service.RestaurantService;

@Service
public class RestaurantServiceImpl
        implements RestaurantService {
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private ServerService idgec;

    @Resource
    private BaseBeanService baseBeanService;


    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;

    private Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);


    /**
     * 初始化行业数据e.g餐饮
     *
     * @param companyID
     */

    public String initIndustryData(String companyID) {
        // 创建本店特色默认菜品分类-物品类别：菜品 -上级：菜品
        String hqlp = "from ProductPackaging where companyID = ? and goodsname = ?";
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hqlp, new Object[]{companyID, "菜品"});
        if (list.size() != 0) {
            return "1";
        }
        List<BaseBean> baseBeans = new ArrayList<BaseBean>();
        //固定资产 scode20100707zdarg24eyj0000000027
        //物品类别：scode20101014v5zed7cukk0000000002
        //创建物品类别-餐饮-菜品
        CCode ccode1 = new CCode();
        ccode1.setCodeID(idgec.getServerID("ccode"));
        ccode1.setCodeValue("餐饮");
        ccode1.setCompanyID(companyID);
        ccode1.setCodePID("scode20101014v5zed7cukk0000000002");
        ccode1.setCodeNumber(15);
        ccode1.setCodeSn("15");
        ccode1.setCodeStatus("01");
        baseBeans.add(ccode1);

        CCode ccode2 = new CCode();
        ccode2.setCodeID(idgec.getServerID("ccode"));
        ccode2.setCodeValue("菜品");
        ccode2.setCompanyID(companyID);
        ccode2.setCodePID(ccode1.getCodeID());
        ccode2.setCodeNumber(1501);
        ccode2.setCodeSn("1501");
        ccode2.setCodeStatus("01");
        baseBeans.add(ccode2);



        CCode ccode3 = new CCode();
        ccode3.setCodeID(idgec.getServerID("ccode"));
        ccode3.setCodeValue("餐桌");
        ccode3.setCompanyID(companyID);
        ccode3.setCodePID(ccode1.getCodeID());
        ccode3.setCodeNumber(1502);
        ccode3.setCodeSn("1502");
        ccode3.setCodeStatus("01");
        baseBeans.add(ccode3);



        //创建菜品物品-物品类别：菜品
        //GoodsManage
        GoodsManage goodsManage = new GoodsManage();
        goodsManage.setGoodsID(idgec.getServerID("goodsID"));
        goodsManage.setGoodsName("菜品");
        goodsManage.setCompanyID(companyID);
        goodsManage.setTypeID("菜品");
        goodsManage.setFiveClear("4");
        baseBeans.add(goodsManage);

        GoodsManage goodsManage2 = new GoodsManage();
        goodsManage2.setGoodsID(idgec.getServerID("goodsID"));
        goodsManage2.setGoodsName("本店特色");
        goodsManage2.setCompanyID(companyID);
        goodsManage2.setFiveClear("4");
        goodsManage2.setTypeID("菜品");
        baseBeans.add(goodsManage2);

        //产品

        ProductPackaging pp = new ProductPackaging();
        pp.setPpID(idgec.getServerID("ppid"));
        pp.setCompanyID(companyID);
        pp.setGoodsName("菜品");
        pp.setType("菜品");
        pp.setDelStatus("00");
        pp.setFiveClear("4");
        pp.setGoodsID(goodsManage2.getGoodsID());
        baseBeans.add(pp);

        ProductPackaging pp1 = new ProductPackaging();
        pp1.setPpID(idgec.getServerID("ppid"));
        pp1.setCompanyID(companyID);
        pp1.setGoodsName("本店特色");
        pp1.setParentId(pp.getPpID());
        pp1.setParentName("菜品");
        pp1.setDelStatus("00");
        pp1.setFiveClear("4");
        pp1.setGoodsID(goodsManage.getGoodsID());
        pp1.setType("菜品");

        baseBeans.add(pp1);

        String hql = "update ProductPackaging set parentId = ?,parentName = ? where  companyID = ?";

        baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeans, new String[]{hql}, new Object[]{pp1.getPpID(), pp1.getGoodsName(), companyID});


        return "2";


    }


    /**
     * 扫码支付
     *
     * @param ppid
     * @return
     */
    public ContactCompany scanCodePay(String ppid) {

        String hql = "from ProductPackaging where ppID = ?";
        ProductPackaging pp = (ProductPackaging) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{ppid});
        String hqlcom = "from ContactCompany where ccompanyID = (select c.ccompanyId from CcomCom c where c.comanyId = ?)";
        ContactCompany contactCompany = (ContactCompany) baseBeanDao.getBeanByHqlAndParams(hqlcom, new Object[]{pp.getCompanyID()});
        return contactCompany;


    }

    /**
     * 扫码加入购物车
     *
     * @param sccid
     * @param ppid
     * @return
     */
    public String joinShopCart(String sccid, String ppid) {
        String result = "";
        List<BaseBean> beans = new ArrayList<BaseBean>();
        String stardard = "默认规格";
        JSONObject obj = new JSONObject();
        //用户信息
        String scchql = "from TEshopCusCom where sccid = ?";
        TEshopCusCom scc = (TEshopCusCom) baseBeanService
                .getBeanByHqlAndParams(scchql,
                        new Object[]{sccid});
        Cart cat = (Cart) baseBeanService.getBeanByHqlAndParams(
                "from Cart where staffId=? and pid=?", new String[]{
                        scc.getStaffid(), ppid});

        ProSetup pp = null;
        if (cat == null) {
            StringBuffer hql = new StringBuffer();
            hql.append("select new ProSetup(e.comId,e.ppid,e.ppname,e.rePrice,pp.photo as photoPath) ");
            hql.append(" from ProSetup e,ProductPackaging pp ");
            hql.append("where e.ppid=pp.ppID and e.ppid=?");
            pp = (ProSetup) baseBeanService.getBeanByHqlAndParams(hql.toString(), new String[]{ppid});
            if (pp == null) {
                pp = new ProSetup();
            }
            cat = new Cart();
            cat.setCartId(serverService.getServerID("cart"));
            cat.setCompanyId(pp.getComId());
            cat.setItemNum(1);
            cat.setPid(ppid);
            cat.setPname(pp.getPpname());
            cat.setPrice(pp.getRePrice());
            cat.setStaffId(scc.getStaffid());
            cat.setPic(pp.getPhotoPath());
            cat.setStardard(stardard);
            cat.setPos("0");
            beans.add(cat);
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);

        } else {

            pp =  (ProSetup)baseBeanDao.getBeanByHqlAndParams("from ProSetup e where e.ppid = ?",new Object[]{ppid});
            baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{"update Cart u set u.itemNum=u.itemNum+1 where u.staffId=? and u.pid=? and stardard = ?"}, new String[]{scc.getStaffid(), ppid, stardard});
        }

        return pp.getComId();
    }

    /***
     *
     * 查询当前公司的购物车
     * @param companyID
     * @param staffID
     * @return
     */
    public Map<String, Object> queryShopCart(String companyID, String staffID) {

        Map<String, Object> map = new HashMap<String, Object>();

        StringBuilder sql = new StringBuilder();
        sql.append("select ps.ppid,p.goodsname,p.image,p.type,ps.re_price,p.companyid,m.companyName,c.itemNum,c.stardard,c.cart_Id");
        sql.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p,DT_cart c,dtCompany m");
        sql.append(" where m.companyID = p.companyId and p.ppid=ps.ppid and p.ppid = c.pid");
        sql.append(" and ps.fcom_id is null and c.staff_id = ? and p.showweixin = ? and m.companyID = ?");

        List<Object> beanList = baseBeanService.getListBeanBySqlAndParams(sql.toString(),
                new Object[]{staffID, "01",companyID});

        List<String> comlist = new ArrayList<String>();
        List<Object> comps = new ArrayList<Object>();

        sql.delete(0, sql.length());

        sql.append("select c.companyId,c.companyName,dc.ccompanyid,dc.logoPath,1+nvl(s.total_pct,0)/100");
        sql.append(" from dtCompany c left join dt_ccom_com cc on c.companyId = cc.compnay_id");
        sql.append(" left join dtContactCompany dc on dc.ccompanyId = cc.ccompany_Id");
        sql.append(" left join dt_set_subsidize s");
        sql.append(" on dc.industrytype = s.gtid and s.stutas=? where c.companyId = ?");

        Object objcom = baseBeanService.getObjectBySqlAndParams(sql.toString(), new Object[]{"01",companyID});
        comps.add(objcom);
        map.put("comps", comps);
        map.put("beanList", beanList);
        return map;


    }

    @Override
    public void addComapnyKuaiJie(String sccid, String companyId) {
        KuaiJieTianJia kuaijie = (KuaiJieTianJia) baseBeanService.getBeanByHqlAndParams(" from KuaiJieTianJia where sccid = ? and companyId = ?", new Object[]{sccid, companyId});
        if (kuaijie == null) {
            KuaiJieTianJia kj = new KuaiJieTianJia();
            kj.setKuaiJieId(serverService.getBillID("keyid"));
            kj.setCompanyId(companyId);
            kj.setSccid(sccid);
            kj.setMingciDate(new Date());
            baseBeanService.save(kj);
        } else {
            kuaijie.setMingciDate(new Date());
            baseBeanService.update(kuaijie);
        }
    }

    /**
     * 查询餐饮公司的分类
     * @param companyId
     * @return
     */
    public List<BaseBean> getALLCate(String companyId){

        String hql = "from SchProCategory s where s.companyId = ? and s.status = ? and s.categoryId in(select p.categoryId from ProductPackaging p where p.companyID = ? and p.showweixin=?)";
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{companyId,"2",companyId,"01"});

        return list;
    }

    /**
     *
     * 根据分类查询商品
     * @param companyId
     * @param cateID
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageForm getProductByCate(String companyId, String staffid,String posNum,String cateID, int pageNumber, int pageSize){
        List<Object> params = new ArrayList<Object>();
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());
        StringBuilder sql = new StringBuilder();
        sql.append("select ps.ppid,p.goodsname,p.goodsID,p.image,ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2) lsprice,nvl(pv.vip,0),");
        sql.append("case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else 0  end price,");
        sql.append("nvl(z.itemNum,0),");
        sql.append("case when tpa.type is not null then '4' when pa.type is not null then '3' else '0' end pricetype,");
        sql.append("case when tpa.type is not null then to_char(tpap.actPriceId) when pa.type is not null then to_char(pap.actPriceId) else ps.suid end activityID ");

         sql.append(" from dt_SchProCategory cc right join dt_ProductPackaging p on cc.categoryid = p.categoryid");

        sql.append("  right join DT_PRO_SETUP ps on p.ppid = ps.ppid and ps.state = '00'");


        if(posNum!=null&&!posNum.equals("")){
            sql.append(" left join (select sum(c.itemNum) itemNum,c.pid from DT_SqSelfCart c where c.posNum = ? group by c.pid ) z on p.ppid = z.pid  ");

        }else {
            sql.append(" left join (select sum(c.itemNum) itemNum,c.pid from DT_cart c where c.staff_id = ? and (c.pos is null or c.pos = '0') group by c.pid ) z on p.ppid = z.pid  ");
        }
        //vip
        sql.append(" left join dt_pro_vip pv on pv.ppid = p.ppid and pv.state ='00' ");


        //特价
        sql.append(" left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state = '00'");
        sql.append(" left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01'");
        sql.append(" and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS')");

        //促销
        sql.append(" left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state = '00'");
        sql.append(" left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00'");
        sql.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS')");
        //消费红包
        sql.append(" left join Dt_Ccom_Com ccc on ccc.compnay_id = p.companyid");
        sql.append(" left join dtCompany t on t.companyid = ccc.compnay_id");
        sql.append(" left join dtContactCompany cm on ccc.ccompany_id = cm.ccompanyid");
        sql.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
        sql.append("  where ps.fcom_id is null and p.type!='扫码收款'");

        if(posNum!=null&&!posNum.equals("")){
            params.add(posNum);
        }else {
            if (staffid != null && !staffid.equals("")) {
                params.add(staffid);
            } else {
                params.add("%%");
            }
        }


        params.add(currentTime);
        params.add(currentTime);
        params.add(currentTime);
        params.add(currentTime);
        if(cateID!=null&&!cateID.equals("")) {
            sql.append(" and p.categoryId = ?");
            params.add(cateID);
        }else{
            sql.append(" and p.categoryId is null");
        }

        params.add(companyId);
        params.add("01");

        sql.append(" and p.companyID = ? and p.showweixin = ?");


        PageForm pageForm = baseBeanService.getPageFormBySQL(
                pageNumber,pageSize, sql.toString(), "select count(*) from (" + sql + ")",
                params.toArray());
        return pageForm;

    }

    /**
     *
     * 获取公司购物车数量
     * @param posNum
     * @param staffId
     * @param companyId
     * @return
     */
    public int getCompanyCartNum(String posNum,String staffId,String companyId){
        int goodNum = 0;

        try {
            if(posNum!=null&&!posNum.equals("")){
                String sql = "select nvl(sum(to_number(c.itemNum)),0) from DT_SqSelfCart c,dt_ProductPackaging  p where p.ppID = c.pid and c.posNum = ? and p.showweixin = ? and p.companyID = ?";
                goodNum = baseBeanService.getConutByBySqlAndParams(sql, new Object[]{posNum, "01",companyId});

            }else if (staffId != null) {

                String sql = "select nvl(sum(c.itemNum),0) from dt_Cart c,dt_ProductPackaging  p where p.ppID = c.pid and c.staff_Id = ? and p.showweixin = ? and p.companyID = ? and (c.pos is null or c.pos = '0')";
                goodNum = baseBeanService.getConutByBySqlAndParams(sql, new Object[]{staffId, "01",companyId});

            }

        } catch (Exception e) {


        }

           return  goodNum;
    }

    
    
    /**
     * 新闻
     * @return
     */
    public  List<BaseBean> getNewsList() {
        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();
        sql.append("select *  from (select *  from (");
        sql.append(" select p.goodsName,p.PackagingDate,p.image,p.goodsID,p.model,p.ppid,p.type,p.companyID,m.ccompany_Id,cc.companyname");
        sql.append(" from dt_ProductPackaging p, DT_ccom_com m,dtcontactcompany cc");
        sql.append(" where (p.companyId = ? and p.type=? and p.fiveclear=? and p.delStatus=? and p.companyID = m.compnay_id and m.ccompany_id = cc.ccompanyid)");
        sql.append(" or (p.type = ? and p.review = ? and p.companyID=m.compnay_id and m.ccompany_id = cc.ccompanyid and p.companyid is not null)");
        sql.append(" union");
        sql.append(" select pp.goodsName,pp.PackagingDate,pp.image,pp.goodsID,pp.model,pp.ppid,pp.type,pp.companyid,pp.companyid,s.staffname");
        sql.append(" from dt_ProductPackaging pp, dt_hr_staff s");
        sql.append(" where pp.type = ? and pp.review = ? and pp.staffid = s.staffid and pp.companyid is null) x  order by x.PackagingDate desc) xc where rownum <4");
        objList.add("company201009046vxdyzy4wg0000000065");
        objList.add("新闻");
        objList.add("2");
        objList.add("00");
        objList.add("会员分享");
        objList.add("00");
        objList.add("会员分享");
        objList.add("00");

        List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql.toString(),  objList.toArray());
               
        return list;
    }
}