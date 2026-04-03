package hy.ea.office.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.junziqian.sdk.bean.req.sign.ext.SignatoryReq;
import com.junziqian.sdk.bean.req.user.OrganizationCreateReq;
import com.stamp.Html2PdfUtil;
import com.stamp.JzqAPI;
import com.stamp.Office2PdfUtil;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.wechat.bo.sft.*;
import com.wechatpay.utils.WeChatUtils;
import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.Enroll;
import hy.ea.bo.SubjectHour;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.PayBackupBill;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.*;
import hy.ea.office.service.ContractService;
import hy.ea.office.service.DocCommonService;
import hy.ea.office.service.DocumentFlowService;
import hy.ea.office.service.ZOfficeService;
import hy.ea.util.MobileMessage;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {

    @Resource
    private BaseBeanDao beandao;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private DocCommonService docCommonService;
    @Resource
    private ZOfficeService zOfficeService;
    @Autowired
    private MobileMessage mobileMessage;//短信接口
    @Resource
    private DocumentFlowService docFlowService;// 工作流相关的业务逻辑

    /**
     * 获取当前驾校全部培训合同
     *
     * @param pageNumber
     * @param pageSize
     * @param parameter
     * @param companyID
     * @return
     */
    public PageForm getALLFileList(int pageNumber, int pageSize, String parameter, String companyID) {

        List<Object> params = new ArrayList<Object>();

        String sql = "select t.docid,t.title,t.createtime,m.staffname,m.staffidentitycard,t.status,m.reference from dt_oa_document t,DT_OA_DOC_TemplateParams  m where t.docid = m.docid and t.companyId = ? and t.scene = ? and t.delstatus is null";

        params.add(companyID);
        params.add("00");
        if (parameter != null && !parameter.equals("")) {
            sql += " and (m.staffname = ? or m.staffidentitycard = ? or m.reference = ?)";
            params.add(parameter);
            params.add(parameter);
            params.add(parameter);
        }

        sql += " order by t.createtime desc";
        PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql,
                "select count(*) from (" + sql + ")", params.toArray());

        return pageForm;

    }

    /**
     * 获取我的文件
     *
     * @param pageNumber
     * @param pageSize
     * @param parameter
     * @param staffID
     * @return
     */
    public PageForm getMyFileList(int pageNumber, int pageSize, String parameter, String staffID, String module, String companyID, String state) {

        if (pageNumber == 0) {
            pageNumber = 1;
        }
        PageForm pageForm = null;
        List<Object> params = new ArrayList<Object>();
        String hql = "";
        //    if (companyID != null && !companyID.equals("")) {

        if (state.equals("draftlist")) {

            hql = "from Document t where t.drafterID = ?  and t.status = ? and  t.subscriberID2 is null and   t.title is not null and t.docId not in(select d.docId from DocDelRecord d)";

            params.add(staffID);
            params.add("I");


            if (companyID != null && !companyID.equals("")) {
                hql += " and t.companyID = ?";
                params.add(companyID);
            } else {
                hql += " and t.companyID is null";

            }
            if (module != null && !module.equals("")) {
                hql += " and t.module = ?";

                params.add(module);
            }


            if (parameter != null && !parameter.equals("")) {
                hql += " and t.title like ?";
                params.add("%" + parameter + "%");
            }

            hql += " order by t.draftTime desc null last";

        } else if (state.equals("receivelist")) {
            if (companyID != null && !companyID.equals("")) {
                hql = "from Document d where d.status!=? and((d.subscriberID2 = ? and d.companyIDofSubscriber2 = ?) or (d.status = ? and d.drafterID = ? and d.companyID = ?))and d.module = ?  and d.docId not in(select c.docId from DocDelRecord c)";
                params.add("y");
                params.add(staffID);
                params.add(companyID);
                params.add("R");
                params.add(staffID);
                params.add(companyID);
                params.add(module);
            } else {
                hql = "from Document d where d.status!=? and ((d.subscriberID2 = ? and d.companyIDofSubscriber2 is null) or (d.status = ? and d.drafterID = ? and d.companyID is null))and d.docId not in(select c.docId from DocDelRecord c)";
                params.add("y");
                params.add(staffID);
                params.add("R");
                params.add(staffID);

            }


            if (parameter != null && !parameter.equals("")) {
                hql += " and d.title like ?";
                params.add("%" + parameter + "%");
            }

            hql += " order by d.passTime desc";

        } else if (state.equals("sendedlist")) {

            if (companyID != null && !companyID.equals("")) {
                hql = "from Document d where ((d.drafterID=? and d.status!='I' and d.status!='D' and d.companyID = ?) or (d.docId in (select p.docId from DocumentPass p where p.subscriber2 = ? and p.companyIDOfsub2 = ?))) and d.docId not in(select docId from DocDelRecord) and d.module = ?";
                params.add(staffID);
                params.add(companyID);

                params.add(staffID);
                params.add(companyID);

                params.add(module);
            } else {
                hql = "from Document d where ((d.drafterID=? and d.status!='I' and d.status!='D' and d.companyID is null) or (d.docId in (select p.docId from DocumentPass p where p.subscriber2 = ? and p.companyIDOfsub2 is null))) and d.docId not in(select docId from DocDelRecord)";
                params.add(staffID);
                params.add(staffID);


            }
            if (parameter != null && !parameter.equals("")) {
                hql += " and d.title like ?";
                params.add("%" + parameter + "%");
            }

            hql += " order by updateTime desc";
        } else if (state.equals("archivelist")) {

            hql = "from Document d where d.groupCompanySn=? and d.status= ? and d.module = ? and d.docId not in(select docId from DocDelRecord)";
            Company company = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{companyID});


            params.add(company.getGroupCompanySn());
            params.add("G");


            params.add(module);
            if (parameter != null && !parameter.equals("")) {
                hql += " and d.title like ?";
                params.add("%" + parameter + "%");
            }

            hql += " order by d.guidangTime desc";
        } else if (state.equals("sharelist")) {

            hql = "from Document d where d.module = ? and d.docId not in(select docId from DocDelRecord where stage = ? and operator = ?) and docId in(select s.docId from DocumentShare s where (s.companyId = ? and s.shareType = ?) or (s.groupCompanySn = ? and s.shareType = ?))";
            Company company = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{companyID});


            params.add(module);
            params.add("share");
            params.add(staffID);
            params.add(companyID);
            params.add("current");

            params.add(company.getGroupCompanySn());
            params.add("group");


            if (parameter != null && !parameter.equals("")) {
                hql += " and d.title like ?";
                params.add("%" + parameter + "%");
            }


        }

        //   }
//        else {
//
//            TEshopCusCom tc = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccid});
//            //没进入公司，在外部显示当前登录人所有公司的他的草稿
//
//            List<Object> lists = getStaffList(sccid);//查询当前登录人所有公司对应账号的staffid
//            if (!lists.contains(tc.getStaffid())) {
//                lists.add(tc.getStaffid());
//
//            }
//
//            hql = "from Document t where  t.title is not null and t.delstatus is null and t.drafterID in(";
//
//            for (int i = 0; i < lists.size(); i++) {
//                if (i != lists.size() - 1) {
//                    hql += "?,";
//                } else {
//                    hql += "?)";
//                }
//                params.add(lists.get(i).toString());
//
//            }
//
//            if (parameter != null && !parameter.equals("")) {
//                hql += " and title like ?";
//                params.add("%" + parameter + "%");
//            }
//
//
//            hql += " order by t.draftTime desc null last";
//        }


        pageForm = baseBeanService.getPageForm(pageNumber, pageSize, hql, params.toArray());

        return pageForm;
    }


    /**
     * 查询sccid所有入职staffID
     *
     * @param sccid
     * @return
     */
    public List<Object> getStaffList(String sccid) {
        TEshopCusCom tc = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccid});

        StringBuffer str = new StringBuffer();
        List<String> params = new ArrayList<String>();
        str.append("select s.staffid");
        str.append(" from dt_hr_Staff s left join t_Eshop_Cuscom t  on s.sccid = t.sccid  and length(t.account)=11  left join dtCOS cos on cos.staffid = s.staffid");
        str.append(" and cos.Cosstatus ='50' left join dtCompany y on cos.companyid = y.companyid  left join Dtcorganization n on n.organizationid = cos.organizationid");


        str.append(" where s.reference = ? or t.account = ?");
        params.add(tc.getAccount());
        params.add(tc.getAccount());


