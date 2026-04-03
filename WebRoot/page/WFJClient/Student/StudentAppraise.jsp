<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/apprise/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/apprise/swiper-3.3.1.min.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/apprise/student.css">
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/swiper-3.3.1.min.js"></script>
    <title>教练信息</title>
    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var pagenumber = 0;
        var pagesize = 5;
        var pagecount;
        var studentId =""; //学员id
        var teacherId="${teacher.teacherId}"; //教练id
        var height = 0;
        var t;
        var pingji="";
    </script>
</head>

<body>
<header class="com_head">
    <a onclick="javascript:window.history.go(-1);return false;" class="back"></a>
    <h1>教练信息</h1>
</header>
<div class="wrap_page">
    <div class="jl_info_wrap clearfix">
        <img src="<%=basePath%>${teacher.photo==null?"images/ea/driving/elkc/head.png":teacher.photo}"  class="jl_head" alt="">
        <ul class="jl_T">
            <li>姓名:${teacher.name}</li>
            <li>入职时间：${teacher.hiredate}</li>
            <li>联系电话：${teacher.mobile}</li>
            <li>准教车型：${teacher.teachpermitted}</li>
        </ul>
    </div>
    <div class="coach_con">
        <section>
            <div class="tab_intro_wrap clearfix">
                <div class="tab_intro_box tab_intro_cur">
                    <span>全部(${map.quanbu})</span>
                </div>
                <div class="tab_intro_box">
                    <span>好评(${map.haoping})</span>
                </div>
                <div class="tab_intro_box">
                    <span>中评(${map.zhongping})</span>
                </div>
                <div class="tab_intro_box">
                    <span>差评(${map.chaping})</span>
                </div>

            </div>
            <hr class="coach_hr">
            <div class="coach_intro_wrap">
                <a href="###" class="coach_int_tit flex flex_align_center">
                    <span class="flex_1">教练评价<b>（${map.quanbu}人评论）</b></span>
                    <%--<i class="std_head_arr"></i>--%>
                </a>
                <div class="intro_con_wrap">
                    <%--   <%--<div class="intro_box flex">--%>
                        <%--<div class="intro_L outer_img">--%>
                            <%--<img src="<%=basePath%>images/WFJClient/student/intro_head01.jpg" alt="">--%>
                        <%--</div>--%>
                        <%--<div class="intro_R flex_1">--%>
                            <%--<div class="intro_top clearfix">--%>
                                <%--<span>专业学车</span>--%>
                                <%--<small>2017-08-15</small>--%>
                            <%--</div>--%>
                            <%--<div class="intro_star_box">--%>
                                <%--<ul class="star_intro clearfix">--%>
                                    <%--<li></li>--%>
                                    <%--<li></li>--%>
                                    <%--<li></li>--%>
                                    <%--<li></li>--%>
                                    <%--<li></li>--%>
                                <%--</ul>--%>
                                <%--<input type="hidden" class="star_num" value="4">--%>
                            <%--</div>--%>
                            <%--<div class="intro_con">--%>
                                <%--整体来说不错，学车和预约速度很快，教练也很有耐心！--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="intro_box flex">--%>
                        <%--<div class="intro_L outer_img">--%>
                            <%--<img src="<%=basePath%>images/WFJClient/student/intro_head01.jpg" alt="">--%>
                        <%--</div>--%>
                        <%--<div class="intro_R flex_1">--%>
                            <%--<div class="intro_top clearfix">--%>
                                <%--<span>向来缘浅，奈何情深</span>--%>
                                <%--<small>2016-08-15</small>--%>
                            <%--</div>--%>
                            <%--<div class="intro_star_box">--%>
                                <%--<ul class="star_intro clearfix">--%>
                                    <%--<li></li>--%>
                                    <%--<li></li>--%>
                                    <%--<li></li>--%>
                                    <%--<li></li>--%>
                                    <%--<li></li>--%>
                                <%--</ul>--%>
                                <%--<input type="hidden" class="star_num" value="3">--%>
                            <%--</div>--%>
                            <%--<div class="intro_con">--%>
                                <%--为何这个做的这么好呢!--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                            <%--<div class="intro_box flex">--%>
                                <%--<div class="intro_L outer_img">--%>
                                    <%--<img src="<%=basePath%>images/WFJClient/student/intro_head01.jpg" alt="">--%>
                                <%--</div>--%>
                                <%--<div class="intro_R flex_1">--%>
                                    <%--<div class="intro_top clearfix">--%>
                                        <%--<span>向来缘浅，奈何情深</span>--%>
                                        <%--<small>2016-08-15</small>--%>
                                    <%--</div>--%>
                                    <%--<div class="intro_star_box">--%>
                                        <%--<ul class="star_intro clearfix">--%>
                                            <%--<li></li>--%>
                                            <%--<li></li>--%>
                                            <%--<li></li>--%>
                                            <%--<li></li>--%>
                                            <%--<li></li>--%>
                                        <%--</ul>--%>
                                        <%--<input type="hidden" class="star_num" value="2">--%>
                                    <%--</div>--%>
                                    <%--<div class="intro_con">--%>
                                        <%--为何这个做的这么好呢!--%>
                                        <%--<div class="intro_img">--%>
                                            <%--<div class="swiper-container">--%>
                                                <%--<div class="swiper-wrapper">--%>
                                                    <%--<div class="swiper-slide"><img src="<%=basePath%>images/WFJClient/student/award.jpg" alt=""></div>--%>
                                                    <%--<div class="swiper-slide"><img src="<%=basePath%>images/WFJClient/student/award2.jpg" alt=""></div>--%>
                                                    <%--<div class="swiper-slide"><img src="<%=basePath%>images/WFJClient/student/award3.jpg" alt=""></div>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <%--<div class="overlay" id="overlay">--%>
                                                <%--<section class="modal overlay_img"></section>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>



                    <%--&lt;%&ndash;<div class="load_all">已显示全部评论</div>&ndash;%&gt;--%>
               </div>
            </div>
        </section>
    </div>

