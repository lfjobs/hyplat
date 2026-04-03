$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	if (type == "company" || type == "group") {
		$(".operate").hide();
		$('.JQueryflexme').flexigrid({
					height : 300,
					width : 'auto',
					minwidth : 30,
					title : '档案类别管理',
					minheight : 80,
					buttons : [{
						name : '返回',
						bclass : 'prev',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '设置每页显示条数',
						bclass : 'mysearch',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}]
				});
	} else {
		$(".operate").show();
		if (hid != "" && hid == "hid") {
			$(".hid").hide();
			$(".show").show();
			$('.JQueryflexme').flexigrid({
						height : 95,
						width : 'auto',
						minwidth : 80,
						title : '档案类别管理',
						minheight : 70,
						buttons : [{
							name : '添加一级类别',
							bclass : 'add',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}, {
							name : '设置每页显示条数',
							bclass : 'mysearch',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}]
					});
		} else {
			$(".hid").show();
			$(".show").hide();
			$('.JQueryflexme').flexigrid({
						height : 300,
						width : 'auto',
						minwidth : 30,
						title : '档案类别管理',
						minheight : 80,
						buttons : [{
							name : '返回',
							bclass : 'prev',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}, {
							name : '添加一级类别',
							bclass : 'add',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}, {
							name : '设置每页显示条数',
							bclass : 'mysearch',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}]
					});
		}
	}
	function action(com, grid) {
		switch (com) {
			case '返回' :
				document.location.href = basePath
						+ "ea/archive/ea_getArchiveList.jspa?type=" + type
						+ "&date=" + new Date();
				break;

			case '添加一级类别' :
				$("#jqModelAdd").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;

			case '设置每页显示条数' :
				pNumber = prompt("输入显示条数", "请输入小于50正整数");
				if (pNumber < 0 || pNumber != parseInt(pNumber) || pNumber > 50) {
					alert("请输入小于50的正整数");
					return;
				}
				document.location.href = basePath
						+ "/ea/catalogue/ea_getCatalogueList.jspa?hid=" + hid
						+ "&search=" + search + "&type=" + type
						+ "&pageNumber=" + pNumber;
				break;
		}
	}

	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				archiveid = this.id;
				opertionID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#menu tbody .level1 td.lev_titl").click(function() {
				var Elem = $(this).parent().nextUntil(".level1")
						.filter(".level2");
				if (Elem.css("display") == "none") {
					Elem.show();
				} else {
					Elem.hide();
				}
			});

	$("input#toSubmit").click(function() {// 提交类别信息
				if ($("#postAddForm #name").val() == "") {
					alert("请填写名称");
					return;
				}
				$("#postAddForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/catalogue/ea_addCatalogue.jspa?pageNumber="
										+ pNumber);
				document.postAddForm.submit.click();
				document.postAddForm.reset();

				token = 2;
				return;
			});

	$("input#toSubmit2").click(function() {// 提交二级类别信息
				if ($("#postAddSecondForm #name").val() == "") {
					alert("请填写名称");
					return;
				}
				$("#postAddSecondForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/catalogue/ea_addCatalogue.jspa?pageNumber="
										+ pNumber);
				document.postAddSecondForm.submit.click();
				document.postAddSecondForm.reset();
				token = 2;
				return;
			});

	$("#tosearch").click(function() {
		$("#postSearchForm")
				.attr(
						"action",
						basePath + "ea/location/ea_toSearch.jspa?pageNumber="
								+ pNumber);
		document.postSearchForm.submit.click();
	});

});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/catalogue/ea_getCatalogueList.jspa?hid=" + hid
				+ "&pageNumber=" + pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

// 添加二级类别
function addSecondLevel(parentid) {
	$p = $("tr#" + parentid);
	$("#postAddSecondForm #parentname").val($p.find("span[id=name]").text());// 设置上级目录值
	$("#postAddSecondForm #parent").val(parentid);
	$("#jqModelAddSecond").jqmShow();
}

// 编辑一级和二级
function edit(archiveid, type, parentname) {
	if (!IsCanEdit(archiveid)) {
		alert("该类别已被引用，不可编辑");
		return;
	}
	$p = $("tr#" + archiveid);
	if (type == "first") {
		document.postAddForm.reset();
		$("#postAddForm #name").val($p.find("span[id=name]").text());
		$("#postAddForm #archiveid").val(archiveid);
		$("#jqModelAdd").jqmShow();
	} else {
		document.postAddSecondForm.reset();
		$("#postAddSecondForm #parentname").val(parentname);
		$("#postAddSecondForm #name").val($p.find("span[id=name]").text());
		$("#postAddSecondForm #archiveid").val(archiveid);
		$("#jqModelAddSecond").jqmShow();
	}
}

// 删除
function deleteLevel() {
	if (confirm("确定删除")) {
		var url = basePath
				+ "ea/catalogue/sajax_ea_deleteCatalogue.jspa?dates="
				+ new Date();
		$.ajax({
					url : url,
					type : "get",
					async : false,
					dataType : "json",
					data : {
						archiveid : archiveid
					},
					success : function(data) {
						var member = eval("(" + data + ")");
						var result = member.result;
						if (result == "suc") {
							alert("删除成功！");
							window.location.reload();
						} else {
							alert("该位置已被引用，无法删除！");
						}
					},
					error : function(data) {
						alert("读取数据失败");
					}

				});
	}
}

// 判断类别是否已被引用，引用了不可编辑

function IsCanEdit(archiveid) {
	var bool = null;
	var url = basePath + "ea/catalogue/sajax_ea_IsCanEdit.jspa?dates="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					archiveid : archiveid
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					if (result == "suc") {
						bool = true;
					} else {
						bool = false;
					}
				},
				error : function(data) {
					alert("读取数据失败");
				}

			});
	return bool;
}
