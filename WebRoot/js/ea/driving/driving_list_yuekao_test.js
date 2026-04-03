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
				title : (docstatus=='01'?'科一':docstatus=='02'?'科二':docstatus=='03'?'科三':'科四')+"预约测试"+searchFormHtml,
				minheight : 80,
				buttons : [
				{
					name : '预约测试',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看',
					bclass : 'edit',
					onpress : action
						// �
					}, {
					separator : true
				}, {
					name : '打印测试记录登记表',
					bclass : 'printer',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}/**, {
					name : '学员考试信息',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}/**, {
					name : '批准入'+(docstatus=='01'?'科二（桩考）':docstatus=='02'?'科二（场地）':docstatus=='03'?'科三（路考）':'结业出证'),
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}*/]
			});
	$("div.bDiv",$("div.flexigrid")).css("height","375px");
	function action(com, grid) {
		switch (com) {
			/*case '返回上级':
				if(other=='other')
				document.location.href=basePath+"page/ea/main/navigation/driving_management_kaoshi.jsp";
				else
				document.location.href=basePath+"page/ea/main/navigation/driving_management.jsp";
				break; */
			case '预约测试' :
				var str = "";
				var message="";
				var docstatusStr="";
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								str += $(this).val() + ",";
								docstatusStr+=$(this).parents("tr").find("span#docstatus").text()+",";
								drivingprincipalid = $(this).val();
								var toTestOther=$("tr#" + drivingprincipalid).find("span#toTestOther").text();
								var testOtherResult=$("tr#" + drivingprincipalid).find("span#testOtherResult").text();
								var studentstatus=$("tr#" + drivingprincipalid).find("span#studentstatus").text();
								var testresult=$("tr#" + drivingprincipalid).find("span#testresult").text();
								if($.trim(studentstatus)=='已合格'){
									message="“考试已合格”";
								}else if($.trim(studentstatus)=='已约考'&&$.trim(testresult)=='未统计'){
									message="“考试已预约”";
								}else if($.trim(testOtherResult)=='已合格'){
									message="“测试已合格”";
								}else if($.trim(toTestOther)=='已预约'&&$.trim(testOtherResult)=='未统计'){
									message="“测试已预约”";
								}
							}
						});
				if (str == "" || str.length == 0) {
					alert('请选择');
					return
				}
				if(message!=""){
					alert("已选中人员中包含 "+message+"学员 无需约考!请重新选择");
					return
				}
				$("#ifPay").trigger("change");
				$("input#strs",$("#yuekaoForm")).attr("value",str);
				$("input#docstatusStr",$("#yuekaoForm")).attr("value",docstatusStr);
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
			case '打印测试记录登记表' :
				var str = "";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
					}
				});
				str=str.substring(0, str.lastIndexOf(","));
				var strs=str.split(",");
				if (strs == "" || strs.length == 0) {
					alert('请选择');
					return;
				}else if(strs.length > 1){
					alert('请选择一条记录');
					return;
				}
				var studentid=strs[0];
				window.open(basePath
						+ "/ea/driving/ea_toPrintOfDrivingTest.jspa?cstaff.staffID="
						+ encodeURI(studentid)+"&docstatus="+docstatus+"&print=print&extensionStaffCoach=extensionStaffStudent",'','fullscreen=1,toolbar=0,directories=0,status=0,menubar=0');
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/driving/ea_getDrivingList.jspa?docstatus="+docstatus+"&studentstatus="+studentstatus+"&title="+title+"&search="+search+"&conditions="+conditions+"&other="+other
						+"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID+"&identifier="+identifier;
				numback(url);
				break;
				
		/**case '学员考试信息':
				var str = 0;
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								drivingprincipalid = $(this).val();
								studentName=$("tr#"+drivingprincipalid).find("span#studentname").text();
								str++;
							}
						})

				if (str != 1) {
					alert('请选择具体一个往来个人!')
					return;
				}
				$("#mainframe")
						.attr(
								"src",
								basePath
										+ "ea/driving/ea_getDtDrivingTestList.jspa?dtDrivingPrincipal.drivingprincipalid="+drivingprincipalid+"&docstatus="+docstatus);
				break;	
			case '批准入'+(docstatus=='01'?'科二（桩考）':docstatus=='02'?'科二（场地）':docstatus=='03'?'科三（路考）':''):	
				$("#mainframe").attr("src","#")
				var str = "";
				var ytg="";
				var isTg="";
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								str += $(this).val() + ",";
								drivingprincipalid = $(this).val();
								var subjectsstatus=$("tr#" + drivingprincipalid).find("span#subjectsstatus").text();
								var testresult=$("tr#" + drivingprincipalid).find("span#testresult").text();
								if($.trim(testresult)!="合格"){
									isTg = "isTg";
								}
								if($.trim(subjectsstatus)=="是"){
									ytg = "ytg";
								}
							}
				})	
				if(isTg=="isTg"){
					alert("已选中人员中包含 “考试未合格学员!”请重新选择");
					return
				}
				if(ytg=="ytg"){
					alert("已选中人员中包含 “已进入下一科目学员!”请重新选择");
					return
				}
				if (str == "" || str.length == 0) {
					alert('请选择')
					return
				}
				if (confirm("确定继续？")) {
					$f = $('#cstaffForm');
					$f.find('input#strs').val(str);
					$("#cstaffForm")
								.attr("target", "hidden")
								.attr(
										"action",
										basePath
												+ "ea/driving/ea_saveNextSubject.jspa?pageNumber="
												+ pNumber );
					document.cstaffForm.submit.click();
					token = 2;
				}
				break;	*/					
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