<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
	<link href="<%=basePath%>css/contacts/Restaurant/style13.css" rel="stylesheet"
		type="text/css" />
		<link href="<%=basePath%>css/contacts/Restaurant/xiugai.css" rel="stylesheet"
		type="text/css" />
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/restaurant/iscroll-probe.js"></script>
	
		
<title>选择餐桌和服务员</title>

</head>
<script type="text/javascript">
	var companyId='${companyId}';
    var ccompanyId='${ccompanyId}';
	var basePath='<%=basePath%>';
	var producttype = "${productDesign.type}";
	var pagecount = ${pageForm.pageCount==null?0:pageForm.pageCount};
    var count = ${pageForm.recordCount==null?0:pageForm.recordCount};
    var pageSize = ${pageForm.pageSize==null?0:pageForm.pageSize};
    var pagenumber = ${pageForm.pageNumber==null?0:pageForm.pageNumber};
</script>
<body style="overflow: auto;">
	<div class="wfj11_015" style="overflow: auto;">
		<div class="wfj11_015_top top fix">
			<ul id="tops">
				<li><img
					src="<%=basePath%>images/contacts/comments/wfj_return_02.png" /></li>
				<li>餐桌和服务员</li>
			</ul>
		</div>
		<div class="box">
			<div>
				<div class="wfj_same_hidden">
					<div class="wfj_same_auto">
						<div class="wfj11_015_top_hide" style="height: 50px;"></div>
						
								<!--切换头部  -->
								
									<div class="can_Nav">
										<div class="can_Nav_main tabs" id="tabs01">
											<div class="baojian pull-left on current" name="${pageForm.pageNumber+1}" pageCount = "">选择包间</div>
											<div class="fuwuyuan pull-right" name="1">选择服务员</div>
										</div>
									</div>
								
								<div class="hp" id="scroll1">
								<div id="pp">
								<div class="container" id="container01">
									<div class="vip wfj_same jiucan con">
										<!--包间选择  -->
										<div class="wfj_same_bot" id="VIP">
							<input type="hidden" id="roomNumber" value="${pageForm.pageNumber}" />
							<input type="hidden" id="roomCount" value="${pageForm.pageCount}" />
											<c:forEach items="${pageForm.list}" var="zlist">
												<div class="wfj_same_bot_lis">
													<div class="wfj_same_bot_lis_main">
														<div class="imgs Img">
															<img src="<%=basePath%>${zlist[1]}" />
														</div>
														<div class="Text" onclick="dianji(this)">
															<h3>${zlist[0]}</h3>
															<input type="hidden" id="baojian" value="${zlist[0]}">
															<input type="hidden" id="goodsid" value="${zlist[3]}">
															<div class="Img_start">
																<span><img
																	src="<%=basePath%>/images/contacts/start_03.png" alt="" />
																</span> <span><img
																	src="<%=basePath%>/images/contacts/start_03.png" alt="" />
																</span> <span><img
																	src="<%=basePath%>/images/contacts/start_03.png" alt="" />
																</span> <span><img
																	src="<%=basePath%>/images/contacts/start_03.png" alt="" />
																</span>
																 <span><img
																	src="<%=basePath%>/images/contacts/start_03.png" alt="" />
																</span>
															</div>
															<div class="Text_main">
																<nobr>${zlist[2]}</nobr>
															</div>
														</div>
														<div class="Img_gou">
															<img class="gou"
																src="<%=basePath%>/images/contacts/restaurant/chan_03_03.png"/>
																<img class="gou on"
																src="<%=basePath%>/images/contacts/restaurant/chan_07.png"
																alt="" />
														</div>
													</div>
												</div>
											</c:forEach>
										</div>
									</div>
									<div class="waiter wfj_same con">
										<input type="hidden" id="waiterNumber"
											value="${pageFormto.pageNumber}" /> <input type="hidden"
											id="waiterCounts" value="${pageFormto.pageCount}" />
										<div class="wfj_same_bot" id="fuwuyuan">
											<c:forEach items="${pageFormto.list}" var="per">
												<div class="wfj_same_bot_lis" >
													<div>
														<div class="imgs2 Img">
															<c:if test='${per[1]!=null&&per[1]!=""}'>
																<img src="<%=basePath%>${per[1]}" />
															</c:if>
															<c:if test='${per[1]==null||per[1]==""}'>
																<img
																	src="<%=basePath%>/images/contacts/restaurant/defaltwaiter.png"
																	/>
															</c:if>
														</div>
														<div class="Text">
															<h3>${per[0]}${per[2]}</h3>
															<div class="Img_start">
																<span><img
																	src="<%=basePath%>/images/contacts/start_03.png" alt="" />
																</span> <span><img
																	src="<%=basePath%>/images/contacts/start_03.png" alt="" />
																</span> <span><img
																	src="<%=basePath%>/images/contacts/start_03.png" alt="" />
																</span> <span><img
																	src="<%=basePath%>/images/contacts/start_03.png" alt="" />
																</span>
																 <span><img
																	src="<%=basePath%>/images/contacts/start_03.png" alt="" />
																</span>
															</div>
															<!-- <div class="Text_main">
																<nobr>多提意见然而噶尔格二哥挖让给挖让给ear艾eagre尔</nobr>
															</div> -->
														</div>
														<div class="Img_gou">
															<img class="gou"
																src="<%=basePath%>/images/contacts/restaurant/chan_03_03.png"
																alt="" />
																<img class="gou on"
																src="<%=basePath%>/images/contacts/restaurant/chan_07.png"
																alt="" />
														</div>
													</div>
												</div>
											</c:forEach>
										</div>
									</div>
								</div>
							</div>
						</div>
							</div>
					</div>
						<input type="hidden" id="privateRoom" value="${param.privateRoom}">
						<div class="huaixin_footer">
							<span class="noxuan">
								<p>选择的项目</p> </span> <span class="xuan">
								<div class="canzhuo"></div>
								<div class="waiters"></div>
								<div class="go" onclick="clickJump(this)">确定</div> </span>
					
				</div>
			</div>
		</div>

