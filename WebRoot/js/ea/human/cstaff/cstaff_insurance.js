$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	parent.document.getElementById("mainframe11").style.height = 180 + len * 27 + "px";
	
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	
	$('.insurance').flexigrid({
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
							"insurancemap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				var heis = parent.document.getElementById("mainframe11").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe11").style.height = heis;
				select++;
				break;

			case '修改' :
				if (insuranceID == '') {
					alert("请选择！");
					return;
				}
				if ($("#" + insuranceID + " #status").attr("value") == '01') {
					alert("不可修改");
					break;
				}
				$p = $("tr#" + insuranceID);
				if ($p.hasClass("check")) {
					return;
				}
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"insurancemap[" + select + "]." + this.name);
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
				if (notoken)
					return;
				if (select == 1) {
					return;
				}
				notoken = 1;
				var re = 0;
				$("#startTime", $(".check")).each(function(i, tmp) {
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

				$f = $('#insuranceForm');
				$('#insuranceForm')
						.attr("target", "hidden")
						.attr(
								"action",
								pbasePath
										+ "ea/insurance/ea_saveInsurance.jspa");
				document.insuranceForm.submit.click();
				token = 2;
				break;
			case '删除' :
				if (insuranceID == '') {
					alert("请选择！");
					return;
				}
				if ($("#" + insuranceID + " #status").attr("value") == '01') {
					alert("不可删除");
					break;
				}
				if (insuranceID.substring(0, 2) == 'sa') {
						$("#" + insuranceID).remove();
						insuranceID = "";
						var heis = parent.document.getElementById("mainframe11").offsetHeight - 27 + "px";
						parent.document.getElementById("mainframe11").style.height = heis;
					return;
				}
				$f = $('#insuranceForm');
					if (notoken)
						return;
					notoken = 1;
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									pbasePath
											+ "ea/insurance/ea_delInsurance.jspa?insurance.staffID=" 
											+ pstaffID
											+ "&insurance.insuranceID="
											+ insuranceID);
					document.insuranceForm.submit.click();
					$("tr#" + insuranceID).remove();
					insuranceID = '';
					token = 11;
				break;
		}
	}
	$(".images_list").click(function() {
		$p = $(this).parent().parent();
		insuranceID = $p.find("td:has(:input[name=insuranceID])")
				.children("input[name=insuranceID]").val();
		document.location.href = pbasePath
				+ "ea/uploadfile/ea_getListImage.jspa?loadFile.parmeterID="
				+ insuranceID + "&loadFile.staffID=" + staffID;
	});

	$(".insurance tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		insuranceID = this.id;
	});
	
	$(".insurance tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		insuranceID = this.id;
		action("修改");
	});
	
	$(".insurance").find("select[id!=xxx]").each(function() {
		$s = $(this).hide();
		$o = $("<span/>").text($s.find("option:selected").text());
		$o.insertAfter($s);
	});
});

function re_load() {
	if (token)
		document.location.href = pbasePath
				+ "/ea/insurance/ea_getListInsurance.jspa?insurance.staffID="
				+ pstaffID;
}