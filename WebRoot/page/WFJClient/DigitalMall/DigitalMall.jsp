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
<script type="text/javascript" src="<%=basePath %>js/koala.min.1.5.js"></script>
<%-- <script src="<%=basePath %>js/ea/marketing/wfjeshop/SearchList.js"></script> --%>
<title>数字地球商城</title>
</head>
<script type="text/javascript">
var basePath='<%=basePath%>';
var pagenumber=0;
var pagecount=0;
</script>
<body>
<div class="numShop">
	<div class="top">
        <ul>
            <li><a href="<%=basePath %>ea/wfjshop/ea_getWFJshops.jspa?"><img src="<%=basePath %>images/WFJClient/DigitalMall/arrow.png" /></a></li>
            <li>数字地球商城</li>
            <li><a href="<%=basePath %>ea/digitalmall/ea_DigitalMall.jspa?flag=1&proName=天太世统"><img src="<%=basePath %>images/WFJClient/DigitalMall/magnifier.png" /></a></li>
        </ul>
    </div>
    <article class="article">
    	<div class="content">
    	<!-- 代码 开始 -->
        <div id="fsD1" class="focus">  
            <div id="D1pic1" class="fPic">  
            	<c:forEach items="${adList }" var="entity" >
            	<c:if test="${entity[0] eq '地球数字商城上部'}">
                <div class="fcon" style="display: none;">
                	<input type="hidden" value="${entity[1] }" id="ppid"/>
                	<input type="hidden" value="${entity[2] }" id="goodsid"/>
                	<input type="hidden" value="${entity[5] }" id="companyid"/>
                    <a  href="javascript:void(0)"><img src="<%=basePath %>${entity[3]}" style="opacity: 1; "></a>            
                </div></c:if>
                </c:forEach>
            </div>
            <div class="fbg">  
            <div class="D1fBt" id="D1fBt">  
                <a href="javascript:void(0)" hidefocus="true" target="_self" class=""><i>1</i></a>  
                <a href="javascript:void(0)" hidefocus="true" target="_self" class=""><i>2</i></a>  
                <a href="javascript:void(0)" hidefocus="true" target="_self" class="current"><i>3</i></a>  
                <a href="javascript:void(0)" hidefocus="true" target="_self" class=""><i>4</i></a>  
            </div>  
            </div>  
            <span class="prev"></span>   
            <span class="next"></span>    
        </div>
        <!--代码结束-->
        <ul class="header">
        	<li onclick="industrySearch('汽车驾校')"><img class="header_img" src="<%=basePath %>images/WFJClient/DigitalMall/jx.png" /><h6>驾校</h6></li>
            <li onclick="industrySearch('中餐厅')"><img class="header_img" src="<%=basePath %>images/WFJClient/DigitalMall/ms.png" /><h6>美食</h6></li>
            <li onclick="industrySearch('休闲')"><img class="header_img" src="<%=basePath %>images/WFJClient/DigitalMall/xxyl.png" /><h6>休闲娱乐</h6></li>
            <li onclick="industrySearch('护肤品')"><img class="header_img" src="<%=basePath %>images/WFJClient/DigitalMall/lr.png" /><h6>丽人</h6></li>
            <li><a href="<%=basePath %>ea/wfjbudget/ea_toBudget.jspa?"><img class="header_img" src="<%=basePath %>images/WFJClient/DigitalMall/yusuan.png" /><h6>预算</h6></a></li>
            <li onclick="industrySearch('运动')"><img class="header_img" src="<%=basePath %>images/WFJClient/DigitalMall/ydjs.png" /><h6>运动健身</h6></li>
            <li onclick="industrySearch('水果制品')"><img class="header_img" src="<%=basePath %>images/WFJClient/DigitalMall/sg.png" /><h6>水果</h6></li>
            <li onclick="industrySearch('蔬菜')"><img class="header_img" src="<%=basePath %>images/WFJClient/DigitalMall/tc.png" /><h6>特产</h6></li>
            <li onclick="industrySearch('休闲装')"><img class="header_img" src="<%=basePath %>images/WFJClient/DigitalMall/fs.png" /><h6>服饰</h6></li>
            <li><img class="header_img" src="<%=basePath %>images/WFJClient/DigitalMall/qb.png" /><h6>全部</h6></li>
            <div class="clear"></div>
        </ul>
        
        <section class="fw">
        	<div class="subject">
            	<h3><img src="<%=basePath %>images/WFJClient/DigitalMall/fw.png" />特色服务</h3>
            </div>
            <ul class="fw_con">
            	<li onclick="industrySearch('汽车驾校')">
                	<h4>汽车服务</h4>
                    <p>汽车周边，细心服务</p>
                    <img class="fw_img" src="<%=basePath %>images/WFJClient/DigitalMall/fw_qc.png" />
                </li>
                <li onclick="industrySearch('中餐厅')">
                	<h4>美食餐饮</h4>
                    <p>我身边的美食专家</p>
                    <img class="fw_img" src="<%=basePath %>images/WFJClient/DigitalMall/fw_ms.png" />
                </li>
                <li onclick="industrySearch('运动')">
                	<h4>运动</h4>
                    <p>运动健康季</p>
                    <img class="fw_img" src="<%=basePath %>images/WFJClient/DigitalMall/fw_yd.png" />
                </li>
                <li onclick="industrySearch('笔记本')">
                	<h4>数码时代</h4>
                    <p>玩转潮流新时代</p>
                    <img class="fw_img" src="<%=basePath %>images/WFJClient/DigitalMall/fw_sm.png" />
                </li>
                <div class="clear"></div>
            </ul>
        </section>
    	<!-- 代码 开始 -->
        <div id="fsD2" class="focus">  
            <div id="D1pic2" class="fPic">
            	<c:forEach items="${adList }" var="entity">
            	<c:if test="${entity[0] eq '地球数字商城中部' }">
                <div class="fcon" style="display: none;">
                	<input type="hidden" value="${entity[1] }" id="ppid"/>
                	<input type="hidden" value="${entity[2] }" id="goodsid"/>
                	<input type="hidden" value="${entity[5] }" id="companyid"/>
                    <a  href="javascript:void(0)"><img src="<%=basePath %>${entity[3]}" style="opacity: 1; "></a>
                </div></c:if> 
                </c:forEach>         
            </div>
            <div class="fbg">  
            <div class="D1fBt" id="D2fBt">  
                <a href="javascript:void(0)" hidefocus="true" target="_self" class=""><i>1</i></a>  
                <a href="javascript:void(0)" hidefocus="true" target="_self" class=""><i>2</i></a>  
                <a href="javascript:void(0)" hidefocus="true" target="_self" class="current"><i>3</i></a>  
                <a href="javascript:void(0)" hidefocus="true" target="_self" class=""><i>4</i></a>  
            </div>  
            </div>  
            <span class="prev"></span>   
            <span class="next"></span>    
        </div>
        <!--代码结束-->
        <section class="tj">
        	<div class="subject">
            	<h3><img src="<%=basePath %>images/WFJClient/DigitalMall/tj.png" />精品推荐</h3>
            </div>
            <ul class="tj_con">
            </ul>
        </section>
    
    </div>
    </article>
