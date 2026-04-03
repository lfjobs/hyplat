$(function(){
	getHotPosition();
    getRecruitmentList();
});

//上一步
function lastStep(){
	if(pageNumber>1){
		pageNumber = parseInt(pageNumber)-1;
        getRecruitmentList();
    }
}
//下一步
function nextStep(){
	if(pageNumber<pageCount){
		pageNumber = parseInt(pageNumber)+1;
        getRecruitmentList();
	}
}

//选取页数
function choose(obj){
	pageNumber = $(obj).text();
    getRecruitmentList();
}

//查询职位信息
function getRecruitmentList(){
	var url=basePath+"ea/newpcend/sajax_ea_ajaxRecruitment.jspa";
	$.ajax({
		url:url,
		type:"post",
		async : true,
		data:{
			"pageForm.pageNumber":pageNumber,
			"pageForm.pageSize":10,
            "ppk.companyID":companyId
		},
		dataType:"json",
		success:function(data){
			var commodity = eval("(" + data + ")");
			var pageForm=commodity.pageForm;
			var array = [];
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0){
				$(pageForm.list).each(function(i) {
					if(i%2==0){
						array.push("<ul class='one' onclick='details(this)'>");
					}else{
						array.push("<ul class='two' onclick='details(this)'>");
					}
					array.push("<input class='riID' type='hidden' value='"+this[0]+"'/>");
					array.push("<li class='job_title'>"+this[1]+"</li>");	
					array.push("<li class='job_category'>"+this[2]+"</li>");	
					array.push("<li class='number'>"+this[3]+"</li>");	
					array.push("<li class='site'>"+this[4]+"</li>");	
					array.push("<li class='pubdate'>"+this[5]+"</li>");	
					array.push("</ul>");
				});
			}
			$("#position").empty();
			$("#position").append(array.join(""));
			$("#positionCount").html("");
			$(".page_my").empty();
			if(pageForm!=null){
				pageCount = pageForm.pageCount;
		        pageNumber = pageForm.pageNumber;
		        var paging = [];
		        paging.push("<button style='float: left;' onclick='lastStep();'><img src='"+basePath+"page/newMyapp/images/newPCHomepage/page-left.png'></button><ul>");
		        for ( var i = 1; i <= pageCount; i++) {
		        	if(pageNumber==i){
		        		paging.push("<li onclick='choose(this)' class='active'>"+i+"</li>");
		        	}else{
		        		if(i==1||i==pageCount||(i>=pageNumber-2&&i<=pageNumber+2)){
		        			paging.push("<li onclick='choose(this)'>"+i+"</li>");
		        		}else if(i!=1&&i!=pageCount&&(i==pageNumber-3||i==pageNumber+3)){
		        			paging.push("<li style='border:none;background-color:white;' class='point'><span>...</span></li>");
		        		}
		        	}
				}
		        paging.push("</ul><button style='float: right;' onclick='nextStep();'><img src='"+basePath+"page/newMyapp/images/newPCHomepage/page-right.png'></button>");
		        $("#positionCount").html("<span style='color:black;'>共</span>"+pageForm.recordCount+"<span style='color:black;'>个职位</span>");
		        $(".page_my").append(paging.join(""));
		        var nub = $(".bid_pages .page_my ul li").length;
		        $(".bid_pages .page_my ul").css("width",40*nub+"px");
		    	$(".bid_pages .page_my").css("width",80+40*nub+"px");
			}else{
				$("#positionCount").html("<span style='color:black;'>共</span>0<span style='color:black;'>个职位</span>");
			}
		},
		error:function(data){ 
			/*alert("获取失败！");*/
		}
	});
}	
	
	
//查询热门职位
function getHotPosition(){
	var url=basePath+"ea/newpcend/sajax_ea_ajaxHotPosition.jspa";
	$.ajax({
		url:url,
		type:"post",
		async : true,
		data:{
			"pageForm.pageNumber":pageNumber,
			"pageForm.pageSize":10,
			"ppk.companyID":companyId
		},
		dataType:"json",
		success:function(data){
			var commodity = eval("(" + data + ")");
			var pageForm=commodity.pageForm;
			var array=[];
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0){
				$(pageForm.list).each(function() {
					array.push("<li><label onclick='details(this)'>");
					array.push("<input class='riID' type='hidden' value='"+this[0]+"'/>");
					array.push("<span limit='6'>"+this[1]+"</span>")
					array.push("<span class='pay' limit='6'>"+this[2]+"</span></label></li>");
				});
			}
			$(".Certificate_con .right").find("ul").append(array.join(""));
			jQuery.fn.limit=function(){
		    	var self = $("[limit]");
		    	self.each(function(){
		       		var objString = $(this).text();
		        	var objLength = objString.length;
		        	var num = $(this).attr("limit");
		        	if(objLength > num){
		            	objString = $(this).html(objString.substring(0,num) + "...");
		        	}
		    	});
			};
			$("[limit]").limit();
		},
		error:function(data){
			/*alert("获取失败！");*/
		}
	});
}


//职位详情
function details(obj){
	var riID=$(obj).find(".riID").val();
	var url=basePath+"/ea/newpcend/ea_companyWebsite.jspa?titleJudge=05&seven=01&ccompanyId="+ccompanyId+"&recruitInfo.riId="+riID;
	window.location.href=url;
}

