<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>微分金店</title>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	 <link href="<%=basePath %>/css/WFJClient/NewStyle.css" rel="stylesheet" />
	 <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
  </head>
  
  <body>
    <div class="con">
        <div class="so fl" style="clear: both; margin: 10px auto 10px auto;">
            <div class="search">
            	
            	<form action="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa" id="submit">
            	<input type="text" value="" name="corganization.organizationName" placeholder="输入编号 店名或地址"/>
            	<input type="hidden" name="search" value="searchShops"/>
            	<input type="hidden" name="activity" value="activity"/>
            	<input type="hidden" name="companyId" value="<%=request.getParameter("companyId")%>"/>
	        </form>
            <img alt="" id="search" title="" src="<%=basePath %>images/WFJClient//sos.png"/> 
            </div>
        </div>       
        <div class="GoodsSource">
            <div>
                <a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa?companyId=<%=request.getParameter("companyId")%>&sort=bhjx&activity=activity">
                <ul>
                    <li class="names">编号</li>
                    <li class="lfloat">
                        <img src="<%=basePath %>/images/WFJClient/arrows_bottom.png" />
                    </li>
                </ul>
                </a>
            </div>
            <div>
            	<a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa?companyId=<%=request.getParameter("companyId")%>&sort=dmjx&activity=activity">
                <ul>
                    <li class="names">店名</li>
                    <li class="lfloat">
                        <img src="<%=basePath %>/images/WFJClient/arrows_top.png" /></li>
                </ul>
                </a>
            </div>
        </div>
        <div class="JQueryflexme">
        <s:iterator value="beans" id="arr">
            <table class="tbl">
                <tr id="${arr[0] }" value="${arr[1] }">
                    <td class="tdi">
                    	<img class="img" alt="" title="" src="<%=basePath %>${arr[4]==null ? '/images/WFJClient/zwtp160.png' : arr[4]}" />
                    </td>
                    <td class="dian">
                        <ul>
                            <li class="fon" id="name">${arr[1] }</li>
                            <li><a href="javascript:;">${arr[8] }</a></li>
                            <li style="display: none;" id="id">${arr[5] }</li>
                        </ul>
                    </td>
                    <td class="rfloat">
                        <ul>
                            <li class="names">编号
                            </li>
                            <li class="num">${arr[2] }
                            </li>
                        </ul>
                        <ul class="ptop">
                            <li class="lfloat">
                            	<input type="radio" name="check" value="${arr[0] }"/>
                            </li>
                            <li class="choice">选择
                            </li>
                        </ul>
                    </td>
                </tr>
            </table>
           </s:iterator>
        </div>
    </div>
</body>
<script type="text/javascript">
 	var personvalue = "";
 	$(".JQueryflexme tr[id]").click(function() {
		personvalue = this.id;
		var name=this.parent().getElementById("name").value();
		alert(name);
	});
	$("#search").click (function(){
			$("#submit").submit();
	  	  	});
</script>
</html>
