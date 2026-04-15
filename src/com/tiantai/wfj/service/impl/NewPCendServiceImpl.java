package com.tiantai.wfj.service.impl;


import hy.ea.bo.Company;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.BenDis.SetSubsidize;
import hy.ea.bo.finance.CartShopPromotion;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.PayCashierBill;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.PromotionAssociation;
import hy.ea.bo.finance.BenDis.DtOrderBillAdd;
import hy.ea.bo.finance.BenDis.RefundAddress;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.service.UploadContentToFileService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.bo.SDistrict;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.SDistrictService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiantai.wfj.bo.Cart;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.service.NewPCendService;
import com.tiantai.wfj.util.SessionWrap;

@Service
public class NewPCendServiceImpl implements NewPCendService {
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private UploadContentToFileService contentToFileService;
    @Resource
    private ServerService serverService;
    @Resource
    private SDistrictService regionService;

    /**
     * 分页
     *
     * @param pageNumber 当前页
     * @param pageSize   显示条数
     * @param sql        sql语句
     * @param sqlcount   总记录数SQL语句
     * @param params     参数
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm getPageFormBySQL(int pageNumber, int pageSize, String sql,
                                     String sqlcount, Object[] params) {
        int count = beandao.getConutByBySqlAndParams(sqlcount, params);// 总条数
        if (count == 0)
            return null;
        int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
        if (pageNumber < 1)
            pageNumber = 1;
        if (pageNumber > pageCount)
            pageNumber = pageCount;
        int firstResult = pageSize * (pageNumber - 1);
        int maxResult = Math.min(pageSize, count - firstResult);
        List<BaseBean> listBaseBean = beandao.getConutByBySqlAndParamsAndPage(
                sql, params, firstResult, maxResult);
        PageForm pageForm = new PageForm();
        pageForm.setPageSize(pageSize);
        pageForm.setRecordCount(count);
        pageForm.setPageCount(pageCount);
        pageForm.setPageNumber(pageNumber);
        pageForm.setList(listBaseBean);
        return pageForm;
    }

    /**
     * 根据txt URL 获取内容
     *
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    private String getContentFromFile(String filepath) {
        String path = ServletActionContext.getServletContext()
                .getRealPath("\\") + filepath;
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        try {
            return contentToFileService.getContent(path);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @return 返回当前用户对象
     * @Title: 查询
     * @Description: 获取当前用户信息
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public TEshopCusCom queryUser() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        return cus;
    }

    /**
     * @return
     * @Title: 存储
     * @Description: 存储url
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void storageUrl() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.removeAttribute("url");
        HttpServletRequest request = ServletActionContext.getRequest();
        String url = request.getHeader("Referer");
        session.setAttribute("url", url);
    }

    /**
     * 退出登录
     *
     * @param
     * @return
     */
    @Override
    @Transactional
    public void quitSession() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.invalidate();
    }


    /**
     * 查询国内经济联营平台
     *
     * @param pageNumber:第几页,pageSize:一页显示多少条,standard:判断条件
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm queryPlatform(int pageNumber, int pageSize, String standard) {
        StringBuilder sb = new StringBuilder();
        sb.append("select p.ppid,y.logopath,y.companyname,m.custype,p.standard ");
        sb.append("from dt_productpackaging p, t_eshop_cuscom m,dt_ccom_com c,dtcontactcompany y ");
        sb.append("where p.type=? and p.standard=? and p.ppid = m.ppid and m.custype=? ");
        sb.append("and m.companyid = c.compnay_id and c.ccompany_id=y.ccompanyid ");
        PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", new Object[]{"联营经济平台", standard, "0"});

        return pageForm;
    }

    /**
     * 查询资讯/推广
     *
     * @param pageNumber :第几页,pageSize:一页显示多少条,informationJudge:00:普通查询,01:查询推荐
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> ajaxInformation(int pageNumber, int pageSize, String informationJudge) {
        StringBuilder sb = new StringBuilder();
        Object[] obj = new Object[]{"company201009046vxdyzy4wg0000000065", "新闻", "2", "00", "会员分享", "00", "02", "会员分享", "00", "02", 1};
        if (informationJudge.equals("01")) {
            obj = new Object[]{"company201009046vxdyzy4wg0000000065", "新闻", "2", "00", "会员分享", "02", "会员分享", "02", 1};
        }

        sb.append("select x.goodsName,x.PackagingDate,x.image,x.ppid,x.name,d.url ");
        sb.append("from (select p.goodsName,p.PackagingDate,p.image,p.ppid,cc.companyname as name,p.goodsid ");
        sb.append("from dt_ProductPackaging p, DT_ccom_com m, dtcontactcompany cc ");
        sb.append("where (p.companyId = ? and p.type = ? and p.fiveclear = ? and p.delStatus = ? and ");
        sb.append("p.companyID = m.compnay_id and m.ccompany_id = cc.ccompanyid) or (p.type = ? and ");
        if (informationJudge.equals("01")) {
            sb.append("p.review = ? ");
        } else {
            sb.append("p.review in (?,?) ");
        }
        sb.append("and p.companyID = m.compnay_id and m.ccompany_id = cc.ccompanyid and p.companyid is not null) union ");
        sb.append("select pp.goodsName,pp.PackagingDate,pp.image,pp.ppid,s.staffname as,pp.goodsid name ");
        sb.append("from dt_ProductPackaging pp, dt_hr_staff s where pp.type = ? and ");
        if (informationJudge.equals("01")) {
            sb.append("pp.review = ? ");
        } else {
            sb.append("pp.review in (?,?) ");
        }
        sb.append("and pp.staffid = s.staffid and pp.companyid is null) x,Dtgoodfunction d where x.goodsid=d.goodsid and d.orders=? ");
        sb.append("order by x.PackagingDate desc");

        PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", obj);

        List<String> list = new ArrayList<String>();

        if (pageForm != null && pageForm.getList() != null) {
            for (int i = 0; i < pageForm.getList().size(); i++) {
                Object pp = pageForm.getList().get(i);
                Object[] pp1 = (Object[]) pp;
                String content = "";
                if (pp1[5] != null) {
                    content = getContentFromFile(pp1[5].toString());
                    String[] con = content.split("<p>");
                    for (int j = 1; j < con.length; j++) {
                        content = con[j].substring(0, con[j].lastIndexOf("</p>"));
                        if (!content.equals("")) {
                            break;
                        }
                    }
                }
                list.add(content);
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        map.put("list", list);

        return map;
    }

    /**
     * 查询商品所有信息
     *
     * @param ppID : 产品id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> goodsDetails(String ppID) {
        //查询商品详情
        StringBuilder sql = new StringBuilder();
        sql.append("select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type,p.monthSales,cc.industrytype");
        sql.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p ,DT_ccom_com ccom,dtContactCompany cc where p.ppid= ?");
        sql.append(" and p.ppid=ps.ppid and ccom.ccompany_Id=cc.ccompanyID and ps.fcom_id is null and p.companyid =ccom.compnay_id ");
        Object obj = beandao.getObjectBySqlAndParams(sql.toString(), new Object[]{ppID});
        Object[] goods = (Object[]) obj;
        //查询商品副图片
        List<BaseBean> images = commodityImages(goods[4].toString());
        //查询商品尺码
        List<BaseBean> size = goodsSize(goods[4].toString());
        //查询商品颜色
        List<BaseBean> color = colorOfCommodity(goods[4].toString());
        //查询商品的规格
        Map<String, Object> goodsStandard = goodsStandard(goods[4].toString());
        //查询商品下的促销品
        List<Object> ppt = promotionProduct(goods[1].toString(), goods[5].toString());
        Map<String, Object> giftMap = null;
        if (ppt != null && ppt.size() > 0) {
            giftMap = new HashMap<String, Object>();
            //查询该商品下的所有促销品规格
            for (int i = 0; i < ppt.size(); i++) {
                Object[] goodsObj = (Object[]) ppt.get(i);
                String goodsID = (String) goodsObj[4];
                Map<String, Object> giftStandardMap = goodsStandard(goodsID);
                giftMap.put((String) goodsObj[1], giftStandardMap);
            }
        }
        //商品详情中商品价格调整
        List<BaseBean> ssl = this.beandao.getListBeanByHqlAndParams("from SetSubsidize where stutas=?", new Object[]{"01"});
        //集合非空校验
        if (ssl != null && ssl.size() > 0) {
            for (int j = 0; j < ssl.size(); j++) {
                SetSubsidize s = (SetSubsidize) ssl.get(j);
                BigDecimal pct = new BigDecimal(s.getTotalPct()).divide(new BigDecimal(100)).add(BigDecimal.ONE);
                if (s.getGtid().equals(goods[8])) {
                    goods[2] = new BigDecimal(goods[2].toString()).multiply(pct).setScale(2, BigDecimal.ROUND_HALF_UP);
                    break;
                }
            }

        }

        //查询商品地址
        Object city = theGoodsAddress(goods[5].toString());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("obj", obj);
        map.put("images", images);
        map.put("size", size);
        map.put("color", color);
        map.put("ppt", ppt);
        map.put("giftMap", giftMap);
        map.put("city", city);
        map.put("goodsStandard", goodsStandard);

        return map;
    }

    /**
     * 查询商品规格
     *
     * @param goodsid : 物品id
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    private Map<String, Object> goodsStandard(String goodsid) {
        String sql = "select attriname,attrivalue from dtAttriProduction where goodsId = ? and type in(?,?) order by type,sort ";
        List<Object> attrObjs = beandao.getListObjectBySqlAndParams(sql, new Object[]{goodsid, "0", "1"});
        List<String> attrivalues = null;
        Map<String, Object> map = new HashMap<String, Object>();
        if (attrObjs != null && attrObjs.size() > 0) {
            attrivalues = new ArrayList<String>();
            for (int i = 0; i < attrObjs.size(); i++) {
                Object[] attrNow = (Object[]) attrObjs.get(i);
                String attriname = (String) attrNow[0];
                String attrivalue = (String) attrNow[1];
                Object[] attrBefore = null;
                String attrinameBefore = null;
                if (i > 0) {
                    attrBefore = (Object[]) attrObjs.get(i - 1);
                    attrinameBefore = (String) attrBefore[0];
                }
                if (attrinameBefore != null && !attriname.equals(attrinameBefore)) {
                    map.put(attrinameBefore, attrivalues);
                    attrivalues = new ArrayList<String>();
                }
                attrivalues.add(attrivalue);
                if (i == attrObjs.size() - 1) {
                    map.put(attriname, attrivalues);
                }
            }
        }
        return map;
    }

    /**
     * 查询商品尺码
     *
     * @param goodsid:物品id
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> goodsSize(String goodsid) {
        String hqlt = "from AttriProduction where type = ? and goodsid = ?";
        List<BaseBean> size = beandao.getListBeanByHqlAndParams(hqlt, new Object[]{"0", goodsid});
        return size;
    }

    /**
     * 查询商品副图片
     *
     * @param goodsid:物品id
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> commodityImages(String goodsid) {
        String hqlt = "from AttriProduction where type = ? and goodsid = ? and sort!=? order by sort";
        List<BaseBean> images = beandao.getListBeanByHqlAndParams(hqlt, new Object[]{"2", goodsid, 1});
        return images;
    }

    /**
     * 查询商品颜色
     *
     * @param goodsid:物品id
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> colorOfCommodity(String goodsid) {
        String hqlt = "from AttriProduction where type = ? and goodsid = ?";
        List<BaseBean> colour = beandao.getListBeanByHqlAndParams(hqlt, new Object[]{"1", goodsid});
        return colour;
    }

    /**
     * 查询商品下的促销品
     *
     * @param ppid:商品id,companyid:公司id
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Object> promotionProduct(String ppid, String companyid) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type,p.monthSales");
        sb.append(" from DT_PRO_SETUP ps, dt_ProductPackaging p");
        sb.append(" where p.ppid = ps.ppid and ps.fcom_id is null and p.showweixin = ? and ps.ppid in");
        sb.append(" (select pt.ptppid from dt_promotion pt");
        sb.append(" where pt.companyid = ? and pt.ppid = ?)");
        List<Object> ptlist = beandao.getListObjectBySqlAndParams(sb.toString(), new Object[]{"01", companyid, ppid});
        return ptlist;
    }

    /**
     * 查询商品地址
     *
     * @param
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Object theGoodsAddress(String companyid) {
        String hql = "from ContactCompany c where c.ccompanyID in (select cc.ccompanyId from CcomCom cc where cc.comanyId = ?)";
        ContactCompany cc = (ContactCompany) beandao.getBeanByHqlAndParams(hql, new Object[]{companyid});
        List<SDistrict> distinctlistSaved = null;
        if (cc.getAddress() != null) {
            distinctlistSaved = regionService.getDistrictListByID(cc.getAddress());
            Collections.reverse(distinctlistSaved);
        }
        return (distinctlistSaved != null && distinctlistSaved.get(1) != null) ? distinctlistSaved.get(1).getDistrictName() : "未设置";
    }

    /**
     * 热搜推荐
     *
     * @param
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm ajaxRecommend(int pageNumber, int pageSize, String ppID) {
        String hql = "from ProductPackaging where ppID = ? ";
        ProductPackaging ppk = (ProductPackaging) beandao.getBeanByHqlAndParams(hql, new Object[]{ppID});

        StringBuilder sb = new StringBuilder();
        sb.append("select p.image,p.goodsname,r.re_price,p.companyid,p.ppid ");
        sb.append("from dt_productpackaging p,dt_pro_setup r ");
        sb.append("where (p.ppid=r.ppid and p.delstatus=? and p.showweixin=? and p.companyid = ? and p.tradecode = ?) ");
        sb.append("or (p.ppid=r.ppid and p.delstatus=? and p.showweixin=? and p.companyid <> ? and p.tradecode = ?)  ");
        sb.append("order by case p.companyid when ? then 1 end ");
        Object[] obj = new Object[]{"00", "01", ppk.getCompanyID(), ppk.getTradeCode(), "00", "01", ppk.getCompanyID(), ppk.getTradeCode(), ppk.getCompanyID()};
        PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", obj);

        return pageForm;
    }

    /**
     * 商品评论
     *
     * @param
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm ajaxProductEvaluation(int pageNumber, int pageSize,
                                          String ppID) {
        StringBuilder sb = new StringBuilder();
        sb.append("select c.anonymous,s.staffname,c.comments,c.image1,c.image2,c.image3,to_char(c.comdate,'yyyy-mm-dd'),g.standard ");
        sb.append(" from dtComments c,dtCashierBills b,dt_hr_Staff s,dtGoodsBills g");
        sb.append(" where c.ppID = ? and b.cashierBillsID = c.cashierBillsID");
        sb.append(" and b.contactUserID = s.staffID");
        sb.append(" and b.cashierBillsID = g.cashierBillsID");
        PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", new Object[]{ppID});
        return pageForm;
    }


    /**
     * 查询商品url
     *
     * @param
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<String> ajaxProductParticulars(String ppID) {
        String sql = "select g.url from dt_productpackaging p,Dtgoodfunction g where p.goodsid=g.goodsid and p.ppid=? order by g.orders asc";
        List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql, new Object[]{ppID});
        Object url;
        List<String> urlList = new ArrayList<String>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                url = list.get(i);
                urlList.add(getContentFromFile((url != null ? url : "").toString()));
            }

        }
        return urlList;
    }

    /**
     * pc查询购物车
     *
     * @param
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> pcShoppingCart() {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean b = false;
        int goodNum = 0;
        TEshopCusCom cuscom = queryUser();
        if (cuscom != null) {
            b = true;
            StringBuilder sb = new StringBuilder();
            sb.append("select ps.ppid,p.goodsname,p.image,ps.re_price,c.itemNum ");
            sb.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p,DT_cart c ");
            sb.append(" where p.ppid=ps.ppid and p.ppid = c.pid and ps.fcom_id is null ");
            sb.append(" and c.staff_id = ? and p.showweixin = ? and rownum <=2");

            List<BaseBean> beanList = beandao.getListBeanBySqlAndParams(sb.toString(),
                    new Object[]{cuscom.getStaffid(), "01"});
            map.put("beanList", beanList);
            try {
                String sql = "select sum(c.itemNum) from dt_Cart c,dt_ProductPackaging  p where p.ppID = c.pid and c.staff_Id = ? and p.showweixin = ?";
                goodNum = beandao.getConutByBySqlAndParams(sql, new Object[]{cuscom.getStaffid(), "01"});
                map.put("goodNum", goodNum);
            } catch (Exception e) {
                map.put("goodNum", goodNum);
                e.printStackTrace();
            }
        } else {
            map.put("goodNum", goodNum);
        }
        map.put("b", b);
        return map;
    }

    /**
     * PC端查询用户购物车的商品数量
     *
     * @param staffId : 用户Id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Integer ajaxShoppingCartCount(String staffId) {
        //查询该用户购物车中的商品数量
        String countSql = " select count(1) from DT_Cart where staff_Id=? ";
        Object cartCountObj = beandao.getObjectBySqlAndParams(countSql, new Object[]{staffId});
        Integer cartCount = Integer.parseInt(cartCountObj.toString());
        return cartCount;
    }

    /**
     * PC端商品加入购物车
     *
     * @param staffId    : 用户Id
     * @param ppID       : 产品ID
     * @param ptppid     : 促销品ID
     * @param standard   : 产品规格
     * @param ptStandard : 促销品规格
     * @param count      : 购买数量
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void pcAddShoppingCart(String staffId, String ppID, String[] ptppid, String standard, String[] ptStandard, String count) {
        //查询所选规格的商品是否已存在购物车中
        String goodsHql = "from Cart where staffId=? and pid=? and stardard =? ";
        Cart cart = (Cart) beandao.getBeanByHqlAndParams(goodsHql, new String[]{staffId, ppID, standard});
        if (cart == null) {
            String sql = "select ps.ppId,ps.ppName,ps.re_price,p.companyId,p.image from DT_PRO_SETUP ps left join dt_ProductPackaging p on ps.ppid=p.ppid where ps.ppid=? ";
            Object[] goodsObj = (Object[]) beandao.getObjectBySqlAndParams(sql, new Object[]{ppID});

            //查询商品详情[获取行业类型goods[8] ]
            StringBuilder sqltype = new StringBuilder();
            sqltype.append("select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type,p.monthSales,cc.industrytype");
            sqltype.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p ,DT_ccom_com ccom,dtContactCompany cc where p.ppid= ?");
            sqltype.append(" and p.ppid=ps.ppid and ccom.ccompany_Id=cc.ccompanyID and ps.fcom_id is null and p.companyid =ccom.compnay_id ");
            Object obj = beandao.getObjectBySqlAndParams(sqltype.toString(), new Object[]{ppID});
            Object[] goods = (Object[]) obj;
            //商品价格调整
            List<BaseBean> ssl = this.beandao.getListBeanByHqlAndParams("from SetSubsidize where stutas=?", new Object[]{"01"});
                if (ssl != null && ssl.size() > 0) {
                    for (int j = 0; j < ssl.size(); j++) {
                        SetSubsidize s = (SetSubsidize) ssl.get(j);
                        BigDecimal pct = new BigDecimal(s.getTotalPct()).divide(new BigDecimal(100)).add(BigDecimal.ONE);
                        if (s.getGtid().equals(goods[8])) {
                            //更改价格
                            goodsObj[2] = new BigDecimal(goodsObj[2].toString()).multiply(pct).setScale(2, BigDecimal.ROUND_HALF_UP);
                            break;
                        }
                    }
                }


            cart = new Cart();
            cart.setCartId(serverService.getServerID("cart"));
            cart.setCompanyId((String) goodsObj[3]);
            cart.setItemNum(Integer.valueOf(count));
            cart.setPid(ppID);
            cart.setPname((String) goodsObj[1]);
            cart.setPrice(goodsObj[2].toString());
            cart.setStaffId(staffId);
            cart.setPic((String) goodsObj[4]);
            cart.setStardard(standard);
            //该商品包含赠品
            if (ptppid != null && ptppid.length > 0) {
                for (int i = 0; i < ptppid.length; i++) {
                    if (ptppid[i] != null && !"".equals(ptppid[i])) {
                        CartShopPromotion cartShopPromotion = new CartShopPromotion();
                        cartShopPromotion.setCspId(serverService.getServerID("cspId"));
                        cartShopPromotion.setCartId(cart.getCartId());
                        cartShopPromotion.setPpId(ppID);
                        cartShopPromotion.setPptId(ptppid[i]);
                        cartShopPromotion.setPtstandard(ptStandard[i]);
                        cartShopPromotion.setPtCount(1);
                        cartShopPromotion.setStaffId(staffId);
                        beandao.save(cartShopPromotion);
                    }
                }
            }
        } else {
            Integer itemNum = cart.getItemNum() + Integer.valueOf(count);
            cart.setItemNum(itemNum);
            //该商品包含赠品
            if (ptppid != null && ptppid.length > 0) {
                //查询所选规格的赠品是否已存在购物车中
                String giftHql = "from CartShopPromotion where cartId=? and pptId=? and ptstandard=? ";
                for (int i = 0; i < ptppid.length; i++) {
                    if (ptppid[i] != null && !"".equals(ptppid[i])) {
                        CartShopPromotion cartShopPromotion = (CartShopPromotion) beandao.getBeanByHqlAndParams(giftHql, new String[]{cart.getCartId(), ptppid[i], ptStandard[i]});
                        if (cartShopPromotion != null) {
                            Integer ptCount = cartShopPromotion.getPtCount() + 1;
                            cartShopPromotion.setPtCount(ptCount);
                        } else {
                            cartShopPromotion = new CartShopPromotion();
                            cartShopPromotion.setCspId(serverService.getServerID("cspId"));
                            cartShopPromotion.setCartId(cart.getCartId());
                            cartShopPromotion.setPpId(ppID);
                            cartShopPromotion.setPptId(ptppid[i]);
                            cartShopPromotion.setPtstandard(ptStandard[i]);
                            cartShopPromotion.setPtCount(1);
                            cartShopPromotion.setStaffId(staffId);
                        }
                        beandao.saveOrUpdate(cartShopPromotion);
                    }
                }
            }
        }
        beandao.saveOrUpdate(cart);
    }

    /**
     * PC端查询用户商品购物车
     *
     * @param staffId   : 用户Id
     * @param showParam : 搜索条件
     * @param ppID
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> selPcShoppingCart(String staffId, String showParam) {
        //查询用户购物车中的所有商品
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        params.add("01");
        params.add(staffId);
        sql.append("select c.cart_id,c.pid,c.pname,c.pic,c.price,c.companyid,c.itemnum,c.stardard,p.goodsid,p.type,m.companyname ");
        sql.append("from DT_cart c ");
        sql.append("left join dt_ProductPackaging p on c.pid=p.ppid ");
        sql.append("left join DT_PRO_SETUP ps on p.ppid=ps.ppid ");
        sql.append("left join dtCompany m on ps.com_id=m.companyid ");
        sql.append("where ps.fcom_id is null ");
        sql.append("and p.showweixin = ? and c.staff_id = ? ");
        if (showParam != null && !"".equals(showParam)) {
            sql.append("and c.pname like ? ");
            params.add("%" + showParam + "%");
        }
        sql.append("order by c.companyid,c.pid ");
        List<Object> goodsList = beandao.getListObjectBySqlAndParams(sql.toString(), params.toArray(new Object[]{}));
        if (goodsList != null && goodsList.size() > 0) {
            //查询用户购物车中的所有促销品
            params.remove("%" + showParam + "%");
            params.remove("01");
            sql.delete(0, sql.length());
            sql.append("select s.cartId,s.ppId,s.pptId,s.ptstandard,s.ptCount,ps.com_id,ps.ppname,ps.re_price,p.image ");
            sql.append("from DT_CartShopPromotion s ");
            sql.append("left join DT_PRO_SETUP ps on s.pptId = ps.ppid ");
            sql.append("left join dt_ProductPackaging p on ps.ppid = p.ppid ");
            sql.append("where s.staffId = ? ");
            if (showParam != null && !"".equals(showParam)) {
                sql.append("and s.cartId in( ");
                for (int i = 0; i < goodsList.size(); i++) {
                    Object[] obj = (Object[]) goodsList.get(i);
                    params.add(obj[0]);
                    if (i == goodsList.size() - 1) {
                        sql.append("?) ");
                        break;
                    }
                    sql.append("?,");
                }
            }
            List<Object> giftList = beandao.getListObjectBySqlAndParams(sql.toString(), params.toArray(new Object[]{}));

            //将商品与公司名称关联
            params.clear();
            Map<String, Object> goodsMap = new HashMap<String, Object>();
            for (int i = 0; i < goodsList.size(); i++) {
                Object[] goodsObj = (Object[]) goodsList.get(i);
                String companyName = (String) goodsObj[10];
                Object[] goodsObjBefore = null;
                String companyNameBefore = null;
                if (i > 0) {
                    goodsObjBefore = (Object[]) goodsList.get(i - 1);
                    companyNameBefore = (String) goodsObjBefore[10];
                }
                if (companyNameBefore != null && !companyNameBefore.equals(companyName)) {
                    goodsMap.put(companyNameBefore, params);
                    params = new ArrayList<Object>();
                }
                params.add(goodsObj);
                if (i == goodsList.size() - 1) {
                    goodsMap.put(companyName, params);
                }
            }
            map.put("goodsMap", goodsMap);
            map.put("giftList", giftList);
        }
        return map;
    }

    /**
     * PC端删除购物车商品
     *
     * @param staffId : 用户Id
     * @param cartId  : 购物车Id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void ajaxDelShoppingCart(String staffId, String cartId) {
        List<String> hqls = new ArrayList<String>();
        List<Object[]> params = new ArrayList<Object[]>();
        //删除购物车中该商品的所有促销品
        String giftHql = "delete from CartShopPromotion where staffId = ? and cartId = ? ";
        hqls.add(giftHql);
        params.add(new Object[]{staffId, cartId});
        //删除购物车中的该商品
        String goodsHql = "delete from Cart where staffId = ? and cartId = ? ";
        hqls.add(goodsHql);
        params.add(new Object[]{staffId, cartId});
        beandao.executeHqlsByParmsList(null, hqls.toArray(new String[]{}), params);
    }

    /**
     * PC端购物车商品结算
     *
     * @param cartIds : 需要结算的购物车Id
     * @param counts  : 需要结算的购买数量
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> payShoppingCart(String[] cartIds, String[] counts) {
        //查询用户购物车中需要结算的商品
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        params.add("01");
        sql.append("select c.cart_id,c.pid,c.pname,c.pic,c.price,c.companyid,c.itemnum,c.stardard,p.goodsid,p.type,p.tradeName,m.companyname ");
        sql.append("from DT_cart c ");
        sql.append("left join dt_ProductPackaging p on c.pid=p.ppid ");
        sql.append("left join DT_PRO_SETUP ps on p.ppid=ps.ppid ");
        sql.append("left join dtCompany m on ps.com_id=m.companyid ");
        sql.append("where ps.fcom_id is null ");
        sql.append("and p.showweixin = ? and c.cart_id in( ");
        for (int i = 0; i < cartIds.length; i++) {
            params.add(cartIds[i]);
            if (i == cartIds.length - 1) {
                sql.append("?) ");
                break;
            }
            sql.append("?,");
        }
        sql.append("order by c.companyid,c.pid ");
        List<Object> goodsList = beandao.getListObjectBySqlAndParams(sql.toString(), params.toArray(new Object[]{}));
        if (goodsList != null && goodsList.size() > 0) {
            //查询结算商品中的所有促销品
            params.clear();
            sql.delete(0, sql.length());
            sql.append("select s.cartId,s.ppId,s.pptId,s.ptstandard,s.ptCount,ps.com_id,ps.ppname,ps.re_price,p.image,p.type,p.tradeName ");
            sql.append("from DT_CartShopPromotion s ");
            sql.append("left join DT_PRO_SETUP ps on s.pptId = ps.ppid ");
            sql.append("left join dt_ProductPackaging p on ps.ppid = p.ppid ");
            sql.append("where s.cartId in( ");
            for (int i = 0; i < goodsList.size(); i++) {
                Object[] obj = (Object[]) goodsList.get(i);
                params.add(obj[0]);
                if (i == goodsList.size() - 1) {
                    sql.append("?) ");
                    break;
                }
                sql.append("?,");
            }
            List<Object> giftList = beandao.getListObjectBySqlAndParams(sql.toString(), params.toArray(new Object[]{}));
            //将结算商品与公司名称关联
            params.clear();
            Map<String, Object> goodsMap = new HashMap<String, Object>();
            for (int i = 0; i < goodsList.size(); i++) {
                Object[] goodsObj = (Object[]) goodsList.get(i);
                String cartID = (String) goodsObj[0];
                String tradeName = (String) goodsObj[10];
                String companyName = (String) goodsObj[11];
                for (int j = 0; j < counts.length; j++) {
                    if (cartID.equals(cartIds[j])) {
                        goodsObj[6] = counts[j];
                        break;
                    }
                }
                //结算商品为驾校报名产品并与公司名称关联
                if ("B102汽车交通工具>z30001汽车驾校".equals(tradeName) && map.get(companyName) == null) {
                    map.put(companyName, "drivingSchoolGoods");
                } else {
                    //结算商品的赠品中包含驾校报名产品并与商品的公司名称关联
                    if (giftList != null && giftList.size() > 0 && map.get(companyName) == null) {
                        for (int j = 0; j < giftList.size(); j++) {
                            Object[] giftObj = (Object[]) giftList.get(j);
                            String giftCartID = (String) giftObj[0];
                            String giftTradeName = (String) giftObj[10];
                            if ("B102汽车交通工具>z30001汽车驾校".equals(giftTradeName) && giftCartID.equals(cartID)) {
                                map.put(companyName, "drivingSchoolGoods");
                                break;
                            }
                        }
                    }
                }
                Object[] goodsObjBefore = null;
                String companyNameBefore = null;
                if (i > 0) {
                    goodsObjBefore = (Object[]) goodsList.get(i - 1);
                    companyNameBefore = (String) goodsObjBefore[11];
                }
                if (companyNameBefore != null && !companyNameBefore.equals(companyName)) {
                    goodsMap.put(companyNameBefore, params);
                    params = new ArrayList<Object>();
                }
                params.add(goodsObj);
                if (i == goodsList.size() - 1) {
                    goodsMap.put(companyName, params);
                }
            }
            map.put("goodsMap", goodsMap);
            map.put("giftList", giftList);
        }
        return map;
    }

    /**
     * PC端商品立即购买
     *
     * @param ppID       : 产品ID
     * @param count      : 购买数量
     * @param ptppid     : 促销品ID
     * @param ptStandard : 促销品样式
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> pcGoodsPayNow(String ppID, String count, String[] ptppid, String[] ptStandard) {
        //查询购买商品的详情
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type,p.monthSales,p.tradeName ");
        sql.append("from DT_PRO_SETUP ps,dt_ProductPackaging p where p.ppid= ? ");
        sql.append("and p.ppid=ps.ppid and ps.fcom_id is null ");
        Object[] obj = (Object[]) beandao.getObjectBySqlAndParams(sql.toString(), new Object[]{ppID});

        //查询商品详情[获取行业类型goods[8] ]
        StringBuilder sqltype = new StringBuilder();
        sqltype.append("select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type,p.monthSales,cc.industrytype");
        sqltype.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p ,DT_ccom_com ccom,dtContactCompany cc where p.ppid= ?");
        sqltype.append(" and p.ppid=ps.ppid and ccom.ccompany_Id=cc.ccompanyID and ps.fcom_id is null and p.companyid =ccom.compnay_id ");
        Object obj1 = beandao.getObjectBySqlAndParams(sqltype.toString(), new Object[]{ppID});
        Object[] goods = (Object[]) obj1;
        //商品价格调整
        List<BaseBean> ssl = this.beandao.getListBeanByHqlAndParams("from SetSubsidize where stutas=?", new Object[]{"01"});
            if (ssl != null && ssl.size() > 0) {
                for (int j = 0; j < ssl.size(); j++) {
                    SetSubsidize s = (SetSubsidize) ssl.get(j);
                    BigDecimal pct = new BigDecimal(s.getTotalPct()).divide(new BigDecimal(100)).add(BigDecimal.ONE);
                    if (s.getGtid().equals(goods[8])) {
                        //更改价格
                        obj[2] = new BigDecimal(obj[2].toString()).multiply(pct).setScale(2, BigDecimal.ROUND_HALF_UP);
                        break;
                    }
                }

            }

        map.put("obj", obj);
        //查询购买商品的总价
        BigDecimal tem1 = new BigDecimal(count);
        BigDecimal tem2 = new BigDecimal(obj[2].toString());
        String totalPrice = tem1.multiply(tem2).toString();
        map.put("totalPrice", totalPrice);
        //查询商品的所属公司信息
        Company company = (Company) beandao.getBeanByHqlAndParams("from Company where companyID=?", new Object[]{(String) obj[5]});
        map.put("company", company);
        //判断商品是否为驾校报名产品
        String tradeName = (String) obj[8];
        if ("B102汽车交通工具>z30001汽车驾校".equals(tradeName)) {
            map.put("drivingSchoolGoods", "drivingSchoolGoods");
        }
        //查询用户选择的所有促销品(赠品)的信息
        if (ptppid != null && ptppid.length > 0) {
            sql.delete(0, sql.length());
            sql.append("select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type,p.monthSales,p.tradeName ");
            sql.append("from DT_PRO_SETUP ps, dt_ProductPackaging p ");
            sql.append("where p.ppid = ps.ppid and ps.fcom_id is null and ps.ppid in( ");
            for (int i = 0; i < ptppid.length; i++) {
                if (i == ptppid.length - 1) {
                    sql.append("?) ");
                    break;
                }
                sql.append("?,");
            }
            sql.append("and p.showweixin = ? ");
            String[] allParams = Arrays.copyOf(ptppid, ptppid.length + 1);
            allParams[allParams.length - 1] = "01";
            List<Object> ppt = beandao.getListObjectBySqlAndParams(sql.toString(), allParams);
            Map<String, String> ptStandardMap = new HashMap<String, String>();
            for (int i = 0; i < ppt.size(); i++) {
                Object[] gift = (Object[]) ppt.get(i);
                String giftID = (String) gift[1];
                String giftTradeName = (String) gift[8];
                for (int j = 0; j < ptppid.length; j++) {
                    if (giftID.equals(ptppid[j])) {
                        ptStandardMap.put(giftID, ptStandard[j]);
                        break;
                    }
                }
                //判断商品的所有促销品(赠品)中是否有驾校报名产品
                if ("B102汽车交通工具>z30001汽车驾校".equals(giftTradeName) && map.get("drivingSchoolGoods") == null) {
                    map.put("drivingSchoolGoods", "drivingSchoolGoods");
                }
            }
            map.put("ppt", ppt);
            map.put("ptStandardMap", ptStandardMap);
        }
        return map;
    }

    /**
     * PC端查询用户收货地址
     *
     * @param staffId : 用户Id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> ajaxPcStaffAddress(String staffId) {
        //查询用户的默认收货信息
        String addressDefaultSql = "select addressId,area,consignee,addressDetailed,phone from dt_hr_staff_address where staffId= ? and isDefault= ? ";
        Object[] addressDefaultObj = (Object[]) beandao.getObjectBySqlAndParams(addressDefaultSql, new Object[]{staffId, "是"});
        //查询用户的所有非默认收货信息
        String addressSql = "select addressId,area,consignee,addressDetailed,phone from dt_hr_staff_address where staffId= ? and isDefault is null ";
        List<Object> addressObjs = beandao.getListObjectBySqlAndParams(addressSql, new Object[]{staffId});
        //查询用户收货信息个数
        String addressCountSql = "select count(1) from dt_hr_staff_address where staffId= ? ";
        Object addressCount = beandao.getObjectBySqlAndParams(addressCountSql, new Object[]{staffId});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("addressDefaultObj", addressDefaultObj);
        map.put("addressObjs", addressObjs);
        map.put("addressCount", addressCount);
        return map;
    }

    /**
     * PC端查询用户收货地址个数
     *
     * @param staffId : 用户Id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Integer selStaffAddressCount(String staffId) {
        //查询用户收货信息个数
        String addressCountSql = "select count(1) from dt_hr_staff_address where staffId= ? ";
        Object addressCountObj = beandao.getObjectBySqlAndParams(addressCountSql, new Object[]{staffId});
        Integer addressCount = Integer.parseInt(addressCountObj.toString());
        return addressCount;
    }

    /**
     * PC端修改用户默认收货地址
     *
     * @param staffId   : 用户Id
     * @param addressID : 收货地址的ID
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void ajaxChangeDefaultAddress(String staffId, String addressID) {
        String addressHql = "from StaffAddress where staffID= ? ";
        List<BaseBean> beans = beandao.getListBeanByHqlAndParams(addressHql, new Object[]{staffId});
        for (BaseBean baseBean : beans) {
            StaffAddress staffAddress = (StaffAddress) baseBean;
            staffAddress.setIsDefault("");
            beandao.update(staffAddress);
        }
        String addressDefaultHql = "from StaffAddress where staffID= ? and addressID= ? ";
        BaseBean bean = beandao.getBeanByHqlAndParams(addressDefaultHql, new Object[]{staffId, addressID});
        StaffAddress staffAddress = (StaffAddress) bean;
        staffAddress.setIsDefault("是");
        beandao.update(staffAddress);
    }

    /**
     * PC端回显收货地址
     *
     * @param staffId   : 用户Id
     * @param addressID : 收货地址的ID
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> selShowStaffAddress(String staffId, String addressID) {
        String addressHql = "from StaffAddress where staffID= ? and addressID= ? ";
        StaffAddress staffAddress = (StaffAddress) beandao.getBeanByHqlAndParams(addressHql, new Object[]{staffId, addressID});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("staffAddress", staffAddress);
        return map;
    }

    /**
     * PC端新增或修改用户收货地址
     *
     * @param account      : 用户帐号
     * @param staffAddress : 用户地址
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void ajaxAddStaffAddress(String account, StaffAddress staffAddress) {
        String sql = "select staffID,companyID,state from T_ESHOP_CUSCOM where account= ? and logOff=0 ";
        Object[] teshopcuscomObj = (Object[]) beandao.getObjectBySqlAndParams(sql, new Object[]{account});
        String addressHql = "from StaffAddress where staffID= ? and addressID= ? ";
        StaffAddress staffAddressAdd = (StaffAddress) beandao.getBeanByHqlAndParams(addressHql, new Object[]{(String) teshopcuscomObj[0], staffAddress.getAddressID()});
        if (staffAddressAdd == null) {
            staffAddressAdd = new StaffAddress();
            staffAddressAdd.setStaffID((String) teshopcuscomObj[0]);
            staffAddressAdd.setCompanyID((String) teshopcuscomObj[1]);
            staffAddressAdd.setAddressID(serverService.getServerID("StaffAddress"));
            staffAddress.setAddressID(staffAddressAdd.getAddressID());
        }
        staffAddressAdd.setAddressDetailed(staffAddress.getAddressDetailed());
        staffAddressAdd.setArea(staffAddress.getArea());
        staffAddressAdd.setConsignee(staffAddress.getConsignee());
        staffAddressAdd.setPhone(staffAddress.getPhone());
        staffAddressAdd.setIsDefault(staffAddress.getIsDefault());
        beandao.saveOrUpdate(staffAddressAdd);
    }

    /**
     * PC端删除用户收货地址
     *
     * @param staffId   : 用户Id,
     * @param addressID : 收货地址的ID
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void ajaxDeleteStaffAddress(String staffId, String addressID) {
        String sql = "select addressKey from dt_hr_staff_address where staffID=? and addressID= ?";
        String addressKey = (String) beandao.getObjectBySqlAndParams(sql, new Object[]{staffId, addressID});
        beandao.deleteBeanByKey(StaffAddress.class, addressKey);
    }

    /**
     * PC端查询一级地址
     *
     * @return
     */
    /*@Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> selDistrictCity()
	{
		String sql="select districtID,districtName from dtSDistrict where districtPID in(?,?,?,?,?,?,?)order by districtName ";
		List<Object> districtCity=beandao.getListObjectBySqlAndParams(sql,new Object[]{"sdistrict20140829779hcj26db0000000011","sdistrict20140829779hcj26db0000000012",
					"sdistrict20140829779hcj26db0000000013","sdistrict20140829779hcj26db0000000014","sdistrict20140829779hcj26db0000000015",
					"sdistrict20140829779hcj26db0000000016","sdistrict20170517bc5tqchj7m0000000002"});
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("districtCity", districtCity);
		return map;
	}*/
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> selDistrictCity() {
        String sql = "select provinceID, province from province order by province ";
        List<Object> districtCity = beandao.getListObjectBySqlAndParams(sql, new Object[]{});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("districtCity", districtCity);
        return map;
    }

    /**
     * PC端查询二三级地址
     *
     * @param districtID : 地域ID
     * @param showParam  : 是否展示
     * @return
     */
    /*@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> selDistrictByID(String districtID,String showParam)
	{
		Map<String, Object> map=new HashMap<String, Object>();
		//判断是否为直辖市
		if("true".equals(showParam)&&("11".equals(districtID)||"12".equals(districtID)||"31".equals(districtID)||"50".equals(districtID)))
		{
			String sql="select districtID,districtName from dtSDistrict where districtID= ? ";
			List<Object> country=beandao.getListObjectBySqlAndParams(sql, new Object[]{districtID});
			map.put("country", country);
			return map;
		}
		String sql="select districtID,districtName from dtSDistrict where districtPID= ? order by districtName ";
		List<Object> country=beandao.getListObjectBySqlAndParams(sql, new Object[]{districtID});
		map.put("country", country);
		return map;
	}*/
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> selDistrictByID(String districtID, String showParam) {
        Map<String, Object> map = new HashMap<String, Object>();
        //判断是否为直辖市
        if ("true".equals(showParam)) {
            String sql = "select cityID,city from city where father= ? order by city";
            List<Object> country = beandao.getListObjectBySqlAndParams(sql, new Object[]{districtID});
            map.put("country", country);
            return map;
        }
        String sql = "select areaID,area from area where father= ? order by area ";
        List<Object> country = beandao.getListObjectBySqlAndParams(sql, new Object[]{districtID});
        map.put("country", country);
        return map;
    }

    /**
     * PC端购物车商城下订单(多个产品下订单)
     *
     * @param staffID            : 用户Id
     * @param account            : 用户账号
     * @param companyIds         : 需要结算的商品所属公司Id
     * @param cartIds            : 需要结算的购物车Id
     * @param counts             : 需要结算的商品数量
     * @param addressID          : 收货地址ID
     * @param companyNames       : 驾校产品所属公司
     * @param accountNames       : 学员信息中所有的学员姓名
     * @param references         : 学员信息中所有的学员联系方式
     * @param staffIdentityCards : 学员信息中所有的学员身份证号
     * @param staffAddresses     : 学员信息中所有的学员住址
     * @param messageMap         : 异步返回的参数集合
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void ajaxMakeShoppingCartPayBills(String staffID, String account, String[] companyIds, String[] cartIds, String[] counts, String addressID,
                                             String[] companyNames, String[] accountNames, String[] references, String[] staffIdentityCards, String[] staffAddresses, Map<String, Object> messageMap) {
        List<BaseBean> operator = new ArrayList<BaseBean>();
        //生成统一的支付订单编号
        String payJournalNum = serverService.getBillID(companyIds[0]);
        messageMap.put("payJournalNum", payJournalNum);
        //查询当前账号的权限表以及人员表
        String sccHql = "from TEshopCusCom where account=? AND logOff=0 and acquiesce = ? ";
        TEshopCusCom teShopCusCom = (TEshopCusCom) beandao.getBeanByHqlAndParams(sccHql, new Object[]{account, "01"});
        String staffHql = "from Staff d where d.staffID= ? ";
        Staff staff = (Staff) beandao.getBeanByHqlAndParams(staffHql, new Object[]{staffID});
        //获取发布产品的公司信息及往来公司信息
        List<Object> params = new ArrayList<Object>();
        StringBuilder comBuilder = new StringBuilder();
        comBuilder.append("select cc.ccompany_Id,c.companyId,c.companyname ");
        comBuilder.append("from DT_ccom_com cc,dtCompany c ");
        comBuilder.append("where cc.compnay_id=c.companyId and c.companyId in( ");
        //查询公司地址发货信息
        StringBuilder refBuilder = new StringBuilder();
        refBuilder.append("from RefundAddress where rtype=? and status=? and companyId in( ");
        Integer num = 1;
        params.add(num);
        params.add("00");
        //查询公司的库房信息
        StringBuilder depBuilder = new StringBuilder();
        depBuilder.append("from DepotManage where companyID in( ");
        for (int i = 0; i < companyIds.length; i++) {
            params.add(companyIds[i]);
            if (i == companyIds.length - 1) {
                comBuilder.append("?) ");
                refBuilder.append("?) ");
                depBuilder.append("?) ");
                break;
            }
            comBuilder.append("?,");
            refBuilder.append("?,");
            depBuilder.append("?,");
        }
        depBuilder.append("and depotName= ? ");
        List<Object> companyObj = beandao.getListObjectBySqlAndParams(comBuilder.toString(), companyIds);
        List<BaseBean> refundAddresses = beandao.getListBeanByHqlAndParams(refBuilder.toString(), params.toArray(new Object[]{}));
        params.remove(num);
        params.remove("00");
        params.add("销售库");
        List<BaseBean> depotManages = beandao.getListBeanByHqlAndParams(depBuilder.toString(), params.toArray(new Object[]{}));
        //查询收货信息
        String addressHql = "from StaffAddress where addressID = ? ";
        StaffAddress staffAddress = (StaffAddress) beandao.getBeanByHqlAndParams(addressHql, new Object[]{addressID});
        //查询需要结算的所有商品信息
        comBuilder.delete(0, comBuilder.length());
        comBuilder.append("select c.cart_id,c.pid,c.pname,c.pic,c.price,c.companyid,c.itemnum,c.stardard,p.goodsid,p.type,p.tradeName,p.productCode,p.model,m.companyname ");
        comBuilder.append("from DT_cart c ");
        comBuilder.append("left join dt_ProductPackaging p on c.pid=p.ppid ");
        comBuilder.append("left join DT_PRO_SETUP ps on p.ppid=ps.ppid ");
        comBuilder.append("left join dtCompany m on ps.com_id=m.companyid ");
        comBuilder.append("where ps.fcom_id is null ");
        comBuilder.append("and p.showweixin = ? and c.cart_id in( ");
        params.clear();
        params.add("01");
        //查询所有的促销品(赠品)信息
        refBuilder.delete(0, refBuilder.length());
        refBuilder.append("select s.cartId,s.ppId,s.pptId,s.ptstandard,s.ptCount,ps.com_id,ps.ppname,ps.re_price,p.image,p.type,p.tradeName ");
        refBuilder.append("from DT_CartShopPromotion s ");
        refBuilder.append("left join DT_PRO_SETUP ps on s.pptId = ps.ppid ");
        refBuilder.append("left join dt_ProductPackaging p on ps.ppid = p.ppid ");
        refBuilder.append("where s.cartId in( ");
        for (int i = 0; i < cartIds.length; i++) {
            params.add(cartIds[i]);
            if (i == cartIds.length - 1) {
                comBuilder.append("?) ");
                refBuilder.append("?) ");
                break;
            }
            comBuilder.append("?,");
            refBuilder.append("?,");
        }
        List<Object> goodsList = beandao.getListObjectBySqlAndParams(comBuilder.toString(), params.toArray(new Object[]{}));
        params.remove("01");
        List<Object> giftList = beandao.getListObjectBySqlAndParams(refBuilder.toString(), params.toArray(new Object[]{}));
        //查询产品的进货价
        comBuilder.delete(0, comBuilder.length());
        comBuilder.append("select S.EF_PRICE from DT_PRO_SETUP S where S.PPID = ? AND S.COM_ID = ? AND S.FCOM_ID IS NULL");
        comBuilder.append(" union all");
        comBuilder.append(" select S.EF_PRICE from DT_PRO_SETUP S where S.PPID = ? AND S.COM_ID = ? AND S.FCOM_ID = ?");
        comBuilder.append(" union all");
        comBuilder.append(" select S.EF_PRICE from DT_PRO_SETUP S where S.PPID = ? AND S.COM_ID = ? AND S.FCOM_ID IS NULL");
        for (int i = 0; i < companyObj.size(); i++) {
            //每个公司生成一个订单
            CashierBills cashierBills = new CashierBills();
            cashierBills.setCashierBillsID(serverService.getServerID("CashierBills"));
            cashierBills.setOrganizationID("organization20110425U539EJC3VF0000012237");
            cashierBills.setCashierDate(new Date());
            cashierBills.setStaffName("系统生成");
            cashierBills.setInputName("系统生成");
            cashierBills.setStatus("40");
            cashierBills.setWfStatus2("00");
            cashierBills.setFkStatus("01");
            cashierBills.setDepartmentName("客户");
            cashierBills.setZctype("cg");
            cashierBills.setBillsType("项目收入预算单");
            cashierBills.setWfStatus1("00");
            //添加用户具体信息
            cashierBills.setContactUserID(staff.getStaffID());
            cashierBills.setCtUserName(staff.getStaffName());
            //添加发布产品的公司信息
            Object[] company = (Object[]) companyObj.get(i);
            String companyId = (String) company[1];
            cashierBills.setCompanyName((String) company[2]);
            cashierBills.setCompanyID(companyId);
            cashierBills.setJournalNum(serverService.getBillID(cashierBills.getCompanyID()));
            cashierBills.setjNumOrder(cashierBills.getJournalNum());
            cashierBills.setStatusbill("04");
            //添加往来公司信息
            cashierBills.setCcompanyID((String) company[0]);
            //创建第三方关联订单
            PayCashierBill payCashierBill = new PayCashierBill();
            payCashierBill.setPcbID(serverService.getServerID("pcbid"));
            payCashierBill.setOriJournalNum(cashierBills.getjNumOrder());
            payCashierBill.setPayJournalNum(payJournalNum);
            operator.add(payCashierBill);
            //创建订单详细地址相关
            DtOrderBillAdd dtOrderBillAdd = new DtOrderBillAdd();
            dtOrderBillAdd.setOaId(serverService.getServerID("DtOrderBillAdd"));
            dtOrderBillAdd.setOaComId(cashierBills.getCompanyID());
            dtOrderBillAdd.setOaSccId(teShopCusCom.getSccId());
            //添加收货信息
            if (staffAddress != null) {
                dtOrderBillAdd.setReceivename(staffAddress.getConsignee());
                dtOrderBillAdd.setReceivecode(staffAddress.getPostCode());
                dtOrderBillAdd.setReceivetel(staffAddress.getPhone());
                dtOrderBillAdd.setReceiveaddress(staffAddress.getArea() + staffAddress.getAddressDetailed());
                dtOrderBillAdd.setShDate(new Date());
            }
            //添加公司发货信息
            for (BaseBean bean : refundAddresses) {
                RefundAddress refundAddress = (RefundAddress) bean;
                if (companyId.equals(refundAddress.getCompanyId())) {
                    dtOrderBillAdd.setSendname(refundAddress.getRname());
                    dtOrderBillAdd.setSendtel(refundAddress.getRphone());
                    dtOrderBillAdd.setSendcode(refundAddress.getRpostcode());
                    dtOrderBillAdd.setSendaddress(refundAddress.getRarea() + refundAddress.getRstreet());
                    dtOrderBillAdd.setFhDate(new Date());
                    break;
                }
            }
            dtOrderBillAdd.setOaBillId(cashierBills.getCashierBillsID());
            dtOrderBillAdd.setOaBillJounum(cashierBills.getJournalNum());
            dtOrderBillAdd.setOaGysId(companyId);
            dtOrderBillAdd.setXdDate(new Date());
            dtOrderBillAdd.setFkDate(new Date());
            operator.add(dtOrderBillAdd);
            float priceSub = 0;
            depBuilder.delete(0, depBuilder.length());
            for (int j = 0; j < goodsList.size(); j++) {
                Object[] goodsObj = (Object[]) goodsList.get(j);
                String goodsCartID = (String) goodsObj[0];
                String goodsCompanyID = (String) goodsObj[5];
                String tradeName = (String) goodsObj[10];
                String goodsCompanyName = (String) goodsObj[13];
                if (goodsCompanyID.equals(companyId)) {
                    //添加驾校报名产品学员信息
                    if (cashierBills.getStaffIdentityCard() == null && "B102汽车交通工具>z30001汽车驾校".equals(tradeName)) {
                        for (int k = 0; k < companyNames.length; k++) {
                            if (goodsCompanyName.equals(companyNames[k])) {
                                cashierBills.setCtUserName(accountNames[k]);
                                cashierBills.setReference(references[k]);
                                cashierBills.setStaffIdentityCard(staffIdentityCards[k]);
                                cashierBills.setReferrerAddress(staffAddresses[k]);
                                break;
                            }
                        }
                    }
                    //创建产品单据
                    GoodsBills goodsBills = new GoodsBills();
                    goodsBills.setCashierBillsID(cashierBills.getCashierBillsID());
                    goodsBills.setGoodsBillsID(serverService.getServerID("GoodsBills"));
                    goodsBills.setCompanyID(companyId);
                    goodsBills.setTypeID((String) goodsObj[9]);
                    for (BaseBean bean : depotManages) {
                        DepotManage depotManage = (DepotManage) bean;
                        if (goodsCompanyID.equals(depotManage.getCompanyID())) {
                            goodsBills.setDepotID(depotManage.getDepotID());
                            goodsBills.setDepotName(depotManage.getDepotName());
                            break;
                        }
                    }
                    goodsBills.setStartDate(new Date());
                    goodsBills.setPrice((String) goodsObj[4]);//零售价
                    for (int k = 0; k < cartIds.length; k++) {
                        if (cartIds[k].equals((String) goodsObj[0])) {
                            goodsBills.setQuantity(counts[k]);
                            break;
                        }
                    }
                    goodsBills.setMoney(Float.parseFloat(goodsBills.getPrice()) * Integer.parseInt(goodsBills.getQuantity()) + "");
                    goodsBills.setRealMoney(goodsBills.getMoney());
                    goodsBills.setGoodsID((String) goodsObj[8]);
                    goodsBills.setGoodsName((String) goodsObj[2]);
                    goodsBills.setStandard((String) goodsObj[7]);
                    goodsBills.setPpID((String) goodsObj[1]);
                    goodsBills.setBoxStandard((String) goodsObj[12]);
                    goodsBills.setEntryTime(new Date());
                    goodsBills.setGoodsNum((String) goodsObj[11]);
                    goodsBills.setPremiums("0");//是否是奖品  0或null:否  1:是
                    List<BaseBean> pro_setup = beandao.getListBeanBySqlAndParams(comBuilder.toString(), new Object[]{(String) goodsObj[1], companyId, (String) goodsObj[1], companyId, companyId, (String) goodsObj[1], companyId});
                    Object pro_su = null;
                    if (pro_setup != null && pro_setup.size() > 0) {
                        pro_su = pro_setup.get(0);
                    }
                    goodsBills.setCostmoney((String) pro_su);
                    operator.add(goodsBills);
                    depBuilder.append((String) goodsObj[2] + ",");
                    priceSub += Float.parseFloat(goodsBills.getMoney());
                    if (giftList != null && giftList.size() > 0) {
                        for (int k = 0; k < giftList.size(); k++) {
                            Object[] giftObj = (Object[]) giftList.get(k);
                            String giftCartID = (String) giftObj[0];
                            String giftTradeName = (String) giftObj[10];
                            if (giftCartID.equals(goodsCartID)) {
                                //添加驾校报名产品学员信息
                                if (cashierBills.getStaffIdentityCard() == null && "B102汽车交通工具>z30001汽车驾校".equals(giftTradeName)) {
                                    for (int l = 0; l < companyNames.length; l++) {
                                        if (goodsCompanyName.equals(companyNames[l])) {
                                            cashierBills.setCtUserName(accountNames[l]);
                                            cashierBills.setReference(references[l]);
                                            cashierBills.setStaffIdentityCard(staffIdentityCards[l]);
                                            cashierBills.setReferrerAddress(staffAddresses[l]);
                                            break;
                                        }
                                    }
                                }
                                //创建赠品相关订单信息
                                PromotionAssociation promotionAssociation = new PromotionAssociation();
                                promotionAssociation.setPaId(serverService.getServerID("paId"));
                                promotionAssociation.setCashierBillsID(cashierBills.getCashierBillsID());
                                promotionAssociation.setCompanyId((String) giftObj[5]);
                                promotionAssociation.setPpId((String) giftObj[2]);
                                promotionAssociation.setCreatDate(new Date());
                                promotionAssociation.setPpName((String) giftObj[6]);
                                promotionAssociation.setPrice((String) giftObj[7]);
                                promotionAssociation.setStandard((String) giftObj[3]);
                                promotionAssociation.setCount(Integer.parseInt(giftObj[4].toString()));
                                operator.add(promotionAssociation);
                            }
                        }
                    }
                }
            }
            String projectName = depBuilder.toString();
            cashierBills.setProjectName(projectName.substring(0, projectName.length() - 1));
            cashierBills.setPriceSub(priceSub + "");
            operator.add(cashierBills);
        }
        beandao.saveBeansListAndexecuteHqlsByParams(operator, null, null);
        //清除购物车中已结算的商品
        for (int i = 0; i < cartIds.length; i++) {
            ajaxDelShoppingCart(staffID, cartIds[i]);
        }
    }

    /**
     * PC端用户商城下订单(单个产品结算)
     *
     * @param staffID      : 用户Id
     * @param account      : 用户账号
     * @param ppID         : 产品ID
     * @param totalMoney   : 购买的产品总价
     * @param count        : 购买的产品数量
     * @param standard     : 购买的产品规格
     * @param ptppid       : 促销品(赠品)Id
     * @param ptStandard   : 促销品(赠品)规格
     * @param addressID    : 收货地址ID
     * @param staffMessage : 学员信息
     * @param messageMap   : 异步返回的参数集合
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void ajaxMakePayBills(String staffID, String account, String ppID, String totalMoney, String count, String standard, String[] ptppid, String[] ptStandard, String addressID, Staff staffMessage, Map<String, Object> messageMap) {
        List<BaseBean> operator = new ArrayList<BaseBean>();
        //创建产品订单
        CashierBills cashierBills = new CashierBills();
        cashierBills.setCashierBillsID(serverService.getServerID("CashierBills"));
        cashierBills.setOrganizationID("organization20110425U539EJC3VF0000012237");
        cashierBills.setCashierDate(new Date());
        cashierBills.setStaffName("系统生成");
        cashierBills.setInputName("系统生成");
        cashierBills.setStatus("40");
        cashierBills.setWfStatus2("00");
        cashierBills.setFkStatus("01");
        cashierBills.setDepartmentName("客户");
        cashierBills.setZctype("cg");
        cashierBills.setPriceSub(totalMoney);
        cashierBills.setBillsType("项目收入预算单");
        cashierBills.setWfStatus1("00");
        //获取产品包装信息
        String ppkHql = "from ProductPackaging where ppid=? ";
        ProductPackaging productPackaging = (ProductPackaging) beandao.getBeanByHqlAndParams(ppkHql, new Object[]{ppID});
        cashierBills.setProjectName(productPackaging.getGoodsName());
        //获取用户具体信息
        String hql = "from Staff d where d.staffID=? ";
        Staff staff = (Staff) beandao.getBeanByHqlAndParams(hql, new Object[]{staffID});
        cashierBills.setContactUserID(staff.getStaffID());
        cashierBills.setCtUserName(staff.getStaffName());
        //驾校报名产品学员信息
        if (staffMessage != null) {
            cashierBills.setCtUserName(staffMessage.getAccountName());
            cashierBills.setReference(staffMessage.getReference());
            cashierBills.setStaffIdentityCard(staffMessage.getStaffIdentityCard());
            cashierBills.setReferrerAddress(staffMessage.getStaffAddress());
        }
        //获取发布产品的公司信息
        String comHql = "from Company where companyID = ? ";
        Company company = (Company) beandao.getBeanByHqlAndParams(comHql, new Object[]{productPackaging.getCompanyID()});
        cashierBills.setCompanyName(company.getCompanyName());
        cashierBills.setCompanyID(productPackaging.getCompanyID());
        cashierBills.setJournalNum(serverService.getBillID(productPackaging.getCompanyID()));
        cashierBills.setjNumOrder(cashierBills.getJournalNum());
        cashierBills.setStatusbill("04");
        //查询往来公司信息
        String comComHql = "from CcomCom where compnay_id=? ";
        CcomCom ccomCom = (CcomCom) beandao.getBeanByHqlAndParams(comComHql, new Object[]{productPackaging.getCompanyID()});
        cashierBills.setCcompanyID(ccomCom.getCcompanyId());
        operator.add(cashierBills);
        //创建第三方关联订单
        PayCashierBill payCashierBill = new PayCashierBill();
        payCashierBill.setPcbID(serverService.getServerID("pcbid"));
        payCashierBill.setOriJournalNum(cashierBills.getJournalNum());
        payCashierBill.setPayJournalNum(cashierBills.getJournalNum());
        messageMap.put("payJournalNum", payCashierBill.getPayJournalNum());
        operator.add(payCashierBill);
        // 商城详细订单地址信息
        DtOrderBillAdd dtOrderBillAdd = new DtOrderBillAdd();
        dtOrderBillAdd.setOaId(serverService.getServerID("DtOrderBillAdd"));
        dtOrderBillAdd.setOaComId(cashierBills.getCompanyID());
        //获取登录信息
        String sccHql = "from TEshopCusCom where account=? AND logOff=0 and acquiesce = ? ";
        TEshopCusCom teShopCusCom = (TEshopCusCom) beandao.getBeanByHqlAndParams(sccHql, new Object[]{account, "01"});
        operator.add(teShopCusCom);
        dtOrderBillAdd.setOaSccId(teShopCusCom.getSccId());
        //查询收货信息
        String addressHql = "from StaffAddress where addressID = ? ";
        StaffAddress staffAddress = (StaffAddress) beandao.getBeanByHqlAndParams(addressHql, new Object[]{addressID});
        if (staffAddress != null) {
            dtOrderBillAdd.setReceivename(staffAddress.getConsignee());
            dtOrderBillAdd.setReceivecode(staffAddress.getPostCode());
            dtOrderBillAdd.setReceivetel(staffAddress.getPhone());
            dtOrderBillAdd.setReceiveaddress(staffAddress.getArea() + staffAddress.getAddressDetailed());
            dtOrderBillAdd.setShDate(new Date());
        }
        //查询发货信息
        String radHql = "from RefundAddress where companyId=? and rtype=? and status=? ";
        RefundAddress refundAddress = (RefundAddress) beandao.getBeanByHqlAndParams(radHql, new Object[]{productPackaging.getCompanyID(), 1, "00"});
        if (refundAddress != null) {
            dtOrderBillAdd.setSendname(refundAddress.getRname());
            dtOrderBillAdd.setSendtel(refundAddress.getRphone());
            dtOrderBillAdd.setSendcode(refundAddress.getRpostcode());
            dtOrderBillAdd.setSendaddress(refundAddress.getRarea() + refundAddress.getRstreet());
            dtOrderBillAdd.setFhDate(new Date());
        }
        dtOrderBillAdd.setOaBillId(cashierBills.getCashierBillsID());
        dtOrderBillAdd.setOaBillJounum(cashierBills.getJournalNum());
        dtOrderBillAdd.setOaGysId(productPackaging.getCompanyID());
        dtOrderBillAdd.setXdDate(new Date());
        dtOrderBillAdd.setFkDate(new Date());
        operator.add(dtOrderBillAdd);
        //创建产品单据
        GoodsBills goodsBills = new GoodsBills();
        goodsBills.setCashierBillsID(cashierBills.getCashierBillsID());
        goodsBills.setGoodsBillsID(serverService.getServerID("GoodsBills"));
        goodsBills.setCompanyID(productPackaging.getCompanyID());
        goodsBills.setTypeID(productPackaging.getType());
        //查询产品所在公司的库房信息
        String hqlinvt = "from DepotManage where companyID = ? and depotName= ?";
        DepotManage depotManage = (DepotManage) beandao.getBeanByHqlAndParams(hqlinvt, new Object[]{productPackaging.getCompanyID(), "销售库"});
        goodsBills.setDepotID(depotManage.getDepotID());
        goodsBills.setDepotName(depotManage.getDepotName());
        goodsBills.setStartDate(new Date());
        goodsBills.setMoney(totalMoney);
        goodsBills.setQuantity(count);
        goodsBills.setRealMoney(totalMoney);
        //获取产品及相关信息
        StringBuffer sql = new StringBuffer();
        sql.append("select ps.ppid,p.goodsname,p.goodsid,p.type,ps.re_price,p.companyid,p.productCode,p.model");
        sql.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p where ps.com_id= ? and p.ppid= ?");
        sql.append(" and p.ppid=ps.ppid and ps.fcom_id is null ");
        Object[] obj = (Object[]) beandao.getObjectBySqlAndParams(sql.toString(), new Object[]{productPackaging.getCompanyID(), ppID});
        if (obj == null) {
            goodsBills.setGoodsID(productPackaging.getGoodsID());
        } else {
            goodsBills.setGoodsID(obj[2].toString());
        }
        goodsBills.setGoodsName(productPackaging.getGoodsName());
        goodsBills.setStandard(standard);
        goodsBills.setPpID(ppID);
        goodsBills.setBoxStandard(productPackaging.getModel());
        goodsBills.setEntryTime(new Date());
        goodsBills.setGoodsNum(productPackaging.getProductCode());
        goodsBills.setPrice((String) obj[4]);
        //查询产品的进货价
        sql.delete(0, sql.length());
        sql.append("select S.EF_PRICE from DT_PRO_SETUP S where S.PPID = ? AND S.COM_ID = ? AND S.FCOM_ID IS NULL");
        sql.append(" union all");
        sql.append(" select S.EF_PRICE from DT_PRO_SETUP S where S.PPID = ? AND S.COM_ID = ? AND S.FCOM_ID = ?");
        sql.append(" union all");
        sql.append(" select S.EF_PRICE from DT_PRO_SETUP S where S.PPID = ? AND S.COM_ID = ? AND S.FCOM_ID IS NULL");
        Object[] goodparm = new Object[]{ppID, productPackaging.getCompanyID(), ppID, productPackaging.getCompanyID(), productPackaging.getCompanyID(), ppID, productPackaging.getCompanyID()};
        List<BaseBean> pro_setup = beandao.getListBeanBySqlAndParams(sql.toString(), goodparm);
        Object pro_su = null;
        if (pro_setup != null) {
            pro_su = pro_setup.get(0);
        }
        goodsBills.setCostmoney(pro_su.toString());
        operator.add(goodsBills);
        //赠品相关订单信息
        if (ptppid != null && ptppid.length > 0) {
            sql.delete(0, sql.length());
            sql.append("select ps.com_Id,ps.ppId,ps.ppname,ps.ef_price ");
            sql.append("from dt_pro_setup ps ");
            sql.append("where ps.ppid in(");
            for (int i = 0; i < ptppid.length; i++) {
                if (i == ptppid.length - 1) {
                    sql.append("?) ");
                    break;
                }
                sql.append("?,");
            }
            List<Object> gift = beandao.getListObjectBySqlAndParams(sql.toString(), ptppid);
            for (int i = 0; i < gift.size(); i++) {
                for (int j = 0; j < ptppid.length; j++) {
                    Object[] giftObj = (Object[]) gift.get(i);
                    String giftID = (String) giftObj[1];
                    if (giftID.equals(ptppid[j])) {
                        PromotionAssociation promotionAssociation = new PromotionAssociation();
                        promotionAssociation.setPaId(serverService.getServerID("paId"));
                        promotionAssociation.setCashierBillsID(cashierBills.getCashierBillsID());
                        promotionAssociation.setCompanyId((String) giftObj[0]);
                        promotionAssociation.setPpId(ptppid[j]);
                        promotionAssociation.setCreatDate(new Date());
                        promotionAssociation.setPpName((String) giftObj[2]);
                        promotionAssociation.setPrice((String) giftObj[3]);
                        promotionAssociation.setStandard(ptStandard[j]);
                        promotionAssociation.setCount(1);
                        operator.add(promotionAssociation);
                        break;
                    }
                }
            }
        }
        beandao.saveBeansListAndexecuteHqlsByParams(operator, null, null);
    }

    /**
     * PC端查询用户下订单的详情
     *
     * @param payJournalNum : 支付订单编号
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> selBillsDetails(String payJournalNum) {
        //查询公司订单编号
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuilder builder = new StringBuilder();
        builder.append("select oriJournalNum from dtPayCashierBill where payJournalNum = ? ");
        List<Object> oriJournalNumObjs = beandao.getListObjectBySqlAndParams(builder.toString(), new Object[]{payJournalNum});
        if (oriJournalNumObjs != null && oriJournalNumObjs.size() > 0) {
            //查询公司订单
            builder.delete(0, builder.length());
            builder.append("from CashierBills where journalNum in(");
            for (int i = 0; i < oriJournalNumObjs.size(); i++) {
                if (i == oriJournalNumObjs.size() - 1) {
                    builder.append("?) ");
                    break;
                }
                builder.append("?,");
            }
            List<BaseBean> cashierBillsBeans = beandao.getListBeanByHqlAndParams(builder.toString(), oriJournalNumObjs.toArray(new Object[]{}));
            StringBuilder subjectBuilder = new StringBuilder();
            BigDecimal total_amount = new BigDecimal("0");
            ;
            List<String> params = new ArrayList<String>();
            //获取支付订单总名称及支付总金额
            for (int i = 0; i < cashierBillsBeans.size(); i++) {
                CashierBills cashierBills = (CashierBills) cashierBillsBeans.get(i);
                total_amount = total_amount.add(new BigDecimal(cashierBills.getPriceSub()));
                params.add(cashierBills.getCashierBillsID());
                if (i == cashierBillsBeans.size() - 1) {
                    subjectBuilder.append(cashierBills.getProjectName());
                    break;
                }
                subjectBuilder.append(cashierBills.getProjectName() + ",");
            }
            map.put("subject", subjectBuilder.toString());
            map.put("total_amount", total_amount);
            //查询具体商品订单
            builder.delete(0, builder.length());
            builder.append("from GoodsBills where cashierBillsID in(");
            for (int i = 0; i < params.size(); i++) {
                if (i == params.size() - 1) {
                    builder.append("?) ");
                    break;
                }
                builder.append("?, ");
            }
            List<BaseBean> goodsBillsBeans = beandao.getListBeanByHqlAndParams(builder.toString(), params.toArray(new String[]{}));
            //获取具体的商品描述
            builder.delete(0, builder.length());
            for (int i = 0; i < goodsBillsBeans.size(); i++) {
                GoodsBills goodsBills = (GoodsBills) goodsBillsBeans.get(i);
                builder.append("商品名称:" + goodsBills.getGoodsName() + ",规格:" + goodsBills.getStandard() + "; ");
            }
            map.put("body", builder.toString());
            map.put("goodsSize", goodsBillsBeans.size());
        }
        return map;
    }

    /**
     * PC端验证支付订单
     *
     * @param payJournalNum : 支付订单编号
     * @param total_amount  : 支付金额
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> ajaxValidatePayBills(String payJournalNum, String total_amount) {
        //验证公司订单编号是否存在
        StringBuilder builder = new StringBuilder();
        builder.append("select oriJournalNum from dtPayCashierBill where payJournalNum = ? ");
        List<Object> oriJournalNumObjs = beandao.getListObjectBySqlAndParams(builder.toString(), new Object[]{payJournalNum});
        Map<String, Object> map = new HashMap<String, Object>();
        if (oriJournalNumObjs != null && oriJournalNumObjs.size() > 0) {
            //查询公司订单
            builder.delete(0, builder.length());
            builder.append("from CashierBills where journalNum in(");
            for (int i = 0; i < oriJournalNumObjs.size(); i++) {
                if (i == oriJournalNumObjs.size() - 1) {
                    builder.append("?) ");
                    break;
                }
                builder.append("?,");
            }
            List<BaseBean> cashierBillsBeans = beandao.getListBeanByHqlAndParams(builder.toString(), oriJournalNumObjs.toArray(new Object[]{}));
            //计算支付订单总金额
            BigDecimal priceSub = new BigDecimal("0");
            for (BaseBean cashierBillsBean : cashierBillsBeans) {
                CashierBills cashierBills = (CashierBills) cashierBillsBean;
                priceSub = priceSub.add(new BigDecimal(cashierBills.getPriceSub()));
            }
            BigDecimal totalMoney = new BigDecimal(total_amount);
            //验证支付订单总金额是否正确
            if (totalMoney.compareTo(priceSub) != 0) {
                //支付订单总金额不正确
                map.put("payBillsMoney", "false");
                map.put("priceSub", priceSub);
            }
        } else {
            //支付订单不存在
            map.put("payBillsExist", "false");
        }
        return map;
    }

    /**
     * PC端验证是否生成收款单
     *
     * @param payJournalNum : 支付订单编号
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean ajaxValidateRelatedBill(String payJournalNum) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select count(1) from dtrelatedbill r ");
        sqlBuilder.append("left join dtCashierBills c ");
        sqlBuilder.append("on r.cashid = c.cashierbillsid ");
        sqlBuilder.append("left join dtPayCashierBill p ");
        sqlBuilder.append("on c.jnumorder = p.orijournalnum ");
        sqlBuilder.append("where p.payjournalnum = ? ");
        sqlBuilder.append("and r.billtype = ? ");
        Object relatedCount = beandao.getObjectBySqlAndParams(sqlBuilder.toString(), new Object[]{payJournalNum, "收款单"});
        Integer count = Integer.parseInt(relatedCount.toString());
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询中联园周边
     *
     * @param
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> ajaxRim() {
        String hql = "from CCode a where a.companyID = ? and a.codePID = ? order by a.codeNumber";

        //因周边数据未录入暂用机械工业加工代替,等录入完成后codePID放入Constant表的常量中
        String codePID = "scode20150815wygb79q82p0000000006";

        Object[] params = {"company201009046vxdyzy4wg0000000025", codePID};

        List<BaseBean> industryList = beandao.getListBeanByHqlAndParams(hql, params);

        return industryList;
    }

    /**
     * 查询国际联营平台
     *
     * @param ppID:父id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm international(int pageNumber, int pageSize, String ppID) {
        StringBuilder sb = new StringBuilder();
        sb.append("select p.ppid,y.logopath,y.companyname,m.custype,p.standard ");
        sb.append("from dt_productpackaging p, t_eshop_cuscom m,dt_ccom_com c,dtcontactcompany y ");
        sb.append("where p.parentId = ? and p.ppid = m.ppid and m.custype = ? ");
        sb.append("and m.companyid = c.compnay_id and c.ccompany_id=y.ccompanyid ");
        PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", new Object[]{ppID, "0"});
        return pageForm;
    }

    /**
     * 查询商品招标列表
     *
     * @param search:判断条件,ppk:产品表,temporary:用于识别是进行中还是已结束,hot:是否是热门
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm ajaxTheTenderList(String search, ProductPackaging ppk,
                                      String temporary, String hot, PageForm pf) {
        StringBuilder sb = new StringBuilder();
        sb.append("select a.goodsName,a.ppID,a.goodsID,a.image,a.bidcount,a.price,a.endDate,a.ibId,a.cashierBillsID,a.s,a.monthSales from ( ");
        sb.append("select p.goodsName,p.ppID,p.goodsID,p.image,b.bidcount,p.price,b.endDate,b.ibId, ");
        sb.append("b.cashierBillsID,(case when  b.endDate < sysdate then ? else ? end ) s,p.monthSales ");
        sb.append("from dt_ProductPackaging p,dtInviteBids b ");
        sb.append("where p.ppID=b.ppID and status != ? ");
        List<Object> params = new ArrayList<Object>();
        params.add("已结束");
        params.add("进行中");
        params.add("00");
        if (ppk != null) {
            if (search != null && search.equals("search")) {
                if (ppk.getGoodsName() != null && !ppk.getGoodsName().equals("")) {
                    sb.append(" and p.goodsName  like ?");
                    params.add("%" + ppk.getGoodsName() + "%");
                }
                if (ppk.getTradeID() != null && !ppk.getTradeID().equals("")) {
                    sb.append(" and p.tradeID  =  ?");
                    params.add(ppk.getTradeID());
                } else if (ppk.getTradeName() != null && !ppk.getTradeName().equals("")) {
                    sb.append(" and (");
                    String[] arary = ppk.getTradeName().split(",");
                    for (int i = 0; i < arary.length; i++) {
                        sb.append("p.tradeName  like  ?");
                        if (i != arary.length - 1) {
                            sb.append(" or ");
                        }
                        params.add("%" + arary[i] + "%");

                    }
                    sb.append(")");
                }
            }
        }
        sb.append(" order by publishDate desc ) a where a.s=? ");
        params.add(temporary);
        if (hot != null && !hot.equals("")) {
            sb.append(" order by cast(a.monthSales as int) desc ");
        }
        PageForm pageForm = getPageFormBySQL((null != pf ? pf.getPageNumber() : 1),
                (null != pf ? pf.getPageSize() : 7), sb.toString(), "select count(*) from (" + sb.toString() + ")", params.toArray());


        return pageForm;
    }

    /**
     * 查询招标详情
     *
     * @param ppID:产品id,cashierBillsID:单据ID,goodsID:物品id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> pcTheTenderDetails(String ppID,
                                                  String cashierBillsID, String goodsID) {

        String sql = "select p.goodsName,p.ppID,p.goodsID,p.image,b.bidcount,p.price,b.endDate,b.ibId,b.publishName,b.remark,b.publishDate,case when b.publishType ='00' then ? else ? end,publishID from dt_ProductPackaging p,dtInviteBids b where p.ppID = ? and b.cashierBillsID = ?";
        List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql, new Object[]{"公司", "个人", ppID, cashierBillsID});
        Object obj = list.get(0);

        String hqlattr = "from AttriProduction where goodsid = ? and  type = ? and sort > ? order by sort";
        List<BaseBean> attrlist = beandao.getListBeanByHqlAndParams(hqlattr, new Object[]{goodsID, "2", 0});

        Object[] obj1 = (Object[]) obj;
        String hql = "from Staff where staffID=?";
        Staff staff = (Staff) beandao.getBeanByHqlAndParams(hql, new Object[]{obj1[12]});
        String reference = "";
        if (staff != null) {
            reference = staff.getReference();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mainobj", obj);
        map.put("attrlist", attrlist);
        map.put("reference", reference);
        return map;
    }

    /**
     * 查询招聘人才
     *
     * @param
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm ajaxRecruitment(int pageNumber, int pageSize) {
        StringBuilder sb = new StringBuilder();
        sb.append("select c.logoPath,p.jobtitle,p.companyName,p.workCity,p.education,p.riId,p.salary,p.publishDate ");
        sb.append("from dtRecruitInfo p,dtContactCompany c ");
        sb.append("where c.ccompanyID = p.ccompanyID and p.status = ? ");
        sb.append("order by p.publishdate desc ");
        PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", new Object[]{"01"});
        return pageForm;
    }

    /**
     * PC端查询招聘人才
     *
     * @param pageNumber : 第几页
     * @param pageSize   : 一页显示多少条
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm ajaxPCRecruitment(int pageNumber, int pageSize, String companyID, String staffid) {
        StringBuilder sb = new StringBuilder();
        PageForm pageForm;
        if (staffid != null && !"".equals(staffid)) {
            sb.append("select rr.riid,rr.jobtitle,d.codevalue,rr.personnumber,rr.workcity,to_char(rr.publishDate, 'yyyy-mm-dd'),rr.publishdate,nvl(pp.tpid, '0') ");
            sb.append("from dtRecruitInfo rr left join Dttalentpool pp on rr.riid = pp.riid and pp.staffid = ? ");
            sb.append("left join  dtccode d on rr.positioncode = d.codeId  and d.companyid = ? where rr.status = ? ");
            pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(1) from (" + sb.toString() + ")", new Object[]{staffid, companyID, "01"});
        } else {
            sb.append("select p.riid,p.jobtitle,d.codeValue,p.personNumber,p.workCity,to_char(p.publishDate,'yyyy-mm-dd'),p.publishdate,0 ");
            sb.append("from dtRecruitInfo p ");
            sb.append("left join dtccode d on p.positioncode=d.codeId and d.companyid= ? ");
            sb.append("where p.status = ? ");
            sb.append("order by p.publishdate desc ");
            pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(1) from (" + sb.toString() + ")", new Object[]{companyID, "01"});
        }

        return pageForm;
    }

    /**
     * PC端查询热门职位
     *
     * @param pageNumber : 第几页
     * @param pageSize   : 一页显示多少条
     * @param companyID
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm ajaxHotPosition(int pageNumber, int pageSize, String companyID) {
        StringBuilder sb = new StringBuilder();
        sb.append("select p.riid,p.jobTitle,p.salary,count(1) ");
        sb.append("from dtRecruitInfo p ");
        sb.append("left join dtTalentPool d ");
        sb.append("on p.riid=d.riid ");
        sb.append("where p.status = ? and d.companyid= ? ");
        sb.append("group by p.riid,p.jobTitle,p.salary ");
        sb.append("order by count(1) desc ");
        PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(1) from (" + sb.toString() + ")", new Object[]{"01", companyID});
        return pageForm;
    }

    /**
     * PC端查询一级职位分类
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> ajaxCodeValueFirst() {
        String sql = "select codeId,codeValue from dtccode where codepid= ? and companyid= ? ";
        List<BaseBean> objs = beandao.getListBeanBySqlAndParams(sql, new Object[]{"scode20160616493hsghahy0000000002", "company201009046vxdyzy4wg0000000025"});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mainObj", objs);
        return map;
    }

    /**
     * PC端查询二级、三级职位分类
     *
     * @param codeID : 一级职位分类的ID
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> ajaxCodeValue(String codeID) {
        String sql = "select codeId,codeValue from dtccode where codePid= ? and companyid= ? ";
        List<Object> objs = beandao.getListObjectBySqlAndParams(sql, new String[]{codeID, "company201009046vxdyzy4wg0000000025"});
        Object[] params = new Object[objs.size() + 1];
        params[0] = "company201009046vxdyzy4wg0000000025";
        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append("select d.codeId,d.codepid,d.codeValue from dtccode d where d.companyid= ? and d.codepid in( ");
        for (int i = 0; i < objs.size(); i++) {
            Object[] obj = (Object[]) objs.get(i);
            params[i + 1] = obj[0];
            if (i == objs.size() - 1) {
                codeBuilder.append("?) ");
                break;
            }
            codeBuilder.append("?,");
        }
        List<Object> nextObj = beandao.getListObjectBySqlAndParams(codeBuilder.toString(), params);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("secObj", objs);
        map.put("nextObj", nextObj);
        return map;
    }

    /**
     * PC端职位搜索
     *
     * @param pageNumber   : 第几页
     * @param pageSize     : 一页显示多少条
     * @param positionName : 职位名称
     * @param codeID       : 一级职位分类的ID
     * @param codePID      : 三级职位分类的ID
     * @param workCity     : 工作地点
     * @param workPlace    : 具体工作地址
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm ajaxSelPosition(int pageNumber, int pageSize,
                                    String jobTitle, String codeID, String codePID, Boolean codePID2, String workCity,
                                    String workPlace) {
        StringBuilder sb = new StringBuilder();
        List<String> params = new ArrayList<String>();
        sb.append("select p.riid,p.jobTitle,d.codeValue,p.personNumber,p.workCity,to_char(p.publishDate,'yyyy-mm-dd'),p.publishdate ");
        sb.append("from dtRecruitInfo p ");
        sb.append("left join dtccode d on p.positionCode=d.codeId ");
        sb.append("where p.status = ? and d.companyid= ? ");
        params.add("01");
        params.add("company201009046vxdyzy4wg0000000025");
        if (jobTitle != null && !"".equals(jobTitle)) {
            sb.append("and p.jobTitle like ? ");
            params.add("%" + jobTitle + "%");
        }
        if (codePID != null && !"".equals(codePID)) {
            sb.append("and d.codepid in(select codeId from dtccode where codePid= ? and  companyid= ? ) ");
            params.add(codePID);
            params.add("company201009046vxdyzy4wg0000000025");
        } else if (codePID2) {
            sb.append("and (d.codepid = ? or d.codeid = ?)");
            params.add(codeID);
            params.add(codeID);
        } else if (codeID != null && !"".equals(codeID)) {
            sb.append("and d.codeid = ? ");
            params.add(codeID);
        }
        if (workCity != null && !"".equals(workCity)) {
            sb.append("and p.workCity like ? ");
            params.add("%" + workCity + "%");
        }
        if (workPlace != null && !"".equals(workPlace)) {
            sb.append("and p.workPlace like ? ");
            params.add("%" + workPlace + "%");
        }
        sb.append("order by p.publishdate desc ");
        Object[] objects = new Object[params.size()];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = params.get(i);
        }
        PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(1) from (" + sb.toString() + ")", objects);
        return pageForm;
    }

    /**
     * PC端招聘职位详情
     *
     * @param riId:职位ID
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> pcRecruitmentDetails(String riId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select p.jobtitle,p.workCity,p.workPlace,d.codeValue,p.personNumber,p.jobRequire,p.riId ");
        sb.append("from dtRecruitInfo p ");
        sb.append("left join dtccode d ");
        sb.append("on p.positioncode=d.codeId ");
        sb.append("where p.status = ? and d.companyid= ? and p.riId= ? ");
        Object obj = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{"01", "company201009046vxdyzy4wg0000000025", riId});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resultObj", obj);
        return map;
    }

    /**
     * PC端热门招聘职位详情
     *
     * @param riId:职位ID
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> pcHotRecruitmentDetails(String staffID, String riId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select p.jobtitle,p.workCity,p.workPlace,d.codeValue,p.personNumber,p.jobRequire,p.riId,nvl(tp.tpid, '0') ");
        sb.append("from dtRecruitInfo p ");
        sb.append("left join dtTalentPool tp on p.riid=tp.riid ");
        sb.append("and tp.staffid = ?");
        sb.append("left join dtccode d ");
        sb.append("on p.positioncode=d.codeId ");
        sb.append("where p.status = ? and d.companyid= ? and p.riId= ? ");
        Object obj = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{staffID, "01", "company201009046vxdyzy4wg0000000025", riId});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resultObj", obj);
        return map;
    }


    /**
     * 招商服务主页
     *
     * @param
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm proAgentList(int pageNumber, int pageSize, ProductPackaging ppk, String hot) {
        StringBuffer sql = new StringBuffer();

        sql.append("select a.ppid,a.susid,a.companyid,a.agenttype,a.re_price,a.amount,a.image,a.goodsname,a.tradeName,count(y.inapid) from (");
        sql.append("select p.ppid,pss.susid,p.companyid,pss.agenttype,ps.re_price,pss.amount,p.image,p.goodsname,p.tradeName");
        sql.append(" from dt_productpackaging p, dt_pro_setup ps, dt_pro_setup_sub pss");
        sql.append(" where p.ppid = ps.ppid and ps.suid = pss.suid and pss.agentstate = ?");
        sql.append(" and pss.type_ppid <> ? and pss.type_ppid <> ?");
        List<Object> params = new ArrayList<Object>();
        params.add("00");
        params.add("p20170220ZVZR76B88M0000000014");
        params.add("p20170220ZVZR76B88M0000000022");

        if (ppk != null) {
            if (ppk.getPpID() != null && ppk.getPpID().length() > 0) {
                sql.append(" and pss.type_ppid = ?");
                params.add(ppk.getPpID());
            }
            if (ppk.getTradeName() != null && ppk.getTradeName().length() > 0) {
                sql.append(" and p.tradeName like ? ");
                params.add("%" + ppk.getTradeName() + "%");
            }
        }
        if (hot != null && !hot.equals("")) {
            sql.append(" order by cast(p.monthSales as int) desc ");
        }

        sql.append(") a left join DT_InvestApply y on a.susid=y.susid ");
        sql.append("group by a.ppid,a.susid,a.companyid,a.agenttype,a.re_price,a.amount,a.image,a.goodsname,a.tradeName ");
        //已发布的招商产品
        PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sql.toString(),
                "select count(*) from (" + sql.toString() + ")", params.toArray());

        return pageForm;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> theQueryImage(String goodsid) {
        String hqlattr = "from AttriProduction where goodsid = ? and  type = ? and sort > ? order by sort";
        List<BaseBean> attrlist = beandao.getListBeanByHqlAndParams(hqlattr, new Object[]{goodsid, "2", 0});
        return attrlist;
    }

    /**
     * pc端城市联营平台
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> UrbanEconomy() {

        String hql = "from CCode a where a.companyID = ? and a.codePID = ? order by a.codeNumber";

        Object[] params = {"company201009046vxdyzy4wg0000000025", "ccode20160909WNF2VFRSRZ0000005086"};

        List<BaseBean> list = beandao.getListBeanByHqlAndParams(hql, params);

        return list;
    }


    /**
     * pc端城市联营平台类型
     *
     * @param hot : 类型
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> platformType(String hot) {

        StringBuilder sb = new StringBuilder();
        sb.append("select p.goodsName,p.ppID,s.re_Price,p.standard,p.standard from dt_ProductPackaging p,");
        sb.append(" DT_PRO_SETUP s where p.type=? and p.ppid=s.ppid order by s.re_Price desc");
        Object[] params = {hot};
        List<BaseBean> list = beandao.getListBeanBySqlAndParams(sb.toString(), params);

        return list;
    }

    /**
     * pc端城市联营地域平台
     *
     * @param hot          : 类型
     * @param goodsName:名称
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm regionalTypes(int pageNumber, int pageSize, String hot, String goodsName) {

        StringBuilder sb = new StringBuilder();
        List<Object> list = new ArrayList<Object>();
        sb.append("select pp.goodsName,pp.ppID,pp.image,pp.type,pp.standard from");
        sb.append(" dt_ProductPackaging pp,dt_ProductPackaging p");
        sb.append(" where pp.parentId = p.ppid and pp.standard=? and pp.type=? and pp.delStatus=? ");
        list.add(hot);
        list.add("联营经济平台");
        list.add("00");
        if (goodsName != null && !goodsName.equals("")) {
            sb.append("and pp.goodsName like ? ");
            list.add("%" + goodsName + "%");
        }
        sb.append(" order by pp.ppid");

        PageForm pageForm = getPageFormBySQL(pageNumber, pageSize,
                sb.toString(), "select count(*) from (" + sb.toString() + ")", list.toArray());

        return pageForm;
    }

    /**
     * 查询收货地址
     *
     * @param cuscom
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public StaffAddress shippingAddress(TEshopCusCom cuscom) {

        String hql = " from StaffAddress where staffID = ?";

        StaffAddress address = (StaffAddress) beandao.getBeanByHqlAndParams(hql, new Object[]{cuscom.getStaffid()});

        return address;
    }

    /**
     * pc端代理资格
     *
     * @param hot          : 类型
     * @param goodsName:名称
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm qualification(int pageNumber, int pageSize, String hot, String goodsName) {

        StringBuilder sb = new StringBuilder();
        List<Object> list = new ArrayList<Object>();

        sb.append("select pp.goodsName,pp.ppID,pp.image,pp.type,pp.standard,pp.goodsid from");
        sb.append(" dt_ProductPackaging pp,dt_ProductPackaging p");
        sb.append(" where pp.parentId = p.ppid and pp.standard=? and pp.type=? and pp.delStatus=? ");
        list.add(hot);
        list.add("联营经济平台");
        list.add("00");
        if (goodsName != null && !goodsName.equals("")) {
            sb.append("and pp.goodsName like ? ");
            list.add("%" + goodsName + "%");
        }
        sb.append(" order by pp.ppid");

        PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", list.toArray());

        return pageForm;
    }

    /**
     * pc端代理价格
     *
     * @param ppID           : 产品id
     * @param companyID:公司ID
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Object agencyPrice(String ppID, String companyID) {
        // 产品明细
        StringBuilder sql = new StringBuilder();
        sql.append("select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type,p.monthSales");
        sql.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p where ps.com_id= ? and p.ppid= ?");
        sql.append(" and p.ppid=ps.ppid and ps.fcom_id is null and rownum=1 ");
        String[] params = {companyID, ppID};

        Object objbeanns = beandao.getObjectBySqlAndParams(sql.toString(), params);

        return objbeanns;

    }

    /**
     * pc端个人/公司加入平台
     *
     * @param ccompanyId:往来单位id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> joinPlatform(String ccompanyId, String typeNews) {

        // 公司
        String hql = "from TEshopCusCom t where t.state = ? and t.companyId = (select c.comanyId from CcomCom c where c.ccompanyId = ?)";
        TEshopCusCom tcc = (TEshopCusCom) beandao.getBeanByHqlAndParams(hql, new Object[]{"2", ccompanyId});
        String cusType = "";
        String lowType = "";
        // 查找下级
        if (tcc != null) {
            cusType = tcc.getCusType();// 网站的
            hql = "select t.cusType from T_ESHOP_CUSCOM t connect by nocycle prior t.account = t.Superioragent start with t.account = ? order by t.cusType desc";
            List<Object> list = beandao.getListObjectBySqlAndParams(hql, new Object[]{tcc.getAccount()});
            hql = null;
            lowType = list.get(0).toString();
            if (lowType.equals("0.5")) {

                lowType = "1";
            } else if (lowType.equals("0")) {
                lowType = "0.5";

            } else {
                lowType = Integer.parseInt(lowType) + 1 + "";
            }

        }

        StringBuilder sql = new StringBuilder();
        StringBuilder sqlgs = new StringBuilder();
        sql.append(
                "select ps.ppname,ps.ppid,ps.re_price,p.image,p.model,p.goodsid,p.companyid from DT_PRO_SETUP ps,dt_ProductPackaging p where ps.com_id= ? ");
        sql.append(" and p.ppid=ps.ppid and p.showweixin ='01' and p.type= ? and  ps.fcom_id is null ");
        sql.append("");
        sql.append("");
        sql.append("");
        sqlgs.append(sql);
        sqlgs.append(" and model != ?  order by sorting");
        sql.append(" and ((");
        if (cusType.equals("0")) {
            sql.append("p.model>=?");
            String tempsql = "select t.cusType from T_ESHOP_CUSCOM t where (t.cusType = ? or t.cusType = ?) connect by nocycle prior t.account = t.Superioragent start with t.account = ?";
            List<Object> lists = beandao.getListObjectBySqlAndParams(tempsql,
                    new Object[]{"0.5", "1", tcc.getAccount()});

            if (lists.contains("0.5")) {
                sql.append(" and p.model!='0.5'");
            }
            if (lists.contains("1")) {
                sql.append(" and p.model!='1'");
            }
        } else {
            sql.append(" p.model>?");
        }

        List<BaseBean> list;

        if ("MyjiaruCompany".equals(typeNews)) {
            // 查询公司会员列表
            list = beandao.getListBeanBySqlAndParams(sqlgs.toString(),
                    new String[]{"company201009046vxdyzy4wg0000000025", "公司会员", "0"});
        } else {
            // 查询个人会员列表
            list = beandao.getListBeanBySqlAndParams(sql.append(") or model = '8') order by sorting").toString(),
                    new String[]{"company201009046vxdyzy4wg0000000025", "个人会员", cusType});
        }
        return list;
    }


    /**
     * pc端查询促销品
     *
     * @param ppID:产品id
     * @param companyID:公司id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Object promotionProducts(String ppID, String companyID) {

        StringBuilder sb = new StringBuilder();

        sb.append("select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type,p.monthSales ");
        sb.append("from DT_PRO_SETUP ps, dt_ProductPackaging p ");
        sb.append("where p.ppid = ps.ppid and ps.fcom_id is null and p.showweixin = ? and ps.ppid in ");
        sb.append("(select pt.ptppid from dt_promotion pt where pt.companyid = ? and pt.ppid = ?) and rownum = 1 ");

        Object obj = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{"01", companyID, ppID});

        return obj;
    }


    /**
     * pc端查询公司轮播图
     *
     * @param ccompanyId:往来单位id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> shufflingFigure(String ccompanyId) {

        StringBuilder sb = new StringBuilder();

        sb.append("select a.imgurl,p.ppid ");
        sb.append("from dt_ccom_com c, dt_productpackaging p,dtAttriProduction a ");
        sb.append("where c.ccompany_id = ? and c.compnay_id = p.companyid ");
        sb.append("and p.delstatus = ? and p.type like ? and p.goodsid = a.goodsid ");
        sb.append("and a.type = ? ");

        List<BaseBean> list = beandao.getListBeanBySqlAndParams(sb.toString(), new Object[]{ccompanyId, "00", "%公司轮播图", "2"});

        return list;
    }


    /**
     * 查询公司资讯/推广
     *
     * @param pageNumber :第几页,pageSize:一页显示多少条
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> companyNews(int pageNumber, int pageSize, String ccompanyId) {
        StringBuilder sb = new StringBuilder();

        sb.append("select x.goodsName,x.PackagingDate,x.image,x.ppid,x.name,d.url ");
        sb.append("from (select p.goodsName,p.PackagingDate,p.image,p.ppid,cc.companyname as name,p.goodsid ");
        sb.append("from dt_ProductPackaging p, DT_ccom_com m, dtcontactcompany cc ");
        sb.append("where (p.type = ? and p.fiveclear = ? and p.delStatus = ? and ");
        sb.append("p.companyID = m.compnay_id and m.ccompany_id = cc.ccompanyid and cc.ccompanyid = ? ) or (p.type = ? and ");
        sb.append("p.review = ? ");
        sb.append("and p.companyID = m.compnay_id and m.ccompany_id = cc.ccompanyid and p.companyid is not null and cc.ccompanyid = ? ) ");
        sb.append(") x,Dtgoodfunction d where x.goodsid=d.goodsid and d.orders=? ");
        sb.append("order by x.PackagingDate desc");

        PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", new Object[]{"新闻", "2", "00", ccompanyId, "会员分享", "02", ccompanyId, 1});

        List<String> list = new ArrayList<String>();

        if (pageForm != null && pageForm.getList() != null) {
            for (int i = 0; i < pageForm.getList().size(); i++) {
                Object pp = pageForm.getList().get(i);
                Object[] pp1 = (Object[]) pp;
                String content = "";
                if (pp1[5] != null) {
                    content = getContentFromFile(pp1[5].toString());
                    String[] con = content.split("<p>");
                    for (int j = 1; j < con.length; j++) {
                        content = con[j].substring(0, con[j].lastIndexOf("</p>"));
                        if (!content.equals("")) {
                            break;
                        }
                    }
                }
                list.add(content);
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        map.put("list", list);

        return map;
    }


}