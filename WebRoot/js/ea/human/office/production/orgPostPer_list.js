$(function() {	
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 'auto',
				width : 'auto',
				minwidth : 30,
				title : '职务人员列表',
				minheight : 80,
				buttons : [ {
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
				}]
			});
	function action(com, grid) {
		switch (com) {
			
			case '查询':
				$("#jqModelcarSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath+ "ea/departmentpost/ea_getListPerson.jspa?departmentPost.depPostID="+depPostID+"&search="+ search  + "&starPer="+starPer;
				numback(url);
				break;
		}
	}
	
    $("#tosearch").click(function() {
		$f = $('#orgPostSearchForm');
		$f.attr("action", basePath
						+ "ea/departmentpost/ea_toSearchPer.jspa?departmentPost.depPostID="+depPostID+"&pageNumber="
						+ pNumber);
		document.orgPostSearchForm.submit.click();
	});
});

