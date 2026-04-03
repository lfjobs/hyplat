<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">

        <link rel="stylesheet" href="<%=basePath%>css/ea/production/resest.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/mem_center.css">
    <script type="text/javascript" src="<%=basePath%>js/ea/production/resume/resumeManagement/zepto.min.js"></script>

 <title>我的职位邀请</title>

<body>
    <!-- header start  -->
    <header class="mem_header">
        <a id="returnClick" class="back"></a>
        <h1>我的职位邀请</h1>
    </header>
    <input type="hidden" id="staffid" value="${param.staffid}">
    <!--  header end  -->
    <!-- 页面内容 start  -->
    <div class="wrap_page">
        <section class="invite_job_wrap">
            <div class="inv_nav clearfix">
                <a class="inv_navcur" onclick="ajac('03')">待确认</a>
                <a onclick="ajac('01')">已接受</a>
                <a onclick="ajac('00')">已拒绝</a>
                <a onclick="ajac('02')">已过期</a>
            </div>
            <div class="inv_con_wrap">
               <!--待确认 开始-->
                <div class="inv_con" id="div0"></div>
                <!--待确认 结束-->
                <!--已接受 开始-->
                <div class="inv_con" id="div1"></div>
                <!--已接受 结束-->
                <!--已拒绝 开始-->
                <div class="inv_con" id="div2"></div>
                <!--已拒绝 结束-->
                <!--已过期 开始-->
                <div class="inv_con" id="div3"></div>
                <!--已过期 结束-->
            </div>
        </section>
    </div>
    <!--  页面内容 end -->
    <script>
    var basePath="<%=basePath%>";
    var jitype="${param.jitype}";
	var sccId="${param.sccId}";
    function ajac(ob){
        $("#div0").empty();
        $("#div1").empty();
        $("#div2").empty();
        $("#div3").empty();
        var staffid = $("#staffid").val();
    var url =basePath+ "ea/resumes/sajax_ea_getRecord.jspa?type=ac&resumeName="+ob+"&staffid="+staffid+"&sccId="+sccId+"&jitype="+jitype;
		    $.ajax({
				url : encodeURI(url),
				type : "get",
				async : false,
				dataType : "json",
				success : function(data) {
					var member = eval("(" + data + ")");
					var page = member.listobj;
					var str = "";
					 for(var i=0;i<page.length;i++){
						var obj = page[i];
                         if (obj[5].hours < 10) {
                             obj[5].hours = "0" + obj[5].hours;
                         }
                         if (obj[5].minutes < 10) {
                             obj[5].minutes = "0" + obj[5].minutes;
                         }
                         var time = obj[5].hours + ":" + obj[5].minutes;
                         if (obj[5].month < 9) {
                             if (obj[5].date < 10) {
                                 var date = (obj[5].year + 1900) + "-0" + (obj[5].month + 1) + "-0" + obj[5].date;
                             } else {
                                 var date = (obj[5].year + 1900) + "-0" + (obj[5].month + 1) + "-" + obj[5].date;
                             }
                         } else {
                             var date = (obj[5].year + 1900) + "-" + (obj[5].month + 1) + "-" + obj[5].date;
                         }
						str +="<a  class='sjob_rec_box flex flex_align_center'>";
						str +=" <div class='sjob_L'>";
						if(obj[4]==null){
						str +="<img src='"+basePath+"page/ea/main/production/resume/resumeManagement/images/sjob_01.png' > </div>";
						}
						str +="<img src="+basePath+obj[4]+" > </div>";
						str +="<div class='sjob_m flex_1'>";
						str +="<p class='sjob_name'>";
						str +="<span>"+obj[0]+"</span>";
						str +=" </p><p class='sjob_com'>";
						str +="<span>"+obj[1]+"</span></p>";
						str +=" <p class='sjob_area'>";
						str +=" <span>"+(ob=='03'?"投递时间：":"面试时间：")+" "+date+"   "+time+"</span>";

						str +=" </p> </div></a>";
					}

						if(ob=="00"){
							$("#div2").append(str);
						}else if(ob=="01"){
							$("#div1").append(str);
						}else if(ob=="02"){
							$("#div3").append(str);
						}else if(ob=="03"){
							$("#div0").append(str);
						}
				},
				error : function(data) {
				}
			});


    }
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
        $(function(){
        //后退
        $("#returnClick").click(function() {
        	history.go(-1)
        });
        //初始化进来
         var staffid = $("#staffid").val();
    	 var url =basePath+ "ea/resumes/sajax_ea_getRecord.jspa?type=ac&resumeName=03&staffid="+staffid+"&sccId="+sccId+"&jitype="+jitype;
        $.ajax({
				url : encodeURI(url),
				type : "get",
				async : false,
				dataType : "json",
				success : function(data) {
					var member = eval("(" + data + ")");
					var page = member.listobj;
					var str = "";
					 for(var i=0;i<page.length;i++){
						var obj = page[i];
                         if (obj[5].hours < 10) {
                             obj[5].hours = "0" + obj[5].hours;
                         }
                         if (obj[5].minutes < 10) {
                             obj[5].minutes = "0" + obj[5].minutes;
                         }
                         var time = obj[5].hours + ":" + obj[5].minutes;
                         if (obj[5].month < 9) {
                             if (obj[5].date < 10) {
                                 var date = (obj[5].year + 1900) + "-0" + (obj[5].month + 1) + "-0" + obj[5].date;
                             } else {
                                 var date = (obj[5].year + 1900) + "-0" + (obj[5].month + 1) + "-" + obj[5].date;
                             }
                         } else {
                             var date = (obj[5].year + 1900) + "-" + (obj[5].month + 1) + "-" + obj[5].date;
                         }
						str +="<a  class='sjob_rec_box flex flex_align_center'>";
						str +=" <div class='sjob_L'>";
						if(obj[4]==null){
						str +="<img src='"+basePath+"page/ea/main/production/resume/resumeManagement/images/sjob_01.png' > </div>";
						}
						str +="<img src="+basePath+obj[4]+" > </div>";
						str +="<div class='sjob_m flex_1'>";
						str +="<p class='sjob_name'>";
						str +="<span>"+obj[0]+"</span>";
						str +=" </p><p class='sjob_com'>";
						str +="<span>"+obj[1]+"</span></p>";
						str +=" <p class='sjob_area'>";
						str +=" <span>投递时间"+date+"   "+time+"</span>";
						str +=" </p> </div></a>";
					}
							$("#div0").append(str);
							/* $("#div1").empty();
							$("#div2").empty();
							$("#div3").empty(); */
					/*
						if(ob=="00"){
							$("#div0").append(str);
							$("#div1").empty();
							$("#div2").empty();
							$("#div3").empty();
						}else if(ob=="01"){
							$("#div0").empty();
							$("#div2").empty();
							$("#div3").empty();
							$("#div1").append(str);
						}else if(ob=="02"){
							$("#div0").empty();
							$("#div1").empty();
							$("#div3").empty();
							$("#div2").append(str);
						}else if(ob=="03"){
							$("#div0").empty();
							$("#div1").empty();
							$("#div2").empty();
							$("#div3").append(str);
						} */
				},
				error : function(data) {
				}
			});




            //点击导航切换样式以及下边的显示部分
            $(".inv_nav a").tap(function(){
                var _index=$(".inv_nav a").index($(this));
                $(this).addClass("inv_navcur").siblings("a").removeClass();
                $(".inv_con").eq(_index).show().siblings().hide();
            })
        })

    </script>
</body>

</html>
