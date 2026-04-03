<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>出纳单据基本信息s</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/finance/company/cashierbillsprint.js"></script>
<style type="text/css">
.table {
	border-collapse:collapse;
	border:1px solid #000000;
	 
}

.table th {
	border:1px solid #000000;
	color:#000000;
}
.table td {
	border:1px solid #000000;
	color:#000000;
}
body,td,th {
	font-size: 12px;
}
body {
	margin-left: 15px;
}
#apDiv1 {
	position:absolute;

	width:63px;
	height:32px;
	z-index:1;
}
</style>
 <script type="text/javascript">
    var  st = "${cashierBillsVO.status}";
    var basePath = "<%=basePath%>";
    var billTypese="${param.billTypese}";
    var statusbill="${cashierBillsVO.statusbill}";
    
   $(function(){
	   if(billTypese!=""){
	   $("#billTypese").text(billTypese);
	   }
	   
   });
  
   
</script>
 </head>
<body>
	<div id="apDiv1"></div>
	<div id="tableprint" align="center">
		<table width="620" border="0" cellpadding="0" cellspacing="0"
			style="background:#FFFFFF;">
			<tr>
				<td height="25" align="center" style="font-weight:bold; ">&nbsp;<span
					style="font-size: 15px" id="billTypese"><s:if
							test="cashierBillsVO.billsType=='项目收入预算单'&&cashierBillsVO.statusbill=='04'">订单</s:if>
						<s:else>${cashierBillsVO.billsType}</s:else> </span></td>
			</tr>
		</table>
		<table width="620" border="0" cellpadding="0" cellspacing="0"
			style="background:#FFFFFF;">
			<tr>


			</tr>
			<tr>
				<td width="5%" height="25" align="right">公司：</td>
				<td width="25%">${cashierBillsVO.companyname}</td>
				<td width="5%" align="right">部门：</td>
				<td width="12%">${cashierBillsVO.departmentname}</td>
				<td width="8%" align="right">责任人：</td>
				<td width="20%"><c:if test="${cashierBillsVO.staffname!=null}">${cashierBillsVO.staffname}---${cashierBillsVO.recordcode}</c:if>
				</td>
			</tr>
			<tr>

				<td colspan="6">
					<table width="100%">
						<tr>
							<td width="20%" align="left">制单人：${cashierBillsVO.inputName}</td>
							<td width="20%" align="center">制单日期：${fn:substring(cashierBillsVO.cashierDate,
								0, 10)}</td>
							<td align="center">凭证号：<%
								hy.ea.bo.finance.vo.CashierBillsVO data = (hy.ea.bo.finance.vo.CashierBillsVO) request
										.getAttribute("cashierBillsVO");
								if (data != null) {
									StringBuffer barCode = new StringBuffer();
									barCode.append("<img src='");
									barCode.append(request.getContextPath());
									barCode.append("/CreateBarCode?data=");
									barCode.append(data.getJournalNum());
									barCode.append("&barType=TF25&height=20&headless=true&drawText=true&width=1' wdith='200'>");
									out.println(barCode.toString());
								} else {
									out.println("no data");
								}
							%><br /> ${cashierBillsVO.journalNum}</td>
						</tr>
						<tr>
							<td>项目：${cashierBillsVO.projectName}</td>
							<td colspan="2" align="left">项目分类：${cashierBillsVO.xmtypename}</td>
						</tr>

					</table>
				</td>
			</tr>

		</table>
		<table width="620" cellpadding="0" cellspacing="0" class="table">
			<tr>
				<th height="15" width="50" align="center">款源日期</th>
				<th width="35" align="center">入账日期</th>
				<th width="25" align="center" bgcolor="#E4F1FA">有效日期</th>
				<th width="20" align="center" bgcolor="#E4F1FA">批号/期号</th>
				<th align="center">品名编号</th>
				<th align="center">费用或品名名称</th>
				<th align="center">类型</th>
				<th align="center">等级</th>
				<th width="30" align="center">型号</th>
				<th align="center">品牌规格</th>
				<th align="center">单位</th>
				<th align="center">数量</th>
				<th align="center">重量</th>
				<th align="center">单价</th>
				<th align="center">金额</th>
				<th align="center">费用类别</th>
				<th align="center">科目</th>
				<th align="center">往来个人</th>
				<!-- <th align="center" width="60" bgcolor="#E4F1FA">摘要</th> -->
			</tr>
			<s:iterator value="goodList">
				<tr>
					<td height="15" align="left" bgcolor="#FFFFFF">
						${fn:substring(startDate, 0, 10)}</td>
					<td align="left" bgcolor="#FFFFFF">${fn:substring(endDate, 0,
						19)}</td>
					<td align="left" bgcolor="#FFFFFF">${fn:substring(periodDate,
						0, 10)}</td>
					<td width="40" align="center" bgcolor="#FFFFFF">
						${batchNumber}</td>
					<td align="left" bgcolor="#FFFFFF">${goodsNomber}</td>
					<td align="left" bgcolor="#FFFFFF">${goodsName}</td>
					<td align="left" bgcolor="#FFFFFF">${typeID}</td>
					<td align="left" bgcolor="#FFFFFF">${goodsCoding}</td>
					<td align="left" bgcolor="#FFFFFF">${model}</td>
					<td align="left" bgcolor="#FFFFFF">${standard}</td>
					<td align="left" bgcolor="#FFFFFF">${variableID}</td>
					<td align="right" bgcolor="#FFFFFF">${quantity}</td>
					<td align="left" bgcolor="#FFFFFF">${weight}</td>
					<td align="right" bgcolor="#FFFFFF">${price}</td>
					<td align="right" bgcolor="#FFFFFF"><span id="money">${balance==null||balance==""?money:balance}</span>
					</td>
					<td align="left" bgcolor="#FFFFFF">${costType}</td>
					<td align="left" bgcolor="#FFFFFF">${subjectsName}<span
						id="balance" style="display:none" class="moneysum">${balance==null||balance==""?money:balance}</span>
					</td>
					<td align="left" bgcolor="#FFFFFF">${connectName}</td>
					<%-- <td align="center" bgcolor="#FFFFFF"> ${reasonThing}</td> --%>
				</tr>
			</s:iterator>
			<tr>
				<td height="15" align="center" bgcolor="#FFFFFF">合计</td>
				<td align="center" bgcolor="#FFFFFF"></td>
				<td align="center" bgcolor="#FFFFFF">&nbsp;</td>
				<td align="center" bgcolor="#FFFFFF">&nbsp;</td>
				<td align="center" bgcolor="#FFFFFF">&nbsp;</td>
				<td align="center" bgcolor="#FFFFFF">&nbsp;</td>
				<td align="center" bgcolor="#FFFFFF">&nbsp;</td>
				<td align="center" bgcolor="#FFFFFF">&nbsp;</td>
				<td align="center" bgcolor="#FFFFFF">&nbsp;</td>
				<td align="center" bgcolor="#FFFFFF">&nbsp;</td>
				<td align="center" bgcolor="#FFFFFF">&nbsp;</td>
				<td align="center" bgcolor="#FFFFFF">&nbsp;</td>
				<td align="center" bgcolor="#FFFFFF">&nbsp;</td>
				<td align="center" bgcolor="#FFFFFF">&nbsp;</td>
				<td align="right" bgcolor="#FFFFFF"><span id="monsum"></span>
				</td>
				<td align="center" bgcolor="#FFFFFF">&nbsp;</td>
				<td align="center" bgcolor="#FFFFFF">&nbsp;</td>
				<td align="center" bgcolor="#FFFFFF">&nbsp;</td>
				<!-- <td align="center" bgcolor="#FFFFFF">&nbsp;</td> -->
			</tr>
		</table>
		<table width="620" border="0" cellpadding="0" cellspacing="0"
			style="border-left:1px solid #000000; border-right:1px solid #000000;border-bottom:1px solid #000000 ">
			<tr>
				<td width="186" height="15" align="center"
					style="border-right:1px solid #000000;">应付款单位或个人所缴金额</td>
				<td id="daxie">金额（大写）：&nbsp;&nbsp;<span id="6"></span>&nbsp;&nbsp;<span
					style="color:red">万</span>&nbsp;&nbsp;<span id="5"></span>&nbsp;&nbsp;<span
					style="color:red">仟</span>&nbsp;&nbsp;<span id="4"></span>&nbsp;&nbsp;<span
					style="color:red">佰</span>&nbsp;&nbsp;<span id="3"></span>&nbsp;&nbsp;<span
					style="color:red">拾</span>&nbsp;&nbsp;<span id="2"></span>&nbsp;&nbsp;<span
					style="color:red">元</span>&nbsp;&nbsp;<span id="1"></span>&nbsp;&nbsp;<span
					style="color:red">角</span>&nbsp;&nbsp;<span id="0"></span>&nbsp;&nbsp;<span
					style="color:red">分</span>
				</td>
			</tr>
		</table>

		<table width="620" border="0" cellpadding="3" cellspacing="3" style="font-size: 10px">
			<tr>
				<td height="30" align="left" style="color: red;padding: 0px;">填写说明:责任人属于支款责任人，往来单位属于收方单位（必填项），往来个人属于收方责任人</td>
			</tr>
			<tr>
				<td height="30" align="left">备注:${cashierBillsVO.remark}<br/>行政财务管理：<br />
				(1)日清月结、凡超24时、延迟1日、每日折扣相应责任人1分(20元)
				(2)无入库单折扣相应责任人1分(20元)<br />
				(3)无收入日清、每日折扣相应责任人1分(20元)
				(4)无支出日清、每日折扣相应责任人1分(20元)<br />
				(5)无资产日清出入库帐折扣相应责任人1分(20元)
				(6)未尽帐务事谊按相应处理
				(7)项目产品物品名称必须用词准确，用词不准确乱用词语每条扣1分<br />
				(8)项目产品物品名称必须填写所属单位、责任人、电话，少一项扣1分<br />
				(9)申请责任人流程必须按原则审核否则审核责任人按每项2分扣责任分<br />
				(10)以前用语不准，申请单、报销单请予及时纠正，不予纠正应给予严肃处理。<br />
				(11)会计部每月须完成以前三个月的问题单纠正任务。</td>
			</tr>
		</table>
		<table width="620" border="0" cellpadding="0" cellspacing="0">
			<%-- <tr>
