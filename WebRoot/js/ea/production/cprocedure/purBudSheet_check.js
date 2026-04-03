$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,
		overlay : 20
		}).jqmAddClose('.close');
		var html =  $(".query").html();
		$(".query").remove();
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : html,
				minheight : 80,
				buttons : [{
					name : '查看审核',
					bclass : 'examine',
					onpress : action
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
			
			case '查看审核' :
				if (pcId == "") {
					alert('请选择!');
					return;
				}
				var cashierBillsID = $("tr#"+pcId).find("#cashierBillsID").text();
				var url = basePath + "ea/purchasebids/ea_viewCheck.jspa?search="
				+ search+"&purchaseCheck.cashierBillsID="+cashierBillsID+"&fiveClear="+fiveClear;
		        window.open(url);
		        
		        break;
			
		    case '打印' :
		    	if (pcId == "") {
					alert('请选择!');
					return;
				}
		    	var cashierBillsID = $("tr#"+pcId).find("#cashierBillsID").text();
		    	var url = basePath + "ea/purchasebids/ea_printPrev.jspa?search="
				+ search+"&cashierBills.cashierBillsID="+cashierBillsID;
		        window.open(url);
				
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/purchasebids/ea_findAuditPurSheetList.jspa?search="
						+ search+"&fiveClear="+fiveClear;
				numback(url);
				break;
			
			
		}
	}


	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				pcId = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});


	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/purchasebids/ea_toAduitSearch.jspa?pageNumber=" + ppageNumber
						+ "&pageForm.pageNumber="
						+ $("#pageNumber").attr("value"));
		document.SearchForm.submit.click();
		
	});

});

function re_load() {

if (token)
		document.location.href = basePath
				+ "ea/purchasebids/ea_findAuditPurSheetList.jspa?pageNumber="
		+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&search=" + search+"&fiveClear="+fiveClear;

}
