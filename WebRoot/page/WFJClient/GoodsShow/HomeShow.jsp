<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="hy.ea.bo.Company"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>微分金首页面</title>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="<%=basePath%>/css/style06.css" rel="stylesheet" />
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/WFJClient/home.js" type="text/javascript"></script>
	<script type="text/javascript">
		var basePath='<%=basePath%>';
		var token=0;
		var cpid="";
		var notoken=0;
	</script>
	<script type="text/javascript">
	var curIndex=0; 
	//时间间隔 单位毫秒 
	var timeInterval=3000; 
	var arr=new Array(); 
	arr[0]=basePath+"/images/home/business_nav07.png"; 
	arr[1]=basePath+"/images/home/business_nav07_01.png"; 
	arr[2]=basePath+"/images/home/wfj07_bottom_01.png"; 
	setInterval(changeImg,timeInterval); 
	function changeImg() 
	{ 
	    var obj=document.getElementById("obj"); 
	    if (curIndex==arr.length-1)  
	    { 
	        curIndex=0; 
	    } 
	    else 
	    { 
	        curIndex+=1; 
	    } 
	    obj.src=arr[curIndex]; 
	} 

	</script>
	
  </head>
  <body>
	<div class="wfj07_005 bgcolorf00">
        <div class="wfj07_width">
            <div class="wfj07_005_top">
                <ul>
                    <li class="left"><a href="javascript:void(0);">< 中联园区微分金</a></li>
                    <li class="right"><a href="javascript:void(0);">个人中心</a></li>
                </ul>
            </div>
        </div>
    </div>
    <%--<div class="wfj07_005">
        <div class="wfj07_005_title">
            <div class="wfj07_width">
                <ul>
                    <li style="width:28%;"><a href="javascript:void(0);" name="col" class="colorf00" id="car" onclick="change('car')">汽车驾校园区</a></li>
                    <li><a href="javascript:void(0);" name="col" class="color0091f1" id="shop" onclick="change('shop')">企业商城</a></li>
                    <li><a href="javascript:void(0);" class="colorf00" id="wfj" onclick="change('wfj')">微分金</a></li>
                    <li><a href="javascript:void(0);" name="col" class="color0091f1" id="join" onclick="change('join')">我要加盟</a></li>
                </ul>
            </div>
        </div>
    </div>
    --%><div class="wfj07_005"  style="width:800px;height:255px;" >
        <div class="wfj07_005_logo"  style="width:800px;height:255px;" >
        <img id=obj src ="<%=basePath %>/images/home/business_nav07.png"  style="width:800px;height:255px;" border =0/> 
        </div>
    </div>
    <div class="wfj07_005">
        <div class="wfj07_005_con">
            <div class="wfj07_width">
                <ul>
                    <li style="line-height:20px;"><span id="layout">简介：家里的冠军阿隆索的风景数量大幅紧缩力度减肥手机里的风景阿萨德冠军阿隆索的风景数量大幅紧缩力度减肥手机里的风景阿萨德雷锋精神了的风景塑料袋</span><a id='more' href='javascript:void(0);' style="color: #CB3E34;">展开更多</a></li>
                </ul>
            </div>
        </div>
    </div>
    <!--文字显示/隐藏一部分-->
    <script type="text/javascript">
        var end;
        var oDiv = document.getElementById("layout");
        var oText = oDiv.innerHTML;
        function suolve(str) {
            var sub_length = 70;
            var temp1 = str.replace(/[^\x00-\xff]/g, "**");
            var temp2 = temp1.substring(0, sub_length);
            var x_length = temp2.split("\*").length - 1;
            var hanzi_num = x_length / 2;
            sub_length = sub_length - hanzi_num;
            var res = str.substring(0, sub_length - 5);
            if (sub_length < str.length) {
                end = res + "......";
            } else {
                end = res;
            }
            return end;
        }
        suolve(oText);
        oDiv.innerHTML = end;
        var oBtn = document.getElementById("more");
        oBtn.onclick = function () {
            if (oBtn.innerHTML == "展开更多") {
                oDiv.innerHTML = oText;
                oBtn.innerHTML = "收起";
                alert(img.src);

            } else if (oBtn.innerHTML == "收起") {
                oDiv.innerHTML = end;
                oBtn.innerHTML = "展开更多"
                alert(img.src);
            }
        }

    </script>

    <div class="wfj07_005">
        <div class="wfj07_005_movie">
            <ul>
                <li>
                    <video src="<%=basePath %>/images/home/wfj1.mp4" controls="controls" loop="loop">
						您的浏览器版本低，不支持视频播放。
					</video>
                </li>
            </ul>
        </div>
    </div>
    <div class="wfj07_005">
        <div class="wfj07_005_content">
            <div class="wfj07_width">
                <ul>
                    <li><a href="javascript:void(0);" onclick="sele()"><img src="<%=basePath %>/images/home/business_nav07_01.png" /></a></li>
                    <li><a href="javascript:void(0);"><img src="<%=basePath %>/images/home/business_nav07_01.png" /></a></li>
                    <li><a href="javascript:void(0);"><img src="<%=basePath %>/images/home/business_nav07_01.png" /></a></li>
                    <li><a href="javascript:void(0);"><img src="<%=basePath %>/images/home/business_nav07_01.png" /></a></li>
                    <li><a href="javascript:void(0);"><img src="<%=basePath %>/images/home/business_nav07_01.png" /></a></li>
                    <li><a href="javascript:void(0);"><img src="<%=basePath %>/images/home/business_nav07_01.png" /></a></li>
                    <li><a href="javascript:void(0);"><img src="<%=basePath %>/images/home/business_nav07_01.png" /></a></li>
                    <li><a href="javascript:void(0);"><img src="<%=basePath %>/images/home/business_nav07_01.png" /></a></li>
                    <li><a href="javascript:void(0);"><img src="<%=basePath %>/images/home/business_nav07_01.png" /></a></li>
                    <li><a href="javascript:void(0);"><img src="<%=basePath %>/images/home/business_nav07_01.png" /></a></li>
                    <li><a href="javascript:void(0);"><img src="<%=basePath %>/images/home/business_nav07_01.png" /></a></li>
                    <li><a href="javascript:void(0);"><img src="<%=basePath %>/images/home/business_nav07_01.png" /></a></li>
                    <li><a href="javascript:void(0);"><img src="<%=basePath %>/images/home/business_nav07_01.png" /></a></li>
                </ul>
            </div>
        </div>
    </div>
    <form id="ckForms" name="ckForms" method="post" enctype="multipart/form-data">
    <div class="wfj07_005">
        <div class="wfj07_005_smallnav">
            <ul>
                <li><a href="javascript:void(0);" name="chan" id="huiyuan" onclick="chan('huiyuan')" style="color:#f00;">会员服务</a></li>
                <li style="border-left: 1px solid #808080; border-right: 1px solid #808080;"><a href="javascript:void(0);" name="chan" id="shangcheng" onclick="chan('shangcheng')">园区商城</a></li>
                <li><a href="javascript:void(0);" name="chan" id="wfjshop" onclick="chan('wfjshop')">微分金店</a></li>
            </ul>
        </div>
    </div>
    <div class="wfj07_005">
        <div class="wfj07_005_area">
            <ul>
                <li>地区：</li>
                <li><a href="javascript:void(0);" style="color:#f00;" name="jia" id="mianyang" onclick="colo('mianyang')">绵阳驾校</a></li>
                <li><a href="javascript:void(0);" name="jia" id="ziyang" onclick="colo('ziyang')">资阳驾校</a></li>
                <li><a href="javascript:void(0);" name="jia" id="shengwei" onclick="colo('shengwei')">盛威驾校</a></li>
                <li><a href="javascript:void(0);" name="jia" id="chengdu" onclick="colo('chengdu')">成都驾校</a></li>
            </ul>
        </div>
    </div>
    <div class="wfj07_005">
        <div class="wfj07_005_logo">
            <img src="<%=basePath %>/images/home/bottom_logo_08.jpg" />
        </div>
    </div>
    <div class="wfj07_005">
        <div class="wfj07_005_movie">
            <ul>
                <li>
	                <video src="<%=basePath %>/images/home/wfj.mp4" controls="controls" loop="loop">
						您的浏览器版本低，不支持视频播放。
					</video>
                </li>
            </ul>
        </div>
    </div>
    <div class="wfj07_005">
        <div class="wfj07_005_logo">
            <a href="javascript:void(0);">
                <img src="<%=basePath %>/images/home/dianji_1.jpg" />
            </a>
        </div>
    </div>
    <div id="jiaxiao">
    </div>
    <div id="jiaru" style="display:none;">
	    <div class="wfj07_005">
	        <div class="wfj07_005_join">
	            <div class="wfj07_width">
	            	<a href="javascript:void(0);">
		                <ul>
		                    <li>合伙加入</li>
		                </ul>
		            </a>
	            </div>
	        </div>
	    </div>
	    <div class="wfj07_005">
	        <div class="wfj07_005_join">
	            <div class="wfj07_width">
	            	<a href="javascript:void(0);">
		                <ul>
		                    <li>微分金店主加入</li>
		                </ul>
	                </a>
	            </div>
	        </div>
	    </div>
	    <div class="wfj07_005">
	        <div class="wfj07_005_join">
	            <div class="wfj07_width">
	            	<a href="javascript:void(0);">
		                <ul>
		                    <li>代理商会员加入</li>
		                </ul>
	                </a>
	            </div>
	        </div>
	    </div>
	    <div class="wfj07_005">
	        <div class="wfj07_005_join">
	            <div class="wfj07_width">
	                <a href="javascript:void(0);">
		                <ul>
		                    <li>消费者</li>
		                </ul>
	                </a>
	            </div>
	        </div>
	    </div>
    </div>
    </form>
    <div class="wfj07_005">
        <div class="wfj07_005_bottom">
            <div class="wfj07_width">
                <ul>
                    <li><img src="<%=basePath %>/images/home/wfj07_bottom_01.png" /></li>
                    <li><img src="<%=basePath %>/images/home/wfj07_bottom_02.png" /></li>
                    <li><img src="<%=basePath %>/images/home/wfj07_bottom_03.png" /></li>
                    <li><img src="<%=basePath %>/images/home/wfj07_bottom_04.png" /></li>
                </ul>
            </div>
        </div>
    </div>
</body>
</html>
