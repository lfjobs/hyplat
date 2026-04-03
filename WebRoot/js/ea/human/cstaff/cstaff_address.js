$(document)
		.ready(
				function() {
					var len = $("#tbwid").find(".trclass").length;
					window.parent.document.getElementById("mainframe2").style.height = 180
							+ len * 27 + "px";

					$('.address').flexigrid({
						height : 'auto',
						allDouble : true,
						width : 'auto',
						minwidth : 30,
						minheight : 80,
						buttons : [ {
							name : '添加',
							bclass : 'add',
							onpress : action
						// 当点击调用方法
						}, {
							separator : true
						}, {
							name : '修改',
							bclass : 'edit',
							onpress : action
						// 当点击调用方法
						}, {
							separator : true
						}, {
							name : '删除',
							bclass : 'delete',
							onpress : action
						// 当点击调用方法
						}, {
							separator : true
						}, {
							name : '全部保存',
							bclass : 'add',
							onpress : action
						// 当点击调用方法
						}, {
							separator : true
						} ]
					});
					function action(com, grid) {
						switch (com) {
						case '添加':
							$("#sa").after(
									$("#sa").clone(true).attr("id",
											"sa" + select).addClass("check"));
							$("#sa" + select).find(':input:gt(0)').each(
									function() {
										$(this).attr(
												"name",
												"addressmap[" + select + "]."
														+ this.name);
									});
							$("#sa" + select).show();
							var heis = parent.document
									.getElementById("mainframe2").offsetHeight
									+ 27 + "px";
							parent.document.getElementById("mainframe2").style.height = heis;
							select++;
							break;
						case '修改':
							if (addressID == '') {
								alert("请选择！");
								return;
							}
							$p = $("tr#" + addressID);
							if ($p.hasClass("check")) {
								return;
							}
							$p.addClass("check");
							$p.find(':input:gt(0)').each(
									function() {
										$(this).attr(
												"name",
												"addressmap[" + select + "]."
														+ this.name);
									});

							select++;
							$p.find("span").addClass("model1");
							$p.find("input").removeClass("model1");
							$p.find("s:select").attr("disabled", false);
							$p.find("select").show();
							$(this).parent().children("span").show();
							break;
						case '全部保存':
							if (notoken) {
								return;
							}
							if (select == 1) {
								return;
							}
							notoken = 1;
							var re = 0;
							$("#livestartDate", $(".check")).each(
									function(i, tmp) {
										if (this.value == "") {
											alert("请输入日期");
											$(this).css("background-color",
													"red");
											re = 1;
										}
									});

							var er = 0;
							$("#addressDetailed", $(".check")).each(
									function(i, tmp) {
										if (this.value == "") {
											alert("请输入您的具体地址");
											$(this).css("background-color",
													"red");
											er = 1;
										}
									});

							if (re == 1 || er == 1) {
								notoken = 0;
								return;
							}/*
								 * if (er) { notoken = 0; return; }
								 */
							$('#addressForm')
									.attr("target", "hidden")
									.attr(
											"action",
											basePath
													+ "ea/csaddress/t_ea_saveAddress.jspa?address.staffID="
													+ staffID);
							document.addressForm.submit.click();
							token = 2;
							break;

						case '删除':
							if (addressID == '') {
								alert("请选择！");
								return;
							}
							if (addressID.substring(0, 2) == "sa") {
								$("#" + addressID).remove();
								addressID = "";
								var heis = parent.document
										.getElementById("mainframe2").offsetHeight
										- 27 + "px";
								parent.document.getElementById("mainframe2").style.height = heis;
								return;
							}
							$f = $('#addressForm');
							if (notoken)
								return;
							notoken = 1;
							$f
									.attr("target", "hidden")
									.attr(
											"action",
											basePath
													+ "ea/csaddress/ea_delAddress.jspa?address.staffID="
													+ staffID
													+ "&address.addressID="
													+ addressID);
							document.addressForm.submit.click();
							$("tr#" + addressID).remove();
							addressID = "";
							token = 11;
							break;
						}
					}

					$(".address tr[id]").click(
							function() {
								$("input.JQuerypersonvalue", $(this)).attr(
										"checked", "checked");
								addressID = this.id;
							});

					$(".address tr[id]").dblclick(
							function() {
								$("input.JQuerypersonvalue", $(this)).attr(
										"checked", "checked");
								addressID = this.id;
								action("修改");
							});
					$(".xxdz").blur(function() {
						if ($(this).val().length > 50) {
							alert("您输入的字数超出范围 ");
						  $(this).val("");
						}
					});
					$(".address").find("select[id!=xxx]").each(function() {
						$s = $(this).hide();
					});
				});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/csaddress/ea_getListAddress.jspa?address.staffID="
				+ staffID;
}
/*function checklength(obj) {
	alert($(this).value)
	if (obj.value.length > 11) {
		obj.value = obj.value.substring(0, 11);
		alert("您输入的字数超出范围 ");
	}
	obj.blur();
}*/