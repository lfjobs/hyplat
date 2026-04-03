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
				title : '卡信息管理',
				minheight : 80,
				buttons : [{
                        name: '返回',
                        bclass: 'delete',
                        onpress: action//当点击调用方法
                    },{
                        separator: true
                    },{
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
				document.location.href=basePath+"ea/cardmanage/ea_getCarGooutRecord.jspa?date="+new Date();
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
						+ "/ea/cardmanage/ea_getCardInfoList.jspa?search=" + search
						+ "&pageNumber=" + pNumber;
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				cardReaderID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("input.JQuerySubmit").click(function() {// 保存
				$(".put3").trigger("blur");
				if ($("form .error").length) {
					return;
				}

				// if (cardInfoID == ""){
				$("#cstaffForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/cardmanage/ea_saveCardInfo.jspa?pageNumber="
										+ pNumber + "&search=" + search);
				document.cstaffForm.submit.click();
				document.cstaffForm.reset();
				$("#cstaffForm").find("#staffID").trigger("change");
				token = 1;
				return;

			});
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
				basePath + "ea/cardmanage/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.postSearchForm.submit.click();
	});
	$(".JQueryflexme").find("select").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});

});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/cardmanage/ea_getCardInfoList.jspa?pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}
//停用
function showCardRecord(cardCode) {
	document.location.href = basePath
			+ "ea/cardmanage/ea_getCardRecordList.jspa?cardCode=" + cardCode;

}