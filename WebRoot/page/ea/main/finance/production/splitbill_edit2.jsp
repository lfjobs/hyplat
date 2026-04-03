<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		<title>拨款单、收款单</title>
		
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
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
			
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />	        
		
	<script type="text/javascript">
		var  tokens = 0;
		var basePath = "<%=basePath%>";
		var cashierBillsID="${cashierBills.cashierBillsID}";
		var token = 0;
		var pNumber=${pageNumber};
		var search="${search}";
		var pageNumber=<%=request.getParameter("pagepageNumber")%>;
		var notoken = 0;
	   	var sdate="${sdate}";
        var edate="${edate}";
        var zz="${zz}";
	   	var billsType="${cashierBillsVO.billsType}";
	   	var status="${cashierBillsVO.status}";
	   	var checkOutUrl="${checkOutUrl}";
	   	var urlsub=checkOutUrl.substring(0,checkOutUrl.indexOf("/",checkOutUrl.indexOf("/",checkOutUrl.indexOf("/")+1)+1)+1);
	   	var urlsh;
	   	$(function() {
	   		$(".jqmWindow").jqm({
				modal : true,// 限制输入（鼠标点击，按键）的对话
				overlay : 20
					// 遮罩程度%
			}).jqmAddClose('.close');// 添加触发关闭的selector
	   	
	   		if(billsType=="收款单"&&status=="09"&&urlsub=="/ea/splitbill/"){
	   			$("#qrsk").show();
	   		}
	   		if(status=="04"&&urlsub=="/ea/responsible/"){
	   			$("#zcwsh").show();
	   			urlsh="ea/responsible/ea_updateResponsible.jspa?search="+ search + "&pageNumber=" + pNumber
	   		}
	   		if(status=="05"&&urlsub=="/ea/accountant/"){
	   			$("#zcnsh").show();
	   			urlsh="ea/responsible/ea_updateResponsible.jspa?search="+ search + "&pageNumber=" + pNumber;
	   		}
	   		if(status=="06"&&urlsub=="/ea/cashier/"){
	   			$("#shtg").show();
	   			urlsh="ea/cashier/ea_updateCashier.jspa?search="+ search + "&pageNumber=" + pNumber;
	   		}
	   		
	   		//转财务审核按钮单击事件
	   		$("#zcwsh").click(function(){
	   			$("#jqModelSend").jqmShow();
	   			$("#CashierBillsform").find("input#status").val("05");
	   		});
	   		
	   		//转财务审核按钮单击事件
	   		$("#zcnsh").click(function(){
	   			$("#jqModelSend").jqmShow();
	   			$("#CashierBillsform").find("input#status").val("06");
	   		});
	   		
	   		//转财务审核按钮单击事件
	   		$("#shtg").click(function(){
	   			$("#jqModelSend").jqmShow();
	   			$("#CashierBillsform").find("input#status").val("07");
	   		});
	   		
	   		//确认收款按钮单击事件
	   		$("#qrsk").click(function(){
	   			$form = $("#CashierBillsform");
				$form
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "/ea/splitbill/ea_confirmReceivables.jspa?search="
										+ search + "&pageNumber=" + pNumber+ "&sdate=" + sdate + "&edate="
					+ edate+ "&zz=" + zz);
				$form.find("input#cashierBillsID").val(cashierBillsID);
				document.CashierBillsform.submit.click();
				cashierBillsID = "";
				token = 12;
	   		});
	   		
	   		//返回按钮单击事件
	   		$("#fh").click(function(){
	   			re_load();
	   		});
	   		
	   		$("#submitResult").click(function(){
		
				if ($.trim($("#SendForm #comments").val()) == "") {
					alert("请填写审核意见");
					return;
				}
				$(".ckTextLength").trigger("blur");
				if ($("#SendForm .error").length) {
					notoken = 0;
					return;
				 }
				$form = $("#CashierBillsform");
				if (confirm("确定执行该操作？")) {
					$form
							.attr("target", "hidden")
							.attr(
									"action",
									basePath+ urlsh);
					$form.find("input#cashierBillsID").val(cashierBillsID);
					$form.find("input#comments").val($("#SendForm #comments").val());
					cashierBillsID = "";
					token = 12;
					document.CashierBillsform.submit.click();
				}
			});
	   	});
	   	
	   	function re_load() {
			var url = basePath+ checkOutUrl;
			document.location.href = encodeURI(url);
		}
	</script>
	<style type="text/css">
	body{ margin:0; padding:0px;}
	html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym,
	address, big, cite, code, del, dfn, em, font, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt,
	var, b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td{
		  margin:0;
		  padding:0;
		  border:0;
		  outline:0;
		  font-family: '宋体 Regular','宋体';
		  -webkit-touch-callout: none;
		  -webkit-user-select: none;
		  -webkit-tap-highlight-color: transparent;
		  font-size:14px;
		}
		a, a:link { color: #222; text-decoration: none; }
		a:focus { outline: none; }
		.fl{ float:left;}
		.fr{ float:right;}
		.tab1,.tab2{ background-color:#99BBE8;}
		.tab1 tr td{ background-color:#fff; height:20px; line-height:20px;}
		.tab2{margin-top:1px;}
		table td{ overflow:hidden;}
		.tab2 tr td{ background-color:#DAE7F6; height:20px; line-height:20px;}		
		.ACT_btn {
			background: url(<%=basePath%>images/admin_images/act_btn.gif) no-repeat;
			height: 26px;
			width: 94px;
			cursor: pointer;
			border: solid 0px #111;
			font-size: 12px;
			margin: 10px;
		}
		.tab2 th {color: #1E5494;background: #E4F1FA;}
		.floor{ width:900px;}
	</style>
  </head>

<boby>
<div style="height:100%; width:100%">
	<div align="center">
		<table border="0" cellpadding="0" cellspacing="0"
			style="background:#FFFFFF; width:900px; height: 100px">
			<tr style="height:50px;">
				<td colspan='2' align="center" height="35px"><span
					style="font-size:20px;font-weight:bold;">${cashierBillsVO.billsType}</span>
				</td>
			</tr>
			<tr>
				<td style="width:70%"><span>转账日期：
						${fn:substring(cashierBillsVO.cashierDate, 0, 10)}</span>
				</td>
				<td><span>编号：${cashierBillsVO.journalNum}</span>
				</td>
			</tr>
		</table>
		<div class="box" align="center"
			style=" width: 920px; background-color:#DAE7F6;">
			<h6 style=" height:10px; width:100%;"></h6>
			<table class="tab1" cellpadding="1" cellspacing="1"
				style="height:110px; width:900px;">
				<tr>
					<td style="width:35%; height:35px;"><span>收款公司：<s:if
								test="cashierBillsVO.billsType=='收款单'">${cashierBillsVO.companyname}</s:if>
							<s:else>${cashierBillsVO.ccompanyname }</s:else> </span></td>
					<td style="width:15%"><span>收款部门：<s:if
								test="cashierBillsVO.billsType=='收款单'">${cashierBillsVO.departmentname}</s:if>
							<s:else>${cashierBillsVO.oorgname }</s:else>
					</span></td>
					<td style="width:15%"><span>收款责任人：<s:if
								test="cashierBillsVO.billsType=='收款单'">${cashierBillsVO.staffname}</s:if>
							<s:else>${cashierBillsVO.contactUserName }</s:else>
					</span></td>
					<td style="width:30%"><span>收款帐号：<span style="font-size: 5px"><s:if
								test="cashierBillsVO.billsType=='收款单'">${cashierBillsVO.companyBankNum}</s:if>
							<s:else>${cashierBillsVO.zzAccountNum}</s:else></span></span>
					</td>
				</tr>
				<tr>
					<td height="35px"><span>付款公司：<s:if
								test="cashierBillsVO.billsType=='拨款单'">${cashierBillsVO.companyname}</s:if>
							<s:else>${cashierBillsVO.ccompanyname }</s:else>
					</span></td>
					<td><span>付款部门：<s:if
								test="cashierBillsVO.billsType=='拨款单'">${cashierBillsVO.departmentname}</s:if>
							<s:else>${cashierBillsVO.oorgname }</s:else>
					</span></td>
					<td><span>付款责任人：<s:if
								test="cashierBillsVO.billsType=='拨款单'">${cashierBillsVO.staffname}</s:if>
							<s:else>${cashierBillsVO.contactUserName }</s:else>
					</span></td>
					<td><span>付款帐号：<s:if
								test="cashierBillsVO.billsType=='拨款单'">${cashierBillsVO.companyBankNum}</s:if>
							<s:else>${cashierBillsVO.zzAccountNum}</s:else>
					</span></td>
				</tr>
				<tr>
					<td colspan="4" align="left" height="20px"><input
						class="ACT_btn" type="button" value="关联票据" /></td>
				</tr>
			</table>
			<table class="tab2" cellpadding="1" cellspacing="1"
				style="width:900px;" border="0px">
				<tr>
					<th height="20px">序号</th>
					<th>物品名称</th>
					<th>编号</th>
					<th>规格</th>
					<th>品牌</th>
					<th>单价</th>
					<th>数量</th>
					<th>重量</th>
					<th>单位</th>
					<th>金额</th>
				</tr>
				<c:set var="monay" value="0"></c:set>
				<s:iterator value="pageForm.list">
					<tr id="${goodsBillsID}">
						<td height="20px"></td>
						<td>${goodsName}</td>
						<td>${goodsCoding}</td>
						<td>${standard}</td>
						<td>${mnemonicCode}</td>
						<td>${price}</td>
						<td>${quantity}</td>
						<td>${weight}</td>
						<td>${variableID}</td>
						<td>${balance} <c:set var="monay" value="${balance+monay}"></c:set>
						</td>
					</tr>
				</s:iterator>
				<tr>
					<th height="20px"></th>
					<th>合计</th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th>${monay }</th>
				</tr>

			</table>
			<h6 style=" height:10px; width:100%;"></h6>
		</div>
		<br />
		<div class="floor" align="left">
			<span style="color: red;font-size:15px;">一式两联，本联为收款单据</span>
		</div>
		<div class="floor" align="center">
			<form name="CashierBillsform" id="CashierBillsform">
				<input type="submit" name="submit" style="display:none" />
				<input name="comments" id="comments" style="display:none" />
				<input name="cashierBills.cashierBillsID" id="cashierBillsID" style="display:none" />
				<input class="ACT_btn" type="button" value="确认收款" id="qrsk" style="display: none;" />
				<input class="ACT_btn" type="button" value="转财务审核" id="zcwsh" style="display: none;" />
				<input class="ACT_btn" type="button" value="转出纳审核" id="zcnsh" style="display: none;" />
				<input class="ACT_btn" type="button" value="审核通过" id="shtg" style="display: none;" />
				<input class="ACT_btn" type="button" value="返回" id="fh"/>
				<s:token />
			</form>
		</div>
	</div>
</div>

<!-- -----------------------------------审核-------------------------------- -->
	<form name="SendForm" id="SendForm2" method="post">
		<div class="jqmWindow"
			style="display: none; width: 500px; height: 230px; right: 20%; top: 10%;"
			id="jqModelSend">
			<input type="submit" name="submit" style="display: none" />
			<div class="contentbannb">
				<div class="drag">
					审核
					<div class="close"></div>
				</div>
			</div>
			<center>
			<table width="100%" id="SearchTable" cellspacing="20"
				cellpadding="20">
				<tr>
					<td align="right" width='15%'>审核意见：</td>
					<td align="left"><textarea rows="5" cols="40" style="max-width: 270px;max-height: 100px;" 
							name="comments" id="comments" class="ckTextLength"
							maxlength="1000"></textarea></td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="submitResult"
					value=" 提交 " /> <input type="hidden" name="cashierBills.status"
					id="status" />
					<input name="cashierBills.cashierBillsID" id="cashierBillsID"
			style="display:none" />
			</div>
			</center>
		</div>
	</form>
</boby>
</html>
