$(document)
		.ready(
				function() {
					$("#target").attr("value", $("#new_content").html());
					$("#prompt").css("position", "absolute").css("top",
							$(window).height() * 0.09 + "px");
					$("#prompt").find("div").css("height",
							$(window).height() * 0.06 + "px").css("font-size",
							$(window).height() * 0.0285 + "px").css("color",
							"#FFFFFF");
					$("#prompt").find("div").css("-moz-border-radius",
							$(window).height() * 0.015 + "px").css(
							"-webkit-border-radius",
							$(window).height() * 0.015 + "px");
				});
// form表单提交
function save() {
	if ($("#crop_img").attr("src") != "") {
		if ($.trim($("#text").val()) != "") {
			if ($.trim($("#target").val().replace(/&nbsp;/g, "")) != ""
					&& $("#target").val() != '<p style="color:#9d9d9d">请输入正文</p>') {
					$("#dosubmit").click();
			} else {
				prompt("请输入正文!!!");
			}
		} else {
			prompt("请输入标题!!!");
		}
	} else {
		prompt("请选择新闻主图!!!");
	}
}
// 返回
function back() {
	$("#preview").toggle();
}
// 新闻预览
function preview() {
	if ($("#crop_img").attr("src") != "") {
		if ($.trim($("#text").val()) != "") {
			if ($.trim($("#target").val().replace(/&nbsp;/g, "")) != ""
					&& $("#target").val() != '<p style="color:#9d9d9d">请输入正文</p>') {
				$("#preview").toggle();
				$(".title").html($("#text").val());
				var date = new Date();
				var seperator1 = "-";
				var year = date.getFullYear();
				var month = date.getMonth() + 1;
				var strDate = date.getDate();
				if (month >= 1 && month <= 9) {
					month = "0" + month;
				}
				if (strDate >= 0 && strDate <= 9) {
					strDate = "0" + strDate;
				}
				var currentdate = year + seperator1 + month + seperator1
						+ strDate;
				$(".time").html(currentdate);
				$("#display").html($("#target").val());
			} else {
				prompt("请输入正文!!!");
			}
		} else {
			prompt("请输入标题!!!");
		}
	} else {
		prompt("请选择新闻主图!!!");
	}
}
// 提示
function prompt(obj) {
	if ($("#prompt").css("display") != "none")
		return;
	$("#prompt").find("span").text(obj);
	$("#prompt").fadeIn(500);
	setTimeout(function() {
		$("#prompt").fadeOut(500);
		$("#prompt").find("span").text("");
	}, 2000);
}