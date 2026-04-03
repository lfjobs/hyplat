<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ page import="hy.ea.bo.Company"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
          <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/";
        Company c = (Company)session.getAttribute("currentcompany");
         %>
		<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />         
        <title>wfj金币分配明细管理</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
     	<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
     	<script type="text/javascript" src="<%=basePath%>js/ea/finance/BenDis/dbbillList.js"></script>
        <script>
        	 var companyID = '<%=c.getCompanyID()%>';
             var basePath = "<%=basePath%>";
             var ppageNumber = '${pageNumber}';
             var pageSize = '${pageSize}';
             var weixinCompanyId='${weixinCompanyId}';
             var inforType='${inforType}';
             var sdate='${sdate}';
             var edate='${edate}';
             var token=0;
             var pl="${pl}";
             var hylb="${hylb}";
             var iisnull="${iisnull}";
             var str="";
             var zzorder ="${zzorder}";
        </script>
	</head>
	<body >
        <div class="main_main">
        	<form name="fenpeiForm" id="fenpeiForm" method="post">
        	<input type="hidden" name="cashierBills.cashierBillsID" id="caid" />
        	<input type="submit" name="submit" id="submit" style="display: none;"/>
        	</form>
            <table class="address">
                <thead>
				<tr class="tablewith">
					<th width="40" align="center">请选择</th>
					<th width="50" align="center">序号</th>
					<th width="150" align="center">公司</th>
					<th width="150" align="center">产品名称</th>
					<th width="70" align="center">数量</th>
					<th width="70" align="center">收款金额</th>
					<!-- <th width="70" align="center">金币(佣金)</th>
					<th width="150" align="center">分配单位</th>
					<th width="50" align="center">比例</th> -->
					<th width="150" align="center">订单号</th>
					<th width="120" align="center">制单时间</th>
					<th width="100" align="center">制单人</th>
			</thead>
			<%int number = 1;%>
			<c:forEach items="${pageForm.list}" var="a">
				<tr id="${a[0]}">
					<td><input type="radio" name="a" class="JQuerypersonvalue"
						value="${a[0]}" /></td>
					<td><span><%=number%></span></td>
					<td><span>${a[1]}</span></td>
					<td><span>${a[7]}</span></td>
					<td><span id="author">${a[10]}</span></td>
					<td><span id="logoNote">${a[11]}</span></td>
					<%-- <td><span id="logoNote"></span></td>
					<td><span id="logoNote"></span></td>
					<td><span id="logoNote"></span></td> --%>
					<td><span id="logoNote">${a[3]}</span></td>
					<td><span id="logoNote">${a[5]}</span></td>
					<td><span id="logoNote">${a[6]}</span></td>
				</tr>
				<%  number++; %>
			</c:forEach>

		</table> 
              <form style="display: none;" name="addressForm" id="addressForm" method="post" action="<%=basePath%>ea/activity/ea_getcomporder.jspa?weixinCompanyId=${weixinCompanyId}">
		<s:token></s:token>
		<input id="formtest" type="test" name="inforType" style="display:none"/>
		<input type="submit" name="submit" style="display:none"/>
	</form>
           <c:import url="../../../../ea/page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/bdbill/ea_getjbfpmx.jspa?pageForm.pageNumber=${pageForm.pageNumber}&pageNumber=${pageNumber}&hylb=${hylb}&pl=${pl}&sdate=${sdate}&edate=${sdate}&inforType=${inforType}">
				</c:param>
			</c:import>
			<s:token></s:token>
        </div>
        
         
            <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>         
    </body>
</html>