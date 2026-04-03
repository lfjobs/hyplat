<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%--<title>联营商城会员需求发布</title>--%>
    <title>发布需求</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta name="format-detection" content="telephone=no, email=no"/>
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/resest.css">
    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/mem_center.css">
    <script type="text/javascript" src="<%=basePath%>/js/WFJClient/zepto.min.js"></script>
</head>
<style>
    p {
        display: inline;
        align-items: center;
        justify-content: center;
        float: right;
        width: 50%;
        line-height: 1.5rem;
        margin-top: 0.4rem;
    }
    .item {
        font-size: .65rem;
        margin-left: 10%;
        vertical-align: top;
    }
    a {
        background: transparent;
        text-decoration: none;
        -webkit-tap-highlight-color: transparent;
        color: #0088cc;
        margin-left: 5%;
    }
    .pzp_R2 {
        display: inline-block;
        font-size: .8rem;
        margin-left: 3%;
        vertical-align: middle;
    }

    .pzp_manger_box2 {
        height: 5rem;
        line-height: 5rem;
        margin-left: 5%;
        display: block;
        color: #232323;
        border-bottom: 1px solid #d6d6d6;
    }

    .mem_need_012 {
        border-color: #3f81c1;
        background: url(../../images/WFJClient/VipCenter/mem_need_01.png) no-repeat center #e6f3ff;
        background-size: 50%;
        vertical-align: middle;
    }
</style>
<body>
<!-- header start  -->
<s:if test="khd==1">
    <header class="mem_header">
        <a target="_self" class="back" id="ret"></a>
        <h1>联营商城会员需求发布</h1>
    </header>
</s:if>
<!-- header start  -->
<header class="mem_header">
    <a target="_self" class="back" id="ret"></a>
    <h1>发布需求</h1>
</header>
<!--  header end  -->
<!-- 页面内容 start  -->
<div class="content">
    <!-- 页面内容 start  -->
    <div class="wrap_page" <s:if test='tle==null||tle==0'>style="margin-top:72px;" </s:if>>
        <section class="pzp_bottom mem_need_wrap">
            <%--<div class="pzp_manger_box pzp_manger_box01">--%>
                <%--<span class="pzp_L mem_need_04"></span>--%>
<%--&lt;%&ndash;                <span class="pzp_R">个人应聘</span>&ndash;%&gt;--%>
            <%--</div>--%>
            <div class="pzp_manger_box pzp_manger_box02 ">
                <span class="pzp_L mem_need_02"></span>
                <span class="pzp_R">家应急</span>
                <p><span class="item"><a>发布需求&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></span>
                    <span class="item"><a>发单</a><a>收单</a> 收支</span></p>
            </div>
            <%--<div class="pzp_manger_box pzp_manger_box03">
                <span class="pzp_L mem_need_07"></span>
                <span class="pzp_R">公司招聘</span>
            </div>--%>
            <div class="pzp_manger_box2 pzp_manger_box01-2">
                <span class="pzp_L mem_need_012"></span>
                <span class="pzp_R2">项目管理</span>
                <p><span class="item"><a id="djfb">初始项目</a><a id="projectPlan">项目计划</a></span>
                <span class="item">比价审批 收付管理</span>
                <span class="item">库存管理</span></p>
            </div>
            <%--<div class="pzp_manger_box pzp_manger_box01">
                <span class="pzp_L mem_need_01"></span>
                <span class="pzp_R">产品发布</span>
            </div>
            <div class="pzp_manger_box pzp_manger_box01-1">
                <span class="pzp_L mem_need_01_1"></span>
                <span class="pzp_R">产品管理</span>
            </div>
            <div class="pzp_manger_box pzp_manger_box02 ">
                <span class="pzp_L mem_need_02"></span>
                <span class="pzp_R">发布收单</span>
            </div>
            <div class="pzp_manger_box pzp_manger_box03">
                <span class="pzp_L mem_need_03"></span>
                <span class="pzp_R">公司招聘</span>
            </div>
            <div class="pzp_manger_box pzp_manger_box04">
                <span class="pzp_L mem_need_04"></span>
                <span class="pzp_R">个人应聘</span>
            </div>
            <div class="pzp_manger_box pzp_manger_box05">
                <span class="pzp_L mem_need_05"></span>
                <span class="pzp_R">公司招商</span>
            </div>
            <div class="pzp_manger_box pzp_manger_box06">
                <span class="pzp_L mem_need_06"></span>
                <span class="pzp_R">产品分类</span>
            </div>
            <div class="pzp_manger_box pzp_manger_box07">
                <span class="pzp_L mem_need_07"></span>
                <span class="pzp_R">收单服务</span>
            </div>--%>
        </section>
    </div>
