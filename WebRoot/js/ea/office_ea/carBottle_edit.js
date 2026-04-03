$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	var title="";
	if(type=="c"){
		title="车用瓶使用管理公司汇总"
	}else if(type=="g"){
		title="车用瓶使用管理集团汇总"
	}else{
		title="车用瓶使用管理";
	}
	if(carID!=undefined&&carID!=""){
	$('.JQueryflexme').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : '车用瓶使用管理',
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
				bottleID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				document.addForm.reset();
				$("#numCarID").val(carID);
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (bottleID == "") {
					alert('请选择!');
					return;
				}
				document.updateForm.reset();
				$t = $("table#stafftableupdate");
				$p = $("tr#" + bottleID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModelup").jqmShow();
				break;
			case '删除' :
				if (bottleID == '') {
					alert("请选择！");
					return;
				}
				$f = $('#bottleForm');
				$f.find(':input#bottleID').val(bottleID);
				if (confirm("是否删除？")) {
					$("#bottleForm").attr("target", "hidden").attr(
							"action",
							basePath + "ea/bottle/ea_deletbottle.jspa?pageNumber="+ pNumber  + "&bottle.bottleID="+ bottleID);
					document.bottleForm.submit.click();
					token = 2;
//					$("tr#" + bottleID).remove();
//					bottleID = "";
					
				}
				break;
			case '查询':
				$("#jqModelcarSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath+ "ea/bottle/ea_getBottleList.jspa?search="+ search+"&carInformation.carID="
						+ carID+"&type="+type;
				numback(url);
				break;
			case '导出' :
				var url = basePath+ "ea/bottle/ea_showbottleExcel.jspa?";
				open(url);
				break;
		}
	}
		//单击事件
	$(".JQueryflexme tr[id]").click(function() {
		bottleID = this.id;
		if (personurl) {
			$("#mainframe").attr("src", personurl + bottleID);
		}
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});
	$(".JQueryflexme tr[id]").dblclick(function(){
                    action('修改');//当双击时出发 action方法.等价于先选中再点击修改按钮
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
				var dianhua=$("#telephone",$("#addForm")).val();
				var filters= (/(^(\d{4}-)?\d{7,8}(-\d{1,3})?$)/.test(dianhua) )|| (/^1[3|4|5|8][0-9]\d{8}$/.test(dianhua) )||(dianhua == "") ; 
						if(("0000000"==(this.value))||("00000000"==(this.value))||(filters==false)){
						alert("请输入正确的电话号码");
						return;
							}
					$("#addForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/bottle/ea_savebottleList.jspa?pageNumber="
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
								+ "ea/bottle/ea_updatebottle.jspa?pageNumber="
								+ pNumber  + "&bottle.bottleID="+ bottleID);
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
				basePath + "/ea/bottle/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.getElementById("carSearchForm").submit();
		$("#carSearchTable").find(":input[name]").val("");
	});	
			
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/bottle/ea_getBottleList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&carInformation.carID="
						+ carID+"&type="+type;
}

