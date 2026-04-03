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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/drive/resest1.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/drive/mo_student.css">
    <script type="text/javascript" src="<%=basePath%>js/ea/production/resume/resumeManagement/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/drive/jx_inform.js"></script>
    <title>驾校通知</title>
</head>

<body>
    <!-- header 开始  -->
    <header class="mem_header">
        <a href="javascript:history.back(-1)" class="back"></a>
        <h1>驾校通知</h1>
        <a href="javascript:void(0);" class="head_R"><i class="head_setico"></i><span class="inform_headR">完成</span></a>
    </header>
    <!--  header 结束  -->
    <!-- 页面内容 开始  -->
    <div class="wrap_page">
        <dl class="inform_wrap">
        	<!-- js拼接 -->
        </dl>
        <div class="inform_fixed clearfix">
            <span>全选</span>
            <a href="javascript:void(0);" class="inform_del" onclick="del()"><span>删除通知消息</span></a>
        </div>
    </div>
    <!--  页面内容 结束 -->
    <script>
    	var basePath = '<%=basePath%>';
    	var pageNumber;
		var	pageCount;
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
        $(document).on("click",".head_R",function(){
        	if($(".judge").val()!="1"){
        		var ele = $(".inform_headR"),
                        fix = $(".inform_fixed");
                    if (ele.is(":hidden")) {
                        $(".inform_wrap").addClass("check");
                        ele.show().prev().hide();
                        fix.show();
                    } else {
                        $(".inform_wrap").removeClass("check");
                        ele.hide().prev().show();
                        fix.hide();
                        All_noselect();
                    }
        	}
                    
        });
        $(document).on("click",".inform_check input",function(){
            $(this).next().toggleClass("select_ico");
            var a = $(".check_state").length;
            var b = $(".select_ico").length;
            if(a == b){
            	$(".inform_fixed>span").addClass("all_select");
            }else{
            	$(".inform_fixed>span").removeClass();
            }
        })
        $(document).on("click",".inform_fixed>span",function(){
        	var l= $(".inform_fixed").find(".all_select").length;
        	if(l==0){
        		Aselect();
        	}else{
        		All_noselect();
        	} 
        })
        
                    //全选
                    function Aselect() {
                        $(".inform_check input").each(function() {
                            this.checked = true;
                            $(this).next("i").addClass("select_ico");
                            $(".inform_fixed>span").addClass("all_select");
                        });
                    }
                    //全不选
                    function All_noselect() {
                        $(".inform_check input").each(function() {
                            this.checked = false;
                            $(this).next("i").removeClass("select_ico");
                            $(".inform_fixed>span").removeClass();
                        });
                    };
                   
    </script>

</body>

</html>
