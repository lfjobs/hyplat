<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="en" id="html">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
    <title>积分账单</title>
    <script type="text/javascript" src="<%=basePath%>js/ea/finance/Gold_Pool/setHtmlFont.js"></script>

    <script src="<%=basePath%>js/ea/finance/Gold_Pool/jquery.min.js"></script>
    <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/touchwipe.js" type="text/javascript" charset="utf-8"></script>
    <script language="javascript" src="<%=basePath%>js/ea/marketing/supermarket/LodopFuncs.js"></script>
    <object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0 style="position: fixed;">
        <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
    </object>
    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var user="${account}";
        var sccid="${sccid}";
        var staffid="${staffid}";
        var khd="${khd}";
        var t;//计时器
        var pagenumber=0;
        var pagecount=0;
        var flag = "${flag}";
        var identifying = "${identifying}"; //判断 个人  登录还是公司登录（移动办公）
        var ccompanyId="${ccompanyId}";
        var object = new Array();
        var inAndExp = "A";
        var staffName="${param.staffName}";
        var LODOP; //声明为全局变量
        var posNum = "${param.posNum}";
        var ccompanyID = "${param.ccompanyID}";
    </script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/finance/Gold_Pool/integral_bills.css">
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/bonuspoints.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/finance/Gold_Pool/date.css"/>
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/date.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/iscroll_date.js" type="text/javascript" charset="utf-8"></script>
    <style type="text/css">

    </style>
</head>
<body>
<div id="datePlugin"></div>
<section id="section_screen">
    <div class="content clearfix">
        <ul>
            <li>
                <p>类型</p>
                <div class="dropdown">
                    <input class="input_select"  type="text" placeholder="请选择类型" readonly="readonly" />
                    <span style="display: none" id="type"></span>
                    <ul class="type">
                        <li>
                            <a href="javascript:void(0);" rel="">全部</a>
                        </li>
                    </ul>
                </div>
            </li>
            <li class="oper" style="display: none">
                <p>操作人</p>
                <div class="dropdown">
                    <input class="input_select"  type="text" placeholder="请选择操作人" readonly="readonly"  />
                    <span style="display: none" id="operator"></span>
                    <ul class="operator">
                        <li>
                            <a href="javascript:void(0);" rel="">全部</a>
                        </li>
                    </ul>
                </div>
            </li>
            <li>
                <p>时间</p>
                <section>
                    <input type="text" placeholder="选择时间" name=""  id="sdate" value="" readonly="readonly" />
                    <input type="text" placeholder="选择时间" name=""  id="edate" value="" readonly="readonly" />
                </section>
            </li>
        </ul>
        <footer>
            <input type="button" name="" id="" value="确定" />
        </footer>
    </div>
</section>
<header>
    <ul>
        <li><a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/ea/finance/Gold_Pool/left.png" alt=""></a></li>
        <li>积分账单</li>
        <li></li>
    </ul>
</header>
<section class="Recharge">
    <article>
        <ul class="top">
            <li id="income" class="active">收入<span class="income"><fmt:formatNumber value="${inexp.get('A')}" groupingUsed="true"/></span></li>
            <li id="expend">支出<span class="exp"><fmt:formatNumber value="${inexp.get('M')}" groupingUsed="true"/></span></li>
            <input type="button" id="btn_screen" value="筛选">
        </ul>
        <ul class="time">
            <li class="active">今天</li>
            <li>昨天</li>
            <li>本月</li>
            <li>上月</li>
            <li>本年</li>
        </ul>
        <div class="z-score">
            <p>总积分<span><fmt:formatNumber value="${bp.bonusPointScore==null?0:bp.bonusPointScore }" groupingUsed="true"/></span></p>
            <c:if test="${param.platType eq 'screen' }">
                <input class="print" type="button" value="打印" style="margin-top: 0.3rem;float: right;margin-right: 0.3rem;display: none">
                <input class="topup" type="button" value="充值" style="margin-top: 0.3rem;float: right;margin-right: 0.3rem;">
            </c:if>
            <c:if test="${param.platType != 'screen' }">
                <input class="print" type="button" value="打印" style="display: none">
            </c:if>
        </div>
        <ul class="box">

        </ul>
        <div class="bottom">
            <p>总积分<span><fmt:formatNumber value="${bp.bonusPointScore==null?0:bp.bonusPointScore }" groupingUsed="true"/></span></p>
        </div>
        <img src="<%=basePath%>/images/ea/finance/Gold_Pool/top.png" alt="" id="top">
    </article>
</section>
</body>
</html>