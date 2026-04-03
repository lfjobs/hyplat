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
				title : 'QQ管理',
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
					name : '查看',
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
					name : '导入',
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
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				qqID = '';
				$("input.JQuerypersonvalue").attr("checked", false);
				document.cstaffForm.reset();
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (qqID == "") {
					alert('请选择QQ');
					return
				}
				if (confirm("是否删除？")) {
					$f = $('#cstaffForm');
					$f.find('#qqID').val(qqID);
					var url = basePath + "ea/qq/ea_delQq.jspa?pageNumber="
							+ pNumber;
					$f.attr("target", "hidden").attr("action", url);
					document.cstaffForm.submit.click();
					$("tr#" + qqID).remove();
					qqID = "";
					token = 11;
				}
				break;
			case '查看' :
				if (qqID == "") {
					alert('请选择QQ');
					return
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + qqID);
				$t.find('img#photo').attr("src",
						basePath + $p.find("span#photo").text());
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModel").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath + "ea/qq/ea_showQqExcel.jspa?search=" + search;
				open(url);
				break;
			case '导入' :
				url = basePath + "ea/importdata/ea_showImportDataPage.jspa?fileType=QQJBXX";				
				window.open(url,"importDataPage","height=400,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
				break;
			case '设置每页显示条数' :
				var url = basePath + "ea/qq/ea_getQqList.jspa?search=" + search;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				qqID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".JQuerySubmit").click(function() {
		$(".put3", $("table#stafftable")).trigger("blur");
		if ($("#cstaffForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		$("#cstaffForm").attr("target", "hidden").attr("action",
				basePath + "ea/qq/ea_saveQq.jspa?pageNumber=" + pNumber);
		if (qqID == "") {
			document.cstaffForm.submit.click();
			$("#cstaffForm").find(":input[name]").val("");
			token = 1;
			return;
		}
		document.cstaffForm.submit.click();
		token = 2;
	});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});

	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#postSearchForm").attr("action",
				basePath + "ea/qq/ea_toSearch.jspa?pageNumber=" + pNumber);
		document.postSearchForm.submit.click();
		$("#postSearchForm").find(":input[name]").val("");
	});
});
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/qq/ea_getQqList.jspa?pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
	}
}