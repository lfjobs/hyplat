<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<%@ include file="/page/ea/BuildPlatform/wfjIndustryPlatfrom/coom.jsp"%>

<header>
   <ul>
       <li style="width: 10%;">
           <a href="javascript:history.go(-1)"><img src="<%=basePath%>images/BuildPlatform/left_jt.png"></a>
       </li>
       <li style="width: 80%;">注册成功</li>
       <li style="width: 10%;"></li>
       <div class="clearfix"></div>
   </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <img src="<%=basePath%>images/BuildPlatform/bg1.png" alt="" width="100%">
            <div class="zc_txt">
                <h5>恭喜你注册建设新的平台成功</h5>
                <p>请记录您的平台管理帐号和初始密码</p>
            </div>
            <div class="zc_password">
             	<p>组织机构：<span>${companyIdentifier}</span></p>
                <p>帐号：<span>sa</span></p>
                <p>密码：<span>123456</span></p>
            </div>
            <a id="onck"><img src="<%=basePath%>images/BuildPlatform/login.png" alt="" id="login"></a>
        </div>
        <div class="alert"></div>
    </div>
</div>
<script>
var compname="${companyIdentifier}";
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");
        $(".con").css("height",$(window).height()*0.92+"px");
       
        $("#onck").click(function(){
        url = basePath+"/mobile/office/mobileoffice_toMobileLogin.jspa?companyName="+compname+"&caccount.accountEmail=sa&caccount.accountPassword=123456";
		document.location.href=url;
		});
    });
</script>

</body>
</html>