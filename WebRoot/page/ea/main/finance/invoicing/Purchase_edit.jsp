<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>采购单</title>
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
			src="<%=basePath%>js/ea/finance/invoicing/Purchase_edit.js"></script>
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
#apDiv1 {
	position: absolute;
	left: 707px;
	top: 407px;
	width: 63px;
	height: 32px;
	z-index: 1;
}
</style>
	</head>
	<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var pNumber=${pageNumber};
    var search="${search}";
    var type="${type}";
    var type1="${param.type1}";
    var select=0;
    var sdate="${sdate}";
    var edate="${edate}";
    var loginstaffcheck=${loginstaffcheck};
    var status="${cashierBills.status}";
    var remark="${param.remark}";
</script>

	<body style="background:#ffffff;border-top:5px solid #c5d9f1;">
	<div id="apDiv1"></div>
		<form name="purchaseForm" id="purchaseForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<input type="hidden" name="billStatus" id="billStatus" value="${financialBill.billStatus}"/>
			<div>
			   <table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
					<tr>
						<td height="30" align="left">
						     <input type="button" class="menubtn grey" 
						    name="button3" value="保存" disabled="disabled"/><input type="button" 
						    class="menubtn grey" name="button" value="提交审核" disabled="disabled"/><input type="button" 
						    class="menubtn grey" name="button" value="本行后增加一行" disabled="disabled"/><input type="button" 
						    class="menubtn grey" name="button" value="删除本行" disabled="disabled"/>
							<input  type="button" class="menubtn JQuerySubmitgd" name="button1" 
							    value="确认收货" style="display:none"/><input type="button" 
							    class="menubtn JQueryprint" name="button"
								value="打印预览" /><input type="button" 
								class="menubtn JQueryClose" name="button2"
								value="关闭" /><input type="button" 
						    class="menubtn grey" name="button" value="帮助" disabled="disabled"/>
						</td>
					</tr>
				</table>
				<table width="800" border="0" id="table3" align="center"
					cellpadding="0" cellspacing="0" style="margin-left: 15px;background: #FFFFFF;">
					<tr><td height="50" colspan="6" align="center"><h2>采购单</h2></td></tr>
					<tr>
						<td height="30" align="right" width="80">
							单据编号：
						</td>
						<td width="300">
						    <input type="hidden" name="cashierBills.cashierBillsID"
								id="cashierBillsID" value="${cashierBills.cashierBillsID}" />
							<input type="hidden" name="financialBill.financialbillID"
								id="cashierID" value="${financialBill.financialbillID}" />
							<input type="hidden" name="financialBill.journalNum"
								id="journalNum" value="${financialBill.journalNum}" />
							<input type="hidden" name="financialBill.financialbillKey"
								id="goodsBillsKey" value="${financialBill.financialbillKey}" />
							 <input type="text" class="inputbottom" style="width: 200px;"
						   readonly="readonly" value="${financialBill.journalNum}"/>
						</td>
						<td align="right" width="50">
							采&nbsp;购&nbsp;&nbsp;员：
						</td>
						<td>
						    <input type="text" class="inputbottom" style="width: 200px;"
						   readonly="readonly" value="${financialBill.staffName}"/>
						</td>
						<td align="right" width="50">
							制单日期：
						</td>
						<td>
						<input type="text" class="inputbottom" style="width: 200px;"
						   readonly="readonly" value="${fn:substring(financialBill.billsDate, 0, 10)}"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司：</span>
						</td>
						<td>
						<input type="text" class="inputbottom" style="width: 200px;"
						   readonly="readonly" value="${companyname}"/>
						</td>
						<td align="right">
							部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门：
						</td>
						<td align="left" id="dept">
						<input type="text" class="inputbottom" style="width: 200px;"
						   readonly="readonly" value="${organizationname}"/>
						</td>
						<td align="right">
							<div id="u1170_rtf">
								制&nbsp;单&nbsp;&nbsp;人：
							</div>
						</td>
						<td align="left" id="staff">
						<input type="text" class="inputbottom" style="width: 200px;"
						   readonly="readonly" value="${cashierBills.inputName}"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">预付定金：</span>
						</td>
						<td>
						   <input type="text" class="inputbottom" style="width: 200px;"
						   readonly="readonly" value="${financialBill.frontMoney==null?0:financialBill.frontMoney}&nbsp;${financialBill.moneyType}"/>
						</td>  
						<td align="right">
							付款方式：
						</td>
						<td align="left" id="dept">
						<input type="text" class="inputbottom" style="width: 200px;"
						   readonly="readonly" value="${financialBill.paymentType==00?'请选择':financialBill.paymentType==01?'银行转帐':financialBill.paymentType==02?'银行支票转账'
                                  :financialBill.paymentType==03?'支付宝转帐':financialBill.paymentType==04?'App转账':financialBill.paymentType==05?'POS机转账'
                                  :financialBill.paymentType==06?'现金转账':''}"/>
						</td>
						<td align="right">
							<div id="u1170_rtf">
								物流方式：
							</div>
						</td>
						<td align="left" id="staff">
						<input type="text" class="inputbottom" style="width: 200px;"
						   readonly="readonly" value="${financialBill.logisticsType}"/>
						</td>
					</tr>
					<%--<tr>
						<td height="20" width="7%" align="right">
							<span class="STYLE1">银行账号：</span>
						</td>
						<td>
							${financialBill.companyBankNum}
						</td>
						<td align="right"></td>
						<td width="15%" align="left"></td>
						<td width="15%" align="left"></td>
						<td width="15%" align="left"></td>
					</tr>
				--%></table>
				<table width="800" height="158px" border="0" align="center"
					cellpadding="0" cellspacing="0" style="margin-left: 15px;margin-top: 5px" id="tt">
					<tr>
						<td valign="top">
							<div id="Layer1"
								style="position: absolute; width: 800px; height: 158px; overflow:auto;">
								<table width="800" align="center" cellpadding="0"
									cellspacing="0" class="table" id="goodtable">
									<tr>
										<th align="center" bgcolor="#E4F1FA" width="30">
											序号
										</th>
										<th align="center" bgcolor="#E4F1FA" id="reQuantity" width="60">
											收货数量
										</th>
										<th align="center" bgcolor="#E4F1FA" width="60">
											品名编号
										</th>
										<th align="center" bgcolor="#E4F1FA" width="60">
											统一分类条码
										</th>
										<th align="center" bgcolor="#E4F1FA" width="60">
											品名名称
										</th>
										<th align="center" bgcolor="#E4F1FA" width="60">
											类别
										</th>
										<th align="center" bgcolor="#E4F1FA" width="60">
											品牌规格
										</th>
										<th align="center" bgcolor="#E4F1FA" width="60">
											单位
										</th>
										<th align="center" bgcolor="#E4F1FA" width="60">
											数量
										</th>
										<th align="center" bgcolor="#E4F1FA" width="60">
											单价
										</th>
										<th align="center" bgcolor="#E4F1FA" width="60">
											金额
										</th>
										<th align="center" bgcolor="#E4F1FA" width="70">
											供应商
										</th>
										<th align="center" bgcolor="#E4F1FA" width="80">
											提醒内容
										</th>

									</tr>
									<s:iterator value="warehouseList">
										<tr class="xggoods">
											<td height="30" align="center" bgcolor="#FFFFFF">
											    <span id="goodsNomber">${goodsNomber}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF" id="reQuantity">
											    <span id="reQuantity">${reQuantity}</span>
												<input  type="text" class="posnum input jisuan put0" size="5" style="display:none;"
												name="reQuantity" id="reQuantity" value="${quantity}"/>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<input type="hidden" name="goodsID" id="goodsID" value="${goodsID}"/>
												<span id="goodsNum">${goodsNum}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="sortCode">${sortCode}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="goodsName">${goodsName}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="typeID">${typeID}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="standard">${standard}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="goodsVariableID" name="goodsVariableID">${goodsVariableID}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="quantity">${quantity}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="price">${price}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="money">${money}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
											<input type="text" name="ccompanyName" id="ccompanyName" class="input" size="15"
												style="margin-left: 2px;width:100%;height:100%;border:0px;" value="${ccompanyName}" readonly="readonly"/>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span name="remindedContent">${remindedContent}</span>
											</td>
										</tr>
									</s:iterator>
								</table>
							</div>
						</td>
					</tr>
				</table>
				<table width="800" border="0" id="table4" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;">
					<tr>
						<td height="30" align="right" width="120">
							<span class="STYLE1">采购单位：</span>
						</td>   
						<td width="150">
						    <input type="text" class="inputbottom" style="width: 150px;"
						   readonly="readonly" id="ccompany" value="${contactCompanyView.companyName}"/> 
					   </td>
						<td align="right" width="120">
							<span class="STYLE1">单位电话：</span>
						</td>
						<td width="150">
						<input type="text" class="inputbottom" style="width: 150px;"
						   readonly="readonly" id="companyTel" value="${contactCompanyView.companyTel}"/>
						</td>
						<td align="right" width="120">
							<span class="STYLE1">单位负责人：</span>
						</td>
						<td width="120">
						    <input type="text" class="inputbottom" style="width: 150px;"
						   readonly="readonly" id="cresponsible" value="${contactCompanyView.cresponsible}"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">单位负责人电话：</span>
						</td>
						<td>
						<input type="text" class="inputbottom" style="width: 150px;"
						   readonly="readonly" id="responsibleTel" value="${contactCompanyView.responsibleTel}"/>
						</td>
						<td align="right">
							<span class="STYLE1">公司地址：</span>
						</td>
						<td>
						    <input type="text" class="inputbottom" style="width: 150px;"
						   readonly="readonly" id="responsibleTel" value="${contactCompanyView.companyAddr}"/>
						</td>
						<td align="right">
							<span class="STYLE1">行业类别：</span>
						</td>
						<td>
						    <input type="text" class="inputbottom" style="width: 150px;"
						   readonly="readonly" id="industryType" value="${contactCompanyView.industryType}"/>
						</td>
					</tr>
				</table>
				<c:if test="${cashierBills.status =='05'}">
				<table width="99%" border="0" align="center" style="margin-left:10px;" cellpadding="0" cellspacing="0">
				   <tr><td colspan="2">&nbsp;</td></tr>
				   <tr>
				   <td align="left" width="50px">备注：</td>
				   <td align="left" colspan="9">
				   <input type="text" id="remark" class="inputbottom" value="${param.remark}" style="width:80%;" readonly="readonly"/>
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
				<tr class="aduittr">
					<td height="25" align="right">公司经理：</td>
					<td><input type="text" class="inputbottom gsjl" value='${billcheckmap["gsjl"]}    '/>
					<c:if test='${billcheckmap["gsjl"]==null||billcheckmap["gsjl"]==""}'>
						<input type="button" class="btncon verify" id="gsjl" />	
					</c:if >
					<c:if test='${billcheckmap["gsjl"]!=null&&billcheckmap["gsjl"]!=""}'>
						${billcheckmap["gsjlzt"]==02?"通过":"驳回"} 
					</c:if>
					<td align="right">部门主管：</td>
					<td><input type="text" class="inputbottom bmzg" value='${billcheckmap["bmzg"] }    '/>
					
					<c:if test='${billcheckmap["bmzg"]==null||billcheckmap["bmzg"]==""}'>		
						<input type="button" class="btncon verify" id="bmzg"/>
					</c:if>
					<c:if test='${billcheckmap["bmzg"]!=null&&billcheckmap["bmzg"]!=""}'>		
						${billcheckmap["bmzgzt"]==02?"通过":"驳回"}
					</c:if>
					</td>
					<td align="right">人事处：</td>
					<td><input type="text" class="inputbottom rsc" value="${billcheckmap['rsc'] }"/>
					<c:if test='${billcheckmap["rsc"]==null||billcheckmap["rsc"]==""}'>
						<input type="button" class="btncon verify" id="rsc" />
					</c:if>
					<c:if test='${billcheckmap["rsc"]!=null&&billcheckmap["rsc"]!=""}'>
						${billcheckmap['rsczt']==02?'通过':'驳回'}
					</c:if>
					</td>
					<td align="right">财务审核：</td>
					<td><input type="text" class="inputbottom cwsh" value="${billcheckmap['cwsh'] }"/>
					<c:if test='${billcheckmap["cwsh"]==null||billcheckmap["cwsh"]==""}'>
						<input type="button" class="btncon verify" id="cwsh" />
					</c:if>
					<c:if test='${billcheckmap["cwsh"]!=null&&billcheckmap["cwsh"]!=""}'>
						${billcheckmap['cwshzt']==02?'通过':'驳回'}
					</c:if>
					</td>
					<td align="center">收款人确认：</td>
					<td><input type="text" class="inputbottom skr" value="${billcheckmap['skr'] }"/>
					<c:if test='${billcheckmap["skr"]==null||billcheckmap["skr"]==""}'>
						<input type="button" class="btncon verify " id="skr" />
					</c:if>
					<c:if test='${billcheckmap["skr"]!=null&&billcheckmap["skr"]!=""}'>
						${billcheckmap['skrzt']==02?'通过':'驳回'}
					</c:if>
					</td>
				</tr>
				<tr class="aduittr">
					<td height="25" align="right">总部总经理：</td>
					<td><input type="text" class="inputbottom zjl" value='${billcheckmap["zjl"] }'/>
					<c:if test='${billcheckmap["zjl"]==null||billcheckmap["zjl"]==""}'>
						<input type="button" class="btncon verify" id="zjl" />
					</c:if>
					<c:if test='${billcheckmap["zjl"]!=null&&billcheckmap["zjl"]!=""}'>
						 ${billcheckmap["zjlzt"]==02?"通过":"驳回"}
					</c:if>
					</td>
					<td align="right">总部部门主管：</td>
					<td><input type="text" class="inputbottom zg" value="${billcheckmap['zg'] }"/>
					<c:if test='${billcheckmap["zg"]==null||billcheckmap["zg"]==""}'>
						<input type="button" class="btncon verify" id="zg" />
					</c:if>
					<c:if test='${billcheckmap["zg"]!=null&&billcheckmap["zg"]!=""}'>
						${billcheckmap['zgzt']==02?'通过':'驳回'}
					</c:if>
					</td>
					<td align="right">总部人事处：</td>
					<td><input type="text" class="inputbottom zbrsc" value="${billcheckmap['zbrsc'] }"/>
					<c:if test='${billcheckmap["zbrsc"]==null||billcheckmap["zbrsc"]==""}'>
						<input type="button" class="btncon verify" id="zbrsc" />
					</c:if>
					<c:if test='${billcheckmap["zbrsc"]!=null&&billcheckmap["zbrsc"]!=""}'>
						${billcheckmap['zbrsczt']==02?'通过':'驳回'}
					</c:if>
					</td>
					<td align="right">总财务审核：</td>
					<td><input type="text" class="inputbottom zbcw" value="${billcheckmap['zbcw'] }"/>
					<c:if test='${billcheckmap["zbcw"]==null||billcheckmap["zbcw"]==""}'>
						<input type="button" class="btncon verify" id="zbcw" />
					</c:if>
					<c:if test='${billcheckmap["zbcw"]!=null&&billcheckmap["zbcw"]!=""}'>
						${billcheckmap['zbcwzt']==02?'通过':'驳回'}
					</c:if>
					</td>
					<td align="center">交款人确认：</td>
					<td><input type="text" class="inputbottom jkr" value="${billcheckmap['jkr'] }"/>
					<c:if test='${billcheckmap["jkr"]==null||billcheckmap["jkr"]==""}'>
						<input type="button" class="btncon verify" id="jkr" />
					</c:if>
					<c:if test='${billcheckmap["jkr"]!=null&&billcheckmap["jkr"]!=""}'>
						 ${billcheckmap['jkrzt']==02?'通过':'驳回'}
					</c:if>
					</td>
				</tr>
			</table>
	        </c:if>
				<%--<table width="99%" border="0" align="center" id="table5"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td width="10%" height="30" align="right">
							<span class="STYLE1">往来个人：</span>
						</td>
						<td width="15%">
							<span id="contactUserName" class="qk">${contactUser.staffName}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">电话：</span>
						</td>
						<td width="15%">
							<span id="tel" class="qk">${contactUser.reference}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人身份证号：</span>
						</td>
						<td width="15%">
							<span id="staffIdentityCard" class="qk">${contactUser.staffIdentityCard}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账户：</span>
						</td>
						<td width="15%">
							<span id="userAccountNum" class="qk">${financialBill.userAccountNum}</span>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">QQ：</span>
						</td>
						<td>
							<span id="userQq" class="qk">${contactUser.referenceCode}</span>
						</td>
						<td align="right">
							<span class="STYLE1">邮箱：</span>
						</td>
						<td>
							<span id="email" class="qk">${contactUser.referenceOrganization}</span>
						</td>
						<td align="right">
							<span class="STYLE1">地址：</span>
						</td>
						<td>
							<span id="userAddr" class="qk">${contactUser.staffAddress}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人往来关系：</span>
						</td>
						<td width="15%">
							<span id="phone" class="qk">${financialBill.cstaffRelationship}</span>
						</td>
					</tr>
				</table>
				--%>
			</div>
			<s:token></s:token>
		</form>
		 <!--审核备注 -->
         <div class="jqmWindow jqmWindowcss" style="width: 400px;top:50%" id="jqModelSearch">
                <div class="contentbannb">
                <div class="drag">
                    审核
                    <div class="close"></div>
                </div>
            </div>
            <center>
            	<input type="radio" class="xuanxiang" value="02" name="xx">审核通过&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	<input type="radio" class="xuanxiang" value="03" name="xx">驳回
			<table width="100%" id="SearchTable2" cellspacing="15"
				cellpadding="15">
				<tr>
					<td align="right" width='25%'>审核意见：</td>
					<td align="left" colspan="2" height="120"><textarea rows="5" cols="40"
							style="max-width: 270px;max-height: 100px;" name="comments"
							id="comments" class="ckTextLength" maxlength="1000"></textarea>
						<input  name="cashierBills.status" id="status1" style="display:none"/>
						<input  name="statusForPerson" id="statusForPerson" value="00" style="display:none"/>
					</td>
				</tr>
			</table>
				
			<div align="center">
                <input type="button" class="input-button" id="tocheckok" value=" 确定 " /> <input type="submit" name="submit" style="display:none"/>
            </div>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
