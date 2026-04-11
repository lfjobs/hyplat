package hy.ea.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.config.AlipayConfig;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.tiantai.wfj.bo.MarKeting;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.wechat.utils.HTTPV3;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.BenDis.DtOrderBillAdd;
//import hy.ea.bo.finance.SrYusuanBill;
import hy.ea.bo.finance.BenDis.Rewarddetail;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.marketing.bo.ProProxy;
import hy.ea.test.util.ProxyUtils;
import hy.ea.util.DateUtil;
import hy.ea.util.RandomDatas;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.*;

import java.io.*;
import java.util.List;

import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import hy.ea.test.other.IPBean;
import hy.ea.test.other.IPList;
import hy.ea.test.other.IPSpider;
import hy.ea.test.util.IPUtils;

/**
 * Created by Administrator on 2017-07-07.
 */

class StopRecursiveException extends Exception {
    public StopRecursiveException(String message) {
        super(message);
    }
}

public class TEST {
    /*private static JDBC jd = new JDBC();
    private static Connection conn = jd.getConnection();*/
    @Resource
    private static BaseBeanDao beandao;
    private static Statement stm;
    private static int parentID = 0;
    private static int order_num_of_first = 0;
    private static int order_num_of_second = 0;
    private static boolean StopRecursion = false; //用于标记递归是否结束
    private static String ReturnRecursion;
    private Logger logger = LoggerFactory.getLogger(TEST.class);

    public static void main(String[] args) {
       /* List<BaseBean> BaseBean=new ArrayList<>();
        way36("1",BaseBean);*/
        way15txt();
    }

    private static void way38(){
        String proStr = "1:1,2:1,";
        String[] str = proStr.split(",");
        for (int j = 0; j < str.length; j++) {
            String str_i = str[j];
            String pid = str_i.substring(0, str_i.indexOf(":"));
            logger.info("调试信息");
            logger.info("调试信息");
            int num = Integer.parseInt(str_i.substring(str_i.indexOf(":") + 1));
            logger.info("调试信息");
        }
    }

    private static void way37(){
        int number = 500000;
        int targetLength = 6; // 目标长度，以位为单位

        // 获取需要补位的数量
        int paddingNeeded = targetLength - Integer.toBinaryString(number).length();

        // 使用正则表达式来进行补位，"0"代表需要补的位
        String paddedBinaryString = String.format("%0" + targetLength + "d", number);

        logger.info("调试信息");
        logger.info("调试信息");
        logger.info("调试信息");
        logger.info("调试信息");
        logger.info("调试信息");
    }

    private static void way36(String companyID,List<BaseBean> beans){
        //新增库房
        String[] stra = {
                "股东库",
                "董事会库",
                "监事会库",
                "工会库",
                "顾问会库",
                "董事长室库",
                "总经理室库",
                "人事库",
                "财务库",
                "生产库",
                "营销库",
                "创收库"
        };
        String[] strb = {"实物仓库","资料仓库", "资金仓库", "金币仓库"};
        String[] strs = {"原材料库", "成品库", "销售库", "退货库", "物流库"};
        for (int j = 0; j < stra.length; j++) {
            String apid=way36(companyID,"001",j+1,stra[j],beans);
            for (int i = 0; i < strb.length; i++){
                if (!stra[j].equals("财务库")&&i>=2){
                    break;
                }
                String bpid=way36(companyID,apid,i+1,strb[i],beans);
                if (stra[j].equals("营销库")&&strb[i].equals("实物仓库")){
                    for (int n = 0; n < strs.length; n++) {
                        way36(companyID,bpid,n+1,strs[n],beans);
                    }
                }
            }
        }
    }

    /**
     * 保存仓库信息
     * @param comid 公司id
     * @param pid 父级id
     * @param i 序号
     * @param name 仓库名
     * @param beans 仓库集合
     */
    private static String way36(String comid,String pid,int i,String name,List<BaseBean> beans){
        DepotManage depot = new DepotManage();
        Random random = new Random();
        int ni = random.nextInt(100);
        depot.setDepotID(ni+"");
        depot.setCompanyID(comid);
        depot.setDepotPID(pid);
        depot.setDepotNum(i);
        depot.setDepotName(name);
        depot.setDepotState("02");
        depot.setDepotType("1");
        beans.add(depot);
        logger.info("调试信息");
        return depot.getDepotID();
    }

    public static void way35() {
        /*// 总重量
        BigDecimal totalWeight = new BigDecimal(15.705);
        // 单种物品的重量数组，这里假设有3种物品，可根据实际情况修改长度和值
        BigDecimal[] singleWeights = {new BigDecimal(0.540), new BigDecimal(0.400), new BigDecimal(0.575)};
        // 物品的种类数
        int numOfTypes = singleWeights.length;

        // 用于存储每种物品的个数
        BigDecimal[] quantities = new BigDecimal[numOfTypes];

        // 计算每种物品的个数
        for (int i = 0; i < numOfTypes; i++) {
            quantities[i] = totalWeight.divide(singleWeights[i]);
            totalWeight = totalWeight.subtract(quantities[i].multiply(singleWeights[i]));
        }*/

        // 总重量
        double totalWeight = 15.705;
        // 单种物品的重量数组，这里假设有3种物品，可根据实际情况修改长度和值
        double[] singleWeights = {0.540, 0.400, 0.575};
        // 物品的种类数
        int numOfTypes = singleWeights.length;

        // 用于存储每种物品的个数
        int[] quantities = new int[numOfTypes];

        // 计算每种物品的个数
        for (int i = 0; i < numOfTypes; i++) {
            quantities[i] = (int) (totalWeight / singleWeights[i]);
            totalWeight -= quantities[i] * singleWeights[i];
        }

        // 输出每种物品的个数
        for (int i = 0; i < numOfTypes; i++) {
            logger.info("调试信息");
        }
    }

    public static void way33() {
        List<String[]> b=new ArrayList<>();
        //new Object[]{名称,库存数,单重}
        b.add(new String[]{"p202411209CURUUQ6JR0000000117","朝湖湾","0","0.27","0"});
        b.add(new String[]{"p2024112049H3WPMKUM0000000003","栀栀乌龙","0","0.540","0"});
        b.add(new String[]{"p202411202VRUYIMBS80000000027","果立方","0","0.400","0"});/*
        b.add(new Object[]{"d",9,14f});
        b.add(new Object[]{"e",4,5.7f});*/
        List<String[]> c=new ArrayList<>();
        try {
            way34("", new BigDecimal("1.355"), BigDecimal.ZERO, b.size(), b); // 调用递归函数，从空字符串开始，限制为n，生成长度为3的组合
            logger.info("调试信息");
            /*if (ReturnRecursion != null&&!ReturnRecursion.equals("")) {
                String[] str=ReturnRecursion.split(",");
                for (int i = 0; i < str.length; i++) {
                    String str_i=str[i];
                    String pid=str_i.substring(0,str_i.indexOf(":"));
                    int num=Integer.parseInt(str_i.substring(str_i.indexOf(":")+1));
                }
            }*/
        }catch (StopRecursiveException e){
            logger.info("调试信息");
        }
    }

