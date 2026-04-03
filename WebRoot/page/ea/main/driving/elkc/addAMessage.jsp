<!DOCTYPE HTML>
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
	<script type="text/javascript" src="<%=basePath%>js/ea/elkc/setHtmlFont.js"></script>
	<link rel="stylesheet" href="<%=basePath%>css/ea/elkc/base.css">
	<link rel="stylesheet" href="<%=basePath%>css/ea/elkc/task_message.css">
	<script src="<%=basePath%>js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/driving/elkc/addAMessage.js"></script>
	<title>消息详情</title>


	<script type="text/javascript">
        var basePath="<%=basePath%>";
        var staffID= "${param.staffID}";
        var companyid = "${param.companyid}";
        var pageNumber = 0;
        var pageCount;

	</script>

</head>

<body>
<header class="com_head">
	<a href="javascript:void(0)" class="back" onclick="javascript: window.history.go(-1);return false;"></a>
	<h1>消息详情</h1>
</header>
<div class="wrap_page">
	<div class="mes_wrap">
		<div class="mes_part clearfix">
			<div class="mes_L">主题</div>
			<div class="mes_R mes_tit_box">
				<input type="text" class="theme_inp" value="" maxlength="125">
			</div>
		</div>
		<div class="mes_part clearfix">
			<div class="mes_L">正文</div>
			<div class="mes_R mes_con_box">
				<textarea class="mes_main_inp" maxlength="250" ></textarea>
			</div>
		</div>
		<div class="mes_part clearfix">
			<div class="mes_L">图片</div>
			<div class="mes_R mes_img_box clearfix">
				<input type="file" class="up_img_btn" accept="image/*">
				<div class="img_box"></div>
			</div>
		</div>
	</div>
	<div class="receive_wrap">
		<div class="receive_tit">
			接收人
		</div>
		<div class="receive clearfix">

			<a  class="assign_btn"></a>
		</div>
	</div>
	<a href="javascript:;" class="submit_btn">提 交</a>
</div>
<!--选择接收人-->
<div class="nest_page assign_wrap">
	<div class="nest_hd">
		<a href="javascript:;" class="nest_back sele_cancel">取消</a>
		<span>选择联系人</span>
		<a href="javascript:;" class="head_R sele_sure">确定(<span>0</span>)</a>
	</div>
	<div class="nest_bd">
		<div class="sele_search_wrap">
			<input type="text" class="sele_search">
			<a href="javascript:;" class="sele_all">全选</a>
		</div>
		<div class="sele_wrap">
			<div class="sele_part">
				<div class="sele_list">
					<%--js拼接--%>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
    var submitDateArray = []; //上传图片暂存数组，需遍历加入fromData AJAX提交
    $(document).on("change",".up_img_btn",function(){
        var file = this.files[0];
        if (file) {
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function() {
                var url = reader.result;
                var _html = '<img src=' + url + ' alt="">';
                $(".mes_img_box").append(_html);
                submitDateArray.push(file);
            }
        }
    })
    //全选
    $(document).on("click",".sele_all",function(){
        $(".sele_wrap").find("input[type=checkbox]").each(function() {
            this.checked = true;
            var n = $("input[type=checkbox]:checked").length;
            //console.log(n);
            $(".sele_sure span").text(n);
        })
    })
    //确定位置选择数量变化
    $(document).on("change","input[type=checkbox]",function(){
        var n = $("input[type=checkbox]:checked").length;
        //console.log(n);
        $(".sele_sure span").text(n);
    })
    //点击取消
    $(document).on("click",".sele_cancel",function(){
        initSele();
    })
    //选择接收人
    $(document).on("click",".assign_btn",function(){
        $(".assign_wrap").show();
        ajax();
    })
    //初始化选择
    function initSele(){
        $(".assign_wrap").hide();
        $(".sele_wrap").find("input[type=checkbox]").each(function() {
            this.checked = false;
        })
        $(".sele_sure span").text(0);
        pageNumber = 0;
        $(".sele_list").empty();
    }
    //点击确定
   $(document).on("click",".sele_sure",function(){
           $(".receive_box").remove();
        if($(this).find("span").text()!=0){
            var total =[];
            $(".sele_wrap").find("input[type=checkbox]:checked").each(function(){
                var _name = $(this).parent().find("span").text();
                var headimg_url = $(this).parent().find("img").attr("src");
                var staffid = $(this).parent().attr("data-staffid");
                var _html ='<div class="receive_box" data-staffid='+staffid+'><img src='+headimg_url+' alt='+_name+'><span>'+_name+'</span></div>';
                total.push(_html);
            })
            var total_html=total.join('');
            $(".assign_btn").before(total_html);
            initSele();
        }
    })
</script>
</body>

</html>