$(function() {
	loaded();
	getHeight();
	

	if (pagecount == 0) {
		$(".load_all").text("您没有上传过视频");
	} else if (pagecount == 1) {
		$(".load_all").text("已显示全部");
	} else {
		$(".load_all").text("加载中");
	}
	
	
	//保存描述
	$("#video_tit").blur(function(){
		var mcId = $("#videoid").val();
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
	
	
	 _video = document.getElementById("video_box");
	//点击视频
	$(document).on("click",".photo_sbox",function(){
		    var video_id = $(this).attr("data-id"); //视频路径
	        var video_src = $(this).attr("data-video"); //视频路径
	        var video_tit = $(this).attr("data-tit"); //视频标题
	        var video_time = $(this).attr("data-time"); //视频上传时间
	        var that = $(this);
	        addPlay(video_src, video_tit, video_time,video_id);
	        //删除（需删除后台数据）

	    })
	    //返回按钮
	$(".back_overlay").click(function(e) {
	    e.stopPropagation();
	    $("#overlay").removeClass("active");
	    initVideo();
	});
	
    //删除
    $(".photo_dele").one("click", function() {
      
        
		if(confirm("确定要删除该视频？")){
			$("#overlay").removeClass("active");
			var mcId = $("#videoid").val();
			var url = basePath+"ea/scm/sajax_ea_deleteFileByPre.jspa";
			$.ajax({
				url : url,
				type : "get",
				asycn : false,
				dataType : "json",
				data : {
					"materialContent.mcId" : mcId,
					
				},success:function(data){
					 document.location.reload();
				    
				},error:function(data){
					alert("删除失败");
			}
		});
		
		}
    });
    
    
	// 移动分组
    $(".photo_move").one("click", function() {
    	// 移动分组
    	$(document).on("click",".photo_move",function() {
    		var mcId = $("#videoid").val();
    		document.location.href = basePath+"/ea/scm/ea_selectGroupList.jspa?materialContent.groupID="+mgId+"&type=novideo&bc=select&&materialGroup.groupname="+groupname+"&materialContent.mcId="+mcId;
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
						array.push("<li class='photo_sbox last' data-id ='"+obj.mcId+"' data-video='"+basePath+obj.filepath+"' data-tit='"+obj.describe+"' data-time='"+obj.createDate+"'>");
					} else {
						array.push("<li class='photo_sbox' data-id ='"+obj.mcId+"' data-video='"+basePath+obj.filepath+"' data-tit='"+obj.describe+"' data-time='"+obj.createDate+"'>");
					}
					array.push("<img src='"+basePath+obj.filecover+"' alt='"+obj.describe+"'>");
					array.push("<i class='video_ico'></i></li>");
					
				}

				$(".photos_wrap").append(array.join(""));
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



//弹窗播放
function addPlay(src, tit, time,id) {
    $("#video_box").attr({
        src: src
    })
    if (video_tit != '') {
        $("#video_tit").val(tit)
        $(".namevideo").text(tit);
    }
    $("#videoid").val(id);
    $("#video_time").text(time);
    $("#overlay").addClass("active");
    _video.play();
}
//重置播放区域
function initVideo() {
    $("#video_box").attr({
        src: ''
    });
    $("#video_tit").val('');
    $("#video_time").text('');
}


