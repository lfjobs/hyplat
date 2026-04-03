$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '调离管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
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
				},{
					name : '搜索',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '查看详情',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '打印预览',
					bclass : 'excel',
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
			case '添加' :
				var url = basePath + "ea/carassectinformation/ea_getCarNumber.jspa";
				open(url);
				break;
			case '删除' :
				if (safetyid == "") {
					alert('请选择');
					return;
				}
				$f = $('#mainForms');
				if (confirm("是否删除？")) {
					$("#mainForms").attr("target", "hidden").attr(
							"action",
							basePath + "ea/carassectinformation/ea_DeleteSafety.jspa?pageNumber="+ pNumber +"&safetyhealth.safetyid=" + safetyid);
					document.mainForms.submit.click();
					$("tr#" + safetyid).remove();
					safetyid = "";
					token = 11;
				}
				break;
			case '搜索' :
				$("#jqModelcarSearch").jqmShow();
				break;
			case '查看详情' :
				if(safetyid == ''){
					alert("请选择一条记录信息");
					return;
				}
				$("input.JQuerypersonvalue").attr("checked", false);
				//url = basePath + "ea/carassemblytable/ea_getCurrentSafetyHealth.jspa";
				//open(url);
				$("#jqModel").jqmShow();
				break;
			case '导出' :
				url = basePath + "ea/carassectinformation/ea_showsafetyHealthExcel.jspa?search="
						+ search;
				open(url);
				break;
			case '打印预览':
				url = basePath + "ea/carassectinformation/ea_getprintList.jspa";
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/carassectinformation/ea_getSafetyHealthList.jspa?search=" + search;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看详情');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				safetyid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
});
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/carassectinformation/ea_getSafetyHealthList.jspa?pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
	}
}
