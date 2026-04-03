$(document).ready(function() {
	//var len = $("#tbwid").find(".trclass").length;137 + len * 27 
	window.parent.document.getElementById("mainframe5").style.height = 256 + "px";
	
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$(".JQueryaddress").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
	$('.registration').flexigrid({
				height : 125,
				width : 'auto',
				minwidth : 30,
				title : '部门机构职责----当前机构：' + ogName,
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
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find('input:gt(0)').each(function() {
					$(this).attr(
							"name",
							"orgdescmap[" + select + "]."
									+ this.name);
				});
				$("#sa" + select).find("#organizationid").attr("value",ogID);
				$("#sa" + select).show();
				select++;
				var heis = parent.document.getElementById("mainframe5").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe5").style.height = heis;
				break;
			case '修改' :
				if (organizationdescid == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + organizationdescid);
				if ($p.hasClass("check")) {
					return;
				}
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr(
							"name",
							"orgdescmap[" + select + "]."
									+ this.name);
				});
				select++;
				$p.find("span")
						.addClass("model1");
				$p.find("input").removeClass("model1");
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
				$("input[name$='datework']", $(".check")).each(
						function(i, tmp) {
							if (this.value == "") {
								alert("日工作不能为空");
								$(this).css("background-color", "red");
								re = 1;
							}
						});
				if (re) {
					notoken = 0;
					return;
				}
				$('#orgDescForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/organizationdesc/ea_saveOrganizationDesc.jspa?ogName="+ogName+"&pageNumber="
										+ ppageNumber+"&date="+new Date());
				document.orgDescForm.submit.click();
				token = 2;
				break;
			case '删除' :
				if (organizationdescid == '') {
					alert("请选择！");
					return;
				}
				if (organizationdescid.substring(0, 2) == "sa") {
					if (confirm("是否移除？")) {
						$("#" + organizationdescid).remove();
						organizationdescid = "";
					}

					return;
				}
				alert(basePath+ "ea/organizationdesc/ea_delete.jspa?organizationdesc.organizationdescid="+ organizationdescid)
				$f = $('#orgDescForm');
				if (confirm("是否删除？")) {
					$f.attr("target", "hidden")
							.attr("action",
									basePath
											+ "ea/organizationdesc/ea_delete.jspa?organizationdesc.organizationdescid="
											+ organizationdescid);
					document.orgDescForm.submit.click();
					$("tr#" + organizationdescid).remove();
					organizationdescid = "";
					token = 11;

				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/organizationdesc/ea_getOrganizationdescList.jspa?organizationdesc.organizationid="+ogID+"&ogName="+ogName+"&search=" + search;
				numback(url);
				break;
			case '导出' :
				var url = basePath
						+ "ea/organizationdesc/ea_showExcel.jspa?organizationdesc.organizationid="+ogID;
				open(url);
				break;
		}
	}
	// 点击选中

	$(".registration tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				organizationdescid = this.id;

			});
	$(".registration tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				organizationdescid = this.id;
				action("修改");
			});

});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/organizationdesc/ea_getOrganizationdescList.jspa?organizationdesc.organizationid="+ogID+"&pageNumber=" + ppageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")+"&ogName="+ogName;
}