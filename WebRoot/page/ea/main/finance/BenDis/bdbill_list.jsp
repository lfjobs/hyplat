<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ page import="hy.ea.bo.Company"%>
         <%@ page import="hy.ea.bo.CAccount"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; 
        Company c = (Company)session.getAttribute("currentcompany");
        CAccount ca = (CAccount)session.getAttribute("account");
        %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />         
        <title>wfj订单管理</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
     	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
       <script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.min.js"></script>
      <script src="<%=basePath%>js/ea/finance/BenDis/dbbillList.js"></script>
        <script>
             var companyID = '<%=c.getCompanyID()%>';
             var aemail = '<%=ca.getAccountEmail()%>';
             var basePath = "<%=basePath%>";
             var ppageNumber = '${pageNumber}';
             var pageSize = '${pageSize}';
             var clientPositioningID="";
             var weixinCompanyId='${weixinCompanyId}';
             var inforType='${inforType}';
             var fk="${fk}";
             var sdate='${sdate}';
             var edate='${edate}';
             var token=0;
             var pl="${pl}";
             var hylb="${hylb}";
             var id="";
             var status="${status}";
             var iisnull="${iisnull}";
             var type="${type}";
             var str="${state[0]}",str2="${state[1]}",str3="${state[2]}";
             var zzorder ="${zzorder}";
        </script>
        <style >
        </style>
	</head>
	<body >
        <div class="main_main">
        <form name="onkeyfhForm" id="onkeyfhForm" method="post">
        <input type="submit" name="submit" id="submit" style="display: none;"/>
        </form>
            <table class="address">
                <thead>
				<tr class="tablewith">
					<th width="40" align="center">请选择</th>
					<th width="50" align="center">序号</th>
					<th width="180" align="center">公司名称</th>
					<!-- <th width="180" align="center">部门名称</th> -->
					<th width="100" align="center">订单编号</th>
					<th width="100" align="center">下单时间</th>
					<th width="100" align="center">订单负责人</th>
					<th width="100" align="center">用户名称</th>
					<th width="100" align="center">用户会员类别</th>
					<th width="100" align="center">收货联系方式</th>
					<th width="100" align="center">收货地址</th>
					<th width="100" align="center">产品总金额</th>
					<th width="100" align="center">状态</th>
			</thead>
			<%int number = 1;%>
			<c:forEach items="${pageForm.list}" var="a">
				<tr id="${a[0]}">
					<td><input type="radio" name="a" class="JQuerypersonvalue"
						value="${a[0]}" /></td>
					<td><span><%=number%></span></td>
					<td><span>${a[1]}</span></td>
					<%-- <td><span>${a[7]}</span></td> --%>
					<td><span>${a[3]}</span></td>
					<td><span>${a[5]}</span></td>
					<td><span>${a[6]}</span></td>
					<td><span>${a[9]}</span></td>
					<td><span>${a[10]}</span></td>
					<td><span>${a[11]}</span></td>
					<td><span>${a[12]}</span></td>
					<td><span>${a[13]}</span></td>
					<script></script>

					<td><span id="fkStatus">${a[4]=='01'?'未付款':a[4]=='00'?'已付款未发货':a[4]=='02'?'已出库正在物流':a[4]=='03'?'用户已收货':a[4]=='04'?'已分配金币':a[4]=='05'?'申请退货中':a[4]=='06'?'同意退货':a[4]=='07'?'退货中':'确认收货'}</span></td>

					<%-- <td><span id="logoNote">${a[4]=='01'?'未付款':a[4]=='00'?'已付款未发货':a[4]=='02'?'已出库正在物流':a[4]=='03'?'用户已收货':'已分配金币'}&nbsp;&nbsp;&nbsp;<a href="<%=basePath%>/page/ea/main/finance/BenDis/approverefund.jsp">退货</a></span></td>
					 --%>
					<input type="hidden" value="${a[4]}" id="ddstatus"/>
				</tr>
				<%  number++; %>
			</c:forEach>
			
		</table> 
              <form style="display: none;" name="addressForm" id="addressForm" method="post">
		<s:token></s:token>
		<input id="formtest" type="test" name="inforType" style="display:none"/>
		<input type="submit" name="submit" style="display:none"/>
	</form>
           <c:import url="../../../../ea/page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/bdbill/ea_getcomporder.jspa?pageForm.pageNumber=${pageForm.pageNumber}&pageNumber=${pageNumber}&hylb=${hylb}&pl=${pl}&sdate=${sdate}&edate=${edate}&inforType=${inforType}&zzorder=${zzorder}&fk=${fk}">
				</c:param>
			</c:import>
			<s:token></s:token>
        </div>
        <!-- 打印预览-->
       <div class="jqmWindow" style="width: 200px; right: 30%; top: 10%"
				id="jqModelpj">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
				票据打印
					<div class="close">
					</div>
				</div>
				<table width="180" id="SearchTable">
					
					<tr>
						<td align="right">
							打印选择：
						</td>
						<td style="width: 100px">
							<input type="radio" class="pj" name="pj" value="dp"/>大票
							<input type="radio" class="pj" name="pj" value="Rxp"/>小票
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="printvsk"
						value=" 打印预览 "/>
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
			<!-- 确认收货 -->
			<div class="jqmWindow "
		style="display:none; background-color:white; right: 20%; top: 10%;width: 400px;height: 200px;" id="confirm">
            <div  style="height: 40px;background-color:#00CCFF;text-align: center;line-height: 40px;font-size: 20px;">
                                确认收货
                                
                    <div class="close"></div>
            </div>
            <form name="confirmForm" id="confirmForm" method="post" enctype="multipart/form-data">
            <input type="submit" name="submit" style="display: none" />
                 <div style="text-align: center;height: 50px;line-height: 50px;">
                   <p style="font-size: 15px;"><img src="<%=basePath%>js/jqModal/css/images_blue/right.png"  />交易成功，宝贝等您<a href="javascript:void(0)">评价</a>！</p>
                 </div>
				</form>
			
			</div>
             
            <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>         
    </body>
</html>