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
						+ "ea/member/ea_getAdd.jspa?fiveClear="+fiveClear+"&type="+type+"&category="+category);
						
						
				break;
			case '修改' :
				if (maid == "") {
					alert('请选择!');
					return;
				}
			  window.open(basePath
						+ "ea/member/ea_getAdd.jspa?memberAllot.makey="+maid+"&fiveClear="+fiveClear+"&type="+type+"&category="+category);
				break;
			case '删除' :
				if (maid == "") {
					alert('请选择');
					return
				}
				$("#maid").val(maid);
				
				if (confirm("确定删除？")) {
					$("#SearchForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/member/ea_deletePlans.jspa?memberAllot.makey="
											+ maid);
					document.SearchForm.submit.click();
					$("tr#" + maid).remove();
					maid = "";
					token = 11;
				}
				break;
			case '导出' :
				var url = basePath + "ea/member/ea_showExcel.jspa?search="
						+ search;
				window.open(url);
				break;
		    case '打印' :
		   
				var url = basePath + "ea/member/ea_print.jspa?search="
						+ search;
				window.open(url);
				
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/member/ea_getMemberList.jspa?search="
						+ search+"&fiveClear="+fiveClear;
				numback(url);
				break;
			
			
		}
	}


	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				maid = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});


	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/member/ea_toSearch.jspa?pageNumber=" + ppageNumber
						+ "&pageForm.pageNumber="
						+ $("#pageNumber").attr("value")+"&type="+type+"&category="+category+"&fiveClear="+fiveClear);
		document.SearchForm.submit.click();
		
	});

});

function re_load() {

if (token)
		document.location.href = basePath
				+ "ea/member/ea_getMemberList.jspa?pageNumber="
		+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&search=" + search+"&type="+type+"&category="+category+"&fiveClear="+fiveClear;

}
