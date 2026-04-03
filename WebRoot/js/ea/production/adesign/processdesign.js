$(document).ready(function() {
	var html="<form>主产品编号/名称：<input type='text' id='parameter'/>&nbsp;" +
			"<input type='button' class='input-button' value='  查询  ' id='searchProduct'/></form>";
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : html,
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'see',
					onpress : action
					}, {
					separator : true
				},{
					name : '打印',
					bclass : 'printer',
					onpress : action
						//
					}, {
					separator : true
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
					}, {
					separator : true
				}]
	
			});
	function action(com, grid) {
		switch (com) {
			case '查看' :
				if (maid == "") {
					alert('请选择!');
					return;
				}
				 window.open(basePath
							+ "ea/prodesign/ea_getSubProductzz.jspa?productPackaging.ppID="+maid);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/prodesign/ea_getProductPage.jspa?fiveClear="+fiveClear+"&type=01&category="+category;
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
				action('查看');
			});
	// 根据输入的产品编号或产品名称查询
	$("input#searchProduct").click(function() {
				var typeName = $("#parameter").val();
				var url = basePath + "ea/prodesign/ea_getProductPage.jspa?parameter="+typeName;
				document.location.href=encodeURI(encodeURI(url));
			});
});

