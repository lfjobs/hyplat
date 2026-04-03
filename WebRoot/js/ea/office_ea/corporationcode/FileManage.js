$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	
	$('.address').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : type==1?'个人资料管理':'公共资料管理',
				minheight : 80,
				buttons : type==1?([{
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
					name : '共享',
					bclass : 'share',
					onpress : action
						// 当点击调用方法
					},{
					separator : true
				},{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
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
				}]):[{
					name : '查询',
					bclass : 'mysearch',
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
				fileManageID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				$t = $("table#stafftable");
				$t.find("#pic").attr("src", "");
				$("#jqModel").jqmShow();
				document.cstaffForm.reset();
				break;
			case '修改' :
				if (fileManageID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable2");
				$p = $("tr#" + fileManageID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				var photo = $p.find("span#fileAccessories").text();
				$t.find("#pic").attr("src", "");
				if (photo.length != 0) {
					$t.find("#pic").attr("src", basePath + photo);
				}
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (fileManageID == "") {
					alert('请选择！');
					return;
				}
				$f = $('#cstaffForm');
				$f.find(':input#fileManageID').val(fileManageID);
				if (confirm("确定删除？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/fileManage/ea_delFileManage.jspa?pageNumber="
											+ bpageNumber);
					document.cstaffForm.submit.click();
					$("tr#" + fileManageID).remove();
					fileManageID = "";
					token = 11;
				}
				break;
           case '共享' :
				if (fileManageID == "") {
					alert('请选择！');
					return;
				}
				
			  $("#jqModelShare").jqmShow();
				
				break;

			case '导出' :
				var url = basePath
						+ "ea/fileManage/ea_showExcel.jspa?pageNumber="
						+ bpageNumber + "&search=" + search+"&type="+type;
				open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/fileManage/ea_getaFileManageList.jspa?search="
						+ search+"&type="+type;
				numback(url);
				break;
		}
	}
	$("table tr[id]").click(function() {
				fileManageID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	/** ********************************************************** */
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	$("input#tosave").click(function() {
		if (fileManageID == "") {
			$("#cstaffForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "/ea/fileManage/ea_saveFileManage.jspa?pageNumber="
									+ bpageNumber);
			document.cstaffForm.submit.click();
			document.cstaffForm.reset();
			token = 1;
			return;
		}
		$("#cstaffForm").attr("target", "hidden").attr(
				"action",
				basePath + "/ea/fileManage/ea_saveFileManage.jspa?pageNumber="
						+ bpageNumber);
		document.cstaffForm.submit.click();
		token = 2;

	});

	$(".address tr[id]").dblclick(function() {
				action("修改");
			});
	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/fileManage/ea_toSearch.jspa?pageNumber="
						+ bpageNumber);
		document.postSearchForm.submit.click();
	});
	
	  //分享提交
		$("#toshare").click(function() {
				
				$f = $('#shareForm');
				$f.find('input#fileManageIDs').val(fileManageID);
				
				
				
				
				
				if (confirm("确定继续？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/fileManage/ea_setShare.jspa?pageNumber="
											+ bpageNumber);
					document.shareForm.submit.click();
					
					fileManageID = "";
					token = 2;
				}

	});
	
	//选中范围
	$("span.sharespan").click(function(){
	
		$("#"+$(this).attr("id")+"1").attr("checked",true);
		
		
	});


});
function re_load() {
	if (token){
		document.location.href = basePath
				+ "ea/fileManage/ea_getaFileManageList.jspa?pageNumber="
				+ bpageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&type="+type;
	
	}
}


// 下载文件
function loadFile(docPath) {
	//docPath = "/upload_files/company201009046vxdyzy4wg0000000025/office/filemanage/2014-04-08/2.flv";
	window.open(basePath + "/servlets/render?filename=" + encodeURI(docPath));
}

// 单附件查看（在新窗口中打开）
function lookImage(imageNO) {
	var imagepath = imageNO.substring(imageNO.indexOf('.'));
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
	} else if (imagepath == ".doc"
			|| imagepath == ".docx" || imagepath == ".xls"
			|| imagepath == ".xlsx" || imagepath == ".ppt"
			|| imagepath == ".pptx" || imagepath == ".vsd") {
	ViewOffice(imageNO, imagepath);

	} else if(imagepath==".pdf"||imagepath==".PDF"){
		 window.open(
					basePath
							+ "page/ea/common/pdf.jsp?docpath="+imageNO);
	}else if(imagepath == ".txt"){
	      	var url = basePath + "ea/fileManage/sajax_n_ea_txtToPdf.jspa";
	$.ajax({
				url : url,
				type : "post",
				dataType : "json",
				async:false,
				data : {
					"filePath" : imageNO

				},
				success : function(data) {
					var m = eval("(" + data + ")");
					var docpath = m.docpath;
					if(docpath==undefined){
						alert("系统繁忙，请稍后重试");
						return;
					}
					 window.open(
					basePath
							+ "page/ea/common/pdf.jsp?docpath="+docpath);
				},
				error : function(obj) {
					alert("系统异常，预览失败！");
				}
			});
	}else{
		alert("该格式文件不能预览");
	}

}

    function ViewOffice(docPath, fileType) {
	  window.open(
					basePath
							+ "page/ea/main/office_ea/corporationcode/Fileonlyreadprint.jsp?docPath="
							+ docPath + "&fileType=" + fileType);
}

function edit(docpath){
var fileType = docpath.substring(docpath.indexOf('.'));
if(fileType!=".docx"&&fileType!=".doc"&&fileType!=".xls"&&fileType!=".xlsx"&&fileType!=".ppt"&&fileType!=".pptx"&&fileType!=".VSD"&&fileType!=".vsd"){
	alert("该文件格式不支持在线编辑");
	return;
}
	window.open(
					basePath
							+ "page/ea/main/office_ea/corporationcode/Filewrite_word.jsp?docPath="
							+ docpath + "&fileType=" + fileType);
	
}



function tosave(){
	
	if (fileManageID == "") {
			$("#cstaffForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "/ea/fileManage/ea_saveFileManage.jspa?pageNumber="
									+ bpageNumber);
			document.cstaffForm.submit.click();
			document.cstaffForm.reset();
			$("#upload_content #divFileProgressContainer").html("");
			token = 2;
			return;
		}
		$("#cstaffForm").attr("target", "hidden").attr(
				"action",
				basePath + "/ea/fileManage/ea_saveFileManage.jspa?pageNumber="
						+ bpageNumber);
		$("#upload_content #divFileProgressContainer").html("");
		document.cstaffForm.submit.click();
		token = 2;	
	
}