    /**
     * 递归函数，用于生成指定长度的组合
     *
     * @param prefix 前缀，用于拼接生成的组合
     * @param target 上限，用于控制生成的组合中数字的和
     * @param length 剩余需要填充的位置数量
     * @param b 待组合的数据列表
     *            列表中的每个元素都是一个数组，数组的第一个元素是字符串，表示组合中的字符，
     */
    public static void way34(String prefix, BigDecimal target,BigDecimal currentSum, int length,List<String[]> b) throws StopRecursiveException {
        // 检查数组长度，如果为0则无需执行后续操作
        if (length == 0) {
            //logger.info("调试信息");
            return;
        }

        // 获取当前循环下的产品数据
        Object[] temp = b.get(length-1);
        // 确保产品数据非空
        if (temp == null) {
            throw new IllegalArgumentException("产品数据出错");
        }

        // 解析产品数据中的数量为整数，用于后续计算
        int n;
        try {
            n = Integer.parseInt(temp[4].toString())<=0?15:Integer.parseInt(temp[4].toString());
        } catch (NumberFormatException e) {
            // 如果转换失败，抛出异常
            throw new IllegalArgumentException("产品数量出错", e);
        }

        // 检查产品数据中的单重是否为Float类型，用于后续计算
        BigDecimal weight;
        try {
            weight = new BigDecimal(temp[3].toString());
        } catch (NumberFormatException e) {
        // 如果不是Float类型，抛出异常
        throw new IllegalArgumentException("产品单重类型出错",e);
        }

        // 构建前缀字符串，用于记录当前递归层级的信息-产品名称
        String prefixPart = temp[1] + ":";
        // 遍历可能的值，从0到n，包括n
        for (int i = 0; i <= n; i++) {
            // 如果设置了停止递归的标志，则跳出循环
            if (StopRecursion) break;

            // 计算更新后的和
            BigDecimal updatedBound = currentSum.add(weight.multiply(new BigDecimal(i)));
            // 构建当前层级的字符串表示
            //String p = prefixPart + i + ",";
            String p = prefixPart + "(数量：" + i + "x单重：" + weight+")" + (length == 1 ? "= 结果重量:" + updatedBound : " + ");
            //String p = "("+symbol + ": " + i +")";

            // 如果更新后的结果重量等于目标重量，则记录当前递归路径并停止递归
            BigDecimal updatedBound2 = updatedBound.add(new BigDecimal(0.005));
            BigDecimal updatedBound3 = updatedBound.subtract(new BigDecimal(0.005));
            if (updatedBound2.compareTo(target)>0&&updatedBound3.compareTo(target)<0) {
                ReturnRecursion = prefix + p;
                StopRecursion = true;
                break;
            }

            // 检查递归深度，如果超过15层，则抛出异常以防止栈溢出
            if (length > 15) {
                throw new StackOverflowError("Recursion depth exceeded");
            }

            // 递归调用，继续处理剩余的元素，更新前缀字符串和和值
            way34(prefix + p, target, updatedBound, length - 1, b);
        }
    }


    /**
     * 寻找所有可能的组合，使得组合中数字的和等于目标数
     * 此方法通过递归实现，尝试包括和不包括当前数字两种情况，以寻找所有可能的组合
     *
     * @param target 目标和，函数试图找到数字组合的和
     * @param currentSum 当前数字组合的和
     * @param currentNumber 当前考虑的数字
     * @param currentCombination 当前的数字组合
     */
    public static void way32(int target, int currentSum, int currentNumber, StringBuilder currentCombination) {
        // 如果当前数字组合的和等于目标值，打印该组合
        if (currentSum == target) {
            logger.info("调试信息");
            return;
        }
        // 如果当前数字组合的和超过目标值或当前数字超过9，则停止递归
        if (currentSum > target || currentNumber > 9) {
            return;
        }
        // 递归调用，不包含当前数字的情况
        way32(target, currentSum, currentNumber + 1, currentCombination);
        // 递归调用，包含当前数字的情况
        way32(target, currentSum + currentNumber, currentNumber + 1, currentCombination.append(currentNumber).append(" "));
        // 回溯，去除最后添加的数字，以便尝试其他可能的组合
        currentCombination.delete(currentCombination.length() - 2, currentCombination.length());
    }

    private static void way31() {
        int n = 10;
        for (int a = 0; a <= n; a++) {
            for (int b = 0; b <= n; b++) {
                for (int c = 0; c <= n; c++) {
                        logger.info("调试信息");
                }
            }
        }
    }

    private static void way30() {
        Object[] psuObj = null;

        Object[] zpro_su = {"80", "198.0198", "33.3333", "setup20180510CQZCDKTT690000008373",""};

        List<Object> splist = new ArrayList<>();

        splist.add(new String[]{"0.0000", "p20170220ZVZR76B88M0000000017", "prosetupsub20180510CQZCDKTT690000008374" });
        splist.add(new String[]{"84.6865", "p20170220ZVZR76B88M0000000016", "prosetupsub20180510CQZCDKTT690000008374" });
        splist.add(new String[]{"0.0000", "p20170220ZVZR76B88M0000000020", "prosetupsub20180510CQZCDKTT690000008374" });
        splist.add(new String[]{"0.0000", "p20170605KY3VAANZJG0000000003", "prosetupsub20180510CQZCDKTT690000008374" });
        splist.add(new String[]{"0.0000", "p20170220ZVZR76B88M0000000019", "prosetupsub20180510CQZCDKTT690000008374" });
        splist.add(new String[]{"0.0000", "p20170220ZVZR76B88M0000000022", "prosetupsub20180510CQZCDKTT690000008374" });
        splist.add(new String[]{"0.0000", "p20170220ZVZR76B88M0000000018", "prosetupsub20180510CQZCDKTT690000008374" });

        psuObj = new Object[zpro_su.length + 1];

        System.arraycopy(zpro_su, 0, psuObj, 0, zpro_su.length);

        psuObj[psuObj.length - 1] = splist;
        if (psuObj != null) {
            List<Object> subList = (List<Object>) psuObj[5];

            if (subList != null && !subList.isEmpty()) {
                BigDecimal d = BigDecimal.ZERO;
                for (int j = 0; j < subList.size(); j++) {
                    Object[] psup = (Object[]) subList.get(j);
                    ProProxy pp = null;
                    //logger.info("调试信息");
                    if (psup[0] != null || !psup[0].equals("")) {
                        BigDecimal jinbi = new BigDecimal(psup[0].toString()).multiply(new BigDecimal(1)).multiply(new BigDecimal(100));
                        if (jinbi.compareTo(BigDecimal.ZERO) > 0) {
                            if (psup[1].equals("p20170220ZVZR76B88M0000000017") || psup[1].equals("p20170220ZVZR76B88M0000000016")) {
                                if (pp != null && pp.getState() != null && pp.getState().equals("01")) {
                                    logger.info("调试信息");
                                } else {
                                    d = d.add(jinbi);
                                }
                            } else if (psup[1].equals("p20170220ZVZR76B88M0000000019") || psup[1].equals("p20170220ZVZR76B88M0000000018") || psup[1].equals("p20170220ZVZR76B88M0000000020")) {
                                if (pp != null && pp.getState() != null && pp.getState().equals("01")) {
                                    logger.info("调试信息");
                                } else {
                                    d = d.add(jinbi);
                                }
                            } else if (psup[1].equals("p20170220ZVZR76B88M0000000022")) {
                                //购物积分代理
                                logger.info("调试信息");

                            } else if (psup[1].equals("p20170605KY3VAANZJG0000000003") && psuObj[4] != null && !psuObj[4].equals("")) {
                                logger.info("调试信息");
                            }
                        }
                        if (j == subList.size() - 1 && d.compareTo(BigDecimal.ZERO) == 1) {
                            logger.info("调试信息");
                        }
                    }
                }
            }
        }
    }

