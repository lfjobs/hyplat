$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		
	showListpic();
	
	$("#picdiv").sortable({
				stop : function() {
					/*var aadd = $("#picdiv").sortable('serialize');*/
					var url = basePath
							+ "ea/photomanager/sajax_n_ea_dragPhotoSort.jspa";
					$.ajax({
								url : encodeURI(url),
								type : "post",
								dataType : "json",
								async : true,
								data : {
									"picdiv" : $("#picdiv").sortable('serialize')
								},
								success : function(data) {
									
								},
								error : function(data) {

								}

							});

				}

			});
});
// 图片不失真
function showListpic() {
	var $picdiv = $("#picdiv");
	var width = "126";
	var height = "126";
	$picdiv.find("img").each(function() {
				var image = new Image();
				image.src = $(this).attr("src");
				swidth = image.width;
				sheight = image.height;

				if (swidth < width) {
					$(this).attr("width", swidth);
				} else {
					$(this).attr("width", "126");
				}
				if (sheight < height) {
					$(this).attr("height", sheight);
				} else {
					$(this).attr("height", "124");
				}

			});

}

function submitsort2(sortTypePhoto) {
	window.location.href = basePath
			+ "ea/photomanager/ea_sortPicList.jspa?sortTypePhoto=" + sortTypePhoto+"&photoBoxId="+photoBoxId;
}


// 保存提交
function savePhotoSort() {
	var url = basePath + "ea/photomanager/sajax_n_ea_savePhotoSort.jspa";
	$.ajax({
				url : url,
				type : "post",
				dataType : "json",
				async : true,
				data:{
					"photoBoxId":photoBoxId
				},
				success : function(data) {
					if (confirm("保存顺序成功，是否返回图片列表？")) {
						window.location.href = basePath
								+ "ea/photomanager/ea_showPhotoList.jspa?photoBoxID=" + photoBoxId;
					}
				},
				error : function(data) {
					alert("获取数据失败");
				}

			});
}

function backPicList(){
	window.location.href = basePath
			+ "ea/photomanager/ea_showPhotoList.jspa?photoBoxID=" + photoBoxId;
}
