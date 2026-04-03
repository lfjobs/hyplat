$(function() {

	loaded();
	getHeight();
	

	if (pagecount == 0) {
		$(".load_all").text("您没有上传过音频");
	} else if (pagecount == 1) {
		$(".load_all").text("已显示全部");
	} else {
		$(".load_all").text("加载更多");
	}
	
	
	//点击播放
	 $(document).on("click",".audio_box",function() {
         var audio = $(this).find("audio")[0];
         var audio_name = $(this).find(".audio_name")[0];
         var state = $(this).find("i")[0];
         var index = $(".audio_wrap .audio_box").index($(this));
         if (audio.paused) {
             for (i = 0; i < $("audio").length; i++) {
                 if (i != index) {
                     document.getElementsByTagName("audio")[i].load();
                 }
                 document.getElementsByClassName("audio_play")[i].className = "audio_play";
                 document.getElementsByClassName("audio_name")[i].className = "audio_name";
                 audio_name.className = "audio_name audio_scroll";
             }
             audio.play();
             state.className = "audio_play audio_pause";
         } else {
             audio.pause();
             state.className = "audio_play";
             audio_name.className = "audio_name";
         }
     })
     //删除 
     $(document).on("click",".audio_del",function(e) {
         e.stopPropagation();
         var $parent = $(this).parents(".audio_box");
     	var mcId = $parent.attr("id");
	  if(confirm("确定要删除该音频？")){
 			var url = basePath+"ea/scm/sajax_ea_deleteFileByPre.jspa";
 			$.ajax({
 				url : url,
 				type : "get",
 				asycn : false,
 				dataType : "json",
 				data : {
 					"materialContent.mcId" : mcId,
 					
 				},success:function(data){
 					$parent.detach();
 					if($(".audio_box").length==0){
 						$(".load_all").text("您没有上传过图片");
 					}
 	
 				    
 				},error:function(data){
 					alert("删除失败");
 			}
 		});
 		
 		}


     })
     
     


	

});
function getHeight() {
	t = setTimeout("getHeight()", 200);
	if ($(".last").length > 0) {
		if ($(".last").offset().top - $(".last").height() <= $(window)
				.height()) {
			if (pagenumber < pagecount) {
				loaded();
			}
		}
	}
}

function loaded() {
	pagenumber += 1;

	var url = basePath + "ea/scm/sajax_ea_findFilelist.jspa";
	$.ajax({
		url : url,
		type : "get",
		asycn : false,
		dataType : "json",
		data : {
			"materialGroup.groupname" : groupname,
			"materialGroup.mgId" : mgId,
			"pageForm.pageNumber" : pagenumber,
			"materialContent.fileType" : "03",
			ajaxtype : "ajax"
		},
		success : function(data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			var html = "";
			var obj;

			$(".last").removeClass("last");

			if (pageForm != null) {

				pagecount = pageForm.pageCount;
				pageSize = pageForm.pageSize;
				if(count==0){
				$(".totalsx").text(pageForm.recordCount);
				}
				 var array = new Array();
				for (var i = 0; i < pageForm.list.length; i++) {
					obj = pageForm.list[i];
					if (i == pageForm.list.length - 1) {
						array.push("<a href='javascript:;' class='audio_box clearfix last' id='"+obj.mcId+"'>");
					} else {
						array.push("<a href='javascript:;' class='audio_box clearfix' id='"+obj.mcId+"'>");
					}
					array.push("<i class='audio_play'></i>");
					array.push("<div class='audio_text'>");
					array.push("<div class='audio_name' scrollamount='3'>"+obj.describe+"</div>");
					array.push("<div class='audio_attr'>");
					array.push("<span>"+obj.filesize+"</span><span>"+obj.createDate+"</span>");
					array.push("</div>");
					array.push("</div>");
					array.push("<i class='audio_del'></i>");
					array.push("<audio preload='none'>");
					array.push("<source src='"+basePath+obj.filepath+"' type='audio/mpeg'>");
					array.push("</audio>");
					array.push("</a>");

				}

				$(".audio_wrap").append(array.join(""));
				if (pagecount == pagenumber) {
					$(".load_all").text("已显示全部");
					clearTimeout(t);
					return;
				}

			}
		},
		error : function(data) {
			alert("加载音频失败");
		}
	});
}


	

