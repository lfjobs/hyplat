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
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath %>css/BuildPlatform/base.css">
    <link rel="stylesheet" href="<%=basePath %>css/BuildPlatform/AL.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/FastApplication.js"></script>
    <title>快捷应用</title>
</head>
<script type="text/javascript">
    var basePath='<%=basePath%>';
    var authState = '${concom.authState}';
    var logintype="${param.logintype}";
    $(function () {
        //判断是否免登录
        if(logintype==1){
            $("#flag").hide();
        }else{
            $("#flag").show();
        }
    })
</script>
<body>
    <!-- 页头 开始  -->
    <header class="com_head">        
        <a href="<%=basePath %>mobile/office/mobileoffice_resourceSystem.jspa?" class="back" id="flag"></a>
        <h1>快捷应用</h1>
        <a href="javascript:;" class="head_R edit">编辑</a>
    </header>
    <!--  页头 结束  -->
    <!-- 页面内容 开始  -->
    <div class="wrap_page">
        <div class="al_wrap">        
        </div>
    </div>
    <!--  页面内容 结束 -->
    <!--遮罩层 开始-->
    <div class="overlay" style="display:block">
        <div class="overlay_page">
            <header class="com_head">
                <a href="javascript:;" class="back overlay_back"></a>
                <h1 class="al_head">快捷应用</h1>
            </header>
            <div class="wrap_page">
                <ul class="s_nav_list hr" data-name="人事应用">
                </ul>   
            </div>
        </div>
    </div>
    <!--遮罩层 结束-->
    <!--公司认证状态弹窗 开始-->
    <div class="popup_rz" style="display:none;">
        <div class="rz_state">
              <!--公司未认证弹窗 开始-->
               <div class="rz_con">
                   <a href="javascript:void(0)" class="rz_close"></a>
                    <div class="bg_top"></div>
                    <img src="<%=basePath %>images/BuildPlatform/rz_img.png" class="rz_img" alt="">
                    <div class="rz_infotop"></div>         
                    <div class="rz_info">为了给您提供更好的服务请选择立即认证</div> 
                    <a href="javascript:void(0)" class="rz_btn">立即认证</a>    
               </div>
                <!--公司未认证弹窗 结束-->
                <!--公司正在认证弹窗 开始-->
<!--
               <div class="rz_con">
                   <a href="###" class="rz_close"></a>
                    <div class="bg_top"></div>
                    <img src="images/rz_img.png" class="rz_img" alt="">
                    <div class="rz_infotop">您的公司信息正在认证</div>         
                    <div class="rz_info">我们将在5个工作日对您的认证审核完毕</div> 
                    <a href="###" class="rz_btn">我知道了</a>    
               </div>
-->
                <!--公司正在认证弹窗 结束-->
        </div>
    </div>
    <!--公司认证状态弹窗 结束-->
</body>
</html>
