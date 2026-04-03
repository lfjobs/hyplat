var pn=1;
$(function(){
		$(".nav_right").css({"width":$(".nav_left").width()+"px","height":$(".nav_left").height()+"px"})
		$(".fix_top").css("height",$(".navbar-fixed-top").height()+"px")
	
        $(".list-group-item").css({"overflow":"hidden"})
        $(".nav_img").css({"width":$(window).width()*0.08+"px","height":$(window).width()*0.08+"px"})
        $(".list-group-item-text").css({"line-height":$(".list-group-item").height()+"px","margin-left":"15px"})
        $(".list-group").css({"padding":"0 10px"})
        $(".list-group a").css({"border":"none","border-bottom":"1px solid #e3e3e3","border-bottom-right-radius":"0","border-bottom-left-radius":"0","margin-bottom":"0","border-bottom":"1px solid #e3e3e3"})

 
    });
//返回点击
function fanhui(){
	var url = basePath + "/ea/industry/ea_getiInvestment.jspa?stype=one";
	document.location.href = url;
}
//当前点击
function Jump(obj){
	var codepid = $(obj).find("#codepid").val();
	var codename = $(obj).find("#codename").text();
	//alert(codepid+codename);
	var url = basePath + "/ea/industry/ea_getiInvestment.jspa?stype=&codeID="+codepid+"&codesId="+codesId+"&codename="+codename;
	document.location.href = url;
	
	}
$(window).scroll(function() {

	//下拉刷新
    if ($(document).scrollTop() <= 0) {
    	var  url =basePath + "/ea/industry/ea_getiInvestment.jspa?stype=two&codesId="+codesId+"&ajax=";
    	document.location.href = url;
    }
    //上拉加载
    if ($(document).scrollTop() >= $(document).height() - $(window).height()) {
    	
        pn +=1;	
        var pc=parseInt($("#pageCount").val());
		if(pn<=pc){
			
			var url=basePath+"/ea/industry/sajax_ea_getiInvestment.jspa?stype=two&ajax=ajax&codesId="+codesId;	
			
		$.ajax({
			url : encodeURI(url),
			type : "get",
			async : false,
			dataType : "json",
			data:{						
				  "pageForm.pageNumber":pn
				},
			success : function(data) {
				var member = eval("(" + data + ")");
				var pageForm = member.pageForm;
				var ttst = pageForm.list;
				var com="";
				for(var i = 0; i < ttst.length; i++){
					var pp=ttst[i];

				com += "<a href='#' class='list-group-item xuan'><img src='"+ basePath+ pp.iconPath+ "' class='nav_img pull-left' />";
				com += "<p class='list-group-item-text pull-left'><span onclick='Jump(this)'>"+ pp.codeValue+ "";
				com += "<input type='hidden' id='codepid' name='codepid' value='"+ pp.codeID+ "'></p></a>";
				}
				$(".list-group").append(com);
				$(".fix_top").css("height",$(".navbar-fixed-top").height()+"px")
				
		        $(".list-group-item").css({"overflow":"hidden"})
		        $(".nav_img").css({"width":$(window).width()*0.08+"px","height":$(window).width()*0.08+"px"})
		        $(".list-group-item-text").css({"line-height":$(".list-group-item").height()+"px","margin-left":"15px"})
		        $(".list-group").css({"padding":"0 10px"})
		        $(".list-group a").css({"border":"none","border-bottom":"1px solid #e3e3e3","border-bottom-right-radius":"0","border-bottom-left-radius":"0","margin-bottom":"0","border-bottom":"1px solid #e3e3e3"})

			},
			error : function(data) {
				alert("获取信息失败");
			}
		});
		}
    }
});