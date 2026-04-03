$(document).ready(function() {
	ajax();
})

function getHeight() {
	t = setTimeout("getHeight()", 200);
	if ($(".last").offset().top < $(window).height()) {
		if(pageNumber<pageCount){
			ajax();	
		}
	}
}

function ajax() {
	var url = basePath + "ea/companyforum/sajax_ea_myAttention.jspa?";
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"pageForm.pageNumber" : parseInt(pageNumber) + 1,
			"pageForm.pageSize" : 5,
			"community":community,
			"ccom.sccId":sccId
		},
		success : function(data) {
			var myAttention = eval("(" + data + ")");
			var pageForm = myAttention.pageForm;
			$(".last").removeClass("last");
			var forum = [];
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				$(pageForm.list).each(function(i, dom) {
					if ($(pageForm.list).length - 1 == i) {
						forum.push("<li class='last' onclick='attention(this)'>");
					} else {
						forum.push("<li onclick='attention(this)'>");
					}
					forum.push("<input type='hidden' class='sccid' value='"+this[0]+"'/>");
					forum.push("<div class='head'>");
					forum.push("<img src='"+basePath+(this[2]==null?'/images/WFJClient/PersonalJoining/headimage.png':this[2])+"' alt=''>");
					forum.push("</div>");
					forum.push("<div class='txt'>");
					forum.push("<h4>"+this[1]+"</h4>");
					forum.push("<p>粉丝<span>"+this[4]+"</span> · 帖子<span>"+this[3]+"</span> </p>");
					if(this[5]=="00"){
						forum.push("<div class='img' onclick='shift(this);event.stopPropagation();'>");
					}else{
						forum.push("<div class='img gz_img' onclick='shift(this);event.stopPropagation();'>");
					}
					forum.push("<img src='"+basePath+"/images/ea/production/forum/guanzhu_.png'>");
					forum.push("<img  src='"+basePath+"/images/ea/production/forum/guanzhu.png'>");
					forum.push("</div>");
					forum.push("</div></li>");
				})
			}
			$(".gz_mil").append(forum.join(""));
			if (pageForm != null) {
				pageNumber = pageForm.pageNumber;
				pageCount = pageForm.pageCount;
				if (pageNumber < pageCount) {
					getHeight();
				}
			}
		}
	})
}
//查询他的社区信息
function attention(obj){
	var sccid = $(obj).find(".sccid").val();
	document.location.href = basePath + "ea/companyforum/ea_myMessage.jspa?community=01&ccom.sccId="+sccid;
}
//添加/取消关注
function shift(obj){
	var b;
	if($(".gz_img").length>0){
		b = "确认添加关注吗?";
	}else{
		b = "确认取消关注吗?";
	}
	if(confirm(b)){
		$(obj).toggleClass("gz_img");
		var ss = $(obj).parents("li").find(".sccid").val();
		var url = basePath + "ea/companyforum/sajax_ea_shift.jspa?";
		$.ajax({
			url : encodeURI(url),
			type : "post",
			async : true,
			dataType : "json",
			data : {
				"ccom.sccId":ss
			},
			success : function(data) {
				var shift = eval("(" + data + ")");
				if(!shift.userExist){
					document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";
				}
			}
		})
	}
}
