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
					name : '查看发布',
					bclass : 'mysearch',
					onpress : action
						//
					}, {
					separator : true
				},{
					name : '导出',
					bclass : 'excel',
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
			
			case '查看发布' :
				if (ibId == "") {
					alert('请选择!');
					return;
				}
				var cashierBillsID = $("tr#"+ibId).find("#cashierBillsID").text();
				var url = basePath + "ea/purchasebids/ea_viewInviteBids.jspa?search="
				+ search+"&inviteBids.cashierBillsID="+cashierBillsID;
		        window.open(url);
		        
		        break;
			
		    case '导出' :
		    	var url = basePath + "ea/purchasebids/ea_showExcelByInvite.jspa?search="
				+ search+"&fiveClear="+fiveClear;
		        window.open(url);
				
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/purchasebids/ea_findInviteBidsList.jspa?search="
						+ search+"&fiveClear="+fiveClear;
				numback(url);
				break;
			
			
		}
	}


	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				ibId = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});


	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/purchasebids/ea_toInviteBSearch.jspa?pageNumber=" + ppageNumber
						+ "&pageForm.pageNumber="
						+ $("#pageNumber").attr("value"));
		document.SearchForm.submit.click();
		
	});

});

function re_load() {

if (token)
		document.location.href = basePath
				+ "ea/purchasebids/ea_findInviteBidsList.jspa?pageNumber="
		+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&search=" + search+"&fiveClear="+fiveClear;

}
