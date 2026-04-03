<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
		<title>资产负债表总</title>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript">
		 var  basePath="<%=basePath%>";
		 var tabPSymbol = "${tabPSymbol}";



		 $(document).ready(function() {           
           $("#main1").css({height:400}).attr("src",basePath+"ea/debtasset/ea_getDebtAssetsList.jspa?tabPSymbol="+tabPSymbol);
	       $("#main2").css({height:250}).attr("src",basePath+"ea/debtasset/ea_getIncomeList.jspa?invCcpbsgl.tabSymbol=js");
	   });
	   
	   
	   function getIncomeList(){
	      var tabSymbol = document.getElementById("tabSymbol").value;
	      $("#main2").css({height:300}).attr("src",basePath+"ea/debtasset/ea_getIncomeList.jspa?invCcpbsgl.tabSymbol="+tabSymbol+"&dats="+new Date());
	   }
	</script>
<style type="text/css">
body{
margin:0;
height:auto;
}
</style>
	</head>
	<body style="overflow:hidden;">

		
	     <iframe src="" name="main1" marginwidth="0"
			style="width: 100%; height: 50%;" scrolling="no" marginheight="0"
			frameborder="0" id="main1" border="0" framespacing="0"
			noresize="noResize" vspale="0">
		</iframe>
		
		<iframe src="" name="main2" marginwidth="0"
			style="width: 100%; height: 50%" scrolling="no" marginheight="0"
			frameborder="0" id="main2" border="0" framespacing="0"
			noresize="noResize" vspale="0">
		</iframe>
		<input type="hidden"  value="" id="tabSymbol"/>
						
	
			
		





</body>
</html>
