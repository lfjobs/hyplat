package hy.ea.production.action.adesign;


import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsBarcode;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.company.ScaleGoods;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.FeeScale;
import hy.ea.bo.office.VenueInformation;
import hy.ea.bo.production.AttriProduction;
import hy.ea.bo.production.BsimTest;
import hy.ea.bo.production.GoodFunction;
import hy.ea.production.service.GoodsManageSerivce;
import hy.ea.service.*;
import hy.ea.util.*;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import org.springframework.transaction.annotation.Transactional;

/**
 * 物品设计
 */
@Controller
@Scope("prototype")
public class GoodsDesignAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private CLogBookService logBookService;
    @Resource
    private ShowExcelService excelService;
    @Resource
    private UpLoadFileService fileService;
    @Resource
    private GoodsManageSerivce gmservice;
    @Resource
    private UploadContentToFileService contentToFileService;
    @Resource
    private CCodeService codeService;
    private InputStream excelStream;

    private PageForm pageForm;

    private String result;

    private int pageNumber;
    private String search;

    private GoodsManage goodsManage;

    private GoodFunction goodFunction;

    private VenueInformation vf;

    private FeeScale fs;

    private List<BaseBean> list;
    private Map<Integer, String> maplist;

    private String aeptype;//添加a，编辑e，预览p
    private List<BaseBean> functionlist;
    private CarInformation carInformation;
    private File file; // 文件
    private String fileFileName; // 文件名
    private String filePath; // 文件路径

    private String fiveClear;
    private AttriProduction attrproduct;
    private File[] photo;
    private String[] photoFileName;
    private String[] colorvalue;
    private String[] imgurl;
    private List<BaseBean> listsize;
    private List<BaseBean> listcolor;
    private List<CCode> variablelist;
    private ScaleGoods scaleGoods;
    Map<String, GoodsBarcode> goodsbarmap;


    private String barCode;


    /***
     *
     *
     * 物品设计列表
     * @return
     */
    public String getGoodDesignList() {


        pageForm = baseBeanService.getPageFormByDC(
                (null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 10 : pageNumber), getLists());


        return "designlist";
    }

    private DetachedCriteria getLists() {

        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");

        DetachedCriteria dc = DetachedCriteria.forClass(GoodsManage.class);
        dc.add(Restrictions.eq("companyID", account.getCompanyID()));
        dc.add(Restrictions.eq("goodsState", "00"));
        if (fiveClear != null && !"".equals(fiveClear))
            dc.add(Restrictions.eq("fiveClear", fiveClear));
        dc.addOrder(Order.desc("createdate"));

        if (search != null && "search".equals(search)) {
            goodsManage = (GoodsManage) session
                    .get("tablesearch");

            if (goodsManage.getGoodsCoding() != null && !goodsManage.getGoodsCoding().equals("")) {
                dc.add(Restrictions.like("goodsCoding", goodsManage.getGoodsCoding(), MatchMode.ANYWHERE));
            }
            if (goodsManage.getGoodsName() != null && !goodsManage.getGoodsName().equals("")) {
                dc.add(Restrictions.like("goodsName", goodsManage.getGoodsName(), MatchMode.ANYWHERE));
            }
            if (goodsManage.getBarCode() != null && !goodsManage.getBarCode().equals("")) {
                dc.add(Restrictions.like("barCode", goodsManage.getBarCode(), MatchMode.EXACT));
            }
        }

        return dc;
    }


    public String toSearch() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("tablesearch", goodsManage);
        return getGoodDesignList();
    }


    /**
     * 获取添加页面
     *
     * @return
     */
    public String getAddPage() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        variablelist = codeService.getCCodeListByPID(account.getCompanyID(),
                "scode20101014v5zed7cukk0000000003");

        return "addpage";
    }

    /**
     * 获取修改页面
     *
     * @return
     */
    public String editOrPrevPage() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        variablelist = codeService.getCCodeListByPID(account.getCompanyID(),
                "scode20101014v5zed7cukk0000000003");
        //获取基本信息
        String hql = "from GoodsManage where goodsID = ?";
        goodsManage = (GoodsManage) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{goodsManage.getGoodsID()});


        scaleGoods = (ScaleGoods) baseBeanService.getBeanByHqlAndParams("from ScaleGoods where goodsID= ?", new Object[]{goodsManage.getGoodsID()});
        if (goodsManage.getTypeID() != null && goodsManage.getTypeID().indexOf("车") != -1) {
            String hqlcar = "from CarInformation where goodsID = ?";
            carInformation = (CarInformation) baseBeanService.getBeanByHqlAndParams(hqlcar, new Object[]{goodsManage.getGoodsID()});
        }

        //获取功能信息
        String hqlfun = "from GoodFunction where goodsid = ? order by orders";
        functionlist = baseBeanService.getListBeanByHqlAndParams(hqlfun, new Object[]{goodsManage.getGoodsID()});
        maplist = new HashMap<Integer, String>();
        //获取每个功能信息的具体内容

        for (int i = 0; i < functionlist.size(); i++) {
            GoodFunction goodFun = (GoodFunction) functionlist.get(i);
            maplist.put(i + 1, getContentFromFile(goodFun.getUrl()));
        }

        //获取颜色，尺码


        String hqlattr = "from AttriProduction where goodsid = ?";
        List<BaseBean> listattr = baseBeanService.getListBeanByHqlAndParams(hqlattr, new Object[]{goodsManage.getGoodsID()});
        listsize = new ArrayList<BaseBean>();
        listcolor = new ArrayList<BaseBean>();
        for (int i = 0; i < listattr.size(); i++) {
            AttriProduction attrpp = (AttriProduction) listattr.get(i);
            if (attrpp.getType().equals("0")) {
                listsize.add(attrpp);
            } else {
                listcolor.add(attrpp);
            }
        }


        //查询场地信息
        String hql1 = "select f.feecID,v.siteId,v.siteName from FeeScale f,VenueInformation v where goodsID=? and f.siteId=v.siteId ";
        Object obj = baseBeanService.getObjectByHqlAndParams(hql1, new Object[]{goodsManage.getGoodsID()});


        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("size", listsize.size() == 0 ? null : ((AttriProduction) listsize.get(0)).getAttriname());
        request.setAttribute("color", listcolor.size() == 0 ? null : ((AttriProduction) listcolor.get(0)).getAttriname());
        request.setAttribute("obj", obj);

        //获取其他规格条码
        List<BaseBean> goodsBarList = gmservice.getGoodsBarList(goodsManage.getGoodsID());
        request.setAttribute("goodsBarList", goodsBarList);
        if (aeptype.equals("e")) {
            return "editpage";
        }

        if (aeptype.equals("p")) {
            return "prevpage";
        }
        return null;

    }

    public String ajaxGetStaffData() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String parameter = ServletActionContext.getRequest().getParameter("parameter");
        String selectDept = (String) session.get("organizationID");
        StringBuilder hql = new StringBuilder();
        hql.append("from Staff s where exists (");
        hql.append(" select staffID from COS c");
        hql.append(" where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and (");
        hql.append(" c.organizationID = ? or exists( select d.depPostID from DepartmentPost d");
        hql.append("  where d.depPostID = c.depPostID and d.leveloneOrgID = ?");
        hql.append("))) and s.staffName like ?");

        pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
                        .getPageNumber() : 1), (pageNumber == 0 ? 20 : pageNumber),
                hql.toString(), "select count(s) " + hql.substring(hql.indexOf("from")),
                new Object[]{account.getCompanyID(), "50", selectDept, selectDept, "%" + parameter + "%"});

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject js = JSONObject.fromObject(map);
        result = js.toString();
        return "success";
    }


    /**
     * 读取excel-产品
     *
     * @return
     */
    public String jxexcelGoods() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        HttpServletRequest request = ServletActionContext.getRequest();
        String path = request.getRealPath("/");
        List<BaseBean> beans = new ArrayList<BaseBean>();
        String s = request.getParameter("path");
        List<BaseBean> goodsbeans = new ArrayList<BaseBean>();
        List<BaseBean> barcodebeans = new ArrayList<BaseBean>();
        int sgnum = 0;
        int gnum = 0;
        try {
            String times = Utilities.getDateString(new Date(), "yyyy-MM-dd");

            //产品名称	单位	条码号	供应价	毛利	贴牌	设备投资	省级代理	县级代理	村级代理	业务佣金	客户积分	批发价	平台服务	平台零售价	商品照片	详情	是否称重	计件方式
            String columns[] = {"产品名称", "产地", "货号", "单位", "是否称重", "计件方式", "条码号", "品牌", "包装规格", "数量", "商品类别", "行业分类", "供应价", "毛利", "设备安装", "贴牌", "设备投资", "省级代理", "县级代理", "村级代理", "业务佣金", "客户积分", "批发价", "平台服务", "平台零售价", "商品主图", "商品照片2", "商品照片3", "详情"};
            List<Map<String, String>> list = ExcelReadUtils.jxexcel("D:\\test.xlsx", columns);
            //遍历解析出来的list
            for (Map<String, String> map : list) {
                GoodFunction goodFun = null;
                GoodsManage goods = null;
                ProductPackaging propack = null;
                GoodsBarcode gb = null;
                AttriProduction apro = null;
                for (int j = 0; j < goodsbeans.size(); j++) {
                    GoodsManage g = (GoodsManage) goodsbeans.get(j);
                    if (g.getGoodsName().equals(map.get("产品名称").replaceAll(" ", ""))) {
                        goods = g;
                        break;
                    }
                }
                if (goods == null && map.get("产品名称") != null && !map.get("产品名称").replaceAll(" ", "").equals("")) {
                    goods = (GoodsManage) baseBeanService.getBeanByHqlAndParams("from GoodsManage where goodsName=? and companyID=?", new Object[]{map.get("产品名称").replaceAll(" ", ""), account.getCompanyID()});
                }

                if (goods == null) {
                    String storepath = "/upload_files/gooddesign/" + "/" + (s == null || s.equals("") ? times : s) + "/" + account.getCompanyID() + "/";

                    String sss = map.get("商品类别") == null || map.get("商品类别").equals("") ? "物品类别暂定" : map.get("商品类别");
                    if (gnum <= 0) {
                        String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in (select c.companyID from Company c where c.groupCompanySn=?)  and vt.typeID=? ";
                        gnum = baseBeanService.getConutByByHqlAndParams(hql, new Object[]{session.get("groupCompanySn"), sss});

                    }
                    gnum++;

                    BigDecimal gyj = new BigDecimal(map.get("供应价").replaceAll(" ", "")).setScale(4, BigDecimal.ROUND_HALF_UP);
                    BigDecimal lsj = new BigDecimal(map.get("平台零售价").replaceAll(" ", "")).setScale(4, BigDecimal.ROUND_HALF_UP);
                    goods = new GoodsManage();
                    goods.setGoodsID(serverService.getServerID("goodsID"));
                    goods.setFiveClear("4");
                    if (map.get("商品主图") != null && !map.get("商品主图").equals("")) {
                        goods.setPhotoPath(storepath + map.get("商品主图"));
                        apro = new AttriProduction();
                        apro.setApid(serverService.getServerID("apid"));
                        apro.setGoodsid(goods.getGoodsID());
                        apro.setImgurl(storepath + map.get("商品主图"));
                        apro.setType("2");
                        apro.setSort(1);
                        beans.add(apro);
                    }

                    goods.setCompanyID(account.getCompanyID());
                    goods.setGoodsState("00");
                    goods.setGoodsName(map.get("产品名称").replaceAll(" ", ""));
                    goods.setVariableID(map.get("单位"));
                    goods.setBarCode(map.get("条码号"));
                    goods.setIsScale(map.get("是否称重"));
                    goods.setTradeCode(map.get("行业分类"));
                    goods.setTypeID(sss);

                    // 获取拼音码
                    String pinyin = ToChineseFirstLetter.getFirstLetter(sss);
                    String Upstr = pinyin.toUpperCase();// 转换为大写

                    DecimalFormat form = new DecimalFormat("000000");
                    String ss = form.format(gnum);
                    goods.setGoodsCoding(Upstr + "_" + ss);
                    goods.setCreatedate(new Date());
                    goods.setBrand(map.get("品牌"));
                    goods.setStandard(map.get("包装规格"));
                    goodsbeans.add(goods);

                    if (map.get("商品照片2") != null && !map.get("商品照片2").equals("")) {
                        apro = new AttriProduction();
                        apro.setApid(serverService.getServerID("apid"));
                        apro.setGoodsid(goods.getGoodsID());
                        apro.setImgurl(storepath + map.get("商品照片2"));
                        apro.setType("2");
                        apro.setSort(2);
                        beans.add(apro);
                    }

                    propack = new ProductPackaging();
                    propack.setPpID(serverService.getServerID("p"));
                    propack.setGoodsID(goods.getGoodsID());
                    propack.setDelStatus("00");
                    propack.setProductstate("00");
                    propack.setFiveClear("4");
                    propack.setYjstatus("00");
                    propack.setVipStatus("00");
                    propack.setWholesaleStatus("00");
                    propack.setActivityStatus("00");
                    propack.setField("01");
                    propack.setProductCode(map.get("条码号"));
                    propack.setCompanyID(account.getCompanyID());
                    propack.setPackagingDate(new Date());
                    propack.setAssemble("00");
                    propack.setIsScale(map.get("是否称重"));
                    propack.setGoodsName(map.get("产品名称").replaceAll(" ", ""));
                    propack.setVariableID(map.get("单位"));
                    propack.setQuantity("1");
                    propack.setPrice(gyj.toString());
                    propack.setMoney(gyj.toString());
                    propack.setBarCode(map.get("条码号"));
                    if (map.get("商品主图") != null && !map.get("商品主图").equals("")) {
                        propack.setPhoto(storepath + map.get("商品主图"));
                        propack.setImage(storepath + map.get("商品主图"));
                    }

                    propack.setCreateTime(new Date());
                    propack.setXmtype("04");
                    propack.setXmtypename("教务项目");
                    propack.setProductstate("01");
                    propack.setCategory("00");
                    propack.setType(sss);
                    propack.setGoodsNum(goods.getGoodsCoding());
                    propack.setBrand(map.get("品牌"));
                    propack.setStandard(map.get("包装规格"));
                    propack.setTradeCode(map.get("行业分类"));
                    propack.setQualified("1");

                    goodFun = new GoodFunction();
                    String url = saveContentToFile("<p>" + map.get("详情").replaceAll("\\s*", "") + "<img src=\"" + storepath + map.get("商品主图") + "\" title=\"" + map.get("商品主图") + "\" alt=\"" + map.get("商品主图") + "\"/></p>");
                    goodFun.setGfid(serverService.getServerID("gfid"));
                    goodFun.setOrders(1);
                    goodFun.setUrl(url);
                    goodFun.setName("产品详情");
                    goodFun.setType("1");
                    goodFun.setGoodsid(goods.getGoodsID());

                    BsimTest bt = new BsimTest();
                    bt.setBsimTestId(serverService.getServerID("testid"));
                    bt.setCompanyId(account.getCompanyID());
                    bt.setCompanyName(((Company) session.get("currentcompany")).getCompanyName());
                    bt.setGoodBar(propack.getBarCode());
                    bt.setGoodName(propack.getGoodsName());
                    //bt.setGoodStandard(propack.getStandard());
                    //bt.setIndustryClassification(pp.getTradeCode());
                    bt.setPrice(propack.getPrice());
                    bt.setMoney(propack.getMoney());
                    bt.setBtnumber(propack.getQuantity());
                    //bt.setItemNumber(pp.getProductCode());
                    bt.setStatus("02");
                    bt.setId(propack.getPpID());
                    bt.setOrganizationId((String) session.get("organizationID"));
                    bt.setOrganizationName((String) session.get("organizationName"));
                    bt.setCategory("00");
                    bt.setFiveClear(propack.getFiveClear());
                    bt.setAuditTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

                    if (map.get("是否称重").equals("0")) {
                        ScaleGoods scaleGoods = new ScaleGoods();
                        scaleGoods.setSgID(serverService.getServerID("sgid"));
                        scaleGoods.setCompanyID(account.getCompanyID());
                        String phql = "select count(*) from ScaleGoods where companyID = ? ";
                        int pcount = baseBeanService.getConutByByHqlAndParams(phql, new Object[]{account.getCompanyID()});
                        if (pcount != 0 && sgnum == 0) {
                            String sql = "select max(to_number(plu)) from dtScaleGoods  where companyID= ?";
                            pcount = baseBeanService.getConutByBySqlAndParams(sql,
                                    new Object[]{account.getCompanyID()});
                        }
                        sgnum++;

                        //自定生成条码
                        scaleGoods.setPlu(pcount + sgnum);
                        DecimalFormat fofrm = new DecimalFormat("00000");
                        String ap = fofrm.format(pcount + sgnum);
                        scaleGoods.setAlternativeItemID(ap);//货号
                        goods.setBarCode(ap);
                        propack.setBarCode(ap);
                        scaleGoods.setGoodsID(goods.getGoodsID());
                        scaleGoods.setUnitOfMeasureCode(map.get("计件方式"));//计价单位前台传
                        beans.add(scaleGoods);
                    }

                    //"供应价","1","贴牌","设备投资","省级代理","县级代理","村级代理","业务佣金","客户积分","2","3","平台零售价",
                    //p20170220ZVZR76B88M0000000022	客户积分
                    //p20170510SIR5KABJEP0000000003	未招标成功代理
                    //p20170220ZVZR76B88M0000000017	设备安装
                    //p20170220ZVZR76B88M0000000016	贴牌
                    //p20170220ZVZR76B88M0000000019	县级代理
                    //p20170220ZVZR76B88M0000000018	省级代理
                    //p20170220ZVZR76B88M0000000020	村级代理
                    //p20170220ZVZR76B88M0000000014	全国代理
                    //p20170605KY3VAANZJG0000000003	设备投资
                    Object[] yj = new Object[]{map.get("设备安装"), map.get("贴牌"), map.get("设备投资"), map.get("省级代理"), map.get("县级代理"), map.get("村级代理"), map.get("客户积分")};
                    Object[] dlid = new Object[]{"p20170220ZVZR76B88M0000000017", "p20170220ZVZR76B88M0000000016", "p20170605KY3VAANZJG0000000003", "p20170220ZVZR76B88M0000000018", "p20170220ZVZR76B88M0000000019", "p20170220ZVZR76B88M0000000020", "p20170220ZVZR76B88M0000000022",};
                    if (lsj.compareTo(BigDecimal.ZERO) > 0) {
                        ProSetup proSetup = new ProSetup();
                        proSetup.setSuid(serverService.getServerID("setup"));

                        BigDecimal proxyprice = BigDecimal.ZERO;

                        for (int i = 0; i < yj.length; i++) {
                            ProSetupSub setupSub = new ProSetupSub();
                            BigDecimal dl = null;
                            setupSub.setSusid(serverService.getServerID("prosetupsub"));
                            if ("".equals(yj[i])) {
                                dl = BigDecimal.ZERO;
                            } else {
                                dl = new BigDecimal(yj[i].toString()).setScale(4, BigDecimal.ROUND_HALF_UP);
                            }
                            setupSub.setPpid(propack.getPpID());
                            setupSub.setSuid(proSetup.getSuid());
                            setupSub.setAmount(dl.toString());
                            setupSub.setTypePpid(dlid[i].toString());
                            setupSub.setSjdate(new Date());
                            proxyprice = proxyprice.add(dl);
                            beans.add(setupSub);
                        }

                        BigDecimal ywyj = lsj.subtract(gyj).subtract(proxyprice);
                        proSetup.setEfPrice(gyj.toString());
                        proSetup.setRePrice(lsj.toString());
                        proSetup.setComId(account.getCompanyID());
                        proSetup.setBrokerage(ywyj.toString());
                        proSetup.setPpid(propack.getPpID());
                        proSetup.setPpname(propack.getGoodsName());
                        proSetup.setTzType("03");
                        proSetup.setSjdate(new Date());
                        proSetup.setProxyprice(proxyprice.toString());
                        proSetup.setState("00");

                        beans.add(proSetup);

                        propack.setYjstatus("01");
                    }

                    beans.add(goods);
                    beans.add(propack);
                    beans.add(goodFun);
                    beans.add(bt);
                } else {
                    if (map.get("条码号") != null && !map.get("条码号").equals("")) {
                        gb = (GoodsBarcode) baseBeanService.getBeanByHqlAndParams("from GoodsBarcode where goodsid=? and barcode=?", new Object[]{goods.getGoodsID(), map.get("条码号")});
                    }
                }
                for (int j = 0; j < barcodebeans.size(); j++) {
                    GoodsBarcode gbs = (GoodsBarcode) barcodebeans.get(j);
                    if (goods.getGoodsID().equals(gbs.getGoodsid()) && gbs.getBarcode().equals(map.get("条码号"))) {
                        gb = gbs;
                        break;
                    }
                }
                if (map.get("条码号") != null && !map.get("条码号").equals("")) {
                    if (gb == null) {
                        gb = new GoodsBarcode();
                        gb.setBarcodeid(serverService.getServerID("barcode"));
                        gb.setGoodsid(goods.getGoodsID());
                        gb.setBarcode(map.get("条码号"));
                        gb.setQuantity(map.get("数量"));
                        gb.setSpcation(map.get("包装规格"));
                        gb.setVariable1Id(map.get("单位"));
                        gb.setCompanyID(account.getCompanyID());
                        if (map.get("数量").equals("1")) {
                            gb.setStatus("00");
                        } else {
                            gb.setStatus("01");
                        }
                        barcodebeans.add(gb);
                        beans.add(gb);
                    }
                }
            }
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }

    public String jxexcela() {
        try {
            Map<String, Object> session = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session.get("account");
            HttpServletRequest request = ServletActionContext.getRequest();
            //产品名称	单位	条码号	供应价	毛利	贴牌	设备投资	省级代理	县级代理	村级代理	业务佣金	客户积分	批发价	平台服务	平台零售价	商品照片	详情	是否称重	计件方式
            String columns[] = {"产品编号", "产品名称", "商品主图", "商品照片2", "商品照片3", "商品照片4", "商品照片5", "详情"};
            List<Map<String, String>> list = ExcelReadUtils.jxexcel("D:\\test.xlsx", columns);
            List<String> sqlbeans = new ArrayList<String>();
            List<Object[]> ppbeans = new ArrayList<Object[]>();
            String path = request.getRealPath("/");
            List<BaseBean> beans = new ArrayList<BaseBean>();
            String s = request.getParameter("path");
            String storepath = "/upload_files/gooddesign/" + "/" + s + "/" + account.getCompanyID() + "/";
            for (Map<String, String> map : list) {
                List<BaseBean> goodss = baseBeanService.getListBeanByHqlAndParams
                        ("from GoodsManage where (barcode=? or goodsName=?) and companyID=?",
                                new Object[]{map.get("产品编号").replaceAll(" ", ""),
                                        map.get("产品名称").replaceAll(" ", ""), account.getCompanyID()});

                AttriProduction apro = null;
                String goodssql = "update GoodsManage g set g.photoPath=? where g.barCode=? or goodsName=?";
                Object[] a = new Object[]{storepath + map.get("商品主图").replaceAll(" ", "")
                        , map.get("产品编号").replaceAll(" ", ""), map.get("产品名称").replaceAll(" ", "")};
                sqlbeans.add(goodssql);
                ppbeans.add(a);
                String ppsql = "update ProductPackaging p set p.photo = ?, p.image = ? where p.barCode = ? or goodsName=?";
                Object[] b = new Object[]{storepath + map.get("商品主图").replaceAll(" ", ""),
                        storepath + map.get("商品主图").replaceAll(" ", ""),
                        map.get("产品编号").replaceAll(" ", ""),
                        map.get("产品名称").replaceAll(" ", "")};
                sqlbeans.add(ppsql);
                ppbeans.add(b);

                if (goodss != null && goodss.size() > 0) {
                    for (int i = 0; i < goodss.size(); i++) {
                        GoodsManage goods = (GoodsManage) goodss.get(i);
                        List<BaseBean> propacks = baseBeanService.getListBeanByHqlAndParams("from ProductPackaging where goodsid=?", new Object[]{goods.getGoodsID()});
                        if (map.get("详情") != null && !map.get("详情").equals("")) {
                            GoodFunction goodFun = (GoodFunction) baseBeanService.getBeanByHqlAndParams("from GoodFunction where goodsid=?", new Object[]{goods.getGoodsID()});
                            if (goodFun != null) {
                                saveContentToFile("<p><img src=\"" + storepath + map.get("详情") +
                                                "\" title=\"" + map.get("详情") + "\" alt=\"" + map.get("详情") + "\"/></p>",
                                        ServletActionContext.getServletContext().getRealPath("") + goodFun.getUrl());
                            } else {
                                String url = saveContentToFile("<p><img src=\"" + storepath + map.get("详情") +
                                        "\" title=\"" + map.get("详情") + "\" alt=\"" + map.get("详情") + "\"/></p>");
                                goodFun.setGfid(serverService.getServerID("gfid"));
                                goodFun.setOrders(1);
                                goodFun.setUrl(url);
                                goodFun.setName("产品详情");
                                goodFun.setType("1");
                                goodFun.setGoodsid(goods.getGoodsID());
                                beans.add(goodFun);
                            }
                        }

                        String dsql = "delete AttriProduction  where goodsID=?";
                        sqlbeans.add(dsql);
                        ppbeans.add(new Object[]{goods.getGoodsID()});
                        if (map.get("商品主图") != null && !map.get("商品主图").equals("")) {
                            goods.setPhotoPath(storepath + map.get("商品主图").replaceAll(" ", ""));
                            beans.add(goods);
                            if (propacks != null &&propacks.size()>0) {
                                for (int j = 0; j <propacks.size() ; j++) {
                                    ProductPackaging propack=(ProductPackaging)propacks.get(j);
                                    propack.setPhoto(storepath + map.get("商品主图").replaceAll(" ", ""));
                                    propack.setImage(storepath + map.get("商品主图").replaceAll(" ", ""));
                                    beans.add(propack);
                                }
                            }
                            apro = new AttriProduction();
                            apro.setApid(serverService.getServerID("apid"));
                            apro.setGoodsid(goods.getGoodsID());
                            apro.setImgurl(storepath + map.get("商品主图").replaceAll(" ", ""));
                            apro.setType("2");
                            apro.setSort(1);
                            beans.add(apro);
                        }
                        if (map.get("商品照片2") != null && !map.get("商品照片2").equals("")) {
                            apro = new AttriProduction();
                            apro.setApid(serverService.getServerID("apid"));
                            apro.setGoodsid(goods.getGoodsID());
                            apro.setImgurl(storepath + map.get("商品照片2").replaceAll(" ", ""));
                            apro.setType("2");
                            apro.setSort(2);
                            beans.add(apro);
                        }
                        if (map.get("商品照片3") != null && !map.get("商品照片3").equals("")) {
                            apro = new AttriProduction();
                            apro.setApid(serverService.getServerID("apid"));
                            apro.setGoodsid(goods.getGoodsID());
                            apro.setImgurl(storepath + map.get("商品照片3").replaceAll(" ", ""));
                            apro.setType("2");
                            apro.setSort(2);
                            beans.add(apro);
                        }
                        if (map.get("商品照片4") != null && !map.get("商品照片4").equals("")) {
                            apro = new AttriProduction();
                            apro.setApid(serverService.getServerID("apid"));
                            apro.setGoodsid(goods.getGoodsID());
                            apro.setImgurl(storepath + map.get("商品照片4").replaceAll(" ", ""));
                            apro.setType("2");
                            apro.setSort(2);
                            beans.add(apro);
                        }
                        if (map.get("商品照片5") != null && !map.get("商品照片5").equals("")) {
                            apro = new AttriProduction();
                            apro.setApid(serverService.getServerID("apid"));
                            apro.setGoodsid(goods.getGoodsID());
                            apro.setImgurl(storepath + map.get("商品照片5").replaceAll(" ", ""));
                            apro.setType("2");
                            apro.setSort(2);
                            beans.add(apro);
                        }
                    }
                }
            }
            baseBeanService.executeHqlsByParamsList(beans, sqlbeans.toArray(new String[]{}), ppbeans);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 读取excel-佣金
     *
     * @return
     */
    public String jxexcelSetup() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        gmservice.jxexcelSetup(account);
        return "success";
    }


    /**
     * 保存和修改物品
     *
     * @return
     */
    public String saveGoods() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        HttpServletRequest request = ServletActionContext.getRequest();

        String path = request.getRealPath("/");

        List<BaseBean> beans = new ArrayList<BaseBean>();
        List<String> hqls = new ArrayList<String>();
        List<Object> parms = new ArrayList<Object>();
        String[] arnames = null;
        String[] arurls = null;
        if (goodFunction != null) {
            arnames = goodFunction.getName().split(",");
            arurls = goodFunction.getUrl().split("#z");
        }


        String typeID = goodsManage.getTypeID();
        goodsManage.setTypeID(typeID.substring(typeID.indexOf(")") + 1));
        goodsManage.setCreatedate(new Date());


        //保存颜色以及图片
        String colorcon = request.getParameter("colorcon");
        String[] imgurls = null;
        String times = Utilities.getDateString(new Date(), "yyyy-MM-dd");
        String storepath = "/upload_files/gooddesign/" + "/" + times + "/" + account.getCompanyID() + "/";
        if (photo != null) {
            imgurls = fileService.mutiFileUpload(path, photoFileName, photo, account.getCompanyID(), "/upload_files/gooddesign/"
                    + times);
        }

        String photoPath = null;
        if (file != null) {
            photoPath = fileService.savePhoto(path, fileFileName, file, account.getCompanyID(), "/upload_files/gooddesign/"
                    + times);
            goodsManage.setPhotoPath(photoPath);
        }

//	    if(imgurls!=null&&imgurls.length>0){
//	    	for(int i=0;i<imgurls.length;i++){
//	    		filePath=imgurls[i];
//	    		String jjPath = filePath.split("\\.")[0] + "small." + filePath.split("\\.")[1];
//	    		ImageCut.scale(path+filePath, path+jjPath, 414, 431);
//	    	}
//	    }

        //保存基本信息
        if (goodsManage.getGoodsID() == null || goodsManage.getGoodsID().equals("")) {
            goodsManage.setGoodsID(serverService.getServerID("goodsID"));
            // 编号处理
            String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in (select c.companyID from Company c where c.groupCompanySn=?)  and vt.typeID=? ";
            Object[] params = {session.get("groupCompanySn"), goodsManage.getTypeID()};
            // 获取拼音码
            String pinyin = ToChineseFirstLetter.getFirstLetter(goodsManage.getTypeID());
            String Upstr = pinyin.toUpperCase();// 转换为大写
            int goodscodingnum = baseBeanService.getConutByByHqlAndParams(hql, params);
            DecimalFormat form = new DecimalFormat("000000");
            String ss = form.format(goodscodingnum + 1);
            goodsManage.setGoodsCoding(Upstr + "_" + ss);
            goodsManage.setFiveClear(fiveClear);


            AttriProduction attrp = null;
            if (colorvalue != null) {
                for (int i = 0; i < colorvalue.length; i++) {
//				if (i == 0)
//				{// 第一张图片保存在goodsmanage表，同时也保存attriproduction表
//					goodsManage.setPhotoPath(storepath+photoPath);
//				}
                    attrp = new AttriProduction();
                    attrp.setApid(serverService.getServerID("apid"));
                    attrp.setAttriname((colorcon == null || colorcon.equals("")) ? "颜色" : colorcon);
                    attrp.setAttrivalue(colorvalue[i].trim());
                    attrp.setImgurl((storepath + imgurls[i]).trim());
                    attrp.setType("1");
                    attrp.setGoodsid(goodsManage.getGoodsID());
                    beans.add(attrp);
                }
            }
            //保存电子秤相关
            if ("0".equals(goodsManage.getIsScale())) {
                scaleGoods.setSgID(serverService.getServerID("sgid"));
                scaleGoods.setCompanyID(account.getCompanyID());

                String phql = "select count(*) from ScaleGoods where companyID = ? ";
                int pcount = baseBeanService.getConutByByHqlAndParams(phql, new Object[]{account.getCompanyID()});
                if (pcount != 0) {
                    String sql = "select max(to_number(plu)) from dtScaleGoods where companyID= ?";
                    pcount = baseBeanService.getConutByBySqlAndParams(sql,
                            new Object[]{account.getCompanyID()});
                }
                //自定生成条码
                scaleGoods.setPlu(pcount + 1);
                DecimalFormat fofrm = new DecimalFormat("00000");
                String ap = fofrm.format(pcount + 1);
                scaleGoods.setAlternativeItemID(ap);//货号
                goodsManage.setBarCode(ap);
                scaleGoods.setGoodsID(goodsManage.getGoodsID());
                //scaleGoods.setUnitOfMeasureCode("");计价单位前台传


                beans.add(scaleGoods);
            }
        } else {

            //删除function
            String hqldelete = "delete from GoodFunction where goodsid = ?";
            hqls.add(hqldelete);


            String hqldelattr0 = "delete from AttriProduction where goodsid = ? and type = '0'";
            hqls.add(hqldelattr0);

            parms.add(goodsManage.getGoodsID());

            String[] appidarry = null;
            if (colorvalue == null || colorvalue.equals("")) {
                String hqldelattr1 = "delete from AttriProduction where goodsid = ? and type = '1'";
                hqls.add(hqldelattr1);

            } else {
                appidarry = attrproduct.getApid().split(",");
                String str = "";
                for (int i = 0; i < appidarry.length; i++) {
                    str += "'" + appidarry[i].trim() + "',";

                }
                str = str.substring(0, str.length() - 1);
                String hqldelattr2 = "delete from AttriProduction where goodsid = ? and type = '1' and apid not in(" + str + ")";
                hqls.add(hqldelattr2);


                String idphoto = request.getParameter("idphoto");
                String colorphoto = request.getParameter("colorphoto");


                String hqla = "from AttriProduction where apid = ?";
                if (photo != null) {
                    String[] idphotos = idphoto.split(",");
                    String[] colorphotos = colorphoto.split(",");

                    for (int i = 0; i < idphotos.length; i++) {
                        AttriProduction attps = (AttriProduction) baseBeanService.getBeanByHqlAndParams(hqla, new Object[]{idphotos[i].trim()});
                        if (attps != null) {
                            attps.setImgurl((storepath + imgurls[i]).trim());
                            attps.setAttriname((colorcon == null || colorcon.equals("")) ? "颜色" : colorcon);
                            beans.add(attps);
                        } else {
                            AttriProduction attrp = new AttriProduction();
                            attrp.setApid(serverService.getServerID("apid"));
                            attrp.setAttriname((colorcon == null || colorcon.equals("")) ? "颜色" : colorcon);
                            attrp.setAttrivalue(colorphotos[i].trim());
                            attrp.setImgurl((storepath + imgurls[i]).trim());
                            attrp.setType("1");
                            attrp.setGoodsid(goodsManage.getGoodsID());
                            beans.add(attrp);
                        }
                    }

                    for (int i = 0; i < colorvalue.length; i++) {
                        if (!Arrays.asList(idphotos).contains(appidarry[i].trim())) {
                            AttriProduction ats = (AttriProduction) baseBeanService.getBeanByHqlAndParams(hqla, new Object[]{appidarry[i].trim()});
                            if (ats != null) {
                                ats.setAttriname((colorcon == null || colorcon.equals("")) ? "颜色" : colorcon);
                                beans.add(ats);
                            }

                        }
                    }
                }


            }

            //保存电子秤相关
            if ("0".equals(goodsManage.getIsScale())) {

                ScaleGoods sg = (ScaleGoods) baseBeanService.getBeanByHqlAndParams("from ScaleGoods where goodsID = ?", new Object[]{goodsManage.getGoodsID()});
                if (sg != null) {
                    sg.setUnitOfMeasureCode(scaleGoods.getUnitOfMeasureCode());
                    beans.add(sg);
                } else {

                    scaleGoods.setSgID(serverService.getServerID("sgid"));
                    scaleGoods.setCompanyID(account.getCompanyID());

                    String phql = "select count(*) from ScaleGoods where companyID = ? ";
                    int pcount = baseBeanService.getConutByByHqlAndParams(phql, new Object[]{account.getCompanyID()});
                    if (pcount != 0) {
                        String sql = "select max(to_number(plu)) from dtScaleGoods where companyID= ?";
                        pcount = baseBeanService.getConutByBySqlAndParams(sql,
                                new Object[]{account.getCompanyID()});
                    }
                    //自定生成条码
                    scaleGoods.setPlu(pcount + 1);
                    DecimalFormat fofrm = new DecimalFormat("00000");
                    String ap = fofrm.format(pcount + 1);
                    scaleGoods.setAlternativeItemID(ap);//货号
                    goodsManage.setBarCode(ap);
                    scaleGoods.setGoodsID(goodsManage.getGoodsID());
                    //scaleGoods.setUnitOfMeasureCode("");计价单位前台传
                    beans.add(scaleGoods);
                }


            }


        }

        goodsManage.setCompanyID(account.getCompanyID());
        goodsManage.setGoodsState("00");


        //保存尺码
        String sizecon = request.getParameter("sizecon");
        String sizevalue = request.getParameter("sizevalue");
        String[] sizearray = null;
        if (sizevalue != null && !sizevalue.equals("")) {
            sizearray = sizevalue.split("##");
            AttriProduction attrp = null;
            for (int i = 0; i < sizearray.length; i++) {
                attrp = new AttriProduction();
                attrp.setApid(serverService.getServerID("apid"));
                attrp.setAttriname((sizecon == null || sizecon.equals("")) ? "尺码" : sizecon);
                attrp.setAttrivalue(sizearray[i]);
                attrp.setType("0");
                attrp.setGoodsid(goodsManage.getGoodsID());
                beans.add(attrp);
            }
        }


        //车辆信息
        if (goodsManage.getTypeID().indexOf("车") != -1) {
            if (carInformation != null) {
                if (carInformation.getCarID() == null || carInformation.getCarID().equals("")) {
                    carInformation.setCarID(serverService
                            .getServerID("carInformation"));
                    carInformation.setGoodsID(goodsManage.getGoodsID());
                    carInformation.setCompanyID(account.getCompanyID());
                }
            }

            //不知道为什么非要在物品表中存车辆信息，先暂时也给存上
            goodsManage.setManufacturers(carInformation.getCarFrameNum());
            goodsManage.setMnemonicCode(carInformation.getEngineNum());

            beans.add(carInformation);

        }
        beans.add(goodsManage);


        //xgb添加场地关联
        if (fs != null && fs.getSiteId() != null && !fs.getSiteId().equals("")) {
            FeeScale feescale = null;
            Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{account.getStaffID()});

            if (fs.getFeecID() != null && !fs.getFeecID().equals("")) {
                feescale = (FeeScale) baseBeanService.getBeanByHqlAndParams("from FeeScale where feecID=?", new Object[]{fs.getFeecID()});
                feescale.setSiteId(fs.getSiteId());
                feescale.setStaffID(staff.getStaffID());
                feescale.setStaffName(staff.getStaffName());
            } else {
                feescale = new FeeScale();
                feescale.setFeecID(serverService.getServerID("fs"));
                Calendar calendar = Calendar.getInstance();
                feescale.setChargeNumber(String.valueOf(calendar.getTime().getTime()));
                feescale.setSiteId(fs.getSiteId());
                feescale.setGoodsID(goodsManage.getGoodsID());
                feescale.setStaffID(staff.getStaffID());
                feescale.setStaffName(staff.getStaffName());
                feescale.setCompanyID(goodsManage.getCompanyID());
                feescale.setStartUsing("00");
            }
            beans.add(feescale);
        }


        //保存功能介绍
        GoodFunction goodFun = null;
        if (arurls != null) {
            for (int i = 0; i < arurls.length; i++) {
                goodFun = new GoodFunction();
                String url = saveContentToFile(arurls[i].trim());
                goodFun.setGfid(serverService.getServerID("gfid"));
                goodFun.setOrders(i + 1);
                goodFun.setUrl(url);
                goodFun.setName(arnames[i].trim());
                goodFun.setType("1");
                goodFun.setGoodsid(goodsManage.getGoodsID());
                beans.add(goodFun);
            }
        }
        //处理规格的
        if (goodsbarmap != null) {
            gmservice.addMoreBar(goodsbarmap, goodsManage, beans, hqls);
        }

        try {
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hqls.toArray(new String[]{}), parms.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "success";
    }


    /**
     * 保存文本编辑器内容
     *
     * @param content
     * @return
     */
    private String saveContentToFile(String content) {
        String id = RandomDatas.getUUID();
        String path = ServletActionContext.getServletContext().getRealPath("")
                + "/upload_files/goodDetail/";

        try {
            contentToFileService.saveContent(id,
                    content, path);

        } catch (IOException e) {
            e.printStackTrace();

        }

        return "/upload_files/goodDetail/" + id + UploadContentToFileService.suffix;
    }

    /**
     * 保存文本编辑器内容
     *
     * @param content
     * @return
     */
    private void saveContentToFile(String content, String path) {
        try {
            contentToFileService.saveContent(content, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据txt URL 获取内容
     *
     * @param filepath
     * @return
     */
    private String getContentFromFile(String filepath) {
        String path = ServletActionContext.getServletContext()
                .getRealPath("\\")
                + filepath;
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
     * 删除物品
     *
     * @return
     */
    public String deleteGoods() {
        String hql = "update GoodsManage set goodsState='01' where goodsID = ?";

        baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
                new String[]{hql}, new Object[]{goodsManage.getGoodsID()});

        return "success";
    }


    /**
     * 导出
     *
     * @return
     */
    public String showExcel() {
        excelStream = excelService.showExcel(GoodsManage.columnHeadings(),
                baseBeanService.getListByDC(getLists()));
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        CLogBook cLogBook = logBookService
                .saveCLogBook(null, "导出物品管理", account);
        baseBeanService.update(cLogBook);
        return "showexcel";
    }


    /**
     * 打印按照查询结构打印
     *
     * @return
     */
    public String printPrev() {
        list = baseBeanService.getListByDC(getLists());


        return "printprev";
    }


    /**
     * 上传文件
     *
     * @return
     */
    public String uploadFile() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyID = request.getParameter("companyID");
        String path = ServletActionContext.getRequest()
                .getSession().getServletContext().getRealPath("/");
        String jjPath = "";
        try {
            filePath = fileService
                    .savePhoto(path, fileFileName, file, companyID,
                            "gooddesign/"
                                    + Utilities.getDateString(
                                    new Date(), "yyyy-MM-dd"));
            jjPath = filePath.split("\\.")[0] + "small." + filePath.split("\\.")[1];
            ImageCut.scale(path + filePath, path + jjPath, 414, 413);
        } catch (Exception e) {
            System.out.println("保存上传图片失败");
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("filePath", jjPath);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();

        return "success";
    }

    /**
     * 判断条码是否存在
     *
     * @return
     */
    public String isExistsBarCode() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        int count = baseBeanService.getConutByBySqlAndParams("select count(*) from dtgoodsmanage g where g.barcode = ? and g.goodsstate = ? and g.companyID = ?", new Object[]{barCode, "00", account.getCompanyID()});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count", count);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public GoodsManage getGoodsManage() {
        return goodsManage;
    }

    public void setGoodsManage(GoodsManage goodsManage) {
        this.goodsManage = goodsManage;
    }


    public GoodFunction getGoodFunction() {
        return goodFunction;
    }

    public void setGoodFunction(GoodFunction goodFunction) {
        this.goodFunction = goodFunction;
    }


    public Map<Integer, String> getMaplist() {
        return maplist;
    }

    public void setMaplist(Map<Integer, String> maplist) {
        this.maplist = maplist;
    }

    public String getAeptype() {
        return aeptype;
    }

    public void setAeptype(String aeptype) {
        this.aeptype = aeptype;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    public List<BaseBean> getFunctionlist() {
        return functionlist;
    }

    public void setFunctionlist(List<BaseBean> functionlist) {
        this.functionlist = functionlist;
    }

    public void setList(List<BaseBean> list) {
        this.list = list;
    }

    public List<BaseBean> getList() {
        return list;
    }

    public CarInformation getCarInformation() {
        return carInformation;
    }

    public void setCarInformation(CarInformation carInformation) {
        this.carInformation = carInformation;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFiveClear() {
        return fiveClear;
    }

    public void setFiveClear(String fiveClear) {
        this.fiveClear = fiveClear;
    }

    public AttriProduction getAttrproduct() {
        return attrproduct;
    }

    public void setAttrproduct(AttriProduction attrproduct) {
        this.attrproduct = attrproduct;
    }

    public File[] getPhoto() {
        return photo;
    }

    public void setPhoto(File[] photo) {
        this.photo = photo;
    }

    public String[] getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String[] photoFileName) {
        this.photoFileName = photoFileName;
    }

    public String[] getColorvalue() {
        return colorvalue;
    }

    public void setColorvalue(String[] colorvalue) {
        this.colorvalue = colorvalue;
    }

    public List<BaseBean> getListsize() {
        return listsize;
    }

    public void setListsize(List<BaseBean> listsize) {
        this.listsize = listsize;
    }

    public List<BaseBean> getListcolor() {
        return listcolor;
    }

    public void setListcolor(List<BaseBean> listcolor) {
        this.listcolor = listcolor;
    }

    public String[] getImgurl() {
        return imgurl;
    }

    public void setImgurl(String[] imgurl) {
        this.imgurl = imgurl;
    }

    public VenueInformation getVf() {
        return vf;
    }

    public void setVf(VenueInformation vf) {
        this.vf = vf;
    }

    public FeeScale getFs() {
        return fs;
    }

    public void setFs(FeeScale fs) {
        this.fs = fs;
    }

    public List<CCode> getVariablelist() {
        return variablelist;
    }

    public void setVariablelist(List<CCode> variablelist) {
        this.variablelist = variablelist;
    }

    public ScaleGoods getScaleGoods() {
        return scaleGoods;
    }

    public void setScaleGoods(ScaleGoods scaleGoods) {
        this.scaleGoods = scaleGoods;
    }

    public Map<String, GoodsBarcode> getGoodsbarmap() {
        return goodsbarmap;
    }

    public void setGoodsbarmap(Map<String, GoodsBarcode> goodsbarmap) {
        this.goodsbarmap = goodsbarmap;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
