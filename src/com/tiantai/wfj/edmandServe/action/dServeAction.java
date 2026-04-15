package com.tiantai.wfj.edmandServe.action;


import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.edmandServe.bo.DemandDetail;
import com.tiantai.wfj.edmandServe.bo.DemandServe;
import com.tiantai.wfj.edmandServe.bo.TCtomerWorktype;
import com.tiantai.wfj.edmandServe.service.DserveService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffCertificate;
import hy.ea.human.service.StaffService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

/**
 * Created by lf on 2017-07-18.
 * 发布需求抢单action
 */

@Controller
@Scope("prototype")
public class dServeAction {
    @Resource
    private DserveService dsservice;
    @Resource
    private BaseBeanService bbservice;
    @Resource
    private StaffService staffService;
    @Resource
    private UpLoadFileService fileService;
    private DemandDetail demandDetail;
    private TCtomerWorktype tcw;
    private DemandServe demandServe;
    private Object result;
    private String tle;//判断页面是否有表头 1为有  0为没有
    private TEshopCusCom tcc;
    private String companyID;
    private CAccount cAccount;
    private Staff staff;
    private File photos; //文件
    private String photosFileName; //文件名称
    private String photosContentType; //文件类型
    private String originPage; //区分页面跳转
    private String otype; //来源 1:个人 2:企业

    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
    SessionWrap sw = SessionWrap.getInstance();

    /**
     * 跳转到添加页面
     * http://localhost:8080//ea/dserve/ea_toaddpage.jspa?staffid=cstaff201509139K6KBSW8TV0000001934&sccId=TEshopCusCom20160524Q6YPCX3UGD0000004414
     *
     * @return
     */
    public String toaddpage() {
        HttpServletRequest request = ServletActionContext.getRequest();
        TEshopCusCom t = getTcc();
        CAccount a = getcAccount();
        originPage =getOriginPage();
        try {
            String id=getOtype();
            request.setAttribute("cid", id);//人员id
            request.setAttribute("sccId", t.getSccId());//数字地球帐号id
            request.setAttribute("user", t.getAccount());//数字地球帐号
            request.setAttribute("name", a.getStaffName());//数字地球帐号名称
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "industryList";
    }

    /**
     * 发布需求详情保存（发布需求）
     * http://localhost:8080//ea/dserve/ea_saveDetail.jspa
     *
     * @return
     */
    public String saveDetail() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            tcc = getTcc();
            cAccount=getcAccount();
            originPage=getOriginPage();
            String id=getOtype();
            demandDetail.setDdtype(otype);
            demandDetail.setDdcid(id);
            demandDetail.setDdsccid(tcc.getSccId());
            demandDetail.setDdstaffid(tcc.getStaffid());
            if (tcc != null) {
                if (demandDetail != null) {
                    if (photos != null) {
                        String path = ServletActionContext.getRequest()
                                .getSession().getServletContext().getRealPath("/");
                        String photoPath = fileService
                                .savePhoto(path, photosFileName == null ? photosContentType : photosFileName, photos, "1", "/human/credentials/"
                                        + Utilities.getDateString(new Date(), "yyyy-MM-dd"));
                        demandDetail.setPhoto(photoPath);
                    }
                    //demandDetail.setDdexpectdate(Utilities.getDateFromString(demandDetail.getDdexdate(), "yyyy-MM-dd"));

                    String flag = dsservice.saveDetail(demandDetail);
                    map.put("flag", flag);
                }
            } else {
                map.put("flag","1");
            }
            JSONObject json = JSONObject.fromObject(map);
            this.result = json;
        } catch (Exception e) {
            map.put("flag","1");
        }

