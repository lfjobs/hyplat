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
		<title>资产负债表内容设定</title>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>/css/ea/staff.css" />
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
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
			src="<%=basePath%>js/ea/finance/invoicing/voucher/assetsdebtlist.js"></script>
			<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<script type="text/javascript">
		 var  basePath="<%=basePath%>";           
         var  pNumber ="${pageNumber}";  
         var  search ="${search}";  
         var  fpID = "";
         var  token=0;
         var  select =1;
         var notoken=0;
         var tabSymbol = "";
         var tabPSymbol = "${tabPSymbol}";


         var tabType = "";
         if(tabPSymbol.length>1){
           tabType = tabPSymbol.substring(0,1);
         }else{
           tabType = tabPSymbol;
         }
         
      


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
							<th width="35" align="center">
								选择
							</th>
							<th width="90" align="center">
								报表代号
							</th>
							<th width="35" align="center">
								序号
							</th>
							<th width="100" align="center">
								报表说明
							</th>
							<th width="300" align="center">
								资产负债注记
							</th>
							
							<th width="150" align="center" class="zc">
								本期损益注记
							</th>
							
							


						</tr>
					</thead>
					<tbody id="tbwid">
						

					
						<%
							int number = 1;
						%>
						<s:iterator value="pageForm.list">
							<tr class="td_bg01 saveAjax" id="${tabSymbol}">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${tabSymbol}" />
                                      
								     <input type="hidden" name="ccId" value="${ccId}" />
								</td>
								<td class="td_bg01">
									<span class="hid" id="tabSymbol">${tabSymbol}</span>
									
									<span class="tabs"></span>&nbsp;

									<input type="text"  name="tabSymbol"  class="model1 notnull" value="${fn:substring(tabSymbol,1,100)}" />
								    <input type = "hidden"  name="tabType" id="tabType" value="${tabType}"/>
								</td>
								<td class="td_bg01">
								<span class="hid">${sequence}</span>
									<input type="text"  name="sequence"  class="model1  posnumred" value="${sequence}" />
								</td>
								<td class="td_bg01">
									<span class="hid">${tabSCaption}</span>
									<input type="text"  name="tabSCaption"  class="model1" value="${tabSCaption}" />
										
								</td>
								<td class="td_bg01">
									<s:if test='tabType=="A"'>
									<s:if test='bsAtion=="A"'> 
                                    <input type="radio"  class="fgnsize" name="bsAtion<%=number%>" value="A" checked/>资产类
                                  
                                   <input type="radio"  class="fgnsize" name="bsAtion<%=number%>" value="B"/>负债及所有者权益
                                    </s:if>
                                    <s:else>
                                       <input type="radio"  class="fgnsize" name="bsAtion<%=number%>" value="A" />资产类
                                      <input type="radio"  class="fgnsize" name="bsAtion<%=number%>" value="B" checked/>负债及所有者权益
                                    </s:else> 
                                    </s:if><s:else>
                                     <s:if test='bsAtion=="C"'>
                                    <input type="radio"  class="fgnsize cc" name="bsAtion<%=number%>" value="C" checked/>营业收入净值
                                    <input type="radio"  class="fgnsize dd" name="bsAtion<%=number%>" value="D"/>本期纯益
                                    <input type="radio"  class="fgnsize" name="bsAtion<%=number%>" value="E" />其他
                                    </s:if>
                                     <s:if test='bsAtion=="D"'>
                                    <input type="radio"  class="fgnsize cc" name="bsAtion<%=number%>" value="C" />营业收入净值
                                    <input type="radio"  class="fgnsize dd" name="bsAtion<%=number%>" value="D" checked/>本期纯益
                                    <input type="radio"  class="fgnsize" name="bsAtion<%=number%>" value="E" />其他
                                    </s:if>
                                     <s:if test='bsAtion=="E"'>
                                    <input type="radio"  class="fgnsize cc"   name="bsAtion<%=number%>" value="C" />营业收入净值
                                    <input type="radio"  class="fgnsize dd"   name="bsAtion<%=number%>" value="D"/>本期纯益
                                    <input type="radio"  class="fgnsize"    name="bsAtion<%=number%>" value="E" checked/>其他
                                    </s:if>
                                    </s:else>
                                  
								</td>
	 
								
								<td class="td_bg01 zc">
								 <div class="f">
								   <s:if test='cglAtion=="Y"'>
								      <input type="checkbox" class="fgnsize" name="cglAtion" value="Y" checked/>是否本期损益
								   </s:if>
								   <s:else>
								     <input type="checkbox"  class="fgnsize" name="cglAtion" value="Y"/>是否本期损益
								   </s:else>
                                    
                                  </div> 
								</td>
								
							</tr>
							<%
								number++;
							%>
						</s:iterator>
						
							<tr id="sa"  style="display:none;" class="td_bg01 saveAjax model2">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${ccId}" />
							</td>
							
							<td class="td_bg01">
							       <span id="tabSymbol"></span>
							        <span class="tabs"></span>&nbsp;
							       
                                    <input type = "text"  name="tabSymbol"  class="notnull" value=""/>
                                   <input type = "hidden"  name="tabPSymbol" value="${tabPSymbol}"/>
                                   <input type = "hidden"  name="tabType" id="tabType"/>
                                 
							</td>
							<td class="td_bg01">
                                  <input type="text"  name="sequence" class="posnumred"/>
							</td>
							<td class="td_bg01">
						
                                <input type="text"  name="tabSCaption"/>

							</td>
							<td class="td_bg01" align="center">
							      <div class="zc">
							    
                                   <input type="radio"  class="fgnsize" name="bsAtion" value="A" />资产类
                                   <input type="radio"  class="fgnsize" name="bsAtion" value="B"/>负债及所有者权益
                                  
                                 </div>
                                 <div class="sy">
                                   <input type="radio" class="fgnsize cc"  name="bsAtion" value="C" />营业收入净值
                                   <input type="radio" class="fgnsize dd"  name="bsAtion" value="D"/>本期纯益
                                   <input type="radio"  class="fgnsize" name="bsAtion" value="E" />其他
                                 </div>
								
							</td> 
							
							<td class="zc">
							
                                   <input type="checkbox"  class="fgnsize" name="cglAtion" value="Y"/>是否本期损益
                           
								
							</td>
							
							
							
							

						</tr>
					</tbody>
				</table>
				<c:import url="../../../../page_navigator.jsp">
					<c:param name="actionPath"
						value="ea/debtasset/ea_getDebtAssetsList.jspa?pageNumber=${pageNumber}&search=${search}&tabPSymbol=${tabPSymbol}">
					</c:param>
				</c:import>
			</div>
		</form>

		<iframe name="hidden" border="0"  height="0" frameborder="0" ></iframe>
		<script type="text/javascript">	
	$(document).ready(function() {

   if(tabType=="B"){
	    $(".zc").css({"display":"none"})
	    $(".sy").css({"display":"show"})
   }else{

	    $(".sy").css({"display":"none"})
	    $(".zc").css({"display":"show"})
   }
   });
   </script>
	</body>
</html>
