package hy.ea.bo.finance;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 预算、收支单管理：CashierBills
 *
 * @author 杨金歌
 */
public class CashierBills implements BaseBean, Cloneable, ExcelBean, java.io.Serializable {
    private String cashierBillsID;
    private String cashierBillsKey;
    private String groupCompanySn; //集团标识
    private String pcompanyID;
    private String pcompanyName;//父公司name
    private String companyID;
    private String companyName;//当前公司name
    private String organizationID; //标示数据属于哪个部门
    private String departmentID;// 部门ID 填单选择的部门
    private String departmentName;//部门name 填单选择的部门
    private String companyBankNum;// 公司银行账号  //微分金会员类别 订单 所属公司订单
    private Date cashierDate;// 单据日期下  单日期

    private String PbillsTypeID;// 父单据类型id
    private String billsType;// 单据类型

    private String journalNum;// 凭证号
    private String trade_no;//第三方支付交易号
    private String staffID;// 责任人ID
    private String staffName;//责任人name
    private String staffCode;// 责任人编号
    private String billCheck;//票据支票管理

    /**************************制单人信息************************/
    private String inputid;  //制单人员id
    private String inputName;//制单人名称
    private String inputCompanyid; //制单人公司id  //注释微分金订单用户上级公司ID  WK
    private String inputCompanyName; //制单人公司名称//注释微分金订单用户上级公司名字	WK
    private String inputOrganizationID; //制单人部门id//注释微分金订单用户上级公店铺ID	WK
    private String inputOrganizationName; //制单人部门名称//注释微分金订单用户上级店铺名字	WK

    /***********************状态**********************/
    private String status;//00：预算添加初始状态 01：招标前审核中 02：招标前审核通过 03:确定招标  04：招标后审核通过(待主管审核)
    //05:审核中 06：待出纳审核  07：已三审通过 08：三审后已归档  09:待确定收款 10:驳回 11：驳回待修改

    //20:转税务单据 30:未审核作废 40:待确定预算(收入确定预算)  50:已确认预算生成收款单  28：待修改车辆信息

    //41:待拨款 42:审核中 43:审核通过拨款 44:审核通过暂不拨款  45:已收款 46：系统生成   27：库存不足，转生产   29:生产完成

    //库存流程单据状态  22:草稿状态 12：未收货  13：已收货 14：已验货 15：已入库 16：已出库  17：盘库 18：移库 19:销售出库 23:入库失败
    //21:库存已关联单据 25: 未发货 24:报修  26：审核中  27:报废 28:驳回

    //微分金单据所用到的状态    98:关闭  99:作废
    /**************************微分金状态**************************/
    private String wfStatus1;//	00微信公众号支付，01微信app支付 02:h5支付
    private String wfStatus2;//订单购买：00在线支付，01现金支付
    private String wfStatus3;//现金支付：00立即支付，01货到付款，02转他人付款
    private String wfStatus4;//在线支付:00微信支付，01，支付宝支付，02银联支付 03:线下转账 04:钱盒子支付  05：积分支付 06：现金支付（收银机公司用积分代付）07:金币支付 08金币积分混合 09 金币积分和其他付款混合 10 积分+其他 11 金币+其他
    private String fkStatus;//付款未付款状态  00:已付款 01:未付款 02:已出库正在物流 03:用户已收货 04:已分配金币
    //05:申请退货    06:同意退货   07:退货中 08:买家退货，卖家确认收货 09:转账确认
    //10:申请售后 11:同意售后 12：售后中 13:售后成功   14:自动收获时金币不够未分配金币  15:作废
    // 16:申请退款h  17：同意退款  18:退款（退款退货）结束 19部分支付
    //20:已经分配积分

    private String ckStatus;//记录
    private String taxstatus;// 税务单据状态 '00'草稿 '01'待主管审核 '02'待经理审核
    // '03'待财务审计审核 '04' 已报税 '10' 驳回

    private String taxstatus2;// 归档状态    '01'已归档

    /***********工资状态********/
    private String paystatus; //00 初始 01已经分配  02  项目跟踪  03 考评

    /*************************审核人信息***********************/
    private String responsible;// 普通单据主管审核人
    private String accountant;// 普通单据会计审核人
    private String cashier;// 普通单据出纳审核人
    private String competent;// 税务单据主管审核人
    private String manager;// 税务单据经理审核人
    private String financial;// 税务单据财务审计审核人
    //招标前审核信息
    private String examineID;//招标前当前审核人姓名ID
    private String examineName;//招标前当前审核人姓名
    private String examineComID;
    private String examineComName;
    private String examineorgID;
    private String examineorgName;

    private Date taxDate;// 报税日期
    private String contactConnections;// 公司往来关系
    private String phone; // 个人往来关系
    private String accountNum;// 单位银行账户


    //钱盒子
    private String userAccountNum;// 个人银行账户  卡号
    private String snNum;//盒子SN号
    private String backName;//发卡行
    private String boxPayName;//付款人姓名
    private String boxPayTel;//付款人电话
    private String tradeDate;//交易时间
    private Date boxDate;//提交时间


    /**************单据原仓库信息****************/
    private String discountMoney;// 实物仓库ID（当做库房ID）
    private String afterDiscount;// 实物仓库
    private String dataDepotID;// 资料仓库ID
    private String dataDepotName;// 资料仓库名称
    private String bankDepotID;// 银行仓库ID
    private String bankDepotName;// 银行仓库名称

    /**************往来个人****************/
    private String contactUserID;// 往来个人ID
    /****************************如果是驾校产品这里保存的是学员信息-开始***************************/
    /***/
    private String ctUserName;//往来个人name
    /***/
    private String reference;//往来个人电话
    /***/
    private String staffIdentityCard;// 身份证
    /***/
    private String referenceCode;// qq
    /***/
    private String referenceOrganization;// 邮箱
    /***/
    private String staffAddress;//家庭地址路径
    /****************************如果是驾校产品这里保存的是学员信息-结束***************************/
    /**************往来单位****************/
    private String ccompanyID;          //往来单位ID
    private String ccompanyName;        //往来单位name
    private String companyAddr;         //具体地址
    private String companyTel;          //公司电话
    private String cresponsible;        //负责人
    private String responsibleTel;      //负责人电话
    private String industryType;        //行业类别

    /**********************拨款、收款数据********************/
    private String oorgid;   //转账部门id
    private String oorgname;   //转账部门名称
    private String zzAccountNum;   //转账账户
    private String appstyle;  //拨款方式 01	银行转帐 02	银行支票转账 03 支付宝转帐 04 App转账 05 POS机转账 06 现金转账 07:金币兑现 08:金币分配 09:银联退款 10：微信退款 11：支付宝退款12:招聘消耗 13:金币折扣14:积分分配15:计时扣费16:积分抽奖17：短信费用

