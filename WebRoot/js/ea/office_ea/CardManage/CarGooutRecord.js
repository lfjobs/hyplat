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
				title : '车辆门禁记录',
				minheight : 80,
				buttons : [{
					name : '返回',
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
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '读卡器管理',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '卡信息管理',
					bclass : 'excel',
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
						+ "/page/ea/main/navigation/logistics_Car.jsp?date="
						+ new Date();

				break;
			case '查看' :
				var ID = $("input[name=a]:radio:checked").val();
				if (ID == undefined) {
					alert("请选择");
					return;
				}
				document.postViewForm.reset();
				$t = $("table#viewTable");
				$p = $("tr#" + ID);
				$p.find("span[id]").each(function() {
							$t.find("span#" + this.id).text($(this).text());
						});
				var url = basePath
						+ "ea/cardmanage/sajax_ea_getGooutView.jspa?date="
						+ new Date();
				+new Date();

				$.ajax({
							url : encodeURI(url),
							type : "get",
							async : true,
							dataType : "json",
							data : {
								ID : ID
							},
							success : function(data) {
								var result = eval("(" + data + ")");
								var cardInfo = result.cardInfo;
								var cardRecord = result.cardRecord;
								var carInfo = result.carInfo;
								$t.find("span#validityTime")
										.text(cardInfo.validityTime + "天");

								$t.find("span#driver").text(carInfo.driver);
								$t.find("img#pic").attr("src",
										basePath + cardRecord.videoPic);
								if (cardInfo.status == "1") {
									$t.find("span#status").text("正常");
								} else if (cardInfo.status == "-1") {
									$t.find("span#status").text("销卡");
								} else {
									$t.find("span#status").text("挂失");
								}

								if (cardInfo.statesType == "1") {
									$t.find("span#statesType").text("正式卡");
								} else {
									$t.find("span#statesType").text("临时卡");
								}

								if (cardInfo.cardType == "01") {
									$t.find("span#cardType").text("epc");
								} else if (cardInfo.cardType == "02") {
									$t.find("span#cardType").text("12.4G");
								} else {
									$t.find("span#cardType").text("mifare");
								}

							}
						});

				$("#jqModelView").jqmShow();
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
						+ "ea/cardmanage/ea_getCarGooutRecord.jspa?search="
						+ search + "&pageNumber=" + pNumber + "&cardCode="
						+ cardCode;
				break;
			case '读卡器管理' :
				document.location.href = basePath
						+ "/ea/cardreader/ea_getcardReaderList.jspa";
				break;
			case '卡信息管理' :
				document.location.href = basePath
						+ "/ea/cardmanage/ea_getCardInfoList.jspa";
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
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
				basePath + "ea/cardmanage/ea_toSearchRecord.jspa?pageNumber="
						+ pNumber);
		document.postSearchForm.submit.click();
	});
	$(".JQueryflexme").find("select").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});
	$("#tocancle").click(function() {
				$("#jqModelView").jqmHide();
			});

	getCardReader();

	// 用于显示图片
	$("#tables span.look").each(function() {
		$(this).hover(function() {
			var left = $(this).position().left;
			var top = $(this).position().top;
			$("#showPlayer > img").attr("src", basePath + $(this).attr("id"))
					.parent().css({
								position : "absolute",
								left : left + 28,
								top : top + 90
							}).show();
		}, function() {
			$("#showPlayer").hide();
		});

	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/cardmanage/ea_getCardInfoList.jspa?pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}

// 跟踪读卡器使用记录,即车辆出入记录
function showCardRecord(cardCode) {
	document.location.href = basePath
			+ "ea/cardreader/ea_getcardReaderList.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")
			+ "&cardCode=" + cardCode;
}
// 获得进出读卡器
function getCardReader() {

	var url = basePath + "ea/cardmanage/sajax_n_ea_getCardReader.jspa";
	$.ajax({
				url : url,
				type : "post",
				async : "true",
				dataType : "json",
				success : function(data) {
					var member = eval("(" + data + ")");
					var ListR = member.result;
					var str1 = "<option value=''>请选择器读卡器</option>";
					for (var i = 0; i < ListR.length; i++) {
						var obj = ListR[i];
						str1 += "<option title='" + obj.readerEnterName
								+ "'value='" + obj.readerEnter + "'>"
								+ obj.readerEnterName + "</option>";

					}
					$("#postSearchForm #readerEnter").html(str1);

				},
				error : function(data) {
					alert("数据获取失败");
				}
			});

}