    private static void way29() {
        StringBuffer sql = new StringBuffer("select p.goodsname,ps.ppid,ps.re_price,p.brand,p.image,p.goodsid,p.companyid,p.stockSize,p.monthSales,cc.companyAddr,ccom.ccompany_Id,cc.industrytype ,ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2) actPrice,ROUND(pv.vip*(1+nvl(sz.total_pct,0)/100),2) vip ,pa.type ACTTYPE,P.TYPE,P.REMARK,P.CATEGORYNAME,P.CATEGORYID from DT_PRO_SETUP ps inner join dt_ProductPackaging p on p.ppid=ps.ppid inner join DT_ccom_com ccom on p.companyid=ccom.compnay_id inner join dtContactCompany cc on ccom.ccompany_Id=cc.ccompanyID left join dt_set_subsidize sz on sz.gtid = cc.industrytype and sz.stutas = '01' left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state ='00' left join dt_pro_vip pv on pv.ppid = p.ppid and pv.state='00' left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.state <> '04' and pa.state <> '03' and pa.state <> '02' where p.showweixin = ? and p.qualified=?  and p.type not in(?,?,?,?,?,?,'扫码收款')  and cc.industrytype not like '%餐饮%'  and ps.state='00' ");
        //Integer.parseInt(a.substring(0, a.indexOf("/")));

        if (sql.indexOf("and p.goodsname like ?") != -1) {
            sql.replace(sql.indexOf("and p.goodsname like ?"), sql.indexOf("and p.goodsname like ?") + 20, "and p.goodsname like ? and p.tradename like ?");
        } else {
            sql.append("and cc.industrytype like ?");
        }
        logger.info("调试信息");
    }

    private static void way28() {
        scale("D:\\svn-work\\hyplat\\out\\artifacts\\hyplat_Web_exploded\\/upload_files/company201009046vxdyzy4wg0000000025/product/ac4a7bc2aa20406a944fff8af555d96c.jpg", "D:\\svn-work\\hyplat\\out\\artifacts\\hyplat_Web_exploded\\/upload_files/company201009046vxdyzy4wg0000000025/product/ac4a7bc2aa20406a944fff8af555d96csmall.jpg", 414, 431, 0, 0, true);
    }

    private static void way27() {
        String a = "\n" +
                "                    \n" +
                "                <div contenteditable=\"true\" class=\"editablesmall\" style=\"height: 66.9px;\"></div><div class=\"p\" id=\"p2\"> <input id=\"file2\" type=\"file\" name=\"pic\" style=\"display:none\" accept=\"image/png,image/jpeg,image/gif,image/jpg,image/bmp,image/webp\" onchange=\"setImagePreviews(this.id)\"><div class=\"pic\" style=\"float:left; width:100%;padding-top:1%;\"> <img id=\"img0\" data-id=\"file2\" src=\"blob:http://localhost:8080/98aed1a2-d345-44f2-a52f-0db539f821d0\" style=\"display: block; width: 100%; height: auto;\"> </div></div><div contenteditable=\"true\" class=\"editablesmall\" style=\"height: 66.9px;\"></div>";
        String newhtl = a.substring(0, a.indexOf("img" + 0));
        String temp = a.substring(a.indexOf("img" + 0));
    }

    private static void way26() {
        logger.info("调试信息");
        logger.info("调试信息");
        logger.info("调试信息");
    }

    private static void way25() {
        JSONObject ye = HTTPV3.balanceResult("1633069352");
        int available_amount = ye.getInt("available_amount");

    }

    private static void way24() {
        String timeyear = Utilities.getDateString(new Date(), "yyyy-MM-dd");
        String mm = "";
        if (timeyear.substring(5, 6).equals("0")) {
            mm = timeyear.substring(6, 7);
        } else {
            mm += timeyear.substring(5, 7);
        }
        List<Object[]> lists = new ArrayList<Object[]>();
        for (int i = 1; i <= Integer.parseInt(mm); i++) {
            int tt = Utilities.getDayMouth(Integer.parseInt(timeyear.substring(
                    0, 4)), i);
            String time = timeyear.substring(0, 4) + "-" + i + "-" + tt
                    + " 23:59:59";
            logger.info("调试信息");
            logger.info("调试信息");
        }
    }

    private static void way23() {
        List<BaseBean> backList = new ArrayList<BaseBean>();
        //List cusList = new ArrayList<BaseBean>();
        TEshopCusCom tsc = new TEshopCusCom();
        tsc.setCusType("6");
        if (tsc.getCusType().equals("0")) {
            List cusList = new ArrayList<BaseBean>();

            cusList.add(new Object[]{"", "", "", "", "", "5" });
            cusList.add(new Object[]{"", "", "", "", "", "1" });
            cusList.add(new Object[]{"", "", "", "", "", "0.5" });
            cusList.add(new Object[]{"", "", "", "", "", "0" });
            way22(cusList, null, tsc);
        } else {
            List cusList = new ArrayList<BaseBean>();

            cusList.add(new Object[]{"", "", "", "", "", "6" });
            cusList.add(new Object[]{"", "", "", "", "", "5" });
            cusList.add(new Object[]{"", "", "", "", "", "4" });
            cusList.add(new Object[]{"", "", "", "", "", "3" });
            cusList.add(new Object[]{"", "", "", "", "", "2" });
            cusList.add(new Object[]{"", "", "", "", "", "1" });
            cusList.add(new Object[]{"", "", "", "", "", "0.5" });
            cusList.add(new Object[]{"", "", "", "", "", "0" });
            MarKeting keting = new MarKeting();

            if (keting != null) {
                // 推荐人
                TEshopCusCom t1 = new TEshopCusCom();
                t1.setCusType("5");
                // 被推荐人
                TEshopCusCom t2 = new TEshopCusCom();
                t2.setCusType("7");
                // 被推荐人上级
                TEshopCusCom t3 = new TEshopCusCom();
                t3.setCusType("5");
                // 如果推荐人级别大于被推荐人
                if (Float.parseFloat(t1.getCusType()) < Float.parseFloat(t2
                        .getCusType())) {
                    if (cusList != null) {
                        way22(cusList, t1, t2);
                    }

                } else {
                    if (t1.getCusType().equals("7")
                            || t1.getCusType().equals("6")) {
                        // 如果被推荐人级别大于推荐人（客户或者vip客户）

                        if (cusList != null) {
                            if (t3.getCusType().equals("1")) {
                                // 平台
                                TEshopCusCom t0 = new TEshopCusCom();
                                t0.setCusType("0");
                                way22(cusList, t0, t2);
                            } else {
                                way22(cusList, t3, t2);
                            }

                        }
                    } else {

                        if (cusList != null) {
                            way22(cusList, null, tsc);
                        }
                    }
                }
            } else {
                if (cusList != null) {
                    way22(cusList, null, tsc);
                }
            }
        }
    }

