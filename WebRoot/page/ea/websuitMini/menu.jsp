<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>5L5C微办公登录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
  	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
  	<style type="text/css"> 
#txt{ 
position:relative ;
margin-top: 25px;
} 
#foot{ 
position:absolute; 
bottom:0px; 
padding:0px; 
margin:0px ;
width: 98%;
font-size: 18px;
color:#467583;
}
</style>
<script>
$(document).ready(function(){ 
  var arrayObj = new Array();
  arrayObj[0]= "<%=basePath%>images/websuitMini/personnel_01.png";
  arrayObj[1]= "<%=basePath%>images/websuitMini/finance_01.png";
  arrayObj[2]= "<%=basePath%>images/websuitMini/office_01.png";
  arrayObj[3]= "<%=basePath%>images/websuitMini/production_01.png";
  arrayObj[4]= "<%=basePath%>images/websuitMini/marketing_01.png";
  var arrayObj2 = new Array();
  arrayObj2[0]= "<%=basePath%>images/websuitMini/personnel.png";
  arrayObj2[1]= "<%=basePath%>images/websuitMini/finance.png";
  arrayObj2[2]= "<%=basePath%>images/websuitMini/office.png";
  arrayObj2[3]= "<%=basePath%>images/websuitMini/production.png";
  arrayObj2[4]= "<%=basePath%>images/websuitMini/marketing.png";
  
    var index = 0;
    $("table img").each(function(index, element) {
		$(this).attr("index",index);
		index++;
		$(this).hover(function(){
		   $(this).attr("src",arrayObj2[$(this).attr("index")]);
		},function(){
		   $(this).attr("src",arrayObj[$(this).attr("index")]);
			});
    });
});
</script>
  </head>
<body style="background-color:#b2ebff">
<img src="<%=basePath%>images/websuitMini/5L5Cindex01_03.png"/>
<div id="txt">
  
  	<div style="height:380px;" align="center">
  		<table style="height:380px">
  			<tr><td><img src="<%=basePath%>images/websuitMini/personnel_01.png"/></td></tr>
  			<tr><td><img src="<%=basePath%>images/websuitMini/finance_01.png"/></td></tr>
  			<tr><td><img src="<%=basePath%>images/websuitMini/office_01.png" onclick="window.location.href='<%=basePath%>page/ea/websuitMini/index.jsp'"/></td></tr>
  			<tr><td><img src="<%=basePath%>images/websuitMini/production_01.png"/></td></tr>
  			<tr><td><img src="<%=basePath%>images/websuitMini/marketing_01.png"/></td></tr>
  		</table>
  	</div>
</div>
<div>
<div id="foot" align="center">本产品最终解释权归天太世统科技有限公司</div>
</div>

</body>
</html>
