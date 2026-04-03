<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>

<script type="text/javascript">
    var basePath='<%=basePath%>';
    var companyID="${companyID}";
    var allPro="${param.allPro}";
    var categoryId = "${categoryId}"
    var categoryName = "${categoryName}";
    var pagenumber=0;
    var pagecount=0;
    var t;
    var ppname='';
    var pname='';
    var search='';
</script>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/base.css"/>
    <link rel="stylesheet" href="<%=basePath %>st/css/apply.css">
    <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/setHtmlFont.js"></script>
    <script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>st/js/productMall.js"></script>
    <title>联营商城-商品</title>
</head>

<body>
<header class="com_head">
    <a href="javascript:history.go(-1);" class="back"></a>
    <h1>联营商城</h1>
</header>
<div class="wrap_page">
    <div class="filtrate_tab clearfix">
        <a class="tab_box "><i class="tab_01"></i><span>排序</span></a>
        <a class="tab_box "><i class="tab_02"></i><span>筛选</span></a>
        <a class="tab_box"><i class="tab_03"></i><span>搜索</span></a>
    </div>
    <div class="filtrate_con">
        <div class="tab_con">
            <div class="px_con">
                <a onclick="screen('pop')" class="px_option">销量优先</a>
                <a onclick="screen('plow')" class="px_option">价格最低</a>
                <a onclick="screen('ptop')" class="px_option">价格最高</a>
                <a onclick="screen('smart')" class="px_option">综合排序</a>
            </div>
        </div>
        <div class="tab_con">
            <div class="sx_con">
                <div class="type_wrap">
                    <div class="sx_tit">产品类型</div>
                    <div class="sx_type_wrap clearfix" >

                    </div>
                </div>

                <div class="sx_btn clearfix">
                    <a class="clear_btn">清除筛选</a>
                    <a onclick="sure_btnClick()" class="sure_btn">确认</a>
                </div>
            </div>
        </div>
        <div class="tab_con" id="search">
            <div class="ss_con">
                <input type="text" class="search_inp" placeholder="请输入搜索内容">
                <a onclick="searchPro()" class="search_btn"><i></i>搜索</a>
            </div>
        </div>
    </div>
    <div class="pro_wrap clearfix">
        <ul>

        </ul>
        <%--<a href="###" class="pro_box">--%>
            <%--<img src="<%=basePath %>st/images/pro_img.jpg" class="pro_img" alt="">--%>
            <%--<div class="pro_tit">皮皮狗羊绒女短款宽松春装圆领</div>--%>
            <%--<div class="pro_price">￥728</div>--%>
        <%--</a>--%>
    </div>
</div>
<script>
    var companyName = "${param.companyName}";
    var companyID = "${param.companyID}";
    $(function(){
        //初始化筛选
        function initSX(){
            $(".tab_box").each(function(){
                $(this).removeClass("tab_act")
            })

            $(".filtrate_con").hide().find(".tab_con").each(function(){
                $(this).hide();
            });

            $(".search_inp").val('');

        }
        //tab导航
        $(".tab_box").click(function(){
            if($(this).hasClass("tab_act")){
                initSX();
            }else{
                $(this).addClass("tab_act").siblings().removeClass("tab_act");
                var _index = $(".filtrate_tab .tab_box").index($(this));
                $(".tab_con").each(function(){
                    $(this).hide();
                })
                $(".filtrate_con").show();
                $(".tab_con").eq(_index).show();
            }
            //筛选-类型 点击
            $(".sx_type_box a").click(function(){
                pname=$(this).text();
                $(this).addClass("type_act").parent().siblings(".sx_type_box").find("a").removeClass("type_act");
            })
        })
        //筛选-清除筛选
        $(".clear_btn").click(function(){
            $(".sx_type_box").each(function(){
                $(this).find("a").removeClass("type_act");
            })
        })
        //点击确认、排序或者搜索按钮后，请运行一遍初始化function
    })
</script>
</body>

</html>
