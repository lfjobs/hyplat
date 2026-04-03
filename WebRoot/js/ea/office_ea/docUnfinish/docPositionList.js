$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.draft0').flexigrid({
				height : 349,
				width : 'auto',
				minwidth : 30,
				title : '公文位置查询',
				minheight : 349,
				buttons : [{
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
					}, {
					separator : true
				}]
			});

	function action(com, grid) {
		switch (com) {
			case '查询' :
			document.location.href=basePath+"ea/docunfinish/ea_showDocUnfinishModule.jspa?type="+type;

				break;

			case '设置每页显示条数' :
				var url = basePath
						+ "ea/docunfinish/ea_getDocumentPosition.jspa?search=" + search +"&type="+type+"&date=" + new Date();
				numback(url);
				break;
		}
	}
});

    function ViewOffice(docPath, fileType,fileShowNameField) {
	  window.open(
					basePath
							+ "page/ea/main/office_ea/docUnfinish/wordonly.jsp?docPath="
							+ docPath + "&fileType=" + fileType
							+ "&fileShowNameField="+fileShowNameField);
}