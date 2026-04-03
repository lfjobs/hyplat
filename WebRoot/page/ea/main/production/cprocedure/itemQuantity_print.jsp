<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产出库单打印预览</title>

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
$(function(){
		//截取日期
		var str=$("#date").text().split(" ");
		$("#date").text(str[0]);
		
var goodsName=$("#goodsName").text();
var basePath="<%=basePath%>";
$.ajax({
			url:basePath+"ea/quantity/sajax_ea_toPrint.jspa?utboundOrderVo.goodsbillsid="+$("#goodsId").val()+"&type=ajax",
			type:"post",
			async : false,
			success:function(data){
				var member = eval("(" + data + ")");
				var list=member.list;
				for(var i=0;i<list.length;i+=3){
					var tr=$("#goodsTbody").find("#goodsTr").clone(true).attr("style","").attr("id","");
					tr.find("td").eq(0).text(goodsName);
					tr.find("td").eq(1).text(list[i].goodsnumber);
					if(list[i+1]!=null){
						tr.find("td").eq(3).text(goodsName);
						tr.find("td").eq(4).text(list[i+1].goodsnumber);
					}
					if(list[i+2]!=null){
						tr.find("td").eq(6).text(goodsName);
						tr.find("td").eq(7).text(list[i+2].goodsnumber);
					}
					
					$("#goodsTbody").append(tr);
				}
			},
			error:function(data){
				alert("数据获取失败！");
			}
		});
});
</script>
  </head>
  
  <body>
  <center>
    <div style=" width: 650px;">
    	<div>
    		<input type="hidden" id="goodsId" value="${ut.goodsbillsid}">
    		<span><font style="font-weight:bold;" size="3">生产出库单</font></span>
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
				<td width="60px" ><font size="2">仓库名称：</font></td>
				<td width="140px" ><font size="2">${ut.depotname}</font></td>
				<td width="60px" ><font size="2">仓库地址：</font></td>
				<td width="150px" ><font size="2">--</font></td>
				<td width="60px" ><font size="2">责任人：</font></td>
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
				<td><font size="2">出库数量：</font></td>
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
					<td width="100" style="border-top-color:#FFFFFF;">物品名称</td>
					<td width="97" style="border-top-color:#FFFFFF;">物品序号</td>
					<td width="20" style="border-top-color:#FFFFFF;"></td>
					<td width="100" style="border-top-color:#FFFFFF;">物品名称</td>
					<td width="97" style="border-top-color:#FFFFFF;">物品序号</td>
					<td width="19" style="border-top-color:#FFFFFF;"></td>
					<td width="100" style="border-top-color:#FFFFFF;">物品名称</td>
					<td width="97" style="border-top-color:#FFFFFF;">物品序号</td>
				</tr>
			</thead>
			<tbody id="goodsTbody">
				<tr style="display: none;" id="goodsTr">
					<td></td><td></td><td></td>
					<td></td><td></td><td></td>
					<td></td><td></td>
				</tr>
			</tbody>
			
		</table>
		<br>
		<table>
			<tr>
				<td  width="240px;"><font size="2">制单人：</font><font size="2">${ut.staffName}</font></td>
				<td width="250px;"></td>
				<td width="140px;"  ><font size="2">制单日期：</font><font size="2" id="date">${ut.cashierDate}</font></td>
			</tr>
		</table>
    </div>
    </center>
  </body>
</html>
