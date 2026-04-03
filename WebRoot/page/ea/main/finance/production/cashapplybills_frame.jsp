<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>现金申请明细审批单框架</title>
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
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>/css/admin_main2.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/production/cashapplybills_frame.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jquery.messager.js"></script>

		<script type="text/javascript">
        	var cashierBillsID=""; 
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var notoken = 0;
        	var token = 0;
        	var showDocument=false;
        </script>
	</head>
	<body>
		<iframe name="cashframe" id="cashframe"
			style="WIDTH: 100%; HEIGHT: 280px; display: block;"
			src="<%=basePath%>ea/cashapplybills/ea_toCash.jspa"
			frameBorder="0" scrolling="no" noresize="noresize"></iframe>
		<div>
			<form name="cashform" id="cashform" enctype="multipart/form-data"
				method="post">
				<s:token></s:token>
				<input type="submit" name="submit" style="display: none" />
				<table class="flexme11" id="cashapply">
					<thead>
						<tr>
							<th width="30" align="center">
								选择
							</th>
							<th width="80" align="center">
								部门
							</th>
							<th width="150" align="center">
								项目单号
							</th>
							<th width="100" align="center">
								使用责任人
							</th>
							<th width="150" align="center">
								费用或品名名称(摘要)
							</th>
							<th width="80" align="center">
								数量
							</th>
							<th width="80" align="center">
								单价
							</th>
							<th width="80" align="center">
								金额
							</th>
							<th width="80" align="center">
								申请时间
							</th>
							<th width="80" align="center">
								备注
							</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</form>
		</div>
		<iframe name="hidden" class="model" width="100%" height="0"></iframe>
	</body>
</html>
