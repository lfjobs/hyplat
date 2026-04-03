$(document).ready(function() {
		$("input#add").click(function(){
			if($("#account").val()=='sa'){
				$("#jqModelAdd").jqmShow();
			}
		});
		
		$("#toadd").click(function(){
			$(".put3", $("#AddForm")).trigger("blur");
			if ($("form .error").length) {
				alert("请填完所有必填项");
				return;
			}
			$("#AddForm").attr("target","hidden").attr("action",basePath + "/ea/hdocument/ea_saveHDocument.jspa?pageNumber="+ pNumber);
			document.AddForm.submit.click();
			$(":input",$("table#tt")).attr("value","");
			token = 1;
		});
		
		$("input#search").click(function(){
			$("#jqModelSearch").jqmShow();
		});
		
		$("#toSearch").click(function(){
			$("#SearchForm").attr("action",basePath + "/ea/hdocument/ea_toSearch.jspa?pageNumber="+ pNumber);
			document.SearchForm.submit.click();
		});
});

function re_load(){
	document.location.href=basePath+"/ea/hdocument/ea_getHDocumentList.jspa?pageNumber="+pNumber+"&search="+search;
}