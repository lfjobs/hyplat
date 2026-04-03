$(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
	// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector

	$(".jqmreturn").click(function() {
		notoken = 0;
		$("#documentsjqModel").jqmHide();
		$("#previewjqModel").jqmHide();
		$("#journalNumAjax").attr("value", "");
		$("#taxDateAjax").attr("value", "");
		showDocument = false;
	});



var query="<form method='post' name='searchf' id='searchf'><input type='hidden' name='type' value='"+type+"'/><input type='submit' style='display:none;' name='submit' />" +
		"<table  border='0' cellspacing='0' cellpadding='0'>" +
		"<tr><td align='right'>项目分类：</td>" +
		"<td><input type='hidden' name='search' value='search'/>" +
		"<input type='text'  class='xmtypename' size='6'/>" +
		"<input type='hidden' name='csbSearch.zprojectname' class='xmtype'/></td>" +
		"<td align='right'>项目名称：</td>" +
		"<td><input type='text' name='csbSearch.sprojectname' size='6'/></td>" +
		"<td align='right'>物品编号：</td>" +
		"<td><input type='text' name='csbSearch.goodsnum' size='6'/></td>" +
//		"<td align='right'>公司：</td>" +
//		"<td><input type='text' name='csbSearch.companyname' size='6'/></td>" +
//		"<td align='right'>部门：</td>" +
//		"<td><input type='text' name='csbSearch.orgname' size='6'/></td>" +
		"<td align='right'>责任人：</td>" +
		"<td><input type='text' name='csbSearch.staffname' size='6'/></td>" +
		"<td align='right'>往来单位：</td>" +
		"<td><input type='text' name='csbSearch.ccompanyname' size='6'/></td>" +
		"<td align='right'>往来个人：</td>" +
		"<td><input type='text' name='csbSearch.cstaffname' size='6'/></td>" +
		"<td align='right'>制单日期：</td>" +
		"<td colspan='3'><input type='text' name='csbSearch.start' onfocus='date(this)' size='6'/>-<input  type='text' name='csbSearch.end' onfocus='date(this)' size='6'/></td>" +
		"<td colspan='4' align='left'><input type='button' class='input-button' value=' 查询 ' id='tosearch'/></td></tr>" +
		"</table>";
	$('.flexme11').flexigrid({
		height : 280,
		width : 'auto',
		minwidth : 30,
		title :(type=='xmmx'?"项目明细列表":type=="srmx"?"项目收入明细":type=="zcmx"?"项目支出明细":type=="clmx"?"项目车辆明细":"") + query,
		minheight : 80,
		buttons : [ {
			name : '查看',
			bclass : 'see',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, 
//		{
//			name : '打印',
//			bclass : 'excel',
//			onpress : action
//		// 当点击调用方法
//		}, {
//			separator : true
//	}, 
	{
		name : '导出',
			bclass : 'printer',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, 
		{
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
	
		case '打印':
			if (csdID == "") {
				alert("请选择！");
				return;
			}
			window.open(basePath
					+ "/ea/costsheet/ea_toprintcsb.jspa?costSheetBill.csbID="
					+ csbID);
			break;
		case '设置每页显示条数':
			var url = basePath
					+ "/ea/costsheet/ea_getProjectDetailList.jspa?search=" + search+"&type="+type;
			numback(url);
			break;
		
		
		
		case '导出':
			var url = basePath
			+ "/ea/costsheet/ea_showExcel.jspa?search=" + search+"&type="+type;
			open(url);
			break;
		
		}
		
		
	}
	// 这一行的单击事件
	$(".flexme11 tr[id]").click(function() {

		goodsBillsID = this.id;
		cashierBillsID = $("tr#" + goodsBillsID).find("#cashierBillsID").text();
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");


	});
	
	// 这一行的双击事件
	$(".flexme11 tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
		goodsBillsID = this.id;
		cashierBillsID = $("tr#" + goodsBillsID).find("#cashierBillsID").text();
		action("查看");
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
						basePath + "/ea/costsheet/ea_toSearchByDetail.jspa?pageNumber="
								+ pNumber);
				document.searchf.submit.click();
			});


	
});

function re_load() {
	if (token) {
		document.location.href = basePath
				+ "/ea/costsheet/ea_getCostSheetList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&jumptype=" + jumptype;
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






