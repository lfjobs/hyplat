$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	
				
	var title="";
	if(type=="c"){
		title="车辆资产信息公司汇总"
	}else if(type=="g"){
		title="车辆资产信息集团汇总"
	}else{
		title="车辆资产信息管理";
	}	
	if(carID!=undefined&&carID!=""){		
	$('.JQueryflexme').flexigrid({
				height : 145,
				width : 'auto',
				minwidth : 30,
				title : '车辆资产信息',
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
	}else{
			$('.JQueryflexme').flexigrid({
				height : 145,
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
				assetcID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				document.addForm.reset();
				$("#numCarID").val(carID);
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (assetcID == '') {
					alert("请选择！");
					return;
				}
				$t = $("table#stafftable2");
				$p = $("tr#" + assetcID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModelUpdate").jqmShow();
				break;
			case '查询' :
				$("#jqModelcarSearch").jqmShow();
				break;
			case '删除' :
				if (assetcID == '') {
					alert("请选择！");
					return;
				}
				$f = $('#carAssetcForm');
				$f.find(':input#assetcID').val(assetcID);
				if (confirm("是否删除？")) {
					$("#carAssetcForm").attr("target", "hidden").attr(
							"action",
							basePath + "ea/carassetcinformation/ea_deleteCarAssetcinformation.jspa?pageNumber="+ pNumber  + "&carassetcinformation.assetcID="+ assetcID);
					document.carAssetcForm.submit.click();
					token = 2;
//					$("tr#" + assetcID).remove();
//					assetcID = "";
				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/carassetcinformation/ea_getCaraffetcinformationList.jspa?search="+ search+"&carInformation.carID="
						+ carID+"&type="+type;
				numback(url);
				break;
			case '导出' :
				var url = basePath
						+ "ea/carassetcinformation/ea_showCarsafeinformationExcel.jspa?carassetcinformation.carID="
						+ carID;
				open(url);
				break;

		}
	}
	//车辆资产添加保存
	$("input.JQuerySubmit").click(function() {
					$("#addForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/carassetcinformation/ea_saveCarAssetcinformationList.jspa?pageNumber="
											+ pNumber );
					document.addForm.submit.click();
						token = 2;
					document.addForm.reset();
				
					return;

			});
			
			/**
			 * 按条件查找
			 */
	$("#searchCar").click(function() {
		
		if(carID!=undefined&&carID!=""){
			$("#carIDs").val(carID);
		}
		$("#carSearchForm")
				.attr(
						"action",
						basePath
								+ "/ea/carassetcinformation/ea_toSearch.jspa?pageNumber="
								+ pNumber);
		document.getElementById("carSearchForm").submit();
		$("#carSearchTable").find(":input[name]").val("");
	});
	$(".JQueryflexme tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		assetcID = this.id;
	});
	// 车辆添加取消	
	$("input.JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	// 车辆修改取消	
	$("input.JQueryUpdateReturn").click(function() {
				$("#jqModelUpdate").jqmHide();
				re_load();
			});
	// 车辆修改保存
	$("input.JQueryUpdateSubmit").click(function() {
		if ($("form .error").length) {
			return;
		}
		if (assetcID != "") {
			$("#UpdateForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/carassetcinformation/ea_UpdateCarAssetcinformationList.jspa?pageNumber="
									+ pNumber + "&carassetcinformation.assetcID="+ assetcID );
			document.UpdateForm.submit.click();
			token = 2;
			return;
		}
			});
	// 选择车辆返回
	$(".JQueryreturngoods").click(function() {
		notoken = 0;
		var numes="";
		$("#carNum", $("table#searchgood")).attr("value",numes);
		$("#carFrameNum", $("table#searchgood")).attr("value",numes);
		$("#carType", $("table#searchgood")).attr("value",numes);
		$(":input#parms").attr("value",numes);
		$(".jqmWindow", $("#goodsForm")).jqmHide();
	});
	// 新增物品
	$(".xzwp").click(function() {
				window.open(basePath
						+ "ea/goodsmanage/ea_getListGoodsManage.jspa?");
			});
$(".CarAssetcInformation tr[id]").dblclick(function() {

	$("input.JQuerypersonvalue", $(this))
	.attr("checked", "checked");
		assetcID = this.id;
		action("修改");
	});
});
function re_load() {
if (token)
document.location.href = basePath
		+ "ea/carassetcinformation/ea_getCaraffetcinformationList.jspa?carassetcinformation.carID="
		+ carID + "&pageNumber=" + pNumber + "&pageForm.pageNumber="
		+ $("#pageNumber").attr("value")+"&carInformation.carID="
						+ carID+"&type="+type;
}
