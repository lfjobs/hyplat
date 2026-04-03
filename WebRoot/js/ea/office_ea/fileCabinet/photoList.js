$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		
	showListpic();
	// 点击管理效果
	$("#mangerli").toggle(function() {
				$("#manager").show();
				$("#mangerspan").text("退出管理");
				$(".checkinput").show();
			}, function() {
				$("#manager").hide();
				$("#mangerspan").text("管理");
				$(".checkinput").hide();
			});

	// 点击移动效果
	$("#move").toggle(function() {
				getBox();
				$("#move").addClass("move2");
				$("#full").show();

			}, function() {
				$("#move").removeClass("move2")
				$("#full").hide();
			});

	// 创建图片库提交
	$("#sumbitPhotoBox").click(function() {
		var photoBoxName=$.trim($("#newPhotoBox #photoBoxName").val());
		if(photoBoxName==null||photoBoxName==""){
			alert("请输入名称");
			return;
		}
		
		var checkinput = document.getElementsByName("checkinput");
		var length = 0;
		var values = "";
		for (var i = 0; i < checkinput.length; i++) {
			if (checkinput[i].checked) {
				values += checkinput[i].value + ",";
			}
		}
		var photoBoxName = $("#photoBoxName").val();
		var remark = $("#descriptor").val();
		var url = basePath + "ea/photomanager/sajax_n_ea_createPhotoBox2.jspa";
		$.ajax({
					url : encodeURI(url),
					type : "post",
					dataType : "json",
					async : true,
					data : {
						"photoBoxName" : photoBoxName,
						"remark" : remark
					},
					success : function(data) {
						var mem = eval("(" + data + ")");
						var photoBoxId = mem.result;
						$("#jqModelPhotoBox").jqmHide();
						if (confirm("确定要移动图片至图片库" + photoBoxName + "中？")) {
							var url2 = basePath
									+ "ea/photomanager/sajax_n_ea_movePicToBox.jspa";
							$.ajax({
										url : encodeURI(url2),
										type : "post",
										dataType : "json",
										async : true,
										data : {
											"photoIdstr" : values,
											"photoBoxIdNew" : photoBoxId
										},
										success : function(data) {
											alert("移动成功");
											window.location.reload();
										},
										error : function(data) {

										}
									})
						}
					},
					error : function(data) {

					}

				})

	})

});
function slideShow(url, photoID) {
	window.location.href = basePath
			+ "ea/photomanager/ea_showSlideList.jspa?photoID=" + photoID
			+ "&url=" + url;

}

function wallShow(photoBoxID) {
	window.location.href = basePath
			+ "ea/photomanager/ea_showWallList.jspa?photoBoxID=" + photoBoxID;
}
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
// 管理中的全选问题
function fullcheck() {
	var fool = $("#fullcheck").attr("checked");
	if (fool == true) {
		$(".checkinput").attr("checked", true);
	} else {
		$(".checkinput").attr("checked", false);
	}
}


function blurevent(photoId, it) {
	$("#" + photoId + "span").show();
	$("#" + photoId + "input").hide();
}

// 设置封面
function setCover() {
	var checkinput = document.getElementsByName("checkinput");
	var length = 0;
	var values = "";
	for (var i = 0; i < checkinput.length; i++) {
		if (checkinput[i].checked) {
			var values = checkinput[i].value;

			length++;
		}
	}
	if (length > 1) {
		alert("只能选择一个");
		return;
	}
	if (length == 0) {
		alert("请选择图片");
		return;
	}
	if (confirm("确定设为封面？")) {

		var url = basePath + "ea/photoboxmanager/sajax_n_ea_setCover.jspa";

		$.ajax({
					url : url,
					type : "post",
					dataType : "json",
					data : {
						"fileId" : values
					},
					success : function(data) {
						alert("设置封面成功！");
					},
					error : function(data) {
						alert("数据获取失败！")
					}
				})

	}
}

// 返回相册列表
function backPhotoList() {
	window.location.href = basePath
			+ "ea/photoboxmanager/ea_getCorPhotoBoxList.jspa";
}

// 移动鼠标移上的效果
function moveover(type) {
	if (type == "move") {
		$("span#move").addClass("move");
	}
	if (type == "delete") {
		$("span#delete").addClass("move");
	}
	if (type == "face") {
		$("span#faces").addClass("move");
	}

}
// 移出
function moveout(type) {
	if (type == "move") {
		$("span#move").removeClass("move");
	}
	if (type == "delete") {
		$("span#delete").removeClass("move");
	}
	if (type == "face") {
		$("span#faces").removeClass("move");
	}

}

