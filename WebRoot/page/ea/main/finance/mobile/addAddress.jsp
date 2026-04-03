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
        <title>卖家添加退货地址</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
     	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/style12(6).css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/jqModal_blue.css" />
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
       <script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.min.js"></script>
       <script type="text/javascript" src="<%=basePath%>js/ea/finance/mobile/addAddress.js"></script>
        <script>
        var basePath="<%=basePath%>";
        var id="${id}";
        var companyId="${companyId}";
        var rsid = "${refundSheet.rsid}";
        var key="${key}";
        var photo="${photo}";
        var param2="${param2}";
        var param4="${param4}";
        var param3="${param3}";
        var param1="${param1}";
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
            	<li>卖家添加退货地址</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>js/jqModal/css/images_blue/top_more.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
        <div class="wfj12_013_con" >
        	<div class="wfj12_013_width" >
        	<form id="addForm" name="addForm" method="post" enctype="multipart/form-data">
        	  <input type="submit" name="submit" style="display: none;"/>
        	       <div style="height: 150px;line-height: 150px; ">
        	  
                    	<span width="25%" >所在地区：</span>
                    	<span width="75%" class="aa"><input type="text" name="refundAddress.rarea" value="${param2}" onfocus="if(this.value=='请输入所在地区'){this.value='';}"  onblur="if(this.value==''){this.value='请输入所在地区';}" /></span>
 
                    </div>
                     <hr />
                    <div style="height: 150px;line-height: 150px;">
        	  
                    	<span width="25%" >详细地区：</span>
                    	<span width="75%"  class="aa"><input type="text" name="refundAddress.rarea" value="${param4}" onfocus="if(this.value=='请输入详细地址'){this.value='';}"  onblur="if(this.value==''){this.value='请输入详细地址';}" /></span>
 
                    </div>
                    <hr />
                     <div style="height: 150px;line-height: 150px; ">
        	  
                    	<span width="25%" >收 货 人：</span>
                    	<span width="75%" class="aa"><input type="text" name="refundAddress.rname" value="${param3}" onfocus="if(this.value=='请输入收货人'){this.value='';}"  onblur="if(this.value==''){this.value='请输入收货人';}" /></span>
 
                    </div>
                    <hr />
                     <div style="height: 150px;line-height: 150px;">
        	  
                    	<span width="25%" >联系电话：</span>
                    	<span width="75%" class="aa"><input type="text" name="refundAddress.rarea" value="${param1}" onfocus="if(this.value=='请输入联系电话'){this.value='';}"  onblur="if(this.value==''){this.value='请输入联系电话';}" /></span>
 
                    </div>
                    <hr />
                     <div style="height: 150px;line-height: 150px; ">
        	  
                    	<span width="25%" >设为默认地址：</span>
                    	<span width="75%" class="aa"><input type="checkbox" id="checkbox" name="row_checkbox"  /></span>
 
                    </div>
                    <hr /> 
                    <div style="height: 150px;line-height: 150px; ">
        	  
                    	<span width="25%" >邮政编码：</span>
                    	<span width="75%" class="aa"><input type="text" name="refundAddress.rarea" value="请输入邮政编码" onfocus="if(this.value=='请输入邮政编码'){this.value='';}"  onblur="if(this.value==''){this.value='请输入邮政编码';}" /></span>
 
                    </div>
                    <hr /> 
            	
            </div>
                <div class="wfj12_013_bottom">
                	<div id="add">保存</div>
                </div>
                  </form>
        </div>
        
        
    </div>
    <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>         
   
</body>
</html>
 