$(function() {
	imageInit();
	loaded();
	getHeight();
	

	if (pagecount == 0) {
		$(".load_all").text("您没有上传过图片");
	} else if (pagecount == 1) {
		$(".load_all").text("已显示全部");
	} else {
		$(".load_all").text("加载更多");
	}
	
	
	//保存描述
	$(document).on("blur",".photo_desc",function(){
		var mcId = $(this).parents(".swiper-slide").attr("id");
		var describe = $(this).val();
		
		var url = basePath+"ea/scm/sajax_ea_saveDescibe.jspa";
		$.ajax({
			url : url,
			type : "get",
			asycn : false,
			dataType : "json",
			data : {
				"materialContent.mcId" : mcId,
				"materialContent.describe" : describe,
				
			},error:function(data){
				alert("保存描述失败");
		}
	});
	


});
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
			"materialContent.fileType" : "00",
			ajaxtype : "ajax"
		},
		success : function(data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			var html = "";
			var obj;

			

			if (pageForm != null) {
				$(".last").removeClass("last");
				pagecount = pageForm.pageCount;
				pageSize = pageForm.pageSize;
				if(count==0){
				$(".totalsx").text(pageForm.recordCount);
				}
				for (var i = 0; i < pageForm.list.length; i++) {
					obj = pageForm.list[i];
					if (i == pageForm.list.length - 1) {
						html += "<li class='"+obj.mcId+" photo_sbox last'>";
					} else {
						html += "<li class='"+obj.mcId+" photo_sbox'>";
					}
					html += "<img src='" + basePath + obj.filepath
							+ "' alt=''></li>";
					
					
					    var array = new Array();
						array.push("<div class='swiper-slide' id='"+obj.mcId+"' style='background-image:url("+basePath+obj.filepath+")'>");
						array.push("<div class='photo_overlay'>");
						array.push("<div class='photo_con'>");
						array.push("<input class='photo_desc' type='text' placeholder='添加名称…' value='"+obj.describe+"'>");
						array.push("<div class='photo_fd'>");
						array.push("<input type='hidden' class='groupID' value='"+obj.groupID+"'/>");
						array.push("<span class='photo_time'>2016年8月17日上传</span>");
						array.push("<a href='###' class='photo_move'></a>");
						array.push("<i class='photo_dele'></i>");
						array.push("</div></div></div></div>");
	                       
	                 
	                    mySwiper.appendSlide(array.join(""));
				}

				$(".photos_wrap").append(html);
				if (pagecount == pagenumber) {
					$(".load_all").text("已显示全部");
					clearTimeout(t);
					return;
				}

			}
		},
		error : function(data) {
			alert("加载图片失败");
		}
	});
}

// 相册初始化

function imageInit() {
	
	 mySwiper = new Swiper('.swiper-container', {
			nextButton : '.swiper-button-next',
			prevButton : '.swiper-button-prev',
			onSlideChangeEnd: function(swiper){
				
				var _last = $(".gallery-top .swiper-slide").length;
                
				if(_last<$(".totalsx").text()){
					if(swiper.activeIndex==_last-3){
						loaded();
					}
					
				}
				$(".sx").text(swiper.activeIndex+1);
			       if(swiper.isEnd){
			        swiper.nextButton[0].style.display='none';
			       }else{
			        swiper.nextButton[0].style.display='block';
			       }
			       if(mySwiper.isBeginning){
				        swiper.prevButton[0].style.display='none';
				       }else{
				        swiper.prevButton[0].style.display='block';
			        }
			 },
			
		});

	//点击查看详情
	
	$(document).on("click",".photo_sbox",function() {
		
		$("#overlay").addClass("active");// 显示遮罩层
		
		var _index = $(".photos_wrap .photo_sbox").index(this);
		$(".sx").text(_index+1);
		mySwiper.slideTo(_index, 10, false);//切换到第一个slide，速度为1秒
		 
	
		
		
	});
	// 相册返回按钮
	$(".back_overlay").click(function(e) {
		e.stopPropagation();
		$("#overlay").removeClass("active");
		
	});
	
	// 相册删除（需删除后台数据）
	$(document).on("click",".photo_dele",function() {
		var del_index = $("#overlay .photo_dele").index(this);

		
		if(confirm("确定要删除该图片么？")){
			
			var mcId = $(this).parents(".swiper-slide").attr("id");
			var url = basePath+"ea/scm/sajax_ea_deleteFileByPre.jspa";
			$.ajax({
				url : url,
				type : "get",
				asycn : false,
				dataType : "json",
				data : {
					"materialContent.mcId" : mcId,
					
				},success:function(data){
					$(".totalsx").text($(".totalsx").text()-1);
					var sx = $(".sx").text();
					$(".sx").text(sx-1==0&&sx<sx?Number(sx)+1:Number(sx)-1);
					count = $(".totalsx").text();
					mySwiper.removeSlide(mySwiper.activeIndex);
					mySwiper.slideTo(del_index-1==-1?del_index+1:del_index-1, 10, false);
					$(".photos_wrap").find("."+mcId).remove();
				    
				},error:function(data){
					alert("删除失败");
			}
		});
		
		}

	});
	
	// 移动分组
	$(document).on("click",".photo_move",function() {
		var mcId = $(this).parents(".swiper-slide").attr("id");

/*		var move_index = $("#overlay .photo_move").index(this);
		var groupID = $(this).parent().find(".groupID").val();*/
		document.location.href = basePath+"/ea/scm/ea_selectGroupList.jspa?materialContent.groupID="+mgId+"&type=nopic&bc=select&&materialGroup.groupname="+groupname+"&materialContent.mcId="+mcId;
	});
	

}
