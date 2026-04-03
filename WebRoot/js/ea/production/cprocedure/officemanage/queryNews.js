$(document).ready(function() {
	//页面加载时运行以下方法
	ajax();
	$("#prompt").css("position","absolute").css("top",$(window).height()*0.09+"px");
	$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
	$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
	$(".head_R").click(function() {
		document.location.href = basePath+"ea/companyMaintain/ea_newsSkip.jspa?skipString=queryNews"+"&cuscom.staffid="+staffId+"&cuscom.companyId="+companyId;
	})
});
function getHeight(){
	t=setTimeout("getHeight()", 200);
	if($(".last").offset().top<$(window).height()){	
		if(pagenumber<pagecount){
			ajax();
		}
	}
}
function ajax() {
	var url = basePath + "ea/companyMaintain/sajax_ea_ajax_queryAllNews.jspa";
	if(pagenumber==null){
		pagenumber = 1;
	}else{
		pagenumber = parseInt(pagenumber)+1;
	}
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		data:{						
		  "pageForm.pageNumber":pagenumber,
		  "cuscom.companyId":companyId,
		  "cuscom.staffid":staffId
		},
		success : function (data) {
			var result = eval("(" + data + ")");
			$(".last").removeClass("last");
			var news = "";
			if(result==null){
				news+="<div style='height: 8rem;padding-top: 1rem;text-align: center;'>";
				news+="<img src='"+basePath+"images/ea/production/deficiency.png' style='height: 100%;'/>";
				news+="<input type='hidden' class='judge'/></div>";
				$(".job_num").html(0);
	   		}else{
	   			$(result.list).each(function(i,dom){
	   					if($(result.list).length-1==i){
		   					news+="<label class='checkbox last'>";
		   				}else{
		   					news+="<label class='checkbox'>";
		   				}
		   				news+="<a href='javascript:void(0);' onclick='check(this)'></a>";
	   		   			news+="<i class='checkbox_ico'></i>";
	   		   			news+="<input class='ppId' type='checkbox' value='"+this[7]+"'>";
	   		   			news+="<input class='companyId' type='hidden' value='"+this[6]+"'>";
	   		   			news+="<input class='staffId' type='hidden' value='"+this[8]+"'>";	
	   		   			news+="<div class='news_box'>";
	   		   			news+="<div class='new_imgbox'>";
	   		   			news+="<img src='"+basePath+this[2]+"'></div>";
	   		   			news+="<div class='news_R'>";
	   		   			news+="<h3>"+this[0]+"</h3>";
	   		   			news+="<span class='news_time'>"+this[1]+"</span>";
	   		   			news+="<p style='white-space: nowrap;-o-text-overflow:ellipsis;text-overflow: ellipsis;' class='neirong'>"+this[5]+"</p>";
	   		   			news+="<div><p style='width: 50%;float:left;'>发布人:"+this[9]+"</p><p style='width: 50%;float: right;'>修改人:"+this[10]+"</p></div>";
	   		   			news+=" </div></div></label>";
	   		   			news+="<hr class='new_hr'>";
	   			})
	   			$(".job_num").html(result.count);
	   			$(".neirong").find("img").attr("style","display:none");
	   		}
			$(".contain_bd").append(news);
			if(result!=null){
				pagenumber = result.pagenumber;
				pagecount = result.pagecount;
				if(pagenumber<pagecount){
					getHeight();
				}else{
					prompt("已加载所有")
				}
			}
		}
	});
}
//查询新闻详情
function check(obj) {
	var ppId = $(obj).nextAll(".ppId").val();
	document.location.href = basePath+"ea/companyMaintain/ea_queryNews.jspa?productPackaging.ppID="+ppId+"&cuscom.staffid="+staffId;
}
//删除新闻
function delNews() {
	if(confirm( "确认删除吗？ ")){ 
		var myarr = new Array();
		var arr = $(".contain_bd").find(".checkbox_select").next(".ppId");
		arr.each(function () {
	        myarr.push($(this).val());
	    });
		var ppId = myarr.join(",");
		
		if(ppId==""){
			prompt("请选择!!!")
		}else{
			var url = basePath+"ea/companyMaintain/sajax_ea_delNews.jspa";
			$.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data:{		
					"productPackaging.ppID":ppId
				},
				success : function (data) {
					var result = eval("(" + data + ")");
					if(result.b){
						$(".contain_bd").empty();
						$(".contain_hd_R").click();
						pagenumber = 0;
						ajax();
					}else{
						prompt("删除失败!!!")
					}
				}
			})
		}
	} 
}
//提示
function prompt(obj){
	if($("#prompt").css("display")!="none")
		return;
	$("#prompt").find("span").text(obj);
	$("#prompt").fadeIn(500);
	setTimeout(function(){
		$("#prompt").fadeOut(500);
		$("#prompt").find("span").text("");
	}, 2000);
}





