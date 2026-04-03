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
<title>教研科科二（场地）</title>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
</head>

<body>
	<table >
		<tr>
			<td  rowspan="2">
				<div class="na_back_img_ks"></div>
				<div class="center_a">
					<strong> 教研科</strong>
				</div></td>
			<td ><div class="na_back_img_jt_hx"></div>
			</td>
			<td><table >
					<tr>
						<td width="110" >
							<div class="na_back_img"></div>
							<div class="center_a">
								<span >教学设备管理</span>
							</div>
						</td>
						<td width="110" >
							<div class="na_back_img"
								onclick="document.location.href='<%=basePath%>/ea/academicadmin/ea_getCompanyListForIncumbent.jspa?'"></div>
							<div class="center_a">
								<span >教师管理</span>
							</div>
						</td>
						<td width="110" >
							<div class="na_back_img"
								onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=03&studentstatus=03&title=10'"></div>
							<div class="center_a">
								<span >学员管理</span>
							</div>
						</td>
						<td width="110" >
							<div class="na_back_img"></div>
							<div class="center_a">
								<span >教学内容管理</span>
							</div>
						</td>
						<td width="110" >
							<div class="na_back_img"></div>
							<div class="center_a">
								<span >学时管理</span>
							</div>
						</td>
						<td width="110" >
							<div class="na_back_img"></div>
							<div class="center_a">
								<span >教学进度管理</span>
							</div>
						</td>
						<td width="110" >
							<div class="na_back_img"
								onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/safeManager.jsp'"></div>
							<div class="center_a">
								<span >安全管理</span>
							</div>
						</td>
						<td width="110" >
							<div class="na_back_img"></div>
							<div class="center_a">
								<span >教学场地管理</span>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<script type="text/javascript">
function clickAction(action,parater){
if(parater == '1'){
var treeID = '<%=session.getAttribute("organizationID")%>';
				window.location.href = action + treeID;
				return;
			}
			window.location.href = action;
		}
	</script>
</body>
</html>
