<!DOCTYPE html>
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
    <script src="<%=basePath %>/js/qrcode.js" type="text/javascript"></script>
    <script src="<%=basePath %>page/newMyapp/js/join_platform.js" type="text/javascript"></script>
    <script>
		var basePath = '<%=basePath%>';
		var titleJudge = '${titleJudge}';
		var hot = '${hot}';
		var goodsName = "";
		var a = '<%=session.getAttribute("key_staff")==null?"":((Staff) session.getAttribute("key_staff")).getStaffName()%>';
		var ccompanyId = '${ccompanyId}';
		var price;
		var ppid;

		//促销品数据
        var ptppid = "";
        var companyId = "";
        var ptmorre = "";
        var ptstandard = "";
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
                        <c:when test="${hot eq '公司'}">
                            <img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-gong.png">
                        </c:when>
                        <c:when test="${hot eq '个人'}">
                            <img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-ge.png">
                        </c:when>
                    </c:choose>
                    <h2>${hot}申请加入经济平台</h2>
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
                <div class="mil huiyuan">
                    <h2>选择购买${hot}经济平台会员类型：</h2>
                    <ul>
                        <c:forEach items="${list}" var="a">
                            <li data-ppid="${a[1]}" data-price="${a[2]}"><p>${a[0]}</p></li>
                        </c:forEach>
                    </ul>
                    <h5>不同会员类型，有不同的区别</h5>
                    <hr>
                </div>
                <c:if test="${hot eq '公司'}">
                    <div class="mil hangye">
                        <h2>填写公司基本信息：</h2>
                        <div class="list_">
                            <div class="list">
                                <h5>行业类别：</h5>
                                <div class="ipt">
                                    <p></p><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-down2.png" alt="">
                                </div>
                            </div>
                            <ul class="class1">
                            </ul>
                            <ul class="class2">
                            </ul>
                        </div>
                        <div class="list">
                            <h5>公司名称：</h5>
                            <div class="ipt2">
                                <input type="text" value="" placeholder="请输入公司名称" onfocus="this.placeholder=''" onblur="this.placeholder='请输入公司名称'">
                            </div>
                        </div>
                        <hr>
                    </div>
                </c:if>
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
        <!-- <p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p> -->
        <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
        <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室<span>热线电话:010-64167113</span></p>
        <p>版权所有：北京天太世统科技有限公司</p>
    </div>
</div>
<script>
    $(document).ready(function() {
        var txt = $(".att_con .att_list4 .payment h1 .money").text();
        var txt2 = new Number(txt).toFixed(2);
        $(".att_con .att_list4 .payment h1 .money").text(txt2);
        /*选择平台类型*/
        $(document).on("click", ".att_con .att_list4 .huiyuan ul li", function () {
            $(this).addClass("active").siblings().removeClass("active");
            $(".money").text($(this).attr("data-price"));
            price = $(this).attr("data-price");
            ppid = $(this).attr("data-ppid");
            goodsName = $(this).find("p").text();
            ajax();
        });

        /*行业类别选择*/
        $(document).on("click", ".att_con .att_list4 .mil .list .ipt", function () {
                findIndustry("", "class1");
                $(".att_con .att_list4 .mil .class1").show();
            });
            $(document).on("click", ".att_con .att_list4 .mil .class1 li", function () {
                var li_txt1 = $(this).text();
                findIndustry($(this).attr("data-codeid"), "class2");
                $(".att_con .att_list4 .mil .class2").show();
                $(".att_con .att_list4 .mil .list .ipt p").text(li_txt1);
            });
            $(document).on("click", ".att_con .att_list4 .mil .class2 li", function () {
                var li_txt2 = $(this).text();
                $(".att_con .att_list4 .mil .class1").hide();
                $(".att_con .att_list4 .mil .class2").hide();
                $(".att_con .att_list4 .mil .list .ipt p").append("/" + li_txt2);
            });
            /*选择支付方式*/
            $(".att_con .att_list4 .payment .payment_con_n li").on("click", function () {
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