<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="hy.ea.bo.Company"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
 <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
        </script>
<link href="<%=basePath%>css/ea/main.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/table.css" rel="stylesheet" type="text/css" />
<title>工资管理1</title>
<script type="text/javascript"> 
</script>
<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>

<script type="text/javascript">
var basePath = "<%=basePath%>";
var treeID = '<%=session.getAttribute("organizationID")%>';
var comID = '<%=c.getCompanyID()%>';
var comName = '<%=c.getCompanyName()%>';
$(function(){
  var pcompanyID="<%=request.getParameter("result")%>";
	    if(pcompanyID == treeID){
	              pcompanyID = "";
	    }
		//查询总公司下的所有子公司
		var url = basePath + "ea/company/sajax_n_ea_getCompanyList.jspa?date="
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
								id : 'all',
								pid : '-1',
								text : '工资集团汇总'
							};
							for (var i = 0; i < companylist.length; i++) {
								data1[i + 1] = {
									id : companylist[i].companyID,
									pid : 'all',
									text : companylist[i].companyName
								};
							}
						var ts3 = new TreeSelector($("#companyList")[0], data1, -1);
						ts3.createTree();
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
					
					
         $("#tosearch").click(function(){
                        $(".put3").trigger("blur");
	                    if ($("#SearchForm .error").length) {
	                        alert("请填完所有必填项")
	                        return;
	                    }
                    $("#SearchForm").attr("action", basePath+"ea/logbooksummary/ea_getcompanyIntegral.jspa?");
                    document.SearchForm.submit.click();
                });
                
     
})
 
</script>
</head>
<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">
<form  name="SearchForm" method="post" id="SearchForm">
<input type="submit" name="submit" style="display:none"/>
<div class="main" >
<table width="98%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#D1DDE9" style="margin-top:8px" >
		<tr bgcolor="#E7E7E7" >
            <td height="24" width="20%" align="left" bgcolor="#d8e6f4">&nbsp;<span class="txt">查询条件</span>&nbsp;</td>
            <td width="60%" height="24" bgcolor="#d8e6f4">&nbsp;</td>
			<td width="20%"  height="24" align="right" bgcolor="#d8e6f4">
           </td>
  		</tr>
</table>

		 <table id="cataffSearchTable" width="98%" height="180" align="center" style="border:#D1DDE9 1px solid;">
                    <tr height="40">
                        <td width="35%"  align="right" class="txt02">
                            员工姓名：
                        </td>
                        <td width="40%">
                            <input name="staffName" />
                        </td>
                    </tr>
                    <tr height="40">
                        <td width="35%"  align="right" class="txt02">
                            公司名称：
                        </td>
                          <td width="40%">
                            <select id="companyList" name="companyID" >
								<%--<option value="">
									请选择公司
								</option>
							--%></select>
                        </td>
                    </tr>
                    <tr height="40">
                        <td width="35%"  align="right" class="txt02">
                            起始时间：
                        </td>
                          <td width="40%">
                            <input name="sdate"  class="put3"  onfocus="date(this);"/>
                        </td>
                    </tr>
                     <tr height="40">
                        <td width="35%"  align="right" class="txt02">
                            结束时间：
                        </td>
                          <td width="40%">
                            <input name="edate"  class="put3" onfocus="date(this);"/>
                        </td>
                    </tr>
                     <tr>
                        <td width="40%"  align="right">
                               查询方式：
                        </td>
                        <td width="40%">
                           <select name="arg" class="put3" >
                           	<option value="0">日期未分组</option>
                           	<option value="1">日期分组</option>
                           </select>
                        </td>
                    </tr>
                      <tr height="40">
                        <td width="35%"  align="right" class="txt02">
                            员工种类：
                        </td>
                        <td width="40%"><select id="staffType" name="staffcategoryid"></select> </td>
                        
                    </tr>
                    <tr height="40">
                        <td  colspan="1"  align="center" class="txt02">
                            <input name="result" type="hidden" value="<%=request.getParameter("result")%>"/>
                            <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                        </td>
                    </tr>
                </table>
</div>
</form>
<script type="text/javascript">
		$(document).ready(function(){
				                   //员工类别
       	var url = basePath +"ea/saudition/sajax_n_ea_getBillID.jspa?date="+new Date().toLocaleString();
        $.ajax({
                    url: url,
                    type: "get",
                    async: true,
                    dataType: "json",
                    success: function cbf(data){
		           		var member = eval("(" + data + ")");
		           		var nologin = member.nologin;
						if(nologin){
		                	document.location.href ="<%=basePath%>page/ea/not_login.jsp";
		                }
		           		var staffTypeList = member.staffTypeList;
		           		if (null != staffTypeList) {
		           			var htmlStr="<option value=''>请选择</option>";
			               	for (var i = 0; i < staffTypeList.length; i++) {
			               		htmlStr+="<option value='"+staffTypeList[i].codeID+"'>"+staffTypeList[i].codeValue+"</option>"	
			             	}
			             	}
			             $("#staffType").html(htmlStr);
           			},error: function cbf(data){
                          		alert("数据获取失败！")
                    }
         });	
		})
</script>
</body>
</html>