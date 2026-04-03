$(document).ready(function() {
	var tip = 0;
	var result1 = tip, result2 = tip, result3 = tip;

	var urlPath = basePath
			+ "ea/extralflow/sajax_ea_checkUnFinishedDocument.jspa?type=examine&date="
			+ new Date();
	$.ajax({
				url : urlPath,
				type : "get",
				dataType : 'json',
				async : true,
				success : function scanback(data) {
					var member = eval("(" + data + ")");
					result2 = member.result;
					$("span#result2").text("(" + result2 + ")");
				},
				error : function scanback(data) {
					alert("检查未审查公文失败！");
				}
			});

	urlPath = basePath
			+ "ea/extralflow/sajax_ea_checkUnFinishedDocument.jspa?type=seal&date="
			+ new Date();
	$.ajax({
				url : urlPath,
				type : "get",
				async : true,
				dataType : 'json',
				success : function scanback(data) {
					var member = eval("(" + data + ")");
					result3 = member.result;
					$("span#result3").text("(" + result3 + ")");
				},
				error : function scanback(data) {
					alert("检查未盖章公文失败！");
				}
			});

	// 查拟投诉处理箱
	urlPath = basePath + "ea/extralflow/sajax_ea_checkDealComplaint.jspa?date="
			+ new Date();
	$.ajax({
				url : urlPath,
				type : "get",
				dataType : 'json',
				async : true,
				success : function scanback(data) {
					var member = eval("(" + data + ")");
					result1 = member.result;
					$("span#result1").text("(" + result1 + ")");
				},
				error : function scanback(data) {
					alert("检查未发行公文失败！");
				}
			});
	setInterval(function() {
		// /setTimeout(function(){
		var FormObj = document.getElementById("mainframe").contentWindow;
		var total = FormObj.document.getElementById("totals");
		if (total != null) {
			FormObj.document.getElementById("totals").innerText = parseInt(result1)
					+ parseInt(result2) + parseInt(result3);
			FormObj.document.getElementById("results1").innerText = parseInt(result1);
			FormObj.document.getElementById("results2").innerText = parseInt(result2);
			FormObj.document.getElementById("results3").innerText = parseInt(result3);
		}
	}, 1000);

	noticeSetDealer();

});

function tonclick(id) {

	if (id == "draft") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/extralflow/ea_getComplaintList.jspa?type=draft&date="
								+ new Date());
	}
	if (id == "dealed") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/extralflow/ea_getComplaintList.jspa?type=dealed&date="
								+ new Date());
	}

	if (id == "approvalno") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/extralflow/ea_getUnfinishedList.jspa?type=examine&date="
								+ new Date());

	}
	if (id == "approvalye") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/extralflow/ea_getfinishedList.jspa?type=examine&date="
								+ new Date());
	}
	if (id == "stampno") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/extralflow/ea_getUnfinishedList.jspa?type=seal&date="
								+ new Date());
	}
	if (id == "stampye") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/extralflow/ea_getfinishedList.jspa?type=seal&date="
								+ new Date());
	}

	if (id == "set") {
		$("#mainframe").attr(
				"src",
				basePath + "ea/extralflow/ea_showSetInfo.jspa?date="
						+ new Date());
	}

}

text = "设置处理人";
color1 = "#F22B01";
color2 = "#2233FF";
fontsize = "span style=\'FONT-SIZE: 8pt\'";
speed = 1;

var i = 0;

function changeCharColor() {

	str = "<center><font size=" + fontsize + "><font color=" + color1 + "><B>";
	for (var j = 0; j < text.length; j++) {
		if (j == i) {
			str += "<font color=" + color2 + ">" + text.charAt(i)
					+ "</font><B>";
		} else {
			str += text.charAt(j);
		}
	}
	str += "</font></font><B></center>";
	a.innerHTML = str;

	(i == text.length) ? i = 0 : i++;
}

function noticeSetDealer() {
	var urlPath = basePath
			+ "ea/extralflow/sajax_ea_noticeSetDealer.jspa?type=seal&date="
			+ new Date();
	$.ajax({
				url : urlPath,
				type : "get",
				async : true,
				dataType : 'json',
				success : function scanback(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					if (result == "set") {
						setInterval("changeCharColor()", speed);
					}

				},
				error : function scanback(data) {
					alert("数据获取失败！");
				}
			});
}