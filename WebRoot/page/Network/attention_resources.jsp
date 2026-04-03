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

<link rel="stylesheet" type="text/css" href="<%=basePath %>/css/ea/finance/attention_resources.css" />
<script type="text/javascript" src="<%=basePath %>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

        



<title>关注资源</title>

   
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
	var user="${user}";
</script>
</head>

<body >

	<div class="main">
		<div class="top">
			<p>关注资源</p>
		</div>

	</div>
	<div class="search">
		
			<section class="one">
        	<ul>
            	<li class="mp" ><img src="<%=basePath%>js/jqModal/css/images_blue/mp_1.png" /><span>好友名片</span></li>
                <li class="mp"><img src="<%=basePath%>js/jqModal/css/images_blue/mp_2.png" /><span>公司名片</span></li>
                <li class="mp"><img src="<%=basePath%>js/jqModal/css/images_blue/mp_3.png" /><span>商务园区</span></li> 
                <li class="mp"><img src="<%=basePath%>js/jqModal/css/images_blue/mp_4.png" /><span>城乡社区</span></li>
            </ul>
        </section>
        <div class="top1">
                         
           </ul>
	  </div>

	</div>
	<div id="letter"></div>
	<div class="sort_box_hidden">
		
				
				
				<div class="sort_box">
				
				</div>
				
			
	</div>
	


	<script type="text/javascript">
     var basePath="<%=basePath%>";
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
											+ "px;");
							$(".top").find("li").attr("style", "width:10%;");
							$(".top").find("li").find("img").attr(
									"style",
									"height:" + $(window).height() * 0.04
											+ "px;");
							$(".top").find("li").eq(1).attr("style",
									"width:80%;");
							$(".one li img").attr("style","height:"+$(window).height()*0.06+"px;width:"+$(window).height()*0.06+"px;");
							$(".one ul li span").attr("style","font-size:"+$(window).height()*0.025+"px;");
							 $(".two h5").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;");
							    $(".two ul li").attr("style","font-size:"+$(window).height()*0.02+"px;");
								$(".two li img").attr("style","height:"+$(window).height()*0.06+"px;width:"+$(window).height()*0.06+"px;");
							    
							//搜索
							$(".search").attr("style",
									"width:" + $(window).width() + "px;height:" + $(window).height() * 0.44
									+ "px;");
							
							$(".mp").click(function(){
								var text=$(this).find("span").text();
								if(text=="好友名片"){
									window.location.href= basePath+"ea/resourse/ea_att_1.jspa?joinFans.zaccount="+${user}+"&type=type&stype=stype";
								}
								if(text=="公司名片"){
									window.location.href= basePath+"ea/resourse/ea_att_2.jspa?joinFans.zaccount="+${user}+"&type=type&stype=stype";
								}
								if(text=="商务园区"){
									window.location.href= basePath+"ea/resourse/ea_att_3.jspa?joinFans.zaccount="+${user}+"&type=type&stype=stype";
								}
								if(text=="城乡社区"){
									window.location.href= basePath+"ea/resourse/ea_att_4.jspa?joinFans.zaccount="+${user}+"&type=type&stype=stype";
								}
							});
							loaded();
						});
	</script>
	 <script type="text/javascript">
var height = 0;
var t;
var pagenumber=1;
var  pagecount;
function loaded() {
	 
	clearTimeout(t);
	pagenumber += 1;
	
	
	$.ajax({
	    url:basePath+"ea/resourse/sajax_ea_attresources.jspa?",
		type:"get",
		async : false,
		data:{
			 
			 "stype":"ajax",
			 "user":user,
            
             
			},

		dataType : "json",
		success:function(data){
		var member = eval("(" + data + ")");
		var pageForm = member.pageForm;
		var pageNumber=pageForm.pageNumber;
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
		$("div.sort_box").append(goodstr);
		
	    var height=$(".main").height()+$(".search").height()+$(window).height()*0.01;
		
	    $(".sort_box_hidden").attr(
				"style",
				"height:" + parseInt(window.innerHeight-height)
						+ "px;width:"
						+ (window.innerWidth)
						+ "px;margin-top:"+(window.innerHeight) *0.015+"px");
		$(".sort_box").attr(
				"style",
				"height:" + (window.innerHeight) 
						+ "px;width:" + (window.innerWidth+17)
						+ "px;overflow:auto;")
		$(".sort_list").attr(
									"style",
									"height:" + (window.innerHeight) *0.04
											+ "px;");
		$(".sort_list .num_logo img").attr(
									"style",
									"height:" + $(window).height() * 0.05
											+ "px;width:" + $(window).height()
											* 0.05 + "px;top:10px");
		$(".sort_list .num_name").attr(
									"style",
									"width:" + $(window).width()
											 + "px;font-size:"+$(window).height()*0.025+"px;");
		
		
	},error:function(data){
		alert("获取项目失败");
	}
	
}); 
				        getHeight(".sort_box",".sort_box_hidden","loaded()");
 

					  
					   

				
			
		}
	</script>
</body>
</html>


 