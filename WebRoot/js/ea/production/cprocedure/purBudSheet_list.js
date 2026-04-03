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
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '提交审核',
					bclass : 'examine',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
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
			case '添加' :
				

				window.open(basePath
						+ "ea/purchasebids/ea_getAddPage.jspa?fiveClear="+fiveClear+"&type="+type);
						
						
				break;
			case '修改' :
				if (cashierBillsID == "") {
					alert('请选择!');
					return;
				}
				var status = $("tr#"+cashierBillsID).find("#status").text();
				if(status!="00"){
					alert("只能修改草稿状态");
					return;
				}
			  window.open(basePath
						+ "ea/purchasebids/ea_getAddPage.jspa?cashierBills.cashierBillsID="+cashierBillsID+"&fiveClear="+fiveClear+"&type="+type);
				break;
			case '提交审核' :
				if (cashierBillsID == "") {
					alert('请选择!');
					return;
				}
				var status = $("tr#"+cashierBillsID).find("#status").text();
				$("#cashierBillsID").val(cashierBillsID);
				if(status=="00"){
					$("tr#"+cashierBillsID).find("#status").text("01");  
					$("#SearchForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/purchasebids/ea_submitExamine.jspa?type="+type);
		               document.SearchForm.submit.click();
			          token = 2;
			         
				}

				break;
			case '删除' :
				if (cashierBillsID == "") {
					alert('请选择');
					return
				}
				
				$("#cashierBillsID").val(cashierBillsID);
				if (confirm("确定删除？")) {
					$("#SearchForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/purchasebids/ea_deletePursheet.jspa?pageNumber="
											+ ppageNumber);
					document.SearchForm.submit.click();
					$("tr#" + cashierBillsID).remove();
					cashierBillsID = "";
					token = 11;
				}
				break;
			case '导出' :
				var url = basePath + "ea/purchasebids/ea_showExcel.jspa?search="
						+ search+"&fiveClear="+fiveClear;
				window.open(url);
				break;
		    case '打印' :
		   
				var url = basePath + "ea/purchasebids/ea_printPrev.jspa?search="
						+ search+"&cashierBills.cashierBillsID="+cashierBillsID;
				window.open(url);
				
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/purchasebids/ea_getPurcBugetSheetList.jspa?search="
						+ search+"&fiveClear="+fiveClear;
				numback(url);
				break;
			
			
		}
	}


	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				cashierBillsID = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});


	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/purchasebids/ea_toSearch.jspa?pageNumber=" + ppageNumber
						+ "&pageForm.pageNumber="
						+ $("#pageNumber").attr("value"));
		document.SearchForm.submit.click();
		
	});

});

function re_load() {

if (token)
		document.location.href = basePath
				+ "ea/purchasebids/ea_getPurcBugetSheetList.jspa?pageNumber="
		+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&search=" + search+"&fiveClear="+fiveClear;

}
