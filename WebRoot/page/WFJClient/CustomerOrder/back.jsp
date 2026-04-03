<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCustomer"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<title>支付宝未付款返回跳转</title>
</head>
<script type="text/javascript">
$(document).ready(function(){
	var flag = "${param.flag}";
    var user="<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount()%>";
    var sccId="<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId()%>";
    var staffid = "<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid()%>";

	if(flag == 'gold'){//金币池充值
		document.location.href="<%=basePath%>ea/jinbi/ea_getwfjchongzhi.jspa?user="+user+"&sccid="+sccId+"&khd=00";
	}else if(flag == 'order'){//订单
		document.location.href ="<%=basePath%>ea/pobuy/ea_getPhoneOrdersList.jspa?staid="+staffid + "&sccId="+sccId ;
	}else if(flag == 'score'){//积分充值
		document.location.href="<%=basePath%>ea/bonuspoints/ea_getWfjJifen.jspa?user="+user+"&sccid="+sccId+"&khd=00";
	}else{
		var url = "${ sessionScope.url}";		
		document.location.href =url;
	}
	
});
</script>
<body>

</body>
</html>