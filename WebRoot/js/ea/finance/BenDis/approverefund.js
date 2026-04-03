$(document).ready(function() {
 
	
	//保存同意
	$(".save").click(function(){
	
		if(confirm("确定提交保存?")){
		
			
			$("#form").attr("target","hidden").attr("action",basePath+"ea/refund/ea_approveOrReject.jspa")
		    document.form.submit.click();
		    token = 2;
		}
		
	});
	
	

});


function re_load() {
	window.opener.location.href=window.opener.location.href
	window.close();

}


