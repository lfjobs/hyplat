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
<script src="<%=basePath%>js/jquery-1.3.1.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
 <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
        </script>
<link href="<%=basePath%>css/ea/main.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/table.css" rel="stylesheet" type="text/css" />
<title>合格率分析</title>
<script type="text/javascript"> 
</script>
<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>

<script type="text/javascript">
var basePath = "<%=basePath%>";
var other='<%=request.getParameter("other")%>';//区别导航页面 
$(function(){
  $("#tosearch").click(function(){
                        $(".put3").trigger("blur");
	                    if ($("#SearchForm .error").length) {
	                        alert("请填完所有必填项")
	                        return;
	                    }
                    $("#SearchForm").attr("action", basePath+"ea/driving/ea_toSearchAnalysis.jspa?other="+other);
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
                            教练名称：
                        </td>
                        <td width="40%">
                            <input name="dtDrivingPrincipalType.coachname" />
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
							<td align="right">
							科目类型：
							</td>
							<td align="left"">
							<select name="dtDrivingTest.examtype" >
								<option value="">全部</option>
								<option value="01">科一</option>
								<option value="02">科二</option>
								<option value="03">科三</option>
								<option value="04">科四</option>
							</select>
							</td>
					</tr>
                    <tr height="40">
                        <td  colspan="1"  align="center" class="txt02">
                         <input name="docstatus"  type="hidden" value="${param.docstatus}"/>
                         <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                        </td>
                    </tr>
                </table>
</div>
</form>
</body>
</html>