  <!DOCTYPE html>
<%@page pageEncoding="UTF-8" language="java"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"  %>
<%
   String path=request.getContextPath();
   String basePath=request.getScheme()+"://"+request.getServerName()
        +":"+request.getServerPort()+path+"/";
   
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

<link rel="stylesheet" type="text/css" href="<%=basePath %>/css/ea/finance/att_1.css" />

<script type="text/javascript" src="<%=basePath %>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
         




<title>好友名片</title>

   
<script type="text/javascript">
function moveClass(x){
			
			document.getElementById(x).className = ""
			document.getElementById(x).placeholder = ""
		
			}
			
function addClass(x){
	var text = $(".search_width input").val()
		if( text == ""){
			$(".search_width input").addClass("moren")
			document.getElementById(x).placeholder = "搜索"
			
			} else {
				$(".search_width input").removeClass("moren")
				document.getElementById(x).placeholder = ""
				}
	} 
</script>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var user="${joinFans.zaccount}";
	</script>
</head>

<body >

	<div class="main">
		<div class="top">
			<ul>
				<li><a href="javaScript:window.history.go(-1);"><img
						src="<%=basePath%>js/jqModal/css/images_blue/arrow.png" /></a></li>
				<li>关注资源</li>
				<li style="visibility:hidden;"></li>
			</ul>
		</div>

	</div>
	<div class="search">
		<div class="search_width">
			<div style="width:97%; margin-left:3%;">
				<input class="moren" type="text" placeholder="搜索"
					onfocus="moveClass(this.id)" id="searchs"
					onblur="addClass(this.id)" /> <img
					src="<%=basePath%>js/jqModal/css/images_blue/search.png" alt=""
					id="img1" />

			</div>

		</div>

	</div>
	
	<div class="sort_box_hidden">
		
				<div class="sort_box">
				
				</div>
				
	</div>
	
 <script type="text/javascript">
var height = 0;
var t;
var pagenumber=1;
var  pagecount;
function loaded() {
	var url;
	clearTimeout(t);
	pagenumber += 1;
	if(result1==""){
		
	 url=basePath+"ea/resourse/sajax_ea_att_1.jspa?type=type"
	}else{
	 url=basePath+"ea/resourse/sajax_ea_att_1.jspa?type=search&result="+result1;
	}
	$("div.sort_box").empty();
	$.ajax({
	    url:url,
		type:"get",
		async : false,
		data:{
			
			 "stype":"ajax",
			 "joinFans.zaccount":user,
             
           
             
			},

		dataType : "json",
		success:function(data){
		var member = eval("(" + data + ")");
		var pageForm = member.pageForm;
		if(pageForm!=null){
		pagecount=pageForm.pageCount;
		
		var objs = pageForm.list;
		
		var goodstr = "";
		for ( var i = 0; i < objs.length; i++) {
			var obj = objs[i];
			goodstr+= "<div class='sort_list'><div class='num_logo'>";
			if(obj[1]==null){
            	goodstr+="<img id='imgs' src='"+basePath+"images/mp_5.jpg'>";
            }else{
            	
			goodstr+="<img id='imgs' src="+basePath+obj[1]+">";
            }
			goodstr+="</div><div class='num_name'>"+obj[0]+"</div></div>";
			
	
	   
	     
			
		}
		}
		$("div.sort_box").append(goodstr);
		$(".sort_list .num_logo img").attr(
				"style",
				"height:" + $(window).height() * 0.05
						+ "px;width:" + $(window).height()
						* 0.05 + "px;");
		$(".sort_box_hidden").attr(
				"style",
				"height:" + window.innerHeight-($(".main").height()+$(".search").height()+$(window).height()*0.065)
						+ "px;width:"
						+ (window.innerWidth)
						+ "px;");
		$(".sort_box").attr(
				"style",
				"height:" + (window.innerHeight) *0.85
						+ "px;width:" + (window.innerWidth+17)
						+ "px;overflow:auto")
		$(".sort_list").attr("style",
				"height:" + (window.innerHeight) *0.05+ 
				 "px;");
		
		
	},error:function(data){
		alert("获取项目失败");
	}
	
}); 
	 getHeight(".sort_box",".sort_box_hidden","loaded()");
 

					  
					   

				
			
		}
	</script>
<script type="text/javascript">
var result1="";
		$(document)
				.ready(
						function(e) {
							
							
							$("body").attr(
									"style",
									"width:" + $(window).width() + "px;height:"
											+ $(window).height()
											+ "px;overflow: hidden;")
							//头部
							$(".top").attr(
									"style",
									"height:" + $(window).height() * 0.07
											+ "px;line-height:"
											+ $(window).height() * 0.07
											+ "px;width:" + $(window).width()
											+ "px;margin-top:-10px;");
							$(".top").find("li").attr("style", "width:10%;");
							$(".top").find("li").find("img").attr(
									"style",
									"height:" + $(window).height() * 0.04
											+ "px;");
							$(".top").find("li").eq(1).attr("style",
									"width:80%;");
							//搜索
							$("#img1").attr("style","width:" + $(window).width()
								*0.06 + "px;height:" + $(window).height() * 0.03
								+ "px;margin:"+$(window).height() * 0.01+"px "+$(window).height() * 0.01+"px;");
							
							//搜索
							$(".search").attr("style",
									"width:" + $(window).width() + "px;height:" + $(window).height() * 0.001
									+ "px;padding:"+ $(window).height()*0.044 + "px 0;");
							$(".search_width").attr(
									"style",
									"border-radius:" + $(window).height()
											* 0.01 + "px;height:" + $(window).height() * 0.05
											+ "px;margin-top:-"+ $(window).height() * 0.02
											+ "px;");			
							
							$(".search_width img").click(
									function() {

										result1 = $("#searchs").val();	

										loaded();
									});
							window.onkeypress = function() {
								if (event.keyCode == 13) {
									
									 result1 = $("#searchs").val();
									 loaded();
								}
							}
							loaded();
						});
	</script>
	       
</body>
</html>


 