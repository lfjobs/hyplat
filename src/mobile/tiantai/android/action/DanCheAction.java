package mobile.tiantai.android.action;


import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.wfj.bo.Money;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.bo.WfjJifen;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.finance.BenDis.KuaiJie;
import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.bo.JiShi;
import mobile.tiantai.android.bo.WeiZhi;
import mobile.tiantai.android.bo.ZhiLing;
import mobile.tiantai.android.util.HttpClient;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

@Controller
@Scope("prototype")
public class DanCheAction extends ActionSupport {

    @Resource
    private BaseBeanService baseBeanService;

    @Resource
    private ServerService serverService;

    private List<JSONObject> list = new ArrayList<JSONObject>();
    private Object result;
    private String moneys;

    private ServletRequest request = ServletActionContext.getRequest();
    private Logger log = LoggerFactory.getLogger(DanCheAction.class);

    /**
     * 下发指令的当前流水号
     */
    private int issuedSerialnum = 1;
    /**
     * 响应数据的流水号
     */
    private int responseSerialnum = 1;

    /**
     * 用户Key
     */
    private  String userKey="garqi@key0001";
    /**
     * 用户唯一标识，十六位数字
     */
    private  String  userid = "garqi20170515001";

    /**
     * 快捷应用展示默认
     * http://127.0.0.1:8080/hyplat/ea/danche/sajax_ea_kuaiJie.jspa?staffid=?
     *
     * @param @staffId 人员的id
     * @return
     */
    public String kuaiJie() {
        String staffid = request.getParameter("staffid");
        JSONObject jsonObj = new JSONObject();
        List<JSONObject> list = new ArrayList<JSONObject>();
        TEshopCustomer cus = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where staffid = ?", new Object[]{staffid});
        if (cus == null) {
            jsonObj.accumulate("kuaijie", "1");  //帐号不存在
        } else {
            if (cus.getSource() != null) {
                List<BaseBean> kj = (List<BaseBean>) baseBeanService.getListBeanBySqlAndParams("select * from dt_kuaijie where moKauiMing = ?", new Object[]{cus.getSource()});
                for (int i = 0; i < kj.size(); i++) {
                    Object objc = kj.get(i);
                    Object[] okj = (Object[]) objc;
                    JSONObject object1 = new JSONObject();
                    object1.accumulate("moKauiMing", isNull(okj[3].toString()));
                    object1.accumulate("tupian", isNull(okj[4].toString()));
                    list.add(object1);
                }
                jsonObj.accumulate("kuaijie", list);   //快捷应用数据
            } else {
                jsonObj.accumulate("kuaijie", "2");  //正常跳转
            }
        }
        result = jsonObj;
        ;
        return "success";
    }


    /**
     * http://127.0.0.1:8080/hyplat/ea/danche/sajax_ea_zhanShiKuaiJie.jspa
     * 全部快捷应用展示
     */
    public String zhanShiKuaiJie() {

        JSONObject jsonObj = new JSONObject();
        List<JSONObject> list = new ArrayList<JSONObject>();
        List<BaseBean> kj = (List<BaseBean>) baseBeanService.getListBeanBySqlAndParams("select * from dt_kuaijie where staffid = ?", new Object[]{"0"});
        for (int i = 0; i < kj.size(); i++) {
            Object objc = kj.get(i);
            Object[] okj = (Object[]) objc;
            JSONObject object1 = new JSONObject();
            object1.accumulate("moKauiMing", isNull(okj[3].toString()));
            object1.accumulate("tupian", isNull(okj[4].toString()));
            list.add(object1);
        }
        jsonObj.accumulate("kuaijie", list);  //正常跳转
        result = jsonObj;
        return "success";
    }


