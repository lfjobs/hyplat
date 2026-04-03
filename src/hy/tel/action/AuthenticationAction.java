/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.cloopen.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package hy.tel.action;

import hy.plat.service.BaseBeanService;
import hy.tel.bo.UserPhoneTime;
import hy.tel.bo.vo.CallAuthen;
import hy.tel.bo.vo.CallEstablish;
import hy.tel.bo.vo.CallHangup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;




/**
 * <p>
 * Title: Controller
 * </p>
 * <p>
 * Description: 鉴权控制器
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: hisunsray
 * </p>
 * <p>
 * Date: 2013-07-09
 * </p>
 *
 * @version 1.0
 */
@Controller
@Scope("prototype")
public class AuthenticationAction{

    @Resource
    private BaseBeanService baseService;
    private Object jret;
    public void forAuthen() throws Exception{
        HttpServletRequest request=ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Document doc = null;
        String body = "";
        InputStream in = request.getInputStream();
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        String str = null;
        StringBuffer xmlfile = new StringBuffer();
        while ((str = bf.readLine()) != null) {
            xmlfile.append(str);
        }
        doc = DocumentHelper.parseText(xmlfile.toString());
        Element root = doc.getRootElement();
        String action = root.elementTextTrim("action");
        if (action.equals("CallAuth")) {
            // 解析呼叫鉴权
            body = parseCallAuth(root);
        } else if (action.equals("CallEstablish")) {
            // 解析摘机请求
            body = parseCallEstablish(root);
        } else if (action.equals("Hangup")) {
            // 解析挂断请求
            body = parseHangup(root);
        }
        // 设置返回header
        response.setHeader("Status-Code", "HTTP/1.1 200 OK");
        response.setHeader("Date", new Date() + "");
        response.setHeader("Content-Length", body.length() + "");
        // 输出 ，返回到客户端
        OutputStream opt = response.getOutputStream();
        OutputStreamWriter out = new OutputStreamWriter(opt);
        out.write(body);
        out.flush();
    }
    /**
     * 解析呼叫鉴权
     * @param e Element
     * @return result
     */
    private String parseCallAuth(Element e) {
        CallAuthen call = new CallAuthen();
        call.setType(e.elementTextTrim("type"));
        call.setOrderId(e.elementTextTrim("orderid"));
        call.setSubId(e.elementTextTrim("subid"));//sid
        call.setCaller(e.elementTextTrim("caller"));
        call.setCalled(e.elementTextTrim("called"));
        call.setCallSid(e.elementTextTrim("callSid"));

        String hql = "select new UserPhoneTime(u.userTime) from UserPhoneTime u where exists (select v.pfKey VoipInfo v where v.pfSid=? and v.userId=u.staffId)";
        UserPhoneTime up =(UserPhoneTime) baseService.getBeanByHqlAndParams(hql, new Object[]{call.getSubId()});
        String  result= "<?xml version='1.0' encoding='UTF-8' ?>";
        result+="<Response>";
        if(up!=null){
            result+="<sessiontime>"+up.getUserTime().doubleValue()+"</sessiontime>";
            //请在此处增加逻辑判断代码
            //刚哥：这里就是当我呼叫别人前先查找我剩余的通话时间。比如：他还剩20分钟，这里设置起，他就只能打20分钟了。
            //返回的数据,如果需要控制呼叫时长需要增加sessiontime  例子：<sessiontime>3600</sessiontime> 加入到下面的result里。
            result += "<statuscode>0000</statuscode><statusmsg>Success</statusmsg><record>1</record>";
        }else{
            result += "<statusmsg>failed</statusmsg>";
        }

        result +="</Response>";
        return result;
    }
    /**
     * 解析摘机请求
     * @param e Element
     * @return result
     */
    private String parseCallEstablish(Element e) {
        CallEstablish call = new CallEstablish();
        call.setType(e.elementTextTrim("type"));
        call.setOrderId(e.elementTextTrim("orderid"));
        call.setSubId(e.elementTextTrim("subid"));
        call.setCaller(e.elementTextTrim("caller"));
        call.setCalled(e.elementTextTrim("called"));
        call.setCallSid(e.elementTextTrim("callSid"));


        String hql = "select new UserPhoneTime(u.userTime) from UserPhoneTime u where exists (select v.pfKey VoipInfo v where v.pfSid=? and v.userId=u.staffId)";
        UserPhoneTime up =(UserPhoneTime) baseService.getBeanByHqlAndParams(hql, new Object[]{call.getSubId()});
        String  result= "<?xml version='1.0' encoding='UTF-8' ?>";
        result+="<Response>";
        if(up!=null){
            result+="<sessiontime>"+up.getUserTime().doubleValue()+"</sessiontime>";
            //请在此处增加逻辑判断代码
            //刚哥：这里就是当我呼叫别人前先查找我剩余的通话时间。比如：他还剩20分钟，这里设置起，他就只能打20分钟了。他设计有点鸡肋。你把通话的限制时间在两个方法里都写起嘛。
            //
            //返回的数据,如果需要控制呼叫时长需要增加sessiontime
            result += "<statuscode>0000</statuscode><statusmsg>Success</statusmsg><billdata>ok</billdata>";
        }else{
            result += "<statusmsg>failed</statusmsg>";
        }

        result +="</Response>";
        return result;
    }

    /**
     * 解析挂断请求
     * @param e Element
     * @return result
     */
    private String parseHangup(Element e) {
        // 封装 CallHangup
        CallHangup call = new CallHangup();
        call.setType(e.elementTextTrim("type"));//呼叫类型
        call.setOrderId(e.elementTextTrim("orderid"));//订单id（不用管）
        call.setSubId(e.elementTextTrim("subid"));//子账号id（subAccountSid）
        call.setCaller(e.elementTextTrim("caller"));//主叫号码
        call.setCalled(e.elementTextTrim("called"));//被叫号码
        call.setByeType(e.elementTextTrim("byeType"));
        call.setStarttime(e.elementTextTrim("starttime"));//通话开始时间,当type为1 也就是回拨时，如果被叫未接听，则为主叫摘机时间；如果被叫接听，则为被叫摘机时间
        call.setEndtime(e.elementTextTrim("endtime"));//通话结束时间
        call.setBilldata(e.elementTextTrim("billdata"));//呼叫的计费私有数据
        call.setCallSid(e.elementTextTrim("callSid"));
        call.setRecordurl(e.elementTextTrim("recordurl"));

        String hql = "from UserPhoneTime u where exists (select v.pfKey VoipInfo v where v.pfSid=? and v.userId=u.staffId)";
        UserPhoneTime up =(UserPhoneTime) baseService.getBeanByHqlAndParams(hql, new Object[]{call.getSubId()});
        BigDecimal temp =new BigDecimal(call.getStarttime());
        temp=temp.subtract(new BigDecimal(call.getEndtime()));
        up.setUserTime(up.getUserTime().subtract(temp).setScale(2,BigDecimal.ROUND_HALF_DOWN));
        baseService.save(up);
        ///endtime-starttime
        //请在此处增加逻辑判断代码
        //刚哥：当用户挂机后，这里会返回一些信息包括（）
        //返回的数据
        String result = "<?xml version='1.0' encoding='UTF-8'?><Response><statuscode>0000</statuscode><statusmsg>Success</statusmsg><totalfee>0.120000</totalfee></Response>";
        return result;
    }
    public Object getJret() {
        return jret;
    }
    public void setJret(Object jret) {
        this.jret = jret;
    }
}
