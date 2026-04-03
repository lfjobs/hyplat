$(document).ready(function() {

	//新增一行
	$("#newline").click(function(){
		
		select++;
		// 克隆一行并修改文本框的name
		$("#kelong").before(
		
		$("#kelong").clone(true).show()
		.attr("id",
				"kelong" + select)
		);
		$("#kelong" + select).find(':input').each(function() {
			$(this).attr("name","jobTaskmap[" + select + "]." + this.name);
		});
		
	});
	
	//选中时间进度
	$("td.spancolor").click(function(){
		
		$(this).css("background","green");
		
	});
	
	$("td.spancolor").toggle( 
			function () { 
				$(this).css("background","green");
				var timeid = $(this).attr("id");
			    var trnum = $(this).parent().attr("id").substring(6);
				$(this).html("<input type='hidden' class='tim' name='jobTaskmap[" + trnum + "].time' value='"+timeid.substring(4)+"'>");
			}, 
			function () { 
				$(this).css("background","#FFFFFF");
				$(this).html("");
			} 
	);
	
	
	//删除
	$("#deleteb").click(function(){
		$(".checkgoods input:checkbox:checked").each(function(){
			$(this).parent().parent().remove();
			
		});
	});
	
	//保存
	$("#fullsave").click(function() {
		$("#kelong1").find(':input').each(function() {
			if($(this).attr("class")!="tim"){
			$(this).attr("name","jobTaskmap[1]." + this.name);
			}
			
		});
	 $("#projectName").val($("#projectID option:selected").text());
		

	$("#taskTargetForm").attr(
			"action",
			pbasePath + "ea/jobtask/s_ea_saveTaskTarget.jspa?pageNumber="
					+ ppageNumber);
	document.taskTargetForm.submit.click();
	token = 2;
	
	
		
	});
	

});
function re_load() {
	document.location.href = pbasePath
			+ "ea/jobtask/s_ea_getJobTaskList_a.jspa?staffID=" + pstaffID
			+ "&pageNumber=" + ppageNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value");
}