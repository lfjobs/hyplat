<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>采购入库审核页面</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/sourcing/sourcingStorage_print.js"></script>	
	<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js" type="text/javascript"></script>
	<link rel="stylesheet" href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
	<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
	<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/admin_main111.css">
	<style type="text/css">
		span{
			display:-moz-inline-box;
			display:inline-block;
		}
	</style>
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var orgId="${organizationID}";
		var search2=0;
		var staffId="";
		var i = 0,r=0,irr=0;
		var status="${c.status}";
	</script>
  </head>
  
  <body>
  <form method="post" id="form" name="form" >
  <input type="submit" id="submit" name="submit" style="display: none;">
  <iframe name="hidden"  style="display: none;"></iframe>
    <center>
    	<div style="border: 1px solid #999999;width: 60%;height: 70%;margin-top: 4%;">
    		<div style="background-color: #009DD9;width:100%;height:8%;font-size: 30px;color:#fff;padding-top: 2%;">采购入库单</div>
    		<div style="width: 100%;height:20%;">
    			<table style="width: 100%;height: 100%;">
    				<tr style="height: 25%;">
    					<td style="width: 32%;">
    						<span style="width: 80px; text-align: right;">所&nbsp;属&nbsp;公&nbsp;司：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${c.companyName}"></span>
    					</td>
    					<td style="width: 32%;">
    						<input type="hidden" id="cashierBillsID" value="${c.cashierBillsID}" name="cashierbills.cashierBillsID">
    						<span  style="width: 80px; text-align: right;">单&nbsp;据&nbsp;编&nbsp;号：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${c.journalNum}"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${c.departmentName}"></span>
    					</td>
    				</tr>
    				<tr style="height: 25%;">
    					<td>
    						<span  style="width: 80px; text-align: right;">采&nbsp;&nbsp;&nbsp;购&nbsp;&nbsp;&nbsp;员：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${f.staffName}"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">采&nbsp;购&nbsp;日&nbsp;期：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${f.purchaseDate}"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">仓&nbsp;库&nbsp; 名&nbsp;称：</span>
    						<span>
    							<input type="text" class="inputbottom" style="width: 180px;" id="depotName" name="financialBill.depotName" readonly="readonly" value="${f.depotName}">
    						</span>
    					</td>
    				</tr>
    				<tr style="height: 25%;">
    					<td>
    						<span  style="width: 80px; text-align: right;">验&nbsp;&nbsp;&nbsp;货&nbsp;&nbsp;&nbsp;人：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${f.staffsName}"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">收&nbsp;&nbsp;&nbsp;货&nbsp;&nbsp;&nbsp;人：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;"  readonly="readonly" value="${f.consigneeName}"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">收&nbsp;货&nbsp; 日&nbsp;期：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" value="${f.goodsReceiptDate}" readonly="readonly"></span>
    					</td>
    				</tr>
    				<tr style="height: 25%;">
    					<td>
    						<span  style="width: 80px; text-align: right;">入&nbsp;库&nbsp;日&nbsp;期：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" name="financialBill.purchaseDate" id="cashierDate" readonly="readonly" value="${f.purchaseDate}"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">责&nbsp;&nbsp;&nbsp;任&nbsp;&nbsp;&nbsp;人：</span>
    						<span>
    							<input type="text" class="inputbottom" id="staffsName" name="cashierbills.staffName" style="width: 180px;" readonly="readonly" value="${c.staffName}">
    						</span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">选&nbsp;择&nbsp; 科&nbsp;目：</span>
    						<span>
    							<input type="text" class="inputbottom" id="tosubjects" style="width: 180px;" name="financialBill.subjectName" readonly="readonly" value="${f.subjectName}">
    						</span>
    					</td>
    				</tr>
    			</table>
    		</div>	<hr color="#DADCDB"/>
    		<div style="height: 45%;width: 100%;" class="tableSample">
    			<table cellpadding="0" cellspacing="0" class="table"  style="width: 80%;float: left;">
	    			<thead>
	    				<tr height="30">
	    					<th><span style="width: 30px;overflow: hidden;">序号</span></th>
	    					<th><span style="width:80px;overflow: hidden;">物品编号</span></th>
	    					<th><span style="width: 73px;overflow: hidden;">物品名称</span></th>
	    					<th><span style="width: 50px;overflow: hidden;">收货数量</span></th>
	    					<th><span style="width: 50px;overflow: hidden;">入库数量</span></th>
	    					<th><span style="width: 40px;overflow: hidden;">单价</span></th>
	    					<th><span style="width: 50px;overflow: hidden;">总金额</span></th>
	    					<th><span style="width: 40px;overflow: hidden;">类别</span></th>
	    					<th><span style="width: 30px;overflow: hidden;">单位</span></th>
	    					<th><span style="width: 45px;overflow: hidden;">规格</span></th>
	    					<th><span style="width: 50px;overflow: hidden;">物流方式</span></th>
	    					<th><span style="width: 100px;overflow: hidden;">供应商</span></th>
	    					<th><span style="width: 91px;overflow: hidden;">供应商负责人</span></th>
	    					<th><span style="width: 78px;overflow: hidden;">负责人电话</span></th>
	    					<th><span style="width: 41px;overflow: hidden;">备注</span></th>
	    				</tr>
	    				</thead>
    				</table>
    				<div style="height: 90%;width: 100%;overflow: hidden;position: relative;top: -5.5px" class="tableOuter">
	    				<div style="height: 95%;overflow-y: auto;" class="tableInner">
		    				<table  cellpadding="0" cellspacing="0" class="table" style="float: left;">
			    				<tbody>
			    					<c:forEach items="${goodsList}" var="list" varStatus="a">
			    						<tr>
			    							<td>
			    								<input type="hidden" name="goodsBillsList[${a.index}].goodsBillsID" value="${list.goodsBillsID}">
			    								<span style="width: 30px;overflow: hidden;">${a.index+1}</span>
			    							</td>
			    							<td><span style="width: 80px;overflow: hidden;">${list.goodsNum}</span></td>
			    							<td><span style="width: 73px;overflow: hidden;">${list.goodsName}</span></td>
			    							<td><span style="width: 50px;overflow: hidden;">${list.isQualify}</span></td>
			    							<td><input type="text" style="border: 0px;width: 50px;" name="goodsBillsList[${a.index}].reQuantity" value="${list.isQualify}" readonly="readonly"></td>
			    							<td><span style="width: 40px;overflow: hidden;">${list.price}</span></td>
			    							<td><span style="width: 50px;overflow: hidden;">${list.money}</span></td>
			    							<td><span style="width: 40px;overflow: hidden;">${list.typeID}</span></td>
			    							<td><span style="width: 30px;overflow: hidden;">${list.goodsVariableID}</span></td>
			    							<td><span style="width: 45px;overflow: hidden;">${list.standard}</span></td>
			    							<td><span style="width: 50px;overflow: hidden;">${list.logistics}</span></td>
			    							<td><span style="width: 100px;overflow: hidden;">${list.supplier}</span></td>
			    							<td><span style="width: 91px;overflow: hidden;">${list.supplierStaffName}</span></td>
			    							<td><span style="width: 78px;overflow: hidden;">${list.supplierStaffTelephone}</span></td>
			    							<td><span style="width: 41px;overflow: hidden;">${list.remark}</span></td>
			    						</tr>
			    					</c:forEach>
							</tbody>
		    			</table>
	    			</div>
	    		</div>
    		</div>
    		<hr color="#DADCDB"/>
    		<div style="width: 100%;height:6%;">
    			<span style="float: left;font-size: 14px;">
    				<span>制单人：</span>
    				<span><input type="text" class="inputbottom" style="width: 150px;" readonly="readonly" value="${f.billStaffName}"></span>
    			</span>
    			<span style="float: right;font-size: 14px;">
    				<span>制单时间：</span>
    				<span><input type="text" class="inputbottom" style="width: 150px;" readonly="readonly" value="${f.billsDate}"></span>
    			</span>
    		</div>
    		<div class="display">
    			<span><input type="button" value="提交审核" style="width: 100px;height: 30px;" class="operation"></span>
    			<span style="width: 100px;"></span>
    			<span><input type="button" value="关   闭" style="width: 100px;height: 30px;" class="operation"></span>
    		</div> 
    	</div>
    </center>
  <s:token></s:token>
  </form>
	<table width="99%" border="0" style="margin-left: 15px;" align="center"
		cellpadding="0" cellspacing="0" class="examine">
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td align="left" width="50px">备注：</td>
			<td align="left" colspan="9"><input type="text" id="remark" value="${str}"
				class="inputbottom" style="width:80%;" readonly="readonly" /></td>
		</tr>
	</table>
	<table width="99%" border="0" cellpadding="0" cellspacing="0"
		id="audittbl" class="examine">
		<tr>
			<td><input type="hidden" id="staffauditname"
				value="${ManStaffName}"> <input type="hidden"
				id="staffauditcode" value="${ManStaffCode}"> <input
				type="hidden" id="staffauditid" value="${ManStaffId}"></td>
		</tr>
		<tr class="aduittr">
			<td height="25" align="right">公司经理：</td>
			<td><input type="text" class="inputbottom gsjl"
				value='${billcheckmap["gsjl"]}    ' /> <c:if
					test='${billcheckmap["gsjl"]==null||billcheckmap["gsjl"]==""}'>
					<input type="button" class="btncon verify" id="gsjl" />
				</c:if> <c:if
					test='${billcheckmap["gsjl"]!=null&&billcheckmap["gsjl"]!=""}'>
