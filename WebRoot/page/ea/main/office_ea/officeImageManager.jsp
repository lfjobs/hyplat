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
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache" />
<title>办公室形象管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>UploadFile/ImageUploader.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<style type="text/css">
a {
	font-size: 12px;
	color: #5e5e5e;
	text-decoration: none;
}

a:HOVER {
	color: red
}

#preview {
	width: 260px;
	height: 190px;
	border: 1px solid #000;
	overflow: hidden;
}

#imghead {
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image
		);
}
</style>
<script type="text/javascript">
		var basePath = '<%=basePath%>';
	var officeImageManagerId = '';
	var search = '${search}';
	var notoken = 0;
	var pNumber = '${pageNumber}';
	var token = 0;
</script>

<script src="<%=basePath%>js/ea/office_ea/officeImage_manager.js"></script>
<!-- 检测输入字数统计 -->
<script type=text/JavaScript>
	function checkLength(wo) {
		var len = $(wo)[0].value.length;
		if (len > 140) {
			$(wo).val($(wo).val().substr(0, 140));//只保留140个字
			len = 140;
		}
		$("#Len").html((140 - len));
	}
</script>
</head>
<body>
	<form name="imageForm" id="imageForm" method="post">
		<input type="submit" name="submit" style="display: none" />
		<div id="main_main" class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr>
						<th width="30" align="center">序号</th>
						<th width="60" align="center">公司</th>
						<th width="60" align="center">部门</th>
						<th width="60" align="center">办公室描述</th>
						<th width="100" align="center">形象显示</th>
						<th width="100" align="center">备注</th>

					</tr>
				</thead>
				<%
					int number = 1;
				%>
				<tbody>
					<s:iterator value="pageForm.list">
						<tr id="${officeImageManagerId}" class="td_bg01 saveAjax"
							class="trclass">
							<td class="td_bg01"><input type="radio" name="a"
								class="JQuerypersonvalue" value="${officeImageManagerId}" /> <input
								type="hidden"  name="officeImageKey" id="officeImageKey"
								value="${officeImageKey}" /> <input type="hidden"
								name="officeImageManagerId" id="officeImageManagerId"
								value="${officeImageManagerId}" /> <input type="hidden"
								name="companyid" id="companyid" value="${companyid}" /> <input
								type="hidden" name="organizationid" id="organizationid"
								value="${organizationid}" /> <input name="officeDescription"
								id="officeDescription" value="${officeDescription}" /> <input
								name="imageShow" id="imageShow" value="${imageShow}" /> <input
								name="mark" id="mark" id="mark" value="${mark}" />
							</td>
							<%
								number++;
							%>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/image/ea_getOfficeImageManagerList.jspa?officeimagemanager.officeImageManagerId=${officeimagemanager.officeImageManagerId}&pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
				framespacing="0" height="0"></iframe>
		</div>
		<!-- 办公室形象管理添加 -->
		<div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%"
			id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display:none" />
				<div class="content">
					<div class="contentbannb">
						<div class="newdrag">
							办公室形象管理添加
							<div class="close"></div>
						</div>
					</div>

					<table width="699" height="250"
						style="background-color:white;color: #5F5F5F" border="0"
						id="stafftable" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right"><span>公司名称：</span></td>
							<td style="width: 220px"><span id="comName"
								style="color:red;">系统生成</span></td>
							<td align="left" style="width:65px">部门名称：</td>
							<td><span id="depname" style="color:red">系统生成</span></td>
						</tr>
						<tr>
							<td align="right" valign="top"><div style="margin-top: 12px">办公室描述：</div>
							</td>
							<td align="left" colspan="3"><textarea maxlength=140
									onpropertychange="checkLength(this);" id="wb_ta" cols="60"
									rows="5" style="color:#CDCDCD;overflow-y: scroll;">
						</textarea><span id="Len">140</span>/140</td>
						</tr>
						<tr>
							<td colspan="4" align="center"><img
								src="<%=basePath%>images/ea/office/uploadimg.png"
								style="margin-left: 20px;cursor: pointer;" id="uploadfileimage" />
							</td>
						</tr>

						<tr>
							<td colspan="5" align="center"><input
								name="carInformation.carKey" id="carKey" type="hidden"
								class="input" size="20" /> <input name="carInformation.carID"
								id="contactUserID" type="hidden" class="input" size="20" /> <img
								src="<%=basePath%>images/sytemicon/save.png"
								style="cursor:pointer;" /> <input name="sub"
								value="${session_value}" type="hidden" /> <!-- 代替token--> <img
								src="<%=basePath%>images/sytemicon/cancle.png"
								style="cursor:pointer;" onclick="cancle()" />
							</td>
						</tr>
					</table>

				</div>
				<s:token></s:token>
			</form>
		</div>
		<!-- 上传照片对话框 -->
		<div class="contentbannb jqmWindow jqmWindowcss7"
			style="top: 10% ;background-color: white;" id="jqMode2">
			<form name="uploadForm" id="uploadForm" method="post"
				enctype="multipart/form-data">
				<%-- <%@ include file="/index.html"%> --%>
				<s:token></s:token>
			</form>
			<div align="center">上传到&nbsp;
			<select id="selectbox" size="1" name="photoboxname" style="width:150px;" onclick="selectphotoalbum()">
			<option>其它</option>
			</select>&nbsp;或者<a href="#" id="newphotoflore">新建相册</a>
			<input type="button"  value="关  闭" class="input-button" id="closeuploadpicture"></div>
		</div>
		<!-- 添加相册 -->
		<div class="jqmWindow"
			style="top: 30%;left:30%;background-color: white; height:165px;width:323px;z-index: 9999;"
			id="jqMode3">
			<form name="phototypeForm" id="phototypeForm" method="post" enctype="multipart/form-data">
				<input type="submit" name="submit" style="display:none" />
				<table width="323px" height="163px" border="0" cellspacing="0"
					cellpadding="0">
					<tr bgcolor="#F3F3F3">
						<td height="25" colspan="2"><div class="newdrag">
								<strong>新建相册</strong>
							</div></td>
						
					</tr>
					<tr>
						<td width="67">相册名称：</td>
						<td width="250"><input type="text" name="photoname"  id="photoname"
							style="width:250px" />
						</td>
					</tr>
					<tr>
						<td>相册描述：</td>
						<td><textarea name="textarea" id="photodescrib" name="photodescrib" style="width:250px;height:50px"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div align="center">
								<input type="button" class="input-button"   onclick="savephotoalbum()" value="保存"/>&nbsp;&nbsp; 
								<input type="button" class="input-button" id="cancleaddflore" value="取消" />
								<input type="hidden" name="corphotobox.photoBoxID" id="photoBoxID" />
				            	<input type="hidden" name="corphotobox.key" id="key" />
				            	<input type="hidden" name="netDisk.companyID" id="companyID" />
				            	<input type="hidden" name="netDisk.organizationID" id="organizationID" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</form>
	<!--JS遮罩层-->
	<div id="fullfilebg"></div>
	<script type="text/JavaScript">
	function savephotoalbum(){
			 var photoname1 = $("#photoname").val();
			 var photodescrib1 = $("#photodescrib").val();
	 if($.trim($("#photoname").val())=="")
	 { 
	 alert("相册名称不能为空!"); 
	 } else{
		 $.ajax({ 
			 type: "post", 
			 url: "<%=basePath%>ea/image/sajax_ea_savephototype.jspa?photoname="+ encodeURI(encodeURI(photoname1))  + "&photodescrib="+ encodeURI(encodeURI(photodescrib1)),
			 success: function(){alert("保存成功!");$("#jqMode3").jqmHide();
		$("#jqMode2").jqmShow();} //操作成功后的操作
			 });  
	 }
}
function  selectphotoalbum(){
    var url = basePath+ "ea/photomanager/sajax_n_ea_selectphotoalbum.jspa";
    $.ajax({ 
			 url : encodeURI(url),
			 type : "get",
			 async : true,
			 dataType : "json",
			 success: function(cc){ 
			 		var member = eval("(" + cc + ")");
							var nologin = member.nologin;
							if (nologin) {
								document.location.href = basePath
										+ "page/ea/not_login.jsp";
							}
							alert("操作成功！")
					} //操作成功后的操作
			 });  
}
</script>
</body>
</html>