</div>
<script>
    $(function() {
        //设置好评、中评、差评属性
        $(".star_num").each(function() {
            var t = $(this).val();
            var $box = $(this).parents(".intro_box")
            if (t == 1) {
                $box.attr("data-intro", "3")
            } else if (t > 1 && t < 5) {
                $box.attr("data-intro", "2")
            } else if (t == 5) {
                $box.attr("data-intro", "1")
            }
        })
        //点击导航切换样式筛选评价
        $(".tab_intro_box").click(function() {
            var _index = $(".tab_intro_wrap .tab_intro_box").index(this);
            pingji=$(this).find("span").text ();
            $(this).addClass("tab_intro_cur").siblings().removeClass("tab_intro_cur")
            if (_index == 0) {
                $(".intro_box").show();
            } else {
                $(".intro_box").hide();
                $('[data-intro="' + _index + '"]').show();
            }
            $(".intro_con_wrap").html("");
            pagenumber=0;
            chaxun();
        })
        //初始化评价星星显示
        $(".intro_star_box").each(function() {
            var that = $(this);
            var _num = that.find("input").val();
            for (i = 0; i < _num; i++) {
                that.find(".star_intro li").eq(i).addClass("star_light")
            }
        })
        //  初始化图片滚动
            var  mySwiper = new Swiper('.swiper-container', {
            loop: false,
            direction: 'horizontal',
            slidesPerView: 4,
            spaceBetween: 6,
            touchMoveStopPropagation: false,
        })

    })