</div>


<script type="text/javascript">	
	Qfast.add('widgets', { path: basePath+"js/terminator2.2.min.js", type: "js", requires: ['fx'] });  
	Qfast(false, 'widgets', function () {
		K.tabs({
			id: 'fsD1',  //焦点图包裹id  
			conId: "D1pic1",  //** 大图域包裹id  
			tabId:"D1fBt",  
			tabTn:"a",
			conCn: '.fcon', //** 大图域配置class       
			auto: 1,   //自动播放 1或0
			effect: 'fade',   //效果配置
			eType: 'click', //** 鼠标事件
			pageBt:true,//是否有按钮切换页码
			bns: ['.prev', '.next'],//** 前后按钮配置class                          
			interval: 3000  //** 停顿时间  
		}) 
	});
	
	Qfast.add('widgets', { path: basePath+"js/terminator2.2.min.js", type: "js", requires: ['fx'] });  
	Qfast(false, 'widgets', function () {
		K.tabs({
			id: 'fsD2',  //焦点图包裹id  
			conId: "D1pic2",  //** 大图域包裹id  
			tabId:"D2fBt",  
			tabTn:"a",
			conCn: '.fcon', //** 大图域配置class       
			auto: 1,   //自动播放 1或0
			effect: 'fade',   //效果配置
			eType: 'click', //** 鼠标事件
			pageBt:true,//是否有按钮切换页码
			bns: ['.prev', '.next'],//** 前后按钮配置class                          
			interval: 3000  //** 停顿时间  
		}) 
	});    
