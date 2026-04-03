package com.tiantai.wfj.front;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.wfj.bo.*;
import com.tiantai.wfj.service.ProductlaunchService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.Company;
import hy.ea.bo.DrivingSchool.SchProCategory;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.company.ScaleGoods;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.BenDis.SetSubsidize;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.*;
import hy.ea.bo.office.FeeScale;
import hy.ea.bo.office.VenueInformation;
import hy.ea.bo.production.*;
import hy.ea.bo.production.view.UtboundOrderVo;
import hy.ea.company.service.DepotManageService;
import hy.ea.office.service.CarManageService;
import hy.ea.service.UpLoadFileService;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.*;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*import hy.ea.util.baidu.ImgDistinguish;
import hy.ea.util.baidu.OperateImage;*/

//import hy.ea.util.baidu.ImgDistinguish;
//import hy.ea.util.baidu.OperateImage;

@Controller
@Scope("prototype")
public class ProductsLaunchAction extends ActionSupport {
    private Logger logger = LoggerFactory.getLogger(ProductsLaunchAction.class);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private UpLoadFileService fileService;
    @Resource
    private UploadContentToFileService contentToFileService;
    @Resource
    private ProductlaunchService productlaunchService;
    @Resource
    private DepotManageService depotManageService;

    private HttpServletRequest request;
    @Resource
    private CarManageService cmService;
    private FeeScale feeScale;
    /**
     * 生产跟踪
     */
    private PTrack ptrack;
    private DCheck dcheck;
    private Object result;

    private GoodsManage goodsManage;// 物品
    private ProductPackaging productPackaging;// 产品
    private Promotion promotion;//产品促销品关系表
    private ProSetup setup;// 佣金
    private String goodsId;// 物品id
    private String ppId;// 产品id
    private String apId;// 物品功能id
    private String colorvalue;// 物品颜色
    private File[] photo;
    private String[] photoFileName;
    private File[] pic;// 产品描述图片
    private String[] picFileName;
    private String sizevalue;// 尺码
    private String htl;// 物品功能
    private String[] functionList;
    private String flag;// 判断标识
    private PageForm pageForm;
    private List<BaseBean> arrlist;// 物品图片列表
    private String sort;// 图片顺序
    private List<BaseBean> prolist;// 产品
    private String barcode;// 以下是条码信息
    private String interval;
    private String name;
    private Map<String, Object> map;
    private List<BaseBean> list;
    private String count;//数量
    private Map<String, ProSetupSub> pssMap;
    private String ppID;
    private TEshopCusCom tcc;
    private String sccId;
    private String falg;
    private String pseudoCompanyName;
    private Object logpath;
    private String type;                        //判断单据是从什么类型过来的
    private UtboundOrderVo utboundOrderVo;
    private List<SchProCategory> typeList;
    private String delList;
    private String attrinames;//自定义
    private String attrinamec;//自定义
    private String search;
    private String unitOfMeasureCode;
    private Map<String, ScaleWeight> proWeight;//用于自动贩卖机传递传递秤盘重量

    private CAccount account;
    private String companyId;// 公司id
    private String user;// 用户

    private productDepot productDepot;  //商品库房关联表

    private int pageNumber;
    private String industryId;
    private List<BaseBean> advlist;

    /**
     * 跳转产品发布页面
     *
     * @return
     */
    public String toProductsLaunch() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String code = request.getParameter("barcode");
        String typez = request.getParameter("typez");
        String audit = request.getParameter("audit");
        companyId = getCompanyId();
        if (companyId == null || companyId.equals("")) {
            return "login";
        }
        //查询产品行业类别
        String hysql = "select cc.industrytype from dtcontactcompany cc,dt_ccom_com ccc where cc.ccompanyid=ccc.ccompany_id and ccc.compnay_id=?";
        Object hy = baseBeanService.getObjectBySqlAndParams(hysql, new Object[]{companyId});

        SetSubsidize setSubsidize = (SetSubsidize) baseBeanService.getBeanByHqlAndParams("from SetSubsidize where gtid=? and stutas=?", new Object[]{hy, "01"});

        if (setSubsidize != null) {
            request.setAttribute("totalPct", new BigDecimal(setSubsidize.getXfPct()).divide(new BigDecimal(100)).add(BigDecimal.ONE));
        } else {
            request.setAttribute("totalPct", 0);
        }
        //代理类别查询
        List<BaseBean> dlList = baseBeanService.getListBeanByHqlAndParams
                ("from ProductPackaging where type=? and goodsname<>? order by sorting",
                        new Object[]{"佣金分配代理类别", "佣金分配代理类别"});
        request.setAttribute("dlList", dlList);
        StringBuffer sql = new StringBuffer();
        List<Object> parame = null;
        parame = new ArrayList<>();
        sql.append("with a as(select gm.goodsid,pp.ppid,pp.goodsname,pp.tradeID,pp.tradeCode,pp.tradename,pp.invNum,ps.com_id,ps.RE_PRICE,");
        sql.append(" ps.BROKERAGE,ps.suid,ps.TZ_TYPE,gm.categoryid,gm.categoryname,gm.isscale,gm.brand,s.unitofmeasurecode,");
        sql.append(" gm.barcode,pp.depotID,pp.depotName,pp.depotCoding,pp.stanpro,pp.singleWeight,PP.VARIABLEID");
        sql.append(" from dtGoodsManage gm left join dt_productpackaging pp on gm.goodsid = pp.goodsid");
        sql.append(" left join dt_pro_setup ps on pp.ppid = ps.ppid");
        sql.append(" left join dtscalegoods s on s.goodsid = gm.goodsid");
        sql.append(" where pp.companyid = ?");
        parame.add(companyId);
        if (code != null && !code.equals("") && (ppId == null || ppId.equals(""))) {
            sql.append(" and pp.barcode = ?)");
            parame.add(code);
        } else {
            sql.append(" and pp.ppid = ?)");
            parame.add(ppId);
        }
        sql.append(" select a.goodsid,a.ppid,a.goodsname,a.tradeID,a.tradeCode,a.tradename,a.invNum,a.com_id,a.RE_PRICE,a.BROKERAGE,");
        sql.append(" wmsys.wm_concat(ap.attrivalue),a.suid,a.tz_type,a.categoryid,a.categoryname,a.isscale,a.brand,a.unitofmeasurecode,");
        sql.append(" a.barcode,a.depotID,a.depotName,a.depotCoding,a.stanpro,a.singleWeight,a.VARIABLEID");
        sql.append(" from a left join dtattriproduction ap on a.goodsid = ap.goodsid");
        sql.append(" group by a.goodsid,a.ppid,a.goodsname,a.tradeID,a.tradeCode,a.tradename,a.invNum,a.com_id,");
        sql.append(" a.RE_PRICE,a.BROKERAGE,a.suid,a.tz_type,a.categoryid,a.categoryname,a.isscale,a.brand,a.unitofmeasurecode,");
        sql.append(" a.barcode,a.depotID,a.depotName,a.depotCoding,a.stanpro,a.singleWeight,a.VARIABLEID");
        prolist = baseBeanService.getListBeanBySqlAndParams(sql.toString(), parame.toArray());

