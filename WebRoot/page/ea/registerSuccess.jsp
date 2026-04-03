<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>timeout</title>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript">
	  alert("操作成功!");
  	  if(confirm("是否继续注册？")){
          window.parent.document.getElementById("rightFrame").src="<%=basePath%>/page/ea/register.jsp";
      }else{
		  window.parent.document.getElementById("rightFrame").src="<%=basePath%>/ea/comregist/ea_getCompanyRegistList.jspa";
      }
	</script>
  </head>
  <body>
  </body>
</html>