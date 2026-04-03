$(document).ready(function() {
	jQuery.ajaxSetup({
				cache : false
			});

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.cabinet0').flexigrid({
		height : 330,
		width : 'auto',
		minwidth : 30,
		title : "<img src='"
				+ basePath
				+ "images/ea/office/fileCabinet/file_folder.png' width='20' height='30'/>"
				+ fileCabinetName,
		minheight : 330,
		buttons : [{
			name : '返回上级',
			bclass : 'prev',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
		}, {
			name : '上传文件',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
		}, {
			name : '新建文件夹',
			bclass : 'edit',
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
			name : '移动到',
			bclass : 'delete',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
		}, {
			name : '复制到',
			bclass : 'delete',
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
			case '返回上级' :
				// history.back();
				window.location.href = basePath
						+ "ea/filecabinet/ea_getListForFileCabinet.jspa";

				break;
			case '上传文件' :
				$("#uploadFiles").text("添加附件");
				$("#hideAttach").hide();
				$("#hideAttach").empty();

				$("#jqModelFileUpload").jqmShow();
				break;
			case '新建文件夹' :
				$("#jqModelFolder").jqmShow();
				break;
			case '删除' :
				var Id = $("input[name=radioGroup]:radio:checked").val();
				if (Id == undefined) {
					alert('请选择!');
					return;
				}

				if (confirm("确定全部删除？")) {
					window.location.href = basePath
							+ "ea/filecabinet/ea_delFileCabinet.jspa?Id=" + Id
							+ "&fileCabinetId=" + fileCabinetId
							+ "&sort=folder";

					alert("删除成功");

				}
				break;
			case '移动到' :
				var fileId = $("input[name=radioGroup]:radio:checked").val();
				if (fileId == undefined) {
					alert('请选择！');
					return
				}
				if (fileId.substring(0, 2) == "fo") {// 说明是文件夹
					alert("对不起，只能移动文件");
					return;
				} else {

					$("#jqModelFileMove").jqmShow();
					$("#moveHide").val(fileId);
				}

				break;

			case '复制到' :
				var fileId = $("input[name=radioGroup]:radio:checked").val();
				if (fileId == undefined) {
					alert('请选择！');
					return
				}
				if (fileId.substring(0, 2) == "fo") {// 说明是文件夹
					alert("对不起，只能复制文件");
					return;
				} else {

					$("#jqModelFileCopy").jqmShow();
					$("#copyHide").val(fileId);
				}

				break;

			case '设置每页显示条数' :
				var url = basePath
						+ "ea/filecabinet/ea_getFileOfFolderList.jspa?fileCabinetId=" + fileCabinetId;
				numback(url);
				break;

		}
	}
	queryFileCabinet("uploadFile");
	queryFileCabinet("moveFile");
	queryFileCabinet("copyFile");
	// 所有关闭功能

	$("#cancelNewFolder").click(function() {
				$("#jqModelFolder").jqmHide();
			});

	$("#cancelFolderA").click(function() {
				$("#jqModelFolderAttr").jqmHide();
			});
	$("#cancelFileA").click(function() {
				$("#jqModelFileAttr").jqmHide();
			});

	$("#cancleUpload").click(function() {
				$("#jqModelFileUpload").jqmHide();
			});

	$("#cancelUpdate").click(function() {
				$("#jqModelFileUpdate").jqmHide();
			});

	$("#cancelMove").click(function() {
				$("#jqModelFileMove").jqmHide();
			});

	$("#cancelCopy").click(function() {
				$("#jqModelFileCopy").jqmHide();
			});

	$("#sumbitFolder").click(function() {
		var Foldername = $.trim($("#newFolder #fileFolderName12").val());
		if (Foldername == null || Foldername == "") {
			alert("请输入文件夹名称");
			return;
		}

		$("#newFolder")
				.attr(
						"action",
						basePath
								+ "ea/filecabinet/ea_newFileFolder.jspa?fileCabinetKey="
								+ fileCabinetKey);
		document.newFolder.submit.click();

	});
	$("#submitUpload").click(function() {
		var filestr = document.getElementsByName("fileUpload.sourcePath");
		if (filestr.length == 0) {
			alert("您还没有添加附件");
			return false;
		}
		var fso, f;
		var fileSize = "";
		for (var i = 0; i < filestr.length; i++) {
			var file = getPath(filestr[i]);
			try {
				if (file != "") {
					fso = new ActiveXObject("Scripting.FileSystemObject");
					f = fso.GetFile(file);
					fileSize += Math.ceil(f.size / 1024) + ",";
					if (f.size > 10 * 1024 * 1024) {
						alert("第" + (i + 1) + "个文件大小不应超过10M");
						return false;
					}

				} else {
					alert("您还没有添加附件");
					return false;
				}
			} catch (e) {
				alert("请将未标记为可安全执行脚本的ActiveX设为提示刷新后，当提示时选择”是“");
				$("#jqModelFileUpload").jqmHide();
				return false;
			}
		}

		$("#uploadFileForm #uploadInfofileSize").val(fileSize);
		$("#uploadFileForm #uploadInfoMode").val("folder");
		$("#uploadFileForm #uploadInfoCaId").val(fileCabinetId);

		$("#uploadFileForm").attr("action",
				basePath + "ea/filecabinet/ea_uploadFile.jspa");

		document.uploadFileForm.submit.click();

	});

	$("#submitUpdate").click(function() {
		var filestr = document.getElementsByName("fileUpload.sourcePath");
		if (filestr.length == 0) {
			alert("您还没有添加附件");
			return false;
		}
		var fso, f;
		var fileSize = "";
		for (var i = 0; i < filestr.length; i++) {
			var file = getPath(filestr[i]);
			if (file != "") {
				fso = new ActiveXObject("Scripting.FileSystemObject");
				f = fso.GetFile(file);
				fileSize += Math.ceil(f.size / 1024) + ",";
				if (f.size > 10 * 1024 * 1024) {
					alert("第" + (i + 1) + "个文件大小不应超过10M");
					return false;
				}

			} else {
				alert("您还没有添加附件");
				return false;
			}
		}

		$("#updateFileForm").attr(
				"action",
				basePath + "ea/filecabinet/ea_updateFile.jspa?fileSize="
						+ fileSize + "&fileCabinetId=" + fileCabinetId
						+ "&uploadMode=folder");
		document.uploadFileForm.submit.click();

	});

});

