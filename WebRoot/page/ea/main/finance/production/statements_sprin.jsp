<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>单据归档流水账</title>
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
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid #000000;
	font-size: 9px;
}

body {
	margin-left: 15px;
}
</style>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var sfdate="${sfdate}";
    var efdate="${efdate}";
    var tt="${tt}";
    var pan="${pan}";
    var jiez=0.0;
 	var daiz=0.0;
    var chuanzhi="${chuanzhi}";
    $(function() {
		if(pan=="01"){
	 	$("#buttonClone").hide();
	 }
	});
   
</script>
</head>
<body>
	<div id="buttonClone" style="text-align: center">
		<input id="dayinClone" type="button" class="printpage input-button" value="打印" />
		<input id="xsxyClone" class="daochu input-button" type="button" value="导出" />
		<input id="xsqbClone" class="closepage input-button" type="button" value="关闭" />
	</div>
	<table border="0" cellspacing="0" cellpadding="0" align="center"
		width="100%">
		<tr>
			<td colspan="48" align="center"><span style="font-size: 20px">${companyname}<c:if
						test="${tt=='z'}">${organizationname}</c:if>${ sfdate
					}至${efdate}流水汇总报表</span>
			</td>
		</tr>
	</table>
	<table border="1" cellspacing="0" cellpadding="0" align="center"
		width="100%" class="table">
		<thead>
			<tr>
				<th colspan="3">公司部门岗位职务责任人管理</th>
				<th>公司部门银行</th>
				<th colspan="6">票据管理</th>
				<th colspan="3">日期管理</th>
				<th colspan="4">产品编号管理</th>
				<th colspan="2">科目管理</th>
				<th colspan="2">产品管理</th>
				<th colspan="10">产品规格信息管理</th>
				<th>库房管理</th>
				<th width="5%">借方金额</th>
				<th>方向</th>
				<th width="5%">贷方金额</th>
				<th>余额</th>
			</tr>
			<tr>
				<th width="100px">公司</th>
				<th>部门</th>
				<th>责任人</th>
				<th>银行帐号</th>
				<th>单据类别</th>
				<th>票据支票管理</th>
				<th>粘贴单据号</th>
				<th>票据编号</th>
				<th>票据归档号</th>
				<th>附件张数</th>
				<th>款源日期</th>
				<th>入账日期</th>
				<th>有效日期</th>
				<th>批号/别号</th>
				<th width="5%">品名编号</th>
				<th>统一分类条码</th>
				<th>标识条码</th>
				<th>费用类别</th>
				<th>科目</th>
				<th>品名名称</th>
				<th>事由</th>
				<th>类型</th>
				<th>品牌</th>
				<th>型号</th>
				<th>品牌规格</th>
				<th>单位</th>
				<th>数量</th>
				<th>质量</th>
				<th>箱规格</th>
				<th>单价管理</th>
				<th>单价</th>
				<th>库房管理</th>
				<th>借方金额</th>
				<th>方向</th>
				<th>贷方金额</th>
				<th>余额</th>
			</tr>
		</thead>
		<s:iterator value="CashierBillslists">
			<script type="text/javascript">
			if("${loan}"==""){
				jie=0;
			}else{
				jie=parseFloat("${loan}");
			}
			if("${forLoan}"==""){
				dai=0;
			}else{
				dai=parseFloat("${forLoan}");
			}
			</script>
			<tr id="${HistoryGoodsBillVO }">
				<td><span id="companyname">${companyname}</span></td>
				<td><span id="departmentname">${departmentname}</span></td>
				<td><span id="staffname">${staffname}</span></td>
				<td><span id="companyBankNum">${companyBankNum}</span></td>
				<td><span id="billsType">${billsType}</span></td>
				<td></td>
				<td><span id="journalNum">${journalNum}</span></td>
				<td><span id=""></span></td>
				<td><span id="archivesNum">${archivesNum}</span></td>
				<td></td>
				<td><span id="startDate">${startDate}</span></td>

				<td><span id="endDate">${endDate}</span></td>
				<td><span id="periodDate">${periodDate}</span></td>
				<td><span id="batchNumber">${batchNumber}</span></td>
				<td><span id="goodsCoding">${goodsCoding}</span></td>
				<td><span id="defaultStorage">${defaultStorage}</span></td>
				<td><span id="identifyingCode">${identifyingCode}</span></td>
				<td><span id="costType">${costType}</span></td>
				<td><span id="subjectsName">${subjectsName}</span></td>
				<td><span id="goodsName">${goodsName}</span></td>
				<td><span id="reasonThing">${reasonThing}</span></td>
				<td><span id="typeID">${typeID}</span></td>
				<td><span id="mnemonicCode">${mnemonicCode}</span></td>
				<td><span id="model">${model}</span></td>
				<td><span id="standard">${standard}</span></td>
				<td><span id="variableID">${variableID}</span></td>
				<td><span id="quantity">${quantity}</span></td>
				<td><span id="weight">${weight}</span></td>
				<td><span id="boxStandard">${boxStandard}</span></td>
				<td><span id="priceManage">${priceManage}</span></td>
				<td><span id="price">${price}</span></td>
				<td><span id="depotName">${depotName}</span></td>
				<td><span id="loan">${loan}</span></td>
				<td><span id="direction">${direction}</span></td>
				<td><span id="forLoan">${forLoan}</span></td>
				<td><span id="balance">${balance}</span></td>
			</tr>
			<script type="text/javascript">
				jiez+=jie;
				daiz+=dai;
			</script>
		</s:iterator>
			<tr>
				<td align="center">&nbsp;合计</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;<span id="jiezong"></span></td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;<span id="daizong"></span></td>
				<td align="center">&nbsp;<span id="yue"></span></td>
			</tr>
	</table>
<script type="text/javascript">
	$("#jiezong").text(parseInt(Math.round(jiez*100))/100);
	$("#daizong").text(parseInt(Math.round(daiz*100))/100);
	$("#yue").text(parseInt(Math.round((jiez-daiz)*100))/100);
  $(function(){ 
  //关闭 
  	$(".closepage").click(function(){
  		window.close();	
  	});
  //打印
  	$(".printpage").click(function(){
  		if(tt=="g"){
  		window.open(basePath + "/ea/archivessummary/ea_toStatements.jspa?tt="+tt+"&sfdate=" + sfdate + "&efdate=" + efdate+"&pan="+"01"+"&chuanzhi="+chuanzhi);
  		}else{
  		window.open(basePath + "/ea/archivest/ea_toStatements.jspa?tt="+tt+"&sfdate=" + sfdate + "&efdate=" +efdate+"&pan="+"01"+"&chuanzhi="+chuanzhi);
  		}
  	});
  //导出
  	$(".daochu").click(function(){
  		if(tt=="g"){
  		url = basePath+ "/ea/archivessummary/ea_toshow.jspa?tt="+tt+"&sfdate=" + sfdate + "&efdate=" +efdate+"&pan="+"10"+"&chuanzhi="+chuanzhi;
		open(url);
  		}else{
  		url = basePath+ "/ea/archivest/ea_toshow.jspa?tt="+tt+"&sfdate=" + sfdate + "&efdate=" +efdate+"&pan="+"10"+"&chuanzhi="+chuanzhi;
		open(url);
		}	
  	});
  });
</script>
</body>
</html>
