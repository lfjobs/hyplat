<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>初始项目单据打印</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
		<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>js/ea/office_ea/contract/jQuery.print.min.js" type="text/javascript" charset="utf-8"></script>
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/finance/invoicing/csbsprint.js"></script>
		<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid #000000;
	font-size: 9px;
}

.table th {
	border: 1px solid #000000;
	color: #000000;
}

.table td {
	border: 1px solid #000000;
	color: #000000;
}

body,td,th {
	font-size: 12px;
}

body {
	margin-left: 15px;
}

#apDiv1 {
	position: absolute;
	left: 507px;
	top: 287px;
	width: 63px;
	height: 32px;
	z-index: 1;
}
.div-bottom {
	margin-left: 300px;
}
.div-bottom p {
	background-color: #83818d;
	border-radius: 0.4rem;
	width: 2.5rem;
	margin-left: 60px;
	text-align: center;
	height: 2.5rem;
	line-height: 2.5rem;
	font-size: 1rem;
	color: #fff;
}
</style>
<script type="text/javascript">
	var taxstatusDu="${cashierBills.status}";
    var basePath = "<%=basePath%>";
</script>
	</head>
	<body>
	<div class="div-bottom">
		<p onclick="print()">
			打印
		</p>
	</div>
		<%--<div id="apDiv1"></div>
		--%><div id="tableprint" align="center">
			<table width="1280" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">

				<tr>
					<td height="25" align="center" style="font-weight: bold">
						${(cashierBills.billsType=='收入' || cashierBills.billsType=='支出') ? '初始项目单':cashierBills.billsType}
					</td>
				</tr>
			</table>
			<table width="1280" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
				</tr>
				<tr>
					<c:if test="${!empty cashierBills.companyName }">
						<td width="2%" height="25" align="left">
							公司：
						</td>
						<td width="30%" align="left">
								${cashierBills.companyName}
						</td>
						<td width="2%" align="left">
							部门：
						</td>
						<td width="13%" align="left">
								${cashierBills.departmentName}
						</td>
						<td width="3%" align="left">
							责任人：
						</td>
						<td width="20%" align="left">
							<c:if test="${cashierBills.staffName!=null}">${cashierBills.staffName}</c:if>
						</td>
					</c:if>
				</tr>
			</table>
			<table width="1280" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td width="12%" align="left">
						制单人：${cashierBills.inputName}
					</td>
					<td align="left" colspan="2">
						制单日期：${fn:substring(cashierBills.cashierDate, 0, 10)}
					</td>
				</tr>
				<tr>
					<td align="left" width="400px">
						<c:if test="${!empty cashierBills.ctUserName }">
							往来个人：${cashierBills.ctUserName}
						</c:if>
					</td>
					<td align="left" width="249px">
						<c:if test="${!empty cashierBills.startTime }">
							开始日期：${fn:substring(cashierBills.startTime, 0, 10)}
						</c:if>
					</td>
					<td align="left" width="400px">
						<c:if test="${!empty cashierBills.endTime }">
							结束日期：${fn:substring(cashierBills.endTime, 0, 10)}
						</c:if>
					</td>
				</tr>
				<tr>
					<td colspan="3" align="left">
						凭证号：${cashierBills.journalNum}
						<%
						hy.ea.bo.finance.CashierBills data = (hy.ea.bo.finance.CashierBills) request
								.getAttribute("cashierBills");
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
					%>
					</td>
<%--					<td width="18%" align="right">--%>
<%--						总金额：${cashierBills.priceSub}--%>
<%--					</td>--%>
				</tr>
				<tr>
					<td align="left" colspan="1">
						项目名称：${cashierBills.projectName}
					</td>
					<td align="left" colspan="1" id="cashierBillsStatus">
					</td>
					<td align="left" colspan="1">
						<c:if test="${cashierBills.billsType == '入库单' || cashierBills.billsType == '出库单' || cashierBills.billsType == '盘库单' || cashierBills.billsType == '报损单'}">
							仓库：${null != cashierBills.dataDepotName ? cashierBills.dataDepotName : ""}
						</c:if>
					</td>
				</tr>
			</table>
			<table width="1280" cellpadding="0"  style="margin-top:5px;"cellspacing="0" class="table">
				<tr>
					<th align="center">
						序号
					</th>
