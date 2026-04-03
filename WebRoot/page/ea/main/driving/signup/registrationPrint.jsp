<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登记证打印</title>
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
<style>
body{ margin:0; padding:0px;}
a, a:link { color: #222; text-decoration: none;}
a:visited {  }
a:active, a:hover {}
a:focus { outline: none; }
.fl{ float:left;}
.fr{ float:right;}
.clear { diplay: block!important; float: none!important; clear: both; overflow: hidden; width: auto!important; height: 0!important; margin: 0 auto!important; padding: 0!important; font-size: 0; line-height: 0;}
.table_con{font-family: '宋体 Regular','宋体'; font-size:10px; float:left; width:100%;; margin:0; padding:0px; color:#000;margin-top:30px;  margin-left:10px; }
._con{ width:524px; height:317px; margin:auto; background:url(<%=basePath%>images/ea/driving/djz_02.jpg) no-repeat; background-size:524px 317px; position:relative;}
._con .dengji{ line-height:20px; }
._con .dengji label{ float:left;line-height:14px; }
</style>
<SCRIPT LANGUAGE="JavaScript">
  function atile(){
    var myDate = new Date();
    var mytime= myDate.toLocaleDateString();    //获取当前时间
    document.getElementById("printtime").innerHTML= mytime;
  }
</SCRIPT>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script type="text/javascript">
			var basePath = '<%=basePath%>';
			var carCylinderId = "";
</script>
</head>
<body onload="atile()">
<div class="table_con">
<div class="_con">
<table width="470px" class="dengji fl" border="0" cellspacing="1" cellpadding="1" style="margin-top:70px; margin-left:32px;text-align:center">
 <c:forEach items='${sessionScope.registrationlist}' var="obj" varStatus="i">
			<tr id="${obj.carCylinderId}">
					<td width="58px"><lable>${obj.cylinderNum}</td>
					<td width="50px"><lable>${obj.cylinderType}</lable></td>
            		<td width="54px"><lable> ${obj.manufactureCompany}</lable></td>
            		<td width="50px"><lable>${fn:substring(obj.leavefactorydate, 0, 10)}</lable></td>
		            <td width="42px"><lable>${obj.weight}</lable></td>
		            <td width="30px"><lable>${obj.volume}</lable></td>
		            <td width="30px"><lable>  ${obj.texture}</lable></td>
		            <td width="40px"><lable>${obj.fiber}</lable></td>
		            <td width="34px"><lable>${obj.resin}</lable></td>
		            <td><lable>${fn:substring(obj.issuedate, 0, 10)}</lable></td>
			</tr>
		 </c:forEach>
</table>

<p style="position:absolute; left:220px; bottom:70px;">打印时间：<lable id="printtime"> </lable></p>
<p style="position:absolute; left:220px; bottom:50px;">打证单位：四川兰特清洁汽车安全评价检测有限公司</p>

</div>
</div>
</body>
</html>
