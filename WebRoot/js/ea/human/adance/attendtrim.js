$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.trimtable').flexigrid({
				height : 'auto',
				width : 'auto',
				title : '考勤调整管理' ,
				minwidth : 30,
				minheight : 80,
				buttons : [{
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
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/attendextleave/ea_getAttendExtleave.jspa?1=1";
				numback(url);
				break;
		}
	}

	$(".trimtable tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		extleaveId = this.id;
	});
	// 取消
	$("input.JQueryreturn").click(function() {
				$("#jqModelAdd").jqmHide();
				re_load();
			});
	
});
//审核 通过 驳回
function audit(e,i){
	var url = basePath+"ea/attendtrim/sajax_ea_audit.jspa?audstate=" + e + "&attendTrimId=" + i + "&d="+Math.ceil(Math.random()*1000);
    $.ajax({
        url : encodeURI(url),
        type : "get",
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            if(member.suc == "suc"){
	           	 alert("通过！");
            }else{
           	 	alert("驳回！");
            }
            document.location.href = basePath
				+ "ea/attendtrim/ea_getAttendTrim.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
        },error : function cbf(data) {
            alert("获取数据失败!");
        }
    });
}

function re_load() {
	if (token){
		document.location.href = basePath
				+ "ea/attendtrim/ea_getAttendTrim.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
	}
}
