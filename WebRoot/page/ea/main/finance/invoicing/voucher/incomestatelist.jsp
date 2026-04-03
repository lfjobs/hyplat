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
		<title>资产负债计算设定</title>
		
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>/css/ea/staff.css" />
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/voucher/incomestatelist.js"></script>
		<script type="text/javascript">
		var tab = "${invCcpbsgl.tabSymbol}";
		 var  basePath="<%=basePath%>";           
         var  pNumber ="${pageNumber}";  
         var  search ="${search}";  
         var  fpID = "";
         var  token=0;
         var  select =1;
         var notoken=0;
          var ccpId = "";
         
	</script>


	</head>



	<body>

		<form name="fispriodForm" id="fispriodForm" method="post">
			<s:token></s:token>

			<input type="submit" name="submit" style="display:none" />



			<div id="main_main" class="main_main">
				<table class="fexlist">
					<thead>
						<tr>
							<th width="50" align="center">
								选择
							</th>
							<th width="35" align="center">
								序号
							</th>
				
							<th width="200" align="center">
								会计类别
							</th>
							<th width="250" align="center">
								会计科目（报表代号）
							</th>
							<th width="200" align="center">
								计算方式
							</th>
							

						</tr>
					</thead>
					<tbody id="tbwid">
						

					
						<%
							int number = 1;
						%>
						<s:iterator value="pageForm.list">
							<tr class="td_bg01 saveAjax" id="${ccpId}">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${ccpId}" />
                                      
								     <input type="hidden" name="ccpId" 
										value="${ccpId}" />
										
								</td>
								<td class="td_bg01">
									<span><%=number%></span>
								</td>
									<td>
							    
							    
							        <s:if test='plaType=="A"'>
							     
							       <input type="radio" class="fgnsize"  name="plaType<%=number%>" value="A" checked/>会计科目
							       <input type="radio" class="fgnsize" id="bbdh"  name="plaType<%=number%>"  value="B">报表代号
							       </s:if>
							       <s:else>
							       
							     
							        <input type="radio" class="fgnsize"  name="plaType<%=number%>" value="A" >会计科目
							        <input type="radio" class="fgnsize"  id="bbdh" name="plaType<%=number%>"  value="B" checked/>报表代号
							       </s:else>
							     
							 
							</td>
							<td>
						        <span class="hid">${stContents}</span>
							       <input type="text"  name="stContents" id="stContents" value="${stContents}" class="model1 stCom" />
							      

							</td>
							<td>
							
							
							 <s:if test='plaMode=="A"'>
                                   <input type="radio" class="fgnsize"  name="plaMode<%=number%>" value="A" checked>加
							       <input type="radio" class="fgnsize"  name="plaMode<%=number%>" value="B">减
							   </s:if>
							   <s:else>
							       <input type="radio" class="fgnsize"  name="plaMode<%=number%>" value="A">加
							       <input type="radio" class="fgnsize"  name="plaMode<%=number%>" value="B" checked>减
							   </s:else>
							     
								
							</td>
								
							</tr>
							<%
								number++;
							%>
						</s:iterator>
						
							<tr id="sa" style="display:none;" class="td_bg01 saveAjax model2">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									/>
							   <input type="hidden" name="tabSymbol" />
							</td>
							<td>
                                  &nbsp;
							</td>
							<td>
							     
							       <input type="radio" class="fgnsize" name="plaType" value="A" checked>会计科目
							       <input type="radio" class="fgnsize" id="bbdh" name="plaType"  value="B">报表代号
							    
							 
							</td>
							<td>
						     
							       <input type="text"  name="stContents" class="stCom" id="stContents"  />
							      

							</td>
							<td>
							
                                   <input type="radio"  class="fgnsize" name="plaMode" value="A" checked>加
							       <input type="radio" class="fgnsize" name="plaMode" value="B">减
							     
								
							</td>
							
							

						</tr>
					</tbody>
				</table>
				<c:import url="../../../../page_navigator.jsp">
					<c:param name="actionPath"
						value="ea/debtasset/ea_getIncomeList.jspa?pageNumber=${pageNumber}&search=${search}&invCcpbsgl.tabSymbol=${invCcpbsgl.tabSymbol}">
					</c:param>
				</c:import>
				<input type="hidden"  id="tabls"/>
			</div>
		</form>
		
		<iframe name="hidden" border="0"  height="0" frameborder="0"></iframe>
			

		
	</body>
</html>
