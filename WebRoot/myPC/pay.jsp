<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
    <title>支付页面</title>

    <link href="<%=basePath %>myPC/css/style.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>myPC/js/jquery-1.9.1.min.js" type="text/javascript"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>myPC/js/html5shiv.min.js"></script>
    <script src="<%=basePath %>myPC/js/respond.min.js"></script>
    <![endif]-->
</head>
<body>
    <div id="header" class="login_header">
        <ul>
            <li class="logo">
                                <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区"><img src="<%=basePath %>myPC/images/wfj.png" alt="" class="log"></a>

               
            </li>
            <li class="name login_name">
                <div>
                    <h3>会员快速支付</h3>

                </div>
            </li>
            <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区">
            <li class="login login_login">
                <img src="<%=basePath %>myPC/images/return2.png" alt="">
                <div>
                    <h3>返回首页</h3>
                </div>
              
            </li>
            </a>
        </ul>
    </div>
    <div class="content pay_con_">
        <div class="pay_con">
            <img src="<%=basePath %>myPC/images/pay-banner.png" alt="" width="100%">
            <div class="head">
                <h3>》收款方：北京天太世统科技有限公司</h3>
                <h3 class="money">应付金额：<span>1.00元</span></h3>
            </div>
            <div class="yinhang">
                <ul>
                    <li><img src="<%=basePath %>myPC/images/nonghang.png" alt="" style="width: 90%;margin-top: 11px;"></li>
                    <li><img src="<%=basePath %>myPC/images/zhonghang.png" alt="" style="width: 75%;margin-top: 8px;"></li>
                    <li><img src="<%=basePath %>myPC/images/jianhang.png" alt="" style="width: 90%;margin-top: 11px;"></li>
                    <li><img src="<%=basePath %>myPC/images/gonghang.png" alt="" style="width: 90%;margin-top: 11px;"></li>
                    <li><img src="<%=basePath %>myPC/images/zhaohang.png" alt="" style="width: 90%;margin-top: 11px;"></li>
                    <li><img src="<%=basePath %>myPC/images/jiaohang.png" alt="" style="width: 90%;margin-top: 11px;"></li>
                    <li><img src="<%=basePath %>myPC/images/renhang.png" alt="" style="width: 90%;margin-top: 11px;"></li>
                    <li><img src="<%=basePath %>myPC/images/zhifubao.png" alt="" style="width: 75%;margin-top: 8px;"></li>
                </ul>
                <input type="button" value="立即支付" id="zf">
            </div>
            <div class="text">
                <ul>
                    <li>
                        请您选择支付方式。建议使用中国工商银行、中国农业银行、中国银行、中国建设银行、招商银行的银行卡，支付请直接点击相应银行的按钮；如果您使用其他银行的银行卡，请点击“中国银联”按钮；如果您使用支付宝账户支付，请点击“支付宝”按钮。
                    </li>
                    <li>
                        如果您选择银行卡或支付宝账户支付时，请遵守相关银行或支付宝的规定进行操作。您在银行或支付宝页面上进行的任何操作及其产生的任何法律后果，将按照您与银行或支付宝之间签订的合同处理。本网站不承担任何责任。
                    </li>
                </ul>
            </div>
            <div class="footer">
                <div class="text" align="center">
                    <a href="#;">关于我们</a>|<a href="#;">网站声明</a>
                    <p>版权所有 @ 2 0 0 8 - 2 0 1 5&nbsp;&nbsp;&nbsp;北京天太世统科技有限公司</p>
                    <p>京 I C P 备 1 0 0 0 9 6 3 6</p>
                </div>
            </div>
        </div>
    </div>
   <!-- <footer>
        <div class="text">
            <p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p>
            <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
            <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室热线<span>电话:010-64167113</span></p>
            <p>版权所有：北京天太世统科技有限公司</p>
            <p>版权所有：北京天太世统科技有限公司</p>
        </div>
    </footer>-->
<script>
    $(document).ready(function(){
		var basePath = "<%=basePath%>";
        $(".ind_con_head ul li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });

        $(".yinhang ul li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });
        $("#index").click(function(){
            var url = basePath+"myPC/index.jsp";
            document.location.href = url;
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