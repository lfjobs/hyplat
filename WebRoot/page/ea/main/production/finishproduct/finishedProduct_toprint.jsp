<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>成品出库单打印页面</title>
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
    		<input type="hidden" id="cashierbillsid" value="${obj[0]}">
    		<span><font style="font-weight:bold;" size="3">生产移库单</font></span>
    	</div>    	<br>
    	
    	<table>
    		<tr>
    			<td align="left" width="290px;"><font size="2" style="font-weight:bold;">&nbsp;公司：</font><font size="2">${obj[1]}</font></td>
    			<td align="right"  width="370px" rowspan="2">
    				<table>
    					<tr>
    						<td width="84px;" rowspan="2"><font size="2" style="font-weight:bold;float: left;">出库单编号：</font></td>
    						<td><font>
						<%
							Object[] data =(Object[])request.getAttribute("obj");
							if (data != null) {
								StringBuffer barCode = new StringBuffer();
								barCode.append("<img src='");
								barCode.append(request.getContextPath());
								barCode.append("/CreateBarCode?data=");
								barCode.append(data[2]);
								barCode
										.append("&barType=TF25&height=20&headless=true&drawText=true&width=1' wdith='200'>");
								out.println(barCode.toString());
							} else {
								out.println("no data");
							}
						%></font></td>
    					</tr>
    					<tr>
    						<td align="center"><font  size="2">${obj[2]}</font></td>
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
				<td width="70px" ><font size="2">调出仓库：</font></td>
				<td width="140px" ><font size="2">${obj[3]}</font></td>
				<td width="70px" ><font size="2">调入仓库：</font></td>
				<td width="150px" ><font size="2">${obj[4]}</font></td>
				<td width="70px" ><font size="2">出库人员：</font></td>
				<td width="140px" ><font size="2">${obj[5]}</font></td>
			</tr>
			<tr height="22">
				<td><font size="2">出库时间：</font></td>
				<td><font size="2" id="goodsName">${obj[6]}</font></td>
				<td><font size="2">对方科目：</font></td>
				<td><font size="2">${obj[7]}</font></td>
				<td><font size="2">联系电话：</font></td>
				<td><font size="2">${obj[8]}</font></td>
			</tr>
			<tr height="22">
				<td><font size="2">物流方式：</font></td>
				<td colspan="5"><font size="2">${obj[9]}</font></td>
			</tr>
		</table>
		<table style="font-size: 3px;" cellpadding="0" cellspacing="0" class="table">
			<thead>
				<tr align="center" height="20px">
					<td width="30" style="border-top-color:#FFFFFF;">序号</td>
					<td width="150" style="border-top-color:#FFFFFF;">产品编号</td>
					<td width="88" style="border-top-color:#FFFFFF;">产品名称</td>
					<td width="80" style="border-top-color:#FFFFFF;">类型</td>
					<td width="50" style="border-top-color:#FFFFFF;">规格</td>
					<td width="50" style="border-top-color:#FFFFFF;">数量</td>
					<td width="50" style="border-top-color:#FFFFFF;">成本单价</td>
					<c:if test="${obj[4]=='销售库'}">
						<td width="60" style="border-top-color:#FFFFFF;">利润金额</td>
						<td width="40" style="border-top-color:#FFFFFF;">销售价</td>
					</c:if>
					<td width="40" style="border-top-color:#FFFFFF;">备注</td>
				</tr>
			</thead>
			<tbody id="goodsTbody">
				<c:forEach items="${goods}" var="l" varStatus="a">
				<tr>
					<td>${a.index+1}</td>
					<td><span>${l.goodsNum}</span></td>
	   				<td><span>${l.goodsName}</span></td>
	   				<td><span>${l.typeID}</span></td>
	   				<td><span>${l.standard}</span></td>
	   				<td><span>${l.quantity}</span></td>
	   				<td><span>${l.price}</span></td>
	   				<c:if test="${obj[4]=='销售库'}">
		   				<td><span>${l.profitAmount}</span></td>
		   				<td><span>${l.pretium}</span></td>
	   				</c:if>
	   				<td><span>${l.remark}</span></td>
   				</tr>
				</c:forEach>
			</tbody>
			
		</table>
		<br>
		<table>
			<tr>
				<td  width="240px;"><font size="2">制单人：</font><font size="2">${obj[10]} - ${obj[12]}</font></td>
				<td width="255px;"></td>
				<td width="135px;"  id="newDate"><font size="2">制单日期：</font><font size="2">${obj[11]}</font></td>
			</tr>
		</table>
    </div>
    </center>
  </body>
</html>
