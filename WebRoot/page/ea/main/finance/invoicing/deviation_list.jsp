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
		<meta http-equiv="cache-control" content="no-cache" />
		<title>验货误差管理</title>
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
		
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/deviation_list.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jquery.messager.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var financialbillID="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
        	var notoken = 0;
        	var inspectmap="";
        	var select=0;
        	var sdate="${sdate}";
        	var edate="${edate}";
			
			document.onkeydown = function(evt){//捕捉回车 
   				evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    			var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    			if (key == 13) { //判断是否是回车事件。
    				if($("input#journalNum").val()==''){
						return false;
    				}
        			if($("input#journalNum").val()!=''){
			        	$("#SearchForm").attr("action", basePath+"ea/dutiable/ea_toSearch.jspa?pageNumber="+pNumber+"&taxstatusDu="+taxstatusDu);
                    	document.SearchForm.submit.click();
					}
    			}
			}      
        </script>
	</head>
	<body>
		<form name="Inspectform" id="Inspectform">
			<input type="submit" name="submit" style="display: none" />
			<s:token />
		<table class="flexme11" id="tt">
			<thead>
				<tr>
					<th width="30" align="center">
						选择
					</th>
					<th width="170" align="center">
						公司名称
					</th>
					<th width="150" align="center">
						凭证号
					</th>
					<th width="70" align="center">
						单据类型
					</th>
					<th width="80" align="center">
						部门
					</th>
					<th width="60" align="center">
						责任人
					</th>
					<th width="80" align="center">
						制单日期
					</th>
					<th align="center" width="70">
						品名编号
					</th>
					<th align="center" width="90">
						统一分类条码
					</th>
					<th align="center" width="70">
						品名名称
					</th>
					<th align="center" width="50">
						类型
					</th>
					<th align="center" width="20">
						品牌
					</th>
					<th align="center" width="20">
						型号
					</th>
					<th align="center" width="20">
						单位
					</th>
					<th align="center" width="50">
						合格数量
					</th>
					<th align="center" width="50">
						收货数量
					</th>
					<th align="center" width="50">
						采购数量
					</th>					
					<th align="center" width="50">
						采购单价
					</th>
					<th align="center" width="50">
						采购金额
					</th>
					<th align="center" width="50">
						付款方式
					</th>
					<th align="center" width="50">
						物流方式
					</th>
					<th align="center" width="100">
						提醒内容
					</th>
					<th width="170" align="center">
						往来单位
					</th>					
					<th width="60" align="center">
						单位往来关系
					</th>
					<th width="60" align="center">
						往来个人
					</th>
					<th width="60" align="center">
						个人往来关系
					</th>
				</tr>
			</thead>
			<tbody>			
				<c:forEach var='arr' items="${pageForm.list}">
					<tr id="${arr[0] }" class="xggoods">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${arr[0]}" />
						</td>
						<td>
							<span id="companyname">${arr[1]}</span>
						</td>
						<td>
							<span id="journalNum">${arr[2]}</span>
						</td>
						<td>
							<span id="billsType">${arr[3]}</span>
						</td>
						<td>
							<span id="departmentname">${arr[4]}</span>
						</td>
						<td>
							<span id="StaffName">${arr[5]}</span>
						</td>
						<td>
							<span id="billsDate">${arr[6]}</span>
						</td>
						<td>
							<span id="goodsNum">${arr[7]}</span>
						</td>
						<td>
							<span id="sortCode">${arr[8]}</span>
						</td>
						<td>
							<span id="goodsName">${arr[9]}</span>
						</td>
						<td>
							<span id="type">${arr[10]}</span>
						</td>
						<td>
							<span id="brand">${arr[11]}</span>
						</td>
						<td>
							<span name="modelNumber">${arr[12]}</span>
						</td>
						<td>
							<span name="requantity">${arr[13]}</span>
						</td>
						<td>
							<span name="isQualify">${arr[14]}</span>
						</td>
						<td>
							<span name="unit">${arr[15]}</span>
						</td>
						<td>
							<span name="quantity">${arr[16]}</span>
						</td>
						<td>
							<span name="unitPrice">${arr[17]}</span>
						</td>
						<td>
							<span name="amount">${arr[18]}</span>
						</td>
						<td>
							<span name="paymentType">${arr[19]}</span>
						</td>
						<td>
							<span name="logisticsType">${arr[20]}</span>
						</td>
						<td>
							<span name="remindContent">${arr[21]}</span>
						</td>
						<td>
							<span name="ccompanyName">${arr[22]}</span>
						</td>
						<td>
							<span name="">${arr[23]}</span>
						</td>
						<td>
							<span name="cstaffName">${arr[24]}</span>
						</td>
						<td>
							<span name="cstaffRelationship">${arr[25]}</span>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</form>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/purchase/ea_getDeviationList.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}">
			</c:param>
		</c:import>
		
		<!-- 搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 30%; top: 10%" id="jqModelSearch2">
            <form name="postSearchForm2" id="postSearchForm2" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询窗口
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr align="right" width="90px">
                        <td >
                           物品名称：</td>
                        <td >
                        <input name="financialBillVO.goodsName" style="width: 195px"/>
                        </td>
                    </tr>
					<tr align="right" width="90px">
                        <td>
                           物品类别：</td>
                        <td>
                        <input name="financialBillVO.type" style="width: 195px"/>
                        </td>
                    </tr>
                    <tr >
						<td align="right" width="90px">
							黏贴单编号：
						</td>
						<td>
							<input id="journalNum" style="width: 195px"
								name="financialBillVO.journalNum" />
						</td>
					</tr>
					<tr>
						<td align="right" width="90px">
							责任人：
						</td>
						<td>
							<s:select list="%{#request.stafflist}" style="width:200px"
								headerKey="" headerValue="请选择" listKey="staffID"
								name="financialBillVO.staffID" listValue="staffName"
								theme="simple">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right" width="90px">
							单位往来关系：
						</td>
						<td>
							<s:select list="%{#request.connectionlist}" style="width:200px"
								headerKey="" headerValue="请选择" listKey="codeValue"
								name="financialBillVO.ccompanyRelationship" listValue="codeValue"
								theme="simple">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right" width="90px">
							个人往来关系：
						</td>
						<td>
							<s:select list="%{#request.codeRelationList}" style="width:200px"
								headerKey="" headerValue="请选择" listKey="codeValue"
								name="financialBillVO.cstaffRelationship" listValue="codeValue" theme="simple">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right" width="90px">
							制单日期：
						</td>
						<td style="width: 200px">
							<input id="sdate" name="sdate" onfocus="date(this);"
								style="width: 87px" />至<input id="edate" name="edate" onfocus="date(this);"
								style="width: 87px" />
						</td>
					</tr>
					<tr>
						<td align="right" width="90px">
							往来单位：
						</td>
						<td>
							<input id="sdate" name="financialBillVO.ccompanyName"
								style="width: 195px"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="90px">
							往来个人：
						</td>
						<td>
							<input id="sdate" name="financialBillVO.cstaffName"
								style="width: 195px"/>
						</td>
					</tr>
					<tr>

                </table>
                <div align="center"> 
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" value="search" type="hidden"/>
                </div>
            </form>
        </div>

		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
