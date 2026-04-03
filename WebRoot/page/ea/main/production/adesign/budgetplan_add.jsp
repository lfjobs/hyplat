<!DOCTYPE HTML>
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
<title>预算计划<c:if test="${plan.bpid!=null}">修改</c:if><c:if test="${plan.bpid==null}">添加</c:if></title>

		<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"
			type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/admin_main111.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/production.css" />

		<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/adesign/budgetplan_add.js"
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
	var type = "${type}";
	var notoken = 0;
	var fiveClear="${fiveClear}";
	var category="${category}";
</script>

</head>
<body>
	<form id="mainForm" name="mainForm" method="post">
		<input type="submit" name="submit" style="display:none;">
		<div class="main">
			<div class="top">
				<c:if test="${type==01}">产品生产量预算</c:if>
				<c:if test="${type==02}">生产量日周月季年计划</c:if>
			</div>
			<div class="body">

				<div class="showinfo show">
					<table id="productbl">
						<tr>
							<td align="right">行业名称：</td>
							<td><input type="text" class="inputtext tradeCode "  name="plan.tradeCode"
								readonly value="${plan.tradeCode}"/> 
						</tr>
						<%--<tr>
							<td align="right">项目产品分类：</td>
							<td><input type="text" class="inputtext" style="width:40%;"
								readonly id="mulpro" /> <input type="hidden" id="pro"
								name="goodsManage.projecttype" /> &nbsp;<input type="button"
								onclick="pop('project')" value="选择" class="btn01" /></td>
						</tr>

						--%><tr>
							<td align="right">产品编号：</td>
							<td><input type="text" class="inputtext productCode "
								name="plan.productCode" value="${plan.productCode}" /> <input class="ppID"
								type="hidden" name="plan.productID" value="${plan.productID}" id="ppid"/>
								<input type="hidden" name="plan.bpid" value="${plan.bpid}"  /> <input
								type="hidden" name="plan.bpkey" value="${plan.bpkey}" />
								<input
								type="hidden" name="plan.type" value="${type}" />
								</td>
						</tr>

						<tr>
							<td align="right">产品名称：</td>
							<td><input type="text" class="inputtext goodsName input3" style="width:40%;"
								name="plan.productName" value="${plan.productName}" id="pp" readonly="readonly"> <input
								type="button" onclick="pop('products')"  id="selectpr" value="选择" class="btn01" />
							</td>
							
						</tr>



						<tr>
							<td align="right">单价：</td>
							<td><input type="text" class="inputtext price" name="plan.price"
								value="${plan.price}" />
							</td>
						</tr>


						<tr>
							<td align="right">流水线设备套数：</td>
							<td><input type="text" class="inputtext posnum"
								name="plan.deviceNum" value="${plan.deviceNum}" />
							</td>
						</tr>


						<tr>
							<td align="right">单套设备最大用人量：</td>
							<td><input type="text" class="inputtext posnum"
								name="plan.maxBydevice" value="${plan.maxBydevice}" />
							</td>
						</tr>

						<tr>
							<td align="right">单设备每小时最大生产量：</td>
							<td><input type="text" class="inputtext posnum"
								name="plan.maxByhbyd" value="${plan.maxByhbyd}" />
							</td>
						</tr>

						<tr>
							<td align="right">设备每天工作时间：</td>
							<td><input type="text" class="inputtext posnum"
								name="plan.worktimeByd" value="${plan.worktimeByd}" /></td>
						</tr>
						<tr>
							<td align="right">数量：</td>
							<td><input type="text" class="inputtext quantity posnum"
								name="plan.quantity" value="${plan.quantity}" /></td>
						</tr>
						<tr>
							<td align="right">金额：</td>
							<td><input type="text" class="inputtext money" name="plan.money"
								value="${plan.money}" /></td>
						</tr>
						<tr>
							<td align="right">日最大生产量：</td>
							<td><input type="text" id="maxDay" class="inputtext jisuan positiveNumZ posnum" name="plan.maxDay"
								value="${plan.maxDay}" /></td>
						</tr>
						<tr>
							<td align="right">周最大生产量：</td>
							<td><input type="text" id="maxWeek" class="inputtext jisuan" name="plan.maxWeek"
								value="${plan.maxWeek}" /></td>
						</tr>
						<tr>
							<td align="right">月最大生产量：</td>
							<td><input type="text" id="maxMonth" class="inputtext jisu"
								name="plan.maxMonth" value="${plan.maxMonth}" /></td>
						</tr>
						<tr>
							<td align="right">季最大生产量：</td>
							<td><input type="text" id="maxSeason" class="inputtext jisuan"
								name="plan.maxSeason" value="${plan.maxSeason}" /></td>
						</tr>
						<tr>
							<td align="right">年最大生产量：</td>
							<td><input type="text" id="maxYear" class="inputtext jisuan" name="plan.maxYear"
								value="${plan.maxYear}" /></td>
						</tr>

						<tr>
							<td align="right">预算年份：</td>
							<td><input type="text" value="${plan.year}" name="plan.year"  
							onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" value="${plan.year}" class="Wdate inputtext"/>
							</td>
						</tr>
						<tr>
							<td align="right">备注：</td>
							<td><textarea type="text" class="inputtext ckTextLength" name="plan.remark" style="height: 60px;" maxlength="40"
								value="${plan.remark}"></textarea>
							</td>
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
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>