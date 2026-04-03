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
        <title>买家退货填写</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
     	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/style12(8).css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/jqModal_blue.css" />
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
       <script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.min.js"></script>
       <script type="text/javascript" src="<%=basePath%>js/ea/finance/mobile/addAddress3.js"></script>
        <script>
        var basePath="<%=basePath%>";
        var staid="${staid}";
        var id="${id}";
        var companyId="${companyId}";
        var status="${status}";
        </script>
        <style >
        </style>
	</head>

<body>
	<div class="wfj12_013">
    
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li><a href="javascript:window.history.back(-1);" target="_self"><img src="<%=basePath%>js/jqModal/css/images_blue/wfj_return_01.png" /></a></li>
            	<li>退货信息填写</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>js/jqModal/css/images_blue/top_more.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
        <div class="wfj12_013_con">
        <div class="wfj12_014_topimg"  >
					<div class="wfj12_014_timg">
						<img src="<%=basePath %>images/ea/finance/BenDis/wfj_gwc_01.png" />
					</div>
        <div class="wfj12_014_top_width">
						<ul>
							<input type="hidden" id="id" value="${pf.cashierBillsID}" />
				
							    <c:if test="${status=='06'}">
								<li class="wfj12_014_top_font1"><font>退货详情</font>
								</li>
								<li class="wfj12_014_top_font2"><a href="javascript:;">您的商品同意退货</a>
								</li>
								</c:if>
								<c:if test="${status=='11'}">
								<li class="wfj12_014_top_font1"><font>换货详情</font>
								</li>
								<li class="wfj12_014_top_font2"><a href="javascript:;">您的商品同意换货</a>
								</li>
								</c:if>
								<c:if test="${status=='08'}">
								<li class="wfj12_014_top_font1"><font>退货详情</font>
								</li>
								<li class="wfj12_014_top_font2"><a href="javascript:;">您的商品退货成功</a>
								</li>
								</c:if>
						</ul>
					</div>
		 </div>
		 
        	<div class="wfj12_013_width" >
        	<form id="addForm" name="addForm" method="post" enctype="multipart/form-data">
        	  <input type="submit" name="submit" style="display: none;"/>
            	<table> 
            	      <input type="hidden" name="refundAddress.companyId" value="${companyId}"/>
                	<tr>
                    	<td>快递公司：</td>
                    	
                    	<td><input type="text" name="refundAddress.rpostcode"/></td>
                    </tr>
                	
                	<tr>
                    	<td>快递单号：</td>
                    	<td><input type="text" name="refundAddress.rtel"/></td>
                    </tr>
                	<tr>
                    	<td width="25%">收货人：</td>
                    	<td width="75%"><input type="text" name="refundAddress.rname" value="${refundSheet.receiverName}"/></td>
                    </tr>
                	<tr>
                    	<td>接收人电话：</td>
                    	<td><input name="refundAddress.rarea" value="${refundSheet.receiverTel}"></input></td>
                    </tr>
                	<tr>
                    	<td>收货地址：</td>
                    	<td><input type="text" name="refundSheet.refundAddress" value="${refundSheet.refundAddress}"/></td>
                    </tr>
                
                </table>
              
                <div class="wfj12_013_bottom">
                	<div id="add" style="float: right;">完成</div>
                </div>
                  </form>
            </div>
        </div>
        
        
    </div>
    <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>         
   
</body>
</html>
 