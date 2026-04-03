<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目现金申请使用明细跟踪</title>

<link href="<%=basePath%>css/ea/human/admin_main.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css"
	href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/finance/production/cashapplubills_list1.js"></script>
<link rel="stylesheet"
			href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
		
<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
	type="text/javascript"></script>
	
	<script src="js/ea/finance/store.js" type="text/javascript"></script> 
<script src="js/ea/finance/jquery.resizableColumns.js" type="text/javascript"></script> 
<style type="text/css">
.table {
	border: #a8c7ce 1px solid;
	cellpadding: 0;
	cellspacing: 0;
	font-size: 18px;
}

.table span{
	font-size: 10px;
}

.td {
	border: #cccccc 1px solid;
}

li {
	list-style-type: none;
}

.containerTableStyle {
	overflow: hidden;
}

.querybtn{
  background:url(<%=basePath%>images/search16.png) no-repeat;
  width:16px;
  height:16px;
  border:none;
  cursor:pointer;
  }
  
.c{
	background-color:#87CEFF;
}
</style>
<script type="text/javascript">
 	var results = "${result}";
	var goodsBillsID="";
   	var cashierBillsID=""; 
   	var organizationID="${sessionScope.organizationID}";
   	var search="${search}";
   	var basePath="<%=basePath%>";
	var pNumber = "${pageNumber}";
	var sdate = "${sdate}";
	var edate = "${edate}";
	var notoken = 0;
	var bokuan = "${param.bokuan}";
	var pageCount = '${pageForm.pageCount}';
	var weibokuan = '${weibokuan}';
	var other = '${param.other}';
	var cother = '${param.cother}';
	var level = '${level}';
	var title = "title";
	var str = "${str}";
	var trid = "";
	var cancel = "${cancel}";
	var strc = "";
	var jy="${jy}"
	var xmtype="";
	
	
