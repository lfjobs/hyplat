package mobile.tiantai.android.common;


import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.util.SessionWrap;
import mobile.tiantai.android.util.MapSesssionUtil;
import org.apache.struts2.ServletActionContext;
import org.hibernate.util.StringHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 预算单拦截器
 * Created by Administrator on 2019-10-18.
 */
public class PayBudget_Interceptor implements Interceptor {

    public PayBudget_Interceptor() {
    }

    public void destroy() {

    }

    public void init() {

    }

    public String intercept(ActionInvocation arg0) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
//        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
//        if (parmaInfor == null) {//session值为空
//            //判断是否是首次登陆传的两个参数是否为空
//            String companyid = request.getParameter("companyid");//公司id
//            String staffId = request.getParameter("staffId");//登录人id
//            if (StringHelper.isNotEmpty(companyid) && StringHelper.isNotEmpty(staffId)) {
//                return arg0.invoke();
//            } else {
//                MapSesssionUtil.removeSession("paramMap");
//                return "logout";
//            }
//        } else {
            //查询是否登录
        boolean b = request.getRequestURL().toString().contains("getBusinessTypeRoot.jspa")
                ||request.getRequestURL().toString().contains("getBusinessTypesById.jspa")||request.getRequestURL().toString().contains("getBusinessTypeLikeName.jspa");
        if (b){
            return arg0.invoke();
        }
        HttpSession session = request.getSession();
            SessionWrap sw = SessionWrap.getInstance();
            TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
            if (cus == null) {
                MapSesssionUtil.removeSession("paramMap");
                return "logout";
            }else{
                return arg0.invoke();
            }
       // }
    }
}
