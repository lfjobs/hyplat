<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物品管理</title>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>		
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/ccompany/goodsmanage/goodsmanage_list.js" type="text/javascript"></script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="100" align="center">
							品名编号
						</th>
						<th width="100" align="center">
							品名名称
						</th>
						<th width="100" align="center">
							物品条码
						</th>
						<th width="100" align="center">
							品牌
						</th>
						<th width="100" align="center">
							类型
						</th>
						<th width="150" align="center">
							单位换算
						</th>
						<th width="80" align="center">
							默认规格
						</th>
						<th width="100" align="center">
							品牌规格
						</th>
						<th width="100" align="center">
							型号
						</th>
						<th width="100" align="center">
							厂家
						</th>
						<th width="80" align="center">
							浏览
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${goodsID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${goodsID}" />
							</td>
							<td>
								<span id="goodsCoding">${goodsCoding}</span>
							</td>
							<td>
								<span id="goodsName">${goodsName}</span>
							</td>
							<td>
								<span id="defaultStorage">${defaultStorage}</span>
							</td>
							<td>
								<span id="mnemonicCode">${mnemonicCode}</span>
							</td>
							<td>
								<span id="typeID">${typeID}</span>
							</td>
							<td>
								<span id="goodsvariable"> ${goodsvariable} </span>
							</td>
							<td>
								<span id="acquiesceStandard">${acquiesceStandard}</span>
							</td>
							<td>
								<span id="standard">${standard}</span>
							</td>
							<td>
								<span id="model">${model}</span>
							</td>
							<td>
								<span id="manufacturers">${manufacturers}</span>
								<span id="num" style="display: none">${num}</span>
								<span id="variableID" style="display: none">${variableID}</span>
								<span id="num1" style="display: none">${num1}</span>
								<span id="variable1ID" style="display: none">${variable1ID}</span>
								<span id="num2" style="display: none">${num2}</span>
								<span id="variable2ID" style="display: none">${variable2ID}</span>
								<span id="num3" style="display: none">${num3}</span>
								<span id="variable3ID" style="display: none">${variable3ID}</span>
								<span id="num4" style="display: none">${num4}</span>
								<span id="variable4ID" style="display: none">${variable4ID}</span>
								<span id="goodsID" style="display: none">${goodsID}</span>
								<span id="goodsKey" style="display: none">${goodsKey}</span>
								<span id="photoPath" style="display: none">${photoPath}</span>
							</td>
							<td class="td_bg01">
								<span><s:if test="photoPath==null||photoPath==''">无</s:if>
								</span>
								<s:else>
									<span id="photo" onclick="lookImage('${photoPath}');"><a
										href="#">查看</a> </span>
								</s:else>


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
					value="ea/cgoodsmanage/ea_getListGoodsManage.jspa?pageNumber=${pageNumber}">
				</c:param>
			</c:import>
		</div>
		<form name="cstaffForm" id="cstaffForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="contentbannb jqmWindow jqmWindowcss1" style="top: 10%"
				id="jqModel">
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							物品管理
							<div class="close"></div>
						</div>
					</div>
					<table width="885" border="0" id="stafftable" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td>
								<table width="885" border="0" id="stafftable2" align="center"
									cellpadding="0" cellspacing="0"
									style="margin-top: 5px; margin-bottom: 5px;">
									<tr>
										<td height="35" align="right">
											品名编号：
										</td>
										<td>
											<input name="goodsManage.goodsCoding" type="text"
												id="goodsCoding" class="goodsCoding put3" size="15" />
										</td>
										<td align="right">
											品名名称：
										</td>
										<td>
											<input name="goodsManage.goodsName" type="text"
												id="goodsName" class="goodsName put3 ckTextLength" size="15" />
										</td>
										<td rowspan="5">
											<div align="center">
												<img name="showphoto" width="99" height="135" id="showphoto" />
												<br />
												<input name="goodsManage.photoPath" id="photoPath"
													class="input01" type="hidden" />
												<input name="goodsManage.filePhoto" type="file"
													contentEditable="false" class="input01" id="filePhoto"
													size="15" />
											</div>
										</td>
									</tr>
									<tr>
										<td height="35" align="right">
											品牌：
										</td>
										<td>
											<input name="goodsManage.mnemonicCode" id="mnemonicCode"
												type="text" class="input ckTextLength" size="20" />
										</td>
										<td align="right">
											类型：
										</td>
										<td>
											<s:select list="%{#request.typelist}" id="typeID"
												listKey="codeValue" listValue="codeValue"
												name="goodsManage.typeID" theme="simple"></s:select>
											<a href="#"
												onclick="toCCode('scode20101014v5zed7cukk0000000002','#typeID','#cstaffForm')">新添</a>
										</td>
									</tr>
									<tr>
										<td height="35" align="right">
											物品条码：
										</td>
										<td>
											<input name="goodsManage.defaultStorage" type="text"
												class="input" id="defaultStorage" size="15" />
										</td>
										<td align="right">
											默认规格：
										</td>
										<td>
											<input name="goodsManage.acquiesceStandard"
												id="acquiesceStandard" type="text"
												class="input ckTextLength" size="15" />
										</td>
									</tr>
									<tr>
										<td height="35" align="right">
											品牌规格：
										</td>
										<td>
											<s:select list="%{#request.standardslist}" id="standard"
												listKey="codeValue" listValue="codeValue"
												name="goodsManage.standard" theme="simple"></s:select>
											<a href="#"
												onclick="toCCode('scode20101216zgkfwy4y8p0000000002','#standard','#cstaffForm')">新添</a>
										</td>
										<td align="right">
											型号：
										</td>
										<td>
											<input name="goodsManage.model" type="text" class="input"
												id="model" size="15" />
										</td>
									</tr>
									<tr>
										<td align="right">
											厂家：
										</td>
										<td>
											<input name="goodsManage.manufacturers" type="text"
												class="input" id="manufacturers" size="15" />
										</td>
									</tr>
									<tr>
										<td height="35" align="right">
											单位换算：
										</td>
										<td colspan="4" class="variables">
											<span id="num"> <input name="goodsManage.num"
													type="text" number="0" class="input" id="num"
													style="width: 30px;" /> <s:select
													list="%{#request.variablelist}" id="variableID"
													headerKey="" number="0" headerValue="请选择"
													style="width: 70px;" listKey="codeValue"
													listValue="codeValue" name="goodsManage.variableID"
													theme="simple"></s:select> </span>
											<span id="num1" style="display: none">= <input
													name="goodsManage.num1" type="text" number="1"
													class="input" id="num1" style="width: 30px;" /> <s:select
													list="%{#request.variablelist}" id="variable1ID"
													headerKey="" number="1" headerValue="请选择"
													style="width: 70px;" listKey="codeValue"
													listValue="codeValue" name="goodsManage.variable1ID"
													theme="simple"></s:select> </span>
											<span id="num2" style="display: none">= <input
													name="goodsManage.num2" type="text" number="2"
													class="input" id="num2" style="width: 30px;" /> <s:select
													list="%{#request.variablelist}" id="variable2ID"
													headerKey="" number="2" headerValue="请选择"
													style="width: 70px;" listKey="codeValue"
													listValue="codeValue" name="goodsManage.variable2ID"
													theme="simple"></s:select> </span>
											<span id="num3" style="display: none">= <input
													name="goodsManage.num3" type="text" number="3"
													class="input" id="num3" style="width: 30px;" /> <s:select
													list="%{#request.variablelist}" id="variable3ID"
													headerKey="" number="3" headerValue="请选择"
													style="width: 70px;" listKey="codeValue"
													listValue="codeValue" name="goodsManage.variable3ID"
													theme="simple"></s:select> </span>
											<span id="num4" style="display: none">= <input
													name="goodsManage.num4" type="text" number="4"
													class="input" id="num4" style="width: 30px;" /> <s:select
													list="%{#request.variablelist}" id="variable4ID"
													headerKey="" number="4" headerValue="请选择"
													style="width: 70px;" listKey="codeValue"
													listValue="codeValue" name="goodsManage.variable4ID"
													theme="simple"></s:select> </span>
											<a href="#"
												onclick="toCCode('scode20101014v5zed7cukk0000000003','#variableID','#cstaffForm')">新添</a>
										</td>
									</tr>
									<tr>
										<td height="35" colspan="5" align="center">
											<input name="goodsManage.goodsKey" id="goodsKey"
												type="hidden" class="input" size="20" />
											<input name="goodsManage.goodsvariable" id="goodsvariable"
												type="hidden" class="input" size="20" />
											<input name="goodsManage.goodsID" id="goodsID" type="hidden"
												class="input" size="20" />
											<input type="button" class="input-button JQuerySubmit"
												style="cursor: pointer; width: 80px;" value="提交" />
											<input type="button" class="input-button JQueryreturn"
												style="cursor: pointer; width: 80px;" value="取消" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 350px; right: 35%; top: 20%"
			id="jqModelSearch2">
			<form name="SearchForm" id="SearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTable">
					<tr>
						<td>
							查询条件
						</td>
					</tr>
					<tr>
						<td>
							物品编号：
						</td>
						<td>
							<input name="goodsManage.goodsCoding" />
						</td>
					</tr>
					<tr>
						<td>
							物品名称：
						</td>
						<td>
							<input name="goodsManage.goodsName" />
						</td>
					</tr>
					<tr>
						<td>
							物品类别：
						</td>
						<td>
							<s:select list="%{#request.typelist}" id="typeID"
								listKey="codeValue" listValue="codeValue"
								name="goodsManage.typeID" theme="simple" headerKey=""
								headerValue="全部"></s:select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>

		<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
			id="newccode">
			<div class="drag">
				添加
			</div>
			<table>
				<tr>
					<td>
						代码名字：
					</td>
					<td>
						<input id="ccodevalue" />
						<input id="codePID" type="hidden" />
						<input id="selectID" type="hidden" />
						<input id="formID" type="hidden" />
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" onclick="saveCCode()"
					value="确定" />
				<input type="button" class="input-button JQueryreturn1" value="取消" />
			</div>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
				<script type="text/javascript">
var token = 0 ;
var basePath = "<%=basePath%>";
var personvalue = "";
var personurl = "";
var staffName;
var staffsize ;//后台验证身份证时应该查到的人数
var goodsID = '';
var ppageNumber = '${pageNumber}';

</script>
	</body>
</html>