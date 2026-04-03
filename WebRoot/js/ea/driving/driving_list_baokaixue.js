$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 140,
				width : 'auto',
				minwidth : 30,
				title : '教务 > 报开学',
				minheight : 80,
				buttons : [/*{
					name : '报开学',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					},*/ {
					separator : true
				}, {
					name : '查看',
					bclass : 'edit',
					onpress : action
						// �
					}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// �
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
			case '返回上级':
				document.location.href=basePath+"page/ea/main/navigation/driving_management.jsp";
				break; 
			case '报开学' :
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
				$("input#strs",$("#baokaixueForm")).attr("value",str);
				$("#jqModelSearchss").jqmShow();
				break;
			case '查看' :
				var str = 0;
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								drivingprincipalid = $(this).val();
								str++;
							}
						});

				if (str != 1) {
					alert('请选择具体一个往来个人!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("div#jqModel");
				$p = $("tr#" + drivingprincipalid);
				$p.find("span[id]").each(function() {
							  $t.find("input#" + this.id+","+"select#" + this.id).val($(this).text());
						});
				$("#jqModel").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;			
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/driving/ea_getDrivingList.jspa?dateYuJing="+dateYuJing+"&docstatus="+docstatus+"&studentstatus="+studentstatus+"&title="+title+"&search="+search+"&conditions="+conditions 
						+"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID;
				numback(url);
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
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});	
	$("#tosearch").click(function() { // 查询
				$("#SearchForm").attr(
						"action",
						basePath
								+ "ea/driving/ea_toSearch.jspa?pageNumber="
								+ pNumber+"&docstatus="+docstatus+"&studentstatus="+studentstatus+"&title="+title+"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID);
				document.SearchForm.submit.click();
			});	
	/*$(".JQueryflexme tr[id]").dblclick(function(){
		var staffID=$.trim($(this).find("span#studentid").text());
		var url = basePath
						+ "ea/stafftrack/ea_toSaveJsp.jspa?showType=edit&cstaff.staffID="
						+ staffID+"&baokaixuecheguan=baokaixuecheguan";//baokaixuecheguan参数  为了区分个人服务办默认子模块   特显示另外几个模块
		window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
	})	*/	
	$("input.JQuerySubmit").click(function() {// 保存
					$(".put3").trigger("blur");
					if ($(".error").length) {
						alert("请填完所有必填项");
						return;
					}
					$("#baokaixueForm")
								.attr("target", "hidden")
								.attr(
										"action",
										basePath
												+ "ea/driving/ea_saveDrivingBaokaixue.jspa?pageNumber="
												+ pNumber );
					document.baokaixueForm.submit.click();
					token = 2;
		
	});				
});
function re_load() {
	if (token)
		document.location.href= basePath
				+ "ea/driving/ea_getDrivingList.jspa?pageNumber="
				+ pNumber+"&docstatus="+docstatus+"&studentstatus="+studentstatus+"&title="+title+"&search="+search+"&conditions="+conditions +"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID ;
}