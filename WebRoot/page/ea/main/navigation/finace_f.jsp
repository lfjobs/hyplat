<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>财务财务-</title>
		<link href="<%=basePath %>css/navegate.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		<script>
function clickAction(action,parater){
if(parater == '1'){
 var treeID = '<%=session.getAttribute("organizationID")%>';
 window.location.href= action+treeID;
 return;
 }
 window.location.href= action;
}
</script>
	</head>
	<body>
	    <div class="roundedCorners" style="margin-top: 10px;">
	            <table width="350" height="69" border="0" cellpadding="0"
				cellspacing="0" style="margin: 0 auto;">
				<tr>
					<td width="98" rowspan="2" align="center">
						<a href="<%=basePath%>/ea/accountant/ea_getAccountantList.jspa"><img src="<%=basePath%>images/04.gif" width="50"
								height="50" border="0" /> <br />会计审核管理</a>
					</td>
					<td width="71" align="center">
						
					</td>
					<td width="136" height="71" align="center">
						<a
							href="<%=basePath%>ea/cashier/ea_getCashierList.jspa?">
							<img src="<%=basePath%>images/004.gif" width="50" height="50"
								border="0" />
							<br /> 出纳审核管理</a>
					</td>
				</tr>
			</table>
	    </div>
	   
		
	</body>
</html>
