<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>派工单打印预览</title>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<style>
.fontsize{
font-size: 12px;
font-family: 微软雅黑;
border-collapse:collapse; 

}
</style>
<script type="text/javascript">
			var basePath = '<%=basePath%>';
			var carCylinderId = "";

</script>
</head>
<body>
<div align="center" >
<h4 >CNG气瓶检验派工单</h4>

<div align="center">
<table width="60%"  cellspacing="1" cellpadding="1" border = "1" style = "border-color: 1px solod black;" class="fontsize">
  <tr>
    <td width="13%" class="bg_color">检验人员</td>
    <td width="37%">${obj[0] }</td>
    <td width="13%" class="bg_color">经手人</td>
    <td width="37%">${obj[1] }</td>
  </tr>
  <tr>
    <td >车牌号码</td>
    <td>${obj[2] }</td>
    <td >检验编号</td>
    <td>${obj[3] }</td>
  </tr>
  <tr>
    <td >气瓶编号</td>
    <td>${obj[4] }</td>
    <td>检验日期</td>
    <td>${fn:substring(obj[5], 0, 10)}</td>
  </tr>
  <tr>
    <td>下次检验日期</td>
    <td colspan="3">${fn:substring(obj[6], 0, 10)}</td>
    </tr>
</table>

</div>
</div>


</body>
</html>
