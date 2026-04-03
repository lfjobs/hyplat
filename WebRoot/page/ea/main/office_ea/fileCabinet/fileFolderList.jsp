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
		<title>文件夹列表</title>
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
			src="<%=basePath%>js/ea/office_ea/fileCabinet/fileFolderList.js"></script>
		<script type="text/javascript">
   var  basePath='<%=basePath%>';           
   var  pNumber ='${pageNumber}';  
   var  search='${search}';
   var token = 2;
   var fileCabinetId = '<%=request.getAttribute("fileCabinetId")%>';
   var fileCabinetKey = '<%=request.getAttribute("fileCabinetKey")%>';
   var fileCabinetName='<%=request.getAttribute("fileCabinetName")%>';
   </script>
		<style type="text/css">
a {
	text-decoration: none;
}
</style>
<style type="text/css">

html,body {height:100%; margin:0px; font-size:12px;}
.mydiv {
background-color: #FFCC66;
border: 1px solid #f00;
text-align: center;
line-height: 40px;
font-size: 12px;
font-weight: bold;
z-index:999;
width: 300px;
height: 120px;
left:50%;
top:50%;
margin-left:-150px!important;/*FF IE7 该值为本身宽的一半 */
margin-top:-60px!important;/*FF IE7 该值为本身高的一半*/
margin-top:0px;
position:fixed!important;/* FF IE7*/
position:absolute;/*IE6*/
_top:       expression(eval(document.compatMode &&
            document.compatMode=='CSS1Compat') ?
            documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/
            document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);/*IE5 IE5.5*/

}

.bg,.popIframe {
background-color: #666; display:none;
width: 100%;
height: 100%;
left:0;
top:0;/*FF IE7*/
filter:alpha(opacity=50);/*IE*/
opacity:0.5;/*FF*/
z-index:1;
position:fixed!important;/*FF IE7*/
position:absolute;/*IE6*/
_top:       expression(eval(document.compatMode &&
            document.compatMode=='CSS1Compat') ?
            documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/
            document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);
}
.popIframe {
filter:alpha(opacity=0);/*IE*/
opacity:0;/*FF*/
}

