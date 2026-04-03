<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script src="<%=basePath%>js/ea/production/cprocedure/qrshare/setHtmlFont.js" type="text/javascript"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/BuildPlatform/base.css">
    <link rel="stylesheet" href="<%=basePath%>/css/ea/production/qrshare/site_manger.css">
    <script src="<%=basePath%>/js/jquery.min.js"></script>
    <script src="<%=basePath%>/js/ea/production/cprocedure/qrshare/siteManagementList.js"></script>
    <title>场地管理</title>
</head>

<script>
     var basePath = '<%=basePath%>';
     var pageNumber = 0;
     var pageSize;
     var pageCount;
     var companyId = '${caccount.companyID}';
     var posNum = "${param.posNum}";
     window.onpageshow = function(event) {
         if (event.persisted) {
             window.location.reload();
         }
     }
</script>

<body>
    <header class="com_head">
        <a href="javascript:void(0);" class="back" onclick="javascript: window.history.go(-1);return false;"></a>
        <h1>车辆出入记录</h1>
        <div class="msh"><input onclick="feeAudit()" type="button" value="免人工审核"/></div>
    </header>
    <div class="wrap_page">
    	<div class="search_wrap">
            <div class="search_box">
                <input type="text" class="search">
                <span class="search_overly"><i class="com_search"></i>搜索车牌号</span>
            </div>
            <div class="select_wrap">
                <div class="select_box" date-status="">全部</div>
                <ul class="select_list">
                    <li date-status="">全部</li>
                    <li date-status="1">进入</li>
                    <li date-status="0">离开</li>
                    <li date-status="2">异常</li>
                </ul>
            </div>
        </div>
        <div class="site_wrap">
            <!-- js拼接 -->
        </div>
    </div>
    <script>
        //搜索
        $(".search").focus(function(){
            $(".search_overly").hide();
        })
        $(".search").blur(function(){
            var val=$(this).val();
            if(val==''){
                $(".search_overly").show();
            }
        })
        $(".select_box").click(function(){
            $(".select_list").slideToggle(200);
        })
        $(".select_list li").click(function(){
            $(".select_box").text($(this).text());
            $(".select_box").attr("date-status",$(this).attr("date-status"));
            $(this).parent().slideUp(200);
			pageNumber = 0;
			$(".site_wrap").empty();
			ajax();
        })
    </script>
</body>

</html>
