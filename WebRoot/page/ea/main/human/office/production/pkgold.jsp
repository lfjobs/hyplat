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
		<title>PKGold</title>
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
		
	
	<script type="text/javascript">
		   var select =1;
		   var pkgoldID = '';
		   var basePath = '<%=basePath%>';
           var ppageNumber=${pageNumber};
           var token=0;
           var notoken = 0;
           var search="${search}";
	</script>
	<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/human/office/production/pkgold.js"></script>
	</head>
	<body>
	
		<form  name="staffappraisalForm"
			enctype="multipart/form-data" id="staffappraisalForm" method="post">
			<s:token></s:token>
			<input type="submit" name="submit" style="display: none" />
		
		

		<div id="main_main" class="main_main">
			<table class="pkgold">
				<thead>
					<tr>
						<th width="35" align="center">
							选择
						</th>
						 <th width="200" align="center">
                               	公司名称
                        </th>
                         <th width="100" align="center">
                            部门名称
                        </th>
                        <th width="100" align="center">
                            职务名称
                        </th>
						<th width="110" align="center">
							员工姓名\操作人
						</th>
						<th width="110" align="center">
							日期
						</th>
						<th width="110" align="center">
							金额
						</th>
						<th width="110" align="center">
							存入\支出
						</th>
					</tr>
				</thead>
				<tbody> 
					<s:iterator value="pageForm.list">
						<tr class="td_bg01 saveAjax" id="${pkgoldID}">
							<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${pkgoldID}" />
							</td>
							<td>
                                <span id="companyName">${companyName}</span>
                            </td>
                            <td>
                                <span id="organizationName">${organizationName}</span>
                            </td>
                             <td>
                                <span id="depName">${depName}</span>
                            </td>
							<td class="td_bg01">
								<span id="staffName" >${staffName}</span> 
							</td>
							<td class="td_bg01">
								<span id="pkDate">${pkDate}</span> 
							</td>
							<td class="td_bg01">
								<span id="workDateSaturation">${gold}</span> 
							</td>
							<td class="td_bg01">
								<span id="responsibility1">
								<c:if test="${status == 00}">存入</c:if>
								<c:if test="${status == 01}">支出</c:if></span>  
								<span style="display:none" id="staffID">${staffID}</span>
								<span style="display:none" id="companyID">${companyID}</span>
								<span style="display:none" id="pkgoldKey">${pkgoldKey}</span>
								<span style="display:none" id="organizationID">${organizationID}</span> 
								<span style="display:none" id="depID">${depID}</span> 
							</td> 
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/pkgold/ea_getList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
			</c:import>
		</div>
		</form>
	
		<!--搜索窗口 -->	
		  <form name="searchForm" id="searchForm" method="post">
        <div class="jqmWindow jqmWindowcss3" style="width: 400px;top: 10%;" id="jqModelSearch">
               
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTable">
				<tr>
				<td>员工姓名：</td>
				<td><input name="pkgold.staffName" size="17"/></td>
				</tr>
				<tr>
				<td>日期：</td>
				<td><input name="pkgold.pkDate" size="17" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" readonly="readonly"/></td>
				</tr>
				<tr>
				<td>存入\支出：</td>
				<td>
					<select name="pkgold.status" id="" >
						<option value="">请选择</option><option value="00">存入</option><option value="01">支出</option>
					</select>
				</td>
				</tr>
                  <input name="search" type="hidden" value="search" />
				</table>
               <div align="center">
				  <input type="button" class="input-button" id="tosearch"	value=" 查询 " />
         	   </div> 
         	</div>
        </form>
       
	</body>
</html>
