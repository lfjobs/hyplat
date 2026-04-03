$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	parent.document.getElementById("mainframe8").style.height = 180 + len * 27 + "px";
	
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	
	$('.political').flexigrid({
				height :'auto',
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
							"politicalmap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				var heis = parent.document.getElementById("mainframe8").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe8").style.height = heis;
				select++;
				break;
			case '修改' :
				if (politicalID == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + politicalID);
				if ($p.hasClass("check")) {
					return;
				}
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"politicalmap[" + select + "]." + this.name);
				});
				select++;
				$p.find("span")
						.addClass("model1");
				$p.find("input").removeClass("model1");
				$p.find("a").removeClass("model1");
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
				$("#joinDate", $(".check")).each(function(i, tmp) {
							if (this.value == "") {
								alert("请输入日期");
								$(this).css("background-color", "red");
								re = 1;
							}
						});
				if (re) {
					notoken = 0;
					return;
				}
				$('#politicalForm')
						.attr("target", "hidden")
						.attr(
								"action",
								pbasePath
										+ "ea/political/ea_savePolitical.jspa");
				document.politicalForm.submit.click();
				token = 2;
				break;

			case '删除' :
				if (politicalID == '') {
					alert("请选择！");
					return;
				}
				if (politicalID.substring(0, 2) == "sa") {
					$("#" + politicalID).remove();
					politicalID = "";
					var heis = parent.document.getElementById("mainframe8").offsetHeight - 27 + "px";
					parent.document.getElementById("mainframe8").style.height = heis;
					return;
				}
				$f = $('#politicalForm');
				if (notoken)
					return;
				notoken = 1;
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									pbasePath
											+ "ea/political/ea_delPolitical.jspa?political.staffID="
											+ politicalstaffID
											+ "&political.politicalID="
											+ politicalID);
					document.politicalForm.submit.click();
					$("tr#" + politicalID).remove();
					politicalID = "";
					token = 11;
				break;
		}
	}
	$(".political tr[id]").click(function() {
			politicalID = $(this).attr('id');
			$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
			});
	$(".political tr[id]").dblclick(function() {
			$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
			politicalID = this.id;
			action("修改");
			});
	$("span.list_changes").click(function() {
		$p = $(this).parent().parent().parent();
		$p.find("select#politicalStatus").attr("name",
				"political.politicalStatus");
		$f = $('#politicalForm');
		$p.find(':input').appendTo($f);
		$f.attr("action", pbasePath + "ea/political/t_ea_savePolitical.jspa");
		document.politicalForm.submit.click();
	});
	$("span.list_remove").click(function() {// 删除点击事件
				$p = $(this).parent().parent().parent();
				$f = $('#politicalForm');
				$p.find(':input').appendTo($f);
				$f.attr("action", pbasePath
								+ "ea/political/t_ea_delPolitical.jspa");
				document.politicalForm.submit.click();
			});

	$(".political").find("select[id!=xxx]").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});
});
function re_load() {
	if (token)
		document.location.href = pbasePath
				+ "ea/political/ea_getListPolitical.jspa?political.staffID="
				+ politicalstaffID;
}