<%--					<th align="center">--%>
<%--						产品编号--%>
<%--					</th>--%>
					<th align="center">
						产品名称
					</th>
					
					<th align="center">
						单位
					</th>
					<th align="center">
						规格
					</th>
					<th align="center">
						数量
					</th>
					<th align="center">
						预算单价
					</th>
					<th align="center">
						预算总金额
					</th>
					<th align="center">
						收方名称
					</th>
					<th align="center">
						付方名称
					</th>
				</tr>
				<s:iterator value="goodBeanslist" status="number">
					<tr>
						<td align="center" bgcolor="#FFFFFF">
							<s:property value="%{#number.index+1}"/>
						</td>
						<!-- 产品编号 -->
<%--						<td align="center" bgcolor="#FFFFFF">--%>
<%--							<span id="goodsNum">${goodsNum}</span>--%>
<%--						</td>--%>
						<!-- 产品名称 -->
						<td align="left" bgcolor="#FFFFFF">
							<span id="goodsName">${goodsName}</span>
						</td>
						
						<!-- 单位 -->
						<td align="center" bgcolor="#FFFFFF">
							${variableId}
						</td>
						<!-- 规格 -->
						<td align="center" bgcolor="#FFFFFF">
								${guigeTypeValue}
						</td>
						<!-- 数量 -->
						<td align="center" bgcolor="#FFFFFF">
							${budgetNumber}
						</td>
						<!-- 单价 -->
						<td align="center" bgcolor="#FFFFFF">
							${unitPrice}
						</td>
						<!-- 总金额 -->
						<td align="center" bgcolor="#FFFFFF">
							<span id="money" class="moneysum">${amount}</span>
							
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<span id="accountShow" >${accountShow}</span>
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<span id="accountShowFrom" >${accountShowFrom}</span>
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td height="15" align="center" bgcolor="#FFFFFF">
						合计
					</td>
<%--					<td align="center" bgcolor="#FFFFFF"></td>--%>
					<td align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
					<td align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
					<td align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
					<td align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
					<td align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
					<td align="center" bgcolor="#FFFFFF">
						<span id="monsum"></span>
					</td>
					<td align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
					<td align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
				</tr>
			</table>
			<table width="1280" border="0" cellpadding="0" cellspacing="0"
				style="border: 1px solid #000000;border-top:none;"
				<tr>
					<td width="186" height="15" align="center"
						style="border-right: 1px solid #000000;">
						应付款单位或个人所缴金额
					</td>
					<td id="daxie">
						金额（大写）：&nbsp;&nbsp;
						<span id="6"></span>&nbsp;&nbsp;
						<span style="color: red">万</span>&nbsp;&nbsp;
						<span id="5"></span>&nbsp;&nbsp;
						<span style="color: red">仟</span>&nbsp;&nbsp;
						<span id="4"></span>&nbsp;&nbsp;
						<span style="color: red">佰</span>&nbsp;&nbsp;
						<span id="3"></span>&nbsp;&nbsp;
						<span style="color: red">拾</span>&nbsp;&nbsp;
						<span id="2"></span>&nbsp;&nbsp;
						<span style="color: red">元</span>&nbsp;&nbsp;
						<span id="1"></span>&nbsp;&nbsp;
						<span style="color: red">角</span>&nbsp;&nbsp;
						<span id="0"></span>&nbsp;&nbsp;
						<span style="color: red">分</span>
					</td>
				</tr>
			</table>
			<table width="1280" border="0" cellpadding="3" cellspacing="3">
<%--			<tr>--%>
<%--				<td height="30" align="left" colspan="2" style="color: red">填写说明:责任人属于支款责任人，往来单位属于收方单位（必填项），往来个人属于收方责任人</td>--%>
<%--			</tr>--%>
			<tr>
