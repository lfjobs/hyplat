$(document).ready(function() {
	 $(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector  
	$('.JQueryflexme').flexigrid({
				height : 240,
				width : 'auto',
				minwidth : 20,
				title : '人事报表--'+basicInfo,
				minheight : 80,
				buttons : [{
					name : '生成图形报表',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true},
					{
					name : '返回',
					bclass : 'prev',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true}
				]
			});
	function action(com, grid) {
		switch (com) {
			case '生成图形报表':
				window.open(basePath+"page/ea/main/human/cstaff/sum_info/staffJfreeCharPh.jsp");
			    break;
			case '返回':
				document.location.href=basePath+"page/ea/main/navigation/pdc_sum_statements.jsp";
			    break;
		}

	}
	
	});


	

