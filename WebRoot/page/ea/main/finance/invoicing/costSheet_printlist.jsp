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
		<title>项目招标打印</title>
		<link href="<%=basePath%>css/ea/admin_main.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/costSheet_printlist.js"></script>
		<script type="text/javascript">
        

        	var basePath="<%=basePath%>";
        	var ppID="${productPack.ppID}";
        	


      
        </script>
	</head>
	<body>

		<div style="text-align: center;">
			
			<input type="button" value="封面" class="btn01" id="cover"
				onclick="getCostSheetBill('printcover')"/>
			<input type="button" value="预算投标总价" class="btn03"
				onclick="getCostSheetBill('printtprice')"/>
			<input type="button" value="项目说明" class="btn03"
				onclick="getCostSheetBill('printpexplain')"/>
			<input type="button" value="预算投标项目总价" class="btn03"
				onclick="getCostSheetBill('printpprice')"/>
			<input type="button" value="各预算总价" class="btn03"
				onclick="getCostSheetBill('printsprice')"/>
			<input type="button" value="各预算单价分类汇总" class="btn03"
				onclick="getCostSheetBill('printdprice')"/>
			<input type="button" value="打印预览" class="btn01" id="printinput"
				onclick="printPreview()"/>
			<input type="hidden" value="" id="print"/>
				
		</div>
		<iframe id="projectprint" style="margin:0px;width:100%;height:600px;"
			scrolling="yes" frameBorder=0" src="<%=basePath%>ea/costsheet/ea_getCoverContent.jspa?ppID=${productPack.ppID}&type=printcover"></iframe>
	</body>
</html>
