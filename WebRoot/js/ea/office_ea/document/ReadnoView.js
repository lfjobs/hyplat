$(document).ready(function() {

		});

//// 展现word文件
//function ViewOffice(docPath,fileShowNameField, fileType, load,
//		print,docId) {
//	alert(load+"dd"+print);
//	window.open(basePath
//			+ "page/ea/main/office_ea/document/wordonlyread.jsp?docPath="
//			+ docPath + "&fileShowNameField=" + fileShowNameField
//			+ "&fileType=" + fileType + "&load=" + load + "&print=" + print
//			+ "&date=" + new Date());
//	countHits(docId);
//}

// 展现word文件
function ViewOffice(docPath, fileType,fileShowNameField,docId){
	window.open(
					basePath
							+ "page/ea/main/office_ea/document/wordonlyreadprint.jsp?docPath="
							+ docPath + "&fileType=" + fileType
							+ "&fileShowNameField="+fileShowNameField);
	countHits(docId);
}

// 标记为已读
function markReaded() {
//	if (confirm("确定标记为已读？")) {

		$("#wviewForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/documentflow/ea_completeRead.jspa?date="
						+ new Date());
		document.wviewForm.submit.click();
		token = 2;
	//}
}

// 点击word或者扫描附件时设置属性文件的值
function countHits(docId) {

	var url = basePath + "ea/documentflow/sajax_n_ea_completeRead.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					docId : docId
				},
				success : function cbf(data) {

				},
				error : function cbf(data) {
					alert("数据获取失败！")
				}
			});

}

function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/documentflow/ea_getReadDocList.jspa?finishType=read";

	}
}
