$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		
	var title="";
	if(type=="c"){
		title="车辆使用信息管理公司汇总"
	}else if(type=="g"){
		title="车辆使用信息管理集团汇总"
	}else{
		title="车辆使用信息管理";
	}		
if(carID!=undefined&&carID!=""){		
	$('.JQueryflexme').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : '车辆使用信息管理',
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
				},   {
					name : '导出',
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
					}]
			});
}else{
		$('.JQueryflexme').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : title,
				minheight : 80,
				buttons : [{
					name : '查询',
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
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
}
	function action(com, grid) {
		switch (com) {
			case '添加' :
				conditionID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				document.addForm.reset();
				$("#numCarID").val(carID);
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (conditionID == "") {
					alert('请选择!');
					return;
				}
				document.updateForm.reset();
				$t = $("table#stafftableupdate");
				$p = $("tr#" + conditionID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModelup").jqmShow();
				break;
			case '删除' :
				if (conditionID == '') {
					alert("请选择！");
					return;
				}
				$f = $('#carEmployConditionForm');
				$f.find(':input#conditionID').val(conditionID);
				if (confirm("是否删除？")) {
					$("#carEmployConditionForm").attr("target", "hidden").attr(
							"action",
							basePath + "ea/employcondition/ea_deletcondi.jspa?pageNumber="+ pNumber  + "&employcondition.conditionID="+ conditionID);
					document.carEmployConditionForm.submit.click();
					token = 2;
//					$("tr#" + conditionID).remove();
//					conditionID = "";
					
				}
				break;
			case '查询':
				$("#jqModelcarSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath+ "ea/employcondition/ea_getemployconditionList.jspa?search="+ search+"&carInformation.carID="
						+ carID+"&type="+type;
				numback(url);
				break;
			case '导出' :
				var url = basePath+ "ea/employcondition/ea_showecondiExcel.jspa?";
				open(url);
				break;
		}
	}
		//单击事件
	$(".JQueryflexme tr[id]").click(function() {
		conditionID = this.id;
		if (personurl) {
			$("#mainframe").attr("src", personurl + conditionID);
		}
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});
	// 车辆添加取消	
	$("input.JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	//车辆添加保存
	$("input.JQuerySubmit").click(function() {
				$(".put3").trigger("blur");

					$("#addForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/employcondition/ea_savecondiList.jspa?pageNumber="
											+ pNumber + "&search=" + search);
					document.addForm.submit.click();
					token = 2;
					document.addForm.reset();
					
					return;
			});
		// 车辆修改取消	
	$("input.JQueryreturns").click(function() {
				$("#jqModelup").jqmHide();
				re_load();
			});
	//选择车辆返回
	$(".JQueryreturngoods").click(function() {
				notoken = 0;
				var numes="";
				$("#carNum", $("table#searchgood")).attr("value",numes);
				$("#carType", $("table#searchgood")).attr("value",numes);
				$(":input#parms").attr("value",numes);
				$(".jqmWindow", $("#goodsForm")).jqmHide();
			});
	// 新增物品
	$(".xzwp").click(function() {
				window.open(basePath
						+ "ea/goodsmanage/ea_getListGoodsManage.jspa?");
			});
	// 修改保存
	$("input.JQuerySubmits").click(function() {
		if ($("form .error").length) {
			return;
		}
		$("#updateForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/employcondition/ea_updatecondi.jspa?pageNumber="
								+ pNumber  + "&employcondition.conditionID="+ conditionID);
		document.updateForm.submit.click();
		token = 2;
	});
	//车辆查询
	$("#searchCar").click(function() {
		if(carID!=undefined&&carID!=""){
			$("#carIDs").val(carID);
		}
		$("#carSearchForm").attr(
				"action",
				basePath + "/ea/employcondition/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.getElementById("carSearchForm").submit();
		$("#carSearchTable").find(":input[name]").val("");
	})	;
			
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/employcondition/ea_getemployconditionList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&carInformation.carID="
						+ carID+"&type="+type;
}
