package com.tiantai.wfj.front;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.ProductlaunchService;
import com.tiantai.wfj.service.ProductsMmanagService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CCode;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.company.ScaleGoods;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.production.*;
import hy.ea.service.CCodeService;
import hy.ea.service.UpLoadFileService;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.*;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProductsManagAction {
    private Logger logger = LoggerFactory.getLogger(ProductsManagAction.class);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private ProductsMmanagService pmService;
    @Resource
    private CCodeService codeService;
    @Resource
    private UpLoadFileService fileService;
    @Resource
    private ProductlaunchService productlaunchService;
    private String companyID;
    private PageForm pageForm;
    private String result;
    private File file;
    private File fileLogo;
    private File[] pic;// 产品描述图片
    private String[] picFileName;

    private GoodsManage goodsManage;// 物品
    private ProductPackaging productPackaging;// 产品
    private List<BaseBean> prolist;// 产品
    private List<BaseBean> arrilist;// 物品图片列表
    private List<BaseBean> arrvlist;// 物品视频列表
    private String[] functionList;


    public String getProductAjax() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> map = new HashMap<String, Object>();

        String staffid = request.getParameter("staffid");
        String goodsName = request.getParameter("goodsName");
        String type = request.getParameter("type");
        String barCode = request.getParameter("barCode");
        String search = request.getParameter("search");

        try {
            map = pmService.getProductList(search, companyID, goodsName, type, barCode, pageForm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class,
                new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject jsonArray = JSONObject.fromObject(pageForm, jsonConfig);
        JSONObject obj = JSONObject.fromObject(map, jsonConfig);
        result = obj.toString();
        return "success";
    }

    /**
     * 根据companyID和codeID查询其子节点
     */
    public String getListCCodeByPID() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> map = new HashMap<String, Object>();
        String codeID = request.getParameter("codeID");
        List<CCode> codeList = codeService.getCCodeListByPID(companyID, codeID);
        map.put("codeList", codeList);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 手机端发布和库存设置
     *
     * @return
     */
    public String phproductslaunch() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String colorvalue = request.getParameter("colorvalue");
        String sizevalue = request.getParameter("sizevalue");
        String attrinames = request.getParameter("attrinames");
        String attrinamec = request.getParameter("attrinamec");
        String attri = request.getParameter("attri");
        String ImagesPath = request.getParameter("ImagesPath");
        String VideoPath = request.getParameter("VideoPath");
        String htl = request.getParameter("htl");
        String unitOfMeasureCode = request.getParameter("unitOfMeasureCode");
        String flag = request.getParameter("flag");

        List<BaseBean> beans = new ArrayList<>();
        GoodsManage gm = null;
        ProductPackaging pp = null;
        String goodsId = null;
        // 编号处理
        String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in (select c.companyID from Company c where c.groupCompanySn=?)  and vt.typeID=? ";
        // 物品设计
        if (goodsManage == null || goodsManage.getGoodsID() == null
                || goodsManage.getGoodsID().equals("")) {
            gm = new GoodsManage();
            gm.setGoodsID(serverService.getServerID("goodsID"));
            goodsId = gm.getGoodsID();
            System.out.print(goodsId);

            if (companyID != null && !companyID.equals("")) {
                gm.setGoodsName(goodsManage.getGoodsName());
                if (goodsManage.getTypeID() == null || goodsManage.getTypeID().equals("")) {
                    gm.setTypeID("其他");
                } else {
                    gm.setTypeID(goodsManage.getTypeID());
                }

                Company company = (Company) baseBeanService
                        .getBeanByHqlAndParams(
                                "from Company where companyID= ?",
                                new Object[]{companyID});
                Object[] params = {company.getGroupCompanySn(), gm.getTypeID()};
                // 获取拼音码
                String pinyin = ToChineseFirstLetter.getFirstLetter(gm
                        .getTypeID());
                String Upstr = pinyin.toUpperCase();// 转换为大写
                int goodscodingnum = baseBeanService.getConutByByHqlAndParams(
                        hql, params);
                DecimalFormat form = new DecimalFormat("000000");
                String ss = form.format(goodscodingnum + 1);
                gm.setGoodsCoding(Upstr + "_" + ss);
                gm.setFiveClear("4");
                gm.setCompanyID(companyID);
                gm.setGoodsState("00");
                gm.setCreatedate(new Date());
                gm.setTradeID(goodsManage.getTradeID());
                gm.setTradeName(goodsManage.getTradeName());
                gm.setTradeCode(goodsManage.getTradeCode());
                gm.setBarCode(goodsManage.getBarCode());// 条码
                gm.setBrand(goodsManage.getBrand());//品牌
                if (fileLogo != null) {
                    try {
                        Map<String, Object> map = pmService.upLoadFile(null, null, fileLogo.getName(), fileLogo, ServletActionContext.getRequest().getSession()
                                .getServletContext().getRealPath("\\"), companyID);
                        gm.setLogoPath(map.get("path").toString());//品牌log路径
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                gm.setIsScale(goodsManage.getIsScale());//是否需要电子秤打印条码
                gm.setVariableID(goodsManage.getVariableID());//单位
                // 保存物品主图
                if (ImagesPath != null && !ImagesPath.equals("")) {
                    pmService.saveAttriProduction(ImagesPath, gm, beans, "I");
                }
                // 保存视频
                if (VideoPath != null && !VideoPath.equals("")) {
                    pmService.saveAttriProduction(VideoPath, gm, beans, "V");
                }
                beans.add(gm);
                // 产品设计
                if (gm != null && !gm.getGoodsID().equals("")) {
                    pp = new ProductPackaging();
                    pp.setPackagingDate(new Date());
                    pp.setPpID(serverService.getServerID("p"));
                    pp.setProductCode(productPackaging.getProductCode());
                    pp.setProducttype(productPackaging.getProducttype());
                    pp.setGradeid(productPackaging.getGradeid());//产品等级id
                    pp.setGradeName(productPackaging.getGradeName());//产品等级
                    pp.setGoodsID(gm.getGoodsID());
                    pp.setGoodsName(gm.getGoodsName());
                    pp.setImage(gm.getPhotoPath());
                    pp.setTradeCode(goodsManage.getTradeCode());
                    pp.setTradeID(goodsManage.getTradeID());
                    pp.setTradeName(goodsManage.getTradeName());
                    pp.setType(gm.getTypeID());
                    pp.setVariableID(gm.getVariableID());
                    pp.setShowweixin("00");
                    pp.setDelStatus("00");
                    pp.setProductstate("01");//生产流程状态 初始：00；模拟：01   05：已设置利润率
                    pp.setFiveClear("4");
                    pp.setCompanyID(companyID);
                    pp.setPackagingDate(new Date());
                    pp.setBarCode(goodsManage.getBarCode());
                    pp.setBrand(goodsManage.getBrand());
                    pp.setLogo(goodsManage.getLogoPath());//品牌log路径
                    pp.setIsScale(goodsManage.getIsScale());
                    pp.setYjstatus("00");// 未设置佣金
                    //未设置佣金默认给:00
                    pp.setWholesaleStatus("00");
                    pp.setVipStatus("00");
                    pp.setActivityStatus("00");
                    pp.setField("01");//00： 字段  01：产品  02：组织机构
                    pp.setInvNum("0");//初始库存
                    pp.setStockSize(0);//库存数量
                    pp.setQualified("1");//用于产品是否合格ljc  0不合格，1合格
                    pp.setTemporary("0");//用于上传图片记忆0：未发布，1：发布成功 ljc
                    beans.add(pp);
                }
            }
        } else {
            // 修改
            goodsId = goodsManage.getGoodsID();
            gm = (GoodsManage) baseBeanService
                    .getBeanByHqlAndParams("from GoodsManage where goodsID=?",
                            new Object[]{goodsId});
            if (!gm.getTypeID().equals(goodsManage.getTradeID())) {
                if (goodsManage.getTypeID() == null || goodsManage.getTypeID().equals("")) {
                    gm.setTypeID("其他");
                } else {
                    gm.setTypeID(goodsManage.getTypeID());
                }
                Company company = (Company) baseBeanService
                        .getBeanByHqlAndParams(
                                "from Company where companyID= ?",
                                new Object[]{companyID});
                Object[] params = {company.getGroupCompanySn(), gm.getTypeID()};
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
            gm.setGoodsName(goodsManage.getGoodsName());
            gm.setTradeName(goodsManage.getTradeName());
            gm.setTradeCode(goodsManage.getTradeCode());
            gm.setBarCode(goodsManage.getBarCode());// 条码
            gm.setBrand(goodsManage.getBrand());//品牌
            gm.setIsScale(goodsManage.getIsScale());//是否需要电子秤打印条码
            if (fileLogo != null) {
                try {
                    Map<String, Object> map = pmService.upLoadFile(null, null, fileLogo.getName(), fileLogo, ServletActionContext.getRequest().getSession()
                            .getServletContext().getRealPath("\\"), companyID);
                    gm.setLogoPath(map.get("path").toString());//品牌log路径
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            gm.setVariableID(goodsManage.getVariableID());//单位
            pp = (ProductPackaging) baseBeanService
                    .getBeanByHqlAndParams(
                            "from ProductPackaging where ppid=?",
                            new Object[]{productPackaging.getPpID()});
            pp.setProductCode(productPackaging.getProductCode());
            pp.setProducttype(productPackaging.getProducttype());
            pp.setGradeid(productPackaging.getGradeid());//产品等级id
            pp.setGradeName(productPackaging.getGradeName());//产品等级
            pp.setVariableID(goodsManage.getVariableID());
            pp.setTradeCode(gm.getTradeCode());
            pp.setTradeID(gm.getTradeID());
            pp.setTradeName(gm.getTradeName());
            pp.setBrand(goodsManage.getBrand());
            pp.setBarCode(goodsManage.getBarCode());
            pp.setLogo(goodsManage.getLogoPath());//品牌log路径
            pp.setIsScale(goodsManage.getIsScale());
            // 保存物品主图
            if (ImagesPath != null && !ImagesPath.equals("")) {
                pmService.saveAttriProduction(ImagesPath, gm, beans, "I");
            }
            // 保存视频
            if (VideoPath != null && !VideoPath.equals("")) {
                pmService.saveAttriProduction(VideoPath, gm, beans, "V");
            }
            pp.setImage(gm.getPhotoPath());
            beans.add(pp);
            beans.add(gm);

        }

        // 保存产品颜色，规格
        String sql = "delete AttriProduction where goodsid=? and type='1' ";
        String sql2 = "delete AttriProduction where goodsid=? and type='0' ";
        String sql3 = "delete AttriProduction where goodsid=? and type='4' ";
        if (colorvalue != null && !colorvalue.equals("")) {
            pmService.saveAttriProduction(colorvalue, attrinames, goodsId, beans, "1");
        }
        if (sizevalue != null && !sizevalue.equals("")) {
            pmService.saveAttriProduction(sizevalue, attrinamec, goodsId, beans, "0");
        }
        if (attri != null && !attri.equals("")) {
            pmService.saveAttriProduction(attri, "", goodsId, beans, "4");
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
                            companyID,
                            "/goodfunction/"
                                    + Utilities.getDateString(new Date(),
                                    "yyyy-MM-dd"));
                    htl = newhtl + "img" + i + "\" src=\"" + basePath
                            + photopath
                            + "\" style=\"display: block; width: 100%;"
                            + oldhtl;
                }
            }
            url = pmService.saveContentToFile(htl.trim());
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
        //保存电子秤相关
        if ("0".equals(goodsManage.getIsScale())) {
            ScaleGoods scaleGoods = (ScaleGoods) baseBeanService.getBeanByHqlAndParams("from ScaleGoods where goodsID=?", new Object[]{gm.getGoodsID()});
            if (scaleGoods == null) {
                scaleGoods = new ScaleGoods();
                scaleGoods.setSgID(serverService.getServerID("sgid"));
                scaleGoods.setCompanyID(companyID);

                String phql = "select count(*) from ScaleGoods where companyID = ? ";
                int pcount = baseBeanService.getConutByByHqlAndParams(phql, new Object[]{companyID});
                if (pcount != 0) {
                    String ScaleGoodSsql = "select max(to_number(plu)) from dtScaleGoods where companyID= ?";
                    pcount = baseBeanService.getConutByBySqlAndParams(ScaleGoodSsql,
                            new Object[]{companyID});
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
        try {
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{sql, sql2, sql3, hql_del}, new Object[]{gm.getGoodsID()});
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

    //上传图片
    public String uplodFile() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> map = new HashMap<String, Object>();
        String name = request.getParameter("name");
        String chunk = request.getParameter("chunk");
        String chunks = request.getParameter("chunks");
        String path = ServletActionContext.getRequest().getSession()
                .getServletContext().getRealPath("\\");
        try {
            map = pmService.upLoadFile(chunk, chunks, name, file, path, companyID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 产品规格颜色
     *
     * @return
     * @desc ppId 产品id
     */
    public String getAttr() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String ppId = request.getParameter("ppId");
        String goodsId = request.getParameter("goodsId");

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
     * 跳转产品发布页面
     *
     * @return
     */
    public String toProductsLaunch() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String code = request.getParameter("barcode");
        String ppId = request.getParameter("ppId");
        String goodsId = request.getParameter("goodsId");
        StringBuffer sql = new StringBuffer();
        List<Object> parame = null;
        parame = new ArrayList<>();
        sql.append("select gm.goodsid,pp.ppid,pp.goodsname,pp.tradeID,pp.tradeCode,pp.tradename,pp.companyid,");
        sql.append(" gm.isscale,gm.brand,s.unitofmeasurecode,gm.barcode,");
        sql.append(" pp.producttype,pp.productCode,pp.type,pp.variableID,gm.logoPath,pp.gradeid,pp.gradeName");
        sql.append(" from dtGoodsManage gm left join dt_productpackaging pp on gm.goodsid = pp.goodsid");
        sql.append(" left join dtscalegoods s on s.goodsid = gm.goodsid");
        sql.append(" where pp.companyid = ?");
        parame.add(companyID);
        if (code != null && !code.equals("") && (ppId == null || ppId.equals(""))) {
            sql.append(" and pp.barcode = ?");
            parame.add(code);
        } else {
            sql.append(" and pp.ppid = ?");
            parame.add(ppId);
        }
        prolist = baseBeanService.getListBeanBySqlAndParams(sql.toString(), parame.toArray());
        if (goodsId != null && !goodsId.equals("")) {
            arrilist = baseBeanService
                    .getListBeanByHqlAndParams(
                            "from AttriProduction where goodsid=? and type='2' order by sort",
                            new Object[]{goodsId});
            arrvlist = baseBeanService
                    .getListBeanByHqlAndParams(
                            "from AttriProduction where goodsid=? and type='3' order by sort",
                            new Object[]{goodsId});
        }
        String hql = "from GoodFunction where goodsid= ?";
        if (goodsId != null && !goodsId.equals("")) {
            GoodFunction goodFun = (GoodFunction) baseBeanService
                    .getBeanByHqlAndParams(hql, new Object[]{goodsId});
            if (goodFun != null) {
                String str = pmService.getContentFromFile(goodFun.getUrl());
                if (str.length() > 0) {
                    functionList = str.split("#z");
                }
            }
        }
        String ret = request.getParameter("ret");
        request.setAttribute("ret", ret);
        return "add";
    }

    /**
     * 获取规格
     *
     * @return
     */
    public String specifications() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String goodsId = request.getParameter("goodsId");
        String v = request.getParameter("v");
        Map<String, Object> mapa = new HashMap<String, Object>();
        List<String> parma = new ArrayList<>();
        String hql = " from AttriProduction where goodsid=?";
        parma.add(goodsId);
        if (v != null && v.equals("4")) {
            hql += " and type=?";
            parma.add("4");
        } else {
            hql += " and (type=? or type=?)";
            parma.add("0");
            parma.add("1");
        }
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, parma.toArray());
        if (list.size() > 0) {
            if (v.equals("4")) {
                mapa.put("attri", list);
            } else {
                String a = null;//颜色
                String b = null;//尺码
                List<BaseBean> clist = new ArrayList<BaseBean>();//颜色值
                List<BaseBean> slist = new ArrayList<BaseBean>();//尺码值
                for (int i = 0; i < list.size(); i++) {
                    AttriProduction at = (AttriProduction) list.get(i);

                    if (i == 0) {
                        a = at.getAttriname() + at.getType();
                    }
                    if (a.substring(0, a.lastIndexOf('1')).equals(at.getAttriname())) {
                        clist.add(at);
                    } else {
                        b = at.getAttriname() + at.getType();
                        slist.add(at);
                    }
                }
                if (a != null && !a.equals("") && clist != null && clist.size() > 0) {
                    mapa.put(a, clist);
                }
                if (b != null && !b.equals("") && slist != null && slist.size() > 0) {
                    mapa.put(b, slist);
                }
            }
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.accumulate("map", mapa);
        result = jsonObj.toString();
        return "success";
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getCompanyID() {
        return companyID;
    }

    public GoodsManage getGoodsManage() {
        return goodsManage;
    }

    public void setGoodsManage(GoodsManage goodsManage) {
        this.goodsManage = goodsManage;
    }

    public ProductPackaging getProductPackaging() {
        return productPackaging;
    }

    public void setProductPackaging(ProductPackaging productPackaging) {
        this.productPackaging = productPackaging;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
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

    public List<BaseBean> getProlist() {
        return prolist;
    }

    public void setProlist(List<BaseBean> prolist) {
        this.prolist = prolist;
    }

    public String[] getFunctionList() {
        return functionList;
    }

    public void setFunctionList(String[] functionList) {
        this.functionList = functionList;
    }

    public List<BaseBean> getArrilist() {
        return arrilist;
    }

    public void setArrilist(List<BaseBean> arrilist) {
        this.arrilist = arrilist;
    }

    public List<BaseBean> getArrvlist() {
        return arrvlist;
    }

    public void setArrvlist(List<BaseBean> arrvlist) {
        this.arrvlist = arrvlist;
    }

    public void setPicFileName(String[] picFileName) {
        this.picFileName = picFileName;
    }

    public File getFileLogo() {
        return fileLogo;
    }

    public void setFileLogo(File fileLogo) {
        this.fileLogo = fileLogo;
    }
}