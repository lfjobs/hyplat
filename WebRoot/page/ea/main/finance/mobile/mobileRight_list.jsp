<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>卖家退货申请单</title>


<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/style12.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/jqModal_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>

<script src="<%=basePath%>js/ea/finance/mobile/mobileRight.js"
	type="text/javascript"></script>




<script type="text/javascript">

var basePath="<%=basePath%>";
	var search = "${search}";
	var ppageNumber = "${pageNumber}";
	var token = 1;
	var rsid = "${refundSheet.rsid}";
	var refundType="${refundSheet.refundType}";
	var refundstate="${refundSheet.refundstate}"
	var photo="${photo}";
</script>
</head>
<body style="overflow: auto;" id="body">
	<div class="wfj12_011">
    
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li><a href="javascript:window.history.go(-1);" target="_self"><img src="<%=basePath%>js/jqModal/css/images_blue/wfj_return_01.png" /></a></li>
            	<li>退货申请单</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>js/jqModal/css/images_blue/top_more.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
        
        <div class="wfj12_011_content">
        	<div class="wfj12_011_hidden">
            	<div class="wfj12_011_con">
                
                	<div class="wfj12_011_shouinfo">
                    	<div class="wfj12_011_width">
                        	<div class="wfj12_011_title">收货信息</div>
                        	<ul>
                            	<li>收件人：</li>
                            	<li>${cashierBills.ctUserName}</li>
                            </ul>
                        	<ul>
                            	<li>地址：</li>
                            	<li>${staffAddress.addressDetailed}</li>
                            </ul>
                        </div>
                    </div>
                    
                    <div class="wfj12_011_proinfo">
                    	<div class="wfj12_011_width">
                        	<div class="wfj12_011_title">商品信息</div>
                            <table>
                            	<tr>
                                	<td rowspan="3" width="30%"><img width="90%" src="<%=basePath%>${photo}" /></td>
                                	<td colspan="2">商品简介</td>
                                </tr>
                            	<tr>
                                	<td>颜色：白色</td>
                                	<td align="right">￥${refundSheet.refundMoney}</td>
                                </tr>
                            	<tr>
                                	<td>大小：XL</td>
                                	<td align="right">X1</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    
                    <div class="wfj12_011_tuiinfo">
                    	<div class="wfj12_011_width">
                        	<div class="wfj12_011_title">退货信息</div>
                            <table>
                            	<tr>
                                	<td width="50%">退货原因：</td>
                                	<td>${refundSheet.refundCause}</td>
                                </tr>
                            	<tr>
                                	<td>退款金额：</td>
                                	<td>${refundSheet.refundMoney}</td>
                                </tr>
                            	<tr>
                                	<td>退款说明：</td>
                                	<td>${refundSheet.refundRemark}</td>
                                </tr>
                                <tr>
                                	<td colspan="2">匹配图片：</td>
                                </tr>
                            </table>
                            <div class="wfj12_011_tuiimg">
                            	<ul>
                                	<li><img src="<%=basePath%>${photo}" /></li>
                                	<li><img src="<%=basePath%>${photo}" /></li>
                                	<li><img src="<%=basePath%>${photo}" /></li>
                                	
                                </ul>
                            </div>
                        </div>
                    </div>
                    
                    <div class="wfj12_011_bottom">
                    	<ul>
                        	<li><div id="returns">拒绝退货</div></li>
                        	<li><div id="right">同意退货</div></li>
                        </ul>
                    </div>
                    
                    
                </div>
            </div>
        </div>
        
        
        
    </div>
    <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>
