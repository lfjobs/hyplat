$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	var url = basePath + "ea/photoboxmanager/sajax_n_ea_queryAllPhotoBox.jspa";
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var cpblist = member.result;
					var str = "";
					$(cpblist).each(function(i, obj) {

						str += "<option value='" + obj.key + "'>"
								+ obj.photoBoxName + "</option>";

						$("#selectbox").html(str);
					});
				},
				error : function cbf(data) {

				}
			});

});