<br>
<td colSpan="5">
<span class="sp">备注:${cashierBillsVO.remark}</span>
</td>
</tr> --%>
			<tr>
				<td height="25" align="right" width="80">公司经理：</td>
				<td width="40"><span>${bcheckMap["gsjl"] }</span></td>
				<td align="right" width="90">部门主管：</td>
				<td width="40"><span>${bcheckMap["bmzg"] }</span></td>
				<td align="right" width="70">人事处：</td>
				<td width="40"><span>${bcheckMap['rsc'] }</span></td>
				<td align="right" width="80">财务审核：</td>
				<td width="40"><span>${bcheckMap['cwsh'] }</span></td>
				<td align="center" width="80">收款人确认：</td>
				<td width="40"><span>${bcheckMap['skr'] }</span></td>
			</tr>
			<tr>
				<td height="25" align="right">总部总经理：</td>
				<td><span>${bcheckMap["zjl"] }</span></td>
				<td align="right">总部部门主管：</td>
				<td><span>${bcheckMap['zg'] }</span></td>
				<td align="right">总部人事处：</td>
				<td><span>${bcheckMap['zbrsc'] }</span></td>
				<td align="right">总财务审核：</td>
				<td><span>${bcheckMap['zbcw'] }</span></td>
				<td align="center">交款人确认：</td>
				<td><span>${bcheckMap['jkr'] }</span></td>
			</tr>
		</table>
		<br /> <br /> <br /> <br />
		<%-- <s:if test="BillCheckList!=null">
<table width="620" border="1" cellpadding="0" cellspacing="0">
<tr><td colspan="5" height=''20 align='center'>审核信息</td></tr>
<tr>
<td align='center' width="160">审核阶段</td>
<td align='center' width="180">审核时间</td>
<td align='center' width="80">审核人</td>
<td align='center' width="90">审核状态</td>
<td align='center' width="100">审核意见</td>
</tr>
<s:iterator value="BillCheckList">
<tr>
<td id='billtatus' align='center'>${billtatus}</td>
<td id='audittime' align='center'>${audittime}</td>
<td id='auditorname' align='center'>${auditorname}</td>
<td id='auditorstatus' align='center'>${auditorstatus=='01'?'未审核':auditorstatus=='02'?'审核通过':'驳回'}</td>
<td id='comments' align='center'>${comments}</td>
</tr>
</s:iterator>
</table>
</s:if><s:else>没有审核信息</s:else>
</div> --%>
		<br /> <br /> <br /> <br />
</body>
</html>
