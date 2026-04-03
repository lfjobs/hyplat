$(document).ready(function() {
	$(".detail_page .det_mil .right .color .xuan li .attrivalue").each(function(){
		if($(this).val()!=""){
			$(this).parent("li").addClass("active").siblings().removeClass("active");
		}
	});
	loaded();
	particulars();
	comment();
})
// 热卖推荐
function loaded() {
	var url = basePath + "/ea/digitalmall/sajax_ea_ajaxDigitalMall.jspa?";
	$.ajax({
				url : url,
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"pageForm.pageNumber" : 1,
					"search":"smart",
					"proName":goodsname
				},
				success : function(data) {
					var search = eval("(" + data + ")");
					var pageForm = search.pageForm;
					var recommend = [];
					$(pageForm.list).each(function(i, dom) {
						recommend.push("<div class='swiper-slide'>");
						recommend.push("<ul class='shop_list'>");
						recommend.push("<li>");
						recommend.push("<input class='ppID' type='hidden' value='"+ this[1] + "'/>");
						recommend.push("<img src='"+basePath+this[4]+"' alt=''>");
						recommend.push("<p>"+this[0]+"</p>");
						recommend.push("<h5>&yen;<span>"+this[2]+"</span></h5>");
						recommend.push("</li>");
						recommend.push("</ul>");
						recommend.push("</div>");
					})
					mySwiper.appendSlide(recommend.join("")); //加到Swiper的最后
				},
				error : function(data) {
					alert("获取失败");
				}
			});
}

//商品详情
function particulars() {
	var url = basePath + "/ea/newpcend/sajax_ea_ajaxProductParticulars.jspa?";
	$
			.ajax({
				url : url,
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"ppk.ppID":ppID
				},
				success : function(data) {
					var search = eval("(" + data + ")");
					var txtList= search.urlList;
					var array=[];
					if(txtList!=null&&txtList.length!=0){
						for(var i=0;i<txtList.length;i++){
							array.push("<ul id=\"small\"><li>"+txtList[i]+"</li></ul>");
						}
					}
					$(".shop-con").first().before(array.join(""));
				},
				error : function(data) {
					alert("获取失败");
				}
			});
}
//上一步
function lastStep(){
	if(pageNumber>1){
		pageNumber = parseInt(pageNumber)-1;
		comment();
	}
}
//下一步
function nextStep(){
	if(pageNumber<pageCount){
		pageNumber = parseInt(pageNumber)+1;
		comment();
	}
}
//选取
function choose(obj){
	pageNumber = $(obj).text();
	comment();
}


//商品评论
function comment() {
	var url = basePath + "/ea/newpcend/sajax_ea_ajaxProductEvaluation.jspa?";
	$
			.ajax({
				url : url,
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"pageForm.pageNumber" : pageNumber,
					"pageForm.pageSize" : 1,
					"ppk.ppID":ppID
				},
				success : function(data) {
					var comment = eval("(" + data + ")");
					var pageForm = comment.pageForm;
					var comment = [];
					var comment1 = [];
					$(".eval").empty();
					if (pageForm != null && pageForm.list != null
							&& pageForm.list.length > 0) {
						$(pageForm.list).each(function(i, dom) {
							comment.push("<div class='eval_mil'>");
							comment.push("<h4 class='name'>"+((this[0]=="00")?"匿名用户":this[1])+"</h4>");
							comment.push("<div class='comment'>");
							comment.push("<p>"+this[2]+"</p>");
							comment.push("<ul class='imgs'>");
							if(this[3]!=null){
								comment.push("<li class='adv'>");
								comment.push("<img src='"+basePath+this[3]+"'>");
								comment.push("</li>");
							}
							if(this[4]!=null){
								comment.push("<li class='adv'>");
								comment.push("<img src='"+basePath+this[4]+"'>");
								comment.push("</li>");
							}
							if(this[5]!=null){
								comment.push("<li class='adv'>");
								comment.push("<img src='"+basePath+this[5]+"'>");
								comment.push("</li>");
							}
							comment.push("</ul>");
							comment.push("<h5 class='time'>"+this[6]+"</h5>");
							comment.push("</div>");
							comment.push("<div class='data'>");
							comment.push("<p class='color'>规格:"+this[7]+"</p>");
							comment.push("</div>");
							comment.push("</div>");
						});
						$(".eval").append(comment.join(""));
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
				        var page_nub = $(".page_my li").length;
				         $(" .page_my ul").css("width",page_nub*40+"px");
				         $(".page_my").css("width",80+page_nub*40+"px");
					}
				},
				error : function(data) {
					alert("获取失败");
				}
			});
}



//立即购买
function buyNow() {
	var standardArray=[];
	var flag=true;
	$(".detail_page .det_mil .right .color h4 .attriname").each(function(){
		if($(this).val()==""){
			alert("请选择"+$(this).parent("h4").text().trim()+"!");
			flag=false;
		}else{
			standardArray.push($(this).val()+":");
			$(this).parent("h4").next("ul").find("li").find(".attrivalue").each(function(){
				if($(this).val()!=""){
					standardArray.push($(this).val()+",");
				}
			});
		}
	});
	if(flag){
		var standard=standardArray.join("")
		$("#standard").val(standard.substring(0,standard.length-1));
		if(type=="报名"){
			document.location.href=basePath+"ea/drivem/ea_toChoice.jspa?ppId="+ppID+"&companyId="+companyId+"&flag=shebei";
			return;
		}else if(type == "个人会员" || type == "公司会员"){
			alert("请先加入公司会员！");
			return false;
		}else {
			var url = basePath + "ea/wfjshop/sajax_ea_validateOrdiGoods.jspa";
			$.ajax({
				url : url,
				type : "get",
				dataType : "json",
				data : {
					ppid : ppID,
					goodsid:goodsId,
					companyId:companyId
				},
				success : function(data) {
					var m = eval("(" + data + ")");
					var login = m.login;
					if (login == "login") {
						document.location.href = basePath + "page/newMyapp/login.jsp?loginPage=login";
						return;
					}
					var result = m.result;
					if (result != "") {
						alert(result);
						if(result=="此产品限会员购买请立即升级"){
							 document.location.href = basePath+"/ea/wfjplatform/ea_getPlatform.jspa?type=getPlatform";
						}
						return false;
					} else {
						$("#mainForm").submit();
					}
				},
				error : function(data) {
					alert("验证失败");
				}
			});
		}
	}else{
		return false;
	}
}




