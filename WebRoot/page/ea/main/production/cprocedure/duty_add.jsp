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

<title>班值分配<c:if test="${onDuty.dutyid!=null}">修改</c:if><c:if test="${onDuty.dutyid==null}">添加</c:if></title>

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
			<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/production.css" />
<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/admin_main111.css" />

<script src="<%=basePath%>js/ea/production/cprocedure/duty_add.js"
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
var seqnum = 2;
var notoken = 0;
var dutyid="${onDuty.dutyid}";
var fiveClear="${fiveClear}";
</script>

</head>
<body>
	<form id="mainForm" name="mainForm" method="post">
		<input type="submit" name="submit" style="display:none;">
		<div class="main">
			<div class="top">班值分配</div>
			<div class="body">

				<div class="showinfo show">
					<table id="productbl">
						<tr>
							<td align="right">项目名称：</td>
							<td><input type="text" class="inputtext goodsName input3" style="width:40%;" 
								name="onDuty.productname" value="${onDuty.productname}" readonly  id="duyy"/> <input
								type="button" onclick="pop('products')"  id="selectpr"  value="选择" class="btn01" />
								 <input type="hidden" name="onDuty.productcode" class="productCode" value="${onDuty.productcode}"/>
								 <input type="hidden" class="ppID" name="onDuty.productid" value="${onDuty.productid}" />
								<input type="hidden" name="onDuty.dutyid" value="${onDuty.dutyid}" />
								<input type="hidden" name="onDuty.dutykey" value="${onDuty.dutykey}" />
								<input type="hidden" name="onDuty.fiveClear" value="${fiveClear}" />
						</tr>
						<tr>
							<td align="right">开始时间：</td>
							<td><input type="text" class="inputtext put3" name="onDuty.startdate"
								value="${fn:substring(onDuty.startdate,0,10)}" onfocus="date(this)"/></td>
						</tr>
						<tr>
							<td align="right">结束时间：</td>
							<td><input type="text" class="inputtext put3" name="onDuty.enddate"
								value="${fn:substring(onDuty.enddate,0,10)}"  onfocus="date(this)"/></td>
						</tr>
						<tr>
							<td align="right">职责：</td>
							<td><input type="text" class="inputtext put3"
								name="onDuty.duty" value="${onDuty.duty}" /></td>
						</tr>
						<tr>
							<td align="right">值班类型：</td>
							<td>
							<s:select id="selecttype" class="inputtext" list="#{'1班倒':'1班倒','2班倒':'2班倒','3班倒':'3班倒','4班倒':'4班倒'}"></s:select>
							<input type="hidden" name="onDuty.dutyType" id="dutyType" value="${onDuty.dutyType}"/>
						</tr>
						<tr>
							<td colspan="2" align="center">
									<div id="edita"
										style="background-color: #C4C4C4;width: 70%;height: 100%;margin-right: 100px;margin-top: 10px;margin-bottom: 10px;">
										<s:iterator value="dblist" status="b">
										<br/>
										<table id="close${b.index}">
											<tr>
												<td colspan="2" align="center"><input width="50px"
													title="${fn:substring(sdate,11,19)}" onfocus="dayhour(this)"
													name="dutyb[${b.index}].strdate" value="${fn:substring(sdate,11,19)}" />-<input
													width="50px" title="${fn:substring(edate,11,19)}" onfocus="dayhour(this)"
													name="dutyb[${b.index}].enddate" value="${fn:substring(edate,11,19)}" /></td>
											</tr>
												<tr>
													<td align="right">接班人：</td>
													<td><input type="text" class="inputtext staffname input3"
														title="staffname" name="dutyb[${b.index}].staffname"
														value="${staffname}"  readonly="readonly"/> <input type="hidden"
														title="staffid" class="staffid"
														name="dutyb[${b.index}].staffid" value="${staffid}"/><input
														type="hidden" name="dutyb[${b.index}].branchid" value="${branchid }" /><input type="hidden"
														name="dutyb[${b.index}].branchkey" value="${branchkey }" /><input type="hidden"
														name="dutyb[${b.index}].dutyid" value="${dutyid }" /><input type="hidden"
														name="dutyb[${b.index}].dutyType" value="${dutyType }" /><input type="button"
														onclick="pop('members'),ss(this)" id="${b.index}-5" value="选择"
														class="btn01" />
													</td>
												</tr>
											</table>
										</s:iterator>
										<br/>
									</div>
								
									<div id="adda"
										style="background-color: #C4C4C4;width: 70%;height: 100%;margin-right: 100px;margin-top: 10px;margin-bottom: 10px;"></div>
								</td>
						</tr>
						<tr>
							<td align="right">分配时间：</td>
							<td><input type="text" class="inputtext put3"
								name="onDuty.allotdate" readonly
								value='<s:if test="onDuty.dutyid!=null"></s:if>${fn:substring(onDuty.allotdate,0,10)}<s:else><fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd" /></s:else>' />
							</td>
						</tr>
						<tr>
							<td align="right">备注：</td>
							<td><textarea type="text" class="inputtext ckTextLength" style="height: 50px" name="onDuty.comments" maxlength="250"
								value="${onDuty.comments}"></textarea></td>
						</tr>
					</table>

				</div>
			</div>

		</div>
		<div class="bottom">
			<input type="button" class="btn save" value="提交保存" />
		</div>
		</div>
	</form>
	<div id="b" style="display: none;">
		<table>
			<tr>
				<td colspan="2" align="center"><input width="50px"
					title="strdate" onfocus="dayhour(this)" name="dutyb[0].strdate" />-<input
					width="50px" title="enddate" onfocus="dayhour(this)"
					name="dutyb[0].enddate" />
				</td>
			</tr>
			<tr>
				<td align="right">接班人：</td>
				<td><input class="inputtext staffname" title="staffname"
					name="dutyb[0].staffname" /> <input type="hidden" title="staffid"
					class="staffid" name="dutyb[0].staffid" /> <input type="button"
					onclick="pop('members'),ss(this)" id="0-5" value="选择" class="btn01" />
				</td>
			</tr>
		</table>
	</div>

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
		        <table width="99%" height="33" id="searchpro"     border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								产品编码或名称：
							</td>
							<td width="110">
								<input name="parameter" class="input" id="parameter" size="10"
									style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn01" id="searchProduct"
									name="button7" value="查询"/>
								<input type="button" class="btn01" id="selectProduct"
									name="button5" value="确定" />
			
								
								
							</td>
							<td width="80">
								<a id="wpsyp" title="0" style="cursor:pointer;">上一页</a>
							</td>
							<td width="80">
								<a id="wpxyp" title="0" style="cursor:pointer;">下一页</a>
							</td>
							<td width="100">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycountp"></span>&nbsp;&nbsp;页 </a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="83%" valign="top" align="left">
								<div id="body_03"
									style="margin-top: 2px; height:340px;width:100%;border:1px solid #ccc; overflow: auto;">
								</div>
							</td>
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
		        <table width="99%" height="33" id="searchuser"  border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								姓名：
							</td>
							<td width="110">
								<input name="contactUserID" class="input" id="contactUserID"
									size="10" style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn01" id="searchuu"
									name="button7" value="查询"/>
								<input type="button" class="btn01" id="qduser"
									name="button5" value="确定" />
								<input type="button" class="btn01 xzgr" name="button" value="新增" />
								<input type="hidden" id="bznum">
							   <input type="hidden" name="parms" id="grparms" />
								
							</td>
							<td width="80">
								<a id="grsy" title="0" style="cursor:pointer;">上一页</a>
							</td>
							<td width="80">
								<a id="grxy" title="0" style="cursor:pointer;">下一页</a>
							</td>
							<td width="100">
								<a id="grzy">共&nbsp;&nbsp; <span style="color: red"
									id="grzycount"></span>&nbsp;&nbsp;页 </a>
							</td>
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
								</table>
							</td>
							<td width="83%" valign="top" align="left">
								<div id="body_02cu"
									style="margin-top: 2px; height:340px;width:100%;border:1px solid #ccc;border-left:none; overflow: auto;">
								</div>
							</td>
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