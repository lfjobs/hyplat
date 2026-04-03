$(document).ready(function() {
	
	
	//切换
	$(".tab li").click(function(){
		$("."+$(".selected").attr("id")).addClass("hidcontent");
		$(".selected").removeClass("selected");
	  
	    $(this).addClass("selected");
	    $("."+$(this).attr("id")).removeClass("hidcontent");
		
	});
	
	
	//新增功能
	
	$(".add").live("click",function(){
	    seqnum++;
		$("#init1").after($("#init1").clone(true).attr("id","init"+seqnum));
		
		$("#init"+seqnum).find(".del").show();
	});
	
	
	//移除
	
	$(".del").live("click",function(){
	   $(this).parent().parent().remove();
		
		
	});

});

