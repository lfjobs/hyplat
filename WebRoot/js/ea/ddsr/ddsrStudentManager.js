$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '学员信息管理',
				minheight : 80,
				buttons : [{
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
					name : '查看',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, /*{
					name : '导出',
					bclass : 'excel',
					onpress : action						
					}, {
					separator : true
				},*/{
					name : '修改详细信息',
					bclass : 'edit',
					onpress : action						
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
			case '删除' :
				var str = "";
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								str += $(this).val() + ",";								
							}
						});
				if (str == "" || str.length == 0) {
					alert('请选择学员');
					return
				}				
				if (confirm("是否删除？")) {					
					$.ajax(
							{type:"POST",
							url:basePath + "ea/ddsrstudentManager/ea_doDdsrStudentManagerAction.jspa?innerAction=delDdsrStudent",
							data:{  
								    delStudent:str
							      },
							dataType:"json",
							success:function(data)
							{						
							   try{	
								   $("[name='chbox']").each(function() {
										if ($(this).is(':checked')) {
											 $("tr#" + $(this).val()).remove();																		
										}
									});								  								   
								   token = 11;								   
							   }catch(err)
							   {
							   	alert("系统发生异常,请联系管理员.");
							   }	
							},
							error : function(data) {						
								alert("系统发生异常,请联系管理员.");
							}
						}
					);					
				}
				break;
			case '修改' :
				var str = "";
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								str += $(this).val() + ",";	
								stud_key = str;
							}
						});
				if (str == "" || str.length == 0) {					
					alert('请选择学员');
					return
				}else if (str.split(",").length>2){
					alert('只能选择一个学员');
					return
				}
				if (stud_key == "") {
					alert('请选择学员');
					return
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + stud_key);
				$t.find('img#photo').attr("src",
						basePath + $p.find("span#photo").text());
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});				
				
				$("#sexSelect").val($p.find("span#sex").html());
				$("#subjKeySelect").val($p.find("span#subjKey").html());
				$("#studStatusSelect").val($p.find("span#studStatus").html());
				$("#studInforclassSelect").val($p.find("span#studInforclass").html());
				$("#studStarSelect").val($p.find("span#studStar").html());
				$("#studCredentialsSelect").val($p.find("span#studCredentials").html());
								
				//$ttt = $p.find("span#subjKeyVal").html();				
				//$("#subjTypeSelect").val($ttt);
				//$("#subjTypeSelect option[text='" + $ttt + "' ]").attr("selected", true);	
				
				$("#jqModel").jqmShow();
				break;	
			case '查看' :
				var str = "";
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								str += $(this).val() + ",";	
								stud_key = str;
							}
						});
				if (str == "" || str.length == 0) {					
					alert('请选择学员');
					return
				}else if (str.split(",").length>2){
					alert('只能选择一个学员');
					return
				}
				if (stud_key == "") {
					alert('请选择要查看的学员');
					return
				}
				
				$t = $("table#showstafftable");
				$p = $("tr#" + stud_key);
				$t.find('img#photo').attr("src",
						basePath + $p.find("span#photo").text());
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#show_" + this.id).val($(this)
									.text());
						});
				
				$("#jqModelShow").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '修改详细信息' :
				var str = "";
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								str += $(this).val() + ",";	
								stud_key = str;
							}
						});
				if (str == "" || str.length == 0) {					
					alert('请选择学员');
					return
				}else if (str.split(",").length>2){
					alert('只能选择一个学员');
					return
				}
				if (stud_key == "") {
					alert('请选择要修改的学员');
					return
				}
				$p = $("tr#" + stud_key);
				status = $p.find("span#status").text();
				staffID = $p.find("span#staffID").text();
				var url = basePath
						+ "ea/humanResource/ea_getHumanResource.jspa?showType=edit&cstaff.staffID="
						+ staffID+"&cstaff.status="+status;
				window.open(url, '','scrollbars=yes,resizable=yes,channelmode');				
				break;
			case '设置每页显示条数' :
				var url = basePath + "ea/ddsrstudentManager/ea_doDdsrStudentManagerAction.jspa?innerAction=showDdsrStudentList";
				numback(url);
				break;			
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	// 复选框选中
	$(".JQuerypersonvalue").bind("click", function(event) {				
		var b = $(this).attr("checked");				
		$(this).attr("checked", !b);
		stud_key = d ? "" : this.value;				
	});
	$(".JQueryflexme tr[id]").click(function() {
		var d = $("input.JQuerypersonvalue", $(this)).attr("checked");
		$("input.JQuerypersonvalue", $(this)).attr("checked", !d);
		stud_key = d ? "" : this.id;				
	});
	$(".JQuerySubmit").click(function() {		
		$(".put3", $("table#stafftable")).trigger("blur");
		if ($("#cstaffForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		$("#cstaffForm").attr("target", "hidden").attr("action",
				basePath + "ea/ddsrstudentManager/ea_doDdsrStudentManagerAction.jspa?innerAction=updateDdsrStudent&pageNumber=" + pNumber);		
		if (stud_key == "") {			
			document.cstaffForm.submit.click();
			$("#cstaffForm").find(":input[name]").val("");
			token = 1;
			return;
		}		
		document.cstaffForm.submit.click();
		token = 2;
	});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".JQuerySearchSubmit").click(function(){
		$(".put3", $("table#jqModelSearch")).trigger("blur");
		if ($("#jqModelSearch .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		$("#jqModelSearch").jqmHide();
		var dt = new Date();
		$("#searchForm").attr("action",
				basePath + "ea/ddsrstudentManager/ea_doDdsrStudentManagerAction.jspa?innerAction=showDdsrStudentList&datetime=" + dt.getTime());
		document.searchForm.submit.click();		
	});
	$(".JQuerySearchReturn").click(function(){
		$("#jqModelSearch").jqmHide();
	});
	
});
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/ddsrstudentManager/ea_doDdsrStudentManagerAction.jspa?pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");		
	}
}