    /**
     * 我的钱包
     *
     * @param @account 账号
     * @return
     */
    public String myWallet() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        //   JSONObject jsonObj = new JSONObject();
        List<JSONObject> list = new ArrayList<JSONObject>();
        Money money = (Money) baseBeanService.getBeanByHqlAndParams("from WfjMoney where staffId = ?", new Object[]{cus.getStaffid()});
        if (money == null) {
            money.setWfjMoneyKey(serverService.getServerID("moneykey"));
            money.setWfjMoneyId(serverService.getServerID("moneyid"));
            money.setStaffId(cus.getStaffid());
            money.setWfjJifenState("1");   //没有交押金，不能使用
            money.setWfjyajin("0");
            money.setWfjyingli("0");
            List<BaseBean> cbean = (List<BaseBean>) baseBeanService.getBeanByHqlAndParams(" from TEshopCusCom where staffid = ?", new Object[]{cus.getStaffid()});
            TEshopCusCom tcom = (TEshopCusCom) cbean.get(0);
            Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(" from Staff where staffID = ?", new Object[]{cus.getSource()});
            money.setHeadimage(staff.getHeadimage());
            money.setStaffName(staff.getStaffName());
            money.setSccid(tcom.getSccId());
            baseBeanService.save(money);
        }
        request.setAttribute("yajin", money.getWfjyajin());
        request.setAttribute("yingli", money.getWfjyingli());


