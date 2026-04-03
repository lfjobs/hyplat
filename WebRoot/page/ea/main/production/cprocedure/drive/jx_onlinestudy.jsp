<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>

<title>在线学习</title>
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/drivestyle12.css"/>
<link rel="stylesheet" href="<%=basePath%>css/ea/production/driveindex.css"/>

<script type="text/javascript">

var basePath="<%=basePath%>";	

</script>
</head>

<body>
<div class="wfj12_002">
    <div class="wfj12_002_top">
        <ul id="tops">
            <li><a href="学员登录首页.html" target="_self"><img src="<%=basePath%>images/ea/production/drive/wfj_return_01.png" /></a></li>
            <li>科目—在线学习</li>
            <!--<li><a class="shop_a" href="javascript:;">完成</a></li>-->
        </ul>
    </div>

    <div class="wfj12_002_content ">
        <div class="xuex_div">
            <div class="xx_left">
                <a href="顺序学习.html">
                    <div class="xuex_left">
                        <div class="xuex_div_1">
                            <img src="<%=basePath%>images/ea/production/drive/xuexi_1.png">
                        </div>
                        <h1>顺序联系</h1>
                        <div class="clearfix"></div>
                    </div>
                </a>
            </div>
            <div class="xx_right">
                <div class="xuex_right">
                    <a href="随机学习.html">
                        <div class="xuex_top">
                            <h2>随机联系</h2>
                            <div class="xuex_div_2"> <img src="<%=basePath%>images/ea/production/drive/xuexi_2.png"></div>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                    <div class="xuex_bot">
                        <h2>专项联系</h2>
                        <div class="xuex_div_2"> <img src="<%=basePath%>images/ea/production/drive/xuexi_3.png"></div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="clearfix"></div>
                </div>

            </div>
            <div class="clearfix"></div>
        </div>
        <div class="xuex_div2">
            <div class="xx_left2">
                <div class="xuex_left2">
                    <div class="xuex_div_2"> <img src="<%=basePath%>images/ea/production/drive/xuexi_4.png"></div>
                    <h2>模拟考试</h2>
                    <div class="clearfix"></div>
                </div>
            </div>
            <div class="xx_right2">
                <div class="xuex_right2">
                    <div class="xuex_div_2"> <img src="<%=basePath%>images/ea/production/drive/xuexi_5.png"></div>
                    <h2>我的成绩</h2>
                    <div class="clearfix"></div>
                </div>

            </div>
        </div>
    </div>






    <script type="text/javascript">
        $(document).ready(function(e) {
            $("body").css("height",$(window).height());
            $("body").css("height",$(window).height());

            /*顶部*/
            $("#tops").find("li").attr("style","float:left;");
            $("#tops").find("li").eq(0).attr("style","width:15%;");
            $("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
            $("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;color:#FFF;");
            $("#tops").find("li").eq(2).attr("style","width:15%; text-align:center; font-size:"+$(window).height()*0.02+"px;color:#FFF;");

            $(".wfj12_002_top").css("height",$(window).height()*0.08+"px");
            $(".wfj12_002_top").css("lineHeight",$(window).height()*0.08+"px");
            $(".wfj12_002_top_img").attr("style","margin-top:"+$(window).height()*0.01+"px;");
            /*顶部 end*/


        });

    </script>
    <script>
        // var num1=num2=num3=0
        window.onload = window.onresize = function(){
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //console.log(clientWidth);
            //通过屏幕宽度去设置不同的后台根字体的大小
            //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
        }
    </script>
    <script src="<%=basePath%>js/ea/production/cprocedure/drive/driveProduct.js"></script>

</div>
</body>
</html>
