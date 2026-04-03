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
		<title>项目说明</title>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/costSheet_printlist.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var basePath="<%=basePath%>";
        	var proID="${projectManage.proID}";

      
        </script>
        <style type="text/css">
        .tables{
          color:#000000;
         font-size:14pt;
        
        }
        </style>
	</head>
	<body>
	<div style="padding-top:40px;overflow:auto;width:950px;margin:auto;text-align:center;">

			<h2>
				编制说明
			</h2>
			
			<div style="text-align:left;">
			<p>工程名称：${productPack.goodsName}</p>
			<h5>编制依据：______________________________</h5>
			<p>
			 ${productPack.remark}
			</p>
			</div>
		</div>
	</body>
</html>
