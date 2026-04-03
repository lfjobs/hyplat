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
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>社会单位人员列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css"/>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/contactcompany/cs.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript">
 var  ccompanyID = '${companyID}';
 var  csid="";
 var  companyName = '${companyName}';
 var  basePath='<%=basePath%>';           
 var  pNumber ='${pageNumber}';  
 var  search='${search}';
 var  token = 0 ;
 var  personurl='';
 var  notoken = 0;
 var  retoken=0;
 var  select = 0;
</script>
	</head>
	<body>
		<form name="csform" id="csform">
			<input type="submit" name="submit" style="display: none" />
			<input name="personID" id="personID" style="display: none" />
			<input name="companyName" id="companyName" value="${companyName }"
				style="display: none" />
			<s:token />
		</form>
		<div>
		<form id="myform" name="myform" action="" method="post">
		<input type="submit" name="submit" style="display: none" />
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="30" align="center">
							请选择
						</th>
						<th width="80" align="center">
							档案编号
						</th>
						<th width="60" align="center">
							人员名称
						</th>
						<th width="50" align="center">
							性别
						</th>
						<th width="200" align="center">
							身份证
						</th>
						<th width="70" align="center">
							录入时间
						</th>
					</tr>
				</thead>
				<tbody  id="tbwid">
					<c:forEach var="obj" items="${pageForm.list}">
						<tr id="${obj[0] }">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue trclass"
									value="${obj[0] }" />
							</td>
							<td>
								<span id="recordCode">${obj[1] }</span>
							</td>

							<td>
								<span id="staffName">${obj[2] }</span>
							</td>
							<td>
								<span id="sex">${obj[3] }</span>
							</td>
							<td>
								<span id="staffIdentityCard">${obj[4] }</span>
							</td>
							<td>
								<span id="verifyTime">${fn:substring(obj[5],0,10) }</span>
							</td>
						</tr>
					</c:forEach>
					<tr id="kelong" style="display: none">
						<td align="center" bgcolor="#FFFFFF">
							<input type="text" name="staffID" id="staffID" />
							<input type="text" name="staffName" id="staffName" />
						</td>
					</tr>
				</tbody>
			</table>
			</form>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/cs/ea_getPerson.jspa?companyID=${companyID}&search=${search }&pageNumber=${pageNumber }">
				</c:param>
			</c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		</div>
		
		<form name="personForm" id="personForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;width: 100%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%">
					<div class="contentbannb">
						<div class="drag">
							筛选人员
						</div>
					</div>
					<table width="99%" height="33" id="searchTable" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td align="right">
								名称：
							</td>
							<td>
								<input name="staff.staffName" class="input" id="staffname"
									size="10" style="margin-left: 2px;" />
							</td>
							<td align="right">
								身份证号：
							</td>
							<td>
								<input name="staff.staffIdentityCard" class="input" id="idcard"
									size="10" style="margin-left: 2px;" />
							</td>
							<td align="right">
								录入时间：
							</td>
							<td>
								<input name="verifyTime" class="input" id="verifyTime" onfocus="date(this);"
									size="10" style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn02" ID="searchPerson"
									name="button7" value="查询" />
							</td>
							<td height="33">
								<input type="button" class="btn02" id="selectPerson"
									name="button5" value="确定" />
							</td>
							<td height="33">
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								<input type="hidden" name="parms" id="parms" />
							</td>
							<td width="60">
								<a id="wpsy" title="0">上一页</a>
							</td>
							<td width="60">
								<a id="dqy">第&nbsp;&nbsp;<span style="color: red"
									id="dqycount"></span>&nbsp;&nbsp;页</a>
							</td>
							<td width="60">
								<a id="wpxy" title="0">下一页</a>
							</td>
							<td width="80">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycount"></span>&nbsp;&nbsp;页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="100%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; display: none; height: 200px; width: 100%; overflow: scroll;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		
		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 40%;; top: 10%"
			id="jqModelSearch">
			<form name="SearchForm" id="SearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询人员
					<div class="close">
					</div>
				</div>
				<table width="396" id="csSearchTable">
					<tr>
						<td width="123" align="right">
							人员名称：
						</td>
						<td width="261">
							<input name="staff.staffName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							身份证号：
						</td>
						<td>
							<input name="staff.staffIdentityCard" />
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
	<script type="text/javascript">
    $(function(){   
    	setTimeout(function(){ 			
    	  	  $("div.bDiv").css({"height":parent.document.getElementById("mainframe5").offsetHeight-120+"px"});
    	    },100);
    	$(window).resize(function(){ 
		      setTimeout(function(){ 			
		    	  $("div.bDiv").css({"height":parent.document.getElementById("mainframe5").offsetHeight-120+"px"});
		      },100);
	 }); 
});
</script> 
	</body>
</html>