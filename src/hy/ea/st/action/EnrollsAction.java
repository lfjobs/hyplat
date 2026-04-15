package hy.ea.st.action;

import com.faceSDK.FaceRecognitionController;
import com.faceSDK.faceUtil.EmpwerUtils;
import com.faceSDK.faceVO.FaceRecognitionVO;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.service.MyCenterService;
import com.tiantai.wfj.service.WfjJifenService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.*;
import hy.ea.bo.DrivingSchool.SchProCategory;
import hy.ea.bo.company.ContactRelation;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.bo.office.face.OfficeFaceEmpower;
import hy.ea.bo.office.face.TbFaceDevice;
import hy.ea.face.service.FaceService;
import hy.ea.finance.service.SetSubsidizeService;
import hy.ea.marketing.service.SuperSelfSerivce;
import hy.ea.service.UpLoadFileService;
import hy.ea.st.service.EnrollsService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/7/18.
 */

@Controller
@Scope("prototype")
public class EnrollsAction {

    @Resource
    private EnrollsService enrollsService;

    @Resource
    private BaseBeanService baseBeanService;

    @Resource
    private ServerService serverService;

    @Resource
    private BaseBeanDao beandao;
    @Resource
    private SuperSelfSerivce smSerivce;
    @Resource
    private SetSubsidizeService setSubsidizeService;
    @Resource
    private WfjJifenService wfjJifenService;

    @Resource
    private FaceService faceService;
    @Resource
    private UpLoadFileService fileService;
    private String ccompanyID;
    private String goodsName;
    private String companyName;
    private String photo;
    private String remark;
    private String searchName;
    private String companyID;
    private String status;
    private String result;
    private String staffId;
    private String carType;
    private String staffName;
    private String carNum;
    private String selectType; //1 教练员 2 客服 3 培训主管
    private static final String postName1 = "教练";
    private static final String postName2 = "客服";
    private static final String postName3 = "培训主管";
    private String type;//活动类型
    private Enroll enrollForm;
    private CAccount account;
    private String companyIdentifier;
    private int pageNumber;
    private int pageSize;
    private PageForm pageForm;
    private String proName;
    private String search;
    private String parName;
    private String allPro;
    private String ppid;
    private String price;
    private String licenceType;
    private String brand;
    private ProductPackaging product;
    private String categoryName;
    private String categoryId;
    private String enrollID;
    private String siteType;
    private StaffAddress staffAddress;// 地址管理 //购买的地址
    private Staff staff;
    public String photoInfoFileName;
    public File photoInfo;

    public String findCompanyID() {
        String sql = "select cc.* from dt_ccom_com cc where cc.ccompany_id = ?";
        Object object = beandao.getObjectBySqlAndParams(sql, new Object[]{ccompanyID});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("company", object);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    public String getProductByType() {
        String sql = "select s.categoryId, s.categoryName,s.companyId from dt_SchProCategory s where s.companyId = ?";
        List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql, new Object[]{companyID});
        if (!"".equals(companyID) && companyID != null) {
            List<BaseBean> beans = new ArrayList<BaseBean>();
            if (list.size() == 0 || list == null) {
                System.out.println("start---------------------");
                String[] str = new String[]{"A1", "A2", "A3", "B1", "B2", "C1", "C2", "C3", "C4", "C5", "D", "E", "F", "M", "N", "P"};//,"从业资格证","其他辅助产品"
                for (int i = 0; i < str.length; i++) {
                    SchProCategory sch = new SchProCategory();
                    sch.setCompanyId(companyID);
                    sch.setCategoryId(serverService.getServerID("category"));
                    sch.setCategoryName(str[i]);
//                if(str[i].equals("从业资格证")){
//                    sch.setStatus("2");
//                }else if (str[i].equals("其他辅助产品")){
//                    sch.setStatus("3");
//                }else {
                    sch.setStatus("1");
//                }
                    beans.add(sch);
                }
                baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
                return getProductByType();
            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    //跳转报名
    public String getEnroll() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        String ptppid = request.getParameter("ptppid");
        SessionWrap sw = SessionWrap.getInstance();
        Company com = (Company) beandao.getBeanByHqlAndParams("from Company y where y.companyID = ?", new Object[]{companyID});

        String cardNum = request.getParameter("cardNum");
        String mode = request.getParameter("mode");
        TEshopCusCom eshopCusCom = null;
        if (cardNum != null && !cardNum.equals("")) {
            eshopCusCom = smSerivce.getTEshopByCum(cardNum);
//           TEshopCustomer customer = (TEshopCustomer) baseBeanService
//                    .getBeanByHqlAndParams(
//                            "from TEshopCustomer where account=? AND logOff=0",
//                            new Object[]{eshopCusCom.getAccount()});
//            sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
//            sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, eshopCusCom);
        } else {
            if (!"cash".equals(mode)) {
                Staff staff = (Staff) sw.getObject(session,
                        SessionWrap.KEY_STAFF);
                eshopCusCom = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
            }
        }

        if (eshopCusCom != null) {
            //查找学员信息
            session.setAttribute("staffId", eshopCusCom.getStaffid());
            Staff s = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{eshopCusCom.getStaffid()});
            //查询当前报名的公司有没有绑定人脸识别设备，如果有，显示上传真实人脸，如果没有，不显示
            String sql = "select FACE_DEVICE_ID,DEVICE_SN from OFFICE_FACEDEVICE where COMPANY_ID=? and FACE_DEVICE_ID IS NOT NULL";
            List<BaseBean> deviceList = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{companyID});
            if(deviceList!=null && deviceList.size()>0){
                session.setAttribute("deviceList", "show");
            }else{
                session.setAttribute("deviceList", "none");
            }
            session.setAttribute("staffName", s.getStaffName());
            session.setAttribute("personImage", s.getHeadimage());
            session.setAttribute("realname",s.getRealname());
            session.setAttribute("phone", eshopCusCom.getAccount());
            session.setAttribute("custype", eshopCusCom.getCusType());
            session.setAttribute("staffIdentityCard", s.getStaffIdentityCard());

            //查找学员推荐人数据
            StringBuilder msql = new StringBuilder("SELECT MKSCCID, MKUSERID, STAFFNAME FROM DTMARKETING M");
            msql.append(" LEFT JOIN T_ESHOP_CUSCOM E ON M.MKSCCID=E.SCCID");
            msql.append(" LEFT JOIN DT_HR_STAFF S ON E.STAFFID=S.STAFFID");
            msql.append(" WHERE USERSCCID = ?");
            Object[] m = (Object[]) beandao.getObjectBySqlAndParams(msql.toString(), new Object[]{eshopCusCom.getSccId()});
            if (m != null) {
                session.setAttribute("mphone", m[1]);
                session.setAttribute("mname", m[2]);
                session.setAttribute("msccid", m[0]);
            }

            String priceType = request.getParameter("priceType");
            String activityid = "";
            if (request.getParameter("activityid") != null && !request.getParameter("activityid").equals("undefined") && !request.getParameter("activityid").equals("")) {
                activityid = request.getParameter("activityid");
            } else {
                activityid = null;
            }
            String cost = request.getParameter("cost");
            if ("2".equals(priceType)) {
                if (eshopCusCom.getCusType().compareTo("6") >= 0) {//判断用户会员等级[小于6级时产品展示vip价]
                    price = cost;
                    priceType = "0";
                }
            }
            request.setAttribute("priceType", priceType);
            request.setAttribute("activityid", activityid);

        } else {
            session.setAttribute("staffId", "");
            if (mode == null || mode.equals("")) {
                return "login";
            }
        }

        //查找报名产品设备投资佣金的金额
        ProSetup ps = (ProSetup) beandao.getBeanByHqlAndParams("from ProSetup where state=? and  ppid=?", new Object[]{"00", ppid});
        if (ps != null) {
            ProSetupSub pss = (ProSetupSub) beandao.getBeanByHqlAndParams("from ProSetupSub where typePpid=? and ppid=? and suid=?", new Object[]{"p20170605KY3VAANZJG0000000003", ppid, ps.getSuid()});
            if (pss != null) {
                session.setAttribute("sbtzfee", pss.getAmount());
            } else {
                session.setAttribute("sbtzfee", 0);
            }
        }

        Object logo = beandao.getObjectBySqlAndParams("select y.logopath from dt_ccom_com cc ,dtcontactcompany y where cc.ccompany_id = y.ccompanyid  and cc.compnay_id = ?", new Object[]{companyID});
        session.setAttribute("com", com);
        session.setAttribute("logo", logo);
        session.setAttribute("goodsName", goodsName);
        session.setAttribute("companyID", companyID);
        session.setAttribute("photo", photo);
        session.setAttribute("remark", remark);
        product = (ProductPackaging) beandao.getBeanByHqlAndParams("from  ProductPackaging where  ppID=?", new Object[]{ppid});


        if (staffAddress == null) {
            if (eshopCusCom != null) {
                String staffaddhql = "from StaffAddress where staffID=? and isDefault='是'";
                staffAddress = (StaffAddress) baseBeanService
                        .getBeanByHqlAndParams(staffaddhql,
                                new String[]{eshopCusCom.getStaffid()});
            }
        } else {
            if (staffAddress.getAddressID() != null && staffAddress.getAddressID() != "") {
                String staffaddhql = "from StaffAddress where  addressID=? ";
                staffAddress = (StaffAddress) baseBeanService
                        .getBeanByHqlAndParams(staffaddhql,
                                new String[]{staffAddress.getAddressID()});
            }
        }

        List<Object> list = enrollsService.getPromotionList(ppid, ptppid);
        request.setAttribute("ptlist", list);
        return "enrollPage";
    }

