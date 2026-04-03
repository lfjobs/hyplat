$(document).ready(function() {
	ajax();
	//提示框    
	$("#prompt").css("position","absolute").css("top",$(window).height()*0.09+"px");
	$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
	$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
})

function getHeight() {
	t = setTimeout("getHeight()", 200);
	if ($(".last").offset().top < $(window).height()) {
		if(pageNumber<pageCount){
			ajax();	
		}
	}
}
//提示框样式
function prompt(obj){
	if($("#prompt").css("display")!="none")
		return;
	$("#prompt").find("span").text(obj);
	$("#prompt").fadeIn(500);
	setTimeout(function(){
		$("#prompt").fadeOut(500);
		$("#prompt").find("span").text("");
	}, 2000);
}
function ajax() {
	var pcid = $(".huifu").find(".pcid").val();
	var url = basePath + "ea/companyforum/sajax_ea_invitationReply.jspa?";
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"pageForm.pageNumber" : parseInt(pageNumber) + 1,
			"pageForm.pageSize" : 5,
			"pct.pcID":pcid,
		},
		success : function(data) {
			var invitationReply = eval("(" + data + ")");
			var pageForm = invitationReply.pageForm;
			$(".last").removeClass("last");
			var forum = [];
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				$(pageForm.list).each(function(i, dom) {
					if ($(pageForm.list).length - 1 == i) {
						forum.push("<li class='last'>");
					} else {
						forum.push("<li>");
					}
					forum.push("<p><span id='name'>"+this[1]+"：</span>");
					forum.push("回复<span id='name2'>"+this[2]+"</span>");
					forum.push("："+this[3]+"</p>");
					forum.push("<p class='time'>"+this[4]+"</p>");
					if(this[5]==staffid){
						forum.push("<input type='button' value='删除' class='remove' onclick='del2(this)'>");
					}
					forum.push("<input type='hidden' class='pcid' value='"+this[0]+"'>");
					forum.push("</li>");
				})
			}
			$(".huifu_").append(forum.join(""));
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
//删除二级回复
function del2(obj){
	var pc = $(obj).parents("li").find(".pcid").val();
	$("#queding").one("click", function() {
		var url = basePath + "ea/companyforum/sajax_ea_delReply.jspa?";
		$.ajax({
			url : encodeURI(url),
			type : "post",
			async : true,
			dataType : "json",
			data : {
				"pct.pcID":pc,
			},
			success : function(data) {
				var judge = eval("(" + data + ")");
				if(judge){
					$(".huifu_").empty();
					$(".alert_rem").hide();
					pageNumber = 0;
					prompt("删除成功");
					ajax();
				}else{
					prompt("删除失败");
				}
			}
		})
	})
}

//删除一级回复
function del1(obj){
	var pcid = $(obj).parents(".huifu").find(".pcid").val();
	$("#queding").one("click", function() {
		var url = basePath + "ea/companyforum/sajax_ea_delMyReply.jspa?";
		$.ajax({
			url : encodeURI(url),
			type : "post",
			async : true,
			dataType : "json",
			data : {
				"pct.pcID":pcid
			},
			success : function(data) {
				var judge = eval("(" + data + ")");
				if(judge){
					prompt("删除成功");
					document.location.href = basePath+"ea/companyforum/ea_forumMessage.jspa?concom.ccompanyID="+ccompanyid+"&commonEssence=02";
				}else{
					prompt("删除失败");
				}
			}
		})
	})
}
//发表
function publish(obj){
	var pcid = $(obj).parents(".hf_ipt").find(".pcid").val();
	var txt = $(obj).parents(".hf_ipt").find(".txt").val();
	var name = $(".huifu_top").find("h4").text();
	var url = basePath + "ea/companyforum/sajax_ea_userReply.jspa?";
	if($.trim(txt)!=""){
		$.ajax({
			url : encodeURI(url),
			type : "post",
			async : true,
			dataType : "json",
			data : {
				"pct.pcID":pcid,
				"pct.content":txt
			},
			success : function(data) {
				var userReply = eval("(" + data + ")");
				if(userReply.user){
					if(userReply.userReply){
						$(".huifu_").empty();
						pageNumber = 0;
						prompt("添加成功");
						$(obj).parents(".hf_ipt").find(".txt").val("");
						ajax();
					}else{
						prompt("添加失败");
					}
				}else{
					document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";
				}
			}
		})
	}else{
		prompt("请输入回复内容");
	}
}

function getNowFormatDate() {
	var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.month + 1;
    var strDate = date.date;
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.hours + seperator2 + date.minutes
            + seperator2 + date.seconds;
    return currentdate;
}