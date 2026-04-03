$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
		var html =  $(".query").html();
		$(".query").remove();
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : html,
				minheight : 80,
				buttons : [ {
					name : '查看',
					bclass : 'see',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '打印',
					bclass : 'printer',
					onpress : action

				},  {
					separator : true
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			
			case '查看' :
				if (csid == "") {
					alert('请选择!');
					return;
				}
				
				
			  window.open(basePath
						+ "ea/consignee/ea_getEditOrPrintPage.jspa?consigneeSheet.csid="+csid+"&voptype=e");
				break;
		
			case '导出' :
				var url = basePath + "ea/consignee/ea_showExcel.jspa?search="
						+ search+"&stype="+stype+"&cusType="+cusType;
				window.open(url);
				break;
		    case '打印' :
		    	if (csid == "") {
					alert('请选择!');
					return;
				}
		   
				 window.open(basePath
						+ "ea/consignee/ea_getEditOrPrintPage.jspa?consigneeSheet.csid="+csid+"&voptype=p");
				
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/consignee/ea_getConsigneeSheetList.jspa?search="
						+ search+"&stype="+stype+"&cusType="+cusType;
				numback(url);
				break;
			
			
		}
	}

	$("div.bDiv", $("div.flexigrid")).css("height", "480px");
	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				csid = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});


	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/consignee/ea_toSearch.jspa?pageNumber=" + ppageNumber
						+ "&pageForm.pageNumber="
						+ $("#pageNumber").attr("value"));
		document.SearchForm.submit.click();
		
	});

});

function re_load() {

if (token)
		document.location.href = basePath
				+ "ea/consignee/ea_getConsigneeSheetList.jspa?pageNumber="
		+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&search=" + search+"&stype="+stype+"&cusType="+cusType;

}
