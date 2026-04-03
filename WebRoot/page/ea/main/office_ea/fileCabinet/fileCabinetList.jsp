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
		<title>文件柜列表</title>
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
			src="<%=basePath%>js/ea/office_ea/fileCabinet/fileCabinetList.js"></script>
		<script type="text/javascript">
   var  basePath='<%=basePath%>';           
   var  pNumber =${pageNumber};  
   var  search='${search}';
   var  token=2;
   var  fileCabinetId = '';

   </script>




	</head>
	<body oncontextmenu='return false'>

		

		<div id="cabinet">
			<table class="cabinet0">
				<thead>
					<tr>
						<th width="70" align="center">
							选择
						</th>
						<th width="70" align="center">
							文件柜名称
						</th>
						<th width="70" align="center">
							占用空间
						</th>
						<th width="160" align="center">
							包含内容
						</th>
						<th width="70" align="center">
							创建人员
						</th>

						<th width="200" align="center">
							所属公司
						</th>
						<th width="160" align="center">
							创建时间
						</th>
						<th width="160" align="center">
							描述信息
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${fileCabinetId}">
							<td class="td_bg01">
								<img
									src="<%=basePath%>images/ea/office/fileCabinet/file_folder.png"
									width="20" height="30">
								<input type="radio" name="radioGroup" class="JQuerypersonvalue"
									value="${fileCabinetId}" />
							</td>
							<td class="td_bg01">
								<span id="fileCabinetName">${fileCabinetName}</span>
							</td>
							<td class="td_bg01">
								<span id="usedSpace">${usedSpace}</span>
							</td>
							<td class="td_bg01">
								<span id="fileContent">包含${fileFolderNumber}个文件夹、${fileNumber}个文件</span>
							</td>
							<td class="td_bg01">
								<span id="createrName">${createrName}</span>
							</td>
							<td class="td_bg01">
								<span id="companyName">${companyName}</span>
							</td>
							<td class="td_bg01">
								<span id="createTime">${createTime}</span>
							</td>
							<td class="td_bg01">
								<span id="descriptor">${descriptor}</span>
							</td>


						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/filecabinet/ea_getListForFileCabinet.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		<form id="newCabinet" name="newCabinet">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelCabinet">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					新建文件柜
					<div class="close">
					</div>
				</div>
				<center>
					<table cellspacing="25" id="filecabinettable">
						<tr>
							<td>
								文件柜名称：
							</td>
							<td>
								<input type="text" id="fileCabinetName2"
									name="fileCabinet.fileCabinetName" />
								
								
							</td>
						</tr>
						<tr>
							<td align="right">
								描述信息：
							</td>
							<td>
								<textarea cols="20" rows="5" id="descriptor2"
									name="fileCabinet.descriptor">
								
								</textarea>
							</td>
						</tr>


						<tr>

							<td colspan="2" align="center">
								<input type="hidden" name="update" value="false" />
								<input type="button" value="提交" class="input-button" id="sumbitCabinet" />
								<input type="button" value="关闭" class="input-button" id="cancelCabinet" />
							</td>
						</tr>
					</table>
				</center>
			</div>
		</form>

		<form id="newCabinetU" name="newCabinetU">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelCabinetU">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					修改文件柜
					<div class="close">
					</div>
				</div>
				<center>
					<table cellspacing="25" id="filecabinettableU">
						<tr>
							<td>
								文件柜名称：
							</td>
							<td>
								<input type="text" id="fileCabinetName3"
									name="fileCabinet.fileCabinetName" />
								<input type="hidden" name="fileCabinet.fileCabinetId" id="hidcabinetid"
									value="" />
							</td>
						</tr>
						<tr>
							<td align="right">
								描述信息：
							</td>
							<td>
								<textarea cols="20" rows="5" id="descriptor3"
									name="fileCabinet.descriptor">
								
								</textarea>
							</td>
						</tr>


						<tr>

							<td colspan="2" align="center">
								<input type="button" value="提交" class="input-button" id="sumbitCabinetU" />
								<input type="button" value="关闭" class="input-button" id="cancelCabinetU" />
							</td>
						</tr>
					</table>
				</center>
			</div>
		</form>
		
		
		
		<!-- 查询文件柜 -->
			<form id="searchCabinet" name="searchCabinet">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelCabinetS">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询文件柜
					<div class="close">
					</div>
				</div>
				<center>
					<table cellspacing="25" id="filecabinettableU2">
						<tr>
							<td>
								文件柜名称：
							</td>
							<td>
								<input type="text" id="fileCabinetName3"
									name="fileCabinet.fileCabinetName" />
							</td>
						</tr>

						<tr>

							<td colspan="2" align="center">
						        <input name="search" type="hidden" value="search" />
								<input type="button" value="查询" class="input-button" id="tosearch" />
								<input type="button" value="关闭" class="input-button" id="cancelCabinetS" />
							</td>
						</tr>
					</table>
				</center>
			</div>
		</form>
		
		
	
	
	<!-- 站内搜索 -->
			<form id="searchGlobal" name="searchGlobal">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelGlobal">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					站内搜索
					<div class="close">
					</div>
				</div>
				<center>
					<table cellspacing="25" id="filecabinettableS">
					    <tr>
							<td>
								搜索类别：
							</td>
							<td>
								文件夹<input type="radio" id="searchType"
									name="fileSearchInfo.searchType" value="folder"/>
								文件<input type="radio" id="searchType"
									name="fileSearchInfo.searchType" value="file" checked/>
							</td>
						</tr>
						<tr>
							<td>
								名称：
							</td>
							<td>
								<input type="text" id="searchName"
									name="fileSearchInfo.name" />
							</td>
						</tr>
						<tr>

							<td colspan="2" align="center">
						        <input name="search" type="hidden" value="search" />
								<input type="button" value="查询" class="input-button" id="tosearchGlobal" />
								<input type="button" value="关闭" class="input-button" id="cancelGlobalS" />
							</td>
						</tr>
					</table>
				</center>
			</div>
		</form>	
		
     <iframe name = "hidden" width="100%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" border="0" framespacing="0" noresize="noResize"></iframe>
	</body>
</html>
