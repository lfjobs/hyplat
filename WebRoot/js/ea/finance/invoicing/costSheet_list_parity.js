$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
	// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	var query = "<form method='post' name='searchf' id='searchf'>"
			+ "<input type='submit' style='display:none;' name='submit' />"
			+ "<input type='hidden' name='search' value='search'/>"
			+ "<table border='0'>"
			+ "<tr>"
			+ "<td><strong>预算/招标比价管理</strong>&nbsp;&nbsp;&nbsp;&nbsp;项目名称</td><td><input type='text' name='cashierBills.projectName' size='13'/></td>"
			+ "<td>主项目</td><td><input type='hidden' class='xmtype' name='cashierBills.xmtype'/><input type='text' class='xmtypename' size='13'/>"
			+ "</td><td>凭证号</td><td><input type='text' name='cashierBills.journalNum' size='13'/></td><td>责任人</td><td><input type='text' name='cashierBills.staffName' size='10'/></td>"
			+ "<td>制单人</td><td><input type='text' name='cashierBills.inputName' size='10'size='10'/></td>"
			+ "<td>制单时间</td><td><input type='text' onfocus='daytime(this);' size='13' name='startTime'/>至<input type='text' onfocus='daytime(this);' size='13' name='endTime'/></td>"
			+ "<td><input type='button' value=' 查询 ' class='input-button' style='margin:2px;'id='tosearch'/></td>"
			+ "</tr></table></form>";

	$('.flexme11').flexigrid({
		height : 350,
		width : 'auto',
		minwidth : 30,
		title : query,
		minheight : 80,
		buttons : [ {
			name : '查看',
			bclass : 'see',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '打印预览',
			bclass : 'printer',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '驳回',
			bclass : 'delete',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '价格比较',
			bclass : 'compare',
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
		}, {
			separator : true
		} ]
	});

	function action(com, grid) {
		switch (com) {
		case '查看':
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}

			window.open(basePath
					+ "/ea/costsheet/ea_toEditCostSheet.jspa?pageNumber=" + pNumber
					+ "&cashierBills.cashierBillsID=" + cashierBillsID+"&vuvtype=edit");
			break;
		case '打印预览':
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}
			window
					.open(basePath
							+ "/ea/costsheet/ea_toEditCostSheet.jspa?cashierBills.cashierBillsID="
							+ cashierBillsID+"&vuvtype=printcsb");
			break;
		
		case '价格比较':
			// 获取项目物品
			window.open(basePath
					+ "ea/product/ea_getBudgetGoodList.jspa?result=first");
			break;
		case '驳回':
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}
			if (confirm("确定驳回？")) {
				var url = basePath
						+ "ea/costsheet/sajax_ea_viaCostSheet.jspa?date="
						+ new Date().toLocaleString();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					data:{
						"cashierBills.cashierBillsID":cashierBillsID
					},
					success : function cbf(data) {

						alert("操作成功！");

						token = 2;
						re_load();

					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
			}
			break;
		case '导出':
			var url = basePath + "ea/costsheet/ea_exportExcel.jspa?search=" + search+"&jumptype="+jumptype;
			open(url);
			break;
		case '设置每页显示条数':
			var url = basePath
					+ "/ea/costsheet/ea_getCostSheetList.jspa?search=" + search
					+ "&jumptype=" + jumptype;
			numback(url);
			break;
		
		}
	}

	// 这一行的单击事件
	$(".flexme11 tr[id]").dblclick(function() {

		cashierBillsID = this.id;

		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");

		action("查看 ");

	});

	// 这一行的单击事件
	$(".flexme11 tr[id]").click(function() {

		cashierBillsID = this.id;

		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
		

	});

	/**
	 * 
	 * 键入时查询项目分类
	 */
	$(".xmtypename").focus(function() {
		getxmtype($(this));
	}).keyup(function() {

		getxmtype($(this));

	});

	// 查询按钮单击事件
	$("#tosearch").click(
			function() {

				$("#searchf").attr(
						"action",
						basePath + "/ea/costsheet/ea_toSearch.jspa?pageNumber="
								+ pNumber + "&jumptype=" + jumptype);
				document.searchf.submit.click();
			});

});

function re_load() {
	if (token) {
		document.location.href = basePath
				+ "/ea/costsheet/ea_getCostSheetList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&search="
				+ search + "&jumptype=" + jumptype;
	}
}



//键入时查询项目分类

function getxmtype(obj){
	
	var left = $(obj).position().left;
	var top = $(obj).position().top;
		var url = basePath
				+ "ea/promanage/sajax_ea_getXmtypeByCode.jspa?date="
				+ new Date().toLocaleString();
		$.ajax({
			url : encodeURI(url),
			type : "post",
			async : false,
			dataType : "json",
			data : {
				parameter : $.trim($(obj).val())
			},
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var xmList = member.xmlist;
				
				var str="";

				for (var i = 0; i < xmList.length; i++) {
					if(xmList[i].isLeaf =="00"){
						str+="&nbsp;&nbsp;<span><a href='#' onclick='selectz(this);'>("+xmList[i].codeSn+")"+xmList[i].codeValue+"</a></span><br/>";
					}else{
					str+="&nbsp;&nbsp;<span>("+xmList[i].codeSn+")"+xmList[i].codeValue+"</span><br/>";
					}
				}
				if(str==""){
					str="&nbsp;无搜索结果";
				}
				
				$("#treeBoxs").html(str);

					
				    $("#jqModel").css({
						position : "absolute",
						left : left,
						top : top+23
					}).show();
				    

			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});

}

//选中主项目
function selectz(obj){
	$("#jqModel").hide();
	var codeValues=$(obj).text();
	$(".xmtypename").val(codeValues.substring(codeValues.indexOf(")")+1));
	$(".xmtype").val(codeValues.substring(1,codeValues.indexOf(")")));
	
}