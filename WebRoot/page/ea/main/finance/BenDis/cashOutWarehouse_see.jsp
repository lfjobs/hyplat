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
    
    <title>现金出库单打印页面</title>
    
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
  </head>
  
  <body>
     <center>
    <div style=" width: 650px;">
    	<div>
    		<span><font style="font-weight:bold;" size="3">现金出库单</font></span>
    	</div>    	<br>
    	
    	<table>
    		<tr>
    			<td align="left" width="290px;"><font size="2" style="font-weight:bold;">所属公司：</font>
    					<font size="2">北京天太世统科技有限责任公司</font></td>
    			<td align="right"  width="370px" rowspan="2">
    				<table>
    					<tr>
    						<td width="84px;" rowspan="2"><font size="2" style="font-weight:bold;float: left;">出库单编号：</font></td>
    						<td><font>
						<%
							Object[] obj=(Object[])session.getAttribute("goods");
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
    						<td align="center"><font  size="2">${goods[1]}</font></td>
    					</tr>
    				</table>	
    			</td>
    		</tr>
    		<tr>
    			<td align="left"><font size="2" style="font-weight:bold;">订单编号：</font><font size="2">${goods[2]}</font></td>
    		</tr>
    	</table>

		<table class="table">
			
			<tr height="22">
				<td width="120px" ><font size="2">单据类型：</font></td>
				<td width="193px" ><font size="2">${goods[3]}</font></td>
				<td width="120px" ><font size="2">往来单位：</font></td>
				<td width="193px" ><font size="2">${goods[4]}</font></td>
				<td width="120px" ><font size="2">往来个人：</font></td>
				<td width="193px" ><font size="2">${goods[5]}</font></td>
			</tr>
			<tr height="22">
				<td><font size="2">仓库编号：</font></td>
				<td><font size="2" ></font></td>
				<td><font size="2">仓库名称：</font></td>
				<td><font size="2">${goods[8]}</font></td>
				<td><font size="2">仓库地址：</font></td>
				<td><font size="2"></font></td>
			</tr>
		</table>
		<table style="font-size: 3px;" cellpadding="0" cellspacing="0" class="table">
			<thead>
				<tr align="center" height="20px">
					<th width="40" style="border-top-color:#FFFFFF;">序号</th>
						<th width="100" style="border-top-color:#FFFFFF;">物品编号</th>
						<th width="120" style="border-top-color:#FFFFFF;">物品名称</th>
						<th width="70" style="border-top-color:#FFFFFF;">类别</th>
						<th width="60" style="border-top-color:#FFFFFF;">规格</th>
						<th width="60" style="border-top-color:#FFFFFF;">数量</th>
						<th width="60" style="border-top-color:#FFFFFF;">单价</th>
						<th width="78" style="border-top-color:#FFFFFF;">金额</th>
						<th width="60" style="border-top-color:#FFFFFF;">备注</th>
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
							<td>${l.quantity}</td>
							<td>${l.price}</td>
							<td>${l.money}</td>
							<td>${l.remark}</td>
						</tr>
					</c:forEach>
			</tbody>
			
		</table>
		<br>
		<table>
			<tr>
				<td  width="240px;"><font size="2">责任人：</font><font size="2">${goods[10]}</font></td>
				<td width="160px;"></td>
				<td width="230px;"  ><font size="2">制单日期：</font><font size="2">${goods[11]}</font></td>
			</tr>
		</table>
    </div>
    </center>
  </body>
</html>
