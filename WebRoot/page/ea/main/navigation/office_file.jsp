
<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>企业文书管理-</title>
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />	
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		<style type="text/css">

a:link,a:visited,a:active {
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}

a {
	color: #333;
	text-decoration: none;
}

a:active {
	color: #002f76;
	font-weight: bold;
}

.roundedCorners {
	width: 750px;
	padding: 10px;
	margin: auto;
	background-color: #FFF;
	border: 1px solid #CCE6F1;
	/* Do rounding (native in Safari, Firefox and Chrome) */
	-webkit-border-radius: 6px;
	-moz-border-radius: 6px;
}
</style>
		<script type="text/javascript" src="<%=basePath%>js/curvycorners.js"></script>
		<script type="text/JavaScript">
addEvent(window, 'load', initCorners);
function initCorners() {
var setting = {
tl: { radius: 6 },
tr: { radius: 6 },
bl: { radius: 6 },
br: { radius: 6 },
antiAlias: true
}
curvyCorners(setting, ".roundedCorners");
}</script>
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
		<!--   OBJECT标签，客户端控件引用    -->
		<OBJECT id="SOAOfficeCtrl"
			codeBase="<%=basePath%>js/cabs/ZSOffice.ocx#version=2,0,0,1"
			height="0" width="0"
			classid="clsid:AD06827C-D92F-4648-B880-138AF11E8A13" data=""
			VIEWASTEXT>
			<div align=center STYLE="color: red;">
				本机尚未安装卓正OFFICE组件，请安装浏览器上方黄色提示条或弹出提示框中的卓正OFFICE组件。
			</div>
		</OBJECT>

		<br />
		<table>
			<tr>
				<td>
					<table>
						<tr>
							<td  rowspan="2"><div class="na_back_img_ks"></div><div class="center_a"><strong>企业文书办</strong></div></td>
							<td><div class="na_back_img_jt_xs"></div></td>
							<td>
								<table >
									<tr>
										<td><div class="na_back_img"
											onClick="document.location.href='<%=basePath%>/ea/payGradeModulation/ea_getListForPage.jspa?'">
											</div><div class="center_a">工资级别单</div>
										</td>
										<td><div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/publicreceipts/ea_getRankPublicreceipt.jspa?'">
											</div><div class="center_a">员工级别单</div>
										</td>
										<td><div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/publicreceipts/ea_getOverPublicreceipts.jspa?'">
											</div><div class="center_a">加班申请单</div>
										</td>
										<td><div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/stafftransfer/ea_getstaffTransferList.jspa?'">
											</div><div class="center_a">人事调令单</div>
										</td>
										<td><div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/informbills/ea_getInformBillsList.jspa?'">
											</div><div class="center_a">通知单管理</div>
										</td>
										<td><div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/completeAllter/ea_getCmpleteAllterList.jspa?'">
											</div><div class="center_a">整改通知单</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							 <td><div class="na_back_img_jt_xx"></div></td>
							<td>
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td><div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/tasknotice/ea_getTaskNoticeList.jspa?'">
											</div><div class="center_a">任务通知单</div>
										</td>
										<td><div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/publicreceipts/ea_getLeaveBillsList.jspa?'">
											</div><div class="center_a">请假单管理</div>
										</td>
										<td><div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/carbills/ea_getcarBillsList.jspa?'">
											</div><div class="center_a">派车单管理</div>
										</td>
										<td><div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/publicreceipts/ea_getListRewardPunishment.jspa?type=1'">
											</div><div class="center_a">奖罚单管理</div>
										</td>
										<td><div class="na_back_img"
											onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=doc&d=<%=Math.random()%>'">
											</div><div class="center_a">公文流转单</div>
										</td>
										<td><div class="na_back_img"
											onclick="document.location.href='<%=basePath%>ea/docunfinish/ea_showDocUnfinishModule.jspa'">
											</div><div class="center_a">特办公文查询</div>
										</td>
										<td><div class="na_back_img">
											</div><div class="center_a">个人公文查询</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td><div class="na_back_img_ks"></div><div class="center_a"><strong>企业文件柜</strong></div></td>
							  <td><div class="na_back_img_jt_hx"></div></td>
							<td>
								<table >
									<tr>
										<td><div class="na_back_img" 
											onclick="document.location.href='<%=basePath%>/ea/filecabinet/ea_getListForFileCabinet.jspa?'">
											</div><div class="center_a">个人文件柜</div>
										</td>
										<td><div class="na_back_img" 
											onclick="document.location.href='<%=basePath%>ea/photoboxmanager/ea_getCorPhotoBoxList.jspa?'">
											</div><div class="center_a">图片管理办</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
