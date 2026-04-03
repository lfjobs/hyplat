$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "选择入库",
				minheight : 80,
				buttons : [{
					name : '入库',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '驳回',
					bclass : 'delete',
					onpress : action
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
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '打印预览',
					bclass : 'printer',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});

	function action(com, grid) {
		switch (com) {
			case '入库' :
				var xuanze = $("#Inspectform")
						.find("input.JQuerypersonvalue[checked='true']");
				if (xuanze.length == 0) {
					alert("请选择！");
					return;
				}
				var num = parseInt($.trim($("tr#" + financialgoodid)
						.find("span#storageQuantity").text()));
				
				if (num == 0) {
					alert("此物品已全部入库");
					return;
				}
				$("#pware").children("option:eq(0)").attr("selected",
						"selected");
				$("#pware").trigger("change");
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/storage/ea_getChooseWarehousingList.jspa?search="
						+ search;
				numback(url);
				break;
			case '驳回':
				var xuanze = $("#Inspectform")
						.find("input.JQuerypersonvalue[checked='true']");
				var num=parseInt($.trim($("tr#" + financialgoodid)
						.find("span#storageQuantity").text()));
				if (xuanze.length == 0) {
					alert("请选择！");
					return;
				}	
				//var num = parseInt($.trim($("tr#" + financialgoodid).find("span#storageQuantity").text()));
				if (num == 0) {
					alert("此物品已全部入库");
					return;
				}
				if(xuanze.length>0 && num != 0){
				 if(confirm("确定驳回？")){
				 	 document.location.href=basePath+"ea/storage/ea_updatestatus.jspa?financialBillsGood.financialgoodID="+financialgoodid; 
				    }
				}
				break;
			case '查询' :
				$("#jqModelSearch2").jqmShow();
				break;
			case '打印预览':
				var url =basePath
				+ "page/ea/main/finance/invoicing/inspect_frame.jsp?search="+search
				+ "&pageNumber="+pNumber+"&pageCount="+pageCount+"&one=one0";
		         open(url);
				break;
		}
	}
	// if(event.srcElement.value==undefined){//点击checked input与点击tr之间的判断
	$("tr.xggoods").click(function(event) {
		$(this).find("input.JQuerypersonvalue").attr("checked", "true");
		financialgoodid = $(this).attr("id");
	});
	
	$("#tosubmit").click(function() {
		/*var warehouse = $("select#pware", $("#postSearchForm"))
				.find("option:selected").val();
		var area = $("select#parea", $("#postSearchForm"))
				.find("option:selected").val();
		var frame = $("select#pframe", $("#postSearchForm"))
				.find("option:selected").val();
		var position = $("select#place", $("#postSearchForm"))
				.find("option:selected").val();
		if (warehouse == "" || area == "" || frame == "" || position == "") {
			alert("请选择到位！");
			return;
		}*/
		var r = /^[0-9]*[1-9][0-9]*$/; // 正整数正则
		if (!r.test($.trim($("input#storageQuantity1").val()))
				|| $.trim($("input#storageQuantity1").val()) == "") {
			alert("必须为正整数或不能为空");
			return
		}
		var num = parseInt($.trim($("tr#" + financialgoodid)
				.find("span#storageQuantity").text()));// 为入库数量
		var num1 = parseInt($.trim($("input#storageQuantity1").val()));// 入库数量
		if (num1 > num) {
			alert("入库数量必须小于未入库数量");
			return
		}
		var seats=($("input#weizhi").val());
		$("input#seat", $("#Inspectform")).attr("value", seats);
		$("input#storageQuantity2", $("#Inspectform")).attr("value", num1);
		$("input#storageQuantity", $("#Inspectform")).attr("value", num - num1);
		$("input#financialgoodid", $("#Inspectform")).attr("value",
				financialgoodid);
		$("#Inspectform")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "/ea/storage/ea_saveChooseWarehousing.jspa?pageNumber="
								+ pNumber + "&search=" + search);
		document.Inspectform.submit.click();
		token = 13;
	});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$("#tosearch").click(function() {// 查询
				$("#postSearchForm2")
						.attr(
								"action",
								basePath
										+ "/ea/storage/ea_toSearchChooseWarehousing.jspa?pageNumber="
										+ pNumber);
				document.postSearchForm2.submit.click();
			});
});

function re_load() {
	document.location.href = basePath
			+ "/ea/storage/ea_getChooseWarehousingList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value") + "&search=" + search;
}
