<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>个人产品浏览</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/ea/wechat/wechartMenugrcp.js"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	 <link href="<%=basePath%>css/shengwei/Style.css" rel="stylesheet" />
	 
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">

  
</style>
  </head>
 
  <script type="text/javascript">
	var basePath='<%=basePath%>';
	var token=0;
	var cpid="";
	var notoken=0;
    
</script>
  
  <body>
  
  <div class="PersonalGoods">
    <form method="post" id="cpform" >
        <div class="pro_top">
            <ul>
                <li class="left"><a  href="<%=basePath%>/ea/buyproducts/ea_getOrders.jspa"><img src="<%=basePath%>images/WFJClient/return_fh.png" /></a></li>
                <li class="pro_info">个人产品</li>
                <li class="right adds"><a  href="javascript:void(0);" onclick="addcp()">+&nbsp;添加</a></li>
            </ul>	
        </div>    
        <!-- 个人产品出售物品信息 -->
  
    <c:forEach var="cartItem" items="${beans}" varStatus="idc">
        <div class="goods" id="${cartItem[0]}" onclick="dui(this)" >
            <div class="choice">
           		 <img id="images" name="images" src="<%=basePath%>/images/WFJClient/choice_blank.png" />
              	 <div class="aa" style="display:none;"><input  type="checkbox" id="cpid" class="chebox" name="checkbox"/></div>
            </div>
      
            <div class="product">
                <img src="${cartItem[6]}"/>
            </div>
            <div class="minute">
                <ul>
                    <li class="pro_name">${cartItem[2]}</li>
                    <li class="pro_money">￥${cartItem[5]}</li>
                    <li>总数量:${cartItem[4]}</li>
                </ul>
            </div>
            <div class="details">     			
                         	<c:if test="${cartItem[3]==0}">
                         	  已下架
                         	 </c:if>
                         	 <c:if test="${cartItem[3]==1}">
                         	  出售中
                         	 </c:if>
            </div>    
        </div>
        </c:forEach>
        <div class="pro_bottom">
            <div>
                <ul>
                    <li onclick="del()"><div><a  href="javascript:void(0);" >删除</a></div></li>
                    <li  onclick="xiajia()"><div><a  href="javascript:void(0);">下架</a></div></li>
                    <li onclick="huifu()"><div><a  href="javascript:void(0);" >出售</a></div></li>
                </ul>
            </div>
        </div>
        <div  style="display:none;">
            <input  type="submit" value="提交"/>
            <input type="text" name="chenpID" id="ID" />
              <iframe name="hidden" height="0" width="0"></iframe>
       		<s:token></s:token>           	
        </div>       	
       </form>     
    </div>
  </body>
</html>
