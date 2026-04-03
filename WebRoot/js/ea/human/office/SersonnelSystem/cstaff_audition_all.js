$(document).ready(function() {
	$("#jqModel").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$("#jqMode2").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
		$('.JQueryflexme').flexigrid({
			height : 300,
			width : 'auto',
			minwidth : 30,
			title : '面试结果管理',
			minheight : 80,
			buttons : [{
				name : '删除',
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
			}, 
				{
				name : '报到入职',
				bclass : 'excel',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '打印登记表',
				bclass : 'excel',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '打印登记数据',
				bclass : 'excel',
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
			case '打印登记表':
				var url = basePath
						+ "/page/ea/main/human/office/production/printBasicInformation.jsp?star=入职登记表";
				window.open(encodeURI(url));
				break;
			 case '打印登记数据' :
					if (auditionID == "") {
						alert("请选择人员！");
						return;
					}
					var staffid = $("tr#"+auditionID).find("span#staffID").text();
					var url = basePath
							+ "ea/saudition/ea_printBIAud.jspa?star=入职登记表&staffID="
							+ staffid;
					window.open(encodeURI(url));
					break;
			case '报到入职' :
				if (auditionID == "") {
					alert('请选择人员');
					return
				}
				if($("tr#"+auditionID).find("span#registerDate").text() != ""){
					alert('选择人员已报道入职');
					return
				}
				$p = $("tr#" + auditionID);
				if ($p.find("span#status").text().length == "6") {
					alert('面试未通过');
					return
				}
				$t = $("#employmentForm");
				document.employmentForm.reset();

				$p.find("span[id]").each(function() {
							$t.find("input#" + this.id).val($(this).text());
						});
				$("#jqMode2").jqmShow();
				break;
			case '查看' :
				if (auditionID == "") {
					alert('请选择人员');
					return
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + auditionID);
				$p.find("span[id]").each(function() {
							$t.find("input#" + this.id).val($(this).text());
							if(this.id == "experience"){
								$t.find("textarea#" + this.id).val($(this).text());
							}
						});
				$("#jqModel").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/saudition/ea_getAuditionkb.jspa?start="+start+"&staffID="
						+ staffID+"&d="+Math.ceil(Math.random()*1000);
				numback(url);
				break;
			case '删除' :
				if (auditionID == '') {
					alert("请选择！");
					return;
				}
				if($("tr#"+auditionID).find("span#status").text().trim() != "面试未通过"){
					alert('不可以删除！');
					return
				}
				$f = $('#auditionForm');
				if (notoken)
					return;
				notoken = 1;
				$f.attr("target", "hidden").attr(
					"action",
					basePath + "ea/saudition/ea_delAudition.jspa?audition.auditionID="
							+ auditionID);
				document.auditionForm.submit.click();
				$("tr#" + auditionID).remove();
				auditionID = "";
				token = 11;
				break;	
		}
	}
	$("input.JQueryreturn").click(function(){// 取消
                    $("#jqMode2").jqmHide();
                    re_load();
                }); 
	$(".JQueryflexme tr[id]").click(function() {
		$("input.JQueryaudition", $(this))
				.attr("checked", "checked");
		auditionID = this.id;
		staffID = $("#"+this.id).find("input#staffID").val();
	});
	$("#auditionBC").click(function() {
		$("form :input:.put3").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#employmentForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/saudition/ea_saveAllAudition.jspa?pageNumber="
						+ ppageNumber + "&status=1"+ "&start=3");
		document.getElementById("employmentForm").submit();
		$(".jqmWindowcss2").jqmHide();
		token = 6;
	});
});

function re_skip(){
	document.location.href = basePath +
		"ea/corganization/ea_getCompanyMessage.jspa?type=e&mold=m";
}

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/saudition/ea_getAuditionkb.jspa?start="+start+"&staffID="
				+ staffID+"&d="+Math.ceil(Math.random()*1000);
}