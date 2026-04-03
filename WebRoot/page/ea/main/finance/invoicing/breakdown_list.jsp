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
		<title>报损管理</title>
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
			src="<%=basePath%>js/ea/finance/invoicing/break_list.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jquery.messager.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var journalNum="";
        	var financialbillID="";
        	var financialgoodid="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
        	var notoken = 0;
        	var inventoryID='';
			document.onkeydown = function(evt){//捕捉回车 
   				evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    			var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    			if (key == 13) { //判断是否是回车事件。
    				if($("input#journalNum").val()==''){
						return false;
    				}
        			if($("input#journalNum").val()!=''){
			        	$("#SearchForm").attr("action", basePath+"/ea/warehousing/ea_toSearchWare.jspa?pageNumber="+pNumber+"&billStatus="+billStatus);
                    	document.SearchForm.submit.click();
					}
    			}
			}
        </script>
	</head>
	<body>
	
	
		<form name="breakdownform" id="breakdownform">
			<input type="submit" name="submit" style="display: none" />
			<s:token />
		</form>
		<table class="flexme11">
			<thead>
				<tr>
					<th width="30" align="center">
						选择
					</th>
					<th align="center" width="90">
						费用或品名名称
					</th>
					<th align="center" width="50">
						类型
					</th>
					<th align="center" width="75">
						品名编号
					</th>
					<th align="center" width="90">
						统一分类条码
					</th>
					<th align="center" width="50">
						报损数量
					</th>					
					<th align="center" width="50">
						报损单价
					</th>
					<th align="center" width="50">
						报损金额
					</th>
				
					<th align="center" width="75">
						物品状态
					</th>
					<th align="center" width="75">
						型号
					</th>
					<th align="center" width="75">
						单位
					</th>
				</tr>
			</thead>
			<tbody>	
				<c:forEach var='arr' items="${pageForm.list}">
					<tr id="${arr[0] }">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${arr[0]}" />
						</td>
						<td>
							<span id="goodsName">${arr[1]}</span>
						</td>
						<td>
							<span id="type">${arr[2]}</span>
						</td>
						<td>
							<span id="goodsNum">${arr[3]}</span>
						</td>
						<td>
							<span id="sortCode">${arr[4]}</span>
						</td>
						<td>
							<span id="quantity">${arr[5]}</span>
						</td>
						
						<td>
							<span id="unitPrice">${arr[6]}</span>
						</td>
						<td>
							<span id="amount">${arr[7]}</span>
						</td>
						<td>
							<span id="goodstatus" >${arr[8]}</span>
						</td>
						<td>
							<span id="goodstatus" >${arr[9]}</span>
						</td>
						<td>
							<span id="goodstatus" >${arr[10]}</span>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/break/ea_getbreakList.jspa?search=${search}&pageNumber=${pageNumber}">
			</c:param>
		</c:import>
	<div class="jqmWindow" style="width: 240px;margin-left: 400px;top:15%" id="jqModelSearch">
            <form name="SearchForm" id="SearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询窗口
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable" style="margin-left:20px;">
                    <tr align="center">
                        <td >
                           物品名称:</td>
                        <td >
                        <input name="inventoryParam.goodsName"/>
                        </td>
                    </tr>
                     <tr align="center">
                        <td >
                           品名编号:</td>
                        <td >
                        <input name="inventoryParam.goodsCoding"/>
                        </td>
                    </tr>
                </table>
                <div align="center"> 
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" value="search" type="hidden"/>
                </div>
            </form>
        </div>
			
		
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
