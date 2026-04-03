$(function(){
//返回；
$("#back").click(function(){
  
	history.back(0);
	
});

	
});


function getCostSheetBill(type){
	$("#print").val(type);
	$("#projectprint").attr("src",basePath+"ea/costsheet/ea_getCoverContent.jspa?ppID="+ppID+"&type="+type);
}

function printPreview(){
	var type = $("#print").val();
	if(type==""){
		
		alert("请选择要打印的页面");
		return;
	}
	window.open(basePath+"ea/costsheet/ea_getCoverContent.jspa?ppID="+ppID+"&type="+type);
}




