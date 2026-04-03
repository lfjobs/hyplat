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
	surl1=basePath+"ea/carSchool/sajax_ea_queryNews.jspa";
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
	   		var inform="";
	   		if(result==null){
	   			inform+="<div style='height: 8rem;padding-top: 1rem;text-align: center;'>";
	   			inform+="<img src='"+basePath+"images/ea/production/drive/deficiency.png' style='height: 100%;'/>";
	   			inform+="<input type='hidden' class='judge' value='1'/></div>";
	   		}else{
	   			$(result.list).each(function(i,dom){
	   				inform+="<dd>";
	   				if($(result.list).length-1==i){
	   					inform+="<label class='inform_check last'>";
	   				}else{
	   					inform+="<label class='inform_check'>";
	   				}
	   				inform+="<input type='checkbox' name='inform'>";
   					inform+="<i class='check_state'></i>";
   					inform+="<div class='inform_box clearfix'>";
   					if(this[4]=='00'){
   						inform+="<i class='read_state'></i>";
   					}else{
   						inform+="<i class='read_state readed'></i>";
   					}
   					inform+="<div class='inform_text clearfix'>";
   					inform+="<span>"+this[1]+"<small>"+this[3]+"</small></span>";
   					inform+="<span>"+this[2]+"</span>";
   					inform+="</div></div>";
   					inform+="</label>";
   					inform+="<a href='javascript:void(0);' class='inform_overly' onclick='details(this)'>";
   					inform+="<input type='hidden' class='ppId' value='"+this[0]+"'/>";
   					inform+="<input type='hidden' class='csiId' value='"+this[5]+"'/>";
   					inform+="</a></dd>";
	   			})
	   		}
	   		$(".inform_wrap").append(inform);
			if(result!=null){
				pageNumber = result.pageNumber;
				pageCount = result.pageCount;
				if(pageNumber<pageCount){
					getHeight();
				}else{
					alert("已加载所有")
				}
			}
	   	}
	});
}
//查看通知详情
function details(obj){
	var ppId = $(obj).find(".ppId").val();
	var csiId = $(obj).find(".csiId").val();
	window.open(basePath+"ea/carSchool/ea_queryNewsDetails.jspa?productPackaging.ppID="+ppId+"&carSchoolInform.csiId="+csiId,"_self");
}
//删除通知信息
function del(){
	var myarr = new Array();
	var arr = $(".select_ico").parent().parent().find(".csiId");
	arr.each(function () {
        myarr.push($(this).val());
    });
	var csiId = myarr.join(",");
	if(csiId==""){
		alert("请选择!!!")
	}else{
		document.location.href = basePath+"ea/carSchool/ea_delNews.jspa?carSchoolInform.csiId="+csiId;
	}
}