$(document).ready(function() {

	$('.DtDrivingAppointmentRecordTable').flexigrid({
				height : 150,
				allDouble : true,
				width : 'auto',
				minwidth : 30,
				title: '培训管理记录',
				minheight : 80,
				buttons : [{
					name : '修改',
					bclass : 'edit',
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
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '修改' :
				if (drivingtraininginforid == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + drivingtraininginforid);
				if ($p.hasClass("check")) {
					return;
				}
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"dtDrivingTrainingInformap[" + select + "]." + this.name);
				});

				select++;
				$p.find("span")
						.addClass("model1");
				$p.find("input").removeClass("model1");
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
										+ "/ea/training/ea_saveDtDrivingTrainingInfor.jspa?");
				document.DtDrivingAppointmentRecordForm.submit.click();
				token = 2;
				break;
			case '导出':
				var url = basePath
				+ "/ea/training/ea_showDtDrivingTrainingInfor.jspa?dtDrivingAppointmentRecord.drivingprincipalid="
				+ drivingprincipalid+"&dtDrivingAppointmentRecord.docstatus="
				+ docstatus;
				open(url);
				break;	
		}
	}

	$(".DtDrivingAppointmentRecordTable tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		drivingtraininginforid = this.id;
	});
	
	$(".DtDrivingAppointmentRecordTable tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		drivingtraininginforid = this.id;
		action("修改");
	});
	$(".DtDrivingAppointmentRecordTable").find("select[id!=xxx]").each(function() {
		$s = $(this).hide();
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/training/ea_getListDtDrivingTrainingInfor.jspa?dtDrivingAppointmentRecord.drivingprincipalid="
				+ drivingprincipalid+"&dtDrivingAppointmentRecord.docstatus="
				+ docstatus;
}