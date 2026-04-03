$(document).ready(function() {
	window.parent.refreshMenu()
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
				title : treename+" ",
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '停用',
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
				if(window.parent.tree.getLevel(parent.treeid) == 3){
	                alert("不能添加下一级");
	                return;
	             }
				microlettermenuid = '';
				$("input.JQuerypersonvalue").attr("checked", false);
				document.cstaffForm.reset();
				$("#cstaffForm").find("input#microlettermenupid").attr("value",microlettermenupid);
				$("#cstaffForm").find("input#microlettermenulevel").attr("value",microlettermenupid=="001"?"10":"11");
				$("#cstaffForm").find("span#microlettermenulevelValue").text(microlettermenupid=="001"?"一级":"二级");
				$("#jqModel").jqmShow();
				break;
			case '停用' :
				if (microlettermenuid == "") {
					alert('请选择菜单');
					return
				}
				if (confirm("是否删除？")) {
					$f = $('#cstaffForm');
					$f.find('#microlettermenuid').val(microlettermenuid);
					var url = basePath + "ea/microletter/ea_delDtMicroletterMenu.jspa?pageNumber="
							+ pNumber;
					$f.attr("target", "hidden").attr("action", url);
					document.cstaffForm.submit.click();
					$("tr#" + microlettermenuid).remove();
					microlettermenuid = "";
					token = 11;
				}
				break;
			case '修改' :
				if (microlettermenuid == "") {
					alert('请选择菜单');
					return
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + microlettermenuid);
				$t.find('img#photo').attr("src",
						basePath + $p.find("span#photo").text());
				$p.find("span[id]").each(function() {
							$t.find("input[name]#" + this.id).val($.trim($(this)
									.text()));
				});
				$("#cstaffForm").find("input#microlettermenupid").attr("value",microlettermenupid);
				$("#cstaffForm").find("input#microlettermenulevel").attr("value",microlettermenupid=="001"?"10":"11");
				$("#cstaffForm").find("span#microlettermenulevelValue").text(microlettermenupid=="001"?"一级":"二级");
				$("#jqModel").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath + "ea/microletter/ea_getListDtMicroletterMenuAll.jspa?dtMicroletterMenu.microlettermenupid="+microlettermenupid+"&treename="+treename;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				microlettermenuid = this.id;
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
				basePath + "ea/microletter/ea_saveDtMicroletterMenu.jspa?pageNumber=" + pNumber);
		document.cstaffForm.submit.click();
		
		token = 2;
	});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
	});
});
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/microletter/ea_getListDtMicroletterMenuAll.jspa?pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")+"&dtMicroletterMenu.microlettermenupid="+microlettermenupid+"&treename="+treename;
	}
}