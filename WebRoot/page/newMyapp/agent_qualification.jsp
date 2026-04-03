<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="hy.ea.bo.human.Staff" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>中联园招商</title>
    <link href="<%=basePath %>page/newMyapp/css/newStyle.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>js/html5shiv.min.js"></script>
    <script src="<%=basePath %>js/respond.min.js"></script>
    <![endif]-->
    <script src="<%=basePath %>page/newMyapp/js/toShareCode.js" type="text/javascript"></script>
    <script src="<%=basePath %>page/newMyapp/js/agent_qualification.js" type="text/javascript"></script>
    <script>
		var basePath = '<%=basePath%>';
        var pageNumber = 0;
        var pageCount;
		var titleJudge = '${titleJudge}';
        var hot = '${hot}';
        var goodsName = "";
		var a = '<%=session.getAttribute("key_staff")==null?"":((Staff) session.getAttribute("key_staff")).getStaffName()%>';
		var price;
		var agentid;
	</script>
</head>
<body>
<div id="header">
    <ul>
        <li class="logo">
            <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa"><img src="<%=basePath%>page/newMyapp/images/wfj.png" alt="" class="log"></a>
        </li>
        <li class="scroll">
            <img src="<%=basePath%>page/newMyapp/images/scroll.png" alt="">
        </li>
    </ul>
</div>
    <div class="content">
        <div class="ind_con_head">
            <ul>
                <a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa" class='homePage'><li>网站首页</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=01" class='information'><li>网站资讯</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=02" class='work'><li>中联园办公</li></a>
                <a href="<%=basePath%>/page/newMyapp/Dress-custom.jsp"><li>服务专区</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=03&seven=00" class='investment'><li>中联园招商</li></a>
                <!-- <a href="#;" class='member'><li>会员中心</li></a> -->
               	<a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=05" class='mall'><li>数字地球商城</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=06" class='readExtensively'><li>中联园博览</li></a>
              	<a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=07&seven=00" class='recruitment'><li>招标招聘</li></a>
           		<!-- <a href="#;" class='platform'><li>县域联营经济平台</li></a> -->
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=09" class='aboutUs'><li>关于我们</li></a>
           </ul>
        </div>
        <div class="att_con">
            <div class="att_list3">
                <div class="tit">
                    <c:choose>
                        <c:when test="${hot eq '省级城市'}">
                            <img src="<%=basePath%>page/newMyapp/images/newPCHomepage/ico-sheng.png">
                        </c:when>
                        <c:when test="${hot eq '县级城市'}">
                            <img src="<%=basePath%>page/newMyapp/images/newPCHomepage/ico-xian.png">
                        </c:when>
                        <c:when test="${hot eq '村级新城'}">
                            <img src="<%=basePath%>page/newMyapp/images/newPCHomepage/ico-cun.png">
                        </c:when>
                    </c:choose>
                    <h2>产品${fn:substring(hot,0,2)}代理</h2>
                </div>
                <ul>
                    <li><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-oval.png">将企业管理分为人事、财务、办公室、生产、营销模块</li>
                    <li><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-oval.png">各种方式推广会员名片，增加会员粉丝获取“金粉丝”经济</li>
                    <li><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-oval.png">会员购买产品产交易，获取丰厚佣金，在家轻松做老板</li>
                    <li><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-oval.png">公司底下加入更多会员，购买企业产品，产生更多利润</li>
                </ul>
                <input type="button" value="查看特权">
            </div>
            <div class="att_list4">
                <div class="mil">
                    <div class="tit">
                        <h2>选择购买产品${fn:substring(hot,0,2)}代理类型：</h2>
                        <div class="search">
                            <input type="search" value="" placeholder="搜索地名" onfocus="this.placeholder=''" onblur="this.placeholder='搜索地名'">
                            <img src="<%=basePath%>images/BuildPlatform/ico-search.png" onclick="dianji()">
                        </div>
                    </div>
                    <ul>
                        <%--js拼接--%>
                    </ul>
                    <img src="<%=basePath%>page/newMyapp/images/newPCHomepage/ico-downs.png" class="down">
                    <hr>
                </div>
                <div class="payment">
                    <h1>实际支付：<span>&yen;</span><span class="money">0.00</span>元</h1>
                    <input type="button" value="立即支付" onclick="zhifu()">
                </div>
            </div>
        </div>

    </div>

<!--尾部-->
<div id="footer">
    <div class="text footer_txt">
        <%--<p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p>--%>
        <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
        <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室热线<span>电话:010-64167113</span></p>
        <p>版权所有：北京天太世统科技有限公司</p>
    </div>
</div>
<script>
    $(document).ready(function(){
        var txt=$(".att_con .att_list4 .payment h1 .money").text();
        var txt2 =new Number(txt).toFixed(2);
        $(".att_con .att_list4 .payment h1 .money").text(txt2);
        /*选择类型*/
        $(document).on("click",".att_con .att_list4 .mil ul li",function(){
            $(this).addClass("active").siblings().removeClass("active");
            agentid = $(this).attr("data-ppid");
        });
        /*选择支付方式*/
            $(document).on("click",".att_con .att_list4 .payment .payment_con_n li",function(){
            $(this).addClass("active").siblings().removeClass("active");
        });



        //绑定滚动条事件
        $(window).bind("scroll", function () {
            var sTop = $(window).scrollTop();
            var sTop = parseInt(sTop);

            if (sTop >= 1100) {
                $(".return").slideDown();
                $(".return").show();
            }
            else {
                $(".return").hide();
            }
        });
    })


</script>
</body>
</html>