    private static void way22(List baseList, TEshopCusCom tEshopCusCom, TEshopCusCom scc) {
        DecimalFormat df = new DecimalFormat("00.000");
        df.setRoundingMode(RoundingMode.DOWN);
        if (baseList.size() > 1) {
            Boolean b = false;
            for (int i = 0; i < baseList.size(); i++) {
                // DtMemberBackup表关联ljc
                //将baseList 集合的cuscoms对象转成数组
                Object cuscoms = baseList.get(i);
                Object[] cuscom = (Object[]) cuscoms;

                //将list集合转成Object对象，再将Object对象转成Object数组
                Object baseList1 = baseList.get(1);
                Object[] baseList2 = (Object[]) baseList1;
                if (tEshopCusCom != null) {
                    if (tEshopCusCom.getCusType().equals("0")) {
                        if (cuscom[5].toString().equals("0")) {
                            logger.info("调试信息");
                        } else {
                            logger.info("调试信息");
                        }
                    } else {
                        if (Float.parseFloat(baseList2[5].toString()) >= 2
                                && Float.parseFloat(baseList2[5].toString()) <= 5) {
                            if (i == 1) {
                                logger.info("调试信息");
                            } else {
                                logger.info("调试信息");
                            }
                        }
                    }
                } else {
                    if (baseList.size() <= 3) {
                        if (cuscom[5].toString().equals("0")) {
                            logger.info("调试信息");
                        } else {
                            logger.info("调试信息");
                        }
                    } else {
                        if (Float.parseFloat(baseList2[5].toString()) == 1) {
                            if (i == 0) {
                                logger.info("调试信息");
                            } else {
                                logger.info("调试信息");
                            }
                        } else {
                            if (i == 0) {
                                logger.info("调试信息");
                            } else {
                                logger.info("调试信息");
                            }
                        }
                    }
                }
            }
        }
    }

    private static void way21() {
        //String q = Float.parseFloat("99030.00") / Float.parseFloat("0.01") * 1000/0.001d+ "";

        logger.info("调试信息");
    }

    private static void way20() {
        logger.info("调试信息");
        String ip = "119.101.118.61";

        logger.info("调试信息");

        System.setProperty("proxyPort", "9999");
        System.setProperty("proxyHost", ip);
        System.setProperty("proxySet", "true");

        logger.info("调试信息");
    }

    private static void way19() {
        logger.info("调试信息");
        String ip = "119.101.118.61";

        logger.info("调试信息");

        System.setProperty("proxyPort", "9999");
        System.setProperty("proxyHost", ip);
        System.setProperty("proxySet", "true");

        logger.info("调试信息");
    }

