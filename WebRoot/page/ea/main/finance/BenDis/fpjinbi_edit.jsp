<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<title>预算单据--查看</title>
		
		<%@page import="hy.ea.bo.Company"%>
		<%@page import="java.util.Date"%>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Company c = (Company) session.getAttribute("currentcompany");
			if(session==null||c==null){
				response.sendRedirect(basePath+"page/ea/index.jsp");
			}
		%>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
		<link rel="stylesheet" type="text/css"	href="<%=basePath%>css/overlayer.css" />
		<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css"	media="screen" />
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/finance/yongjin.css"/>
<style type="text/css">
#table3 input{
    height:20px;
}

.bg{
	table-layout: fixed;
    border-left: 1px solid #000;
    border-top: 1px solid #000;
}
.bg td {
    border-bottom: 1px solid #000;
    border-right: 1px solid #000;
    line-height: 30px;
}
.bg tbody:first-child tr:first-child td{background: #e4f1fa;}

.bold td{
	font-weight:bold;
}


</style>

<script type="text/javascript">
  	var treeID = "<%=session.getAttribute("organizationID")%>";
  	var treeNames="<%=session.getAttribute("organizationName")%>";
  	var gongsiname="<%=c.getCompanyName()%>";
	var tokens = 0;
	var basePath = "<%=basePath%>";
	var jumptype = "${jumptype}";
	var status = "${cashierBills.status}";
	var cashierBillsID  = "${cashierBills.cashierBillsID}";
	var xmtype="${cashierBills.xmtype}";
	var billsType = "${cashierBills.billsType}";
	var zctype="${zctype}";
	var proID = "${cashierBills.proID}";
	var projectName = "${cashierBills.projectName}";
	var select = 1;
	var sl=0;
	var money=0.0;
</script>
   <script type="text/javascript">
		
	function MouseDownToResize(obj) {
		setTableLayoutToFixed();
		obj.mouseDownX = event.clientX;
		obj.pareneTdW = obj.parentElement.offsetWidth;
		obj.pareneTableW = goodtable.offsetWidth;
		obj.setCapture();
	}

   
	function MouseMoveToResize(obj) {
		if (!obj.mouseDownX)
			return false;
		var newWidth = obj.pareneTdW * 1 + event.clientX * 1 - obj.mouseDownX;
		if (newWidth > 0) {
			obj.parentElement.style.width = newWidth;
			goodtable.style.width = obj.pareneTableW * 1 + event.clientX * 1
					- obj.mouseDownX;
		}
	}
	function MouseUpToResize(obj) {
		obj.releaseCapture();
		obj.mouseDownX = 0;
	}
	function setTableLayoutToFixed() {
		if (goodtable.style.tableLayout == 'fixed')
			return;
		var headerTr = goodtable.rows[0];
		for ( var i = 0; i < headerTr.cells.length; i++) {
			headerTr.cells[i].styleOffsetWidth = headerTr.cells[i].offsetWidth;
		}

		for ( var i = 0; i < headerTr.cells.length; i++) {
			headerTr.cells[i].style.width = headerTr.cells[i].styleOffsetWidth;
		}
		goodtable.style.tableLayout = 'fixed';
	}
</script>

	</head>
<body style="background:#ffffff;border-top:5px solid #c5d9f1;">
	<!-- 项目预算添加表单开始 -->
	<form name="CostSheetForm" id="CostSheetForm" method="post"
		enctype="multipart/form-data">
		<div id="Layer11" align="center" style="height:900px; ">
			<!-- maindiv-->
			<input type="submit" name="submit" style="display: none" />
			<div id="content1">
				<table id="table3" cellpadding="20"
					cellspacing="5">
					<tr>
						<td align="center" colspan="6" id="titlestyle"><span>金币分配明细</span><input
							type="hidden" name="cashierBills.billsType" />
						</td>
					</tr>
					<tr>
						<td align="right">公司：</td>
						<td><input type="text" readonly class="inputbottom1"
							style="width:100%;" name="cashierBills.companyName"
							value="${cashierBills.companyName}" /> <input type="hidden"
							name="zctype" value="${zctype}" /></td>
						<td align="right">创收部门：</td>
						<td id="dept"><input type="text" id="departmentName"
							class="inputbottom1 notnull" name="cashierBills.departmentName"
							value="${cashierBills.departmentName}"
							style="width:100%;cursor:pointer;" onclick="getDeptInfo()" /></td>
						<td align="right">订单编号：</td>
						<td class="yz"><input type="text" class="inputbottom1"
							style="width:100%;" id="journalNum"
							name="cashierBills.journalNum" readonly="readonly"
							value="${cashierBills.journalNum}" /></td>

					</tr>
					<tr>
						<td height="20" align="right">用户名称：</td>
<td style="width:210px;"><input type="text" id="xmtypename"
							readonly name="cashierBills.xmtypename"
							value="${shr.staffName}" class="inputbottom1"
							style="width:100%;"/></td>


						<td align="right"><span class="STYLE1">用户账号：</span>
						</td>
						<td style="width:200px;"><input type="text" id="projectName"
							readonly name="cashierBills.projectName"
							class="inputbottom1"
							value="${te.account}" style="width:100%;" /></td>
						<td align="right">用户会员类型：</td>
						<td class="yz"><input type="text" class="inputbottom1"
							style="width:100%;" id="journalNum" name="cashierBills.wfthytpe"
							readonly="readonly" value="${wfjzh.cusType=='1'?'平台':wfjzh.cusType=='2'?'公司平台商城会员':wfjzh.cusType=='3'?'合伙创业商城会员':wfjzh.cusType=='4'?'微分金商城会员':wfjzh.cusType=='5'?'代理商商城会员':wfjzh.cusType=='6'?'消费者会员':'消费者'}" /></td>
					</tr>
					<tr>
						<td height="20" align="right">商家：</td>

						<td style="width:200px;"><input type="text"
							id="xmtypename" readonly name="cashierBills.xmtypename"
							value="${cashierBills.companyName}" class="inputbottom1"
							style="width:100%;"/></td>
						<td height="20" align="right">支付方式：</td>

						<td style="width:200px;"><input type="text"
							id="xmtypename" readonly name="cashierBills.xmtypename"
							value="${cashierBills.wfStatus4=='00'?'微信支付':cashierBills.wfStatus4=='01'?'支付宝支付':cashierBills.wfStatus4=='02'?'银联支付':'线下转账'}" class="inputbottom1"
							style="width:100%;"/></td>
						<td align="right">物流单位：</td>
						<td class="yz"><input type="text" class="inputbottom1"
							style="width:100%" id="journalNum" name="cashierBills.wlcomname"
							readonly="readonly" value="${cashierBills.wlcomname}" /></td>
					</tr>
					<tr>
						<td height="20" align="right">收货信息：</td>
						<td colspan="5"><input type="text" id="xmtypename" readonly
							name="cashierBills.xmtypename"
							value="    ${ord.receivename}   ${ord.receivetel}   ${ord.receiveaddress}"
							class="inputbottom1" style="width:100%;"/>
						</td>
					</tr>
					<tr>
						<td height="20" align="right">发货信息：</td>
						<td colspan="5"><input type="text" id="xmtypename" readonly
							name="cashierBills.xmtypename"
							value="${ord.sendname}   ${ord.sendtel}   ${ord.sendaddress}"
							class="inputbottom1" style="width:100%;"/>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="Layer10" align="left" style="margin-top: 20px;height:600px;">
				<table width="100%" cellspacing="0" class="bg">
					<tr class='bold'>
						<td width="20%" align="center">产品名称</td>
						<td width="20%" align="center">出厂价</td>
						<td width="20%" align="center">零售价</td>
						<td width="20%" align="center">业务佣金</td>
						<td width="20%" align="center">代理佣金</td>
					</tr>
					<s:iterator value="goodBillslist" var="pc" status="pcr">
						<tbody >
							<tr>
								<td align="center">${pc.goodsName }</td>
								<td align="center">${pc.costmoney*pc.quantity }</td>
								<td align="center">${pc.price*pc.quantity }</td>
								<s:if test="#pc.proSetup != null">
									<td align="center" class="yewu" name="yewu${pcr.index }">${pc.proSetup.brokerage*pc.quantity }</td>
									<td align="center" class="daili" name="daili${pcr.index }">
									<fmt:formatNumber value="${pc.proSetup.proxyprice==null?0:pc.proSetup.proxyprice*pc.quantity }"></fmt:formatNumber>
									</td>
								</s:if>
								<s:else>
									<td align="center" class="yewu"><input name="brokerage" id="yj"
										size="3" class="cp money${pcr.index}" readonly
										style="width:100%; border:0px; text-align: right;"
										value="${pc.price*pc.quantity-pc.costmoney*pc.quantity}" />
										<script type="text/javascript">
											$(function() {
												var money="${pc.money}";
												var num=money.indexOf("-");
									    	    if(num!=-1){
									    		   var money2=money.substring(num);
									    		   money=money.substring(0,num);
									    		   money=$(".money${pcr.index}").val()+money2;
									    		   $(".money${pcr.index}").val(money);
									    	   }
										});
									</script>
									</td>
									<td align="center" class="daili">0</td>
								</s:else>
							</tr>
						</tbody>
					<tbody class="overlay_table"  id="yewu${pcr.index }" style="background: #eee;display:none;">
						<tr class='bold'>
							<td width="20%" align="center">会员名称</td>
							<td width="20%" align="center">比例</td>
								<td width="20%" align="center">金币</td>
								<td width="20%" align="center">会员名称</td>
								<td width="20%" align="center">会员账号</td>
						</tr>		
						<c:forEach items="${pc.pBeans}" var="zx">
						<tr>
							<td align="center">${zx.goodsName}</td>
							<td align="center" class="jbs${pcr.index }${zx.ppID }1"><c:forEach items="${pc.objects }" var="aa"><c:if test="${zx.model==aa[0] }">${aa[2]}</c:if></c:forEach></td>
							<td align="center" class="jbs${pcr.index }${zx.ppID }2"></td>
							<td align="center" class="jbs${pcr.index }${zx.ppID }3"><c:forEach items="${pc.objects }" var="aa"><c:if test="${zx.model==aa[0] }">${aa[1]}</c:if></c:forEach></td>
							<td align="center" class="jbs${pcr.index }${zx.ppID }4"><c:forEach items="${pc.objects }" var="aa"><c:if test="${zx.model==aa[0] }">${aa[4]}</c:if></c:forEach></td>
						</tr>
						<script type="text/javascript">
							$(function() {
								var money="${pc.money}";
								var num=money.indexOf("-");
								var money2=0;
								var c = "${pcr.index }${zx.ppID}";
					    	   	if(num!=-1){
					    		   money2=parseFloat(money.substring(num+1));
					    		   console.log(c+ "-"+ "money2:"+ money2);
					    		}
					    	   	var a = 0;
					    	   	if("${pc.proSetup}" != null)
					    	   	{
					    	   		a = "${pc.proSetup.brokerage*pc.quantity}";
					    	   	}
					    	   	else
					    	   	{
					    	   		a = "${pc.price*pc.quantity-pc.costmoney*pc.quantity}";
					    	   	}
								console.log(c+ "-"+ "a:"+ a);
								var b = $(".jbs${pcr.index }${zx.ppID}1").text();
								console.log(c+ "-"+ "b:"+ b);
								if (b != ""&& b != null&& b != " ") 
								{
									var d = (parseFloat(a == "" ? "0.00": a)-money2)
											* parseFloat(b == "" ? "0.00": b)
									console.log(c+ "-"+ d)
									$(".jbs"+ c+"2").text(d.toFixed(2));
								}
							});
						</script>
						</c:forEach>
						<script type="text/javascript">
							$(function(){
								sl=sl+${pc.quantity};
								money=money+${pc.money};
							});
						</script>
					</tbody>
					<s:if test="#pc.backList.size > 0">
						<tbody class="overlay_table" id="daili${pcr.index }" style="background: #eee;display:none">
							<tr class='bold'>
								<td align="center" width="25%">代理名称</td>
								<td align="center" width="25%">积分数</td>
								<td align="center" width="25%">会员名称</td>
								<td align="center" width="25%" colspan="2">会员账号</td>
							</tr>
							<c:forEach items="${pc.backList}" var="dl">
								<tr>
									<td align="center">${dl[15] }</td>
									<td align="center">${dl[11] }</td>
									<td align="center">${dl[8] }</td>
									<td align="center" colspan="2">${dl[6] }</td>
								</tr>
							</c:forEach> 
						</tbody>
					 </s:if>
					</s:iterator>
				</table>					
			</div>
			<s:token></s:token>
	</form>
	<div style="margin-top:20px; margin-left: 20px" align="left">
		下单日期：<input type="text" class="inputbottom1" id="createD"   readonly="readonly"
			value="<fmt:formatDate value="${cashierBills.cashierDate}" pattern="yyyy-MM-dd" />"
			size="20"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 订单状态：<input type="text" readonly="readonly" value="${cashierBills.fkStatus=='01'?'未付款':cashierBills.fkStatus=='00'?'已付款未发货':cashierBills.fkStatus=='02'?'已出库正在物流':cashierBills.fkStatus=='03'?'用户已收货':'已分配金币'}"
			class="inputbottom1" style="cursor:pointer;" size="15"/><%----------onclick="getStaffInfo('${cashierBills.fkStatus}')"---------%>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 产品总数量：<input type="text" class="inputbottom1" id="b" 
			style="cursor:pointer;"	 size="15"/><!-- readonly="readonly" -->
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 总金额：<input type="text" class="inputbottom1" id="c"  readonly="readonly"
			style="cursor:pointer;" size="15"/><%-- onclick="getStaffInfo('${cashierBills.inputid}')" --%>
			</p>
	</div>
	<%------------------------------------用于表单提交-----------------------------------%>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
<script type="text/javascript">
$(function(){

	var a=0;
	$(".a").each(function(){
		a= parseFloat($(this).text())+a;
	})
	$("#c").val(a);
	var f=0;
	$(".d").each(function(){
		f= parseFloat($(this).text())+f;
	});
	$("#b").val(f);

	$("#b").val(sl);

	$("#c").val(money);
	$(".wfj11_111_height").each(function(){
		var $prent=$(this).parent().parent().parent();
		var $td=$(this).find("td");
		//$td.attr("style","height:"+(parseInt(($prent.find(".tbleft").find("tr").length-2)*25-1))+"px;");
		$td.find("td").eq(0).css("padding-left","5px");
		$td.find("td").eq(3).css("border-right","none");
	});
	$(".yewu,.daili").mouseenter(function(){
		var $id=$(this).attr("name");
		$(".overlay_table").hide();
		$("#"+$id).show();
		})
	$(".overlay_table").mouseleave(function(){
		$(this).hide();
	})
});
</script>
</body>

</html>

