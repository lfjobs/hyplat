<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>

<body>
<div class="buy_mess">
            <ul class="buyer">
                <li><h4>收货信息</h4></li>
            </ul>
            <ul class="site">
                <li>
                    <i><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/ico-location.png" alt=""></i>
                    <p>地址：<span>${orderBill.receiveaddress}</span></p>
                </li>
                <li>
                    <i><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/ico-tell.png" alt=""></i>
                    <p>电话：<span>${orderBill.receivetel}</span>&nbsp;<span>${orderBill.receivename}</span></p>
                </li>
            </ul>
</div>
</body>
</html>