</script>
<!-- 代码 结束 -->

<div style="text-align:center;clear:both;">

<script type="text/javascript">
var height = 0;
var t;
$(document).ready(function(e) {
		loaded();
     $(".top").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;");
     $(".top").find("li").attr("style","width:10%;");
     $(".top").find("li").find("img").attr("style","height:"+$(window).height()*0.04+"px;");
     $(".top").find("li").eq(1).attr("style","width:80%;");
	 
 	 $("article").attr("style","height:"+$(window).height()*0.93+"px;");
	 $(".fPic div.fcon").css("height",$(this).parent().height()+"px")
	 $("#D1pic1 div").css("height",$("#D1pic1").height())
	 

});
function getHeight(){
	t=setTimeout("getHeight()", 200);
	if($(".last").offset().top+$(".last").height()-$(".top").height()*3<$(window).height()){	
		if(pagenumber<pagecount){
			loaded();
		}	
	}
}
function loaded () {
	pagenumber++;
	var url=basePath+"ea/digitalmall/sajax_ea_ajaxDigitalMall.jspa?";			
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
						prostr+="<input type='hidden' value='"+pro[1]+"' id='ppid'/>";
						prostr+="<input type='hidden' value='"+pro[5]+"' id='goodsid'/>";
						prostr+="<input type='hidden' value='"+pro[6]+"' id='companyid'/>";
						prostr+="<input type='hidden' value='"+pro[10]+"' id='ccompanyid'/>";
		              	prostr+="<div class='tj_img'><img src='"+basePath+pro[4]+"'/></div>";
		                prostr+="<div class='tj_text'>";
		                prostr+="<h3>"+pro[0]+"</h3>";
		                prostr+="<h4>"+"￥"+pro[2]+"<span>"+pro[8]+"人已购买</span></h4>";                       
		                prostr+="</div></li>";
	                }else{
	                	prostr+="<li class='goodsDetail'>";
						prostr+="<input type='hidden' value='"+pro[1]+"' id='ppid'/>";
						prostr+="<input type='hidden' value='"+pro[5]+"' id='goodsid'/>";
						prostr+="<input type='hidden' value='"+pro[6]+"' id='companyid'/>";
						prostr+="<input type='hidden' value='"+pro[10]+"' id='ccompanyid'/>";
		              	prostr+="<div class='tj_img'><img src='"+basePath+pro[4]+"'/></div>";
		                prostr+="<div class='tj_text'>";
		                prostr+="<h3>"+pro[0]+"</h3>";
		                prostr+="<h4>"+"￥"+pro[2]+"<span>"+pro[8]+"人已购买</span></h4>";                       
		                prostr+="</div></li>";
	                }	                	
				}
				if(pagenumber == pagecount){
					prostr+="<li style='height:"+$(window).height()*0.05+"px;'><div style='text-align:center;height:100px;padding-top:"+$(window).height()*0.015+"px;'>无更多产品</div></li>";
				}
				$(".tj_con").append(prostr);				
				getHeight()
			}
		},
		error: function cbf(data){
			alert("产品加载失败");
		}
	});
}
function industrySearch(s){
	var url=basePath+"ea/digitalmall/ea_industrySearch.jspa?tradecode="+s+"&industry=industry&flag=2";
	window.location.href=url;
}
//广告详情
$(".fPic").live("click",function(){
	var goodsId=$(this).find("#goodsid").val();
	var url=basePath+"ea/digitalmall/ea_toDigitalMallAD.jspa?goodsId="+goodsId;
	window.location.href=url;
});
//产品详情
$(".goodsDetail").live("click",function(){
	var parms=new Array();
	parms.push("ppid="+$(this).find("#ppid").val());
	parms.push("&goodsid="+$(this).find("#goodsid").val());
	parms.push("&companyId="+$(this).find("#companyid").val());
	parms.push("&ccompanyId="+$(this).find("#ccompanyid").val());
	var url=basePath+"ea/wfjshop/ea_doodsDetail.jspa?"+parms.join("");
	window.location.href=url;
});
</script>
</body>
</html>
