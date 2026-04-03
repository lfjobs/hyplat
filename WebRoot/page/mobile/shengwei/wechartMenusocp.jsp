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
    <title>可代理的产品</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	
	-->
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
 
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	
	<link href="<%=basePath%>css/WFJClient/Style1.css" rel="stylesheet" />
	<script src="<%=basePath%>js/ea/wechat/wechartMenusocp.js"></script>
	

  </head>
  <script type="text/javascript">
     var basePath ="<%=basePath%>";
     var yeshu=${pagenull};
     var cpid="";
  </script>
  
  <body>

    <div class="AgentProducts">
	    	 <div class="pro_top">
	            <ul>
	                <li class="left"><a href="<%=basePath%>/page/WFJClient/GoodsShow/myWfjNav.jsp"><img src="<%=basePath%>images/WFJClient/return_fh.png" /></a></li>
	                <li class="pro_info">可代理的产品</li>
	                
	            </ul>
	        </div> 
	          <form method="post" id="cpform" >
	          
	           <c:forEach var="s" items="${beans}" varStatus="idc">
	       	 <div class="agent_block"  id="${s[0]}">
	            <div class="all_width">
	                <div class="agent_title">
	                    <ul>
	                        <li>所属公司：</li>
	                        <li style="padding-left:10px;">${s[2]}</li>
	                      
	                    </ul>
	                </div>
	                <div class="agent_pro">
	                    <ul>
	                        <li><input type="checkbox" class="chebox" /></li>
	                        <li class="agent_img"><img src="${s[6]}" /></li>
	                        <li style="width:40%;">
	                            <div>
	                                <ul>
	                                    <li>${s[1]}</li>
	                                    <li class="colorf00">￥${s[3]}</li>
	                                    <li>总数量：${s[4]}</li>
	                                </ul>
	                            </div>
	                        </li>
	                        <li class="agent_yong colorf00">佣金:${s[5]}</li>
	                    </ul>
	                </div>
	            </div>
	        </div>
	        
        </c:forEach>
     		 <div id="app">
       	 	</div>
         <div style="height: 40px;width: 100%;border:  1px solid;ackground-color:#DEDEDE ;text-align:center; cursor:pointer;" class="goods" onclick="jiazai()">
                        <img alt="显示更多" src="<%=basePath%>images/WFJClient/jiazai.gif" width="30px;" height="30px;">
                      	   更多加载
        </div>
       
     
        <div class="pro_bottom">
            <div >
                <ul>
                    <li style ="margin-left: 5px;" onclick="dl()"><div>确认代理</div></li>
                   
                </ul>
            </div>
        </div>
       <div style="display: none;">
          <iframe name="hidden" height="0" width="0"></iframe>
         	 <input type="text" id="chenpID"name="chenpID"/>
       		<s:token></s:token></div>
       	</form> 
      
    </div>
  </body>
</html>
