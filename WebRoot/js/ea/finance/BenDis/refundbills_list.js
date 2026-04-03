$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
	// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector

	$('.flexme11')
			.flexigrid({
						height : 330,
						width : 'auto',
						minwidth : 30,
						title : "<form name='SearchForm' style='height:30px;'id='SearchForm'><input type='hidden' name='stype' value='"+stype+"'/><input type='submit' style='display:none;' name='submit'/><input type='hidden'  name='search' value='search'/><table><tr><td><strong>退款管理</strong>&nbsp;订单编号：</td><td><input type='text' name='cashierBills.jNumOrder' style='width:100px;height:18px;'/></td><td>退款单编号：</td><td><input type='text' name='cashierBills.journalNum' style='width:100px;height:18px;'/></td><td><input type='button' value='  查询   ' class='input-button'  id='tosearch'></td></tr></table></form>",
						minheight : 80,
						buttons : [ {
							name : '查看',
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
	$("div.bDiv", $("div.flexigrid")).css("height", "490px");
	function action(com, grid) {
		switch (com) {
		case '查看':
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}

			window
					.open(basePath
							+ "/ea/refund/ea_getViewCashPage.jspa?cashierBills.cashierBillsID="
							+ cashierBillsID + "&voptype=v");
			break;

		case '设置每页显示条数':
			var url = basePath
					+ "/ea/refund/ea_findRefundCashList.jspa?search=" + search+"&stype="+stype;
			numback(url);
			break;

		}
	}
	$(".flexme11 tr[id]").click(function() {
		cashierBillsID = this.id;
		status = $("tr#" + cashierBillsID).find("span#status").text();
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});
	$("#tosearch").click(
			function() {
				$("#SearchForm").attr(
						"action",
						basePath + "/ea/refund/ea_toRefundCashSearch.jspa?pageNumber="
								+ pNumber);
				document.SearchForm.submit.click();
			});

	$(".flexme11 tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
		cashierBillsID = this.id;
		action("查看");
	});

});

function re_load() {
	var url = basePath + "/ea/refund/ea_findRefundCashList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value")+"&stype="+stype;
	document.location.href = encodeURI(url);
}
