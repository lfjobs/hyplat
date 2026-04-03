<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>气瓶判废通知单填写</title>
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
<script type="text/javascript"
	src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<style>
*{ margin:0; padding:0px;}
a, a:link { color: #222; text-decoration: none; }
a:visited {  }
a:active, a:hover {}
a:focus { outline: none; }
.fl{ float:left;}
.fr{ float:right;}
.clear { diplay: block!important; float: none!important; clear: both; overflow: hidden; width: auto!important; height: 0!important; margin: 0 auto!important; padding: 0!important; font-size: 0; line-height: 0; }
.table_con{font-family: '宋体 Regular','宋体'; font-size:12px; float:left; width:900px; margin:0; padding:0px; color:#000;margin-top:30px;  margin-left:10px;margin-left:150px;}
.table_con h1{ font-size:18px; margin:0px;}
.left_t{ width:17px; height:29px; background:url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/left_t_c.png) no-repeat left bottom;}
.cross{ width:275px; height:30px; background:url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/cross.png) repeat-x center;}
.title_t{ width:310px; height:29px; line-height:30px;text-align:center;}
.right_t{ width:17px; height:29px; background:url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/right_t_c.png) no-repeat right bottom;}
.left{width:17px; height:880px; background:url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/vertical.png) repeat-y left;}
.right{width:17px; height:880px; background:url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/vertical.png) repeat-y right;}
.left_b{ width:17px; height:30px; background:url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/left_b_c.png) no-repeat left top; margin-bottom:30px;}
.cross_b{ width:866px; height:16px; background:url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/cross.png) repeat-x 5px; margin-bottom:30px;}
.right_b{ width:17px; height:30px; background:url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/right_b_c.png) no-repeat right top; margin-bottom:30px;}
._con{ width:866px; float:left; height:880px;}
._con h2{ float:left; height:30px; width:100%; text-align: right; margin-top:40px; font-size:14px; font-weight:normal;}
._con p{ line-height:30px;}
._con ._pp{ width:80px;}
._con ._bor{ border-bottom:1px solid #000;}
._con .content{ margin-top:50px;}
._con .zhang{ width:800px; line-height:30px;}
._con .zhang p{ width:100px; text-align:center; float:right;}
._con table { line-height:30px; margin-top:50px; float:left;}
._con table tr td{ background:#fff; text-align:center;}
._con table tr .bg_color{ background:#e3e3e3;}
._con .low{ width:100%; line-height:40px; text-align: center; margin-top:30px;}
.inputstyle{
border:1px solid;border-left-color: white;border-right-color: white;border-top-color: white;border-bottom-color: 1px solid black;width:30px;
}
.inputstylebig{border:1px solid;border-left-color: white;border-right-color: white;border-top-color: white;border-bottom-color: 1px solid black;width:80px;}
</style>

</head>
		<body style="overflow-Y:scroll" >
<div class="table_con fl" >
<div class="clear"></div>
<div class="left_t fl"></div>
<div class="cross fl"></div>
<h1 class="title_t fl">气瓶判废通知单</h1>
<div class="right_t fr"></div>
<div class="cross fr"></div>
<div class="clear"></div>
<div class="left fl"></div>
<div class="_con">
  <h2>编号：<input type="text"  class="inputstylebig"/></h2>
  <div class="clear"></div>
  <p class="content">　　根据《气瓶安全监察规程》：GBL9533-2001《汽车用压缩天然气钢瓶定期检验与评定》和川质监办[2009] 202号《关于车用压缩天然气气瓶管理有关问题的通知》的规定，经检验，您单位
  <input type="text"  readonly="readonly" class="inputstylebig" value="${cylinderInformation.licensenumber}"/> 气瓶共
  <input type="text" readonly="readonly" class="inputstyle" value="${sessionScope.countnum}"/>只已判废，并做破坏性处理。</p>
  <p >特此通知。</p>
  <div class="clear"></div>
  <p>&nbsp;</p>
  <p>&nbsp;</p>
  <p>检验：</p>
  <p>&nbsp;</p>
  <p>&nbsp;</p>
  <p>审核：</p>
  <p>&nbsp;</p>
  <p>&nbsp;</p>
  <p>批准：</p>
  <div class="clear"></div>
  <div class="zhang fl">
  <p>(检验单位章)</p>
  </div>
   <div class="zhang fl">
    <p> <span id="currentdate"></span></p>
  </div>
  <div class="clear"></div>
  <table width="100%" bgcolor="#3fb2ad" border="0" cellspacing="1" cellpadding="1">
  <tr>
    <td width="10%" class="bg_color">瓶号</td>
    <td width="15%" class="bg_color">制造单位</td>
    <td width="15%" class="bg_color">公称容积</td>
    <td width="15%" class="bg_color">判废原因</td>
    <td width="16%" class="bg_color">处理结果</td>
    <td width="29%" class="bg_color">备注</td>
  </tr>
  <tr>
    <td>${cylinderInformation.cylinderNum}</td>
    <td>${cylinderInformation.manufactureCompany}</td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
  </tr>
</table>
<div class="clear"></div>
<div class="low fl">
      注：本表一式两份，检验单位存档一份，气瓶产权单位（或所有这） 一份。
</div>
</div>
<div class="right fr"></div>
<div class="clear"></div>
<div class="left_b fl"></div>
<div class="cross_b fl"></div>
<div class="right_b fl"></div>
</div>

		<script type="text/javascript">
		$(document).ready(function(){
		var myDate = new Date();
		var currentdate = myDate.toLocaleDateString(); //获取当前日期
		$("#currentdate").html("日期:"+ currentdate);
		
	});
</script>
		</body>
</html>
