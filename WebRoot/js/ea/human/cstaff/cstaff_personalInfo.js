$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$('.JQueryflexmepost').flexigrid({
				height : 'auto',
				width : 'auto',
				title : '在职员工资料入库',
				minwidth : 20,
				minheight : 100,

				buttons : [{
					name : '填写档案入库单',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
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
			case '填写档案入库单' :
				document.location.href = basePath
						+ "ea/personalarchive/ea_toSavePersonalArchive.jspa?type=in&pageNumber="
								+ pNumber;
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/personalarchive/ea_getListPersonalInfo.jspa?type=in&search="
						+ search;
				numback(url);
				break;
		}
	}
	
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/personalarchive/ea_getListPersonalInfo.jspa?type=in";
}


