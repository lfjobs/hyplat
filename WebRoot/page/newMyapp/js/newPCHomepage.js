$(document).ready(function() {
	var showCount = 1;
	//行业轮播图
	ajaxCarousel();
	//新闻
	information();
	//热门推荐
	generalize();
	$(".content .rim").hide();
	$(".content .shopMall").hide();
	$(".content .mil_shop_con").hide();
	$(".content .recruit").hide();
	$("#footer").hide();
	//商城
	//shoppingMall("praise");//精品
	//shoppingMall("pop");//溯源
	//shoppingMall("smart");//热门
	//shoppingMall("newest");//新品
	//招标商品
	//callforbids();
	//招商服务
	//attractinvestment();
	//招聘人才
	//recruitment();
	//省级联营平台
	//platform("a");
	//县级联营平台
	//platform("b");
	//村级联营平台
	//platform("c");
	//行业联营平台
	//platform("d");
	//国际联营平台
	//international("p201612089W7PQNDBEM0000000009");
	$(window).scroll(function(){
		var scrollTop = $(this).scrollTop(); 
		var documentHeight = $(document).height();
		var windowHeight = $(window).height();
		if(scrollTop + windowHeight + 50 >= documentHeight)
		{
			if(showCount == 1){
				$(".content .rim").show();
				//中联园周边
				ajaxRim();
				showCount++;
				return;
			}
			if(showCount == 2){
				$(".content .shopMall").show();
				$(".content .mil_shop_con").show();
				shoppingMall("praise");//精品
				shoppingMall("pop");//溯源
				shoppingMall("smart");//热门
				shoppingMall("newest");//新品
				showCount++;
				return;
			}
			if(showCount == 3){
				$(".content .recruit").show();
				callforbids();//招标商品
				attractinvestment();//招商服务
				recruitment();//招聘人才
				$("#footer").show();
				$(window).unbind("scroll");
				return;
			}
		}
	});
})
//行业轮播图
function ajaxCarousel(){
	var url = basePath + "/ea/industry/sajax_ea_getIndustry.jspa?";
	$.ajax({
		url : url,
		type : "post",
		async : false,
		dataType : "json",
		success : function(data) {
			var ccode = eval("(" + data + ")");
			var temporary = [];
			if (ccode != null && ccode.industryList!=null && ccode.industryList.length>0) {
				$(ccode.industryList).each(function(i, dom) {
					if(i<17){
						temporary.push("<li>");
						temporary.push("<a href='javascript:void(0);'>");
						temporary.push("<input type='hidden' class='codeID' value='"+this.codeID+"'/>");
						temporary.push("<input type='hidden' class='companyID' value='"+this.companyID+"'/>");
						if(this.iconPath!=""){
							temporary.push("<img src='"+ basePath + this.iconPath +"' alt=''/>");
						}else{
							temporary.push("<img src='"+ basePath +"/page/newMyapp/images/newPCHomepage/temporary/banner3.png' alt=''/>");
						}
						temporary.push("</a>");
						temporary.push("</li>");
					}
				})
				$("#slide").find("ul").append(temporary.join(""));
				
			}
		},
		error : function(data) {
			console.log("获取失败");
		}
	});
			
}

//中联园周边
function ajaxRim(){
	var url = basePath + "/ea/newpcend/sajax_ea_ajaxRim.jspa?";
	$.ajax({
		url : url,
		type : "post",
		async : true,
		dataType : "json",
		success : function(data) {
			var ccode = eval("(" + data + ")");
			var temporary = [];
			if (ccode != null && ccode.industryList!=null && ccode.industryList.length>0) {
				$(ccode.industryList).each(function(i, dom) {
					if(i<12){
						temporary.push("<li>");
						temporary.push("<a href=''>");
						temporary.push("<input type='hidden' class='codeID' value='"+this.codeID+"'/>");
						temporary.push("<input type='hidden' class='companyID' value='"+this.companyID+"'/>");
						temporary.push("<img src='"+ basePath + this.iconPath +"' alt=''/>");
						temporary.push("<p>"+this.codeValue+"</p>");
						temporary.push("</a>");
						temporary.push("</li>");
					}
				})
				$(".rim_swiper").find("ul").append(temporary.join(""));
				
			}
		},
		error : function(data) {
			console.log("获取失败");
		}
	});
}

