<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>领用出库单查看审核</title>
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
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<link rel="stylesheet"
			href="<%=basePath%>js/tree/common/css/style.css" type="text/css"
			media="screen" />
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<link rel="STYLESHEET" type="text/css"
			href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/receiveManagement_edit.js"></script>
		<style type="text/css">
.hide {
	border-left: 0px;
	border-right: 0px;
	border-top: 0px;
	border-bottom: 0px;
	background: transparent;
}

.classhide {
	display: none;
}

#table input {
	width: 180px;
}

#goodstable input {
	width: 180px;
}

#contactcompany input {
	width: 180px;
}

</style>
	</head>
	<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var pNumber=${pageNumber};
    var search="${search}";
    var select=0;
    var sdate="${sdate}";
    var edate="${edate}";
    var billStatus='${billStatus}';
    var loginstaffcheck=${loginstaffcheck};//判断审核次数
</script>
	<body style="background:#ffffff;border-top:5px solid #c5d9f1;">
		<form name="purchaseForm" id="purchaseForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div style="width: 100%;">
                 <table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
					<tr>
						<td height="30" align="left">
						<input type="button" class="menubtn grey" name="button3" value="保存" disabled="disabled"/><input type="button" 
						class="menubtn grey" name="button" value="提交审核" disabled="disabled"/><input type="button" 
						class="menubtn grey" name="button" value="增加一行" disabled="disabled"/><input type="button" 
						class="menubtn grey" name="button" value="删除新增行" disabled="disabled"/><input type="button" class="menubtn JQueryprint" name="button"
								value="打印预览" /><input type="button" class="menubtn JQueryClose" name="button2"
								value="关闭" /><input type="button" 
						class="menubtn grey" name="button" value="复制" disabled="disabled"/>
						</td>
					</tr>
				</table>
				<table width="1000" border="0" id="table3" align="center"
					cellpadding="0" cellspacing="0" style="margin-left:5px;background: #FFFFFF;">
					<tr>
					<td colspan="6" align="center" id="titlestyle">领用出库单</td>
					</tr>
					<tr>
					<td align="right" colspan="5">单据编号：</td>
					<td align="left">
						<input type="text" style="width: 160px;" class="inputbottom"
							name="financialBill.journalNum" id="journalNum"
							readonly="readonly" value="${financialBill.journalNum}"/>
						<input type="hidden" id="cashierID" value="${financialBill.cashierBillsID}">
					</td>
					</tr>
					<tr>
						<td height="30" align="right">
		                   <span style="color:red;">*</span>领&nbsp;用&nbsp;人：
						</td>
						<td>
							<input type="hidden" id="choosedepotID" name="financialBill.staffsID"
								readonly="readonly" value="${financialBill.staffID==null?ManStaffId:financialBill.staffID}"/>
							<input type="text" id="collarPerson" onclick="importGY('td2','partnerID','partnerName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')"
								placeholder="点击选择领用人" name="financialBill.staffsName" value="${financialBill.staffName==null?ManStaffName1:financialBill.staffName}" style="width: 160px;" class="notnull inputbottom" readonly="readonly"/>
						</td>
						<td align="right">
							 <span style="color:red;">*</span>领用仓库：
						</td>
						<td>
							
						   <input name="financialBill.depotName" value="${financialBill.depotName}" placeholder="单击选择领用仓库" 
						   id="choosedepotName" style="width: 160px;" class="notnull inputbottom" readonly="readonly">
						   <input type="hidden" name="financialBill.depotID" value="${financialBill.depotID}" id="choosedepotID"/>
						
						</td>	
						<td height="30" align="right">
							<span style="color:red;">*</span>责&nbsp;任&nbsp;人：
						</td>
						<td id="td2">
							<input type="hidden" id="partnerID" name="financialBill.staffID"
								readonly="readonly" value="${financialBill.staffID==null?ManStaffId:financialBill.staffID}"/>
							<input type="text" id="partnerName" onclick="importGY('td2','partnerID','partnerName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')"
								placeholder="点击选择人员" name="financialBill.staffName" value="${financialBill.staffName==null?ManStaffName1:financialBill.staffName}" style="width: 160px;" class="notnull inputbottom" readonly="readonly"/>
						</td>
					</tr>
					<tr>
					    <td align="right">
							联系电话：
						</td>
						<td>
							<input type="text" readonly="readonly" class="inputbottom" style="width: 160px;"
							 value="${financialBill.ccompanyTel}"/></td>
						<td height="30" align="right">
		                   <span style="color:red;">*</span>对方科目：
						</td>
						<td> 
						   <input type="hidden" id="subjectsID"
								name="financialBill.subjectID" value="${financialBill.subjectID}" />
							<input type="text" readonly="readonly"
								placeholder="单击选择科目"	 class="notnull inputbottom tosubjects" style="width: 160px;"
									id="subjectsName" name="financialBill.subjectName" value="${financialBill.subjectName}" readonly="readonly"/>
						</td>
						<td align="right">
						           联系电话：
						</td>
						<td align="left">
							<input type="text" readonly="readonly" class="inputbottom" style="width: 160px;"
							 value="${financialBill.staffPhone}"/></td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span style="color:red;">*</span>领用日期：
						</td>
						<td>
							<input type="text" readonly="readonly" class="inputbottom" style="width: 160px;"
							 value="${financialBill.purchaseDate}"/></td>
						<td align="right">
						    <div id="u1170_rtf">
							物流方式：
							</div>
						</td>
						<td align="left">
						<input type="text" readonly="readonly" class="inputbottom" style="width: 160px;"
							 value="${financialBill.logisticsType}"/>
						</td>
					</tr>	
				</table>
				<table width="800" height="158px" border="0" align="center"
					cellpadding="0" cellspacing="0" style="margin-left:15px;margin-top: 5px" id="tt">
					<tr>
						<td valign="top">
							<div id="Layer1"
								style="position: absolute; width: 800px; height: 158px; overflow: auto;">
								<table width="800" align="center" cellpadding="0"
									cellspacing="0" class="table" id="goodtable">
									<tr>
										<th align="center" bgcolor="#E4F1FA">
											序号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											物品名称	
										</th>
										<th align="center" bgcolor="#E4F1FA">
											规格
										</th>
										<th align="center" bgcolor="#E4F1FA">
											型号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											批次
										</th>
										<th align="center" bgcolor="#E4F1FA">
											库房
										</th>
										<th align="center" bgcolor="#E4F1FA">
											入库位置
										</th>
										<th align="center" bgcolor="#E4F1FA">
											单价
										</th>
										<th align="center" bgcolor="#E4F1FA">
											数量
										</th>
										<th align="center" bgcolor="#E4F1FA">
											单位
										</th>
										<th align="center" bgcolor="#E4F1FA">
											金额
										</th>
										<th align="center" bgcolor="#E4F1FA">
											科目
										</th>
										<th align="center" bgcolor="#E4F1FA">
											备注
										</th>
										<th align="center" bgcolor="#E4F1FA">
											操作
										</th>
									</tr>
									<c:forEach items="${BillsGoodList}" var="list" varStatus="ltm">
									 <tr>
										<td height="30" width="50" align="center" bgcolor="#FFFFFF">
										    <input type="hidden" id="goodsBillsID" name="goodsmapold[${ltm.index}].goodsBillsID" value="${list.goodsBillsID}"/>
										    <input type="hidden" id="goodsBillsKey" name="goodsmapold[${ltm.index}].goodsBillsKey" value="${list.goodsBillsKey}"/>
											<input type="hidden" id="inventoryID" name="inventoryID" value="${list.inventoryID}"/>
											<input type="hidden" id="standard" name="standard" value="${list.standard}"/>
											<input type="hidden" name="goodsmapold[${ltm.index}].goodsNomber" id="goodsNomber" 
											size="5" style="border:0px;height:100%;" value="${list.goodsNomber==null?ltm.index+1:list.goodsNomber}"> 
										    <span>${list.goodsNomber==null?ltm.index+1:list.goodsNomber}</span>
										</td>					
										<td align="center" bgcolor="#FFFFFF">
											<span>${list.goodsName}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.typeID}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span>${list.goodsNum}</span>
										</td>					
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.sortCode}</span> 
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.depotName==null?"无":list.depotName}</span>
										</td>
										
										<td align="center" bgcolor="#FFFFFF" style="display: none;">
											<span>${list.goodsnumber}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${financialBill.drdepotName}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.price}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.quantity}</span>
										</td>
										<td>
											<span>${list.goodsVariableID==null?'':list.goodsVariableID}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.money}</span>
										</td>
										
										
										<td align="center" bgcolor="#FFFFFF">
											<span>${list.subjectsName}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span>${list.remark}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>无</span>
										</td>
									</tr>
									</c:forEach>
								</table>
							</div>
						</td>
					</tr>
				</table>
                <table width="99%" border="0" style="margin-left: 15px;" align="center" cellpadding="0" cellspacing="0">
				   <tr><td colspan="2">&nbsp;</td></tr>
				   <tr>
				   <td align="left" width="50px">备注：</td>
				   <td align="left" colspan="9">
				   <input type="text" id="remark" class="inputbottom" style="width:80%;" readonly="readonly"/>
				   </td>
				   </tr>
				</table>
				<table width="99%" border="0" cellpadding="0" cellspacing="0" id="audittbl">
				<tr><td>
				<input type="hidden" id="staffauditname" 
				value="${ManStaffName}">
				<input type="hidden" id="staffauditcode" 
				value="${ManStaffCode}">
				<input type="hidden" id="staffauditid" 
				value="${ManStaffId}">
				</td></tr>
				<tr>
					<td height="25" align="right">
						公司经理：
					</td>
					<td>
						<input type="text" class="inputbottom gsjl" value="${billcheckmap['gsjl']}" readonly="readonly"/>
						<c:if test='${billcheckmap["gsjl"]==null||billcheckmap["gsjl"]==""}'>
						<input type="button" class="btncon verify" id="gsjl" />
					    </c:if>
					    <c:if test='${billcheckmap["gsjlzt"]!=null&&billcheckmap["gsjlzt"]!=""}'>
					    	${billcheckmap["gsjlzt"]=="02"?"通过":"驳回"}
					    </c:if>
					</td>
					<td align="right">
						部门主管：
					</td>
					<td>
						<input type="text" class="inputbottom bmzg" value="${billcheckmap['bmzg']}" readonly="readonly"/>
						<c:if test='${billcheckmap["bmzg"]==null||billcheckmap["bmzg"]==""}'>
						<input type="button" class="btncon verify" id="bmzg" />
					    </c:if>
					    <c:if test='${billcheckmap["bmzgzt"]!=null&&billcheckmap["bmzgzt"]!=""}'>
					    	${billcheckmap["bmzgzt"]=="02"?"通过":"驳回"}
					    </c:if>
					</td>
					<td  align="right">
						人事处：
					</td>
					<td>
						<input type="text" class="inputbottom rsc" value="${billcheckmap['rsc']}" readonly="readonly"/>
						<c:if test='${billcheckmap["rsc"]==null||billcheckmap["rsc"]==""}'>
						<input type="button" class="btncon verify" id="rsc"/>
						</c:if>
						<c:if test='${billcheckmap["rsczt"]!=null&&billcheckmap["rsczt"]!=""}'>
					    	${billcheckmap["rsczt"]=="02"?"通过":"驳回"}
					    </c:if>
					</td>
					<td  align="right">
						财务审核：
					</td>
					<td>
						<input type="text" class="inputbottom cwsh" value="${billcheckmap['cwsh']}" readonly="readonly"/>
						<c:if test='${billcheckmap["cwsh"]==null||billcheckmap["cwsh"]==""}'>
						<input type="button" class="btncon verify" id="cwsh"/>
						</c:if>
						<c:if test='${billcheckmap["cwshzt"]!=null&&billcheckmap["cwshzt"]!=""}'>
					    	${billcheckmap["cwshzt"]=="02"?"通过":"驳回"}
					    </c:if>
					</td>
					<td  align="center">
						收款人确认：
					</td>
					<td>
						<input type="text" class="inputbottom skr" value="${billcheckmap['skr']}"readonly="readonly"/>
						<c:if test='${billcheckmap["skr"]==null||billcheckmap["skr"]==""}'>
						<input type="button" class="btncon verify" id="skr"/>
						</c:if>
						<c:if test='${billcheckmap["skrzt"]!=null&&billcheckmap["skrzt"]!=""}'>
					    	${billcheckmap["skrzt"]=="02"?"通过":"驳回"}
					    </c:if>
					</td>
				</tr>
				<tr>
					<td  height="25" align="right">
						总部总经理：
					</td>
					<td>
						<input type="text" class="inputbottom zjl" value="${billcheckmap['zjl']}" readonly="readonly"/>
						<c:if test='${billcheckmap["zjl"]==null||billcheckmap["zjl"]==""}'>
						<input type="button" class="btncon verify"  id="zjl"/>
						</c:if>
						<c:if test='${billcheckmap["zjlzt"]!=null&&billcheckmap["zjlzt"]!=""}'>
					    	${billcheckmap["zjlzt"]=="02"?"通过":"驳回"}
					    </c:if>
					</td>
					<td  align="right">
						总部部门主管：
					</td>
					<td>
						<input type="text" class="inputbottom zg" value="${billcheckmap['zg']}" readonly="readonly"/>
						<c:if test='${billcheckmap["zg"]==null||billcheckmap["zg"]==""}'>
						<input type="button" class="btncon verify" id="zg"/>
						</c:if>
						<c:if test='${billcheckmap["zgzt"]!=null&&billcheckmap["zgzt"]!=""}'>
					    	${billcheckmap["zgzt"]=="02"?"通过":"驳回"}
					    </c:if>
					</td>
					<td  align="right">
						总部人事处：
					</td>
					<td>
						<input type="text" class="inputbottom zbrsc" value="${billcheckmap['zbrsc']}" readonly="readonly"/>
						<c:if test='${billcheckmap["zbrsc"]==null||billcheckmap["zbrsc"]==""}'>
						<input type="button" class="btncon verify" id="zbrsc"/>
						</c:if>
						<c:if test='${billcheckmap["zbrsczt"]!=null&&billcheckmap["zbrsczt"]!=""}'>
					    	${billcheckmap["zbrsczt"]=="02"?"通过":"驳回"}
					    </c:if>
					</td>
					<td align="right">
						总财务审核：
					</td>
					<td>
						<input type="text" class="inputbottom zbcw" value="${billcheckmap['zbcw']}" readonly="readonly"/>
						<c:if test='${billcheckmap["zbcw"]==null||billcheckmap["zbcw"]==""}'>
						<input type="button" class="btncon verify" id="zbcw"/>
						</c:if>
						<c:if test='${billcheckmap["zbcwzt"]!=null&&billcheckmap["zbcwzt"]!=""}'>
					    	${billcheckmap["zbcwzt"]=="02"?"通过":"驳回"}
					    </c:if>
					</td>
					<td  align="center">
						交款人确认：
					</td>
					<td>
						<input type="text" class="inputbottom jkr" value="${billcheckmap['jkr']}" readonly="readonly"/>
						<c:if test='${billcheckmap["jkr"]==null||billcheckmap["jkr"]==""}'>
						<input type="button" class="btncon verify" id="jkr"/>
						</c:if>
						<c:if test='${billcheckmap["jkrzt"]!=null&&billcheckmap["jkrzt"]!=""}'>
					    	${billcheckmap["jkrzt"]=="02"?"通过":"驳回"}
					    </c:if>
					</td>
				</tr>
			</table>
			</div>
			<s:token></s:token>
		</form>
		<!--审核备注 -->
        <div class="jqmWindow jqmWindowcss" style="width: 350px;top:50%" id="jqModelSearch">
                <div class="drag">审核信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                	<tr>
                		<td colSpan="2" align="center">
                			<input type="radio" name="status" class="status" value="02"/>审核通过
                			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                			<input type="radio" name="status" class="status" value="03"/>驳回
                		</td>
                	</tr>
                    <tr>
                        <td>审核意见：</td>
                        <td>
                        <input type="text" id="remarks" class="inputbottom" style="width:280px;"/>
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tocheckok" value="确定" />
                    <input type="button" class="input-button" id="tocheckclose" value="取消" />
                </div>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>

	</body>
</html>
