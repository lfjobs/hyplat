<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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

<title>设备分配交接单</title>

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
			<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/production.css" />
<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/admin_main111.css" />

<script src="<%=basePath%>js/ea/production/cprocedure/proedpedit.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/popLayer/css/popstyle.css" />
<script src="<%=basePath%>js/popLayer/js/popLayer.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css"
	href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";
var seqnum = 2;
var notoken = 0;
var token=0;
var fiveClear="${fiveClear}";
var fieId="";
</script>

</head>
<body>
	<form id="mainForm" name="mainForm" method="post">
	<s:hidden name="pedKey"></s:hidden>
	<s:hidden name="pedId"></s:hidden>
	<s:token></s:token>
		<input type="submit" name="submit" style="display:none;">
		<div class="main">
			<div class="top">设备分配交接单</div>
			<div class="body">

				<div class="showinfo show">
					<table id="productbl">
						<tr>
							<td align="right" style="width:38%;">产品名称：</td>
							<td>
								<s:textfield cssClass="inputtext goodsName input3" name="goodsName" readOnly="readOnly" cssStyle="width:40%;" id="proed"></s:textfield>
							<input
								type="button" onclick="pop('products')"  id="selectpr"  value="选择" class="btn01" />
							</td>
						</tr>
						<tr>
							<td align="right">产品编号：</td>
							<td>
								<s:textfield cssClass="inputtext productCode" name="prodctSn"  readOnly="readOnly" ></s:textfield>
								<s:hidden cssClass="ppID" name="ppID"></s:hidden>
								
								</td>
						</tr>
						<tr>
							<td align="right" style="width:38%;">选择场地：</td>
							<td>
								<s:textfield cssClass="inputtext   input3" name="siteAddress" readOnly="readOnly" cssStyle="width:40%;" id="siteAddress" value="%{#request.fie.siteAddress}"></s:textfield>
								<input type="button"   id="fieldPut"  value="选择" class="btn01" />
								<input type="hidden" name="field" id="field"  value="${fie.fieldDistributionId}"/>
							</td>
						</tr>
						<tr>
							<td align="right">开始时间：</td>
							<td>
								<s:textfield cssClass="inputtext put3" name="sdate" onfocus="date()" ></s:textfield>
								</td>
						</tr>


						<tr>
							<td align="right">结束时间：</td>
							<td>
								<s:textfield cssClass="inputtext put3" name="edate"  onfocus="date()"
								></s:textfield>
								</td>
						</tr>

						<tr>
							<td align="right">分配责任人：</td>
							<td>
								<s:textfield cssClass="inputtext input3" cssStyle="width:40%;" name="staffName" id="dutorName" readOnly="readOnly"></s:textfield>
								<s:hidden cssClass="inputtext" name="staffId" id="dutorID"></s:hidden>
								
								
								
								<input
								type="button" onclick="pop('members')"  id="selectme"  value="选择" class="btn01" />
								
								</td>
						</tr>
						<tr>
							<td align="right">职责：</td>
							<td>
								<s:textfield cssClass="inputtext put3" name="duty"></s:textfield></td>
						</tr>

						<tr>
							<td align="right">设备分配：			
							</td>
							<td style="white-space:nowrap;">
							<nobr>
							<select size="8" style="width:40%;" id="sb">${options}</select>
							
							<input type="hidden"  name="devices" id="devices" value="${device}"/>	
						
							
									
							<input
								type="button" onclick="pop('goods')" value="选择" id="selgood" class="btn01"/>
							</nobr>
							</td>
						</tr>
						<tr>
							<td align="right">分配时间：</td>
							<td>
								<s:textfield cssClass="inputtext put3" name="odate"
								onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
								></s:textfield>
								</td>
						</tr>


						<tr>
							<td align="right">分配备注：</td>
							<td>
							<s:textarea name="distRemark"  cssStyle="width: 50%;height: 50px;border: 1px solid #ccc;" size="250"></s:textarea>
							</td>
						</tr>
					</table>

			</div>

		</div>
		<div class="bottom">
			<input type="button" class="btn save" value="提交保存" />
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
	
	
	
		
	<!-- 选择物品 -->

	<div id="goods" class="popMain">
		<div class="choose-box">
			<div class="choosetitle">
				<span>选择物品</span>
			</div>
			<div class="chooseborder">
				<div id="goodsTree" style=" border: 0px solid #000000;"></div>
		        <table width="99%" height="33" id="searchgood"     border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								物品编码或名称：
							</td>
							<td width="110">
								<input name="typeID" class="input" id="typeID" size="10"
									style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn01" id="searchGood"
									name="button7" value="查询"/>
								<input type="button" class="btn01" id="selectGood"
									name="button5" value="确定" />
								<input type="button" class="btn01 xzwp" name="button" value="新增" />
			
								<input type="hidden" name="parms" id="parms" />
								
							</td>
							<td width="80">
								<a id="wpsy" title="0" style="cursor:pointer;">上一页</a>
							</td>
							<td width="80">
								<a id="wpxy" title="0" style="cursor:pointer;">下一页</a>
							</td>
							<td width="100">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycount"></span>&nbsp;&nbsp;页 </a>
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
											<div id="gtree" 
												style="overflow: scroll; z-index: 99; width:150px;height: 340px;border:1px solid #ccc;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="83%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; height:340px;width:100%;border:1px solid #ccc;border-left:none; overflow: auto;">
								</div>
							</td>
						</tr>
					</table>

			</div>
			<div class="choose-box-bottom">
				<input type="botton" onclick="hide('goods')" value="关闭" />
			</div>
		</div>
	</div>
	<!--  选择场地 -->
	
	<div id="fie" class="popMain">
		<div class="choose-box">
			<div class="choosetitle">
				<span>选择场地</span>
			</div>
			<div class="chooseborder">
				<div id="fieTree" style=" border: 0px solid #000000;"></div>
		        <table width="99%" height="33" id="fiegood"  border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								场地地址：
							</td>
							<td width="110">
								<input name="fieName" class="input" id="fieName" size="10"
									style="margin-left: 2px;" />
								<input type="hidden" id="fieID" name="fieID">
							</td>
							<td height="33">
								<input type="button" class="btn01 fieOperation" 
									name="button7" value="查询"/>
								<input type="button" class="btn01 fieOperation" 
									name="button5" value="确定" />				
							</td>
							<td width="80">
								<a id="wpsy" title="0" style="cursor:pointer;">上一页</a>
							</td>
							<td width="80">
								<a id="wpxy" title="0" style="cursor:pointer;">下一页</a>
							</td>
							<td width="100">
								<a id="fieWpzy">共&nbsp;&nbsp; <span style="color: red"
									id="fieWpzycount"></span>&nbsp;&nbsp;页 </a>
							</td>
						</tr>
					</table>
					<hr style="color:#C3C3C3">
					<table width="99%" border="0" align="center" cellpadding="0" id="fieTable" class="table"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
					
					</table>

			</div>
			<div class="choose-box-bottom">
				<input type="botton" id="fieClose" value="关闭" />
			</div>
		</div>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>