//新闻
function information() {
	var url = basePath + "ea/newpcend/sajax_ea_ajaxInformation.jspa?";
	$.ajax({
		url : url,
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"pageForm.pageNumber" : 1,
			"pageForm.pageSize":4,
			"informationJudge":"00"
		},
		success : function(data) {
			var news = eval("(" + data + ")");
			var pageForm = news.pageForm;
			var news1 = [];
			$(".web_news_mil").find("ul").empty();
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				$(pageForm.list).each(function(i, dom) {
					news1.push("<li>");
					news1.push("<a href='javascript:void(0)' onclick='details(this)'>");
					news1.push("<input class='ppID' type='hidden' value='"+ this[3] + "'/>");
					news1.push("<img src='"+ basePath+ this[2]+ "' alt='' onerror='this.src=\""+ basePath+ "/images/ea/production/forum/reportAnError.png\"'>");
					news1.push("<div class='text'>");
					news1.push("<h5>" + this[0] + "</h5>");
					news1.push("<div class='bottom'>");
                    if(this[4]!=null){
                        news1.push("<h4>" + this[4]
                            + "</h4>");
                    }
					if (this[1].month < 9) {
						if (this[1].date < 10) {
							var date = (this[1].year + 1900)
									+ "-0"
									+ (this[1].month + 1)
									+ "-0"
									+ this[1].date;
						} else {
							var date = (this[1].year + 1900)
									+ "-0"
									+ (this[1].month + 1)
									+ "-"
									+ this[1].date;
						}
					} else {
						var date = (this[1].year + 1900)
								+ "-"
								+ (this[1].month + 1)
								+ "-" + this[1].date;
					}
					news1.push("<span>" + date + "</span>");
					news1.push("</div>");
					news1.push("</div>");
					news1.push("</a>");
					news1.push("</li>");	
				})
			}
			$(".web_news_mil").find("ul").append(news1.join(""));
		},
		error : function(data) {
			console.log("获取失败");
		}
	});
			
}
//新闻详情
function details(obj){
	var ppID = $(obj).find(".ppID").val();
	document.location.href = basePath + "ea/newpcend/ea_informationForDetails.jspa?titleJudge=01&ppk.ppID="+ppID;
}

//热门推荐
function generalize() {
	var url = basePath + "ea/newpcend/sajax_ea_ajaxInformation.jspa?";
	$.ajax({
		url : url,
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"pageForm.pageNumber" : 1,
			"pageForm.pageSize":3,
			"informationJudge":"01"
		},
		success : function(data) {
			var news = eval("(" + data + ")");
			var pageForm = news.pageForm;
			var news1 = [];
			$(".hot").empty();
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				$(pageForm.list).each(function(i, dom) {
					news1.push("<li>"); 
					news1.push("<a href='javascript:void(0)' onclick='details(this)'>");
					news1.push("<input class='ppID' type='hidden' value='"+ this[3] + "'/>");
					news1.push("<img src='"+basePath+this[2]+"' alt='' onerror='this.src=\""
									+ basePath
									+ "/images/ea/production/forum/reportAnError.png\"'>"); 
					news1.push("<h5>"+this[0]+"</h5>"); 
					news1.push("</a>"); 
					news1.push("</li>"); 
				})	
			}
			$(".hot").append(news1.join(""));
		},
		error : function(data) {
			console.log("获取失败");
		}
	});
			
}

