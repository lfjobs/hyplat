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
		var submitType = $("#jqModelSend #submitType").val();
		var subscriberComment = $("#subscriberComment").val();
		if ((subscriberComment.length) * 2 >= 200) {
			alert("意见长度不能大于200");
			return;
		}
	//	if (confirm("确定提交？")) {

			$("#SendForm #docId").val(docId);
			$("#comment").val(subscriberComment);
			if (submitType == "toSeal") {
				$("#jump").val("approve");
			} else {
				$("#jump").val("transfer");
			}
			$("#SendForm").attr("target", "hidden").attr(
					"action",
					basePath + "ea/documentflow/ea_examineDocument.jspa?date="
							+ new Date());
			document.SendForm.submit.click();
			token = 2;
		//}

	});
});

// 审批不通过
function noApprove() {
	$("#wviewForm")
			.attr("target", "hidden")
			.attr(
					"action",
					basePath
							+ "ea/documentflow/ea_examineDocument.jspa?jump=noapprove&date="
							+ new Date());
	document.wviewForm.submit.click();
	token = 2;

}

// 返回修改
function Reject() {
	$("#wviewForm")
			.attr("target", "hidden")
			.attr(
					"action",
					basePath
							+ "ea/documentflow/ea_examineDocument.jspa?jump=reject&date="
							+ new Date());
	document.wviewForm.submit.click();
	token = 2;

}
// 点击转去盖章/转交下个人审批 toSeal/toNext
function Approve(type) {
	$("#jqModelSend").jqmShow();
	document.SendForm.reset();
	getAllCompanyOfGroup();
	$("#jqModelSend #submitType").val(type);
	if (type == "toSeal") {
		$("#SendForm #titlem").text("选择人员——转去盖章");
	} else {
		$("#SendForm #titlem").text("选择人员——转交审批");
	}

}

function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/documentflow/ea_getExamineDocList.jspa?finishType=examine";

	}
}

// 点击查看office
function ViewOffice(docPath, fileType, fileShowNameField,fileId) {
	var subscriberName = $("#subscriberName").text();
	window.open(basePath + "page/ea/common/wordcommon.jsp?docPath="
			+ docPath + "&fileType=" + fileType + "&fileShowNameField="
			+ fileShowNameField + "&subscriberName=" + subscriberName+"&fileId="+fileId);

}