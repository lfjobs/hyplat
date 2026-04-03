<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>没有权限</title>
	<script type="text/javascript">
	  var accountID = "${accountID}"
	  if(accountID.length){
	  alert("升级成功！此账户拥有管理员权限")
	  parent.parent.parent.parent.parent.window.location.href= "<%=basePath%>page/ea/index.jsp";
	  }
	  function updatecompany(){
	    if(confirm("确定升级？")){
		  window.document.location.href ="<%=basePath%>ea/caccount/ea_updateRelease.jspa?accountID=1";
		  }
	  }
	</script>
	</head>
	<body>
		<form method="post" action="<%=basePath%>plat/login.jspa">
		   <span>版本升级后此账户将拥有最高权限!同时其他角色需要重新配置权限</span>
			<span><input type="button" onclick="updatecompany()" value="升级"/></span>
		</form>
	</body>
</html>
