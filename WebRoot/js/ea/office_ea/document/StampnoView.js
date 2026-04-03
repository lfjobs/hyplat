$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	// 提交审批通过转去盖章
	$("#submitResult").click(function() {
		var radio = $(".source:checked").val();

		if(radio=="in") {
			if ($("#SendForm #receiverID").val() == "") {
				alert("请选择收件人");
				return;
			}else{

				$("#SearchTable").remove();
			}
		}else{
			if($("#receiverid").val()==""){
				alert("请填写收件人");
				return;
			}else{
				$("#SearchTable2").remove();
			}

		}
		var sealComment = $("#sealComment").val();
		if ((sealComment.length) * 2 >= 200) {
			alert("意见长度不能大于200");
			return;
		}
	//	if (confirm("确定提交？")) {
			$("#SendForm #docId").val(docId);
			$("#comment").val(sealComment);
			$("#jump").val("publish");

			$("#SendForm").attr("target", "hidden").attr(
					"action",
					basePath + "ea/documentflow/ea_sealDocument.jspa?date="
							+ new Date());
			document.SendForm.submit.click();
			token = 2;
		//}

	});

});

/**
 * 转至分发人
 */
function Approve() {
	$("#jqModelSend").jqmShow();
	document.SendForm.reset();
	getAllCompanyOfGroup();
	$("#SendForm #titlem").text("选择人员——转至分发人");

}
/**
 * 存档
 */
function Finish() {
	//if (confirm("确定存档？")) {
		$("#wviewForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/documentflow/ea_sealDocument.jspa?jump=finish&date="
								+ new Date());
		document.wviewForm.submit.click();
		token = 2;
	//}

}

function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/documentflow/ea_getSealDocList.jspa?finishType=seal";

	}
}

// 点击查看office
function ViewOffice(docPath, fileType, fileShowNameField, fileId) {
	var sealName = $("#sealerName").val();
	var formalNum = $("#formalNum").text();
	window.open(basePath + "page/ea/main/office_ea/document/word4.jsp?docPath="
			+ docPath + "&fileType=" + fileType + "&fileShowNameField="
			+ fileShowNameField + "&userName=" + sealName+"&fileId="+fileId
			+"&formalNum="+encodeURI(formalNum));
}
