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
		<title>会计期间</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

        <script type="text/javascript"  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/voucher/fisperiod.js"></script>
		<script type="text/javascript">
		 var  basePath="<%=basePath%>";           
         var  pNumber ="${pageNumber}";  
         var  search ="${search}";  
         var  fpID = "";
         var  token=0;
         var  select =1;
         var notoken=0;
	</script>
	<style type="text/css">
		.td_bg01 span{
		text-align:left;display:block;}
	</style>


	</head>



	<body>

		<form name="fispriodForm" id="fispriodForm" method="post">
			<s:token></s:token>

			<input type="submit" name="submit" style="display:none" />



			<div id="main_main" class="main_main">
				<table class="fexlist">
					<thead>
						<tr>
							<th width="35" align="center">
								选择
							</th>
							<th width="35" align="center">
								序号
							</th>
				
							<th width="150" align="center">
								会计年度
							</th>
							<th width="100" align="center">
								会计年度起始月份
							</th>
							<th width="130" align="center">
								会计年度终止月份
							</th>
							<th width="90" align="center">
								新增人员
							</th>
							<th width="150" align="center">
								新增时间
							</th>
							<th width="90" align="center">
								修改人员
							</th>

							<th width="150" align="center">
								修改时间
							</th>
							<th width="180" align="center">
								公司
							</th>


						</tr>
					</thead>
					<tbody id="tbwid">
						

					
						<%
							int number = 1;
						%>
						<s:iterator value="pageForm.list">
							<tr class="td_bg01 saveAjax" id="${fpID}">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${fpID}" />
                                      
								     <input type="hidden" name="fpID" value="${fpID}" />
								</td>
								<td class="td_bg01">
									<span><%=number%></span>
								</td>
								<td class="td_bg01">
									<span id="year">${year}</span>
									<input type="hidden"  name="year" value="${year}" />
										<span id="oriyear"  style="display:none;">${year}</span>
								</td>
								<td class="td_bg01">
									<span>${fn:substring(startDate, 0, 10)}</span>
									<input type="hidden" name = "startDate" id="startDate" value="${fn:substring(startDate, 0, 10)}"
								/>
								</td>
								<td class="td_bg01">
									<span class="hid">${fn:substring(endDate, 0, 10)}</span>
									<input type="text" name="endDate" id="endDate" class="model1" value="${fn:substring(endDate, 0, 10)}"
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyyMM',  onpicked:function(dp){getstat($dp.cal.getDateStr(),this)}})"
								readonly/>
								</td>
								<td class="td_bg01">
									<span id="creatorName">${creatorName}</span>
								
							
									
								</td>
								<td class="td_bg01">
									<span id="createDate">${fn:substring(createDate, 0, 19)}</span>
									
								</td>
								<td class="td_bg01">
									<span id="updatorName">${updatorName}</span>
								
							
									
								</td>
								<td class="td_bg01">
								    <span id="updateDate">${fn:substring(updateDate, 0, 19)}</span>
									
								</td>
								<td class="td_bg01">
									<span id="companyName">${companyName}</span>
									

								</td>
							</tr>
							<%
								number++;
							%>
						</s:iterator>
						
							<tr id="sa"  style="display:none;" class="td_bg01 saveAjax model2">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${fpID}" />
							</td>
							<td class="td_bg01">
                                  &nbsp;
							</td>
							<td class="td_bg01">
							       <span id="year"></span>
                                   <input type = "hidden"  name="year"/>
                                   <span id="oriyear" style="display:none;">${year}</span>
							</td>
							<td class="td_bg01">
						
								<input type="text" name = "startDate" id="startDate" 
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyyMM', onpicked:function(){getend($dp.cal.getDateStr(),this)}})"
								readonly/>

							</td>
							<td class="td_bg01">

								<input type="text" name="endDate" id="endDate"
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyyMM', onpicked:function(dp){getstat($dp.cal.getDateStr(),this)}})"
								readonly/>
							</td>
							<td class="td_bg01">
								&nbsp;
							</td>
							<td class="td_bg01">
								&nbsp;
							</td>
							<td class="td_bg01">
                                &nbsp;
							</td>
							<td class="td_bg01">
                                &nbsp;
							</td>
							<td class="td_bg01">
                                &nbsp;
							</td>
							
							

						</tr>
					</tbody>
				</table>
				<c:import url="../../../../page_navigator.jsp">
					<c:param name="actionPath"
						value="ea/fisperiod/ea_getFiscalPeriodList.jspa?pageNumber=${pageNumber}&search=${search}">
					</c:param>
				</c:import>
			</div>
		</form>
		
		<iframe name="hidden"border="0"  height="0" frameborder="0"></iframe>
			
	
		
	</body>
</html>
