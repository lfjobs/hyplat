$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,
		overlay : 20	
		});//.jqmAddClose('.close');	

	//---显示导入的主界面---//
	$("#jqModelShow").jqmShow();
	
	//---检查---//
	$(".JQueryValidate").click(function() {		
		clear();
		fill();		
		$("span[id='spanProgressBar']").css("display","block");
		try{
			
		$.ajax(
			{
			type:"get",
			url:basePath + "ea/importdata/sea_validateFileData.jspa",
			data:{
				fileType1 : $("#fileType1").val(),
				fileName1 : $("#fileName1").val(),
				fileType2 : $("#fileType2").val(),
				fileName2 : $("#fileName2").val(),
				fileType3 : $("#fileType3").val(),
				fileName3 : $("#fileName3").val(),
				fileType4 : $("#fileType4").val(),
				fileName4 : $("#fileName4").val(),
				fileType5 : $("#fileType5").val(),
				fileName5 : $("#fileName5").val()				
				},
			dataType:"text",//not json
			success:function(data){
				$("span[id='spanProgressBar']").css("display","none");				
				alert(data);				
			},
			error:function(XmlHttpRequest, textStatus, errorThrown){
				
				//alert(XmlHttpRequest.status);
                //alert(XmlHttpRequest.readyState);
                //alert(XmlHttpRequest.responseText);
                //alert(textStatus);
                //alert(errorThrown);
                
				$("span[id='spanProgressBar']").css("display","none");
				alert("数据验证时发生异常,可联系管理员!");				
			}			
		});
	}catch(err){alert(err.toString());}
	});
	
	//---导入---//JQueryImport
	$(".JQueryImport").click(function() {
		clear();
		fill();
		$("span[id='spanProgressBar']").css("display","block");
		$.ajax(
			{
			type:"post",
			url:basePath + "ea/importdata/tea_importData2DataBase.jspa",
			data:{
				fileType1 : $("#fileType1").val(),
				fileName1 : $("#fileName1").val(),
				fileType2 : $("#fileType2").val(),
				fileName2 : $("#fileName2").val(),
				fileType3 : $("#fileType3").val(),
				fileName3 : $("#fileName3").val(),
				fileType4 : $("#fileType4").val(),
				fileName4 : $("#fileName4").val(),
				fileType5 : $("#fileType5").val(),
				fileName5 : $("#fileName5").val()		
				},
			dataType:"text",//not json
			success:function(data){
				$("span[id='spanProgressBar']").css("display","none");				
				alert(data);
			},
			error:function(data){
				$("span[id='spanProgressBar']").css("display","none");
				alert("数据导入时发生异常,可联系管理员!");				
			}			
		});
	});	
		
});

window.onload = function () {
	swfu = new SWFUpload({
		upload_url: basePath + "servlet/FileUploadServlet.htm",  //已在web.xml里配置
		post_params: {},
		
		// File Upload Settings
		file_size_limit : "10 MB",	
		file_types : "*.xls;*.xlsx",
		file_types_description : "电子表格文件",
		file_upload_limit : "5",
						
		file_queue_error_handler : fileQueueError,
		file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
		file_queued_handler : fileQueued,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadComplete,

		// Button Settings
		button_image_url : basePath + "images/SmallSpyGlassWithTransperancy_17x18.png",
		button_placeholder_id : "spanButtonPlaceholder",
		button_width: 180,
		button_height: 18,
		button_text : '<span class="button">选择数据文件 <span class="buttonSmall">(&lt;=10 MB)</span></span>',
		button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
		button_text_top_padding: 0,
		button_text_left_padding: 18,
		button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
		button_cursor: SWFUpload.CURSOR.HAND,
		
		// Flash Settings
		flash_url : basePath + "swfupload/swfupload.swf",

		custom_settings : {
			upload_target : "divFileProgressContainer"
		},
		// Debug Settings
		debug: false  //是否显示调试窗口
	});
	
	$( document ).ajaxError(function( event, jqxhr, settings, exception )
			{  
				alert(event.toString());
				alert(exception);
			});
};

function startUploadFile(){
	if ($("#fileType").val()=="0")
		{
			alert("请选择文件类型");
			return;
		};
	swfu.startUpload();
}

function clear(){
	for (var j = 1; j < 6; j++){				
		$("#fileType"+j).val("");
		$("#fileName"+j).val("");
	}
}

function fill(){
	var infoTable = document.getElementById("infoTable");
	for ( var int = 0; int < infoTable.rows.length; int++) {
		for (var j = 0; j < 5; j++){//0   1(文件类型)  2(文件名) 3  4				
			if (j==1) $("#fileType"+(int+1)).val(infoTable.rows[int].cells[j].innerText);
			if (j==2) $("#fileName"+(int+1)).val(infoTable.rows[int].cells[j].innerText);
		}
	}
}





