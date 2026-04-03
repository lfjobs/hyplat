var pagenumber = 0;
var height = 0;
var t;
var pagecount = 0;
$(document).ready(function() {
	loaded();
	
	$("header").css("height", $(window).height() * 0.08 - 1 + "px");
	$("header").css("line-height", $(window).height() * 0.08 - 1 + "px");
	$(".content_hidden").attr("style", ";overflow: hidden;" + "height:" + $(window).height() * 0.92 + "px");
	$(".content").attr("style", "overflow: hidden;" + "height:" + $(window).height() * 0.92 + "px");
	$(".content_").attr("style", "overflow: hidden;" + "height:" + $(window).height() * 0.92 + "px");
	$(".head_top").css("height", $(window).height() * 0.08 - 1 + "px");
	$(".head_top ul li").css("line-height", $(window).height() * 0.05 + "px");
	$(".head_top ul li:nth-child(1) dl").css("margin", $(window).height() * 0.015 + "px");
	$(".head_top ul li:nth-child(2) input").attr("style", "margin:" + $(window).height() * 0.015 + "px;margin-left:0;line-height:" + $(window).height() * 0.05 + "px;");
	$(".content_hidden_").css("height", $(window).height() * 0.92 + "px");
	$(".gjpt_search").css("height", $(window).height() * 0.08 + "px");
	$(".con_").css("height", $(".content_").height() - $(".gjpt_search").height() - 6 + "px");
	$(".gjpt_search_").click(function() {
		$(".gjpt_search_").hide();
		$(".gjpt_search input").focus()
	});
	$(".gjpt_search input").focus(function() {
		$(".gjpt_search_").hide()
	});
	$(".gjpt_search input").blur(function() {
		var txt = $(".gjpt_search input").val();
		if (txt == "") {
			$(".gjpt_search_").show()
		} else {
			$(".gjpt_search_").hide()
		}
	});
	$("#content").bind("propertychange input", function() {
		$(".gjpt_zzy").empty();
		pagenumber=0;
		loaded();
	})
});

function getHeight() {
	t = setTimeout("getHeight()", 200);
	var height = $(".gjpt_zzy").height() - $(".gjpt_zzy").offset().top;
	var height1 = $(".content").height();
	if (height < height1) {
		if (pagenumber < pagecount) {
			loaded();
		}
	}
}

function Share(){
	clearTimeout(t);
	pagenumber += 1;
}
function loaded() {
	var content = $("#content").val();
	Share();
	if(param!=null&param!=""&&param!=undefined){
		url = basePath + "ea/WfjIndustryPlatform/sajax_ea_getAjax.jspa?ppid=" + ppid + "&content=" + content+"&param="+param;
		
	}else{
		url = basePath + "ea/WfjIndustryPlatform/sajax_ea_getAjax.jspa?ppid=" + ppid + "&content=" + content;
		
	}
	$.ajax({
		url: url,
		type: "post",
		async: false,
		dateType: "json",
		data: {
			"pageForm.pageNumber": pagenumber,
		},
		success: function(data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			$(".last").removeClass("last");
			var htl = new Array();
			if (pageForm != null) {
				var list = pageForm.list;
				pagenumber = pageForm.pageNumber;
				pagecount = pageForm.pageCount
			}
			if (pageForm != null && pageForm.recordCount > 0) {
				for (var i = 0; i < list.length; i++) {
					var pp = list[i];
					htl.push('<ul onclick="dj(\''+pp[1]+'\',\''+pp[2]+'\')">');
					htl.push('<li > ');
					htl.push('<div class="left"><img src="'+basePath+'images/BuildPlatform/ico-L.png" alt=""></div>');
					htl.push('<div class="right" >');
					htl.push(' <p>' + pp[2] + '经济平台</p>');
					htl.push('</div></li></ul>');
				}
			}
			$(".gjpt_zzy").append(htl.join(""));
			getHeight();
		},
		error: function(data) {
			alert("获取项目失败")
		}
	})
}
