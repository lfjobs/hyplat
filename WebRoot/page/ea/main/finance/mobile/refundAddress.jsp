<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/style11.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/jqModal_blue(2).css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />

<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>

<script src="<%=basePath%>js/ea/finance/mobile/mobileAddress.js"
	type="text/javascript"></script>
<title>退货地址管理</title>
<script type="text/javascript">
  var basePath="<%=basePath%>";
  var companyId="${companyId}";
  var id="${id}";
  var photo="${photo}";
  var rsid = "${refundSheet.rsid}";
  
</script>
</head>

<body >
	<div class="wfj11_016" style="overflow: auto;">
    	<div class="wfj11_016_top">
        	<ul id="tops">
            	<li><a href="javascript:window.history.back(-1)" target="_self"><img src="<%=basePath%>js/jqModal/css/images_blue/wfj_return_02.png" /></a></li>
            	<li>退货地址管理</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>js/jqModal/css/images_blue/top_more_02.png" /></a></li>
            </ul>
        </div>
        <div class="wfj11_016_content">
        	<div class="wfj11_016_hidden">
                
                    <div class="wfj11_016_consignee tables" id="tables">
                             <div style="height: 50px;font-size: 40px;color: red;">[默认地址]</div> 
                        <div class="wfj11_016_width">
                            <table>
                                <tr>
                                    <td width="10%"></td>
                                    <td width="40%">退货人：<span id="rname">${RefundAddress.rname}</span></td>
                                    <td width="40%">电话：<span id="rtel">${RefundAddress.rtel}</span></td>
                                    <td width="10%"></td>
                                </tr>
                                <tr>
                                    <td><img style="width:60%;" src="<%=basePath%>js/jqModal/css/images_blue/wfj11_address_01.png" /></td>

                                    <td colspan="2">退货地址：<span id="area">${RefundAddress.rarea}</span><span id="rstreet">${RefundAddress.rstreet}</span></td>


                                    <td align="right"><img class="changeimg" style="width:70%;" src="<%=basePath%>js/jqModal/css/images_blue/choice_01.png"/></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                
                    <div class="wfj11_016_consignee changes">
                        <div class="wfj11_016_width">
                            
                       
                               <s:iterator value="list" var="l">
                                 <div class="tables">
                                 <table id="${l.raddressKey}">
                                <tr>
                                    <td width="10%"></td>
                                    <td width="40%">退货人：<span id="rname">${l.rname}</span></td>
                                    <td width="40%">电   话：<span id="rtel">${l.rtel}</span></td>
                                    <td width="10%"></td>
                                </tr>
                                <tr>
                                    <td><img style="width:60%;" src="<%=basePath%>js/jqModal/css/images_blue/wfj11_address_01.png" /></td>

                                    <td colspan="2">收货地址：<span id="area">${l.rarea}</span><span id="rstreet">${l.rstreet}</span></td>
                                    <td align="right"><img class="changeimg" style="width:70%;" src="<%=basePath%>js/jqModal/css/images_blue/choice_02.png"/></td>
                                </tr>
                                </table>
                                </div>
                                </s:iterator>
                                
                           
                        </div>
                    </div>
              
                
        	</div>
        </div>
        
        <div class="wfj11_016_bottom_address">
        	<div >添加退货地址</div>
        </div>
    

</body>
</html>

















