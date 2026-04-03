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
				title : '办公室形象管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					name : '删除',
					bclass : 'delete',
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
				}, {
					name : '导出',
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
				officeImageManagerId = '';
				$("input.JQuerypersonvalue").attr("checked", false);
				$("#jqModel").jqmShow();
				document.cstaffForm.reset();
				break;
			case '修改' :
				if (addressID == '') {
					alert("请选择！");
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + addressID);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());// .attr("readonly","readonly")
						});
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (addressID == '') {
					alert("请选择！");
					return;
				}

				$f = $('#addressForm');
				$f.find(':input#addressID').val(addressID);
				if (confirm("确定继续？")) {
					$f.attr("target", "hidden").attr(
							"action",
							basePath + "/ea/onduty/ea_del.jspa?pageNumber="
									+ ppageNumber + "&onduty.ondutyID="
									+ addressID);
					document.addressForm.submit.click();
					$("tr#" + addressID).remove();
					addressID == '';
					token = 11;
				}

				break;
			case '导出' :
				var url = basePath + "/ea/onduty/ea_showExcel.jspa?pageNumber="
						+ ppageNumber + "&search=" + psearch;
				open(url);
				break;
			// case '查询':
			// $("#jqModelSearch").jqmShow();
			// break;
			case '查看' :
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + addressID);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());// .attr("readonly","readonly")
						});
				$("#jqModel").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath + "ea/onduty/ea_getListForPage.jspa?search="
						+ psearch;
				numback(url);
				break;
		}
	}
	
	$("#newphotoflore").click(function(){
		$("#jqMode2").jqmHide();
		$("#jqMode3").jqmShow();
	});
	$("#cancleaddflore").click(function(){
		$("#jqMode3").jqmHide();
		$("#jqMode2").jqmShow();
	});
	 $("#uploadfileimage").click(function(){
		 document.uploadForm.reset();
		  	 $("#jqModel").jqmHide();
			 $("#jqMode2").jqmShow();
	 });
	 $("#closeuploadpicture").click(function(){
		  	 $("#jqMode2").jqmHide();
			 $("#jqModel").jqmShow();
	 });
	 
});

 function cancle(){
	 
	 $("#jqModel").jqmHide();
	 
 }
 
function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/onduty/ea_getListForPage.jspa?pageNumber=" + ppageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}
// 新加内容结束
