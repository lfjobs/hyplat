$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "库存预警",
				minheight : 80,
				buttons : [/*
							 * { name: '查询', bclass: 'mysearch', onpress:
							 * action//当点击调用方法 }, { separator: true },
							 */{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置上下限值',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});

	function action(com, grid) {
		switch (com) {
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/warning/ea_getWarningList.jspa?search=" + search;
				numback(url);
				break;
			// case '查询':
			// $("#jqModelSearch").jqmShow();
			// $("input#journalNum").focus();
			// break;
			case '设置上下限值' :
				if (financialbillID == '') {
					alert('请选择！');
					return;
				}
				$("input.JQuerypersonvalue").attr("checked", false);
				$t = $("div#setWarn");
				$p = $("tr#" + financialbillID);
				$p.find("span[id]").each(function() {
					$t.find(":input[name]#" + this.id).val($(this)
							.text());
				});
				$("#setWarn").jqmShow();
				break;
		}
	}
	// 这一行的单击事件
	$(".flexme11 tr[id]").click(function() {
		financialbillID = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});

	// 这一行的双击事件
	$(".flexme11 tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		financialbillID = this.id;
		action("查看");
	});

	// 查询按钮单击事件
	/*
	 * $("#tosearch").click(function(){ $("#SearchForm").attr("action",
	 * basePath+"/ea/warning/ea_toSearch.jspa?pageNumber="+pNumber);
	 * document.SearchForm.submit.click(); });
	 */

	// 设置上下限单击事件
	$("#toWarn").click(function() {
		$("#warnForm").attr("target", "hidden").attr(
				"action",
				basePath + "/ea/warning/ea_updateUpDownLine.jspa?pageNumber="
						+ pNumber);
		document.warnForm.submit.click();
		token = 12;
	});
});

function re_load() {
	document.location.href = basePath
			+ "/ea/warning/ea_getWarningList.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")
			+ "&search=" + search;
}