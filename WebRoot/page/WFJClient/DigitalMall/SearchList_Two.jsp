<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/digitalmall.css" />
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="<%=basePath %>css/WFJClient/SearchList.css">
<script src="<%=basePath %>js/ea/marketing/wfjeshop/SearchList.js"></script>

<meta name="apple-touch-fullscreen" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="x-rim-auto-match" content="none"/>
<meta name="HandheldFriendly" content="true"/>


<title>地球数字商城搜索结果2</title>
</head>
<script type="text/javascript">
var basePath='<%=basePath%>';
var region=""//地域搜索字串
var proName='${proName}';
var search='${search}';
var flag='${flag}';
var tradecode='${tradecode}';
var industry='${industry}';
</script>
<body onload="loaded()">
<div class="search_con02">  
	<div class="top">
        	<ul>
            	<a href="<%=basePath%>ea/digitalmall/ea_DigitalMall.jspa?"><li class="arrow"><img src="<%=basePath %>images/WFJClient/DigitalMall/num_arrow.png"/></li></a>
            	<li class="sea">
                    <div class="sea_box">
                    <input type="text" placeholder="请输入关键字" id="sea_word" onkeyup="toSearch()" value="${proName ne '天太世统'?proName:'' }"/>
                    <img src="<%=basePath %>images/WFJClient/DigitalMall/search.png">
                	</div>
                </li>
            	<a href="javascript:void(0);"><li><img src="<%=basePath %>images/WFJClient/DigitalMall/num_dot.png"/></li></a>
                <div class="clear"></div>
            </ul>
    </div>
    <div class="nav">
    	<div class="screening">
            <ul>
                <li class="Regional">地域</li>
                <li class="Brand">品牌</li>
                <li class="Sort">类型</li>
            </ul>
        </div>
        <div class="style">
        	<a href="javascript:void(0);" onclick="changeStyle()"><img src="<%=basePath %>images/WFJClient/DigitalMall/style_01.png" /></a>
        </div>
    </div>
    <article class="style_con">
    	<ul class="style2_con">
        </ul>
    </article>
</div>

<!-- grade -->
<div class="grade-eject">
    <ul class="grade-w" id="gradew">
    </ul>
    <ul class="grade-t" id="gradet">
    </ul>
    <ul class="grade-s" id="grades">
    </ul>
</div>
<!-- End grade -->
<!-- Category -->
<div class="Category-eject">
    <ul class="Category-w" id="Categorytw">
    </ul>
    <ul class="Category-t" id="Categoryt">
    </ul>
    <ul class="Category-s" id="Categorys">
    </ul>
</div>
<!-- End Category -->
<!-- Category -->
<div class="Sort-eject Sort-height">
    <ul class="Sort-Sort" id="Sort-Sort">
        <li id="smart" onclick="Sorts(this.id)">综合搜索</li>
        <li id="praise" onclick="Sorts(this.id)">好评优先</li>
        <li id="newest" onclick="Sorts(this.id)">最新发布</li>
        <li id="pop" onclick="Sorts(this.id)">销量优先</li>
        <li id="plow" onclick="Sorts(this.id)">价格最低</li>
        <li id="ptop" onclick="Sorts(this.id)">价格最高</li>
    </ul>
</div>
<!-- End Category -->
<script type="text/javascript">
var height = 0;
var t;
var pagenumber=0;
var pagecount=0;
var basePath='<%=basePath%>';
function toSearch(){
	proName=$("#sea_word").val();
	$(".style2_con").html("");
	if( proName == ""){
		var s="<li style='height:"+($(window).height()-$(".top").height()-$(".nav").height()-$(window).height()*0.025)+"px;width:100%;clear:both;'>";
		s+="<div style='text-align:center;height:100px;padding-top:"+$(window).height()*0.015+"px;'>";
		s+="<img style='width:200px;' src='"+basePath+"images/WFJClient/DigitalMall/noresult.png' alt=''/></div></li>";
		$(".style2_con").append(s);
	}else{
		loaded();
	}
}
function getHeight(){
	t=setTimeout("getHeight()", 200);
	if($(".last").offset().top+$(".last").height()-$(".top").height()*0.5<$(window).height()){	
		if(pagenumber<pagecount){
			loaded();
		}	
	}
}
function loaded(){
	clearTimeout(t);
	pagenumber++;
	var params=new Array();
	params.push("flag=2");
	params.push("&proName="+proName);
	params.push("&search="+search);
	params.push("&region="+region);
	params.push("&tradecode="+tradecode);
	params.push("&industry="+industry);
	var url=basePath+"ea/digitalmall/sajax_ea_ajaxDigitalMall.jspa?"+params.join("");			
	$.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		data:{						
		  "pageForm.pageNumber":pagenumber
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			if(pageForm!=null&&pageForm.recordCount>0){	
			var productlist=pageForm.list;
			pagenumber=pageForm.pageNumber;
			pagecount=pageForm.pageCount;
			var prostr="";			
			if(productlist!=null){
				$(".last").removeClass("last");
					for(var i=0;i<productlist.length;i++){					
						var pro=productlist[i];
						if(i==productlist.length-1){
							prostr+="<li class='goodsDetail last'>";
						}else{
							prostr+="<li class='goodsDetail'>";
						}
						prostr+="<input type='hidden' value='"+pro[1]+"' id='ppid'/>";
						prostr+="<input type='hidden' value='"+pro[5]+"' id='goodsid'/>";
						prostr+="<input type='hidden' value='"+pro[6]+"' id='companyid'/>";
						prostr+="<input type='hidden' value='"+pro[10]+"' id='ccompanyid'/>";
		              	prostr+="<div class='img'><img src='"+basePath+pro[4]+"'/></div>";
		                prostr+="<div class='text'>";
		                prostr+="<h3>"+pro[0]+"</h3>";
		                prostr+="<h4>"+"￥"+pro[2]+"<span>"+pro[8]+"人已购买</span></h4>";                       
		                prostr+="</div></li>";
					}
					if(pagenumber == pagecount){
						prostr+="<li style='height:"+$(window).height()*0.05+"px;'><div style='text-align:center;height:100px;padding-top:"+$(window).height()*0.015+"px;'>无更多产品</div></li>";
					}
					$(".style2_con").append(prostr);
					getHeight();
				}
			}else{
				var s="<li style='height:"+($(window).height()-$(".top").height()-$(".nav").height()-$(window).height()*0.025)+"px;width:100%;clear:both;'>";
				s+="<div style='text-align:center;height:100px;padding-top:"+$(window).height()*0.015+"px;'>";
				s+="<img style='width:200px;' src='"+basePath+"images/WFJClient/DigitalMall/search_da.png' alt=''/></div></li>";
				$(".style2_con").append(s);
				}
			},	
		error: function cbf(data){
			alert("产品加载失败");
		}
	});
}
</script>
</body>
</html>
