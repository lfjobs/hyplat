$(document).ready(function() {
	//页面加载时运行以下方法
	ajax(); 
});
function getHeight(){
	t=setTimeout("getHeight()", 200);
	if($(".last").offset().top<$(window).height()){	
		if(pageNumber<pageCount){
			ajax();
		}
	}
}
function ajax() {
	surl1=basePath+"ea/carSchool/sajax_ea_relevanceInformation.jspa";
	if(pageNumber==null){
		pageNumber = 1;
	}else{
		pageNumber = parseInt(pageNumber)+1;
	}
	$.ajax({
		url:encodeURI(surl1),
	 	type:"post",
	 	data:{"pageForm.pageNumber":pageNumber,"pageForm.pageSize":7},
	  	dataType:"json",
	   	async:false, 
	   	success:function (data) {
			var result = eval("(" + data + ")");
			$(".last").removeClass("last");
			var makeMoney="";
			if(result.pageForm==null){
				makeMoney+="<div class='showImage' >";
				makeMoney+="<p style='height: 8rem;padding-top: 1rem;text-align: center;'>";
				makeMoney+="<img src='"+basePath+'images/ea/production/drive/thereIsNot.png'+"' style='height: 100%;'/>";
				makeMoney+="</p></div>";
			}else{
				$(result.pageForm.list).each(function(i,dom){
					makeMoney+="<a href='###' class='mm_listbox' >";
					if($(result.pageForm.list).length-1==i){
						if(this[3]==staffId){
							makeMoney+="<div class='mm_list_tit last'>恭喜您购买"+this[1]+"产品获得"+this[2]+"金币</div>";
						}else{
							makeMoney+="<div class='mm_list_tit last'>恭喜"+this[0]+"购买了"+this[1]+"产品,您获得"+this[2]+"金币</div>";
						}
					}else{
						if(this[3]==staffId){
							makeMoney+="<div class='mm_list_tit'>恭喜您购买"+this[1]+"产品获得"+this[2]+"金币</div>";
						}else{
							makeMoney+="<div class='mm_list_tit'>恭喜"+this[0]+"购买了"+this[1]+"产品,您获得"+this[2]+"金币</div>";
						}
					}
					makeMoney+="<div class='mm_listcon clearfix'>";
					makeMoney+="<div class='mm_img outer_img'>";
					makeMoney+="<img src='"+basePath+this[4]+"'>";
					makeMoney+="</div>";
					makeMoney+="<div class='mm_listR'>";
					makeMoney+="<div class='mm_listRt clearfix'>";
					makeMoney+="<span>"+this[1]+"</span>";
					makeMoney+="<span class='mm_star'><i></i><i></i><i></i></span>";
					makeMoney+="<span>￥"+this[5]+"</span>";
					makeMoney+="</div></div>";
					makeMoney+="</div></a>";
				})
			}
			$(".mm_list_wrap").append(makeMoney);
			if(result.pageForm!=null){
				pageNumber = result.pageForm.pageNumber;
				pageCount = result.pageForm.pageCount;
				if(pageNumber<pageCount){
					getHeight();
				}else{
					alert("已加载所有")
				}
			}
	   	}
	});
}
