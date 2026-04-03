$(document).ready(function() {
	information();
	paging();
})
function information() {
	var url = basePath + "ea/newpcend/sajax_ea_ajaxInformation.jspa?";
	$
			.ajax({
				url : url,
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"pageForm.pageNumber" : pageNumber,
					"pageForm.pageSize":5,
					"informationJudge":"01"
				},
				success : function(data) {
					var news = eval("(" + data + ")");
					var pageForm = news.pageForm;
					var list = news.list;
					var news1 = [];
					$(".web_con_left4").empty();
					if (pageForm != null && pageForm.list != null
							&& pageForm.list.length > 0) {
						$(pageForm.list)
								.each(
										function(i, dom) {
											news1.push("<a href='javascript:void(0);' onclick='details(this)'>");
											news1.push("<li>");
											news1.push("<input class='ppID' type='hidden' value='"+ this[3] + "'/>");
											news1.push("<h3>"+this[0]+"</h3>");
											news1.push("<p><img src='"+basePath+this[2]+"' alt='' onerror='this.src=\""
															+ basePath
															+ "/images/ea/production/forum/reportAnError.png\"'><span limit='17'>"+list[i]+"</span></p>");
											news1.push("</li>");
											news1.push("</a>");    
											news1.push("<hr style='border-top: 1px solid #ddd;width: 80%;margin: 20px auto 0 auto;'>");  
										})
					}
					$(".web_con_left4").append(news1.join(""));
				},
				error : function(data) {
					alert("获取失败");
				}
			});
}

function paging() {
	var url = basePath + "ea/newpcend/sajax_ea_ajaxInformation.jspa?";
	$
			.ajax({
				url : url,
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"pageForm.pageNumber" : pageNumber,
					"pageForm.pageSize":5,
					"informationJudge":"00"
				},
				success : function(data) {
					var news = eval("(" + data + ")");
					var pageForm = news.pageForm;
					var news1 = [];
					$(".Web_news_con_c").empty();
					$(".paging").empty();
					if (pageForm != null && pageForm.list != null
							&& pageForm.list.length > 0) {
						$(pageForm.list)
								.each(
										function(i, dom) {
											news1.push("<li onclick='details(this)'>");
											news1
													.push("<input class='ppID' type='hidden' value='"
															+ this[3] + "'/>");
											news1
													.push("<img src='"
															+ basePath
															+ this[2]
															+ "' alt='' onerror='this.src=\""
															+ basePath
															+ "/images/ea/production/forum/reportAnError.png\"'>");
											news1.push("<div class='text'>");
											news1.push("<h5>" + this[0]
													+ "</h5>");
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
											news1.push("<span>" + date
													+ "</span>");
											news1.push("</div>");
											news1.push("</div>");
											news1.push("</li>");
										})
					}
					$(".Web_news_con_c").append(news1.join(""));
					pageCount = pageForm.pageCount;
					pageNumber = pageForm.pageNumber;
					news1.splice(0,news1.length);
					news1.push("<a href='javascript:homePage();'>首页</a>");
					news1.push("<a href='javascript:lastStep();'>&lt; 上一页</a>");
					for ( var i = 1; i <= pageCount; i++) {
						if(i==pageNumber){
							news1.push("<a href='javascript:void(0);' onclick='choose(this);' class='active'>"+i+"</a>");
						}else{
							if(i==1||i==pageCount||(i>=pageNumber-2&&i<=pageNumber+2)){
								news1.push("<a href='javascript:void(0);' onclick='choose(this);'>"+i+"</a>");
							}else if(i!=1&&i!=pageCount&&(i==pageNumber-3||i==pageNumber+3)){
								news1.push("<a href='javascript:void(0);' style='border:none;background-color:white;'><span>...</span></a>");
							}
						}
					}
					news1.push("<a href='javascript:nextStep();'>下一页 &gt;</a>");
					news1.push("<a href='javascript:colophon();'>末页</a>");
					news1.push("<span>第"+pageNumber+"页/共"+pageCount+"页 记录数："+pageForm.recordCount+"条</span>");
                    $(".paging").append(news1.join(""));
				},
				error : function(data) {
					alert("获取失败");
				}
			});
}
//首页
function homePage(){
	pageNumber = 1;
	paging();
}
//末页
function colophon(){
	pageNumber = pageCount;
	paging();
}
//上一步
function lastStep(){
	if(pageNumber>1){
		pageNumber = parseInt(pageNumber)-1;
		paging();
	}
}
//下一步
function nextStep(){
	if(pageNumber<pageCount){
		pageNumber = parseInt(pageNumber)+1;
		paging();
	}
}
//选取
function choose(obj){
	pageNumber = $(obj).text();
	paging();
}


function details(obj){
	var ppID = $(obj).find(".ppID").val();
	document.location.href = basePath + "ea/newpcend/ea_informationForDetails.jspa?titleJudge=01&ppk.ppID="+ppID;
}
