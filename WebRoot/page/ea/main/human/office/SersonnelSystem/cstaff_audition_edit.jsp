<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String filepath = request.getSession().getServletContext()
			.getRealPath("/");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>面试-口试-笔试</title>
<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>css/admin_mains.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/overlayer.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/photoup/CJL.0.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/photoup/ImagePreviewd.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/human/office/SersonnelSystem/cstaff_audition_edit.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var retoken = 0;
		var staffID = '';
		var personIdentityCard;
		var aa = '<%=request.getParameter("aa")%>';
		var showType = '';
		var roleID = '${account.roleID}';
		var select = 1;
		var photosizes = 0;
		var str = "";
		var temp = "";
</script>
	
</head>

<body>
	<div class="content" style="width:850px;">
		<div class="contentbannb">
			<div class="divtx">&nbsp;面试信息管理</div>
			<table class="JQueryflexme" border="0">
				<tr>
					<td></td>
				</tr>
			</table>
		</div>
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="biaoti box1">
			<tr>
				<td height="27" class="txt03">面试人员信息</td>
				<td align="right"></td>
			</tr>
		</table>
		<p style="display: none;">
			<object classid="clsid:E6E0A751-541A-4855-9A8D-35EB7122C950"
				id="SynIDCard1" name="SynIDCard1"
				codeBase="<%=basePath%>WEB-INF/plug-in/SynIDCard.Cab#version=1,0,0,1"
				width="0" height="0">
				<param name="_Version" value="65536" />
				<param name="_ExtentX" value="635" />
				<param name="_ExtentY" value="582" />
				<param name="_StockProps" value="0" />
			</object>
			<textarea rows="17" name="S1" cols="82"></textarea>
		</p>
		<div id="box1">
			<form name="box1Form" id="box1Form" method="post"
				enctype="multipart/form-data">
				<s:token />
				<table id="stafftable" width="99%" align="center" cellpadding="0"
					cellspacing="0" class="table">
					<tr>
						<td style="height:30;width:10% " align="right">员工姓名：</td>
						<td done0="10" done1="10"  style="width:40%">
							<span class="isShow">${audition.staffName}</span>
						 	<span id="staffID" style="display:none">${audition.staffID}</span>
						</td>
						<td align="right" style="width:10%" >身份证号：</td>
						<td done0="11" done1="11"  style="width:40%"><span class="isShow">${audition.staffIdentityCard}</span></td>
					</tr>
					<tr>
						<td style="height:30" align="right">应聘方向：</td>
						<td done0="12" done1="12"><span class="isShow">${audition.auditionDirection}</span></td>
						<td align="right">应聘岗位：</td>
						<td done0="13" done1="13"><span class="isShow">${audition.auditionPost}</span></td>
					</tr>
					<tr>
						<td style="height:30" align="right">面试地点：</td>
						<td><span class="isShow">${audition.place }</span>
						</td>
						<td align="right">面试部门：</td>
						<td><span class="isShow">${audition.auditionDept }</span>
						</td>
					</tr>
					<tr>
						<td style="height:30" align="right">面试时间：</td>
						<td><span class="isShow">${audition.auditionDate }</span>
						</td>
						<td align="right">面试考官：</td>
						<td><span class="isShow">${audition.examiner }</span>
						</td>
					</tr>
					<tr>
						<td style="height:30" align="right">工作经验：</td>
						<td colspan="3">
							<span class="isShow">${audition.experience }</span>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div style="overflow-y:scroll;" class="gdkd" >

			<div name="audition_k" id="">
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box2">
					<tr>
						<td height="27" class="txt03">口试</td>
						<td align="right"><a href="javascript:"
							onclick="changemenu('box2',2,'edit')" id="mord2" class="mord"
							style="color:#0066FF">编辑</a><a href="#"
							onclick="changemenu('box2',2,'close')" id="mord2_close"
							class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;</td>
					</tr>
				</table>
				<div id="box2" style="display:none;">
					<form name="box2Form" id="box2Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table contact">
							<tr>
								<td><input type="submit" name="submit"
									style="display: none" />
									<div style="width: 100%;">
										<iframe
											url="ea/saudition/ea_getAuditionkb.jspa?start=1&staffID="
											src="" name="main" height="80px" width="100%" marginwidth="0"
											marginheight="0" scrolling="no" frameborder="0"
											id="mainframe2" border="0" framespacing="0"
											noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>

			<div name="audition_b" id="">
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box3">
					<tr>
						<td height="27" class="txt03">笔试</td>
						<td align="right"><a href="javascript:"
							onclick="changemenu('box3',3,'edit')" id="mord3" class="mord"
							style="color:#0066FF">编辑</a><a href="#"
							onclick="changemenu('box3',3,'close')" id="mord3_close"
							class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;</td>
					</tr>
				</table>
				<div id="box3" style="display:none;">
					<form name="box3Form" id="box3Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table contact">
							<tr>
								<td><input type="submit" name="submit"
									style="display: none" />
									<div style="width: 100%;">
										<iframe
											url="ea/saudition/ea_getAuditionkb.jspa?start=2&staffID="
											src="" name="main" width="100%" marginwidth="0" height="80px"
											marginheight="0" scrolling="no" frameborder="0"
											id="mainframe3" border="0" framespacing="0"
											noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>

		</div>
	</div>

	<script type="text/javascript">

		$(function() {
			setTimeout(function() {
				$("div.gdkd").css({
					"height" : GetPageSize()[3] - 350 + "px"
				});
			}, 100);
			$(window).resize(function() {
				setTimeout(function() {
					$("div.gdkd").css({
						"height" : GetPageSize()[3] - 350 + "px"
					});
				}, 100);
			});
		});
	</script>
</body>
</html>