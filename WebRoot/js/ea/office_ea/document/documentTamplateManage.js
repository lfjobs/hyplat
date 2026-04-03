$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 320,
				width : 'auto',
				minwidth : 30,
				title : '模板管理-'+receiptType,
				minheight : 340,
				buttons : [ {
					name : '新建',
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
					name : '查询',
					bclass : 'mysearch',
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

	function action(com, grid) {
		switch (com) {
			case '新建' :
				templateId = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				document.templateForm.reset();
				$("form .error").remove();
				$("form .corect").remove();
				$("form .tooltip").remove();
				$("#winname").text("新建模板");
				$(".new").show();
				$(".update").hide();
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (templateId == "") {
					alert('请选择!')
					return;
				}
				document.templateForm.reset();
				$t = $("table#temptable");
				$p = $("tr#" + templateId);
				$p.find("span[id]").each(function() {

							$t.find("input[name]#" + this.id).val($(this)
									.text());
							if (this.id == "fileType") {
								$t.find("span#" + this.id).text($(this).text())
								$t.find("input#fileTypes").val($(this).text())
							}
							if (this.id == "fileShowName") {
								$t.find("input[id=fileShowName0]").val($(this)
										.text());
							}
							if (this.id == "templatePath") {
								$t.find("input[id=templatePaths]").val($(this)
										.text());
							}
						});

				$("#winname").text("更新模板");
				$(".new").hide();
				$(".update").show();
				$("#jqModel").jqmShow();

				break;
			case '删除' :
				if (templateId == "") {
					alert('请选择！')
					return
				}
				//if (confirm("确定继续？")) {
					var url = basePath
							+ "ea/documenttemplate/sajax_n_ea_delDocumentTemplate.jspa?templateId="
							+ templateId + "&date="
							+ new Date().toLocaleString();
					$.ajax({
								url : url,
								type : "get",
								aysnc : false,
								dataType : "json",
								success : function(data) {
									var message = eval("(" + data + ")");
									var msg = message.result;
									if (msg == "suc") {
										window.location.href = window.location.href;
										alert("删除成功！")
									}
									if (msg == "fail") {
										alert("该模板已被使用不能删除！")
									}
								},
								error : function(data) {
									alert("删除操作失败")
								}

							})

				//}
				break;
			case '查询' :
				document.searchForm.reset();
				$("#jqModelSearch").jqmShow();
				break;

			case '设置每页显示条数' :
				var url = basePath
						+ "ea/documenttemplate/ea_getDocTemplateList.jspa?search="+search+"&date="
						+ new Date().toLocaleString();
				numback(url);
				break;

		}
	}

	$("#tosearch").click(function() {
		$("#searchForm").attr(
				"action",
				basePath + "ea/documenttemplate/ea_toSearch.jspa?pageNumber="
						+ pNumber + "&date=" + new Date());
		document.searchForm.submit.click();
	});

	$(".JQueryflexme tr[id]").click(function() {
				templateId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			})
	$(".JQueryflexme tr[id]").dblclick(function() {
				action("修改");
			})

	// 判断模板名字是否重复使用/以及判断是否设置了模板
	$("#templateForm .filename").bind("blur", function() {
		$input = $(this);
		$parent = $input.parent();
		var inputValue = $input.attr("value");
		$parent.find(".error").remove();
		$parent.find(".corect").remove();
		$parent.find(".tooltip").remove();
		if ($input.is(".filename")) {
			if ($.trim(inputValue)!= "") {
				if ($("#templateForm #fileShowName0").val() != inputValue) {
					if (templateNameIsExist(inputValue) == 0) {
						$parent
								.append("<span class=\"error\"><a class=\"tex\">名称已使用</a></span>");
						return false;
					} else {
						$parent
								.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
						return;
					}
				} else {
					$parent
							.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
					return;
				}
			} else {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">\u4e0d\u80fd\u4e3a\u7a7a</a></span>");
				return;
			}
		}

	});

	// 新建/更新点击设置模板

	$("#setTemplate").click(function() {
		var fileType = "";
		if (templateId == "") {
			fileType = $("#templateForm select#fileTypes")
					.find('option:selected').val();
			if ($("#templateForm #fileTypes0").val() != fileType) {
				$("#templateForm #templatePaths").val("");
			}
		} else {
			fileType = $("#templateForm span[id=fileType]").text();
		}
		var docPath = $("#templateForm #templatePaths").val();
		OpenOfficeTemp(docPath, fileType);

	});

	// 新建和更新的确定
	$("#confirm").click(function() {
		$("#templateForm .filename").trigger("blur");
		$(".put3").trigger("blur");
		validatetemp();

		if ($("form .error").length) {

			return;
		}

		$("#templateForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/documenttemplate/ea_confirmSubmit.jspa?&dats="
						+ new Date());
		document.templateForm.submit.click();
		token = 2;
	});

});

// 新建和更新模板 打开Office
function OpenOfficeTemp(docPath, fileType) {
	if (docPath == "") {
		var url = basePath + "ea/zoffice/sajax_ea_createBlankOffice.jspa?date="
				+ new Date().toLocaleString();
		$.ajax({
			url : encodeURI(url),
			type : "get",
			dataType : "json",
			async : false,
			data : {
				fileType : fileType,
				temp : "temp"
			},
			success : function(data) {
				var jsonresult = eval("(" + data + ")");
				var docPath = jsonresult.result;
				window
						.open(
								encodeURI(basePath
										+ "page/ea/main/office_ea/document/wordcommon.jsp?docPath="
										+ docPath + "&fileType="+fileType+"&isRead=2&stage=设置模板"));
				$("#templateForm #templatePaths").val(docPath);
				$("#templateForm #fileTypes0").val(fileType);
			},
			error : function(data) {
				alert("创建模板失败");
			}

		});
	} else {
		window
				.open(
						encodeURI(basePath
								+ "page/ea/main/office_ea/document/wordcommon.jsp?docPath="
								+ docPath + "&fileType=" + fileType+"&isRead=2&stage=设置模板"));

	}

}

// 判断模板名称在公司范围内是否重复
function templateNameIsExist(fileShowName) {
	var num;
	var url = basePath
			+ "ea/documenttemplate/sajax_n_ea_templateNameIsExist.jspa?date="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				dataType : "json",
				async : false,
				data : {
					fileShowName : fileShowName
				},
				success : function(data) {
					var jsonresult = eval("(" + data + ")");
					var result = jsonresult.result;
					if (result == "suc") {
						num = 1;
					} else {
						num = 0;
					}

				},
				error : function(data) {
					num = 2;
					alert("失败");

				}
			});

	return num;
}

// 验证模板是否设置以及文件格式更改后是否重新设置了
function validatetemp() {
	$("#setTemplate").parent().find(".error").remove();
	$("#setTemplate").parent().find(".corect").remove();
	$("#setTemplate").parent().find(".tooltip").remove();
	if ($("#templateForm #templatePaths").val() != "") {
		var fileType = $("#templateForm select#fileTypes")
				.find('option:selected').val();

		if ($("#templateForm #fileTypes0").val() != ""
				&& $("#templateForm #fileTypes0").val() != fileType) {
			$("#setTemplate")
					.parent()
					.append("<span class=\"error\"><a class=\"tex\">格式已更改请重新设置</a></span>");
			return;
		} else {
			$("#setTemplate")
					.parent()
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
			return;
		}
	} else {
		$("#setTemplate")
				.parent()
				.append("<span class=\"error\"><a class=\"tex\">请设置模板</a></span>");
		return;
	}
}

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/documenttemplate/ea_getDocTemplateList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&receiptType="+receiptType;
}
