<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
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
		<title>预算管理</title>
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
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/costSheet_list.js"></script>
		
			<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
	type="text/css" />
			
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var cashierBillsID="";
        	var groupCompanySn = "${groupCompanySn}";
        	var pNumber="${pageNumber}";
        	var token=0;
        	var zhuangtai="${zhuangtai}"
        	
        	var jumptype="${jumptype}";
        	var billsType="${billsType}";
        	var zctype = "${zctype}";
        	var yg = "${param.yg}";
        	var statuscurr="";
			var fgtype = '${fgtype}';
     
        </script>
        <style type="text/css">
        
          .thtd{
         background-color:grey;
        }
      
        .qh_gg_nav{
        
        }
        .txt01{
        color:#1E5494;
font-size:12px;
font-weight:bolder;
line-height:22px
        }
        
        
        #jqModel {
	display: none;
	overflow: auto;
	border: 1px solid #a8c7ce;
	width: 300px;
	height: 350px;
	position: absolute;
	top: 31%;
	left: 25%;
	z-index: 999999;
	background-color: #e1ecfc;
	filter: Alpha(opacity = 100);
}

#jqModel a{

color:#0066CC;
}
        </style>
	</head>
	<body>
		<form name="CashierBillsform" id="CashierBillsform">
		<input type="hidden" name="jumptype" value="${jumptype }"/>
		<input type="hidden" name="billsType" value="${billsType }" />
		<input type="hidden" name="zctype" value="${zctype }"/>
		<input type="hidden" name="fgtype" value="${fgtype }"/>
		<input type="hidden" name="zhuangtai" value="${zhuangtai }"/>
		<input type="submit" name="submit" style="display: none" />
		<input type="hidden" name="cashierBills.cashierBillsID" id="paystatusID"/>
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
						项目编号
					</th>
					<th width="150" align="center">
						项目名称
					</th>
					<th width="150" align="center">
						项目分类
					</th>
					<th width="150" align="center">
						单据凭证号
					</th>
					<th width="150" align="center">
						单据类别
					</th>
					<th width="100" align="center">
						责任人部门
					</th>
					<th width="70" align="center">
						责任人
					</th>
					<th width="70" align="center">
						制单人
					</th>
					<th width="150" align="center">
						制单日期
					</th>
					<th width="90" align="center">
						单据状态
					</th>
					<th width="90" align="center">
						是否调整
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
							<span id="companyName">${companyName}</span>
						</td>
						<td>
							<span id="projectCode">${projectCode}</span>
						</td>
						<td>
							<span id="projectName">${projectName}</span>
						</td>
						<td>
							<span id="xmtypename">${xmtypename}</span>
						</td>
						<td>
							<span id="journalNum">${journalNum}</span>
						</td>
						<td>
							<span id="billsType">${billsType}</span>
						</td>
						<td>
							<span id="departmentName">${departmentName}</span>
						</td>
						<td>
							<span id="staffName">${staffName}</span>
						</td>
						<td>
							<span id="inputName">${inputName}</span>
						</td>
						<td>
							<span id="cashierDate">${fn:substring(cashierDate,0,19)}</span>
						</td>
						
						<td>
							<span id="paystatus" style="display:none;">${paystatus}</span>
						   <span id="status" style="display:none;">${status}</span>
						<s:if test="paystatus!=null">
							<s:if test="paystatus=='00'">项目未分配</s:if>
							<s:elseif test="paystatus=='01'">项目已分配未跟踪</s:elseif>
							<s:elseif test="paystatus=='02'">项目已跟踪未考评</s:elseif>
							<s:elseif test="paystatus=='03'">项目已考评</s:elseif>
						</s:if>
						<s:else>
								<s:if test="status=='00'">草稿</s:if>
								<s:elseif test="status=='01'">审核中-招标前</s:elseif>
								<s:elseif test="status=='02'">已通过-招标前</s:elseif>
								<s:elseif test="status=='03'">比价审核中</s:elseif>
								<s:elseif test="status=='04'">已提交资金申请</s:elseif>
								<s:elseif test="status=='05'">待会计审核</s:elseif>
								<s:elseif test="status=='06'">待出纳审核</s:elseif>
								<s:elseif test="status=='07'">已审核</s:elseif>
								<s:elseif test="status=='20'">税务单据</s:elseif>
								<s:elseif test="status=='08'">三审已归档</s:elseif>
								<s:elseif test="status=='09'">待确认收款</s:elseif>
								<s:elseif test="status=='40'">待确定预算收入单</s:elseif>
								<s:elseif test="status=='45'">已收款</s:elseif>
								<s:elseif test="status=='46'">系统生成</s:elseif>
								<s:elseif test="status=='11'">驳回待修改</s:elseif>
						</s:else>
						</td>
						<td>
						</td>
						
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/costsheet/ea_getCostSheetList.jspa?search=${search}&pageNumber=${pageNumber}&jumptype=${jumptype}&xmtype=${xmtype}&xmtypename=${xmtypename}&summary=${summary}&zctype=${zctype}&billsType=${billsType}&yg=${param.yg}">
			</c:param>
		</c:import>


		
		<iframe name="hidden" border="0" framespacing="0" height="0"></iframe>
		
	
		

	<!--选择审核人员窗口 -->
	<div class="jqmWindow"
		style="display: none; width: 450px; height: 250px; right: 20%; top: 10%;"
		id="jqModelSend">
		<div class="drag">报送审批</div>
		<center>
		<form  id="sendForm" name="sendForm" method="post">
		<input type="submit" name="submit" style="display:none;"/>
		<table width="100%" id="SearchTable2" cellspacing="20"
			cellpadding="20">
			<tr>
				<td align="right">审核人公司：</td>
				<td align="left"><select id="receiverCompanyID"
					name="cashierBills.examineComID" onchange="changeCompany(this);"
					style="width:200px;">
						<option value="">请选择审核人公司</option>
				</select>
				<input type="hidden"  id="examineComName"  name="cashierBills.examineComName"/>
				</td>
			</tr>
			<tr>
				<td align="right">审核人部门：</td>
				<td align="left"><select id="receiverDeptID"
					name="cashierBills.examineorgID" onchange="changeDept(this);"
					style="width: 200px;">
						<option value="">请选择审核人部门</option>
				</select>
				<input type="hidden"  id="examineorgName"  name="cashierBills.examineorgName"/>
				</td>
			</tr>
			<tr>
				<td align="right">审核人姓名：</td>
				<td align="left"><select name="cashierBills.examineID"
					id='receiverID' style="width: 200px;">
						<option value="">请选择审核人</option>
				</select>
				<input type="hidden"  id="examineName"  name="cashierBills.examineName"/>
				<input type="hidden"  id="examinecsbID"  name="cashierBills.cashierBillsID"/>
				</td>
			</tr>
		</table>
  
		<div align="center" style="margin-top: 25px;">
			<input type="button" class="input-button" id="submitResult" onclick="submitExamine()"
				value=" 提交 " /> <input type="button" class="input-button close"
				id="submitColsed"   value=" 关闭 " />
		</div>
		</form>
		</center>
	</div>
		
		
		
		<!-- 审核记录 -->
	<form method="post">
		<div class="jqmWindow" style="width: 600px;right: 25%;top:15%"
			id="jqModelCheck">
			<div class="drag">
				审核记录
				<div class="close"></div>
			</div>
			<center>
			<table width="500" id="CheckTable"  cellspacing='5px' cellpadding='5px'>
				<tr id="checkTitle">
					<th width="200" align="center">审核阶段</th>
					<th width="200" align="center">审核时间</th>
					<th width="100" align="center">审核人</th>
					
					<th width="123" align="center">审核状态</th>
					<th width="123" align="center">审核意见</th>
				</tr>
				
			</table>
			</center>
		</div>
	</form>
	
	
	  <!-- 查询项目分类 -->
	<div id="jqModel">
		<div id="treeBoxs"></div>
	</div>
	
	
	</body>
</html>
