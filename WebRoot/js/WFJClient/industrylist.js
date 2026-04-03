var height = 0;
var h1=0;
var codepid = "";
var pagenumber = 1 ;//第几页
var pagecount = 0;//总页数
var t;
//一级行业
var url = basePath + "/ea/industry/sajax_ea_getIndustry.jspa";
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : false,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var list = member.industryList;
			if (null == list) {
				return;
			} else {
				var htl = new Array();
				htl
						.push("<li><a href='"
								+ basePath
								+ "ea/industry/ea_getAllCompanyList.jspa?industryType='>全部</a></li>");
				for (var i = 0; i < list.length; i++) {
					htl.push("<li id='a" + i + "' onclick='getIndustry(" + i
							+ ")' class='" + list[i].codeID
							+ "' name='industryname'>" + list[i].codeValue
							+ "</li>");
				}
				$("#daohang").html(htl.join(""));
			}
		}
	});
	getIndustry(1);
// 全部公司
// 子行业

function getIndustry(h) {
	clearTimeout(t);
	$("#hangyecontent").empty();
	pagenumber=1;
	h1=h;
	codepid = $("#a" + h).attr("class");
	var url = basePath + "/ea/industry/sajax_ea_getAjaxIndustry.jspa?codePID="
			+ codepid;
	$.ajax({
		url : encodeURI(url),
		type : "get",
		dataType : "json",
		data:{						
		  "pageForm.pageNumber":pagenumber,
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			pagenumber = pageForm.pageNumber;
			pagecount = pageForm.pageCount;
			var industrylist = pageForm.list;
			var _FS=$(window).height()* 0.0285+"px";
			var head_c="<style>"+".li_Rtxt"+"{"+"font-size:"+_FS+"}"+"</style>";
			$("head").append(head_c);
			if (pageForm == null) {
				return;
			} else {
				var industrystr = "";
				for (var i = 0; i < industrylist.length; i++) {
					
					industrystr += "<li id='b" + i
							+ "'onclick='getCompanyList(" + h + "," + i
							+ ")' style=' margin-bottom:2.4px;'>";
					industrystr += "<div style='line-height:25.6px;font-size:12.8px;'>";
					industrystr += "<img src='" + basePath
							+ industrylist[i].iconPath + "'/></div>";
						industrystr += "<div style='padding-left: 42%;padding-right: 6%;line-height:25.6px;font-size:12.8px;padding-top:24px;'>";
					industrystr += "<p style='color:#373737' class='li_Rtxt'>"
							+ industrylist[i].codeValue
							+ "</p><p id='companycount" + i
							+ "'></p></div></li>";
				}
				if(pagenumber==pagecount){
					industrystr += "<li style='margin-bottom:2.4px;height:40px;vertical-align:middle;text-align:center;font-size:14px;padding-top:8px;'>更多行业入驻进行中</li>";
				}
				$("#hangyecontent").html(industrystr);
				getHeight();
				/* for (var i = 0; i < industrylist.length; i++) {
					// getCompanyCount(h,i);
				} */	
			}
		}
	});
}
//获取hangyedaohang距离页面底部的高度
function getHeight(){
	height = parseInt(Math.abs($("#hangyecontent").height()-($(window).height()-$("#hangyecontent").offset().top)));
	t=setTimeout("getHeight()", 200);
	if(height<$(".wfj12_019_numshop_right_hidden").height()){
		if(pagenumber<pagecount){
			getMoreIndustry(h1);
		}
	}
}
function getMoreIndustry(h) {
	pagenumber = pagenumber+1;
	h1=h;
	codepid = $("#a" + h).attr("class");
	var url = basePath + "/ea/industry/sajax_ea_getAjaxIndustry.jspa?codePID="
			+ codepid;
	$.ajax({
		url : encodeURI(url),
		type : "get",
		dataType : "json",
		data:{						
		  "pageForm.pageNumber":pagenumber,
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			pagenumber = pageForm.pageNumber;
			pagecount = pageForm.pageCount;
			pagesize = pageForm.pageSize;
			var industrylist = pageForm.list;
			var _FS=$(window).height()* 0.0285+"px";
			var head_c="<style>"+".li_Rtxt"+"{"+"font-size:"+_FS+"}"+"</style>";
			$("head").append(head_c);
			var industrylist = pageForm.list;
			if (pageForm == null) {
				return;
			} else {
				//$("#hangyecontent").find("li").eq(-1).remove();
				var industrystr="";
				for (var i = 0; i < industrylist.length; i++) {
					industrystr += "<li id='b" + (pagesize*(pagenumber-1)+i)
							+ "'onclick='getCompanyList(" + h + "," + (pagesize*(pagenumber-1)+i)
							+ ")' style=' margin-bottom:2.4px;'>";
					industrystr += "<div style='line-height:25.6px;font-size:12.8px;'>";
					industrystr += "<img src='" + basePath
							+ industrylist[i].iconPath + "'/></div>";
					industrystr += "<div style='padding-left: 42%;padding-right: 6%;line-height:25.6px;font-size:12.8px;padding-top:24px;'>";
					industrystr += "<p style='color:#373737' class='li_Rtxt'>"
							+ industrylist[i].codeValue
							+ "</p><p id='companycount" + ((pagesize-1)*pagenumber+i)
							+ "'></p></div></li>";
				}
				
				if(pagenumber==pagecount){
					industrystr += "<li style='margin-bottom:2.4px;height:40px;vertical-align:middle;text-align:center;font-size:14px;padding-top:8px;'>更多行业入驻进行中</li>";
				}
				$("#hangyecontent").append(industrystr);
				/* for (var i = 0; i < industrylist.length; i++) {
					// getCompanyCount(h,i);
				} */
			}
		}
	});
}	
// 搜索框模糊查询
$("#img").click(function() {
	var industryType = $(".wfj_search").find("input").val();
	if (industryType == "请输入行业名称或公司名称进行查询") {
		industryType = "";
	}
	var surl = basePath
			+ "ea/industry/ea_getMySelCompanyList.jspa?industryType="
			+ industryType;
	document.location.href = surl;
});
//入驻公司数
function getCompanyCount(h, i) {
	var industry = $("#a" + h).text();// 一级行业
	var industry1 = $("#b" + i).find("div").find("p").eq(0).text();// 二级行业
	var url = basePath
			+ "/ea/industry/sajax_ea_getCompanyCount.jspa?industryType="
			+ industry + "/" + industry1;
	$.ajax({
				url : encodeURI(url),
				type : "get",
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					companycount = member.companycount;
					$("#companycount" + i).append("入驻公司数:" + companycount);
				}
			});
}
/* 查询公司 */
function getCompanyList(h, i) {
	var industry = $("#a" + h).text();// 一级行业
	console.log(industry)
	var industry1 = $("#b" + i).find("div").find("p").eq(0).text();// 二级行业
	
	vurl = basePath + "ea/industry/ea_getAllCompanyList.jspa?industryType="
			+ industry + "/" + industry1;
	document.location.href = vurl;
}

