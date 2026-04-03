<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String backurl=request.getParameter("backurl");

%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath%>css/ea/finance/Gold_Pool/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/finance/Gold_Pool/wfj_withdraw.css">
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <title>提现转出</title>
    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var user="${user}";
        var sccid="${sccid}";
        var staffid="${staffid}";
        var khd="${khd}";
        var tocen=0;
        var flag = "${flag}";
        var mark = "${mark}";//标识
        var wfjJifenId="${jifen.wfjJifenId}";
        var jifenscore= Math.floor(${jifen.wfjJifenScore});
        var gold_no;//红包金币数
        var avagold;//可提现金币数
        var bankId= "${bankId}";
        var identifying = "${identifying}"; //判断 个人  登录还是公司登录（移动办公）
        var ccompanyId="${ccompanyId}";
        var state="${state}"; //金币池   用户身份是   个人  还是平台   1是个人   2 是平台
        var object = new Array();
        var type = "${type}" //提现方式
        var jum=${jum};
        var rdate="${rDate}";//当前用户上次提现时间
        var rtype="${rType}";//当前用户上次提现状态
        var SysSecond;
        var InterValObj;
        var bs=0;
        var  account_bank = "${accountInfo.account_bank}";
        var available_amount = ${available_amount};
        var companyID = "${companyID}";

    </script>
</head>
<body>

<c:if test="${mark=='01' }">
    <script language="javascript">location.replace(basePath+"/ea/perinfor/ea_getBankCardInformation.jspa?khd=0&flag=&identifying=&ccompanyId=&staffId="+staffid+"&sccid="+sccid+"&user="+user+"&editType=00&mark=01")</script>
</c:if>
<s:if test="khd==0">
    <header>
        <ul class="clearfix">
            <li>
                <a href="<%=basePath%>/ea/jinbi/ea_gethyjifen.jspa?user=${user}&sccid=${sccid }&khd=${khd}&identifying=${identifying}&flag=${flag}&ccompanyId=${ccompanyId}" class="">

                    <img src="<%=basePath%>images/ea/finance/BenDis/return.png">
                </a>
            </li>
            <li>
                提现转出
            </li>
            <li>

            </li>
        </ul>
    </header>
</s:if>

<div class="content">
    <div class="div-tab">
        <div class="chukuan">
            <div class="chukuan_01 chukuanX clearfix">
                <p>出款账户</p>
                <p>基本账户</p>
                <p>可用余额<span class="g_num_no"></span>元</p>
                <p class="chukuai1">
                    <img src="<%=basePath%>images/ea/finance/BenDis/img_04.png"/>
                </p>
            </div>
            <div class="chukuan_02 chukuanX clearfix">
                <p>出款账户</p>
                <p>微信商户号账户</p>
                <p c>可用余额<span class="g_num_wechat"></span>元</p>
                <p class="chukuai1">
                    <img src="<%=basePath%>images/ea/finance/BenDis/img_04.png"/>
                </p>
            </div>
        </div>
        <div class="shoukuan" >
            <div class="shoukuan_01 shoukuanX" id="clearfix">
                <section class="sec-01 clearfix">
                    <div class="div-left">
                        收款账户
                    </div>
                    <div class="div-right clearfix">
                        <p class="p-img1">
                            <img src=""  class="sele_img"/>
                        </p>
                        <div class="div-txt sele_t">
                            <input id="sele_id" type="hidden"/>
                            <p class="sele_name"></p>
                            <p class="sele_num"></p>
                            <%--<div id="branchName" style="display:none;"></div>--%>
                            <input type="hidden" id="branchName" value=""/>
                        </div>
                        <p class="p-img2">
                            <img src="<%=basePath%>images/ea/finance/BenDis/img_06.png"/>
                        </p>
                    </div>
                </section>

            </div>
            <div class="shoukuan_02 shoukuanX">
                <section class="sec-04 clearfix">
                    <div class="div-left">
                        收款账户
                    </div>
                    <div class="div-right clearfix">
                        <p class="p-img1">
                            <img src="<%=basePath%>images/ea/finance/BenDis/img_03.png" class="wec_img"/>
                        </p>
                        <div class="div-txt">
                            <p>${accountInfo.account_bank}</p>
                            <p>${accountInfo.account_number}</p>
                        </div>
                        <!--<p class="p-img2">
                            <img src="img/img_05.png"/>
                        </p>-->
                    </div>
                </section>
            </div>
        </div>
    </div>
    <form action="" method="post">
        <div class="div-bottom">
            <p>转出金额(元)</p>
            <input type="number" class="gold_inp" placeholder="手续费3元 请输入转出金额 最低100元" id="but1" value="" style="outline: 0;" />
            <div>
                受理方式
                <span class="sltype">实时</span>
            </div>
        </div>
        <div class="div-button">
            <input type="button" name="" class="exchange" id="exchange" value="确认" />
        </div>
    </form>
    <div class="div-tishi">
        <h5>
            温馨提示
        </h5>
        <div>
            <p>
                基本账户
            </p>
            <ul>
                <li>
                    1.限制一个小时提现转出一次
                </li>
                <li>
                    2.一次提现手续费3元
                </li>
                <li>
                    3.一次最少提现100元
                </li>
                <li>
                    4.提现到微信零钱:单笔单日限额5000元
                </li>
                <li>
                    5.提现到支付宝:个人支付宝账户单笔限额5万元
                    企业支付宝账户单笔限额10万元;
                    未实名账户单日限额1000元
                </li>
                <li>
                    6.提现到银行卡:单笔单日限额2万元
                </li>
            </ul>
        </div>
        <div>
            <p>
                微信商户账户
            </p>
            <ul>
                <li>1.提现免手续费，</li>
            </ul>
        </div>
    </div>
