var aadd;
$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		
	$("#sumbitPhotoBox").click(function() {
		var photoBoxName=$.trim($("#newPhotoBox #photoBoxName").val());
		if(photoBoxName==null||photoBoxName==""){
			alert("请输入名称");
			return;
		}
		
		$("#newPhotoBox").attr("action",
				basePath + "ea/photoboxmanager/ea_createOrUpdatePhotoBox.jspa")
		document.newPhotoBox.submit.click();

	})

	avoidDistortion();// 初始化封面失真问题；
});
function createPhotoBox() {
	$("#divname").text("新建图片库");
	$("#newPhotoBox #hidphotoboxId").val("");
	$("#newPhotoBox #photoBoxName").val("");
	$("#newPhotoBox #descriptor").val("");
	$("#jqModelPhotoBox").jqmShow();
}

function enterBox(photoBoxID) {
	window.location.href = basePath
			+ "ea/photomanager/ea_showPhotoList.jspa?photoBoxID=" + photoBoxID;
}

function uploadPhotos() {
	var ss = $("div #aaa").html();
	if(ss==null||ss==""){
		alert("请先创建图片库");
		return;
	}
	window.location.href = basePath
			+ "page/ea/main/office_ea/fileCabinet/prepareUploadPage02.jsp";
	// JqueryDialog.Open('文件窗口',
	// basePath+'page/ea/main/office_ea/fileCabinet/uploadPhoto.jsp', 700, 400);

}
// 排序打开排序选择页面
function SortPhotosBox() {
	// $("#jqModelSort").jqmShow(); 暂时不使用打开窗口的方式
	if($("div#aaa").html()==null||$("div#aaa").html()==""){
		alert("请先创建图片库");
		return;
	}
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
	var url = basePath + "ea/photomanager/sajax_n_ea_saveSort.jspa";
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
// 查询确定提交
function submitSearch() {
	var tpkname = $("#tpkname").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	window.location.href = basePath
			+ "ea/photoboxmanager/ea_searchPicBox.jspa?tpkname=" + tpkname
			+ "&startDate=" + startDate + "&endDate=" + endDate;

}

// 查询打开查询页面
function QueryPhotosBox() {
	$("#jqModelSearch").jqmShow();
}

// 显示隐藏编辑和删除
function hidbs(photoboxId, photoBoxName, remark) {
	$("#hidBS_" + photoboxId).css("filter", "alpha(opacity=0)");
	$("#gogao").text("无展示信息");
	$("#boxName").text("公告栏");

}

function showbs(photoboxId, photoBoxName, remark) {
	$("#hidBS_" + photoboxId).css("filter", "alpha(opacity=100)");
	$("#gogao").text(remark);

	$("#boxName").text(photoBoxName);
}

// 编辑图片库
function edit(photoboxId, photoboxName, remark) {
	$("#divname").text("修改图片库");
	$("#newPhotoBox #hidphotoboxId").val(photoboxId);
	$("#newPhotoBox #photoBoxName").val(photoboxName);
	$("#newPhotoBox #descriptor").val(remark);
	$("#jqModelPhotoBox").jqmShow();
}

// 删除图片库
function deletePicBox(photoboxId) {
	if (confirm("确定删除？")) {
		var url = basePath + "ea/photoboxmanager/sajax_n_ea_delPicBox.jspa";
		$.ajax({
					url : url,
					type : "post",
					dataType : "json",
					async : true,
					data : {
						"photoBoxID" : photoboxId
					},
					success : function(data) {
						alert("删除图片库成功！");
						backPicBox();
					},
					error : function(data) {
						alert("获取数据失败");
					}

				});
	}
}

// 创建图片库的取消功能
function canclepb() {
	$("#jqModelPhotoBox").jqmHide();
}

// 排序的取消功能
function canclesort() {
	$("#jqModelSort").jqmHide();
}

// 排序的取消功能
function canclesearch() {
	$("#jqModelSearch").jqmHide();
}

// 统计文本域输入个数
function title_len() {
	var value = $('#photoBoxName').val().length;
	if (value == 30) {
		var string = "<span style=\"color:#FF0000\">" + value + "/30</span>";
	} else {
		var string = "<span style=\"color:#FF0000\">" + value + "</span>/30";
	}
	$('#titlelen').html(string);
}

// 统计textarea输入个数
function title_arealen() {
	var value = $('#descriptor').val().length;
	if (value >= 80) {
		var string = "<span style=\"color:#FF0000\">80/80</span>";
		var remark = $("#descriptor").val();
		remark = remark.slice(0, 80);
		$("#descriptor").val(remark);
	} else {
		var string = "<span style=\"color:#FF0000\">" + value + "</span>/80";

	}
	$('#titlearealen').html(string);
}

// 防止封面失真
function avoidDistortion() {
	var imgface = document.getElementsByName("imgface")
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
