package com.tiantai.wfj.service.impl;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.service.SmsServiceImpl;
import com.tiantai.wfj.util.SessionWrap;
import com.tiantai.wfj.util.VerifyCodeManager;
import hy.ea.util.Utilities;
import mobile.tiantai.android.action.SDKTestSendTemplateSMS;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
@Service
public class SmsService implements SmsServiceImpl {

    @Resource
    private VerifyCodeManager verifyCodeManager;

    private SDKTestSendTemplateSMS sdk = new SDKTestSendTemplateSMS();
    private Object result;
    @Override
    public String sendSms() {
            ServletRequest request = ServletActionContext.getRequest();

            String sbhk = request.getParameter("sbhk");// 用于防止攻击

            Map<String, Object> session = ActionContext.getContext().getSession();
            String pahe = request.getParameter("phone");// 电话号码
            String yanz = request.getParameter("yanz");// 验证码

            JSONObject temp = new JSONObject();
            if(yanz!=null)
            {
//			System.out.println(yanz+pahe+yanz);
//			System.out.println(Utilities.MD5(yanz+pahe+yanz));
                if(Utilities.MD5(yanz+pahe+yanz).equals(sbhk)) {
                    System.out.println("带随机数调用");
                    if (yanzheng(new String[]{pahe, yanz})) {
                        sdk.getduan(pahe, yanz);
                        temp.accumulate("return", "0");
                    } else {
                        temp.accumulate("return", "1");
                    }
                }else{
                    temp.accumulate("return", "1");

                }
            }else{

                System.out.println(session.get("sbhk"));
                if (sbhk != null && !sbhk.equals("")&&sbhk.equals(session.get("sbhk"))) {
                    System.out.println("不带随机数调用");
                }else{
                    System.out.println("不合理调用");
//				return "success";
                }

                java.util.Random random = new java.util.Random();
                int ir=100000;
                int is=999999;
                int i=random.nextInt(is)%(is-ir+1)+ir;
                String yz=""+i;
                sdk.getduan(pahe,yz);
			/* 国际短信 暂时不用
			 * String back = MobileSMS.sendMessage(pahe, yanz);
			if(back.equals("0")){*/
                System.out.println(yz);
                temp.accumulate("returna",yz);
                session.put(SessionWrap.KEY_SMS,yz);
                verifyCodeManager.generateCode(pahe,yz);
			/*}else{
				temp.accumulate("return", "error");
			}*/

            }
            result = temp;

            return "success";


    }

    /**
     *
     * 验证方法 掉方法 然后 (new String[]{参数,参数,参数以此类推})
     *
     * @param string
     * @return
     */
    private boolean yanzheng(String[] string) {
        boolean b = true;
        for (String s : string) {
            if (s == null || "".equals(s)) {
                b = false;
                break;
            }
        }
        return b;
    }
}
