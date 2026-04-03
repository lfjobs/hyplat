var aadd;
$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$("#aaa").sortable();
	// 暂时不使用
	aadd = $("#aaa").sortable('serialize');

	$("#aaa").sortable({
				stop : function() {
					/*var aadd = $("#aaa").sortable('serialize');*/
					var url = basePath
							+ "ea/photoboxmanager/sajax_n_ea_dragSort.jspa";
					$.ajax({
								url : encodeURI(url),
								type : "post",
								dataType : "json",
								async : true,
								data : {
									"aaa" : $("#aaa").sortable('serialize')
								},
								success : function(data) {
									alert("顺序保存成功！");
								},
								error : function(data) {

								}

							});

				}

			});

	avoidDistortion();// 初始化封面失真问题；
});

// 排序打开排序选择页面
function SortPhotosBox() {
	// $("#jqModelSort").jqmShow(); 暂时不使用打开窗口的方式
	window.location.href = basePath
			+ "ea/photoboxmanager/ea_getCorPhotoBoxSortList.jspa";

}

// 排序确定提交
function submitsort2(sortType) {
	window.location.href = basePath
			+ "ea/photoboxmanager/ea_sortPicBox.jspa?sortType=" + sortType;
}
// 保存提交
function saveSort() {
	var url = basePath + "ea/photoboxmanager/sajax_n_ea_saveSort.jspa";
	$.ajax({
				url : url,
				type : "post",
				dataType : "json",
				async : true,
				success : function(data) {
					if (confirm("保存顺序成功，是否返回图片库？")) {
						window.location.href = basePath
								+ "ea/photoboxmanager/ea_getCorPhotoBoxList.jspa";
					}
				},
				error : function(data) {
					alert("获取数据失败");
				}

			});
}


// 防止封面失真
function avoidDistortion() {
	var imgface = document.getElementsByName("imgface");
	for (var i = 0; i < imgface.length; i++) {
		var width = "102";
		var height = "104";
		var image = new Image();
		image.src = imgface[i].src;
		swidth = image.width;
		sheight = image.height;
		if (swidth < width) {
			imgface[i].width = swidth;
		} else {
			imgface[i].width = "102";
		}
		if (sheight < height) {
			imgface[i].height = sheight;
		} else {
			imgface[i].height = "104";

		}

	}

}

function backPicBox() {
	window.location.href = basePath
			+ "ea/photoboxmanager/ea_getCorPhotoBoxList.jspa";
}