    //查找驾校
    public String findDrive() {
        List<Object> objList = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select c.companyid,cc.ccompanyid,c.companyname,cc.logopath,cc.companyaddr from dtcontactcompany cc,dtcompany c,dt_ccom_com co where c.companyid = co.compnay_id and co.ccompany_id = cc.ccompanyid and c.industrytype = ? ");
        objList.add("汽车交通工具/汽车驾校");
        if (searchName != null) {
            sql.append(" and c.companyname like ?");
            objList.add("%" + searchName + "%");
        }
        List<BaseBean> driveList = beandao.getListBeanBySqlAndParams(sql.toString(), objList.toArray());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("driveList", driveList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    //查找班型
    public String findPro() {
        List<Object> objList = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select pg.* from dt_productpackaging pg where pg.companyid =? and pg.parentname = ?");
        objList.add(companyID);
        objList.add(goodsName);
        if (searchName != null) {
            sql.append(" and pg.goodsname like ?");
            objList.add("%" + searchName + "%");
        }
        List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql.toString(), objList.toArray());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    //查找职务
    public String selectPro() {
        List<Object> objList = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct s.staffID,s.staffName,s.usedNmae,s.photo,s.reference,t.sccid,t.account,t.custype,");
        sql.append(" case when t.custype = ? then '平台' when t.custype = ? then '国税' ");
        sql.append(" when t.custype = ? then '地税' when t.custype = ? then '公司商城业主会员'");
        sql.append(" when t.custype = ? then '经理创业商城业主会员' when t.custype = ? then '主管商城业主会员'");
        sql.append(" when t.custype = ? then '业务商城业主会员' when t.custype = ? then 'VIP客户'");
        sql.append(" when t.custype = ? then '客户' else '' end ");
        sql.append(" from dtcos c left join dt_hr_deptpost d");
        sql.append(" on c.depPostID = d.depPostID  right join dt_hr_staff s on c.staffID = s.staffID and s.sccid is not null");
        if (selectType.equals("1")) {
            sql.append(" left join tb_jp_teacher co on co.staffid  = s.staffID");
        }
        sql.append(" left join t_eshop_cuscom t on t.sccid = s.sccid");
        sql.append("  where d.postName like ?  and c.companyID = ? and c.cosStatus = ? ");
        //String sql = "select s.staffID,s.staffName,s.usedNmae,s.photo,s.reference from dtcos c left join dt_hr_deptpost d on c.depPostID = d.depPostID  right join dt_hr_staff s on c.staffID = s.staffID left join dtaudition da on c.staffID = da.staffID  where da.companyID= ? and d.postName like ? and da.status = ? and c.companyID = ? and c.cosStatus = ? and c.status = ? order by s.staffID desc";
        objList.add("0");
        objList.add("0.5");
        objList.add("1");
        objList.add("2");
        objList.add("3");
        objList.add("4");
        objList.add("5");
        objList.add("6");
        objList.add("7");
        if (selectType.equals("1")) {
            objList.add("%" + postName1 + "%");
        } else if (selectType.equals("2")) {
            objList.add("%" + postName2 + "%");
        } else if (selectType.equals("3")) {
            objList.add("%" + postName3 + "%");
        }
        objList.add(companyID);
        objList.add("50");
        if (selectType.equals("1")) {
            JSONArray ja = (JSONArray) getqenm(categoryName);
            if (ja != null) {
                sql.append(" and co.teachpermitted in ( ");
                for (int i = 0; i < ja.size(); i++) {
                    sql.append(" ?");
                    objList.add(ja.get(i).toString());
                    if (i == ja.size() - 1) {
                        sql.append(" )");
                    } else {
                        sql.append(" ,");
                    }
                }
            }
        }
        sql.append(" order by s.staffID desc");
        if (searchName != null) {
            sql.delete(0, sql.length());
            objList.clear();
            sql.append("select * from (");
            sql.append("select distinct s.staffID,s.staffName,s.usedNmae,s.photo,s.reference,t.sccid,t.account,t.custype,");
            sql.append(" case when t.custype = ? then '平台' when t.custype = ? then '国税' ");
            sql.append(" when t.custype = ? then '地税' when t.custype = ? then '公司商城业主会员'");
            sql.append(" when t.custype = ? then '经理创业商城业主会员' when t.custype = ? then '主管商城业主会员'");
            sql.append(" when t.custype = ? then '业务商城业主会员' when t.custype = ? then 'VIP客户'");
            sql.append(" when t.custype = ? then '客户' else '' end ");
            sql.append(" from dtcos c left join dt_hr_deptpost d");
            sql.append(" on c.depPostID = d.depPostID  right join dt_hr_staff s on c.staffID = s.staffID and s.sccid is not null");
            if (selectType.equals("1")) {
                sql.append(" left join tb_jp_teacher co on co.staffid  = s.staffID");
            }
            sql.append(" left join t_eshop_cuscom t on t.sccid = s.sccid");
            sql.append(" where  d.postName like ?  and c.companyID = ? ");
            objList.add("0");
            objList.add("0.5");
            objList.add("1");
            objList.add("2");
            objList.add("3");
            objList.add("4");
            objList.add("5");
            objList.add("6");
            objList.add("7");

            if (selectType.equals("1")) {
                objList.add("%" + postName1 + "%");
            } else if (selectType.equals("2")) {
                objList.add("%" + postName2 + "%");
            } else if (selectType.equals("3")) {
                objList.add("%" + postName3 + "%");
            }
            objList.add(companyID);
            if (selectType.equals("1")) {
                JSONArray ja = (JSONArray) getqenm(categoryName);
                if (ja != null) {
                    sql.append(" and co.teachpermitted in ( ");
                    for (int i = 0; i < ja.size(); i++) {
                        sql.append(" ?");
                        objList.add(ja.get(i).toString());
                        if (i == ja.size() - 1) {
                            sql.append(" )");
                        } else {
                            sql.append(" ,");
                        }
                    }
                }
            }
            sql.append("and c.cosStatus = ?  order by s.staffID desc");
            sql.append(") p where p.staffname like ? or p.reference like ?");
            objList.add("50");
            objList.add("%" + searchName + "%");
            objList.add("%" + searchName + "%");
        }
        List<BaseBean> cosList = beandao.getListBeanBySqlAndParams(sql.toString(), objList.toArray());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cosList", cosList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 根据帐号id查询设备投资责任人
     *
     * @return
     */
    public String selectSbtz() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String sccid = request.getParameter("sccid");
        Map<String, Object> map = new HashMap<String, Object>();
        //查询设备投资责任人
        StringBuilder sql = new StringBuilder("select d.dbsccid, s.staffname,e.account, e.custype,d.dbStaffId ");
        sql.append(" from dt_deviceBind d left join dt_devicebind_staff ds on d.dbid = ds.dbsdbid");
        sql.append(" left join dt_car_carinformation c on d.dbcarid = c.carid");
        sql.append(" left join dt_hr_Staff s on d.dbstaffid = s.staffid");
        sql.append(" left join t_Eshop_Cuscom e on d.dbsccid = e.sccid");
        sql.append(" where d.dbstatus=? and ds.dbsstatus=? and ds.dbssccid=? and d.dbtzType=? and c.companyid = ?");
        Object deviceBind = baseBeanService.getObjectBySqlAndParams(sql.toString(), new Object[]{'1', '1', sccid, "01",companyID});
        if (deviceBind != null) {
            Object[] d = (Object[]) deviceBind;
            map.put("Phone", d[2]);
            map.put("sccid", d[0]);
            map.put("staffid", d[4]);
            map.put("staffname", d[1]);
        }
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 查找帐号
     *
     * @return
     */
    public String selectShopCus() {
        List<Object> objList = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select s.staffID,s.staffName,s.usedNmae,s.photo,s.reference from dtcos c left join dt_hr_deptpost d");
        sql.append(" on c.depPostID = d.depPostID  right join dt_hr_staff s on c.staffID = s.staffID ");
        sql.append("  where d.postName like ?  and c.companyID = ? and c.cosStatus = ? ");
        if (selectType.equals("1")) {
            objList.add("%" + postName1 + "%");
        } else if (selectType.equals("2")) {
            objList.add("%" + postName2 + "%");
        } else if (selectType.equals("3")) {
            objList.add("%" + postName3 + "%");
        }
        objList.add(companyID);
        objList.add("50");
        if (selectType.equals("1")) {
            sql.append("and co.teachpermitted like ?");
            objList.add("%" + categoryName + "%");
        }
        sql.append(" order by s.staffID desc");
        if (searchName != null) {
            sql.delete(0, sql.length());
            objList.clear();
            sql.append("select * from (");
            sql.append("select s.staffID,s.staffName,s.usedNmae,s.photo,s.reference from dtcos c left join dt_hr_deptpost d");
            sql.append(" on c.depPostID = d.depPostID  right join dt_hr_staff s on c.staffID = s.staffID ");
            if (selectType.equals("1")) {
                sql.append("left join tb_jp_teacher co on co.staffid  = s.staffID");
            }
            sql.append(" where  d.postName like ?  and c.companyID = ? ");
            if (selectType.equals("1")) {
                objList.add("%" + postName1 + "%");
            } else if (selectType.equals("2")) {
                objList.add("%" + postName2 + "%");
            } else if (selectType.equals("3")) {
                objList.add("%" + postName3 + "%");
            }
            objList.add(companyID);
            if (selectType.equals("1")) {
                sql.append("and co.teachpermitted like ? ");
                objList.add("%" + categoryName + "%");
            }
            sql.append("and c.cosStatus = ?  order by s.staffID desc");
            sql.append(") p where p.staffname like ? or p.reference like ?");
            objList.add("50");
            objList.add("%" + searchName + "%");
            objList.add("%" + searchName + "%");
        }
        List<BaseBean> cosList = beandao.getListBeanBySqlAndParams(sql.toString(), objList.toArray());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cosList", cosList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    //查找教练下面的车
    public String coachByCar() {
        List<Object> objList = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select c.photo,c.carnum,c.cartype,c.carid from dt_car_carinformation c where c.staffid = ? and (state3=? or state2=?)");
        objList.add(staffId);
        objList.add("01");
        objList.add("01");
        if (searchName != null) {
            sql.append(" and c.cartype like ?");
            objList.add("%" + searchName + "%");
        }
        List<BaseBean> carList = beandao.getListBeanBySqlAndParams(sql.toString(), objList.toArray());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("carList", carList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    //选择场地
    public String findSite() {
        List<Object> objList = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select t.erid,t.ername,t.itslocation from DT_ExaminationRoom t where t.sitetype = ? and t.companyid = ?");
        objList.add("1");
        objList.add(companyID);
        if (searchName != null) {
            sql.append(" and t.ername like ?");
            objList.add("%" + searchName + "%");
        }
        List<BaseBean> siteList = beandao.getListBeanBySqlAndParams(sql.toString(), objList.toArray());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("siteList", siteList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();

        return "success";
    }

    /**
     * 查找公司帐号
     *
     * @return
     */
    public String findComShopCus() {
        Map<String, Object> map = new HashMap<String, Object>();
        TEshopCusCom cus = (TEshopCusCom) baseBeanService
                .getBeanByHqlAndParams("from TEshopCusCom where companyId=?", new Object[]{companyID});
        if (cus != null) {
            map.put("comPhone", cus.getAccount());
            map.put("comsccid", cus.getSccId());
            map.put("comstaffid", cus.getStaffid());
        }
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }
    //保存报名信息
    public String saveEnroll() throws IOException {
        HttpSession session = ServletActionContext.getRequest().getSession();
        List<BaseBean> beans = new ArrayList<BaseBean>();
        if (enrollForm.getEnrollID() == "" || enrollForm.getEnrollID() == null) {
            enrollForm.setEnrollID(serverService.getServerID("enroll"));
        }
        enrollForm.setEnrollDate(new Date());// new Date()为获取当前系统时间
        enrollForm.setManagementFees(enrollForm.getPrice()); //管理费
//        TbJpStudentInfo stu = new TbJpStudentInfo();
//        stu.setStudentId(serverService.getServerID("student"));
//        stu.setApplyDate(enrollForm.getEnrollDate());
//        stu.setCompanyId(enrollForm.getCompanyID());
//        stu.setStaffId(enrollForm.getStaffId());
//        stu.setTrainType(enrollForm.getLicenceType());

//        beans.add(stu);
        if (enrollForm.getStaffId() == null || enrollForm.getStaffId().equals("")) {
            TEshopCusCom tuicus = enrollsService.getTEshopByStaffID(enrollForm.getCoachStaffID());
            String staffID = wfjJifenService.zhuCe(tuicus, tuicus != null ? tuicus.getSccId() : "", staff.getReference(), "123456", staff);
            enrollForm.setStaffId(staffID);
        }
        beans.add(enrollForm);
//        HttpClient httpClient = new HttpClient();
//        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
//        String url = Constant.TIMING_DOMAIN + "/jptrainperiod/findTrainPeriod";
//        PostMethod postMethod = new PostMethod(url);
//        NameValuePair[] data = {
//                new NameValuePair("carType", enrollForm.getLicenceType()),
//        };
//        postMethod.setRequestBody(data);
//        httpClient.executeMethod(postMethod);
//        // 读取内容
//        InputStream resInputStream = null;
//        resInputStream = postMethod.getResponseBodyAsStream();
//        // 处理内容
//        BufferedReader reader = new BufferedReader(new InputStreamReader(resInputStream));
//        String tempBf = null;
//        StringBuffer html = new StringBuffer();
//        while ((tempBf = reader.readLine()) != null) {
//
//            html.append(tempBf);
//        }
//        String resMes = html.toString();
//        String[] str = null;
//        if (resMes != null && resMes.indexOf(",") != -1) {
//            str = resMes.split(",");
//        }
        Object strs = baseBeanService.getObjectBySqlAndParams("select P1_TIME,P2_TIME,P3_TIME,P4_TIME,TOTAL_TIME  from tb_jp_train_period where CAR_TYPE = ?",new Object[]{enrollForm.getLicenceType()});
        Object[] str = (Object[])strs;
        SubjectHour subjectHour = new SubjectHour();
        subjectHour.setSubjectHourId(serverService.getServerID("period"));
        subjectHour.setStaffId(enrollForm.getStaffId());
        subjectHour.setLicenseType(enrollForm.getLicenceType());
        if (str != null && str[1] != null) {
            subjectHour.setP1Time(Integer.parseInt(str[0].toString()));
            subjectHour.setP2Time(Integer.parseInt(str[1].toString()));
            subjectHour.setP3Time(Integer.parseInt(str[2].toString()));
            subjectHour.setP4Time(Integer.parseInt(str[3].toString()));
            subjectHour.setTotalTime(Integer.parseInt(str[4].toString()));
        }
        if (Double.valueOf(enrollForm.getPrice()) == 0) {
            subjectHour.setHasTime(0);
        } else {
            subjectHour.setHasTime(subjectHour.getTotalTime());
        }
        beans.add(subjectHour);
//        System.out.println(subjectHour.getP1Time());
        try {
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
            enrollID = enrollForm.getEnrollID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("enrollID", enrollID);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        //人脸识别设备添加人脸
        try {
            insertFace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return "success";
        }
    }

    private void insertFace() {
        //判断当前人员所报名的驾校或者场所是否已绑定设备，如果已绑定设备直接进行人脸识别的添加，如果没有则不做任何的操作
        BaseBean beanByHqlAndParams = baseBeanService.getBeanByHqlAndParams("from TbFaceDevice where siteId=?", new Object[]{enrollForm.getSiteID()});
        if(beanByHqlAndParams!=null){
            //学员添加成功之后，向人脸识别中添加数据，
            OfficeFaceEmpower officeFaceEmpower=new OfficeFaceEmpower();
            officeFaceEmpower.setCompanyId(enrollForm.getCompanyID());
            //通过学员ID查询当前学员的头像，用于下发到人脸识别的设备
            Staff staffInfo = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{enrollForm.getStaffId()});
            officeFaceEmpower.setPersonImage(staffInfo.getHeadimage());
            officeFaceEmpower.setStaffName(staffInfo.getRealname());
            officeFaceEmpower.setStaffId(enrollForm.getStaffId());
            officeFaceEmpower.setPayMoney(new BigDecimal(enrollForm.getPrice()));
            officeFaceEmpower.setCreateTime(new Date());
            officeFaceEmpower.setEmpowerTime(new Date());
            officeFaceEmpower.setEmpowerState(1);
            officeFaceEmpower.setPersonType(1);//系统人员
            officeFaceEmpower.setPayStatus(1);
            officeFaceEmpower.setNotes("系统学员订单支付");
            officeFaceEmpower.setSiteId(enrollForm.getSiteID());
            //查询学员是否已添加，已添加直接授权 personType=1系统人员
            OfficeFaceEmpower isNotAdd = (OfficeFaceEmpower)baseBeanService.getBeanByHqlAndParams("from OfficeFaceEmpower where staffId=? and personType=1", new Object[]{enrollForm.getStaffId()});
            if(isNotAdd!=null){
                officeFaceEmpower.setStaffKey(isNotAdd.getStaffKey());
            }else{
                faceService.savePersonEmpower(officeFaceEmpower);
            }
            //向火种平台授权
            String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
            FaceRecognitionVO faceRecognitionVO=new FaceRecognitionVO();
            faceRecognitionVO.setType(1);
            //faceRecognitionVO.setFaceUrl("http://localhost:8080/"+officeFaceEmpower.getPersonImage());
            faceRecognitionVO.setFaceUrl(path+officeFaceEmpower.getPersonImage());
            //这里的Code不用直接用staffId,应使用表字段的StaffKey
            faceRecognitionVO.setCode(officeFaceEmpower.getStaffKey());
            faceRecognitionVO.setEmpowerType(1);
            faceRecognitionVO.setName(officeFaceEmpower.getStaffName());
            List<TbFaceDevice>  device=faceService.findCompanyDeviceByCompanyKey(officeFaceEmpower.getSiteId());
            String devices="";
            for (int i = 0; i < device.size() ; i++) {
                if(i==device.size()-1){
                    devices+=device.get(i).getFaceDeviceId();
                }else{
                    devices+=device.get(i).getFaceDeviceId()+",";
                }
            }
            faceRecognitionVO.setDeviceId(devices);
            FaceRecognitionController fc=new FaceRecognitionController();
            EmpwerUtils.personCreateOrUpdate(faceRecognitionVO,officeFaceEmpower,faceService);
        }
    }

    //保存报名账号信息
    public void saveAccount() {
        if (account.getAccountID() == "" || account.getAccountID() == null) {
            account.setAccountID(serverService.getServerID("account"));
        }
        String hql = "from Company d where d.companyIdentifier=? ";
        Company com = (Company) beandao.getObjectByHqlAndParams(hql, new String[]{companyIdentifier});
        String hql2 = "from CRole r where r.roleName like ?";
        CRole role = (CRole) beandao.getBeanByHqlAndParams(hql2, new String[]{"%客户%"});
        account.setCompanyID(com.getCompanyID());
        account.setCompanyName(com.getCompanyName());
        account.setRoleID(role.getRoleID());
        account.setCompany(com);
        account.setAccountPassword(Utilities.MD5(account.getAccountPassword()));
        account.setAccountName(account.getAccountEmail().toLowerCase());
        account.setAccountEmail(account.getAccountEmail().toLowerCase());
        account.setAccountStatus("00");
        account.setAccountOnLine("00");
        enrollsService.saveAccount(account);
    }

    //团队展示
    public String getByTeam() {

        return "team";
    }

    //设备展示
    public String getByEqpt() {

        return "equipment";
    }

    //设备展示详情
    public String getEqptDetails() {
        return "equipmentDetails";
    }

    //团队详情
    public String getTeamDetails() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String staffId = request.getParameter("staffId");
        String sql = "select t.name,s.staffcode,t.photo,t.mobile,t.address,e.sccid from dt_hr_staff s,tb_jp_teacher t left join t_eshop_cuscom e on e.staffid = t.staffid where t.staffid = s.staffid and t.staffid = ?";
        Object staff = beandao.getObjectBySqlAndParams(sql, new Object[]{staffId});
        request.setAttribute("teacher", staff);
        return "teamDetails";
    }

    public String AjaxTeam() {
        List<Object> objList = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select t.staffid,t.name,t.photo,t.mobile ,y.companyname from tb_jp_teacher t,dtcompany y where t.companyid = y.companyid");
        if (companyID != null && !"".equals(companyID)) {
            sql.append(" and t.companyid = ?");
            objList.add(companyID);
        }
        if (staffName != null) {
            sql.append(" and t.name like ?");
            objList.add("%" + staffName + "%");
        }
        List<BaseBean> personnelList = beandao.getListBeanBySqlAndParams(sql.toString(), objList.toArray());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personnelList", personnelList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    public String AjaxEqment() {
        List<Object> objList = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select ca.carnum,ca.cartype,ca.staffname,s.reference,cy.companyaddr,ca.photo,s.staffid,ca.carid from dtcompany c,dtcontactcompany cy,dt_ccom_com cc, dt_car_carinformation ca inner join  dt_hr_staff s  on ca.staffid = s.staffid where  ca.state1 = ? and c.companyid=ca.companyid and c.companyid=cc.compnay_id and cy.ccompanyid = cc.ccompany_id");
        objList.add("02");
        if (carNum != null && !"".equals(carNum)) {
            sql.append(" and (ca.carnum like ? or ca.cartype like ?)");
            objList.add("%" + carNum + "%");
            objList.add("%" + carNum + "%");
        }
        if (companyID != null && !"".equals(companyID)) {
            sql.append(" and ca.companyid = ?");
            objList.add(companyID);
        }
        List<BaseBean> carList = beandao.getListBeanBySqlAndParams(sql.toString(), objList.toArray());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("carList", carList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    //报名费
    public String EnrollFee() {
        String hql = "from ProductPackaging p where p.companyID = ? and p.parentName = ? and p.type = ? ";
        ProductPackaging product = (ProductPackaging) beandao.getObjectByHqlAndParams(hql, new Object[]{companyID, "报名费用", "产品分类"});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("product", product);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 增加往来关系-学员
     */
    public String addRelation() {
        ContactRelation contactRelation = new ContactRelation();
        contactRelation.setRelationID(serverService.getServerID("contactrelation"));
        contactRelation.setRelation("学员");
        contactRelation.setCompanyID(companyID);
        contactRelation.setStaffID(staffId);
        enrollsService.addRelation(contactRelation);
        return "success";
    }

    /**
     * 查找往来关系
     */
    public String findRelation() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String sql = "select t.relation from dtContactRelation t where t.staffid = ? and t.companyID = ? ";
        List<BaseBean> contactRelationList = beandao.getListBeanBySqlAndParams(sql, new Object[]{staffId, account.getCompanyID()});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contactRelationList", contactRelationList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 跳转产品分类
     */
    public String getProductType() {

        return "productType";
    }

    //联营商城
    public String getAssociatedMall() {

        return "associatedMall";
    }

    public Map getSelProductList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Object> parms = new ArrayList<Object>();
        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = df.format(new Date());
        StringBuilder sql = new StringBuilder();
        sql.append(" select p.goodsname,ps.ppid,ps.re_price,p.brand,p.image,p.goodsid,p.companyid,p.stockSize,p.monthSales,p.remark ");
        sql.append(" ,p.type ,cc.companyAddr,ccom.ccompany_Id,p.categoryName,p.categoryId,pap.actPrice,pv.vip,pa.type activityType ");
        sql.append(" ,ps.suid,pv.vipid,pap.actpriceid from DT_PRO_SETUP ps ");
        sql.append(" inner join dt_ProductPackaging p on p.ppid = ps.ppid ");
        sql.append(" inner join DT_ccom_com ccom on p.companyid = ccom.compnay_id ");
        sql.append(" inner join dtContactCompany cc on ccom.ccompany_Id = cc.ccompanyID ");
        sql.append(" inner join dt_schprocategory sp on p.categoryid = sp.categoryid and sp.companyid = p.companyid ");
        sql.append(" left join dt_pro_vip pv on pv.ppid = p.ppid and pv.state = '00' ");
        sql.append(" left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state = '00' ");
        sql.append(" left join dt_pro_activity pa on pa.activityId = pap.activityid ");
        sql.append(" and pa.endtime >= to_date(?, 'yyyy-MM-dd HH24:MI:SS') ");
        sql.append(" and pa.starttime <= to_date(?, 'yyyy-MM-dd HH24:MI:SS') ");
        sql.append(" and pa.state <> '04'  and pa.state <> '03'  and pa.state <> '02' ");
        sql.append(" where p.showweixin ='01' and ps.state = '00' and p.qualified = '1' ");
        sql.append(" and p.type = '学员报名' and p.companyid = ? ");

        parms.add(currentTime);
        parms.add(currentTime);
        parms.add(companyID);
        if (allPro != null && !"".equals(allPro)) {
            sql.append(" and sp.status = ?");
            parms.add("1");
        } else if (categoryId != null && !"".equals(categoryId)) {
            sql.append(" and p.categoryId = ? ");
            parms.add(categoryId);
        }
        if (proName != null && !"".equals(proName)) {//p.goodsname//ps.ppname
            sql.append(" and p.goodsname like ? ");
            parms.add("%" + proName + "%");
        }
        sql.append(" and (p.type not in(?,?,?,?,?,?) or p.type is null) ");
        parms.add("个人会员");
        parms.add("公司会员");
        parms.add("省级城市");
        parms.add("县级城市");
        parms.add("乡镇城市");
        parms.add("村级新城");
        if (search != null) {
            if (search.equals("pop")) {
                sql.append(" order by cast(p.monthsales as int) desc");
            } else if (search.equals("plow")) {
                sql.append(" order by cast(ps.re_price as number)");
            } else if (search.equals("ptop")) {
                sql.append(" order by cast(ps.re_price as number) desc");
            } else if (search.equals("smart")) {
                sql.append(" order by cast(p.monthsales as int) desc");
            }
        }

        StringBuilder tsql = new StringBuilder("select 1+nvl(s.total_pct,0)/100");
        tsql.append(" from dtContactCompany dc left join dt_ccom_com cc on dc.ccompanyId = cc.ccompany_Id");
        tsql.append(" left join dt_set_subsidize s");
        tsql.append(" on dc.industrytype = s.gtid  and s.stutas= ? where cc.compnay_id = ?");

        try {
            Object pct = baseBeanService.getObjectBySqlAndParams(tsql.toString(), new Object[]{"01", companyID});
            pageForm = baseBeanService.getPageFormBySQL(
                    (null != pageForm ? pageForm.getPageNumber() : 1), 15, sql.toString(), "select count(*) from (" + sql + ")", parms.toArray());
            map.put("pageForm", pageForm);
            map.put("pct", pct == null ? 1 : pct);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public String getAjax() {
        Map<String, Object> map = getSelProductList();
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    //平台新闻
    public String AjaxNewsList() {
        pageForm = NewsList();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    public PageForm NewsList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();
        sql.append("select p.goodsName,p.PackagingDate,p.image,p.goodsID,p.model,p.ppid,p.type,p.companyID,m.ccompany_Id,ct.companyname");
        sql.append(" from dt_ProductPackaging p, DT_ccom_com m, dtcontactcompany ct");
        sql.append(" where m.ccompany_id = ct.ccompanyid and p.companyid = m.compnay_id and p.type like ? ");
        sql.append(" and ct.industrytype like ? ");
        sql.append(" order by p.PackagingDate desc");
        objList.add("%新闻%");
        objList.add("%汽车驾校%");
        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1), 6, sql.toString(), "select count(*) from (" + sql + ")",
                objList.toArray());
        return pageForm;
    }

    // 新闻页面
    public String News() {


        return "news";
    }

    //查寻公司图片
    public String getComPhoto() {
        Object photo = beandao.getObjectBySqlAndParams("select y.logopath from dtcontactcompany y where y.ccompanyid = ?", new Object[]{ccompanyID});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("photo", photo);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    //驾校联盟-活动模块
    public String activityList() {
        String sql = "select p.company_id,p.activity_type from dt_prize_activity p where sysdate between p.starting_time+0 and p.end_time+0 and trim(p.activity_range) = ? and trim(p.status) = ? and trim(p.activity_type) = ?";
        List<BaseBean> activityList = beandao.getListBeanBySqlAndParams(sql, new Object[]{"0", "0", type});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("activityList", activityList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    //驾校联盟-热门驾校产品
    public String driverProducts() {
        String sql = "select pp.ppid,pp.goodsid,pp.companyid,cc.ccompanyid,pp.image,pp.goodsname,pp.type," +
                " pp.brand,pp.stockSize,pp.monthSales,pp.remark,cc.companyAddr,pp.categoryName,pp.categoryId,ps.re_price" +
                " from dt_productpackaging pp, dt_ccom_com cm,dtcontactcompany cc,DT_PRO_SETUP ps  " +
                " where pp.ppid = ps.ppid and cm.ccompany_id = cc.ccompanyid and pp.companyid = cm.compnay_id " +
                " and cc.industrytype like ? and  pp.showweixin = ? and pp.qualified= ? and rownum < ?" +
                " order by pp.monthsales desc";

        List<BaseBean> ProductList = beandao.getListBeanBySqlAndParams(sql, new Object[]{"%汽车驾校%", "01", "1", "7"});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ProductList", ProductList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    //驾校联盟-热门驾校
    public String stList() {
        String sql = " select cc.ccompanyid,cc.logopath,cc.industrytype,cc.companyname from dtcontactcompany cc where cc.industrytype like ? and rownum < ?  and cc.webstatus=? and cc.ccompanyid <> ?";
        List<BaseBean> stList = beandao.getListBeanBySqlAndParams(sql, new Object[]{"%汽车驾校%", "10", "01", ccompanyID});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("stList", stList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    //投诉列表
    public String complaints() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom eshopCusCom = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
        Map<String, Object> map = new HashMap<String, Object>();
        if (eshopCusCom == null) {
            map.put("complaintList", null);
        } else {
            List<BaseBean> complaintList = baseBeanService.getListBeanBySqlAndParams("select d.photo,t.name,s.teacherid ,s.staffid,c.companyname from tb_elyc_Complaint_School s,dt_hr_Staff d ,TB_JP_TEACHER t,dtcompany c where c.companyid = s.companyid and d.staffid=t.staffid and t.teacherid=s.teacherid and s.staffid=? ", new Object[]{eshopCusCom.getStaffid()});
            map.put("complaintList", complaintList);
        }
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    //场地
    public String getSite() {
        String sql = "select distinct e.erid,p.image,e.ername,e.itslocation,e.sitetype from dt_examinationroom e,dt_productpackaging p where e.staffid = p.staffid";
        List<BaseBean> siteList = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("siteList", siteList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 验证报名
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String validateEnroll() {
        HttpServletRequest request = ServletActionContext.getRequest();

        String account = request.getParameter("account");//手机账号
        String mode = request.getParameter("mode");
        String cardNum = request.getParameter("cardNum");
        String sccId = "";
        if ("cash".equals(mode)) {
            if (account != null && !account.equals("")) {
                sccId = enrollsService.getSccIdByAccount(account);
            }
        } else if ("scan".equals(mode)) {
            HttpSession session = ServletActionContext.getRequest().getSession();
            SessionWrap sw = SessionWrap.getInstance();
            TEshopCusCom eshopCusCom = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
            sccId = eshopCusCom.getSccId();
        } else if ("scard".equals(mode)) {

            TEshopCusCom eshopCusCom = smSerivce.getTEshopByCum(cardNum);
            sccId = eshopCusCom.getSccId();

        }
        String tips = enrollsService.validateEnroll(sccId,enrollForm.getLicenceType(),enrollForm.getCompanyID());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", tips);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";


    }

    /**
     * 准教车型对应的可执教车型
     * 通过可教车型查准教车型
     * @param categoryName 准教车型
     * @return
     */
    private Object getqenm(String categoryName) {
        JSONObject jo = new JSONObject();
        /*jo.accumulate("A1", new String[]{"A1", "A3", "B1", "B2", "C1", "C2", "C3", "C4", "C5", "M"});
        jo.accumulate("A2", new String[]{"A2", "B1", "B2", "C1", "C2", "C3", "C4", "C5", "M"});
        jo.accumulate("A3", new String[]{"A3", "C1", "C2", "C3", "C4", "C5"});
        jo.accumulate("B1", new String[]{"B1", "C1", "C2", "C3", "C4", "C5", "M"});
        jo.accumulate("B2", new String[]{"B2", "C1", "C2", "C3", "C4", "C5", "M"});
        jo.accumulate("C1", new String[]{"C1", "C2", "C3", "C4", "C5"});
        jo.accumulate("C2", new String[]{"C2", "C5"});
        jo.accumulate("C3", new String[]{"C3", "C4", "C5"});
        jo.accumulate("C4", new String[]{"C4", "C5"});
        jo.accumulate("C5", new String[]{"C5"});
        jo.accumulate("D", new String[]{"D", "E", "F"});
        jo.accumulate("E", new String[]{"E", "F"});
        jo.accumulate("F", new String[]{"F"});
        jo.accumulate("M", new String[]{"M"});
        jo.accumulate("N", new String[]{"N"});
        jo.accumulate("P", new String[]{"P"});*/


        jo.accumulate("A1", new String[]{"A1"});
        jo.accumulate("A2", new String[]{"A2"});
        jo.accumulate("A3", new String[]{"A3", "A1"});
        jo.accumulate("B1", new String[]{"B1", "A1", "A2"});
        jo.accumulate("B2", new String[]{"B2", "A1", "A2"});
        jo.accumulate("C1", new String[]{"C1", "A1", "A2", "A3", "B1", "B2"});
        jo.accumulate("C2", new String[]{"C2", "A1", "A2", "A3", "B1", "B2", "C1"});
        jo.accumulate("C3", new String[]{"C3", "A1", "A2", "A3", "B1", "B2", "C1"});
        jo.accumulate("C4", new String[]{"C4", "A1", "A2", "A3", "B1", "B2", "C1", "C3"});
        jo.accumulate("C5", new String[]{"C5","C4", "A1", "A2", "A3", "B1", "B2", "C1", "C2","C3"});
        jo.accumulate("D", new String[]{"D","C1"});
        jo.accumulate("E", new String[]{"E", "D","C1"});
        jo.accumulate("F", new String[]{"D", "E", "F","C1"});
        jo.accumulate("M", new String[]{"M","A1", "A2", "B1", "B2","C1"});
        jo.accumulate("N", new String[]{"N","C1"});
        jo.accumulate("P", new String[]{"P","C1"});
        return jo.get(categoryName);
    }
    private File headImageFile;
    private String headImageFileFileName;
    @Resource
    private MyCenterService myCenterService;
    private String realname;
    private String staffIdentityCard;
    private String photoPath;
    //保存用户身份证和名字以及头像
    public String updateStaffInfo(){
        Staff staff=new Staff();
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        //将图片上传
        String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
        if(headImageFile!=null){
            String photoPath = fileService.savePhoto(path,headImageFileFileName, headImageFile, tc.getStaffid(),"/staff/headimage/"
                    +Utilities.getDateString(new Date(), "yyyy-MM-dd"));
            staff.setHeadimage(photoPath);
        }else{
            staff.setHeadimage(photoPath);
        }
        //保存数据
        staff.setStaffID(staffId);
        staff.setRealname(realname);
        staff.setStaffIdentityCard(staffIdentityCard);
        myCenterService.editInfo(staff,tc.getSccId());
        return "success";
    }
    private String textType;
    private String textInfo;
    private String textCode;
    //根据文本获取省市区code
    public String getAddressByTextInfo(){
        String sql="";
        List<BaseBean> siteList=new ArrayList<>();
        if("s".equals(textType)){
            //省
            sql+="select PROVINCEID from PROVINCE where PROVINCE=?";
            siteList=baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{textInfo});
        }else if("x".equals(textType)){
            //区
            sql+="select CITYID from city where CITY=? and FATHER=?";
            siteList=baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{textInfo,textCode});
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("siteList", siteList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    public StaffAddress getStaffAddress() {
        return staffAddress;
    }

    public void setStaffAddress(StaffAddress staffAddress) {
        this.staffAddress = staffAddress;
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

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Enroll getEnrollForm() {
        return enrollForm;
    }

    public void setEnrollForm(Enroll enrollForm) {
        this.enrollForm = enrollForm;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCcompanyID() {
        return ccompanyID;
    }

    public void setCcompanyID(String ccompanyID) {
        this.ccompanyID = ccompanyID;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getSelectType() {
        return selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public CAccount getAccount() {
        return account;
    }

    public void setAccount(CAccount account) {
        this.account = account;
    }

    public String getCompanyIdentifier() {
        return companyIdentifier;
    }

    public void setCompanyIdentifier(String companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getParName() {
        return parName;
    }

    public void setParName(String parName) {
        this.parName = parName;
    }

    public String getAllPro() {
        return allPro;
    }

    public void setAllPro(String allPro) {
        this.allPro = allPro;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLicenceType() {
        return licenceType;
    }

    public void setLicenceType(String licenceType) {
        this.licenceType = licenceType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public ProductPackaging getProduct() {
        return product;
    }

    public void setProduct(ProductPackaging product) {
        this.product = product;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getEnrollID() {
        return enrollID;
    }

    public void setEnrollID(String enrollID) {
        this.enrollID = enrollID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getPhotoInfoFileName() {
        return photoInfoFileName;
    }

    public void setPhotoInfoFileName(String photoInfoFileName) {
        this.photoInfoFileName = photoInfoFileName;
    }

    public File getPhotoInfo() {
        return photoInfo;
    }

    public void setPhotoInfo(File photoInfo) {
        this.photoInfo = photoInfo;
    }

    public File getHeadImageFile() {
        return headImageFile;
    }

    public void setHeadImageFile(File headImageFile) {
        this.headImageFile = headImageFile;
    }

    public String getHeadImageFileFileName() {
        return headImageFileFileName;
    }

    public void setHeadImageFileFileName(String headImageFileFileName) {
        this.headImageFileFileName = headImageFileFileName;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getStaffIdentityCard() {
        return staffIdentityCard;
    }

    public void setStaffIdentityCard(String staffIdentityCard) {
        this.staffIdentityCard = staffIdentityCard;
    }

    public String getTextType() {
        return textType;
    }

    public void setTextType(String textType) {
        this.textType = textType;
    }

    public String getTextInfo() {
        return textInfo;
    }

    public void setTextInfo(String textInfo) {
        this.textInfo = textInfo;
    }

    public String getTextCode() {
        return textCode;
    }

    public void setTextCode(String textCode) {
        this.textCode = textCode;
    }
}
