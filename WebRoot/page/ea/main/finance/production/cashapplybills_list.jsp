<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
		<meta http-equiv="cache-control" content="no-cache" />
		<title>现金申请明细审批单/现金申请对账单 </title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		
		<script src="<%=basePath%>js/jquery-1.3.1.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>/css/admin_main2.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>		
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/production/cashapplybills_list.js"></script>
			
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />	
        <script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<link rel="stylesheet"
			href="<%=basePath%>js/tree/common/css/style.css" type="text/css"
			media="screen" />
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<link rel="STYLESHEET" type="text/css"
			href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
		
        <style type="text/css">
        body{
	        overflow: hidden;
        }
        </style>
		<script type="text/javascript">
			var goodsBillsID="";
        	var cashierBillsID=""; 
        	var organizationID="${sessionScope.organizationID}";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var sdate="${sdate}";
        	var edate="${edate}";
         	var notoken = 0;
         	var bokuan="${param.bokuan}";
         	var pageCount='${pageForm.pageCount}';
         	var weibokuan='${weibokuan}';
         	var other='${param.other}';
         	var cother='${param.cother}';
         	var level='${level}';
         	var title="title";
         	var str="${str}";
         	var trid="";
         	var cancel = "${cancel}";
         	var strc = "";
         	var status="";
        </script>
	</head>
	<body>
		<div>
		<form  name="CashApplyBillsform" id="CashApplyBillsform" method="post">
		<input name="xxx" value="xxx" style="display: none;"/>
			<table class="flexme11 JQueryflexme" id="cashapply" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th width="30" align="center">
							选择
						</th>
						<th width="170" align="center">
							公司
						</th>
						<th width="100" align="center">
							部门
						</th>
						<th width="140" align="center">
							项目名称
						</th>
						<th width="120" align="center">
							项目分类
						</th>
						<th width="150" align="center">
							项目单号
						</th>
						<th width="60" align="center">
							使用责任人
						</th>
						<th width="60" align="center">
							制单人
						</th>
						<th width="80" align="center">
							制单日期
						</th>
						<th width="50" align="center">
							拨款状态
						</th>
					</tr>
				</thead>
				<tbody id="tbwid">
					<c:forEach var='arr' items="${pageForm.list}" varStatus="list">
						<tr id="${arr[0] }">
							<td align="center">
								<input type="radio" name="a" class="JQuerypersonvalue"
								value="${arr[0]}" />
								<input type="hidden" value="${arr[0]}" name="cashierBills.cashierBillsID"/>
							</td>
							<td align="center">
								<span id="companyname">${arr[1]}</span>
								<input type="hidden" value="${arr[1]}" name="cashierBills.companyname"/>
							</td>
							<td align="center">
								<span id="organizationname">${arr[2]}</span>
								<input type="hidden" value="${arr[2]}" name="cashierBills.organizationname"/>
							</td>
							<td align="center">
								<span id="projectName">${arr[3]}</span>
								<input type="hidden" value="${arr[3]}" name="cashierBills.projectName"/>
							</td>
							<td align="center">
								<span id="xmtypename">${arr[4]}</span>
								<input type="hidden" value="${arr[4]}" name="cashierBills.xmtypename"/>
							</td>
							<td align="center">
								<span id="journalNum">${arr[5]}</span>
								<input type="hidden" value="${arr[5]}" name="cashierBills.journalNum"/>
							</td>
							<td align="center">
								<span id="staffname">${arr[6]}</span>
								<input type="hidden" value="${arr[6]}" name="cashierBills.staffname"/>
							</td>
							<td align="center">
								<span id="inputName">${arr[7]}</span>
								<input type="hidden" value="${arr[6]}" name="cashierBills.inputName"/>
							</td>
							<td align="center">
								<span id="cashierdate">${arr[8]}</span>
							</td>
							<td align="center">
								<span id="appropriatestatus">
								<c:if test="${arr[9]=='41'}">未拨款</c:if>
								<c:if test="${arr[9]=='42'}">审核中</c:if>
								<c:if test="${arr[9]=='44'}">暂不拨款</c:if>
								<c:if test="${arr[9]=='43'}">已拨款</c:if>
								</span>
								<input type="hidden" id="notesStatus" value="${arr[9]}"/>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</form>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/cashapplybills/ea_toCashList.jspa?pageNumber=${pageNumber}&search=${search}&sdate=${sdate}&edate=${edate}&bokuan=${param.bokuan}&weibokuan=${weibokuan}&other=${param.other}&level=${level}&str=${str}&cancel=${cancel}&cother=${param.cother}">
				</c:param>
			</c:import>
		</div>
		<c:if test="${param.bokuan=='bokuan'}">
		<div align="center" style="height: 26px">
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 保存 " style="cursor: hand; border: 0;" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 取消 "
					style="cursor: hand; border: 0;" />
		</div>
		</c:if> 
		 <!--搜索窗口 -->
         <form name="cstaffSearchForm" id="cstaffSearchForm" method="post">
        <div class="jqmWindow" style="width: 400px;right: 30%;top: 10%;" id="jqModelSearch">
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table width="396" id="SearchTable">
					<tr>
						<td align="right">
							项目单号：
						</td>
						<td>
							<input id="journalNum" style="width: 195px"
								name="cashierBillsVO.journalNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							公司名称：
						</td>
						<td>
							<select id="deptID" name="cashierBillsVO.companyID">
							<option value="">全部</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							部门名称：
						</td>
						<td width="314">
							<select id="orgID" name="cashierBillsVO.departmentID">
								<option value="">
									全部
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="200" align="right">
							责任人：
						</td>
						<td>
							<select name="cashierBillsVO.staffID" id='person'>
								<option value="">
									全部
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							申请日期：
						</td>
						<td style="width: 200px">
							<input id="sdate" name="sdate" onfocus="date(this);"
								style="width: 85px" />
							至
							<input id="edate" name="edate" onfocus="date(this);"
								style="width: 85px" />
						</td>
					</tr>
				</table>
                <div align="center">
                    <input type="button" class="input-button" id="searchStaff" value="查询" /><input type="button" class="input-button JQueryreturn" value="取消" /><input name="search" type="hidden" value="search" /><input name="cancel" type="hidden" value="${cancel}" />
                </div>
        </div> 
        </form>
        <!--调拨窗口 -->
        <form name="cstaffChangeForm" id="cstaffChangeForm" method="post">
        <div class="jqmWindow" style="width: 400px;right: 30%;top: 10%;" id="jqModelChange">
                <div class="drag">
                    调拨单位
                    <div class="close">
                    </div>
                </div>
                <table width="396" id="SearchTable">
					<tr>
						<td align="right">
							其他公司名称：
						</td>
						<td>
						    <input name="cashierBillsVO.companyname" type="hidden" id="othercompanyname" value="" />
							<select id="cdeptID" name="cashierBillsVO.companyID">
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							其他部门名称：
						</td>
						<td width="314">
						    <input name="cashierBillsVO.departmentname" type="hidden" id="otherdepartmentname" value="" />
							<select id="corgID" name="cashierBillsVO.departmentID">
								<option value="">
									全部
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="200" align="right">
							其他责任人：
						</td>
						<td>
						 <input name="cashierBillsVO.staffname" type="hidden" id="otherstaffname" value="" />
							<select id='cperson' name="cashierBillsVO.staffID">
								<option value="">
									全部
								</option>
							</select>
						</td>
					</tr>
				</table>
                <div align="center">
                    <input type="button" class="input-button" id="ChangeStaff" value="调拨" /><input type="button" class="input-button JQueryreturnc" value="取消" /><input name="allot" type="hidden" value="allot" /><input name="cancel" type="hidden" value="${cancel}" />
                </div>
        </div> 
        </form>
        <%------------------------------------选择往来单位------------------------------------%>
		<form name="selectcompanyForm" id="selectcompanyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="companyjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择往来单位
						</div>
					</div>
					<table width="99%" height="33" id="searchcompany" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="70" align="right">
								单位名称：
							</td>
							<td width="60">
								<input name="ccompanyID" class="input" id="ccompanyID" size="10"
									style="margin-left: 2px;" />
							</td>
							
							<td height="33">
								<input type="button" class="btn02" id="searchcc" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdcompany" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzdw" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
							</td>
							<td width="50">
								<a id="dwsy" title="0">上一页</a>
							</td>
							<td width="50">
								<a id="dwxy" title="0">下一页</a>
							</td>
							<td width="70">
								<a id="dwzy">共&nbsp;&nbsp; <span style="color: red"
									id="zycount"></span>&nbsp;&nbsp; 页</a>
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
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		<form name="Staffform" id="Staffform" method="post">
		<div id="bankJqm" class="jqmWindow"
			style="width: 90%; height: 400px; absolute; display: none; left: 5%; top: 7%; background: #eff; overflow-x: hidden; overflow-y: hidden">

			<iframe name="daoRu" id="daoRu" width="100%" height="100%" 
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
		</div>
		</form>
		<!-- ---------------银行账号------------- -->
		<div id="yhbankJqm" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<input type="hidden" id/>
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
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
	</body>
</html>
