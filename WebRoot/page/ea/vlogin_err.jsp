<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>注册失败页面</title>
		<meta http-equiv="Access-Control-Allow-Origin" content="*">
    	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
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
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css"/>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<style type="text/css">
        body{padding-bottom: 0px; background-color: #ffffff; margin: 0px;padding-left: 0px;padding-right: 0px; font-family: "宋体" , Arial, Helvetica, sans-serif;color: #666666;font-size: 12px;padding-top: 0px;}
        .wrap { width: 500px; margin: 0 auto; }
        .wrapmain{ width: 500px; margin: 0 auto; margin-top: 10px; background-color: #ffffff; }
        body, table, tr, td{font-size: 12px;}
        .logo{ width: 781px; height: 80px; margin-top: 20px; overflow: hidden;}
        .logo img{width: 80px;height: 80px; float: left; }
        .logotxt{ height: 80px;  line-height: 80px; float: left;margin-left: 20px;font-family: 微软雅黑; font-size: 32px; color: #4B78A1;}
        .main {position: relative; margin: 0 auto;}
        .c_left{width: 500px; height: 484px;}
        .c_left img {width: 780px;height: 484px;}
        .shipin{ width: 400px; height: 315px; position: absolute; left: 430px; top: 57px; padding-top: 20px;}
        .ziti{height: 40px; color: #00000;width: 75px; text-align: right; padding-right: 2px;font-size: 13px; }
        .notice { line-height: 26px; padding-top: 10px; margin: 0 auto; }
         #rettab tr td{ font-size:16px;}
        .ret_td_con{color:#4b78a1;}
        #mit{ color: red;}
    </style>
</head>
<body style="text-align: center;" onload="show();">
    <div class="wrap">
        <div class="logo">
            <img src="<%=basePath%>images/ea/login/logo.png" alt="" />
            <div class="logotxt">
                五层五清孵化管理体系</div>
        </div>
    </div>
    <div class="wrapmain">
        <div class="wrap">
            <div class="main fl">
            <div class="c_left fl">
            <div class="clear"></div>
			<form name="vForm" method="post" id="vForm" action="">
          <input type="submit" name="submit" style="display: none">
    	<table id="rettab" style="width: 360px; height: 350px;padding-top: 20px;">
			<tr>
				<th colspan="2" style="height:50px;font-size: 15px;">注册失败!</th>
			</tr>  
			<tr>
			<td height="30" colspan="2" algin="center"><span id="mit"></span>秒钟后自动跳转......</td>
			</tr> 
    	</table>
    	</form>
					</div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="wrap">
        <div class="notice">
            版权所有 北京天太世统科技有限公司 服务热线：010-64164005&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=basePath%>images/ea/login/knet.png"
                style="vertical-align: middle;"/>
        </div>
    </div> 
    <script type="text/javascript">
     var basePath = "<%=basePath%>";
    	   var i = 4;
		  function show(){
		    if (i>0){
		      i--;
		      $("#mit").text(i);
		    }else{
		    	document.location.href = basePath +"page/ea/vlogin.jsp";
		    }
		  }
		  setInterval('show()',1000);       
    </script>
   
</body>

</html>
