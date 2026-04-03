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
<title>卖家确认退货单</title>


<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/style12(4).css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/jqModal_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/mobile/style12.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>

<script src="<%=basePath%>js/ea/finance/mobile/mobileAgree.js"
	type="text/javascript"></script>
	<script type="text/javascript">
	   var id="${id}";
	   var basePath="<%=basePath%>";
	   var companyId="${refundSheet.companyID}";
	   var rsid = "${refundSheet.rsid}";
	   var cashid="${refundSheet.cashierBillsID}";
	   var status="${refundSheet.refundstate}";
	   var photo="${photo}";
	   var param3="${param3}";
	   var param1="${param1}";
	   var param2="${param2}";
	   var param4="${param4}";
	</script>
</head>

<body>
	<div class="wfj12_012">
    
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li><a href="javascript:window.history.back(-1);" target="_self"><img src="<%=basePath%>js/jqModal/css/images_blue/wfj_return_01.png"  /></a></li>
            	<li>卖家信息</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>js/jqModal/css/images_blue/top_more.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
        <div class="wfj12_012_title">
        	<div class="wfj12_012_width">
            	<ul>
                	<li><img src="<%=basePath%>js/jqModal/css/images_blue/aboutusimg_01.png" /></li>
                	<li>美居雅旗舰店</li>
                </ul>
            </div>
        </div>
        <div class="wfj12_012_product">
        	<div class="wfj12_012_width">
            	<table>
            	   
                	<tr>
                	   
                    	<td width="30%" rowspan="3"><img src="<%=basePath%>${photo}" /></td>
                    	<td colspan="2">产品介绍</td>
                    </tr>
                	<tr>
                    	<td colspan="2">香味：玫瑰</td>
                    </tr>
                	<tr>
                    	<td><span>￥${refundSheet.refundMoney}</span></td>
                    	<td align="right">X1</td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="wfj12_012_address" style="margin: 5px;">
        	<div class="wfj12_012_width">
                <table>
                	<tr>
                	
                    	<td width="10%"></td>
                    	<td width="45%" >退货人：<span id="receiverName" >${refundAddress.rname}</span></td>
                    	<td width="45%"> 电   话：<span id="receiverTel">${refundAddress.rtel}</span></td>
                    </tr>
                	<tr>
                    	<td><img src="<%=basePath%>js/jqModal/css/images_blue/wfj11_address_01.png" /></td>
                    	<td colspan="2">
                        	<div class="addresses">
                            	<ul>
                                	<li class="left" >退货地址：<span id="refundAddress">${refundAddress.rarea}${refundAddress.rstreet}</span></li>
                                    <li class="right"><img src="<%=basePath%>js/jqModal/css/images_blue/wfj_return_03.png" /></li>
                                </ul>
                            </div>
                        </td>
                    </tr>
                	<tr>
                    	<td></td>
                    	<td colspan="2"><font>（收货不便时，可选择免费代收货服务）</font></td>
                    </tr>
                </table>
            </div>
        </div>
        
        <div class="wfj12_012_bottom">
        	<div>确认</div>
        </div>
        
        
        
    </div>
</body>
</html>
















