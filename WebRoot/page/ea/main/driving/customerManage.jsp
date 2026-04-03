<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/driving/customerManage.js"></script>
<script type="text/javascript">
	var token=0;
    var select = 1;
    var customerManageID = '';
	var basePath='<%=basePath%>';           
    var pNumber ='${pageNumber}';  
    var notoken=0;
    var staffid = "${staffid}";
    var status1="${status1}";
    var search="${search}";
    var frameid = '<%=request.getParameter("frameid")%>';
	var mainheight = 0; //框架高度
	var ids = ''; //存放行ID
	
	function getValueForParm(id){ //打开页面
		ids = id;
	  	$("#ifr").attr("src",basePath+"ea/productdesign/ea_getListProductdesign.jspa?flexbutton=flexbutton");
	  	mainheught = parent.document.getElementById(frameid).offsetHeight;
	  	parent.document.getElementById(frameid).style.height = 330 + 'px';
	  	$("#jqmWindow2").jqmShow();
    }
	
	$(document).ready(function() {
		$("#isBack").click(function(){// 返回
	       $("#jqmWindow2").jqmHide();
	       $("#ifr").attr("src","");
	       parent.document.getElementById(frameid).style.height = mainheught + 'px';
		}); 
	   
		$("#isSubmit").click(function(){// 选择确定
			var value1 = window.frames["ifr"].productdesignID;//弹出框的页面必须声明productdesignID这个参数接收id
			if(value1 == ""){
				alert("请选择")
				return;
			}
			var value2 = window.frames["ifr"].$('tr#'+value1).find("span#goodsID").text();//弹出框的页面存在于span中才取得到
			var value3 = window.frames["ifr"].$('tr#'+value1).find("span#goodsName").text();//弹出框的页面存在于span中才取得到
			
			$("#"+ids).find("#goodsID").val(value2);
			$("#"+ids).find("#goodsName").val(value3);
			$("#ifr").attr("src","");
			parent.document.getElementById(frameid).style.height = mainheught + 'px';
	        $("#jqmWindow2").jqmHide();
	    });
	});
 </script>
