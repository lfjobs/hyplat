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

<script type="text/javascript" src="<%=basePath%>js/jquery-1.6.1.min.js"></script>
	<link href="<%=basePath%>css/contacts/Network/jqModal_blue.css" rel="stylesheet"
		type="text/css" />
		<link href="<%=basePath%>css/contacts/Network/style12.css" rel="stylesheet"
		type="text/css" />
	<script src="<%=basePath%>js/jquery-1.6.1.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/jqModal/jqModal.js" type="text/javascript"></script>

<title>添加粉丝好友名片</title>
</head>

<body>
	<div class="wfj12_021">
    
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li><a href="<%=basePath%>/ea/resourse/ea_findconWealth.jspa" target="_self"><img src="<%=basePath%>images/contacts/Network/wfj_return_01.png" /></a></li>
            	<li>添加粉丝好友名片</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>images/contacts/Network/top_more.png"  /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
        <div class="wfj12_021_content">
        	<div class="wfj12_021_hidden">
            	<div class="wfj12_021_con">
            		<div class="wfj12_021_width">
                        <table>
                            <tr class="wfj12_021_click01">
                                <td width="10%"><img src="<%=basePath%>images/contacts/Network/wfj_add_friend_01.png" /></td>
                                <td width="80%">分享人脉</td>
                                <td width="10%" align="right"><img src="<%=basePath%>images/contacts/Network/wfj_return_06.png" /></td>
                            </tr>
                            <tr class="wfj12_021_click02">
                                <td><img src="<%=basePath%>images/contacts/Network/wfj_add_friend_02.png" /></td>
                                <td>二维码</td>
                                <td align="right"><img src="<%=basePath%>images/contacts/Network/wfj_return_06.png" /></td>
                            </tr>
                            <tr>
                                <td><img src="<%=basePath%>images/contacts/Network/wfj_add_friend_03.png" /></td>
                                <td>手机通讯录</td>
                                <td align="right"><img src="<%=basePath%>images/contacts/Network/wfj_return_06.png" /></td>
                            </tr>