    /**
     * ******售前客户服务*******
     */
    private String consultStatus;  //咨询跟踪单状态  '00'草稿  '01'待营销审核  '02'待人事审核  '03'已审核  '10'驳回
    private String marketer;  //咨询跟踪单营销审核人
    private String personnel; //咨询跟踪单人事审核人

    private String snumber;  //归档号

    /*******************设备信息******************/
    private String goodsCoding;//设备编号
    private String goodsName;//设备名称
    private String defaultStorage;//统一分类条码
    private String mnemonicCode;//设备品牌
    private String standard;//设备品牌规格
    private String typeID;//设备类型
    private String model;//设备型号
    private String variableID;//设备单位
    private String acquiescestandard;//默认规格
    private String pieces; //张数

    //------ 招生信息 ---------//
    private String assignsID;//分校id  公司子部门id
    private String assignsName;//分校名称 公司子部门名称
    private Date signUpDate;//报名时间
    private String referrerName; //推荐人  当前公司在职人员
    private String referrerID;//推荐ID
    private String referrerPhone; //推荐人电话
    private String referrerIdentityCard; //推荐人身份证
    private String referrerEmail;
    private String referrerAddress;
    private String statusbill; //单据状态判断单据来源    00:拨款自动添加单据 01:收入预算单02：支出预算单03：调拨出库单  04：与微分金相关   10：半成品生产出库  11：成品生产出库 12：用户退款   05：与生产相关单据

    /************项目信息***********/
    private String proID;                    //项目单据id  001:供应商成本  002:金币分配  003:金币兑现  004:购买金币 005:项目预算006:招聘消耗007积分 008:计时扣费 009:积分抽奖 010:金币购物 011：打赏 012:短信扣费
    private String projectName;                //项目名称

    private Date startTime;                    //起时间
    private Date endTime;                    //止时间
    private String xmtype;                    //项目类型
    private String xmtypename;                //项目类型名称
    private String projectCode;           //凭证号（项目编号）
    private String content;                //项目内容
    private String rezhi;                  //项目日志

    private String zctype;//支出类型 分为常规支出cg;和非常规类型fcg;
    private String remark;//备注

    private Date targetStart;//目标起时间
    private Date targetEnd;//目标止时间
    private String targetDeptID;//目标部门ID
    private String targetDeptName;//目标部门name
    private String targetSalerID;//目标业务员ID
    private String targetSalerName;//目标业务员Name

    private String unionpayQueryID;//用于银联退货或者撤销交易（作废）
    private String priceSub;//价钱总和
    private String ssprice;//实际收
    private String zlprice;//找零
    private String wlcomname;  //物流单位
    private String wlyf;  //运费
    private String jNumOrder; //订单号

    //餐饮相关
    private String privateRoom;//包间
    private String waiter;//服务员
    private String waiterID;//服务人员ID；

    private String mealNum;//取餐顺序号

    private String fiveClear;//1人事2办公室3财务4生产5营销

    private String type;                //生产类别  00：订单  01：计划

    //购买省市县乡平台 zzl
    private String platfromid;//记录购买省市县乡平台的id
    private String platfromAccount;//记录新生成的公司手机号
    private String platfromConpanyName;//记录新生成的公司名字

    private String golds;
    private String industryId;//行业类型id
    private String nopush;//无需推送 00 代表无需给商家推送消息

    private String pricetype;//0或者null零售订单 ,1：批发商城订单 ,2:VIP ,3:普通活动 ,4:特价活动
    private String posNum;
    private String address;//地址
    private String coordinate;//坐标
    private String tradeId;//行业id
    private String tradeName;//行业名称

    @Override
    public String[] properties() {

        String[] properties = {
                companyName,
                projectCode,
                projectName,
                xmtypename,
                journalNum,
                billsType,
                departmentName,
                staffName,
                inputName,
                String.format("%1$tF", cashierDate),
                status.equals("00") ? "未审核" : (status.equals("01") ? "招标前审核中"
                        : status.equals("02") ? "招标前审核通过" : status
                        .equals("03") ? "招标中" : status
                        .equals("05") ? ":待会计审核" : status
                        .equals("06") ? ":待出纳审核" : status
                        .equals("07") ? ":已三审通过" : status
                        .equals("08") ? ":三审后已归档" : status
                        .equals("09") ? ":待确定收款" : status
                        .equals("10") ? ":驳回" : status
                        .equals("11") ? "驳回待修改" : status
                        .equals("20") ? ":转税务单据" : status
                        .equals("30") ? "未审核作废" : status
                        .equals("40") ? "待确定预算收入单" : ""),


        };
        return properties;
    }

    public static String[] columnHeadings() {
        String[] titles = {"序号", "公司名称", "项目编号", "项目名称", "主项目名称", "单据凭证号", "单据类别", "责任人部门", "责任人", "制单人", "制单日期", "单据状态"};
        return titles;
    }

