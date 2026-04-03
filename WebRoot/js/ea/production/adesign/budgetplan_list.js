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
						+ "ea/budgetplan/ea_getAddPage.jspa?type="+type+"&fiveClear="+fiveClear+"&category="+category);
						
					
				break;
			case '修改' :
				if (bpid == "") {
					alert('请选择!');
					return;
				}
				 
			  window.open(basePath
						+ "ea/budgetplan/ea_getAddPage.jspa?plan.bpid="+bpid+"&type="+type+"&fiveClear="+fiveClear+"&category="+category);
			
				break;
			case '删除' :
				if (bpid == "") {
					alert('请选择');
					return
				}
				
				$("#bpid").val(bpid);
				
				if (confirm("确定删除？")) {
					$("#SearchForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/budgetplan/ea_deleteBudgetPlan.jspa?pageNumber="
											+ ppageNumber+"&category="+category);
					document.SearchForm.submit.click();
					$("tr#" + bpid).remove();
					bpid = "";
					token = 11;
				}
				break;
			case '导出' :
				var url = basePath + "ea/budgetplan/ea_showExcel.jspa?search="
						+ search+"&type="+type+"&category="+category;
				window.open(url);
				break;
		    case '打印' :
		   
				var url = basePath + "ea/budgetplan/ea_printPrev.jspa?search="
						+ search+"&type="+type+"&category="+category;
				window.open(url);
				
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/budgetplan/ea_getBudgetPlanList.jspa?search="
						+ search+"&type="+type+"fiveClear="+fiveClear+"&category="+category;
				numback(url);
				break;
			
			
		}
	}


	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				bpid = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});


	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/budgetplan/ea_toSearch.jspa?pageNumber=" + ppageNumber
						+ "&pageForm.pageNumber="
						+ $("#pageNumber").attr("value"))+"&category="+category;
		document.SearchForm.submit.click();
		
	});

});

function re_load() {

if (token)
		document.location.href = basePath
				+ "ea/budgetplan/ea_getBudgetPlanList.jspa?pageNumber="
		+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&search=" + search+"&type="+type+"&category="+category;

}
