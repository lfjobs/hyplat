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
        <title>卖家信息填写</title>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/style12(7).css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/jqModal_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />

<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>


       <script type="text/javascript" src="<%=basePath%>js/ea/finance/mobile/addAddress1.js"></script>
        <script>
        var basePath="<%=basePath%>";
        var id="${id}";
        var companyId="${companyId}";
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
            	<li>卖家信息填写</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>js/jqModal/css/images_blue/top_more.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
        <div class="wfj12_013_con">
        	<div class="wfj12_013_width">
        	<form id="addForm" name="addForm" method="post" enctype="multipart/form-data">
        	    <input type="submit" name="submit" id="submit" style="display: none;"/>
            	<table id="tab"> 
            	      <input type="hidden" name="refundAddress.companyId" value="${companyId}"/>
                	<tr>
                    	<td width="25%">联系人：</td>
                    	<td width="75%"><input type="text" name="refundAddress.rname" /></td>
                    </tr>
                	<tr>
                    	<td>所在地区：</td>
                    	<td><input name="refundAddress.rarea"></input></td>
                    </tr>
                	<tr>
                    	<td>街道地址：</td>
                    	<td><input type="text" name="refundAddress.rstreet" /></td>
                    </tr>
                	<tr>
                    	<td>邮政编码：</td>
                    	
                    	<td><input type="text" name="refundAddress.rpostcode"/></td>
                    </tr>
                	<tr>
                    	<td>电话号码：</td>
                    	<td>
                            <table>
                                <tr>
                                     <input name="refundAddress.rphone" type="hidden" id="rPhone" value=""/>
                                    <td><input type="text" id="area" style="width: 20px;"/>-</td>
                                   
                                  <td><input type="text" id="area1" style="width: 20px;"/></td>
                                
                                    
                                </tr>
                            </table>
                        </td>
                    </tr>
                	<tr>
                    	<td>手机号码：</td>
                    	<td><input type="text" name="refundAddress.rtel"/></td>
                    </tr>
                	<tr>
                    	<td>公司名称：</td>
                    	<td><input type="text" name="companyName"/></td>
                    </tr>
                	<tr>
                    	<td>备注：</td>
                    	<td><textarea name="refundAddress.rremark"></textarea></td>
                    </tr>
                </table>
              
                <div class="wfj12_013_bottom">
                	<div id="adds">完成</div>
                </div>
                  </form>
            </div>
        </div>
        
        
    </div>
    <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>         
   
   
</body>
</html>
 