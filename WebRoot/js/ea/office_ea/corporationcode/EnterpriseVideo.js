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
				title : '企业录像管理',
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
				}, {
					name : '修改',
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
				enterpriseVideoID = '';
				$("input.JQuerypersonvalue").attr("checked", false);
				$t = $("table#stafftable");
				$t.find("#pic").attr("src", "");
				document.cstaffForm.reset();
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (enterpriseVideoID == "") {
					alert('请选择录像');
					return;
				}
				if (confirm("是否删除？")) {
					$f = $('#cstaffForm');
					$f.find('#enterpriseVideoID').val(enterpriseVideoID);
					var url = basePath
							+ "ea/enterprisevideo/ea_delEnterpriseVideo.jspa?pageNumber="
							+ pNumber;
					$f.attr("target", "hidden").attr("action", url);
					document.cstaffForm.submit.click();
					$("tr#" + enterpriseVideoID).remove();
					enterpriseVideoID = "";
					token = 11;
				}
				break;
			case '修改' :
				if (enterpriseVideoID == "") {
					alert('请选择录像');
					return;
				}
				$t = $("table#stafftable");
				$t.find("#pic").attr("src", "");
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + enterpriseVideoID);
				$t.find('img#pic').attr("src",
						basePath + $p.find("span#videoPath").text());
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModel").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/enterprisevideo/ea_showEnterpriseVideoExcel.jspa?search="
						+ search;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/enterprisevideo/ea_getEnterpriseVideoList.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				enterpriseVideoID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".JQuerySubmit").click(function() {
		var videoPath = document.getElementById("hidIdList").value;
		if(videoPath==""){
			alert("请上传视频");
			return;
		}
        $("#videoPath1").val(videoPath.substring(0,videoPath.length-1));
		if (enterpriseVideoID == "") {
			$("#cstaffForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/enterprisevideo/ea_saveEnterpriseVideo.jspa?pageNumber="
									+ pNumber);
			document.cstaffForm.submit.click();
			$("#cstaffForm").find(":input[name]").val("");
			document.cstaffForm.reset();
			token = 2;
			return;
		}
		$("#cstaffForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/enterprisevideo/ea_saveEnterpriseVideo.jspa?pageNumber="
								+ pNumber);
		document.cstaffForm.submit.click();
		token = 2;
	});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});

	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/enterprisevideo/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.postSearchForm.submit.click();
		$("#postSearchForm").find(":input[name]").val("");
	});
});
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/enterprisevideo/ea_getEnterpriseVideoList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
	}
}

function viewVedio(videoPath,enName){
	//document.location.href = basePath+"page/ea/main/office_ea/corporationcode/players.jsp?path="+videoPath;
	
	window.open(basePath+"page/ea/main/office_ea/corporationcode/flowplayer/players.jsp?enName="+enName+"&path="+videoPath);
	//window.open(basePath+"page/ea/main/office_ea/corporationcode/player.html?enName="+enName+"&path="+videoPath);
}

function uploadVedio(){
	
	
		var videoPath = document.getElementById("hidIdList").value;
		if(videoPath==""){
			alert("请上传视频");
			return;
		}
        $("#videoPath1").val(videoPath.substring(0,videoPath.length-1));
		if (enterpriseVideoID == "") {
			$("#cstaffForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/enterprisevideo/ea_saveEnterpriseVideo.jspa?pageNumber="
									+ pNumber);
			document.cstaffForm.submit.click();
			$("#cstaffForm").find(":input[name]").val("");
			document.cstaffForm.reset();
			token = 2;
			return;
		}
		$("#cstaffForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/enterprisevideo/ea_saveEnterpriseVideo.jspa?pageNumber="
								+ pNumber);
		document.cstaffForm.submit.click();
		token = 2;

}