//数字地球商城
function shoppingMall(type) {
	var tradecode = "";
	var url = basePath + "/ea/digitalmall/sajax_ea_ajaxDigitalMall.jspa?";
	if(type=="pop"){//溯源
		tradecode = "农林牧渔";
	}
	$.ajax({
		url : url,
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"pageForm.pageNumber" : pageNumber,
			"search" : type,
			"tradecode":tradecode,
			"proName" : ""
		},
		success : function(data) {
			var mall = eval("(" + data + ")");
			var pageForm = mall.pageForm;
			if(type=="praise"){//精品
				$(".mil_shop_con").find(".no1").find(".shop_con").empty();
			}else if(type=="pop"){//溯源
				$(".mil_shop_con").find(".no2").find(".shop_con").empty();
			}else if(type=="smart"){//推荐
				$(".mil_shop_con").find(".no3").find(".shop_con").empty();
			}else if(type=="newest"){//新品
				$(".mil_shop_con").find(".no4").find(".shop_con").empty();
			}
			var mall1 = [];
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				$(pageForm.list).each(function(i, dom) {
					if(i<=3){
						mall1.push("<li onclick='description(this)'>");
						/*mall1.push("<li>");*/
						mall1.push("<input class='ppID' type='hidden' value='"+ this[1] + "'/>");
						mall1.push("<img src='"+basePath+this[4]+"'>");
						mall1.push("<p>"+this[0]+"</p>");
						mall1.push("</li>");
					}
				})	
			}
			if(type=="praise"){//精品
				$(".mil_shop_con").find(".no1").find(".shop_con").append(mall1.join(""));
			}else if(type=="pop"){//溯源
				$(".mil_shop_con").find(".no2").find(".shop_con").append(mall1.join(""));
			}else if(type=="smart"){//推荐
				$(".mil_shop_con").find(".no3").find(".shop_con").append(mall1.join(""));
			}else if(type=="newest"){//新品
				$(".mil_shop_con").find(".no4").find(".shop_con").append(mall1.join(""));
			}
			$(".mil_shop_con").find(".shop_li").find(".shop_con").find("li:even").css("border-right","1px solid #ddd");
		},
		error : function(data) {
			console.log("获取失败");
		}
	});
}
//商品详情
function description(obj){
	ppid = $(obj).find(".ppID").val();
	var url=basePath+"ea/newpcend/ea_goodsDetails.jspa?ppk.ppID="+ppid+"&titleJudge=05";
	document.location.href = url;
}

//招标商品
function callforbids() {
	var url = basePath + "ea/purchasebids/sajax_ea_ajaxGoodbidList.jspa";
	$.ajax({
		url : url,
		type : "post",
		async : true,
		dataType : "json",
		data : {
			search : "search",
			parameter:"",
			tradeID:"",
			tradeName:"",
			"pageForm.pageNumber":1

		},
		success : function(data) {
			var commodity = eval("(" + data + ")");
			var pageForm = commodity.pageForm;
			var commodity1 = [];
			$(".attract_con").find(".left").find(".top").find("ul").empty();
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				$(pageForm.list).each(function(i, dom) {
					if(i<=2){
						commodity1.push("<li>"); 
						commodity1.push("<a href='javascript:void(0)'>");
						commodity1.push("<input class='ppID' type='hidden' value='"+ this[1] + "'/>");
						commodity1.push("<img src='"+basePath+this[3]+"' alt=''>"); 
						commodity1.push("<p>"+this[0]+"</p>"); 
						commodity1.push("</a>"); 
						commodity1.push("</li>"); 
					}
				});	
			}
			$(".attract_con").find(".left").find(".top").find("ul").append(commodity1.join(""));
		},
		error : function(data) {
			console.log("获取失败");
		}
	});
			
}

//招商服务
function attractinvestment() {
	url = basePath + "/ea/productAgent/sajax_ajaxProAgentList.jspa";
	$.ajax({
		url : url,
		type : "post",
		async : true,
		dataType : "json",
		data:{
            "pageForm.pageNumber":1,
            "pageForm.pageSize":3,
            "flag":"p20170220ZVZR76B88M0000000018",
            "search":""
        },
		success : function(data) {
			var commodity = eval("(" + data + ")");
			var pageForm = commodity.pageForm;
			var commodity1 = [];
			$(".attract_con").find(".left").find(".bottom").find("ul").empty();
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				$(pageForm.list).each(function(i, dom) {
					if(i<=2){
						commodity1.push("<li>"); 
						commodity1.push("<a href='javascript:void(0)'>");
						commodity1.push("<input class='ppID' type='hidden' value='"+ this[0] + "'/>");
						commodity1.push("<img src='"+basePath+this[6]+"' alt=''>"); 
						commodity1.push("<p>"+this[7]+"</p>"); 
						commodity1.push("</a>"); 
						commodity1.push("</li>"); 
					}
				});	
			}
			$(".attract_con").find(".left").find(".bottom").find("ul").append(commodity1.join(""));
		},
		error : function(data) {
			console.log("获取失败");
		}
	});
}

