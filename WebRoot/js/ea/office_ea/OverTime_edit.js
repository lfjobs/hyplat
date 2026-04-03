$(function(){
	//为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
	       modal: true,// 
	       overlay: 20 // 
	       }).jqmAddClose('.close');//
    if(acceName != ''){
    	var onload =acceName.substring(acceName.lastIndexOf("."),acceName.length);
    	if(onload.toLowerCase()!=".jpg" && onload.toLowerCase()!=".gif" && onload.toLowerCase()!=".png"){
    		$("#look").hide();
 			$("#load").find("a").attr("href",basePath+"ea/publicreceipts/ea_downFile.jspa?downLoadPath="+acceName);
   			$("#load").show();
   		}
    }
});