        if (goodsId != null && !goodsId.equals("")) {
            arrlist = baseBeanService
                    .getListBeanByHqlAndParams(
                            "from AttriProduction where goodsid=? and type='2' order by sort",
                            new Object[]{goodsId});
        }
        if (prolist != null && prolist.size() > 0) {
            Object o = prolist.get(0);
            Object[] obj = (Object[]) o;
            if (obj[11] != null && !obj[11].equals("")) {
                String suid = obj[11].toString();
                StringBuilder psssql = new StringBuilder("select pss.susid,p.goodsname,pss.amount,pss.suskey,pss.type_ppid");
                psssql.append(" from dt_pro_setup s, dt_pro_setup_sub pss, dt_productpackaging p");
                psssql.append(" where s.suid = pss.suid");
                psssql.append(" and pss.type_ppid = p.ppid");
                psssql.append(" and s.suid=?");
                List<Object> dlyjlist = baseBeanService.getListBeanBySqlAndParams(psssql.toString(), new Object[]{suid});
                request.setAttribute("dlyj", dlyjlist);
            }
        }
        String hql = "from GoodFunction where goodsid= ?";
        if (goodsId != null && !goodsId.equals("")) {
            GoodFunction goodFun = (GoodFunction) baseBeanService
                    .getBeanByHqlAndParams(hql, new Object[]{goodsId});
            if (goodFun != null) {
                String str = getContentFromFile(goodFun.getUrl());
                if (str.length() > 0) {
                    functionList = str.split("#z");
                }
            }
            if ("tc".equals(typez)) {
                feeScale = (FeeScale) baseBeanService.getBeanByHqlAndParams("from FeeScale where goodsID = ?", new Object[]{goodsId});
                if ("tc".equals(audit)) {
                    VenueInformation vf = (VenueInformation) baseBeanService.getBeanByHqlAndParams("from VenueInformation where siteId = ?", new Object[]{feeScale.getSiteId()});
                    String totalPct = cmService.getTotalPct(companyId);
                    request.setAttribute("vf", vf);
                    request.setAttribute("totalPct", totalPct);
                }
            }
        }
        if (ppId != null && !ppId.equals("")) {
            String countsql = "select count(*) from dt_promotion p where p.ppId=?";
            Integer count = baseBeanService.getConutByBySqlAndParams(countsql, new Object[]{ppId});
            request.setAttribute("ptcount", count);
        }
        String ret = request.getParameter("ret");
        request.setAttribute("ret", ret);
        request.setAttribute("typez", typez);
        if ("tc".equals(audit)) {
            return "feeaudit";
        }
        return "add";
    }

    /**
     * 跳转产品发布页面
     *
     * @return
     */
    public String toProducts() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        String code = request.getParameter("barcode");
        String sccid = request.getParameter("sccid");
        TEshopCusCom cuscom = null;
        TEshopCustomer customer = null;
        if (sccid != null && !sccid.equals("")) {
            cuscom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});
        } else {
            customer = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
            if (customer == null) {
                String url = request.getHeader("Referer");
                session.setAttribute("url", url);
                return "login";
            }
            cuscom = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
        }
        sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cuscom);
        TEshopCustomer cus = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams(
                "from TEshopCustomer where account=? AND logOff=0", new Object[]{cuscom.getAccount()});
        sw.setObject(session, SessionWrap.KEY_CUSTOMER, cus);

        if (user == null || companyId == null || user.length() < 0 || companyId.length() < 0) {
            getAccountSession(request);
        }

        TEshopCusCom tec = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where companyId=?", new Object[]{companyId});

        if (goodsId != null && !goodsId.equals("")) {
            arrlist = baseBeanService
                    .getListBeanByHqlAndParams(
                            "from AttriProduction where goodsid=? and type='2' order by sort",
                            new Object[]{goodsId});
        }

        String hql = "from GoodFunction where goodsid= ?";
        if (goodsId != null && !goodsId.equals("")) {
            GoodFunction goodFun = (GoodFunction) baseBeanService
                    .getBeanByHqlAndParams(hql, new Object[]{goodsId});
            if (goodFun != null) {
                String str = getContentFromFile(goodFun.getUrl());
                if (str.length() > 0) {
                    functionList = str.split("#z");
                }
            }
        }
        if (ppId != null && !ppId.equals("")) {
            String countsql = "select count(*) from dt_promotion p where p.ppId=?";
            Integer count = baseBeanService.getConutByBySqlAndParams(countsql, new Object[]{ppId});
            request.setAttribute("ptcount", count);
        }
        String ret = request.getParameter("ret");
        request.setAttribute("ret", ret);
        return "addproduct";
    }

    /**
     * 通用物体和场景识别
     */
   /* public String AjaxImg() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String imgUrl = request.getParameter("imgUrl");
        String filpath = ServletActionContext.getServletContext().getRealPath("/");
        result = ImgDistinguish.advancedGeneral(filpath+imgUrl);
        return "success";
    }*/

    /**
     * 识别字体
     */
    /*public String AjaxImgToText() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String filpath = ServletActionContext.getServletContext().getRealPath("/");
        String imgUrl = request.getParameter("imgUrl");
        result = ImgDistinguish.accurateBasic(filpath+imgUrl);
        return "success";
    }*/

    /**
     * 图片截取
     */
    /*public String AjaxToImg() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String filpath = ServletActionContext.getServletContext().getRealPath("/");
        String imgUrl = request.getParameter("imgUrl");
        String width = request.getParameter("width");
        String height = request.getParameter("height");
        String top = request.getParameter("top");
        String left = request.getParameter("left");
        OperateImage operateImage = new OperateImage(Integer.parseInt(top), Integer.parseInt(left),Integer.parseInt(width), Integer.parseInt(height));
        operateImage.setSrcpath(filpath+imgUrl);
        operateImage.setSubpath(filpath+imgUrl);
        try {
            operateImage.cut();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }*/

    // 删除图片
    public String delPhoto() {
        List<Object[]> paramslist = new ArrayList<Object[]>();
        List<BaseBean> beans = new ArrayList<BaseBean>();
        String filpath = ServletActionContext.getServletContext().getRealPath(
                "/");
        String sql = "delete dtAttriProduction where apid=?";
        if (apId != null && !apId.equals("")) {
            AttriProduction ap = (AttriProduction) baseBeanService
                    .getBeanByHqlAndParams("from AttriProduction where apid=?",
                            new Object[]{apId});
            if (ap != null) {
                paramslist.add(new Object[]{apId});
                //如果删除第一张，则把第二张图片换成产品主图。
                if (sort != null && sort.equals("1")) {
                    String hql = "select p from ProductPackaging p,GoodsManage g where p.goodsID=g.goodsID and g.goodsID=?";
                    GoodsManage g = (GoodsManage) baseBeanService.getBeanByHqlAndParams("from GoodsManage where goodsID=?", new Object[]{ap.getGoodsid()});
                    ProductPackaging p = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ap.getGoodsid()});
                    List<BaseBean> aplist = baseBeanService.getListBeanByHqlAndParams("from AttriProduction where goodsid=? ", new Object[]{ap.getGoodsid()});
                    if (!aplist.isEmpty() && aplist.size() >= 2) {
                        AttriProduction a = (AttriProduction) aplist.get(1);
                        if (p != null) {
                            System.out.print(a.getImgurl());
                            p.setImage(a.getImgurl());
                            g.setPhotoPath(a.getImgurl());
                            beans.add(p);
                            beans.add(g);
                        }
                    }
                }
                FileUtil.delete(filpath + ap.getImgurl());// 删除本地图片
                baseBeanService.executeSqlsByParmsList(beans,
                        new String[]{sql}, paramslist);
                JSONObject json = new JSONObject();
                json.accumulate("flag", "1");
                result = json.toString();

            } else {
                String temp = apId.substring(apId.indexOf("upload_files"));
                FileUtil.delete(filpath + temp);// 删除本地图片
                JSONObject json = new JSONObject();
                json.accumulate("flag", "2");
                result = json.toString();
            }
        }
        return "success";
    }

    //ajax 上传图片
    @SuppressWarnings("unchecked")
    public String ajaxUploadPic() {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select gm.goodsid,pp.ppid,pp.goodsname,pp.tradeID,pp.tradeCode,pp.tradename,pp.quantity,pp.companyid");
        sql.append(" from dt_productpackaging pp,dtgoodsmanage gm");
        sql.append(" where pp.goodsid=gm.goodsid and pp.temporary=? and pp.staffid=?");
        List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql.toString(),
                new Object[]{"0", cus != null ? cus.getStaffid() : ""});
        GoodsManage gm = null;
        if (list != null && list.size() > 0) {
            Object obj = list.get(0);
            Object[] o = (Object[]) obj;
            gm = (GoodsManage) baseBeanService.getBeanByHqlAndParams("from GoodsManage where goodsID=?",
                    new Object[]{o[0].toString()});
        }
        // 物品设计
        if (gm == null) {
            gm = new GoodsManage();
            gm.setGoodsID(serverService.getServerID("goodsID"));
        }
        goodsId = gm.getGoodsID();
        // 编号处理
        String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in (select c.companyID from Company c where c.groupCompanySn=?)  and vt.typeID=? ";
        if (companyId != null && !companyId.equals("")) {
            gm.setTypeID("其他");
            Company company = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID= ?",
                    new Object[]{companyId});
            Object[] params = {company.getGroupCompanySn(), gm.getTypeID()};
            // 获取拼音码
            String pinyin = ToChineseFirstLetter.getFirstLetter(gm.getTypeID());
            String Upstr = pinyin.toUpperCase();// 转换为大写
            int goodscodingnum = baseBeanService.getConutByByHqlAndParams(hql, params);
            DecimalFormat form = new DecimalFormat("000000");
            String ss = form.format(goodscodingnum + 1);
            gm.setGoodsName(goodsManage.getGoodsName());
            gm.setGoodsCoding(Upstr + "_" + ss);
            gm.setFiveClear("4");
            gm.setCompanyID(companyId);
            gm.setGoodsState("00");
            gm.setCreatedate(new Date());
            gm.setTradeID(goodsManage.getTradeID());
            gm.setTradeName(goodsManage.getTradeName());
            gm.setTradeCode(goodsManage.getTradeCode());
            gm.setBarCode(goodsManage.getBarCode());// 条码
            // 保存物品主图
            if (photo != null && photo.length > 0) {
                String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
                String photopath = "";
                AttriProduction ap = null;
                List<BaseBean> aplist = new ArrayList<BaseBean>();
                aplist = baseBeanService
                        .getListBeanByHqlAndParams(
                                "from AttriProduction where goodsid=? and type=? order by sort",
                                new Object[]{goodsId, "2"});
                for (int i = 0; i < photo.length; i++) {
                    photopath = fileService.savePhoto(path, photoFileName[i], photo[i], companyId,
                            "/gooddesign/" + Utilities.getDateString(new Date(), "yyyy-MM-dd"));
                    if (i == 0) {// 第一张图片保存在goodsmanage表，同时也保存attriproduction表
                        if (gm.getPhotoPath() == null || gm.getPhotoPath().equals("")) {
                            gm.setPhotoPath(photopath);
                        }
                    }
                    if (aplist != null && aplist.size() > 0) {
                        ap = (AttriProduction) aplist.get(aplist.size() - 1);
                        ap.setApkey(null);
                        ap.setApid(serverService.getServerID("apid"));
                        ap.setImgurl(photopath);
                        ap.setSort(ap.getSort() + i + 1);
                    } else {
                        ap = new AttriProduction();
                        ap.setApid(serverService.getServerID("apid"));
                        ap.setImgurl(photopath);
                        ap.setSort(i + 1);
                        ap.setGoodsid(goodsId);
                        ap.setType("2");
                    }
                    beans.add(ap);
                }

                beans.add(gm);
            }
            // 产品设计
            ProductPackaging pp = null;
            if (gm != null && !gm.getGoodsID().equals("")) {
                pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams("from ProductPackaging where goodsID=?",
                        new Object[]{gm.getGoodsID()});
            }
            if (pp == null) {
                pp = new ProductPackaging();
                pp.setPpID(serverService.getServerID("p"));
            }

            String hqlcode = "from CCode where codeID = ?";
            CCode code = (CCode) baseBeanService.getBeanByHqlAndParams(hqlcode, new Object[]{gm.getTradeID()});
            String codes = "";
            if (code != null) {
                codes = code.getCodeSn();
            }
            pp.setProductCode(productlaunchService.generateProductCode(codes, ""));
            pp.setGoodsID(gm.getGoodsID());
            pp.setGoodsName(gm.getGoodsName());
            pp.setImage(gm.getPhotoPath());
            pp.setTradeCode(goodsManage.getTradeCode());
            pp.setTradeName(goodsManage.getTradeName());
            pp.setTradeID(goodsManage.getTradeID());
            pp.setType("物品类别暂定");
            if (flag != null && flag.equals("tocang")) {
                pp.setShowweixin("00");
            } else {
                pp.setShowweixin("01");
            }
            pp.setDelStatus("00");
            pp.setProductstate("00");
            pp.setFiveClear("4");
            pp.setField("01");
            pp.setCompanyID(companyId);
            pp.setPackagingDate(new Date());
            pp.setQuantity(productPackaging.getQuantity());
            pp.setStaffID(cus != null ? cus.getStaffid() : "");// 责任人
            pp.setAmendStaffID(cus != null ? cus.getStaffid() : "");
            pp.setTemporary("0");
            pp.setQualified("1");
            beans.add(pp);
        }
        String boo = "1";
        JSONObject json = new JSONObject();
        try {
            baseBeanService.executeHqlsByParamsList(beans, null, null);
        } catch (Exception e) {
            logger.error("保存图片失败");
            boo = "0";
        }
        json.accumulate("boo", boo);
        result = json.toString();
        return "success";
    }

    //产品管理
    public String productsManage() {
        HttpServletRequest request = ServletActionContext.getRequest();

        if (user != null && !user.equals("")) {
            companyId = productlaunchService.productsManage(user.trim());

            String ret = request.getParameter("ret");
            request.setAttribute("ret", ret);
        } else if (companyId == null || companyId.equals("")) {
            companyId = getCompanyId();
        } else {
            getAccountSession(request);
        }
        return "productsmanage";
    }

    /**
     * 移动端产品库存列表
     *
     * @return
     */
    public String ProductManageMobileList() {
        companyId = getCompanyId();
        Map<String, Object> map = new HashMap<>();
        request = ServletActionContext.getRequest();
        String pname = request.getParameter("pname");
        String depotid = request.getParameter("depotid");
        String hgcode = request.getParameter("hgcode");
        if (hgcode == null || hgcode.equals("")) {
            hgcode = CookieUtil.getCookieValue("hgcode", request);
        }
        if (companyId == null || companyId.equals("")) {
            map.put("flag", 1);
            map.put("error", 0);
            map.put("meg", "登录信息错误");
        } else {
            map = productlaunchService.ProductManageMobilePageForm(null != pageForm ? pageForm.getPageNumber() : 1, pname, depotid, hgcode, companyId);
        }
        JSONObject json = JSONObject.fromObject(map);
        result = json.toString();
        return "success";
    }

    //ajax加载产品
    public String ajaxProducts() {
        if (companyId == null || companyId.equals("")) {
            request  = ServletActionContext.getRequest();
            companyId = CookieUtil.getCookieValue("comID", request);
            if(companyId == null || companyId.equals("")){
                companyId=getCompanyId();
            }
        }
        if (flag != null && !flag.equals("")) {
            pageForm = productlaunchService.productsPageForm
                    (flag.trim(), null != pageForm ? pageForm.getPageNumber() : 1, search, companyId);
            JSONObject json = new JSONObject();
            json.accumulate("pageForm", pageForm);
            json.accumulate("flag", flag);
            result = json.toString();
        }
        return "success";
    }

    //上架,下架
    public String upOrdown() {
        JSONObject json = new JSONObject();
        if (ppId != null && !ppId.equals("")) {
            if (flag != null && !flag.equals("")) {
                if (productlaunchService.upOrdown(ppId.trim(), flag.trim())) {
                    json.accumulate("flag", "1");
                    result = json.toString();
                }
            }
        }
        return "success";
    }

    /**
     * 促销套餐详情
     */
    public String PromotionsDetail() {
        ActionContext ctx = ActionContext.getContext();
        HttpServletRequest request = (HttpServletRequest) ctx
                .get(ServletActionContext.HTTP_REQUEST);
        if (companyId != null && !companyId.equals("")) {
            if (ppId != null && !ppId.equals("")) {
                List<Object> list = new ArrayList<Object>();
                list = productlaunchService.PromotionsDetail(companyId.trim(), ppId.trim());
                request.setAttribute("prolist", list);
            }
        }
        return "promotionDetail";
    }

    /**
     * 验证商品名称
     */
    public String checkProName() {
        String s = "0";
        if (productlaunchService.checkProName(name.trim(), companyId.trim())) {
            s = "1";
        }
        JSONObject json = new JSONObject();
        json.accumulate("s", s);
        result = json.toString();
        return "success";
    }

    /**
     * 产品规格颜色
     *
     * @return
     * @desc ppId 产品id
     */
    public String getAttr() {
        if (ppId != null && ppId.length() > 0) {
            List<Object> list = productlaunchService.getAttr(ppId.trim());
            int count = baseBeanService.getConutByBySqlAndParams("select count(*) from dtattriproduction ap where ap.goodsID=? and (ap.type=? or ap.type=?)", new Object[]{goodsId, '0', '1'});
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("attrlist", list);
            map.put("count", count);
            JSONObject json = JSONObject.fromObject(map);
            result = json.toString();
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
            contentToFileService.saveContent(id, content, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/upload_files/goodDetail/" + id
                + UploadContentToFileService.suffix;
    }

    /**
     * 根据txt URL 获取内容
     *
     * @return
     * @desc url
     */
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
     * @return 返回的集合
     * @desc ppid:产品id
     * @Title: 查询
     * @Description: 查询产品及其下的促销品
     */
    public String productInquiry() {
        map = productlaunchService.productInquiry(this
                .getProductPackaging().getPpID());
        return "productInquiry";
    }

    /**
     * @return 返回的集合
     * @desc ppid:产品id
     * @Title: 查询
     * @Description: 查询产品
     */
    public String queryPromotionProduct() {
        map = productlaunchService.productInquiry(this
                .getProductPackaging().getPpID());

        list = productlaunchService.queryPromotionProduct(this.getPpId());

        return "queryPromotionProduct";
    }

    /**
     * @return 返回的集合
     * @desc pageNumber:当前页,pageSize:每页显示条数,goodsName:查询条件
     * @Title: 模糊查询加分页
     * @Description: 查询产品及其下的促销品
     */
    public String allGiftsProducts() {

        if (this.getProductPackaging().getGoodsName() != null
                && !this.getProductPackaging().getGoodsName().equals("")) {
            try {
                this.getProductPackaging().setGoodsName(
                        URLDecoder.decode(this.getProductPackaging()
                                .getGoodsName(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        pageForm = productlaunchService.allGiftsProducts(this.getPageForm()
                .getPageNumber(), this.getPageForm().getPageSize(), this
                .getProductPackaging().getGoodsName(), promotion.getPtppId());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", pageForm);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();

        return "success";
    }

    /**
     * @return 无返回值
     * @desc promotions:促销品数据
     * @Title: 添加促销品
     * @Description: 为产品添加促销品套餐
     */
    public String addSalesPromotion() {
        Date date = new Date();
        this.getPromotion().setPromotionDate(date);
        productlaunchService.save(this.getPromotion());

        return "addSalesPromotion";

    }

    /**
     * @param req
     * @return
     * @Title: 提炼重复的代码
     * @Description: 根据系统登录帐号查询该公司未分级帐号
     */
    private CAccount getAccountSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        CAccount ca = (CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        String hql = "select t from CAccount c,TEshopCusCom t where c.companyID=t.companyId and t.acquiesce=? and c.companyID=?";
        TEshopCusCom tec = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{"01", ca != null ? ca.getCompanyID() : ""});
        user = (tec != null ? tec.getAccount() : "");
        companyId = (ca != null ? ca.getCompanyID() : "");
        req.setAttribute("sys", "sys");
        return ca;
    }

    /**
     * @param pp    产品
     * @param beans 改动数据集合
     * @Title: 提炼重复的代码
     * @Description: 产品佣金设置
     */
    private void setsetup(ProductPackaging pp, List<BaseBean> beans) {
        String suid = null;
        ProSetup ps = (ProSetup) baseBeanService.getBeanByHqlAndParams(
                "from ProSetup where ppid=?",
                new Object[]{productPackaging.getPpID()});


        BigDecimal reprice = new BigDecimal(setup.getRePrice());
        BigDecimal efprice = new BigDecimal(setup.getRePrice());
        BigDecimal brokerage = new BigDecimal(setup.getBrokerage());

        BigDecimal dlsum = new BigDecimal(0);
        if (ps == null) {
            // 佣金设计
            ps = new ProSetup();
            ps.setSuid(serverService.getServerID("setup"));
            ps.setComId(companyId);
            ps.setPpid(pp.getPpID());

        }
        if (ps.getState() == null || "".equals(ps.getState())) {
            ps.setState("00");//给状态赋初始值:00
        }

        suid = ps.getSuid();
        ps.setPpname(pp.getGoodsName());
        ps.setRePrice(setup.getRePrice());
        ps.setBrokerage(setup.getBrokerage());
        ps.setTzType(setup.getTzType());
        ps.setSjdate(new Date());
        List<ProSetupSub> pssList = new ArrayList<ProSetupSub>();
        //代理商佣金设置
        if (pssMap != null) {
            BigDecimal dl = null;
            for (ProSetupSub pss : pssMap.values()) {
                ProSetupSub pross = null;
                if (pss.getSuskey() != null) {
                    pross = (ProSetupSub) baseBeanService.getBeanByHqlAndParams(
                            "from ProSetupSub where suskey=?",
                            new Object[]{pss.getSuskey()});
                }
                if (pross == null) {
                    pross = new ProSetupSub();
                    pross.setSusid(serverService.getServerID("prosetupsub"));
                }
                dl = new BigDecimal(pss.getAmount());
                dlsum = dlsum.add(dl);
                pross.setAmount(dl.toString());
                pross.setTypePpid(pss.getTypePpid());
                pross.setPpid(pp.getPpID());
                pross.setSjdate(new Date());
                pross.setSuid(suid);
                beans.add(pross);
                pssList.add(pross);
            }
        }

        efprice = reprice.subtract(brokerage).subtract(dlsum);
        ps.setEfPrice(efprice.toString());
        ps.setProxyprice(dlsum.toString());
        pp.setPrice(ps.getEfPrice());
        pp.setMoney(ps.getEfPrice());
        productlaunchService.savePssb(ps, pssList, beans);
        beans.add(ps);
        beans.add(pp);

    }

    /**
     * pc端二维码设置佣金
     *
     * @return
     */
    public String productserweima() {


        List<BaseBean> beans = new ArrayList<BaseBean>();
        CAccount account = (CAccount) ActionContext.getContext().getSession()
                .get("account");
        if (account == null) {
            return "no_login";
        }

        companyId = account.getCompanyID();
        System.out.print(companyId);


        String hql2 = " from TEshopCusCom where  companyId=?";
        tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{companyId});
        sccId = tcc.getSccId();
        beans.add(tcc);

        // 物品设计
        if (productPackaging.getPpID() == null || productPackaging.getPpID().equals("")) {
            GoodsManage gm = new GoodsManage();
            gm.setGoodsID(serverService.getServerID("goodsID"));
            goodsId = gm.getGoodsID();
            // 编号处理
            String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in (select c.companyID from Company c where c.groupCompanySn=?)  and vt.typeID=? ";
            if (companyId != null && !companyId.equals("")) {
                gm.setTypeID("扫码收款");
                Company company = (Company) baseBeanService
                        .getBeanByHqlAndParams(
                                "from Company where companyID= ?",
                                new Object[]{companyId});
                Object[] params = {company.getGroupCompanySn(), gm.getTypeID()};
                // 获取拼音码
                String pinyin = ToChineseFirstLetter.getFirstLetter(gm
                        .getTypeID());
                String Upstr = pinyin.toUpperCase();// 转换为大写
                int goodscodingnum = baseBeanService.getConutByByHqlAndParams(
                        hql, params);
                DecimalFormat form = new DecimalFormat("000000");
                String ss = form.format(goodscodingnum + 1);
                gm.setGoodsName("扫码收款");
                gm.setGoodsCoding(Upstr + "_" + ss);
                gm.setFiveClear("4");
                gm.setCompanyID(companyId);
                gm.setGoodsState("00");
                gm.setCreatedate(new Date());
                gm.setTradeID("扫码收款");
                gm.setTradeName("扫码收款");
                gm.setTradeCode("扫码收款");
                String jjPath = ("/images/ea/finance/NewPhoneOrders/sellerOrder/1.png");
                gm.setPhotoPath(jjPath);
                AttriProduction attrp = new AttriProduction();
                attrp.setApid(serverService.getServerID("apid"));
                attrp.setType("2");
                attrp.setImgurl(jjPath);
                attrp.setGoodsid(gm.getGoodsID());
                //attrp.setSort(i + 1);
                beans.add(attrp);

                beans.add(gm);

                // 产品设计
                if (gm != null && !gm.getGoodsID().equals("")) {
                    ProductPackaging pp = new ProductPackaging();
                    pp.setPpID(serverService.getServerID("p"));
                    ppId = pp.getPpID();
                    String hqlcode = "from CCode where codeID = ?";
                    CCode code = (CCode) baseBeanService.getBeanByHqlAndParams(
                            hqlcode, new Object[]{gm.getTradeID()});
                    String codes = "";
                    if (code != null) {
                        codes = code.getCodeSn();
                    }
                    pp.setProductCode(productlaunchService.generateProductCode(codes, ""));
                    pp.setGoodsID(gm.getGoodsID());
                    pp.setGoodsName(gm.getGoodsName());
                    pp.setImage(gm.getPhotoPath());
                    pp.setTradeCode(gm.getTradeCode());
                    pp.setTradeID(gm.getTradeID());
                    pp.setTradeName(gm.getTradeName());
                    //pp.setDelStatus("01");
                    pp.setType("扫码收款");
                    if (flag != null && flag.equals("tocang")) {

                        pp.setShowweixin("01");
                    }
                    pp.setDelStatus("01");
                    pp.setProductstate("00");
                    pp.setFiveClear("4");
                    pp.setCompanyID(companyId);
                    pp.setPackagingDate(new Date());
                    pp.setYjstatus("01");// 已设置佣金
                    pp.setField("01");
                    pp.setQuantity("1");
                    pp.setQualified("0");

                    setsetuperwei(pp, beans);
                }
            }
        } else {

            ppId = productPackaging.getPpID();
            goodsId = goodsManage.getGoodsID();
            String hql1 = "from ProductPackaging where type='扫码收款' and ppId=?";
            productPackaging = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{ppId});
            setsetuperwei(productPackaging, beans);

        }
        JSONObject json = new JSONObject();
        baseBeanService.executeHqlsByParamsList(beans, null, null);
        json.accumulate("ppId", ppId);
        json.accumulate("goodsId", goodsId);
        json.accumulate("sccId", sccId);
        result = json.toString();
        System.out.println(result);
        return "success";
    }

    /**
     * 获取二维码页面
     *
     * @return
     */

    public String getErWeiMa() {
        CAccount account = (CAccount) ActionContext.getContext().getSession()
                .get("account");
        if (account == null) {
            return "no_login";
        }


        String hql1 = "from ProductPackaging where type='扫码收款' and companyId=?";
        productPackaging = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{account.getCompanyID()});
        String hql = " from TEshopCusCom where  companyId=?";
        tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()});
        if (productPackaging != null) {

            setup = (ProSetup) baseBeanService.getBeanByHqlAndParams("from ProSetup where ppid = ?", new Object[]{productPackaging.getPpID()});
        }

        return "erweima";

    }


    public String getErWeiMa2() {

        request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        if (!"2".equals(falg) && companyId == null) {
            CAccount account = (CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
            companyId = account.getCompanyID();
            if (account == null) {
                return "login";
            }
        }
        String hql2 = " select t.logoPath,c.compnay_id from DT_ccom_com c,dtContactCompany t where  c.ccompany_Id=t.ccompanyID and c.compnay_id=?";
        Object[] obj = (Object[]) baseBeanService.getObjectBySqlAndParams(hql2, new Object[]{companyId});
        String hql = " from TEshopCusCom where  companyId=?";
        tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{companyId});
        String hql1 = "from ProductPackaging where type='扫码收款' and companyId=?";
        productPackaging = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{companyId});
        if (productPackaging != null) {

            setup = (ProSetup) baseBeanService.getBeanByHqlAndParams("from ProSetup where ppid = ?", new Object[]{productPackaging.getPpID()});
        }
        request.setAttribute("path", obj);

        return "qrCode";
    }


    /**
     * @param pp    产品
     * @param beans 改动数据集合
     * @Title: 提炼重复的代码
     * @Description: 产品佣金设置
     */
    private void setsetuperwei(ProductPackaging pp, List<BaseBean> beans) {
        String suid = null;
        ProSetup ps = null;
        if (productPackaging != null) {

            ps = (ProSetup) baseBeanService.getBeanByHqlAndParams(
                    "from ProSetup where ppid=?",
                    new Object[]{productPackaging.getPpID()});
        }

        BigDecimal re = new BigDecimal(0.01).setScale(2, RoundingMode.HALF_UP);;
        BigDecimal br1 = new BigDecimal(setup.getBrokerage());
        BigDecimal bignum4 = new BigDecimal(0.01).setScale(2, RoundingMode.HALF_UP);
        BigDecimal ef = null;
        BigDecimal bignum3 = null;
        BigDecimal br = null;
        bignum3 = bignum4.multiply(br1);
        br = re.multiply(bignum3);
        ef = re.subtract(br);
        Double efprice1 = ef.setScale(6, BigDecimal.ROUND_DOWN).doubleValue();
        String efprice = null;
        efprice = String.format("%.6f", efprice1);
    //    System.out.println("efprice=" + efprice);
     //   Double reprice1 = re.setScale(6, BigDecimal.ROUND_DOWN).doubleValue();
        String reprice = null;
        reprice = String.format("%.6f", re);
   //     System.out.println("reprice=" + reprice);
       // Double brokerage1 = br.setScale(6, BigDecimal.ROUND_DOWN).doubleValue();
        String brokerage = null;
        brokerage = String.format("%.6f", br);
    //    System.out.println("brokerage=" + brokerage);
        BigDecimal dlsum = new BigDecimal(0);
        if (ps == null) {
            // 佣金设计
            ps = new ProSetup();
            ps.setSuid(serverService.getServerID("setup"));
            ps.setComId(companyId);
            ps.setPpid(pp.getPpID());

        }

        suid = ps.getSuid();
        ps.setPpname(pp.getGoodsName());
        ps.setRePrice(reprice);
        ps.setBrokerage(brokerage);
        ps.setSjdate(new Date());
        ps.setEfPrice(efprice);
        ps.setProxyprice("0");
        pp.setPrice(ps.getEfPrice());
        pp.setMoney(ps.getEfPrice());

        beans.add(ps);
        beans.add(pp);
    }

    /**
     * 获取二维码页面
     *
     * @return
     */

    public String getEWeiMa() {

        String hql = "from ProductPackaging where ppID = ?";
        productPackaging = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ppID});


        return "shengerweima";
    }

    /**
     * 获取发布情况
     *
     * @return
     */

    public String getById() {

        String hql = "from ProductPackaging where ppID = ?";
        JSONObject json = new JSONObject();
        productPackaging = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ppID});
        String i = productPackaging.getShowweixin();
        json.accumulate("i", i);
        result = json.toString();
        return "success";

    }

    /**
     * 手机端设置佣金
     *
     * @return
     */
    public String productserweima2() {


        List<BaseBean> beans = new ArrayList<BaseBean>();

        request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        if (!"2".equals(falg) && companyId == null) {
            CAccount account = (CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
            if (account == null) {
                return "login";
            }
            companyId = account.getCompanyID();
        }

        String hql2 = " from TEshopCusCom where  companyId=?";
        tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{companyId});
        sccId = tcc.getSccId();
        beans.add(tcc);
        // 物品设计
        if (productPackaging.getPpID() == null || productPackaging.getPpID().equals("")) {
            GoodsManage gm = new GoodsManage();
            gm.setGoodsID(serverService.getServerID("goodsID"));
            goodsId = gm.getGoodsID();
            // 编号处理
            String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in (select c.companyID from Company c where c.groupCompanySn=?)  and vt.typeID=? ";
            if (companyId != null && !companyId.equals("")) {
                gm.setTypeID("扫码收款");
                Company company = (Company) baseBeanService
                        .getBeanByHqlAndParams(
                                "from Company where companyID= ?",
                                new Object[]{companyId});
                Object[] params = {company.getGroupCompanySn(), gm.getTypeID()};
                // 获取拼音码
                String pinyin = ToChineseFirstLetter.getFirstLetter(gm
                        .getTypeID());
                String Upstr = pinyin.toUpperCase();// 转换为大写
                int goodscodingnum = baseBeanService.getConutByByHqlAndParams(
                        hql, params);
                DecimalFormat form = new DecimalFormat("000000");
                String ss = form.format(goodscodingnum + 1);
                gm.setGoodsName("扫码收款");
                gm.setGoodsCoding(Upstr + "_" + ss);
                gm.setFiveClear("4");
                gm.setCompanyID(companyId);
                gm.setGoodsState("00");
                gm.setCreatedate(new Date());
                gm.setTradeID("扫码收款");
                gm.setTradeName("扫码收款");
                gm.setTradeCode("扫码收款");
                String jjPath = ("/images/ea/finance/NewPhoneOrders/sellerOrder/1.png");
                gm.setPhotoPath(jjPath);
                AttriProduction attrp = new AttriProduction();
                attrp.setApid(serverService.getServerID("apid"));
                attrp.setType("2");
                attrp.setImgurl(jjPath);
                attrp.setGoodsid(gm.getGoodsID());
                //attrp.setSort(i + 1);
                beans.add(attrp);

                beans.add(gm);

                // 产品设计
                if (gm != null && !gm.getGoodsID().equals("")) {
                    ProductPackaging pp = new ProductPackaging();
                    pp.setPpID(serverService.getServerID("p"));
                    ppId = pp.getPpID();
                    String hqlcode = "from CCode where codeID = ?";
                    CCode code = (CCode) baseBeanService.getBeanByHqlAndParams(
                            hqlcode, new Object[]{gm.getTradeID()});
                    String codes = "";
                    if (code != null) {
                        codes = code.getCodeSn();
                    }
                    pp.setProductCode(productlaunchService.generateProductCode(codes, ""));
                    pp.setGoodsID(gm.getGoodsID());
                    pp.setGoodsName(gm.getGoodsName());
                    pp.setImage(gm.getPhotoPath());
                    pp.setTradeCode(gm.getTradeCode());
                    pp.setTradeID(gm.getTradeID());
                    pp.setTradeName(gm.getTradeName());
                    //pp.setDelStatus("01");
                    pp.setType("扫码收款");
                    if (flag != null && flag.equals("tocang")) {

                        pp.setShowweixin("01");
                    }
                    pp.setDelStatus("01");
                    pp.setProductstate("00");
                    pp.setFiveClear("4");
                    pp.setCompanyID(companyId);
                    pp.setPackagingDate(new Date());
                    pp.setYjstatus("01");// 已设置佣金
                    pp.setField("01");
                    pp.setQuantity("1");
                    pp.setQualified("0");

                    setsetuperwei(pp, beans);
                }
            }
        } else {

            ppId = productPackaging.getPpID();
            goodsId = goodsManage.getGoodsID();
            String hql1 = "from ProductPackaging where type='扫码收款' and ppId=?";
            productPackaging = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{ppId});
            setsetuperwei(productPackaging, beans);

        }
        JSONObject json = new JSONObject();
        baseBeanService.executeHqlsByParamsList(beans, null, null);
        json.accumulate("ppId", ppId);
        json.accumulate("goodsId", goodsId);
        result = json.toString();
        System.out.println(result);
        return "success";
    }

    /**
     * 手机端发布和库存设置
     *
     * @return
     */
    public String phproductslaunch() {
        String staffid = null;
        tcc = getTcc();
        account = getAccount();
        companyId = getCompanyId();
        if (tcc != null && tcc.getStaffid() != null && !tcc.getStaffid().equals("")) {
            staffid = tcc.getStaffid();
            pseudoCompanyName = tcc.getPseudoCompanyName();
        } else if (account != null && !account.getStaffID().equals("")) {
            staffid = account.getStaffID();
            pseudoCompanyName = account.getCompanyName();
        }
        //request = ServletActionContext.getRequest();
        //HttpSession httpsession = request.getSession();
        //SessionWrap sw = SessionWrap.getInstance();
        //TEshopCusCom tcc = (TEshopCusCom) sw.getObject(httpsession, SessionWrap.KEY_SHOPCUSCOM);
        Map<String, Object> session = ActionContext.getContext().getSession();
        //HttpSession httpSession = ServletActionContext.getRequest().getSession();
        List<BaseBean> beans = new ArrayList<>();
        GoodsManage gm = null;
        ProductPackaging pp = null;
        // 物品设计
        if (goodsManage == null || goodsManage.getGoodsID() == null
                || goodsManage.getGoodsID().equals("")) {
            gm = new GoodsManage();
            gm.setGoodsID(serverService.getServerID("goodsID"));
            goodsId = gm.getGoodsID();
            System.out.print(goodsId);
            // 编号处理
            String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in (select c.companyID from Company c where c.groupCompanySn=?)  and vt.typeID=? ";
            if (companyId != null && !companyId.equals("")) {
                gm.setTypeID("其他");
                Company company = (Company) baseBeanService
                        .getBeanByHqlAndParams(
                                "from Company where companyID= ?",
                                new Object[]{companyId});
                Object[] params = {company.getGroupCompanySn(), gm.getTypeID()};
                // 获取拼音码
                String pinyin = ToChineseFirstLetter.getFirstLetter(gm
                        .getTypeID());
                String Upstr = pinyin.toUpperCase();// 转换为大写
                int goodscodingnum = baseBeanService.getConutByByHqlAndParams(
                        hql, params);
                DecimalFormat form = new DecimalFormat("000000");
                String ss = form.format(goodscodingnum + 1);
                //物品
                gm.setGoodsName(goodsManage.getGoodsName());
                gm.setGoodsCoding(Upstr + "_" + ss);
                gm.setFiveClear("4");
                gm.setCompanyID(companyId);
                gm.setGoodsState("00");
                gm.setCreatedate(new Date());
                gm.setTradeID(goodsManage.getTradeID());
                gm.setTradeName(goodsManage.getTradeName());
                gm.setTradeCode(goodsManage.getTradeCode());
                gm.setBarCode(goodsManage.getBarCode());// 条码
                gm.setCategoryName(goodsManage.getCategoryName());
                gm.setCategoryId(goodsManage.getCategoryId());
                gm.setBrand(goodsManage.getBrand());
                gm.setIsScale(goodsManage.getIsScale());
                gm.setVariableID(goodsManage.getVariableID());
                beans.add(gm);
                // 保存物品主图
                if (photo != null && photo.length > 0) {
                    String path = ServletActionContext.getRequest()
                            .getSession().getServletContext().getRealPath("/");
                    String photopath = "";
                    AttriProduction attrp = null;
                    for (int i = 0; i < photo.length; i++) {
                        photopath = fileService.savePhoto(
                                path,
                                photoFileName[i],
                                photo[i],
                                companyId,
                                "/gooddesign/"
                                        + Utilities.getDateString(new Date(),
                                        "yyyy-MM-dd"));
                        String jjPath = photopath.split("\\.")[0] + "small." + photopath.split("\\.")[1];
                        ImageCut.scale(path + photopath, path + jjPath, 414, 431);
                        if (i == 0) {// 第一张图片保存在goodsmanage表，同时也保存attriproduction表
                            gm.setPhotoPath(jjPath);
                        }
                        attrp = new AttriProduction();
                        attrp.setApid(serverService.getServerID("apid"));
                        attrp.setType("2");
                        attrp.setImgurl(jjPath);
                        attrp.setGoodsid(gm.getGoodsID());
                        attrp.setSort(i + 1);
                        beans.add(attrp);
                    }

                }
                // 产品设计
                if (gm != null && !gm.getGoodsID().equals("")) {
                    pp = new ProductPackaging();
                    pp.setPpID(serverService.getServerID("p"));
                    String hqlcode = "from CCode where codeID = ?";
                    CCode code = (CCode) baseBeanService.getBeanByHqlAndParams(
                            hqlcode, new Object[]{gm.getTradeID()});
                    String codes = "";
                    if (code != null) {
                        codes = code.getCodeSn();
                    }
                    pp.setProductCode(productlaunchService.generateProductCode(codes, ""));
                    pp.setGoodsID(gm.getGoodsID());
                    pp.setGoodsName(gm.getGoodsName());
                    pp.setImage(gm.getPhotoPath());
                    pp.setTradeCode(goodsManage.getTradeCode());
                    pp.setTradeID(goodsManage.getTradeID());
                    pp.setTradeName(goodsManage.getTradeName());
                    pp.setType("物品类别暂定");
                    if (flag != null && (flag.equals("tocang")||flag.equals("container"))) {
                        pp.setShowweixin("00");
                    } else {
                        pp.setShowweixin("01");
                    }
                    pp.setDelStatus("00");
                    pp.setProductstate("01");
                    pp.setFiveClear("4");
                    pp.setCompanyID(companyId);
                    pp.setPackagingDate(new Date());
                    pp.setYjstatus("01");// 已设置佣金
                    //未设置佣金默认给:00
                    pp.setWholesaleStatus("00");
                    pp.setVipStatus("00");
                    pp.setActivityStatus("00");
                    pp.setField("01");
                    pp.setQuantity(productPackaging.getInvNum());
                    pp.setInvNum(productPackaging.getInvNum());
                    pp.setPrice(setup.getEfPrice());
                    //pp.setStockSize(Integer.valueOf(productPackaging.getInvNum()));
                    pp.setQualified("1");
                    pp.setCategoryId(goodsManage.getCategoryId());
                    pp.setCategoryName(goodsManage.getCategoryName());
                    pp.setTemporary("1");//成功发布
                    pp.setBarCode(goodsManage.getBarCode());
                    pp.setBrand(goodsManage.getBrand());
                    pp.setIsScale(goodsManage.getIsScale());

                    /*pp.setDepotID(productPackaging.getDepotID());
                    pp.setDepotName(productPackaging.getDepotName());
                    pp.setDepotCoding(productPackaging.getDepotCoding());*/
                    pp.setSingleWeight(productPackaging.getSingleWeight());
                    pp.setStanpro(productPackaging.getStanpro());
                    pp.setVariableID(goodsManage.getVariableID());
                    setsetup(pp, beans);
                }
            } // 修改
        } else {
            //物品
            goodsId = goodsManage.getGoodsID();
            gm = (GoodsManage) baseBeanService
                    .getBeanByHqlAndParams("from GoodsManage where goodsID=?",
                            new Object[]{goodsId});
            gm.setGoodsName(goodsManage.getGoodsName());
            gm.setTradeCode(goodsManage.getTradeCode());
            gm.setTradeID(goodsManage.getTradeID());
            gm.setTradeName(goodsManage.getTradeName());
            gm.setCategoryId(goodsManage.getCategoryId());
            gm.setCategoryName(goodsManage.getCategoryName());
            gm.setBrand(goodsManage.getBrand());
            // 产品设计
            pp = (ProductPackaging) baseBeanService
                    .getBeanByHqlAndParams(
                            "from ProductPackaging where goodsID=?",
                            new Object[]{goodsManage.getGoodsID()});
            pp.setGoodsName(gm.getGoodsName());
            pp.setTradeCode(gm.getTradeCode());
            pp.setTradeID(gm.getTradeID());
            pp.setTradeName(gm.getTradeName());
            if (pp.getInvNum() == null && "".equals(pp.getInvNum())) {
                pp.setInvNum(productPackaging.getInvNum());
                pp.setStockSize(Integer.valueOf(productPackaging.getInvNum()));
            }
            pp.setCategoryId(goodsManage.getCategoryId());
            pp.setCategoryName(goodsManage.getCategoryName());
            pp.setBrand(goodsManage.getBrand());
            pp.setTemporary("1");//成功发布
            if (flag != null && (flag.equals("tocang")||flag.equals("container"))) {
                pp.setShowweixin("00");
            } else {
                pp.setShowweixin("01");
            }
            // 物品主图
            AttriProduction ap = null;
            List<BaseBean> aplist = new ArrayList<BaseBean>();
            aplist = baseBeanService
                    .getListBeanByHqlAndParams(
                            "from AttriProduction where goodsid=? and type=? order by sort",
                            new Object[]{goodsId, "2"});
            if (photo != null && photo.length > 0) {
                String path = ServletActionContext.getRequest().getSession()
                        .getServletContext().getRealPath("/");
                String photopath = "";
                for (int i = 0; i < photo.length; i++) {
                    photopath = fileService.savePhoto(
                            path,
                            photoFileName[i],
                            photo[i],
                            companyId,
                            "/gooddesign/"
                                    + Utilities.getDateString(new Date(),
                                    "yyyy-MM-dd"));
                    String jjPath = photopath.split("\\.")[0] + "small." + photopath.split("\\.")[1];
                    ImageCut.scale(path + photopath, path + jjPath, 414, 431);
                    ap = new AttriProduction();
                    ap.setApid(serverService.getServerID("apid"));
                    ap.setImgurl(jjPath);
                    ap.setGoodsid(goodsId);
                    ap.setType("2");
                    if (aplist != null && aplist.size() > 0) {
                        AttriProduction a = (AttriProduction) aplist.get(aplist.size() - 1);
                        ap.setSort(a.getSort() + i + 1);
                    } else {
                        ap.setSort(i + 1);
                        gm.setPhotoPath(jjPath);
                        pp.setImage(jjPath);
                    }
                    beans.add(ap);
                }
            }
            setsetup(pp, beans);
            beans.add(gm);
        }

        if (flag != null && flag.equals("container")) {
            String pdhql="from productDepot where ppid=? and depotid=?";
            productDepot pd=(productDepot)baseBeanService.getBeanByHqlAndParams(pdhql.toString(),new Object[]{pp.getPpID(), productDepot.getDepotid()});
            if (pd==null){
                pd=new productDepot();
                BeanUtils.copyProperties(pd,productDepot);
                pd.setPdid(serverService.getServerID("pd"));
                pd.setPpid(pp.getPpID());
                pd.setProComid(pp.getCompanyID());
                beans.add(pd);
            }
        }

        // 保存产品颜色，规格
        String sql = "delete AttriProduction where goodsid=? and type='1' ";
        String sql2 = "delete AttriProduction where goodsid=? and type='0' ";
        /* List<Object[]> paramslist = new ArrayList<Object[]>();
        paramslist.add(new Object[]{goodsId});
        baseBeanService.executeSqlsByParmsList(null, new String[]{sql},
                paramslist);
        baseBeanService.executeSqlsByParmsList(null, new String[]{sql2},
                paramslist);*/
        if (colorvalue != null && !colorvalue.equals("")) {
            AttriProduction attrp = null;
            String[] colors = colorvalue.split(",");
            for (int i = 0; i < colors.length; i++) {
                attrp = new AttriProduction();
                attrp.setApid(serverService.getServerID("apid"));
                attrp.setAttriname(attrinames);
                attrp.setAttrivalue(colors[i].trim());
                attrp.setType("1");
                attrp.setGoodsid(goodsId);
                attrp.setSort(i + 1);
                beans.add(attrp);
            }
        }
        if (sizevalue != null && !sizevalue.equals("")) {
            AttriProduction attrp = null;
            String[] sizearray = sizevalue.split(",,");
            for (int i = 0; i < sizearray.length; i++) {
                attrp = new AttriProduction();
                attrp.setApid(serverService.getServerID("apid"));
                attrp.setAttriname(attrinamec);
                attrp.setAttrivalue(sizearray[i]);
                attrp.setType("0");
                attrp.setGoodsid(goodsId);
                attrp.setSort(i + 1);
                beans.add(attrp);
            }
        }
        // 保存产品描述
        List<Object[]> params = new ArrayList<Object[]>();
        String url = "", newhtl = "", oldhtl = "", temp = "", photopath = "";
        String path = ServletActionContext.getRequest().getContextPath();
        String basePath = ServletActionContext.getRequest().getScheme() + "://"
                + ServletActionContext.getRequest().getServerName() + ":"
                + ServletActionContext.getRequest().getServerPort() + path
                + "/";
        if (htl != null && !htl.equals("")) {
            if (pic != null && pic.length > 0) {
                for (int i = 0; i < pic.length; i++) {
                    newhtl = htl.substring(0, htl.indexOf("img" + i));
                    temp = htl.substring(htl.indexOf("img" + i));
                    oldhtl = temp.substring(temp.indexOf(" height: auto;\">"));
                    String path1 = ServletActionContext.getRequest()
                            .getSession().getServletContext().getRealPath("/");
                    photopath = fileService.savePhoto(
                            path1,
                            picFileName[i],
                            pic[i],
                            companyId,
                            "/goodfunction/"
                                    + Utilities.getDateString(new Date(),
                                    "yyyy-MM-dd"));
                    htl = newhtl + "img" + i + "\" src=\"" + basePath
                            + photopath
                            + "\" style=\"display: block; width: 100%;"
                            + oldhtl;
                }
            }
            url = saveContentToFile(htl.trim());
        }
        String hql_del = "delete GoodFunction where goodsid= ?";
        if (goodsId != null && !goodsId.equals("")) {
            String filpath = ServletActionContext.getServletContext()
                    .getRealPath("/");
            params.add(new Object[]{goodsId});
            GoodFunction gf = (GoodFunction) baseBeanService
                    .getBeanByHqlAndParams(
                            "from GoodFunction where goodsid= ?",
                            new Object[]{goodsId});
            if (gf != null) {
                FileUtil.delete(filpath + gf.getUrl());
            }

            GoodFunction goodFun = new GoodFunction();
            goodFun.setGfid(serverService.getServerID("gfid"));
            goodFun.setUrl(url);
            goodFun.setName("物品功能");
            goodFun.setType("1");
            goodFun.setGoodsid(goodsId);
            beans.add(goodFun);
        }
        String s = "1";

        if (pp != null && !pp.getPpID().equals("")) {
            //模拟测试
            BsimTest bt = (BsimTest) baseBeanService.getBeanByHqlAndParams("from BsimTest where id=?", new Object[]{pp.getPpID()});
            if (bt == null) {
                bt = new BsimTest();
                bt.setBsimTestId(serverService.getServerID("testid"));
                bt.setGoodBar(pp.getBarCode());
                bt.setGoodName(pp.getGoodsName());
                bt.setGoodStandard(pp.getStandard());
                bt.setIndustryClassification(pp.getTradeCode());
                bt.setPrice(pp.getPrice());
                bt.setMoney(pp.getMoney());
                bt.setBtnumber(pp.getQuantity());
                bt.setItemNumber(pp.getProductCode());
                bt.setStatus("02");//合格
                bt.setId(pp.getPpID());
                bt.setAuditTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                beans.add(bt);
            }

        }
        if (ptrack == null || ptrack.getPtrackeId() == null || "".equals(ptrack.getPtrackeId())) {
            PTrack ptrack = (PTrack) baseBeanService.getBeanByHqlAndParams("from PTrack where id=? and companyID=?", new Object[]{pp.getPpID(), companyId});
            if (ptrack == null) {
                //生产跟踪
                ptrack = new PTrack();
                DCheck dcheck = new DCheck();
                ptrack.setPtrackeId(serverService.getServerID("ptrack"));
                ptrack.setStatus("00");
                ptrack.setTrackmanId(staffid);
                ptrack.setProductName(pp.getGoodsName());
                ptrack.setProductionDepartment((String) session.get("organizationName"));
                ptrack.setDepartmentID((String) session.get("organizaitonID"));
                ptrack.setId(pp.getPpID());
                dcheck.setDcheckId(serverService.getServerID("dcheck"));
                dcheck.setId(ptrack.getPtrackeId());
                dcheck.setGoodName(ptrack.getProductName());
                dcheck.setItemNumber(ptrack.getProductNumber());
                dcheck.setBtnumber(ptrack.getThroughput());
                dcheck.setStatus("01");
                dcheck.setCompanyId(companyId);
                ptrack.setCompanyID(companyId);
                beans.add(ptrack);
                beans.add(dcheck);
            }
            //保存电子秤相关
            if ("0".equals(goodsManage.getIsScale())) {
                ScaleGoods scaleGoods = (ScaleGoods) baseBeanService.getBeanByHqlAndParams("from ScaleGoods where goodsID=?", new Object[]{gm.getGoodsID()});
                if (scaleGoods == null) {
                    scaleGoods = new ScaleGoods();
                    scaleGoods.setSgID(serverService.getServerID("sgid"));
                    scaleGoods.setCompanyID(companyId);

                    String phql = "select count(*) from ScaleGoods where companyID = ? ";
                    int pcount = baseBeanService.getConutByByHqlAndParams(phql, new Object[]{companyId});
                    if (pcount != 0) {
                        String ScaleGoodSsql = "select max(to_number(plu)) from dtScaleGoods where companyID= ?";
                        pcount = baseBeanService.getConutByBySqlAndParams(ScaleGoodSsql,
                                new Object[]{companyId});
                    }
                    //自定生成条码
                    scaleGoods.setPlu(pcount + 1);
                    DecimalFormat fofrm = new DecimalFormat("00000");
                    String ap = fofrm.format(pcount + 1);
                    scaleGoods.setAlternativeItemID(ap);//货号
                    gm.setBarCode(ap);
                    scaleGoods.setGoodsID(gm.getGoodsID());
                    pp.setBarCode(ap);
                    scaleGoods.setUnitOfMeasureCode(unitOfMeasureCode);//计价单位前台传
                    beans.add(scaleGoods);
                }
            }
        }

        if (pp != null && !pp.getPpID().equals("")) {
            DepotManage depot = null;
            if (pp.getDepotID() == null || pp.getDepotID().equals("")) {
                depot = (DepotManage) baseBeanService.getBeanByHqlAndParams("from DepotManage where companyID=? and depotName=? and depotState!=?", new Object[]{companyId, "销售库", "01"});
            } else {
                depot = (DepotManage) baseBeanService.getBeanByHqlAndParams("from DepotManage where companyID=? and depotID=? and depotState!=?", new Object[]{companyId, pp.getDepotID(), "01"});
            }

            int count = baseBeanService.getConutByByHqlAndParams("select count(*) from Inventory where companyID=? and productId=? and warehouse=?", new Object[]{companyId, pp.getPpID(), depot.getDepotID()});
            if (count <= 0) {
                String staffHql = "from Staff where staffID=?";
                Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(staffHql, new Object[]{staffid});
                UtboundOrderVo ut = null;
                if (ut == null) {
                    ut = new UtboundOrderVo();
                    ut.setJournalnum(serverService.getBillID(companyId));
                }
                ut.setCashierDate(DateUtil.getCurrentDate("yyyy-MM-dd"));
                String InventoryId = serverService.getServerID("Inventory");
                String goodsbillsId = serverService.getServerID("GoodsBills");

                COrganization org = (COrganization) baseBeanService.getBeanByHqlAndParams("from COrganization where organizationUrl=? and" +
                        " organizationPID=? and companyID=?", new Object[]{"/ea/office/ea_toTeachingAffairsDepartment", companyId, companyId});
                Company c = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", new Object[]{companyId});
                String financialbillId = serverService.getServerID("FinancialBill");
                String cashierbillsId = serverService.getServerID("CashierBills");
                String jNumOrder = "";

                //存储出库单
                CashierBills cashier = new CashierBills();
                cashier.setCashierBillsID(cashierbillsId);
                System.out.print(cashierbillsId);
                cashier.setJournalNum(ut.getJournalnum());
                cashier.setBillsType("生产入库单");
                //cashier.setGroupCompanySn(groupCompanySn);
                cashier.setCompanyID(companyId);
                cashier.setCompanyName(pseudoCompanyName);
                cashier.setOrganizationID(org.getOrganizationID());
                cashier.setInputOrganizationName(org.getOrganizationName());
                cashier.setCashierDate(new Date());
                cashier.setStaffID(staffid);
                cashier.setStaffName(staff.getStaffName());
                cashier.setInputid(staffid);
                cashier.setInputName(staff.getStaffName());
                cashier.setStatus("15");
                cashier.setjNumOrder(jNumOrder);
                cashier.setFiveClear("4");
                beans.add(cashier);

                //添加进销存单据
                FinancialBill fin = new FinancialBill();
                fin.setFinancialbillID(financialbillId);
                fin.setCashierBillsID(cashier.getCashierBillsID());
                //fin.setGroupCompanySn(groupCompanySn);
                fin.setCcompanyID(companyId);
                fin.setOrganizationID(org.getOrganizationID());
                fin.setDepotID("001");
                fin.setDepotName("生产流水线");
                fin.setDrdepotID(depot.getDepotID());
                fin.setDrdepotName(depot.getDepotName());
                fin.setBillsType("生产入库单");
                fin.setJournalNum(ut.getJournalnum());
                fin.setBillsDate(new Date());
                fin.setBillStaffID(staffid);
                fin.setBillStaffName(staff.getStaffName());
                fin.setStaffsID(staffid);
                fin.setStaffsName(staff.getStaffName());
                //fin.setExaminegoodsDate(utboundOrderVo.getExaminegoodsDate());
                fin.setBillStatus("14");
                beans.add(fin);

                //添加物品单据
                GoodsBills goods = new GoodsBills();
                goods.setGoodsBillsID(goodsbillsId);
                goods.setCompanyID(companyId);
                goods.setCashierBillsID(cashier.getCashierBillsID());
                goods.setPpID(pp.getPpID());
                goods.setGoodsID(gm.getGoodsID());
                goods.setTypeID(gm.getTypeID());
                goods.setGoodsNum(gm.getNum());
                goods.setGoodsName(gm.getGoodsName());
                goods.setStandard(pp.getStandard());
                goods.setPrice(pp.getPrice());
                goods.setQuantity(pp.getInvNum());
                goods.setMoney(StringUtil.formatFloatNumber((Double.parseDouble(pp.getQuantity()) * Double.parseDouble(pp.getPrice()))));
                goods.setDepotID(depot.getDepotID());
                goods.setDepotName(depot.getDepotName());
                goods.setKcStatus("14");
                goods.setGoodsStatus("00");
                goods.setCategory("00");
                //goods.setProInspectionID(dcheck.getId());
                goods.setPpID(pp.getPpID());
                beans.add(goods);
                //库存表
                Inventory inv = new Inventory();
                inv.setInventoryID(InventoryId);
                inv.setCompanyID(companyId);
                inv.setOrganizationID(org.getOrganizationID());
                inv.setDepartmentID(org.getOrganizationID());
                inv.setStaffID(staffid);
                inv.setStaffName(staff.getStaffName());
                inv.setWarehouse(depot.getDepotID());
                inv.setWarehouseName(depot.getDepotName());
                inv.setGoodsID(goods.getGoodsID());
                inv.setGoodsName(goods.getGoodsName());
                inv.setGoodsType(goods.getTypeID());
                inv.setStandard(goods.getStandard());
                inv.setGoodsCoding(goods.getGoodsNum());
                inv.setUnitPrice(goods.getPrice());
                inv.setProductId(goods.getPpID());//物品单价
                inv.setInvenQuantity(pp.getInvNum());    //物品数量
                inv.setSumPrice(goods.getMoney());            //总价
                inv.setGoodstatus("00");
                inv.setProductId(pp.getPpID());
                inv.setBarcode(pp.getBarCode());
                beans.add(inv);

                //盘库
                InvtFbCheck ifc = new InvtFbCheck();
                ifc.setFbillid(this.serverService.getServerID("invtFbCheck"));
                ifc.setCompanyid(companyId);
                ifc.setCompanyName(pseudoCompanyName);
                ifc.setOrganizationid(org.getOrganizationID());
                ifc.setOrgName(org.getOrganizationName());
                ifc.setDepartmentid(org.getOrganizationID());
                ifc.setGroupcompanysn(c.getGroupCompanySn());
                ifc.setBillsdate(new Date());//日期转字符串
                ifc.setBillstype("盘库单");
                ifc.setBillstatus("草稿");
                ifc.setWarehousename(depot.getDepotName());
                ifc.setWarehouse(depot.getDepotID());
                ifc.setJournalnum(this.serverService.getBillID(companyId));
                ifc.setStaffid(staffid);
                ifc.setStaffname(staff.getStaffName());
                beans.add(ifc);

                InvCheckGoods ci = new InvCheckGoods();
                ci.setCheckinvId(this.serverService.getServerID("invCheckGoods"));
                ci.setFBILLID(ifc.getFbillid());
                ci.setRealQuantity(pp.getInvNum());
                ci.setPrice(setup.getEfPrice());
                ci.setPpID(pp.getPpID());
                ci.setGoodsID(goods.getGoodsID());
                ci.setGoodsType(goods.getTypeID());
                ci.setGoodsName(goods.getGoodsName());
                ci.setInvenQuantity("0");
                beans.add(ci);
            }

        }
        try {
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{sql, sql2, hql_del}, new Object[]{gm.getGoodsID()});
            /*ObjectMapper mapper = new ObjectMapper();
            for (int i = 0; i < beans.size(); i++) {
                try {
                    String json = mapper.writeValueAsString(beans.get(i));
                    System.out.println("\"" + BaseBean.class.getName() + i + "\":" + json + ",");

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("保存失败");
            s = "0";
        }
        JSONObject json = new JSONObject();
        json.accumulate("s", s);
        result = json.toString();
        return "success";
    }

    /**
     * 手机端发布和库存设置
     *
     * @return
     */
    public String productSave() {
        request = ServletActionContext.getRequest();
        HttpSession httpsession = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom tcc = (TEshopCusCom) sw.getObject(httpsession, SessionWrap.KEY_SHOPCUSCOM);
        Map<String, Object> session = ActionContext.getContext().getSession();
        HttpSession httpSession = ServletActionContext.getRequest().getSession();
        List<BaseBean> beans = new ArrayList<>();
        GoodsManage gm = null;
        ProductPackaging pp = null;
        // 物品设计
        if (goodsManage == null || goodsManage.getGoodsID() == null
                || goodsManage.getGoodsID().equals("")) {
            gm = new GoodsManage();
            gm.setGoodsID(serverService.getServerID("goodsID"));
            goodsId = gm.getGoodsID();
            System.out.print(goodsId);
            // 编号处理
            String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in (select c.companyID from Company c where c.groupCompanySn=?)  and vt.typeID=? ";
            if (companyId != null && !companyId.equals("")) {
                gm.setTypeID("其他");
                Company company = (Company) baseBeanService
                        .getBeanByHqlAndParams(
                                "from Company where companyID= ?",
                                new Object[]{companyId});
                Object[] params = {company.getGroupCompanySn(), gm.getTypeID()};
                // 获取拼音码
                String pinyin = ToChineseFirstLetter.getFirstLetter(gm
                        .getTypeID());
                String Upstr = pinyin.toUpperCase();// 转换为大写
                int goodscodingnum = baseBeanService.getConutByByHqlAndParams(
                        hql, params);
                DecimalFormat form = new DecimalFormat("000000");
                String ss = form.format(goodscodingnum + 1);
                gm.setGoodsName(goodsManage.getGoodsName());
                gm.setGoodsCoding(Upstr + "_" + ss);
                gm.setFiveClear("4");
                gm.setCompanyID(companyId);
                gm.setGoodsState("00");
                gm.setCreatedate(new Date());
                gm.setTradeID(goodsManage.getTradeID());
                gm.setTradeName(goodsManage.getTradeName());
                gm.setTradeCode(goodsManage.getTradeCode());
                gm.setBarCode(goodsManage.getBarCode());// 条码
                gm.setCategoryName(goodsManage.getCategoryName());
                gm.setCategoryId(goodsManage.getCategoryId());
                gm.setBrand(goodsManage.getBrand());
                gm.setIsScale(goodsManage.getIsScale());
                // 保存物品主图
                if (photo != null && photo.length > 0) {
                    String path = ServletActionContext.getRequest()
                            .getSession().getServletContext().getRealPath("/");
                    String photopath = "";
                    AttriProduction attrp = null;
                    for (int i = 0; i < photo.length; i++) {
                        photopath = fileService.savePhoto(
                                path,
                                photoFileName[i],
                                photo[i],
                                companyId,
                                "/gooddesign/"
                                        + Utilities.getDateString(new Date(),
                                        "yyyy-MM-dd"));
                        String jjPath = photopath.split("\\.")[0] + "small." + photopath.split("\\.")[1];
                        ImageCut.scale(path + photopath, path + jjPath, 414, 431);
                        if (i == 0) {// 第一张图片保存在goodsmanage表，同时也保存attriproduction表
                            gm.setPhotoPath(jjPath);
                        }
                        attrp = new AttriProduction();
                        attrp.setApid(serverService.getServerID("apid"));
                        attrp.setType("2");
                        attrp.setImgurl(jjPath);
                        attrp.setGoodsid(gm.getGoodsID());
                        attrp.setSort(i + 1);
                        beans.add(attrp);
                    }
                    beans.add(gm);
                }
                // 产品设计
                if (gm != null && !gm.getGoodsID().equals("")) {
                    pp = new ProductPackaging();
                    pp.setPpID(serverService.getServerID("p"));
                    String hqlcode = "from CCode where codeID = ?";
                    CCode code = (CCode) baseBeanService.getBeanByHqlAndParams(
                            hqlcode, new Object[]{gm.getTradeID()});
                    String codes = "";
                    if (code != null) {
                        codes = code.getCodeSn();
                    }
                    pp.setProductCode(productlaunchService.generateProductCode(codes, ""));
                    pp.setGoodsID(gm.getGoodsID());
                    pp.setGoodsName(gm.getGoodsName());
                    pp.setImage(gm.getPhotoPath());
                    pp.setTradeCode(goodsManage.getTradeCode());
                    pp.setTradeID(goodsManage.getTradeID());
                    pp.setTradeName(goodsManage.getTradeName());
                    pp.setType("物品类别暂定");
                    if (flag != null && flag.equals("tocang")) {
                        pp.setShowweixin("00");
                    } else {
                        pp.setShowweixin("01");
                    }
                    pp.setDelStatus("00");
                    pp.setProductstate("01");
                    pp.setFiveClear("4");
                    pp.setCompanyID(companyId);
                    pp.setPackagingDate(new Date());
                    pp.setYjstatus("01");// 已设置佣金
                    //未设置佣金默认给:00
                    pp.setWholesaleStatus("00");
                    pp.setVipStatus("00");
                    pp.setActivityStatus("00");
                    pp.setField("01");
                    pp.setQuantity(productPackaging.getInvNum());
                    pp.setInvNum(productPackaging.getInvNum());
                    pp.setPrice(setup.getEfPrice());
                    pp.setStockSize(Integer.valueOf(productPackaging.getInvNum()));
                    pp.setQualified("1");
                    pp.setCategoryId(goodsManage.getCategoryId());
                    pp.setCategoryName(goodsManage.getCategoryName());
                    pp.setTemporary("1");//成功发布
                    pp.setBarCode(goodsManage.getBarCode());
                    pp.setBrand(goodsManage.getBrand());
                    pp.setIsScale(goodsManage.getIsScale());
                    setsetup(pp, beans);
                }
            } // 修改
        } else {
            goodsId = goodsManage.getGoodsID();
            gm = (GoodsManage) baseBeanService
                    .getBeanByHqlAndParams("from GoodsManage where goodsID=?",
                            new Object[]{goodsId});
            gm.setGoodsName(goodsManage.getGoodsName());
            gm.setTradeCode(goodsManage.getTradeCode());
            gm.setTradeID(goodsManage.getTradeID());
            gm.setTradeName(goodsManage.getTradeName());
            gm.setCategoryId(goodsManage.getCategoryId());
            gm.setCategoryName(goodsManage.getCategoryName());
            gm.setBrand(goodsManage.getBrand());
            pp = (ProductPackaging) baseBeanService
                    .getBeanByHqlAndParams(
                            "from ProductPackaging where goodsID=?",
                            new Object[]{goodsManage.getGoodsID()});
            pp.setGoodsName(gm.getGoodsName());
            pp.setTradeCode(gm.getTradeCode());
            pp.setTradeID(gm.getTradeID());
            pp.setTradeName(gm.getTradeName());
            if (pp.getInvNum() == null && "".equals(pp.getInvNum())) {
                pp.setInvNum(productPackaging.getInvNum());
                pp.setStockSize(Integer.valueOf(productPackaging.getInvNum()));
            }
            pp.setCategoryId(goodsManage.getCategoryId());
            pp.setCategoryName(goodsManage.getCategoryName());
            pp.setBrand(goodsManage.getBrand());
            pp.setTemporary("1");//成功发布
            if (flag != null && flag.equals("tocang")) {
                pp.setShowweixin("00");
            } else {
                pp.setShowweixin("01");
            }
            AttriProduction ap = null;
            List<BaseBean> aplist = new ArrayList<BaseBean>();
            aplist = baseBeanService
                    .getListBeanByHqlAndParams(
                            "from AttriProduction where goodsid=? and type=? order by sort",
                            new Object[]{goodsId, "2"});
            if (photo != null && photo.length > 0) {
                String path = ServletActionContext.getRequest().getSession()
                        .getServletContext().getRealPath("/");
                String photopath = "";
                for (int i = 0; i < photo.length; i++) {
                    photopath = fileService.savePhoto(
                            path,
                            photoFileName[i],
                            photo[i],
                            companyId,
                            "/gooddesign/"
                                    + Utilities.getDateString(new Date(),
                                    "yyyy-MM-dd"));
                    String jjPath = photopath.split("\\.")[0] + "small." + photopath.split("\\.")[1];
                    ImageCut.scale(path + photopath, path + jjPath, 414, 431);
                    ap = new AttriProduction();
                    ap.setApid(serverService.getServerID("apid"));
                    ap.setImgurl(jjPath);
                    ap.setGoodsid(goodsId);
                    ap.setType("2");
                    if (aplist != null && aplist.size() > 0) {
                        AttriProduction a = (AttriProduction) aplist.get(aplist.size() - 1);
                        ap.setSort(a.getSort() + i + 1);
                    } else {
                        ap.setSort(i + 1);
                        gm.setPhotoPath(jjPath);
                        pp.setImage(jjPath);
                    }
                    beans.add(ap);
                }
            }
            setsetup(pp, beans);
            beans.add(gm);
        }

        // 保存产品颜色，规格
        String sql = "delete AttriProduction where goodsid=? and type='1' ";
        String sql2 = "delete AttriProduction where goodsid=? and type='0' ";

        if (colorvalue != null && !colorvalue.equals("")) {
            AttriProduction attrp = null;
            String[] colors = colorvalue.split(",");
            for (int i = 0; i < colors.length; i++) {
                attrp = new AttriProduction();
                attrp.setApid(serverService.getServerID("apid"));
                attrp.setAttriname(attrinames);
                attrp.setAttrivalue(colors[i].trim());
                attrp.setType("1");
                attrp.setGoodsid(goodsId);
                attrp.setSort(i + 1);
                beans.add(attrp);
            }
        }
        if (sizevalue != null && !sizevalue.equals("")) {
            AttriProduction attrp = null;
            String[] sizearray = sizevalue.split(",,");
            for (int i = 0; i < sizearray.length; i++) {
                attrp = new AttriProduction();
                attrp.setApid(serverService.getServerID("apid"));
                attrp.setAttriname(attrinamec);
                attrp.setAttrivalue(sizearray[i]);
                attrp.setType("0");
                attrp.setGoodsid(goodsId);
                attrp.setSort(i + 1);
                beans.add(attrp);
            }
        }
        // 保存产品描述
        List<Object[]> params = new ArrayList<Object[]>();
        String url = "", newhtl = "", oldhtl = "", temp = "", photopath = "";
        String path = ServletActionContext.getRequest().getContextPath();
        String basePath = ServletActionContext.getRequest().getScheme() + "://"
                + ServletActionContext.getRequest().getServerName() + ":"
                + ServletActionContext.getRequest().getServerPort() + path
                + "/";
        if (htl != null && !htl.equals("")) {
            if (pic != null && pic.length > 0) {
                for (int i = 0; i < pic.length; i++) {
                    newhtl = htl.substring(0, htl.indexOf("img" + i));
                    temp = htl.substring(htl.indexOf("img" + i));
                    oldhtl = temp.substring(temp.indexOf(" height: auto;\">"));
                    String path1 = ServletActionContext.getRequest()
                            .getSession().getServletContext().getRealPath("/");
                    photopath = fileService.savePhoto(
                            path1,
                            picFileName[i],
                            pic[i],
                            companyId,
                            "/goodfunction/"
                                    + Utilities.getDateString(new Date(),
                                    "yyyy-MM-dd"));
                    htl = newhtl + "img" + i + "\" src=\"" + basePath
                            + photopath
                            + "\" style=\"display: block; width: 100%;"
                            + oldhtl;
                }
            }
            url = saveContentToFile(htl.trim());
        }
        String hql_del = "delete GoodFunction where goodsid= ?";
        if (goodsId != null && !goodsId.equals("")) {
            String filpath = ServletActionContext.getServletContext()
                    .getRealPath("/");
            params.add(new Object[]{goodsId});
            GoodFunction gf = (GoodFunction) baseBeanService
                    .getBeanByHqlAndParams(
                            "from GoodFunction where goodsid= ?",
                            new Object[]{goodsId});
            if (gf != null) {
                FileUtil.delete(filpath + gf.getUrl());
            }
            GoodFunction goodFun = new GoodFunction();
            goodFun.setGfid(serverService.getServerID("gfid"));
            goodFun.setUrl(url);
            goodFun.setName("物品功能");
            goodFun.setType("1");
            goodFun.setGoodsid(goodsId);
            beans.add(goodFun);
        }
        String s = "1";

        if (ptrack == null || ptrack.getPtrackeId() == null || "".equals(ptrack.getPtrackeId())) {
            //保存电子秤相关
            if ("0".equals(goodsManage.getIsScale())) {
                ScaleGoods scaleGoods = (ScaleGoods) baseBeanService.getBeanByHqlAndParams("from ScaleGoods where goodsID=?", new Object[]{gm.getGoodsID()});
                if (scaleGoods == null) {
                    scaleGoods = new ScaleGoods();
                    scaleGoods.setSgID(serverService.getServerID("sgid"));
                    scaleGoods.setCompanyID(tcc.getCompanyId());

                    String phql = "select count(*) from ScaleGoods where companyID = ? ";
                    int pcount = baseBeanService.getConutByByHqlAndParams(phql, new Object[]{tcc.getCompanyId()});
                    if (pcount != 0) {
                        String ScaleGoodSsql = "select max(to_number(plu)) from dtScaleGoods where companyID= ?";
                        pcount = baseBeanService.getConutByBySqlAndParams(ScaleGoodSsql,
                                new Object[]{tcc.getCompanyId()});
                    }
                    //自定生成条码
                    scaleGoods.setPlu(pcount + 1);
                    DecimalFormat fofrm = new DecimalFormat("00000");
                    String ap = fofrm.format(pcount + 1);
                    scaleGoods.setAlternativeItemID(ap);//货号
                    gm.setBarCode(ap);
                    scaleGoods.setGoodsID(gm.getGoodsID());
                    pp.setBarCode(ap);
                    scaleGoods.setUnitOfMeasureCode(unitOfMeasureCode);//计价单位前台传
                    beans.add(scaleGoods);
                }
            }
        }
        try {
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{sql, sql2, hql_del}, new Object[]{gm.getGoodsID()});
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("保存失败");
            s = "0";
        }
        JSONObject json = new JSONObject();
        json.accumulate("s", s);
        result = json.toString();
        return "success";
    }

    /**
     * 根据终端号获取产品信息
     * 终端机调用
     *
     * @return
     */
    public String getProductByposNum() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyId = request.getParameter("companyId");
        String code = request.getParameter("posNum");
        String depotType = request.getParameter("depotType"); //判断是货柜还是秤盘  1：货柜 2:柜门 3:秤盘
        JSONObject json = new JSONObject();
        if (code == null || code.equals("")) {
            code = CookieUtil.getCookieValue("hgcode", request);
        }
        if (companyId == null || companyId.equals("")) {
            companyId = CookieUtil.getCookieValue("comID", request);
        }

        if (depotType != null && !depotType.equals("")) {
            if (depotType.equals("1")) {
                PosDevice pd=(PosDevice)depotManageService.getPosDeviceByPosNum(code);
                if (pd!=null){
                    code=pd.getHgcode();
                }else {
                    json.accumulate("msg", "01");
                    json.accumulate("error", "没有找到货柜信息");
                    return "success";
                }
            }
            json = productlaunchService.getProductToDepot(code, companyId, Integer.valueOf(depotType));
        } else {
            json.accumulate("msg", "04");
            json.accumulate("error", "参数错误");
        }
        result = json;
        return "success";
    }

    //产品分类查询
    public String ajaxProductType() {
        String sql = "select sc.* from dt_schprocategory sc where sc.companyid = ?";
        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1), 10, sql.toString(), "select count(*) from (" + sql.toString() + ")", new Object[]{companyId});
        JSONObject json = new JSONObject();
        json.accumulate("pageForm", pageForm);
        result = json.toString();
        return "success";
    }

    //产品分类增删改
    public String ProductTypeCRUD() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom eshopCusCom = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
        List<BaseBean> beans = new ArrayList<BaseBean>();
        List<Object[]> objects = new ArrayList<>();
        if (typeList != null && !"".equals(typeList)) {
            for (SchProCategory s : typeList) {
                if (s != null) {
                    if (s.getCategoryId() == null || "".equals(s.getCategoryId())) {
                        s.setCategoryId(serverService.getServerID("category"));
                        s.setStatus("2");
                        s.setCompanyId(eshopCusCom.getCompanyId());
                    }
                    String hql2 = "from ProductPackaging pp where pp.categoryId= ? ";
                    String hql3 = "from GoodsManage g where g.categoryId= ? ";
                    List<BaseBean> packagingList = baseBeanService.getListBeanByHqlAndParams(hql2, new Object[]{s.getCategoryId()});
                    List<BaseBean> goodsList = baseBeanService.getListBeanByHqlAndParams(hql3, new Object[]{s.getCategoryId()});
                    for (int i = 0; i < packagingList.size(); i++) {
                        ProductPackaging productPackaging = (ProductPackaging) packagingList.get(i);
                        productPackaging.setCategoryId(s.getCategoryId());
                        productPackaging.setCategoryName(s.getCategoryName());
                        beans.add(productPackaging);
                    }
                    for (int i = 0; i < goodsList.size(); i++) {
                        GoodsManage goodsManage = (GoodsManage) goodsList.get(i);
                        goodsManage.setCategoryId(s.getCategoryId());
                        goodsManage.setCategoryName(s.getCategoryName());
                        beans.add(goodsManage);
                    }
                    beans.add(s);
                }
            }
        }
        String sql = "";
        String sql2 = "";
        String sql3 = "";
        List<Object[]> list = new ArrayList<Object[]>();
        if (!"".equals(delList) && delList != null) {
            sql = "delete from dt_schprocategory sc where sc.categoryid in (";
            sql2 = "update dt_productpackaging p set p.categoryid = ' ' ,p.categoryname = ' ' where p.categoryid in (";
            sql3 = "update dtgoodsmanage g set g.categoryid = ' ' ,g.categoryname = ' ' where g.categoryid in (";
            Object[] categoryId = delList.split(",");
            for (int i = 0; i < categoryId.length; i++) {
                sql += "?,";
                sql2 += "?,";
                sql3 += "?,";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql2 = sql2.substring(0, sql2.length() - 1);
            sql3 = sql3.substring(0, sql3.length() - 1);
            sql += ")";
            sql2 += ")";
            sql3 += ")";
            list.add(categoryId);
            list.add(categoryId);
            list.add(categoryId);
        }
        baseBeanService.executeSqlsByParmsList(beans, new String[]{sql, sql2, sql3}, list);
        companyId = eshopCusCom.getCompanyId();
        if ("isform".equals(flag)) {
            return "ProductCategories";
        } else {
            return "success";
        }
    }

    /**
     * 获取秤盘产品信息
     *
     * @return
     */
    public String getProductTochBalance() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> map = new HashMap<>();
        String companyId = request.getParameter("companyId");
        String code = request.getParameter("code");
        account = getAccount();
        if (account == null) {
            map.put("code", "00");
            map.put("msg", "登录信息失效");
            map.put("flag", 1);
        } else {
            if (code == null || code.equals("")) {
                map.put("code", "02");
                map.put("msg", "数据传输错误");
                map.put("flag", 1);
            }
            if (companyId == null || companyId.equals("")) {
                companyId = CookieUtil.getCookieValue("comID", request);
            }
            try {
                map = productlaunchService.getProductTochBalance(code, companyId);
                map.put("Error_Num", Constant.Error_Num);
            } catch (Exception e) {
                map.put("code", "03");
                map.put("msg", e.getMessage());
                map.put("flag", 1);
            }
        }
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 补货
     *
     * @return
     */
    public String calculationNum() {
        Map<String, Object> map = new HashMap<>();
        proWeight = null;
        account = getAccount();
        if (account == null) {
            map.put("code", "0");
            map.put("msg", "登录信息失效");
            map.put("flag", 1);
        } else {
            try {
                productlaunchService.calculationNum(account, map, productPackaging.getPpID() + ":" + productPackaging.getInvNum());
                map.put("flag", 0);
            } catch (Exception e) {
                map.put("code", "1");
                map.put("msg", e.getMessage());
                map.put("flag", 1);
            }
        }
        JSONObject json = new JSONObject();
        json.accumulate("data", map);
        result = json.toString();
        return "success";
    }

    /**
     * 保存秤盘实时重量
     *
     * @return
     */
    public String updateScaleWeight() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String sc = request.getParameter("scaleCoding");
        String cc = request.getParameter("containerCoding");
        String w = request.getParameter("weight");
        String t = request.getParameter("time");
        int flag = 1; //0:成功，1：失败
        JSONObject json = new JSONObject();

        // 输入验证
        if (sc == null || sc.equals("")) {
            json.accumulate("msg", "01");
            json.accumulate("error", "秤盘编号错误");
        } else if (cc == null || cc.equals("")) {
            json.accumulate("msg", "02");
            json.accumulate("error", "货柜编号错误");
        } else if (w == null || w.equals("")) {
            json.accumulate("msg", "03");
            json.accumulate("error", "重量错误");
        } else if (t == null || t.equals("")) {
            json.accumulate("msg", "04");
            json.accumulate("error", "时间错误");
        } else {
            try {
                // 验证和转换输入
                double weight = Double.parseDouble(w);
                Date time = Utilities.getDateFromString(t, "yyyy-MM-dd HH:mm:ss");

                productlaunchService.updateScaleWeight(sc, cc, weight, time);
                flag = 0;
            } catch (NumberFormatException e) {
                json.accumulate("msg", "06");
                json.accumulate("error", "无效的重量格式");
            } catch (ParseException e) {
                json.accumulate("msg", "07");
                json.accumulate("error", "无效的时间格式");
            } catch (Exception e) {
                json.accumulate("msg", "05");
                map.put("error", "更新秤重失败(" + e.getMessage() + ")");
                // 记录日志以便调试
                logger.error("更新秤重失败", e);
            }
        }
        json.accumulate("flag", flag);
        result = json;
        return "success";
    }

    /**
     * 获取秤重
     *
     * @return
     */
    public String getWeitht() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String sc = request.getParameter("scaleCoding");
        String cc = request.getParameter("containerCoding");
        Map<String, Object> map = new HashMap<>();
        int flag = 1; //0:成功，1：失败
        // 输入验证
        if (sc == null || sc.equals("")) {
            map.put("code", "01");
            map.put("msg", "秤盘编号错误");
        } else if (cc == null || cc.equals("")) {
            map.put("code", "02");
            map.put("msg", "货柜编号错误");
        } else {
            try {
                ScaleWeight sw = productlaunchService.getWeitht(sc, cc);
                if (sw == null) {
                    map.put("code", "03");
                    map.put("msg", "秤重信息不存在");
                } else {
                    /*// 计算差值（秒）
                    long diffInMillis = Math.abs(new Date().getTime() - sw.getTime().getTime());
                    long diffInDays = diffInMillis / 1000; // 将毫秒转换为秒
                    if (diffInDays > 60) {
                        map.put("msg", "04");
                        map.put("error", "秤重信息已过期");
                    } else {*/
                    map.put("weight", sw.getWeight());
                    flag = 0;
                    /*}*/
                }
            } catch (Exception e) {
                map.put("code", "05");
                map.put("msg", "获取秤重失败(系统错误，请稍后再试)");
                // 记录日志以便调试
                logger.error("获取秤重失败", e);
            }
        }
        map.put("flag", flag);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }


    /**
     * 获取秤重
     *
     * @return
     */
    public String getWeithts() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String cc = request.getParameter("containerCoding");
        Map<String, Object> map = new HashMap<>();
        int flag = 1; //0:成功，1：失败
        // 输入验证
        if (cc == null || cc.equals("")) {
            map.put("code", "02");
            map.put("msg", "货柜编号错误");
        } else {
            try {
                List<BaseBean> swl = productlaunchService.getWeithts(cc);
                if (swl == null) {
                    map.put("code", "03");
                    map.put("msg", "秤重信息不存在");
                } else {
                    /*// 计算差值（秒）
                    long diffInMillis = Math.abs(new Date().getTime() - sw.getTime().getTime());
                    long diffInDays = diffInMillis / 1000; // 将毫秒转换为秒
                    if (diffInDays > 60) {
                        map.put("msg", "04");
                        map.put("error", "秤重信息已过期");
                    } else {*/
                    map.put("weights", swl);
                    flag = 0;
                    /*}*/
                }
            } catch (Exception e) {
                map.put("code", "05");
                map.put("msg", "获取秤重失败(系统错误，请稍后再试)");
                // 记录日志以便调试
                logger.error("获取秤重失败", e);
            }
        }
        map.put("flag", flag);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    public String getDepotByProid() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String depotid = request.getParameter("depotid");
        Map<String, Object> map = new HashMap<>();
        int flag = 1; //0:成功，1：失败
        // 输入验证
        if (ppId == null || ppId.equals("") || depotid == null || depotid.equals("")) {
            map.put("code", "02");
            map.put("msg", "参数信息错误");
        } else {
            try {
                List<BaseBean> invl = productlaunchService.getDepotByProid(ppId, depotid);
                if (invl == null) {
                    map.put("code", "03");
                    map.put("msg", "库存信息不存在");
                } else {
                    map.put("invl", invl);
                    flag = 0;
                }
            } catch (Exception e) {
                map.put("code", "05");
                map.put("msg", "获取库存失败(系统错误，请稍后再试)");
                // 记录日志以便调试
                logger.error("获取秤重失败", e);
            }
        }
        map.put("flag", flag);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 移动端产品列表
     *
     * @return
     */
    public String getProductByParam() {
        companyId = getCompanyId();
        Map<String, Object> map = new HashMap<>();
        request = ServletActionContext.getRequest();
        String param = request.getParameter("param");
        if (param == null || param.equals("")) {
            map.put("flag", 1);
            map.put("error", 2);
            map.put("meg", "参数错误");
        }
        if (companyId == null || companyId.equals("")) {
            map.put("flag", 1);
            map.put("error", 0);
            map.put("meg", "登录信息错误");
        } else {
            map = productlaunchService.getProductByParam(param,companyId);
        }
        JSONObject json = JSONObject.fromObject(map);
        result = json.toString();
        return "success";
    }

    /**
     *
     * 验证会员促销品
     * @return
     */
    public String checkVip(){
        String r = productlaunchService.checkVip(ppId);

        Map<String, Object> map = new HashMap<>();
        map.put("r", r);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     *
     * VIP会员加促销品
     * @return
     */
    public  String getVipList(){
        tcc = getTcc();

        if(tcc!=null) {
            pageForm = productlaunchService.getVipList(tcc.getSccId(), 30, pageNumber);
            List<Object> cxlist = new ArrayList<Object>();
            if(pageForm!=null) {
                cxlist = productlaunchService.getCxList(pageForm);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("pageForm", pageForm);
            map.put("cxlist", cxlist);
            JSONObject oj = JSONObject.fromObject(map);
            result = oj.toString();
        }
        return "success";
    }

    /**
     *
     * 带有公司列表加主产品3个加对应的促销品，推荐，是按照用户上级，以及北京天太优先，行业就是按行业。
     * @return
     */
    public  String getVipList1(){
        tcc = getTcc();

        String sccid = "";
        if(tcc!=null) {
            sccid = tcc.getSccId();
        }

        HttpServletRequest request = ServletActionContext.getRequest();
        String parameter = request.getParameter("parameter");

        Map<String, Object> map = new HashMap<>();


        pageForm = productlaunchService.getComViplist(sccid, 10, pageNumber,industryId,parameter);
        List<Object> cxlist = new ArrayList<Object>();
        if(pageForm!=null) {
            Map<String,List<Object>> clist = productlaunchService.getproList(pageForm);
            cxlist = productlaunchService.getCxList(clist);
            map.put("pageForm", pageForm);
            map.put("clist", clist);
            map.put("cxlist", cxlist);
        }




        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }
    /**
     * 查询产品
     * @return
     */
    public  String getVipList2(){
        HttpServletRequest request = ServletActionContext.getRequest();

        String parameter = request.getParameter("parameter");

        Map<String, Object> map = new HashMap<>();

        //查询产品
        pageForm = productlaunchService.getVipListALL(10,pageNumber, industryId,parameter);

        List<Object> cxlist = new ArrayList<Object>();
        if(pageForm!=null) {
            cxlist = productlaunchService.getCxList(pageForm);
        }
        map.put("pageForm", pageForm);
        map.put("cxlist", cxlist);

        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }
    /**
     *  获取广告首页
     *
     * @return
     */
    public String getAdvList() {

        advlist =  productlaunchService.advList();

        return "advlist";
    }

    /**
     * 获取店铺信息
     * @return
     */
    public String getShopInfo(){
        tcc = getTcc();
        String sccid = "";
        if(tcc!=null) {
            sccid = tcc.getSccId();
        }
        ContactCompany contactCompany = productlaunchService.getShopInfo(companyId);
        Map<String, String> m = productlaunchService.getJoinFans(sccid, companyId);

        Map<String, Object> map = new HashMap<>();

        map.put("contactCompany", contactCompany);
        map.put("g", m.get("g"));
        map.put("c", m.get("c"));

        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";

    }


    /**
     * 根据公司ID获取公司带会员的产品
     * @return
     */
    public String getVipListCompany(){

        pageForm = productlaunchService.getVipListCompany(5,pageNumber, companyId,productPackaging!=null?productPackaging.getGoodsName():"");

        Map<String, Object> map = new HashMap<>();
        List<Object> cxlist = new ArrayList<Object>();
        if(pageForm!=null) {
            cxlist = productlaunchService.getCxList(pageForm);
        }
        map.put("pageForm", pageForm);
        map.put("cxlist", cxlist);

        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";

    }

    /**
     * 关注取消关注
     * @return
     */
    public String addJoinFans(){
        tcc = getTcc();
        Map<String, Object> map = new HashMap<>();

        if(tcc==null){
            map.put("login","login");


        }else{
            String res =  productlaunchService.addJoinFans(companyId, tcc.getSccId());
            map.put("result",res);

        }

        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";


    }

    public String getProductCountByCo(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyid = request.getParameter("companyid");
        StringBuilder sb=new StringBuilder();
        sb.append("select count(*) from  dt_ProductPackaging p");
        sb.append("where p.companyid = ? and p.showweixin = ? and p.type != ? and ps.state = ?");
        Integer Count=baseBeanService.getConutByByHqlAndParams(sb.toString(),new Object[]{companyid,"01","扫码收款","00"});
        JSONObject jobj = new JSONObject();
        jobj.accumulate("count", count);
        result = jobj;
        return "success";
    }


    public UtboundOrderVo getUtboundOrderVo() {
        return utboundOrderVo;
    }

    public void setUtboundOrderVo(UtboundOrderVo utboundOrderVo) {
        this.utboundOrderVo = utboundOrderVo;
    }

    public DCheck getDcheck() {
        return dcheck;
    }

    public void setDcheck(DCheck dcheck) {
        this.dcheck = dcheck;
    }

    public PTrack getPtrack() {
        return ptrack;
    }

    public void setPtrack(PTrack ptrack) {
        this.ptrack = ptrack;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public GoodsManage getGoodsManage() {
        return goodsManage;
    }

    public void setGoodsManage(GoodsManage goodsManage) {
        this.goodsManage = goodsManage;
    }

    public String getColorvalue() {
        return colorvalue;
    }

    public void setColorvalue(String colorvalue) {
        this.colorvalue = colorvalue;
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public String getSizevalue() {
        return sizevalue;
    }

    public void setSizevalue(String sizevalue) {
        this.sizevalue = sizevalue;
    }

    public String getHtl() {
        return htl;
    }

    public void setHtl(String htl) {
        this.htl = htl;
    }

    public String[] getFunctionList() {
        return functionList;
    }

    public void setFunctionList(String[] functionList) {
        this.functionList = functionList;
    }

    public ProductPackaging getProductPackaging() {
        return productPackaging;
    }

    public void setProductPackaging(ProductPackaging productPackaging) {
        this.productPackaging = productPackaging;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public ProSetup getSetup() {
        return setup;
    }

    public void setSetup(ProSetup setup) {
        this.setup = setup;
    }

    public List<BaseBean> getArrlist() {
        return arrlist;
    }

    public void setArrlist(List<BaseBean> arrlist) {
        this.arrlist = arrlist;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<BaseBean> getProlist() {
        return prolist;
    }

    public void setProlist(List<BaseBean> prolist) {
        this.prolist = prolist;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApId() {
        return apId;
    }

    public void setApId(String apId) {
        this.apId = apId;
    }

    public File[] getPic() {
        return pic;
    }

    public void setPic(File[] pic) {
        this.pic = pic;
    }

    public String[] getPicFileName() {
        return picFileName;
    }

    public void setPicFileName(String[] picFileName) {
        this.picFileName = picFileName;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public List<BaseBean> getList() {
        return list;
    }

    public void setList(List<BaseBean> list) {
        this.list = list;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Map<String, ProSetupSub> getPssMap() {
        return pssMap;
    }

    public void setPssMap(Map<String, ProSetupSub> pssMap) {
        this.pssMap = pssMap;
    }

    public String getPpID() {
        return ppID;
    }

    public void setPpID(String ppID) {
        this.ppID = ppID;
    }

    public String getSccId() {
        return sccId;
    }

    public void setSccId(String sccId) {
        this.sccId = sccId;
    }

    public String getFalg() {
        return falg;
    }

    public void setFalg(String falg) {
        this.falg = falg;
    }

    public String getPseudoCompanyName() {
        return pseudoCompanyName;
    }

    public void setPseudoCompanyName(String pseudoCompanyName) {
        this.pseudoCompanyName = pseudoCompanyName;
    }

    public Object getLogpath() {
        return logpath;
    }

    public void setLogpath(Object logpath) {
        this.logpath = logpath;
    }

    public List<SchProCategory> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SchProCategory> typeList) {
        this.typeList = typeList;
    }

    public String getDelList() {
        return delList;
    }

    public void setDelList(String delList) {
        this.delList = delList;
    }

    public String getAttrinames() {
        return attrinames;
    }

    public void setAttrinames(String attrinames) {
        this.attrinames = attrinames;
    }

    public String getAttrinamec() {
        return attrinamec;
    }

    public void setAttrinamec(String attrinamec) {
        this.attrinamec = attrinamec;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getType() {
        return type;
    }

    public String getUnitOfMeasureCode() {
        return unitOfMeasureCode;
    }

    public void setUnitOfMeasureCode(String unitOfMeasureCode) {
        this.unitOfMeasureCode = unitOfMeasureCode;
    }

    public void setType(String type) {
        this.type = type;

    }

    public CAccount getAccount() {
        if (account == null) setAccount(null);
        return account;
    }

    public void setAccount(CAccount account) {
        if (account == null) {
            this.account = (CAccount) ActionContext.getContext().getSession().get("account");
        } else {
            this.account = account;
        }
    }

    public TEshopCusCom getTcc() {
        if (tcc == null) setTcc(null);
        return tcc;
    }

    public void setTcc(TEshopCusCom tcc) {
        if (tcc == null) {
            this.tcc = (TEshopCusCom) ActionContext.getContext().getSession().get("key_shop_cus_com");
        } else {
            this.tcc = tcc;
        }
    }

    public String getUser() {
        if (user == null) setUser(null);
        return user;
    }

    public void setUser(String user) {
        if (user == null) {
            tcc = getTcc();
            if (tcc != null) {
                if (tcc.getAccount() != null && !tcc.getAccount().equals("")) {
                    this.user = tcc.getAccount();
                }
            } else {
                this.user = null;
            }
        } else {
            this.user = user;
        }
    }

    public String getCompanyId() {
        if (companyId == null) setCompanyId(null);
        return companyId;
    }

    public void setCompanyId(String companyId) {
        if (companyId == null || companyId.equals("")) {
            tcc = getTcc();
            account = getAccount();
            if (account != null) {
                if (account.getCompanyID() != null && !account.getCompanyID().equals("")) {
                    this.companyId = account.getCompanyID();
                }
            } else if (tcc != null) {
                if (tcc.getCompanyId() != null && !tcc.getCompanyId().equals("")) {
                    this.companyId = tcc.getCompanyId();
                }
            }
        } else {
            this.companyId = companyId;
        }
    }

    public Map<String, ScaleWeight> getProWeight() {
        return proWeight;
    }

    public void setProWeight(Map<String, ScaleWeight> proWeight) {
        this.proWeight = proWeight;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public List<BaseBean> getAdvlist() {
        return advlist;
    }

    public void setAdvlist(List<BaseBean> advlist) {
        this.advlist = advlist;
    }
}
