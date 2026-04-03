$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	 var  name="积分管理";
	
	$('.address').flexigrid({
				height : 445,
				width : 'auto',
				minwidth : 30,
				title : name,
					
				minheight : 80,
				buttons : [{
				
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, 
					{
						name:'<input type="test" name="jifendetail" id="fromname"/>',
						onpress : action
						
					}, {
						separator : true
					},
					{
						name : '查询',
						bclass : 'mysearch',
						onpress : action
							// 当点击调用方法
						},
						 {
							separator : true
						},
					
					{
						name : '设置每页显示条数',
						bclass : 'mysearch',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
						}
				]
			});
	function action(com, grid) {
		switch (com) {	
			case '导出' :
				var url = basePath
						+ "ea/activity/ea_showClientPositioningExcel.jspa?pageNumber="
						+ ppageNumber + "&search=" + pageSize;
				open(url);
				break;
			case '查询' :	
					    $("#formtest").val($("#fromname").val());
						document.getElementById("addressForm").submit.click();
						break;
			   case '设置每页显示条数' :
					var url = basePath + "ea/activity/ea_getjifenlist.jspa?pageNumber="+pageNumber;	
					numback(url);
					break;
		}
	}
	$("table tr[id]").click(function() {
				clientPositioningID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});



});
