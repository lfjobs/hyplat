$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	$('.address').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '微信内容管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
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
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}/*, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}*/, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				microlettermenucontentid = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				$t = $("table#stafftable");
				$t.find("#pic").attr("src", "");
				$("#jqModel").jqmShow();
				document.cstaffForm.reset();
				$t.find("input#microlettermenukey").val(microlettermenukey);
				break;
			case '修改' :
				if (microlettermenucontentid == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + microlettermenucontentid);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($.trim($(this)
									.text()));
						});
				var photo = $p.find("span#microlettermenucontentimageurl").text();
				$t = $("table#stafftable");
				$t.find("#pic").attr("src", "");
				if (photo.length != 0) {
					$t.find("#pic").attr("src", basePath + photo);
				}
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (microlettermenucontentid == "") {
					alert('请选择！');
					return
				}
				$f = $('#cstaffForm');
				$f.find(':input#microlettermenucontentid').val(microlettermenucontentid);
				if (confirm("确定删除？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/microlettercontent/ea_delDtMicroletterMenuContent.jspa?pageNumber="
											+ bpageNumber);
					document.cstaffForm.submit.click();
					$("tr#" + microlettermenucontentid).remove();
					microlettermenucontentid = "";
					token = 11;
				}
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '查看' :
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + microlettermenucontentid);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());
						});
				$("#jqModel").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/microlettercontent/ea_getDtMicroletterMenuContentList.jspa?search="
						+ search+"&dtMicroletterMenu.microlettermenukey="+microlettermenukey;
				numback(url);
				break;
		}
	}
	$("table tr[id]").click(function() {
				microlettermenucontentid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".JQueryreturn").click(function() {
				re_load();
				$("#jqModel").jqmHide();
			});
	/** ********************************************************** */
	$("input#tosave").click(function() {
		$("#cstaffForm")
		.attr("target", "hidden")
		.attr(
				"action",
				basePath
						+ "/ea/microlettercontent/ea_saveDtMicroletterMenuContent.jspa?pageNumber="
						+ bpageNumber);
		document.cstaffForm.submit.click();
		document.cstaffForm.reset();
		if (microlettermenucontentid == "") {
			token = 1;
			return;
		}
		
		token = 2;

	});

	$(".address tr[id]").dblclick(function() {
				action("修改");
			});
	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/microlettercontent/ea_toSearch.jspa?pageNumber="
						+ bpageNumber);
		document.postSearchForm.submit.click();
	});

});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/microlettercontent/ea_getDtMicroletterMenuContentList.jspa?pageNumber="
				+ bpageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&dtMicroletterMenu.microlettermenukey="+microlettermenukey;
}
