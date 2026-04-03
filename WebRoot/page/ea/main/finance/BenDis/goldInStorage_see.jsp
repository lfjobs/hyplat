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
    
    <title>${cashi[8]}打印页面</title>
    
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
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript">
	var basePath="<%=basePath%>";
	$(function(){
	$.ajax({
			url:basePath+"ea/goldStock/sajax_ea_ajaxGetUserOwnedCompany.jspa?utboundOrderVo.cashierbillsid="+$("#cashiId").val(),
		    type:"get",
		    success:function(data){
		    	var member = eval("(" + data + ")");
				var com = member.com;
				$("#company").text(com.companyName);
		    },
		});
	})
		
	</script>
  </head>
  
  <body>
     <center>
    <div style=" width: 650px;">
    	<div>
    		<span><font style="font-weight:bold;" size="3">${cashi[8]}</font></span>
    	</div>    	<br>
    	
    	<table>
    		<tr>
    			<td align="left" width="290px;"><font size="2" style="font-weight:bold;">所属公司：</font><font size="2">${cashi[3]}</font></td>
    			<td align="right"  width="370px" rowspan="2">
    				<table>
    					<tr>
    						<td width="84px;" rowspan="2"><font size="2" style="font-weight:bold;float: left;">单据编号：</font></td>
    						<td><font>
						<%
							Object[] obj=(Object[])session.getAttribute("cashi");
							if (obj!=null) {
								StringBuffer barCode = new StringBuffer();
								barCode.append("<img src='");
								barCode.append(request.getContextPath());
								barCode.append("/CreateBarCode?data=");
								barCode.append(obj[5]);
								barCode
										.append("&barType=TF25&height=20&headless=true&drawText=true&width=1' wdith='200'>");
								out.println(barCode.toString());
							} else {
								out.println("无数据");
							}
						%></font></td>
    					</tr>
    					<tr>
    						<input type="hidden" id="cashiId" value="${cashi[0]}">
    						<td align="center"><font  size="2">${cashi[5]}</font></td>
    					</tr>
    				</table>	
    			</td>
    		</tr>
    		<tr>
    			<td align="left"><font size="2" style="font-weight:bold;">会员类别：</font><font size="2">${cashi[4]}</font></td>
    		</tr>
    	</table>

		<table class="table">
			<tr height="22">
				<td width="70px;"><font size="2">用户所属<br>公司名称：</font></td>
				<td width="190px;"><font size="2" id="company"></font></td>
				<td width="70px;"><font size="2">用户名称：</font></td>
				<td width="120px;"><font size="2">${cashi[1]}</font></td>
				<td width="70px;"><font size="2">用户编号：</font></td>
				<td width="120px;"><font size="2">${cashi[2]}</font></td>
			</tr>
		</table>
		<table style="font-size: 3px;" cellpadding="0" cellspacing="0" class="table">
			<thead>
				<tr align="center" height="20px">
						<th width="40"  style="border-top-color:#FFFFFF;">序号</th>
						<th width="197" style="border-top-color:#FFFFFF;">产品所属公司</th>
						<th width="120" style="border-top-color:#FFFFFF;">产品名称</th>
						<th width="80" style="border-top-color:#FFFFFF;">产品编号</th>
						<th width="70" style="border-top-color:#FFFFFF;">数量</th>
						<th width="60" style="border-top-color:#FFFFFF;">单价</th>
						<th width="70" style="border-top-color:#FFFFFF;">金额</th>
						<th width="60" style="border-top-color:#FFFFFF;">金币（佣金）</th>
						<th width="80" style="border-top-color:#FFFFFF;">仓库地址</th>
						<th width="55" style="border-top-color:#FFFFFF;">备注</th>
				</tr>
			</thead>
			<tbody id="goodsTbody">
				<c:forEach items="${goodsList}" var="l" varStatus="a">
							<tr>
								<td>${a.index+1}</td>
								<td>${cashi[4]}</td>
								<td>${l[0]}</td>
								<td>${l[1]}</td>
								<td>${l[2]}</td>
								<td>${l[3]}</td>
								<td>${l[4]}</td>
								<td>${l[5]}</td>
								<td>${l[5]}</td>
								<td>${l[6]}</td>
							</tr>
						</c:forEach>
			</tbody>
			
		</table>
		<br>
		<table>
			<tr>
				<td  width="240px;"><font size="2">责任人：</font><font size="2">${cashi[7]}</font></td>
				<td width="160px;"></td>
				<td width="230px;"  ><font size="2">制单日期：</font><font size="2">${cashi[6]}</font></td>
			</tr>
		</table>
    </div>
    </center>
  </body>
</html>
