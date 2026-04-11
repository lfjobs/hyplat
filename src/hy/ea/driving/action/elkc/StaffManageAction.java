package hy.ea.driving.action.elkc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.DrivingSchool.TbJpStudentInfo;
import hy.ea.bo.DrivingSchool.TbJpTeacher;
import hy.ea.bo.Enroll;
import hy.ea.bo.finance.BenDis.DtOrderBillAdd;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.human.Staff;
import hy.ea.driving.service.StaffManageService;
import hy.ea.util.ImageCut;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Controller
@Scope("prototype")
public class StaffManageAction {
	private static final Logger logger = LoggerFactory.getLogger(StaffManageAction.class);

    @Resource
    private ServerService serverService;

    @Resource
    private BaseBeanService baseBeanService;

    @Resource
    private StaffManageService staffManageService;

    private String staffId;

    private TbJpStudentInfo tbJpStudentInfo;

    private Enroll enroll;

    private TbJpTeacher tbJpTeacher;

    private String brith;

    private String applyDate;

    private String companyName;

    private String fstdrilicdate;

    private String companyID;

    public String basicInfoPage(){
        return "basicInfo";
    }

    public String studentApplyInfoPage(){
//        HttpSession session = ServletActionContext.getRequest().getSession();
//        SessionWrap sw = SessionWrap.getInstance();
//        CAccount cAccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
//        Company company  = (Company)baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?",new Object[]{cAccount.getCompanyID()});
//        session.setAttribute("companyName",company.getCompanyName());
        CAccount caccount=(CAccount) baseBeanService.getBeanByHqlAndParams("from CAccount where staffid=?", new Object[]{staffId});
        Company company  = (Company)baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?",new Object[]{caccount.getCompanyID()});
//        Company company  = (Company)baseBeanService.getBeanByHqlAndParams("select y from Staff as s ,TEshopCusCom as c,Company as y where s.sccid= c.sccId and c.companyId = y.companyID and s.staffID  = ?",new Object[]{staffId});
        if(company!=null){
            companyName = company.getCompanyName();
        }
        enroll = (Enroll)baseBeanService.getBeanByHqlAndParams("from Enroll e where e.staffId = ?",new Object[]{staffId});
        tbJpStudentInfo = (TbJpStudentInfo)baseBeanService.getBeanByHqlAndParams("from TbJpStudentInfo st where st.staffId = ?",new Object[]{staffId});
        if(tbJpStudentInfo!=null){
            if(tbJpStudentInfo.getCompanyId() == null || tbJpStudentInfo.getCompanyId() == ""){
                tbJpStudentInfo.setCompanyId(company.getCompanyID());
                if(enroll!=null){
                    tbJpStudentInfo.setCompanyId(enroll.getCompanyID());
                    tbJpStudentInfo.setApplyDate(enroll.getEnrollDate());
                    tbJpStudentInfo.setTrainType(enroll.getLicenceType());
                }
            }
        }else {
            if(enroll!=null){
                tbJpStudentInfo.setCompanyId(enroll.getCompanyID());
                tbJpStudentInfo.setApplyDate(enroll.getEnrollDate());
                tbJpStudentInfo.setTrainType(enroll.getLicenceType());
            }
        }
        return "studentApplyInfo";
    }

    public String studentBasicInfoPage(){
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        Staff staff = (Staff)sw.getObject(session, SessionWrap.KEY_STAFF);
        Enroll enroll = (Enroll)baseBeanService.getBeanByHqlAndParams("from Enroll e where e.staffId = ?",new Object[]{staffId});

        tbJpStudentInfo = (TbJpStudentInfo)baseBeanService.getBeanByHqlAndParams("from TbJpStudentInfo st where st.staffId = ?",new Object[]{staffId});
        if(tbJpStudentInfo ==null){
            tbJpStudentInfo = new TbJpStudentInfo();
            if(enroll!=null){
                tbJpStudentInfo.setAddress(enroll.getStaffAddress());
                tbJpStudentInfo.setCardNum(enroll.getIdCard());
            }
            if(staff!=null){
                tbJpStudentInfo.setName(staff.getStaffName());
            }
        }
        return "studentBasicInfo";
    }

