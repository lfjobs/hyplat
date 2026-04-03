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

         <link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/iscroll.css"/>
<script type="text/javascript" src="<%=basePath%>js/slideUpDownRefresh_files/iscroll.js"></script>
		  <link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/bootstrap.css"/>
          <link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/bootstrap-theme.css"/>






<title>城乡社区</title>

   
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
	var pagenumber="${pageForm.pageNumber}";
	var result1="${result}";
</script>
</head>

<body onload="loaded()">

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
	<div id="letter"></div>
	<div class="sort_box_hidden">
		<div id="wrapper">
			<div id="scroller">
				<div id="scroller-pullDown">
					<img id="wfj_sx"
						src="<%=basePath%>images/ea/finance/BenDis/fej_jiazai.gif"
						width="20px" height="20px"> <span id="down-icon"
						class="icon-double-angle-down pull-down-icon"></span> <span
						id="pullDown-msg" class="pull-down-msg sx">下拉刷新</span>
				</div>
				<div class="sort_box">
				<c:forEach items="${pageForm.list}" var="l">

						<div class="sort_list">
							<div class="num_logo">
								 <c:if test="${l[1]!=''}">
								<img id="imgs" src="<%=basePath %>${l[1]}">
								</c:if>
								<c:if test="${l[1]==''}">
								<img id="imgs" src="<%=basePath %>images/mp_5.jpg">
								</c:if>
							</div>
							<div class="num_name">${l[0]}</div>
						</div>

					</c:forEach>
				</div>
				<div id="scroller-pullUp">
					<img src="<%=basePath%>images/ea/finance/BenDis/fej_jiazai.gif"
						width="20px" height="20px"> <span id="up-icon"
						class="icon-double-angle-up pull-up-icon"></span> <span
						id="pullUp-msg" class="pull-up-msg sx">上拉加载...</span>
				</div>
			</div>
		</div>
	</div>
	

	<script type="text/javascript">
	    
	    var pn=1;
		var myScroll,
			upIcon = $("#up-icon"),
			downIcon = $("#down-icon");
			document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
		function loaded () {
			
			
            //preventDefault为false这行就是解决onclick失效问题
            //为true就是阻止事件冒泡,所以onclick没用
             
			myScroll = new IScroll('#wrapper', { scrollX: false, scrollY: true, mouseWheel: true,probeType: 3,preventDefault:false});
	
			myScroll.on("scroll",function(){
				var y = this.y,	maxY = this.maxScrollY - y,downHasClass = downIcon.hasClass("reverse_icon"),
					upHasClass = upIcon.hasClass("reverse_icon");
		
				if(y >= 40){
					!downHasClass && downIcon.addClass("reverse_icon");
					return "";
				}else if(y < 40 && y > 0){
					
					downHasClass && downIcon.removeClass("reverse_icon");
					return "";
				}
				
				if(maxY >= 40){
					!upHasClass && upIcon.addClass("reverse_icon");
					return "";
				}else if(maxY < 40 && maxY >=0){
					upHasClass && upIcon.removeClass("reverse_icon");
					return "";
				}
			});
			
			myScroll.on("slideDown",function(){
				if(this.y > 40){
					console.log("down");
					//下拉刷新操作
					//document.location.href=basePath+"ea/resourse/ea_att_1.jspa?stype=&joinFans.zaccount="+user+"&type=search&result="+result1;
					myScroll.refresh();
				}
			});
			
			myScroll.on("slideUp",function(){
				
				if(this.maxScrollY - this.y > 40 ){
					
					console.log("up");	
					//上拉操作
					
					pn=pn+1;
					
					var count=parseInt("${pageForm.recordCount}");
					var pageSize=parseInt("${pageForm.pageSize}");
					var pagenumber=count%pageSize==0?Math.floor(count/pageSize):Math.floor(count/pageSize)+1;
					
					 if(pn<=pagenumber){
						
						pn=pn>=parseInt("${pageForm.recordCount}")?parseInt("${pageForm.recordCount}"):pn;
						//var url=basePath+"ea/resourse/sajax_ea_att_1.jspa?";
						var basePath="<%=basePath %>";
						
						
							$.ajax({
							    url:url,
								type:"get",
								async : false,
								data:{
									 "pageForm.pageNumber":pn,
									 "stype":"ajax",
									 "joinFans.zaccount":user,
						             "type":"search",
						             "result":result1,
						             
									},

								dataType : "json",
								success:function(data){
								var member = eval("(" + data + ")");
								var pageForm = member.pageForm;
								var pageNumber=pageForm.pageNumber;
							 
								
								var objs = pageForm.list;
								
								var goodstr = "";
								for ( var i = 0; i < objs.length; i++) {
									var obj = objs[i];
									goodstr+= "<div class='sort_list'><div class='num_logo'>";

									goodstr+="<img id='imgs' src="+basePath+obj[1]+"></div><div class='num_name'>"+obj[0]+"</div></div>";
									
							
							   
							     
									
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
												+ (window.innerWidth - 18)
												+ "px;overflow:hidden");
								$(".sort_box").attr(
										"style",
										"height:" + (window.innerHeight) *0.85
												+ "px;width:" + window.innerWidth
												+ "px;overflow:auto")
								$(".sort_list").attr("style",
										"height:" + (window.innerHeight) *0.08+ 
										 "px;");
								$(".wfj12_010_content").attr("style","width:"+$(window).width()+"px;");
								$(".wfj12_010_hidden").attr("style","overflow-y: auto;");
								myScroll.refresh();
								
							},error:function(data){
								alert("获取项目失败");
							}
							
						}); 
				
						
					}
					 
				}
			});
	
		}
	</script>
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
									"width:" + $(window).width() + "px;height:" + $(window).height() * 0.08
									+ "px;padding:"+ $(window).height()*0.044 + "px 0;");
							$(".search_width").attr(
									"style",
									"border-radius:" + $(window).height()
											* 0.01 + "px;height:" + $(window).height() * 0.05
											+ "px;margin-top:-"+ $(window).height() * 0.02
											+ "px;");


							$(".sort_list .num_logo img").attr(
									"style",
									"height:" + $(window).height() * 0.05
											+ "px;width:" + $(window).height()
											* 0.05 + "px;top:10px");
							//
                              
                            var height=$(window).height()*0.09;
                            var height1=$(".top").height();
                            var height2=height+height1;
                            
                            $("#wrapper").attr("style","top:"+height2+"px;width:" + $(window).width()
								*0.95 + "px;");
							$(".initials").attr("style",
									"top:" + $(window).height() * 0.2 + "px;")

							$(".initials li")
									.attr(
											"style",
											"height:" + $(window).height()
													* 0.025 + "px;font-size: "
													+ $(window).height() * 0.01
													+ "px;")
							$(".sort_box_hidden").attr(
									"style",
									"height:" + window.innerHeight-($(".main").height()+$(".search").height()+$(window).height()*0.065)
											+ "px;width:"
											+ (window.innerWidth - 18)
											+ "px;overflow:hidden");
							$(".sort_box").attr(
									"style",
									"height:" + (window.innerHeight) *0.9
											+ "px;width:" + window.innerWidth
											+ "px;overflow:auto");
							$(".sort_list").attr(
									"style",
									"height:" + (window.innerHeight) *0.09
											+ "px;");
							
							
							$(".search_width img")
									.click(
											function() {

												
											});
							$(".sort_list .num_name").attr(
									"style",
									"font-size:"+$(window).height()*0.025+"px;");
							window.onkeypress = function() {
								if (event.keyCode == 13) {
									
									
								}
							}
						});
	</script>
</body>
</html>


 