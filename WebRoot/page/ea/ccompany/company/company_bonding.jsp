<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>单位归属邦定</title>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet" type="text/css">
</head>
<body>
<form name="bonding" method="post">
<div class="qh_right">
   <div class="qh_gg_nav">&nbsp;绑定上级</div> 
          <table width="100%" height="97" border="0" cellpadding="0" cellspacing="0" style="margin-left:5px;">
            <tr >
              <td width="32%" height="37" align="right"><span>父单位组织机构名称：</span></td>
              <td width="68%"><span class="td_bg">
                <input name="companyIdentifier" type="text" value="${companyIdentifier}" id="user_id"/>
              </span></td>
              
            </tr>
            <tr>
              <td height="30" align="right"><span>父单公司名称：</span></td>
        <td ><span class="td_bg">
                ${companyName}
              </span></td>
            </tr>
            <tr>
              <td height="30" align="center"></td>
              <td height="30"><span>
                <input type="submit" onclick="save()" class="ACT_btn" name="submit" value=" 邦定归属 " />
              </span></td>
            </tr>
  </table>
<s:token/>
</div>
</form>
<script type="text/javascript">
 var basePath="<%=basePath%>";
 function save(){
          document.all.bonding.action=basePath + "ea/ccompany/t_ea_companyBonding.jspa";
   		  document.all.bonding.submit();
    }
</script>
</body>
</html>
