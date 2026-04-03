$(document).ready(function() {
	
	getHeight();
	
//  选择
	var num = 0;
	
	
	$(document).on("click",".img_xuan .no",function(){
		num++;
		$(".footer_left").find("span").html(num);
		$(this).toggleClass("dN");
		$(this).next("img").addClass("selected").removeClass("dN");
		
	});
	
	$(document).on("click",".img_xuan .yes",function(){
		
		num--;
		$(".footer_left").find("span").html(num);
		$(this).toggleClass("dN");
		$(this).prev("img").removeClass("dN");
		$(this).removeClass("selected");
		
	});
	
	//        跳转页面
	$(".arrar").click(function() {
		
		document.location.href = basePath
		+ "ea/purchasebids/ea_enterBids.jspa?bidsinfo.goodsID="
		+ goodsID
		+ "&inviteBids.cashierBillsID="
		+ cashierBillsID + "&mainpid="
		+ mainpid+"&ppid="+ppid;
	});
	
	
	//确定
	
	$(".footer_right").click(function(){
		var ppids = "";
		$(".selected").parents(".img_xuan").find(".ppid").each(function(){
			ppids+=$(this).val()+",";
			
		});
		ppids = ppids.substring(0,ppids.length-1);
		if(ppids==""){
			alert("请选择");
			return;
		}
		$("#ppids").val(ppids);
		
		$("#form").attr("action",basePath+"ea/purchasebids/ea_enterBids.jspa");
		document.form.submit.click();
		
	});
});

function getHeight(){
	t=setTimeout("getHeight()", 200);
	if($(".last").length>0){
		if($(".last").offset().top-$(".last").height()*3<=$(window).height()){
			
		if(pagenumber<pagecount){
			loaded();
		}	
	}
	}
}



function loaded(){
	pagenumber += 1;
	var url = basePath+"ea/purchasebids/sajax_ea_ajaxMybidGoods.jspa";
	$.ajax({
		url : url,
		type : "get",
		async : false,
		data : {
			search : "search",
			"pageForm.pageNumber":pagenumber

		},
		dataType : "json",
		success : function(data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			var htmlstr="";
             var obj;
             $(".last").removeClass("last");
             if(pageForm!=null){
				pagecount = pageForm.pageCount;
				count = pageForm.recordCount;
				pageSize = pageForm.pageSize;
			    $(".last").removeClass("last");
			    for ( var i = 0; i <  pageForm.list.length; i++) {
			    	 obj = pageForm.list[i];
			    	 if(i==pageForm.list.length-1){
			    	   htmlstr+="<li class='last' style='width:100%;'>";
			    	 }else{
			    	   htmlstr+="<li style='width:100%;'>";	 
			    		 
			    	 } 
			    	 htmlstr+="<div class='tj_img'><img src='"+basePath+obj[2]+"' /></div>";
			    	 htmlstr+="<div class='toubiao2_main_right'>";
					 htmlstr+="<div class='tj_text'>";
					 htmlstr+="<h3>"+obj[1]+"</h3>";
					 htmlstr+="<ul><li class='price'>￥"+obj[5]+"</li></ul>";
				     htmlstr+="</div>";
				     htmlstr+="<div class='img_xuan'>";
				     htmlstr+="<input type='hidden' value='"+obj[0]+"' class='ppid'>";
				     htmlstr+="<img class='no' src='"+basePath+"images/ea/bids/chan_03_03.png'/>";
				     htmlstr+="<img class='yes dN' src='"+basePath+"images/ea/bids/chan_07.png' />";
					 htmlstr+="</div>";
					 htmlstr+="</div>";
					 htmlstr+="</li>";

			    }
			    $(".tj_con").append(htmlstr);
			    $(".tj_img").css({"height":"10rem"});
		        $(".tj_text").css({"height":$(".tj_img").height()+"px"});
		        $(".toubiao2_main_right").css({"height":$(".tj_img").height()+"px"});
		        $(".img_xuan").css({"height":$(".toubiao2_main_right").height()+"px"});
             }
		   
	
		 },
	     error:function(data){
		   alert("加载下一页失败");
		   }
	   });
	
}

