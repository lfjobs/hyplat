$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	var title="";
	if(type=="c"){
		title="车辆状态管理公司汇总"
	}else if(type=="g"){
		title="车辆状态管理集团汇总"
	}else{
		title="车辆状态管理";
	}
		
		
  if(carID!=undefined&&carID!=""){
	$('.carstatus').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : '车辆状态管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, 
//				{
//					name : '修改',
//					bclass : 'edit',
//					onpress : action
//						// 当点击调用方法
//					}, {
//					separator : true
//				},
					
						{
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, 
				 {
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
  	$('.carstatus').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : title,
				minheight : 80,
				buttons : [
				{
					name : '查询',
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
				cnID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				document.addForm.reset();
				$("#numCarID").val(carID);
				$("#jqModel").jqmShow();
				break;
			case '导出' :
				var url = basePath+ "ea/carstatus/ea_showExcelCarstatus.jspa?carStatus.carID="+carID;
				open(url);
				break;
//			case'修改':
//				if(cnID==""){
//					alert("请选择！");
//					return;
//				}
//				$("#UpdatecnID").attr('value',cnID);
//				$t = $("table#stafftable2");
//				$p = $("tr#" + cnID);
//				$p.find("span[id]").each(function() {
//							$t.find(":input[name]#" + this.id).val($(this)
//									.text());
//						});
//				$("#updatejqModel").jqmShow();
//				break;
			case '查询' :
				$("#jqModelcarSearch").jqmShow();
				break;
			case '删除' :
				if (stID == '') {
					alert("请选择！");
					return;
				}
                 
				$f = $('#addForm');
				$f.find("#stIDs").val(stID);
				if (confirm("是否删除？")) {
					
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "/ea/carstatus/ea_deleteCarstatus.jspa?pageNumber="
											+ pNumber);
					document.addForm.submit.click();
					token = 2;
					stID = "";

				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/carstatus/ea_getCarStatusList.jspa?carInformation.carID="
						+ carID+"&search="+search+"&type="+type;
				numback(url);
				break;
		}
	}
	/**
	 *按条件查找
	 * */
	$("#searchCar").click(function(){
		if(carID!=undefined&&carID!=""){
			$("#carIDs").val(carID);
		}
		$("#carSearchForm").attr("action",basePath + "/ea/carnum/ea_toSearchCar.jspa?pageNumber="
				+ pNumber);
		document.getElementById("carSearchForm").submit();
		$("#carSearchTable").find(":input[name]").val("");
	});
	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				cnID = this.id;
			});
	$(".carstatus tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				stID = this.id;
			    
			});
	$(".carstatus tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				
				stID = this.id;
				action("修改");
			});
			// 车辆添加取消	
	$("input.JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
					re_load();
				});
				// 车辆修改取消	
	$("input.JQueryupdatereturn").click(function() {
				$("#updatejqModel").jqmHide();
					re_load();
				});
	//选择车辆返回
	$(".JQueryreturngoods").click(function() {
				notoken = 0;
				var numes="";
				$("#carNum", $("table#searchgood")).attr("value",numes);
				$("#carFrameNum", $("table#searchgood")).attr("value",numes);
				$("#carType", $("table#searchgood")).attr("value",numes);
				$(":input#parms").attr("value",numes);
				$(".jqmWindow", $("#goodsForm")).jqmHide();
			});
			//车牌号添加保存
	$("input.JQuerySubmit").click(function() {
			$("#addForm").attr("target", "hidden").attr(
					"action",
					basePath + "ea/carstatus/ea_saveCarStatusList.jspa?pageNumber="
							+ pNumber);
			document.addForm.submit.click();
			document.addForm.reset();
			token = 2;
			return;
	});
	// 车牌号修改保存
	$("#JQueryupdateSubmit").click(function() {
		if ($("form .error").length) {
			return;
		}
		if (cnID != "") {
			$("#updateForm").attr("target", "hidden").attr(
					"action",
					basePath
							+ "ea/carnum/ea_UpdateCarroadList.jspa?pageNumber="
							+ pNumber);
			document.updateForm.submit.click();
			document.updateForm.reset();
			token = 2;
			return;
		}
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/carstatus/ea_getCarStatusList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&carInformation.carID="+carID+"&type="+type;
}

