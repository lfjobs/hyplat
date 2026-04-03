package mobile.tiantai.android.action.storeProduction.budgetSheet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.service.DictDataService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import mobile.tiantai.android.action.SDKTestSendTemplateSMS;
import mobile.tiantai.android.bo.StaffSend;
import mobile.tiantai.android.bo.TrilateralData;
import mobile.tiantai.android.service.storeProduction.budgetSheet.CrmCustomerPoService;
import mobile.tiantai.android.service.storeProduction.budgetSheet.TrilateralDataService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
public class TrilateralDataAction extends HttpServlet {
    @Resource
    private UpLoadFileService fileService;
    @Resource
    private TrilateralDataService trilateralDataService;
    private List<TrilateralData> trilateralDataList;
    private TrilateralData trilateralData;
    private String uploadProject;
    private String trusteeship;
    @Resource
    private CrmCustomerPoService crmCustomerService;
    //删除id
    private String id;

    @Resource
    private BaseBeanService baseBeanService;

    @Resource
    private DictDataService dictDataService;

    private PageForm pageForm;
    private String cusType;
    private String user;
    private Object result;// AJAX使用
    private String sccid;
    private TEshopCusCom cuscom;
    private TEshopCustomer customer;
    private Staff staff;
    private ContactCompany contactCompany;
    private ProductPackaging productPackaging;
    private List<BaseBean> list;
    private Object object;
    private String staffid;
    private String type;
    private String qrcode;
    private Map<Integer, String[]> maplist1;
    private List<Object> list1;
    private String ret;
    private String flag;//标识
    private String khd; //0为网页查看  1为APP查看
    private String itemId;
    private SDKTestSendTemplateSMS sdk = new SDKTestSendTemplateSMS();
    private String phone;
    private String staffName;
    private String companyName;

    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = ServletActionContext.getRequest().getSession();
    SessionWrap sw = SessionWrap.getInstance();

    private int pageNumber;
    private int pageSize;
    private File image;
    private String imageFileName;

    private String auditOpinion;
    private String auditStatus;


