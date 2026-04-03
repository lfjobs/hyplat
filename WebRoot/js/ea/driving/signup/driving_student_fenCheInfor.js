$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	parent.document.getElementById("mainframe55").style.height = 80 + len * 27 + "px";
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	
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
				var heis = parent.document.getElementById("mainframe55").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe55").style.height = heis;
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
				if (select >2) {
					alert("请   “逐条”  添加");
					return;
				}
				notoken = 1;
				var re = 0;
				$("#carIdentifier,#carNumber,#carCoach,#carType", $(".check")).each(function() {
					if (this.value == "") {
						$(this).css("background-color", "red");
						re = 1;
					}
					if (this.value!=null&&this.value.length>30) {
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
				select--;
				if (drivingAllInformationID.substring(0, 2) == "sa") {
					$("#" + drivingAllInformationID).remove();
					drivingAllInformationID = '';
					var heis = parent.document.getElementById("mainframe55").offsetHeight - 27 + "px";
					parent.document.getElementById("mainframe55").style.height = heis;
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
	//物品返回
	$(".JQueryreturngoods").click(function() {
				notoken = 0;
				$(".jqmWindow", $("#goodsForm")).jqmHide();
				parent.document.getElementById("mainframe55").style.height = mainheught + 'px';
	})	;
	
	  $(".close").click(function(){// 取消
        $("#jqModel").jqmHide();
        re_load();
    });
    $("#xuanze").live("click",function(){
        $("#daoRu").attr("src", basePath + "ea/driving/ea_getCarInformationList.jspa?");
        mainheught = parent.document.getElementById("mainframe55").offsetHeight;
        parent.document.getElementById("mainframe55").style.height = 330 + 'px';
         $("#bankJqm").jqmShow();
    });
    $("#DaoRuFan").click(function(){// 返回
        $("#bankJqm").jqmHide();
        parent.document.getElementById("mainframe55").style.height = mainheught + 'px';
    });
    $("#DaoRuFanqd").click(function(){// 选择确定
        var checkform = $("#checkform", $("#bankJqm")).attr("value");
        var carID = window.frames["daoRu"].carID;
        if (carID == "") {
            alert("请选择");
            return;
        }
        var staffName = window.frames["daoRu"].$('tr#' + carID).find("span#staffName").text();
        var carsID = window.frames["daoRu"].$('tr#' + carID).find("span#carID").text();
        var carNum = window.frames["daoRu"].$('tr#' + carID).find("span#carNum").text();
         var carType = window.frames["daoRu"].$('tr#' + carID).find("span#carType").text();
        
        $("input#carCoach", $("tr#" + drivingAllInformationID)).attr("value", staffName).trigger("blur");
        $("input#carID", $("tr#" + drivingAllInformationID)).attr("value", carsID).trigger("blur");
        $("input#carIdentifier", $("tr#" + drivingAllInformationID)).attr("value", carNum).trigger("blur");
        $("input#carType", $("tr#" + drivingAllInformationID)).attr("value", carType).trigger("blur");
        $("#daoRu").attr("src", "");
        $("#bankJqm").jqmHide();
        parent.document.getElementById("mainframe55").style.height = mainheught + 'px';
    });
	
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/enroll/ea_getFenCheShuttleList.jspa?dtDrivingAllInformation.dataTitle=09&dtDrivingAllInformation.staffID="+ staffID+"&dtDrivingAllInformation.relationID="+relationID;
}

