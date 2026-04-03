<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>测试调整页面</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<!-- 最好引用 引用顺序不能错 -->
		<script type="text/javascript"src="<%=basePath%>js/accifr/js/category.js"></script>
		<script type="text/javascript">
			var basePath ='<%=basePath%>';
		</script>
		<style type="text/css">
			*{margin:0; padding:0;}
			table{border-collapse:collapse; border:none; width:700px;}
			td{border:solid #000 1px;}
			div{height:400px; display:block;}
			input{width:80px;}
			p{text-align:center;height:40px;}	
		</style>
	</head>
	<body style="background: #eff; margin-top: 15%;margin-left: 30%">
		<a href="#" onclick="javascript:category('pro',paret)">产品分类</a>
		<a href="#" onclick="javascript:category('wfj',paret)">微分金分类</a>		
	</body>
	<script>
	//父页面必须用此方法，以供子页(弹出层返回数据)调用**********************
	function paret(e){
		//alert(e);
	  	//隐藏弹出层
	  	$("#category").hide();
	}		
	</script>
</html>
