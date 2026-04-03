<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/ea/main.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/table.css" rel="stylesheet" type="text/css" />
 <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<title>凭证明细账和总账报表</title>
<script type="text/javascript"> 
</script>
<script type="text/javascript">
var basePath = "<%=basePath%>";
var zz="${zz}";
var kemu='';
var keid='';
var myDate = new Date();
var ddate= myDate.toLocaleDateString();    //获取当前日期
$(function(){
 $("#tosearch").click(function(){
 						if($("#ming option:selected").text()=="请选择"){
							$("#ming option:selected").attr("text","");
							}
							var s1=$("#sdate").val().substring(0,4)+$("#sdate").val().substring(6,7);
							var s2=ddate.substring(0,4)+ddate.substring(5,6);
	                    if(zz=="00"){
							if($("#sdate").val()==""){
							alert("请先查询具体时间！");
							return;
							}else{
							window.open(encodeURI(basePath+"/ea/vaccount/ea_toPrint.jspa?&zz="+zz+"&sdate="+$("#sdate").val()+"&kemu="+$("#ming option:selected").text()+"&keid="+$("#ming").val()));
							}
						}else{
							if($("#ming option:selected").text()=="" || $("#ming").val()=="" || $("#sdate").val()=="" ){
							alert("请先查询具体科目与时间！");
							return;
							}
							else{
							window.open(encodeURI(basePath+"/ea/vaccount/ea_toPrint.jspa?&zz="+zz+"&sdate="+$("#sdate").val()+"&kemu="+$("#ming option:selected").text()+"&keid="+$("#ming").val()));
							}
						}
                });
});
 
</script>
</head>
<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">
<form  name="SearchForm" method="post" id="SearchForm">
<input type="submit" name="submit" style="display:none"/>
<div class="main" >
<table width="98%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#D1DDE9" style="margin-top:8px" >
		<tr bgcolor="#E7E7E7" >
            <td height="24" width="20%" align="left" bgcolor="#d8e6f4">&nbsp;<span class="txt">查询</span>&nbsp;</td>
            <td width="60%" height="24" bgcolor="#d8e6f4">&nbsp;</td>
			<td width="20%"  height="24" align="right" bgcolor="#d8e6f4">
           </td>
  		</tr>
</table>
		 <table id="SearchTable" width="98%" height="180" align="center" style="border:#D1DDE9 1px solid;">
                    
                    <tr height="40">
                        <td width="35%"  align="right" class="txt02">
                            	查询日期：
                        </td>
                        <td width="40%">
                            <input name="sdate" id="sdate"   onfocus="daymonth(this);"/>
                        </td>
                       </tr>
                        <tr height="40">
						<td width="35%"  align="right" class="txt02">科目名称：</td>
						<td width="40%">
						<s:select list="%{#request.sublist}" style="width:200px" headerKey="" headerValue="请选择" listKey="subjectsNumbers" name="vouchers.subjectsID" listValue="subjectsName" theme="simple" id="ming">
						</s:select>
						</td>
					</tr>
                     <tr height="40">
                        <td  colspan="1"  align="center" class="txt02">
                            <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                        </td>
                    </tr>
                </table>
</div>
</form>
</body>
</html>