${billcheckmap["gsjlzt"]==02?"通过":"驳回"} 
</c:if>
			<td align="right">部门主管：</td>
			<td><input type="text" class="inputbottom bmzg"
				value='${billcheckmap["bmzg"] }    ' /> <c:if
					test='${billcheckmap["bmzg"]==null||billcheckmap["bmzg"]==""}'>
					<input type="button" class="btncon verify" id="bmzg" />
				</c:if> <c:if
					test='${billcheckmap["bmzg"]!=null&&billcheckmap["bmzg"]!=""}'>		
${billcheckmap["bmzgzt"]==02?"通过":"驳回"}
</c:if></td>
			<td align="right">人事处：</td>
			<td><input type="text" class="inputbottom rsc"
				value="${billcheckmap['rsc'] }" /> <c:if
					test='${billcheckmap["rsc"]==null||billcheckmap["rsc"]==""}'>
					<input type="button" class="btncon verify" id="rsc" />
				</c:if> <c:if test='${billcheckmap["rsc"]!=null&&billcheckmap["rsc"]!=""}'>
${billcheckmap['rsczt']==02?'通过':'驳回'}
</c:if></td>
			<td align="right">财务审核：</td>
			<td><input type="text" class="inputbottom cwsh"
				value="${billcheckmap['cwsh'] }" /> <c:if
					test='${billcheckmap["cwsh"]==null||billcheckmap["cwsh"]==""}'>
					<input type="button" class="btncon verify" id="cwsh" />
				</c:if> <c:if
					test='${billcheckmap["cwsh"]!=null&&billcheckmap["cwsh"]!=""}'>
