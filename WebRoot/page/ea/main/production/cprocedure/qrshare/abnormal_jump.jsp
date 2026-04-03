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
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>停车收费</title>
    <script type="text/javascript" src="<%=basePath%>js/BuildPlatform/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/page/newMyapp/css/swiper.min.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/production/qrshare/abnormal_jump.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/page/newMyapp/js/swiper.min.js"></script>
    <script src="<%=basePath%>/js/ea/production/cprocedure/qrshare/abnormal_jump.js"></script>
</head>
<script>
     var basePath = '<%=basePath%>';
     var carmID = '${carManage.carmID}';
     var posNum = "${param.posNum}";
</script>
<body>
<header>
   <ul>
       <li style="width: 10%;">
           <a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/BuildPlatform/left_jt.png"></a>
       </li>
       <li style="width: 80%;">停车收费</li>
       <li style="width: 10%;"></li>
   </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="mil">
            <h4 class="title"><i></i>异常车辆</h4>
            <ul>
                <li class="abnormal">
                    <div class="img">
                        <img src="<%=basePath%>${obj[15] }">
                        <p><span>1</span>/2</p>
                    </div>
                    <div class="text">
                        <div class="ipt">
                            <input class="carNumber" date-vid="${obj[14]}" type="text" readonly value="${obj[9] }">
                        </div>
                        <p class="time">${obj[3]}<span>出</span></p>
                        <p class="site">${obj[5] }</p>
                    </div>
                    <input type="button" value="修改" id="revise">
                    <input type="button" value="匹配" id="matching">
                </li>
            </ul>
        </div>
        
        
        <div class="mil mate">
            <h4 class="title"><i style="background-color: #ff6d2f;"></i>匹配车辆</h4>
            <!--无数据-->
            <div class="not" style="display: none;">
                <img src="<%=basePath%>/images/wu.png">
                <p>暂无数据，请修改车牌号匹配</p>
            </div>

            <!--有数据-->
            <ul style="display: none;">
                <!-- js拼接 -->
            </ul>
        </div>
    </div>
    <div class="btn">
        <button>确定</button>
    </div>
</div>

<div class="alert_"></div>
<div class="alert">
    <img src="<%=basePath%>/images/ico-delete.png" class="deldte">
    <div class="alert_mil">
        <h4 class="title"><i></i>异常车辆</h4>
        <div class="swiper-container swiper-container1">
            <div class="swiper-wrapper">
                <div class="swiper-slide"><img class="panorama" src="<%=basePath%>${obj[15] }"></div>
                <div class="swiper-slide"><img class="picture" src="<%=basePath%>${obj[16] }"></div>
            </div>
            <div class="swiper-pagination"></div>
        </div>
    </div>
    <div class="alert_mil">
        <h4 class="title"><i style="background-color: #ff6d2f;"></i>匹配车辆</h4>
        <div class="swiper-container swiper-container2">
            <div class="swiper-wrapper">
                <div class="swiper-slide"><img class="panorama" src="images/img1.jpg"></div>
                <div class="swiper-slide"><img class="picture" src="images/img2.jpg"></div>
            </div>
            <div class="swiper-pagination"></div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $("header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".content_hidden").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: auto;"+"height:"+$(window).height()*0.84+"px");
        $(".btn").css("height",$(window).height()*0.08-1+"px");

		$(document).on("click","#revise",function(){
            var t=$(".mil .abnormal .text .ipt input").val();
            $(".mil .abnormal").addClass("ipt_on");
            $(".mil .abnormal .text .ipt input").attr("readonly",false);
            $(".mil .abnormal text .ipt input").focus().val(t);
        });
        $(document).on("click","#matching",function(){
            var t=$(".mil .abnormal .text .ipt input").val();
            var vid = $(".mil .abnormal .text .ipt input").attr("date-vid");
            $(".mil .abnormal").removeClass("ipt_on");
            $(".mil .abnormal .text .ipt input").attr("readonly",true);
            $(".mil .abnormal .text .ipt input").focus().val(t);
            if($(".ipt_on").length>0){
            	ajax1(carmID,t,vid);
            }else{
                ajax2(t,vid);
            }
        });

        /*匹配列表选择*/
        $(document).on("click",".mate li .select",function(){
            $(this).parents("li").addClass("active").siblings().removeClass("active");
        });

        /*弹框*/
        $(document).on("click",".mate li .img img",function(){
        	$(".swiper-container2").find(".panorama").attr("src",basePath+$(this).parents("li").attr("date-panorama"));
        	$(".swiper-container2").find(".picture").attr("src",basePath+$(this).parents("li").attr("date-picture"));
            $(".alert_").show();
            $(".alert").show();
            var mySwiper = new Swiper('.swiper-container1', {
                autoplay: 5000,//可选选项，自动滑动
                pagination : '.swiper-pagination',
                paginationType : 'fraction',
            });
            var mySwiper2 = new Swiper('.swiper-container2', {
                autoplay: false,//可选选项，自动滑动
                pagination : '.swiper-pagination',
                paginationType : 'fraction',
            })
        });
        $(document).on("click",".alert .deldte",function(){
            $(".alert_").hide();
            $(".alert").hide();
        });
        $(document).on("click",".alert_",function(){
            $(".alert_").hide();
            $(".alert").hide();
        });


    });
</script>
<script>

</script>
</body>
</html>