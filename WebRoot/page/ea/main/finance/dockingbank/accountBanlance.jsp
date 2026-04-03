<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="hy.ea.bo.Company"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company)session.getAttribute("currentcompany"); 	
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
 <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
        </script>
<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>        
<title>账户余额</title>
<style type="text/css">
.xx{
	color:#FF0000;
	margin-right:2px;}
</style>

<script type="text/javascript">
	
		
</script>
</head>
<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">

<div class="main" >
<table width="98%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#D1DDE9" style="margin-top:8px" >
		<tr bgcolor="#E7E7E7" >
            <td height="24" width="20%" align="left" bgcolor="#d8e6f4">&nbsp;<span class="txt">账户余额信息</span>&nbsp;</td>
            <td width="60%" height="24" bgcolor="#d8e6f4">&nbsp;</td>
			<td width="20%"  height="24" align="right" bgcolor="#d8e6f4">
           </td>
  		</tr>
</table>
		 <table id="cataffSearchTable" width="98%" align="center" style="border:#D1DDE9 1px solid;">
                    <tr>
						<td align="right">账户别名：
						</td>
						<td>
						</td>
					</tr>
                    <tr height="40">
                        <td width="35%"  align="right" class="txt02">注册账号： </td>
                        <td width="40%">&nbsp;</td>
                    </tr>
                     <tr height="40">
                        <td width="35%"  align="right" class="txt02">币种： </td>
                        <td width="40%">&nbsp;</td>
                    </tr>
                     <tr height="40">
                        <td width="35%"  align="right" class="txt02">开户行： </td>
                        <td width="40%">&nbsp;</td>
                    </tr>
                     <tr height="40">
                        <td width="35%"  align="right" class="txt02">开户时间： </td>
                        <td width="40%">&nbsp;</td>
                    </tr>
                     <tr height="40">
                        <td width="35%"  align="right" class="txt02">可用余额： </td>
                        <td width="40%">&nbsp;</td>
                    </tr>                     
                </table>
</div>

</body>
</html>