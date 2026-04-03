$(document).ready(function() {	
	    var maxlength = 10;
	    //文本框中失去焦点事件截取日期为年月日
		$("table").find("input[onfocus]").each(function(){
		          var len = $(this).attr("value").length;
				  if(len > maxlength){
				       $(this).attr("value",$(this).attr("value").substring(0, maxlength));
				      
				  }
				});
		//日期中如果存在时分秒的截取为年月日
    	$("table").find("span[id][class=datas]").each(function(){
    	// var len1 = $(this).text().length;
    	  $(this).text($(this).text().substring(0, maxlength));
    	});
});
