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
<title>日记账管理s</title>
<style type="text/css">
.xx{
	color:#FF0000;
	margin-right:2px;}
</style>
<script type="text/javascript">
var basePath = "<%=basePath%>";
var zz="${zz}";
var treeid = "<%=c.getCompanyID()%>";
var treename = "<%=c.getCompanyName()%>";
$(function(){
 $("#tosearch").click(function(){
                        $(".put3").trigger("blur");
	                    if ($("#SearchForm .error").length) {
	                       // alert("请填完所有必填项");
	                        return;
	                    }
                    window.open(basePath + "/ea/statement/ea_toStatements.jspa?zz="+zz+"&companyid="+$("#deptID").val()+"&sdate="+$("#sdate").val());
                });
                
                var pid="";
		var pname="";
			pid=treeid;
			pname=treename;
	
			var url = basePath
					+ "ea/company/sajax_n_ea_getCompanyList.jspa?date="
					+ new Date().toLocaleString();
			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var companylist = member.companylist;
							var data1 = new Array();
							data1[0] = {
									id : pid,
									pid : '-1',
									text :pname
								};
							for (var i = 0; i < companylist.length; i++) {
								data1[i + 1] = {
									id : companylist[i].companyID,
									pid : companylist[i].companyPID,
									text : companylist[i].companyName
								};
							}
							var ts3 = new TreeSelector($("#deptID")[0], data1,
									-1);
							ts3.createTree();
						},
						error : function cbf(data) {
							alert("数据获取失败！");
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
		 <table id="cataffSearchTable" width="98%" height="180" align="center" style="border:#D1DDE9 1px solid;">
                    <tr>
						<td align="right">
							公司名称：
						</td>
						<td>
							<select id="deptID" name="companyid">
							</select>
						</td>
					</tr>
                    <tr height="40">
                        <td width="35%"  align="right" class="txt02">
                            	<span class="xx">*</span>款源日期：
                        </td>
                        <td width="40%">
                            <input name="sdate" id="sdate" class="put3"  onfocus="daymonth(this);"/>
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