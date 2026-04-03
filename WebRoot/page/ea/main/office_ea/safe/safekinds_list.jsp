<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>安全检查类别</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <!-- 办公室办公--后勤管理-- 安全检查类别表-->
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
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/office_ea/safe/safekinds_list.js"  type="text/javascript"></script>
<script type="text/javascript">
var token = 0 ;
var bPath = '<%=basePath%>';
var id = '';
var parentID='${parentID}';
var ppageNumber =${pageNumber};
var  search='${search}';  
var cid='${parameter}';
var status = '';
</script>
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
							安全检查类别序号
						</th>
						<th width="150" align="center">
							安全检查类别名称
						</th>
						<th width="200" align="center">
							描述
						</th>
						<th width="200" align="center">
							检查指标
						</th>
						<th width="100" align="center">
							检查指标附件
						</th>
						<th width="60" align="center">
							状态
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${id}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${id}" />
							</td>
							<td><span>
							<%=number%>
							</span>
							</td>
							<td>
								<span id="name">${name}</span>
							</td>
							<td>
								<span id="descRiption">${descRiption}</span>
							</td>
							<td>
								<span id="guideline">${guideline}</span>
								<span id="key"   style="display:none" >${key}</span>
								<span id="id" style="display:none">${id}</span>
							</td>
                            <td>
								<span id="attachment" style="display:none">${attachment}</span>
              					<s:if test="attachment == null||attachment==''">无</s:if>
                             	<s:else>
                                	<span id="photo" onclick="lookImage('${attachment}');"><a href="#">查看</a></span>
                            	</s:else>
							</td>
														<td>
									<span id="status" style="display:none">${status}</span>
								    <s:if test='status == "00"'>可停用</s:if>
								    <s:if test='status == "01"'>不可停用</s:if>
								    <s:if test='status == "98"'>已停用</s:if>
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
					value="ea/oasafeKind/ea_getListOASafeKind.jspa?pageNumber=${pageNumber}&search=${search}&parentID=${parentID }">
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
							安全检查类别
							<div class="close"></div>
						</div>
					</div>
					<table width="885"  border="0" id="stafftable" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 50px;">
						<tr>
							<td>
								<table width="885"  border="0" id="stafftable2" align="center"
									cellpadding="0" cellspacing="0"
									style="margin-top: 5px; margin-bottom: 50px;">
									<tr>
										<td align="center">
											安全检查类别名称：
										</td>
										<td>
											<input name="oasafeKind.name" type="text" maxlength="25"
												id="name" class="name put3 ckTypeName chinese" size="30" />
										</td>
										<td height="35" align="center">
											描述：
										</td>
										<td>
											<input name="oasafeKind.descRiption" id="descRiption" maxlength="50"
												type="text" class="input ckTextLength" size="30" />
										</td>
			        					<td rowspan="3" align="left"><img id="pic" width="99" height="135"  /></td>
										
									</tr>
									<tr>
										
										<td align="center">
											检查指标：
										</td>
										<td>
											<input name="oasafeKind.guideline" id="guideline" maxlength="50"
												type="text" class="input ckTextLength" size="30" />
										</td>
										<td align="center">
											上传检查指标附件：
										</td>
										
			         	 <td width="110" align="center">
			              <input name="photo" type="file" class="input"  size="15"  contentEditable="false"/>
                          <input name="oasafeKind.attachment" type="hidden" id="attachment"/></td>
									</tr>
									<tr>
										<td height="35" colspan="5" align="center">
											<input name="oasafeKind.key" id="key"
												type="hidden" class="input" size="20" />
											<input name="oasafeKind.id" id="id" type="hidden"
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
        <div class="jqmWindow" style="width: 350px;right: 35%; top:20%" id="jqModelSearch2">
            <form name="SearchForm" id="SearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                          安全检查类型名称：
                        </td>
                        <td>         
                         <input  name="oasafeKind.name"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                           检查指标：
                        </td>
                        <td>         
                           <input name="oasafeKind.guideline" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value="查询" /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>