    /**
     * 注册平台列表页面
     */
    public String getDictDataListByType() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");
        List<BaseBean> list = dictDataService.getDictDataListByType(type, "");
        JSONArray obj = JSONArray.fromObject(list);
        result = obj.toString();
        return "success";
    }

    /**
     * 三方列表页面
     *
     * @return
     */
    public String trilateralDataList() {
        try {
            trilateralDataList = trilateralDataService.getTrilateralDataList(uploadProject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "trilateralData";
    }

    public String getTrilateral() {
        pageForm = trilateralDataService.getTrilateral(pageNumber, pageSize, uploadProject, trusteeship);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String getTrilateralTotals() {
        pageForm = trilateralDataService.getTrilateralTotals(pageNumber, pageSize, uploadProject, trusteeship, staffName, phone);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String getTrilateralAudit() {
        pageForm = trilateralDataService.getTrilateralAudit(pageNumber, pageSize, uploadProject, trusteeship, staffName, phone);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String submitAuditOpinion() {
        String result1 = trilateralDataService.submitAuditOpinion(auditOpinion, auditStatus, id);
        result = result1;
        return "success";
    }

    public String auditResult() {
        List<BaseBean> list = trilateralDataService.auditResult(id);
        result = JSONArray.fromObject(list).toString();
        return "success";
    }

    //根据姓名搜索
    public String searchTrilateralByName() {
        pageForm = trilateralDataService.searchTrilateralByName(pageNumber, pageSize, trilateralData.getName());
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String getBusinessPersonnel() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        result = account.getStaffName();
        return "success";
    }

    //    public String getPath() {
//        String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
//        String path1 = trilateralData.getAttachmentAddress();
//        // 将路径分隔符从 / 替换为 \
//        String formattedPath = path.replace("/", "\\");
//        String formattedPath1 = path1.replace("/", "\\");
//        result = formattedPath + formattedPath1;
//        return "success";
//    }
    public String getPath() {
        // 直接使用附件地址，不拼接服务器物理路径
        String attachmentPath = trilateralData.getAttachmentAddress();

        if (attachmentPath != null && !attachmentPath.isEmpty()) {
            // 确保路径格式适合Web访问
            // 移除可能的敏感前缀，只返回相对路径或URL
            result = attachmentPath.replace("..", ""); // 防止路径遍历
        } else {
            result = "";
        }

        return "success";
    }


    /**
     * 跳转添加页面
     *
     * @return
     */
    public String trilateralDataAdd() {
        return "trilateralDataAdd";
    }

    /**
     * 执行添加方法
     *
     * @return
     */
    public String addTrilateralData() {

        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            CAccount account = (CAccount) session.getAttribute("account");
            String photoPath = null;
            if (image != null) {
                String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
                photoPath = fileService.savePhoto(path, imageFileName, image, (account.getCompanyID() == null || account.getCompanyID().equals("")) ? account.getStaffID() : account.getCompanyID(), "/web/temp/"
                        + Utilities.getDateString(new Date(), "yyyy-MM-dd"));
//                documentTemplate.setTemplatePath(photoPath);
            }
            TrilateralData addTrilateralData = this.trilateralData;
            trilateralDataService.addTrilateralData(addTrilateralData, photoPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "trilateralData";
    }

    /**
     * 跳转修改页面
     *
     * @return
     */
    public String trilateralDataUpdate() {
        try {
            trilateralData = trilateralDataService.selTrilateralDataBy(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "trilateralDataUpdate";
    }

    /**
     * 跳转详情页面
     *
     * @return
     */
    public String toDetailTrilateralData() {
        try {
            trilateralData = trilateralDataService.selTrilateralDataBy(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "detailTrilateralData";
    }

    /**
     * 执行修改方法
     *
     * @return
     */
    public String UpdateTrilateralData() {
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            CAccount account = (CAccount) session.getAttribute("account");
            String photoPath = null;
            if (image != null) {
                String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
                photoPath = fileService.savePhoto(path, imageFileName, image, (account.getCompanyID() == null || account.getCompanyID().equals("")) ? account.getStaffID() : account.getCompanyID(), "/web/temp/"
                        + Utilities.getDateString(new Date(), "yyyy-MM-dd"));
//                documentTemplate.setTemplatePath(photoPath);
            }
            TrilateralData updateTrilateralData = this.trilateralData;
            trilateralDataService.updateTrilateralData(updateTrilateralData, photoPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "trilateralData";
    }


    /**
     * 执行删除方法
     *
     * @return
     */
    public String delTrilateralData() {
        try {
            result = trilateralDataService.delTrilateralData(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "success";
    }

    /**
     * 跳转导入页面
     *
     * @return
     */
    public String importData() {
        return "visitorImport";
    }

    /**
     * 确认导入方法
     *
     * @return
     */
    public String saveImportData() {
        try {
            // trilateralDataService.saveImportData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "trilateralData";
    }

    /**
     * 确认导入方法
     *
     * @return
     */
    public String saveImportData1() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String date = request.getParameter("data");
            //json字符串转实体
            ObjectMapper objectMapper = new ObjectMapper();
            List<DataDTO> DataDTOList = objectMapper.readValue(date, objectMapper.getTypeFactory().constructCollectionType(List.class, DataDTO.class));
            trilateralDataService.saveImportData(DataDTOList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "trilateralData";
    }

    /**
     * 保存导入数据
     *
     * @return
     */
    public String saveImportDataPo() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String date = request.getParameter("data");
            //json字符串转实体
            ObjectMapper objectMapper = new ObjectMapper();
            List<CrmCustomerVo> DataDTOList = objectMapper.readValue(date, objectMapper.getTypeFactory().constructCollectionType(List.class, CrmCustomerVo.class));
            crmCustomerService.saveImportData(DataDTOList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "crmCustomer";
    }

    public String allocationImportContacts() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String selectedData = request.getParameter("selectedData");
        String sfpt = request.getParameter("sfpt");
        ObjectMapper objectMapper = new ObjectMapper();
        List<StaffSend> StaffSendList = new ArrayList<StaffSend>();
        try {
            StaffSendList = objectMapper.readValue(selectedData, objectMapper.getTypeFactory().constructCollectionType(List.class, StaffSend.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        result = trilateralDataService.allocationImportContacts(StaffSendList, sfpt);
        return "success";
    }

    public String toTrilateral() {
        trilateralData = trilateralDataService.selTrilateralDataBy(id);
        return "trilateralPrint";
    }

    /**
     * 执行分配业务
     *
     * @return
     */
    public String assignment1() {
        try {
            TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                    SessionWrap.KEY_SHOPCUSCOM);
            if (tc != null) {
                request.setAttribute("sccId", tc.getSccId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "trilateralData";
    }

    /**
     * 跳转分配业务
     *
     * @return
     */
    public String assignment2() {
        try {
            trilateralData = trilateralDataService.selTrilateralDataBy(id);
            TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                    SessionWrap.KEY_SHOPCUSCOM);
            if (tc != null) {
                request.setAttribute("sccid", tc.getSccId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "assignmentData";
    }

    /**
     * 跳转分配业务  测试
     *
     * @return
     */
    public String assignment3() {
        try {
            trilateralData = trilateralDataService.selTrilateralDataBy(id);
            TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                    SessionWrap.KEY_SHOPCUSCOM);
            if (tc != null) {
                cuscom = tc;
                request.setAttribute("sccid", tc.getSccId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "assignmentData";
    }

    public String assignment4() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            trilateralData = trilateralDataService.selTrilateralDataBy(id);
            trilateralData.setBusinessPersonnel(name);
            trilateralDataService.saveTrilateralData(trilateralData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "trilateralData";
    }

    //发短信分配成功
    public String sendPhone1() {
        try {
            String phone1 = this.phone;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "trilateralData";
    }

    public String sendPhone() {
        String yanz = "[数字地球]为您分配一条数据";// 验证码
        String phone1 = this.phone;
        String yz = yanz;
        sdk.getduan(phone1, yz);
        return "trilateralData";

    }


    public String toQuery1() {
        try {
            trilateralDataList = trilateralDataService.getTrilateralData(companyName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "trilateralData";
    }
    //粉丝

//    public String findVipAccout() {
//        StringBuffer str = new StringBuffer();
//        List<String> params = new ArrayList<String>();
//        //重要信息   详细信息
//        String sqlcount = "select count(1) from ";
//        //代理商级别以上
//        if (((int) Integer.parseInt(cusType)) <= 5) {
//
//            str.append("with t as(");
//            str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state,t.sccId from T_ESHOP_CUSCOM t where t.cusType = ?");
//            str.append(" connect by nocycle prior t.sccId = t.supperSccId");
//            str.append(" start with t.sccId =?)");
//            params.add(cusType);
//            params.add(sccid);
//
//            str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state,t.sccId,pp.goodsname");
//            str.append(" from t,dt_hr_staff staff,dt_ProductPackaging pp");
//            str.append(" where staff.staffid = t.staffid and t.state = '1' and t.cusType = pp.model and pp.type ='会员类型级别'");
//
//            str.append(" union all");
//            str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType,t.state,t.sccId,pp.goodsname");
//            str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc,dt_ProductPackaging pp");
//            str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
//            str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2' and t.cusType = pp.model and pp.type ='会员类型级别'");
//            sqlcount = sqlcount + "(" + str.toString() + ")";
//            pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 15, str.toString(), sqlcount, params.toArray());
//        } else if (cusType.equals("6")) {
//            //查询VIP客户和客户
//            str.append("with t as(");
//            str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state,t.sccId from T_ESHOP_CUSCOM t where (t.cusType = ? or t.cusType = ?)");
//
//            str.append(" connect by nocycle prior t.sccId = t.supperSccId");
//            str.append(" start with t.sccId =?)");
//            params.add(cusType);
//            params.add(String.valueOf((int) Integer.parseInt(cusType) + 1));
//            params.add(sccid);
//
//
//            str.append(" select t.account,t.custype,staff.staffname, staff.headimage,staff.staffPost,t.state,t.sccId,pp.goodsname");
//            str.append(" from t, dt_hr_staff staff,dt_ProductPackaging pp");
//            str.append(" where staff.staffid = t.staffid and t.state = '1' and t.cusType = pp.model and pp.type ='会员类型级别'");
//            sqlcount = sqlcount + "(" + str.toString() + ")";
//            pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 15, str.toString(), sqlcount, params.toArray());
//        } else if (cusType.equals("8")) {
//            //查询粉丝
//            //查询当前用户所有的默认等级的会员
//            str.append("with t as(");
//            str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state ,t.sccId from T_ESHOP_CUSCOM t,dtJoinFans f");
//            str.append(" where f.fsccId = t.sccId and f.zsccId = ? and f.state='00' and f.source!='系统粉丝' and f.source!='移动粉丝')");
//            params.add(sccid);
//
//            str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state ,t.sccId,pp.goodsname");
//            str.append(" from t,dt_hr_staff staff,dt_ProductPackaging pp");
//            str.append(" where staff.staffid = t.staffid and t.state = '1' and t.cusType = pp.model and pp.type ='会员类型级别'");
//
//            str.append(" union all");
//            str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType,t.state ,t.sccId,pp.goodsname");
//            str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc,dt_ProductPackaging pp");
//            str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
//            str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2' and t.cusType = pp.model and pp.type ='会员类型级别'");
//            sqlcount = sqlcount + "(" + str.toString() + ")";
//            pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 15, str.toString(), sqlcount, params.toArray());
//        } else if (cusType.equals("9")) {
//            //查询移动粉丝
//            str.append("with t as (");
//            str.append(" select t.account,t.cusType,t.staffid st,t.Superioragent,t.companyid,t.state,t.sccId,f.staffid sta,f.faccount,f.fsccid");
//            str.append(" from T_ESHOP_CUSCOM t right join dtJoinFans f on f.fsccId = t.sccId");
//            str.append(" where f.zsccId = ? and f.source = '移动粉丝')");
//            params.add(sccid);
//
//            str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state ,t.sccId,pp.goodsname");
//            str.append(" from t,dt_hr_staff staff,dt_ProductPackaging pp");
//            str.append(" where staff.staffid = t.st and t.state = '1'  and t.cusType = pp.model and pp.type ='会员类型级别'");
//
//            str.append(" union all");
//            str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType,t.state ,t.sccId,pp.goodsname");
//            str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc,dt_ProductPackaging pp");
//            str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
//            str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2' and t.cusType = pp.model and pp.type ='会员类型级别'");
//
//            str.append(" union all");
//            str.append(" select t.faccount,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state ,t.sccId,t.fsccId");
//            str.append(" from dt_hr_staff staff,t where staff.staffid = t.sta");
//
//            sqlcount = sqlcount + "(" + str.toString() + ")";
//            pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 15, str.toString(), sqlcount, params.toArray());
//        } else if (cusType.equals("10")) {
//            //查询移动粉丝
//            str.append("with t as (");
//            str.append(" select t.account,t.cusType,t.staffid st,t.Superioragent,t.companyid,t.state,t.sccId,f.staffid sta,f.faccount,f.fsccid");
//            str.append(" from T_ESHOP_CUSCOM t right join dtJoinFans f on f.fsccId = t.sccId");
//            str.append(" where f.zsccId = ? and f.source = '系统粉丝')");
//            params.add(sccid);
//
//            str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state ,t.sccId,pp.goodsname");
//            str.append(" from t,dt_hr_staff staff,dt_ProductPackaging pp");
//            str.append(" where staff.staffid = t .st and t.state = '1'  and t.cusType = pp.model and pp.type ='会员类型级别'");
//
//            str.append(" union all");
//            str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType,t.state ,t.sccId,pp.goodsname");
//            str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc,dt_ProductPackaging pp");
//            str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
//            str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2' and t.cusType = pp.model and pp.type ='会员类型级别'");
//
//            str.append(" union all");
//            str.append(" select t.faccount,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state ,t.sccId,t.fsccid");
//            str.append(" from dt_hr_staff staff,t where staff.staffid = t.sta");
//
//            sqlcount = sqlcount + "(" + str.toString() + ")";
//            pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 15, str.toString(), sqlcount, params.toArray());
//        } else if ("15".equals(cusType)) {
//            String sql = "select ec.account,15,hs.staffname,hs.headimage,hs.staffPost, ec.state,ec.sccId,cast('购物卡' as varchar(10)) as rem, hs.staffid"
//                    + " from dt_giftcards gc left join t_eshop_cuscom ec on ec.staffid = gc.staffid and ec.state = ?" +
//                    " left join dt_hr_staff hs on hs.staffid = gc.staffid" +
//                    " where gc.operator in (select ec.staffid from t_eshop_cuscom ec where ec.sccid  = ?)";
//            pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 15, sql, "select count(*) from (" + sql + ")", new Object[]{"1", sccid});
//        }
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("pageForm", pageForm);
//        JSONObject oj = JSONObject.fromObject(map);
//        result = oj.toString();
//        return "assignmentData";
//    }

    @SuppressWarnings("unchecked")
//    public String findVipNum() {
//        String hql = "from ProductPackaging s where s.type=?";
//        List<BaseBean> viplist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"会员类型级别"});
//        //重要信息
//        Map<String, String> namemap = new HashMap<String, String>();
//        for (int i = 0; i < viplist.size(); i++) {
//            ProductPackaging pp = (ProductPackaging) viplist.get(i);
//            namemap.put(pp.getModel(), pp.getGoodsName());
//        }
//
//        TEshopCusCom tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom d where d.sccId=?", new Object[]{sccid});
//
//        List<List<String>> listt = new ArrayList<List<String>>();
//        List<String> params = new ArrayList<String>();
//        params.add(sccid);
//        String sql = "select t.account,t.cusType,t.staffid,t.sccId from T_ESHOP_CUSCOM t  connect by nocycle prior t.sccid = t.supperSccId start with t.sccid = ?";
//        List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql, params.toArray());
//
//        String fanssql = "select count(*) from dtJoinFans f where f.zsccId = ? and f.source!='移动粉丝' and f.source!='系统粉丝' and state='00'";
//
//        BigDecimal f = baseBeanService.getCountByBDSqlAndParams(fanssql, params.toArray());
//
//        String syssql = "select count(*) from dtJoinFans jf where jf.zsccId=? and jf.source='系统粉丝' and jf.state='00'";
//        BigDecimal x = baseBeanService.getCountByBDSqlAndParams(syssql, new Object[]{sccid});
//
//        String telsql = "select count(*) from dtJoinFans jf where jf.zsccId=? and jf.source='移动粉丝' and jf.state='00'";
//
//        BigDecimal t = baseBeanService.getCountByBDSqlAndParams(telsql, new Object[]{sccid});
//
//        int a = 0;
//        int b = 0;
//        int c = 0;
//        int d = 0;
//        int e = 0;//客户+VIP客户
//        //	int f= 0;//粉丝
//        //	int x = 0;//系统粉丝
//        //int t = 0;//移动粉丝
//
//        //2-7会员个数
//        for (int i = 0; i < list.size(); i++) {
//
//            Object[] obj = (Object[]) (Object) list.get(i);
//
//            if (obj[1].equals("2")) {
//                a++;
//            }
//            if (obj[1].equals("3")) {
//                b++;
//            }
//            if (obj[1].equals("4")) {
//                c++;
//            }
//            if (obj[1].equals("5")) {
//                d++;
//            }
//            if (obj[1].equals("6") || obj[1].equals("7")) {
//                e++;
//            }
//
//        }
//        //粉丝个数
//        //	f=fanslist.size();
//        //系统粉丝个数
//        //	x=syslist.size();
//        //移动粉丝个数
//        //	t=tellist.size();
//        //根据当前用户等级统计类型和会员等级
//        String cusType = tcc.getCusType();
//        List<String> temp = new ArrayList<String>();
//
//        if (cusType.equals("6") || cusType.equals("7")) {
//            //等级会员VIP客户或者普通客户 只显示粉丝
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("8"));
//            temp.add(f + "");//粉丝数
//            temp.add("8");//临时当做8
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("9"));
//            temp.add(t + "");//移动粉丝数
//            temp.add("9");//临时当做9
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("10"));
//            temp.add(x + "");//系统粉丝数
//            temp.add("10");//临时当做10
//            listt.add(temp);
//        }
//        if (cusType.equals("5")) {
//            //等级会员是代理商 显示 客户(普通客户+VIP客户)，粉丝
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("6"));
//            temp.add(e + "");
//            temp.add("6");
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("8"));
//            temp.add(f + "");//粉丝数
//            temp.add("8");//临时当做8
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("9"));
//            temp.add(t + "");//移动粉丝数
//            temp.add("9");//临时当做9
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("10"));
//            temp.add(x + "");//系统粉丝数
//            temp.add("10");//临时当做10
//            listt.add(temp);
//        } else if (cusType.equals("4")) {
//            //等级会员是微分金显示 代理商商城业主会员 、客户(普通客户+VIP客户)，粉丝
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("5"));
//            temp.add(d + "");
//            temp.add("5");
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("6"));
//            temp.add(e + "");
//            temp.add("6");
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("8"));
//            temp.add(f + "");//粉丝数
//            temp.add("8");//临时当做8
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("9"));
//            temp.add(t + "");//移动粉丝数
//            temp.add("9");//临时当做9
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("10"));
//            temp.add(x + "");//系统粉丝数
//            temp.add("10");//临时当做10
//            listt.add(temp);
//        } else if (cusType.equals("3")) {
//            //等级会员是合伙创业 显示微分金、 代理商商城业主会员 、客户(普通客户+VIP客户)，粉丝
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("4"));
//            temp.add(c + "");
//            temp.add("4");
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("5"));
//            temp.add(d + "");
//            temp.add("5");
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("6"));
//            temp.add(e + "");
//            temp.add("6");
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("8"));
//            temp.add(f + "");//粉丝数
//            temp.add("8");//临时当做8
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("9"));
//            temp.add(t + "");//移动粉丝数
//            temp.add("9");//临时当做9
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("10"));
//            temp.add(x + "");//系统粉丝数
//            temp.add("10");//临时当做10
//            listt.add(temp);
//        } else if (cusType.equals("2")) {
//            //等级会员是公司会员 显示合伙创业商城业主会员微分金、 代理商商城业主会员 、客户(普通客户+VIP客户)，粉丝
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("3"));
//            temp.add(b + "");
//            temp.add("3");
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("4"));
//            temp.add(c + "");
//            temp.add("4");
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("5"));
//            temp.add(d + "");
//            temp.add("5");
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("6"));
//            temp.add(e + "");
//            temp.add("6");
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("8"));
//            temp.add(f + "");//粉丝数
//            temp.add("8");//临时当做8
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("9"));
//            temp.add(t + "");//移动粉丝数
//            temp.add("9");//临时当做9
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("10"));
//            temp.add(x + "");//系统粉丝数
//            temp.add("10");//临时当做10
//            listt.add(temp);
//        } else if (cusType.equals("0")) {
//            //等级会员是税务 显示公司 合伙创业商城业主会员微分金、 代理商商城业主会员 、客户(普通客户+VIP客户)，粉丝
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("2"));
//            temp.add(a + "");
//            temp.add("2");
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("3"));
//            temp.add(b + "");
//            temp.add("3");
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("4"));
//            temp.add(c + "");
//            temp.add("4");
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("5"));
//            temp.add(d + "");
//            temp.add("5");
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("6"));
//            temp.add(e + "");
//            temp.add("6");
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("8"));
//            temp.add(f + "");//粉丝数
//            temp.add("8");//临时当做8
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("9"));
//            temp.add(t + "");//移动粉丝数
//            temp.add("9");//临时当做9
//            listt.add(temp);
//
//            temp = new ArrayList<String>();
//            temp.add(namemap.get("10"));
//            temp.add(x + "");//系统粉丝数
//            temp.add("10");//临时当做10
//            listt.add(temp);
//        }
//
//        //增加用该用户创建的购物卡
//
//        int count = baseBeanService.getConutByBySqlAndParams("select count(*) from dt_giftcards gc where gc.operator in (select ec.staffid from t_eshop_cuscom ec where ec.sccid  = ?)", new Object[]{sccid});
//        temp = new ArrayList<String>();
//        temp.add("办理购物卡");
//        temp.add(count + "");//开通购物卡数
//        temp.add("15");//临时当做15
//        listt.add(temp);
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("listt", listt);
//        JSONObject oj = JSONObject.fromObject(map);
//        result = oj.toString();
//        return "assignmentData";
//    }


    //get()set()
    public List<TrilateralData> getTrilateralDataList() {
        return trilateralDataList;
    }

    public void setTrilateralDataList(List<TrilateralData> trilateralDataList) {
        this.trilateralDataList = trilateralDataList;
    }

    public String getUploadProject() {
        return uploadProject;
    }

    public void setUploadProject(String uploadProject) {
        this.uploadProject = uploadProject;
    }

    public TrilateralData getTrilateralData() {
        return trilateralData;
    }

    public void setTrilateralData(TrilateralData trilateralData) {
        this.trilateralData = trilateralData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getCusType() {
        return cusType;
    }

    public void setCusType(String cusType) {
        this.cusType = cusType;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }

    public TEshopCusCom getCuscom() {
        return cuscom;
    }

    public void setCuscom(TEshopCusCom cuscom) {
        this.cuscom = cuscom;
    }

    public TEshopCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(TEshopCustomer customer) {
        this.customer = customer;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public ContactCompany getContactCompany() {
        return contactCompany;
    }

    public void setContactCompany(ContactCompany contactCompany) {
        this.contactCompany = contactCompany;
    }

    public ProductPackaging getProductPackaging() {
        return productPackaging;
    }

    public void setProductPackaging(ProductPackaging productPackaging) {
        this.productPackaging = productPackaging;
    }

    public List<BaseBean> getList() {
        return list;
    }

    public void setList(List<BaseBean> list) {
        this.list = list;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public Map<Integer, String[]> getMaplist1() {
        return maplist1;
    }

    public void setMaplist1(Map<Integer, String[]> maplist1) {
        this.maplist1 = maplist1;
    }

    public List<Object> getList1() {
        return list1;
    }

    public void setList1(List<Object> list1) {
        this.list1 = list1;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getKhd() {
        return khd;
    }

    public void setKhd(String khd) {
        this.khd = khd;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getTrusteeship() {
        return trusteeship;
    }

    public void setTrusteeship(String trusteeship) {
        this.trusteeship = trusteeship;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }
}