function getBox() {
	var url = basePath + "ea/photoboxmanager/sajax_n_ea_queryAllPhotoBox.jspa";

	$.ajax({
				url : url,
				type : "post",
				dataType : "json",
				success : function(data) {
					$("##full #picboxdiv").empty();
					var memer = eval("(" + data + ")");
					var list = memer.result;
					for (var i = 0; i < list.length; i++) {
						var photoBoxID = '"' + list[i].photoBoxID + '"';
						var photoBoxName = '"' + list[i].photoBoxName + '"';
						$("#full #picboxdiv").append("<span onclick='selectPicBox("
								+ photoBoxID + "," + photoBoxName
								+ ");' onmouseout='mouseoutSelected("
								+ photoBoxID
								+ ");'onmouseover='mouseoverSelected("
								+ photoBoxID + ");'style='cursor: pointer;width:120px;display:inline-block;'id='"
								+ list[i].photoBoxID
								+ "picb'><a href='javascript:selectPicBox("
								+ photoBoxID + "," + photoBoxName
								+ ");' onmouseout='mouseoutSelected("
								+ photoBoxID
								+ ");'onmouseover='mouseoverSelected("
								+ photoBoxID + ");'>" + list[i].photoBoxName
								+ "</a></span><br>");
					}
				},
				error : function(data) {

				}
			})

}
// 点击具体移动的图片库名称
function selectPicBox(boxid, BoxName) {
	var checkinput = document.getElementsByName("checkinput");
	var length = 0;
	var values = "";
	for (var i = 0; i < checkinput.length; i++) {
		if (checkinput[i].checked) {
			values += checkinput[i].value + ",";
			length++;
		}
	}
	if (length == 0) {
		alert("请选择图片");
		return;
	}
	if (confirm("确定移动图片至图片库" + BoxName + "中？")) {
		var url2 = basePath + "ea/photomanager/sajax_n_ea_movePicToBox.jspa";
		$.ajax({
					url : encodeURI(url2),
					type : "post",
					dataType : "json",
					async : true,
					data : {
						"photoIdstr" : values,
						"photoBoxIdNew" : boxid
					},
					success : function(data) {
						alert("移动成功");
						window.location.reload();
					},
					error : function(data) {

					}
				})
	}
}

// 鼠标移动上图片库名称上的效果
function mouseoverSelected(photoboxID) {
	$("span#" + photoboxID + "picb").addClass("mouseover");
}
// 鼠标移动出图片库名称上的效果
function mouseoutSelected(photoboxID) {
	$("span#" + photoboxID + "picb").removeClass("mouseover");
}

function createPhotoBox() {
	var checkinput = document.getElementsByName("checkinput");
	var length = 0;
	var values = "";
	for (var i = 0; i < checkinput.length; i++) {
		if (checkinput[i].checked) {
			values += checkinput[i].value + ",";

			length++;
		}
	}
	if (length == 0) {
		alert("请选择要移动的图片");
		return;
	}
	$("#divname").text("新建图片库");
	$("#newPhotoBox #hidphotoboxId").val("");
	$("#newPhotoBox #photoBoxName").val("");
	$("#newPhotoBox #descriptor").val("");
	$("#jqModelPhotoBox").jqmShow();
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
	if (value >= 200) {
		var string = "<span style=\"color:#FF0000\">200/200</span>";
		var remark = $("#descriptor").val();
		remark = remark.slice(0, 200);
		$("#descriptor").val(remark);
	} else {
		var string = "<span style=\"color:#FF0000\">" + value + "</span>/200";

	}
	$('#titlearealen').html(string);
}
// 创建图片库的取消功能
function canclepb() {
	$("#jqModelPhotoBox").jqmHide();
}

// 删除图片
function deletePhoto() {
	var checkinput = document.getElementsByName("checkinput");
	var length = 0;
	var values = "";
	for (var i = 0; i < checkinput.length; i++) {
		if (checkinput[i].checked) {
			values += checkinput[i].value + ",";

			length++;
		}
	}
	if (length == 0) {
		alert("请选择图片");
		return;
	}
	if (confirm("确定删除？")) {
		var url = basePath + "ea/photomanager/sajax_n_ea_deletePhoto.jspa";
		$.ajax({
					url : url,
					type : "post",
					dataType : "json",
					async : true,
					data : {
						"photoIdstr" : values
					},
					success : function(data) {
                        alert("删除图片成功！");
                        window.location.reload();
					},
					error : function(data) {
                      alert("获取数据失败");
					}

				});

	}

}


//图片列表排序
function SortPhotos(){
	//$("#jqModelSort").jqmShow();暂不使用
	if($("#picdiv").html()==null||$("#picdiv").html()==""){
		alert("请先上传图片");
		return;
	}
	window.location.href = basePath
			+ "ea/photomanager/ea_showPhotoSortList.jspa?photoBoxID=" + photoBoxId;
}
// 排序确定提交暂不使用
function submitsort() {
	var sortType = $("input[name=sort]:radio:checked").val();
	window.location.href = basePath
			+ "ea/photomanager/ea_sortPicList.jspa?sortType=" + sortType+"&photoBoxId="+photoBoxId;
}


// 排序的取消功能暂不使用
function canclesort() {
	$("#jqModelSort").jqmHide();
}
