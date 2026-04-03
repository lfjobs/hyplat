<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产流程设计</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/production/adesign/processdesign_print.js"></script>
	<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/admin_main111.css">
	
	
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var goodsBillsID="${productPackaging.ppID}";
		var staffName="${c.staffName}";
		var cls="";
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
		  				生&nbsp;&nbsp;产&nbsp;&nbsp;流&nbsp;&nbsp;程&nbsp;&nbsp;设&nbsp;&nbsp;计
		  		</div>
					
		  		<div style="height: 70%;width: 100%;">
		  			<table class="table" width="929" height="39" style="width: 100%;">
		  				<thead>
		  					<tr>
		  						<th width='300px;'>产品名称</th>
		  						<th width='148px;'>产品编号</th>
		  						<th width='148px;'>数量</th>
		  						<th width='148px;'>单价</th>
		  						<th width='148px;'>金额</th>
		  						<th width='148px;'>备注</th>
		  						<th width='148px;'>操作</th>
		  					</tr>
		  				</thead>
						<tbody id="tableR">
						</tbody>
		  			</table>
		  			<p id="save" style="width:100%;height:30px;text-align: center;background-color: #009DD9;line-height: 30px;color: #fff;font-size: 24px">保&nbsp;&nbsp;&nbsp;存</p>
		  		</div>
		  		
		  </div>
  </body>
</html>
