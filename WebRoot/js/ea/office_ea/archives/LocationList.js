$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		if (type == "company" || type == "group") {
			var title = "";
			if(type=="company"){
				title="存储位置公司汇总";
			}else{
				title="存储位置集团汇总";
			}
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : title,
				minheight : 80,
				buttons : [{
					name : '返回',
					bclass : 'prev',
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
		}else{
			$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '存储位置管理',
				minheight : 80,
				buttons : [{
					name : '返回',
					bclass : 'prev',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
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
		}
	function action(com, grid) {
		switch (com) {
			case '返回' :
				document.location.href = basePath
						+ "ea/archive/ea_getArchiveList.jspa?type=" + type+"&date="
						+ new Date();
				break;

			case '添加' :
				document.postAddForm.reset();
				$("#postAddForm div#title").text("添加");
				$("#jqModelAdd").jqmShow();
				break;
			case '修改' :
				if (locationid == "") {
					alert("请选择");
					return;
				}
				document.postAddForm.reset();
				$t = $("table#addTable");
				$p = $("tr#" + locationid);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());
						});

				$t.find("input#locationid").val(locationid);
				$("#postAddForm div#title").text("修改");
				$("#jqModelAdd").jqmShow();
				break;
			case '删除' :
				if (locationid == "") {
					alert("请选择");
					return;
				}

				if (confirm("确定删除")) {
					var url = basePath
							+ "ea/location/sajax_ea_deleteLocation.jspa?dates="
							+ new Date();
					$.ajax({
								url : url,
								type : "get",
								async : "true",
								dataType : "json",
								data : {
									locationid : locationid
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
						+ "/ea/location/ea_getLocationList.jspa?search="
						+ search+"&type="+type+ "&pageNumber=" + pNumber;
				break;
		}
	}

	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				locationid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#toSubmit").click(function() {// 提交位置信息

				if ($("#postAddForm #locationname").val() == "") {
					alert("请添写名称");
					return;
				}
				$("#postAddForm").attr("target", "hidden").attr(
						"action",
						basePath
								+ "ea/location/ea_addLocation.jspa?pageNumber="
								+ pNumber);
				document.postAddForm.submit.click();
				document.postAddForm.reset();
				token = 2;
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
				+ "ea/location/ea_getLocationList.jspa?pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}
