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
		<title>单据拆分</title>
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
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/splitbill_list.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script><%--
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		--%><script type="text/javascript"
			src="<%=basePath%>js/jquery.messager.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.4/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.4/themes/icon.css">
		<script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
		<style type="text/css">
			/*loading*/
#loadbg{z-index:1;left:400px;top:255px;position:fixed;background:#99ccff;width:400px;height:88px;opacity:.6}
#reloading{z-index:1;left:400px;top:255px;position:fixed;}
#reloading div{position:absolute;}
#loadfull,#loadpercent{height:28px;margin:30px 0 0 50px;}
#loadfull{background:white;width:300px;opacity:.7}
#loadpercent{background:#000;width:0px;opacity:.8}
#loadtext{font-size:14px;z-index:2;width:300px;color:#cc0033;padding:35px 0 5px 59px;}
		</style>
		<script type="text/javascript">
		    var accname="${ManStaffName}";
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
        	var  differenceStyle="${differenceStyle}";
        	var bill="${BType }";
        	var zz="${zz}";
        	var billsTypess="${billsType}";
        	var notoken = 0; 
        	var sztype = "${sztype}";
        	/* $(function() {
        		if(billsTypess=="[]"){
        			alert("无此类单据！不能执行操作！");
        			document.location.href=basePath+"page/ea/main/navigation/finace_c.jsp";
        		}
        	}) */

        	
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
	<body >
		<form name="CashierBillsform" id="CashierBillsform">

			<input type="submit" name="submit" style="display: none" />
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
					<th width="200" align="center">
						项目名称
					</th>
					<th width="150" align="center">
						黏贴单编号
					</th>
					<th width="80" align="center">
						单据类别
					</th>
					<th width="100" align="center">
						部门
					</th>
					<th width="60" align="center">
						责任人
					</th>
					<th width="60" align="center">
						付款方
					</th>
					<th width="60" align="center">
						制单人
					</th>
					<th width="80" align="center">
						制单日期
					</th>
					<th width="80" align="center">
						单据状态
					</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="pageForm.list">
					<tr id="${cashierBillsID}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${cashierBillsID}" />
						</td>
						   <td>
                                <span id="companyname">${companyname}</span>
                            </td>
                            <td>
                                <span id="projectName">${projectName}</span>
                            </td>
                            <td>
                                <span id="journalNum">${journalNum}</span>
                            </td>
                            <td>
                                <span id="billsType">${billsType}</span>
                            </td>
                            <td>
                                <span id="departmentname">${departmentname}</span>
                            </td>
                            <td>
                                <span id="staffname">${staffname}</span>
                            </td>
                            <td>
                                <span id="appropriationcompanyName">${appropriationcompanyName}</span>
                            </td>
                            <td>
                                <span id="inputName">${inputName}</span>
                            </td>
                              <td>
                                <span id="cashierDate">${fn:substring(cashierDate, 0, 10)}</span>
                            </td>
                            <td>
                               <span id="statuss">${status=='04'?"审核中":
                                status=='05'?"待会计审核":status=='06'?"待出纳审核":status=='07'?"已审核":
                                status=='20'?"税务单据":status=='30'?"未审核作废":status=='11'?"驳回待修改":status=='09'?"待确认收款":status=='40'?"待确定预算":status=='45'?"已收款":status=='46'?"系统生成":status==null?"":"驳回作废"}</span>
                                <input type="hidden" value="${status}" id="status"/>
                            </td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/splitbill/ea_getCashierBillsList.jspa?zz=${zz}&search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}">
			</c:param>
		</c:import>
		
		
		<!--搜索窗口 -->
		<!-- <form name="ExcelForm" id="ExcelForm" method="post">
			<div class="jqmWindow" style="width: 400px; right: 30%; top: 10%"
				id="jqModelExcel">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="SearchTable">
					
					<tr>
						<td align="right">
							报账日期：
						</td>
						<td style="width: 200px">
							<input id="sdates" name="sdate" onfocus="date(this);"
								style="width: 85px" />至<input id="edates" name="edate" onfocus="date(this);"
								style="width: 85px" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearchExcel"
						value=" 查询 "/>
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form> -->
		
		<div class="jqmWindow" style="width: 200px; right: 30%; top: 10%"
				id="jqModelpj">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
				票据打印
					<div class="close">
					</div>
				</div>
				<table width="180" id="SearchTable">
					
					<tr>
						<td align="right">
							打印选择：
						</td>
						<td style="width: 100px">
							<input type="radio" class="pj" name="pj" value="dp"/>大票
							<input type="radio" class="pj" name="pj" value="xp"/>小票
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="printvsk"
						value=" 打印预览 "/>
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		
		<!--单据类别 -->
		<form name="billForm" id="billForm" method="post">
			<div class="jqmWindow" style="width: 400px; right: 30%; top: 10%"
				id="jqModelbill">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
				单据类别
					<div class="close">
					</div>
				</div>
				<table width="396" id="SearchTable">
					
					<tr>
						<td align="right">
							单据类别：
						</td>
						<td style="width: 200px">
							<select id="selectbilltype">
							<option value="费用报销单">费用报销单</option>
							<option value="费用出/入库报销单">费用出/入库报销单</option>
							<option value="退/费货报销单据">退/费货报销单据</option>
							<option value="收款单">收款单</option>
							<option value="支款单">支款单</option>
							<option value="销售明细单">销售明细单</option>
							<option value="项目收入预算单">项目收入预算单</option>
							<option value="欠款单">欠款单</option>
							<option value="应收款单">应收款单</option>
							</select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="printprev"
						value=" 打印预览 "/>
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
	<!-- 审核记录 -->
	<form method="post">
		<div class="jqmWindow" style="width: 600px;right: 25%;top:15%"
			id="jqModelCheck">
			<div class="drag">
				审核记录
				<div class="close"></div>
			</div>
			<table width="596" id="CheckTable"  cellspacing='5px' cellpadding='5px'>
			</table>
		</div>
	</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </script>
	</body>
</html>
