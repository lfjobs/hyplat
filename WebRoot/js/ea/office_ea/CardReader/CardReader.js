$(function() {
			$(".jqmWindow").jqm({
				modal : true,// 限制输入（鼠标点击，按键）的对话
				overlay : 20
					// 遮罩程度%
				}).jqmAddClose('.close');// 添加触发关闭的selector
			// .jqDrag('.drag');// 添加拖拽的selector
			$('.JQueryflexme').flexigrid({
						height : 360,
						width : 'auto',
						minwidth : 30,
						title : '读卡器管理',
						minheight : 80,
						buttons : [{
							name : '返回',
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
					case '返回' :
						document.location.href = basePath
								+ "/ea/cardmanage/ea_getCarGooutRecord.jspa?date="
								+ new Date();
						break;
					case '查询' :
						$("#jqModelSearch").jqmShow();
						break;

					case '设置每页显示条数' :
						var url = basePath
								+ "ea/cardreader/ea_getcardReaderList.jspa?search="
								+ search;
						numback(url);
						break;
				}
			}
			$("input.JQueryreturn").click(function() {// 取消
						$("#jqModel").jqmHide();
						re_load();
					});
			$(".close").click(function() {// 取消
						$("#jqModel").jqmHide();
						re_load();

					});
			$("#tosearch").click(function() {
				$("#postSearchForm").attr(
						"action",
						basePath + "ea/cardreader/ea_toSearch.jspa?pageNumber="
								+ pNumber);
				document.postSearchForm.submit.click();
			});
			$(".JQueryflexme").find("select").each(function() {
						$s = $(this).hide();
						$o = $("<span/>").text($s.find("option:selected")
								.text());
						$o.insertAfter($s);
					});

		});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/cardreader/ea_getcardReaderList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}