<%--				<td height="30" align="left" colspan="2">备注:${cashierBillsVO.remark}<br/>行政财务管理：<br />--%>
<%--				(1)日清月结、凡超24时、延迟1日、每日折扣相应责任人1分(20元)--%>
<%--				(2)无入库单折扣相应责任人1分(20元)<br />--%>
<%--				(3)无收入日清、每日折扣相应责任人1分(20元)--%>
<%--				(4)无支出日清、每日折扣相应责任人1分(20元)<br />--%>
<%--				(5)无资产日清出入库帐折扣相应责任人1分(20元)--%>
<%--				(6)未尽帐务事谊按相应处理<br />--%>
<%--				(7)项目产品物品名称必须用词准确，用词不准确乱用词语每条扣1分<br />--%>
<%--				(8)项目产品物品名称必须填写所属单位、责任人、电话，少一项扣1分<br />--%>
<%--				(9)申请责任人流程必须按原则审核否则审核责任人按每项2分扣责任分<br />--%>
<%--				(10)以前用语不准，申请单、报销单请予及时纠正，不予纠正应给予严肃处理。<br />--%>
<%--				(11)会计部每月须完成以前三个月的问题单纠正任务。</td>--%>
			</tr>
		</table>
			<table width="1280" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="80" height="25" align="right">
						公司经理：
					</td>
					<td>
						&nbsp;
					</td>
					<td width="90" align="right">
						部门主管：
					</td>
					<td>
						&nbsp;
					</td>
					<td width="80" align="right">
						人事处：
					</td>
					<td>
						&nbsp;
					</td>
					<td width="80" align="right">
						财务审核：
					</td>
					<td>
						&nbsp;
					</td>
					<td width="80" align="center">
						收款人签字：
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="80" height="25" align="right">
						总部总经理：
					</td>
					<td>
						&nbsp;
					</td>
					<td width="90" align="right">
						总部部门主管：
					</td>
					<td>
						&nbsp;
					</td>
					<td width="80" align="right">
						总部人事处：
					</td>
					<td>
						&nbsp;
					</td>
					<td width="80" align="right">
						总财务审核：
					</td>
					<td>
						&nbsp;
					</td>
					<td width="80" align="center">
						交款人签字：
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
		<br />
		<br />
		<br />
		<br />
	</body>
<script>
	$(document).ready(function() {
		$("#cashierBillsStatus").html("单据状态：" + getStrutsStr(taxstatusDu));
	});
	function print(){
		var u = navigator.userAgent;
		var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

		if(isAndroid || isiOS) {
			alert("请在电脑端进行打印操作");
			return;
		}


		$("#tableprint").print({

			globalStyles:true,//是否包含父文档的样式，默认为true
			mediaPrint:false,//是否包含media=print的链接标签，会被globalStyles选项覆盖，默认为false
			stylesheet:null,//外部样式表的url地址，默认为null
			noPrintSelector:".no-print",//不想打印的元素的Jquery选择器，默认为".no-print"
			iframe:true,//是否使用一个Iframe来替代打一遍表单的弹出窗口，true未在本页面进行打印，false新开一个页面打印，默认为ture
			append:null,//将内容添加到打印机内容的后面
			prepend:null,//将内容添加到打印机内容的前面，可以用来作为要打印内容
			deferred:$.Deferred()//会掉函数

		});
	}
	function getStrutsStr(status){
		var strutsStr = "";
		if(status == "00"){
			strutsStr="拟稿";
		}else if(status == "01"){
			strutsStr="审核中-招标前";
		}else if(status == "02"){
			strutsStr="已通过-招标前";
		}else if(status == "03"){
			strutsStr="比价审核中";
		}else if(status == "04"){
			strutsStr="已提交资金申请";
		}else if(status == "05"){
			strutsStr=" 待会计审核";
		}else if(status == "06"){
			strutsStr="待出纳审核";
		}else if(status == "07"){
			strutsStr="已审核";
		}else if(status == "20"){
			strutsStr="税务单据";
		}else if(status == "08"){
			strutsStr="三审已归档";
		}else if(status == "09"){
			strutsStr="待确认收款";
		}else if(status == "40"){
			strutsStr="待确定预算收入单";
		}else if(status == "45"){
			strutsStr="已收款";
		}else if(status == "46"){
			strutsStr="系统生成";
		}else if(status == "11"){
			strutsStr="驳回待修改";
		}else if(status == "50"){
			strutsStr="传阅中";
		}else if(status == "15"){
			strutsStr="已入库";
		}else if(status == "16"){
			strutsStr="已出库";
		}
		return strutsStr;
	}
</script>
</html>