var k = 0;
function uploadFiles(str) {
	if (str == "upload") {
		$("#hideAttach").show();
		$("#uploadFiles").text("继续添加");
		$("#hideAttach")
				.append("<div id='attach_"
						+ k
						+ "'><input type='file' name='fileUpload.sourcePath' id='sourcePath_"
						+ k + "'/><a href='javascript:deleteAttach(" + k
						+ ")'>删除</a></div>");
		k++;
	}
}

function deleteAttach(k) {
	$("#attach_" + k).remove();
}

function getPath(obj) {
	if (obj) {
		if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
			obj.select();
			return document.selection.createRange().text;
		} else if (window.navigator.userAgent.indexOf("Firefox") >= 1) {
			if (obj.files) {
				return obj.files.item(0).getAsDataURL();
			}
			return obj.value;
		}
		return obj.value;
	}
}

// 根据公司,创建人查找文件柜——>根据文件柜查文件夹
function queryFileCabinet(str) {
	var urlc = basePath
			+ "ea/filecabinet/sajax_n_ea_getFileCabinetList.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(urlc),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					$("#directory").empty();
					$("#directoryMove").empty();
					$("#directoryCopy").empty();
					var member = eval("(" + data + ")");
					var oList = member.fileClist;

					for (var i = 0; i < oList.length; i++) {
						var fileCabinetId = oList[i].fileCabinetId;
						var fileCabinetName = oList[i].fileCabinetName;
						queryFileFolder(fileCabinetId, fileCabinetName, str);
					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

	$("option[value=" + this.value + "]", $("#deptIDofSubscriber")).val("");
}
// 为了获取目录的树（select）查询文件夹
function queryFileFolder(fileCabinetId, fileCabinetName, str) {
	var urlf = basePath
			+ "ea/filecabinet/sajax_n_ea_getFileFolderList.jspa?fileCabinetId="
			+ fileCabinetId + "&date=" + new Date().toLocaleString();
	$.ajax({

				url : encodeURI(urlf),
				type : "get",
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var oList = member.fileFlist;
					if (str == "uploadFile") {
						$("#directory").append("<option value=" + fileCabinetId
								+ ">" + fileCabinetName + "</option>");
						for (var i = 0; i < oList.length; i++) {
							var fileFolderId = oList[i].fileFolderId;
							var fileFolderName = oList[i].fileFolderName;
							$("#directory").append("<option value="
									+ fileFolderId
									+ "> &nbsp;&nbsp;&nbsp;&nbsp;├"
									+ fileFolderName + "</option>");
						}
					}
					if (str == "uploadFileUpdate") {
						$("#directoryUpdate").append("<option value="
								+ fileCabinetId + ">" + fileCabinetName
								+ "</option>");
						for (var i = 0; i < oList.length; i++) {
							var fileFolderId = oList[i].fileFolderId;
							var fileFolderName = oList[i].fileFolderName;
							$("#directoryUpdate").append("<option value="
									+ fileFolderId
									+ "> &nbsp;&nbsp;&nbsp;&nbsp;├"
									+ fileFolderName + "</option>");
						}
					}
					if (str == "moveFile") {
						$("#directoryMove").append("<option value="
								+ fileCabinetId + ">" + fileCabinetName
								+ "</option>");
						for (var i = 0; i < oList.length; i++) {
							var fileFolderId = oList[i].fileFolderId;
							var fileFolderName = oList[i].fileFolderName;
							$("#directoryMove").append("<option value="
									+ fileFolderId
									+ "> &nbsp;&nbsp;&nbsp;&nbsp;├"
									+ fileFolderName + "</option>");
						}
					}
					if (str == "copyFile") {
						$("#directoryCopy").append("<option value="
								+ fileCabinetId + ">" + fileCabinetName
								+ "</option>");
						for (var i = 0; i < oList.length; i++) {
							var fileFolderId = oList[i].fileFolderId;
							var fileFolderName = oList[i].fileFolderName;
							$("#directoryCopy").append("<option value="
									+ fileFolderId
									+ "> &nbsp;&nbsp;&nbsp;&nbsp;├"
									+ fileFolderName + "</option>");
						}
					}

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}

			});
}
// 进入文件夹
function enterFolder(fileFolderId) {
	window.location.href = basePath
			+ "ea/filecabinet/ea_getFileOfFolder.jspa?fileFolderId="
			+ fileFolderId;
}
// 查看属性
function viewAttribute(fileId) {
	var url = basePath + "ea/filecabinet/sajax_n_ea_getAttributes.jspa?fileId="
			+ fileId + "&date=" + new Date().toLocaleString();

	$.ajax({
				url : url,
				type : "get",
				dataType : "json",
				success : function(data) {
					var member = eval("(" + data + ")");
					var oList = member.bean;
					if (fileId.substring(0, 2) == "fo") {
						$("#fileFolderNameA").val(oList.fileFolderName);
						$("#descriptorA").text(oList.descriptor);
						$("#usedSpaceA").text(oList.usedSpace);
						$("#fileNumberA").text(oList.fileNumber + "个");
						$("#createrNameA").text(oList.createrName);
						$("#createTimeA").text(oList.createTimeString);;
						$("#folderIdhid").val(oList.fileFolderId);
						$("#jqModelFolderAttr").jqmShow();
					} else {
						$("#fileUploadNameA").text(oList.fileUploadName);
						$("#fileUploadSizeA").text(oList.fileUploadSize);
						$("#uploadPersonNameA").text(oList.uploadPersonName);
						$("#uploadTimeA").text(oList.uploadTimeString);
						$("#saveDirectoryA").text(oList.saveDirectory);
						$("#jqModelFileAttr").jqmShow();
					}
				},
				error : function(data) {
					alert("数据获取失败！");
				}
			});
}