        return "success";
    }

    /**
     * 支付押金(金额为199元)
     *
     * @param
     * @return
     */
    public String zhiFuyajin() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        JSONObject temp = new JSONObject();
        if (cus == null) {
            temp.accumulate("zhifu", "login");
            result = temp;
            return "success";
        }
        if (Integer.parseInt(moneys) < 199) {
            temp.accumulate("zhifu", "buzu");
            result = temp;
            return "success";
        }
        Money fm = new Money();
        fm = (Money) baseBeanService.getBeanByHqlAndParams(" from WfjMoney where staffId = ?", new Object[]{cus.getStaffid()});
        TEshopCusCom tcom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(" from TEshopCusCom where staffid = ?", new Object[]{cus.getStaffid()});

        if (fm == null) {
            fm.setStaffId(cus.getStaffid());
            fm.setWfjMoneyKey(serverService.getServerID("money"));
            fm.setWfjMoneyId(serverService.getServerID("money"));
            fm.setSccid(tcom.getSccId());
            fm.setWfjyingli("0");
            Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(" from Staff where staffID = ?", new Object[]{cus.getStaffid()});
            if (staff != null) {
                fm.setStaffName(staff.getStaffName());
                fm.setHeadimage(staff.getHeadimage());
            }
        }

        fm.setWfjyajin(moneys);
        fm.setWfjJifenState("0");
        baseBeanService.save(fm);
        temp.accumulate("zhifu", "chenggong");
        result = temp;
        return "success";
    }

    /**
     * http://127.0.0.1:8080/hyplat/ea/danche/ea_chaXunYajin.jspa?sccid=
     * 查看个人单车的押金和余额
     * @return
     */
    public String chaXunYajin(){

        String sccid=request.getParameter("sccid");
        JSONObject obj=new JSONObject();
        List<JSONObject> list = new ArrayList<JSONObject>();
        //查看押金
        Money fm  = (Money) baseBeanService.getBeanByHqlAndParams(" from Money where sccid = ?", new Object[]{sccid});
        if(fm == null || fm.getWfjyajin().equals("")){
          request.setAttribute("yajin","0");
        }else {
            request.setAttribute("yajin",fm.getWfjyajin());
        }
        //查看余额
        WfjJifen  jinbi = (WfjJifen) baseBeanService.getBeanByHqlAndParams(" from WfjJifen where sccid = ?",new Object[]{sccid});
        if(jinbi == null || jinbi.getWfjJifenScore().equals("")){
           request.setAttribute("jinbishu","0");
        }else {
            request.setAttribute("jinbishu",jinbi.getWfjJifenScore());
        }

        return "danchebao";
    }


    /**
     * 申请开锁的接口
         * http://127.0.0.1:8080/hyplat/ea/danche/sajax_ea_diaoyongKaiSuo.jspa?cmd=1&deviceid=75580000009&operateType=0&sccid=?
     *@param  @cmd 0表示查询设备信息,1表示下发开锁指令，2表示寻车，3表示设置设备位置回传间隔
     *          deviceid 锁的编号
     *          operateType   @param type 0:下发 1：响应
     * @return
     */
    public String diaoyongKaiSuo() {
        //快捷访问测试
        JSONObject jsonObj = new JSONObject();
        String cmd = request.getParameter("cmd");
        String deviceid = request.getParameter("deviceid");
        String operateType = request.getParameter("operateType");
        String sccid=request.getParameter("sccid");
        if(sccid == null || sccid.equals("")){
           /* jsonObj.accumulate("fanhui", "2"); //没有sccid
            result = jsonObj;
            return "success";*/
        }else if(sccid != null && !sccid.equals("") ){
             TEshopCusCom te= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(" from TEshopCusCom where sccId = ?",new Object[]{sccid});
             if(te == null){
                 jsonObj.accumulate("fanhui", "3"); //staffid身份不存在
                 result = jsonObj;
                 return "success";
             }else{
                 Money money= (Money) baseBeanService.getBeanByHqlAndParams("  from Money where sccid = ?",new Object[]{sccid});
                 if(money == null || !money.getWfjyajin().equals(199)){
                     jsonObj.accumulate("fanhui", "5"); //没有缴纳押金
                     result = jsonObj;
                     return "success";
                 }
                 WfjJifen jifen= (WfjJifen) baseBeanService.getBeanByHqlAndParams(" from WfjJifen where sccid = ?",new Object[]{sccid});
                 if(jifen ==null || Float.parseFloat(jifen.getWfjJifenScore())<=0){
                     jsonObj.accumulate("fanhui", "4"); //没有金币或者金币不足
                     result = jsonObj;
                     return "success";
                 }
                 JiShi js= (JiShi) baseBeanService.getBeanByHqlAndParams(" from JiShi where deviceid = ?",new Object[]{deviceid});
                 if(js == null) {
                     js=new JiShi();
                     js.setJskey(serverService.getServerID("js"));
                     js.setJsid(serverService.getServerID(""));
                     js.setSccid(sccid);
                     js.setDeviceid(deviceid);
                     baseBeanService.save(js);
                 }
             }
        }
        try {
            String url = "http://121.43.101.137:8888/?version=1";
          //  String url = "http://127.0.0.1:8080/hyplat/ea/danche/sajax_ea_ResponseResult.jspa";
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("userid", userid);
                map.put("cmd", cmd);
                map.put("deviceid", deviceid);
                int  serialnum=getSerialNumber(Integer.parseInt(operateType));
                map.put("serialnum",serialnum);
                map.put("sign", toMD5(userid + cmd + deviceid + serialnum+userKey));
                JSONObject jsonO = JSONObject.fromObject(map);
                JSONObject str = HttpClient.httpPost(url, jsonO);
                //   logger.info("值：{}", str);
                if (str != null) {
                    jsonObj.accumulate("fanhui", "1"); //没有请求成功
                    result = jsonObj;
                    return "success";
            }
            JiShi jj=new JiShi();
            int t=0;
            do {
                jj= (JiShi) baseBeanService.getBeanByHqlAndParams(" from JiShi where deviceid = ?",new Object[]{deviceid});
                if(jj == null){
                    Thread.sleep(2000);
                }
                t+=1;
            } while (t<4 || jj ==null );
            if(jj.getBeginDate() !=null || !jj.getBeginDate().equals("")){
                jsonObj.accumulate("fanhui", "0"); //等待返回响应
                result = jsonObj;
                return "success";
            }
        } catch (Exception e) {
            log.error("调用开锁时的异常锁" + deviceid);
        }
        jsonObj.accumulate("fanhui", "1"); //等待返回响应
        result = jsonObj;
        return "success";
    }



    /**
     * 获取当前用户下发指令流水号
     *
     * @param type 0:下发 1：响应
     * @return
     */
    public int getSerialNumber(int type) {
        if (type == 0) {
            if (issuedSerialnum > 0xffff) {
                issuedSerialnum = 1;
            } else
                issuedSerialnum++;
            return issuedSerialnum;
        } else {
            if (responseSerialnum > 0xffff) {
                responseSerialnum = 1;
            } else
                responseSerialnum++;
            return responseSerialnum;
        }
    }


    /**
     * 设备位置信息获取
     * http://127.0.0.1:8080/hyplat/ea/danche/sajax_ea_ResponseResult.jspa
     *
     * @param
     * @return
     */
    public String ResponseResult() {
        log.error("开锁回调设备位置信息获取");
        JSONObject jsonObj=new JSONObject();
        int len = 0;
        String res="";
        try {
            res= dealResponseResult(request.getInputStream());
        }catch (Exception e) {
             log.error("解析单车数据报错");
        }
        logger.info("值：{}", res);

        if(res == null || res.equals("")) {
            jsonObj.accumulate("fanhui", "1"); //网络异常，车辆故障
            result = jsonObj;
            return "success";
        }
        log.error("数据查看"+res);
        List<BaseBean> backList = new ArrayList<BaseBean>();

        JSONObject json=JSONObject.fromObject(res); // 将读取的数据转成JSON
        log.error(json.toString()+"数据查看");
        if (json == null ) {
            jsonObj.accumulate("fanhui", 1); //网络异常，车辆故障
            result = jsonObj;
            return "success";
        }
            WeiZhi  wei = (WeiZhi) baseBeanService.getBeanByHqlAndParams(" from WeiZhi where deviceId =  ?", new Object[]{json.get("deviceId")});
            if (wei == null) {
                wei=new WeiZhi();
                wei.setWeizhikey(serverService.getServerID("key"));
                wei.setWeizhiid(serverService.getServerID("id"));
                wei.setUserid(json.get("userid").toString());
                wei.setCmd(Integer.parseInt(json.get("cmd").toString()));
                wei.setDeviceId(json.get("deviceId").toString());
                wei.setBattery(Integer.parseInt(json.get("battery").toString()));
                wei.setBike(Integer.parseInt(json.get("bike").toString()));
                wei.setLockstatus(Integer.parseInt(json.get("lockstatus").toString()));
                wei.setLng(Float.parseFloat(json.get("lng").toString()));
                wei.setLat(Float.parseFloat(json.get("lat").toString()));
                wei.setGx(Integer.parseInt(json.get("gx").toString()));
                wei.setGy(Integer.parseInt(json.get("gy").toString()));
                wei.setGz(Integer.parseInt(json.get("gz").toString()));
                wei.setWtime(new Date());
                wei.setSerialnum(Integer.parseInt(json.get("serialnum").toString()));
                wei.setSign(json.get("sign").toString());
            } else {
                wei.setUserid(json.get("userid").toString());
                wei.setCmd(Integer.parseInt(json.get("cmd").toString()));
                wei.setDeviceId(json.get("deviceId").toString());
                wei.setBattery(Integer.parseInt(json.get("battery").toString()));
                wei.setBike(Integer.parseInt(json.get("bike").toString()));
                wei.setLockstatus(Integer.parseInt(json.get("lockstatus").toString()));
                wei.setLng(Float.parseFloat(json.get("lng").toString()));
                wei.setLat(Float.parseFloat(json.get("lat").toString()));
                wei.setGx(Integer.parseInt(json.get("gx").toString()));
                wei.setGy(Integer.parseInt(json.get("gy").toString()));
                wei.setGz(Integer.parseInt(json.get("gz").toString()));
                wei.setWtime(new Date());
                wei.setSerialnum(Integer.parseInt(json.get("serialnum").toString()));
                wei.setSign(json.get("sign").toString());
            }
            backList.add(wei);
            if (backList == null || backList.size() == 0) {
                jsonObj.accumulate("fanhui", "1"); //网络异常，车辆故障
                result = jsonObj;
                return "success";
            } else {
                baseBeanService.executeHqlsByParamsList(backList, null, null);
            }
         log.error("开锁回调结束设备位置信息获取");
        jsonObj.accumulate("fanhui", backList); //网络异常，车辆故障
        result = jsonObj;
        return "success";
    }

    /**
     * 用户下发指令的应答
     *http://127.0.0.1:8080/hyplat/ea/danche/sajax_ea_jieShouZhiLing.jspa
     *
     * @return
     */
    public String jieShouZhiLing() {
        log.error("-------用户下发指令的应答开始----------");
        JSONObject jsonObj=new JSONObject();
        int len = 0;
         String res="";
        try {
            res= dealResponseResult(request.getInputStream());
        }catch (Exception e) {
            log.error("解析单车数据报错");
        }
        logger.info("值：{}", res);

        if(res == null || res.equals("")) {
            jsonObj.accumulate("fanhui", "1"); //网络异常，车辆故障
            result = jsonObj;
            return "success";
        }
        log.error("数据查看"+res);
        List<BaseBean> backList = new ArrayList<BaseBean>();


        JSONObject json=JSONObject.fromObject(res); // 将读取的数据转成JSON
        log.error(json.toString()+"数据查看");
        logger.info("值：{}", json);
        if (json == null ) {
            jsonObj.accumulate("fanhui", 1); //网络异常，车辆故障
            result = jsonObj;
            return "success";
        }

        ZhiLing zhiLing = (ZhiLing) baseBeanService.getBeanByHqlAndParams(" from ZhiLing where deviceId = ?", new Object[]{json.get("deviceid")});
            if (zhiLing == null) {
                zhiLing=new ZhiLing();
                zhiLing.setKey(serverService.getServerID("zlkey"));
                zhiLing.setId(serverService.getServerID("zlid"));
                zhiLing.setUserid(json.get("userid").toString());
                zhiLing.setCmd(Integer.parseInt(json.get("cmd").toString()));
                zhiLing.setDeviceid(json.get("deviceid").toString());
                zhiLing.setResult(json.get("result").toString());
                zhiLing.setInfo(json.get("info").toString());
                zhiLing.setSerialnum(Integer.parseInt(json.get("serialnum").toString()));
                zhiLing.setSign(json.get("sign").toString());
            } else {
                zhiLing.setUserid(json.get("userid").toString());
                zhiLing.setCmd(Integer.parseInt(json.get("cmd").toString()));
                zhiLing.setDeviceid(json.get("deviceid").toString());
                zhiLing.setResult(json.get("result").toString());
                zhiLing.setInfo(json.get("info").toString());
                zhiLing.setSerialnum(Integer.parseInt(json.get("serialnum").toString()));
                zhiLing.setSign(json.get("sign").toString());
            }
        backList.add(zhiLing);
        if (backList == null || backList.size() == 0) {
            jsonObj.accumulate("fanhui", "1"); //网络异常，车辆故障
            result = jsonObj;
            return "success";
        } else {
            baseBeanService.executeHqlsByParamsList(backList, null, null);
        }

        log.error("-------用户下发指令的应答结束----------");

        jsonObj.accumulate("fanhui", backList); //网络异常，车辆故障
        result = jsonObj;
        return "success";
    }

    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null; // 存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            logger.error("操作异常", e);
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }


    public  String toMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            log.error("MD5加密异常");
        }
        return "";
    }


    private Object isNull(Object tep) {
        if (tep == null || "null".equals(tep)) {
            return "";
        } else {
            return tep;
        }
    }


    public List<JSONObject> getList() {
        return list;
    }

    public void setList(List<JSONObject> list) {
        this.list = list;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ServletRequest getRequest() {
        return request;
    }

    public void setRequest(ServletRequest request) {
        this.request = request;
    }


    public String getMoneys() {
        return moneys;
    }

    public void setMoneys(String moneys) {
        this.moneys = moneys;
    }
}
