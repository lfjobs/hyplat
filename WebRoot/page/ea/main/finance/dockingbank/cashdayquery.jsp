<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.tiantai.nwa.tbank.bo.BankAccount"%>
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
 <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
        </script>
<title>银行日记账</title>
<script type="text/javascript"> 
</script>

<script type="text/javascript">
var basePath = "<%=basePath%>";
$(function(){
 $("#tosearch").click(function(){
 						var _account = $("#accountlist").find("option:selected").text().split("|");				
						$("#accountno").val(_account[0]);
						$("#banksx").val(_account[1]);
                        $(".put3").trigger("blur");
	                    if ($("#SearchForm .error").length) {
	                        alert("请填完所有必填项");
	                        return;
	                    }
                    window.open(basePath + "/ea/bankcashdayquery/ea_showAccountList.jspa?innerAction=showCashDayList&sdate="+$("#sdate").val()+"&accountno="+$("#accountno").val()+"&banksx="+$("#banksx").val(),'newwin','toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
                });
});
 
</script>
</head>
<%
	List accountList = (List) request.getAttribute("accountList");
	Iterator iter = accountList.iterator();
%>
<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">
<form  name="SearchForm" method="post" id="SearchForm">
<input type="hidden" name="banksx" id="banksx" value=""/>
<input type="hidden" name="accountno" id="accountno" value=""/>
<div class="main" >
<table width="98%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#D1DDE9" style="margin-top:8px" >
		<tr bgcolor="#E7E7E7" >
            <td height="24" width="20%" align="left" bgcolor="#d8e6f4">&nbsp;<span class="txt">查询</span>&nbsp;</td>
            <td width="60%" height="24" bgcolor="#d8e6f4">&nbsp;</td>
			<td width="20%"  height="24" align="right" bgcolor="#d8e6f4">
           </td>
  		</tr>
</table>
		 <table id="cataffSearchTable" width="98%" height="120" align="center" style="border:#D1DDE9 1px solid;">
                    <tr height="20">
                        <td width="35%"  align="right" class="txt02">
                            	选择银行账号：
                        </td>
                        <td width="40%">
                            <select name="accountlist" id="accountlist">
								<%
									while (iter.hasNext()) {
										BankAccount bankAccount = (BankAccount) iter.next();
								%>
								<option value="<%=bankAccount.getPkey()%>"><%=bankAccount.getAccount()%>|<%=bankAccount.getBanksx()%>|<%=bankAccount.getProvcode()%>|<%=bankAccount.getCurrency()%></option>
								<%
									}
								%>
							</select>
                        </td>
                    </tr>
                    <tr height="20">
                        <td width="35%"  align="right" class="txt02">
                            	款源日期：
                        </td>
                        <td width="40%">
                            <input name="sdate" id="sdate" class="put3" onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', onpicked:function(){;}, maxDate:'%y-%M-{%d-1}'})"
								readonly size="25"/>
                        </td>
                    </tr>
                     <tr height="20" align="center">
                        <td  colspan="2"  align="center">
                            <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                        </td>
                    </tr>
                </table>
</div>
</form>
</body>
</html>