<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta charset="UTF-8">
	<meta name="viewport"
		content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<meta name="format-detection" content="telephone=no, email=no" />
	<meta name="screen-orientation" content="portrait">
		<meta name="x5-orientation" content="portrait">
			<link rel="stylesheet"
				href="<%=basePath%>css/ea/production/officemanage/base.css">
				<link rel="stylesheet"
					href="<%=basePath%>css/ea/production/officemanage/manage_5L5C.css">
					<script type="text/javascript"
						src="<%=basePath%>js/WFJClient/jquery.min.js"></script>
					<script type="text/javascript"
						src="<%=basePath%>js/ea/production/cprocedure/officemanage/queryNews.js"></script>

					<title>公司新闻</title>
</head>

<body>
	<!-- header start  -->
	<header class="com_head"> <a
		href="javascript:history.back(-1)" class="back"></a>
	<h1>公司新闻</h1>
	<a href="javascript:void(0);" class="head_R"><i
		class="add_new_ico"></i>
	</a>
	<div id="prompt" style="width: 100%; display: none;z-index: 1001">
		<center>
		<div>
			<span style="position: relative; top: 19.8%;"></span>
		</div>
		</center>
	</header>
	<!--  header end  -->
	<!-- 页面内容 start  -->
	<div class="wrap_page">
		<section>
		<div class="wrap_contain">
			<div class="contain_hd">
				<div class="contain_hd_L">
					<i class="checkbox_ico"></i> <span>全部新闻故事（<span
						class="job_num"></span>）</span>
				</div>
				<a href="javascript:;" class="contain_hd_R"> <span
					class="edit_btn">编辑</span><span class="complete_btn">完成</span> </a>
			</div>
			<div class="contain_bd">
				<!-- json拼接 -->
			</div>
			<div class="contain_fd">
				<a href="javascript:void(0);" class="dele_btn" onclick="delNews()"><i></i><span>删除项目</span>
				</a>
			</div>
		</div>
		</section>
	</div>
	<!--  页面内容 end -->
	<script>
    	var basePath='<%=basePath%>
		';
		var companyId = '${cuscom.companyId}';
		var staffId = '${cuscom.staffid}';
		var pagenumber;
		var pagecount;
		var t;

		window.onload = window.onresize = function() {
			//含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
			//获取窗口的尺寸
			var clientWidth = document.documentElement.clientWidth;
			//通过屏幕宽度去设置不同的后台根字体的大小
			document.getElementsByTagName('html')[0].style.fontSize = clientWidth
					/ 640 * 40 + 'px';
		}
		//初始化
		$("input[type='checkbox']").prop("disabled", true);

		//判断是否为编辑状态
		$(document).on("click", ".contain_hd_R", function(e) {
			var m = $(".contain_bd").find(".judge").length;
			if (m == 0) {
				var _n = $(".edit").length;
				if (_n == 0) {
					$(".wrap_contain").addClass("edit");
					$("input[type='checkbox']").prop("disabled", false);
					$(".checkbox a").addClass("a_hidden");
				} else if (_n > 0) {
					$(".wrap_contain").removeClass("edit");
					$("input[type='checkbox']").prop("disabled", true);
					$(".checkbox a").removeClass("a_hidden");
					All_noselect();
				}
			}
		});
		//编辑状态下点击列表
		$(document).on("click", ".checkbox a", function(e) {
			e.stopPropagation();
		})
		$(document)
				.on(
						"click",
						".checkbox",
						function(e) {
							var _n = $(".edit").length;
							var inp = $(this).find("input")[0];
							if (_n > 0) {
								if (inp.checked) {
									$(this).find("i").first().removeClass(
											"checkbox_select");
									$(".contain_hd_L").find("i").removeClass(
											"checkbox_select");
									inp.checked = false;
								} else {
									$(this).find("i").first().addClass(
											"checkbox_select");
									inp.checked = true;
									if ($("input[type='checkbox']").length == $("input[type='checkbox']:checked").length) {
										$(".contain_hd_L").find("i").addClass(
												"checkbox_select");
									}
								}
							}
							return false;
						})
		//全选操作
		$(document).on("click", ".contain_hd_L", function(e) {
			var _n = $(".edit").length;
			if (_n > 0) {
				var a = $(".contain_hd_L").find(".checkbox_select").length;
				if (a == 0) {
					Aselect();
				} else {
					All_noselect();
				}

			}
		});
		//全选
		function Aselect() {
			$("label.checkbox input").each(function() {
				this.checked = true;
				$(this).prev("i").addClass("checkbox_select");
				$(".contain_hd_L").find("i").addClass("checkbox_select");
			});
		}
		//全不选
		function All_noselect() {
			$("label.checkbox input").each(function() {
				this.checked = false;
				$(this).prev("i").removeClass("checkbox_select");
				$(".contain_hd_L").find("i").removeClass("checkbox_select");
			});
		};
	</script>
</body>
</html>