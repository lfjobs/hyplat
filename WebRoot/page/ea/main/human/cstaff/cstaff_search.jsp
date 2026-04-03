<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>人员信息</title>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
$(function(){
    $("#find").click(function(){
        //		document.location="<%=basePath%>page/ea/main/human/personal/personal_info.jsp";
        
        document.all.searchForm.action = "<%=basePath%>" + "ea/cstaff/ea_toSearchCStaff.jspa";
        document.all.searchForm.submit();
    });
})
</script>
</head>
<body>
<form name="searchForm" method="post">
<div class="unitlib_list_right02" style="height:500px;">
   <table width="100%"　border="0" align="center" cellpadding="0" cellspacing="0" class="table">
    	<tr>
          <td width="3%" rowspan="11" align="right" class="td_bg">&nbsp;</td>
          <td width="24%" align="left" class="td_bg"></td>
      </tr>
      <tr>
        <td width="24%" rowspan="9" align="left" valign="top" class="td_bg">
      </td>
        <td height="30" align="center" class="txt01">查询条件</td>
        <td>&nbsp;</td>
        <td height="36">&nbsp;</td>
      </tr>
      <tr>
        <td width="12%" height="30" align="right" class="td_bg">人员编号：</td>
        <td colspan="2" class="td_bg"><input  name="searchCStaff.staffCode"  type="text" class="input01"  value=""size="14"/></td>
      </tr>
       <tr>
        <td width="12%" height="30" align="right" class="td_bg">人员姓名：</td>
        <td colspan="2" class="td_bg"><input name="searchCStaff.staffName" type="text" class="input01"  size="14"/>
										<input name="search" type="hidden" value="search"  size="14"/>
		</td>
      </tr>
       <tr>
        <td width="12%" height="30" align="right" class="td_bg">身份证：</td>
        <td colspan="2" class="td_bg"><input name="searchCStaff.staffIdentityCard" type="text" class="input01"  size="14"/></td>
      </tr>
     
      <tr>
        <td height="39" colspan="2" align="center" class="td_bg">
          <input type="button" class="ACT_btn" id="find"   value=" 查询 " />
        </td>
        <td width="16%" height="39" align="left" class="td_bg">&nbsp;</td>
      </tr>
      <tr>
      	<td height="18" colspan="5" align="right" class="td_bg">&nbsp;</td>
      </tr>
  </table>
</div>
</form>
</body>
</html>