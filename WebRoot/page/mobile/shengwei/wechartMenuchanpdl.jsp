<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
      <title>代理产品</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
 <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<meta http-equiv="description" content="This is my page">
	<link href="<%=basePath%>css/WFJClient/Style1.css" rel="stylesheet" />
	<script src="<%=basePath%>js/ea/wechat/wechatMenuchanpdl.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
 <script type="text/javascript">
 var basePath="<%=basePath%>";
 var cpid;
 
 </script>

<body>

    <div class="AgentProducts">
        <div class="pro_top">
            <ul>
                <li class="left"><a href="<%=basePath%>/ea/buyproducts/ea_getOrders.jspa"><img src="<%=basePath%>images/WFJClient/return_fh.png" /></a></li>
                <li class="pro_info">代理产品</li>
                <li class="right adds"><a href="<%=basePath%>/ea/wfjcustomer/ea_getDLgetlist.jspa?pagenull=1">+&nbsp;代理添加</a></li>
            </ul>
        </div>   
            <form method="post" id="cpform">
            
 
         <c:forEach var="s" items="${beans}" varStatus="idc">
	        <div class="agent_block"  id="${s[0]}">
	            <div class="all_width">
	                <div class="agent_title">
	                    <ul> 
	                    
	                        <li style="padding-left:10px;">${s[5]}</li>
	                      
	                    </ul>
	                </div>
	                <div class="agent_pro">
	                    <ul>
	                        <li><input type="checkbox" class="chebox"/></li>
	                        <li class="agent_img"><img src="${s[6]}"  width="60px" height="80px;"/></li>
	                        <li style="width:40%;">
	                            <div>
	                                <ul>
	                                    <li>${s[2]}</li>
	                                    <li class="colorf00">￥${s[3]}</li>
	                                    <li>总数量：${s[7]}</li>
	                                </ul>
	                            </div>
	                        </li>
	                        <li class="agent_yong colorf00">代理费用:${s[4]}</li>
	                    </ul>
	                </div>
	            </div>
	        </div>
        </c:forEach>
      

        <div style="display: none;">
       		 <input  type="submit" value="提交"/>
           	 <input type="text" name="chenpID" id="chenpID" />
              <iframe name="hidden" height="0" width="0"></iframe>
       		<s:token></s:token>           	
        
        </div>
                 </form>
       
        <div class="pro_bottom">
            <div >
                <ul>
                    <li style ="margin-bottom:20px;" onclick="quxiao()"><div>取消代理</div></li>                  
                </ul>
            </div>
        </div>
    </div>
</body>
</html>
