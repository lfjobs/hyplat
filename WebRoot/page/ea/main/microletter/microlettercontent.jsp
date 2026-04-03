<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<title>微信内容管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/microletter/microlettercontent.js"></script>
<script src="<%=basePath%>js/ea/microletter/PreviewImage.js"></script>
<script type="text/javascript">
   var  microlettermenucontentid = '';
   var  basePath='<%=basePath%>';
	var bpageNumber = ${pageNumber};
	var search = '${search}';
	var token = 0; 
	var microlettermenukey = '${dtMicroletterMenu.microlettermenukey}';
	
</script>
</head>
<body>
	<div id="main_main" class="main_main">
		<table class="address">
			<thead>
				<tr>
					<th width="30" align="center">选择</th>
					<th width="20" align="center">序号</th>
					<th width="93" align="center">标题描述</th>
					<th width="100" align="center">创建时间</th>
					<th width="160" align="center">更新时间</th>
					<th width="240" align="center">文本描述</th>
					<th width="100" align="center">图片描述</th>
				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr class="td_bg01 saveAjax" id="${microlettermenucontentid}">
						<td class="td_bg01"><input type="radio" name="a"
							class="JQuerypersonvalue" value="${microlettermenucontentid}" />
						</td>
						<td class="td_bg01"><span><%=number%></span></td>
						<td class="td_bg01"><span id="microlettermenucontentlabel">${microlettermenucontentlabel}
						</span></td>
						<td class="td_bg01"><span id="microlettermenucontentcdate">${fn:substring(microlettermenucontentcdate,0,11)}</span>
						</td>
						<td class="td_bg01"><span id="microlettermenucontentudate">${fn:substring(microlettermenucontentudate,0,19)}</span>
						</td>
						<td class="td_bg01"><span id="microlettermenucontent">${microlettermenucontent}</span>
						</td>
						<td class="td_bg01"><span id="microlettermenucontentimageurl"
							style="display:none">${microlettermenucontentimageurl}</span> <s:if
								test="microlettermenucontentimageurl==null||microlettermenucontentimageurl==''">无</s:if>
							<s:else>
								<span id="Photo"
									onclick="lookImage('${microlettermenucontentimageurl}');"><a
									href="#">查看</a>
								</span>
							</s:else> <span id="microlettermenucontentid" style="display:none">${microlettermenucontentid}</span>
							<span id="microlettermenucontentkey" style="display:none">${microlettermenucontentkey}</span>
							<span id="microlettermenukey" style="display:none">${dtMicroletterMenu.microlettermenukey}</span>
						</td>
					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/microlettercontent/ea_getDtMicroletterMenuContentList.jspa?pageNumber=${pageNumber}&search=${search}&dtMicroletterMenu.microlettermenukey=${dtMicroletterMenu.microlettermenukey}"></c:param>
		</c:import>
	</div>

	<!--搜索窗口 -->
	<div class="jqmWindow " style="width: 270px;right: 35%; top:20%"
		id="jqModelSearch">
		<form name="postSearchForm" id="postSearchForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display:none" />
			<div class="drag">
				查询信息
				<div class="close"></div>
			</div>
			<table id="cataffSearchTable">
				<tr>
					<td>图片编码：</td>
					<td><input name="corporationPhoto.corporationPhotoCode" /></td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="tosearch" value=" 查询 " /><input
					name="search" type="hidden" value="search" />
			</div>
		</form>
	</div>


	<!--查看 -->
	<form name="cstaffForm" id="cstaffForm" method="post" action=""
		enctype="multipart/form-data">
		<input type="submit" name="submit" style="display:none" />
		<div class="jqmWindow jqmWindowcss" style="top: 10%" id="jqModel">

			<div class="drag">
				微信内容编辑
				<div class="close"></div>
			</div>

			<div class="content">
				<div class="contentbannb"></div>
							<table width="600"  id="stafftable">
								<tr>
									<td width="100" align="right">标题描述：</td>
									<td width="200" align="left"><input
										name="dtMicroletterMenuContent.microlettermenucontentlabel"
										type="text" id="microlettermenucontentlabel" style="width: 100px" class="ckTextLength"
										maxlength="40"/>
									</td>
									<td width="100" align="right">文件：</td>
									<td width="125" rowspan="3" align="left">
									<div id="divPreview">
									<img id="pic" 
										width="99" height="135" />
									</div>	
									</td>
								</tr>
								<tr>
									<td height="37" align="right">创建时间：</td>
									<td width="120"><input
										name="dtMicroletterMenuContent.microlettermenucontentcdate"
										type="text" id="microlettermenucontentcdate"
										readonly="readonly" style="width: 100px" /></td>
								</tr>
								<tr>
									<td height="41" align="right">更新时间：</td>
									<td><input id="microlettermenucontentudate" type="text"
										class="input"
										name="dtMicroletterMenuContent.microlettermenucontentudate"
										readonly="readonly" style="width: 150px" />
									</td>
								</tr>

								<tr>
									<td></td><td></td>
									<td width="60" align="right">上传文件：</td>
									<td width="125" align="right"><input id="staffphoto" onchange="PreviewImage(this,'pic','divPreview')"
										name="dtMicroletterMenuContent.photo" type="file" 
										 size="15"  /> <input
										name="dtMicroletterMenuContent.microlettermenucontentimageurl"
										type="hidden" class="fileNum"
										id="microlettermenucontentimageurl" />
									</td>
								</tr>
								<tr>
									<td align="right" height="41">文本描述：</td>
									<td colspan="3" align="left">
									<textarea rows="3" cols="50" id="microlettermenucontent"
										 style="resize: none;overflow-y: scroll;"
										name="dtMicroletterMenuContent.microlettermenucontent"
										class="ckTextLength"
										maxlength="1800"
									></textarea>	
										
										 <input
										name="dtMicroletterMenuContent.microlettermenucontentid"
										type="text" id="microlettermenucontentid" size="20" style="display:none"/> <input
										type="text"
										name="dtMicroletterMenuContent.microlettermenucontentkey"
										id="microlettermenucontentkey" style="display:none"/> <input type="hidden"
										name="dtMicroletterMenu.microlettermenukey"
										id="microlettermenukey"
										value="${dtMicroletterMenu.microlettermenukey}" /></td>
								</tr>
							</table>
			</div>

			<div align="center">
				<input type="button" class="input-button"
					style="cursor:pointer;width:80px;" id="tosave" value="保存 " /> <input
					type="button" class="input-button JQueryreturn"
					style="cursor: pointer; width: 80px;" value="取消" />
			</div>
		</div>
		<s:token></s:token>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>
