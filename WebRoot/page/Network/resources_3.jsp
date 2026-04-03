<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>添加粉丝人脉</title>

	<link href="<%=basePath%>css/contacts/Network/style12.css"
		rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>css/contacts/jqModal_blue.css"
		rel="stylesheet" type="text/css" />
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
</head>
<body class="bgcolorFFF">
	<div class="wfj12_003">
		<c:if test='${showType!="notitle"}'>
			<div class="wfj_top">
				<ul>
					<li><a href="javascript:;" target="_self"><img
							src="<%=basePath%>images/contacts/wfj_return_01.png" /> </a></li>
					<li>添加人脉</li>
					<li><a href="javascript:;"><img
							src="<%=basePath%>images/contacts/iconfont-fenxiang.png" /> </a></li>
				</ul>
			</div>
		</c:if>
		<div class="wfj12_003_title">
			<div>
				<ul>
					<li>个人粉丝</li>
				</ul>
			</div>
			<div>
				<ul>
					<li>公司粉丝</li>
				</ul>
			</div>
		</div>
		<div class="wfj12_003_content">
			<div class="wfj12_003_hidden">
				<div class="wfj12_003_person">
					<div class="wfj12_003_icon">
						<div>
							<img src="<%=basePath%>images/contacts/wfj_huiyuan_01.png" />
						</div>
					</div>
					<div class="wfj12_003_con">
						<div class="wfj12_003_width">
							<form action="" id="from1" method="post">
								<input type="submit" style="display:none;" value="" id="sub" />
								<table width="100%">
									<tr>
										<td width="15%"><img
											src="<%=basePath%>images/contacts/wfj_all_Profile_01.png" />
										</td>
										<td width="65%"><input type="hidden" name="pid"
											value="${pid}" /> <input type="hidden" name="showType"
											value="${showType}" /> <input type="text"
											name="astaff.staffName" value="请输入姓名"
											onfocus="if(this.value=='请输入姓名'){this.value='';}"
											onblur="if(this.value==''){this.value='请输入姓名';}" /></td>
										<td width="20%">
											<div id="ssex">
												<ul>
													<li class="selected">男</li>
													<li>女</li>
												</ul>
												<input type="hidden" name="astaff.sex" id="grsex" />
											</div>
										</td>
									</tr>

									<tr>
										<td><img
											src="<%=basePath%>images/contacts/wfj_shop_info_03.png" /></td>
										<td colspan="2"><input type="text"
											name="astaff.industryType" value="请选择行业" class="paytype"
											onfocus="if(this.value=='请选择行业'){this.value='';}"
											onblur="if(this.value==''){this.value='请选择行业';}" /></td>

										</td>
									</tr>
									<tr>
										<td><img
											src="<%=basePath%>images/contacts/wfj_all_Profile_03.png" />
										</td>
										<td colspan="2"><input type="text"
											name="astaff.staffAddress" value="请输入地址"
											onfocus="if(this.value=='请输入地址'){this.value='';}"
											onblur="if(this.value==''){this.value='请输入地址';}" /></td>
									</tr>
									<tr>
										<td><img
											src="<%=basePath%>images/contacts/wfj_all_Profile_06.png" />
										</td>

										<td colspan="2"><input type="text"
											name="astaff.reference" id="phonegeren" value="请输入电话号码"
											onfocus="if(this.value=='请输入电话号码'){this.value='';}"
											onblur="if(this.value==''){this.value='请输入电话号码';}" /></td>
									</tr>
									<tr>
										<td><img
											src="<%=basePath%>images/contacts/wfj_all_Profile_07.png" />
										</td>
										<td colspan="2"><input type="text" name="astaff.weixin"
											value="请输入微信号"
											onfocus="if(this.value=='请输入微信号'){this.value='';}"
											onblur="if(this.value==''){this.value='请输入微信号';}" /></td>
									</tr>
									<tr>
										<td></td>
										<td colspan="2" class="yzm"><div class="left">
												<input id="yzmgr" value="请输入手机验证码" type="text"
													onfocus="if(this.value=='请输入手机验证码'){this.value='';}"
													onblur="if(this.value==''){this.value='请输入手机验证码';yanz('yzmgr');}" />
											</div>
											<div class="right changetime" id="getyzm"
												onclick="duanxin('phonegeren')">获取验证码</div></td>
									</tr>
									<tr>
										<td colspan="3" align="center"><div id="commit">提交保存</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
				<div class="wfj12_003_company" style="display:none;">
					<div class="wfj12_003_con">
						<div class="wfj12_003_width">
							<form action="" id="from2" method="post">
								<input type="submit" style="display:none;" value="" id="sub2" />
								<table width="100%">
									<tr>
										<input type="hidden" name="pid" value="${pid}" />
										<td width="15%"><img
											src="<%=basePath%>images/contacts/wfj_shop_info_02.png" /></td>
										<td colspan="2"><input type="text"
											name="contact.companyName" value="请输入公司名称"
											onfocus="if(this.value=='请输入公司名称'){this.value='';}"
											onblur="if(this.value==''){this.value='请输入公司名称';}" /></td>
									</tr>
									<tr>
										<td><img
											src="<%=basePath%>images/contacts/wfj_shop_info_03.png" /></td>
										<td colspan="2"><input type="text" id="idd"
											name="contact.industryType" value="请选择行业" class="paytype"
											onfocus="if(this.value=='请选择行业'){this.value='';}"
											onblur="if(this.value==''){this.value='请选择行业';}" /></td>

										<%--   <s:select list="%{#request.typelist}" id="industryType" 
										listKey="codeValue" listValue="codeValue"
										name="contact.industryType" theme="simple"></s:select> --%>

										</td>
									</tr>
									<tr>
										<td><img
											src="<%=basePath%>images/contacts/wfj_shop_info_04.png" /></td>
										<td colspan="2"><input type="text" value="公司地址"
											name="contact.companyAddr"
											onfocus="if(this.value=='公司地址'){this.value='';}"
											onblur="if(this.value==''){this.value='公司地址';}" /></td>
									</tr>
									<tr>
										<td><img
											src="<%=basePath%>images/contacts/wfj_all_Profile_01.png" />
										</td>
										<td><input type="text" value="请输入公司负责人"
											name="contact.cresponsible"
											onfocus="if(this.value=='请输入公司负责人'){this.value='';}"
											onblur="if(this.value==''){this.value='请输入公司负责人';}" /></td>
										<td width="20%">
											<div id="sexs">
												<ul>
													<li class="selected">男</li>
													<li>女</li>
												</ul>
												<input type="hidden" name="contact.sex" id="gssex" />
											</div>
										</td>
									</tr>
									<tr>
										<td><img
											src="<%=basePath%>images/contacts/wfj_all_Profile_06.png" />
										</td>
										<td colspan="2"><input type="text" id="phonegs"
											value="请输入手机号码" name="contact.responsibleTel"
											onfocus="if(this.value=='请输入手机号码'){this.value='';}"
											onblur="if(this.value==''){this.value='请输入手机号码';}" /></td>
									</tr>

									<tr>
										<td></td>
										<td colspan="2" class="yzm"><div class="left">
												<input id="yzmgs" value="请输入手机验证码" type="text"
													onfocus="if(this.value=='请输入手机验证码'){this.value='';}"
													onblur="if(this.value==''){this.value='请输入手机验证码';yanz('yzmgs');}" />
											</div>
											<div class="right changetime" id="getyzm"
												onclick="duanxin('phonegs')">获取验证码</div></td>
									</tr>
									<tr>
										<td colspan="3" align="center"><div id="commit1">提交保存</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>


	<div class="wfj12_006_float">
		<div class="wfj12_006_content">
			<div style="width:100%;">
				<div class="wfj12_006_width" style="width:100%;margin:0 auto;">
					<div id="indus" class="indus" style="width:80%;margin:0 auto;"></div>
				</div>
			</div>
		</div>
	</div>




	<script type="text/javascript">
   var basePath='<%=basePath%>';
		var obj;
		getIndustryItem();
		var i;
		var c = 0;
		var d = 1;
		var q = 0;
		var l = 0;
		var times = 59;

		function yanz(id) {

			if (q == 0) {
				alert("请先获取验证码");

				return;
			}
			if ($("#" + id).val().length < 6) {
				alert("验证码不能小于六位数");
				l = 6;
				return;
			}

			if ($("#" + id).val() == '') {

				alert("请填写验证码");
				c = 1;
				return;

			}

			if ($("#" + id).val() != i) {

				alert("验证码不正确");
				c = 1;
				return;
			}

			else {

				c = 0;
			}

		}

		function isshouji(id) {
			var count = $("#" + id).val();
			$.ajax({
				cache : true,
				type : "POST",
				url : basePath + "/ea/met/sajax_ea_ajaxIsFans.jspa",
				async : false,
				dataType : "json",
				data : {
					gsrtype : id,
					phone : count

				},
				success : function(data) {

					var member = eval("(" + data + ")");

					if (member.result == true) {
						d = 1;
					} else {

						//已注册
						d = 2;
					}

				},
				error : function(data) {
					alert(data);
				}

			});

			return d;
		}

		function duanxin(id) {

			var count = $("#" + id).val();

			if (times < 59) {

				alert("请稍后");
				return;
			}
			if (count == "") {
				alert("手机号为空");
				return;
			}
			var reg = /^\d{11}$/;

			if (!reg.test(count)) {
				alert("手机号码输入错误");
				return;
			}
			if (isshouji(id) == 2) {
				alert("已被注册");
				return;
			}

			update(times, $("#" + id).parents("table").find(".changetime"));
			q = 1;

			$.ajax({
				cache : true,
				type : "POST",
				url : basePath + "/ea/android/sajax_ea_getduanxin.jspa?pahe="
						+ count + "&yanz=1",

				async : false,
				dataType : "json",
				success : function(data) {

					var member = eval("(" + data + ")");

					i = member.returna;
					alert(i);
				}

			});

		}
		function update(num, obj) {
			if (num > 0) {
				$(obj).text("已发送（" + num + "）");
				$(obj).css("cursor", "not-allowed");
				$(obj).css("backgroundColor", "#999");
				$(obj).css("color", "#FFF");
				times = num;
				setTimeout(function() {
					update(num - 1, obj);
				}, 1000);

			} else {
				$(obj).css("cursor", "pointer");
				$(obj).css("backgroundColor", "#F74C31");
				$(obj).css("color", "#FFF");
				$(obj).text("获取验证码");

				times = 59;

			}
		}

		function getIndusValue(indusvalue) {

			$(obj).val(indusvalue);
			$(".wfj12_006_float").fadeOut();
			$("#occlusion2").jqmHide();

		}

		function getIndustryItem() {
			var getListCCodeurl = basePath
					+ "ea/met/sajax_ea_ajaxIndustryList.jspa?codeID=scode20150815wygb79q82p0000000005&date="
					+ new Date().toLocaleString();
			$
					.ajax({
						url : encodeURI(getListCCodeurl),
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var codeList = member.codeList;

							if (codeList.length == 0) {

								return;
							}
							var tag = new Array();
							tag
									.push("<span style='display:-moz-inline-box;display:inline-block;width:10%;float:left;height:1px;'>");
							tag.push("</span>");
							tag.push("<span style='width:90%;float:left;'>");
							tag.push("<ul>");

							var s = "font-size:"
									+ $(window).height()
									* 0.025
									+ "px;height:"
									+ $(window).height()
									* 0.05
									+ "px;line-height:"
									+ $(window).height()
									* 0.05
									+ "px;z-index:100;float:left;width:50%;text-align:left;"
							for ( var i = 0; i < codeList.length; i++) {

								tag
										.push("<li  style='"+s+"'><a style='cursor:pointer;' onclick=\"getIndusValue('"
												+ codeList[i].codeValue
												+ "')\">"
												+ codeList[i].codeValue
												+ "</a></li>");

							}

							tag.push("</ul>");
							tag.push("</span>");
							$("#indus").html(tag.join(""));

						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});

		}

		$(document)
				.ready(
						function(e) {

							//弹出层初始化
							$(".jqmWindow").jqm({
								modal : true,
								overlay : 20
							}).jqmAddClose('.close');

							$(".jqmOverlay").live("click", function() {
								$(".wfj12_006_float").fadeOut();
								$("#occlusion2").jqmHide();
							});

							//提交保存按钮《公司的》
							$("#commit1")
									.click(
											function() {
												yanz("yzmgs");
												if (q == 0 || l == 6 || c == 1) {

													return;
												}

												$("#gssex").val(
														$("#sexs").find(
																".selected")
																.text());
												$("#from2")
														.attr(
																"action",
																basePath
																		+ "ea/met/mea_addUnit.jspa");
												$("#sub2").click();
											});

							//提交保存按钮<个人的>
							$("#commit")
									.click(
											function() {
												yanz("yzmgr");
												if (q == 0 || l == 6 || c == 1) {

													return;
												}
												$("#grsex").val(
														$("#ssex").find(
																".selected")
																.text());
												$("#from1")
														.attr(
																"action",
																basePath
																		+ "ea/met/mea_addPersonal.jspa");
												$("#sub").click();
											});

							$(".paytype")
									.click(
											function() {
												obj = $(this);

												$("#occlusion2").css(
														"z-index",
														$(".wfj12_006").css(
																"z-index") + 1);
												$("#occlusion2").jqmShow();
												$(".wfj12_006_float").css(
														"z-index",
														$("#occlusion2").css(
																"z-index") + 1);
												$(".wfj12_006_float").fadeIn(
														1000);
											});

							$("body").css("width", $(window).width());
							$("body").css("height", $(window).height());
							$(".wfj12_006_float").attr(
									"style",
									"height:" + $(window).height() * 0.7
											+ "px;margin-top:0px;");
							$(".wfj12_006_float").find("ul").attr(
									"style",
									"height:" + $(window).height() * 0.06
											+ "px;line-height:"
											+ $(window).height() * 0.06
											+ "px; border-bottom:"
											+ $(window).height() * 0.005
											+ "px solid #F0F0F0;");
							$(".wfj12_006_float").find("ul").find("li").attr(
									"style",
									"font-size:" + $(window).height() * 0.02
											+ "px;");
							$(".wfj12_006_float").find("ul").find("img").attr(
									"style",
									"height:" + $(window).height() * 0.03
											+ "px;");
							$(".wfj12_006_content").attr(
									"style",
									"width:" + $(".wfj12_006_float").width()
											+ "px;height:"
											+ $(".wfj12_006_float").height()
											+ "px;");
							$(".indus").css("marginTop",
									$(window).height() * 0.05 + "px");

							$(".wfj_top")
									.attr(
											"style",
											"height:" + $(window).height()
													* 0.06 + "px;line-height:"
													+ $(window).height() * 0.06
													+ "px;");
							$(".wfj_top").find("li")
									.attr("style", "width:15%;");
							$(".wfj_top").find("li").find("img").attr(
									"style",
									"height:" + $(window).height() * 0.03
											+ "px;");
							$(".wfj_top").find("li").eq(1).attr(
									"style",
									"width:70%;font-size:" + $(window).height()
											* 0.025 + "px;");
							$(".wfj12_003_title")
									.attr(
											"style",
											"height:" + $(window).height()
													* 0.06 + "px;line-height:"
													+ $(window).height() * 0.05
													+ "px;");
							$(".wfj12_003_title").find("div").attr(
									"style",
									" border-bottom:" + $(window).height()
											* 0.005 + "px solid #E0E0E0;")
							$(".wfj12_003_title")
									.find("div")
									.eq(0)
									.find("li")
									.attr(
											"style",
											" border-bottom:"
													+ $(window).height()
													* 0.005
													+ "px solid #DA2116;font-size:"
													+ $(window).height()
													* 0.025 + "px;")
							$(".wfj12_003_title")
									.find("div")
									.eq(1)
									.find("li")
									.attr(
											"style",
											" border-bottom:"
													+ $(window).height()
													* 0.005
													+ "px solid #E0E0E0;font-size:"
													+ $(window).height()
													* 0.025 + "px;")
							$(".wfj12_003_icon").attr(
									"style",
									"  margin-top:" + $(window).height() * 0.04
											+ "px;");
							$(".wfj12_003_con")
									.find("input")
									.attr(
											"style",
											"height:"
													+ $(window).height()
													* 0.05
													+ "px;font-size:"
													+ $(window).height()
													* 0.02
													+ "px;color:#000;background-color:#F2F2F2;border:none;");
							$(".wfj12_003_con").find("td")
									.attr(
											"style",
											"height:" + $(window).height()
													* 0.08 + "px;line-height:"
													+ $(window).height() * 0.08
													+ "px;");
							$(".wfj12_003_con").find("div").find("div")
									.attr(
											"style",
											"font-size:" + $(window).height()
													* 0.02 + "px;height:"
													+ $(window).height() * 0.05
													+ "px;line-height:"
													+ $(window).height() * 0.05
													+ "px;");
							$("#ssex").attr(
									"style",
									"height:" + $(window).height() * 0.04
											+ "px;line-height:"
											+ $(window).height() * 0.04
											+ "px; border-radius:"
											+ $(window).height() * 0.04
											+ "px;background-color:#F2F2F2;");
							$("#ssex")
									.find("li")
									.eq(0)
									.attr(
											"style",
											"font-size:"
													+ $(window).height()
													* 0.025
													+ "px; background-color:#1C7DC5;border-radius:"
													+ $(window).height() * 0.04
													+ "px;color:#FFF;");
							$("#ssex").find("li").eq(1).attr(
									"style",
									"font-size:" + $(window).height() * 0.025
											+ "px; border-radius:"
											+ $(window).height() * 0.04
											+ "px;color:#FFF;");

							$("#sexs").attr(
									"style",
									"height:" + $(window).height() * 0.04
											+ "px;line-height:"
											+ $(window).height() * 0.04
											+ "px; border-radius:"
											+ $(window).height() * 0.04
											+ "px;background-color:#F2F2F2;");
							$("#sexs")
									.find("li")
									.eq(0)
									.attr(
											"style",
											"font-size:"
													+ $(window).height()
													* 0.025
													+ "px; background-color:#1C7DC5;border-radius:"
													+ $(window).height() * 0.04
													+ "px;color:#FFF;");
							$("#sexs").find("li").eq(1).attr(
									"style",
									"font-size:" + $(window).height() * 0.025
											+ "px; border-radius:"
											+ $(window).height() * 0.04
											+ "px;color:#FFF;");

							$("#ssex").find("li").click(
									function() {
										$("#ssex").find(".selected")
												.removeClass("selected");
										if ($(this).text() == "男") {
											//alert($(this).find("li").text()=="ä¸ªäºº")
											$("#ssex").find("li").css(
													"backgroundColor",
													"#F0F0F0");
											$(this).css("backgroundColor",
													"#1C7DC5");

										} else {
											$("#ssex").find("li").css(
													"backgroundColor",
													"#F0F0F0");
											$(this).css("backgroundColor",
													"#F64C32");
										}
										$(this).addClass("selected");

									});

							$("#sexs").find("li").click(
									function() {
										$("#sexs").find(".selected")
												.removeClass("selected");
										if ($(this).text() == "男") {
											//alert($(this).find("li").text()=="ä¸ªäºº")
											$("#sexs").find("li").css(
													"backgroundColor",
													"#F0F0F0");
											$(this).css("backgroundColor",
													"#1C7DC5");
										} else {
											$("#sexs").find("li").css(
													"backgroundColor",
													"#F0F0F0");
											$(this).css("backgroundColor",
													"#F64C32");
										}
										$(this).addClass("selected");
									});

							$(".wfj12_003_content").css("width",
									$(window).width() + "px");
							$(".wfj12_003_content")
									.attr(
											"style",
											"width:"
													+ $(window).width()
													+ "px;height:"
													+ parseInt($(window)
															.height()
															- $(".wfj_top")
																	.height()
															- $(
																	".wfj12_003_title")
																	.height()
															- $(window)
																	.height()
															* 0.01)
													+ "px;overflow:hidden;margin-top:"
													+ $(window).height() * 0.01
													+ "px;");
							$(".wfj12_003_hidden").attr(
									"style",
									"height:"
											+ $(".wfj12_003_content").height()
											+ "px;overflow:auto;");

							var h1 = $(".wfj12_003_icon").height()
									+ $(".wfj12_003_con").height()
									+ $(window).height() * 0.08;
							var h2 = $(".wfj12_003_company").height()
									+ $(window).height() * 0.03;

							if (h1 > parseInt($(".wfj12_003_content").height())) {
								$(".wfj12_003_content")
										.attr(
												"style",
												"width:"
														+ $(window).width()
														+ "px;height:"
														+ parseInt($(window)
																.height()
																- $(".wfj_top")
																		.height()
																- $(
																		".wfj12_003_title")
																		.height())
														+ "px;overflow:hidden;margin-top:"
														+ $(window).height()
														* 0.01 + "px;");
								$(".wfj12_003_hidden").attr(
										"style",
										"height:"
												+ $(".wfj12_003_content")
														.height()
												+ "px;width:"
												+ parseInt($(
														".wfj12_003_content")
														.width() + 17)
												+ "px;overflow:auto;");
							} else {
								$(".wfj12_003_content")
										.attr(
												"style",
												"width:"
														+ $(window).width()
														+ "px;height:"
														+ parseInt($(window)
																.height()
																- $(".wfj_top")
																		.height()
																- $(
																		".wfj12_003_title")
																		.height())
														+ "px;overflow:hidden;margin-top:"
														+ $(window).height()
														* 0.01 + "px;");
								$(".wfj12_003_hidden").attr(
										"style",
										"height:"
												+ $(".wfj12_003_content")
														.height()
												+ "px;width:"
												+ parseInt($(
														".wfj12_003_content")
														.width())
												+ "px;overflow:auto;");
							}

							$(".wfj12_003_title")
									.find("div")
									.click(
											function() {
												if ($(this).find("li").text() == "个人粉丝") {

													$(".wfj12_003_title")
															.find("div")
															.find("li")
															.attr(
																	"style",
																	"border-bottom:"
																			+ $(
																					window)
																					.height()
																			* 0.005
																			+ "px solid #E0E0E0;font-size:"
																			+ $(
																					window)
																					.height()
																			* 0.025
																			+ "px;");
													$(this)
															.find("li")
															.attr(
																	"style",
																	"border-bottom:"
																			+ $(
																					window)
																					.height()
																			* 0.005
																			+ "px solid #DA2116;font-size:"
																			+ $(
																					window)
																					.height()
																			* 0.025
																			+ "px;");
													$(".wfj12_003_person")
															.attr("style",
																	"display:block;");
													$(".wfj12_003_company")
															.attr("style",
																	"display:none;");

													if (h1 > parseInt($(
															".wfj12_003_content")
															.height())) {
														$(".wfj12_003_content")
																.attr(
																		"style",
																		"width:"
																				+ $(
																						window)
																						.width()
																				+ "px;height:"
																				+ parseInt($(
																						window)
																						.height()
																						- $(
																								".wfj_top")
																								.height()
																						- $(
																								".wfj12_003_title")
																								.height())
																				+ "px;overflow:hidden;");
														$(".wfj12_003_hidden")
																.attr(
																		"style",
																		"height:"
																				+ $(
																						".wfj12_003_content")
																						.height()
																				+ "px;width:"
																				+ parseInt($(
																						".wfj12_003_content")
																						.width() + 17)
																				+ "px;overflow:auto;");
													} else {
														$(".wfj12_003_content")
																.attr(
																		"style",
																		"width:"
																				+ $(
																						window)
																						.width()
																				+ "px;height:"
																				+ parseInt($(
																						window)
																						.height()
																						- $(
																								".wfj_top")
																								.height()
																						- $(
																								".wfj12_003_title")
																								.height())
																				+ "px;overflow:hidden;");
														$(".wfj12_003_hidden")
																.attr(
																		"style",
																		"height:"
																				+ $(
																						".wfj12_003_content")
																						.height()
																				+ "px;width:"
																				+ parseInt($(
																						".wfj12_003_content")
																						.width())
																				+ "px;overflow:auto;");
													}
												} else {
													$(".wfj12_003_title")
															.find("div")
															.find("li")
															.attr(
																	"style",
																	"border-bottom:"
																			+ $(
																					window)
																					.height()
																			* 0.005
																			+ "px solid #E0E0E0;font-size:"
																			+ $(
																					window)
																					.height()
																			* 0.025
																			+ "px;");
													$(".wfj12_003_person")
															.attr("style",
																	"display:none;");
													$(".wfj12_003_company")
															.attr(
																	"style",
																	"clear:both;width:100%;float:left;display:block;margin-top:"
																			+ $(
																					window)
																					.height()
																			* 0.03
																			+ "px;");
													$(this)
															.find("li")
															.attr(
																	"style",
																	"border-bottom:"
																			+ $(
																					window)
																					.height()
																			* 0.005
																			+ "px solid #DA2116;font-size:"
																			+ $(
																					window)
																					.height()
																			* 0.025
																			+ "px;");

													if (h2 > parseInt($(
															".wfj12_003_content")
															.height())) {
														$(".wfj12_003_content")
																.attr(
																		"style",
																		"width:"
																				+ $(
																						window)
																						.width()
																				+ "px;height:"
																				+ parseInt($(
																						window)
																						.height()
																						- $(
																								".wfj_top")
																								.height()
																						- $(
																								".wfj12_003_title")
																								.height()
																						- $(
																								window)
																								.height()
																						* 0.01)
																				+ "px;overflow:hidden;");
														$(".wfj12_003_hidden")
																.attr(
																		"style",
																		"height:"
																				+ $(
																						".wfj12_003_content")
																						.height()
																				+ "px;width:"
																				+ parseInt($(
																						".wfj12_003_content")
																						.width() + 17)
																				+ "px;overflow:auto;");
													} else {
														$(".wfj12_003_content")
																.attr(
																		"style",
																		"width:"
																				+ $(
																						window)
																						.width()
																				+ "px;height:"
																				+ parseInt($(
																						window)
																						.height()
																						- $(
																								".wfj_top")
																								.height()
																						- $(
																								".wfj12_003_title")
																								.height()
																						- $(
																								window)
																								.height()
																						* 0.01)
																				+ "px;overflow:hidden;");
														$(".wfj12_003_hidden")
																.attr(
																		"style",
																		"height:"
																				+ $(
																						".wfj12_003_content")
																						.height()
																				+ "px;width:"
																				+ parseInt($(
																						".wfj12_003_content")
																						.width())
																				+ "px;overflow:auto;");
													}
												}
											});

						});
	</script>
	</div>
</body>
</html>