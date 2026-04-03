$(document).ready(function() {
$(".J_Checkbox").attr("checked",false);
	$(".kui-combobox").click(function() {

				$(this).next().show();
			});

	// 颜色更换
	$("#sku-color-tab-header li").click(function() {
				// 更改颜色西选中的选中状态
				$(".selected").removeClass("selected");
				$(this).addClass("selected");

				// 切换具体颜色
				var index = $(this).attr("index");

				$("#sku-color-tab-contents ul[index=" + index + "]").show();
				$("#sku-color-tab-contents ul[index!=" + index + "]").hide()

			});

	// 切换尺码
	$(".size-type li").click(function() {

				var $content = $(".size-content ");
				$content.find("ul").hide();
				$("#J_SizePannel_" + $(this).find("input").val(), $content)
						.show();

			});

	// 选中颜色 展示每个颜色的图片上传
	var select = 1;
	$(".color-list li").find(".J_Checkbox").change(function() {
		var colorname = $(this).attr("data-text");
		var color = $(this).next(".color-box").attr("color");

		if ($(this).attr("checked") == true) {
			// 选中颜色 展示每个颜色的图片上传
			$("#kelong").before(

					$("#kelong").clone(true).attr("id", "kelong" + select)
							.addClass("checkcolors").show()

			);
			$("#kelong" + select).find(".tile").find(".color-lump").css(
					"background", color);
			$("#kelong" + select).find(".tile").find("span").text(colorname);
			$("#kelong" + select).find(".img-select").attr("id","file"+select);
            createuploadRest("file"+select);
			select++;
            
			// 点击颜色 展示在尺寸颜色对应表中
			// 循环尺码
			$(".size-content .sku-item").find(".J_Checkbox:checked").each(
					function() {
						var size = $(this).next("label").text();
						$("#J_SKUMapContainer #kelongcsize")
								.before($("#J_SKUMapContainer #kelongcsize")
												.clone(true).attr(
														"id",
														"kelongcsize"
																+ selectsize)
												.addClass("checksizes").show());
						$("#J_SKUMapContainer #kelongcsize" + selectsize)
								.find(".J_Map_color").text(colorname);
						$("#J_SKUMapContainer #kelongcsize" + selectsize)
								.find(".J_Map_size").text(size);
						selectsize++;

					});
					
					
			if($(".checksizes").length>0){
			  $("#sizeContainer").show();
			}
			
			

		} else {
			// 删除颜色图片
			$(".checkcolors .tile").each(function() {
						$.parent = $(this).parent("tr");
						if ($(this).find("span").text() == colorname) {
							$.parent.remove();
							return false;
						}
					});

			// 删除颜色尺码

			$(".checksizes").each(function() {
						if ($(this).find(".J_Map_color").text() == colorname) {
							$(this).remove();
						}
					});
					
		  if($(".checksizes").length<1){
		  	$("#sizeContainer").hide();
		  }
		}

	});

	var selectsize = 1;
	// 点击尺码 展示颜色尺寸对应表

	$(".size-content .J_Checkbox").change(function() {
		var size = $(this).next("label").text();
		if ($(this).attr("checked") == true) {
             
			
			
			
			// 循环被选中的颜色颜色图片对应表的tr
			$(".checkcolors").each(function() {
				var colorname = $(this).find(".tile").find("span").text();

				$("#J_SKUMapContainer #kelongcsize")
						.before($("#J_SKUMapContainer #kelongcsize")
										.clone(true).attr("id",
												"kelongcsize" + selectsize)
										.addClass("checksizes").show());
				$("#J_SKUMapContainer #kelongcsize" + selectsize)
						.find(".J_Map_color").text(colorname);
				$("#J_SKUMapContainer #kelongcsize" + selectsize)
						.find(".J_Map_size").text(size);
				selectsize++;
			});
			
			if($(".checksizes").length>0){
			  $("#sizeContainer").show();
			}
		} else {
			// 删除颜色尺码
			$(".checksizes").each(function() {
						if ($(this).find(".J_Map_size").text() == size) {
							$(this).remove();
						}
					});
	     if(!$(".checksizes").length){
		  	$("#sizeContainer").hide();
		  }
		}

	});
	
	

        //文件上传
        $("#J_MultimageField").uploadify({
              "swf"      : basePath+"js/uploadify/uploadify.swf",    //指定上传控件的主体文件
             "fileObjName"  : "file",        //文件对象
             "uploader" : basePath+"ea/babymanage/sajax_ea_uploadFile.jspa",   //指定服务器端上传处理文件
             "fileSizeLimit":"3MB",
             "queueSizeLimit":1,
             'buttonText' : '文件上传',
             'width': 72,
             "onUploadSuccess" : function(file, data, response) {
	      	 var member = eval("(" + data + ")");
	      	 var filePath = member.filePath;
	      	   addImg(filePath)
             }
            //其他配置项
        });
 
       
        
        //删除
     $(".del").live("click",function(){
     
     	 $(this).prev("a").remove();
     });
	
	

});

function addImg(imgUrl){
	var img = "<img src=\""+basePath+imgUrl+"\">";

	$(".multimage-gallery").find(".preview").find("input.hideimageurl").each(function(){
		if($(this).attr("name")!="videoAsPicThum"){
	   if($(this).val()==""){
	   	 $(this).parent().show().append(img);
	   	 $(this).val(imgUrl);
	   	 return false;
	   }
		}
	});
	
}

//创建上传环境
function createuploadRest(id){
	        //文件上传
        $("#"+id).uploadify({
              "swf"      : basePath+"js/uploadify/uploadify.swf",    //指定上传控件的主体文件
             "fileObjName"  : "file",        //文件对象
             "uploader" : basePath+"ea/babymanage/sajax_ea_uploadFile.jspa",   //指定服务器端上传处理文件
             "fileSizeLimit":"3MB",
             "queueSizeLimit":1,
             'buttonText' : '本地上传',
             'width': 72,
               'queueID'  : 'some_file_queue',
             "onUploadSuccess" : function(file, data, response) {
	      	 var member = eval("(" + data + ")");
	      	 var filePath = member.filePath;
	      	   addImgDan(id,filePath)
             }
            //其他配置项
        });
}

function addImgDan(id,filePath){

	
 var img="<a target=\"_blank\" href=\""+basePath+filePath+"\"><img src=\""+basePath+filePath+"\"></a><a class=\"del\" href=\"javascript:void(0);\">删除</a><a class=\"undel\" data-path=\""+basePath+filePath+"\" href=\"javascript:void(0);\">恢复删除</a>"
 $("#"+id).parent().next(".preview").show().html(img);
}

