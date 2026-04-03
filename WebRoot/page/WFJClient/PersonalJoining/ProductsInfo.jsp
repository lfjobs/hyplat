<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>公司平台管理系统信息</title>

<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/wfjapp.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>

</head>

<body>
	<div class="wfj11_007">
    	<div class="wfj11_007_top">
        	<ul id="tops">
            	<li><a href="<%=basePath %>page/WFJClient/PersonalJoining/Myjiaru.jsp" target="_self"><</a></li>
            	<li>${goodname }</li>
            	<li><a href="javascript:;"><img src="<%=basePath %>images/WFJClient/PersonalJoining/top_more_02.png" /></a></li>
            
            </ul>
        </div>
        <div class="wfj11_007_top_logo">
        	<img src="<%=basePath %>images/WFJClient/PersonalJoining/5L5Clogo.png" />
        </div>
        <div class="wfj11_007_content">
        	<div class="wfj11_007_hidden">
                <div class="wfj11_007_title">
                    <ul>
                        <li>5层5清管理体系</li>
                    </ul>
                </div>
                <div class="wfj11_007_manage">
                    <div class="wfj11_007_width">
                        <div class="wfj11_007_masystem">
                        	<img src="<%=basePath %>images/WFJClient/PersonalJoining/manage_system_01.png" />
                        </div>
                    </div>
                    <div class="wfj11_007_con">
                    	<div class="wfj11_007_width">
                            <ul>
                                <li class="wfj11_007_con_title">5L5C系统平台介绍</li>
                                <li class="wfj11_007_con_content">5L5C是由北京天太世统科技有限公司研发的，集管理系统、电子商务平台系统、咨询培训系统、赢利模式系统等为一体的大数据平台，即5L5C大数据（中联园区）+5L5C管理系统（人事系统、办公系统、财务系统、生产系统、营销系统等）+网络推广（电子商务平台系统、微分金、微信平台、电子商务资源平台、线上线下博览、5L5C管理咨询培训系统、资源赢利系统模式等），5L5C强大的管理后端，完成电子商务从进货、送货、销售、赢利等的系列流程，打造中国互联网大数据管理电子商务资源平台第一品牌！</li>
                            </ul>
                        </div>
                    </div>
                    <div class="wfj11_007_con">
                    	<div class="wfj11_007_width">
                            <ul>
                                <li class="wfj11_007_con_title">5L5C系统平台优势</li>
                                <li class="wfj11_007_con_content">它的好处优势是，在帮助企业实现管理新标准的基础上，完善平台股权激励机制方案，使各加盟商与有贡献的员工实现股权致富，实现共享资源共享金的梦想，进一步把五层五清企业管理新标准推广到学校企业公司及各贸易机构，完成企业公民的的神圣职责和使命！</li>
                            </ul>
                        </div>
                    </div>
                    <div class="wfj11_007_con borders">
                    	<div class="wfj11_007_width">
                            <ul>
                                <li class="wfj11_007_con_title">支持行业</li>
                                <li class="wfj11_007_con_content">适用于汽车交通工具、软件网络计算机、航空航天科技、食品饮料酒类、娱乐体育休闲、家电五金日用、运输物流仓储、机械工业加工、化工能源环保、媒体广告影视、旅游餐饮酒店、玩具礼品工艺品等行业。</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="wfj11_007_bottom" >
        	<div id="right">确认购买</div>
        </div>
    
    </div>
    
    <script type="text/javascript">
    	var basePath='<%=basePath%>';
    	var lei='${lei}';
    	var indus='${indus}';
    	var sort='1';
    	$(document).ready(function(e) {
            $("body").css("height",$(window).height()) ;
          //修改字体大小
			$("#tops").find("li").eq(0).attr("style","width:15%; text-align:center; font-size:"+$(window).height()*0.03+"px;");
			$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.02+"px;");
			$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
			$("#tops").find("li").eq(2).find("img").attr("style","max-height:"+$(window).height()*0.03+"px; width:auto;");
			$(".wfj11_007_title").find("li").eq(0).attr("style","font-size:"+$(window).height()*0.03+"px;color:#000;");
			
			$(".wfj11_007_con_title").attr("style"," text-align:center; font-size:"+$(window).height()*0.02+"px;color:#000;");
			$(".wfj11_007_con_content").attr("style","  text-indent:2em; line-height:"+$(window).height()*0.03+"px;font-size:"+$(window).height()*0.015+"px;");
			$(".wfj11_007_bottom").find("div").eq(0).attr("style","width:50%; margin-left:80px;text-align:center; background-color:#F74C31; color:#FFF; cursor:pointer;font-size:"+$(window).height()*0.02+"px;");

			//修改字体大小end
			
			
			$(".wfj11_007_top").css("height",$(window).height()*0.05+"px");
			$(".wfj11_007_top").css("lineHeight",$(window).height()*0.05+"px");
			$(".wfj11_007_top_logo").find("img").css("height",$(window).height()*0.15+"px");
			$(".wfj11_007_content").css("width",$(window).width());
			$(".wfj11_007_title").css("height",$(window).height()*0.1+"px");
			$(".wfj11_007_title").css("lineHeight",$(window).height()*0.1+"px");
			$(".wfj11_007_masystem").find("img").css("height",$(window).height()*0.32+"px");
			$(".wfj11_007_con_title").css("height",$(window).height()*0.05+"px");
			$(".wfj11_007_con_title").css("lineHeight",$(window).height()*0.05+"px");
			$(".wfj11_007_con_title").css("marginTop",$(window).height()*0.01+"px");
			$(".borders").css("marginBottom",$(window).height()*0.05+"px");
			
			
			$(".wfj11_007_content").css("height",$(window).height()*0.75+"px");
			$(".wfj11_007_hidden").css("height",$(window).height()*0.75+17+"px");
			$(".wfj11_007_content").css("width",$(window).width()+"px");
			$(".wfj11_007_hidden").css("width",$(window).width()+17+"px");
			
			$(".wfj11_007_bottom").css("height",$(window).height()*0.05+"px");
			$(".wfj11_007_bottom").find("div").css("height",$(window).height()*0.05+"px");
			$(".wfj11_007_bottom").find("div").css("lineHeight",$(window).height()*0.05+"px");
			
			$(".wfj11_007_bottom").find("#right").click(function(){
				if(lei=="1"){
					open(basePath+"ea/wfjshop/ea_getgrxx.jspa?indus="+indus+"&sort="+sort, "_self");
				}else if(lei=="2"){
					open(basePath+"ea/wfjshop/ea_getxx.jspa?indus="+indus+"&sort="+sort, "_self");
				}
			});
			
        });
    </script>
</body>
</html>
















