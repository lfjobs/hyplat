<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="x-ua-compatible" content="ie=7" />
		<title>搜索列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/globalSearchList.js"></script>
		<script type="text/javascript">
   var  basePath='<%=basePath%>';           
   var  pNumber =${pageNumber};  
   var  search='${search}';
   var  token=0;
   var  fileCabinetId = '';

   </script>

  		<style type="text/css">
a {
	text-decoration: none;
}
</style>


	</head>
	<body>


		<div id="cabinet">
			<table class="cabinet0">
				<thead>
					<tr>
						<th width="70" align="center">
							选择
						</th>
						<th width="200" align="center">
							名称
						</th>
						<th width="250" align="center">
							操作
						</th>

					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>

					<s:iterator id="list" value="pageForm.list">


						<tr>
							<c:choose>
								<c:when test='${typeFolder=="Folder"}'>

									<td class="td_bg01">
										<img src="<%=basePath%>images/ea/office/fileCabinet/009.gif"
											width="20" height="30">
										<input type="radio" name="radioGroup"
											class="JQuerypersonvalue" value="${fileFolderId}" />
									</td>
									<td class="td_bg01">
										<span id="fileFolderName">${fileFolderName}</span>
									</td>
									<td class="td_bg01">
										<a href="javascript:enterFolder('${fileFolderId}');">进入文件夹</a>&nbsp;|
										&nbsp;
										<a href="javascript:viewAttribute('${fileFolderId}');">属性</a>&nbsp;
									</td>

								</c:when>

								<c:otherwise>
									<td class="td_bg01">
										<img src="<%=basePath%>images/ea/office/fileCabinet/007.png"
											width="20" height="30">
										<input type="radio" name="radioGroup"
											class="JQuerypersonvalue" value="${fileFolderId}" />
									</td>
									<td class="td_bg01">
										<span id="fileUploadName">${fileUploadName}</span>
									</td>
									<td class="td_bg01">
										&nbsp;
										<a href="javascript:viewAttribute('${fileUploadId}');">属性</a>&nbsp;|&nbsp;
										<a href="javascript:loadFile('${fileUploadPath}')">下载</a>&nbsp;
										|&nbsp;
										<!--<a href="javascript:updateFile('${fileUploadPath}')">更新</a>&nbsp;|&nbsp;  -->
										<span id='yulan_${fileUploadPath}'><a
											href="javascript:lookImage('${fileUploadPath}')">预览</a>
										</span>
									</td>
								</c:otherwise>
							</c:choose>

						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>

			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/filecabinet/ea_getFileOfFolderList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
      
      	<form id="folderAttribute" name="folderAttribute">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelFolderAttr">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					文件夹属性
					<div class="close">
					</div>
				</div>
				<center>
					<table cellspacing="25">
						<tr>
							<td align="left" style="white-space: nowrap;">
								文件夹名称：
							</td>
							<td align="left">
								<input type="text" id="fileFolderNameA" name="fileFolder.fileFolderName"/>
								<input type="hidden" name="fileFolder.fileFolderId" id="folderIdhid" />
							</td>
						</tr>
						<tr>
							<td align="left">
								描述信息：
							</td>
							<td align="left">
								<span id="descriptorA"></span>

							</td>
						</tr>
						<tr>
							<td align="left">
								占用空间：
							</td>
							<td align="left">
								<span id="usedSpaceA"></span>
							</td>
						</tr>
						<tr>
							<td align="left">
								文件个数：
							</td>
							<td align="left">
								<span id="fileNumberA"></span>
							</td>
						</tr>
						<tr>
							<td align="left">
								创建人员：
							</td>
							<td align="left">
								<span id="createrNameA"></span>
							</td>
						</tr>
						<tr>
							<td align="left">
								创建时间：
							</td>
							<td align="left">
								<span id="createTimeA"></span>
							</td>
						</tr>
						<tr>
							<td align="left">
								所属文件柜：
							</td>
							<td align="left">
								<span id="directoryA"></span>
							</td>
						</tr>


						<tr>

							<td colspan="2" align="center">
								<input type="button" class="input-button" value="提交" id="sumbitFolderA" onclick="submitFolderA();"/>
								<input type="button" class="input-button" value="关闭" id="cancelFolderA" />
							</td>
						</tr>
					</table>
				</center>
			</div>
		</form>


		<form id="fileAttribute" name="fileAttribute">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelFileAttr">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					文件属性
					<div class="close">
					</div>
				</div>
				<center>
					<table cellspacing="25">
						<tr>
							<td align="left" style="white-space: nowrap;">
								文件名称：
							</td>
							<td align="left">
								<span id="fileUploadNameA"></span>
							</td>
						</tr>
						<tr>
							<td align="left">
								文件大小：
							</td>
							<td align="left">
								<span id="fileUploadSizeA"></span>
							</td>
						</tr>

						<tr>
							<td align="left">
								上传人员：
							</td>
							<td align="left">
								<span id="uploadPersonNameA"></span>
							</td>
						</tr>
						<tr>
							<td align="left">
								上传时间：
							</td>
							<td align="left">
								<span id="uploadTimeA"></span>
							</td>
						</tr>
						<tr>
							<td align="left">
								所属目录：
							</td>
							<td align="left">
								<span id="saveDirectoryA"></span>
							</td>
						</tr>


						<tr>

							<td colspan="2" align="center">
								<input type="button" value="关闭" class="input-button" id="cancelFileA" />
							</td>
						</tr>
					</table>
				</center>
			</div>
		</form>

	</body>
</html>