${billcheckmap['cwshzt']==02?'通过':'驳回'}
</c:if></td>
			<td align="center">收款人确认：</td>
			<td><input type="text" class="inputbottom skr"
				value="${billcheckmap['skr'] }" /> <c:if
					test='${billcheckmap["skr"]==null||billcheckmap["skr"]==""}'>
					<input type="button" class="btncon verify " id="skr" />
				</c:if> <c:if test='${billcheckmap["skr"]!=null&&billcheckmap["skr"]!=""}'>
${billcheckmap['skrzt']==02?'通过':'驳回'}
</c:if></td>
		</tr>
		<tr class="aduittr">
			<td height="25" align="right">总部总经理：</td>
			<td><input type="text" class="inputbottom zjl"
				value='${billcheckmap["zjl"] }' /> <c:if
					test='${billcheckmap["zjl"]==null||billcheckmap["zjl"]==""}'>
					<input type="button" class="btncon verify" id="zjl" />
				</c:if> <c:if test='${billcheckmap["zjl"]!=null&&billcheckmap["zjl"]!=""}'>
${billcheckmap["zjlzt"]==02?"通过":"驳回"}
</c:if></td>
			<td align="right">总部部门主管：</td>
			<td><input type="text" class="inputbottom zg"
				value="${billcheckmap['zg'] }" /> <c:if
					test='${billcheckmap["zg"]==null||billcheckmap["zg"]==""}'>
					<input type="button" class="btncon verify" id="zg" />
				</c:if> <c:if test='${billcheckmap["zg"]!=null&&billcheckmap["zg"]!=""}'>
