package hy.ea.marketing.action.supermaket;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.GiftCards;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.WfjJifenService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.finance.BenDis.BonusPoints;
import hy.ea.bo.finance.BenDis.OperatorInfo;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.marketing.service.GiftCardService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2018/10/13.
 *
 * 购物卡
 */
@Controller
@Scope("prototype")
public class GiftCardAction {

    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private WfjJifenService wfjJifenService;
    @Resource
    private GiftCardService giftCardService;
    private PageForm pageForm;
    private int pageNumber;
    private String result;
    private Staff staff;
    private GiftCards giftCards;
    private TEshopCusCom usereEshop;
    private StaffAddress staffAddress;
    private String password;
    private String cardNumber;
    private String iphone;
    private String flag;
    private String sccid;
    private String staffID;
    private String jum;
    private String integral;
    private String state;
    private BonusPoints bp;
    private String account;
    private String paymentCode;

    public String getGidtCardPage(){
            HttpSession session = ServletActionContext.getRequest().getSession();
            String companyID = (String) session.getAttribute("companyID");
            pageForm = giftCardService.getSearch(null!= pageForm? pageForm.getPageNumber():1,pageNumber,companyID,staff,giftCards);
            return  "giftCardPage";
        }

        public String getSearchList(){
            HttpSession session = ServletActionContext.getRequest().getSession();
            String companyID = (String) session.getAttribute("companyID");
            ActionContext.getContext().getSession()
                    .put("staff_search", staff);
            ActionContext.getContext().getSession()
                    .put("gift_search", giftCards);

            pageForm = giftCardService.getSearch(null!= pageForm? pageForm.getPageNumber():1,pageNumber,companyID,staff,giftCards);
            return "giftCardPage";
        }
    public String getSearchListByLimit(){
        staff=(Staff) ActionContext.getContext().getSession().get("staff_search");
        giftCards = (GiftCards) ActionContext.getContext().getSession().get("gift_search");
        return getSearchList();
    }

