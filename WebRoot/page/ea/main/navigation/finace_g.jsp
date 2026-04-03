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
<title>公司税务管理-</title>
<%-- <link href="<%=basePath %>css/navegate.css" rel="stylesheet" type="text/css" /> --%>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
<script>
function clickAction(action,parater){
if(parater == '1'){
var treeID = '<%=session.getAttribute("organizationID")%>';
			window.location.href = action + treeID;
			return;
		}
		window.location.href = action;
	};
</script>
</head>
<body>
	<br />
	<table style="width: 100%; border: 0; padding: 0; margin: 0">
		<tr>
			<td>
				<table>
					<tr>
						<td width="120">
							<div class="na_back_img_ks"></div>
							<div class="center_a">
								<strong>报税出纳管理处</strong>
							</div></td>
						<td width="55">
							<div class="na_back_img_jt_hx"></div></td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>
