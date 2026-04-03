<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>财务部</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/finance/finance_index.css">

    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
</head>
<body id="">
<div class="pc-box">
    <div class="div-box div-header">
        <ul class="ul-header clearfix">
            <li>
                <a onclick="window.history.go(-1);return false;" target="_self">
                    <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                </a>
            </li>
            <li>
                财务办
            </li>
            <li></li>
        </ul>
        <div class="container">
            <ul class="ul-con">
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/finance/productplan.jsp">
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
                        </div>
                        <p class="p-title">项目设计</p>
                        <p class="p-height">收入项目预算管理</p>
                        <p class="p-height">支出羡慕预算管理</p>
                        <p class="p-height">招标比价审核管理</p>
                        <p class="p-height">项目收支月管理</p>

                    </a>
                </li>
                <li class="clearfix">
<%--                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/finance/zzdetail.jsp">--%>
                        <p class="p-title">调价发标</p>
                        <p class="p-height"></p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
                        </div>
<%--                    </a>--%>
                </li>
                <li class="clearfix">
<%--                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/finance/zzdetail.jsp">--%>
                        <p class="p-title">收标预算</p>
                        <p class="p-height"></p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
                        </div>
<%--                    </a>--%>
                </li>
                <li class="clearfix">
<%--                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/finance/zzdetail.jsp">--%>
                        <p class="p-title">比价审批</p>
                        <p class="p-height"></p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
                        </div>
<%--                    </a>--%>
                </li>
                <li class="clearfix">
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
                    </div>
                    <p class="p-title">申请账单</p>
                    <p class="p-height">资金审转对账管理</p>
                    <p class="p-height">未拨现金申请</p>
                    <p class="p-height">已拨现金申请</p>
                </li>
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/finance/szproof.jsp">
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
                        </div>
                        <p class="p-title">收付管理</p>
                        <p class="p-height">费用使用明细账</p>
                        <p class="p-height">项目现金申请使用明细</p>
                        <p class="p-height">收支凭据</p>
                        <p class="p-height">微分金收支单据</p>
                        <p class="p-height">财务生产管理</p>
                        <p class="p-height">报表</p>
                        </p>

                    </a>
                </li>
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/finance/proofmanage.jsp">
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
                        </div>
                        <p class="p-title">项目凭据</p>
                        <p class="p-height">凭证管理</p>
                        <p class="p-height">相关报表</p>
                        <p class="p-height">管理分析</p>
                        </p>
                    </a>
                </li>
                <li class="clearfix">
<%--                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/finance/zzdetail.jsp">--%>
                        <p class="p-title">明细凭证</p>
                        <p class="p-height"></p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
                        </div>
<%--                    </a>--%>
                </li>
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/finance/zzdetail.jsp">
                        <p class="p-title">总账管理</p>
                        <p class="p-height"></p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
                        </div>
                    </a>
                </li>
            </ul>
        </div>
        <div class="footer div-bottom">
            <ul class="clearfix">
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_23.png" alt="">
                    </div>
                    <p>
                        消息
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_38.png" alt="">
                    </div>
                    <p>
                        通讯
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_08.jpg" alt="">
                    </div>
                    <p>
                        数字
                    </p>
                </li>
                <li class="active">
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_37.png" alt="">
                    </div>
                    <p>
                        5L5C
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_11.png" alt="">
                    </div>
                    <p>
                        我的
                    </p>
                </li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    //计算列表高度
    $(".p-height").each(function () {
        if (document.documentElement.clientWidth<=960){
            if($(this).parent().find(".p-title").text()=="凭据管理"){
                $(this).parent().find(".p-title").css('line-height', $(this).height()*3 + "px");
                $(this).parent().find(".div-more").css('line-height', $(this).height()*3 + "px");
            }else if($(this).parent().find(".p-title").text()=="凭证管理"){
                $(this).parent().find(".p-title").css('line-height', $(this).height() + "px");
                $(this).parent().find(".div-more").css('line-height', $(this).height() + "px");
            }else {
                $(this).parent().find(".p-title").css('line-height', $(this).height()*2 + "px");
                $(this).parent().find(".div-more").css('line-height', $(this).height()*2 + "px");
            }
        }else {
            $(this).parent().find(".p-title").css('line-height', $(this).height() + "px");
            $(this).parent().find(".div-more").css('line-height', $(this).height() + "px");
        }
    });
</script>
</body>
</html>
