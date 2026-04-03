$(document).ready(function() {
	avoidDistortion();

	// 修改名称提交
	$("#submitTitle").click(function() {

		var photoKey = $("#hidphoto").val();
		var photoName = $("#photoNameT").val();
		if(photoName.length>30){
			alert("长度不能超过30");
			return;
		}
		var url = basePath
				+ "ea/photomanager/sajax_n_ea_updateTitleOrRemark.jspa";

		$.ajax({
					url : url,
					type : "post",
					dataType : "json",
					data : {
						"key" : photoKey,
						"photoName" : photoName
					},
					success : function(data) {
						var mem = eval("(" + data + ")");
						var photo = mem.result;
						var title = photo.photoName;
						$("#titlespan").text(title);
						$("#titlediv").show();
						$("#updateTitle").hide();
						alert("修改成功！");
					},
					error : function(data) {
						alert("数据获取失败！");
					}
				});

	});
	// 修改描述提交
	$("#submitremark").click(function() {

		var photoKey = $("#hidphoto2").val();
		var remarktitle = $("#remarktitle").val();
		var url = basePath
				+ "ea/photomanager/sajax_n_ea_updateTitleOrRemark.jspa";

		$.ajax({
					url : url,
					type : "post",
					dataType : "json",
					data : {
						"key" : photoKey,
						"remark" : remarktitle
					},
					success : function(data) {
						var mem = eval("(" + data + ")");
						var photo = mem.result;
						var remark = photo.remark;
						$("#remarkspan").text(remark);
						$("#remarkdiv").show();
						$("#updateRemark").hide();
						alert("修改成功！");

					
					},
					error : function(data) {
						alert("数据获取失败！");
					}
				});

	});

});
// 为了图片不失真
function avoidDistortion() {
	var width = "640";
	var height = "350";
	var image = new Image();
	image.src = $("img#mainphoto").attr("src");
	swidth = image.width;
	sheight = image.height;
	if (swidth < width) {
		$("#mainbody img").attr("width", swidth);
	} else {
		$("#mainbody img").attr("width", "640");
	}
	if (sheight < height) {
		$("#mainbody img").attr("height", sheight);
		if (sheight < 175) {
			$("#mainbody img").css("margin-top", 175 - sheight / 2);
		}
	} else {
		$("#mainbody img").attr("height", "350");

	}
}

// 为了图片上下居中
function changeMargin(type, sheight) {
	if (type == "add") {
		$("#mainbody img").css("margin-top", 175 - sheight / 2);
	} else {
		$("#mainbody img").css("margin-top", 0);
	}
}
// 改变右边的显示信息
function changeInfo(title, key, remark) {
	$("#titlediv").show();
	$("#updateTitle").hide();
	$("#titlespan").text(title);
	$("#hidphoto").val(key);
	$("#photoNameT").val(title);

	$("#remarkdiv").show();
	$("#hidphoto2").val(key);
	$("#updateRemark").hide();
	if (remark == null) {
		$("#remarkspan").text("暂无描述");
		$("#remarktitle").val("暂无描述");
	} else {
		$("#remarkspan").text(remark);
		$("#remarktitle").val(remark);
	}
}

function cancleTitle() {
	$("#titlediv").show();
	$("#updateTitle").hide();
}

function cancelremark() {
	$("#remarkdiv").show();
	$("#updateRemark").hide();
}
// 点击修改名称
function titlediv() {
	$("#titlediv").hide();
	$("#updateTitle").show();
}
// 点击修改描述
function remarkdiv() {
	$("#remarkdiv").hide();
	$("#updateRemark").show();
}

//返回相册列表
function backPhotoList(){
	window.location.href = basePath
			+ "ea/photomanager/ea_showPhotoList.jspa?photoBoxID=" + photoBoxId;
}