// 修改文件夹属性 ——修改名称提交功能
function submitFolderA() {
	var fileFolderNameA = $.trim($("#folderAttribute #fileFolderNameA").val());
	if (fileFolderNameA == null || fileFolderNameA == "") {
		alert("请输入文件夹名称");
		return;
	}
	var folderId = $("#folderIdhid").val();
	window.location.href = basePath
			+ "ea/filecabinet/ea_updateFolderName.jspa?folderId=" + folderId
			+ "&fileFolderNameA=" + fileFolderNameA;
}

// 文件移动和拷贝提交调用方法
function fileMoveAndCopy(str) {
	if (str == "moveFile") {
		var fileId = document.getElementById("moveHide").value;
		$("#fileMove").attr(
				"action",
				basePath + "ea/filecabinet/ea_fileMove.jspa?fileId=" + fileId
						+ "&fileCabinetId=" + fileCabinetId
						+ "&uploadMode=folder");
		document.fileMove.submit.click();
	} else {
		var fileId = document.getElementById("copyHide").value;
		$("#fileCopy").attr(
				"action",
				basePath + "ea/filecabinet/ea_fileCopy.jspa?fileId=" + fileId
						+ "&fileCabinetId=" + fileCabinetId
						+ "&uploadMode=folder");
		document.fileCopy.submit.click();
	}
}

