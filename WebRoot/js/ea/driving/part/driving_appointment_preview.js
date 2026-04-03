$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.DtDrivingAppointmentRecordTable').flexigrid({
				height : 700,
				allDouble : true,
				width : 'auto',
				minwidth : 30,
				title: '预约培训记录',
				minheight : 80
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"dtDrivingAppointmentRecordmap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				select++;
				break;
			case '修改' :
				if (drivingappointmentrecordid == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + drivingappointmentrecordid);
				if($.trim($p.find("span#isappointmentstatusHidden").text())=="01"){
					alert("该数据已“预约成功”不可修改!");
					return;
				}
				if ($p.hasClass("check")) {
					return;
				}
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"dtDrivingAppointmentRecordmap[" + select + "]." + this.name);
				});

				select++;
				$p.find("span")
						.addClass("model1");
				$p.find("input").removeClass("model1");
				$p.find("a").removeClass("model1");
				$p.find("select").attr("disabled", false);
				$p.find("select").show();
				$(this).parent().children("span").show();
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
				$("#submitCheGuanDate", $(".check")).each(function(i, tmp) {
							if (this.value == "") {
								alert("请输入日期");
								$(this).css("background-color", "red");
								re = 1;
							}
						});
				if (re) {
					notoken = 0;
					return;
				}
				$('#DtDrivingAppointmentRecordForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "/ea/appointment/ea_saveDtDrivingAppointmentRecord.jspa?");
				document.DtDrivingAppointmentRecordForm.submit.click();
				token = 2;
				break;

			case '删除' :
				if (drivingappointmentrecordid == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + drivingappointmentrecordid);
				if($.trim($p.find("span#isappointmentstatusHidden").text())=="01"){
					alert("该数据已“预约成功”不可删除!");
					return;
				}
				if (drivingappointmentrecordid.substring(0, 2) == "sa") {
					$("#" + drivingappointmentrecordid).remove();
					drivingappointmentrecordid = "";
					return;
				}
				$f = $('#DtDrivingAppointmentRecordForm');
				if (notoken)
					return;
				notoken = 1;
				$f.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "/ea/appointment/ea_delDtDrivingAppointmentRecord.jspa?dtDrivingAppointmentRecord.drivingappointmentrecordid="
										+ drivingappointmentrecordid);
				document.DtDrivingAppointmentRecordForm.submit.click();
				$("tr#" + drivingappointmentrecordid).remove();
				drivingappointmentrecordid = "";
				token = 11;
				break;
			case '导出':
				var url = basePath
				+ "/ea/appointment/ea_showDtDrivingAppointmentRecord.jspa?dtDrivingAppointmentRecord.drivingprincipalid="
				+ drivingprincipalid;
				open(url);
				break;
		}
	}
	
	$(".DtDrivingAppointmentRecordTable").find("select[id!=xxx]").each(function() {
		$s = $(this).hide();
	});
	
	
	
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/appointment/ea_getListDtDrivingAppointmentRecord.jspa?dtDrivingAppointmentRecord.drivingprincipalid="
				+ drivingprincipalid;
}