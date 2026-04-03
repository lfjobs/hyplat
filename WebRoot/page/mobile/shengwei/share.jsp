<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">
	<title>分享</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	<link href="<%=basePath%>/css/WFJClient/wfjhtmlStyle.css" rel="stylesheet" />
	<link href="<%=basePath%>css/WFJClient/wfjstyle.css" rel="stylesheet" />
	
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/WFJClient/PhoneWfj.js"></script>
	<script src="<%=basePath%>js/ea/wechat/wechartMenugrcp.js"></script>
	<script type="text/javascript">
		var basePath='<%=basePath%>';
		var token=0;
		var cpid="";
		var notoken=0;
		function clo(){
			document.location.href=basePath+"/ea/wfjcustomer/ea_getProductAgency.jspa"; 
		}
		
	</script>
	
	</head>
	<body>
	<%-- 分享 --%>
		<div class="wfjall">
			<div class="bdsharebuttonbox" data-tag="share_1" style="padding:20px 0px 0px 40px;">
				<input type="hidden" name="bdText" id="bdText" value="${bdText }"/>
				<input type="hidden" name="bdUrl" id="bdUrl" value="${bdUrl }"/>
				<input type="hidden" name="bdPic" id="bdPic" value="${bdPic }"/>
				<a class="bds_tsina" data-cmd="tsina"></a>
				<a class="bds_qzone" data-cmd="qzone" href="#"></a>
				<a class="bds_tqf" data-cmd="tqf"></a>
				<a class="bds_weixin" data-cmd="weixin"></a>
				<a class="bds_tqq" data-cmd="tqq"></a>
				<input type="button" class="btn02 JQueryreturns" name="button4" style="margin-top:5px;height:35px;width:35px;" value="关闭" onclick="clo()"/> 
			</div>
		</div>
	</body>
	<script>
		bdText= $("#bdText").val();	
		bdUrl= $("#bdUrl").val(); 	
		bdPic= $("#bdPic").val();
		window._bd_share_config = {
				common : {
					bdText : bdText,	
					bdUrl : bdUrl, 	
					bdPic : bdPic
				},
				share : [{
					"bdSize" : 32
				}]
				
				
		}
		with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src=basePath + '/js/WFJClient/share.js?cdnversion='+~(-new Date()/36e5)];
	</script>
	</html>