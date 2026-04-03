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
				height : 200,
				width : 'auto',
				minwidth : 30,
				title : "预约测试汇总"+searchFormHtml,
				minheight : 80,
				buttons : [ {
					name : '预约测试',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '打印测试记录登记表',
					bclass : 'printer',
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
	$("div.bDiv",$("div.flexigrid")).css("height","375px");
	function action(com, grid) {
		switch (com) {
		case '预约测试' :
			var str = "";
			var docstatusStr="";
			var message="";
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
				return;
			}
			if(message!=""){
				alert("已选中人员中包含 "+message+"学员 无需重新预约!请重新选择");
				return;
			}
			$("#ifPay").trigger("change");
			$("input#strs",$("#yuekaoForm")).attr("value",str);
			$("input#docstatusStr",$("#yuekaoForm")).attr("value",docstatusStr);
			$("#jqModelSearchss").jqmShow();
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
						+ encodeURI(studentid)+"&print=print&extensionStaffCoach=extensionStaffStudent",'','fullscreen=1,toolbar=0,directories=0,status=0,menubar=0');
				break;
			case '培训记录表' :
				var url = basePath
				+ "page/ea/main/driving/printdriving.jsp";
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/driving/ea_getDrivingList.jspa?total="+total+"&search="+search+"&identifier="+identifier;
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
			})	;
	$("#tosearch").click(function() { // 查询
				$("#SearchForm").attr(
						"action",
						basePath
								+ "ea/driving/ea_toSearch.jspa?pageNumber="
								+ pNumber+"&total="+total+"&identifier="+identifier);
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
	/*$("#docstatus",$("#SearchForm")).change(function(){
		var checkVal=$(this).val();
		var optionVal="<option value=''>全部</option>";
		if(checkVal=="01"){
			optionVal+="<option value='03'>已分车</option>";
			optionVal+="<option value='04'>未报开学</option><option value='05'>已报开学</option>";
			optionVal+="<option value='06'>已约考</option><option value='07'>已合格</option>";
		}else if(checkVal !=""){
			optionVal+="<option value='02'>未预约培训</option><option value='03'>已预约培训</option>";
			optionVal+="<option value='05'>已培训</option><option value='06'>已约考</option>";
			optionVal+="<option value='07'>已合格</option>";
		}
		$("#studentstatus",$("#SearchForm")).html(optionVal);
	});*/
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
});
function re_load() {
	if (token)
		document.location.href= basePath
				+ "ea/driving/ea_getDrivingList.jspa?pageNumber="
				+ pNumber+"&total="+total+"&search="+search+"&identifier="+identifier;
}