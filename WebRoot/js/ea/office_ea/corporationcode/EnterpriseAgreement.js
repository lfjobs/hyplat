$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '企业合同管理',
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
				}, {
					name : '查询',
					bclass : 'mysearch',
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
					}, {
					separator : true
				}, {
					name : '添加到文件盒',
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
				enterpriseAgreementID = '';
				$("input.JQuerypersonvalue").attr("checked", false);
				$f = $('#cstaffForm');
				$f.find("#pic").attr("src", "");
				$("#jqModel").jqmShow();
				document.cstaffForm.reset();
				break;
			case '删除' :
				if (enterpriseAgreementID == "") {
					alert('请选择合同');
					return
				}
				if (confirm("是否删除？")) {
					$f = $('#cstaffForm');
					$f.find('#enterpriseAgreementID')
							.val(enterpriseAgreementID);
					var url = basePath
							+ "ea/enterpriseagreement/ea_delEnterpriseAgreement.jspa?pageNumber="
							+ pNumber;
					$f.attr("target", "hidden").attr("action", url);
					document.cstaffForm.submit.click();
					$("tr#" + enterpriseAgreementID).remove();
					enterpriseAgreementID = "";
					token = 11;
				}
				break;
			case '修改' :
				if (enterpriseAgreementID == "") {
					alert('请选择合同');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + enterpriseAgreementID);
				$t.find('img#photo').attr("src",
						basePath + $p.find("span#photo").text());
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				var photo = $p.find("span#enScan").text();
				$t.find("#pic").attr("src", "");
				if (photo.length != 0) {
					$t.find("#pic").attr("src", basePath + photo);
				}
				$t.find("input#enEdit").attr("value",
						$.trim($p.find("input#enEdit").attr("value")));
				$("#jqModel").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/enterpriseagreement/ea_showEnterpriseAgreementExcel.jspa?search="
						+ search;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/enterpriseagreement/ea_getEnterpriseAgreementList.jspa?search="
						+ search;
				numback(url);
				break;
			case '添加到文件盒' :
				if (enterpriseAgreementID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffBoxForm.reset();
				$t = $("table#stafftableBox");
				$p = $("tr#" + enterpriseAgreementID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModelBox").jqmShow();
				break;
		}
	}

	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModelBox").jqmHide();
				re_load();
			});
	$(".closeBox").click(function() {// 取消
				$("#jqModelBox").jqmHide();
				re_load();
			});

	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				enterpriseAgreementID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".JQuerySubmit").click(function() {

		if (enterpriseAgreementID == "") {
			$("#cstaffForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/enterpriseagreement/ea_saveEnterpriseAgreement.jspa?pageNumber="
									+ pNumber);
			document.cstaffForm.submit.click();
			$("#cstaffForm").find(":input[name]").val("");
			document.cstaffForm.reset();
			token = 1;
			return;
		}
		$("#cstaffForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/enterpriseagreement/ea_saveEnterpriseAgreement.jspa?pageNumber="
								+ pNumber);
		document.cstaffForm.submit.click();
		token = 2;
	});

	$("input#tosavebox").click(function() {
		$("#cstaffBoxForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/enterpriseagreement/ea_saveToFileBoxByID.jspa?pageNumber="
								+ pNumber);
		document.cstaffBoxForm.submit.click();
		token = 2;
	});

	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});

	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath
						+ "ea/enterpriseagreement/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.postSearchForm.submit.click();
		$("#postSearchForm").find(":input[name]").val("");
	});
	// 关闭绑定
	$("input#clsEditor", "form#editorForm").click(function() {
				$(".jqmWindow", $("#editorForm")).jqmHide();
			});
	// 确定绑定
	$("input#subEditor", "form#editorForm").click(function() {
		var val = $('#editorTxtare').val();
		$("#editorForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/enterpriseagreement/ea_saveEdit.jspa?enterpriseAgreement.enterpriseAgreementID="
								+ enterpriseAgreementID);
		document.editorForm.submit.click();
		token = 2;

		$(".jqmWindow", $("#editorForm")).jqmHide();
	});
});
/** *************************word文档编辑********************************* */
		
function savehql(enterpriseAgreementID, urlReturn) {
	document.cstaffForm.reset();
	$t = $("table#stafftable");
	$p = $("tr#" + enterpriseAgreementID);
	$p.find("span[id]").each(function() {
				$t.find(":input[name]#" + this.id).val($(this).text());
			});
	$t.find("input#enEdit").attr("value", urlReturn);
	$("#cstaffForm")
			.attr("target", "hidden")
			.attr(
					"action",
					basePath
							+ "ea/enterpriseagreement/ea_saveEnterpriseAgreement.jspa?pageNumber="
							+ pNumber);
	document.cstaffForm.submit.click();
}
function opennew(e) {
	var ooo = "";
		enterpriseAgreementID = e;
		ooo = $("tr#"+enterpriseAgreementID).find("input#enEdit").attr("value");
		if (ooo == "") {
			var urlReturn = OpenWord(ooo, 2);
			savehql(enterpriseAgreementID, urlReturn);
			$t = $("table#stafftable");
			$p = $("tr#" + enterpriseAgreementID);
			$p.find("input#enEdit").attr("value", urlReturn);
		} else {
		//var ss=	OpenWord(ooo, 2);
		}
		
}
/** ********************************************************** */
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/enterpriseagreement/ea_getEnterpriseAgreementList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
	}
}