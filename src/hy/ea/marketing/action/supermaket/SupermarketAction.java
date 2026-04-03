package hy.ea.marketing.action.supermaket;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiantai.wfj.bo.Cart;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.EarthIndexService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.Company;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.finance.ProSetup;
import hy.ea.marketing.service.SupermarketSerivce;
import hy.ea.util.StringUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;



@Controller
@Scope("prototype")
public class SupermarketAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private ServerService serverService;

    @Resource
    private SupermarketSerivce sumkSerivce;//mz

    private String posNum;//社区机器代码 mz
    private String result;
    private String pos;//购物车类别 0:正常购物车 1：代购购物车
    private String ccompanyID;//超市id
    private String companyId;//公司id
    private String companyName;//超市名称
    private String goodsName;//商品名称
    private String goodsId;//商品id
    private String goodsType;//商品类别[id]
    private String industryType;//行业类别//行业类别id
    private String codePID;//商品分类父id
    private String longitude;//经度
    private String latitude;//纬度
    private PageForm pageForm;
    private String ppid;//商品id
    private String stardard;//商品的属性(sku信息)[e.g颜色，尺码]
    private Integer itemNum;//购物车对应商品数量
    private String totalNum; //加入购物车数量
    private String sendNum;//待配送数量
    private String showNum;
    private String barCode;
    private String nopr;  //判断机器有无 操作
    private String back;
    private Object results;
    private String sgrId;
    private String relateID;

    private int lxType = 0;//查询类型，0：其他入口进入1：批发商城进入
    private String type;
    private Logger logger = Logger.getLogger(SupermarketAction.class);
    // 店铺对象


    // 商品对象

    @Resource
    private EarthIndexService earthIndexService;
    /**
     * 超市商品加入购物车
     * 时间:2018-9-8
     * 创建人:lnn
     *
     * @return
     */
    public String addShoppingCart() {

        JSONObject obj = new JSONObject();
        if (posNum != null && !posNum.equals("")) {//社区购物车

            sumkSerivce.sqJoinCart(ppid, stardard, totalNum, sendNum, posNum, barCode, showNum, sgrId, relateID);
        } else {
            //获取用户登录信息
            HttpSession session = ServletActionContext.getRequest().getSession();
            SessionWrap sw = SessionWrap.getInstance();
            HttpServletRequest request = ServletActionContext.getRequest();
            TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                    SessionWrap.KEY_CUSTOMER);
            if (cus == null) {
                logger.info(cus + "登录用户信息");
                String url = request.getHeader("Referer");
                session.setAttribute("url", url);
                obj.accumulate("login", "login");
                result = obj.toString();
                return "success";
            }
            String hqlcart = "from Cart where staffId=? and pid=? and stardard = ?";

            if (pos != null && pos.equals("1")) {
                hqlcart += " and pos = '1'";
            } else {
                hqlcart += " and (pos is null or pos = '0')";
            }
            Cart cat = (Cart) baseBeanService.getBeanByHqlAndParams(hqlcart, new Object[]{
                    cus.getStaffid(), ppid, stardard});
            if (cat == null) {//购物车中不存在此商品>>添加该商品至购物车
                StringBuffer hql = new StringBuffer();
                hql.append("select new ProSetup(e.comId,e.ppid,e.ppname,e.rePrice,pp.image as photoPath) ");
                hql.append(" from ProSetup e,ProductPackaging pp ");
                hql.append("where e.ppid=pp.ppID and e.ppid=?");
                ProSetup pp = (ProSetup) baseBeanService.getBeanByHqlAndParams(hql.toString(), new String[]{ppid});
                if (pp == null) {
                    pp = new ProSetup();
                }
                cat = new Cart();
                cat.setCartId(serverService.getServerID("cart"));
                cat.setCompanyId(pp.getComId());
                cat.setItemNum(itemNum);
                cat.setPid(ppid);
                cat.setPname(pp.getPpname());
                cat.setPrice(pp.getRePrice());
                cat.setStaffId(cus.getStaffid());
                cat.setPic(pp.getPhotoPath());
                cat.setStardard(stardard);//规格
                cat.setPos(pos);//购物车类型[默认:0 正常购物车]
                this.baseBeanService.save(cat);
                obj.accumulate("sect", "1");

            } else {//购物车中已存在此商品>>商品数量增加
                String updatehql = "update Cart u set  u.itemNum=u.itemNum+? where u.staffId=? and u.pid=? and u.stardard = ? and u.pos ='0'";
                //修改购物车商品数量
                this.baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{updatehql}, new Object[]{itemNum, cus.getStaffid(), ppid, stardard});
                obj.accumulate("sect", "2");
            }
        }
        result = obj.toString();
        return "success";
    }

    /**
     * 减少购物车商品数量
     * 时间:2018-9-18
     * 创建人:lnn
     *
     * @return
     */
    public String reduceShoppingCart() {
        JSONObject obj = new JSONObject();
        if (posNum != null && !posNum.equals("")) {//社区购物车
            sumkSerivce.sqReduceCart(ppid, stardard, totalNum.toString(), sendNum, posNum, barCode, showNum);
        } else {
            HttpSession session = ServletActionContext.getRequest().getSession();
            SessionWrap sw = SessionWrap.getInstance();
            HttpServletRequest request = ServletActionContext.getRequest();
            TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                    SessionWrap.KEY_CUSTOMER);
            if (cus == null) {
                logger.info("用户未登录");
                String url = request.getHeader("Referer");
                session.setAttribute("url", url);
                obj.accumulate("login", "login");
                result = obj.toString();
                return "success";
            }
            String hqlcart = "from Cart where staffId=? and pid=? and stardard = ?";
            if (pos != null && pos.equals("1")) {
                hqlcart += " and pos = '1'";
            } else {
                hqlcart += " and (pos is null or pos = '0')";
            }
            Cart cat = (Cart) baseBeanService.getBeanByHqlAndParams(hqlcart, new Object[]{
                    cus.getStaffid(), ppid, stardard});
            if (cat == null) {
                obj.accumulate("sect", "4");//购物车商品数量减少异常
            } else {//购物车中存在此商品
                if (cat.getItemNum() != null && cat.getItemNum() == 1) {//从购物车中删除该商品
                    List<Object[]> parms = new ArrayList<Object[]>();
                    String sql = "delete from DT_cart dc where dc.cart_id = ? and dc.staff_id = ?";
                    Object[] temp2 = new Object[2];
                    temp2[0] = cat.getCartId();
                    temp2[1] = cus.getStaffid();
                    parms.add(temp2);
                    this.baseBeanService.executeSqlsByParmsList(null, new String[]{sql}, parms);
                } else {//购物车商品数量减1
                    String updatehql = "update Cart u set  u.itemNum=u.itemNum-1 where u.staffId=? and u.pid=? and u.stardard = ? and u.pos ='0'";
                    this.baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{updatehql}, new String[]{cus.getStaffid(), ppid, stardard});
                    obj.accumulate("sect", "-1");
                }
            }
        }
        result = obj.toString();
        return "success";
    }


    /**
     * 查询购物车商品数目
     * 2018-10-14
     */

    public String shoppingCartGoodsNum() {
        Map<String, Object> map = new HashMap<String, Object>();
        int goodNum = 0;
        if (posNum != null && !posNum.equals("")) {//社区购物车
            Object obj = sumkSerivce.sqTotalNumCart(posNum, ccompanyID);
            Object[] objs = (Object[]) obj;
            if (obj != null) {
                goodNum = Math.round(Float.parseFloat(objs[0].toString()));
                if (objs[1].toString().equals("1")) {
                    map.put("relateID", serverService.getServerID("relateID"));
                } else {
                    map.put("relateID", objs[1].toString());
                }
            }
            map.put("goodNum", goodNum);
        } else {
            HttpSession session = ServletActionContext.getRequest().getSession();
            SessionWrap sw = SessionWrap.getInstance();
            TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, sw.KEY_CUSTOMER);

            if (cus != null) {
                //获取购物车商品数量
                try {
                    String sql = "select nvl(sum(c.itemNum),0) from dt_Cart c,dt_ProductPackaging  p where p.ppID = c.pid and c.staff_Id = ? and p.showweixin = ? and  c.pos = '0' and c.companyid =?";
                    goodNum = baseBeanService.getConutByBySqlAndParams(sql, new Object[]{cus.getStaffid(), "01", companyId});
                    map.put("goodNum", goodNum);
                    //    logger.info("获取购物车商品数量" + goodNum);
                } catch (Exception e) {
                    map.put("goodNum", goodNum);
                    //  e.printStackTrace();
                    //  logger.error("获取购物车商品数量异常");
                }
            }
        }
        JSONObject obj = JSONObject.fromObject(map);

        result = obj.toString();
        return "success";
    }

    /**
     * 查询某个商品在购物车中的数量
     * 2018-10-14
     */

    public String shoppingCartGoodsNumByPpid() {
        Map<String, Object> map = new HashMap<String, Object>();
        int goodNum = 0;

        if (posNum != null && !posNum.equals("")) {//社区购物车
            Object obj = sumkSerivce.sqPidTotalNumCart(posNum, ppid);

            Object[] objs = (Object[]) obj;
            if (obj != null) {
                goodNum = Integer.parseInt(objs[0].toString());
                if (objs[1].toString().equals("1")) {
                    map.put("sgrId", serverService.getServerID("xn"));
                } else {
                    map.put("sgrId", objs[1].toString());
                }
            }
            map.put("goodNum", goodNum);
            map.put("login", 200);//已登录
        } else {
            HttpSession session = ServletActionContext.getRequest().getSession();
            SessionWrap sw = SessionWrap.getInstance();
            TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, sw.KEY_CUSTOMER);

            if (cus != null) {
                //获取购物车某个商品的数量
                try {
                    String sql = "select nvl(sum(c.itemNum),0) from dt_Cart c,dt_ProductPackaging  p where p.ppID = c.pid and c.staff_Id = ? " +
                            "and p.showweixin = ? and  c.pos = '0' and c.companyid =?  and p.ppID =? and c.stardard = '默认规格' ";
                    goodNum = baseBeanService.getConutByBySqlAndParams(sql, new Object[]{cus.getStaffid(), "01", companyId, ppid});
                    map.put("goodNum", goodNum);
                    map.put("login", 200);//已登录
                    //   logger.info("获取购物车某个商品的数量" + goodNum);
                } catch (Exception e) {
                    map.put("goodNum", goodNum);
                    e.printStackTrace();
                    logger.error("获取购物车某个商品的数量异常");
                }
            } else {
                map.put("login", 400);//登录异常，重新登录
                logger.error("登录异常");
            }
        }
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 获取商品属性(sku信息)
     * 2018-10-24
     *
     * @return
     */
    public String getGoodsSKU() {
        //获取商品sku信息
        Map<String, Object> map = new HashMap<String, Object>();
        String sql0 = "select dta.apid,dta.attriname,dta.attrivalue from dtAttriProduction dta where dta.type ='0' and dta.goodsid =?";
        List<BaseBean> list0 = this.baseBeanService.getListBeanBySqlAndParams(sql0, new Object[]{goodsId});
        if (list0 != null && list0.size() > 0) {
            String sql1 = "select dta.apid,dta.attriname,dta.attrivalue from dtAttriProduction dta where dta.type ='1'and dta.goodsid =?";
            List<BaseBean> list1 = this.baseBeanService.getListBeanBySqlAndParams(sql1, new Object[]{goodsId});
            map.put("sku0", list0);
            map.put("sku1", list1);
            map.put("code", "200");
            logger.info("获取商品sku信息");
        } else {
            //该商品无sku信息》设置为默认规格
            map.put("code", "400");
            //   logger.info("该商品无sku信息");
        }
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 功能:查询超市有关商品
     * 时间:2018-8-9
     * 创建人:lnn
     *
     * @return
     */
    public String getSupermarketGoodsList() {
        Map<String, Object> map = new HashMap<String, Object>();
        //参数非空校验
        if (ccompanyID != null && !ccompanyID.equals("")) {
            List<Object> parms = new ArrayList<Object>();
            Company company = (Company) beandao.getBeanByHqlAndParams("select c from Company c ,CcomCom t where c.companyID=t.comanyId and t.ccompanyId=?", new Object[]{ccompanyID});
            //公司非空校验
            if (company != null && company.getCompanyID() != null && !company.getCompanyID().equals("")) {
                StringBuilder sql = new StringBuilder();
                //获取当前时间
                SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = e.format(new Date());
                parms.add(currentTime);
                parms.add(currentTime);
                //商品类型id非空校验
                if (goodsType != null && !goodsType.equals("")) {//按商品分类id查询商品
                    sql.append("select ps.ppid,ROUND(re_price*(1+nvl(sz.total_pct,0)/100),2),dpp.goodsid,dpp.goodsName,dpp.image,dpp.companyid,dpp.standard");
                    sql.append(",ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2) actPrice,ROUND(pv.vip*(1+nvl(sz.total_pct,0)/100),2) vip,pa.type actType, ");
                    sql.append(" ROUND(dpw.wholesale*(1+nvl(sz.total_pct,0)/100),2) wholesalePrice, dpw.wholesaleId ");//批发价=批发价*批发价的倍数
                    sql.append("from dt_ProductPackaging dpp ");
                    sql.append("inner join DT_PRO_SETUP ps on dpp.ppid = ps.ppid ");
                    sql.append("inner join dt_ProCateRelate dpc on dpp.ppid = dpc.ppid ");
                    sql.append("left join Dt_Ccom_Com cc on cc.compnay_id = dpp.companyid ");
                    sql.append("left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");
                    sql.append("left join dt_set_subsidize sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
                    sql.append("left join dt_pro_activity_price pap on pap.ppid = dpp.ppid and pap.state ='00' ");
                    sql.append("left join dt_pro_vip pv on pv.ppid = dpp.ppid and pv.state ='00' ");
                    sql.append("left join dt_pro_wholesale dpw on dpp.ppid = dpw.ppid and dpw.state = '00' ");//批发价
                    sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid ");
                    sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                    sql.append("and pa.state <> '04' and pa.state <> '03' and pa.state <> '02' ");
                    sql.append("where dpp.showweixin='01' and ps.state ='00'");
                    //商品名称非空校验
                    if (goodsName != null && !goodsName.equals("")) {
                        sql.append(" and dpp.goodsname like ?");
                        parms.add("%" + goodsName.trim() + "%");
                    }
                    sql.append(" and dpc.codeid = ?");
                    parms.add(goodsType.trim());

                    sql.append(" and dpp.companyid = ?");
                    parms.add(company.getCompanyID());
                } else {//查询所有分类的商品
                    sql.append("select ps.ppid,ROUND(re_price*(1+nvl(sz.total_pct,0)/100),2),dpp.goodsid,dpp.goodsName,dpp.image,dpp.companyid,dpp.standard");
                    sql.append(",ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2) actPrice,ROUND(pv.vip*(1+nvl(sz.total_pct,0)/100),2) vip,pa.type actType, ");
                    sql.append(" ROUND(dpw.wholesale*(1+nvl(sz.total_pct,0)/100),2) wholesalePrice, dpw.wholesaleId ");//批发价=批发价*批发价的倍数
                    sql.append("from dt_ProductPackaging dpp ");
                    sql.append("inner join DT_PRO_SETUP ps on dpp.ppid = ps.ppid ");
                    sql.append("left join Dt_Ccom_Com cc on cc.compnay_id = dpp.companyid ");
                    sql.append("left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");
                    sql.append("left join dt_set_subsidize sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
                    sql.append("left join dt_pro_activity_price pap on pap.ppid = dpp.ppid and pap.state ='00' ");
                    sql.append("left join dt_pro_vip pv on pv.ppid = dpp.ppid and pv.state ='00' ");
                    sql.append("left join dt_pro_wholesale dpw on dpp.ppid = dpw.ppid and dpw.state = '00' ");//批发价
                    sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid ");
                    sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                    sql.append("and pa.state <> '04' and pa.state <> '03' and pa.state <> '02' ");
                    sql.append("where dpp.showweixin='01' and ps.state ='00' ");
                    //商品名称非空校验
                    if (goodsName != null && !goodsName.equals("")) {
                        sql.append(" and dpp.goodsName like ?");
                        parms.add("%" + goodsName.trim() + "%");
                    }
                    sql.append(" and dpp.companyid = ?");
                    parms.add(company.getCompanyID());
                }
                if(lxType == 1){//类型为0是其他入口进入1为批发商城入库进入
                    sql.append(" and dpw.wholesale is not null ");
                }
                //当前页
                int pageNumber = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
                //每页显示最大数[默认显示20条]
                int pageSize =  20 ;
                //通过查询条件获取该超市的相应商品
                pageForm = this.baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", parms.toArray());
                //非空校验
//                if (pageForm != null) {
//                    List pflist = pageForm.getList();
//                    //非空校验
//                    if (pflist.size() > 0) {
//                        List aList = new ArrayList();
//                        List<BaseBean> ssl = baseBeanService.getListBeanByHqlAndParams("from SetSubsidize where stutas=?", new Object[]{"01"});
//                        if (ssl != null && ssl.size() > 0) {//非空校验
//                            for (int i = 0, fList = pflist.size(); i < fList; i++) {
//                                Object[] pfo = (Object[]) pflist.get(i);
//                                for (int j = 0; j < ssl.size(); j++) {
//                                    SetSubsidize s = (SetSubsidize) ssl.get(j);
//                                    BigDecimal pct = new BigDecimal(s.getTotalPct()).divide(new BigDecimal(100)).add(BigDecimal.ONE);
//                                    if (s.getGtid().equals(industryType)) {//行业类别判断
//                                        pfo[1] = new BigDecimal(pfo[1].toString()).multiply(pct).setScale(2, BigDecimal.ROUND_HALF_UP);
//                                        break;
//                                    }
//                                }
//                                aList.add(pfo);
//                            }
//                            pageForm.setList(aList);
//
//                        }
//                    }
//
//                }
                map.put("pageForm", pageForm);
                map.put("companyId", company.getCompanyID());
            } else {
                logger.error("获取商品失败,公司id不存在");
                map.put("pageForm", null);
            }

        } else {
            logger.error("获取商品失败,公司id不存在");
            map.put("pageForm", null);
        }
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 功能:查询相关超市[往来单位]
     * 时间:2018-8-2
     * 创建人:lnn
     *
     * @return
     */
    public String getSupermarketList() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);

        if(tc!=null) {

            earthIndexService.addBrowseHistory(tc.getSccId(),"超市","");

        }


        Map<String, Object> map = new HashMap<String, Object>();
        List<Object> parms = new ArrayList<Object>();
        //当前页
        int pageNumber = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
        //每页显示最大数[默认显示10条]
        int pageSize = 10;
        //参数非空校验
        if (longitude != null && !longitude.equals("") && latitude != null && !latitude.equals("")) {
            StringBuilder sql = new StringBuilder("select s.ccompanyid,s.companyname,s.companyaddr,s.logopath,s.industrytype ," +
                    "(2*asin(sqrt(power(sin((" + longitude + "-s.accuracy)*3.14159265359/180/2),2)+cos(" + latitude + "*3.14159265359/180)" +
                    "*cos(s.dimension*3.14159265359/180)*power(sin((" + latitude + "-s.dimension)*3.14159265359/180/2),2)))*6378.137*1000)" +
                    "as distance from dtcontactcompany s where 1=1");
            //参数非空校验
            if (industryType != null && !industryType.equals("")) {//行业类型id校验
                sql.append(" and s.industryid = ?");
                parms.add(industryType);
            }
            if (companyName != null && !companyName.equals("")) {
                sql.append(" and companyname like '%" + companyName + "%'");
            }
            sql.append(" order by distance");
            //通过查询条件获取相应超市
            pageForm = this.baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", parms.toArray());
            map.put("pageForm", pageForm);
            //自动定位成功返回200
            map.put("code", "200");
        } else {
            StringBuilder sql = new StringBuilder("select ccompanyid,companyname,companyaddr,logopath,industrytype from dtcontactcompany where 1=1");
            //参数非空校验
            if (industryType != null && !industryType.equals("")) {//行业类型id校验
                sql.append(" and industryid = ?");
                parms.add(industryType);
            }
            if (companyName != null && !companyName.equals("")) {
                sql.append(" and companyname like '%" + companyName + "%'");
            }
            //通过查询条件获取相应超市
            pageForm = this.baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", parms.toArray());
            map.put("pageForm", pageForm);
            //自动定位失败返回201
            //logger.error("自动定位失败");
            map.put("code", "201");
        }
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";

    }

    public String getShopByType(){
        Map<String, Object> map = new HashMap<String, Object>();
        List<Object> parms = new ArrayList<Object>();
        HttpServletRequest request = ServletActionContext.getRequest();
        String shopType = request.getParameter("shopType");
        String sort = request.getParameter("sort");
        String longitude=request.getParameter("longitude");
        String latitude=request.getParameter("latitude");
        String conditions=SwitchCase(sort);
        //当前页
        int pageNumber = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
        //每页显示最大数[默认显示10条]
        int pageSize = 10;
        String sql="SELECT" +
                "      CP.COMPANYID," +
                "      CC.CCOMPANYID," +
                "      CC.COMPANYNAME," +
                "      CC.RESPONSIBLETEL," +
                "      CC.LOGOPATH," +
                "      GM.PHOTOPATH," +
                "      GM.GOODSNAME," +
                "      GM.NUM" +
                "    FROM" +
                "      DTCOMPANY CP " +
                "      INNER JOIN DTGOODSMANAGE GM ON GM.COMPANYID = CP.COMPANYID " +
                "      INNER JOIN DT_CCOM_COM CCC ON CCC.COMPNAY_ID = CP.COMPANYID " +
                "      INNER JOIN DTCONTACTCOMPANY CC ON CC.CCOMPANYID = CCC.CCOMPANY_ID " +
                "      INNER JOIN DT_PRODUCTPACKAGING pp ON GM.GOODSID = pp.GOODSID "+
                "    WHERE" +
                "      CP.COMPANYSTATUS = '00' " +
                "      AND CP.INDUSTRYTYPE like ? " +
                "order by "+
                conditions+
                "    ";

        //通过查询条件获取相应超市
       
        List goods = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"%" + shopType + "%"});
//        pageForm = this.baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", parms.toArray());

        //查店铺
       String shopSql=" SELECT" +
               "      CP.COMPANYID, " +
               "      CC.CCOMPANYID, " +
               "      CC.COMPANYNAME, " +
               "      CC.LOGOPATH ," +
               "      CC.RESPONSIBLETEL," +
               "MAX("+conditions+") AS LASTTIME,"+
               "(CC.ACCURACY-"+longitude+")*(CC.ACCURACY-"+longitude+") + (CC.DIMENSION-"+latitude+")*(CC.DIMENSION-"+latitude+") AS SORTVALUE"+
               "    FROM " +
               "      DTCOMPANY CP " +
               "      INNER JOIN DTGOODSMANAGE GM ON GM.COMPANYID = CP.COMPANYID " +
               "      INNER JOIN DT_CCOM_COM CCC ON CCC.COMPNAY_ID = CP.COMPANYID " +
               "      INNER JOIN DTCONTACTCOMPANY CC ON CC.CCOMPANYID = CCC.CCOMPANY_ID " +
               "      INNER JOIN DT_PRODUCTPACKAGING pp ON GM.GOODSID = pp.GOODSID "+
               "    WHERE" +
               "      CP.COMPANYSTATUS = '00' " +
               "      AND CP.INDUSTRYTYPE LIKE ? " +
               "      GROUP BY" +
               "      CP.COMPANYID," +
               "      CC.CCOMPANYID, " +
               "      CC.COMPANYNAME," +
               "      CC.LOGOPATH," +
               "      CC.RESPONSIBLETEL, "+
               "      CC.ACCURACY," +
               "      CC.DIMENSION"+
               "      order by " +
               "SORTVALUE,LASTTIME";
       if (sort.equals("plow")){
           shopSql +=" desc";
       }
        parms.add("%"+shopType+"%");
        pageForm = this.baseBeanService.getPageFormBySQL(pageNumber, pageSize, shopSql.toString(), "select count(*) from (" + shopSql.toString() + ")", parms.toArray());
        //组合数据
        if (pageForm==null||pageNumber>pageForm.getPageNumber()){
            return "success";
        }
        List shoplist = pageForm.getList();
        /*Map<String, Shop> shopMap = new HashMap<String, Shop>();
        List<Shop> result1 = new ArrayList<Shop>();
        List<Goods> goodsList = new ArrayList<Goods>();*/
        for (Object good : goods) {
            Object[] obj=(Object[])good;
            /*Goods g = new Goods();
            g.setCompanyId(obj[0]==null?"":obj[0].toString());
            g.setCcompanyId(obj[1]==null?"":obj[1].toString());
            g.setCompanyName(obj[2]==null?"":obj[2].toString());
            g.setResponsibleTel(obj[3]==null?"":obj[3].toString());
            g.setLogoPath(obj[4]==null?"":obj[4].toString());
            g.setPhotoPath(obj[5]==null?"":obj[5].toString());
            g.setGoodsName(obj[6]==null?"":obj[6].toString());
            g.setNum(obj[7]==null?"":obj[1].toString());
            goodsList.add(g);*/
        }
// 2. shoplist 转成 Shop 对象列表，并挂接商品
        for (Object b : shoplist) {
            Object[] obj=(Object[])b;
            /*Shop shop = new Shop();

            shop.setCompanyId(obj[0]==null?"":obj[0].toString());
            shop.setCcompanyId(obj[1]==null?"":obj[1].toString());
            shop.setCompanyName(obj[2]==null?"":obj[2].toString());
            shop.setLogoPath(obj[3]==null?"":obj[3].toString());
            shop.setPhone(obj[4]==null?"":obj[4].toString());
            shop.setGoodsList(new ArrayList<>());
            shopMap.put(obj[0].toString(),shop);
            result1.add(shop);*/
        }

        // 3. 挂商品到店铺（最多 3 个）
        /*for (Goods g : goodsList) {
            if (g.getCompanyId() == null) continue;
            Shop shop = shopMap.get(g.getCompanyId());
            if (shop != null) {
                List<Goods> shopGoods = shop.getGoodsList();
                if (shopGoods.size() < 4) {
                    shopGoods.add(g);
                }
            }
        }*/


        if (pageForm==null||pageNumber>pageForm.getPageNumber()){
            return "success";
        }
        //map.put("pageForm", result1);
        map.put("pageCount", pageForm.getPageCount());
        map.put("recordCount", pageForm.getRecordCount());
        map.put("pageNumber", pageForm.getPageNumber());
        //自动定位失败返回201
        //logger.error("自动定位失败");
        map.put("code", "201");
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";

    }
    /*
            <dd id="newest">最新发布</dd>
                      <dd id="nearest">距离最近</dd>
                      <dd id="smart">综合排序</dd>
                      <dd id="praise">好评优先</dd>
                      <dd id="plow">价格最低</dd>
                      <dd id="ptop">价格最高</dd>
           */
    private String SwitchCase(String sort) {
        switch (sort){
            case "newest":
                return " pp.CREATETIME";

            case "nearest":
                return " NULL";
            case "smart":
                return " pp.CREATETIME";

            case "praise":
                return " pp.MONTHSALES";

            case "plow":
                return " pp.PRICE";
            case "ptop":
                return " pp.PRICE";
            default:
                return "";
        }

    }

    public String test(){
        return "supermarketGoods1";
    }
    /**
     * 功能:商品分类菜单展示
     * 时间:2018-8-11
     * 创建人:lnn
     *
     * @return
     */
    public String getGoodsClassify() {
        Map<String, Object> map = new HashMap<String, Object>();
        //公司非空校验
        if (ccompanyID != null && !ccompanyID.equals("")) {
            List<Object> parm1 = new ArrayList<Object>();
            List<Object> parm2 = new ArrayList<Object>();
            Company company = (Company) beandao.getBeanByHqlAndParams("select c from Company c ,CcomCom t where c.companyID=t.comanyId and t.ccompanyId=?", new Object[]{ccompanyID});
            //公司非空校验
            if (company != null && company.getCompanyID() != null && !company.getCompanyID().equals("")) {

                if (codePID != null && !codePID.equals("")) {
                    StringBuilder sql1 = new StringBuilder("select y.codeid,y.codevalue,y.iconpath,y.codepid from DTCCODE y ,");
                    sql1.append(" (select distinct(c.codepid) from (select distinct(pcr.codeid)  from dt_ProCateRelate pcr left join dt_productpackaging p on p.ppid = pcr.ppid");
                    sql1.append("  where p.showweixin = '01' and pcr.companyid = ?) dc left join Dtccode c on c.codeid = dc.codeid where c.companyid=?) code");
                    sql1.append(" where code.codepid = y.codeid  and y.companyid = ?");
                    parm1.add(company.getCompanyID());
                    parm1.add(company.getCompanyID());
                    parm1.add(company.getCompanyID());

//                    StringBuilder sql2 = new StringBuilder("select y.codeid,y.codevalue,y.codepid from DTCCODE y ,");
//                    sql2.append("(select distinct ej.codeid from dtccode ej inner join dt_ProCateRelate pcr on ej.codeid = pcr.codeid");
//                    sql2.append(" left join dt_productpackaging p on p.ppid=pcr.ppid where p.showweixin ='01'and pcr.companyid =?) code ");
//                    sql2.append(" where code.codeid =y.codeid  and y.codepid = ? and y.companyid = ?");
                    String sql2="SELECT y.codeid,y.codevalue,y.codepid FROM DTCCODE y" +
                            "WHERE " +
                            "    y.codepid =  ? AND y.companyid = ? AND EXISTS (" +
                            "        SELECT 1 FROM dt_ProCateRelate pcr INNER JOIN dt_productpackaging p ON p.ppid = pcr.ppid" +
                            "        WHERE pcr.codeid = y.codeid AND p.showweixin = '01'AND pcr.companyid = ?" +
                            "    )";
                    parm2.add(codePID);
                    parm2.add(company.getCompanyID());
                    parm2.add(company.getCompanyID());

                    //通过判断获取相应商品分类
                    List<BaseBean> goodsClassifyList = null;
                    if (codePID.equals("scode20190415raqvqk3uvs0000000762")) {
                        //查询商品一级分类
                        goodsClassifyList = this.baseBeanService.getListBeanBySqlAndParams(sql1.toString(), parm1.toArray());

                    } else {
                        //查询商品二级分类
                        goodsClassifyList = this.baseBeanService.getListBeanBySqlAndParams(sql2.toString(), parm2.toArray());
                    }
                    map.put("goodsClassifyList", goodsClassifyList);
                } else {
                    logger.error("获取商品分类失败,商品分类上级id不存在");
                    map.put("goodsClassifyList", null);

                }

            } else {
                logger.error("获取商品分类失败,公司id不存在");
                map.put("goodsClassifyList", null);
            }


        } else {
            logger.error("获取商品分类失败,公司id不存在");
            map.put("goodsClassifyList", null);
        }
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 功能:去超市商品主页
     * 时间:2018-8-8
     * 创建人:lnn
     *
     * @return
     */
    public String toSupermarketGoods() {
//        if(posNum!=null&&!posNum.equals("")) {
//           sumkSerivce.saveAccessCComID(posNum,ccompanyID);//mz
//          if("1".equals(nopr)){
//              sumkSerivce.clearSqCart(posNum);
//          }
//
//        }
        HttpServletRequest request = ServletActionContext.getRequest();
        String hql = "from CcomCom where ccompanyId = ?";
        CcomCom cc = (CcomCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ccompanyID});
        if (cc != null) {
            request.setAttribute("companyId", cc.getComanyId());
        }


        return "supermarketGoods";
    }

    /**
     * mz 20190211
     * 获取入口公司用于返回页面
     *
     * @return
     */
    public String ajaxGetAccess() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (posNum != null && !posNum.equals("")) {
            map = sumkSerivce.getAccessCompany(posNum);

        }
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();

        return "success";
    }


    /**
     * mz 20190211
     * 获取入口公司用于返回页面
     *
     * @return
     */
    public String getAccess() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (posNum != null && !posNum.equals("")) {
            String url = sumkSerivce.getAccessUrl(posNum);
            map.put("result", url);
        }
        JSONObject obj = JSONObject.fromObject(map);
        results = obj;

        return "success";
    }

    /**
     * 根据机器编号是否是终端机
     *
     * @return
     */
    public String isExistPosNum() {


        Map<String, Object> map = sumkSerivce.isExitPosNum(posNum);

        JSONObject obj = JSONObject.fromObject(map);
        if (back != null && !back.equals("")) {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            SessionWrap sw = SessionWrap.getInstance();
            sw.clearAllCartItem(session);
            TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                    SessionWrap.KEY_CUSTOMER);
            TEshopCusCom tcom = (TEshopCusCom) sw.getObject(session,
                    SessionWrap.KEY_SHOPCUSCOM);
            results = obj;
        } else {
            result = obj.toString();
        }

        return "success";
    }

    /**
     * 功能:去商品搜索主页
     * 时间:2018-8-8
     * 创建人:lnn
     *
     * @return
     */
    public String toGoodsSearch() {

        return "goodsSearch";
    }


    ///////////////////////////////////////////////////社区超市@mz///////////////////////////////////////////////////////////////////////////////

    /**
     * 社区加入购物车
     */
    public String sqJoinCart() {

        sumkSerivce.sqJoinCart(ppid, stardard, itemNum.toString(), sendNum, posNum, barCode, showNum, sgrId, relateID);
        return "success";
    }

    /**
     * 获取购物车数据
     */
    public String getSqCartList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String fhform = request.getParameter("fhform");
        List<BaseBean> list = sumkSerivce.getSqCartList(posNum, ccompanyID, lxType,fhform);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodlist", list);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }


    public String clearSqCart() {

        sumkSerivce.clearSqCart(posNum);
        return "success";
    }

    /**
     * 删除购物车单个商品
     */
    public String deleteCartGoods() {


        sumkSerivce.deleteCartGoods(ppid, stardard, posNum, barCode);

        return "success";
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getCcompanyID() {
        return ccompanyID;
    }

    public void setCcompanyID(String ccompanyID) {
        this.ccompanyID = ccompanyID;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getCodePID() {
        return codePID;
    }

    public void setCodePID(String codePID) {
        this.codePID = codePID;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getStardard() {
        return stardard;
    }

    public void setStardard(String stardard) {
        this.stardard = stardard;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPosNum() {
        return posNum;
    }

    public void setPosNum(String posNum) {
        this.posNum = posNum;
    }

    public String getNopr() {
        return nopr;
    }

    public void setNopr(String nopr) {
        this.nopr = nopr;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSendNum() {
        return sendNum;
    }

    public void setSendNum(String sendNum) {
        this.sendNum = sendNum;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getShowNum() {
        return showNum;
    }

    public void setShowNum(String showNum) {
        this.showNum = showNum;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public Object getResults() {
        return results;
    }

    public void setResults(Object results) {
        this.results = results;
    }

    public String getSgrId() {
        return sgrId;
    }

    public void setSgrId(String sgrId) {
        this.sgrId = sgrId;
    }

    public String getRelateID() {
        return relateID;
    }

    public void setRelateID(String relateID) {
        this.relateID = relateID;
    }

    public int getLxType() {
        return lxType;
    }

    public void setLxType(int lxType) {
        this.lxType = lxType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
