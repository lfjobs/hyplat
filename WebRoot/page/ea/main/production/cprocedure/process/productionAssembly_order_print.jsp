<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产组装</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/process/productionAssembly_order_print.js"></script>
	<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/admin_main111.css">
	
	
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var goodsBillsID="${utboundOrderVo.goodsbillsid}";
		var staffName="${c.staffName}";
		var ppId="${utboundOrderVo.ppId}";
		var goodsName="${utboundOrderVo.goodsname}";
		var judge=true;
		var fiveClear="${fiveClear}";
		var quantity="${quantity}";
	</script>
	<style type="text/css">
		#tableR{
			border-right:1px solid #a8c7ce;border-bottom:1px solid #a8c7ce;
		}
		#tableR td{
			border-left:1px solid #a8c7ce;border-top:1px solid #a8c7ce;
		}
		a{text-decoration:none;}
	</style>
  </head>
  <body>
		  <div style="width: 70%;position: relative;left: 15%;top:5%;height:95%;">
		  		<div style="height: 10%;width: 100%;color: #fff;font-size: 36px;text-align: center;line-height: 180%;background-color:#009DD9">
		  				生&nbsp;&nbsp;产&nbsp;&nbsp;组&nbsp;&nbsp;装
		  		</div>
		  		<div style="height: 10%;width: 100%;">
		  			<table style="height: 100%;width: 100%;">
		  				<tr style="height: 100%;">
		  					<c:forEach items="${list}" var="l">
		  						<td style="height:100%;width: ${100/fn:length(list)}%;" align="center">
		  								<div style="width: 50%;height: 50%;border: 1px solid #000;cursor:pointer;line-height: 250%;
		  										background-color: ${l[0]==obj[0]?'#EDDB7D':'#707070'};" id="${l[0]}" class="option">${l[2]}</div>
		  						</td>
		  					</c:forEach>
		  				</tr>
		  			</table>
		  		</div>
		  		<div style="height: 70%;width: 100%;">
		  			<table class="table">
		  				<thead>
		  					<tr>
		  						<th width='250px;'>产品名称</th>
		  						<th width='75px;'>结构量</th>
		  						<th width='75px;'>库存量</th>
		  						<th width='75px;'>生产量</th>
		  						<th width='75px;'>出库量</th>
		  						<th width='118px;'>起止时间</th>
		  						<th width='95px;'>教学日志 - 教练</th>
		  						<th width='95px;'>教学日志 - 学员</th>
		  						<th width='85px;'>教学主管</th>
		  						<th width='60px;'>操作</th>
		  					</tr>
		  				</thead>
		  				<div>
		  					<div>
		  						<table id="tableR" cellpadding="0" cellspacing="0"  style="position: relative;top: -5px;">
		  							<tbody>
		  							</tbody>
		  						</table>
		  					</div>
		  				</div>
		  			</table>
		  		</div>
		  </div>
		  <form method="post" id="form" name="form">
		  		<input type="submit" id="submit" name="submit" style="display: none;">
		  		<iframe name="hidden"  style="display: none;"></iframe>
		  </form>
  </body>
</html>
