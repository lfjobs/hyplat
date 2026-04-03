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
		%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />

		<title>税务单据待经理审核</title>
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
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jquery.messager.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/finance/manager_list.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var treeNames="<%=session.getAttribute("organizationName")%>";
        	var cashierBillsID="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
        	var status="";
        	var sdate="${sdate}";
        	var edate="${edate}";
        	var tsdate="${tsdate}";
        	var tedate="${tedate}";
        	var notoken = 0;
        	var taxstatusDu="${taxstatusDu}";
        	
        	var differenceStyle="${differenceStyle}";
        	var bill="${BType }";
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
	<form name="Managerform" id="Managerform">
			<input type="submit" name="submit" style="display: none" />
			<input name="cashierBills.taxstatus" id="taxstatus" style="display: none" />
			<input name="cashierBills.cashierBillsID" id="cashierBillsID"
				style="display: none" />
			<s:token />
		</form>
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
						单据类别
					</th>
					<th width="100" align="center">
						部门
					</th>
					<th width="80" align="center">
						责任人
					</th>
					<th width="80" align="center">
						制单人
					</th>
					<th width="90" align="center">
						制单日期
					</th>
					<th width="80" align="center">
						实物仓库
					</th>
					<th width="80" align="center">
						财务仓库
					</th>
					<th width="80" align="center">
						资料仓库
					</th>
					<th width="90" align="center">
						往来单位
					</th>
					<th width="90" align="center">
						单位银行账号
					</th>
					<th width="90" align="center">
						单位往来关系
					</th>
					<th width="90" align="center">
						往来个人
					</th>
					<th width="90" align="center">
						个人银行账号
					</th>
					<th width="90" align="center">
						个人往来关系
					</th>
					<th width="90" align="center">
						报税时间
					</th>
					<th width="90" align="center">
						税务单据主管审核人
					</th>
					<th width="90" align="center">
						税务单据经理审核人
					</th>
					<th width="90" align="center">
						税务单据财务审核人
					</th>
					<th width="90" align="center">
						税务状态
					</th>
					<th width="90" align="center">
						附件管理
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
							<span id="staffname">${arr[5]}</span>
						</td>
						<td>
							<span id="staffname">${arr[6]}</span>
						</td>
						<td>
							<span id="cashierDate">${fn:substring(arr[7], 0, 10)}</span>
						</td>
						<td>
							<span id="afterDiscount">${arr[8]}</span>
						</td>
						<td>
							<span id="bankDepotName">${arr[9]}</span>
						</td>
						<td>
							<span id="dataDepotName">${arr[10]}</span>
						</td>
						<td>
							<span id="ccompanyname">${arr[11]}</span>
						</td>
						<td>
							<span id="accountNum">${arr[12]}</span>
						</td>
						<td>
							<span id="contactConnections">${arr[13]}</span>
						</td>
						<td>
							<span id="contactUserName">${arr[14]}</span>
						</td>
						<td>
							<span id="userAccountNum">${arr[15]}</span>
						</td>
						<td>
							<span id="phone">${arr[16]}</span>
						</td>
						<td>
							<span id="taxDate">${arr[17]}</span>
						</td>
						<td>
							<span id="competent">${arr[18]}</span>
						</td>
						<td>
							<span id="manager">${arr[19]}</span>
						</td>
						<td>
							<span id="financial">${arr[20]}</span>
						</td>
						<td>
							<span id="taxstatus">${arr[21]}</span>
							<input id="taxstatusd" value="${arr[21]}" style="display: none;"/>
						</td>
						<td>
							<a href="#" onclick="fj('${arr[0]}')" class="fj">附件</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	<c:import url="../../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/manager/ea_getDutiableList.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}&tsdate=${tsdate}&tedate=${tedate}&taxstatusDu=${taxstatusDu}">
			</c:param>
		</c:import>
		
	
		<!--搜索窗口 -->
		<form name="SearchForm" id="SearchForm" method="post">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelSearch">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="SearchTable">
					<tr>
						<td width="123" align="right">
							单据类别：
						</td>
						<td><select name="cashierBillsVO.billsType" id="btype"></select>
						</td>
					</tr>
					<tr>
						<td align="right">
							黏贴单编号：
						</td>
						<td>
							<input id="journalNum" style="width: 195px"
								name="cashierBillsVO.journalNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							部门：
						</td>
						<td>
							<select name="cashierBillsVO.departmentID" style="width: 200px"
								id="departmentID">
								<option value="">
									请选择部门
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td>
							<s:select list="%{#request.stafflist}" style="width:200px"
								headerKey="" headerValue="请选择" listKey="staffID"
								name="cashierBillsVO.staffID" listValue="staffName"
								theme="simple">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							制单日期：
						</td>
						<td style="width: 200px">
							<input id="sdate" name="sdate" onfocus="date(this);"
								style="width: 85px" />至	<input id="edate" name="edate" onfocus="date(this);"
								style="width: 85px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							报税日期：
						</td>
						<td style="width: 200px">
							<input id="sdate" name="tsdate" onfocus="date(this);"
								style="width: 85px" />至	<input id="edate" name="tedate" onfocus="date(this);"
								style="width: 85px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							往来单位：
						</td>
						<td>
							<input id="ccID" style="width: 195px"
								name="cashierBillsVO.ccompanyname" />
						</td>
					</tr>
					<tr>
						<td align="right">
							单位往来关系：
						</td>
						<td>
							<s:select list="%{#request.connectionlist}" style="width:200px"
								headerKey="" headerValue="请选择" listKey="codeValue"
								name="cashierBillsVO.contactConnections" listValue="codeValue"
								theme="simple">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							往来个人：
						</td>
						<td>
							<input id="cuID" style="width: 195px"
								name="cashierBillsVO.contactUserName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							个人往来关系：
						</td>
						<td>
							<s:select list="%{#request.codeRelationList}" style="width:200px"
								headerKey="" headerValue="请选择" listKey="codeValue"
								name="cashierBillsVO.phone" listValue="codeValue" theme="simple">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							查询税务单据状态：
						</td>
						<td>
					    	<select id="taxstatus" style="width:200px"  name="taxstatusDu">
					    	<option value="2" selected="selected" >待经理审核</option>
					    	<option value="taxstatusDu">历史单据</option>
					    	</select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>

		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