</head>
<body>
	
		<form name="lForm" id="lForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div id="main_main" class="main_main" >
			<table class="staffappraisal">
				<thead>
					<tr>
						<th width="30" align="center">选择</th>
						<s:if test="status1 != '03'">
						<th width="70" align="center">起日期</th>
						<th width="70" align="center">止日期</th>
						</s:if>
						<th width="100" align="center">产品名称</th>
						<s:if test="status1 != '03'">
							<th width="110" align="center">
								<s:if test="status1 == '00'">客户分类</s:if>
								<s:elseif test="status1 == '01'">潜在客户分类</s:elseif>
								<s:else>客户来源分类</s:else>
							</th>
						</s:if>
						<s:else>
							<th width="90" align="center">产品兴趣分类</th>
						</s:else>
							<th width="200" align="center">客户详细备注</th>
					</tr>
				</thead>
				<tbody id="tbwid">
					<input type="hidden" id="start" />
					<input type="hidden" id="end" />
					<tr id="sa" style="display: none" class="td_bg01 saveAjax model2 ">
						<td><input type="radio" name="a" class="JQuerypersonvalue "
							value="${ccompanyID}" /></td>
						<s:if test="status1 != '03'">
							<td class="td_bg01"><input class="aaaa"
								name="startDate" id="startDate"
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'end\')}'})"
								size="7" />
							</td>
							<td class="td_bg01"><input class="aaaa"
								name="endDate" id="endDate"
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'start\')}'})"
								size="7" />
							</td>
						</s:if>
						<td class="td_bg01">
							<input type="hidden" name="goodsID" id="goodsID" />
							<input name="goodsName" id="goodsName" class="aaaa"/>
							<a href="#" class="aaaa" onclick="getValueForParm($(this).parent().parent().parent().attr('id'))">选择</a>
						</td>
						<td class="td_bg01">
							<s:if test="status1 != '03'">
								<s:select list="customersList" listKey="codeID" listValue="codeValue" 
									headerKey="" headerValue="请选择" name="codeID" id="codeID" 
									cssClass="selectedname" theme="simple">
								</s:select>
								<input type="hidden" name="codeName" id="codeName"/>
							</s:if>
							<s:else>
								<select name="interest" id="interest">
									<option value="">请选择</option>
									<option value="00">有兴趣</option>
									<option value="01">特别有兴趣</option>
									<option value="02">一般有兴趣</option>
								</select>
							</s:else>
							<input type="hidden" name="staffID" value="${staffid}" />
							<input type="hidden" name="status" value="${status1}" />
						</td>
						<td class="td_bg01"><input 
								name="customerRemarks" id="customerRemarks"
								/>
							</td>
					</tr>
					
					<s:iterator value="pageForm.list">
						<tr class="td_bg01 saveAjax trclass staffappraisal2 " id="${customerManageID}">
							<td><input type="radio" name="a" class="JQuerypersonvalue"
								value="${customerManageID}" /></td>
							<s:if test="status1 != '03'">
								<td class="td_bg01"><span id="startDate">${fn:substring(startDate,0,10)}</span>
									<input class="aaaa model1" name="startDate"
									id="startDate" value="${fn:substring(startDate,0,10)}"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'end\')}'})"
									size="7" />
								</td>
								<td class="td_bg01"><span id="endDate">${fn:substring(endDate,0,10)}</span>
									<input class="aaaa model1" name="endDate" id="endDate"
									value="${fn:substring(endDate,0,10)}"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'start\')}'})"
									size="7" />
								</td>
							</s:if>
							<td class="td_bg01">
								<span>${goodsName}</span>
								<input type="hidden" name="goodsID" value="${goodsID}" id="goodsID" />
								<input name="goodsName" value="${goodsName}" id="goodsName" class="aaaa model1"/>
								<a href="#" class="model1" onclick="getValueForParm($(this).parent().parent().parent().attr('id'))">选择</a>
							</td>
							<td class="td_bg01">
								<s:if test="status1 != '03'">
									<span>${codeName}</span>
									<s:select list="customersList" listKey="codeID" listValue="codeValue" 
										headerKey="" headerValue="请选择" name="codeID" id="codeID" 
										cssClass="model1 selectedname" theme="simple">
									</s:select>
									<input type="hidden" name="codeName" value="${codeName}" id="codeName"/>
								</s:if>
								<s:else>
									<span>
										<s:if test="interest == '00'">有兴趣</s:if>
										<s:elseif test="interest == '01'">特别有兴趣</s:elseif>
										<s:else>一般有兴趣</s:else>
									</span>
									<select name="interest" id="interest" class="model1">
										<option value="">请选择</option>
										<option value="00">有兴趣</option>
										<option value="01">特别有兴趣</option>
										<option value="02">一般有兴趣</option>
									</select>
								</s:else>
								<input type="hidden" name="customerManageKey" id="customerManageKey" value="${customerManageKey}" /> 
								<input type="hidden" name="customerManageID" id="customerManageID" value="${customerManageID}" />
								<input type="hidden" name="staffID" id="staffID" value="${staffID}" />
								<input type="hidden" name="status" id="status" value="${status}" />
							</td>
							<td class="td_bg01"><span id="customerRemarks">${customerRemarks}</span>
									<input class="model1" name="customerRemarks"
									id="customerRemarks" value="${customerRemarks}" />
								</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
				<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/customermanage/ea_getCustomerList.jspa?frameid=${param.frameid}&pageNumber=${pageNumber}&search=${search}&status1=${status1}&staffid=${staffid}"></c:param>
			</c:import>
			<s:token></s:token>
			</div>
		</form>
	
	<!--搜索窗口 -->
		<form name="SearchForm" id="SearchForm" method="post">
			<div class="jqmWindow" style="width: 300px; right: 25%; top: 10%"
				id="jqModelSearch">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="280" id="SearchTable">
					<tr>
						<td width="40%" align="right">
							产品名称：
						</td>
						<td style="width: 200px">
							<input name="customerManage.goodsName"/>
						</td>
					</tr>
					<s:if test="status1 != '03'">
					<tr>
						<td width="45%" align="right">
							<s:if test="status1 == '00'">客户分类：</s:if>
							<s:elseif test="status1 == '01'">潜在客户分类：</s:elseif>
							<s:else>客户来源分类：</s:else>
						</td>
						<td style="width: 200px">
							<s:select list="customersList" listKey="codeID" listValue="codeValue" 
								headerKey="" headerValue="请选择" name="customerManage.codeID" theme="simple">
							</s:select>
						</td>
					</tr>
					</s:if>
					<s:else>
					<tr>
						<td align="right">
							产品兴趣分类：
						</td>
						<td style="width: 200px">
							<select name="customerManage.interest">
								<option value="">请选择</option>
								<option value="00">有兴趣</option>
								<option value="01">特别有兴趣</option>
								<option value="02">一般有兴趣</option>
							</select>
						</td>
					</tr>
					</s:else>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		
		<!-- 从当前部门的员工中选择责任人 -->
	    <div id="jqmWindow2" class="jqmWindow"
			style="width: 98%; height: 320px; absolute; display: none; left: 1%; top: 1%; background: #eff">
			<div align="center">
				<iframe name="ifr" id="ifr" width="100%" height="280px"
				frameborder="0"></iframe>
				<input type="button" class="input-button" id="isSubmit" value=" 确定 "
					style="cursor: hand" />
				<input type="button" class="input-button" id="isBack" value=" 关闭 "
					style="cursor: hand" />
			</div>
		 </div>
		
		<script type="text/javascript">
    	$(function(){   
    		setTimeout(function(){ 			
    	  	    $("div.bDiv").css({"height":parent.document.getElementById(frameid).offsetHeight-98+"px"});
    	    },100);
    		 $(window).resize(function(){ 
   			      setTimeout(function(){ 			
   			    	  $("div.bDiv").css({"height":parent.document.getElementById(frameid).offsetHeight-98+"px"});
   			      },100);
    		 }); 
		});
		</script> 
	</body>
</html>