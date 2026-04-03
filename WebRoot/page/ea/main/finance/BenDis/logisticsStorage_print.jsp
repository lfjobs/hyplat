<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>物流入库单预览页面</title>
    
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" />
    <script type="text/javascript">
    	var basePath="<%=basePath%>";
    	$(function(){
    		$("#date").val($("#date").val().split(" "));
    		
    		$(".JQueryprint").click(function(){
    			open(basePath+"ea/logstor/ea_getPreviewPageData.jspa?type=01&cashierBills.cashierBillsID="+$("#cashiId").val());
    		});
    		$(".JQueryClose").click(function(){
    			window.close();
    		});
    	});
    </script>
  </head>
  
  <body>
    <div style="width: 100%;text-align:left;border:0px solid red;">
		<table id="titleClone" width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background:#dbe5f1;margin-top:1px;
					margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
			<tr><td align="left" style="height:24px;">
			    <nobr>
				<input type="button" 
								class="menubtn JQueryprint" name="button" value="打印"/><input type="button" 
								class="menubtn JQueryClose" name="button2"value="关闭" /><input type="button" 
								class="menubtn grey" name="button" value="帮助" disabled="disabled"/>
				</nobr>
			</td></tr>
		</table>
	</div>
	<div>
		<div style="position: relative;top: 30px;left: 400px;width: 100px;"><font color="#15428b" style="font-weight:bold;">物流入库单</font></div>
		<div style="position: relative;top: 60px;left:10px;width:900px;">
			<table>
				<tr height="30px;">
					<td><font size="2" color="#4F4F4F">入库编号：</font></td>
					<td width="247px;"><input type="text" class="inputbottom" value="${cashi[1]}" readonly="readonly" 
							style="width: 140px;"><input type="hidden" id="cashiId" value="${cashi[0]}"></td>
					<td><font size="2" color="#4F4F4F">订单编号：</font></td>
					<td width="247px;"><input type="text" class="inputbottom" value="${cashi[2]}" readonly="readonly" style="width: 140px;"></td>
					<td><font size="2" color="#4F4F4F">所属公司：</font></td>
					<td><input type="text" class="inputbottom" value="${companyName}" readonly="readonly" style="width: 140px;"></td>
				</tr>
				<tr height="25px;">
					<td><font size="2" color="#4F4F4F">仓库编号：</font></td>
					<td><input type="text" class="inputbottom" value="${cashi[3]}" readonly="readonly" style="width: 140px;"></td>
					<td><font size="2" color="#4F4F4F">仓库名称：</font></td>
					<td><input type="text" class="inputbottom" value="${cashi[5]}" readonly="readonly" style="width: 140px;"></td>
					<td><font size="2" color="#4F4F4F">仓库地址：</font></td>
					<td><input type="text" class="inputbottom" value="${cashi[6]}" readonly="readonly" style="width: 140px;"></td>
				</tr>	
			</table>
		</div>
		<div style="position: relative;top: 80px;left:10px;border: 1px solid #a8c7ce;width: 850px;height: 300px;">
			<table class="table">
				<thead>
					<tr height="22px;">
						<th width="40">序号</th>
						<th width="100">物品编号</th>
						<th width="120">物品名称</th>
						<th width="90">类别</th>
						<th width="70">规格</th>
						<th width="70">数量</th>
						<th width="70">单价</th>
						<th width="80">金额</th>
						<th width="110">原属仓库</th>
						<th width="70">备注</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="l" varStatus="a">
						<tr>
							<td>${a.index+1}</td>
							<td>${l.goodsNum}</td>
							<td>${l.goodsName}</td>
							<td>${l.typeID}</td>
							<td>${l.standard}</td>
							<td>${l.quantity}</td>
							<td>${l.price}</td>
							<td>${l.money}</td>
							<td>${l.depotName}</td>
							<td>${l.remark}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div style="position: relative;top: 95px;left:10px;">
			<span>
				<span><font size="2" color="#4F4F4F">责任人：</font></span>
				<span><input type="text" class="inputbottom" readonly="readonly" value="${cashi[8]}" style="width: 140px;"></span>
			</span>
			<span style="position: relative;left: 430px;">
				<span><font size="2" color="#4F4F4F">单据时间：</font></span>
				<span><input type="text" class="inputbottom" readonly="readonly" value="${cashi[9]}" id="date"  style="width: 140px;"></span>
			</span>
		</div>
	</div>
  </body>
</html>
