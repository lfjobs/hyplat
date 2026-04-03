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
    
    <title>财务库存管理</title>
    <script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
 	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
 	<script src="<%=basePath%>js/ea/finance/invoicing/financialInventory_list.js"></script>
 	<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>

	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var invId="";
	</script>
  </head>
  
  <body>
  <table   class="address" >
	  <thead>
		   <tr>
		 	    <th width="40" align="center">请选择</th>
	            <th width="100" align="center" >物品名称</th>
				<th width="100" align="center" >物品类别</th>
				<th width="120" align="center" >库存数量</th>
				<th width="60" align="center" >来源途径</th>
				<th width="80" align="center" >品名编号</th>
				<th width="60" align="center" >物品单位</th>
				<th width="60" align="center" >物品上限</th>
				<th width="60" align="center" >物品下限</th>
				<th width="80" align="center" >库存预警值</th>
				<th width="75" align="center" >库存位置</th>
	      </tr>
	    </thead>
		<tbody>
          <c:forEach items="${pageForm.list}" var="l">
          	<tr class="td_bg01 saveAjax dds">
          		<td>
          			 <a href="javascript:;" class="add">
		             <img src="<%=basePath%>images/u15.png" width="16" height="16"   border="0"/>
		             </a>
          		</td>
          		<td>${l[0]}</td>
          		<td>${l[0]}</td>
          		<td name='${l[4]}'>${l[1]}</td>
          		<td></td>
          		<td></td>
          		<td></td>
          		<td></td>
          		<td></td>
          		<td><font></font></td>
          		<td id="${l[2]}">${l[3]}</td>
          	</tr>
          </c:forEach>
   		</tbody>
  </table>
  
  <div class="jqmWindow jqmWindowcss2" id="subjectr" style="width: 300px;height:150px;top: 10%;left:60%;background-color: #DAE7F6;">
	    	<center>
	    	<div style="height: 17%; background-color: #F8F8CE;border: 2px inset  #FFFFFF;">
	    		<font size="3" style="position: relative;top: 4px;">选择日期</font>
	    	</div>
	    	<div style="height: 83%;background-color: #BAEDE9">
		    	<div style="position: relative;top: 15%;">	    	
						查询日期：<input type="text" name="sdate" id="sdate"  
							onfocus="WdatePicker({dateFmt:'yyyyMM'})" readonly="readonly" style="width: 40%;">
		    	</div>
	    	<div style="position: relative;top: 40%;">
	    		<input type="button" id="pandian" value="确定" style="width: 20%;height: 18%;position: relative;left: -4%;">
	    		<input type="button" class="colse" value="关闭" style="width: 20%;height: 18%;position: relative;left: 4%;">
	    	</div>
	    	</div>
    </div>
    
     <div class="jqmWindow jqmWindowcss2" id="limiting" style="width: 300px;height:200px;top: 10%;left:60%;background-color: #DAE7F6;">
	    	<center>
	    	<div style="height: 13%; background-color: #F8F8CE;border: 2px inset  #FFFFFF;">
	    		<font size="3" style="position: relative;top: 4px;">设置上下限值</font>
	    	</div>
	    	<form id="form" name="form" method="post">
	    	<div style="height: 87%;background-color: #BAEDE9">
		    	<div style="position: relative;top: 15%;">	    	
						上限值：<input type="text" name="invenOnline" class="inven error"><br><br>
						下限值：<input type="text" name="invenUnderline" class="inven error">
		    	</div>
	    	<div style="position: relative;top: 30%;">
	    		<input type="button" id="tijiao" value="确定" style="width: 20%;height: 13%;position: relative;left: -4%;">
	    		<input type="button" class="colse" value="关闭" style="width: 20%;height: 13%;position: relative;left: 4%;">
	    	</div>
	    	</div>
	    	<input type="submit" id="submit" name="submit" style="display: none;">
	    	</form>
    </div>
   <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/fininv/ea_getPageHomeData.jspa?pageNumber=${pageNumber}"></c:param>
</c:import>
		<iframe name="hidden"  style="display: none;"></iframe>

  </body>
</html>