</script> 
</script>
</head>
<body>
	<form  name="CashApplyBillsform" id="CashApplyBillsform" method="post">
	<div
		style="position: absolute; width: 100%; height:100%; overflow-x: scroll;">
		<table width="1300" border="0" align="center" style="margin-top:20px;margin-bottom: 10px">
			<tr>
				<td align="center" colspan="6">
					<h2 style="padding-top:7px;">
					收支单据明细流水
					</h2>
				</td>
			</tr>
		</table>
		<table width="1300px" align="center" class="table" id="tablemain"
			style="font-size:16px;" cellpadding="10">			
			<thead>
				<tr id="${arr[0] }" >
				<td align="right" colspan="18"><span id="jy"></span></td>
			</tr>
				<tr>
					<th width="27px" align="center">序号</th>
					<th width="70px" align="center">公司</th>
					<th width="60px" align="center">部门</th>
					<th width="100px" align="center">项目名称<a href="#"
						onclick="getPID('xm',this)" style="margin-left:5px;"> <img
							src="<%=basePath%>images/up.jpg"
							style="border: 0;vertical-align:middle" /></a>
					</th>
					<th width="100px" align="center">项目分类<a href="#"
						onclick="getPID('xmtype',this)" style="margin-left:5px;"><img
							src="<%=basePath%>images/up.jpg"
							style="border: 0;vertical-align:middle" /></a>
					</th>
					<th width="130px" align="center">黏贴单号</th>
					<th width="40px" align="center">使用责任人</th>
					<th width="80px" align="center">物品编码<a href="#"
						onclick="getPID('wp',this)" style="margin-left:5px;"><img
							src="<%=basePath%>images/up.jpg"
							style="border: 0;vertical-align:middle" /></a></th>
					<th width="70px" align="center">物品名称<a href="#"
						onclick="getPID('wp',this)" style="margin-left:5px;"><img
							src="<%=basePath%>images/up.jpg"
							style="border: 0;vertical-align:middle" /></a></th>
					<th width="50px" align="center">类别</th>
					<th width="50px" align="center">数量</th>
					<th width="50px" align="center">单价</th>
					<th width="30px" align="center">方向</th>
					<th width="60px" align="center">收入金额</th>
					<th width="60px" align="center">支出金额</th>
					<th width="60px" align="center">结余</th>
					<th width="100px" align="center">往来公司</th>
					<th width="53px" align="center">往来个人</th>
				</tr>
			</thead>
			<tbody id="tb">
				<%
					int number = 1;
				%>
				<c:forEach var='arr' items="${report}" varStatus="list">
					<tr id="${arr[0] }" >
						<td align="center"><span><%=number%></span></td>
						<td align="center"><span id="companyname">${arr[1]}</span></td>
						<td align="center"><span id="organizationname">${arr[2]}</span></td>
						<td align="center"><span id="projectName">${arr[3]}</span></td>
						<td align="center"><span id="xmtypename">${arr[4]}</span></td>
						<td align="center"><span id="journalNum">${arr[5]}</span></td>
						<td align="center"><span id="staffname">${arr[6]}</span></td>
						<td align="center"><span id="Goodsnum">${arr[7]}</span></td>
						<td align="center"><span id="goodsname">${arr[8]}</span></td>
						<td align="center"><span id="typeid">${arr[9]}</span></td>
						<td><span id="quantity">${arr[10]}</span></td>
						<td><span id="price">${arr[11]}</span></td>
						<td align="center"><span id="f">${arr[15]}</span></td>
						<td><span id="m1">${arr[16]}</span></td>
						<td><span id="m2">${arr[17]}</span></td>
						<td align="center"><span id="cashierDate">${arr[18]}</span></td>
						<td align="center"><span id="ccompanyname">${arr[13]}</span></td>
						<td align="center"><span id="UserName">${arr[14]}</span></td>
						</td>
					</tr>
					<%
						number++;
					%>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</form>
	<div id="jqModel" style="display:none;border:1px solid #a8c7ce;background-color:#e1ecfc;width: 210px; height: 330px;position: absolute;top: 31%;left: 25%;"></div>


	<!-- 筛选提交表单 -->
	<div style="display:none;">
		<form id="searchForm" name="searchForm" method="post">

			<input type="submit" style="display:none;" name="submit" /> <input
				type="hidden" value="" name="costSheetBill.projectName" id="proname" />
			<input type="hidden" value="" name="detail.goodsName" id="goodname" />
			<input type="hidden" value="" name="costSheetBill.xmtype"
				id="xmtypes" /> <input type="hidden" value="" name="sdate"
				id="startDates" /> <input type="hidden" value="" name="type"
				id="types" /> <input type="hidden" value="search" name="search" />
			<!-- 用于通过 -->
			<input type="hidden" value="" name="csdID" id="csdIDss" /> <input
				type="hidden" value="" name="csbID" id="csbIDss" />

		</form>
	</div>
	<%------------------------------------选择往来单位------------------------------------%>
	<form name="selectcompanyForm" id="selectcompanyForm" method="post"
		enctype="multipart/form-data">
		<input type="submit" name="submit" style="display: none" />
		<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
			id="companyjqModel">
			<div class="content1" style="width: 100%; height: 400px;">
				<div class="contentbannb">
					<div class="drag">选择往来单位</div>
				</div>
				<table width="99%" height="33" id="searchcompany" border="0"
					align="center" cellpadding="0" cellspacing="0"
					style="margin-top: 5px; background: #FFFFFF;">
					<tr>
						<td width="70" align="right">单位名称：</td>
						<td width="60"><input name="ccompanyID" class="input"
							id="ccompanyID" size="10" style="margin-left: 2px;" />
						</td>

						<td height="33"><input type="button" class="btn02"
							id="searchcc" name="button7" value="查询" /> <input type="button"
							class="btn02" id="qdcompany" name="button5" value="确定" /> <input
							type="button" class="btn02 xzdw" name="button" value="新增" /> <input
							type="button" class="btn02 JQueryreturns" name="button4"
							value="关闭" />
						</td>
						<td width="50"><a id="dwsy" title="0">上一页</a>
						</td>
						<td width="50"><a id="dwxy" title="0">下一页</a>
						</td>
						<td width="70"><a id="dwzy">共&nbsp;&nbsp; <span
								style="color: red" id="zycount"></span>&nbsp;&nbsp; 页</a>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0"
					style="margin-top: 5px; margin-bottom: 5px; height: 450px;">
					<tr>
						<td width="99%" valign="top" align="left">
							<div id="body_02cc"
								style="margin-top: 2px; display: none; width: 100%; overflow: scroll; height: 330px;">
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<s:token></s:token>
	</form>
	<!-- ---------------往来单位------------- -->
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
	<form name="Staffform" id="Staffform" method="post">
		<div id="bankJqm" class="jqmWindow"
			style="width: 90%; height: 400px; absolute; display: none; left: 5%; top: 7%; background: #eff; overflow-x: hidden; overflow-y: hidden">

			<iframe name="daoRu" id="daoRu" width="100%" height="100%"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
		</div>
	</form>
	<!-- ---------------银行账号------------- -->
	<div id="yhbankJqm" class="jqmWindow"
		style="width: 70%; height: 400px; absolute; display: none; left: 20%; top: 10%; background: #eff; overflow-x: hidden; overflow-y: auto;">
		<input type="hidden" id />
		<div style="background: #efg; margin-right: 500px;">
			<input style="display: none;" id="checkopertionID" /> <input
				style="display: none;" id="checkopertionName" /> <input
				style="display: none;" id="childopertionName" /> <input
				style="display: none;" id="checkform" />
		</div>
		<iframe name="yhdaoRu" id="yhdaoRu" width="100%" height="350px"
			frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
		<div>
			<input type="button" class="input-button" id="DaoRuFanqd2"
				value=" 确定 "
				style="cursor: hand; border: 0; margin-left: 400px; height: 25px; width: 60px" />
			<input type="button" class="input-button" id="DaoRuFan2" value=" 关闭 "
				style="cursor: hand; border: 0; margin-left: 40px; height: 25px; width: 60px" />
		</div>
	</div>
	<script type="text/javascript">
		$(function(){ 
		$("#tablemain").resizableColumns({ 
		store: window.store 
		}); 
	}) 
	</script>
</body>
</html>
