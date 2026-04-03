<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>timeout</title>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var financialbillID="${financialBill.financialbillID}";
	var message = '${param.message}';
	var t = 0;

	if(message==null||message==""){
	    message='${message}';
	}
	switch (message) {
	
		case '10':
			alert("数据异常");
			break;
        case '11':

            break;
		default:
            if(t==0) {
                alert("操作成功");
            }
			break;
			
	}
	if (parent.notoken != "undefined") {
		parent.notoken = 0;
	}
	if (parent.token == 1) {
		if (!confirm("是否继续添加？")) {
			parent.token = 2;
		}
	}
	if (parent.token == 11) {
		if (!confirm("是否继续删除？")) {
			parent.token = 2;
		}
	}
	if (parent.token == 12) {
		if (!confirm("是否继续？")) {
			parent.token = 2;
		}
	}
	if (parent.token == 13) {
		parent.re_load();
	}
	if (parent.token == 14) {
		parent.re_load();
		if (!confirm("是否提交？")) {
			parent.token = 2;
		}
	}
	if (parent.token == 2) {
		parent.re_load();
	}
	if (parent.token == 3) {
		parent.parent.re_load();
	}
	if (parent.token == 5) {
		if (confirm("是否打印单子？")) {
			var urlPrint=basePath + "/ea/purchase1/ea_toPrintPurchase.jspa?cashierBills.cashierBillsID="+cashierBillsID;
			window.open(urlPrint);
		}else if(!confirm("是否继续添加 ？")){
			parent.re_load();
		}
	}
	if (parent.token == 6) {//跳转下一流程token状态
		parent.re_skip();
	}
</script>
</head>

<body>
</body>
</html>