$(document).ready(function(){
	$("body").css("height",$(window).height());
	$(".wfj12_019_numshop_top").find("ul").attr("style","height:"+$(window).height()*0.08+"px;lineHeight:"+$(window).height()*0.08+"px;background-color:#FFF;");
	$(".wfj12_019_numshop_top").find("li").eq(0).attr("style","float:left;width:15%;height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
	$(".wfj12_019_numshop_top").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.05+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
	$(".wfj12_019_numshop_top").find("li").eq(1).attr("style","float:left;height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;color:#373737;");
	
	$(".wfj_search").find("div").attr("style","margin:" + $(window).height() * 0.01 + "px auto;height:"+ $(window).height() * 0.05 + "px; border-radius:"+ $(window).height() * 0.01 + "px;");
	$(".wfj_search").find("li").eq(0).attr("style", "width:86%;margin-left:2%;");
	$(".wfj_search").find("input").attr("style","height:" + $(window).height() * 0.048 + "px;font-size:"+ $(window).height() * 0.025 + "px;outline:none;");
	$(".wfj_search").find("li").eq(1).attr("style","width:10%;margin-right:2%;text-align:right;");
	$(".wfj_search").find("img").attr("style","height:" + $(window).height() * 0.040 + "px;width:auto;padding-top:2px");

	$(".wfj12_019_content").attr("style","height:"+($(window).height()-$(".wfj12_019_numshop_top").height()- $(".wfj_search").height())+"px;width:"+$(window).width()+"px;overflow:hidden;");
   	$(".wfj12_019_hidden").attr("style","height:"+$(".wfj12_019_content").height()+"px;overflow:auto;");
   	$(".wfj12_019_numshop_left_content").attr("style","width:"+ $(window).width()* 0.35+"px;height:"+ ($(window).height()-$(".wfj12_019_numshop_top").height()- $(".wfj_search").height())+"px;");
	$(".wfj12_019_numshop_left_hidden").attr("style","width:"+ ($(".wfj12_019_numshop_left_content").width()+ 17) + "px;height:"+ $(".wfj12_019_numshop_left_content").height() + "px;");
	$(".wfj12_019_numshop_right_content").attr("style","width:"+ $(window).width()* 0.65+ "px;height:"+ parseInt($(window).height()- $(".wfj12_019_numshop_top").height()- $(".wfj_search").height()) + "px;");
	$(".wfj12_019_numshop_right_hidden").attr("style","width:"+ ($(".wfj12_019_numshop_right_content").width()+ 17) + "px;height:"+ $(".wfj12_019_numshop_right_content").height()+ "px;");
	
	$(".wfj12_019_numshop_left ul li").attr("style","font-size:"+$(window).height()* 0.03+"px");
	
	
});