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
					name : '发布/取消发布',
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
					},{
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
						+ "ea/bidrecruit/ea_getAddPage.jspa");
						
						
				break;
			case '修改' :
				if (riId == "") {
					alert('请选择!');
					return;
				}
			  window.open(basePath
						+ "ea/bidrecruit/ea_getAddPage.jspa?recruitInfo.riId="+riId);
				break;
			case '发布/取消发布' :
				if (riId == "") {
					alert('请选择!');
					return;
				}
				var status = $("tr#"+riId).find("#status").text();
				if(status=="00"){
					 $(".tip").text("确定要发布招聘信息到微分金?");
					
				}else{
					 $(".tip").text("确定要取消发布招聘信息?");
				}
				$("#riId").val(riId);
				$("#jqModel").jqmShow();
				
				

				break;
			case '删除' :
				if (riId == "") {
					alert('请选择');
					return
				}
				
				$("#riId").val(riId);
				if (confirm("确定删除？")) {
					$("#SearchForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/bidrecruit/ea_deleteRecruitInfo.jspa?pageNumber="
											+ ppageNumber);
					document.SearchForm.submit.click();
					$("tr#" + riId).remove();
					riId = "";
					token = 11;
				}
				break;
			case '导出' :
				var url = basePath + "ea/bidrecruit/ea_showExcel.jspa?search="
						+ search;
				window.open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/bidrecruit/ea_findRecruitInfoList.jspa?search="
						+ search;
				numback(url);
				break;
			
			
		}
	}
   
	//发布\取消发布
	$(".confirm").click(function(){
      $("#SearchForm")
		 .attr("target", "hidden")
		 .attr(
				"action",
				basePath
						+ "ea/bidrecruit/ea_publishRecruitInfo.jspa");
          document.SearchForm.submit.click();
          token = 2;
		
	});

	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				riId = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});


	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/bidrecruit/ea_toSearch.jspa?pageNumber=" + ppageNumber
						+ "&pageForm.pageNumber="
						+ $("#pageNumber").attr("value"));
		document.SearchForm.submit.click();
		
	});

});

function re_load() {

if (token)
		document.location.href = basePath
				+ "ea/bidrecruit/ea_findRecruitInfoList.jspa?pageNumber="
		+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&search=" + search;

}
