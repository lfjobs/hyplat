$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	parent.document.getElementById("mainframe3").style.height = 180 + len * 27 + "px";
	
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	
	$('.contact').flexigrid({
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
							"contactmap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				var heis = parent.document.getElementById("mainframe3").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe3").style.height = heis;
				select++;
				break;

			case '修改' :
				if (contactID == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + contactID);
				if ($p.hasClass("check")) {
					return;
				}

				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"contactmap[" + select + "]." + this.name);
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
				$("#contactDate", $(".check")).each(function(i, tmp) {
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
				$('#contactForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/contact/ea_saveContact.jspa?contact.staffID="
										+ staffID);
				document.contactForm.submit.click();
				token = 2;
				break;

			case '删除' :
				if (contactID == '') {
					alert("请选择！");
					return;
				}
				if (contactID.substring(0, 2) == "sa") {
					$("#" + contactID).remove();
					contactID = '';
					var heis = parent.document.getElementById("mainframe3").offsetHeight - 27 + "px";
					parent.document.getElementById("mainframe3").style.height = heis;
					return;
				}
				$f = $('#contactForm');
				$f
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/contact/ea_delContact.jspa?contact.staffID="
										+ staffID + "&contact.contactID="
										+ contactID);
				document.contactForm.submit.click();
				$("tr#" + contactID).remove();
				contactID = "";
				token = 11;
				break;
		}
	}

	$(".contact tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		contactID = this.id;
	});
	
	$(".contact tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		contactID = this.id;
		action("修改");
	});

	$(".contact").find("select[id!=xxx]").each(function() {
		$s = $(this).hide();
		$o = $("<span/>").text($s.find("option:selected").text());
		$o.insertAfter($s);
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/contact/ea_getListContact.jspa?contact.staffID="
				+ staffID;
}