</div>
<!--  页面内容 end -->
<script>
    var basePath = '<%=basePath%>';
    var state = '${cuscom.state}';
    var staffid = '${cuscom.staffid}';
    var user = '${cuscom.account}';
    var companyId = '${cuscom.companyId}';
    var sccid = "${cuscom.sccId}";
    var khd = "${khd}";
    if (state == 2) {
        $(".pzp_manger_box04").css("display", "none");
    }
    if (state == 1) {
        $(".pzp_manger_box01").css("display", "none");
        $(".pzp_manger_box03").css("display", "none");
        $(".pzp_manger_box01-1").css("display", "none");
        $(".pzp_manger_box05").css("display", "none");
        $(".pzp_manger_box06").css("display", "none");
    }
    $(".pzp_manger_box01").click(function () {
        document.location.href = basePath + "ea/productslaunch/ea_toProductsLaunch.jspa?sccId=" + sccid + "&user=" + user + "&companyId=" + companyId + "&sys=wdhy";
    });
    $(".pzp_manger_box01-1").click(function () {
        document.location.href = basePath + "ea/productslaunch/ea_productsManage.jspa?user=" + user;
    });
    $(".pzp_manger_box02").click(function () {
        //document.location.href = basePath + "/ea/dserve/ea_toaddpage.jspa?staffid=" + staffid + "&sccId=" + sccid + "&tle=" + khd;
        document.location.href = basePath + "page/ea/main/marketing/edmandServe/certificate_Specifics.jsp?staffid=" + staffid + "&sccId=" + sccid;
    });

    //公司招聘
    $(".pzp_manger_box03").click(function () {
        document.location.href = basePath + "/page/ea/main/production/resume/resumeManagement/gscandidates.jsp?sccId=" + sccid + "&back=3";
    });
    $(".pzp_manger_box04").click(function () {
        document.location.href = basePath + "page/ea/main/production/resume/resumeManagement/candidates.jsp?staffid=" + staffid + "&jitype=demand&sccId=" + sccid;
    });

    //发布招商
    $(".pzp_manger_box05").click(function () {
        document.location.href = basePath + "ea/productAgent/ea_investmentPro.jspa?companyId=" + companyId;
    });
    //分类管理
    $(".pzp_manger_box06").click(function () {
        document.location.href = basePath + "ea/productslaunch/ea_queryProductType.jspa?companyId=" + companyId;
    });
    //收单服务
    $(".pzp_manger_box07").click(function () {
        document.location.href = basePath + "page/ea/main/marketing/edmandServe/receiiveServiceList.jsp?sccid=" + sccid;
    });

    //初始项目
    $("#djfb").click(function () {
        // document.location.href = basePath + "ea/productslaunch/ea_productsManage.jspa?user=" + user;
        document.location.href = "<%=basePath%>ea/scBudget/ea_toCostBudgetBillList.jspa?menuId=ng&tenantFlag=personal";
    });

    $("#projectPlan").click(function () {
        document.location.href = "<%=basePath%>ea/scBudget/ea_toPlanCostBudgetBillList.jspa?menuId=ng&tenantFlag=personal";
    });

</script>
<script>
    window.onload = window.onresize = function () {
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //通过屏幕宽度去设置不同的后台根字体的大小
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
    }

</script>

<script>
    $(document).ready(function () {
        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

        $("#ret").click(function() {


            window.history.back();
            return false;

        });
//        if (isAndroid == true) {
//            var obj = document.getElementById("ret");
//            obj.setAttribute("href", "#");
//            obj.setAttribute("onclick", "retAndroid()");
//        } else if (isiOS == true) {
//            var obj = document.getElementById("ret");
//            obj.setAttribute("href", "#");
//            obj.setAttribute("onclick", "retIOS()");
//        }
    });

    //安卓，苹果返回
    function retAndroid() {
        try {
            Android.callAndroidjianli();
        } catch (err) {
            $(".back").removeAttr("onclick");
            $(".back").attr("href", basePath + "ea/consignee/ea_toVipCenter.jspa");
        }
    }

    function retIOS() {
        try {
            calImport('');
        } catch (err) {
            $(".back").removeAttr("onclick");
            $(".back").attr("href", basePath + "ea/consignee/ea_toVipCenter.jspa");
        }
    }
</script>
</body>
</html>