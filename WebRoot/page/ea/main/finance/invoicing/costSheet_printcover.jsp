<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>封面</title>
		<link href="<%=basePath%>css/ea/admin_main.css"
			rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/costSheet_printlist.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var journalNum="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var csbID="${costSheetBill.csbID}";
        	var pNumber=${pageNumber};
        	var token=0;
     
        
      
        </script>
        <style type="text/css">
        .tables{
          color:#000000;
       
        
        }
          .tables td{
          color:#000000;
         font-size:14pt;
        
        }
         .botooms{
        border:none;
        border-bottom:1px solid #000000;
        width:220px;
    
        }
        
        </style>
	</head>
	<body>
	<div style="padding-top:40px;overflow:auto;text-align:center;">
	  <div >
			<h4>
				${productPack.goodsName}
			</h4>
			<br/> <br/>
			<h2>
				工程量清单价报价单
			</h2>
			<p>
				合同编号：
			</p>
			</div>
			<table  class="tables" style="margin:200px auto 100px auto;" border = "0"  cellpadding="10" >
				<tr>
					<td align="right">投标人：</td>
					<td align="left"><input type="text"  class="botooms"  readonly value="${company.companyName}" /></td>
					<td>(单位盖章)</td>
				</tr>
				<tr>
					<td align="right">委托代理人：</td>
					<td align="left"><input type="text"  class="botooms" readonly value="${staff.staffName}" /></td>
					<td>签字盖章</td>
				</tr>
				<tr>
					<td align="right">造价工程师及注册证号：</td>
					<td align="left"><input type="text"  class="botooms"  readonly/></td>
					<td>(签字盖执业专用章)</td>
				</tr>
				<tr>
					<td align="right"> 编制日期：</td>
					<td align="left"><input type="text"  class="botooms" readonly value="${fn:substring(productPack.packagingDate,0,10)}"/></td>
					<td></td>
				</tr>

			</table>
		</div>
	</body>
</html>
