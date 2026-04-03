<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<title><c:if test="${ptrack.ptrackekey!=null}">修改生产跟踪单</c:if>
	<c:if test="${ptrack.ptrackekey==null}">添加生产跟踪单</c:if>
</title>

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
			<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/production.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/admin_main111.css" />

<script src="<%=basePath%>js/ea/production/cprocedure/ptrack_add.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/popLayer/css/popstyle.css" />
<script src="<%=basePath%>js/popLayer/js/popLayer.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css"
	href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>


<script type="text/javascript">

var basePath="<%=basePath%>";
var notoken = 0;
var ptrackeId = "${ptrack.ptrackeId}";
var fiveClear="${fiveClear}";
</script>

</head>
<body>
	<form id="ptrackForm" name="ptrackForm" method="post">
		<input type="submit" name="submit" style="display:none;">
		<div class="main">
			<div class="top">生产跟踪</div>
			<div class="body">

				<div class="showinfo show">
					<table id="productbl">
					
					
						<tr>
							<td align="right" width="40%">生产批次号：</td>
							<td><input type="text" class="inputtext" readonly
								name="ptrack.lot"  id="lot" 
								value='<s:if test="ptrack.ptrackeId!=null">${ptrack.lot}</s:if><s:else></s:else>' />
							</td>
						</tr>

						<tr>
							<td align="right">产品编号：</td>
							<td><input type="text" class="inputtext productCode"
								name="ptrack.productNumber" value="${ptrack.productNumber}"
								readonly /> 
								<input type="hidden" class="ppID" name="ptrack.id" value="${ptrack.id}" /> 
								<input type="hidden" name="ptrack.ptrackeId" value="${ptrack.ptrackeId}" />
								<input type="hidden" name="ptrack.ptrackekey"
								value="${ptrack.ptrackekey}" /></td>
						</tr>

						<tr>
							<td align="right">产品名称：</td>
							<td><input type="text" class="inputtext goodsName input3"
								style="width:40%;" name="ptrack.productName"
								value="${ptrack.productName}" readonly /> <input type="button"
								onclick="pop('products')" id="selectpr" value="选择" class="btn01" />
							</td>
						</tr>
						<tr>
							<td align="right">生产部门：</td>
							<td><input readonly="readonly"  type="text" class="inputtext"
								name="ptrack.productionDepartment" 
								value='<s:if test="ptrack.ptrackeId!=null">${ptrack.productionDepartment}</s:if><s:else><%=session.getAttribute("organizationName")%></s:else>' />
							
							<input type="hidden" 
								name="ptrack.departmentID"  value="${ptrack.departmentID}"/>
							
							</td>
						</tr>

						<tr>
							<td align="right">项目负责人：</td>
							<td><input type="text" class="inputtext input3" style="width:40%;"
								name="ptrack.projectLeader" value="${ptrack.projectLeader}" readonly="readonly"
								id="dutorName" /> <input type="hidden" name="ptrack.projectLeaderID"
								value="${ptrack.projectLeaderID}" id="dutorID" /> <input type="button"
								onclick="pop('members')" id="selectme" value="选择" class="btn01" />

							</td>
						</tr>



						<tr>
							<td align="right">日期：</td>
							<td><input type="text" class="inputtext"  onfocus="date()"
								name="ptrack.trackTime" 
								value='<s:if test="ptrack.ptrackeId!=null">${fn:substring(ptrack.trackTime,0,10)}</s:if><s:else><fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd" /></s:else>' />
							</td>
						</tr>

						<tr>
							<td align="right">生产量：</td>
							<td><input type="text" class="inputtext jisuan put3"
								name="ptrack.throughput" value="${ptrack.throughput}"  id="throughput"/>
							</td>
						</tr>


						<tr>
							<td align="right">跟踪员：</td>
							<td><input type="text" class="inputtext" readonly
								name="ptrack.trackman" 
								
								value='<s:if test="ptrack.ptrackeId!=null">${ptrack.trackman}</s:if><s:else>${account.staffName}</s:else>' />
							<input type="hidden" value="${ptrack.trackmanId}"
								name="ptrack.trackmanId" />
							
							</td>
						</tr>


						<tr>
							<td align="right">备注：</td>
							<td><textarea type="text" class="inputtext ckTextLength" style="height: 50px" maxLength="250"
								name="ptrack.remark" value="${ptrack.remark}" ></textarea>
							</td>
						</tr>
					</table>

				</div>
			</div>

		</div>
		<div class="bottom">
			<input type="button" class="btn submitResult" value="提交保存" />
		</div>
		</div>
	</form>


	<!-- 行业 -->

	<div id="industry" class="popMain">
		<div class="choose-box">
			<div class="choosetitle">
				<span>选择行业</span>
			</div>
			<div class="chooseborder">
				<div id="industryTree" style=" border: 0px solid #000000;"></div>


			</div>
			<div class="choose-box-bottom">
				<input type="botton" onclick="hide('industry')" value="关闭" />
			</div>
		</div>
	</div>


	<!-- 项目产品分类-->

	<div id="project" class="popMain">
		<div class="choose-box">
			<div class="choosetitle">
				<span>选择项目产品分类</span>
			</div>
			<div class="chooseborder">
				<div id="projectTree" style=" border: 0px solid #000000;"></div>


			</div>
			<div class="choose-box-bottom">
				<input type="botton" onclick="hide('project')" value="关闭" />
			</div>
		</div>
	</div>


	<!-- 选择产品 -->

	<div id="products" class="popMain">
		<div class="choose-box">
			<div class="choosetitle">
				<span>选择项目产品</span>
			</div>
			<div class="chooseborder">
				<table width="99%" height="33" id="searchpro" border="0"
					align="center" cellpadding="0" cellspacing="0"
					style="margin-top: 5px; background: #FFFFFF;">
					<tr>
						<td width="100" align="right">产品编码或名称：</td>
						<td width="110"><input name="parameter" class="input"
							id="parameter" size="10" style="margin-left: 2px;" /></td>
						<td height="33"><input type="button" class="btn01"
							id="searchProduct" name="button7" value="查询" /> <input
							type="button" class="btn01" id="selectProduct" name="button5"
							value="确定" /></td>
						<td width="80"><a id="wpsyp" title="0"
							style="cursor:pointer;">上一页</a></td>
						<td width="80"><a id="wpxyp" title="0"
							style="cursor:pointer;">下一页</a></td>
						<td width="100"><a id="wpzy">共&nbsp;&nbsp; <span
								style="color: red" id="wpzycountp"></span>&nbsp;&nbsp;页 </a></td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
					<tr>
						<td width="83%" valign="top" align="left">
							<div id="body_03"
								style="margin-top: 2px; height:340px;width:100%;border:1px solid #ccc; overflow: auto;">
							</div></td>
					</tr>
				</table>

			</div>
			<div class="choose-box-bottom">
				<input type="botton" onclick="hide('products')" value="关闭" />
			</div>
		</div>
	</div>



	<!-- 选择人员 -->
	<div id="members" class="popMain">
		<div class="choose-box">
			<div class="choosetitle">
				<span>选择人员</span>
			</div>
			<div class="chooseborder">
				<div id="goodsTree" style=" border: 0px solid #000000;"></div>
				<table width="99%" height="33" id="searchuser" border="0"
					align="center" cellpadding="0" cellspacing="0"
					style="margin-top: 5px; background: #FFFFFF;">
					<tr>
						<td width="100" align="right">姓名：</td>
						<td width="110"><input name="contactUserID" class="input"
							id="contactUserID" size="10" style="margin-left: 2px;" /></td>
						<td height="33"><input type="button" class="btn01"
							id="searchuu" name="button7" value="查询" /> <input type="button"
							class="btn01" id="qduser" name="button5" value="确定" /> <input
							type="button" class="btn01 xzgr" name="button" value="新增" /> <input
							type="hidden" name="parms" id="grparms" /></td>
						<td width="80"><a id="grsy" title="0" style="cursor:pointer;">上一页</a>
						</td>
						<td width="80"><a id="grxy" title="0" style="cursor:pointer;">下一页</a>
						</td>
						<td width="100"><a id="grzy">共&nbsp;&nbsp; <span
								style="color: red" id="grzycount"></span>&nbsp;&nbsp;页 </a></td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
					<tr>
						<td width="16%">
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
									<td>
										<div id="grTree"
											style="overflow: scroll; z-index: 99; width:180px;height: 340px;border:1px solid #ccc;"></div>
									</td>
								</tr>
							</table></td>
						<td width="83%" valign="top" align="left">
							<div id="body_02cu"
								style="margin-top: 2px; height:340px;width:100%;border:1px solid #ccc;border-left:none; overflow: auto;">
							</div></td>
					</tr>
				</table>

			</div>
			<div class="choose-box-bottom">
				<input type="botton" onclick="hide('members')" value="关闭" />
			</div>
		</div>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>