$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$('.JQueryflexme').flexigrid({
				height : 500,
				width : 'auto',
				minwidth : 30,
				title : '备选车辆',
				minheight : 80,
				buttons : [{
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

			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;

			case '设置每页显示条数' :
				pageSize = prompt("输入显示条数", "请输入小于50正整数");
				if (pageSize < 0 || pageSize != parseInt(pageSize)
						|| pageSize > 50) {
					alert("请输入小于50的正整数");
					return;
				}
				window.location.href = basePath
						+ "/ea/safetycheck/ea_point.jspa?search=" + search
						+ "&pageSize=" + pageSize + "&type=" + type;
				break;
		}
	}

	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "/ea/safetycheck/ea_toSearchPoint.jspa?pageNumber="
						+ pageNumber);
		document.postSearchForm.submit.click();
	});

	$(".JQueryflexme tr[id]").click(function() {

		id = this.id;

		if (id.indexOf("car") > -1) {
			devicetypeid = id;
			if ($(".level2_" + devicetypeid).length > 0) {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				if ($(".level2_" + devicetypeid).is(":hidden")) {
					$(".level2_" + devicetypeid).show();
				} else {
					$("input.JQuerypersonvalue", $(this))
							.attr("checked", false);
					$(".level2_" + devicetypeid).hide();
					$(".level3_" + devicetypeid).hide();
				}
			} else {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				showPointList(devicetypeid);
			}
		} else if (id.indexOf("pointid") > -1) {
			checkpointid = id;
			if ($(".level3_" + checkpointid).length > 0) {
				if ($(".level3_" + checkpointid).is(":hidden")) {
					$(".level3_" + checkpointid).show();
				} else {
					$(".level3_" + checkpointid).hide();
				}
			} else {
				showPointItemList(checkpointid);
			}
		} else {
			return;
		}

	});

	// 用于阻止编辑的冒泡行为；
	$("input.JQuerypersonvalue").click(function(event) {
				event.stopPropagation();
			});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/safetycheck/ea_point.jspa?pageNumber=" + pageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}

// 单击车辆信息展出检查点
function showPointList(devicetypeid) {
	var url = basePath + "/ea/safetycheck/sajax_ea_pointListByCar.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					"pointbean.devicetypeid" : devicetypeid
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var pointlist = member.pointlist;
					var str = "";
					var obj;
					for (var i = 0; i < pointlist.length; i++) {
						obj = pointlist[i];
						// 克隆一份并且插入被克隆那行的前面
						$("tr#" + devicetypeid).after(

								$("#kelong").clone(true).attr("id",
										obj.checkpointid).addClass("level2_"
										+ devicetypeid));
						$tr = $("tr#" + obj.checkpointid);
						$tr.find("span#checkpointname")
								.text(obj.checkpointname);
						$tr.find("span#chip").text(obj.chip);

						if (obj.useflag == 0) {
							$tr.find("span#useflag").text("已启用");
						} else {
							$tr.find("span#useflag").text("已停用");
						}

					}

					$(".level2_" + devicetypeid).show();
				},
				error : function(data) {
					alert("根据车获取检查点失败");
				}

			});

}

// 单击检查点信息展出检查点项
function showPointItemList(checkpointid) {
	var url = basePath + "/ea/safetycheck/sajax_ea_getPointItemList.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					"checkpointid" : checkpointid
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var pointitemlist = member.pointitemlist;
					var carID = member.carID;
					var str = "";
					var obj;
					for (var i = 0; i < pointitemlist.length; i++) {
						obj = pointitemlist[i];
						// 克隆一份并且插入被克隆那行的前面
						$("tr#" + checkpointid).after(

								$("#kelong2").clone(true).attr("id",
										obj.checkpointitemid)
										.addClass("level3_" + checkpointid)
										.addClass("level3_" + carID));
						$tr = $("tr#" + obj.checkpointitemid);
						$tr.find("span#checkpointitemname")
								.text(obj.checkpointitemname);

					}

					$(".level3_" + checkpointid).show();
				},
				error : function(data) {
					alert("根据检查点获取检查项失败");
				}

			});

}
