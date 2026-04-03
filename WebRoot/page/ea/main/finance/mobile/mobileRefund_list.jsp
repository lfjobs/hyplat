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
        <meta name="viewport"
	     content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />       
        <title>退货单管理</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
	<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/style12(3).css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/jqModal_blue.css" />
     	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
       <script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.min.js"></script>
      <script src="<%=basePath%>js/ea/finance/mobile/mobileRefund.js"></script>
        <script>
        var basePath="<%=basePath%>";
        var id="";
        var tt="00";
        var role = "${role}";
        var user="${user}";
    	$(document).ready(function(){
    		var u = navigator.userAgent;
    	    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
           	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    		if(isAndroid==true){
            	var obj = document.getElementById("ret");
           		obj.setAttribute("href","#");
           		obj.setAttribute("onclick", "retAndroid()");    
           	}else if(isiOS==true){   	          		
           		var obj = document.getElementById("ret");
           		obj.setAttribute("href","#");
           		obj.setAttribute("onclick", "retIOS()");
           	}
           });
        
           	//安卓，苹果返回
    		function retAndroid(){
    			try{       		
    				Android.callAndroidjianli();
    			}catch(err){
    				$("#ret").removeAttr("onclick");
    				$("#ret").attr("href",basePath+"ea/vipcenter/ea_orderManage.jspa");
    			}
    		}
    		function retIOS(){
    			try{
    				calRefund('');
    			}
    			catch(err){    		
    				$("#ret").removeAttr("onclick");
    				$("#ret").attr("href",basePath+"ea/vipcenter/ea_orderManage.jspa");
    			}    		
    		}
        </script>
        <style >
        </style>
	</head>
	<body>
	<div class="wfj12_009">
    
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li><a id="ret" href="<%=basePath%>ea/vipcenter/ea_orderManage.jspa" target="_self"><img src="<%=basePath%>js/jqModal/css/images_blue/wfj_return_01.png" /></a></li>
            	<li>退货单</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>js/jqModal/css/images_blue/top_more.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-
        
        <!--中联园区搜索 end-->
        <div class="wfj_search">
        	<div class="wfj_width">
            	<ul>
                	<li><input type="text" id="params" name="params" value="退货单号、物品名称、日期" onfocus="if(this.value=='退货单号、物品名称、日期'){this.value='';}"  onblur="if(this.value==''){this.value='退货单号、物品名称、日期';}" /></li>
                    <li><img src="<%=basePath%>js/jqModal/css/images_blue/search999.png" id="img"/></li>
                </ul>
            </div>
        </div>
        <!--中联园区搜索 end-->
        <div class="wfj12_009_content">
        	<div class="wfj12_009_hidden">
            	<div class="wfj12_009_con">
                	<div class="wfj12_009_width">
                    	<s:iterator value="list"  var="l"> 
                    	<table>
                             <tr>
                            	<td width="20%" rowspan="2"><img width="100%" src="<%=basePath%>${l[4]}"/></td>
                            	<td width="30%" align="center"><span id="goodsname">${l[1]}</span></td>
                            	<td width="25%" align="center" >￥<span class="price">${l[2]}</span></td>
                            	<td width="25%"><span id="Brand">X${l[7]}</span></td>
                            </tr>
                            
                        	<tr id="${l[0]}">
                        	    <input type="hidden"  value="${l[4]}" id="phono"/>
                        	<input type="hidden"  value="${l[5]}" id="status"/>
                        	<input type="hidden"  value="${l[6]}" id="risd"/>
                        	
                            	<td align="center">${l[3]}</td>

                            	<!-- <a class="wfj12_009_div refundss  " >
                            	 
                            	 </a> -->

                            	 <td><c:if test="${role==seller}"><a class="wfj12_009_div refundss  " >退款</a></c:if>

                            	 </td> 
                            	<td><a class="wfj12_009_div refundss " >
                            	
                            	<span>
                            	<c:if test="${role=='buyer'}">
                            	
                            	   <c:if test="${l[5]=='03'}">确认退货</c:if>
                            	   <c:if test="${l[5]=='08'}">确认售后</c:if>
                            	   <c:if test="${l[5]=='04'}">退货成功</c:if>
                            	   <c:if test="${l[5]=='09'}">售后成功</c:if>
                            	   <c:if test="${l[5]=='00'||l[5]=='06'||l[5]=='01'}">查看详情</c:if>
                            	 </c:if>
                            	 <c:if test="${role=='buy'}">
                            	   <c:if test="${l[5]=='00'}">提交申请</c:if>
                            	   <c:if test="${l[5]=='01'}">同意退款</c:if>
                            	   <c:if test="${l[5]=='01'}">拒绝退款</c:if>
                            	   <c:if test="${l[5]=='03'}">退货中</c:if>
                            	   <c:if test="${l[5]=='04'}">退货成功</c:if>
                            	    <c:if test="${l[5]=='06'}">售后申请</c:if>
                            	    <c:if test="${l[5]=='07'}">同意售后</c:if>
                            	   <c:if test="${l[5]=='08'}">售后中</c:if>
                            	   
                            	 </c:if>
                            	 </span>
                            	 
                            	 </a></td>
                            </tr>
                        </table>
                            </s:iterator>
                    	
                    	
                    </div>
                </div>
            </div>
        </div>
        
    </div>
      
    <form id="forms" name="forms" method="post" enctype="multipart/form-data">
        	  <input type="submit" name="submit" style="display: none;"/>
        	
                  </form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html> 