$(document).ready(function() {
	//查询一级行业
	industry();
	//查询招标信息
	callforbids();
	//热门招标
	popularBidding();
})

//查询一级行业
function industry(){
 	var url=basePath+"ea/industry/sajax_ea_getIndustry.jspa?";			
	$.ajax({
		url : url,
		type : "post",
		async : true,
		dataType : "json",
		data:{	
			"codePID":""
		},
		success : function(data) {
			var member = eval("(" + data + ")");
			$(".bid_con").find(".top").find(".right").empty();
			if(member!=null){
				var m = member.industryList;
				var s = [];
				s.push("<li class='active' date-name=''>全部</li>");
				for ( var i = 0; i < m.length; i++) {
					var b = m[i].codeDesc;
					s.push("<li date-name='"+b+"'>"+b+"</li>");
				}
				$(".bid_con").find(".top").find(".right").append(s.join(""));
			}
		},
		error: function(){
			alert("行业加载失败");
		}
	});		
}
//上一步
function lastStep(){
	if(pageNumber>1){
		pageNumber = parseInt(pageNumber)-1;
		callforbids();
	}
}
//下一步
function nextStep(){
	if(pageNumber<pageCount){
		pageNumber = parseInt(pageNumber)+1;
		callforbids();
	}
}
//选取
function choose(obj){
	pageNumber = $(obj).text();
	callforbids();
}

//查询招标信息
function callforbids(type) {
	var tradeName = $(".bid_con").find(".top").find(".right").find(".active").attr("date-name");
	var name = $(".bottom").find(".tit").find(".active").attr("date-name");
	var url = basePath + "/ea/newpcend/sajax_ea_ajaxTheTenderList().jspa";
	$.ajax({
		url : url,
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"search" : "search",
			"ppk.goodsName":"",
			"ppk.tradeID":"",
			"ppk.tradeName":tradeName,
			"pageForm.pageNumber":pageNumber,
			"pageForm.pageSize":7,
			"hot":type,
			"temporary":name
		},
		success : function(data) {
			var commodity = eval("(" + data + ")");
			var pageForm = commodity.pageForm;
			var commodity1 = [];
			if(name=="进行中"){
				$("#bid_ing").empty();
			}else if(name=="已结束"){
				$("#bid_acc").empty();
			}
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				$(pageForm.list).each(function(i, dom) {
					if(i<=6){
						commodity1.push("<li onclick='details(this)'>"); 
						commodity1.push("<input class='ppID' type='hidden' value='"+ this[1] + "'/>");
						commodity1.push("<input class='goodsID' type='hidden' value='"+ this[2] + "'/>");
						commodity1.push("<input class='cashierBillsID' type='hidden' value='"+ this[8] + "'/>");
						commodity1.push("<input class='temporary' type='hidden' value='"+ name + "'/>");
						commodity1.push("<img src='"+basePath+this[3]+"' alt='' class='shop'>"); 
						commodity1.push("<div class='text'>"); 
						commodity1.push("<h1>"+this[0]+"</h1>"); 
						commodity1.push("<h5>"+((this[4]==null || this[4]=="" || this[4]=="null")?0:this[4])+"次投标</h5>"); 
						commodity1.push("<p>"); 
						commodity1.push("<span class='money'>¥"+this[5]+"</span>"); 
						commodity1.push("<span class='bidding'>"+this[9]+"</span>"); 
						commodity1.push("</p>"); 
						commodity1.push("</div>"); 
						commodity1.push("</li>"); 
					}
				});	
			}
			if(name=="进行中"){
				$("#bid_ing").append(commodity1.join(""));
			}else if(name=="已结束"){
				$("#bid_acc").append(commodity1.join(""));
			}
			if(pageForm!=null){
				pageCount = pageForm.pageCount;
		        pageNumber = pageForm.pageNumber;
		        $(".page_my").find("ul").empty();
		        var paging = [];
		        for ( var i = 1; i <= pageCount; i++) {
		        	if(pageNumber==i){
		        		paging.push("<li onclick='choose(this)' class='active'>"+i+"</li>");
		        	}else{
		        		paging.push("<li onclick='choose(this)'>"+i+"</li>");
		        	}
				}
		        $(".page_my").find("ul").append(paging.join(""));
		        
		        var nub = $(".bid_pages .page_my ul li").length;
		        $(".bid_pages .page_my ul").css("width",40*nub+"px");
		    	$(".bid_pages .page_my").css("width",80+40*nub+"px");
			}
		},
		error : function(data) {
			alert("获取失败");
		}
	});
}

//热门招标
function popularBidding() {
	var tradeName = $(".bid_con").find(".top").find(".right").find(".active").attr("date-name");
	var url = basePath + "/ea/newpcend/sajax_ea_ajaxTheTenderList().jspa";
	$.ajax({
		url : url,
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"search" : "search",
			"ppk.goodsName":"",
			"ppk.tradeID":"",
			"ppk.tradeName":tradeName,
			"pageForm.pageNumber":1,
			"pageForm.pageSize":4,
			"hot":"true",
			"temporary":"进行中"
		},
		success : function(data) {
			var commodity = eval("(" + data + ")");
			var pageForm = commodity.pageForm;
			var commodity1 = [];
			$(".bot_con").find(".right").find("ul").empty();
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				$(pageForm.list).each(function(i, dom) {
					if(i<=6){
						commodity1.push("<li onclick='details(this)'>"); 
						commodity1.push("<input class='ppID' type='hidden' value='"+ this[1] + "'/>");
						commodity1.push("<input class='goodsID' type='hidden' value='"+ this[2] + "'/>");
						commodity1.push("<input class='cashierBillsID' type='hidden' value='"+ this[8] + "'/>");
						commodity1.push("<input class='temporary' type='hidden' value='进行中'/>");
						commodity1.push("<img src='"+basePath+this[3]+"' alt=''>"); 
						commodity1.push("<div class='text'>"); 
						commodity1.push("<h4>"+this[0]+"</h4>"); 
						commodity1.push("<p>&yen;"+this[5]+"</p>"); 
						commodity1.push("</div>"); 
						commodity1.push("</li>"); 
					}
				});	
			}
			$(".bot_con").find(".right").find("ul").append(commodity1.join(""));
		},
		error : function(data) {
			alert("获取失败");
		}
	});
}
//招标详情
function details(obj){
	var ppID = $(obj).find(".ppID").val();
	var goodsID = $(obj).find(".goodsID").val();
	var cashierBillsID = $(obj).find(".cashierBillsID").val();
	var temporary = $(obj).find(".temporary").val();
	var url = basePath + "/ea/newpcend/ea_pcTheTenderDetails.jspa?titleJudge="+titleJudge+"&ppk.ppID="+ppID+"&ppk.goodsID="+goodsID+"&ibs.cashierBillsID="+cashierBillsID+"&temporary="+temporary; 
	
	document.location.href = url;
}