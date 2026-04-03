package hy.ea.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



public class Constant {
    //制作二维码网站
    public static final String HTTP = "http://qr.liantu.com/api.php?";
    //二维码apiKey值
    public static final String API_KEY = "83a998bddbe09bf1fd5fd633067638e1";
    //测试：http://101.200.124.18/wfj-api-timing-1.0
    public static final String TIMING_DOMAIN = "http://112.126.87.226/wfj-api-timing-1.0";

    //文件路径
    public static final String SYS_CONFIG_PATH = Constant.class.getClassLoader().getResource("hy/ea/util/sys_config.properties").getPath().replaceAll("%20", " ");
    //对接的银行缩写列表(BankList.properties)，银行交易配置文件(ABC.properties),以及银行客户端配置文件(ABC_BankClient.properties)路径
    public static final String BANK_LIST_PATH = Constant.class.getClassLoader().getResource("com/tiantai/nwa/tbank/properties/").getPath().replaceAll("%20", " ");
    //和农业银行对接的相关字典信息的文件路径
    public static final String ABC_DICT_FILE_PATH = Constant.class.getClassLoader().getResource("com/tiantai/nwa/tbank/xml/abc_dict.xml").getPath().replaceAll("%20", " ");
    //银行缩写的文件路径
    public static final String BANK_SX_FILE_PATH = Constant.class.getClassLoader().getResource("com/tiantai/nwa/tbank/xml/banksx.xml").getPath().replaceAll("%20", " ");

    public static final Map<String, String> billTypeNumber = new HashMap<String, String>();
    //工作日设定
    public static final double WORK_DAY = 21.75;
    //工作小时设定
//	public static final double WORK_DAY_HOUR=8;
    /**
     * codepid 为这个值的 为考评项分类数据
     */
    public static final String WAGE_ITEM_ASSORT = "wageItemAssort";

    /**
     * xgb添加
     */
    //音乐apiKey值
    public static final String MUSIC_KEY = "e260519636034660fbd346c7e94210dd";
    //音乐列表接口
    public static final String MUSIC_LIST_URL = "http://apis.baidu.com/geekery/music/query";
    //音乐路径接口
    public static final String MUSIC_PATH_URL = "http://apis.baidu.com/geekery/music/playinfo";

    public static final String ZLY_COMPNAYID = "company201009046vxdyzy4wg0000000065";

    //客户访问测试公司
    public static final String COMPANY_TEXT = "strj";
    //访客公司
    public static final String COMPAYN_ID = "company201009046vxdyzy4wg0000000025";
    //访客部门
    public static final String ORGANIZATION_ID = "organization20110425U539EJC3VF0000012237";
    //访客职务
    public static final String POSTNAME_ID = "departmentPost20150313RAZKHQX2EY0000000006";
    //访客权限
    public static final String CROLE_ID = "crole20150313RAZKHQX2EY0000000008";

    public static final Map<String, String> TY = new HashMap<String, String>();
    //基础数据设置
    public static final Map<String, Object> BASIC_CONFIG = new HashMap<String, Object>();
    //五项目导航设置
    public static final Map<String, Object> CCODES_FIVE = new HashMap<String, Object>();
    //考勤项目预设类别
    public static final List<String> CONFTYPE = new ArrayList<String>();

