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
<title>打印（个人/单位）奖励汇总统计表</title>
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
	<div align="center" width="100%"height="100px" ><span style="font-size: 30px;">（个人/单位）奖励汇总统计表</span></div>
  <table class="agreement " align="center" width="850px" cellpadding="1" cellspacing="1">
	    <tr>
	      <th align="center" height="50px">单位</th>
	      <th align="center" >职务</th>
	      <th align="center" >姓名</th>
	      <th align="center" >荣誉名称</th>
	      <th align="center" >奖励金额</th>
	    </tr>
	    <%  int ss = 0 ;
	    	for(int i=0;i<beans.size();i++){
	    	Object[] obj = (Object[])beans.get(i);
	    %>
		<tr id="">
			<td align="center" height="50px">
				<%=obj[0] %>
			</td>
			<td align="center" >
				<%=obj[1] %>
			</td>
			<td align="center" >
				<%=obj[2] %>
			</td>
			<td align="center" >
				<%=obj[3] %>
			</td>
			<td align="center" >
				<%=obj[4] %>
				<% ss += Integer.parseInt(obj[4].toString()); %>
			</td>
		</tr>
	<%} %>
		<tr id="">
			<td align="center" height="50px">
				合计：
			</td>
			<td align="center" >
			</td>
			<td align="center" >
			</td>
			<td align="center" >
			</td>
			<td align="center" >
				<%=ss %>
			</td>
		</tr>
  </table>
</div>
</form>
</body>
</html>

