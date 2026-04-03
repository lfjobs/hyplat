<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产入库单打印预览</title>

	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	
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

<script type="text/javascript">
var goodsName=$("#goodsName").text();
var basePath="<%=basePath%>";
$(function(){
$("#newDate").find("font").eq(1).text($("#newDate").find("font").eq(1).text().split(" ")[0]);
});
</script>
  </head>
  
  <body>
  <center>
    <div style=" width: 650px;">
    	<div>
    		<input type="hidden" id="goodsId" value="${ut.goodsbillsid}">
    		<span><font style="font-weight:bold;" size="3">生产入库单</font></span>
    	</div>    	<br>
    	
    	<table>
    		<tr>
    			<td align="left" width="290px;"><font size="2" style="font-weight:bold;">&nbsp;公司：</font><font size="2">${comName}</font></td>
    			<td align="right"  width="370px" rowspan="2">
    				<table>
    					<tr>
    						<td width="84px;" rowspan="2"><font size="2" style="font-weight:bold;float: left;">出库单编号：</font></td>
    						<td><font>
						<%
							hy.ea.bo.production.view.UtboundOrderVo data = (hy.ea.bo.production.view.UtboundOrderVo) request
									.getAttribute("ut");
							if (data != null) {
								StringBuffer barCode = new StringBuffer();
								barCode.append("<img src='");
								barCode.append(request.getContextPath());
								barCode.append("/CreateBarCode?data=");
								barCode.append(data.getJournalnum());
								barCode
										.append("&barType=TF25&height=20&headless=true&drawText=true&width=1' wdith='200'>");
								out.println(barCode.toString());
							} else {
								out.println("no data");
							}
						%></font></td>
    					</tr>
    					<tr>
    						<td align="center"><font  size="2">${ut.journalnum}</font></td>
    					</tr>
    				</table>	
    			</td>
    		</tr>
    		<tr>
    			<td align="left"><font size="2" style="font-weight:bold;">&nbsp;部门：</font><font size="2">${orgName}</font></td>
    		</tr>
    	</table>

		<table class="table">
			
			<tr height="22">
				<td width="70px" ><font size="2">仓库名称：</font></td>
				<td width="130px" ><font size="2">${ut.drdepotName}</font></td>
				<td width="70px" ><font size="2">仓库地址：</font></td>
				<td width="140px" ><font size="2">--</font></td>
				<td width="70px" ><font size="2">责任人：</font></td>
				<td width="140px" ><font size="2">${ut.staffName}</font></td>
			</tr>
			<tr height="22">
				<td><font size="2">产品名称：</font></td>
				<td><font size="2" id="goodsName">${ut.goodsname}</font></td>
				<td><font size="2">产品编号：</font></td>
				<td><font size="2">${ut.goodsnum}</font></td>
				<td><font size="2">规格：</font></td>
				<td><font size="2">${ut.standard}</font></td>
			</tr>
			<tr height="22">
				<td><font size="2">入库数量：</font></td>
				<td><font size="2">${ut.quantity}</font></td>
				<td><font size="2">单价：</font></td>
				<td><font size="2">${ut.price}</font></td>
				<td><font size="2">总金额：</font></td>
				<td><font size="2">${ut.money}</font></td>
			</tr>
		</table>
		<table style="font-size: 3px;" cellpadding="0" cellspacing="0" class="table">
			<thead>
				<tr align="center" height="20px">
					<td width="30" style="border-top-color:#FFFFFF;">序号</td>
					<td width="110" style="border-top-color:#FFFFFF;">项目编号</td>
					<td width="88" style="border-top-color:#FFFFFF;">物品名称</td>
					<td width="80" style="border-top-color:#FFFFFF;">物品编号</td>
					<td width="50" style="border-top-color:#FFFFFF;">类别</td>
					<td width="60" style="border-top-color:#FFFFFF;">规格</td>
					<td width="60" style="border-top-color:#FFFFFF;">数量</td>
					<td width="50" style="border-top-color:#FFFFFF;">单价</td>
					<td width="60" style="border-top-color:#FFFFFF;">总价</td>
					<td width="40" style="border-top-color:#FFFFFF;">备注</td>
				</tr>
			</thead>
			<tbody id="goodsTbody">
				<c:forEach items="${list}" var="l" varStatus="a">
				<tr>
					<td>${a.index+1}</td>
					<td><span>${l[0]}</span></td>
	   				<td><span>${l[1]}</span></td>
	   				<td><span>${l[2]}</span></td>
	   				<td><span>${l[3]}</span></td>
	   				<td><span>${l[4]}</span></td>
	   				<td><span>${l[5]}</span></td>
	   				<td><span>${l[6]}</span></td>
	   				<td><span>${l[7]}</span></td>
	   				<td><span>${l[8]}</span></td>
   				</tr>
				</c:forEach>
			</tbody>
			
		</table>
		<br>
		<table>
			<tr>
				<td  width="240px;"><font size="2">制单人：</font><font size="2">${ut.staffName}</font></td>
				<td width="255px;"></td>
				<td width="135px;"  id="newDate"><font size="2">制单日期：</font><font size="2">${ut.cashierDate}</font></td>
			</tr>
		</table>
    </div>
    </center>
  </body>
</html>
