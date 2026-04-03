$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 70,
				width : 'auto',
				minwidth : 30,
				title : '派工单信息列表',
				minheight : 50,
				buttons : [ {
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				},{
					name : '打印预览',
					bclass : 'printer',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				},{
					name : '查看',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				} , {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
							},{
								name : '导出',
								bclass : 'excel',
								onpress : action
									// 当点击调用方法
							}, {
								separator : true
							}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
						window.location.href = basePath + "page/ea/main/driving/signup/joborderAdd.jsp";
				break;
			case '打印预览' :
				if(carCylinderId == ""){
					alert("请选择一张派工单！")
					return;
				}
				var url = basePath + "ea/productregister/ea_detailinformation.jspa?carCylinderId=" + carCylinderId+"&edit=printsheetlist";
				open(url);
				break;
			case '查看' :
				if(carCylinderId == ""){
					alert("请选择一张派工单！")
					return;
				}
				window.location.href = basePath + "ea/productregister/ea_detailinformation.jspa?carCylinderId=" + carCylinderId+"&edit=seedetial";
				break;
			case'查询':
				$("#jqModelSearch").jqmShow();
				break;
			case '修改' :
				if(carCylinderId == ""){
					alert("请选择一张派工单！")
					return;
				}
				window.location.href = basePath + "ea/productregister/ea_detailinformation.jspa?carCylinderId=" + carCylinderId +"&edit=edit";
				break;
			case '设置每页显示条数' :
				var url = basePath + "ea/dispitch/ea_getListorderwork.jspa?search="+ search;
				numback(url);
				break;

		}
	}
	$(".JQueryflexme tr[id]").click(function(){
        $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
        carCylinderId =this.id;
    });
    
 /*   $(".JQueryflexme tr[id]").dblclick(function(){
        $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
        carCylinderId =this.id;
        action("查看");
    });*/
    
    //模糊查询
    $("#tosearch").click(function(){
    	/*$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}*/
		$("#postSearchForm")
				.attr("action", basePath
								+ "ea/productregister/ea_toSearch.jspa?1=1"
								);
		document.postSearchForm.submit.click();
    });
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/productregister/ea_getListProductregister.jspa?pageNumber=" + pNumber;
}