</div>
<div class="div-box">
    <div>
        <p>
            金额格式错误
            <!--请输入非零开头的最多带两位小数的数字-->
        </p>
    </div>
</div>
<div class="div-zhanghu1">
    <div>
        <p>
            基本账户
        </p>
        <p class="wechatzh">
            微信商户号账户
        </p>
    </div>
</div>
<div class="div-zhanghu2">
    <div>
        <p>
            银行卡
        </p>
        <p>
            微信
        </p>
        <p>
            支付宝
        </p>
    </div>
</div>
<!-- 弹窗 -->
<div id="prompt" style="width: 100%;display: none;">
    <center>
        <div>
            <span style="position: relative;top: 19.8%;"></span>
        </div>
    </center>
</div>  <!-- 弹窗 -->

<!--正在加载/正在发布 遮罩层 开始-->
<div class="overlay_text" style="background-color:rgba(0,0,0,0.3)">
    <span>正在转出，请稍候……</span>
</div>
<div class="overlay">
    <div class="popup_pay">
        <div class="pay_hd clearfix">
            <span>请输入支付密码</span>
            <a href="javascript:;" class="close_btn"></a>
        </div>
        <div class="pay_bd">
            <div>
                <span class="rmb_ico">￥</span><span class="sum_num"></span>
            </div>
            <div class="pay_text">转出金额</div>
        </div>
        <div class="pay_fd">
            <ul class="pay_label clearfix">
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
            <input type="tel" maxlength="6" class="pay_inp">
        </div>
    </div>
</div>
</body>
<script src="<%=basePath%>js/ea/finance/Gold_Pool/wfj_goldCash.js?version=20220929"></script>
<script type="text/javascript">
$(function(){


    if($(".sele_t").html().indexOf("微信账户")!=-1||$(".sele_t").html().indexOf("支付宝账户")!=-1){
    $(".sltype").text("实时");
}else{

    $(".sltype").text("T+1");
}

})

    $(".div-box").click(function(){
        $(this).hide();
    })
    //账户选择
    $(".chukuai1").click(function(){
        $(".div-zhanghu1").show();
    })
    $(".div-zhanghu1").click(function(){
        $(".div-zhanghu1").hide();
    })
    $(".div-zhanghu1 p").click(function(){
        $(".chukuan").find(".chukuanX").hide();
        $(".chukuan").find(".chukuanX").eq($(this).index()).show();
        $(".shoukuan").find(".shoukuanX").hide();
        $(".shoukuan").find(".shoukuanX").eq($(this).index()).show();
        $(this).parents(".div-zhanghu1").hide();
    })
    $(".div-zhanghu1 p").click(function(){
        $(".chukuan").find(".chukuanX").hide();
        $(".chukuan").find(".chukuanX").eq($(this).index()).show();
        $(".shoukuan").find(".shoukuanX").hide();
        $(".shoukuan").find(".shoukuanX").eq($(this).index()).show();
        $(this).parents(".div-zhanghu1").hide();
        if($(this).index()==0){
            $("#but1").attr("placeholder","手续费3元 请输入转出金额 最低100元");
            if($(".sele_t").html().indexOf("微信账户")!=-1||$(".sele_t").html().indexOf("支付宝账户")!=-1){
                $(".sltype").text("实时");
            }else{

                $(".sltype").text("T+1");
            }


        }else{
            $("#but1").attr("placeholder","免手续费 请输入转出金额")
            $(".sltype").text("T+1");
        }

        $(".gold_inp").val(null);
        $("#exchange").val("确认");
        $("#exchange").css({"background": "#cdcdcd"});//改变按钮的颜色
    })

    $(".div-zhanghu2 p").click(function(){
        $(".shoukuan_01").find("section").hide();
        $(".shoukuan_01").find("section").eq($(this).index()).show();
        $(this).parents(".div-zhanghu2").hide();
    })


</script>
</html>
