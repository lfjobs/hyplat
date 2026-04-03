<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	List beans = (List)request.getAttribute("beans");
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>打印年度奖励汇总统计表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="te/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript">
</script>
<style type="text/css">
table,td,th{
	border:none;
	margin: 0px;
}	
.agreement{ background: #000;}
.agreement td,.agreement th{ background: #fff;}
</style>
</head>

  <body>
<div id="main_main" class="main_main" align="center"> 
	<p>&nbsp;</p>
	<div align="center" width="100%"height="100px" ><span style="font-size: 30px;">${reward.rewtimes }年度奖励汇总统计表</span></div>
  <table class="agreement " align="center" width="850px" cellpadding="1" cellspacing="1">
	    <tr>
	      	<th align="center" height="50px" colspan="4">基本信息</th>
	      	<th align="center" colspan="${num }">职能奖励</th>
	      	<th align="center" colspan="3">应发实发已发汇总</th>
	    </tr>
	    <tr>
	    	<td align="center" style="width:30px">序号</td>
	    	<td align="center" style="width:100px">部门</td>
	    	<td align="center" style="width:70px">职务</td>
	    	<td align="center" style="width:50px">姓名</td>
	    	<s:iterator value="beans">
	    		<td align="center">${honorName }</td>
	    	</s:iterator>
	    	<td align="center">应发合计</td>
	    	<td align="center" style="width:70px">已扣</td>
	    	<td align="center" style="width:70px">实发合计</td>
	    </tr> 
	   	<c:forEach var="entity" items="${beansl}" varStatus="step">
			<tr id="">
				<td align="center" height="40px">
					${step.index+1 }
				</td>
				<c:forEach var="x" begin="0" end="${maxnum }">
					<td align="center" >
						 ${entity[x]}
					</td>
				</c:forEach>
				<td align="center">
				</td>
				<td align="center" >
				</td>
				
			</tr>
		</c:forEach>
		  <tr>
	    	<td align="center" colspan="4">合计：</td>
	    	<c:forEach var="ent" items="${beanslist}" varStatus="step">
						<c:forEach var="x" begin="0" end="${num+1 }">
					<td align="center" >
						 ${ent[x]}
					</td>
				</c:forEach>
			</c:forEach>
	    	<td align="center"></td>
	    	<td align="center"></td>
	    </tr> 
  </table>
</div>
</form>
</body>
</html>

