package com.tiantai.wfj.secure.nfc;

import com.tiantai.wfj.bo.SafetyInspect;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.SafetyInspectService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.util.DateJsonValueProcessor;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SafetyInspectAction {
	private static final Logger logger = LoggerFactory.getLogger(SafetyInspectAction.class);
    private final Logger logger = LoggerFactory.getLogger(SafetyInspectAction.class);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private SafetyInspectService siService;

    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
    SessionWrap sw = SessionWrap.getInstance();
    TEshopCustomer tr=(TEshopCustomer)sw.getObject(session,SessionWrap.KEY_CUSTOMER); //保存Customer的key
    TEshopCusCom tm=(TEshopCusCom)sw.getObject(session,SessionWrap.KEY_SHOPCUSCOM); //保存当前登陆客户的登陆角色

    private SafetyInspect si;  //巡查信息
    private String siid; //巡查id
    private String result; //ajax回传数据
    private int pageNumber; //当前页数
    private int pageSize; //每页条数
    private String parameter;  //查询,传递 参数
    private String companyID; //公司id

    /**
     * 保存巡查信息
     *
     * @return
     * @url /ea/SafetyInspect/ea_saveSafetyInspect.jspa?
     */
    public String saveSafetyInspect() {
        HttpServletRequest request = ServletActionContext.getRequest();
        if(tm!=null){
            si.setStaffID(tm.getStaffid());
            String ImagesPath = request.getParameter("ImagesPath");
            String VideoPath = request.getParameter("VideoPath");
            String oask=request.getParameter("oask");
            boolean falg = false;
            if (si != null) {
                si.setSiID(serverService.getServerID("SafetyInspect"));
                falg = siService.saveInspect(si, ImagesPath, VideoPath,oask);
            }
            companyID = si.getCompanyID();
            request.setAttribute("pagetype","inspect");
            return "inspectList";
        }else {
            String sn = request.getParameter("sn");
            String model = request.getParameter("model");
            String jumpType="inspect"; //跳转页面类型  inspect:安全巡查页面
            parameter="sn="+sn+"&model="+model;
            request.setAttribute("jumpType",jumpType);
            return "login";
        }
    }

    /**
     * 列表
     * @return
     * @url /ea/SafetyInspect/sajax_ea_getInspectList.jspa?companyID=
     */
    public String getInspectList() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String ln = request.getParameter("ln");
            request.setAttribute("ln", ln);
            String staffname = request.getParameter("staffName");
            request.setAttribute("staffname", staffname);
            String oaskName = request.getParameter("oaskName");
            request.setAttribute("oaskName", oaskName);
            String siType = request.getParameter("siType");
            request.setAttribute("siType", siType);
            String sDate = request.getParameter("sdate");
            request.setAttribute("sdate", sDate);
            String eDate = request.getParameter("edate");
            request.setAttribute("edate", eDate);
            PageForm pageForm = siService.getInspectList(companyID,ln,staffname,oaskName,sDate,eDate,siType, pageNumber, pageSize);
            JsonConfig jc=new JsonConfig();
            jc.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor());
            jc.setExcludes(new String[]{"handler","hibernateLazyInitializer"});
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageForm", pageForm);
            JSONObject oj = JSONObject.fromObject(map,jc);
            result = oj.toString();
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "success";
    }

    /**
     * 查看
     * @return
     * @url /ea/SafetyInspect/ea_getInspect.jspa?siid=
     */
    public String getInspect(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String siid = request.getParameter("siid");
        Object i=siService.getInspect(siid);
        List<BaseBean> iaList=siService.getAnnex(siid);
        List<BaseBean> ikList=siService.getInspectKind(siid);
        request.setAttribute("inspectVo",i);
        request.setAttribute("Annex",iaList);
        request.setAttribute("kind",ikList);
        return "getInspect";
    }

    public SafetyInspect getSi() {
        return si;
    }

    public void setSi(SafetyInspect si) {
        this.si = si;
    }

    public String getSiid() {
        return siid;
    }

    public void setSiid(String siid) {
        this.siid = siid;
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

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }
}
