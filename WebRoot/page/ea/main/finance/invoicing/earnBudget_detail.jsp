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
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>预算明细管理</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script  type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/earnBudget_detail.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

		<script type="text/javascript">
       		var treeID = '<%=session.getAttribute("organizationID")%>';
       		var treeNames="<%=session.getAttribute("organizationName")%>";
        	var ebbID="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
        	var status="";
        	var type="${type}";
        	var sztype="${sztype}";
        	
        	
        	document.onkeydown = function(evt){//捕捉回车 
   				evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    			var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    			if (key == 13) { //判断是否是回车事件。
        			$("form#SearchForm").find("input#journalNum").each(function(){
					if($(this).val()==""){
						return false;
					}else{
						$("#SearchForm").attr("action", basePath+"/ea/cashierbills/ea_toSearch.jspa?pageNumber="+pNumber);
                    	document.SearchForm.submit.click();
					}					
				});
    			}
			}
        </script>
	</head>
	<body>
		<table class="flexme11">
			<thead>
				<tr>
					<th width="30" align="center">
						选择
					</th>
					<th width="200" align="center">
						公司名称
					</th>
					<th width="150" align="center">
						黏贴单编号
					</th>
					<th width="100" align="center">
						部门
					</th>
					<th width="120" align="center">
						责任人
					</th>
					<th width="140" align="center">
						制单日期
					</th>
					<th width="90" align="center">
						预算年份
					</th>
					<th width="170" align="center">
						预算名称
					</th>
					<th width="180" align="center">
						产品名称
					</th>
					<th width="130" align="center">
						产品编号
					</th>
					<th width="90" align="center">
						产品单价
					</th>
					<th width="90" align="center">
						月预算个数
					</th>
					<th width="90" align="center">
						日预算金额
					</th>
					<th width="90" align="center">
						周预算金额
					</th>
					<th width="130" align="center">
					    月预算金额
					</th>
					<th width="90" align="center">
						季度预算金额
					</th>
					<th width="90" align="center">
						年预算金额
					</th>
					
					<th width="90" align="center">
						月调整个数
					</th>
					<th width="90" align="center">
						日调整金额
					</th>
					<th width="90" align="center">
						周调整金额
					</th>
					<th width="130" align="center">
					    月调整金额
					</th>
					<th width="90" align="center">
						季度调整金额
					</th>
					<th width="90" align="center">
						年调整金额
					</th>
					<th width="90" align="center">
						状态
					</th>
	

				</tr>
			</thead>
			<tbody>
				<s:iterator value="pageForm.list">
					<tr id="${ebbID }">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${ebbID}" />
						</td>
						<td>
							<span id="companyname">${companyname}</span>
						</td>
						<td>
							<span id="billnum">${billnum}</span>
						</td>
						<td>
							<span id="organizationname">${organizationname}</span>
						</td>
						<td>
							<span id="staffname">${staffname}</span>
						</td>
						<td>
							<span id="billsdate">${fn:substring(billsdate,0,19)}</span>
						</td>
						<td>
							<span id="year">${year}</span>
						</td>
						<td>
							<span id="budgetname">${budgetname}</span>
						</td>
						<td>
							<span id="productname">${productname}</span>
						</td>
						<td>
							<span id="productnum">${productnum}</span>
						</td>
						<td>
							<span id="bunitprice">${bunitprice}</span>
						</td>
						<td>
							<span id="bdquantity">${bdquantity}</span>
						</td>
					    <td>
							<span id="bdamount">${bdamount}</span>
						</td>
						<td>
							<span id="bwamount">${bwamount}</span>
						</td>
						<td>
							<span id="bdamount">${bmamount}</span>
						</td>
						<td>
							<span id="bwamount">${bsamount}</span>
						</td>
							<td>
							<span id="bdamount">${byamount}</span>
						</td>
						<td>
							<span id="bwamount">${tdamount}</span>
						</td>
							<td>
							<span id="bdamount">${twamount}</span>
						</td>
						<td>
							<span id="bwamount">${tmamount}</span>
						</td>
							<td>
							<span id="bwamount">${tsamount}</span>
						</td>
							<td>
							<span id="bdamount">${tyamount}</span>
						</td>
					
						<td>
							<span id="billstatus" style="display:none;">${billstatus}</span>
							<s:if test="billstatus==01">
							   已确认预算
							</s:if>
							<s:else>草稿</s:else>
						</td>

					</tr>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/earnbudget/ea_getEarnBudgetDetails.jspa?search=${search}&pageNumber=${pageNumber}&type=${type}&sztype=${sztype}">
			</c:param>
		</c:import>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>

		
		
	<div class="jqmWindow" style="width: 400px; right: 30%; top: 10%" id="jqModelSearch">
		<form name="SearchForm" id="SearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<input name="type" style="display: none" value="${type}"/>
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396px"  >
					<tr>
						<td align="right" width="80px">
							黏贴单编号：
						</td>
						<td>
							<input id="billNum" style="width: 195px"
								name="viewInvEbd.billnum" />
						</td>
					</tr>
					<tr>
						<td align="right" width="80px">
							责任人：
						</td>
						<td>
						<s:select list="%{#request.staffList}" style="width:200px"
								headerKey="" headerValue="请选择" listKey="staffID"
								name="viewInvEbd.staffid" listValue="staffName"
								theme="simple">
							</s:select>
						</td>
					</tr>
					
					<tr>
						<td align="right" width="80px">
							制单日期：
						</td>
						<td style="width: 200px">
							<input type="text"  id="sdate"
								class="input" name="viewInvEbd.sdate" style="width:85px;"
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm:ss', onpicked:function(){sdate.focus();}})"
								readonly /> 至<input type="text" name="viewInvEbd.edate" id="edate" style="width:85px;"
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'sdate\')}'})"
								readonly />
						</td>
					</tr>
					
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 "/>
					<input name="search" type="hidden" value="search" />
					<input name="sztype" type="hidden" value="${sztype}" />
					<input name="type" type="hidden" value="${type}" />
				</div>
				</form>
			</div>
			
			
	</body>
</html>
