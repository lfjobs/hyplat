$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.loglock').flexigrid({
				height : 345,
				width : 'auto',
				minwidth : 30,
				title : '加锁管理',
				minheight : 80,
				buttons : [/*{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '作废',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '全部保存',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, */{
					name : '加锁',
					bclass : 'locked',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '解锁',
					bclass : 'unlocked',
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
					}]
			});
	function action(com, grid) {
		switch (com) {
		/*	case '添加' :
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"loglockmap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				select++;
				break;
			case '全部保存' :
				if (notoken) {
					return;
				}
				if (select == 1) {
					return;
				}
				notoken = 1;
				var re = 0;
				$("#startDate", $(".check")).each(function(i, tmp) {
							if (this.value == "") {
								$(this).css("background-color", "red");
								re = 1;
							}
						});
				$("#endDate", $(".check")).each(function(i, tmp) {
							if (this.value == "") {
								$(this).css("background-color", "red");
								re = 1;
							}
						});
				if (re) {
					alert("请输入日期");
					notoken = 0;
					return;
				}
				$('#loglockForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/loglock/t_ea_saveLogLock.jspa?pageNumber="
										+ pNumber);
				document.loglockForm.submit.click();
				token = 2;
				break;
			case '作废' :
				if (loglockID == '') {
					alert("请选择！");
					return;
				}
				if (loglockID.substring(0, 2) == "sa") {
					if (confirm("是否移除？")) {
						$("#" + loglockID).remove();
						loglockID = "";
					}

					return;
				}
				var str = $("span#status", $("tr#" + loglockID)).text();
				if (str == "01") {
					alert("该条信息已作废！");
					return;
				}
				$f = $('#loglockForm');
				if (confirm("是否作废？")) {
					if (notoken)
						return;
					notoken = 1;
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/loglock/ea_delLogLock.jspa?pageNumber="
											+ pNumber + "&loglock.logLockID="
											+ loglockID);
					document.loglockForm.submit.click();
					$("span#st", $("tr#" + loglockID)).text("已作废");
					$("span#status", $("tr#" + loglockID)).text("01");
					loglockID = "";
					token = 11;
				}
				break;*/
			case '加锁' :
				if (loglockID == '') {
					alert("请选择！");
					return;
				}
				var str = $("span#status", $("tr#" + loglockID)).text();
				if (str == "00") {
					alert("已加锁！");
					return;
				}
				if (notoken)
					return;
				notoken = 1;
				var url = basePath + "ea/logbooksummary/sajax_n_ea_logLocked.jspa?logLockID=" + loglockID;
				$.ajax({
						url : encodeURI(url),
						type: "get",
						async: true,
						dataType: "json",
						success: function cbf(data){
							alert("日志已加锁！");
							re_load();
						},
						error: function cnf(data){
							alert("加锁失败！");
						}
				});
				break;
			case '解锁' :
				if (loglockID == '') {
					alert("请选择！");
					return;
				}
				var str = $("span#status", $("tr#" + loglockID)).text();
				if (str == "01") {
					alert("已解锁！");
					return;
				}
				if (notoken)
					return;
				notoken = 1;
				var url = basePath + "ea/loglock/sajax_ea_delLogLock.jspa?loglock.logLockID=" + loglockID;
				$.ajax({
						url : encodeURI(url),
						type: "get",
						async: true,
						dataType: "json",
						success: function cbf(data){
							alert("日志已解锁！");
							re_load();
						},
						error: function cnf(data){
							alert("解锁失败！");
						}
				});
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath + "ea/loglock/ea_getListLogLock.jspa?1=1";
				numback(url);
				break;
		}
	}
	
	$("#tosearch").click(function(){
		$("#lockSearchForm").attr(
				"action",
				basePath + "ea/loglock/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.lockSearchForm.submit.click();
	});

	$(".loglock tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		loglockID = this.id;
	});
});

function re_load() {
	document.location.href = basePath
			+ "ea/loglock/ea_getListLogLock.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}