    private static void way18() {
        logger.info("调试信息");

        IPSpider spider = new IPSpider();
        List<IPBean> list = spider.crawlHttp(3);

        logger.info("调试信息");

        Gson gson = new Gson();
        for (IPBean ipBean : list) {
            logger.info("调试信息");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean valid = IPUtils.isValid(ipBean);
                    if (valid) {
                        //ProxyUtils.setGlobalProxy(ipBean);
                        IPList.add(ipBean);
                    }
                    IPList.increase();
                }
            }).start();
        }

        while (true) {
            // 判断所有副线程是否完成
            if (IPList.getCount() == list.size()) {
                logger.info("调试信息");
                break;
            }
        }


    }

    private static void way17() {
        DefaultAlipayClient alipayClient = AlipayConfig.AlipayConfig();
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        request.setBizContent("{" +
                "\"out_biz_no\":\"" + 111111 + "\"," +
                "\"trans_amount\":\"" + 0.1 + "\"," +
                "\"product_code\":\"TRANS_ACCOUNT_NO_PWD\"," +
                "\"biz_scene\":\"DIRECT_TRANSFER\"," +
                "\"order_title\":\"提现转账\"," +
                "\"payer_info\":{" +
                "\"identity\":\"" + AlipayConfig.partner + "\"," +
                "\"identity_type\":\"ALIPAY_USER_ID\"," +
                "\"name\":\"北京天太世统科技有限公司\"," +
                "\"remark\":\"提现转账\"," +
                "    }" +
                "  }");

        AlipayFundTransUniTransferResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {

        }
        String trade_no = response.getOrderId();
        if (response.isSuccess()) {

        } else {

        }
    }

    private static void way16() {
        DefaultAlipayClient alipayClient = AlipayConfig.AlipayConfig();
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");
        model.setSubject("App支付测试Java");
        model.setOutTradeNo("111111");
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("/ea/sm/ea_getzfb.jspa");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            logger.info("调试信息"); //就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            logger.error("操作异常", e);
        }
    }

    private static void way15txt() {
        try {
            WebBookHelper.拯救苍生从拆CP走起();
        } catch (IOException e) {
            logger.error("操作异常", e);
        } catch (InterruptedException e) {
            logger.error("操作异常", e);
        }
    }

    private static void way14(String categoryName) {
        //连接本地的 Redis 服务
        //Jedis jedis = new Jedis("47.94.207.152");
        // 如果 Redis 服务设置来密码，需要下面这行，没有就不需要
        // jedis.auth("123456");
        logger.info("调试信息");
        //查看服务是否运行
        //logger.info("调试信息");
        //设置 redis 字符串数据
        //jedis.set("runoobkey", "www.runoob.com");
        // 获取存储的数据并输出
        //logger.info("调试信息");

        //Set<String> keys = jedis.keys("*");

        //logger.info("调试信息");
        //jedis.del("qenm");
        JSONObject jo=new JSONObject();
        jo.accumulate("A1", new String[]{"A1", "A3", "B1", "B2", "C1", "C2", "C3", "C4", "C5", "M" });
        jo.accumulate("A2", new String[]{"A2", "B1", "B2", "C1", "C2", "C3", "C4", "C5", "M" });
        jo.accumulate("A3", new String[]{"A3", "C1", "C2", "C3", "C4", "C5" });
        jo.accumulate("B1", new String[]{"B1", "C1", "C2", "C3", "C4", "C5", "M" });
        jo.accumulate("B2", new String[]{"B2", "C1", "C2", "C3", "C4", "C5", "M" });
        jo.accumulate("C1", new String[]{"C1", "C2", "C3", "C4", "C5" });
        jo.accumulate("C2", new String[]{"C2", "C5" });
        jo.accumulate("C3", new String[]{"C3", "C4", "C5" });
        jo.accumulate("C4", new String[]{"C4", "C5" });
        jo.accumulate("C5", new String[]{"C5" });
        jo.accumulate("D", new String[]{"D", "E", "F" });
        jo.accumulate("E", new String[]{"E", "F" });
        jo.accumulate("F", new String[]{"F" });
        jo.accumulate("M", new String[]{"M" });
        jo.accumulate("N", new String[]{"N" });
        jo.accumulate("P", new String[]{"P" });


        JSONArray ja=(JSONArray) jo.get(categoryName);
        logger.info("调试信息");
        for (int i = 0; i < ja.size(); i++) {
            logger.info("调试信息");
        }
        Object [] qenm= (Object[]) ja.toArray();
        logger.info("调试信息");
    }

    private static void way13() {
        /*String a="CashierBills20190325I225KSZUMZ0000098975";
        logger.info("调试信息");*/

        String a = "第1/2页";
        logger.info("调试信息");
        a = a.substring(a.lastIndexOf("第") + 1);
        logger.info("调试信息");
    }

    private static void way12() {
        String title = "章节目录 第1章 白老大之死（1）(1/4)";
        System.out.print(title.substring(title.lastIndexOf("第"), title.length()));

        logger.info("调试信息");
        title = "第26章第一医院(完)(一更+二更+两百雷三更+1.2w营养液四";
        logger.info("调试信息");
        if (title.indexOf("(") >= 0 && title.indexOf(")") >= 0 && WebBookCrawler.method_2(title, "\\(") == WebBookCrawler.method_2(title, "\\)")) {
            String a = title.substring(title.lastIndexOf("(") + 1,
                    title.lastIndexOf(")"));
            if (a.indexOf("第") >= 0) {
                a = a.substring(a.lastIndexOf("第") + 1);
            }
            if (a.indexOf("/") > 0) {
                if (Integer.parseInt(a.substring(0, a.indexOf("/"))) == 1) {
                    title = title.substring(0, title.lastIndexOf("("));
                } else {
                    title = "";
                }
            }
        }

        /*StringBuilder s = new StringBuilder("02 　　“少爷先休息一下，熟悉一下家里的环境，下午我们商量一下选学校的事，可以吗？” 　　鹿行吟点头。 　　他在房间里收拾东西，一样一样地拿出来，所有的东西都填不满那么大的衣橱。 　　霍家别墅干净得不染纤尘，窗户对着庭院湖水，望过去寂静茫然的一大片，寂静得几乎把人淹没。 　　阿姨做好了东西叫他吃饭。 　　上桌是热腾腾的南方菜。她说：“问了好多人，费好多功夫才打听到你是冬桐市来的，我照着那边的口味做的，吃不吃得惯？” 　　少年文文静静的，虽然相貌很精致，但是看着总是带着病气。老一辈看着就容易心疼。 　　鹿行吟又点了点头，这次眼底露出浅浅一个笑意：“吃得惯，谢谢您。” 　　吃完饭后又来了人给鹿行吟量体裁衣。 　　裁缝半跪下来替他试鞋。 　　鹿行吟有点僵，裁缝自己反而笑了：“没事的，不用紧张。” 　　等他们出门后，门缝间隐约漏出一两句他们的闲谈：“这个刚来的小少爷是个宝。” 　　“霍先生和夫人呢？他们应该还收养了两个孩子吧？” 　　“今天周六，听说是把孩子们接出去游玩散心了，毕竟家里回来了一个……思笃小姐和思烈少爷心里难过也是正常的。” 　　“嗨，都正常，不就跟家里要二胎一样嘛，原来的那个肯定不高兴……” 　　人走远了，声音慢慢地听不见了。 　　鹿行吟安静地坐在书桌边，翻看书籍。 　　他带的行李不多，毕竟出发前季冰峰就嘱咐过“什么都不用带，过来了全部换新的”。 　　他唯一带过来的一本书封皮印着粗劣的《金牌奥赛：青少年化学竞赛基础知识解读——繁星中学出版社出品》，冬桐市实验初中校外书摊上买的。 　　二手书五块钱一本，不知经过几人辗转，内页已经泛黄。 　　放在那几年，小地方的人甚至不知道竞赛为何物，这本书自然无人问津，于是让他捡了个漏子。 　　书里内容多而杂，涉及各种体系，却又很多地方语焉不详地一笔带过了。 　　鹿行吟没有手机，这年网络尚且处于蓬勃生长的阶段，他查资料只能摸黑偷偷去学校计算机室，半懂半不懂地啃，啃了前一半，笔记密密麻麻地写满了，还有后一半崭新未动。 　　他托腮慢慢看着，即使已经看过很多遍，他依然沉迷其中，像看一本故事书。 　　第一单元的名字是价层电子对互斥理论，刚好看完这一章，房门被人敲了敲。 　　季冰峰又回来了，说：“我带少爷去学校看看，上午联系好了，我带少爷去看看。校长也想见见你。” 　　鹿行吟合上书本。 　　* 　　“青墨七中是我们S省的名校，历史可追溯到百年前。” 　　“地理位置也十分好，附近的友谊中学是鹰才中学，听说过么？去年出了四十多个清北生。这边位于郊外，也很安静。有走读也有住读，先生和夫人的意思是让您住读，当然，宿舍可以自己选。” 　　周六傍晚，大部分学生都被家长接出去了。校园绿化做得很好，面积大，空旷安静，偶尔有远处男生的呼喊声，篮球撞击地面的回响。 　　鹿行吟听完他介绍，突然问道：“我可以一个人住一间宿舍吗？” 　　季冰峰愣了一下，第一反应是这小少爷刚来一天，已经知道怎么“过得像一个少爷”——但他看鹿行吟的干干净净的眼神，倒是想起了一件事。 　　鹿行吟初中也是寄宿学校，在集体宿舍免费的情况下，他也是坚持走读。 　　他和这个年龄段那些臭烘烘的毛头小子不一样，似乎更爱干净，也更喜欢清静。 　　季冰峰说：“可以，我去替您联系一下。就在旁边教务生活处。您在外面等一等，有空也可以转一转，熟悉一下校园。” 　　高二开学一个月了，他这个时候转学不好办。 　　季冰峰也告诉他了，要他见校长，一是会问一下他的学籍问题，如实作答就好，另一个就是分班，要他自己选择。 　　按道理这些事用不着校长出马，不过这显然是看着霍家的面子。 　　鹿行吟站在走廊外，看着教务处里边零星的灯光，安静地等。 　　天色渐晚，角落处有人“啪”地开了灯。 　　抱怨的声音传过来：“我们到底要罚站多久？蚊子咬我好几个包了。” 　　“都快十一月了有个屁的蚊子。” 　　旁边的人接话，又建议道，“说不定我们站着站着他们就忘了，待会儿直接开溜就行。校长健忘也不是一天两天了。你们知不知道，上次他硬是要我退学，我还在那里哭呢，他吃个饭回来问我过来干嘛的，我说我是孙老师叫来来给您送茶的……他当真了！操，笑死我了。” 　　是几个学生，大约犯了事，笑嘻嘻地在那里贴着墙闲聊。 　　鹿行吟歪歪头，抬眼看见拐角办公室上挂的金属牌，于是走了过去。 　　他不知道里边有没有人，于是敲了敲门。 　　校长办公室的门是厚重的实木门，指尖碰到泛着温润凉意。 　　后边的几个学生都在看他，说话的声音一下子小了下去，接近鸦雀无声。 　　“校长您好，转学生。”鹿行吟说，又等了一会儿，“您有空吗？” 　　寂静过后，里边传来一声闲散的：“请进。” 　　鹿行吟拧动把手，推门进去。 　　他身后，男生们彼此对视一眼，接着爆发出一阵惊天大笑——没笑出来就用嘴捂住了，尽量不发出声音，只差笑岔气。 　　* 　　校长办公桌前。 　　少年按着鼠标玩扫雷，游戏框刷出了不到二十秒，他就已经标记了最后一个红旗，游戏上方显出一个黄色像素笑脸。 　　握着鼠标的这只手修长有力，骨节分明。 　　他比同龄人要高，端正地坐在那里，薄唇抿着，也颇有几分锋利的气质。 　　他有一双桃花眼，是风流多情的样子，可是这双眼到了他脸上就显得冷，反而多出了几分肃杀和戾气。漂亮，却仿佛让人无法接近。 　　门被推开。 　　“我找校长。” 　　鹿行吟看见办公室里只有他一个人，有些迟疑，重复了一遍，“我是转学生，来问学籍和分班登记的事。” 　　空调风嗡嗡地吹着，凉气升腾，他站得很直，脊背挺立，乌黑碎发下露出雪白的脖颈，一双眼沉静温润。 　　是看着就很乖的长相和气质。 　　顾放为勾起唇角，说不清道不明的，玩心起来：“我就是校长。” 　　鹿行吟怔了怔。 　　眼前的人怎么看都不太像——反而像他的同龄人。 　　“天生娃娃脸，我三十了。” 　　顾放为随手抽出一沓文卷，找了张空白的纸张。 　　校长办公室里昂贵的金笔被他拿在手上飞快地转，指节一抬，金笔就稳稳地停住了。 　　“姓名？”顾放为问。 　　“鹿行吟。” 　　“哦，前几天说过的那个是吧，学籍没什么问题。分班的话，开学收心考试和班级流动也过了。”顾放为的声音带着说不清道不明的散漫，“你哪科成绩最好？” 　　鹿行吟愣了一下，又说：“化学。” 　　金笔红墨，沙沙写在纸张上，是龙飞凤舞的笔迹。 　　顾放为写了一道题，慢悠悠地抬起眼睛：“就这一个题，做吧。” 　　题目是个配平题： 　　？Cs3H8=====Cs2B10H10+Cs2B12H12+CsBH4+H2 　　鹿行吟垂眼看到这个题，怔了怔。 　　配平题在初中化学里并不少见，但这道题是竞赛级别的，拥有两种可能解并需要排除一种，而一般学生连Cs这种元素恐怕都没注意过，甚至连元素名称都想不起来。 　　他愣的不是这个题的难度级别，而是感到巧合——因为这就是他今年中考前参与的全国青少年区域化学竞赛，初赛笔试的第一个题，原原本本一丝不差。 　　鹿行吟向他确认：“就这一个题吗？” 　　顾放为看了一眼时间，金色的钢笔重新在指尖旋转了起来：“要我告诉你铯和硼的信息吗？给你多点时间，二十分钟。” 　　他的语气也淡淡的。 　　“不用。” 　　鹿行吟微微俯身，从桌边拿起一支原子笔，而后在少年的注视下，提笔写字。 　　他微微低下头时，可以让人瞧见他乌黑碎发中的发旋，连发旋都规规矩矩的一副好学生气。 　　填入数字，字迹清秀漂亮，笔势也行云流水。 　　他一边写，顾放为的表情就微微地变了。 　　16,1,2,1,8,28. 　　标准答案。 　　没列方程，没试数，没打草稿。 　　整个过程没用三秒。 　　顾放为手里转着的笔一下没拿稳，飞了出去，“啪嗒”一声淹没在气势汹汹的撞门声中。 　　办公室门在此刻被猛地推开，来人西装革履，劈头盖脸地一顿厉喝：“你们在干什么？！” 　　鹿行吟回头。 　　西装革履、自带威严的中年人进来了，他身上戴着胸牌，职务明明白白写着：校长。 　　后边还跟着一溜儿老师，个个表情都很精彩。 　　场面陷入了一阵诡异的沉默，空气紧张起来。 　　鹿行吟没有看到季冰峰。 　　他张了张嘴，万籁俱寂中，面前的少年却突然笑了。 　　很随性、很散漫的那种笑，带着水光的顾放为眯起来，带着某种锋利而朗然的鲜活气息，热烈张扬得如同盛夏的烈阳。手机\\端 一秒記住『』為您提\\供精彩小說\\閱讀 　　他扬了扬下巴，“喂，好学生。” 　　鹿行吟怔了怔。 　　“换个学校吧，这学校不行，别听他们百年名校地吹。” 　　他站起身来，漫不经心地将飞出去的金笔插回办公桌前的笔筒。 　　“三秒一个配平题，你这样的学生应该去隔壁学校。这里生源跟不上，风气一般，过段时间还要公办转民办。别把前途毁在这里。”");

        String[] strC = new String[]{"手机\\端 一秒記住『", "』為您提\\供精彩小說\\閱讀" };

        for (String str : strC) {
            if (s.indexOf(str) >= 0) {
                s = new StringBuilder(s.toString().replaceAll(str, ""));
            }
        }
        logger.info("调试信息");*/

    }

    private static void way11() {
        TEST t = new TEST();
        Document doc = t.getDocument("http://www.weather.com.cn/html/weather/101280101.shtml");
        // 获取目标HTML代码
        Elements elements1 = doc.select("[class=left fl]");
        // 今天
        Elements elements2 = elements1.select("h1");
        String today = elements2.get(0).text();
        logger.info("调试信息");
        // 几号
        Elements elements3 = elements1.select("h2");
        String number = elements3.get(0).text();
        logger.info("调试信息");
        // 是否有雨
        Elements elements4 = elements1.select("[class=wea]");
        String rain = elements4.get(0).text();
        logger.info("调试信息");
        // 高的温度
        Elements elements5 = elements1.select("span");
        String highTemperature = elements5.get(0).text() + "°C";
        logger.info("调试信息");
        // 低的温度
        String lowTemperature = elements5.get(1).text() + "°C";
        logger.info("调试信息");
        // 风力
        Elements elements6 = elements1.select("i");
        String wind = elements6.get(2).text();
        logger.info("调试信息");
    }

    public Document getDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            logger.error("操作异常", e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static void getServerIp() {
        /*String SERVER_IP = null;
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                ip = (InetAddress) ni.getInetAddresses().nextElement();
                SERVER_IP = ip.getHostAddress();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {
                    SERVER_IP = ip.getHostAddress();
                    break;
                } else {
                    ip = null;
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            logger.error("操作异常", e);
        }
        return SERVER_IP;*/
        try {
            //InetAddress address = InetAddress.getLocalHost();//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
            //String hostAddress = address.getHostAddress());//192.168.0.121
            InetAddress address1 = InetAddress.getByName("www.impf2010.com");//获取的是该网站的ip地址，比如我们所有的请求都通过nginx的，所以这里获取到的其实是nginx服务器的IP地
            String hostAddress1 = address1.getHostAddress();//124.237.121.122
            logger.info("调试信息");
            //InetAddress[] addresses = InetAddress.getAllByName("www.impf2010.com");//根据主机名返回其可能的所有InetAddress对象
            //for (InetAddress addr : addresses) {
            //    logger.info("调试信息");//www.baidu.com/14.215.177.38
            //www.baidu.com/14.215.177.37
            //}
        } catch (UnknownHostException e) {
            logger.error("操作异常", e);
        }
    }

    private static void way10() {
        logger.info("调试信息");
    }

    private static void way9() {
        //Float.parseFloat(gold) + Float.parseFloat(notWithDrawGlod(wfjJifen.getWfjJifenId())) > Float.parseFloat("29040.9082000000")
        logger.info("调试信息");
        logger.info("调试信息");
        logger.info("调试信息");
    }

    private static void way8() {
        String a = "{  \"code\" : \"0\",  \"message\" : \"鎴愬姛\",  \"data\" : {    \"result\" : \"1\",    \"description\" : \"涓?嚧\"  }}";
        //利用getBytes将unicode字符串转成UTF-8格式的字节数组
        byte[] utf8Bytes = new byte[0];
        try {
            utf8Bytes = a.getBytes("UTF-8");
            //然后用utf-8 对这个字节数组解码成新的字符串
            String result = new String(utf8Bytes, "UTF-8");
            logger.info("调试信息");
        } catch (UnsupportedEncodingException e) {
            logger.error("操作异常", e);
        }

    }

    private static void way7() {
        String a = "from hy.ea.bo.finance.ProSetup ps,hy.ea.bo.finance.ProductPackaging p where p.ppid = ps.ppid and p.showweixin = ? and ps.comId = ? and  exists ( select 1 from hy.ea.marketing.bo.ProProxy pp where pp.ppid = p.ppID and pp.agentstate = ? ) order by ps.editDate desc";
        logger.info("调试信息");
    }

    private static void way6() {
        try {
            int i = 100 / 0;
            logger.info("调试信息");
        } catch (Exception e) {
            logger.info("调试信息");
            throw new RuntimeException();
        } finally {
            logger.info("调试信息");
        }
        logger.info("调试信息");
    }

    private static void way5() {


        String[] a = {"p20170220ZVZR76B88M0000000019", "p20170220ZVZR76B88M0000000018", "p20170220ZVZR76B88M0000000020" };
        String[] b = new String[4];
        System.arraycopy(a, 0, b, 0, a.length);
        b[3] = "sdfsdfsfsdfsfdsf";
        logger.info("调试信息");
        String[] c = {"4444444444444444444444444444" };


    }

    private static void way4() {
        String str = "egg1,\n" +
                "egg2,\n" +
                "egg3,\n" +
                "egg4,\n" +
                "egg5,\n" +
                "egg6,\n" +
                "egg7,\n" +
                "egg8,\n" +
                "egg9,\n" +
                "egg10,\n" +
                "egg11,\n" +
                "egg12,\n" +
                "egg13,\n" +
                "egg14,\n" +
                "egg15,\n" +
                "egg16,\n" +
                "egg17,\n" +
                "egg18,\n" +
                "egg19,\n" +
                "egg20,\n" +
                "egg21,\n" +
                "egg22,\n" +
                "egg23,\n" +
                "egg24,\n" +
                "egg25,\n" +
                "egg26,\n" +
                "egg27,\n" +
                "egg28,\n" +
                "egg29,\n" +
                "egg30,\n" +
                "egg31,\n" +
                "egg32,\n" +
                "egg33,\n" +
                "egg34,\n" +
                "egg35,\n" +
                "egg36,\n" +
                "egg37,\n" +
                "egg38,\n" +
                "egg39,\n" +
                "egg40,\n" +
                "egg41,\n" +
                "egg42,\n" +
                "egg43,\n" +
                "egg44,\n" +
                "egg45,\n" +
                "egg46,\n" +
                "egg47,\n" +
                "egg48,\n" +
                "egg49,\n" +
                "egg50,\n" +
                "egg51,\n" +
                "egg52,\n" +
                "egg53,\n" +
                "egg54,\n" +
                "egg55,\n" +
                "egg56,\n" +
                "egg57,\n" +
                "egg58,\n" +
                "egg59,\n" +
                "egg60,\n" +
                "egg61,\n" +
                "egg62,\n" +
                "egg63,\n" +
                "egg64,\n" +
                "egg65,\n" +
                "egg66,\n" +
                "egg67,\n" +
                "egg68,\n" +
                "egg69,\n" +
                "egg70,\n" +
                "egg71,\n" +
                "egg72,\n" +
                "egg73,\n" +
                "egg74,\n" +
                "egg75,\n" +
                "egg76,\n" +
                "egg77,\n" +
                "egg78,\n" +
                "egg79,\n" +
                "egg80,\n" +
                "egg81,\n" +
                "egg82,\n" +
                "egg83,\n" +
                "egg84,\n" +
                "egg85,\n" +
                "egg86,\n" +
                "egg87,\n" +
                "egg88,\n" +
                "egg89,\n" +
                "egg90,\n" +
                "egg91,\n" +
                "egg92,\n" +
                "egg93,\n" +
                "egg94,\n" +
                "egg95,\n" +
                "egg96,\n" +
                "egg97,\n" +
                "egg98,\n" +
                "egg99,\n" +
                "egg100,\n";
        logger.info("调试信息");
    }

    private static void way3() {
        logger.info("调试信息");
    }

    private static void way2() {
        /*try {
            if (!conn.isClosed())
                logger.info("调试信息");
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM dtgoodsmanage WHERE goodsid='goodsID20180815CXCYV9YNCH0000018056'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                logger.info("调试信息");
            }
        } catch (SQLException e) {
            logger.error("操作异常", e);
        }*/
    }

    public static void way1() {
        float a = 123.234478f;
        float b = (float) (Math.round(a * 100)) / 100;
        logger.info("调试信息");
        BigDecimal v = BigDecimal.ONE;
        BigDecimal c = new BigDecimal(12).subtract(v).subtract(new BigDecimal(8)).multiply(new BigDecimal(1));
        if (c.compareTo(new BigDecimal(3)) > 0) {
            logger.info("调试信息");
        }
        Object[] tz = {0.30};
        BigDecimal d = new BigDecimal(tz[0].toString()).multiply(new BigDecimal(100));

        logger.info("调试信息");


        BigDecimal vv = BigDecimal.ZERO;
        BigDecimal e = BigDecimal.ZERO;
        BigDecimal zs = BigDecimal.ZERO;
        BigDecimal p = BigDecimal.ZERO;
        zs = new BigDecimal(12.5); //零售价
        p = new BigDecimal(15.00);  //实际价格
        e = new BigDecimal(1.20); //佣金
        logger.info("调试信息");
        logger.info("调试信息");
        if (zs.compareTo(p) <= 0 && e.compareTo(new BigDecimal(3)) == 1) {
            logger.info("调试信息");
        }
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{0, "a", "a" });
        list.add(new Object[]{1, "a", "b" });
        list.add(new Object[]{2, "a", "c" });
        list.add(new Object[]{3, "a", "d" });
        list.add(new Object[]{4, "a", "e" });
        list.add(new Object[]{5, "a", "f" });
        list.add(new Object[]{6, "a", "g" });
        list.add(new Object[]{7, "b", "h" });
        list.add(new Object[]{8, "b", "i" });
        list.add(new Object[]{9, "b", "j" });
        list.add(new Object[]{10, "b", "k" });
        list.add(new Object[]{11, "b", "l" });
        list.add(new Object[]{12, "b", "m" });
        list.add(new Object[]{13, "b", "n" });
        list.add(new Object[]{14, "c", "o" });
        list.add(new Object[]{15, "c", "p" });

        List<Object[]> plist = new ArrayList<>();
        plist.add(new Object[]{0, "a", "a" });
        plist.add(new Object[]{1, "a", "b" });
        plist.add(new Object[]{2, "a", "c" });
        plist.add(new Object[]{3, "a", "d" });
        plist.add(new Object[]{4, "a", "e" });
        plist.add(new Object[]{5, "a", "f" });
        plist.add(new Object[]{6, "a", "g" });
        plist.add(new Object[]{7, "b", "h" });
        plist.add(new Object[]{8, "b", "i" });
        plist.add(new Object[]{9, "b", "j" });
        plist.add(new Object[]{10, "b", "k" });
        plist.add(new Object[]{11, "b", "l" });
        plist.add(new Object[]{12, "b", "m" });
        plist.add(new Object[]{13, "b", "n" });
        plist.add(new Object[]{14, "c", "o" });
        plist.add(new Object[]{15, "c", "p" });

        String psccid = "";
        int num = 0;
        int pnum = 0;

        List<Object[]> clist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Object[] a1 = list.get(i);
            if (num < 3) {
                a1[1] = plist.get(pnum)[2];
                num++;
                if (num == 3) {
                    pnum++;
                }
            } else {
                Object[] b1 = list.get(pnum);
                a1[1] = plist.get(pnum)[2];
                num = 1;
            }
            clist.add(a1);
        }
        //logger.info("调试信息");
        BigDecimal total = new BigDecimal(6);
        logger.info("调试信息");

        logger.info("调试信息");

        logger.info("调试信息");

        logger.info("调试信息");

        String str = "\"品牌： 伊利  \n" +
                "商品名称：伊利牛奶\n" +
                "商品编号：865339\n" +
                "商品毛重：3.52kg\n" +
                "商品产地：中国\n" +
                "资质认证：其它\n" +
                "国产/进口：国产\n" +
                "每箱规格：7-12\n" +
                "包装单位：盒装\n" +
                "是否含糖：含糖\n" +
                "单件容量：201-400ml\n" +
                "脂肪含量：其它\n" +
                "净含量：250ml*12\n" +
                "是否量贩：否\n" +
                "适用人群：通用\n" +
                "适用场景：日常\n" +
                "\n" +
                "\n" +
                "\"\n";
        String str2 = str.replaceAll("\\s*", "");
        logger.info("调试信息");

        logger.info("调试信息");

        logger.info("调试信息");
    }

    public synchronized void log1() throws InterruptedException {
        logger.info("调试信息");
        Thread.sleep(3000);
        logger.info("调试信息");
    }

    public void log2() throws InterruptedException {
        synchronized (this) {
            logger.info("调试信息");
            Thread.sleep(3000);
            logger.info("调试信息");
        }
    }

    public static void scale(String srcImageFile, String result, int width,
                             int height, int x, int y, boolean boo) {
        try {

            BufferedImage src = ImageIO.read(new File(srcImageFile)); //
            if (src != null) {
                int w = src.getWidth(); //
                int h = src.getHeight(); //
                int ww = 0;
                int hh = 0;

                if (w > 0 && h > 0) {
                    if (boo) {
                        if (w / h >= width / height) {
                            if (w > width) {
                                ww = width;
                                hh = (h * width) / w;
                            } else {
                                ww = w;
                                hh = h;
                            }
                        } else {
                            if (h > height) {
                                hh = height;
                                ww = (w * height) / h;
                            } else {
                                ww = w;
                                hh = h;
                            }
                        }
                    } else {
                        if (w < h) {
                            if (w > width) {
                                ww = width;
                                hh = (h * width) / w;
                            } else {
                                ww = w;
                                hh = h;
                            }
                        } else {
                            if (h > height) {
                                hh = height;
                                ww = (w * height) / h;
                            } else {
                                ww = w;
                                hh = h;
                            }
                        }
                    }
                }

                Image image = src
                        .getScaledInstance(ww, hh, Image.SCALE_DEFAULT);
                BufferedImage tag = new BufferedImage(ww, hh,
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(image, x, y, null); //
                g.dispose();
                ImageIO.write(tag, "JPEG", new File(result));//
            }
        } catch (IOException e) {
            logger.error("操作异常", e);
        }
    }

}
