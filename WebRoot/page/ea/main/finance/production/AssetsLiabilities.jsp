<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资产负债表</title>
        <%
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
        %>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<style>
span{
	font-size:10px;
	}
.span1{
	font-weight:bold;
	}
.td1 {
	border-style: solid;
	border-width: 1px 1px 0px 0px;
	height:15px
	}
.td2 {
	border-style: solid;
	border-width: 1px 1px 0px ;
	background-color: #9CF;
	border-color: #000;
	height:15px
	}
.td3 {
	border-style: solid;
	border-width: 1px 1px;
	background-color: #9CF;
	border-color: #000
	}
.td4 {
	border-style: solid;
	border-width: 1px 1px 1px 0px;
	}
.td5 {
	border-style: solid;
	border-width: 1px 1px 0px 0px;
	background-color: #9CF;
	border-color: #000;
	height:15px
	}
</style>
<style media="print">
    #but{
       display:none;
    }
</style>
<script type="text/javascript">
var basePath="<%=basePath%>";
var bt="${bt}";
var ym="${ym}";
var dw="${dw}";
var re="${re}";
	$(function(){
		$("#but").click(function(){
			var d="td="+ym+"&dw="+dw+"&re="+re+"&ym="+ym;
			if(bt=="A"){
				window.location.href=basePath+"ea/ccpbsgl/ea_zfshowExcel.jspa?"+d;
			}else{
				window.location.href=basePath+"ea/ccpbsgl/ea_syzfshowExcel.jspa?"+d;
			}
		});
	});
</script>
</head>

<body>
<table width="850" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="8" align="center" height="30px"><h3>资产负债表</h3></td>
  </tr>
  <tr>
  	<td colspan="8">&nbsp;<input type="button" id="but" value="导出Ecxel"/></td>
  </tr>
  <tr>
  	<td colspan="3" align="left"><span>编制单位：${cname }</span></td>
    <td colspan="3" align="center"><span>报表日期：${ym }</span></td>
    <td colspan="2" align="right"><span>单位：${dw}</span></td>
  </tr>
  <tr>
  	<td colspan="8">&nbsp;</td>
  </tr>
  <tr>
    <td width="3%" align="center" class="td2"><span class="span1">序号</span></td>
    <td width="23%" align="center" class="td5"><span class="span1">资产</span></td>
    <td width="12%" align="center" class="td1"><span class="span1">年初余额</span></td>
    <td width="12%" align="center" class="td1"><span class="span1">期末余额</span></td>
    <td width="3%" align="center" class="td5"><span class="span1">序号</span></td>
    <td width="23%" align="center" class="td5"><span class="span1">负债和所有者权益（或股东权益）</span></td>
    <td width="12%" align="center" class="td1"><span class="span1">年初余额</span></td>
    <td width="12%" align="center" class="td1"><span class="span1">期末余额</span></td>
  </tr>

  <c:forEach var="arr" items="${objlist}">
  <tr>
    <td class="td2"><span>&nbsp;${arr[0]}</span></td>
    <td class="td5"><span>&nbsp;${arr[1]}</span></td>
    <td class="td1" align="right"><span>${arr[1]==null||arr[1]==""?"":arr[2]}&nbsp;</span></td>
    <td class="td1" align="right"><span>${arr[1]==null||arr[1]==""?"":arr[3]}&nbsp;</span></td>
  
    <td class="td5"><span>&nbsp;${arr[4]}</span></td>
    <td class="td5"><span>&nbsp;${arr[5]}</span></td>
    <td class="td1" align="right"><span>${arr[5]==null||arr[5]==""?"":arr[6]}&nbsp;</span></td>
    <td class="td1" align="right"><span>${arr[5]==null||arr[5]==""?"":arr[7]}&nbsp;</span></td>
  </tr>
  </c:forEach>
  <tr>
    <td class="td3"></td>
    <td class="td4" style="background-color: #9CF;"></td>
    <td class="td4"></td>
    <td class="td4"></td>
    <td class="td4"  style="background-color: #9CF;">&nbsp;&nbsp;</td>
    <td class="td4" style="background-color: #9CF;"></td>
    <td class="td4">&nbsp;&nbsp;</td>
    <td class="td4">&nbsp;&nbsp; </td>
  </tr>
</table>
</body>
</html>
