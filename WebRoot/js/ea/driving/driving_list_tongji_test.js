$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	
	var searchFormHtml=$("div#jqModelSearchss").html();	
	$("div#jqModelSearchss").remove();
	if(extensionStaffCoach=='extensionStaffStudent'){
			$('.JQueryflexme').flexigrid({
				height : 240,
				width : 'auto',
				minwidth : 30,
				title : '测试成绩统计 > '+searchFormHtml,
				minheight : 50,
				buttons : [ {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				} ,{
					name : '合格',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '不合格',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '缺考',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '误报',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
			$("div.bDiv",$("div.flexigrid")).css("height","375px");
			//$("div",$("div.flexigrid")).css({"margin":"0px","padding":"0px","border":"0px"});
	}else{
		$('.JQueryflexme').flexigrid({
				height : 240,
				width : 'auto',
				minwidth : 30,
				title : '考试成绩统计 > '+searchFormHtml,
				minheight : 50,
				buttons : [{
					name : '设置每页显示条数',
					bclass : 'mysearch',
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
		$("div.bDiv",$("div.flexigrid")).css("height","375px");
	}		
	function action(com, grid) {
		switch (com) {
			case '返回上级':
				if(other=='other')
				document.location.href=basePath+"page/ea/main/navigation/driving_management_kaoshi.jsp";
				else
				document.location.href=basePath+"page/ea/main/navigation/driving_management.jsp";
				break; 
			case '查询' :
				$("#jqModelSearchss").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/driving/ea_getStatisticsOfTestList.jspa?docstatus="+docstatus+"&sdate="+sdate+"&edate="+edate+"&search="+search+"&other="+other
						+"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID;
				numback(url);
				break;
			case '导出':
				var url = basePath
						+ "ea/driving/ea_getStatisticsOfTestList.jspa?docstatus="+docstatus+"&sdate="+sdate+"&edate="+edate+"&search="+search+"&excel=excel"
						+"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID;
				open(url);
				break;	
			case com:
				
				var examresult=(com=='合格'?'00':com=='不合格'?'01':com=='缺考'?'02':'03');
				var str = "";
				var wtj="";
				var wtjstrsScore="";
				$("input[type='checkbox']",$("table.JQueryflexme")).each(function(i) {
							if ($(this).is(':checked')) {
								str += $(this).val() + ",";
								$(this).parents("tr").find(":input.data").each(function(){
									$(this).attr("name","dtDrivingTestOtherMap["+i+"]."+$(this).attr("name"));
								});
								drivingtestid = $(this).val();
								var examresult1=$("tr#" + drivingtestid).find("span#examresult").text();
								var examresult2=$.trim($("tr#" + drivingtestid).find("input#examScore").val());
								if($.trim(examresult1)!="未统计"){
									wtj = "wtj";
								}
								if(isNaN(examresult2)||examresult2==""){
									wtjstrsScore = "wtjstrsScore";
								}
							}
				});
				if(wtj=="wtj"){
					alert("已选中人员中包含 “已统计学员!”请重新选择");
					return
				}
				if(wtjstrsScore=="wtjstrsScore"){
					alert("已选中人员中包含成绩 “不能为空或者不是数字!”请重新填写");
					return
				}
				if (str == "" || str.length == 0) {
					alert('请选择');
					return
				}
				if (confirm("确定继续？")) {
					$f = $('#addressForm');
					$f.find('input#strs').val(str);
					$f.find('input#examresult').val(examresult);
					$f.attr("target", "hidden")
								.attr(
										"action",
										basePath
												+ "ea/driving/ea_updateDrivingTestOfTest.jspa?pageNumber="
												+ pNumber );
					document.addressForm.submit.click();
					token = 2;
				}
				break;
		}
	}
	/*$(".JQueryflexme tr[id]").dblclick(function(){
		var staffID=$.trim($(this).find("span#studentid").text());
		var url = basePath
						+ "ea/stafftrack/ea_toSaveJsp.jspa?showType=edit&cstaff.staffID="
						+ staffID+"&baokaixuecheguan=baokaixuecheguan";//baokaixuecheguan参数  为了区分是否为学员报名模块   显示另外几个模块
		window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
	});	*/
	$(".chx").click(function(event) {
				/*var b = $(this).attr("checked");
				$(this).attr("checked", !b);*/
				event.stopPropagation();
	});
	$(".JQueryflexme tr[id]").click(function(event) {
				//var d = $("input.chx", $(this)).attr("checked");
				$("input.chx", $(this)).attr("checked", true);
	});
	$("#tosearch").click(function(){
                $(".put3").trigger("blur");
                   if ($("#SearchForm .error").length) {
                       alert("请填完所有必填项");
                       return;
                   }
                    $("#SearchForm").attr("action", basePath+"ea/driving/ea_toSearchStatisticsOfTest.jspa?other="+other
                    +"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID);
                    document.SearchForm.submit.click();
                });      
	$("tr").each(function(){
		$(this).find("select#firstExamPoints").find("option[value="+$(this).find("span#firstExamPoints").text()+"]").attr("selected","selected");
		$(this).find("select#secondExamPoints").find("option[value="+$(this).find("span#secondExamPoints").text()+"]").attr("selected","selected");
		$(this).find("select#firstExamresult").find("option[value="+$(this).find("span#firstExamresult").text()+"]").attr("selected","selected");
		$(this).find("select#secondExamresult").find("option[value="+$(this).find("span#secondExamresult").text()+"]").attr("selected","selected");
	});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/driving/ea_getStatisticsOfTestList.jspa?pageNumber=" + pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&docstatus="+docstatus+"&sdate="+sdate+"&edate="+edate+"&search="+search+"&other="+other
				+"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID;
}
