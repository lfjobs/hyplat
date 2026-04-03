<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="<%=basePath%>css/contacts/Network/style12.css" rel="stylesheet"
		type="text/css" />
	<script src="<%=basePath%>js/jquery-1.6.1.min.js" type="text/javascript"></script>
<title>我的二维码</title>
</head>

<body class="bgcolorFFF">
	<div class="wfj12_024">
    
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li><a href="resources_1.jsp" target="_self"><img src="<%=basePath%>images/contacts/Network/wfj_return_01.png" /></a></li>
            	<li>我的二维码</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>images/contacts/Network/top_more.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
        <div class="wfj12_024_content">
        	<div class="wfj12_024_hidden">
            
            	<div class="wfj12_024_con">
                	<div class="wfj12_024_width">
                    	<table width="100%">
                        	<tr>
                            	<td width="40%" rowspan="3"><img class="wfj12_024_icon" src="<%=basePath%>images/contacts/Network/wfj_huiyuan_01.png" /></td>
                            	<td width="60%">姓名</td>
                            </tr>
                        	<tr>
                            	<td>微分金商城业主会员</td>
                            </tr>
                        	<tr>
                            	<td>${param.account }</td>
                            </tr>
                        </table>
                        <div class="wfj12_024_ewm"><img src="<%=basePath%>images/contacts/Network/ewm.jpg" /></div>
                        <div class="wfj12_024_bottom">
                            <ul>
                                <li>扫一扫上面的二维码图案，关注我</li>
                                <li><a href="javascript:;">保存二维码到相册</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
    </div>
    
   
    <script type="text/javascript">
    	$(document).ready(function(e) {
			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
			
			
			if($(window).width()>$(window).height()){
            	$(".wfj12_024").attr("style","width:"+$(window).width()*0.7+"px;");
            	$(".wfj12_024_content").attr("style","width:"+$(".wfj12_024").width()+"px;height:"+parseInt($(window).height()-$(".wfj_top").height())+"px;overflow:hidden;");
				$(".wfj12_024_hidden").attr("style","width:"+parseInt($(".wfj12_024_content").width()+17)+"px;height:"+$(".wfj12_024_content").height()+"px;overflow:auto;");
			}
			
            $(".wfj12_024_con").attr("style"," padding-top:"+$(window).height()*0.03+"px;");
            $(".wfj12_024_con").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_024_con").find("a").attr("style","font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_024_bottom").attr("style","  margin-top:"+$(window).height()*0.02+"px;");
            $(".wfj12_024_bottom").find("li").attr("style"," font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.04+"px;line-height:"+$(window).height()*0.04+"px;");
			
			
        });
    </script>
</body>
</html>