</script>
<script type="text/javascript">
    function getHeight(){
        height = parseInt(Math.abs($(".coach_intro_wrap").height()-($(window).height()-$(".coach_intro_wrap").offset().top)));
        t=setTimeout("getHeight()", 200);
        if(height<$(window).height()){
            if(pagenumber<pagecount){
                chaxun();
            }
        }
    }
    $(document).ready(function(){

        clearTimeout(t);
        pagenumber += 1;

        var typeId="${typeId}";

        var url = basePath+"ea/student/sajax_ea_getStudentAppraiseBufen.jspa?teacherId="+teacherId+"&pingji="+pingji;

        console.log(url)
        $.ajax({
            url : url,
            type : "post",
            async : false,
            dateType : "json",
            data:{
                "pageForm.pageNumber":pagenumber,
            },
            success : function (data) {
                var member = eval("("+data+")");
                var pageForm = member.pageForm;
                var plist=member.plist;
                var p;
                if (pageForm == null) {
                    return;
                } else {
                    var htl =new Array();
                    var list = pageForm.list;
                    pagenumber = pageForm.pageNumber;
                    pagecount = pageForm.pageCount;
                    if(list ==null){
                        return null;
                    }
                    for(var i = 0;i<list.length;i++){
                        var search = list[i];
                        htl.push("<div class='intro_box flex'><div class='intro_L outer_img'> ");
                        htl.push("<img src=<%=basePath%>images/ea/driving/elkc/head.png alt=''> </div>");
                        htl.push("<div class='intro_R flex_1'><div class='intro_top clearfix'>");
                        htl.push("<span>学员</span><small>" + search[6] + "</small></div> ");
                        htl.push("<div class='intro_star_box'><ul class='star_intro clearfix'>");
                        htl.push("   <li></li>  <li></li> <li></li> <li></li> <li></li> </ul> ");
                        htl.push(" <input type='hidden' class='star_num' value="+search[2]+"> </div>");
                        htl.push(" <div class='intro_con'>" + search[5]);
                        if (plist != null ) {
                            $.each(plist, function(key,value) {
                                if(search[8]==key) {
                                    htl.push("<div class='intro_img'><div class='swiper-container'><div class='swiper-wrapper'>");
                                    $(value).each(function() {
                                        htl.push("<div class='swiper-slide'><img src=<%=basePath%>/" + this +  "></div>");
                                    });
                                    htl.push("</div></div><div class='overlay' id='overlay'><section class='modal overlay_img'></section>");
                                    htl.push("</div></div>");
                                }
                            });
                        }
                        htl.push("</div></div> </div>");
                    }
                    $(".intro_con_wrap").append(htl.join(""));
                    studentchushi();
                    getHeight();
                }

            },
            error:function(data){
                alert("获取项目失败");
            }
        });

    });




    function chaxun(){
        clearTimeout(t);
        pagenumber += 1;

        var typeId="${typeId}";

        var url = basePath+"ea/student/sajax_ea_getStudentAppraiseBufen.jspa?teacherId="+teacherId+"&pingji="+pingji;

        console.log(url)
        $.ajax({
            url : url,
            type : "post",
            async : false,
            dateType : "json",
            data:{
                "pageForm.pageNumber":pagenumber,
            },
            success : function (data) {
                var member = eval("("+data+")");
                var pageForm = member.pageForm;
                var plist=member.plist;
                var p;
                if (pageForm == null) {
                    return;
                } else {
                    var htl =new Array();
                    var list = pageForm.list;
                    pagenumber = pageForm.pageNumber;
                    pagecount = pageForm.pageCount;
                    if(list ==null){
                        return null;
                    }
                    for(var i = 0;i<list.length;i++){
                        var search = list[i];
                        htl.push("<div class='intro_box flex'><div class='intro_L outer_img'> ");
                        htl.push("<img src=<%=basePath%>images/ea/driving/elkc/head.png  alt=''> </div>");
                        htl.push("<div class='intro_R flex_1'><div class='intro_top clearfix'>");
                        htl.push("<span>学员</span><small>" + search[6] + "</small></div> ");
                        htl.push("<div class='intro_star_box'><ul class='star_intro clearfix'>");
                        htl.push("   <li></li>  <li></li> <li></li> <li></li> <li></li> </ul> ");
                        htl.push(" <input type='hidden' class='star_num' value="+search[2]+"> </div>");
                        htl.push(" <div class='intro_con'>" + search[5]);
                        if (plist != null ) {
                            $.each(plist, function(key,value) {
                                if(search[8]==key) {
                                    htl.push("<div class='intro_img'><div class='swiper-container'><div class='swiper-wrapper'>");
                                    $(value).each(function() {
                                        htl.push("<div class='swiper-slide'><img src=<%=basePath%>" + this +  "></div>");
                                    });
                                    htl.push("</div></div><div class='overlay' id='overlay'><section class='modal overlay_img'></section>");
                                    htl.push("</div></div>");
                                }

                            });
                        }
                        htl.push("</div></div> </div>");
                    }
                    $(".intro_con_wrap").append(htl.join(""));
                    studentchushi();
                    getHeight();
                }

            },
            error:function(data){
                alert("获取项目失败");
            }
        });

    }

    function studentchushi() {
        //初始化评价星星显示
        $(".intro_star_box").each(function() {
            var that = $(this);
            var _num = that.find("input").val();
            for (i = 0; i < _num; i++) {
                that.find(".star_intro li").eq(i).addClass("star_light")
            }
        })
        //  初始化图片滚动
        var  mySwiper = new Swiper('.swiper-container', {
            loop: false,
            direction: 'horizontal',
            slidesPerView: 4,
            spaceBetween: 6,
            touchMoveStopPropagation: false,
        })




    }

</script>
</body>

</html>