${billcheckmap['zgzt']==02?'通过':'驳回'}
</c:if></td>
			<td align="right">总部人事处：</td>
			<td><input type="text" class="inputbottom zbrsc"
				value="${billcheckmap['zbrsc'] }" /> <c:if
					test='${billcheckmap["zbrsc"]==null||billcheckmap["zbrsc"]==""}'>
					<input type="button" class="btncon verify" id="zbrsc" />
				</c:if> <c:if
					test='${billcheckmap["zbrsc"]!=null&&billcheckmap["zbrsc"]!=""}'>
${billcheckmap['zbrsczt']==02?'通过':'驳回'}
</c:if></td>
			<td align="right">总财务审核：</td>
			<td><input type="text" class="inputbottom zbcw"
				value="${billcheckmap['zbcw'] }" /> <c:if
					test='${billcheckmap["zbcw"]==null||billcheckmap["zbcw"]==""}'>
					<input type="button" class="btncon verify" id="zbcw" />
				</c:if> <c:if
					test='${billcheckmap["zbcw"]!=null&&billcheckmap["zbcw"]!=""}'>
${billcheckmap['zbcwzt']==02?'通过':'驳回'}
</c:if></td>
			<td align="center">交款人确认：</td>
			<td><input type="text" class="inputbottom jkr"
				value="${billcheckmap['jkr'] }" /> <c:if
					test='${billcheckmap["jkr"]==null||billcheckmap["jkr"]==""}'>
					<input type="button" class="btncon verify" id="jkr" />
				</c:if> <c:if test='${billcheckmap["jkr"]!=null&&billcheckmap["jkr"]!=""}'>
${billcheckmap['jkrzt']==02?'通过':'驳回'}
</c:if></td>
		</tr>
	</table>
	<div style="width: 350px;height:220px;border: 1px solid #ffffff;background-color: #DBFAF8;
			position: absolute;top:209px;left: 860px;" class="jqmWindow jqmWindowcss2" id="single">
    	<div style="background-color: #C5C1AA;height: 28px;text-align: center;">
    		<font size="3" color="#008B00" style="position: relative;top: 4px;">物品审核</font>
    	</div>
    	<div style="position: relative;top: 10px;height: 120px;">
    		<div style="position: relative;left: 20px;"><span>审核意见：</span>
    			<span style="position: relative;left: 80px;"><input type="radio" name="radio" class="radio" value="yes" checked="checked">通过</span>
    			<span style="position: relative;left: 110px;"><input type="radio" name="radio" class="radio" value="no">驳回</span></div>
    		<div><textarea style="width: 250px;height: 100px;position: relative;left: 20px;top: 2px;"></textarea></div>
    	</div>
    	<div style="position:relative;top:10px;">
    		<input type="button" class="but" value="提交" style="width: 45px;height: 25px;position: relative;top: 18px;left: 70px;">
    		<input type="button" class="but" value="关闭" style="width: 45px;height: 25px;position: relative;top: 18px;left: 120px;">
    	</div>
    </div>
  </body>
</html>
