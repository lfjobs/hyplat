$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	window.parent.document.getElementById("mainframe56").style.height = 180 + len * 27 + "px";

	$('.address').flexigrid({
				height : 'auto',
				allDouble : true,
				width : 'auto',
				minwidth : 30,
				minheight : 80,
				buttons : [{
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
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"drivingDealCheGuanmap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				var heis = parent.document.getElementById("mainframe56").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe56").style.height = heis;
				select++;
				break;
			case '修改' :
				if (drivingDealCheGuanid == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + drivingDealCheGuanid);
				if ($p.hasClass("check")) {
					return;
				}
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"drivingDealCheGuanmap[" + select + "]." + this.name);
				});

				select++;
				$p.find("span")
						.addClass("model1");
				$p.find("input").removeClass("model1");
				$p.find("select").attr("disabled", false);
				$p.find("select").show();
				$(this).parent().children("span").show();
				break;
			case '全部保存' :
				if (notoken) {
					return;
				}
				if (select == 1) {
					return;
				}
				notoken = 1;
				var re = 0;
				$("#submitCheGuanDate,#checkCheGuanStatesReason", $(".check")).each(function(i, tmp) {
						if (this.value == "") {
							alert("此处为必填项");
							$(this).css("background-color", "red");
							re = 1;
						}
						if (this.value!=null&&this.value.length>30) {
							alert("最多可输入30个字");
							$(this).css("background-color", "red");
							re = 1;
						}
				});
				if (re) {
					notoken = 0;
					return;
				}
				$('#addressForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/cheguan/ea_saveDrivingCheGuan.jspa?drivingDealCheGuan.staffID="
										+ staffID);
				document.addressForm.submit.click();
				token = 2;
				break;

			case '删除' :
				if (drivingDealCheGuanid == '') {
					alert("请选择！");
					return;
				}
				if (drivingDealCheGuanid.substring(0, 2) == "sa") {
					$("#" + drivingDealCheGuanid).remove();
					drivingDealCheGuanid = "";
					var heis = parent.document.getElementById("mainframe56").offsetHeight - 27 + "px";
					parent.document.getElementById("mainframe56").style.height = heis;
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
										+ "ea/cheguan/ea_delDrivingCheGuan.jspa?drivingDealCheGuan.drivingDealCheGuanid="
										+ drivingDealCheGuanid);
				document.addressForm.submit.click();
				$("tr#" + drivingDealCheGuanid).remove();
				drivingDealCheGuanid = "";
				token = 11;
				break;
		}
	}

	$(".address tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		drivingDealCheGuanid = this.id;
	});
	
	$(".address tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		drivingDealCheGuanid = this.id;
		action("修改");
	});
	$(".address").find("select[id!=xxx]").each(function() {
		$s = $(this).hide();
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/cheguan/ea_getListDrivingCheGuan.jspa?drivingDealCheGuan.staffID="
				+ staffID;
}