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
		<title>PKGoldPool</title>
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
		   var pkgoldpoolID = '';
		   var basePath = '<%=basePath%>';
           var ppageNumber=${pageNumber};
           var token=0;
           var notoken = 0;
           var search="${search}";
	</script>
	<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/human/office/production/pkgoldpool.js"></script>
	</head>
	<body>
	
		<form  name="staffappraisalForm"
			enctype="multipart/form-data" id="staffappraisalForm" method="post">
			<s:token></s:token>
			<input type="submit" name="submit" style="display: none" />
		
		

		<div id="main_main" class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr>
						<th width="35" align="center">
							选择
						</th>
						 <th width="200" align="center">
                               	公司名称
                        </th>
                        <th width="100" align="center">
                           	日期
                        </th>
						<th width="110" align="center">
							PK金汇总
						</th>
						<th width="110" align="center">
							支出
						</th>
						<th width="110" align="center">
							余额
						</th>
					</tr>
				</thead>
				<tbody> 
					<s:iterator value="pageForm.list">
						<tr class="td_bg01 saveAjax" id="${pkgoldpoolID}">
							<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue" value="${pkgoldpoolID}" />
							</td>
							<td>
                                <span id="companyName">${companyName}</span>
                            </td>
                             <td>
                                <span id="pkDate">${pkDate}</span>
                            </td>
							<td class="td_bg01">
								<span id="goldpool" >${goldpool}</span> 
							</td>
							<td class="td_bg01">
								<span id="goldpaypool">${goldpaypool}</span> 
							</td>
							<td class="td_bg01">
								<span id="goldbalpool">${goldbalpool}</span> 
								<span style="display:none" id="companyID">${companyID}</span>
								<span style="display:none" id="pkgoldpoolKey">${pkgoldpoolKey}</span>
							</td> 
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/pkgoldpool/ea_getList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
			</c:import>
		</div>
		</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		<!--搜索窗口 -->	
		  <form name="searchForm" id="searchForm" method="post">
        <div class="jqmWindow jqmWindowcss3" style="width: 300px;top: 10%;" id="jqModelSearch">
               
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="pkSearchTable">
				<td align="right" width="100px">日期：</td>
					<td><input name="pkgoldpool.pkDate" size="17" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" readonly="readonly"/></td>
					</tr>
                  <input name="search" type="hidden" value="search" />
				</table>
               <div align="center">
				  <input type="button" class="input-button" id="tosearch"	value=" 查询 " />
         	   </div> 
         	</div>
        </form>
        <!--PK支出窗口 -->	
		  <form name="pkSaveForm" id="pkSaveForm" method="post">
        <div class="jqmWindow " style="width: 400px;top: 10%;left: 20%;" id="jqModelSave">
               
				<input type="submit" name="submit" style="display: none" />
				<div class="drag" >
					pk金
					<div class="close">
					</div>
				</div>
				<table id="pkSaveTable">
					<tr>
					<td align="right" width="100px">公司：</td>
					<td><input name="pkgold.companyName" id="companyName" size="30" readonly="readonly"/></td>
						<input type="hidden" name="pkgold.companyID" id="companyID"/>
						<input type="hidden" name="pkgold.status" id="status" value="01"/>
					</tr>
					<tr>
					<td align="right">日期：</td>
					<td><input name="pkgold.pkDate" onfocus="date(this);" id="pkDate" size="30" /></td>
					</tr>
					<tr>
					<td align="right">可用金额：</td>
					<td><input id="goldbalpool" size="30" readonly="readonly" /></td>
					</tr>
	                 <tr>
					<td align="right">实际支出：</td>
					<td><input name="pkgold.gold" id="gold" size="30"/></td>
					</tr>
					<tr>
					<td align="right">备注：</td>
					<td><input name="pkgold.remarks" size="30"/></td>
					</tr>
				</table>
               <div align="center">
				  <input type="button" class="input-button" id="tosave"	value=" 保存 " />
         	   </div> 
         	</div>
        </form>
	</body>
</html>