//		str.append(" select cus.staffid from T_ESHOP_CUSCOM cus, dtCompany com, dt_ccom_com d, dtcontactcompany t where cus.companyid = com.companyid ");
//		str.append(" and d.ccompany_id = t.ccompanyid and com.companyid = d.compnay_id and cus.account in (select j.account  from T_ESHOP_CUSCOM j  where j.sccid = ?) union  select staff.staffid from  dt_ccom_com d, ");
//		str.append(" dtCompany com,dtcontactcompany t,dtcos cos,T_ESHOP_CUSCOM cus, dt_hr_Staff staff, T_ESHOP_CUSCOM   ccus where staff.staffid = cos.staffid and staff.reference = cus.account and cos.companyid=com.companyid and cos.status='01' and cos.cosStatus='50' and com.companyid=d.compnay_id ");
//		str.append("  and d.ccompany_id=t.ccompanyid  and com.companyid = ccus.companyid and cus.sccid = ? ");
//
//		params.add(sccid);
//		params.add(sccid);
        List<java.lang.Object> list = beandao.getListObjectBySqlAndParams(str.toString(), params.toArray());
        return list;
    }

    /**
     * 待审批，待盖章，待发布，待阅读
     *
     * @param pageNumber
     * @param pageSize
     * @param parameter
     * @param staffID
     * @return
     */
    public PageForm getDealFileList(int pageNumber, int pageSize, String parameter, String staffID, String companyID, String module, String sccid) {
        List<Object> params = new ArrayList<Object>();

        StringBuilder sb = new StringBuilder();
        sb.append("select zz.docId,zz.title,to_char(zz.a,'yyyy-mm-dd hh24:mi:ss'),zz.status from(");
        sb.append("select e1.docId,e1.title,e1.updatetime a,e1.status from  VIEW_UNEXAMINEDOC e1 where e1.activityname_=?");
        params.add("Examine and Approve");

        if (companyID != null && !companyID.equals("")) {

            sb.append(" and  e1.assignee_= ?");
            params.add(staffID);
        } else {
            TEshopCusCom tc = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccid});
            //没进入公司，在外部显示当前登录人所有公司的他的草稿

            List<Object> lists = getStaffList(sccid);//查询当前登录人所有公司对应账号的staffid
            if (!lists.contains(tc.getStaffid())) {
                lists.add(tc.getStaffid());

            }

            sb.append(" and  e1.assignee_ in(");

            for (int i = 0; i < lists.size(); i++) {
                if (i != lists.size() - 1) {
                    sb.append("?,");
                } else {
                    sb.append("?)");
                }
                params.add(lists.get(i).toString());

            }
        }

        if (companyID != null && !companyID.equals("")) {
            sb.append(" and e1.companyidofsubscriber = ?");
            params.add(companyID);
        }
        if (module != null && !module.equals("")) {
            sb.append(" and e1.module = ?");

            params.add(module);
        }
        sb.append(" union all ");
        sb.append("select e2.docId,e2.title,e2.subscribetime a,e2.status from  VIEW_UNSEALDOC e2 where e2.activityname_=?");
        params.add("Seal");
        if (companyID != null && !companyID.equals("")) {

            sb.append(" and  e2.assignee_= ?");
            params.add(staffID);
        } else {
            TEshopCusCom tc = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccid});
            //没进入公司，在外部显示当前登录人所有公司的他的草稿

            List<Object> lists = getStaffList(sccid);//查询当前登录人所有公司对应账号的staffid
            if (!lists.contains(tc.getStaffid())) {
                lists.add(tc.getStaffid());

            }

            sb.append(" and  e2.assignee_ in(");

            for (int i = 0; i < lists.size(); i++) {
                if (i != lists.size() - 1) {
                    sb.append("?,");
                } else {
                    sb.append("?)");
                }
                params.add(lists.get(i).toString());

            }
        }
        if (companyID != null && !companyID.equals("")) {
            sb.append(" and e2.companyidofsealer = ?");
            params.add(companyID);
        }
        if (module != null && !module.equals("")) {
            sb.append(" and e2.module = ?");

            params.add(module);
        }
        sb.append(" union all ");
        sb.append("select e3.docId,e3.title,e3.sealTime a,e3.status from  VIEW_UNPUBLISHDOC e3 where e3.activityname_=?");
        params.add("Publish");
        if (companyID != null && !companyID.equals("")) {

            sb.append(" and  e3.assignee_= ?");
            params.add(staffID);
        } else {
            TEshopCusCom tc = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccid});
            //没进入公司，在外部显示当前登录人所有公司的他的草稿

            List<Object> lists = getStaffList(sccid);//查询当前登录人所有公司对应账号的staffid
            if (!lists.contains(tc.getStaffid())) {
                lists.add(tc.getStaffid());

            }

            sb.append(" and  e3.assignee_ in(");

            for (int i = 0; i < lists.size(); i++) {
                if (i != lists.size() - 1) {
                    sb.append("?,");
                } else {
                    sb.append("?)");
                }
                params.add(lists.get(i).toString());

            }
        }
        if (companyID != null && !companyID.equals("")) {
            sb.append(" and e3.companyidofpublisher = ?");
            params.add(companyID);
        }
        if (module != null && !module.equals("")) {
            sb.append(" and e3.module = ?");

            params.add(module);
        }
        sb.append(" union all ");
        sb.append("select e4.docId,e4.title,to_date(e4.recivetime,'yyyy-mm-dd hh24:mi:ss') a,e4.status  from  VIEW_UNREADDOC e4 where  e4.readstate = '1'");
        if (companyID != null && !companyID.equals("")) {

            sb.append(" and  e4.readerid=?");
            params.add(staffID);
        } else {
            TEshopCusCom tc = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccid});
            //没进入公司，在外部显示当前登录人所有公司的他的草稿

            List<Object> lists = getStaffList(sccid);//查询当前登录人所有公司对应账号的staffid
            if (!lists.contains(tc.getStaffid())) {
                lists.add(tc.getStaffid());

            }

            sb.append(" and  e4.readerid in(");

            for (int i = 0; i < lists.size(); i++) {
                if (i != lists.size() - 1) {
                    sb.append("?,");
                } else {
                    sb.append("?)");
                }
                params.add(lists.get(i).toString());

            }
        }
        if (companyID != null && !companyID.equals("")) {
            sb.append(" and e4.readcompanyid = ?");
            params.add(companyID);
        }
        if (module != null && !module.equals("")) {
            sb.append(" and e4.module = ?");

            params.add(module);
        }

        sb.append(" union all ");
        sb.append("select e5.docid,e5.title,e5.passtime a,e5.status  from dt_oa_document e5 where");
        if (companyID != null && !companyID.equals("")) {

            sb.append(" e5.subscriberid2= ?");
            params.add(staffID);
        } else {
            TEshopCusCom tc = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccid});
            //没进入公司，在外部显示当前登录人所有公司的他的草稿

            List<Object> lists = getStaffList(sccid);//查询当前登录人所有公司对应账号的staffid
            if (!lists.contains(tc.getStaffid())) {
                lists.add(tc.getStaffid());

            }

            sb.append(" e5.subscriberid2 in(");

            for (int i = 0; i < lists.size(); i++) {
                if (i != lists.size() - 1) {
                    sb.append("?,");
                } else {
                    sb.append("?)");
                }
                params.add(lists.get(i).toString());

            }
        }
        if (companyID != null && !companyID.equals("")) {
            sb.append(" and e5.companyIDofSubscriber2 = ?");
            params.add(companyID);
        }
        if (module != null && !module.equals("")) {
            sb.append(" and e5.module = ?");

            params.add(module);
        }

        sb.append(") zz");


        if (parameter != null && !parameter.equals("")) {
            sb.append(" where zz.title like ?");
            params.add("%" + parameter + "%");
        }

        sb.append(" order by a desc");
        PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", params.toArray());
        return pageForm;
    }

    /**
     * 待审批，待盖章，待发布，待阅读
     *
     * @param pageNumber
     * @param pageSize
     * @param parameter
     * @param staffID
     * @return
     */
    public PageForm getDealFileByState(int pageNumber, int pageSize, String parameter, String staffID, String companyID, String module, String state) {
        List<Object> params = new ArrayList<Object>();

        StringBuilder sb = new StringBuilder();
        if ("auditlist".equals(state)) {
            sb.append("select e1.docId,e1.title,to_char(e1.updatetime,'yyyy-mm-dd hh24:mi:ss') a,e1.status,e1.draftername,e1.deptnameofdraft,e1.companyname,e1.docNum,e1.theme,e1.docType,e1.emergencyType from  VIEW_UNEXAMINEDOC e1 where e1.status!=? and e1.activityname_=?");
            params.add("y");
            params.add("Examine and Approve");


            sb.append(" and  e1.assignee_= ?");
            params.add(staffID);
            if (companyID != null && !companyID.equals("")) {

                sb.append(" and e1.companyidofsubscriber = ?");
                params.add(companyID);
            } else {

                sb.append(" and e1.companyidofsubscriber is null");

            }

            if (module != null && !module.equals("")) {
                sb.append(" and e1.module = ?");

                params.add(module);
            }

            if (parameter != null && !parameter.equals("")) {
                sb.append(" and  e1.title like ?");
                params.add("%" + parameter + "%");
            }
        } else if ("auditedlist".equals(state)) {
            sb.append("select e1.docId,e1.title,to_char(e1.examinetime,'yyyy-mm-dd hh24:mi:ss') a,e1.status,e1.draftername,e1.deptnameofdraft,e1.companyname,e1.docNum,e1.theme,e1.docType,e1.emergencyType from  VIEW_EXAMINEDOC e1 where ");

            sb.append(" e1.assignee_= ?");
            params.add(staffID);
            if (companyID != null && !companyID.equals("")) {
                sb.append(" and e1.companyidofsubscriber = ?");
                params.add(companyID);
            } else {

                sb.append(" and e1.companyidofsubscriber is null");
            }

            if (module != null && !module.equals("")) {
                sb.append(" and e1.module = ?");

                params.add(module);
            }

            if (parameter != null && !parameter.equals("")) {
                sb.append(" and e1.title like ?");
                params.add("%" + parameter + "%");
            }

            sb.append(" union ");

            sb.append("select e1.docId,e1.title,to_char(e1.updatetime,'yyyy-mm-dd hh24:mi:ss') a,e1.status,e1.draftername,e1.deptnameofdraft,e1.companyname,e1.docNum,e1.theme,e1.docType,e1.emergencyType from  VIEW_UNEXAMINEDOC e1 where e1.status=? and e1.activityname_=?");
            params.add("y");
            params.add("Examine and Approve");


            sb.append(" and  e1.assignee_= ?");
            params.add(staffID);
            if (companyID != null && !companyID.equals("")) {

                sb.append(" and e1.companyidofsubscriber = ?");
                params.add(companyID);
            } else {

                sb.append(" and e1.companyidofsubscriber is null");

            }

            if (module != null && !module.equals("")) {
                sb.append(" and e1.module = ?");

                params.add(module);
            }

            if (parameter != null && !parameter.equals("")) {
                sb.append(" and  e1.title like ?");
                params.add("%" + parameter + "%");
            }


        } else if ("seallist".equals(state)) {

            sb.append("select * from(");
            sb.append("select e2.docId,e2.title,to_char(e2.subscribetime,'yyyy-mm-dd hh24:mi:ss') a ,e2.status,e2.draftername,e2.deptnameofdraft,e2.companyname,e2.docNum,e2.theme,e2.docType,e2.emergencyType,e2.formalNum,e2.scene,e2.companyId from  VIEW_UNSEALDOC e2 where e2.status!=? and e2.activityname_=?");

            params.add("y");
            params.add("Seal");


            sb.append(" and  e2.assignee_= ?");
            params.add(staffID);

            if (companyID != null && !companyID.equals("")) {
                sb.append(" and e2.companyidofsealer = ?");
                params.add(companyID);
            } else {
                sb.append(" and e2.companyidofsealer is null");

            }
            if (module != null && !module.equals("")) {
                sb.append(" and e2.module = ?");

                params.add(module);

            }


            if (parameter != null && !parameter.equals("")) {
                sb.append(" and e2.title like ?");
                params.add("%" + parameter + "%");
            }


            sb.append(" union ");

            sb.append("select e.docId,e.title,to_char(e.updateTime,'yyyy-mm-dd hh24:mi:ss') a ,e.status,s.staffname,'',coms.companyname,e.docNum,e.theme,e.docType,e.emergencyType,e.formalNum,e.scene,e.companyId from dt_oa_document e inner join dt_hr_staff s on s.staffid = e.drafterid  left join dtCompany com on com.companyid = e.companyid left join dtCompany coms on e.companyid = coms.companyid where e.scene = '00' and e.drafterid=? and (e.status = 'A' or e.status = 'K') ");

            params.add(staffID);

            if (parameter != null && !parameter.equals("")) {
                sb.append(" and e.title like ?");
                params.add("%" + parameter + "%");
            }


            sb.append(")");
        } else if ("sealedlist".equals(state)) {
            sb.append("select * from(");

            sb.append("select e2.docId,e2.title,to_char(e2.sealtime,'yyyy-mm-dd hh24:mi:ss') a,e2.status,e2.draftername,e2.deptnameofdraft,e2.companyname,e2.docNum,e2.theme,e2.docType,e2.emergencyType,e2.formalNum,e2.scene from  VIEW_SEALDOC e2 where ");


            sb.append("  e2.assignee_= ?");
            params.add(staffID);

            if (companyID != null && !companyID.equals("")) {
                sb.append(" and e2.companyidofsealer = ?");
                params.add(companyID);
            } else {
                sb.append(" and e2.companyidofsealer is null");

            }
            if (module != null && !module.equals("")) {
                sb.append(" and e2.module = ?");

                params.add(module);

            }
            if (parameter != null && !parameter.equals("")) {
                sb.append(" and e2.title like ?");
                params.add("%" + parameter + "%");
            }

            sb.append(" union ");

            sb.append("select e.docId,e.title,to_char(e.updateTime,'yyyy-mm-dd hh24:mi:ss') a ,e.status,s.staffname,'',coms.companyname,e.docNum,e.theme,e.docType,e.emergencyType,e.formalNum,e.scene from dt_oa_document e inner join dt_hr_staff s on s.staffid = e.drafterid  left join dtCompany com on com.companyid = e.companyid left join dtCompany coms on e.companyid = coms.companyid where e.scene = '00' and e.drafterid=? and e.status = 'F'");

            params.add(staffID);

            if (parameter != null && !parameter.equals("")) {
                sb.append(" and e.title like ?");
                params.add("%" + parameter + "%");
            }
            sb.append(" union ");

            sb.append("select e2.docId,e2.title,to_char(e2.subscribetime,'yyyy-mm-dd hh24:mi:ss') a ,e2.status,e2.draftername,e2.deptnameofdraft,e2.companyname,e2.docNum,e2.theme,e2.docType,e2.emergencyType,e2.formalNum,e2.scene from  VIEW_UNSEALDOC e2 where e2.status=? and e2.activityname_=?");

            params.add("y");
            params.add("Seal");


            sb.append(" and  e2.assignee_= ?");
            params.add(staffID);

            if (companyID != null && !companyID.equals("")) {
                sb.append(" and e2.companyidofsealer = ?");
                params.add(companyID);
            } else {
                sb.append(" and e2.companyidofsealer is null");

            }
            if (module != null && !module.equals("")) {
                sb.append(" and e2.module = ?");

                params.add(module);

            }


            if (parameter != null && !parameter.equals("")) {
                sb.append(" and e2.title like ?");
                params.add("%" + parameter + "%");
            }


            sb.append(")");
        } else if ("publishlist".equals(state)) {
            sb.append("select e3.docId,e3.title,to_char(e3.sealTime,'yyyy-mm-dd hh24:mi:ss')  a,e3.status,e3.draftername,e3.deptnameofdraft,e3.companyname,e3.docNum,e3.theme,e3.docType,e3.emergencyType,e3.formalNum from  VIEW_UNPUBLISHDOC e3 where e3.status!=? and e3.activityname_=?");
            params.add("y");
            params.add("Publish");


            sb.append(" and  e3.assignee_= ?");
            params.add(staffID);

            if (companyID != null && !companyID.equals("")) {
                sb.append(" and e3.companyidofpublisher = ?");
                params.add(companyID);
            } else {
                sb.append(" and e3.companyidofpublisher is null");

            }
            if (module != null && !module.equals("")) {
                sb.append(" and e3.module = ?");

                params.add(module);
            }
            if (parameter != null && !parameter.equals("")) {
                sb.append(" and e3.title like ?");
                params.add("%" + parameter + "%");
            }
        } else if ("publishedlist".equals(state)) {
            sb.append("select e3.docId,e3.title,to_char(e3.publishtime,'yyyy-mm-dd hh24:mi:ss') a,e3.status,e3.draftername,e3.deptnameofdraft,e3.companyname,e3.docNum,e3.theme,e3.docType,e3.emergencyType,e3.formalNum  from  VIEW_PUBLISHDOC e3 where ");


            sb.append("  e3.assignee_= ?");
            params.add(staffID);

            if (companyID != null && !companyID.equals("")) {
                sb.append(" and e3.companyidofpublisher = ?");
                params.add(companyID);
            } else {
                sb.append(" and e3.companyidofpublisher is null");


            }
            if (module != null && !module.equals("")) {
                sb.append(" and e3.module = ?");

                params.add(module);
            }
            if (parameter != null && !parameter.equals("")) {
                sb.append(" and e3.title like ?");
                params.add("%" + parameter + "%");
            }

            sb.append(" union ");
            sb.append("select e3.docId,e3.title,to_char(e3.sealTime,'yyyy-mm-dd hh24:mi:ss')  a,e3.status,e3.draftername,e3.deptnameofdraft,e3.companyname,e3.docNum,e3.theme,e3.docType,e3.emergencyType,e3.formalNum from  VIEW_UNPUBLISHDOC e3 where e3.status=? and e3.activityname_=?");
            params.add("y");
            params.add("Publish");


            sb.append(" and  e3.assignee_= ?");
            params.add(staffID);

            if (companyID != null && !companyID.equals("")) {
                sb.append(" and e3.companyidofpublisher = ?");
                params.add(companyID);
            } else {
                sb.append(" and e3.companyidofpublisher is null");

            }
            if (module != null && !module.equals("")) {
                sb.append(" and e3.module = ?");

                params.add(module);
            }
            if (parameter != null && !parameter.equals("")) {
                sb.append(" and e3.title like ?");
                params.add("%" + parameter + "%");
            }
        } else if ("readlist".equals(state) || "readedlist".equals(state)) {
            sb.append("select e4.docId,e4.title,to_char(to_date(e4.recivetime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss') a,e4.status,e4.draftername,e4.deptnameofdraft,e4.companyname,e4.docNum,e4.theme,e4.docType,e4.emergencyType,e4.formalNum,e4.module from  VIEW_UNREADDOC e4 where  e4.readstate = ?");

            if ("readlist".equals(state)) {

                params.add("1");

            } else {
                params.add("0");
            }
            sb.append(" and  e4.readerid=?");
            params.add(staffID);

            if (companyID != null && !companyID.equals("")) {
                sb.append(" and e4.readcompanyid = ?");
                params.add(companyID);
            } else {
                sb.append(" and e4.readcompanyid is null");
            }
            if (module != null && !module.equals("")) {
                sb.append(" and e4.module = ?");

                params.add(module);
            }

            if (parameter != null && !parameter.equals("")) {
                sb.append(" and e4.title like ?");
                params.add("%" + parameter + "%");
            }


        } else if ("recylelist".equals(state)) {
            sb.append("select e5.docId,e5.title,to_char(r.operateTime,'yyyy-mm-dd hh24:mi:ss') a,r.delId,r.key,r.stage,r.orgmark,f.staffname,org.organizationName,cc.companyname,e5.docNum,e5.theme,e5.docType,e5.emergencyType from DT_OA_DOCUMENT e5 left join DT_OA_DOC_DELRECORD r on e5.docId = r.docId left join dt_hr_staff f on e5.drafterID = f.staffId left join dtCompany cc on e5.companyID = cc.companyID left join dtCOrganization org on org.organizationID = e5.deptIDofDrafter where  r.operator = ?");

            params.add(staffID);

            if (module != null && !module.equals("")) {
                sb.append(" and e5.module = ?");

                params.add(module);
            }

            if (parameter != null && !parameter.equals("")) {
                sb.append(" and e5.title like ?");
                params.add("%" + parameter + "%");
            }


        }


        sb.append(" order by a desc");


        PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", params.toArray());
        return pageForm;
    }

    /**
     * 文件预览
     *
     * @return
     */
    public String getFilePre(String scene, String companyID, String docID) {

        String hql = "from JzqTemplate where scene = ? and companyID = ?";
        JzqTemplate bc = (JzqTemplate) beandao.getBeanByHqlAndParams(hql, new Object[]{scene, companyID});
        String templateID = "company201009046vxdyzy4wg0000000130";
        if (bc != null) {
            templateID = bc.getTemplateID();
        }
        String html = "";
        if ("00".equals(scene)) {
            //驾校学员协议
            TemplateParams templateParams = (TemplateParams) beandao.getBeanByHqlAndParams("from TemplateParams where docID = ?", new Object[]{docID});

            JSONObject tpjson = new JSONObject();

            tpjson.put("companyName", templateParams.getCompanyName());
            tpjson.put("companyAddr", templateParams.getCompanyAddr());
            tpjson.put("companyTel", templateParams.getCompanyTel());
            tpjson.put("staffName", templateParams.getStaffName());
            tpjson.put("reference", templateParams.getReference());
            tpjson.put("staffIdentityCard", templateParams.getStaffIdentityCard());
            tpjson.put("subjectType", templateParams.getSubjectType());
            tpjson.put("productName", templateParams.getProductName());
            tpjson.put("money", templateParams.getMoney());
            tpjson.put("dmoney", templateParams.getDmoney());
            tpjson.put("managementFees", templateParams.getManagementFees());
            tpjson.put("dmanagementFees", templateParams.getDmanagementFees());
            tpjson.put("operatingFee", templateParams.getOperatingFee());
            tpjson.put("doperatingFee", templateParams.getDoperatingFee());

            tpjson.put("operatingStaffName", templateParams.getOperatingStaffName());
            tpjson.put("lldate", templateParams.getLldate());
            tpjson.put("scdate", templateParams.getScdate());
            tpjson.put("cnxsite", templateParams.getCnxsite());


            tpjson.put("referrerAddress", templateParams.getReferrerAddress());
            tpjson.put("year", templateParams.getYear());
            tpjson.put("month", templateParams.getMonth());
            tpjson.put("day", templateParams.getDays());
            tpjson.put("validateYear", templateParams.getValidateYear());


            html = JzqAPI.tmplPre(tpjson.toJSONString(), templateID);
        }

        return html;

    }

    /**
     * 获取价格
     *
     * @param companyID
     * @param sence
     * @return
     */
    public Object getProduct(String companyID, String sence) {
        Object object = null;
        if ("00".equals(sence)) {
            //学员协议
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = e.format(new Date());
            StringBuilder sql = new StringBuilder();

            sql.append("select p.ppId,case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,t.companyName,p.goodsName");
            sql.append(" from DT_PRO_SETUP ps inner join dt_ProductPackaging p on ps.ppid = p.ppid ");
            sql.append("left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state='00' ");
            sql.append("left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01' and (tpa.state!='02' and tpa.state!='04') ");

            sql.append("and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

            sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state='00'");
            sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00' and (pa.state!='02' and pa.state!='04')");


            sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

            sql.append("left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
            sql.append("left join dtCompany t on t.companyid = cc.compnay_id ");
            sql.append("left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");
            sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
            sql.append("where p.ppid = (select pd.ppID from dt_ProductPackaging pd where pd.type = ? and pd.companyID = ? and rownum = 1)");

            Object[] objects = new Object[]{currentTime, currentTime, currentTime, currentTime, "学员协议", companyID};

            List<Object> objlist = beandao.getListObjectBySqlAndParams(sql.toString(), objects);
            if (objlist.size() > 0) {
                object = objlist.get(0);
            }
        }
        return object;

    }

    /**
     * 更新协议状态
     *
     * @param docId
     * @return
     */
    public void updateDocState(String docId) {
        String hql = "update Document set status = ? where docId = ?";
        List<Object[]> paramlist = new ArrayList<Object[]>();
        paramlist.add(new Object[]{"A", docId});
        baseBeanService.executeHqlsByParamsList(null, new String[]{hql}, paramlist);


    }

    /**
     * 创建一个Document学员协议，并记录学员的信息
     */
    public void docTempleateParams(CashierBills cashierBills, String staffID, String journalNum, String ppId, String money) {
        try {
            List<BaseBean> baseBeans = new ArrayList<BaseBean>();
            Staff staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffID});
            Document document = new Document();
            document.setDocId(serverService.getServerID("docId"));
            document.setCompanyID(cashierBills.getCompanyID());
            document.setDrafterID(staffID);
            document.setModule("contract");
            document.setCreateTime(new Date());
            document.setUpdateTime(new Date());
            document.setDraftTime(new Date());
            document.setTheme("学员协议");
            document.setDocName(cashierBills.getCtUserName() + "学员协议");
            document.setTitle(cashierBills.getCtUserName() + "学员协议");
            document.setDrafterName(staff.getStaffName());
            document.setJournalNum(journalNum);
            document.setScene("00");
            document.setStatus("K");


            String hql = "from ContactCompany c where c.ccompanyID = (select cc.ccompanyId from CcomCom cc where comanyId = ?)";

            ContactCompany contactCompany = (ContactCompany) beandao.getBeanByHqlAndParams(hql, new Object[]{cashierBills.getCompanyID()});
            TemplateParams templateParams = new TemplateParams();


            String sqls = "select b.merchant_name from dtContactCompany c left join DT_ccom_com m on c.ccompanyID = m.ccompany_Id  left join dt_sft_ApplyParam p on c.applyid = p.applyid left join dt_sft_BusinessLicenseInfo b on p.bliKey = b.bliKey where m.compnay_id = ?";

            Object objr = beandao.getObjectBySqlAndParams(sqls, new Object[]{cashierBills.getCompanyID()});
            if (objr == null) {
                Company y = (Company) beandao.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{cashierBills.getCompanyID()});
                templateParams.setCompanyName(y.getCompanyName());
            } else {
                templateParams.setCompanyName(objr.toString());
            }

            templateParams.setCompanyAddr(contactCompany.getCompanyAddr());

            templateParams.setCompanyTel(contactCompany.getCompanyTel());

            Enroll enroll = null;
            try {

                List<BaseBean> enrolllist = beandao.getListBeanByHqlAndParams("from Enroll where staffId = ? and idCard = ? order by enrollDate desc", new Object[]{staffID, cashierBills.getStaffIdentityCard()});
                if (enrolllist != null && enrolllist.size() > 0) {


                    enroll = (Enroll) enrolllist.get(0);
                    if (enroll != null) {
                        //场地
                        templateParams.setCnxsite(enroll.getSiteName());


                        //学时
                        SubjectHour subjectHour = (SubjectHour) baseBeanService.getBeanByHqlAndParams("from SubjectHour where staffId = ? and licenseType = ?", new Object[]{enroll.getStaffId(), enroll.getLicenceType()});

                        if (subjectHour != null) {

                            int p1 = subjectHour.getP1Time();
                            int p2 = subjectHour.getP2Time();
                            int p3 = subjectHour.getP3Time();
                            int p4 = subjectHour.getP4Time();

                            templateParams.setLldate((p1 + p4) / 45 + "");
                            templateParams.setScdate((p2 + p3) / 45 + "");
                        }


                        //金额

                        if (enroll.getOperatingStaffName() != null && !enroll.getOperatingStaffName().equals("")) {
                            templateParams.setOperatingStaffName(enroll.getOperatingStaffName());
                        }

                        if (enroll.getManagementFees() != null && !enroll.getManagementFees().equals("")) {
                            templateParams.setManagementFees(enroll.getManagementFees());
                            templateParams.setDmanagementFees(Utilities.digitUppercase(new Double(templateParams.getManagementFees())));

                        }

                        if (enroll.getOperatingFee() != null && !enroll.getOperatingFee().equals("") && !enroll.getOperatingFee().equals("0")) {
                            templateParams.setOperatingFee(enroll.getOperatingFee());
                            templateParams.setDoperatingFee(Utilities.digitUppercase(new Double(templateParams.getOperatingFee())));

                            //如果有实操费用合计金额为管理费+实操费
                            templateParams.setMoney(new Double(enroll.getManagementFees()) + new Double(enroll.getOperatingFee()) + "");
                            templateParams.setDmoney(Utilities.digitUppercase(new Double(templateParams.getMoney())));

                        } else {
                            //如果没有实操费 直接赋值管理费金额
                            templateParams.setMoney(enroll.getManagementFees());
                            templateParams.setDmoney(Utilities.digitUppercase(new Double(enroll.getManagementFees())));

                        }
                        templateParams.setCashierBillsID(enroll.getCashierBillsID());

                    }
                } else {

                    templateParams.setMoney(money);
                    templateParams.setDmoney(Utilities.digitUppercase(new Double(money)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

//            templateParams.setMoney(money);
//            templateParams.setDmoney(Utilities.digitUppercase(new Double(money)));


            templateParams.setDocID(document.getDocId());
            templateParams.setTpId(serverService.getServerID("tpid"));

            String sql = "select p.goodsName,nvl(c.categoryName,'') from dt_ProductPackaging  p left join  dt_SchProCategory c on p.categoryId = c.categoryId where p.ppId = ?";

            Object obj = beandao.getObjectBySqlAndParams(sql, new Object[]{ppId});
            Object[] objs = (Object[]) obj;
            templateParams.setSubjectType(objs[1] == null ? "" : objs[1].toString());
            templateParams.setProductName(objs[0] == null ? "" : objs[0].toString());
            templateParams.setReference(cashierBills.getReference());
            templateParams.setStaffIdentityCard(cashierBills.getStaffIdentityCard());
            templateParams.setReferrerAddress(cashierBills.getReferrerAddress());
            templateParams.setStaffName(cashierBills.getCtUserName());


            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);

            int month = calendar.get(Calendar.MONTH) + 1;

            int day = calendar.get(Calendar.DATE);

            templateParams.setYear(year + "");
            templateParams.setMonth(month + "");
            templateParams.setDays(day + "");
            templateParams.setValidateYear(String.valueOf(year + 3));


            baseBeans.add(templateParams);


            JzqTemplate jzqTemplate = (JzqTemplate) beandao.getBeanByHqlAndParams("from JzqTemplate where companyID = ? and scene = ?", new Object[]{document.getCompanyID(), "00"});

            if (jzqTemplate == null) {
                List<BaseBean> listtemp = beandao.getListBeanByHqlAndParams("from JzqTemplate where  scene = ? and templateID = ? ", new Object[]{"00", "company201009046vxdyzy4wg0000000130"});
                if (listtemp.size() > 0) {
                    jzqTemplate = (JzqTemplate) listtemp.get(0);
                }
            }

            String contractName = jzqTemplate.getTemplateName();//合同名称
            String templateNo = jzqTemplate.getTemplateID();//君子签合同模板

            String sql1 = "select b.business_license_number,r.emailOrMobile from dtContactCompany c left join DT_ccom_com m on c.ccompanyID = m.ccompany_Id  left join dt_sft_ApplyParam p on c.applyid = p.applyid left join dt_sft_BusinessLicenseInfo b on p.bliKey = b.bliKey left join dt_sft_ApplyResult r on r.out_request_no = p.out_request_no where m.compnay_id = ?";

            Object obj1 = beandao.getObjectBySqlAndParams(sql1, new Object[]{document.getCompanyID()});
            Object[] objs1 = (Object[]) obj1;

            String liceneNo = objs1[0] != null ? objs1[0].toString() : "";//营业执照
            String email = objs1[1] != null ? objs1[1].toString() : "";//邮编


            JSONObject tpjson = new JSONObject();

            tpjson.put("companyName", templateParams.getCompanyName());
            tpjson.put("companyAddr", templateParams.getCompanyAddr());
            tpjson.put("companyTel", templateParams.getCompanyTel());
            tpjson.put("staffName", templateParams.getStaffName());
            tpjson.put("reference", templateParams.getReference());
            tpjson.put("staffIdentityCard", templateParams.getStaffIdentityCard());
            tpjson.put("subjectType", templateParams.getSubjectType());
            tpjson.put("productName", templateParams.getProductName());
            tpjson.put("money", templateParams.getMoney());
            tpjson.put("dmoney", templateParams.getDmoney());


            tpjson.put("managementFees", templateParams.getManagementFees());
            tpjson.put("dmanagementFees", templateParams.getDmanagementFees());
            tpjson.put("operatingFee", templateParams.getOperatingFee());
            tpjson.put("doperatingFee", templateParams.getDoperatingFee());

            tpjson.put("operatingStaffName", templateParams.getOperatingStaffName());
            tpjson.put("lldate", templateParams.getLldate());
            tpjson.put("scdate", templateParams.getScdate());
            tpjson.put("cnxsite", templateParams.getCnxsite());
//            tpjson.put("site", templateParams.getSite());


            tpjson.put("referrerAddress", templateParams.getReferrerAddress());
            tpjson.put("year", templateParams.getYear());
            tpjson.put("month", templateParams.getMonth());
            tpjson.put("day", templateParams.getDays());
            tpjson.put("validateYear", templateParams.getValidateYear());

            String signId = "";
            List<BaseBean> stamplist = beandao.getListBeanByHqlAndParams("from EnterpriseStamp where companyID = ? and stampType = ? and signId is not null order by createTime desc", new Object[]{document.getCompanyID(), "企业公章"});
//            EnterpriseStamp enterpriseStamp = (EnterpriseStamp) stamplist.get(0);
//            if (enterpriseStamp != null) {
//                signId = enterpriseStamp.getSignId();
//            }
            //签订日期
            String aplNo = JzqAPI.applySign(tpjson, contractName, templateNo, liceneNo, email, signId);
            document.setApplyNo(aplNo);
            baseBeans.add(document);

            beandao.saveBeansListAndexecuteHqlsByParams(baseBeans, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 君子签公司认证
     * * @return
     */
    public void synJzqCompany(String out_request_no, String realpath) {

        String hql = "from ApplyParam where out_request_no = ?";
        ApplyParam applyParam = (ApplyParam) beandao.getBeanByHqlAndParams(hql, new Object[]{out_request_no});

        BusinessLicenseInfo businessLicenseInfo = applyParam.getBusiness_license_info();

        IdCardInfo idCardInfo = applyParam.getId_card_info();
        ContactInfo contactInfo = applyParam.getContact_info();

        try {
            OrganizationCreateReq req = new OrganizationCreateReq();
            req.setEmailOrMobile(contactInfo.getContact_email());
            req.setName(businessLicenseInfo.getMerchant_name());    //
            req.setOrganizationType(0);//组织类型 0企业,1事业单位
            req.setIdentificationType(1);//证件类型：0多证,1多证合一
            req.setOrganizationRegNo(businessLicenseInfo.getBusiness_license_number());//营业执照号
            String license_copy = businessLicenseInfo.getBusiness_license_copy();
            String licensecopyname = license_copy.substring(license_copy.lastIndexOf("/") + 1);
            req.setOrganizationRegImg(new ByteArrayBody(Files.readAllBytes(new File(realpath + license_copy).toPath()), licensecopyname));

            //	req.setOrganizationRegImg(new ByteArrayBody(Files.readAllBytes(new File("E://mysw.jpg").toPath()), "mysw.jpg"));//营业执照图片


            req.setLegalName(idCardInfo.getId_card_name());//法人姓名
            req.setLegalIdentityCard(idCardInfo.getId_card_number());//法人证件号
            req.setLegalMobile(idCardInfo.getLegal_tel());//法人电话
            //req.setEmailOrMobile("123@163.com"); //不填写默认生成

            if (businessLicenseInfo.getBusiness_license_number().length() != 18) {
                OrganizationCertInfo organizationCertInfo = applyParam.getOrganization_cert_info();
                req.setOrganizationCode(organizationCertInfo.getOrganization_number());//组织结构代码,多证时必传

                String organization_copy = organizationCertInfo.getOrganization_copy();
                String copyname = organization_copy.substring(organization_copy.lastIndexOf("/") + 1);
                req.setOrganizationCodeImg(new ByteArrayBody(Files.readAllBytes(new File(realpath + organization_copy).toPath()), copyname));//组织结构代码扫描件,多证时必传,图片,不能超2MB
            }
            ApplyResult applyResult = (ApplyResult) beandao.getBeanByHqlAndParams("from ApplyResult where out_request_no = ?", new Object[]{out_request_no});
            //存哪去呢
            if (applyResult == null) {
                applyResult = new ApplyResult();
                applyResult.setArId(serverService.getServerID("arid"));
                applyResult.setOut_request_no(out_request_no);


            }
            applyResult = JzqAPI.addCompany(req, applyResult);


            beandao.update(applyResult);
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    /**
     * 君子签公司认证结果
     * * @return
     */
    public void searchJzqCompany(String out_request_no) {

        ApplyResult applyResult = (ApplyResult) beandao.getBeanByHqlAndParams("from ApplyResult where out_request_no = ?", new Object[]{out_request_no});

        if (applyResult != null) {
            String emailOrMobile = applyResult.getEmailOrMobile();
            if (emailOrMobile != null && !emailOrMobile.equals("")) {
                applyResult = JzqAPI.auditStatus(emailOrMobile, applyResult);
                beandao.update(applyResult);

            }

        }

    }

    /**
     * 获取签约链接
     *
     * @return
     */
    public String signUrl(String docID, String companyID, String scene) {

        Document document = (Document) beandao.getBeanByHqlAndParams("from Document where docId = ?", new Object[]{docID});

        TemplateParams templateParams = (TemplateParams) beandao.getBeanByHqlAndParams("from TemplateParams where docID = ?", new Object[]{docID});

        String aplNo = "";
        JSONObject tpjson = new JSONObject();
        String contractName = "";
        String templateNo = "";
        String liceneNo = "";
        String email = "";
        String signId = "";
        String signUrl = "";
        if (document.getApplyNo() != null && !document.getApplyNo().equals("")) {
            aplNo = document.getApplyNo();
            signUrl = JzqAPI.signLink(aplNo, templateParams.getStaffName(), templateParams.getStaffIdentityCard());
        }

        if (signUrl.equals("")) {
            JzqTemplate jzqTemplate = (JzqTemplate) beandao.getBeanByHqlAndParams("from JzqTemplate where companyID = ? and scene = ?", new Object[]{companyID, scene});

            if (jzqTemplate == null) {
                List<BaseBean> listtemp = beandao.getListBeanByHqlAndParams("from JzqTemplate where  scene = ? and templateID = ? ", new Object[]{scene, "company201009046vxdyzy4wg0000000130"});
                if (listtemp.size() > 0) {
                    jzqTemplate = (JzqTemplate) listtemp.get(0);
                }
            }

            contractName = jzqTemplate.getTemplateName();//合同名称
            templateNo = jzqTemplate.getTemplateID();//君子签合同模板

            String sql = "select b.business_license_number,r.emailOrMobile from dtContactCompany c left join DT_ccom_com m on c.ccompanyID = m.ccompany_Id  left join dt_sft_ApplyParam p on c.applyid = p.applyid left join dt_sft_BusinessLicenseInfo b on p.bliKey = b.bliKey left join dt_sft_ApplyResult r on r.out_request_no = p.out_request_no where m.compnay_id = ?";

            Object obj = beandao.getObjectBySqlAndParams(sql, new Object[]{companyID});
            Object[] objs = (Object[]) obj;

            liceneNo = objs[0] != null ? objs[0].toString() : "";//营业执照
            email = objs[1] != null ? objs[1].toString() : "";//邮编


            tpjson.put("companyName", templateParams.getCompanyName());
            tpjson.put("companyAddr", templateParams.getCompanyAddr());
            tpjson.put("companyTel", templateParams.getCompanyTel());
            tpjson.put("staffName", templateParams.getStaffName());
            tpjson.put("reference", templateParams.getReference());
            tpjson.put("staffIdentityCard", templateParams.getStaffIdentityCard());
            tpjson.put("subjectType", templateParams.getSubjectType());
            tpjson.put("productName", templateParams.getProductName());
            tpjson.put("money", templateParams.getMoney());
            tpjson.put("dmoney", templateParams.getDmoney());

            tpjson.put("managementFees", templateParams.getManagementFees());
            tpjson.put("dmanagementFees", templateParams.getDmanagementFees());
            tpjson.put("operatingFee", templateParams.getOperatingFee());
            tpjson.put("doperatingFee", templateParams.getDoperatingFee());

            tpjson.put("operatingStaffName", templateParams.getOperatingStaffName());
            tpjson.put("lldate", templateParams.getLldate());
            tpjson.put("scdate", templateParams.getScdate());
            tpjson.put("cnxsite", templateParams.getCnxsite());
//            tpjson.put("site", templateParams.getSite());

            tpjson.put("referrerAddress", templateParams.getReferrerAddress());
            tpjson.put("year", templateParams.getYear());
            tpjson.put("month", templateParams.getMonth());
            tpjson.put("day", templateParams.getDays());
            tpjson.put("validateYear", templateParams.getValidateYear());

            List<BaseBean> stamplist = beandao.getListBeanByHqlAndParams("from EnterpriseStamp where companyID = ? and stampType = ? and signId is not null order by createTime desc", new Object[]{companyID, "企业公章"});

            if (stamplist.size() > 0) {
                EnterpriseStamp enterpriseStamp = (EnterpriseStamp) stamplist.get(0);
                if (enterpriseStamp != null) {
                    signId = enterpriseStamp.getSignId();
                }

            }


            aplNo = JzqAPI.applySign(tpjson, contractName, templateNo, liceneNo, email, signId);
            document.setApplyNo(aplNo);
            beandao.update(document);

            signUrl = JzqAPI.signLink(aplNo, templateParams.getStaffName(), templateParams.getStaffIdentityCard());

        }


        if ("".equals(signUrl)) {

            document.setApplyNo("");
            beandao.update(document);
        }

        return signUrl;

    }


    /**
     * 发起签约
     *
     * @return
     */
    public void applySign(String docID, String scene) {
        Document document = (Document) beandao.getBeanByHqlAndParams("from Document where docId = ?", new Object[]{docID});

        TemplateParams templateParams = (TemplateParams) beandao.getBeanByHqlAndParams("from TemplateParams where docID = ?", new Object[]{docID});
        JzqTemplate jzqTemplate = (JzqTemplate) beandao.getBeanByHqlAndParams("from JzqTemplate where companyID = ? and scene = ?", new Object[]{document.getCompanyID(), scene});

        if (jzqTemplate == null) {
            List<BaseBean> listtemp = beandao.getListBeanByHqlAndParams("from JzqTemplate where  scene = ? and templateID = ? ", new Object[]{scene, "company201009046vxdyzy4wg0000000130"});
            if (listtemp.size() > 0) {
                jzqTemplate = (JzqTemplate) listtemp.get(0);
            }
        }

        String contractName = jzqTemplate.getTemplateName();//合同名称
        String templateNo = jzqTemplate.getTemplateID();//君子签合同模板

        String sql = "select b.business_license_number,r.emailOrMobile from dtContactCompany c left join DT_ccom_com m on c.ccompanyID = m.ccompany_Id  left join dt_sft_ApplyParam p on c.applyid = p.applyid left join dt_sft_BusinessLicenseInfo b on p.bliKey = b.bliKey left join dt_sft_ApplyResult r on r.out_request_no = p.out_request_no where m.compnay_id = ?";

        Object obj = beandao.getObjectBySqlAndParams(sql, new Object[]{document.getCompanyID()});
        Object[] objs = (Object[]) obj;

        String liceneNo = objs[0] != null ? objs[0].toString() : "";//营业执照
        String email = objs[1] != null ? objs[1].toString() : "";//邮编


        JSONObject tpjson = new JSONObject();

        tpjson.put("companyName", templateParams.getCompanyName());
        tpjson.put("companyAddr", templateParams.getCompanyAddr());
        tpjson.put("companyTel", templateParams.getCompanyTel());
        tpjson.put("staffName", templateParams.getStaffName());
        tpjson.put("reference", templateParams.getReference());
        tpjson.put("staffIdentityCard", templateParams.getStaffIdentityCard());
        tpjson.put("subjectType", templateParams.getSubjectType());
        tpjson.put("productName", templateParams.getProductName());
        tpjson.put("money", templateParams.getMoney());
        tpjson.put("dmoney", templateParams.getDmoney());


        tpjson.put("managementFees", templateParams.getManagementFees());
        tpjson.put("dmanagementFees", templateParams.getDmanagementFees());
        tpjson.put("operatingFee", templateParams.getOperatingFee());
        tpjson.put("doperatingFee", templateParams.getDoperatingFee());

        tpjson.put("operatingStaffName", templateParams.getOperatingStaffName());
        tpjson.put("lldate", templateParams.getLldate());
        tpjson.put("scdate", templateParams.getScdate());
        tpjson.put("cnxsite", templateParams.getCnxsite());
//        tpjson.put("site", templateParams.getSite());

        tpjson.put("referrerAddress", templateParams.getReferrerAddress());
        tpjson.put("year", templateParams.getYear());
        tpjson.put("month", templateParams.getMonth());
        tpjson.put("day", templateParams.getDays());
        tpjson.put("validateYear", templateParams.getValidateYear());

        String signId = "";
        List<BaseBean> stamplist = beandao.getListBeanByHqlAndParams("from EnterpriseStamp where companyID = ? and stampType = ? and signId is not null order by createTime desc", new Object[]{document.getCompanyID(), "企业公章"});
        EnterpriseStamp enterpriseStamp = (EnterpriseStamp) stamplist.get(0);
        if (enterpriseStamp != null) {
            signId = enterpriseStamp.getSignId();
        }

        String aplNo = JzqAPI.applySign(tpjson, contractName, templateNo, liceneNo, email, signId);
        document.setApplyNo(aplNo);
        beandao.update(document);

        System.out.println(aplNo);
    }


    /**
     * 获取签约查看链接
     *
     * @param docID
     * @return
     */
    public String viewUrl(String docID) {

        Document document = (Document) beandao.getBeanByHqlAndParams("from Document where docId = ?", new Object[]{docID});

        TemplateParams templateParams = (TemplateParams) beandao.getBeanByHqlAndParams("from TemplateParams where docID = ?", new Object[]{docID});

        String aplNo = "";
        if (document.getApplyNo() != null && !document.getApplyNo().equals("")) {
            aplNo = document.getApplyNo();
        }
        String viewUrl = JzqAPI.signDetailLink(aplNo);

        return viewUrl;

    }

    /**
     * 根据订单号获取签署的合同
     *
     * @param journalNum
     * @return
     */
    public Document getDocInfo(String journalNum) {
        PayBackupBill pb = (PayBackupBill) baseBeanService.getBeanByHqlAndParams("from PayBackupBill where journalNum = ?", new Object[]{journalNum});

        Document doc = (Document) beandao.getBeanByHqlAndParams("from Document where docId = ?", new Object[]{pb.getCoID()});

        return doc;
    }


    /**
     * 生成pdf
     *
     * @param templateId
     * @return
     */
    public String getPdfTempView(String templateId, String realPath) {

        DocumentTemplate docTemp = (DocumentTemplate) beandao.getBeanByHqlAndParams("from DocumentTemplate where templateId = ?", new Object[]{templateId});


        String filePath = docTemp.getTemplatePath();
        String pdfPath = filePath.substring(0, filePath.lastIndexOf(".")) + ".pdf";
        if (docTemp.getPdfpath() != null && !docTemp.getPdfpath().equals("")) {

            File filePDF = new File(realPath + docTemp.getPdfpath());
            if (!filePDF.exists()) {
                Office2PdfUtil.office2Pdf(realPath + filePath, realPath + pdfPath);
                //有路径没有实际文件就生成

            } else {
                //存在 就看看是不是最新的
                File file = new File(realPath + filePath);
                long time = file.lastModified();//当前
                long newtime = time / 1000L * 1000L;
                Date date = docTemp.getPdfTime();

                long times = date.getTime();

                if (newtime > times) {
                    //说明修改过 改过就重新生成
                    Office2PdfUtil.office2Pdf(realPath + filePath, realPath + pdfPath);
                    docTemp.setPdfTime(new Date());
                    beandao.update(docTemp);

                }
            }

        } else {

            //word,excel 还没有生成过PDF
            Office2PdfUtil.office2Pdf(realPath + filePath, realPath + pdfPath);
            docTemp.setPdfTime(new Date());
            docTemp.setPdfpath(pdfPath);
            beandao.update(docTemp);


        }


        return pdfPath;
    }


    /**
     * 生成pdf
     *
     * @param docId
     * @return
     */
    public String getPdfView(String docId, String realPath) {
        DocumentFileAttach docFileAttach = null;
        Document doc = (Document) beandao.getBeanByHqlAndParams("from Document where docId = ?", new Object[]{docId});


        // 处理附件(因为之前的数据有可能是多个附件，以免出现错误)
        List<BaseBean> attachlist = docCommonService.getOfficeAttach(doc);
        if (attachlist.size() != 0) {
            docFileAttach = (DocumentFileAttach) attachlist.get(0);
        }
        String filePath = docFileAttach.getFilePath();
        String pdfPath = "";
        if (docFileAttach.getFileType().equals("W") || docFileAttach.getFileType().equals("E") || docFileAttach.getFileType().equals("M")) {

            pdfPath = filePath.substring(0, filePath.lastIndexOf(".")) + ".pdf";
            if (doc.getPdfUrl() != null && !doc.getPdfUrl().equals("")) {

                File filePDF = new File(realPath + doc.getPdfUrl());
                if (!filePDF.exists()) {
                    Office2PdfUtil.office2Pdf(realPath + docFileAttach.getFilePath(), realPath + pdfPath);

                } else {

                    File file = new File(realPath + docFileAttach.getFilePath());
                    long time = file.lastModified();//当前
                    long newtime = time / 1000L * 1000L;
                    System.out.println("newtime" + time);
                    System.out.println("time" + time);
                    Date date = docFileAttach.getCreatetime();
                    System.out.println("date" + date);
                    long times = date.getTime();
                    System.out.println("times" + times);
                    List<BaseBean> sealerlist = beandao.getListBeanByHqlAndParams("from DocumentSealer where docId = ?", new Object[]{docId});

                    //草稿，驳回，待审核,不批准，公文为转他人审批状态 这几种容易修改
                    if (doc.getStatus().equals("I") || doc.getStatus().equals("R") || doc.getStatus().equals("S") || doc.getStatus().equals("U") || doc.getStatus().equals("T") || doc.getStatus().equals("A")) {

                        if (sealerlist != null && sealerlist.size() > 0) {
                            //说明盖过盖过君子签的章，盖过会自动更新PDFURL，无需转
                            pdfPath = doc.getPdfUrl();
                        } else {

                            if (newtime > times) {
                                System.out.println("有修改");
                                //说明修改过 改过就重新生成
                                Office2PdfUtil.office2Pdf(realPath + docFileAttach.getFilePath(), realPath + pdfPath);
                                docFileAttach.setCreatetime(new Date());
                                beandao.update(docFileAttach);

                            }
                        }
                    } else if (doc.getStatus().equals("P") || doc.getStatus().equals("F") || doc.getStatus().equals("O")) {

                        //分发以后就不转换了
                        pdfPath = doc.getPdfUrl();
                    }
                }

            } else {

                //word,excel 还没有生成过PDF
                Office2PdfUtil.office2Pdf(realPath + docFileAttach.getFilePath(), realPath + pdfPath);
                docFileAttach.setCreatetime(new Date());
                doc.setPdfUrl(pdfPath);
                beandao.update(docFileAttach);
                beandao.update(doc);

            }
        } else if (docFileAttach.getFileType().equals("P")) {
            //本身就是PDF个是无需转换
            if (doc.getPdfUrl() != null && !doc.getPdfUrl().equals("")) {
                pdfPath = doc.getPdfUrl();
            } else {
                pdfPath = filePath;
                doc.setPdfUrl(pdfPath);
                beandao.update(doc);

            }

        }


        return pdfPath;
    }

    /**
     * 改变合同状态
     *
     * @param docId
     * @return
     */
    public String updateState(String docId) {

        String hql = "from  Document where docId = ?";
        Document doc = (Document) beandao.getBeanByHqlAndParams(hql, new Object[]{docId});
        doc.setStatus("F");
        doc.setSealTime(new Date());
        doc.setSealerID(doc.getDrafterID());
        beandao.update(doc);

        return null;
    }

    /**
     * 创建文件
     *
     * @param staffID
     * @param companyID
     * @param receiptType
     * @return
     */
    public String createDoc(String realpath, String staffID, String companyID, String receiptType) {
        String newfilepath = "";
        String hql = "from DocumentTemplate where companyId = ? and receiptType = ? and fileType = ? order by time desc";
        List<BaseBean> list = beandao.getListBeanByHqlAndParams(hql, new Object[]{companyID, receiptType, "P"});
        if (list.size() == 0) {
            list = beandao.getListBeanByHqlAndParams(hql, new Object[]{companyID, receiptType, "W"});

        }

        if (list.size() == 0) {
            return "0";
        } else {
            DocumentTemplate dc = (DocumentTemplate) list.get(0);
            if (dc != null) {
                if (dc.getPdfpath() != null && !dc.getPdfpath().equals("")) {
                    //有PDf模板了 复制一个模板

                    newfilepath = createOffice(realpath, "P",
                            dc.getPdfpath(), "");

                } else {
                    //没有pdf，转成pdf


                }
            }

        }


        return null;
    }


    // 创建
    private String createOffice(String realpath, String fileType,
                                String templatepath, String storePath) {
        String ext = "";
        if (fileType.equals("W")) {

            ext = ".doc";
        } else if (fileType.equals("E")) {

            ext = ".xls";
        } else if (fileType.equals("P")) {
            ;
            ext = ".pdf";
        }

        String fileSaveName = UUID.randomUUID().toString().replaceAll("-", "") + "." + ext;
        try {

            File fileSaveDir = new File(realpath + storePath);
            File fileSave = new File(realpath + storePath, fileSaveName);
            File office = new File(realpath + templatepath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }
            FileCopyUtils.copy(office, fileSave);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return storePath + "/" + fileSaveName;
    }


    /**
     * 获取下载地址
     *
     * @param docId
     * @return
     */
    public String getLoadLink(String docId) {
        Document document = (Document) beandao.getBeanByHqlAndParams("from Document where docId = ?", new Object[]{docId});


        String aplNo = "";
        if (document.getApplyNo() != null && !document.getApplyNo().equals("")) {
            aplNo = document.getApplyNo();
        }
        String loadUrl = JzqAPI.linkFile(aplNo);
        System.out.println("loadUrl" + loadUrl);


        return loadUrl;

    }

    /**
     * 保存文件到本地
     *
     * @param loadUrl
     * @param companyID
     * @param realpath
     * @return
     */
    public String saveFile(String loadUrl, String companyID, String realpath) {

        String savepath = "upload_files/" + companyID + "/web/document/"
                + Utilities.getDateString(new Date(), "yyyy-MM-dd") + "/";
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".pdf";
        try {
            URL url = new URL(loadUrl);
            BufferedInputStream bis = new BufferedInputStream(url.openStream());

            File file = new File(realpath + savepath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File file1 = new File(realpath + savepath + fileName);
            if (!file1.exists()) {
                file1.createNewFile();
            }
            FileOutputStream fis = new FileOutputStream(realpath + savepath + fileName);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = bis.read(buffer, 0, 1024)) != -1) {
                fis.write(buffer, 0, count);
            }
            fis.close();
            ;
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return savepath + fileName;
    }

    /**
     * 保存文件到本地
     *
     * @param loadUrl
     * @param companyID
     * @param realpath
     * @return
     */
    public String saveVideoUrl(String loadUrl, String companyID, String realpath) {

        String savepath = "upload_files/" + companyID + "/web/document/"
                + Utilities.getDateString(new Date(), "yyyy-MM-dd") + "/";
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".mp4";//文件写入
        try {
            URL url = new URL(loadUrl);
            BufferedInputStream bis = new BufferedInputStream(url.openStream());

            File file = new File(realpath + savepath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File file1 = new File(realpath + savepath + fileName);
            if (!file1.exists()) {
                file1.createNewFile();
            }
            FileOutputStream fis = new FileOutputStream(realpath + savepath + fileName);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = bis.read(buffer, 0, 1024)) != -1) {
                fis.write(buffer, 0, count);
            }
            fis.close();
            ;
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return savepath + fileName;
    }

    /**
     * 保存视频
     *
     * @param base64
     * @param companyID
     * @param realpath
     * @return
     */
    private String saveVideo(String base64, String companyID, String realpath) {


        String video = "";//base64编码的视频
//拼接路径
        String savepath = "upload_files/" + companyID + "/web/document/"
                + Utilities.getDateString(new Date(), "yyyy-MM-dd") + "/";
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".mp4";//文件写入
        try (FileOutputStream fileOutputStream = new FileOutputStream(
                new File(savepath + fileName));) {

            byte[] decode = Base64.getDecoder().decode(base64);
            fileOutputStream.write(decode);

        } catch (Exception e) {

        }

        return savepath + fileName;
    }

    /**
     * 上传印章
     *
     * @param companyId
     * @param stampname
     * @param stampimgs
     * @return
     */
    public String uploadStamp(String companyId, String stampname, String stampimgs) {

        String sql = "select t.emailOrMobile from dt_sft_applyresult t ,dt_sft_applyparam p,Dtcontactcompany c,dt_ccom_com m where t.out_request_no= p.out_request_no and p.applyid = c.applyid and c.ccompanyid = m.ccompany_id and m.compnay_id= ?";
        Object obj = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{companyId});


        if (obj != null) {

            String data = JzqAPI.uploadEntSign(stampname, obj.toString(), stampimgs);
            System.out.println(data);
            EnterpriseStamp es = new EnterpriseStamp();
            es.setCompanyID(companyId);
            es.setStampName(stampname);
            es.setStampType("企业公章");
            es.setSignId(data);
            es.setCreateTime(new Date());
            baseBeanService.save(es);

            return data;
        }

        return null;
    }


    /**
     * 查询专岗部门
     *
     * @param staffID
     * @param companyID
     * @return
     */
    public String getCheckOrg(String staffID, String companyID) {

        String sql = "select s.organizationid from Dtcos s,Dtcorganization o where  s.companyid = ? and s.organizationid = o.organizationid  and o.organizationpid = s.companyid and s.cosStatus='50' and s.status='01' and s.staffid = ?";
        Object obj = beandao.getObjectBySqlAndParams(sql, new Object[]{companyID, staffID});

        return obj == null ? "" : obj.toString();
    }


    /**
     * 获取签约链接
     *
     * @return
     */
    public String signUrlDoc(String docID, String basePath, String tel, String realpath) {

        Document document = (Document) beandao.getBeanByHqlAndParams("from Document where docId = ?", new Object[]{docID});
        String hql = "from Staff f where f.staffID = ?";
        Staff staff = (Staff) beandao.getBeanByHqlAndParams(hql, new Object[]{document.getSealerID()});

        if (staff.getRealname() == null || staff.getRealname().equals("")) {

            return "noauth";
        }


        SignatoryReq sReq = new SignatoryReq();
        String aplNo = "";


        List<BaseBean> stamplist = null;
        String companyIDofSealer = document.getCompanyIDofSealer();

        if (companyIDofSealer != null && !companyIDofSealer.equals("")) {
            if ("15810799888".equals(tel)) {
                stamplist = beandao.getListBeanByHqlAndParams("from EnterpriseStamp where companyID = ? and signId is not null", new Object[]{companyIDofSealer});

                if (stamplist == null || stamplist.size() == 0) {

                    companyIDofSealer = "company201009046vxdyzy4wg0000000025";
                    stamplist = beandao.getListBeanByHqlAndParams("from EnterpriseStamp where companyID = ? and signId is not null", new Object[]{companyIDofSealer});

                }

            } else {
                stamplist = beandao.getListBeanByHqlAndParams("from EnterpriseStamp where companyID = ? and responsibleID = ? and signId is not null", new Object[]{document.getCompanyIDofSealer(), document.getSealerID()});

            }
        }


        if (stamplist != null && stamplist.size() > 0) {
            //有印章，按公司来

            String sql1 = "select b.business_license_number,r.emailOrMobile,c.companyName from dtContactCompany c left join DT_ccom_com m on c.ccompanyID = m.ccompany_Id  left join dt_sft_ApplyParam p on c.applyid = p.applyid left join dt_sft_BusinessLicenseInfo b on p.bliKey = b.bliKey left join dt_sft_ApplyResult r on r.out_request_no = p.out_request_no where m.compnay_id = ?";
            Object obj = baseBeanService.getObjectBySqlAndParams(sql1, new Object[]{companyIDofSealer});
            Object[] objs = (Object[]) obj;
            sReq.setChapteName(staff.getRealname());//仅用于传值使用没有实际意义 授权人姓名
            sReq.setChapteJson(staff.getStaffIdentityCard());//仅用于传值使用没有实际意义 授权人身份证号
            staff.setRealname(objs[2].toString());
            staff.setStaffIdentityCard(objs[0].toString());

            sReq.setEmail(objs[1].toString());
            sReq.setIdentityType(11);//	身份类型:1身份证 11营业执照,


        } else {

            sReq.setIdentityType(1);//	身份类型:1身份证 11营业执照,


        }


        sReq.setFullName(staff.getRealname());

        sReq.setIdentityCard(staff.getStaffIdentityCard());//个人传身份证号，企业传营业执照号/统一社会信用代码号
        sReq.setMobile(staff.getReference());

        DocumentSealer sealer = (DocumentSealer) beandao.getBeanByHqlAndParams("from DocumentSealer where docId = ? and (state = '00' or state is null) and applyNo = ? and  sealerID = ?", new Object[]{docID, document.getApplyNo(), document.getSealerID()});

        if (document.getApplyNo() != null && !document.getApplyNo().equals("")) {
            aplNo = document.getApplyNo();//说明已经发起过签约
            System.out.println(aplNo);
            try {


                //   if(sealer==null) {
                JzqAPI.applySignAdd(document.getApplyNo(), sReq);
                //    }
            } catch (Exception e) {

            }
        } else {

            //第一次发起签约
            String pdfUrl = getPdfView(document.getDocId(), realpath);

            aplNo = JzqAPI.applySignUrl(basePath + pdfUrl, document.getTitle(), sReq);
            document.setApplyNo(aplNo);
            beandao.update(document);


        }


        //获取签约链接可能出现修改过信息身份证或者真实姓名的情况，如果修改过会提示查询不到签约方，要重新发起一个新的
        String signUrl = JzqAPI.signLinkDoc(aplNo, staff.getRealname(), staff.getStaffIdentityCard(), sReq.getIdentityType());

        if (signUrl.equals("")) {
            //第一次发起签约
            String pdfUrl = getPdfView(document.getDocId(), realpath);

            File file = new File(realpath + pdfUrl);

            if (file.exists()) {//如果文件已经生成完毕了，再发起签约
                aplNo = JzqAPI.applySignUrl(basePath + pdfUrl, document.getTitle(), sReq);
                document.setApplyNo(aplNo);
                beandao.update(document);

                signUrl = JzqAPI.signLinkDoc(aplNo, staff.getRealname(), staff.getStaffIdentityCard(), sReq.getIdentityType());
            } else {
                //如果文件尚未生成就一直生成给他个1分钟时间生成
            }


        }


        if (sealer == null) {


            DocumentSealer documentSealer = new DocumentSealer();
            documentSealer.setId(serverService.getServerID("id"));
            documentSealer.setCompanyIDofSealer(document.getCompanyIDofSealer());
            documentSealer.setDeptIDofSealer(document.getDeptIDofSealer());
            documentSealer.setSealerID(document.getSealerID());
            documentSealer.setDocId(document.getDocId());
            documentSealer.setState("00");
            documentSealer.setApplyNo(document.getApplyNo());

            documentSealer.setIdentityType(sReq.getIdentityType() + "");
            documentSealer.setIdentityCard(sReq.getIdentityCard());
            documentSealer.setFullName(sReq.getFullName());
            beandao.save(documentSealer);
        } else {
            sealer.setIdentityType(sReq.getIdentityType() + "");
            sealer.setIdentityCard(sReq.getIdentityCard());
            beandao.update(sealer);
        }

        return signUrl;
    }

    /**
     * 更新盖章信息
     *
     * @param docId
     * @return
     */
    public String updateSealer(String docId, String path) {
        Document document = (Document) beandao.getBeanByHqlAndParams("from Document where docId = ?", new Object[]{docId});

        String loadLink = getLoadLink(docId);

        String filepath = saveFile(loadLink, document.getCompanyID() == null ? document.getDrafterID() : document.getCompanyID(), path);
        System.out.println("filepath" + filepath);
        if (!"".equals(filepath)) {
            document.setPdfUrl(filepath);

        }
        String hql = "from DocumentSealer where docId = ? and (state = '00' or state is null) and applyNo = ? and  sealerID = ?";

        DocumentSealer sealer = (DocumentSealer) beandao.getBeanByHqlAndParams(hql, new Object[]{docId, document.getApplyNo(), document.getSealerID()});

        if (sealer == null) {
            sealer = new DocumentSealer();
            sealer.setId(serverService.getServerID("id"));
            sealer.setCompanyIDofSealer(document.getCompanyIDofSealer());
            sealer.setDeptIDofSealer(document.getDeptIDofSealer());
            sealer.setSealerID(document.getSealerID());
            sealer.setDocId(docId);
            sealer.setApplyNo(document.getApplyNo());
        }
        sealer.setLoadLink(loadLink);
        sealer.setPdfpath(filepath);
        sealer.setState("01");
        sealer.setSealTime(new Date());
        beandao.update(sealer);
        beandao.update(document);


        //判断是否是第三方的如果是第三方的处理第三方状态
        updateRelate(document.getScene(), docId, "seal");


        return filepath;
    }

    /**
     * 修改状态
     *
     * @param sence
     * @param docId
     */
    public void updateRelate(String sence, String docId, String jump) {

        if ("03".equals(sence)) {
            String stsl = "from DocRelateOther where docId = ?";
            DocRelateOther docRelateOther = (DocRelateOther) beandao.getBeanByHqlAndParams(stsl, new Object[]{docId});

            if (docRelateOther != null) {
                String tableName = docRelateOther.getTableName();
                String idName = docRelateOther.getIdName();
                String idValue = docRelateOther.getIdValue();
                String stateName = docRelateOther.getStateName();
                String stateValue = docRelateOther.getStateValue();

                if (jump.equals("reject")) {
                    stateValue = docRelateOther.getRefundValue();

                }
                String hqls = "update " + tableName + " set " + stateName + "=? where " + idName + "= ?";
                docRelateOther.setUpdateDate(new Date());
                docRelateOther.setStatus("01");
                List<BaseBean> beans = new ArrayList<BaseBean>();
                beans.add(docRelateOther);
                beandao.saveBeansListAndexecuteHqlsByParams(beans,
                        new String[]{hqls}, new Object[]{stateValue, idValue});
            }
            if (docRelateOther.getSource().equals("fs")) {
                Document doc = (Document) beandao.getBeanByHqlAndParams("from Document where docId = ?", new Object[]{docId});
                //如果是停车收费设置免费时长
                if (jump.equals("reject")) {

                    addOthers(docRelateOther, "03", doc);
                } else {
                    //如果通过

                    addOthers(docRelateOther, "02", doc);
                }


            }

        }
    }

    /**
     * 追加签约人好像没用
     *
     * @param docId
     * @return
     */
    public void applySignAdd(String docId) {
        Document document = (Document) beandao.getBeanByHqlAndParams("from Document where docId = ?", new Object[]{docId});

        Staff staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{document.getSealerID()});

        SignatoryReq signatoryReq = new SignatoryReq();
        signatoryReq.setFullName(staff.getStaffName());
        signatoryReq.setIdentityType(1);
        signatoryReq.setIdentityCard(staff.getStaffIdentityCard());
        signatoryReq.setMobile(staff.getReference());
        signatoryReq.setEmail(null);


        JzqAPI.applySignAdd(document.getApplyNo(), signatoryReq);

        DocumentSealer documentSealer = new DocumentSealer();
        documentSealer.setId(serverService.getServerID("id"));
        documentSealer.setCompanyIDofSealer(document.getCompanyIDofSealer());
        documentSealer.setDeptIDofSealer(document.getDeptIDofSealer());
        documentSealer.setSealerID(document.getSealerID());
        documentSealer.setDocId(document.getDocId());
        documentSealer.setState("00");
        documentSealer.setApplyNo(document.getApplyNo());
        documentSealer.setIdentityType(signatoryReq.getIdentityType() + "");
        documentSealer.setIdentityCard(signatoryReq.getIdentityCard());
        documentSealer.setFullName(signatoryReq.getFullName());
        beandao.save(documentSealer);
    }

    /**
     * 获取模板
     *
     * @param companyID
     * @param parameter
     * @param fileType
     * @param temptId
     * @return
     */
    public PageForm getDocTemp(int pageNumber, int pageSize, String companyID, String staffID, String parameter, String fileType, String temptId, String module) {
        List<Object> params = new ArrayList<Object>();


        String sql = "select t.templateId,t.templatePath,nvl(t.templateType,''),t.fileType,t.fileShowName,to_char(t.time,'yyyy/mm/dd hh24:mi:ss'),f.staffName,nvl(t.sysSet,'01'),nvl(p.templateTypeName,''),t.createrID,nvl(t.temptId,'') from DT_OA_DOCUMENT_TEMPLATE  t left join dt_hr_Staff f on  t.createrID = f.staffID  left join DT_OA_TEMPLATE_TYPE p on t.temptId = p.temptId where ";

        if (companyID != null && !companyID.equals("")) {
            sql += "(t.companyId = ? or t.sysSet = ?)";
            params.add(companyID);
        } else {
            sql += "((t.createrID = ? and t.companyId is null) or t.sysSet = ?)";
            params.add(staffID);

        }

        params.add("00");
        if (parameter != null && !parameter.equals("")) {
            sql += " and t.fileShowName like ?";
            params.add("%" + parameter + "%");
        }

        if (fileType != null && !fileType.equals("")) {
            sql += " and t.fileType = ?";
            params.add(fileType);
        }
        if (temptId != null && !temptId.equals("")) {
            sql += " and t.temptId = ?";
            params.add(temptId);
        }
        if (module != null && !module.equals("")) {
            sql += " and t.module = ?";
            params.add(module);
        }
        sql += " order by  case when t.sysSet is null or t.sysSet ='' then '01' else t.sysSet end desc,t.time desc";
        PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql,
                "select count(*) from (" + sql + ")", params.toArray());

        return pageForm;
    }

    /**
     * 获取模板
     *
     * @param companyID
     * @param parameter
     * @param fileType
     * @param temptId
     * @return
     */
    public List<BaseBean> getDocTempTree(String companyID, String staffID, String parameter, String fileType, String temptId, String module, String range) {
        List<Object> params = new ArrayList<Object>();


        String sql = "select t.templateId,t.templatePath,nvl(t.templateType,''),t.fileType,t.fileShowName,to_char(t.time,'yyyy/mm/dd hh24:mi:ss'),f.staffName,nvl(t.sysSet,'01'),nvl(p.templateTypeName,''),t.createrID,nvl(t.temptId,'') from DT_OA_DOCUMENT_TEMPLATE  t left join dt_hr_Staff f on  t.createrID = f.staffID  left join DT_OA_TEMPLATE_TYPE p on t.temptId = p.temptId where ";

        if (companyID != null && !companyID.equals("")) {
            sql += "(t.companyId = ? or t.sysSet = ?)";
            params.add(companyID);
        } else {
            sql += "((t.createrID = ? and t.companyId is null) or t.sysSet = ?)";
            params.add(staffID);

        }

        params.add("00");
        if (parameter != null && !parameter.equals("")) {
            sql += " and t.fileShowName like ?";
            params.add("%" + parameter + "%");
        }

        if (fileType != null && !fileType.equals("")) {
            sql += " and t.fileType = ?";
            params.add(fileType);
        }
        if (temptId != null && !temptId.equals("")) {
            sql += " and t.temptId = ?";
            params.add(temptId);
        }
        if (module != null && !module.equals("")) {
            sql += " and t.module = ?";
            params.add(module);
        }
        sql += " order by  t.seq asc";

        List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql, params.toArray());


        return list;
    }

    /**
     * 添加模板分类
     *
     * @return
     */
    public void addDocTempType(TemplateType templateType, String companyID, String staffID) {
        Staff staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffID});

        if (templateType.getTemptId() == null || templateType.getTemptId().equals("")) {
            templateType.setTemptId(serverService.getServerID("temptid"));
            templateType.setCompanyId(companyID);
            templateType.setCreaterID(staffID);
            templateType.setCreaterName(staff.getStaffName());
            templateType.setTime(new Date());
            templateType.setSeq(0);
            if (templateType.getSysSet() == null || templateType.getSysSet().equals("")) {
                templateType.setSysSet("01");
            }
            beandao.save(templateType);
        } else {
            TemplateType templateType1 = (TemplateType) beandao.getBeanByHqlAndParams("from TemplateType where temptId = ?", new Object[]{templateType.getTemptId()});
            if (templateType.getSysSet() == null || templateType.getSysSet().equals("")) {
                templateType1.setSysSet("01");
            } else {
                templateType1.setSysSet(templateType.getSysSet());
            }


            templateType1.setTemplateTypeName(templateType.getTemplateTypeName());
            templateType1.setCreaterID(staffID);
            templateType1.setCreaterName(staff.getStaffName());
            templateType1.setTime(new Date());
            beandao.update(templateType1);
        }


    }


    /**
     * 添加模板
     *
     * @return
     */
    public void addDocTemp(String companyID, DocumentTemplate documentTemplate, String staffID) {
        if (documentTemplate.getTemplateId() != null
                && !documentTemplate.getTemplateId().equals("")) {
            String hql = "from DocumentTemplate where templateId = ?";
            DocumentTemplate dt = (DocumentTemplate) baseBeanService
                    .getBeanByHqlAndParams(hql, new Object[]{documentTemplate
                            .getTemplateId()});

            dt.setFileShowName(documentTemplate.getFileShowName());
            //     dt.setReceiptType(documentTemplate.getReceiptType());
            dt.setTemptId(documentTemplate.getTemptId());
            dt.setSysSet(documentTemplate.getSysSet());
            dt.setTime(new Date());
            dt.setContractTypeName(documentTemplate.getContractTypeName());
            dt.setContractType(documentTemplate.getContractType());
            baseBeanService.update(dt);
        } else {

            documentTemplate.setTemplateId(serverService.getServerID("temp"));
            documentTemplate.setCreaterID(staffID);
            documentTemplate.setTime(new Date());
            documentTemplate.setCompanyId(companyID);
            String templatePath = documentTemplate.getTemplatePath();
            documentTemplate.setExt(templatePath.substring(templatePath
                    .lastIndexOf(".") + 1));

            documentTemplate.setFileSaveName(templatePath
                    .substring(templatePath.lastIndexOf("/") + 1));
            baseBeanService.save(documentTemplate);
        }


    }

    /**
     * 获取模板分类
     *
     * @return
     */
    public PageForm getDocTempTypeList(int pageNumber, int pageSize, String companyID, String staffID, String parameter, String module) {


        List<Object> params = new ArrayList<Object>();
        String hql = "from TemplateType where ";


        if (companyID != null && !companyID.equals("")) {
            hql += "(companyId = ? or sysSet = ?)";
            params.add(companyID);
        } else {
            hql += "((createrID = ? and companyId is null) or sysSet = ?)";
            params.add(staffID);

        }


        params.add("00");
        if (parameter != null && !parameter.equals("")) {
            hql += " and templateTypeName like ?";
            params.add("%" + parameter + "%");
        }
        if (module != null && !module.equals("")) {
            hql += " and module = ?";
            params.add(module);
        }

        hql += " order by sysSet desc,time desc";

        PageForm pageForm = baseBeanService.getPageForm(pageNumber, pageSize, hql, params.toArray());

        return pageForm;
    }

    /**
     * 获取模板分类不分页
     *
     * @return
     */
    public List<BaseBean> getDocTempTypeLists(String parentId, String companyID, String staffID, String parameter, String module) {


        List<Object> params = new ArrayList<Object>();
        String hql = "from TemplateType where ";

        if (parentId != null && !parentId.equals("")) {
//            if(parentId.equals("main")){
//                //查询共享分类
//
//                hql += " sysSet = ?";
//                params.add("00");
//
//
//                hql += " and (parentId is null or parentId = ?)";
//                params.add(parentId);
//            }else if(parentId.equals("me")){
//                hql += " sysSet = ?";
//                params.add("01");
//
//                hql += " and (parentId is null or parentId = ?)";
//                params.add(parentId);
//            }else{
//                hql += " parentId = ?";
//                params.add(parentId);
//            }
            hql += " parentId = ?";
            params.add(parentId);

        } else {
            hql += " parentId is null";
        }

        if (!parentId.equals("main")) {
            String hqls = "from TemplateType where temptId = ? ";
            TemplateType templateType = (TemplateType) beandao.getBeanByHqlAndParams(hqls, new Object[]{parentId});
            if (templateType != null && templateType.getSysSet().equals("00")) {

            } else {
                if (companyID != null && !companyID.equals("")) {

                    //   if (!"company201009046vxdyzy4wg0000000025".equals(companyID) && !"company201009046vxdyzy4wg0000000065".equals(companyID)) {
                    hql += " and companyId = ?";
                    params.add(companyID);
                    //   }

                } else {
                    hql += " and createrID = ? and companyId is null and sysSet = ? and module=?";
                    params.add(staffID);
                    params.add("01");
                    params.add(parentId);
                }
            }

        }

        if (module != null && !module.equals("")) {


            hql += " and module = ?";

            params.add(module);
        }


        if (parameter != null && !parameter.equals("")) {
            hql += " and templateTypeName like ?";
            params.add("%" + parameter + "%");
        }


        hql += " order by seq asc desc";

        List<BaseBean> list = beandao.getListBeanByHqlAndParams(hql, params.toArray());

        return list;

    }

    /**
     * 保存模板排序
     */
    public void saveTempSort(String childrenID) {

        String[] ids = childrenID.split("_");
        String hql = "";
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            hql += "update DocumentTemplate set seq = " + (i + 1) + " where templateId = '" + id + "'_";
        }
        String[] hqls = hql.substring(0, hql.length() - 1).split("_");
        baseBeanService.saveBeansListAndexecuteHqlsByParams(null, hqls, null);

    }

    /**
     * 保存分类排序
     */
    public void saveCateSort(String childrenID) {

        String[] ids = childrenID.split("_");
        String hql = "";
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            hql += "update TemplateType set seq = " + (i + 1) + " where temptId = '" + id + "'_";
        }
        String[] hqls = hql.substring(0, hql.length() - 1).split("_");
        baseBeanService.saveBeansListAndexecuteHqlsByParams(null, hqls, null);

    }

    /**
     * 删除模板分类
     *
     * @return
     */
    public String deleteTempType(String tempId) {
        String hql = "from DocumentTemplate  where temptId = ?";
        List<BaseBean> list = beandao.getListBeanByHqlAndParams(hql, new Object[]{tempId});

        if (list.size() > 0) {
            return "1";
        }
        String hqldel = "delete from TemplateType where temptId = ?";

        baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hqldel}, new Object[]{tempId});
        return "0";
    }

    /**
     * 删除模板
     *
     * @param templateId
     * @return
     */
    public String deleteTemp(String templateId, String realpath) {
        String hqldoc = "from Document where specificTemplate = ?";
        List<BaseBean> list = (List<BaseBean>) baseBeanService
                .getListBeanByHqlAndParams(hqldoc, new Object[]{templateId});
        DocumentTemplate documentTemplate = null;

        if (list.size() == 0) {
            String hql = "from DocumentTemplate where templateId = ?";
            documentTemplate = (DocumentTemplate) baseBeanService
                    .getBeanByHqlAndParams(hql, new Object[]{templateId});

            String templatePath = documentTemplate.getTemplatePath();
            String templateFullPath = realpath + templatePath;
            // 删除具体文件
            zOfficeService.deleteOffice(templateFullPath);
            // 删除 documentTemplate表记录

            baseBeanService.deleteBeanByKey(DocumentTemplate.class,
                    documentTemplate.getKey());
        } else {

            return "1";
        }

        return "0";
    }


    /**
     * 重新发起签约
     *
     * @param docId
     * @param applyNo
     * @param sealerID
     * @param realpath
     * @param receiveID
     * @param receiveComID
     * @param tel
     * @return
     */

    public String repeatApplySign(String docId, String applyNo, String sealerID, String realpath, String receiveID, String receiveOrgID, String receiveComID, String tel) {
        String hqlc = "from DocumentSealer where docId = ? and applyNo = ? and sealerID = ? and (((state is null or state ='00')) or state = '01')";


        DocumentSealer recer = (DocumentSealer) baseBeanService.getBeanByHqlAndParams(hqlc, new Object[]{docId, applyNo, receiveID});
        DocumentSealer sealer = (DocumentSealer) baseBeanService.getBeanByHqlAndParams(hqlc, new Object[]{docId, applyNo, sealerID});


        if (recer != null || sealer == null) {//转交盖章的人盖过了，盖过了还要再盖就要重新发起||转交之前的当前盖章人没有盖章就转交就要重新发起


            //说明当前盖章人没盖章就转交他人了，不允许，只能重新发起一份签约。
            String hql = "from Staff f where f.staffID = ?";
            Staff staff = (Staff) beandao.getBeanByHqlAndParams(hql, new Object[]{receiveID});

            SignatoryReq sReq = new SignatoryReq();
            String aplNo = "";


            List<BaseBean> stamplist = null;
            String companyIDofSealer = receiveComID;
            if (companyIDofSealer != null && !companyIDofSealer.equals("")) {
                if ("15810799888".equals(tel)) {
                    companyIDofSealer = "company201009046vxdyzy4wg0000000025";
                    stamplist = beandao.getListBeanByHqlAndParams("from EnterpriseStamp where companyID = ? and signId is not null", new Object[]{companyIDofSealer});

                } else {
                    stamplist = beandao.getListBeanByHqlAndParams("from EnterpriseStamp where companyID = ? and responsibleID = ? and signId is not null", new Object[]{receiveID, receiveComID});

                }
            }


            if (stamplist != null && stamplist.size() > 0) {
                //有印章，按公司来

                String sql1 = "select b.business_license_number,r.emailOrMobile,c.companyName from dtContactCompany c left join DT_ccom_com m on c.ccompanyID = m.ccompany_Id  left join dt_sft_ApplyParam p on c.applyid = p.applyid left join dt_sft_BusinessLicenseInfo b on p.bliKey = b.bliKey left join dt_sft_ApplyResult r on r.out_request_no = p.out_request_no where m.compnay_id = ?";
                Object obj = baseBeanService.getObjectBySqlAndParams(sql1, new Object[]{companyIDofSealer});
                Object[] objs = (Object[]) obj;

                staff.setStaffName(objs[2].toString());
                staff.setStaffIdentityCard(objs[0].toString());

                sReq.setEmail(objs[1].toString());
                sReq.setIdentityType(11);//	身份类型:1身份证 11营业执照,
            } else {

                sReq.setIdentityType(1);//	身份类型:1身份证 11营业执照,

            }


            sReq.setFullName(staff.getStaffName());
            sReq.setIdentityCard(staff.getStaffIdentityCard());//个人传身份证号，企业传营业执照号/统一社会信用代码号
            sReq.setMobile(staff.getReference());
            Document doc = (Document) baseBeanService
                    .getBeanByHqlAndParams("from Document where docId = ?", new Object[]{docId});

            aplNo = JzqAPI.applySignUrl(realpath + doc.getPdfUrl(), doc.getTitle(), sReq);
            doc.setApplyNo(aplNo);
            beandao.update(doc);


            DocumentSealer documentSealer = new DocumentSealer();
            documentSealer.setId(serverService.getServerID("id"));
            documentSealer.setCompanyIDofSealer(receiveComID);
            documentSealer.setDeptIDofSealer(receiveOrgID);
            documentSealer.setSealerID(receiveID);
            documentSealer.setDocId(docId);
            documentSealer.setState("00");
            documentSealer.setApplyNo(applyNo);
            documentSealer.setIdentityType(sReq.getIdentityType() + "");
            documentSealer.setIdentityCard(sReq.getIdentityCard());
            documentSealer.setFullName(sReq.getFullName());
            beandao.save(documentSealer);

        }
        return "";
    }

    /**
     * 下载视频证据
     *
     * @param docId
     * @return
     */
    public String downVideoURL(String docId, String realpath) {
        String videoUrl = "";
        Document doc = (Document) beandao.getBeanByHqlAndParams("from Document where docId = ?", new Object[]{docId});
        if (doc.getApplyNo() != null && !doc.getApplyNo().equals("")) {
            DocumentSealer sealer = (DocumentSealer) beandao.getBeanByHqlAndParams("from DocumentSealer where docId = ? and applyNo = ? and sealerID = ? and state = '01'", new Object[]{doc.getDocId(), doc.getApplyNo(), doc.getSealerID()});
            if (sealer != null) {
                if (sealer.getVideoURL() != null && !sealer.getVideoURL().equals("")) {
                    videoUrl = sealer.getVideoURL();
                } else {
                    String loadUrl = JzqAPI.downloadFaceVideo(doc.getApplyNo(), sealer.getFullName(), sealer.getIdentityCard(), sealer.getIdentityType());

                    if (loadUrl != null && !loadUrl.equals("")) {
                        videoUrl = saveVideoUrl(loadUrl, (sealer.getCompanyIDofSealer() != null && sealer.getCompanyIDofSealer().equals("")) ? sealer.getCompanyIDofSealer() : sealer.getSealerID(), realpath);
                        sealer.setVideoURL(videoUrl);
                        beandao.update(sealer);
                    }
                }
            } else if ("00".equals(doc.getScene())) {

                TemplateParams templateParams = (TemplateParams) beandao.getBeanByHqlAndParams("from TemplateParams where docID = ?", new Object[]{doc.getDocId()});

                videoUrl = JzqAPI.downloadFaceVideo(doc.getApplyNo(), templateParams.getStaffName(), templateParams.getStaffIdentityCard(), sealer.getIdentityType());


            }


        }

        return videoUrl;
    }

    /**
     * 添加公文邀请记录
     */
    public String addInventRecord(String docId, String status, String telphone, String staffID) {
        String hql = "from DocInviteRecord where docId = ? and telphone = ?";
        String[] ar = telphone.split(",");
        String returns = "0";
        List<BaseBean> beans = new ArrayList<BaseBean>();
        for (int i = 0; i < ar.length; i++) {

            String[] info = ar[i].split("-");
            String tel = "";
            String bystaffID = "";
            if (info != null && info.length > 1) {
                bystaffID = info[0];

                    tel = info[1];

            } else {
                tel = ar[i];
            }

            DocInviteRecord record = (DocInviteRecord) beandao.getBeanByHqlAndParams(hql, new Object[]{docId, tel});
            if (record == null) {
                DocInviteRecord record1 = new DocInviteRecord();
                record1.setDocId(docId);
                record1.setOprState("00");
                record1.setId(serverService.getServerID("rid"));
                record1.setInventDate(new Date());
                record1.setStatus(status);
                record1.setTelphone(tel);
                record1.setBystaffID(bystaffID);
                record1.setSeqno(WeChatUtils.getNonceStr());

                Staff staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffID});
                String staffName = staff.getStaffName();

                record1.setSccid(staff.getSccid());
                record1.setStaffID(staff.getStaffID());
                beans.add(record1);

                Document doc = (Document) beandao.getBeanByHqlAndParams("from Document where docId = ?", new Object[]{docId});
                record1.setDocstatus(doc.getStatus());
                if(!"read".equals(status)){
                    doc.setStatus("y");
                }



                String title = doc.getTitle();
                String seqno = record1.getSeqno();
                String content = staffName + "同志给您发送了一条公文,(标题为《" + title + "》),至→我的→办公通→";
                if (status.equals("p")) {
                    content += "拟稿→收件";
                } else if (status.equals("A")) {
                    content += "审批→待审批";
                } else if (status.equals("approve") || status.equals("transfer")) {
                    content += "盖章→待盖章";
                } else if (status.equals("approve") || status.equals("seal")) {
                    content += "盖章→待盖章";
                } else if (status.equals("publish")) {
                    content += "分发→待分发";
                } else if (status.equals("read")) {
                    content += "阅读→待阅读";
                    doc.setStatus(doc.getStatus());
                }
                beans.add(doc);


                content += "请贵者→注册→登录→地址为 http://www.impf2010.com/page/WFJClient/pc/pc_login.jsp?vs=" + seqno;


                String reStr = "";
                try {
                    String d = ar[i].replaceAll("[^0-9-]+", " ").trim();
                    String t = d.replaceAll("[ ]+", ","); // 第二个参数是短信发送需要的电话号码分割url。etc.136855,11255

                    mobileMessage.setMessage(content);
                    mobileMessage.setMobiles(t);
                    reStr = mobileMessage.sendMsg();
                    System.out.println(reStr);
                } catch (IOException e) {

                    e.printStackTrace();
                }


            } else {
                returns = "1";
            }
        }
        beandao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        return returns;
    }

    /**
     * 注册的时候接收文件
     *
     * @param telphone
     * @return
     */
    public String receiveDoc(String telphone) {

        String hql = "from DocInviteRecord where telphone = ? and oprState = ? order by inventDate";
        List<BaseBean> list = beandao.getListBeanByHqlAndParams(hql, new Object[]{telphone, "00"});
        if (list.size() > 0) {
            TEshopCusCom tEshopCusCom = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 and acquiesce = '01'", new Object[]{telphone});
            List<BaseBean> beans = new ArrayList<BaseBean>();
            for (int i = 0; i < list.size(); i++) {
                DocInviteRecord record = (DocInviteRecord) list.get(i);
                String status = record.getStatus();
                String docId = record.getDocId();
                Document doc = null;

                doc = (Document) baseBeanService.getBeanByHqlAndParams("from Document where docId=?",
                        new Object[]{docId});
                doc.setStatus(record.getDocstatus());
                if (status.equals("p")) {

                    doc.setPassTime(new Date());
                    doc.setUpdateTime(new Date());

                    doc.setFromMember(record.getStaffID());

                    String subscriberID2 = doc.getSubscriberID2();
                    String deptIDofSubscriber2 = doc.getDeptIDofSubscriber2();
                    String companyIDofSubscriber2 = doc.getCompanyIDofSubscriber2();
                    doc.setSubscriberID2(tEshopCusCom.getStaffid());
                    doc.setDeptIDofSubscriber2(null);
                    doc.setCompanyIDofSubscriber2(null);
                    doc.setReceiverID(null);
                    doc.setReceiverDeptID(null);
                    doc.setReceiverCompanyID(null);
                    beans.add(doc);


                    // 增加已传阅记录
                    DocumentPass dp = new DocumentPass();
                    dp.setPassId(serverService.getServerID("passId"));
                    dp.setDocId(doc.getDocId());
                    dp.setPassTime(new Date());

                    dp.setSubscriber2(record.getStaffID());
                    if (subscriberID2 != null && !subscriberID2.equals("")) {
                        dp.setDeptOfsub2(deptIDofSubscriber2);
                        dp.setCompanyIDOfsub2(companyIDofSubscriber2);
                    } else {
                        dp.setDeptOfsub2(doc.getOrganizationID());
                        dp.setCompanyIDOfsub2(doc.getCompanyID());
                    }
                    dp.setToSubscriber2(tEshopCusCom.getStaffid());
                    dp.setDeptOftoSub2(null);
                    dp.setCompanyIDOftosub2(null);
                    beans.add(dp);


                    baseBeanService.update(doc);

                } else if (status.equals("A")) {
                    doc.setSubscriberID(tEshopCusCom.getStaffid());
                    doc.setDeptIDofSubscriber(null);
                    doc.setCompanyIDofSubscriber(null);
                    doc.setReceiverID(tEshopCusCom.getStaffid());
                    doc.setReceiverDeptID(null);
                    doc.setReceiverCompanyID(null);
                    CAccount account = new CAccount();
                    account.setStaffID(record.getStaffID());
                    docFlowService.createDocument(doc, account); // 第一次发送


                    // 添加对公文的操作记录
                    String hqlstaff = "from Staff where staffID = ?";

                    Staff receiver = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff, new Object[]{tEshopCusCom.getStaffid()});

                    docCommonService.addTrackRecord(doc.getDocId(), record.getStaffID(), doc.getOrganizationID(),
                            doc.getCompanyID(), "提交审批至" + receiver.getStaffName()
                                    + "(" + receiver.getStaffCode() + ")");

                } else if (status.equals("approve") || status.equals("transfer")) {
                    doc.setReceiverID(tEshopCusCom.getStaffid());
                    doc.setReceiverDeptID(null);
                    doc.setReceiverCompanyID(null);
                    docFlowService.examineDocument(doc, status);
                    String content = "审批通过转至盖章";
                    if (status.equals("transfer")) {
                        content = "审批通过转至他人审批";
                    }
                    docCommonService.addTrackRecord(doc.getDocId(), doc.getSubscriberID()
                            , doc.getDeptIDofSubscriber(),
                            doc.getCompanyIDofSubscriber(), content);

                } else if (status.equals("publish") || status.equals("seal")) {
                    doc.setReceiverID(tEshopCusCom.getStaffid());
                    doc.setReceiverDeptID(null);
                    doc.setReceiverCompanyID(null);
                    int result = docFlowService.sealDocument(doc, status);

                    String content = "转至分发人";
                    String checkOrgID = doc.getDeptIDofSealer();
                    String checkComID = doc.getComNameofSealer();
                    if (status.equals("seal")) {
                        content = "转交盖章";
                    }
                    docCommonService.addTrackRecord(doc.getDocId(), record.getStaffID(), checkOrgID,
                            checkComID, content);
                } else if (status.equals("read")) {
                    doc.setReceiverID(tEshopCusCom.getStaffid());
                    doc.setReceiverDeptID(null);
                    doc.setReceiverCompanyID(null);
                    if (doc.getStatus().equals("P")) {
                        docFlowService.publishDocument(doc, tEshopCusCom.getStaffid() + "--");
                    } else {
                        docFlowService.rePublishDocument(doc, null,
                                tEshopCusCom.getStaffid() + "--");
                    }


                }
                record.setOprState("01");
                record.setReceiveDate(new Date());
                beandao.update(record);
            }

        }

        return null;
    }


    /**
     * 获取档案夹不分页
     *
     * @return
     */
    public List<BaseBean> getArchiveTypeLists(String parentId, String companyID, String staffID, String parameter, String module) {


        List<Object> params = new ArrayList<Object>();
        String hql = "from ArchiveDocType where parentId = ? and createrID = ? and module=?";

        params.add(parentId);
        params.add(staffID);
        params.add(module);

        if (companyID != null && !companyID.equals("")) {
            hql += " and companyID  = ? ";
            params.add(companyID);
        } else {
            hql += " and companyID is null ";
        }


        hql += " order by seq asc desc";

        List<BaseBean> list = beandao.getListBeanByHqlAndParams(hql, params.toArray());

        return list;

    }


    /**
     * 添加档案盒
     *
     * @return
     */
    public void addArchiveType(ArchiveDocType archiveDoc, String companyID, String staffID) {
        Staff staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffID});

        if (archiveDoc.getAdtId() == null || archiveDoc.getAdtId().equals("")) {
            archiveDoc.setAdtId(serverService.getServerID("adtId"));
            if (companyID != null && !companyID.equals("")) {
                Company company = (Company) beandao.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{companyID});
                archiveDoc.setCompanyID(companyID);
                archiveDoc.setCompanyName(company.getCompanyName());
            }

            archiveDoc.setCreaterID(staffID);
            archiveDoc.setCreaterName(staff.getStaffName());
            archiveDoc.setTime(new Date());
            archiveDoc.setSeq(0);

            int arnum = beandao.getConutByBySqlAndParams("select max(to_number(arnum)) from DT_OA_ArchiveDocType", null);
            DecimalFormat form = new DecimalFormat("0000");
            String ss = form.format(arnum + 1);
            archiveDoc.setArnum(ss);
            archiveDoc.setBarCode(Utilities.getDateString(new Date(), "yyyyMMdd") + ss);
            beandao.save(archiveDoc);

            //添加条码扫描数据

            ScanBarCode scanBarCode = new ScanBarCode();
            scanBarCode.setBarcode(archiveDoc.getBarCode());
            scanBarCode.setId(serverService.getServerID("scanid"));
            scanBarCode.setUrls("ea/androiddoc/ea_scanArchiveBox.jspa");
            beandao.save(scanBarCode);

        } else {
            ArchiveDocType archiveType1 = (ArchiveDocType) beandao.getBeanByHqlAndParams("from ArchiveDocType where adtId = ?", new Object[]{archiveDoc.getAdtId()});


            archiveType1.setTypeName(archiveDoc.getTypeName());
            archiveType1.setCreaterID(staffID);
            archiveType1.setCreaterName(staff.getStaffName());
            archiveType1.setTime(new Date());
            beandao.update(archiveType1);
        }


    }

    /**
     * 删除档案夹
     *
     * @return
     */
    public String deleteArchiveType(String adtId) {
        String hql = "from ArchiveDocRelate  where adtId = ?";
        List<BaseBean> list = beandao.getListBeanByHqlAndParams(hql, new Object[]{adtId});

        if (list.size() > 0) {
            return "1";
        }
        String hqldel = "delete from ArchiveDocType where adtId = ?";

        baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hqldel}, new Object[]{adtId});
        return "0";
    }

    /**
     * 个人归档
     *
     * @param docId
     * @param staffID
     * @param companyID
     * @param adtId
     * @return
     */
    public String archiveDoc(String docId, String staffID, String companyID, String adtId) {
        ArchiveDocRelate relate = null;
        if (companyID == null || companyID.equals("")) {
            relate = (ArchiveDocRelate) beandao.getBeanByHqlAndParams("from ArchiveDocRelate where docId = ? and createrID = ? and companyID is null", new String[]{docId, staffID});

        } else {
            relate = (ArchiveDocRelate) beandao.getBeanByHqlAndParams("from ArchiveDocRelate where docId = ? and createrID = ? and companyID = ?", new String[]{docId, staffID, companyID});

        }
        if (relate != null) {
            return "1";
        }
        ArchiveDocRelate archiveDocRelate = new ArchiveDocRelate();
        archiveDocRelate.setAdrId(serverService.getServerID("adrid"));
        archiveDocRelate.setCreaterID(staffID);
        archiveDocRelate.setTime(new Date());
        archiveDocRelate.setSeq(0);
        archiveDocRelate.setAdtId(adtId);
        archiveDocRelate.setDocId(docId);
        archiveDocRelate.setCompanyID(companyID);
        Staff staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffID});
        archiveDocRelate.setCreaterName(staff.getStaffName());
        beandao.save(archiveDocRelate);

        return "0";
    }

    /**
     * 获取归档文件
     *
     * @param companyID
     * @param parameter
     * @return
     */
    public List<BaseBean> getArchiveTree(String companyID, String staffID, String parameter, String adtId, String module) {
        List<Object> params = new ArrayList<Object>();


        String sql = "select t.docId,t.pdfUrl,t.title,to_char(p.time,'yyyy/mm/dd hh24:mi:ss'),f.staffName,nvl(a.typeName,''),p.createrID,nvl(p.adtId,''),t.module from DT_OA_DOCUMENT  t left join dt_hr_Staff f on  t.drafterID = f.staffID   left join DT_OA_ArchiveDocRelate p on t.docId = p.docId left join DT_OA_ArchiveDocType a  on  a.adtId = p.adtId where ";

        sql += " p.createrID = ?";
        params.add(staffID);

        if (companyID == null || companyID.equals("")) {
            sql += " and p.companyID is null";
        } else {
            sql += " and p.companyID = ?";
            params.add(companyID);

        }
        if (parameter != null && !parameter.equals("")) {
            sql += " and t.title like ?";
            params.add("%" + parameter + "%");
        }


        if (adtId != null && !adtId.equals("")) {
            sql += " and p.adtId = ?";
            params.add(adtId);
        }
        if (module != null && !module.equals("")) {
            sql += " and t.module = ?";
            params.add(module);
        }
        sql += " order by  p.seq asc";

        List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql, params.toArray());


        return list;
    }


    /**
     * 获取已归档分页
     *
     * @param companyID
     * @param parameter
     * @return
     */
    public PageForm getPageFormArchive(int pageNumber, int pageSize, String companyID, String staffID, String parameter, String adtId, String module) {
        List<Object> params = new ArrayList<Object>();


        String sql = "select t.docId,t.pdfUrl,t.title,to_char(p.time,'yyyy/mm/dd hh24:mi:ss'),f.staffName,nvl(a.typeName,''),p.createrID,nvl(p.adtId,''),t.module from DT_OA_DOCUMENT  t left join dt_hr_Staff f on  t.drafterID = f.staffID   left join DT_OA_ArchiveDocRelate p on t.docId = p.docId left join DT_OA_ArchiveDocType a  on  a.adtId = p.adtId where ";
        if (adtId != null && !adtId.equals("")) {
            sql += "  p.adtId = ?";
            params.add(adtId);
        } else {
            sql += " p.createrID = ?";
            params.add(staffID);
            if (companyID == null || companyID.equals("")) {
                sql += " and p.companyID is null";
            } else {
                sql += " and  p.companyID = ?";
                params.add(companyID);
            }

            if (parameter != null && !parameter.equals("")) {
                sql += " and t.title like ?";
                params.add("%" + parameter + "%");
            }


            if (module != null && !module.equals("")) {
                sql += " and t.module = ?";
                params.add(module);
            }
        }
        sql += " order by  p.seq asc";
        PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql,
                "select count(*) from (" + sql + ")", params.toArray());

        return pageForm;
    }

    /**
     * 查看详情
     *
     * @param adtId
     * @return
     */
    public ArchiveDocType getArchiveDeatil(String adtId) {
        ArchiveDocType archiveDoc = null;
        if (adtId.indexOf("adtId") != -1) {
            archiveDoc = (ArchiveDocType) beandao.getBeanByHqlAndParams("from ArchiveDocType where adtId = ?", new Object[]{adtId});

        } else {
            archiveDoc = (ArchiveDocType) beandao.getBeanByHqlAndParams("from ArchiveDocType where barCode = ?", new Object[]{adtId});

        }
        return archiveDoc;
    }

    /**
     * 转移
     *
     * @param docId
     * @param module
     */
    public void transferDoc(String docId, String module) {

        String hql = "update Document set module = ? where docId = ?";
        List<Object[]> paramlist = new ArrayList<Object[]>();
        paramlist.add(new Object[]{module, docId});
        baseBeanService.executeHqlsByParamsList(null, new String[]{hql}, paramlist);

    }

    /**
     * 添加发送人记录
     *
     * @param staffID
     * @param companyID
     * @param sstaffID
     * @param sorgID
     * @param scompanyID
     * @return
     */
    public String addRecentContact(String staffID, String companyID, String sstaffID, String sorgID, String scompanyID, String source) {

        String hql = "from RecentContact where (sstaffID = ? or telphone = ?) and staffID = ? and source = ?";
        List<BaseBean> list = beandao.getListBeanByHqlAndParams(hql, new Object[]{sstaffID,scompanyID,staffID,source});
        String id="";
        int r = 0;
        if (list.size() > 0) {
            for (BaseBean rc : list) {
                RecentContact recentContact = (RecentContact) rc;
                if (source.equals("03")) {
                    //说明是个人，只要查出有没有个人的就可以了

                    //有个人记录
                    r = 0;
                    //说明有 个人只修改时间就可以了
                    recentContact.setCreateDate(new Date());
                    beandao.update(recentContact);
                    id = recentContact.getId();
                    break;

                } else {
                    //不是个人有公司和部门找到部门相同的
                    if (recentContact.getSorgID() != null && recentContact.getSorgID().equals(sorgID)) {
                        r = 0;
                        //说明有 个人只修改时间就可以了
                        recentContact.setCreateDate(new Date());
                        beandao.update(recentContact);
                        id = recentContact.getId();
                        break;
                    } else {
                        r = 1;
                    }

                }


            }

        } else {
            r = 1;//没有
        }

        if (r == 1) {
            Staff staff = null;
            RecentContact recentContact = new RecentContact();
            if (sstaffID != null && !sstaffID.equals("")) {
                staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{sstaffID});
                recentContact.setSstaffID(sstaffID);
                recentContact.setStaffName(staff.getStaffName());
                recentContact.setScompanyID(scompanyID);


                if (sorgID != null && !sorgID.equals("")) {
                    COrganization org = (COrganization) beandao.getBeanByHqlAndParams("from COrganization where organizationID = ?", new Object[]{sorgID});
                    recentContact.setSorgID(sorgID);
                    recentContact.setSorgName(org.getOrganizationName());
                }

                if (scompanyID != null && !scompanyID.equals("")) {
                    Company company = (Company) beandao.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{scompanyID});
                    recentContact.setScompanyID(scompanyID);
                    recentContact.setScompanyName(company.getCompanyName());
                }

            } else {
                recentContact.setStaffName("被邀请");
                recentContact.setTelphone(scompanyID);
                recentContact.setScompanyID("");
            }

            recentContact.setId(serverService.getServerID("rcid"));
            recentContact.setCompanyID(companyID);
            recentContact.setCreateDate(new Date());


            recentContact.setStaffID(staffID);




            recentContact.setSource(source);
            beandao.save(recentContact);
            id = recentContact.getId();


        }
        return id;
    }

    //更新状态
    public void updateRecentContact(String id,String oprState){

        String hql = "update RecentContact set oprState = ? where id = ?";
        beandao.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql},new Object[]{oprState,id});

    }

    /**
     * 获取最近联系人
     *
     * @param staffID
     * @return
     */
    public List<Object> getRecentContactInfo(String staffID, String source) {
        List<Object> list = beandao.getListObjectBySqlAndParams("select nvl(r.sstaffid,m.staffid),r.sorgID,r.scompanyID, nvl(k.staffname,r.staffname),r.sorgName,r.scompanyName,f.headimage,nvl(f.reference,r.telphone),nvl(m.sccid,'1') from DT_OA_RecentContact r left join dt_hr_staff f on r.sstaffID = f.staffID left join t_Eshop_Cuscom m on r.telphone = m.account  left join dt_hr_staff k on m.staffid = k.staffid where r.staffID = ? and r.source = ? and rownum<=20 order by r.createDate desc ", new Object[]{staffID, source});
        return list;
    }

    /**
     *
     *
     * @param staffID
     * @param oprState 根据操作查询
     * @return
     */
    public  List<Object> getRecentInfoByOpr(String staffID,String oprState){
        List<Object> list = beandao.getListObjectBySqlAndParams("select nvl(r.sstaffid,m.staffid),r.sorgID,r.scompanyID, nvl(k.staffname,r.staffname),r.sorgName,r.scompanyName,f.headimage,nvl(f.reference,r.telphone),nvl(m.sccid,'1') from DT_OA_RecentContact r left join dt_hr_staff f on r.sstaffID = f.staffID left join t_Eshop_Cuscom m on r.telphone = m.account  left join dt_hr_staff k on m.staffid = k.staffid where r.staffID = ? and r.oprState = ? and rownum<=20 order by r.createDate desc ", new Object[]{staffID, oprState});
        return list;
    }
    /**
     * 获取学员报名信息
     *
     * @param docId
     * @return
     */
    public CashierBills checkInfo(String docId) {


        CashierBills cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills c where c.cashierBillsID=(select t.cashierBillsID  from  TemplateParams t where t.docID = ?)", new Object[]{docId});


        return cashierBills;
    }

    /**
     * 修改身份信息
     *
     * @param noviceName
     * @param noviceCode
     * @param cashierBillsID
     */
    public void updateInfo(String noviceName, String noviceCode, String reference, String cashierBillsID) {


        CashierBills cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where cashierBillsID=?", new Object[]{cashierBillsID});
        cashierBills.setCtUserName(noviceName);
        cashierBills.setStaffIdentityCard(noviceCode);
        cashierBills.setReference(reference);
        baseBeanService.update(cashierBills);


        try {
            TemplateParams templateParams = (TemplateParams) baseBeanService.getBeanByHqlAndParams("from TemplateParams where cashierBillsID = ?", new Object[]{cashierBills.getCashierBillsID()});
            if (templateParams != null) {
                templateParams.setStaffIdentityCard(cashierBills.getStaffIdentityCard());
                templateParams.setStaffName(cashierBills.getCtUserName());
                templateParams.setReference(cashierBills.getReference());
                baseBeanService.update(templateParams);

            }


            Document t = (Document) beandao.getBeanByHqlAndParams("from Document where docId = ?", new Object[]{templateParams.getDocID()});
            t.setTitle(cashierBills.getCtUserName() + "学员协议");
            beandao.update(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * html转PDF返回路径
     */
    public DocumentFileAttach createAttach(DocRelateOther docRelateOther, String realPath, String basePath, String companyID) {
        DocumentFileAttach docFileAttach = new DocumentFileAttach();

        String savePath = "upload_files/" + companyID + "/web/document/" + Utilities.getDateString(new Date(), "yyyy-MM-dd");

        String fileName = UUID.randomUUID().toString().replaceAll("-", "");

        String outpdf = savePath + "/" + fileName + ".pdf";
        docFileAttach.setFilePath(outpdf);
        docFileAttach.setFileType("P");

        File fileLocation = new File(realPath + savePath);
        // 判断上传路径是否存在，如果不存在就创建
        if (!fileLocation.exists()) {
            fileLocation.mkdirs();

        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                Html2PdfUtil.html2Pdf(basePath + docRelateOther.getHtmlUrl(), outpdf, realPath);

            }
        }).start();

        docRelateOther.setId(serverService.getServerID("id"));
        docRelateOther.setStatus("00");
        docRelateOther.setCreateDate(new Date());
        beandao.update(docRelateOther);


        return docFileAttach;
    }


    /**
     * 做一些其余操作
     *
     * @param docRelateOther
     * @return
     */
    public void addOthers(DocRelateOther docRelateOther, String stage, Document document) {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        if (docRelateOther.getSource().equals("fs")) {
            String hql = "from CarManageAudit where cmaID = ?";
            String hqls = "from CarManage where carmID = ?";
            CarManageAudit carManageAudit = (CarManageAudit) beandao.getBeanByHqlAndParams(hql, new Object[]{docRelateOther.getIdValue()});
            CarManage carManage = (CarManage) beandao.getBeanByHqlAndParams(hqls, new Object[]{carManageAudit.getCarmID()});

            if (stage.equals("01")) {
                //提交阶段

                if (carManageAudit != null) {
                    carManageAudit.setAuditStatus("01");//提交审核
                    carManageAudit.setCreateDate(new Date());//提交审核时间
                }


                if (carManage != null) {
                    carManage.setAuditStatus("01");//已提交审核也就是审核中。。。
                }


            } else if (stage.equals("02")) {
                //审核通过阶段
                carManageAudit.setAuditStatus("02");
                carManageAudit.setAuditDate(new Date());
                carManageAudit.setSealID(document.getSealerID());
                carManage.setAuditStatus("02");//人工审核通过
                //通过要生成时间表timeCharge


                String hql1 = "from TimingCharging where carNumber = ? and siteId = ? and state='00'";
                TimingCharging timingCharging = (TimingCharging) beandao.getBeanByHqlAndParams(hql1, new Object[]{carManageAudit.getCarNumber(), carManageAudit.getSiteId()});
                if (timingCharging != null) {
                    timingCharging.setState("01");
                    beans.add(timingCharging);

                }
                TimingCharging tc = new TimingCharging();
                tc.setTcId(serverService.getServerID("tc"));
                tc.setSiteId(carManageAudit.getSiteId());
                tc.setJournalnum("");
                tc.setCarNumber(carManageAudit.getCarNumber());
                tc.setState("00");
                tc.setStartDate(carManageAudit.getIndate());
                tc.setEndDate(carManageAudit.getOutdate());
                tc.setRemark("人工审核通过");
                beans.add(tc);
                if (carManage.getStatus().equals("0")) {
                    carManage.setChargeState("06");
                    carManage.setChargeType("03");
                } else {
                    carManage.setChargeState1("06");

                }


            } else if (stage.equals("03")) {
                //审核失败阶段
                carManageAudit.setAuditStatus("03");
                carManageAudit.setAuditDate(new Date());
                carManageAudit.setSealID(document.getSubscriberID());
                carManage.setAuditStatus("03");//人工审核驳回
            }

            beans.add(carManageAudit);
            beans.add(carManage);

            beandao.executeSqlsByParmsList(beans, null, null);

        } else if (docRelateOther.getSource().equals("tc")) {
            if (stage.equals("01")) {
                //提交阶段
                String hql = "from FeeScale where feecID = ?";

                FeeScale feeScale = (FeeScale) beandao.getBeanByHqlAndParams(hql, new Object[]{docRelateOther.getIdValue()});
                if (feeScale != null) {
                    feeScale.setStartUsing("04");
                    beans.add(feeScale);
                }

                beandao.executeSqlsByParmsList(beans, null, null);


            }
        }
    }

    /**
     * 获取未注册用户
     *
     * @param pageNumber
     * @param pageSize
     * @param parameter
     * @param companyID
     * @return
     */
    public PageForm getWregMember(int pageNumber, int pageSize, String parameter, String companyID) {
        List<Object> params = new ArrayList<Object>();
        Company y = (Company) beandao.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{companyID});
        String gsn = y.getGroupCompanySn();
        params.add(gsn);
        String sql = "select s.staffid,nvl(s.realname, s.staffname),nvl(t.account, s.reference),s.headimage from dt_hr_staff s left join t_eshop_cuscom t on s.staffid = t.staffid left join t_Eshop_Cuscom tt on s.reference = tt.account where s.groupCompanySn = ?";
        if (parameter != null && !parameter.equals("")) {
            sql += " and (s.staffName like ? or s.reference = ?)";
            params.add("%" + parameter + "%");
            params.add(parameter);
        }
        sql += " and s.reference is not null and t.staffid is null and tt.account is null order by s.verifytime desc";
        PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql,
                "select count(*) from (" + sql + ")", params.toArray());


        return pageForm;


    }

    /**
     * 自己公司起草的公文
     * * @param companyID
     *
     * @return
     */
    public PageForm getAllFiles(int pageNumber, int pageSize, String companyID, String module, String parameter) {
        String hql = "from Document t where t.companyID  = ? and  t.title is not null and  t.docId not in(select c.docId from DocDelRecord c) ";

        List<Object> params = new ArrayList<Object>();
        params.add(companyID);
        if (module != null && !module.equals("")&& !module.equals("null")) {
            hql+=" and t.module = ?";
            params.add(module);
        }
        if (parameter != null && !parameter.equals("")) {
            hql+=" and t.title like ?";
            params.add("%"+parameter+"%");
        }

        hql+=" order by case when t.updateTime is null then 1 else 0 end,t.updateTime desc";
        PageForm pageForm = baseBeanService.getPageForm(pageNumber, pageSize, hql, params.toArray());


        return pageForm;
    }
}
