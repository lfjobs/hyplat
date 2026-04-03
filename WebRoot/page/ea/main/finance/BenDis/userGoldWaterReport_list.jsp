<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>金币流水报表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<style type="text/css">
		.dps{
		height: 20px;
		font-size: 11px;}	
		#but{
		height:30px;
		float: right;}
		a{
		text-decoration:none;
		}
		input{
			width: 100px;
		}
		.like{
			position: relative;top: 5px;left: -35px;
		}
	</style>
	<style media="print">
		#but{
		display:none;}
		.operation{
		display: none;
		}
	</style>

	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script src="<%=basePath%>js/ea/finance/BenDis/userGoldWaterReport_list.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		var basePath="<%=basePath%>";
	</script>
  </head>
  
  <body>
  <center>
    <div style="width: 790px;">	<br>  	
     <span><font size="5" style="font-weight:bold;">金币流水报表</font></span><br>
     <span class="like">开始时间：<input type="text" name="sdate" id="sdate"  onfocus="WdatePicker({dateFmt:'yyyyMMdd'})">
     	结束时间：<input type="text" name="edate" id="edate"  onfocus="WdatePicker({dateFmt:'yyyyMMdd'})">&nbsp;
     	类别：<input type="text" id="category">
     	<input type='button' id='tosearch' value='查询'  style='margin:0px;margin-left:5px;width:40px;'>
     	<input type="hidden" id="userId" value="${userId}"></span>
   	 <input type="button" value="导出Excel表格" id="but" /><br><br>
    	<table align="center" class="table">     
    		<tr height="25" class="dataHead">
    			<th width="40">序号</th>
  				<th width="230">记录内容</th>
  				<th width="74">记录时间</th>
  				<th width="100">收入金币数量</th>
  				<th width="100">支出金币数量</th>
  				<th width="100">结余金币数量</th>
  				<th width="80">类别</th>
  				<th width="40"  class="operation">操作</th>
    		</tr>
    		<c:if test="${balance!=null}">
    			<tr id="balance" class="dps">
    				<td  align="center"></td>
    				<td></td>
    				<td>${sdate}</td>
    				<td></td>
    				<td></td>
    				<td id="balanceNum">${balance}</td>
    				<td></td>
    				<td class="operation"></td>
    			</tr>
    		</c:if>
    		<c:forEach items="${pageForm.list}" var="l" varStatus="a">
    			<tr class="dps" id="${a.index}">
    				<td>${a.index+1}</td>
    				<td>${l[0]}</td>
    				<td>${l[1]}</td>
    				<c:if test="${l[2]=='A'}">
    					<td id="increase" align="right">${l[3]}</td>
    					<td></td>
    				</c:if>
    				<c:if test="${l[2]=='M'}">
    				    <td></td>
    					<td id="reduce" align="right">${l[3]}</td>
    				</c:if>
    				<td id="balances"  align="right"></td>
    				<td>${l[4]}</td>
    				<td align="center" class="operation"><a id="${l[5]}"  href="javascript:;">详情</a></td>
    			</tr>
    		</c:forEach>
    	</table>
    </div>
    <div class="jqmWindow jqmWindowcss2" id="subjectr" style="width: 300px;height:180px;top: 10%;left:60%;background-color: #DAE7F6;">
	    	<div style="background-color: #63B8FF;height:30px;text-align: center;">
			<font size="4" color="#FFFFFF" style="position: relative;top:5px;">查&nbsp;看&nbsp;单&nbsp;据&nbsp;详&nbsp;情</font>
			<div style="position: relative; top: 35px;">
				<span>选择单据：</span>
				<span><select id="cashi">
					
				</select></span>
			</div>
			<div  style="position: relative; top: 75px;">
				<input type="button" value="查看" class="butt" style="width: 60px;height: 25px;position: relative;left: -10px;">
				<input type="button" value="关闭" class="butt" style="width: 60px;height: 25px;position: relative;left: 20px;">
			</div>
		</div>
    </div>
  </body>
</html>

