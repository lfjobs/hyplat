  $(function(){
  $("input.submitClose").click( 
			function(){
				
					$("#refund").jqmHide();
				
	});
//提交申请
	$(".submitResult").click(function(){
		
		$("form :input:.put3").trigger("blur");
		  
          if($("form .error").length)
          { 
            return;
          } 
			if(confirm("确认提交申请吗？")){
				
					
					$("form#refundForm")
					.attr("target","hidden")
					.attr("action",basePath+ "/ea/bdbill/ea_refundApply.jspa?id="+id+"&ppid="+ppid);
					
					document.refundForm.submit.click();
					document.refundForm.reset();
					token = 2;
					
				
				
			}
			
		    
		
		
	});
//上传图片
	$("input.upload").each(function(){
	
		var id=$(this).attr("id");
		
		$("#"+id).uploadify({
			
			"swf"      : basePath+"js/uploadify/uploadify.swf",    //指定上传控件的主体文件
			"fileObjName"  : "file",        //文件对象
			"uploader" : basePath+"/ea/refund/sajax_ea_uploadFile.jspa?companyID="+companyID,   //指定服务器端上传处理文件
			"fileSizeLimit":3072,
			"queueSizeLimit":1,
			'buttonText' : '图片上传',
			'width': 72,
			'fileTypeDesc':'请选择图片格式',
			'fileTypeExts':'*.jpeg;*.jpg;*.gif;*.png;*.JPEG',
		   "onUploadSuccess" : function(file, data, response) {
		     
			var member = eval("(" + data + ")");
			var obj = jQuery.parseJSON(member);
			var filePath = obj.filePath;
			 var img = "<img width=\"200\" height=\"200\" src=\""+basePath+filePath+"\">";
        
			   $("#"+id).parents("tr").find("div.uploadpic").html(img);
             var voucherfile=$("#"+id).parent().find(".voucherfile").attr("id");
			$("#"+voucherfile).val(filePath);
		}
	//其他配置项
	});
	});
  });
  function re_load() {
	    alert("==========");
		window.opener.location.href=window.opener.location.href
		window.close();

	}