    /**
     * 订单导出使用
     *
     * @return
     */
    public static String[] orderHeadings() {
        String[] titles = {"序号", "公司名称", "项目编号", "项目名称", "主项目名称", "单据凭证号", "单据类别", "责任人部门", "责任人", "制单人", "制单日期", "单据状态"};
        return titles;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Object cloneCashierBills() throws CloneNotSupportedException {
        return this.clone();
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((PbillsTypeID == null) ? 0 : PbillsTypeID.hashCode());
        result = prime * result
                + ((accountNum == null) ? 0 : accountNum.hashCode());
        result = prime * result
                + ((accountant == null) ? 0 : accountant.hashCode());
        result = prime
                * result
                + ((acquiescestandard == null) ? 0 : acquiescestandard
                .hashCode());
        result = prime * result
                + ((afterDiscount == null) ? 0 : afterDiscount.hashCode());
        result = prime * result
                + ((appstyle == null) ? 0 : appstyle.hashCode());
        result = prime * result
                + ((assignsID == null) ? 0 : assignsID.hashCode());
        result = prime * result
                + ((assignsName == null) ? 0 : assignsName.hashCode());
        result = prime * result
                + ((bankDepotID == null) ? 0 : bankDepotID.hashCode());
        result = prime * result
                + ((bankDepotName == null) ? 0 : bankDepotName.hashCode());
        result = prime * result
                + ((billCheck == null) ? 0 : billCheck.hashCode());
        result = prime * result
                + ((billsType == null) ? 0 : billsType.hashCode());
        result = prime * result + ((cashier == null) ? 0 : cashier.hashCode());
        result = prime * result
                + ((cashierBillsID == null) ? 0 : cashierBillsID.hashCode());
        result = prime * result
                + ((cashierBillsKey == null) ? 0 : cashierBillsKey.hashCode());
        result = prime * result
                + ((cashierDate == null) ? 0 : cashierDate.hashCode());
        result = prime * result
                + ((ccompanyID == null) ? 0 : ccompanyID.hashCode());
        result = prime * result
                + ((ccompanyName == null) ? 0 : ccompanyName.hashCode());
        result = prime * result
                + ((companyAddr == null) ? 0 : companyAddr.hashCode());
        result = prime * result
                + ((companyBankNum == null) ? 0 : companyBankNum.hashCode());
        result = prime * result
                + ((companyID == null) ? 0 : companyID.hashCode());
        result = prime * result
                + ((companyName == null) ? 0 : companyName.hashCode());
        result = prime * result
                + ((companyTel == null) ? 0 : companyTel.hashCode());
        result = prime * result
                + ((competent == null) ? 0 : competent.hashCode());
        result = prime * result
                + ((consultStatus == null) ? 0 : consultStatus.hashCode());
        result = prime
                * result
                + ((contactConnections == null) ? 0 : contactConnections
                .hashCode());
        result = prime * result
                + ((contactUserID == null) ? 0 : contactUserID.hashCode());
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result
                + ((cresponsible == null) ? 0 : cresponsible.hashCode());
        result = prime * result
                + ((ctUserName == null) ? 0 : ctUserName.hashCode());
        result = prime * result
                + ((dataDepotID == null) ? 0 : dataDepotID.hashCode());
        result = prime * result
                + ((dataDepotName == null) ? 0 : dataDepotName.hashCode());
        result = prime * result
                + ((defaultStorage == null) ? 0 : defaultStorage.hashCode());
        result = prime * result
                + ((departmentID == null) ? 0 : departmentID.hashCode());
        result = prime * result
                + ((departmentName == null) ? 0 : departmentName.hashCode());
        result = prime * result
                + ((discountMoney == null) ? 0 : discountMoney.hashCode());
        result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
        result = prime * result
                + ((examineComID == null) ? 0 : examineComID.hashCode());
        result = prime * result
                + ((examineComName == null) ? 0 : examineComName.hashCode());
        result = prime * result
                + ((examineID == null) ? 0 : examineID.hashCode());
        result = prime * result
                + ((examineName == null) ? 0 : examineName.hashCode());
        result = prime * result
                + ((examineorgID == null) ? 0 : examineorgID.hashCode());
        result = prime * result
                + ((examineorgName == null) ? 0 : examineorgName.hashCode());
        result = prime * result
                + ((financial == null) ? 0 : financial.hashCode());
        result = prime * result
                + ((goodsCoding == null) ? 0 : goodsCoding.hashCode());
        result = prime * result
                + ((goodsName == null) ? 0 : goodsName.hashCode());
        result = prime * result
                + ((groupCompanySn == null) ? 0 : groupCompanySn.hashCode());
        result = prime * result
                + ((industryType == null) ? 0 : industryType.hashCode());
        result = prime
                * result
                + ((inputCompanyName == null) ? 0 : inputCompanyName.hashCode());
        result = prime * result
                + ((inputCompanyid == null) ? 0 : inputCompanyid.hashCode());
        result = prime * result
                + ((inputName == null) ? 0 : inputName.hashCode());
        result = prime
                * result
                + ((inputOrganizationID == null) ? 0 : inputOrganizationID
                .hashCode());
        result = prime
                * result
                + ((inputOrganizationName == null) ? 0 : inputOrganizationName
                .hashCode());
        result = prime * result + ((inputid == null) ? 0 : inputid.hashCode());
        result = prime * result
                + ((journalNum == null) ? 0 : journalNum.hashCode());
        result = prime * result + ((manager == null) ? 0 : manager.hashCode());
        result = prime * result
                + ((marketer == null) ? 0 : marketer.hashCode());
        result = prime * result
                + ((mnemonicCode == null) ? 0 : mnemonicCode.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + ((oorgid == null) ? 0 : oorgid.hashCode());
        result = prime * result
                + ((oorgname == null) ? 0 : oorgname.hashCode());
        result = prime * result
                + ((organizationID == null) ? 0 : organizationID.hashCode());
        result = prime * result
                + ((pcompanyID == null) ? 0 : pcompanyID.hashCode());
        result = prime * result
                + ((pcompanyName == null) ? 0 : pcompanyName.hashCode());
        result = prime * result
                + ((personnel == null) ? 0 : personnel.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((pieces == null) ? 0 : pieces.hashCode());
        result = prime * result + ((proID == null) ? 0 : proID.hashCode());
        result = prime * result
                + ((projectCode == null) ? 0 : projectCode.hashCode());
        result = prime * result
                + ((projectName == null) ? 0 : projectName.hashCode());
        result = prime * result
                + ((reference == null) ? 0 : reference.hashCode());
        result = prime * result
                + ((referenceCode == null) ? 0 : referenceCode.hashCode());
        result = prime
                * result
                + ((referenceOrganization == null) ? 0 : referenceOrganization
                .hashCode());
        result = prime * result
                + ((referrerAddress == null) ? 0 : referrerAddress.hashCode());
        result = prime * result
                + ((referrerEmail == null) ? 0 : referrerEmail.hashCode());
        result = prime * result
                + ((referrerID == null) ? 0 : referrerID.hashCode());
        result = prime
                * result
                + ((referrerIdentityCard == null) ? 0 : referrerIdentityCard
                .hashCode());
        result = prime * result
                + ((referrerName == null) ? 0 : referrerName.hashCode());
        result = prime * result
                + ((referrerPhone == null) ? 0 : referrerPhone.hashCode());
        result = prime * result + ((remark == null) ? 0 : remark.hashCode());
        result = prime * result
                + ((responsible == null) ? 0 : responsible.hashCode());
        result = prime * result
                + ((responsibleTel == null) ? 0 : responsibleTel.hashCode());
        result = prime * result
                + ((signUpDate == null) ? 0 : signUpDate.hashCode());
        result = prime * result + ((snumber == null) ? 0 : snumber.hashCode());
        result = prime * result
                + ((staffAddress == null) ? 0 : staffAddress.hashCode());
        result = prime * result
                + ((staffCode == null) ? 0 : staffCode.hashCode());
        result = prime * result + ((staffID == null) ? 0 : staffID.hashCode());
        result = prime
                * result
                + ((staffIdentityCard == null) ? 0 : staffIdentityCard
                .hashCode());
        result = prime * result
                + ((staffName == null) ? 0 : staffName.hashCode());
        result = prime * result
                + ((standard == null) ? 0 : standard.hashCode());
        result = prime * result
                + ((startTime == null) ? 0 : startTime.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result
                + ((statusbill == null) ? 0 : statusbill.hashCode());
        result = prime * result
                + ((targetDeptID == null) ? 0 : targetDeptID.hashCode());
        result = prime * result
                + ((targetDeptName == null) ? 0 : targetDeptName.hashCode());
        result = prime * result
                + ((targetEnd == null) ? 0 : targetEnd.hashCode());
        result = prime * result
                + ((targetSalerID == null) ? 0 : targetSalerID.hashCode());
        result = prime * result
                + ((targetSalerName == null) ? 0 : targetSalerName.hashCode());
        result = prime * result
                + ((targetStart == null) ? 0 : targetStart.hashCode());
        result = prime * result + ((taxDate == null) ? 0 : taxDate.hashCode());
        result = prime * result
                + ((taxstatus == null) ? 0 : taxstatus.hashCode());
        result = prime * result
                + ((taxstatus2 == null) ? 0 : taxstatus2.hashCode());
        result = prime * result + ((typeID == null) ? 0 : typeID.hashCode());
        result = prime * result
                + ((userAccountNum == null) ? 0 : userAccountNum.hashCode());
        result = prime * result
                + ((variableID == null) ? 0 : variableID.hashCode());
        result = prime * result + ((xmtype == null) ? 0 : xmtype.hashCode());
        result = prime * result
                + ((xmtypename == null) ? 0 : xmtypename.hashCode());
        result = prime * result + ((zctype == null) ? 0 : zctype.hashCode());
        result = prime * result
                + ((zzAccountNum == null) ? 0 : zzAccountNum.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CashierBills other = (CashierBills) obj;
        if (PbillsTypeID == null) {
            if (other.PbillsTypeID != null)
                return false;
        } else if (!PbillsTypeID.equals(other.PbillsTypeID))
            return false;
        if (accountNum == null) {
            if (other.accountNum != null)
                return false;
        } else if (!accountNum.equals(other.accountNum))
            return false;
        if (accountant == null) {
            if (other.accountant != null)
                return false;
        } else if (!accountant.equals(other.accountant))
            return false;
        if (acquiescestandard == null) {
            if (other.acquiescestandard != null)
                return false;
        } else if (!acquiescestandard.equals(other.acquiescestandard))
            return false;
        if (afterDiscount == null) {
            if (other.afterDiscount != null)
                return false;
        } else if (!afterDiscount.equals(other.afterDiscount))
            return false;
        if (appstyle == null) {
            if (other.appstyle != null)
                return false;
        } else if (!appstyle.equals(other.appstyle))
            return false;
        if (assignsID == null) {
            if (other.assignsID != null)
                return false;
        } else if (!assignsID.equals(other.assignsID))
            return false;
        if (assignsName == null) {
            if (other.assignsName != null)
                return false;
        } else if (!assignsName.equals(other.assignsName))
            return false;
        if (bankDepotID == null) {
            if (other.bankDepotID != null)
                return false;
        } else if (!bankDepotID.equals(other.bankDepotID))
            return false;
        if (bankDepotName == null) {
            if (other.bankDepotName != null)
                return false;
        } else if (!bankDepotName.equals(other.bankDepotName))
            return false;
        if (billCheck == null) {
            if (other.billCheck != null)
                return false;
        } else if (!billCheck.equals(other.billCheck))
            return false;
        if (billsType == null) {
            if (other.billsType != null)
                return false;
        } else if (!billsType.equals(other.billsType))
            return false;
        if (cashier == null) {
            if (other.cashier != null)
                return false;
        } else if (!cashier.equals(other.cashier))
            return false;
        if (cashierBillsID == null) {
            if (other.cashierBillsID != null)
                return false;
        } else if (!cashierBillsID.equals(other.cashierBillsID))
            return false;
        if (cashierBillsKey == null) {
            if (other.cashierBillsKey != null)
                return false;
        } else if (!cashierBillsKey.equals(other.cashierBillsKey))
            return false;
        if (cashierDate == null) {
            if (other.cashierDate != null)
                return false;
        } else if (!cashierDate.equals(other.cashierDate))
            return false;
        if (ccompanyID == null) {
            if (other.ccompanyID != null)
                return false;
        } else if (!ccompanyID.equals(other.ccompanyID))
            return false;
        if (ccompanyName == null) {
            if (other.ccompanyName != null)
                return false;
        } else if (!ccompanyName.equals(other.ccompanyName))
            return false;
        if (companyAddr == null) {
            if (other.companyAddr != null)
                return false;
        } else if (!companyAddr.equals(other.companyAddr))
            return false;
        if (companyBankNum == null) {
            if (other.companyBankNum != null)
                return false;
        } else if (!companyBankNum.equals(other.companyBankNum))
            return false;
        if (companyID == null) {
            if (other.companyID != null)
                return false;
        } else if (!companyID.equals(other.companyID))
            return false;
        if (companyName == null) {
            if (other.companyName != null)
                return false;
        } else if (!companyName.equals(other.companyName))
            return false;
        if (companyTel == null) {
            if (other.companyTel != null)
                return false;
        } else if (!companyTel.equals(other.companyTel))
            return false;
        if (competent == null) {
            if (other.competent != null)
                return false;
        } else if (!competent.equals(other.competent))
            return false;
        if (consultStatus == null) {
            if (other.consultStatus != null)
                return false;
        } else if (!consultStatus.equals(other.consultStatus))
            return false;
        if (contactConnections == null) {
            if (other.contactConnections != null)
                return false;
        } else if (!contactConnections.equals(other.contactConnections))
            return false;
        if (contactUserID == null) {
            if (other.contactUserID != null)
                return false;
        } else if (!contactUserID.equals(other.contactUserID))
            return false;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        if (cresponsible == null) {
            if (other.cresponsible != null)
                return false;
        } else if (!cresponsible.equals(other.cresponsible))
            return false;
        if (ctUserName == null) {
            if (other.ctUserName != null)
                return false;
        } else if (!ctUserName.equals(other.ctUserName))
            return false;
        if (dataDepotID == null) {
            if (other.dataDepotID != null)
                return false;
        } else if (!dataDepotID.equals(other.dataDepotID))
            return false;
        if (dataDepotName == null) {
            if (other.dataDepotName != null)
                return false;
        } else if (!dataDepotName.equals(other.dataDepotName))
            return false;
        if (defaultStorage == null) {
            if (other.defaultStorage != null)
                return false;
        } else if (!defaultStorage.equals(other.defaultStorage))
            return false;
        if (departmentID == null) {
            if (other.departmentID != null)
                return false;
        } else if (!departmentID.equals(other.departmentID))
            return false;
        if (departmentName == null) {
            if (other.departmentName != null)
                return false;
        } else if (!departmentName.equals(other.departmentName))
            return false;
        if (discountMoney == null) {
            if (other.discountMoney != null)
                return false;
        } else if (!discountMoney.equals(other.discountMoney))
            return false;
        if (endTime == null) {
            if (other.endTime != null)
                return false;
        } else if (!endTime.equals(other.endTime))
            return false;
        if (examineComID == null) {
            if (other.examineComID != null)
                return false;
        } else if (!examineComID.equals(other.examineComID))
            return false;
        if (examineComName == null) {
            if (other.examineComName != null)
                return false;
        } else if (!examineComName.equals(other.examineComName))
            return false;
        if (examineID == null) {
            if (other.examineID != null)
                return false;
        } else if (!examineID.equals(other.examineID))
            return false;
        if (examineName == null) {
            if (other.examineName != null)
                return false;
        } else if (!examineName.equals(other.examineName))
            return false;
        if (examineorgID == null) {
            if (other.examineorgID != null)
                return false;
        } else if (!examineorgID.equals(other.examineorgID))
            return false;
        if (examineorgName == null) {
            if (other.examineorgName != null)
                return false;
        } else if (!examineorgName.equals(other.examineorgName))
            return false;
        if (financial == null) {
            if (other.financial != null)
                return false;
        } else if (!financial.equals(other.financial))
            return false;
        if (goodsCoding == null) {
            if (other.goodsCoding != null)
                return false;
        } else if (!goodsCoding.equals(other.goodsCoding))
            return false;
        if (goodsName == null) {
            if (other.goodsName != null)
                return false;
        } else if (!goodsName.equals(other.goodsName))
            return false;
        if (groupCompanySn == null) {
            if (other.groupCompanySn != null)
                return false;
        } else if (!groupCompanySn.equals(other.groupCompanySn))
            return false;
        if (industryType == null) {
            if (other.industryType != null)
                return false;
        } else if (!industryType.equals(other.industryType))
            return false;
        if (inputCompanyName == null) {
            if (other.inputCompanyName != null)
                return false;
        } else if (!inputCompanyName.equals(other.inputCompanyName))
            return false;
        if (inputCompanyid == null) {
            if (other.inputCompanyid != null)
                return false;
        } else if (!inputCompanyid.equals(other.inputCompanyid))
            return false;
        if (inputName == null) {
            if (other.inputName != null)
                return false;
        } else if (!inputName.equals(other.inputName))
            return false;
        if (inputOrganizationID == null) {
            if (other.inputOrganizationID != null)
                return false;
        } else if (!inputOrganizationID.equals(other.inputOrganizationID))
            return false;
        if (inputOrganizationName == null) {
            if (other.inputOrganizationName != null)
                return false;
        } else if (!inputOrganizationName.equals(other.inputOrganizationName))
            return false;
        if (inputid == null) {
            if (other.inputid != null)
                return false;
        } else if (!inputid.equals(other.inputid))
            return false;
        if (journalNum == null) {
            if (other.journalNum != null)
                return false;
        } else if (!journalNum.equals(other.journalNum))
            return false;
        if (manager == null) {
            if (other.manager != null)
                return false;
        } else if (!manager.equals(other.manager))
            return false;
        if (marketer == null) {
            if (other.marketer != null)
                return false;
        } else if (!marketer.equals(other.marketer))
            return false;
        if (mnemonicCode == null) {
            if (other.mnemonicCode != null)
                return false;
        } else if (!mnemonicCode.equals(other.mnemonicCode))
            return false;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        if (oorgid == null) {
            if (other.oorgid != null)
                return false;
        } else if (!oorgid.equals(other.oorgid))
            return false;
        if (oorgname == null) {
            if (other.oorgname != null)
                return false;
        } else if (!oorgname.equals(other.oorgname))
            return false;
        if (organizationID == null) {
            if (other.organizationID != null)
                return false;
        } else if (!organizationID.equals(other.organizationID))
            return false;
        if (pcompanyID == null) {
            if (other.pcompanyID != null)
                return false;
        } else if (!pcompanyID.equals(other.pcompanyID))
            return false;
        if (pcompanyName == null) {
            if (other.pcompanyName != null)
                return false;
        } else if (!pcompanyName.equals(other.pcompanyName))
            return false;
        if (personnel == null) {
            if (other.personnel != null)
                return false;
        } else if (!personnel.equals(other.personnel))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        if (pieces == null) {
            if (other.pieces != null)
                return false;
        } else if (!pieces.equals(other.pieces))
            return false;
        if (proID == null) {
            if (other.proID != null)
                return false;
        } else if (!proID.equals(other.proID))
            return false;
        if (projectCode == null) {
            if (other.projectCode != null)
                return false;
        } else if (!projectCode.equals(other.projectCode))
            return false;
        if (projectName == null) {
            if (other.projectName != null)
                return false;
        } else if (!projectName.equals(other.projectName))
            return false;
        if (reference == null) {
            if (other.reference != null)
                return false;
        } else if (!reference.equals(other.reference))
            return false;
        if (referenceCode == null) {
            if (other.referenceCode != null)
                return false;
        } else if (!referenceCode.equals(other.referenceCode))
            return false;
        if (referenceOrganization == null) {
            if (other.referenceOrganization != null)
                return false;
        } else if (!referenceOrganization.equals(other.referenceOrganization))
            return false;
        if (referrerAddress == null) {
            if (other.referrerAddress != null)
                return false;
        } else if (!referrerAddress.equals(other.referrerAddress))
            return false;
        if (referrerEmail == null) {
            if (other.referrerEmail != null)
                return false;
        } else if (!referrerEmail.equals(other.referrerEmail))
            return false;
        if (referrerID == null) {
            if (other.referrerID != null)
                return false;
        } else if (!referrerID.equals(other.referrerID))
            return false;
        if (referrerIdentityCard == null) {
            if (other.referrerIdentityCard != null)
                return false;
        } else if (!referrerIdentityCard.equals(other.referrerIdentityCard))
            return false;
        if (referrerName == null) {
            if (other.referrerName != null)
                return false;
        } else if (!referrerName.equals(other.referrerName))
            return false;
        if (referrerPhone == null) {
            if (other.referrerPhone != null)
                return false;
        } else if (!referrerPhone.equals(other.referrerPhone))
            return false;
        if (remark == null) {
            if (other.remark != null)
                return false;
        } else if (!remark.equals(other.remark))
            return false;
        if (responsible == null) {
            if (other.responsible != null)
                return false;
        } else if (!responsible.equals(other.responsible))
            return false;
        if (responsibleTel == null) {
            if (other.responsibleTel != null)
                return false;
        } else if (!responsibleTel.equals(other.responsibleTel))
            return false;
        if (signUpDate == null) {
            if (other.signUpDate != null)
                return false;
        } else if (!signUpDate.equals(other.signUpDate))
            return false;
        if (snumber == null) {
            if (other.snumber != null)
                return false;
        } else if (!snumber.equals(other.snumber))
            return false;
        if (staffAddress == null) {
            if (other.staffAddress != null)
                return false;
        } else if (!staffAddress.equals(other.staffAddress))
            return false;
        if (staffCode == null) {
            if (other.staffCode != null)
                return false;
        } else if (!staffCode.equals(other.staffCode))
            return false;
        if (staffID == null) {
            if (other.staffID != null)
                return false;
        } else if (!staffID.equals(other.staffID))
            return false;
        if (staffIdentityCard == null) {
            if (other.staffIdentityCard != null)
                return false;
        } else if (!staffIdentityCard.equals(other.staffIdentityCard))
            return false;
        if (staffName == null) {
            if (other.staffName != null)
                return false;
        } else if (!staffName.equals(other.staffName))
            return false;
        if (standard == null) {
            if (other.standard != null)
                return false;
        } else if (!standard.equals(other.standard))
            return false;
        if (startTime == null) {
            if (other.startTime != null)
                return false;
        } else if (!startTime.equals(other.startTime))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (statusbill == null) {
            if (other.statusbill != null)
                return false;
        } else if (!statusbill.equals(other.statusbill))
            return false;
        if (targetDeptID == null) {
            if (other.targetDeptID != null)
                return false;
        } else if (!targetDeptID.equals(other.targetDeptID))
            return false;
        if (targetDeptName == null) {
            if (other.targetDeptName != null)
                return false;
        } else if (!targetDeptName.equals(other.targetDeptName))
            return false;
        if (targetEnd == null) {
            if (other.targetEnd != null)
                return false;
        } else if (!targetEnd.equals(other.targetEnd))
            return false;
        if (targetSalerID == null) {
            if (other.targetSalerID != null)
                return false;
        } else if (!targetSalerID.equals(other.targetSalerID))
            return false;
        if (targetSalerName == null) {
            if (other.targetSalerName != null)
                return false;
        } else if (!targetSalerName.equals(other.targetSalerName))
            return false;
        if (targetStart == null) {
            if (other.targetStart != null)
                return false;
        } else if (!targetStart.equals(other.targetStart))
            return false;
        if (taxDate == null) {
            if (other.taxDate != null)
                return false;
        } else if (!taxDate.equals(other.taxDate))
            return false;
        if (taxstatus == null) {
            if (other.taxstatus != null)
                return false;
        } else if (!taxstatus.equals(other.taxstatus))
            return false;
        if (taxstatus2 == null) {
            if (other.taxstatus2 != null)
                return false;
        } else if (!taxstatus2.equals(other.taxstatus2))
            return false;
        if (typeID == null) {
            if (other.typeID != null)
                return false;
        } else if (!typeID.equals(other.typeID))
            return false;
        if (userAccountNum == null) {
            if (other.userAccountNum != null)
                return false;
        } else if (!userAccountNum.equals(other.userAccountNum))
            return false;
        if (variableID == null) {
            if (other.variableID != null)
                return false;
        } else if (!variableID.equals(other.variableID))
            return false;
        if (xmtype == null) {
            if (other.xmtype != null)
                return false;
        } else if (!xmtype.equals(other.xmtype))
            return false;
        if (xmtypename == null) {
            if (other.xmtypename != null)
                return false;
        } else if (!xmtypename.equals(other.xmtypename))
            return false;
        if (zctype == null) {
            if (other.zctype != null)
                return false;
        } else if (!zctype.equals(other.zctype))
            return false;
        if (zzAccountNum == null) {
            if (other.zzAccountNum != null)
                return false;
        } else if (!zzAccountNum.equals(other.zzAccountNum))
            return false;
        return true;
    }

    public String getCashierBillsID() {
        return cashierBillsID;
    }

    public String getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(String discountMoney) {
        this.discountMoney = discountMoney;
    }

    public String getAfterDiscount() {
        return afterDiscount;
    }

    public void setAfterDiscount(String afterDiscount) {
        this.afterDiscount = afterDiscount;
    }

    public void setCashierBillsID(String cashierBillsID) {
        this.cashierBillsID = cashierBillsID;
    }

    public String getCashierBillsKey() {
        return cashierBillsKey;
    }

    public void setCashierBillsKey(String cashierBillsKey) {
        this.cashierBillsKey = cashierBillsKey;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getContactUserID() {
        return contactUserID;
    }

    public void setContactUserID(String contactUserID) {
        this.contactUserID = contactUserID;
    }

    public String getCcompanyID() {
        return ccompanyID;
    }

    public void setCcompanyID(String ccompanyID) {
        this.ccompanyID = ccompanyID;
    }

    public String getBillsType() {
        return billsType;
    }

    public void setBillsType(String billsType) {
        this.billsType = billsType;
    }

    public String getJournalNum() {
        return journalNum;
    }

    public void setJournalNum(String journalNum) {
        this.journalNum = journalNum;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public Date getCashierDate() {
        return cashierDate;
    }

    public void setCashierDate(Date cashierDate) {
        this.cashierDate = cashierDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataDepotID() {
        return dataDepotID;
    }

    public String getRezhi() {
        return rezhi;
    }

    public void setRezhi(String rezhi) {
        this.rezhi = rezhi;
    }

    public void setDataDepotID(String dataDepotID) {
        this.dataDepotID = dataDepotID;
    }

    public String getDataDepotName() {
        return dataDepotName;
    }

    public void setDataDepotName(String dataDepotName) {
        this.dataDepotName = dataDepotName;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getAccountant() {
        return accountant;
    }

    public void setAccountant(String accountant) {
        this.accountant = accountant;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public String getBankDepotID() {
        return bankDepotID;
    }

    public void setBankDepotID(String bankDepotID) {
        this.bankDepotID = bankDepotID;
    }

    public String getBankDepotName() {
        return bankDepotName;
    }

    public void setBankDepotName(String bankDepotName) {
        this.bankDepotName = bankDepotName;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getUserAccountNum() {
        return userAccountNum;
    }

    public void setUserAccountNum(String userAccountNum) {
        this.userAccountNum = userAccountNum;
    }

    public String getContactConnections() {
        return contactConnections;
    }

    public void setContactConnections(String contactConnections) {
        this.contactConnections = contactConnections;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPcompanyID() {
        return pcompanyID;
    }

    public void setPcompanyID(String pcompanyID) {
        this.pcompanyID = pcompanyID;
    }

    /**
     * 税务单据状态
     *
     * @return
     */
    public String getTaxstatus() {
        return taxstatus;
    }

    public void setTaxstatus(String taxstatus) {
        this.taxstatus = taxstatus;
    }

    /**
     * 公司银行账号
     *
     * @return
     */
    public String getCompanyBankNum() {
        return companyBankNum;
    }

    public void setCompanyBankNum(String companyBankNum) {
        this.companyBankNum = companyBankNum;
    }


    /**
     * 历史税务单据状态
     *
     * @return
     */
    public String getTaxstatus2() {
        return taxstatus2;
    }

    public void setTaxstatus2(String taxstatus2) {
        this.taxstatus2 = taxstatus2;
    }

    /**
     * 单据录入人员信息
     *
     * @return
     */
    public String getInputid() {
        return inputid;
    }

    public void setInputid(String inputid) {
        this.inputid = inputid;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getInputCompanyid() {
        return inputCompanyid;
    }

    public void setInputCompanyid(String inputCompanyid) {
        this.inputCompanyid = inputCompanyid;
    }

    public String getInputCompanyName() {
        return inputCompanyName;
    }

    public void setInputCompanyName(String inputCompanyName) {
        this.inputCompanyName = inputCompanyName;
    }

    public String getInputOrganizationID() {
        return inputOrganizationID;
    }

    public void setInputOrganizationID(String inputOrganizationID) {
        this.inputOrganizationID = inputOrganizationID;
    }

    public String getInputOrganizationName() {
        return inputOrganizationName;
    }

    public void setInputOrganizationName(String inputOrganizationName) {
        this.inputOrganizationName = inputOrganizationName;
    }

    /**
     * 税务单据主管审核人
     *
     * @return
     */
    public String getCompetent() {
        return competent;
    }

    public void setCompetent(String competent) {
        this.competent = competent;
    }

    /**
     * 税务单据经理审核人
     *
     * @param
     */
    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    /**
     * 税务单据财务审计审核人
     *
     * @return
     */
    public String getFinancial() {
        return financial;
    }

    public void setFinancial(String financial) {
        this.financial = financial;
    }

    /**
     * 报税日期
     *
     * @return
     */
    public Date getTaxDate() {
        return taxDate;
    }

    public void setTaxDate(Date taxDate) {
        this.taxDate = taxDate;
    }

    /**
     * 咨询跟踪单营销审核人
     *
     * @return
     */
    public String getMarketer() {
        return marketer;
    }

    public void setMarketer(String marketer) {
        this.marketer = marketer;
    }

    /**
     * 咨询跟踪单人事审核人
     *
     * @return
     */
    public String getPersonnel() {
        return personnel;
    }

    public void setPersonnel(String personnel) {
        this.personnel = personnel;
    }

    /**
     * 咨询跟踪单状态
     *
     * @return
     */
    public String getConsultStatus() {
        return consultStatus;
    }

    public void setConsultStatus(String consultStatus) {
        this.consultStatus = consultStatus;
    }

    public String getSnumber() {
        return snumber;
    }

    public void setSnumber(String snumber) {
        this.snumber = snumber;
    }

    public String getBillCheck() {
        return billCheck;
    }

    public void setBillCheck(String billCheck) {
        this.billCheck = billCheck;
    }

    public String getGoodsCoding() {
        return goodsCoding;
    }

    public void setGoodsCoding(String goodsCoding) {
        this.goodsCoding = goodsCoding;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getDefaultStorage() {
        return defaultStorage;
    }

    public void setDefaultStorage(String defaultStorage) {
        this.defaultStorage = defaultStorage;
    }

    public String getMnemonicCode() {
        return mnemonicCode;
    }

    public void setMnemonicCode(String mnemonicCode) {
        this.mnemonicCode = mnemonicCode;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVariableID() {
        return variableID;
    }

    public void setVariableID(String variableID) {
        this.variableID = variableID;
    }

    public String getAcquiescestandard() {
        return acquiescestandard;
    }

    public void setAcquiescestandard(String acquiescestandard) {
        this.acquiescestandard = acquiescestandard;
    }

    public String getPieces() {
        return pieces;
    }

    public void setPieces(String pieces) {
        this.pieces = pieces;
    }

    public String getPbillsTypeID() {
        return PbillsTypeID;
    }

    public void setPbillsTypeID(String pbillsTypeID) {
        PbillsTypeID = pbillsTypeID;
    }

    public String getPcompanyName() {
        return pcompanyName;
    }

    public void setPcompanyName(String pcompanyName) {
        this.pcompanyName = pcompanyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCtUserName() {
        return ctUserName;
    }

    public void setCtUserName(String ctUserName) {
        this.ctUserName = ctUserName;
    }

    public String getCcompanyName() {
        return ccompanyName;
    }

    public void setCcompanyName(String ccompanyName) {
        this.ccompanyName = ccompanyName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStaffIdentityCard() {
        return staffIdentityCard;
    }

    public void setStaffIdentityCard(String staffIdentityCard) {
        this.staffIdentityCard = staffIdentityCard;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getReferenceOrganization() {
        return referenceOrganization;
    }

    public void setReferenceOrganization(String referenceOrganization) {
        this.referenceOrganization = referenceOrganization;
    }


    public String getStaffAddress() {
        return staffAddress;
    }

    public void setStaffAddress(String staffAddress) {
        this.staffAddress = staffAddress;
    }


    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getCresponsible() {
        return cresponsible;
    }

    public void setCresponsible(String cresponsible) {
        this.cresponsible = cresponsible;
    }

    public String getResponsibleTel() {
        return responsibleTel;
    }

    public void setResponsibleTel(String responsibleTel) {
        this.responsibleTel = responsibleTel;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getAssignsID() {
        return assignsID;
    }

    public void setAssignsID(String assignsID) {
        this.assignsID = assignsID;
    }

    public String getAssignsName() {
        return assignsName;
    }

    public void setAssignsName(String assignsName) {
        this.assignsName = assignsName;
    }

    public Date getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(Date signUpDate) {
        this.signUpDate = signUpDate;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public String getReferrerID() {
        return referrerID;
    }

    public void setReferrerID(String referrerID) {
        this.referrerID = referrerID;
    }

    public String getReferrerPhone() {
        return referrerPhone;
    }

    public void setReferrerPhone(String referrerPhone) {
        this.referrerPhone = referrerPhone;
    }

    public String getReferrerIdentityCard() {
        return referrerIdentityCard;
    }

    public void setReferrerIdentityCard(String referrerIdentityCard) {
        this.referrerIdentityCard = referrerIdentityCard;
    }

    public String getReferrerEmail() {
        return referrerEmail;
    }

    public void setReferrerEmail(String referrerEmail) {
        this.referrerEmail = referrerEmail;
    }

    public String getReferrerAddress() {
        return referrerAddress;
    }

    public void setReferrerAddress(String referrerAddress) {
        this.referrerAddress = referrerAddress;
    }

    public String getOorgid() {
        return oorgid;
    }

    public void setOorgid(String oorgid) {
        this.oorgid = oorgid;
    }

    public String getOorgname() {
        return oorgname;
    }

    public void setOorgname(String oorgname) {
        this.oorgname = oorgname;
    }

    public String getZzAccountNum() {
        return zzAccountNum;
    }

    public void setZzAccountNum(String zzAccountNum) {
        this.zzAccountNum = zzAccountNum;
    }

    public String getStatusbill() {
        return statusbill;
    }

    public void setStatusbill(String statusbill) {
        this.statusbill = statusbill;
    }

    public String getProID() {
        return proID;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getXmtype() {
        return xmtype;
    }

    public void setXmtype(String xmtype) {
        this.xmtype = xmtype;
    }

    public String getXmtypename() {
        return xmtypename;
    }

    public void setXmtypename(String xmtypename) {
        this.xmtypename = xmtypename;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getGroupCompanySn() {
        return groupCompanySn;
    }

    public void setGroupCompanySn(String groupCompanySn) {
        this.groupCompanySn = groupCompanySn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExamineID() {
        return examineID;
    }

    public void setExamineID(String examineID) {
        this.examineID = examineID;
    }

    public String getExamineName() {
        return examineName;
    }

    public void setExamineName(String examineName) {
        this.examineName = examineName;
    }

    public String getExamineComID() {
        return examineComID;
    }

    public void setExamineComID(String examineComID) {
        this.examineComID = examineComID;
    }

    public String getExamineComName() {
        return examineComName;
    }

    public void setExamineComName(String examineComName) {
        this.examineComName = examineComName;
    }

    public String getExamineorgID() {
        return examineorgID;
    }

    public void setExamineorgID(String examineorgID) {
        this.examineorgID = examineorgID;
    }

    public String getExamineorgName() {
        return examineorgName;
    }

    public void setExamineorgName(String examineorgName) {
        this.examineorgName = examineorgName;
    }

    public String getZctype() {
        return zctype;
    }

    public void setZctype(String zctype) {
        this.zctype = zctype;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAppstyle() {
        return appstyle;
    }

    public void setAppstyle(String appstyle) {
        this.appstyle = appstyle;
    }

    public Date getTargetStart() {
        return targetStart;
    }

    public void setTargetStart(Date targetStart) {
        this.targetStart = targetStart;
    }

    public Date getTargetEnd() {
        return targetEnd;
    }

    public void setTargetEnd(Date targetEnd) {
        this.targetEnd = targetEnd;
    }

    public String getTargetDeptID() {
        return targetDeptID;
    }

    public void setTargetDeptID(String targetDeptID) {
        this.targetDeptID = targetDeptID;
    }

    public String getTargetDeptName() {
        return targetDeptName;
    }

    public void setTargetDeptName(String targetDeptName) {
        this.targetDeptName = targetDeptName;
    }

    public String getTargetSalerID() {
        return targetSalerID;
    }

    public void setTargetSalerID(String targetSalerID) {
        this.targetSalerID = targetSalerID;
    }

    public String getTargetSalerName() {
        return targetSalerName;
    }

    public void setTargetSalerName(String targetSalerName) {
        this.targetSalerName = targetSalerName;
    }

    public String getWfStatus1() {
        return wfStatus1;
    }

    public void setWfStatus1(String wfStatus1) {
        this.wfStatus1 = wfStatus1;
    }

    public String getWfStatus2() {
        return wfStatus2;
    }

    public void setWfStatus2(String wfStatus2) {
        this.wfStatus2 = wfStatus2;
    }

    public String getWfStatus3() {
        return wfStatus3;
    }

    public void setWfStatus3(String wfStatus3) {
        this.wfStatus3 = wfStatus3;
    }

    public String getFkStatus() {
        return fkStatus;
    }

    public void setFkStatus(String fkStatus) {
        this.fkStatus = fkStatus;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public String getWfStatus4() {
        return wfStatus4;
    }

    public void setWfStatus4(String wfStatus4) {
        this.wfStatus4 = wfStatus4;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getUnionpayQueryID() {
        return unionpayQueryID;
    }

    public void setUnionpayQueryID(String unionpayQueryID) {
        this.unionpayQueryID = unionpayQueryID;
    }

    public String getPriceSub() {
        return priceSub;
    }

    public void setPriceSub(String priceSub) {
        this.priceSub = priceSub;
    }

    public String getjNumOrder() {
        return jNumOrder;
    }

    public void setjNumOrder(String jNumOrder) {
        this.jNumOrder = jNumOrder;
    }

    public String getWlcomname() {
        return wlcomname;
    }

    public void setWlcomname(String wlcomname) {
        this.wlcomname = wlcomname;
    }

    public String getWlyf() {
        return wlyf;
    }

    public void setWlyf(String wlyf) {
        this.wlyf = wlyf;
    }

    public String getPrivateRoom() {
        return privateRoom;
    }

    public void setPrivateRoom(String privateRoom) {
        this.privateRoom = privateRoom;
    }

    public String getWaiter() {
        return waiter;
    }

    public void setWaiter(String waiter) {
        this.waiter = waiter;
    }

    public String getFiveClear() {
        return fiveClear;
    }

    public void setFiveClear(String fiveClear) {
        this.fiveClear = fiveClear;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSnNum() {
        return snNum;
    }

    public void setSnNum(String snNum) {
        this.snNum = snNum;
    }

    public String getBackName() {
        return backName;
    }

    public void setBackName(String backName) {
        this.backName = backName;
    }

    public String getBoxPayName() {
        return boxPayName;
    }

    public void setBoxPayName(String boxPayName) {
        this.boxPayName = boxPayName;
    }

    public String getBoxPayTel() {
        return boxPayTel;
    }

    public void setBoxPayTel(String boxPayTel) {
        this.boxPayTel = boxPayTel;
    }


    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Date getBoxDate() {
        return boxDate;
    }

    public void setBoxDate(Date boxDate) {
        this.boxDate = boxDate;
    }

    public String getPlatfromid() {
        return platfromid;
    }

    public void setPlatfromid(String platfromid) {
        this.platfromid = platfromid;
    }

    public String getPlatfromAccount() {
        return platfromAccount;
    }

    public void setPlatfromAccount(String platfromAccount) {
        this.platfromAccount = platfromAccount;
    }

    public String getPlatfromConpanyName() {
        return platfromConpanyName;
    }

    public void setPlatfromConpanyName(String platfromConpanyName) {
        this.platfromConpanyName = platfromConpanyName;
    }

    public String getGolds() {
        return golds;
    }

    public void setGolds(String golds) {
        this.golds = golds;
    }

    public String getCkStatus() {
        return ckStatus;
    }

    public void setCkStatus(String ckStatus) {
        this.ckStatus = ckStatus;
    }

    public String getWaiterID() {
        return waiterID;
    }

    public void setWaiterID(String waiterID) {
        this.waiterID = waiterID;
    }

    public String getMealNum() {
        return mealNum;
    }

    public void setMealNum(String mealNum) {
        this.mealNum = mealNum;
    }

    public String getSsprice() {
        return ssprice;
    }

    public void setSsprice(String ssprice) {
        this.ssprice = ssprice;
    }

    public String getZlprice() {
        return zlprice;
    }

    public void setZlprice(String zlprice) {
        this.zlprice = zlprice;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getNopush() {
        return nopush;
    }

    public void setNopush(String nopush) {
        this.nopush = nopush;
    }

    public String getPricetype() {
        return pricetype;
    }

    public void setPricetype(String pricetype) {
        this.pricetype = pricetype;
    }

    public String getPosNum() {
        return posNum;
    }

    public void setPosNum(String posNum) {
        this.posNum = posNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

}
