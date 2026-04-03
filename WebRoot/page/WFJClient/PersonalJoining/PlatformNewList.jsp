<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/css/bootstrap.css">
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/css/WFJClient/style_platform.css">
<script type="text/javascript"
	src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/toucher.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>
</head>

<body>
	<header>
	<ul class="pub_top1">
		<li style="width: 10%;"><img
			src="<%=basePath%>/images/WFJClient/PersonalJoining/left_jt.png">
		</li>
		<li style="width: 80%;"><c:choose>
				<c:when test="${miniSystemJudge eq '00'}">
					公司简介
				</c:when>
				<c:when test="${miniSystemJudge eq '01'}">
				 	公司文化
				</c:when>
				<c:when test="${miniSystemJudge eq '02'}">
				 	新闻资讯
				</c:when>
				<c:when test="${miniSystemJudge eq '03'}">
				 	会员分享
				</c:when>
				<c:when test="${miniSystemJudge eq '04'}">
				 	公司论坛
				</c:when>
			</c:choose></li>
		<li style="width: 10%;"></li>
		<div class="clearfix"></div>
	</ul>
	</header>
	<div class="content content2">
		<div class="china_grd china_grd2 news_grd"></div>
	</div>
	<script>
	var basePath="<%=basePath%>";
		
		var ppid = "${ppid }";
		var pagenumber = 0;
		var height = 0;
		var t;
		var pagecount;
		var ccompanyId = '${ccompanyId}';
		var type = '${type}';
		var flag = "${param.flag}"
		var miniSystemJudge = '${miniSystemJudge}';
        var industryType = "${param.industryType}";
		function loaded() {
			clearTimeout(t);
			pagenumber += 1;
			var url = basePath + "ea/wfjplatform/sajax_ea_AjaxNews.jspa?ppid="
					+ ppid+"&industryType="+industryType;
			$
					.ajax({
						url : url,
						type : "get",
						async : false,
						dataType : "json",
						data : {
							"pageForm.pageNumber" : pagenumber,
							"ccompanyId" : ccompanyId,
							"type" : type,
							"miniSystemJudge" : miniSystemJudge
						},
						success : function(data) {
							var member = eval("(" + data + ")");
							var list = member.list;
							pagecount = member.pagecount;
							if (list.length == 0) {
								if (miniSystemJudge == '00') {
									$("title").html("公司简介");
								} else if (miniSystemJudge == '01') {
									$("title").html("公司文化");
								} else if (miniSystemJudge == '02') {
									$("title").html("公司新闻");
								} else if (miniSystemJudge == '03') {
									$("title").html("会员分享");
								} else if (miniSystemJudge == '04') {
									$("title").html("公司论坛");
								}
								return;
							} else {
								var htl = new Array();
								$("title").html(list[0][0]);
								for ( var int = 0; int < list.length; int++) {
									var news = list[int];
									var name = "";
									if (news[4] != "") {
										name = news[7] + "平台";
									} else {
										name = news[6];
									}
									var t = news[2].split(".")[0]+"small."+news[2].split(".")[1];
									htl
											.push("<div class='china_mil' id='"+news[3]+"'><input type='hidden' class='types' value='"+news[9]+"' /><img src='"+basePath+t+"' alt='' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'><input class='ppID' type='hidden' value='"+news[8]+"'/>");
									htl.push("<div class='china_mil_txt'><h4>"
											+ news[0] + "</h4><h5 limit='25'>"
											+ news[5] + "</h5></div>");
									htl.push("<div class='clearfix'></div><p>"
											+ name + "</p><p>"
											+ news[1].substring(0, 10)
											+ "</p><hr></div>");
								}
								if (pagenumber == pagecount) {
									var aa = "";
									if (miniSystemJudge == "00") {
										aa = "更多简介正在添加中";
									} else if (miniSystemJudge == "01") {
										aa = "更多文化正在添加中";
									} else if (miniSystemJudge == "02") {
										aa = "更多新闻正在添加中";
									} else if (miniSystemJudge == "03") {
										aa = "更多分享正在添加中";
									} else if (miniSystemJudge == "04") {
										aa = "更多公司论坛正在添加中";
									}

									htl
											.push("<div style='text-align:center;font-size:"
													+ $(window).height()
													* 0.025
													+ "px;height:"
													+ $(window).height()
													* 0.05
													+ "px;color:#373737;'>"
													+ aa + "</div>");
								}
								$(".news_grd").append(htl.join(""));
								getHeight(".news_grd", ".content", "loaded()");
								$(".china_mil_txt").find("img").css("display",
										"none");
								$(".china_mil_txt").find("span").attr("style",
										"");
								$(".pub_txt")
										.attr(
												"style",
												"word-break: break-all;text-overflow: ellipsis;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 2;overflow: hidden;text-align: left;height:"
														+ $(window).height()
														* 0.06 + "px;")
							}
						},
						error : function(data) {
							alert("获取项目失败");
						}
					});

			jQuery.fn.limit = function() {
				var self = $("[limit]");
				self.each(function() {
					$(this).find(".article_pro").remove();
					$(this).find(".more_pro").remove();
					var objString = $(this).text();
					var objLength = $(this).text().length;
					var num = $(this).attr("limit");
					if (objLength > num) {
						$(this).attr("title", objString);
						objString = $(this).text(
								objString.substring(0, num) + "...");
					} else {
						objString = $(this).text(objString);
					}
				})
			}
			$(function() {
				$("[limit]").limit();
			})

		}
		//新闻详情
		$(document)
				.on(
						"click",
						".china_mil",
						function() {
							var goodsid = $(this).attr("id");
							var ppID = $(this).find(".ppID").val();
							var types = $(this).find(".types").val();
							if (types == "会员分享") {
								document.location.href = basePath
											+ "ea/industry/ea_informationDetails.jspa?ppId=" + ppID
											+ "&ccompanyId=" + ccompanyId
											+ "&type=" + type
											+ "&miniSystemJudge=03";
							} else if (types == "公司新闻") {
								if (type == 'web') {
									document.location.href = basePath
											+ "ea/industry/ea_informationDetails.jspa?ppId=" + ppID
											+ "&ccompanyId=" + ccompanyId
											+ "&type=" + type
											+ "&miniSystemJudge=02";
								} else {
									document.location.href = basePath
											+ "ea/wfjplatform/ea_newdetail.jspa?goodsid="
											+ goodsid + "&ppid=" + ppid;
								}
							} else if (types == "公司简介") {
								document.location.href = basePath
										+ "ea/industry/ea_informationDetails.jspa?ppId=" + ppID
										+ "&ccompanyId=" + ccompanyId
										+ "&type=" + type
										+ "&miniSystemJudge=00";
							} else if(types == "公司文化"){
								document.location.href = basePath
										+ "ea/industry/ea_informationDetails.jspa?ppId=" + ppID
										+ "&ccompanyId=" + ccompanyId
										+ "&type=" + type
										+ "&miniSystemJudge=01";
							}
						});
		$(".pub_top1")
				.find("li")
				.eq(0)
				.click(
						function() {
						    if(flag == "plat"){
                                open(
                                    basePath
                                    + "ea/industry/ea/industry/ea_getPlatform.jspa?industryType=汽车交通工具/汽车驾校", "_self");
							} else if (type == 'web') {
//								open(
//										basePath
//												+ "ea/industry/ea_getCompanyHome.jspa?ccompanyId="
//												+ ccompanyId, "_self");
                                history.back();
							} else {
								document.location.href = basePath
										+ "ea/wfjplatform/ea_getPlatformByPpid.jspa?ppid="
										+ ppid + "&type=summary";
							}
						});
	</script>
	<script>
		
	</script>

	<script>
		$(document).ready(
				function() {
					$("header").attr(
							"style",
							"height:" + $(window).height() * 0.08
									+ "px;line-height:" + $(window).height()
									* 0.08 + "px;");
					$(".content").attr(
							"style",
							"margin-top:" + $(window).height() * 0.08
									+ "px;height:" + $(window).height() * 0.92
									+ "px;overflow: auto;");
					loaded();
				});
	</script>
	<script>
		// var num1=num2=num3=0
		window.onload = window.onresize = function() {
			//含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
			//获取窗口的尺寸
			var clientWidth = document.documentElement.clientWidth;
			//console.log(clientWidth);
			//通过屏幕宽度去设置不同的后台根字体的大小
			//document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
			document.getElementsByTagName('html')[0].style.fontSize = clientWidth
					/ 640 * 40 + 'px'
		}
	</script>
</body>
</html>