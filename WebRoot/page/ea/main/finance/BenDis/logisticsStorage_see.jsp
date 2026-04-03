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
    
    <title>物流入库单打印页面</title>
    
    <style	 type="text/css">
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
</style>
<script type="text/javascript" src="<%=basePath%>js/jqChart/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		var quantity=0;
		$(".quantity").each(function(){
			quantity+=parseInt($(this).text());
		});
		$("#n").text(quantity);
	});
</script>
  </head>
  
  <body>
     <center>
    <div style=" width: 650px;">
    	<div>
    		<span><font style="font-weight:bold;" size="3">物流入库单</font></span>
    	</div>    	<br>
    	
    	<table>
    		<tr>
    			<td align="left" width="290px;"><font size="2" style="font-weight:bold;">所属公司：</font><font size="2">${companyName}</font></td>
    			<td align="right"  width="370px" rowspan="2">
    				<table>
    					<tr>
    						<td width="84px;" rowspan="2"><font size="2" style="font-weight:bold;float: left;">入库单编号：</font></td>
    						<td><font>
						<%
							Object[] obj=(Object[])request.getAttribute("cashi");
							if (obj!=null) {
								StringBuffer barCode = new StringBuffer();
								barCode.append("<img src='");
								barCode.append(request.getContextPath());
								barCode.append("/CreateBarCode?data=");
								barCode.append(obj[1]);
								barCode
										.append("&barType=TF25&height=20&headless=true&drawText=true&width=1' wdith='200'>");
								out.println(barCode.toString());
							} else {
								out.println("无数据");
							}
						%></font></td>
    					</tr>
    					<tr>
    						<td align="center"><font  size="2">${cashi[1]}</font></td>
    					</tr>
    				</table>	
    			</td>
    		</tr>
    		<tr>
    			<td align="left"><font size="2" style="font-weight:bold;">订单编号：</font><font size="2">${cashi[2]}</font></td>
    		</tr>
    	</table>

		<table class="table">
			
			<tr height="22">
				<td width="120px" ><font size="2">仓库编号：</font></td>
				<td width="193px" ><font size="2">${cashi[3]}</font></td>
				<td width="120px" ><font size="2">仓库名称：</font></td>
				<td width="193px" ><font size="2">${cashi[5]}</font></td>
			</tr>
			<tr height="22">
				<td><font size="2">仓库地址：</font></td>
				<td><font size="2" id="goodsName">${cashi[6]}</font></td>
				<td><font size="2">物品总数量：</font></td>
				<td><font size="2" id="n"></font></td>
			</tr>
		</table>
		<table style="font-size: 3px;" cellpadding="0" cellspacing="0" class="table">
			<thead>
				<tr align="center" height="20px">
					<th width="40" style="border-top-color:#FFFFFF;">序号</th>
						<th width="90" style="border-top-color:#FFFFFF;">物品编号</th>
						<th width="90" style="border-top-color:#FFFFFF;">物品名称</th>
						<th width="60" style="border-top-color:#FFFFFF;">类别</th>
						<th width="50" style="border-top-color:#FFFFFF;">规格</th>
						<th width="50" style="border-top-color:#FFFFFF;">数量</th>
						<th width="50" style="border-top-color:#FFFFFF;">单价</th>
						<th width="68" style="border-top-color:#FFFFFF;">金额</th>
						<th width="80" style="border-top-color:#FFFFFF;">原属仓库</th>
						<th width="50" style="border-top-color:#FFFFFF;">备注</th>
				</tr>
			</thead>
			<tbody id="goodsTbody">
				<c:forEach items="${list}" var="l" varStatus="a">
				<tr>
					<td>${a.index+1}</td>
					<td>${l.goodsNum}</td>
					<td>${l.goodsName}</td>
					<td>${l.typeID}</td>
					<td>${l.standard}</td>
					<td class="quantity">${l.quantity}</td>
					<td>${l.price}</td>
					<td>${l.money}</td>
					<td>${l.depotName}</td>
					<td>${l.remark}</td>
   				</tr>
				</c:forEach>
			</tbody>
			
		</table>
		<br>
		<table>
			<tr>
				<td  width="240px;"><font size="2">责任人：</font><font size="2">${cashi[8]}</font></td>
				<td width="262px;"></td>
				<td width="130px;"  ><font size="2">制单日期：</font><font size="2">${cashi[9]}</font></td>
			</tr>
		</table>
    </div>
    </center>
  </body>
</html>