    //排序保存路径-变量名
    public static final String REORDERURL = "reorderURL";
    //签到规则id（预设）
    public static final String WFJGUIZEID = "WFJGUIZEID";
    /**
     * 证件类型
     */
    public static final String CODE_ZZLX = "scode20100423qr9eg4m2nx0000000006";
    /**
     * 处分类别
     */
    public static final String CODE_CFLB = "scode20100423qr9eg4m2nx0000000004";
    /**
     * 奖励类别
     */
    public static final String CODE_JLLB = "scode20100423qr9eg4m2nx0000000003";
    /**
     * 政治面貌
     */
    public static final String CODE_ZZMM = "scode20100423qr9eg4m2nx0000000001";
    /**
     * 体检内容
     */
    public static final String CODE_TJNR = "scode20100423qr9eg4m2nx0000000002";
    /**
     * 本人关系
     */
    public static final String CODE_BRGX = "scode20100423vw54xx7r4f0000000056";
    /**
     * 岗位类型
     */
    public static final String CODE_GWLX = "scode20100423vw54xx7r4f0000000019";
    /**
     * 岗位情况
     */
    public static final String CODE_GWQK = "scode20100423vw54xx7r4f0000000053";
    /**
     * 专业类型
     */
    public static final String CODE_ZYLX = "scode201004233ern4m24yx0000000077";
    /**
     * 学历类型
     */
    public static final String CODE_XLLX = "scode20100331mk6yn5b5f60000000008";
    /**
     * 学习形式
     */
    public static final String CODE_XXXS = "scode20100423vw54xx7r4f0000000043";
    /**
     * 教育类别
     */
    public static final String CODE_JYLB = "scode20100423vw54xx7r4f0000000050";
    /**
     * 联系类型
     */
    public static final String CODE_LXLX = "scode201004233ern4m24yx0000000262";
    /**
     * 地址类型
     */
    public static final String CODE_DZLX = "scode201004233ern4m24yx0000000258";
    /**
     * 民族
     */
    public static final String CODE_MZLX = "scode20100331mk6yn5b5f60000000006";
    /**
     * 籍贯
     */
    public static final String CODE_JGLX = "scode2010053143wpua87db0000000008";
    /**
     * 人员与公司往来关系
     */
    public static final String CODE_WLGX = "scode20110106hfjes5ucxp0000000017";
    /**
     * groupsn为此值---行业类别数据
     */
    public static final String CODE_HYLB = "hylb";

    public static final String CODE_INDUS = "scode20190522bj56dxj7r40000000002";

    /**
     * groupsn为此值---项目数据
     */
    public static final String CODE_XMLB = "scode201410284shpd9x4fa0000000005";

    public static final Map<String, Map<String, String>> wechatMap = new HashMap<String, Map<String, String>>();
    /**
     * 工资构成类型预设**基本1、职务2、考评3、考勤4、奖惩5, 级差6, 计件7
     ***/
    public static final Map<Long, String> WAGE_ITEM_LX = new LinkedHashMap<Long, String>();

    public static final String SuperGood = "微分金超市消费";
    //拼货拉公司ID
    public static final String PHL_ID = "contactCompany201810227W6XBMAH4A0000023487";

    public static final String PHL_MID = "company201810227W6XBMAH4A0000023467";
    //农贸市场行业平台
    public static final String NMSC_ID = "scode20200715cnjcrn5jm20000000121";

    public static final String NY_ID = "scode20190415raqvqk3uvs0000000762";
    //车辆类型
    public static final String CARTYPE_ID = "scode20120919qah364usw40000000003";
    //车辆使用性质
    public static final String CARUSE_ID = "scode20120919qah364usw40000000002";

    /**
     * 库房类别 智能货柜id
     */
    public static final String DEPOT_VM = "scode20241026gvvqfp64fk0000000003";

    /**
     * 库房类别 柜门id
     */
    public static final String DEPOT_DOOR = "scode20250424wixfqhz9y30000000002";

    /**
     * 库房类别 秤盘id
     */
    public static final String DEPOT_SCALE = "scode20241026gvvqfp64fk0000000004";

    /**
     * 库房类别 自助超市id
     */
    public static final String DEPOT_SM = "";

    /**
     * 秤盘编号长度，以位为单位
     */
    public static final Integer TARGETLENGTH = 6;

    //提现手续费
    public static final int POUNDAGE = 3;
    //微信app开放平台APPID
    public static final String WXSPAPP_APPID = "wxf17107b0e9021507";

    //微信服务商APPID
    public static final String WXSP_APPID = "wxa1b3f84c027804c3";

    //微信服务商AppSecret
    public static final String WXSP_AppSecret = "89b9ff8d2c921fe5511b8ae0da6b060f";

    //微信服务商Mch_id
    public static final String WXSP_Mchid = "1559430361";

    //微信服务商Partnerkey
    public static final String WXSP_Partnerkey = "bjttstkjyxgsdszltpwfjwxhapi65432";

//    public static final String WXSP_SerialNo = "1FE459A6BC1CE09CEFDE4D56A72D0D372A7068BF";
    public static final String WXSP_SerialNo = "141D1F3E4DDFCC2AD795C333E1491DCC7C7E5577";


