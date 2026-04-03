$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	parent.document.getElementById("mainframe5").style.height = 80 + len * 27 + "px";
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
				var heis = parent.document.getElementById("mainframe5").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe5").style.height = heis;
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
				$("#chargeMoney", $(".check")).each(function(i, tmp) {
					if (this.value == "") {
						alert("此处为必填项");
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
					var heis = parent.document.getElementById("mainframe5").offsetHeight - 27 + "px";
					parent.document.getElementById("mainframe5").style.height = heis;
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
	$("select.biaozhun").change(function(){
		$(this).parents("tr").find("input#chargeMoney").trigger("keydown");
	})
	$("input#chargeMoney,input#codeValue").live("keydown keyup",function(){
		var codeValue=$.trim($(this).parents("tr").find("input#codeValue").attr("value"));
		var chargeMoney=$.trim($(this).parents("tr").find("input#chargeMoney").attr("value"));
		if(isNaN(codeValue)||$.trim(codeValue)==''){
			$(this).parents("tr").find("input#codeValue").attr("value","");
		}
		if(isNaN(chargeMoney)||$.trim(chargeMoney)==''){
			$(this).parents("tr").find("input#chargeMoney").attr("value","");
		}
		var yj=parseFloat($.trim($(this).parents("tr").find("input#codeValue").attr("value"))==''?0:$.trim($(this).parents("tr").find("input#codeValue").attr("value")));
		var sj=parseFloat($.trim($(this).parents("tr").find("input#chargeMoney").attr("value"))==''?0:$.trim($(this).parents("tr").find("input#chargeMoney").attr("value")));
		$(this).parents("tr").find("input#arrearsMoney").val(yj-sj);
	})
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/enroll/ea_getStudentShargeList.jspa?dtDrivingAllInformation.dataTitle=05&dtDrivingAllInformation.staffID="+ staffID+"&dtDrivingAllInformation.relationID="+relationID;
}