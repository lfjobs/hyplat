$(function(){
	//保存
	$(".submitResult").click(function(){
		
		if(confirm("确认提交吗？")){
		$("form#addApplyForm")
		.attr("target","hidden")
		.attr("action",basePath+ "/ea/bdbill/ea_refundApply.jspa?id="+id);
		document.addApplyForm.submit.click();
		token =2;
		}
	});
});
function re_load() {
	window.opener.location.reload();
	window.close();

}