    //微信默认子商户中联园区APPID
    public static final String WXSP_SUBAPPID = "wx3eff7ad97837b78b";

    //微信默认子商户中联园区
    public static final String WXSP_SUBMchid = "1561059921";

    public static final String gdkey = "60f2afc9a34ec851b0baf7fd83eb75ba";
    //银行类型
    public static final String bank_type = "scode20201223rnzhjs7ap60000000001";
    //银行类型
    public static final String news_type = "scode20210222rnzhjs7ap60000000001";

    //称重误差值
	public  static final String  Error_Num = "0.01";


    static {
        TY.put("01", "ea/accessresource/sajax_ea_getCorporation.jspa?selectedID="); //单位
        TY.put("02", "ea/accessresource/sajax_ea_getSocial.jspa?selectedID="); //人员
        TY.put("03", "ea/accessresource/sajax_ea_getPersonnel.jspa?selectedID="); //部门
        TY.put("04", "ea/accessresource/sajax_ea_getGoods.jspa?selectedID="); //物品

        BASIC_CONFIG.put("scode20141029rnzhjs7ap60000000004", "项目物品基础数据");//项目物品基础数据
        BASIC_CONFIG.put("scode20150501kze3xkwxgv0000000006", "代码元素管理");//代码元素管理
        BASIC_CONFIG.put("scode20141029rnzhjs7ap60000000005", "人事基础数据");//人事基础数据
        BASIC_CONFIG.put("scode20141029rnzhjs7ap60000000006", "办公室基础数据");//办公室基础数据
        BASIC_CONFIG.put("scode20141029rnzhjs7ap60000000026", "财务基础数据");//财务基础数据
        BASIC_CONFIG.put("scode20141029rnzhjs7ap60000000027", "生产基础数据");//生产基础数据
        BASIC_CONFIG.put("scode20141029rnzhjs7ap60000000028", "营销基础数据");//营销基础数据
//		BASIC_CONFIG.put("scode20141030rnzhjs7ap60000000331", "其他基础数据");//其他基础数据

        CCODES_FIVE.put("scode20141028whpjevz6ge0000000009", "人事基础数据");//"人事基础数据"
        CCODES_FIVE.put("scode20141028whpjevz6ge0000000010", "办公室基础数据");//办公室基础数据
        CCODES_FIVE.put("scode20141028whpjevz6ge0000000011", "财务基础数据");//财务基础数据
        CCODES_FIVE.put("scode20141028whpjevz6ge0000000012", "生产基础数据");//生产基础数据
        CCODES_FIVE.put("scode20141028whpjevz6ge0000000013", "营销基础数据");//营销基础数据

        billTypeNumber.put("001", "001");//耗材领用审批单
        billTypeNumber.put("002", "002");//办公室设备借条
        billTypeNumber.put("003", "003");//办公室设备维修审批单
        billTypeNumber.put("004", "004");//各类证书领用审批表
        billTypeNumber.put("005", "005");//图书借条
        billTypeNumber.put("006", "006");//盖章登记表
        billTypeNumber.put("007", "007");//出差审批表
        billTypeNumber.put("008", "008");//请假审批表
        billTypeNumber.put("009", "009");//加班审批表
        billTypeNumber.put("010", "010");//汽车维护检查登记表
        billTypeNumber.put("011", "011");//卫生检查情况计分表
        billTypeNumber.put("012", "012");//派车单
        billTypeNumber.put("013", "013");//客餐申请
        billTypeNumber.put("014", "014");//生产批次号


        CONFTYPE.add("正常");
        CONFTYPE.add("迟到");
        CONFTYPE.add("早退");
        CONFTYPE.add("旷工");
        CONFTYPE.add("补签");


        WAGE_ITEM_LX.put(1l, "基本工资设定");//基本
        WAGE_ITEM_LX.put(2l, "职务工资设定");//职务
        WAGE_ITEM_LX.put(3l, "考评工资设定");//考评
        WAGE_ITEM_LX.put(4l, "考勤工资设定");//考勤
        WAGE_ITEM_LX.put(5l, "奖惩工资设定");//奖惩
        WAGE_ITEM_LX.put(6l, "级差工资设定");//级差
        WAGE_ITEM_LX.put(7l, "计件工资设定");//计件


        //weChat
        /*中联园区*/
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("appID", "wxff4c5683480d6664");
        map1.put("appSecret", "89b9ff8d2c921fe5511b8ae0da6b060f");
        map1.put("mchid", "1238821802");//微信支付商户号
        map1.put("partnerkey", "ttsw123321456mzttst890349liutaip");
        wechatMap.put("wxff4c5683480d6664", map1);

        /*北京天太世统科技有限公司*/
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("appID", "wxde6fd2de39f16f83");
        map2.put("appSecret", "0d06b9aa750551bf6a1d69944dd5ef9c");
        map2.put("mchid", "1250761401");//微信支付商户号
        map2.put("partnerkey", "bjttstkjyxgsliutaipingdszmz12344");
        wechatMap.put("wxde6fd2de39f16f83", map2);

        /*5L5C管理培训平台*/
        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("appID", "wx265de4246f1e537a");
        map3.put("appSecret", "da14fdc8e4387aeda5b40535c4e4c204");
        map3.put("mchid", "1250770201");//微信支付商户号
        map3.put("partnerkey", "5l5cglxtbjttstkjyxgsdszliutaipin");
        wechatMap.put("wx265de4246f1e537a", map3);


        /*汽车驾校联盟*/
        Map<String, String> map4 = new HashMap<String, String>();
        map4.put("appID", "wx398cc59eacdde3bd");
        map4.put("appSecret", "4a62e334a2c833c20d4d0e3f42e7dc9c");
        map4.put("mchid", "1250771801");//微信支付商户号
        map4.put("partnerkey", "qcjxlmwliutaipingbjttstkjyxgs123");
        wechatMap.put("wx398cc59eacdde3bd", map4);

        /*微分金*/
        Map<String, String> map5 = new HashMap<String, String>();
        map5.put("appID", "wxa1b3f84c027804c3");
        map5.put("appSecret", "26d569353c295fa8ad4fcb85a199f631");
        map5.put("mchid", "1250801501");//微信支付商户号
        map5.put("partnerkey", "bjttstkjyxgsdszltpwfjwxhapi12345");
        wechatMap.put("wxa1b3f84c027804c3", map5);


        /*绵阳胜威驾校*/
        Map<String, String> map6 = new HashMap<String, String>();
        map6.put("appID", "wxd9c5a653054ca863");
        map6.put("appSecret", "6b43b77e97ea28b5b9fee59175d6987c");
        map6.put("mchid", "1250797501");//微信支付商户号
        map6.put("partnerkey", "scmyswjxyxgsdszliutaipingwfjdzly");
        wechatMap.put("wxd9c5a653054ca863", map6);

        /*APP微信支付*/
        Map<String, String> map7 = new HashMap<String, String>();
        map7.put("appID", "wxf17107b0e9021507");
        map7.put("appSecret", "bc20c1f38b00b08727676b3f19043535");
        map7.put("mchid", "1445018902");//微信支付商户号
        map7.put("partnerkey", "shuzidiqiuwfj2010ttswliutaipingd");
        wechatMap.put("apppay", map7);


        /*APPe路快车微信支付*/
        Map<String, String> map8 = new HashMap<String, String>();
        map8.put("appID", "wx146ee50ec26dd3cc");
        map8.put("appSecret", "7be4c68697b3334872354e8cbb1d42ae");
        map8.put("mchid", "1445018902");//微信支付商户号
        map8.put("partnerkey", "shuzidiqiuwfj2010ttswliutaipingd");
        wechatMap.put("elkc", map8);

        /*特约商户中联园*/
        Map<String, String> map9 = new HashMap<String, String>();
        map9.put("appID", "wx3eff7ad97837b78b");
        map9.put("appSecret", "f4c049bbb463045452ced89248eea635");
        map9.put("mchid", "1561059921");//微信支付商户号
        map9.put("partnerkey", "bjttstkjyxgsdszltpwfjwxhapi03092");
        wechatMap.put("wx3eff7ad97837b78b", map9);


    }


}