    public String saveStudent() throws Exception {
        TbJpStudentInfo stu = (TbJpStudentInfo)baseBeanService.getBeanByHqlAndParams("from TbJpStudentInfo st where st.staffId = ?",new Object[]{staffId});
        if(stu==null){
            stu = new TbJpStudentInfo();
            stu.setStaffId(staffId);
            stu.setStudentId(serverService.getServerID("student"));
        }
        stu.setName(tbJpStudentInfo.getName());
        stu.setSex(tbJpStudentInfo.getSex());
        stu.setBrith(Utilities.getDateFromString(brith,"yyyy/MM/dd"));
        stu.setNationality(tbJpStudentInfo.getNationality());
        stu.setCardType(tbJpStudentInfo.getCardType());
        stu.setCardNum(tbJpStudentInfo.getCardNum());
        stu.setIsLocal(tbJpStudentInfo.getIsLocal());
        stu.setPhone(tbJpStudentInfo.getPhone());
        if(tbJpStudentInfo.getTempCardNo() != null && tbJpStudentInfo.getTempCardNo() != ""){
            stu.setTempCardNo(tbJpStudentInfo.getTempCardNo());
        }
        if(tbJpStudentInfo.getStayAddress() != null && tbJpStudentInfo.getStayAddress() != ""){
            stu.setStayAddress(tbJpStudentInfo.getStayAddress());
        }
        stu.setAddress(tbJpStudentInfo.getAddress());
        stu.setPhoto(tbJpStudentInfo.getPhoto());
        enroll = (Enroll)baseBeanService.getBeanByHqlAndParams("from Enroll e where e.staffId = ?",new Object[]{staffId});
//        if(enroll != null){
//            try {
//                CashierBills cashierBills = (CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills c where c.cashierBillsID = ?",new Object[]{enroll.getCashierBillsID()});
//                DtOrderBillAdd order = (DtOrderBillAdd)baseBeanService.getBeanByHqlAndParams("from DtOrderBillAdd o where o.oaBillId = ?",new Object[]{enroll.getCashierBillsID()});
//                if(order!=null){
//                    order.setReceiveaddress(tbJpStudentInfo.getAddress());
//                    beans.add(order);
//                }
//                if(cashierBills!=null){
//                    List<BaseBean> cashierBillList = baseBeanService.getListBeanByHqlAndParams("from CashierBills cc where cc.jNumOrder = ?",new Object[]{cashierBills.getjNumOrder()});
//                    CashierBills cashierBills1 = (CashierBills)cashierBillList.get(0);
//                    CashierBills cashierBills2 = (CashierBills)cashierBillList.get(1);
//                    cashierBills1.setCtUserName(tbJpStudentInfo.getName());
//                    cashierBills1.setReference(tbJpStudentInfo.getPhone());
//                    cashierBills1.setStaffIdentityCard(tbJpStudentInfo.getJsCardId());
//                    cashierBills1.setReferrerAddress(tbJpStudentInfo.getAddress());
//
//                    cashierBills2.setCtUserName(tbJpStudentInfo.getName());
//                    cashierBills2.setReference(tbJpStudentInfo.getPhone());
//                    cashierBills2.setStaffIdentityCard(tbJpStudentInfo.getJsCardId());
//                    cashierBills2.setReferrerAddress(tbJpStudentInfo.getAddress());
//                    String ss = "select * from DT_ORDER_BILL_ADD o where o.oa_bill_jounum = '2016030804140800077'";
//
//                    beans.add(cashierBills1);
//                    beans.add(cashierBills2);
//                }
//            }catch (Exception e){
//                logger.error("操作异常", e);
//            }
//        }
        Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff e where e.staffID = ?",new Object[]{staffId});
        if(staff!=null){
            staff.setStaffName(stu.getName());
            if("1".equals(stu.getSex())){
                staff.setSex("男");
            }else if("2".equals(stu.getSex())){
                staff.setSex("女");
            }
            staff.setReference(stu.getPhone());
            staff.setStaffAddress(stu.getAddress());
            staff.setStaffIdentityCard(stu.getCardNum());
        }
        staffManageService.saveStudentInfo(stu,staff);
        return "studentBasicInfo";
    }

