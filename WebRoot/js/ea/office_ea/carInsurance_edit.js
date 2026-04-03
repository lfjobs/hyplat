$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		
	 var title="";
	if(type=="c"){
		title="购置保险信息公司汇总"
	}else if(type=="g"){
		title="购置保险信息集团汇总"
	}else{
		title="购置保险信息";
	}
	 if(carID!=undefined&&carID!=""){
	$('.JQueryflexme').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : '购置保险信息',
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
				insuranceID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				document.addForm.reset();
				$("#numCarID").val(carID);
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (insuranceID == "") {
					alert('请选择!');
					return;
				}
				document.updateForm.reset();
				$t = $("table#stafftableupdate");
				$p = $("tr#" + insuranceID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModelup").jqmShow();
				break;
			case '删除' :
				if (insuranceID == '') {
					alert("请选择！");
					return;
				}
				$f = $('#carInsuranceForm');
				$f.find(':input#insuranceID').val(insuranceID);
				if (confirm("是否删除？")) {
					$("#carInsuranceForm").attr("target", "hidden").attr(
							"action",
							basePath + "ea/carinsurance/ea_deleteInsurance.jspa?pageNumber="+ pNumber  + "&carInsurance.insuranceID="+ insuranceID);
					document.carInsuranceForm.submit.click();
					token = 2;
//					$("tr#" + insuranceID).remove();
//					insuranceID = "";
					
				}
				break;
			case '查询':
				$("#jqModelcarSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath+ "ea/carinsurance/ea_getCarInsuranceList.jspa?search="+ search+"&carInformation.carID="+carID+"&type="+type;
				numback(url);
				break;
			case '导出' :
				var url = basePath+ "ea/carinsurance/ea_showInsuranceExcel.jspa?";
				open(url);
				break;
		}
	}
		//单击事件
	$(".JQueryflexme tr[id]").click(function() {
		insuranceID = this.id;
		if (personurl) {
			$("#mainframe").attr("src", personurl + insuranceID);
		}
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});
	$(".JQueryflexme tr[id]").dblclick(function(){
                    action('修改');//当双击时出发 action方法.等价于先选中再点击修改按钮
          });
	$("#startTime",$("#addForm")).blur(function() {
				var start = $(this).val();
				if (start != '') {
					$("#start").attr("value", start);
				}
			});
	$("#endTime",$("#addForm")).blur(function() {
				var end = $(this).val();
				if (end != '') {
					$("#end").attr("value", end);
				}
			});
	// 车辆添加取消	
	$("input.JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	//车辆添加保存
	$("input.JQuerySubmit").click(function() {
				$(".put3").trigger("blur");
				if ($("form .error").length) {
					return;
				}
				 $("#addForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/carinsurance/ea_savecarinsuranceList.jspa?pageNumber="
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
	// 新增物品
	$(".xzwp").click(function() {
				window.open(basePath
						+ "ea/goodsmanage/ea_getListGoodsManage.jspa?");
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
								+ "ea/carinsurance/ea_updateCarinsurance.jspa?pageNumber="
								+ pNumber  + "&carInsurance.insuranceID="+ insuranceID);
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
				basePath + "/ea/carinsurance/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.getElementById("carSearchForm").submit();
		$("#carSearchTable").find(":input[name]").val("");
	})	;
			
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/carinsurance/ea_getCarInsuranceList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&carInformation.carID="+carID+"&type="+type;
}

