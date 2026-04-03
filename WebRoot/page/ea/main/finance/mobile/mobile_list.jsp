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
        <title>订单管理</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
     	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
       <script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.min.js"></script>
      <script src="<%=basePath%>js/ea/finance/mobile/mobileList.js"></script>
        <script>
        var basePath="<%=basePath%>";
        var id="";
        </script>
        <style >
        </style>
	</head>
	<body >
        <div class="main_main">
            <table class="flexigrid address">
            
        <s:iterator value="list"  var="l"> 
                        <tr  id="${l[0]}">
						<td style="width: 200px;"><span id="Image" style="display:none;">${l[3]}</span>
						<c:if test="${l[3]!=null}"><img src="<%=basePath%>${l[3]}"/></c:if>
						
						</td>
						<td style="width: 200px;color: red;"><span id="goodsname">${l[1]}</span>
						</td>
						<td style="color: orange;width: 80px;"><span id="price">￥${l[5]}.00</span>
						</td>
                    
						<td style="width: 80px;"><span id="Brand">×${l[4]}</span>
						</td>
						<td style="width: 80px;">
						<input type="button" class="look" value="查看详情" />
						</td>
					</tr>
				</s:iterator>
			
		</table> 
        </div>
      


	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html> 