//招聘人才
function recruitment(){
	var url = basePath + "/ea/newpcend/sajax_ea_ajaxRecruitment.jspa";
	$.ajax({
		url : url,
		type : "post",
		asycn : true,
		dataType : "json",
		data : {
			"pageForm.pageNumber" : 1,
			"pageForm.pageSize" : 3,
            "ppk.companyID":"company201009046vxdyzy4wg0000000025"
		},
		success : function(data) {
			var rt = eval("(" + data + ")");
			var pageForm = rt.pageForm;
			var rtt = [];
			$(".swiper-container-att").find("ul").empty();
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				$(pageForm.list).each(function(i, dom) {
					rtt.push("<li>"); 
					rtt.push("<a href='javascript:void(0)'>");
					rtt.push("<input class='riId' type='hidden' value='"+ this[5] + "'/>");
					rtt.push("<div class='txt'>"); 
					rtt.push("<h4>"+this[1]+"</h4>"); 
					rtt.push("<p>"+this[2]+"</p>"); 
					rtt.push("</div>"); 
					rtt.push("</a>"); 
					rtt.push("</li>"); 
				});	
			}
			$(".swiper-container-att").find("ul").append(rtt.join(""));
		},
		error : function(data) {
			console.log("获取失败");
		}
	});			
}






//省/县/村/行业联营平台
function platform(type) {
	var url = basePath + "/ea/newpcend/sajax_ea_queryPlatform.jspa?";
	$.ajax({
		url : url,
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"pageForm.pageNumber" : 1,
			"pageForm.pageSize" : 4,
			"ppk.standard" : type
		},
		success : function(data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			if(type=="a"){
				$(".mil_terrace").find(".sheng").find(".mil_con").empty();
			}else if(type=="b"){
				$(".mil_terrace").find(".xian").find(".mil_con").empty();
			}else if(type=="c"){
				$(".mil_terrace").find(".cun").find(".mil_con").empty();
			}else if(type=="d"){
				$(".mil_terrace").find(".hang").find(".mil_con").empty();
			}
			var platform = [];
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				$(pageForm.list).each(function(i, dom) {
					platform.push("<li>");
					platform.push("<a href='javascript:void(0)'>");
					platform.push("<input class='ppID' type='hidden' value='"+ this[0] + "'/>");
					platform.push("<div class='left'>");
					platform.push("<img src='"+basePath+this[1]+"' alt=''>");
					platform.push("</div>");
					platform.push("<div class='right'>");
					platform.push("<p>"+this[2]+"</p>");
					platform.push("</div>");
					platform.push("</a>");
					platform.push("</li>");
				})	
			}
			if(type=="a"){
				$(".mil_terrace").find(".sheng").find(".mil_con").append(platform.join(""));
			}else if(type=="b"){
				$(".mil_terrace").find(".xian").find(".mil_con").append(platform.join(""));
			}else if(type=="c"){
				$(".mil_terrace").find(".cun").find(".mil_con").append(platform.join(""));
			}else if(type=="d"){
				$(".mil_terrace").find(".hang").find(".mil_con").append(platform.join(""));
			}
		},
		error : function(data) {
			console.log("获取失败");
		}
	});
			
}
//国际联营平台
function international(type) {
	var url = basePath + "/ea/newpcend/sajax_ea_international.jspa?";
	$.ajax({
		url : url,
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"pageForm.pageNumber" : 1,
			"pageForm.pageSize" : 12,
			"ppk.ppID" : type
		},
		success : function(data) {
			var international = eval("(" + data + ")");
			var pageForm = international.pageForm;
			$(".internation").find(".min_inter").empty();
			var platform = [];
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				$(pageForm.list).each(function(i, dom) {
					platform.push("<li>");
					platform.push("<a href='javascript:void(0)'>");
					platform.push("<input class='ppID' type='hidden' value='"+ this[0] + "'/>");
					platform.push("<div class='left'>");
					platform.push("<img src='"+basePath+this[1]+"' alt=''>");
					platform.push("</div>");
					platform.push("<div class='right'>");
					platform.push("<p>"+this[2]+"</p>");
					platform.push("</div>");
					platform.push("</a>");
					platform.push("</li>");
				})	
			}
			$(".internation").find(".min_inter").append(platform.join(""));
		},
		error : function(data) {
			console.log("获取失败");
		}
	});
			
}