<script>
	$(document).ready(function(e) {
						$(".wfj11_015_top_hide").css("height",$(".wfj11_015_top").height() + "px")
						$("body").css("height", $(window).height());
						$('#scroll1').css('height', $(window).height()-100);
						//修改字体大小
						$("#tops").find("li").attr("style", "float:left;");
						$("#tops").find("li").eq(0).attr("style", "width:15%;");
						$("#tops").find("li").eq(0).find("img").attr("style","height:" + $(window).height() * 0.03+ "px;padding-left:"
										+ $(window).height() * 0.02
										+ "px; vertical-align:middle;");
						$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+ $(window).height() * 0.03+ "px;");
						$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
						$("#tops").find("li").eq(2).find("img").attr("style","height:"+ $(window).height()* 0.03+ "px; width:auto; vertical-align:middle;");
						
						$(".wfj_same .wfj_same_bot .wfj_same_bot_lis div.Img").css("height",$(".wfj_same_bot_lis_main").height()+ "px")

					//包间的点击
						$(document).on("click",
						".jiucan .wfj_same_bot .wfj_same_bot_lis img.gou",
						function(){
						$("#VIP").find(".selected").find("img").toggleClass("on");
						$("#VIP").find(".selected").removeClass("selected");
						$(this).parent().find("img").toggleClass("on");
						$(this).parent().addClass("selected");
				
						var text = $(this).parents(".wfj_same_bot_lis").find(".Text").find("h3").text();
						$(".xuan .canzhuo").html(text);
						$(".xuan").css("display", "block");
						$(".noxuan").css("display", "none");
						if(($(".huaixin_footer .xuan .canzhuo").text()=="")||($(".huaixin_footer .xuan .waiters").text()=="")){

						$(".huaixin_footer .xuan .go").css("background","#a9a9a9")
						}else if(($(".huaixin_footer .xuan .canzhuo").text()!="")||($(".huaixin_footer .xuan .waiters").text()!="")){
							$(".huaixin_footer .xuan .go").css("background","#e7821f")
						}
						})
						//服务员的点击
						$(document).on("click",
						".waiter .wfj_same_bot .wfj_same_bot_lis img.gou",
						function(){
					    $("#fuwuyuan").find(".selected").find("img").toggleClass("on");
						$("#fuwuyuan").find(".selected").removeClass("selected");
						$(this).parent().find("img").toggleClass("on");
						$(this).parent().addClass("selected");
						var text1 = $(this).parents(".wfj_same_bot_lis").find(".Text").find("h3").text();
						$(".xuan .waiters").html(text1);
						$(".xuan").css("display", "block");
						$(".noxuan").css("display", "none");					
										
						if(($(".huaixin_footer .xuan .canzhuo").text()=="")||($(".huaixin_footer .xuan .waiters").text()=="")){
							$(".huaixin_footer .xuan .go").css("background","#a9a9a9")
						}else if(($(".huaixin_footer .xuan .canzhuo").text()!="")||($(".huaixin_footer .xuan .waiters").text()!="")){
							$(".huaixin_footer .xuan .go").css("background","#e7821f")
						}
						})
						//返回点击
						$("#tops").click(function() {
							history.go(-1)
						})
						var privateRoom = $("#privateRoom").val();
						$(".wfj_same .wfj_same_bot .wfj_same_bot_lis div.Text").each(function(){if($(this).find("h3").text()==privateRoom){$(this).next(".Img_gou").find(".gou").click();
							}
						}) 

});
</script>
<script type="text/javascript" src="<%=basePath%>js/restaurant/xiugai.js"></script>

</body>
</html>