    public String registered(){
        HttpSession session = ServletActionContext.getRequest().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String cookies = request.getHeader("cookie");
        Cookie[] cookies1 = request.getCookies();
        CAccount account = (CAccount)session.getAttribute("account");
        String companyID = (String) session.getAttribute("companyID");
        String message = "";
        boolean isRegister = true;
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject json = new JSONObject();
        //新用户注册
        TEshopCusCom user= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0", new Object[]{staff.getReference()});
        int countByNumber = baseBeanService.getConutByBySqlAndParams("select count(*) from dt_giftcards g where g.cardnumber = ?", new Object[]{giftCards.getCardNumber()});
        if(user==null&&staff.getReference()!=null&&!"".equals(staff.getReference())&&!"add".equals(flag)){
            TEshopCusCom referees= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0 and acquiesce = ?", new Object[]{iphone,"01"});
            if(referees==null){
                message="推荐人有误！请重新输入推荐人号码！";
                isRegister = false;
            }else {
                if(countByNumber!=0){
                    message = "购物卡号已开通过，请重新输入！";
                    isRegister = false;
                }else {
                    giftCardService.registered(referees,referees.getSccId(),staff.getReference(),password,staff,giftCards,staffAddress,sccid,paymentCode);
                    message = "此用户为新用户，注册成功！开通购物卡成功！";
                }
            }
        }else {
            if(user==null){
                message = "此手机号没有注册微分金，请点击注册并开通！";
            }else {
                int countByPhone = baseBeanService.getConutByBySqlAndParams("select count(*) from dt_giftcards g left join t_eshop_cuscom s on g.staffid = s.staffid where s.account = ?", new Object[]{staff.getReference()});

                TEshopCustomer eshopCustomer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where staffid = ?", new Object[]{user.getStaffid()});
                if (!eshopCustomer.getPassword().equals(password)) {
                    message = "此账号已注册过微分金，密码输入错误！请重新输入！";
                    isRegister = false;
                } else {
                    if (countByPhone != 0) {
                        message = "此手机号已经开通购物卡！请重新输入！";
                        isRegister = false;
                    } else {
                        if (countByNumber != 0) {
                            message = "购物卡号已开通过，请重新输入！";
                            isRegister = false;
                        } else {
                            Staff staff_ = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{user.getStaffid()});
                            if (staff_.getStaffIdentityCard() == null || "".equals(staff_.getStaffIdentityCard())) {
                                staff_.setStaffIdentityCard(staff.getStaffIdentityCard());
                            }
                            if (staff_.getStaffName() == null || "".equals(staff_.getStaffName())) {
                                staff_.setStaffName(staff.getStaffName());
                            }
                            Staff staff1 = new Staff();
                            Staff staffOperator = new Staff();
                            TEshopCusCom cus = new TEshopCusCom();
                            if(account!=null){
                                staff1= (Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{account.getStaffID()});
                            }else {
//                                SessionWrap sw = SessionWrap.getInstance();
//                                cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
                                  cus = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?",new Object[]{sccid});
                                staff1= (Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{cus.getStaffid()});
                            }
                            giftCards.setCardId(serverService.getServerID("giftCards"));
                            giftCards.setCompanyID(account!=null?account.getCompanyID():"company20180510CQZCDKTT690000006064");
                            giftCards.setOperator(staff1!=null?staff1.getStaffID():"");
                            giftCards.setApplyCardDate(new Date());
                            giftCards.setStaffID(user != null ? user.getStaffid() : "");
                            giftCards.setState("1");
                            baseBeanService.save(giftCards);
                            baseBeanService.update(staff_);
                            message = "此用户为微分金用户，开通购物卡成功！";
                        }
                    }
                }
            }
        }
        map.put("isRegister",isRegister);
        map.put("message",message);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }
    //补卡
    public String reissueCard(){
        String message = "";
        int countByCard = baseBeanService.getConutByBySqlAndParams("select count(*) from  dt_giftcards where cardnumber = ?",new Object[]{cardNumber});
        if(countByCard!=0){
            message = "购物卡号重复！";
        }else {
            GiftCards gift= (GiftCards) baseBeanService.getBeanByHqlAndParams("from GiftCards where staffID =?",new Object[]{staffID});
            gift.setOldCardNumber(gift.getCardNumber());
            gift.setCardNumber(cardNumber);
            gift.setReissueCardDate(new Date());
            gift.setState("1");
            baseBeanService.update(gift);
            message = "补卡成功";
        }
        JSONObject json = new JSONObject();
        json.accumulate("message",message);
        result = json.toString();
        return "success";
    }

    //挂失//取消挂失
    public String reportLoss(){
        String sql = "update dt_giftcards set state = ? where staffID = ?";
        String[] sqls = { sql };
        List<Object[]> beams = new ArrayList<Object[]>();
        String[] bb = { state,staffID };
        beams.add(bb);
        baseBeanService.executeSqlsByParmsList(null,sqls,beams);
        return "success";
    }

    //充值
    public String topup(){
        HttpSession session = ServletActionContext.getRequest().getSession();
        CAccount account = (CAccount) session.getAttribute("account");
        OperatorInfo operator = new OperatorInfo(serverService.getServerID("operator"),account.getStaffID(),"",jum,"","00");
        baseBeanService.save(operator);
        return "topup";
    }

    public String appGiftCard(){
        HttpServletRequest request = ServletActionContext.getRequest();
        TEshopCusCom cus = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?",new Object[]{sccid});
        int count  = baseBeanService.getConutByBySqlAndParams("select count(*) from dt_giftcards gc where gc.cardNumber = ?",new Object[]{cardNumber});
        if(count>0){
            Object sccidBycard =  baseBeanService.getObjectBySqlAndParams("select ec.sccid,ec.account,gc.staffid from dt_giftcards gc,t_eshop_cuscom ec where gc.staffid  = ec.staffid and  gc.cardnumber = ? and rownum = ?",new Object[]{cardNumber,1});
            Object [] cardman = (Object[]) sccidBycard;
            bp = (BonusPoints)baseBeanService.getBeanByHqlAndParams("from BonusPoints where sccid=? ", new Object[]{cardman[0].toString()});
            account = cardman[1].toString();
            sccid = cardman[0].toString();
            request.setAttribute("staffid",cardman[2].toString());
            return "getbpDetail";
        }
        request.setAttribute("account",cus.getAccount());
        request.setAttribute("sccid",cus.getSccId());
        return "appGiftCardPage";
    }

    public String app_Registered(){

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

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public GiftCards getGiftCards() {
        return giftCards;
    }

    public void setGiftCards(GiftCards giftCards) {
        this.giftCards = giftCards;
    }

    public TEshopCusCom getUsereEshop() {
        return usereEshop;
    }

    public void setUsereEshop(TEshopCusCom usereEshop) {
        this.usereEshop = usereEshop;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getJum() {
        return jum;
    }

    public void setJum(String jum) {
        this.jum = jum;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public StaffAddress getStaffAddress() {
        return staffAddress;
    }

    public void setStaffAddress(StaffAddress staffAddress) {
        this.staffAddress = staffAddress;
    }

    public BonusPoints getBp() {
        return bp;
    }

    public void setBp(BonusPoints bp) {
        this.bp = bp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }
}
