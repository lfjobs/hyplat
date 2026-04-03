$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	parent.document.getElementById("mainframe"+state).style.height = 80 + len * 27 + "px";
	$('.contact').flexigrid({
				height : 'auto',
				width : 'auto',
				minwidth : 30,
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '删除',
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
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"studentRegInformap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				var heis = parent.document.getElementById("mainframe"+state).offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe"+state).style.height = heis;
				select++;
				break;

			case '修改' :
				if (drivingAllInformationID == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + drivingAllInformationID);
				if ($p.hasClass("check")) {
					return;
				}

				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"studentRegInformap[" + select + "]." + this.name);
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
				$("#timingStartTime,#timingEndTime,#timingNote", $(".check")).each(function(i, tmp) {
					if (this.value == "") {
						alert("请输入日期");
						$(this).css("background-color", "red");
						re = 1;
					}
					if (this.id == "timingNote"&&this.value.length>30) {
						alert("最多可输入30个字");
						$(this).css("background-color", "red");
						re = 1;
					}
				});
				if (re) {
					notoken = 0;
					return;
				}
				$('#contactForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/enroll/ea_saveStudentRegInfors.jspa?");
				document.contactForm.submit.click();
				token = 2;
				break;

			case '删除' :
				if (drivingAllInformationID == '') {
					alert("请选择！");
					return;
				}
				if (drivingAllInformationID.substring(0, 2) == "sa") {
					$("#" + drivingAllInformationID).remove();
					drivingAllInformationID = '';
					var heis = parent.document.getElementById("mainframe"+state).offsetHeight - 27 + "px";
					parent.document.getElementById("mainframe"+state).style.height = heis;
					return;
				}
				$f = $('#contactForm');
				$f
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/enroll/ea_deleteStudentRegInfors.jspa?dtDrivingAllInformation.drivingAllInformationID="
										+ drivingAllInformationID);
				document.contactForm.submit.click();
				$("tr#" + drivingAllInformationID).remove();
				drivingAllInformationID = "";
				token = 11;
				break;
		}
	}
	$("input#timingStartTime").blur(function(){
    	$("input#start").val($(this).val());
    });
    
    $("input#timingEndTime").blur(function(){
    	$("input#end").val($(this).val());
    });
	$(".contact tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		drivingAllInformationID = this.id;
	});
	
	$(".contact tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		drivingAllInformationID = this.id;
		action("修改");
	});

	$(".contact").find("select[id!=xxx]").each(function() {
		$s = $(this).hide();
		$o = $("<span/>").text($s.find("option:selected").text());
		$o.insertAfter($s);
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/enroll/ea_getStudentTimingList.jspa?dtDrivingAllInformation.subjectStatus="+subjectStatus+"&dtDrivingAllInformation.dataTitle=08&dtDrivingAllInformation.staffID="+ staffID+"&dtDrivingAllInformation.relationID="+relationID;
}