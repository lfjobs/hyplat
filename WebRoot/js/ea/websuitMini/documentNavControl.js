$(document).ready(function(){
	$("#upload").click(function(){
		if($("#filevalue").val() == ""){
			   alert("请选择要上传的公文！");
				return;
		}
		if($("#title").val()==""){
			alert("文件标题不为空！");
			return;
		}if($("#themes").val()==""){
			alert("主题词不为空！");
			return;
		}if($("#docType").val()=="ss"){
			alert("请选择公文类型！");
			return;
		}
		 var documentType = $("#filevalue").val().substring($("#filevalue").val().lastIndexOf("."));
		 var documentTypelower = documentType.toLocaleLowerCase();
		 if(documentTypelower == ".doc" || documentTypelower == ".docx" || documentTypelower==".xls"||documentTypelower==".xlsx"){
			  var filename = $("#filevalue").val();
				$('#uploadFiles').attr("target","hidden")
				.attr(
						"action",
						basePath
								+ "ea/documentcommon/ea_uploadFiles.jspa?contentType="+ filename );
				document.uploadFiles.submit.click();
			
				$("#uploading").show();
				token = 2;
		  }else{
			  alert("只支持office格式的所有文档类型!");
				 return; 
		  }	
	});
});
function re_load() {
	/*if (token)
		document.location.href = basePath
				+ "ea/documentinfo/ea_getReceiveBoxList.jspa?searchType=mobile&data=Math.random()";*/
	$("#uploading").hide();
}
			   