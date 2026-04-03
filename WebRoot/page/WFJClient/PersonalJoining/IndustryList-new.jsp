<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait"/>
    <meta name="x5-orientation" content="portrait"/>
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/base.css"/>
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/demand.css"/>
    <script src="<%=basePath%>/js/ea/marketing/edmandServe/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/industrylist-new.js"></script>
    <title>行业列表</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
    </script>
</head>
<body>
<header class="com_head" style="position: static">
    <a href="javascript:history.go(-1)" class="back" style="top: 4%"></a>
    <h1>中联园博览</h1>
</header>
<%--<div class="search_frd_ search_company">--%>
    <%--<div class="search_frd">--%>
        <%--<input type="text" class="search">--%>
        <%--<div class="search_">--%>
            <%--<img src="<%=basePath%>/st/images/ico-search.png" alt="">--%>
            <%--<p>搜索行业名称或公司名称</p>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
<div class="wfj_search" id="wfjsearch">
    <div class="wfj_width" style="margin:6.67px auto;height:33.35px; border-radius:6.67px;border: 1px solid #999;background-color: #FFF; width: 92%;">
        <ul>
            <li style="width:86%;margin-left:2%;"><input style="height:31.016px;font-size:16.675px;outline:none;width: 100%;border: none;float: left;" type="text" value="请输入行业名称或公司名称进行查询" onfocus="this.value='';" onblur="if(this.value==''){this.value='请输入行业名称或公司名称进行查询';}"></li>
            <li style="margin-right:2%;text-align:right;"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/iconfont-sousuo.png" id="img" style="height:26.68px;width:auto;padding-top:2px;float: left;">
            </li>
        </ul>
    </div>
</div>

<div class="nest_page" style="background: #f3f3f3;position:static">
    <%--<div class="nest_hd">
        <a href="###" class="nest_back"></a>
        <span>选择行业类别</span>
    </div>--%>
    <div class="nest_bd" style="margin-top:0;">

    </div>
</div>
</div>
<!--正在加载/正在发布 遮罩层 开始-->
<div class="overlay_text">
    <span>正在加载，请稍候……</span>
</div>
<!--正在加载/正在发布 遮罩层 结束-->
<script language="JavaScript">
    $(function () {
        $(".search_frd input").focus(function(){
            $(".search_frd .search_").hide();
        });
        $(".search_frd input").blur(function(){
            if( $(".search_frd input").val()==""){
                $(".search_frd .search_").show();
            }else{
                $(".search_frd .search_").hide();
            }
        });
        $(".search_frd .search_").click(function(){
            $(".search_frd .search_").hide();
            $(".search_frd input").focus();
        });
        $(".search").on("input",function(){
            var ptext =  $(".search").val();
            var surl = basePath
                + "ea/industry/ea_getMySelCompanyList.jspa?industryType="
                + ptext;
            document.location.href = surl;
        })
    })

</script>
</body>
</html>