        return "success";
    }

    public String toPage_success() {
        return "toPage_success";
    }

    /**
     * 根据发布人查询需求列表
     * http://localhost:8080//ea/dserve/ea_detailListBySccid.jspa?sccid=A8F6A67664C64B4A94F8C3AC3017A7B6
     *
     * @return
     */
    public String detailListBySccid() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");//区分页面按钮
        originPage=getOriginPage();
        System.out.println("originPage = " + originPage);
        System.out.println("otype = " + otype);
        //cAccount = getcAccount();
        try {
            String id=getOtype();
            //String tab=request.getParameter("tab");//0:(我的发布)发布过的需求但还未确认抢单对象  1:(发布记录)发布者已经选了抢单用户了（状态）以及 超时结束的
            List<BaseBean> detailList = dsservice.detailListBySccid(id, type,otype);
            request.setAttribute("detailList", detailList);
            //List<BaseBean> detailList2 = dsservice.detailListBySccid(sccid, "1");
            //request.setAttribute("detailList2", detailList2);
            request.setAttribute("dlsccid", id);
            request.setAttribute("staffid", getcAccount().getStaffID());//人员id
        }catch (Exception e){
            e.printStackTrace();
        }
        return "ddlist";
    }

    /**
     * 跳转需求列表页面
     * http://localhost:8080/ea/dserve/ea_toPage_demandListBydssccid.jspa?sccid=A8F6A67664C64B4A94F8C3AC3017A7B6
     *
     * @return
     */
    public String toPage_demandListBydssccid() {
        HttpServletRequest request = ServletActionContext.getRequest();
        originPage=getOriginPage();
        System.out.println("originPage = " + originPage);
        System.out.println("otype = " + otype);
        try {
            String id=getOtype();
            request.setAttribute("sccid", id);//抢单人id
            List<Object> ObjectList = dsservice.detailListBydssccid(id);
            request.setAttribute("ObjectList", ObjectList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "detailListBydssccid";
    }

    /**
     * 根据抢单人查询需求列表
     *
     * @return
     */
    public String detailListBydssccid() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Integer pageNumber = Integer.parseInt(request.getParameter("pagenumber"));//页数
        originPage=getOriginPage();
        System.out.println("originPage = " + originPage);
        System.out.println("otype = " + otype);
        try {
            String id=getOtype();
            PageForm pageForm = dsservice.detailListBydssccid(id, pageNumber);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageForm", pageForm);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            JSONObject json = new JSONObject();
            json.putAll(map, jsonConfig);
            this.result = json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 根据id查询需求详情
     * http://localhost:8080//ea/dserve/ea_detailByDdid.jspa?ddid=ddetail20170719EVHN3RH3MH0000000002
     *
     * @return
     */
    public String detailByDdid() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String ddid = request.getParameter("ddid");//需求id
        demandDetail = dsservice.detailByDdid(ddid);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("demandDetail", demandDetail);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject json = new JSONObject();
        json.putAll(map, jsonConfig);
        this.result = json.toString();
        return "success";
    }

    /**
     * 根据需求id查询已抢单责任人列表
     *
     * @return
     */
    public String serveListByDdid() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String ddid = request.getParameter("ddid");//需求id
        //String dlsccid = request.getParameter("dlsccid");
        String dlsccid = getTcc().getSccId();
        request.setAttribute("wtvalue", request.getParameter("wtvalue"));//工种
        List<Object> serveList = dsservice.serveListByDdid(ddid);
        request.setAttribute("serveList", serveList);
        request.setAttribute("dlsccid", dlsccid);
        return "serveList";
    }

    /**
     * 分页查询需求列表
     * http://localhost:8080//ea/dserve/sajax_ea_detailListBywts.jspa?pageNumber=1&wts=scode20170715cnjcrn5jm20000000258,scode20170715cnjcrn5jm20000000508
     *
     * @return
     */
    public String detailListBywts() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String pagenumber = request.getParameter("pagenumber");
        Integer pageNumber = Integer.parseInt(pagenumber == null || pagenumber.equals("") ? "0" : pagenumber);//页数
        String wts = request.getParameter("wts");//工中id集合
        String wtype = request.getParameter("wtype");//null or 0:抢单 1:派单
        PageForm pageForm = dsservice.detailListBywts(pageNumber, wts, wtype);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject json = new JSONObject();
        json.putAll(map, jsonConfig);
        this.result = json.toString();
        return "success";
    }

    /**
     * 跳转需求列表页面
     * http://localhost:8080//ea/dserve/ea_toPage_demandList.jspa?wts=scode20170715cnjcrn5jm20000000258,scode20170715cnjcrn5jm20000000508&sccid=A8F6A67664C64B4A94F8C3AC3017A7B6
     *
     * @return
     */
    public String toPage_demandList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String wts = request.getParameter("wts");
        String type = request.getParameter("type");//类别 1:个人 2:企业
        String cwcid = "";
        if (wts == null || wts.equals("") || wts.equals("undefined")) {
            wts = "bType20250830AKEYDEP5ZJ0000001270";
            try {
                originPage=getOriginPage();
                String id=getOtype();
                List<BaseBean> beanList = dsservice.wtypeListBySccid(id, null, type);
                if (beanList != null && beanList.size() > 0) {
                    //wts=wts+",";
                    request.setAttribute("cwts", beanList.size());
                    for (int i = 0; i < beanList.size(); i++) {
                        TCtomerWorktype t = (TCtomerWorktype) beanList.get(i);
                        wts = wts + ((wts == null || wts.equals("")) ? "" : ",") + t.getCwscodeid();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("wts", wts);//工中id集合
        request.setAttribute("sccid", getTcc().getSccId());//工中id集合
        return "detailList";
    }

    /**
     * 跳转需求列表页面
     * http://localhost:8080//ea/dserve/ea_toPage_demandList.jspa?wts=scode20170715cnjcrn5jm20000000258,scode20170715cnjcrn5jm20000000508&sccid=A8F6A67664C64B4A94F8C3AC3017A7B6
     *
     * @return
     */
    public String ajax_demandList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<BaseBean> beanList =new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        //String type = request.getParameter("type");//类别 1:个人 2:企业
        try {
            //originPage=getOriginPage();
            //String id=getOtype();
            //beanList = dsservice.wtypeListBySccid(id, null, originPage);
            beanList = dsservice.wtypeListBySccid(getTcc().getStaffid(), null, originPage);
            if (beanList != null && beanList.size() > 0) {
                map.put("cwts", beanList.size());
                String wts = "";
                for (int i = 0; i < beanList.size(); i++) {
                    TCtomerWorktype t = (TCtomerWorktype) beanList.get(i);
                    wts = wts + ((wts == null || wts.equals("")) ? "" : ",") + t.getCwscodeid();
                }
                map.put("wts", wts);
                map.put("sccid", getTcc().getSccId());
            }
        }catch (Exception e){
            map.put("error", e.getMessage());
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 根据时间查询需求列表
     * http://localhost:8080//ea/dserve/sajax_ea_detailListByDate.jspa?pageSize=5&wts=scode20170715cnjcrn5jm20000000258,scode20170715cnjcrn5jm20000000508
     *
     * @return
     */
    public String detailListByDate() {
        JSONObject ret = new JSONObject();
        HttpServletRequest request = ServletActionContext.getRequest();
        String pageSize = request.getParameter("pageSize");//条数
        String wts = request.getParameter("wts");//工中id集合
        String wtype = request.getParameter("wtype");//null or 0:抢单 1:派单
        //otype = request.getParameter("type");//类别 1:个人 2:企业
        if (wtype == null || wtype.isEmpty() || wtype.equals("0")) {
            if (wts == null || wts.equals("") || wts.equals("undefined")) {
                wts = "bType20250830AKEYDEP5ZJ0000001270";
                try {
                    originPage=getOriginPage();
                    String id=getOtype();
                    List<BaseBean> beanList = dsservice.wtypeListBySccid(id, null, otype);
                    if (beanList != null && beanList.size() > 0) {
                        //wts=wts+",";
                        request.setAttribute("cwts", beanList.size());
                        for (int i = 0; i < beanList.size(); i++) {
                            TCtomerWorktype t = (TCtomerWorktype) beanList.get(i);
                            wts = wts + ((wts == null || wts.equals("")) ? "" : ",") + t.getCwscodeid();
                        }
                    }
                } catch (Exception e) {
                    ret.accumulate("flag", "2");
                    ret.accumulate("error", e.getMessage());
                }
            }
            if (wts == null || wts.equals("") || wts.equals("undefined")) {
                ret.accumulate("flag", "0");
                ret.accumulate("error", "没有工种认证！");
            } else {
                try {
                    List<BaseBean> detailList = dsservice.detailListByDate(pageSize, wts, wtype);
        /*JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());*/
                    ret.accumulate("detailList", detailList);
                    ret.accumulate("flag", "1");
                } catch (Exception e) {
                    ret.accumulate("flag", "2");
                    ret.accumulate("error", e.getMessage());
                }
            }
        } else {
            try {
                List<BaseBean> detailList = dsservice.detailListByDate(pageSize, null, wtype);
                ret.accumulate("detailList", detailList);
                ret.accumulate("flag", "1");
            } catch (Exception e) {
                ret.accumulate("flag", "2");
                ret.accumulate("error", e.getMessage());
            }
        }
        result = ret;
        return "success";
    }

    /**
     * 查询行业
     * http://localhost:8080//ea/dserve/sajax_ea_industryList.jspa
     *
     * @return
     */
    public String industryList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String level = request.getParameter("level");
        String pid = request.getParameter("pid");//条数
        String typeName = request.getParameter("typeName");//名称
        List<BaseBean> beanList = new ArrayList<>();
        if (typeName == null || typeName.equals("")) {
            beanList = dsservice.industryListNew(level, pid);
        } else {
            beanList = dsservice.industryListNew(typeName);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("beanList", beanList);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 保存抢单数据
     * http://localhost:8080//ea/dserve/sajax_ea_saveServe.jspa?sccid=A8F6A67664C64B4A94F8C3AC3017A7B6&ddid=ddetail20170719EVHN3RH3MH0000000003
     *
     * @return
     */
    public String saveServe() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> map = new HashMap<String, Object>();
        //String sccid = request.getParameter("sccid");//抢单人帐号id
        String sccid = getTcc().getSccId();
        String ddid = request.getParameter("ddid");//需求id
        String dsaddress = request.getParameter("dsaddress");//抢单人地址
        String coordinate = request.getParameter("coordinate");//抢单人经纬度
        try {
            originPage=getOriginPage();
            String id=getOtype();
            if (sccid != null && !sccid.equals("") && ddid != null && !ddid.equals("")) {
                String flag = dsservice.saveServe(ddid, sccid,id,otype,dsaddress, coordinate);
                map.put("flag", flag);
            } else {
                map.put("flag", "参数传输失败！");
            }
        } catch (Exception e) {
            map.put("flag", e.getMessage());
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo;
        return "success";
    }

    /**
     * 确认接单人员
     * http://localhost:8080//ea/dserve/sajax_ea_updateDetail.jspa?dsid=dserve201707192IGJZDGCCK0000000001&ddid=ddetail20170719EVHN3RH3MH0000000003
     *
     * @return
     */
    public String updateDetail() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> map = new HashMap<String, Object>();
        //String dsid = request.getParameter("dsid");//抢单人帐号id
        String dsid = getTcc().getSccId();
        String ddid = request.getParameter("ddid");//需求id
        if (dsid != null && !dsid.equals("") && ddid != null && !ddid.equals("")) {
            String flag = dsservice.updateDetail(ddid, dsid);
            map.put("flag", flag);
        } else {
            map.put("flag", "参数传输失败！");
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 保存工种
     * http://localhost:8080//ea/dserve/sajax_ea_saveWtype.jspa?user=dserve201707192IGJZDGCCK0000000001&scodeid=ddetail20170719EVHN3RH3MH0000000003&cwvalue=ddetail20170719EVHN3RH3MH0000000003
     *
     * @return
     */
    public String saveWtype() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> map = new HashMap<String, Object>();
        //String user = request.getParameter("user");//帐号
        String user = getTcc().getAccount();
        //String scodeid = request.getParameter("scodeid");//工种id
        //String cwvalue = request.getParameter("cwvalue");//工种值
        if (tcw != null) {
            map = dsservice.saveWtype(user, tcw);
        } else {
            map.put("flag", "参数传输失败！");
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 删除工种
     * http://localhost:8080//ea/dserve/sajax_ea_delWtype.jspa?cwtkey=dserve201707192IGJZDGCCK0000000001
     *
     * @return
     */
    public String delWtype() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> map = new HashMap<String, Object>();
        String cwtid = request.getParameter("cwtid");//工种key
        if (cwtid != null && !cwtid.equals("")) {
            String flag = dsservice.delWtype(cwtid);
            map.put("flag", flag);
        } else {
            map.put("flag", "参数传输失败！");
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    // 查看工种详情
    public String view() {
        TCtomerWorktype cert = dsservice.getWtypeByKey(tcw.getCwkey());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cert", cert);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject json = new JSONObject();
        json.putAll(map, jsonConfig);
        this.result = json.toString();
        return "success";
    }

    /**
     * 查询工种列表
     * http://localhost:8080/ea/dserve/ea_wtypeListBySccid.jspa?sccid=A8F6A67664C64B4A94F8C3AC3017A7B6&user=15801151282
     *
     * @return
     */
    public String wtypeListBySccid() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> map = new HashMap<String, Object>();
        String cwcid = getTcc().getSccId();//人员id
        String a_user = getTcc().getAccount();//微分金帐号
        otype = request.getParameter("type");//类别 1:个人 2:企业
        try {
            originPage=getOriginPage();
            String id=getOtype();
            List<BaseBean> beanList = dsservice.wtypeListBySccid(cwcid, a_user, otype);
            PageForm p = new PageForm();
            p.setPageCount(1);
            p.setPageNumber(beanList.size());
            p.setList(beanList);
            staff = getStaff();
            map.put("staff", staff);
            map.put("pageForm", p);
        } catch (Exception e) {
            map.put("error", e.getMessage());
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 判断是否有工种
     * http://localhost:8080/ea/dserve/sajax_ea_wtypeCountBySccid.jspa?sccid=A8F6A67664C64B4A94F8C3AC3017A7B6&user=15801151282
     *
     * @return
     */
    public String wtypeCountBySccid() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String sccid = request.getParameter("sccid");//微分金帐号id
        String user = request.getParameter("user");//微分金帐号
        if (sccid == null || sccid == "") {
            sccid = getTcc().getSccId();//微分金帐号id
        }
        if (user == null || user == "") {
            user = getTcc().getAccount();//微分金帐号
        }
        JSONObject jo = dsservice.wtypeCountBySccid(sccid, user);
        result = jo;
        return "success";
    }

    /**
     * 可以被抢单的列表，注册用户无推荐人
     * http://localhost:8080/ea/dserve/sajax_ea_zhuceqdList.jspa?pageNumber=1
     *
     * @return
     */
    public String zhuceqdList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String number = request.getParameter("pageNumber");
        int pageNumber = 1;
        if ((number != null) || ("".equals(number))) {
            pageNumber = Integer.parseInt(number);
        }
        PageForm pageForm = dsservice.zhuceqdList(pageNumber);
        JSONObject jo = new JSONObject();
        if (pageForm == null) {

            jo.accumulate("flag", "没有数据");
        } else {
            List<BaseBean> list = pageForm.getList();
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                JSONObject jsonObj = new JSONObject();
                Object[] obj = (Object[]) (Object) list.get(i);
                jsonObj.accumulate("title", obj[0].toString());
                jsonObj.accumulate("date", obj[1].toString());
                jsonObj.accumulate("phone", obj[2].toString());
                if (obj[3] != null) {
                    jsonObj.accumulate("name", obj[3].toString());
                } else {
                    jsonObj.accumulate("name", "");
                }
                jsonObj.accumulate("ddid", obj[4].toString());
                jsonArray.add(jsonObj);
            }
            jo.accumulate("qdlist", jsonArray);
            jo.accumulate("pageNumber", pageForm.getPageNumber());
            jo.accumulate("pageCount", pageForm.getPageCount());
            jo.accumulate("recordCount", pageForm.getRecordCount());
        }
        result = jo;
        return "success";
    }

    /**
     * 用户已抢 抢单服务（服务平台）集合
     * @param cid 用户id
     * @return
     */
    public String detailhyListBydssccid() {
        originPage=getOriginPage();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String id=getOtype();
            List<BaseBean> ObjectList = dsservice.detailhyListBydssccid(id);
            map.put("hylist",ObjectList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 收单服务,查询用户已经抢到的单
     * http://localhost:8080/hyplat/ea/dserve/sajax_ea_shouDan.jspa?pageNumber=1&sccid=32EE426C1E404EBA88D148D62F40356C
     */
    public String shouDan() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String number = request.getParameter("pageNumber");
        String sccid = getTcc().getSccId();
        String phone = request.getParameter("phone");
        String title = request.getParameter("title");
        int pageNumber = 1;
        if ((number != null) || ("".equals(number))) {
            pageNumber = Integer.parseInt(number);
        }
        PageForm pageForm = dsservice.shouDan(sccid, pageNumber, phone, title);
        JSONObject jo = new JSONObject();
        jo.accumulate("pageForm", pageForm);
        result = jo.toString();
        return "success";
    }

    /**
     * 抢单人详细信息
     * http://localhost:8080/hyplat/ea/dserve/sajax_ea_selQdPerDetail.jspa?dsid=dserve201711019YS8MADSZI0000000002
     */
    public String selQdPerDetail() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String dsid = request.getParameter("dsid");
        String type = request.getParameter("type");
        //String dlsscid = request.getParameter("dlsccid");
        String dlsscid = getTcc().getSccId();
        Object obj = dsservice.selQdPerDetail(dsid);
        request.setAttribute("details", obj);
        request.setAttribute("dlsccid", dlsscid);
        request.setAttribute("type", type);
        return "qdPerDetail";
    }

    /**
     * 确认抢单成功人
     */
    public String isOKDemand() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String dsid = request.getParameter("dsid");
        String dlsccid = request.getParameter("dlsccid");
        TEshopCusCom cc = (TEshopCusCom) bbservice.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?", new Object[]{dlsccid});
        String[] insurePerson = dsservice.isOKDemand(dsid);
        request.setAttribute("insurePerson", insurePerson);
        request.setAttribute("account", cc.getAccount());
        return "insureQdPerson";
    }

    /**
     * 新注册用户确认抢单成功人
     */
    public String insureQdPerson() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String dsid = request.getParameter("dsid");
        String sccid = request.getParameter("sccid");
        String account = request.getParameter("account");
        String dlsccid = request.getParameter("dlsccid");
        TEshopCusCom cc = (TEshopCusCom) bbservice.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?", new Object[]{dlsccid});
        String[] insurePerson = dsservice.insureQdPerson(sccid, account, dsid, cc);
        request.setAttribute("insurePerson", insurePerson);
        request.setAttribute("account", cc.getAccount());
        return "insureQdPerson";
    }

    /**
     * 判断登录用户的级别以及推介人是否还是默认的
     * 比较抢单用户的级别和登录用户的级别
     */
    public String paduan() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String sccid = request.getParameter("sccid");
        /*HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cc = (TEshopCusCom)sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);*/
        String dsid = request.getParameter("sccid");
        String dlSccid = request.getParameter("dlSccid");
        String flag = dsservice.isPanduan(sccid, dlSccid);
        JSONObject jo = new JSONObject();
        jo.accumulate("flag", flag);
        result = jo.toString();
        return "success";
    }

    /**
     * 加载推荐工种类别
     *
     * @return
     */
    public String industryListNewBybtid() {
        HttpServletRequest request = ServletActionContext.getRequest();
        //String staffid = request.getParameter("staffid");//名称
        String staffid = getTcc().getStaffid();//名称
        Map<String, Object> map = new HashMap<String, Object>();
        if (staffid != null && !staffid.isEmpty()) {
            List<BaseBean> beanList = dsservice.industryListNewBybtid(staffid);
            map.put("beanList", beanList);
        } else {
            map.put("flag", 1);
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    public String getSession(){
        tcc=getTcc();
        Map<String, Object> map = new HashMap<String, Object>();
        if (tcc == null) {
            map.put("nologin","1");
        }else {
            map.put("nologin","0");
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 处理数据
     * @return
     * @throws Exception
     */
    public String getOtype() throws Exception{
        String id = null;
        switch (this.otype) {
            case "1":
                id = getTcc().getStaffid();//人员id；
                break;
            case "2":
                id = getcAccount().getCompanyID();//公司id
                break;
            default:
                new Exception("参数错误");
                break;
        }
        return id;
    }

    public DemandDetail getDemandDetail() {
        return demandDetail;
    }

    public void setDemandDetail(DemandDetail demandDetail) {
        this.demandDetail = demandDetail;
    }

    public DemandServe getDemandServe() {
        return demandServe;
    }

    public void setDemandServe(DemandServe demandServe) {
        this.demandServe = demandServe;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getTle() {
        return tle;
    }

    public void setTle(String tle) {
        this.tle = tle;
    }

    public TEshopCusCom getTcc() {
        if (tcc == null) setTcc(null);
        return tcc;
    }

    public void setTcc(TEshopCusCom tcc) {
        if (tcc == null) {
            this.tcc = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
        } else {
            this.tcc = tcc;
        }
    }

    public String getCompanyID() {
        if (companyID == null) setCompanyID(null);
        return companyID;
    }

    public void setCompanyID(String companyID) {
        if (this.companyID == null || this.companyID.isEmpty()) {
            if (getTcc() != null && getTcc().getCompanyId() != null) {
                this.companyID = getTcc().getCompanyId();
            } else {
                this.companyID = "company201009046vxdyzy4wg0000000025";
            }
        }
    }

    public CAccount getcAccount() {
        if (cAccount == null) {
            setcAccount(null);
        }
        return cAccount;
    }

    public void setcAccount(CAccount cAccount) {
        if (cAccount == null) {
            Map<String, Object> s = ActionContext.getContext().getSession();
            this.cAccount = (CAccount) s.get("account");
            if (this.cAccount.getStaffID() == null || this.cAccount.getStaffID().isEmpty()) {
                this.cAccount.setStaffID(getTcc().getStaffid());
            }
            if (this.cAccount.getStaffName() == null || this.cAccount.getStaffName().isEmpty()) {
                this.cAccount.setStaffName(getcAccount().getStaffName());
            }
        } else {
            this.cAccount = cAccount;
        }
    }

    public Staff getStaff() {
        if (staff == null) {
            setStaff(null);
        }
        return staff;
    }

    public void setStaff(Staff staff) {
        if (staff == null) {
            this.staff = (Staff) sw.getObject(session, SessionWrap.KEY_STAFF);
            if (this.staff == null) {
                this.staff = staffService.getStaffDataByStaffId(getTcc().getStaffid());
                sw.setObject(session, SessionWrap.KEY_STAFF, staff);
            }
        } else {
            this.staff = staff;
        }
    }

    public File getPhotos() {
        return photos;
    }

    public void setPhotos(File photos) {
        this.photos = photos;
    }

    public String getPhotosFileName() {
        return photosFileName;
    }

    public void setPhotosFileName(String photosFileName) {
        this.photosFileName = photosFileName;
    }

    public String getPhotosContentType() {
        return photosContentType;
    }

    public void setPhotosContentType(String photosContentType) {
        this.photosContentType = photosContentType;
    }

    public TCtomerWorktype getTcw() {
        return tcw;
    }

    public void setTcw(TCtomerWorktype tcw) {
        this.tcw = tcw;
    }

    public String getOriginPage() {
        if (originPage == null) {
            otype="1";
        }else {
            String[] requestPage = originPage.split("-");
            if (requestPage != null&&requestPage.length==3) {
                if (requestPage[2] == null&&requestPage[2]=="") {
                    otype="1";
                }else {
                    if (requestPage[2].equals("enterprise")) {
                        otype="2";
                    }else {
                        otype="1";
                    }
                }
            }else {
                otype="1";
            }
        }
        return originPage;
    }

    public void setOriginPage(String originPage) {
        this.originPage = originPage;
    }
}
