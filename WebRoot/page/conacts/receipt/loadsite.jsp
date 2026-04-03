<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" /> 
<title>微分金下载</title>
<link href="<%=basePath%>css/contacts/recepit/style13.css" rel="stylesheet"
		type="text/css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		
<style type="text/css">
	*{margin:0; padding:0;}
	a{text-decoration: none;}
	img{max-width: 100%; height: auto;}
	.weizhi{width:200px;height:200px;}
	.weixin-tip{display: none; position: fixed; left:0; top:0; bottom:0; background: rgba(0,0,0,0.8); filter:alpha(opacity=80);  height: 100%; width: 100%; z-index: 100;}
	.weixin-tip p{text-align: center; margin-top: 10%; padding:0 5%;}
	.spans{position: relative;left:30%;color: red;}
</style>
</head>



<body onload="loadwx();">
<div class="weixin-tip">
		<p>
			<img src="<%=basePath%>images/contacts/loadsite/live_weixin.png" alt="微信打开"/>
		</p>
	</div>
	<script type="text/javascript">

        function loadwx(){
	        var winHeight = $(window).height();
			function is_weixin() {
			    var ua = navigator.userAgent.toLowerCase();
			    if (ua.match(/MicroMessenger/i) == "micromessenger") {
			        return true;
			    } else {
			        return false;
			    }
			}
			var isWeixin = is_weixin();
			if(isWeixin){
				$(".weixin-tip").css("height",winHeight);
	            $(".weixin-tip").show();
			}

        }
	</script>
	<div class="wfj01_017">
      <div  class="wfj01_017_out">
    	<div class="wfj01_017_title">
        	<table>
            	<tr>
                	<td width="35%" align="center" rowspan="3"><img src="<%=basePath%>images/contacts/loadsite/wfj_logo_01.png" /></td>
                	<td width="65%"><span>共享资源共享金</span></td>
                </tr>
            	<tr>
                	<td>微分金系统平台</td>
                </tr>
            	<tr>
                	<td>打造数字地球商城</td>
                </tr>
            </table>
        </div>
       
        <div class="wfj01_017_content">
        	<div class="wfj01_017_hidden">
            	<div class="wfj01_017_download">
                	<div class="wfj01_017_width">
                    	<div class="wfj01_017_down">
                            <ul>
                                <li>微分金</li>
                            </ul>
                        </div>
                        <div class="wfj01_017_load" >
                        	<div class="left divs"><a href="<%=basePath%>upload_files/company201009046vxdyzy4wg0000000025/upload_files/ea/Android/wfj.apk"><img src="<%=basePath%>images/contacts/loadsite/wfj_download_01.png" /></a></div>
                            <div class="right"><a href="https://itunes.apple.com/cn/app/shu-zi-de-qiu/id1182214522?l=en&mt=8"><img class="right" src="<%=basePath%>images/contacts/loadsite/wfj_download_02.png" /></a></div>                       	
                        </div>
                       
                        <div class="wfj01_017_industry">
                            <ul>
                                <li>行业客户</li>
                            </ul>
                        </div>
      				 <c:forEach items="${beans}" var="order">
      				  <c:if test='${order[1]!="wfj"}'>
                        <div class="wfj01_017_load">
                      
                        <c:if test="${order[2]=='0'}">
                        	<div class="left">
                        	
                        		<a href="<%=basePath%>${order[3]}">
                        			<img class="left heig" src="<%=basePath%>images/contacts/loadsite/hxsc2.png"  /><span class="spans" >${order[0]}下载</span></a>
                        			</div>
                        	</c:if>
                        	<c:if test="${order[2]=='1'}">
                            <div class="right weizhi">
                        		<a href="${order[3]}">
                        			<img class="right heig" src="<%=basePath%>images/contacts/loadsite/hxscios2.png"  /><span class="spans" >${order[0]}下载</span></a>
                        			</div>
                            		</c:if>
                            		
                        </div>
                       </c:if>
                      </c:forEach>
         
                        <div class="wfj01_017_phone_con">
                        	<ul>
                            	<li><img src="<%=basePath%>images/contacts/loadsite/wfj_phone_content_01.png" /></li>
                            </ul>
                        </div>
                    
                        <div class="wfj01_017_bottom">
                        	<ul>
                            	<li>北京天太世统科技有限公司</li>
                            	<li>地址：东直门外大街宇飞大厦801室</li>
                                <li>电话：010-64167113</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
    </div>
    
    
    
    <script type="text/javascript">
    var basePath="<%=basePath%>";
    	$(document).ready(function(e) {
			
			$("body").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;");

			
			$(".wfj01_017").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;");
			
			$(".wfj01_017_title").attr("style","padding:"+$(window).height()*0.03+"px 0;");
			$(".wfj01_017_title").find("td").attr("style","font-size:"+$(window).height()*0.02+"px; ");
			$(".wfj01_017_title").find("tr").eq(1).find("td").attr("style","padding-left:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.025+"px;");
			$(".wfj01_017_title").find("tr").eq(2).find("td").attr("style","padding-left:"+$(window).height()*0.1+"px;font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.025+"px;");
			$(".wfj01_017_title").find("td").find("span").attr("style","font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.045+"px;line-height:"+$(window).height()*0.045+"px;");
			$(".wfj01_017_down").find("li").attr("style","font-size:"+$(window).height()*0.023+"px;height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px;");
			$(".wfj01_017_down").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
			$(".wfj01_017_industry").attr("style","margin-top:"+$(window).height()*0.09+"px;"+"height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;");
			$(".wfj01_017_industry").find("li").attr("style","font-size:"+$(window).height()*0.023+"px;height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px;");
			
			$(".wfj01_017_bottom").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.035+"px; line-height:"+$(window).height()*0.035+"px;");
			
			
			
			if($(window).width()>$(window).height()){
				$(".wfj01_017").css("width","70%");
			}else{
				$(".wfj01_017").css("width",$(window).width());
			}
			//$(".wfj01_017_content").attr("style","width:"+$(".wfj01_017").width()+"px;height:"+parseInt($(window).height()-$(".wfj01_017_title").height()-$(window).height()*0.06)+"px;overflow:hidden");
			
			$(".wfj01_017_out").attr("style","height:"+parseInt($(window).height()+17)+"px; width:"+parseInt($(window).width()+17)+"px;overflow:auto;");
			$(".wfj01_017_download").css("background","url("+basePath+"images/contacts/loadsite/wfj_bgimg_01.png) repeat");
			
			$(".spans").attr("style","top:-"+$(".left").height()*0.77+"px;font-size:"+$(window).height()*0.025+"px");
			
		});
		
	</script>
</body>
</html> 
