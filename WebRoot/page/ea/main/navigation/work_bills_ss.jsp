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
<title>行政管理科集团汇总-</title>
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
			本机尚未安装卓正OFFICE组件，请安装浏览器上方黄色提示条或弹出提示框中的卓正OFFICE组件。</div>
	</OBJECT>

	<br />
	<br />
	<table style="width: 100%; border: 0; padding: 0; margin: 0">
		<tr>
			<td>
				<table>
					<tr>
						<td width="120" rowspan="2">
							<div class="na_back_img_ks"></div>
							<div class="center_a">
								<strong>精神建设管理</strong>
							</div></td>
						<td width="55">
							<div class="na_back_img_jt_xs"></div></td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/logoo/ea_getEnterpriseLogoList.jspa?'"></div>
										<div class="center_a">企业形象管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/spiritgroup/ea_getEnterpriseSpiritList.jspa?'"></div>
										<div class="center_a">企业精神管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/culturegroup/ea_getEnterpriseCultureList.jspa?'"></div>
										<div class="center_a">企业文化管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/ideagroup/ea_getEnterpriseIdeaList.jspa?'"></div>
										<div class="center_a">企业理念管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/goalgroup/ea_getEnterpriseGoalList.jspa?'"></div>
										<div class="center_a">企业目标管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/mannergroup/ea_getEnterpriseMannerList.jspa?'"></div>
										<div class="center_a">工作态度管理</div></td>

								</tr>
							</table></td>
					</tr>
					<tr>
						<td width="55">
							<div class="na_back_img_jt_xx"></div></td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/managegroup/ea_getEnterpriseManageList.jspa?'"></div>
										<div class="center_a">管理理念管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/meritoriousgroup/ea_getEnterpriseMeritoriousList.jspa?'"></div>
										<div class="center_a">企业功臣管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/reportgroup/ea_getEnterpriseReportList.jspa?'"></div>
										<div class="center_a">企业纪实管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/work_bills_a.jsp?'"></div>
										<div class="center_a">公益活动管理</div></td>
									<td width="110">
										<div class="na_back_img"></div>
										<div class="center_a">奖状奖牌管理</div></td>

								</tr>
							</table></td>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td width="120" rowspan="2">

							<div class="na_back_img_ks"></div>
							<div class="center_a">
								<strong>行政建设管理</strong>
							</div></td>
						<td width="55">
							<div class="na_back_img_jt_xs"></div></td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>ea/documentsummary/ea_getSummaryDocList.jspa?module=contract&type=group&date=Math.random()'"></div>
										<div class="center_a">企业合同管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>ea/documentsummary/ea_getSummaryDocList.jspa?module=regime&type=group&date=Math.random()'"></div>
										<div class="center_a">制度管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/enterprisestamp/ea_getListEnterpriseStamp.jspa?type=group'"></div>
										<div class="center_a">电子印章管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/enterprisestamp/ea_getListGeneralStamp.jspa?type=group'"></div>
										<div class="center_a">普通印章管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/enterprisecitationgroup/ea_getEnterpriseCitationList.jspa?'"></div>
										<div class="center_a">奖状奖牌管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>ea/enterprisevideogroup/ea_getEnterpriseVideoList.jspa?'"></div>
										<div class="center_a">录像管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/corporationPhotogroup/ea_getCorporationPhotoList.jspa?'"></div>
										<div class="center_a">图片管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>ea/enterpriseartgroup/ea_getEnterpriseArtList.jspa?'"></div>
										<div class="center_a">文化艺术管理</div></td>
								</tr>
							</table></td>
					</tr>
					<tr>
						<td width="55">
							<div class="na_back_img_jt_xx"></div></td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>page/ea/main/navigation/logistics_conference.jsp'"></div>
										<div class="center_a">现场会议管理</div></td>
									<td width="110">
										<div class="na_back_img"></div>
										<div class="center_a">视频会议管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/fileManageGroup/ea_getaFileManageGroupList.jspa?'"></div>
										<div class="center_a">文件管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/office_fileg.jsp?'"></div>
										<div class="center_a">企业文书管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>ea/documentsummary/ea_getSummaryDocList.jspa?module=CountReg&type=group&date=Math.random()'"></div>
										<div class="center_a">国家法规管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>ea/documentsummary/ea_getSummaryDocList.jspa?module=InduReg&type=group&date=Math.random()'"></div>
										<div class="center_a">行业法规管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>ea/archive/ea_getArchiveList.jspa?catemodule=global&type=group'"></div>
										<div class="center_a">档案管理</div></td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>ea/documentsummary/ea_getSummaryDocList.jspa?module=MeetRecord&type=group&date=Math.random()'"></div>
										<div class="center_a">会议记录管理</div></td>

								</tr>
							</table></td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>
