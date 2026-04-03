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
<title>银行账户查询</title>
<style type="text/css">
.xx{
	color:#FF0000;
	margin-right:2px;}
</style>

<script type="text/javascript">
	//$(function(){ $("input[type=button][name='tosearch']").click(function () { alert("aaa"); }); })
	$(function(){ $("input[type=button][name='tosearch']").bind("click", function () 
	{ 
		
		if ($('#dockingbank').val()=='') 
		{
			alert('请选择银行业务系统');
			return false;
		}
		if ($('#transName').val()=='') 
		{
			alert('请选择查询交易名称');
			return false;
		}	
		
		$('#SearchForm').submit();		
	}
	); })	
	
	function selectOnChange()
	{			
		$.ajax(
				{type:"POST",
				url:"<%=basePath%>ea/dockingbank/ea_getDockingBankTransList.jspa",
				data:{enameSx:$("#dockingbank").val()},
				dataType:"json",
				success:function(returnMessage)
						{							
							$("#transName").empty();							
							$("#transName").append("<option value=''>请选择</option>");
							// 处理简单数据类型如{a:"1",b:"2",c:"3"}
							//var json = eval('(' + data + ')');							
							//$.each(json, function (name, value) {							
							//	$("#transName").append("<option value=" + value +">" + name + "</option>");
							//});
							
							// 处理对象数组类型如{{p1:"a",p2:"b",p3:"c"},{p1:"d",p2:"e",p3:"f"},{p1:"g",p2:"h",p3:"i"}}							
							for(var i=0;i<returnMessage.length;i++){
								$("#transName").append("<option value=" + returnMessage[i]['wbaTransCode'] +">"
								                           + returnMessage[i]['transName'] + "</option>");
								//for(var key in returnMessage[i]){
									//alert(key + ':' + returnMessage[i][key]);
									//alert(returnMessage[i]['transName']);
								//}
							 }
							
						}
				}
			);			
	}	
		
</script>
</head>
<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">
<form  name="SearchForm" method="post" id="SearchForm" action="<%=basePath%>ea/dockingbank/ea_queryAccountBanlance.jspa">
<div class="main" >
<table width="98%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#D1DDE9" style="margin-top:8px" >
		<tr bgcolor="#E7E7E7" >
            <td height="24" width="20%" align="left" bgcolor="#d8e6f4">&nbsp;<span class="txt">查询</span>&nbsp;</td>
            <td width="60%" height="24" bgcolor="#d8e6f4">&nbsp;</td>
			<td width="20%"  height="24" align="right" bgcolor="#d8e6f4">
           </td>
  		</tr>
</table>
		 <table id="cataffSearchTable" width="98%" height="180" align="center" style="border:#D1DDE9 1px solid;">
                    <tr>
						<td align="right"><span class="xx">*</span>银行业务系统：
						</td>
						<td>							
                            <s:select id="dockingbank" name="dockingbank" list="%{#request.dockingBanklist}" headerKey="" headerValue="请选择" listKey="enameSx" listValue="bankName" onChange="selectOnChange()"></s:select>
						</td>
					</tr>
                    <tr height="40">
                        <td width="35%"  align="right" class="txt02">
                            	<span class="xx">*</span>查询交易名称：
                        </td>
                        <td width="40%">
                            <select id="transName" name="transName"></select>
                        </td>
                    </tr>
                     <tr height="40">
                        <td  colspan="1"  align="center" class="txt02">
                            <input type="button" class="input-button" name="tosearch" id="tosearch" value=" 查询 " />                        </td>
                    </tr>
                </table>
</div>
</form>
</body>
</html>