// 更新上传文件(打开页面)
function updateFile(fileId) {
	$("#jqModelFileUpdate").jqmShow();
	$("#hiddenFileId").val(fileId);
}

// 下载文件
function loadFile(docPath) {
	window.open(basePath + "/servlets/render?filename=" + docPath);
}
// 单附件查看（在新窗口中打开）
function lookImage(imageNO) {
	var imagepath = imageNO.substring(imageNO.lastIndexOf('.'));
	if (imagepath == ".jpg" || imagepath == ".gif" || imagepath == ".bmp"
			|| imagepath == ".tiff" || imagepath == ".png") {
		var imgurl = basePath + imageNO;
		newwin = window.open('about:blank', '',
				'width=800,height=600,left=0,top=0');
		var img = new Image();
		img.src = imgurl;
		if (img.height > 600 || img.width > 800) {
			newwin.document
					.write('<body  style="margin: 0px"><div align="center"><img id="img" height="600" width="800" src="'
							+ imgurl + '"></div>');
		} else {
			newwin.document
					.write('<body  style="margin: 0px"><div align="center"><img id="img" src="'
							+ imgurl + '"></div>');
		}
		newwin.resizeTo(screen.availWidth, screen.availHeight);

	} else if (imagepath == ".html" || imagepath == ".htm"
			|| imagepath == ".jsp") {
		open(basePath + imageNO);
	} else if (imagepath == ".pdf" || imagepath == ".PDF"
			|| imagepath == ".txt" || imagepath == ".doc"
			|| imagepath == ".docx" || imagepath == ".xls"
			|| imagepath == ".xlsx" || imagepath == ".ppt"
			|| imagepath == ".pptx") {
		existSwf(imageNO);
		var url = basePath + "ea/filecabinet/sajax_n_ea_pdfToSwf.jspa";
		$.ajax({
			url : url,
			type : "post",
			dataType : "json",
			data : {
				"filePath" : imageNO

			},
			success : function(data) {
				var m = eval("(" + data + ")");
				var path = m.result;
				path = path.replace("\\", "/");
				open(basePath
						+ "page/ea/main/office_ea/fileCabinet/pdfpreview.jsp?url="
						+ path);
			},
			error : function(obj) {
				alert("请重试！");
			}
		});

	} else {
		alert("该格式文件不能预览");
	}

}

function existSwf(imageNO) {
	var url = basePath + "ea/filecabinet/sajax_n_ea_existSwf.jspa";
	$.ajax({
				url : url,
				type : "post",
				dataType : "json",
				data : {
					"filePath" : imageNO

				},
				success : function(data) {
					var m = eval("(" + data + ")");
					var ex = m.result;
					if (ex == "unexists") {
						showDiv();
					}
				},
				error : function(obj) {
					alert("系统异常，预览失败！");
				}
			});
}

function showDiv() {
	document.getElementById('popDiv').style.display = 'block';
	document.getElementById('popIframe').style.display = 'block';
	document.getElementById('bg').style.display = 'block';
	setTimeout("closeDiv()", 20000);//
}
function closeDiv() {
	document.getElementById('popDiv').style.display = 'none';
	document.getElementById('bg').style.display = 'none';
	document.getElementById('popIframe').style.display = 'none';
}
