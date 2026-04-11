package com.tiantai.wfj.secure.nfc;

import com.tiantai.telrec.tool.JsonDateValueProcessor;
import com.tiantai.wfj.bo.NfcChip;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.bindNfcService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.human.Staff;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@Scope("prototype")
public class bindNfcAction {
	private static final Logger logger = LoggerFactory.getLogger(bindNfcAction.class);
    private final Logger logger = LoggerFactory.getLogger(bindNfcAction.class);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private bindNfcService bnsi;

    private final HttpServletRequest request = ServletActionContext.getRequest();
    private final HttpSession session = request.getSession();
    private final SessionWrap sw = SessionWrap.getInstance();
    private final TEshopCustomer tr=(TEshopCustomer)sw.getObject(session,SessionWrap.KEY_CUSTOMER); //保存Customer的key
    private final TEshopCusCom tm=(TEshopCusCom)sw.getObject(session,SessionWrap.KEY_SHOPCUSCOM); //保存当前登陆客户的登陆角色

    private String result; //ajax回传数据
    private int pageNumber; //当前页数
    private int pageSize; //每页条数
    private String parameter;  //查询,传递 参数
    private String companyID; //公司id
    private NfcChip nfc; //nfc绑定实体

    /**
     * 读取NFC芯片
     *
     * @return
     * @url /ea/bindnfc/ea_readNfc.jspa?sn=?&model=?
     */
    public String readNfc() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String model = request.getParameter("model");
        String sn=request.getParameter("sn");
        if(tm!=null){
            if(sn==null||sn.equals("")||model==null||model.equals("")){
                return "login";
            }else {
                nfc = bnsi.getNfcBySn(sn);
                if (nfc != null) {
                    Staff s = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?",new Object[]{tm.getStaffid()});
                    request.setAttribute("staffid",s.getStaffID());
                    request.setAttribute("staffname",s.getStaffName());
                    return "Patrol";
                } else {
                    return "bingNfc";
                }
            }
        }else {
            String jumpType="inspect"; //跳页转面类型  inspect:安全巡查页面
            parameter="sn="+sn+"&model="+model;
            request.setAttribute("jumpType",jumpType);
            return "login";
        }
    }

    /**
     * 获取商家
     *
     * @return
     * @url /ea/bindnfc/sajax_ea_getCom.jspa
     */
    public String getCom() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String valString = request.getParameter("parameter");
        StringBuilder sqlString = new StringBuilder("select cc.compnay_id,c.companyname from dtcontactcompany c, dt_ccom_com cc,t_eshop_cuscom t");
        sqlString.append(" where c.ccompanyid = cc.ccompany_id and cc.compnay_id=t.companyid and c.companyname like ?");
        sqlString.append(" and t.account is not null and cc.compnay_id is not null and c.companyname is not null");
        PageForm pageForm = baseBeanService.getPageFormBySQL((0 < pageNumber ? pageNumber : 1), (pageSize == 0 ? 15 : pageSize), sqlString.toString(), "select count(*) from (" + sqlString + ")", new Object[]{"%" + (valString == null ? "" : valString) + "%"});
        JSONObject obj = new JSONObject();
        obj.accumulate("pageForm", pageForm);
        result = obj.toString();
        return "success";
    }

    /**
     * 获取采购商员工
     *
     * @return
     * @url /ea/bindnfc/sajax_ea_getStaff.jspa?comid=
     */
    public String getStaff() {
        HttpServletRequest request = ServletActionContext.getRequest();
        logger.error(companyID);
        String valString = request.getParameter("parameter");
        logger.error(valString);
        String hql = "select staffID,staffName from view_sc where companyID = ?  and staffName like ?";
        Object[] params = {companyID, "%" + (valString == null ? "" : valString) + "%"};
        PageForm pageForm = baseBeanService.getPageFormBySQL((0 < pageNumber ? pageNumber : 1), (pageSize == 0 ? 15 : pageSize), hql, "select count(*) from (" + hql + ")", params);
        JSONObject obj = new JSONObject();
        obj.accumulate("pageForm", pageForm);
        result = obj.toString();
        return "success";
    }

    /**
     * 获取安全类别
     *
     * @return
     * @url /ea/bindnfc/sajax_ea_getOASafeKindList.jspa?companyID=
     */
    public String getOASafeKindList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String valString = request.getParameter("parameter");
        String hql = "select id,name from DT_OA_SAFEKIND where companyID = ? and status in(?,?)  and name like ?";
        Object[] params = {companyID, "00", "01", "%" + (valString == null ? "" : valString) + "%"};
        PageForm pageForm = baseBeanService.getPageFormBySQL((0 < pageNumber ? pageNumber : 1), (pageSize == 0 ? 15 : pageSize), hql, "select count(*) from (" + hql + ")", params);
        JSONObject obj = new JSONObject();
        obj.accumulate("pageForm", pageForm);
        result = obj.toString();
        return "success";
    }

    /**
     * 保存
     *
     * @return
     * @url /ea/bindnfc/ea_saveNFC.jspa?
     */
    public String saveNFC() {
        HttpServletRequest request = ServletActionContext.getRequest();
        boolean falg = false;
        if (nfc != null) {
            nfc.setNfcID(serverService.getServerID("NFC"));
            falg = bnsi.saveNfc(nfc);
        }
        companyID = nfc.getCompanyID();
        request.setAttribute("pagetype","nfc");
        return "NfcList";
    }

    /**
     * 解绑
     *
     * @return
     * @url /ea/bindnfc/sajax_ea_UnbindingNFC.jspa?id=
     */
    public String UnbindingNFC() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String id = request.getParameter("id");
        boolean flag = false;
        if (id != null && !id.equals("")) {
            flag = bnsi.updateNfc(id);
        }
        JSONObject obj = new JSONObject();
        obj.accumulate("flag", flag);
        result = obj.toString();
        return "success";
    }


    /**
     * 列表
     *
     * @return
     * @url /ea/bindnfc/sajax_ea_getNfcList.jspa?companyID=
     */
    public String getNfcList() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String bindState = request.getParameter("bindState");
            String staffname = request.getParameter("staffName");
            request.setAttribute("staffname", staffname);
            String ln = request.getParameter("ln");
            request.setAttribute("ln", ln);
            String oaskName = request.getParameter("oaskName");
            request.setAttribute("oaskName", oaskName);
            DetachedCriteria dc = bnsi.getNfcList(companyID, "00", staffname, ln, oaskName);
            PageForm pageForm = baseBeanService.getPageFormByDC((0 < pageNumber ? pageNumber : 1), (pageSize == 0 ? 15 : pageSize), dc);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageForm", pageForm);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(java.util.Date.class,
                    new JsonDateValueProcessor("yyyy-MM-dd"));
            JSONObject jsonArray = JSONObject.fromObject(map, jsonConfig);
            result = jsonArray.toString();
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "success";
    }

    /**
     * 列表查看
     *
     * @return
     * @url /ea/bindnfc/ea_getNfcByid.jspa?nfcid=
     */
    public String getNfcByid() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String id = request.getParameter("nfcid");
        request.setAttribute("id", id);
        NfcChip n = bnsi.getNfcById(id);
        request.setAttribute("nfc",n);
        return "getnfc";
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

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public NfcChip getNfc() {
        return nfc;
    }

    public void setNfc(NfcChip nfc) {
        this.nfc = nfc;
    }
}