</style>


	</head>
	<body oncontextmenu='return false'> 
		<div id="cabinet">
		<div id="popDiv" class="mydiv" style="display:none;">第一次加载可能会很慢，请稍后。。。<br/>
        <div id="d"></div>
        <a href="javascript:closeDiv()">关闭窗口</a></div>
        <div id="bg" class="bg" style="display:none;"></div>
        <iframe id='popIframe' class='popIframe' frameborder='0' ></iframe>
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
								<c:when test='${list[2]=="F"}'>

									<td class="td_bg01">
										<img src="<%=basePath%>images/ea/office/fileCabinet/009.gif"
											width="20" height="30">
										<input type="radio" name="radioGroup"
											class="JQuerypersonvalue" value="${list[0]}" />				
									</td>
									<td class="td_bg01">
										<span id="fileFolderName">${list[1]}</span>
									</td>
									<td class="td_bg01">
										<a href="javascript:enterFolder('${list[0]}');">进入文件夹</a>&nbsp;|
										&nbsp;
										<a href="javascript:viewAttribute('${list[0]}');">属性</a>&nbsp;
									</td>

								</c:when>

								<c:otherwise>
									<td class="td_bg01">
										<img src="<%=basePath%>${list[2]}"
											width="20" height="20">
										<input type="radio" name="radioGroup"
											class="JQuerypersonvalue" value="${list[0]}" />
									</td>
									<td class="td_bg01">
										<span id="fileUploadName">${list[1]}</span>
									</td>
									<td class="td_bg01">
										&nbsp;
										<a href="javascript:viewAttribute('${list[0]}');">属性</a>&nbsp;|&nbsp;
										<a href="javascript:loadFile('${list[3]}')">下载</a>&nbsp;
										|&nbsp;
										<!--<a href="javascript:updateFile('${list[0]}')">更新</a>&nbsp;|&nbsp;  -->
										<span id='yulan_${list[3]}'><a
											href="javascript:lookImage('${list[3]}')">预览</a>
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

		<form id="newFolder" name="newFolder" method="post">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelFolder">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					新建文件夹
					<div class="close">
					</div>
				</div>
				<center>
					<table cellspacing="25">
						<tr>
							<td>
								文件夹名称
							</td>
							<td>
								<input type="text" id="fileFolderName12"
									name="fileFolder.fileFolderName" />
							</td>
						</tr>
						<tr>
							<td align="right">
								描述信息：
							</td>
							<td>
								<textarea cols="20" rows="5" id="descriptor"
									name="fileFolder.descriptor">
								
								</textarea>
							</td>
						</tr>


						<tr>

							<td colspan="2" align="center">
								<input type="button" value="提交" class="input-button" id="sumbitFolder" />
								<input type="button" value="关闭" class="input-button" id="cancelNewFolder" />
							</td>
						</tr>
					</table>
				</center>
			</div>
		</form>
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
								<span id="directoryA"><%=request.getAttribute("fileCabinetName")%></span>
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
		<!-- 第一次上传文件 -->
		<form id="uploadFileForm" name="uploadFileForm" method="post"
			enctype="multipart/form-data">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelFileUpload">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					上传文件
					<div class="close">
					</div>
				</div>
				<center>
					<table cellspacing="25">
						<tr>
							<td>
								保存位置
							</td>
							<td>
								<select style="width: 180px;" id="directory"
									name="fileUpload.saveDirectory">

								</select>
							</td>
						</tr>
						<tr>
							<td>
								<img src="<%=basePath%>images/ea/office/document/attach1.png"
									width="20" height="20" />
								<a href="javascript:uploadFiles('upload')" id="uploadFiles">添加附件</a>
							</td>
							<td>
								单次上传文件大小不能超过10MB。
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div style="display: none" id="hideAttach">
								</div>
							
							</td>
						</tr>
						<tr>
                         
							<td colspan="2" align="center">
							    <input type="hidden" id="uploadInfoCaId" name="fileUploadInfo.fileCabinetId" />
							    <input type="hidden" id="uploadInfofileSize" name="fileUploadInfo.fileSize" />
							    <input type="hidden" id="uploadInfoMode" name="fileUploadInfo.uploadMode" />
								<input type="button" value="上传文件" class="input-button" id="submitUpload" />
								<input type="button" value="关闭" class="input-button" id="cancleUpload" />
							</td>
						</tr>
					</table>
				</center>
			</div>
		</form>

		<form id="fileMove" name="fileMove" method="post">
			<div class="jqmWindow"
				style="heigth: 200px; width: 300px; right: 25%; top: 10%"
				id="jqModelFileMove">
				<input type="submit" name="submit" style="display: none" />
				<input type="hidden" id="moveHide" value=""
					name="fileUpload.fileUploadId" />
				<div class="drag">
					文件移动
					<div class="close">
					</div>
				</div>
				<center>
					<table cellspacing="10">
						<tr>
							<td align="left">
								移动到：
							</td>
							<td align="left" style="white-space: nowrap;">
								<select style="width: 150px;" id="directoryMove"
									name="fileUpload.saveDirectory">




								</select>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<input type="button" value="确定" class="input-button" id="submitMove"
									onclick="fileMoveAndCopy('moveFile')" />
								<input type="button" value="关闭" class="input-button" id="cancelMove" />
							</td>
						</tr>
					</table>
				</center>
			</div>
		</form>

		<form id="fileCopy" name="fileCopy" method="post">
			<div class="jqmWindow"
				style="heigth: 200px; width: 300px; right: 25%; top: 10%"
				id="jqModelFileCopy">
				<input type="submit" name="submit" style="display: none" />
				<input type="hidden" id="copyHide" value=""
					name="fileUpload.fileUploadId" />
				<div class="drag">
					文件复制
					<div class="close">
					</div>
				</div>
				<center>
					<table cellspacing="10">
						<tr>
							<td align="left">
								复制到：
							</td>
							<td align="left" style="white-space: nowrap;">
								<select style="width: 150px;" id="directoryCopy"
									name="fileUpload.saveDirectory">




								</select>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<input type="button" value="确定" class="input-button" id="submitCopy"
									onclick="fileMoveAndCopy('copyFile')" />
								<input type="button" value="关闭" class="input-button" id="cancelCopy" />
							</td>
						</tr>
					</table>
				</center>
			</div>
		</form>

   <iframe name = "hidden" width="100%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" border="0" framespacing="0" noresize="noResize"></iframe>
   
	</body>
</html>
