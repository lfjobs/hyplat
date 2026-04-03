<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>更换打印登记证</title>
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
._con{ width:524px; height:290px; margin:auto; background:url('<%=basePath%>images/ea/driving/djz_01.jpg') no-repeat; background-size:524px 290px; position:relative;}
._con .luru tr td{ line-height:20px;}
._con .luru tr td label{ margin-left:10px; float:left; line-height:12px;}
</style>
</head>
<body>

	<div class="table_con">
<div class="_con">
  <table class="luru fl" width="100%" border="0" cellspacing="1" cellpadding="1" style="margin-top: 58px;">
  <tr>
    <td width="128px">&nbsp;</td>
    <td width="150px"><label>${obj[16] }</label></td>
    <td width="10px">&nbsp;</td>
    <td width="56px">&nbsp;</td>
    <td width="150px"><label><input type="text" style="border: 0 ;font-size: 10px;" value="${obj[17] }" id="certificateNumber"/></label></td>
    <td width="">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><label>${obj[18] }</label></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td><label id="carnum">${obj[15] }</label></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><label>${obj[27] }</label></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td><label>${obj[14] }</label></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><label>${obj[34] }</label></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td><label>${obj[20] }</label></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><label>${obj[40] }</label></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td><label>${obj[21] }</label></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><label>${obj[35] }</label></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><label>${obj[22] }</label></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><label>${obj[23] }</label></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td><label style=" margin-left:34px;">${obj[24] }</label></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><label>${fn:substring(obj[25], 0, 10)}</label></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td><label>${fn:substring(obj[26], 0, 10)}</label></td>
    <td>&nbsp;</td>
  </tr>
</table>

</div>
</div>
<div align="center"  id="wentitishi"><input type="button" value="保存" id="submiedate" />&nbsp;&nbsp;&nbsp;&nbsp;<lable style="color:red;font-size:10px;">注:此按钮存在即可修改登记证号</lable></div>
</body>
<script type="text/javascript">
var basePath = "<%=basePath%>";
	$("#submiedate").click(function(){
	var carlisc = $("#carnum").html();
	var certificateNumber = $("#certificateNumber").val();
		var updateurl = basePath
				+ "ea/productregister/sajax_ea_updateregistration.jspa?liscennum=";
		$.ajax({
			url : encodeURI(updateurl+ carlisc+ "&date="
					+ new Date().toLocaleString()+"&edit="+certificateNumber),
			type : "post",
			async : true,
			dataType : "json",
			success : function cbf() {
				$("#wentitishi").hide();
				alert("操作已成功！");
				
			},
			error : function cbf() {
				alert("数据保存失败！");
			}
		});
	});
</script>
</html>
