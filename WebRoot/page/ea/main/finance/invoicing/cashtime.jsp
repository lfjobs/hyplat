<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
<title>预算收入金额</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	var search="${search}";
   	var basePath="<%=basePath%>";
   	var pNumber=${pageNumber};
    var search='${search}'; 
   	var notoken = 0;
   	var personurl = "";
   	var token=0;
   	var cashID = '';
</script>
<script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/finance/invoicing/cashtime.js"></script>
	</head>
	<body> 
		<form name="cashtimeList" id="cashtimeList" method="post"><input type="submit" name="submit" style="display:none"/>	
			<div class="main_main">
				<table class="flexme11">
				<thead>
					<tr >
						<th width="80" align="center">
							选择
						</th>
						<th width="220" align="center">
							公司
						</th>
						<th width="100" align="center">
							预算收入时间
						</th>
						<th width="220" align="center">
							金额
						</th>
					</tr>
					</thead>
					<tbody>
					<s:iterator value="pageForm.list">
						<tr align="center" id="${cashID}">
							<td >
								<input type="radio" name="a" class="JQuerypersonvalue" value="${cashID}" />
								<input type="hidden" name="cashKey" id="cashKey" value="${cashKey}" />
								<input type="hidden" name="cashID" id="cashID" value="${cashID}" />
								<input type="hidden" name="companyID" id="companyID" value="${companyID}" />
							</td>
							<td class="td_bg01"><span id="companyName">${companyName}</span></td>
							<td class="td_bg01"><span id="sTime">${stime}</span></td>
							<td class="td_bg01"><span id="num">${num}</span></td>
						</tr>
					</s:iterator>
					</tbody>
				</table>
				<c:import url="../../../page_navigator.jsp">
					<c:param name="actionPath" value="ea/cashtime/ea_getcashList.jspa?pageNumber=${pageNumber}"></c:param>
				</c:import>
				<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
			</div>
		</form>
		 <!-- 查询按钮 -->
			<div class="jqmWindow" style="width:300px; right: 35%; top: 10%" id="jqModelSearch">
				<form name="SearchForm" id="SearchForm" method="post" >
				<div class="drag">查询信息<div class="close"></div>
				</div>
				<table id="SearchTable">
					<tr><td>查询条件 </td></tr>
					<tr>
						<td>金额：</td>
						<td><input name="cashtime.num" /></td>
					</tr>
					<tr>
						<td >时间：</td>
						<td> <input name="sdate" id="sdate"    onfocus="daymonth(this);"/></td>
					</tr>
				</table>
				<div style="text-align:center ;margin:0 auto">
					<input type="button" class="input-button" id="search" value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
				</form>
			</div>
			<!--添加 -->
			 <div class="jqmWindow " style="width: 400px; right: 25%; top: 10%" id="jqModel">
            <form name="addForms" id="addForms" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                	<div class="drag">添加</div>
					   <table width="99%" id="table4">	
					     <tr>
					     <td>
					     	<table  border="0" id="addtables" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
					     		<tr>
									<td height="40" align="right">日期：</td>
									<td><input name="stime" id="stime"  onfocus="daymonth(this);"/></td>
								</tr>
								<tr>
									<td height="40" align="right">金额：</td>
									<td><input name="num" id="num" /></td>
								</tr>
	                       		<tr>
			                         <td height="30" colspan="5" align="center">
			                         	<input name="cashtime.cashID" id="cashID" type="hidden" class="input"  size="20"/>
			                            <input name="cashtime.cashKey" id="cashKey" type="hidden" class="input"  size="20"/>
			                            <input type="button"   class="input-button addSubmits" style="cursor:pointer;width:80px;" value="提交" />
			                           	<input name="sub" value="${session_value}" type="hidden" /><!-- 代替token-->
			                            <input type="button"  class="input-button addreturns" style="cursor:pointer;width:80px;"  value="取消" />
			                         </td>
			                    </tr>
	                     </table>
					     </td>
					     </tr>
					</table>  
        		<s:token></s:token> 	
        	</form>
        </div>
        <!--修改 -->
			 <div class="jqmWindow " style="width: 400px; right: 25%; top: 10%" id="jqModels">
            <form name="upForms" id="upForms" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                	<div class="drag">修改</div>
					   <table width="99%" id="table4">	
					     <tr>
					     <td>
					     	<table  border="0" id="uptables" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
								<tr>
									<td height="40" align="right">金额：</td>
									<td><input name="num" id="num" /></td>
								</tr>
	                       		<tr>
			                         <td height="30" colspan="5" align="center">
			                         	<input name="cashtime.cashID" id="cashID" type="hidden" class="input"  size="20"/>
			                            <input name="cashtime.cashKey" id="cashKey" type="hidden" class="input"  size="20"/>
			                            <input type="button"   class="input-button upSubmits" style="cursor:pointer;width:80px;" value="提交" />
			                           	<input name="sub" value="${session_value}" type="hidden" /><!-- 代替token-->
			                            <input type="button"  class="input-button upreturns" style="cursor:pointer;width:80px;"  value="取消" />
			                         </td>
			                    </tr>
	                     </table>
					     </td>
					     </tr>
					</table>  
        		<s:token></s:token> 	
        	</form>
        </div>
	</body>
</html>