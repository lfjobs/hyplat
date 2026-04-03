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
		<title>部门机构职责</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
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
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<script src="<%=basePath%>js/ea/ccompany/organization/orgdesc_list.js"></script>
	</head>
	<SCRIPT type="text/javascript">
		   var select =1;
		   var basePath = '<%=basePath%>';
           var ppageNumber=${pageNumber};
          	var search='${search}';
           var token=0;
           var notoken=0;
           var ogID = '${organizationdesc.organizationid}';
			var ogName = '<%=request.getParameter("ogName") %>';
			var organizationdescid = '';
    </SCRIPT>
	<body>
		<form  name="orgDescForm" enctype="multipart/form-data" id="orgDescForm" method="post">
			<s:token></s:token>
			<input type="submit" name="submit" style="display: none" />
		<div id="main_main" >
			<table class="registration">
				<thead>
					<tr>
						<th width="30" align="center">
							选择
						</th>
						<th width="160" align="center">
							日工作
						</th>
						<th width="160" align="center">
							周工作
						</th>
						<th width="160" align="center">
							月工作
						</th>
						<th width="160" align="center">
							季工作
						</th>
						<th width="160" align="center">
							年工作
						</th>
						<th width="160" align="center">
							本职工作
						</th>
						<th width="160" align="center">
							兼职工作
						</th>
					</tr>
				</thead>
                <tbody  id="tbwid">
					<tr id="sa" style="display: none" class="td_bg01 saveAjax model2 ">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue" value="${organizationdescid}" />
						</td>
						<td class="td_bg01">
							<input name="datework" id="datework" /></td>
						<td class="td_bg01">
							<input name="weekwork" id="weekwork"/>
						<td class="td_bg01">
							<input name="monthwork" id="monthwork"/>
						</td>
						<td class="td_bg01">
							<input name="seasonwork" id="seasonwork"/>
						</td>
						<td class="td_bg01">
							<input name="yearwork" id="yearwork"/>
						</td>
						<td class="td_bg01">
							<input name="jobwork" id="jobwork"/>
						</td>
						<td class="td_bg01">
							<input name="twicework" id="twicework" />
							<input type="hidden" name="organizationdesckey" id="organizationdesckey" />
							<input type="hidden" name="organizationdescid" id="organizationdescid" />
							<input type="hidden" name="companyid" id="companyid" />
							<input type="hidden" name="organizationid" id="organizationid" />
						</td>

					</tr>
					<s:iterator value="pageForm.list">
						<tr class="td_bg01 saveAjax" id="${organizationdescid}">
							<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue trclass" value="${organizationdescid}" />
							</td>
							<td class="td_bg01">
								<span id="datework" >${datework}</span>
								<input class="model1" value="${datework}" name="datework"  />
							</td>
							<td class="td_bg01">
								<span id="weekwork" >${weekwork}</span>
								<input class="model1" value="${weekwork}" name="weekwork"  />
							</td>
							<td class="td_bg01">
								<span id="monthwork" >${monthwork}</span>
								<input class="model1" value="${monthwork}" name="monthwork"  />
							</td>
							<td class="td_bg01">
								<span id="seasonwork" >${seasonwork}</span>
								<input class="model1" value="${seasonwork}" name="seasonwork"  />
							</td>
							<td class="td_bg01">
								<span id="yearwork" >${yearwork}</span>
								<input class="model1" value="${yearwork}" name="yearwork"  />
							</td>
							<td class="td_bg01">
								<span id="jobwork" >${jobwork}</span>
								<input class="model1" value="${jobwork}" name="jobwork"  />
							</td>
							<td class="td_bg01">
								<span id="twicework" >${twicework}</span>
								<input class="model1" value="${twicework}" name="twicework"  />
								<input type="hidden" name="organizationdesckey" value="${organizationdesckey}" />
								<input type="hidden" name="organizationdescid" value="${organizationdescid}" />
								<input type="hidden" name="companyid" value="${companyid}" id="companyid"/> 
								<input type="hidden" name="organizationid" value="${organizationid}" id="organizationid"/> 
								<input type="hidden" name="cname" value="${cname}" id="cname"/> 
								<input type="hidden" name="ctime" value="${ctime}" id="ctime"/> 
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/organizationdesc/ea_getOrganizationdescList.jspa?organizationdesc.organizationid=${organizationdesc.organizationid}&search=${search}&pageNumber=${pageNumber}&ogName=${param.ogName}"></c:param>
			</c:import>
		</div>
	</form>
		 <iframe name="hidden" style="height:0;"  marginheight="0" frameborder="0" border="0" framespacing="0" noresize="noResize" ></iframe>
		 
	</body>
</html>
