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
				title : '参会人员管理——' + meetingName,
				minheight : 80,
				buttons : [{
					name : '返回',
					bclass : 'restore',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看',
					bclass : 'see',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '参会情况录入',
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
					name : '通知',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
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
						+ "ea/smeeting/ea_getStaffMeeingList.jspa";
				break;
				
		    case '查看' :

				$t = $("div#jqModelView");
				$p = $("tr#" + partiID);
				$p.find("span[id]").each(function() {
							$t.find("span[name]#" + this.id).text($(this)
									.text());
							$t.find(":input[name]#" + this.id).val($(this)
									.text());

						});

				// 缺席
				if ($t.find("#isAttend").val() == "00") {
					$(".unAttendCause").hide();
					$(".late").show();
					$(".speak").show();
                   
				} else {//缺席
					$(".unAttendCause").show();

					$(".lateCause").hide();
					$(".speakContent").hide();
					$(".late").hide();
					$(".speak").hide();
				}

				// 迟到
				if ($t.find("#isLate").val() == "00") {
					$(".lateCause").hide();
				} else {
					$(".lateCause").show();
				}

				// 发言
				if ($t.find("#isSpeak").val() == "00") {
					$(".speakContent").hide();
				} else {
					$(".speakContent").show();
				}

				$("#jqModelView").jqmShow();
				break;
				break;
			case '参会情况录入' :
				if (partiID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$("#partiIDs").val(partiID);

				$t = $("div#jqModel");
				$p = $("tr#" + partiID);
				$p.find("span[id]").each(function() {
							$t.find("span[name]#" + this.id).text($(this)
									.text());
							$t.find(":input[name]#" + this.id).val($(this)
									.text());

						});

				// 缺席
				if ($t.find("#isAttend").val() == "00") {
					$(".unAttendCause").hide();
					$(".late").show();
					$(".speak").show();
                   
				} else {//缺席
					$(".unAttendCause").show();

					$(".lateCause").hide();
					$(".speakContent").hide();
					$(".late").hide();
					$(".speak").hide();
				}

				// 迟到
				if ($t.find("#isLate").val() == "00") {
					$(".lateCause").hide();
				} else {
					$(".lateCause").show();
				}

				// 发言
				if ($t.find("#isSpeak").val() == "00") {
					$(".speakContent").hide();
				} else {
					$(".speakContent").show();
				}

				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (informID == "") {
					alert('请选择！');
					return
				}
				$f = $('#cstaffForm');
				$f.find(':input#informID').val(informID);
				if (confirm("是否删除？")) {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/informbills/ea_delInformBills.jspa?pageNumber="
											+ ppageNumber + "&search="
											+ psearch);
					document.cstaffForm.submit.click();
					$("tr#" + informID).remove();
					informID = "";
					token = 11;
				}
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/smeeting/ea_getPartiStaffList.jspa?search="
						+ psearch+"&meetingID="+meetingID;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				partiID = this.id;
				action('参会情况录入');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				partiID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("input.JQuerySubmit").click(function() {// 保存
				$(".put3").trigger("blur");

				if ($("form .error").length) {
					return;
				}

				$("#cstaffForm").attr("target", "hidden").attr(
						"action",
						basePath
								+ "ea/smeeting/ea_saveSMParti.jspa?pageNumber="
								+ ppageNumber + "&search=" + psearch);
				document.cstaffForm.submit.click();
				token = 2;
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/smeeting/ea_toSearchParti.jspa?pageNumber="
						+ ppageNumber);
		document.postSearchForm.submit.click();
	});
	$(".JQueryflexme").find("select").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});

	// 缺席
	$(".isAttend").change(function() {
				if ($(this).val() == "00") {
					$(".unAttendCause").hide();

					$(".lateCause").hide();
					$(".speakContent").hide();
					$(".late").show();
					$(".speak").show();

					$(".isLate").val("00");
					$(".isSpeak").val("00");
				} else {
					$(".unAttendCause").show();

					$(".lateCause").hide();
					$(".speakContent").hide();
					$(".late").hide();
					$(".speak").hide();
				}
			});

	// 迟到
	$(".isLate").change(function() {
				if ($(this).val() == "00") {
					$(".lateCause").hide();
				} else {
					$(".lateCause").show();
				}
			});

	// 发言
	$(".isSpeak").change(function() {
				if ($(this).val() == "00") {
					$(".speakContent").hide();
				} else {
					$(".speakContent").show();
				}
			});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/smeeting/ea_getPartiStaffList.jspa?search=" + psearch
				+ "&pageNumber=" + ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&meetingID="+meetingID;
}
