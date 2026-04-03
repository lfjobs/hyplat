$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.cabinet0').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '站内搜索',
				minheight : 350,
				buttons : [{
					name : '返回',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});

	function action(com, grid) {
		switch (com) {
			case '返回' :
				history.back();
				break;
			

		}
	}

	
});


// 下载文件
function loadFile(docPath) {
	window.open(basePath + "/servlets/render?filename=" + docPath);
}
// 单附件查看（在新窗口中打开）
function lookImage(imageNO) {
	var imagepath = imageNO.substring(imageNO.indexOf('.'));
	if (imagepath == ".jpg" || imagepath == ".gif" || imagepath == ".bmp"
			|| imagepath == ".tiff") {
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

	} 
	else if(imagepath == ".html"||imagepath ==".htm"||imagepath ==".jsp"){
		open(basePath+imageNO);
	}
	else if(imagepath == ".pdf"||imagepath ==".PDF"||imagepath == ".txt"||imagepath == ".doc" || imagepath == ".docx"
			|| imagepath == ".xls" || imagepath == ".xlsx"
			|| imagepath == ".ppt" || imagepath == ".pptx"){
		var url = basePath
				+ "ea/filecabinet/sajax_n_ea_pdfToSwf.jspa";
		$.ajax({
					url : url,
					type : "post",
					dataType : "json",
					data:{
					 "filePath":imageNO
					
					},
					success : function(data) {
						var m = eval("(" + data + ")");
						var path = m.result;
						//path = path.replace("\\","/");
						open(basePath+"page/ea/main/office_ea/fileCabinet/pdfpreview.jsp?url="+path);

					},
					error : function(obj) {
						alert("系统异常，预览失败！");
					}
				});
		
	}else{
		alert("该格式文件不能预览");
	}

}
// 修改文件夹属性 ——修改名称提交功能
function submitFolderA() {
	var fileFolderNameA = $("#fileFolderNameA").val();
	var folderId = $("#folderIdhid").val();
	window.location.href = basePath
			+ "ea/filecabinet/ea_updateFolderName2.jspa?fileCabinetId="
			+ fileCabinetId + "&folderId=" + folderId + "&fileFolderNameA="
			+ fileFolderNameA;
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
						$("#folderIdhid").val(oList.fileFolderId);;
						$("#directoryA").text(oList.saveCabinet);
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