    public String saveStudentApplyInfo() throws Exception {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        TbJpStudentInfo stu = (TbJpStudentInfo)baseBeanService.getBeanByHqlAndParams("from TbJpStudentInfo st where st.staffId = ?",new Object[]{staffId});
        if(stu==null){
            stu = new TbJpStudentInfo();
            stu.setStaffId(staffId);
            stu.setStudentId(serverService.getServerID("student"));
        }
        stu.setCompanyId(tbJpStudentInfo.getCompanyId());
        stu.setApplyDate(Utilities.getDateFromString(applyDate,"yyyy/MM/dd"));
        stu.setTrainType(tbJpStudentInfo.getTrainType());
        stu.setCharId(tbJpStudentInfo.getCharId());
        stu.setBusiType(tbJpStudentInfo.getBusiType());
        stu.setPerdriType(tbJpStudentInfo.getPerdriType());

        beans.add(stu);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        return "studentApplyInfo";
    }

    public String coachInfo(){
        tbJpTeacher = (TbJpTeacher)baseBeanService.getBeanByHqlAndParams("from TbJpTeacher st where st.staffId = ?",new Object[]{staffId});
        return "coachInfo";
    }

    public String saveCoachInfo() throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        CAccount cAccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        if ("".equals(tbJpTeacher.getTeacherId()) || tbJpTeacher.getTeacherId() == null) {
            tbJpTeacher.setTeacherId(serverService.getServerID("teacher"));
        }
        if(cAccount==null){
            tbJpTeacher.setCompanyId(companyID);
        }else {
            tbJpTeacher.setCompanyId(cAccount.getCompanyID());
        }
        tbJpTeacher.setStaffId(staffId);
        tbJpTeacher.setFstdrilicdate(fstdrilicdate==null || "".equals(fstdrilicdate) ?null: Utilities.getDateFromString(fstdrilicdate,"yyyy/MM/dd"));
        Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff e where e.staffID = ?",new Object[]{staffId});
        if(staff!=null){
            staff.setStaffName(tbJpTeacher.getName());
            if("1".equals(tbJpTeacher.getSex())){
                staff.setSex("男");
            }else if("2".equals(tbJpTeacher.getSex())){
                staff.setSex("女");
            }
            staff.setStaffIdentityCard(tbJpTeacher.getIdcard());
            staff.setStaffAddress(tbJpTeacher.getAddress());
            staff.setReference(tbJpTeacher.getMobile());
        }
        staffManageService.saveCoachInfo(tbJpTeacher,staff);
        return "success";
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public TbJpStudentInfo getTbJpStudentInfo() {
        return tbJpStudentInfo;
    }

    public void setTbJpStudentInfo(TbJpStudentInfo tbJpStudentInfo) {
        this.tbJpStudentInfo = tbJpStudentInfo;
    }

    public Enroll getEnroll() {
        return enroll;
    }

    public void setEnroll(Enroll enroll) {
        this.enroll = enroll;
    }

    public TbJpTeacher getTbJpTeacher() {
        return tbJpTeacher;
    }

    public void setTbJpTeacher(TbJpTeacher tbJpTeacher) {
        this.tbJpTeacher = tbJpTeacher;
    }

    public String getBrith() {
        return brith;
    }

    public void setBrith(String brith) {
        this.brith = brith;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getFstdrilicdate() {
        return fstdrilicdate;
    }

    public void setFstdrilicdate(String fstdrilicdate) {
        this.fstdrilicdate = fstdrilicdate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }
}
