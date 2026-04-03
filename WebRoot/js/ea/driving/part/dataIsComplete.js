$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	var searchFormHtml=$("div#jqModelSearch").html();	
	$("div#jqModelSearch").remove();
	$('.JQueryflexme').flexigrid({
				height : 120,
				width : 'auto',
				minwidth : 30,
				title : "证件"+(dataIsComplete=='00'?'齐全':'不齐全')+"学员"+searchFormHtml,
				minheight : 80,
				buttons : [{
					name : dataIsComplete=='00'?'设置证件不齐全':'设置证件齐全',
					bclass : dataIsComplete=='00'?'delete':'add',
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
				},  {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
					name : '证件管理',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	$("div.bDiv",$("div.flexigrid")).css("height","355px");
	function action(com, grid) {
		switch (com) {
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/driving/ea_getDrivingList.jspa?docstatus="+docstatus+"&studentstatus="+studentstatus+"&title="+title+"&search="+search+"&conditions="+conditions+"&other="+other
						+"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID+"&identifier="+identifier;
				numback(url);
				break;
			case '导出':
				var url = basePath
				+ "ea/driving/ea_exportOfExcelOfDriving.jspa?docstatus="+docstatus+"&studentstatus="+studentstatus+"&title="+title+"&search="+search+"&conditions="+conditions+"&other="+other
				+"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID+"&identifier="+identifier;
				window.open(url);
				break;	
			case '证件管理' :
				var str = 0;
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								staffID = $.trim($(this).parents("tr").find("span#studentid").text());
								str++;
							}
				});
				if (str != 1) {
					alert('请选择具体一个往来个人!');
					return;
				}
				URL=basePath + "ea/credentials/ea_getListCredentials.jspa?credentials.staffID=";
				$("#mainframe13").css({"height" :"auto"}).attr("src",URL+ staffID);	
				$("div.bDiv",$("div.flexigrid")).css("height","170px");
				break;		
			case dataIsComplete=='00'?'设置证件不齐全':'设置证件齐全':
				var str = "";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
					}
				});
				if (str == "" || str.length == 0) {
					alert('请选择');
					return
				}	
				if (confirm("确定继续？")) {
					$f = $('#cstaffForm');
					$f.find('input#strs').val(str);
					$f.find('input#dataIsComplete').val(dataIsComplete=='00'?'01':'00');
					$("#cstaffForm")
								.attr("target", "hidden")
								.attr(
										"action",
										basePath
												+ "/ea/baokaixue/ea_setDataIsCompleteOfStudent.jspa?pageNumber="
												+ pNumber );
					document.cstaffForm.submit.click();
					token = 2;
				}
				break;					
		}
	}
	// 复选框选中
	$(".chx").live("click", function(event) {
				var b = $(this).attr("checked");
				$(this).attr("checked", !b);
			});
	$(".JQueryflexme tr[id]").click(function() {
				var d = $("input.chx", $(this)).attr("checked");
				$("input.chx", $(this)).attr("checked", !d);
			})	;
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});	
	$("#tosearch").click(function() { // 查询
				$("#SearchForm").attr(
						"action",
						basePath
								+ "ea/driving/ea_toSearch.jspa?pageNumber="
								+ pNumber+"&docstatus="+docstatus+"&studentstatus="+studentstatus+"&title="+title
								+"&other="+other+"&extensionStaffCoach="+extensionStaffCoach
								+"&dtDrivingPrincipal.studentid="+studentID+"&identifier="+identifier );
				document.SearchForm.submit.click();
			});		
	$("input.JQuerySubmit").click(function() {// 保存
					$(".put3").trigger("blur");
					if ($(".error").length) {
						alert("请填完所有必填项");
						return;
					}
					$("#yuekaoForm")
								.attr("target", "hidden")
								.attr(
										"action",
										basePath
												+ "ea/driving/ea_saveDrivingYuekaoOfTest.jspa?pageNumber="
												+ pNumber );
					document.yuekaoForm.submit.click();
					token = 2;
		
	});	
	$("#ifPay").change(function(){
		if($(this).val()=='00'){
			$("tr.ifPayNote").hide();
			$("input#payName").removeClass();
			$("input#payMoney").removeClass();
			$("input#payName").parent().find(".error").remove();
			$("input#payMoney").parent().find(".error").remove()
		}else{
			$("tr.ifPayNote").show();
			$("input#payName").addClass("put3");
			$("input#payMoney").addClass("put3 isNaN");
		}
	})		
});
function re_load() {
	if (token)
		document.location.href= basePath
				+ "ea/driving/ea_getDrivingList.jspa?pageNumber="
				+ pNumber+"&docstatus="+docstatus+"&studentstatus="+studentstatus
				+"&title="+title+"&search="+search+"&conditions="+conditions
				+"&other="+other+"&extensionStaffCoach="+extensionStaffCoach
				+"&dtDrivingPrincipal.studentid="+studentID+"&identifier="+identifier ;
}