<%--                             <tr class="wfj12_021_click04">
                                <td><img src="<%=basePath%>images/contacts/Network/wfj_add_friend_04.png" /></td>
                                <td>添加人脉</td>
                                <td align="right"><img src="<%=basePath%>images/contacts/Network/wfj_return_06.png" /></td>
                            </tr> --%>
                            <tr>
                                <td><img src="<%=basePath%>images/contacts/Network/wfj_add_friend_05.png" /></td>
                                <td><a href="<%=basePath%>/ea/resourse/ea_findVipNum.jspa">关注粉丝人脉</a></td>
                                <td align="right"><img src="<%=basePath%>images/contacts/Network/wfj_return_06.png" /></td>
                            </tr>
                        </table>
                	</div>	
                </div>
            </div>
        </div>
        
        <div class="wfj12_021_bottom" style="display:none;">
        	<div class="wfj12_021_bottom_title">
            	<ul>
                	<li>分享人脉</li>
                </ul>
            </div>
            <div class="wfj12_021_bottom_content">
            	<ul>
                	<li><img src="<%=basePath%>images/contacts/Network/wfj_fans_001.png" /></li>
                	<li>QQ好友</li>
                </ul>
            	<ul>
                	<li><img src="<%=basePath%>images/contacts/Network/wfj_fans_002.png" /></li>
                	<li>QQ空间</li>
                </ul>
            	<ul>
                	<li><img src="<%=basePath%>images/contacts/Network/wfj_fans_003.png" /></li>
                	<li>朋友网</li>
                </ul>
            	<ul>
                	<li><img src="<%=basePath%>images/contacts/Network/wfj_fans_004.png" /></li>
                	<li>腾讯微博</li>
                </ul>
            	<ul>
                	<li><img src="<%=basePath%>images/contacts/Network/wfj_fans_005.png" /></li>
                	<li>微信好友</li>
                </ul>
            	<ul>
                	<li><img src="<%=basePath%>images/contacts/Network/wfj_fans_006.png" /></li>
                	<li>朋友圈</li>
                </ul>
            	<ul>
                	<li><img src="<%=basePath%>images/contacts/Network/wfj_fans_007.png" /></li>
                	<li>新浪微博</li>
                </ul>
            	<ul>
                	<li><img src="<%=basePath%>images/contacts/Network/wfj_fans_008.png" /></li>
                	<li>人人网</li>
                </ul>
            	<ul>
                	<li><img src="<%=basePath%>images/contacts/Network/wfj_fans_009.png" /></li>
                	<li>易信好友</li>
                </ul>
            	<ul>
                	<li><img src="<%=basePath%>images/contacts/Network/wfj_fans_010.png" /></li>
                	<li>百度空间</li>
                </ul>
            	<ul>
                	<li><img src="<%=basePath%>images/contacts/Network/wfj_fans_011.png" /></li>
                	<li>豆瓣网</li>
                </ul>
            	<ul>
                	<li><img src="<%=basePath%>images/contacts/Network/wfj_fans_012.png" /></li>
                	<li>短信</li>
                </ul>
            </div>
        </div>
        <input type="hidden" value="${param.custid}" id="custid"/>
        <input type="hidden" value="${param.account}" id="account"/>
        
        
        
    </div>
    <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
   
    <script type="text/javascript">
     var basePath='<%=basePath%>';
    	$(document).ready(function(e) {
			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
			
			if($(window).width()>$(window).height()){
				$(".wfj12_021").attr("style","width:"+$(window).width()*0.7+"px;height:"+$(window).height()+"px;");
				$(".wfj12_021_bottom").attr("style","width:"+$(window).width()*0.7+"px;");
			}else{
				$(".wfj12_021").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;");
				$(".wfj12_021_bottom").attr("style","width:"+$(window).width()+"px;");
			}
			
			$(".wfj12_021_con").find("td").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;font-size:"+$(window).height()*0.02+"px; border-bottom:"+$(window).height()*0.002+"px solid #F0F0F0;");
			$(".wfj12_021_con").find("td").find("img").parent().attr("style"," text-align:center; border-bottom:"+$(window).height()*0.002+"px solid #F0F0F0;");
			$(".wfj12_021_con").find("td").find("img").attr("style","height:"+$(window).height()*0.04+"px;width:auto;");
			
			$(".wfj12_021_bottom_title").attr("style"," height:"+$(window).height()*0.08+"px; line-height:"+$(window).height()*0.08+"px; border-bottom:"+$(window).height()*0.002+"px solid #CACACA;");
			$(".wfj12_021_bottom_title").find("li").attr("style","font-size:"+$(window).height()*0.025+"px;");
			$(".wfj12_021_bottom_content").attr("style"," padding-top:"+$(window).height()*0.03+"px;");
			$(".wfj12_021_bottom_content").find("ul").attr("style"," padding-bottom:"+$(window).height()*0.02+"px;");
			$(".wfj12_021_bottom_content").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;line-height:"+$(window).height()*0.04+"px;");
			var custid=$("#custid").attr("value");
			var account=$("#account").attr("value");
			
			$(".wfj12_021_click04").click(function(){
				open(basePath+"ea/met/mea_juem.jspa?pid="+custid,"_self");
			});
			
			$(".wfj12_021_click02").click(function(){
				open("resources_4.jsp?account="+account,"_self");
			});
			
			
			
			$(".wfj12_021_click01").click(function(){
				//$(".点击的按钮").css("display","none");
				
				$("#occlusion2").css("z-index",$(".wfj11_015").css("z-index")+1);
				$("#occlusion2").jqmShow();
				$(".wfj12_021_bottom").css("z-index",$("#occlusion2").css("z-index")+1);
				$(".wfj12_021_bottom").fadeIn(1000);
			});
			$(".jqmOverlay").live("click",function(){
				$(".wfj12_021_bottom").fadeOut();
				$("#occlusion2").jqmHide();
				//$(".点击的按钮").css("display","");
			});
			
			//弹出层初始化
			$(".jqmWindow").jqm({
				modal : true, 
				overlay : 20
			}).jqmAddClose('.close');
			
			
			
			
        });
    </script>
</body>
</html>
