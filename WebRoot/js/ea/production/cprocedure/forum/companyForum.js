$(document).ready(function() {
	//提示框    
	$("#prompt").css("position","absolute").css("top",$(window).height()*0.09+"px");
	$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
	$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
	$("header").css("height",$(window).height()*0.08-1+"px");
    $("header").css("line-height",$(window).height()*0.08-1+"px");
    $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
    $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px;position: relative;");
    $(".head_top").css("height",$(window).height()*0.08-1+"px");
    $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
    $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
    $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");
    /* $(".con").css("height",$(window).height()*0.828+"px");*/
    $(".con_pl").css("height",$(window).height()*0.86-2+"px");
    $(".btn_jia").attr("style","height:"+$(window).height()*0.06+"px");
    $(".mil_txt .mil img").css("height",$(".mil_txt .mil img").width()+"px");
    $(".lt_top").css("height",$(window).height()*0.12+"px");
    $("#menu_con .swiper-slide").css("height",$(".con_pl").height()-$(".lt_lie").height()+1+"px");
    
    
    
    /*限制显示1个图片*/
    $(".mil_txt_img img:gt(0)").hide();
    /*限制显示3个图片*/
    $(".mil_txt .mil img:gt(2)").hide();

    var tabsSwiper = new Swiper('#tabs-container',{
        speed:500,
        onSlideChangeStart: function(){
            $(".tabs .active").removeClass('active');
            $(".tabs a").eq(tabsSwiper.activeIndex).addClass('active')
        }
    });
    $(".tabs li").on('touchstart mousedown',function(e){
        e.preventDefault();
        $(".tabs .active").removeClass('active');
        $(this).addClass('active');
        tabsSwiper.slideTo( $(this).index() );
        $(".content-slide").find("ul").empty();
        switch ($(this).find("a").html()) {
    		case "帖子":
    			judge = "00";
    			pageNumber = 0;
    			ajax(judge);
    			break;
    		case "精华":
    			judge = "01";
    			pageNumber = 0;
    			ajax(judge);
    			break;
    		case "回复":
    			judge = "02";
    			pageNumber = 0;
    			ajax1();
    			$(".invitation_no").css("height",$("#menu_con .swiper-slide").height()+"px");
    			break;

    		default:
    			break;
    	}
        
        	/*2017.2.4*/
            $(".invitation_no").css("height",$("#menu_con .swiper-slide").height()+"px");
      
    });
    $(document).on("click",".tabs li",function(){
        e.preventDefault()
        
    });


    
    $(".alert_txt textarea").on("input",function(){
        var t = $(".alert_txt textarea").val();
        if(t == ""){
            $(".alert_txt #fb").removeClass("have");
        }else{
            $(".alert_txt #fb").addClass("have");
        }
    });
    $(document).on("click",".alert_txt #qx2",function(){
        $(".alert_txt_").hide();
        $(".alert_txt").hide();
        $(".alert_txt textarea").val("");
        $(".have").removeClass("have");
    });
    $(document).on("click",".alert_txt_",function(){
        $(".alert_txt_").hide();
        $(".alert_txt").hide();
        $(".alert_txt textarea").val("");
    });
	var topMain=$(".lt_top").height(); //是头部的高度加头部与nav导航之间的距离

    $(".con_pl").scroll(function(){
        console.log(1);
        if ($(".con_pl").scrollTop()>topMain){

            $("#menu_con .swiper-slide").addClass("swiper-slide_");
        }else{
            $("#menu_con .swiper-slide").removeClass("swiper-slide_");
        }
    });
	
	$(document).on("click",".att",function(){
		var b;
		if($(".att").val()=="加关注"){
			b = "确认添加关注吗?"; 
		}else{
			b = "确认取消关注吗?";
		}
		if(confirm(b)){
			var url = basePath + "ea/companyforum/sajax_ea_follow.jspa?";
			$.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"companyid" : companyid,
				},
				success : function(data) {
					var attention = eval("(" + data + ")");
					if(attention.userExist==false){
						document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";
					}else{
						if($(".att").val()=="加关注"){
							$(".att").val("取消关注");
							var t = parseInt($(".txt").find("span")[1].firstChild.data)+1;
							$(".txt").find("span")[1].innerHTML= t;
						}else{
							$(".att").val("加关注");
							var t = parseInt($(".txt").find("span")[1].firstChild.data)-1;
							$(".txt").find("span")[1].innerHTML= t;
						}
					}
				}
			})
		}
	})
	if(commonEssence=='02'){
		$(".tabs li").find(".common").toggleClass("active");
		$(".tabs li").find(".reply").toggleClass("active");
		judge = "02";
    	pageNumber = 0;
    	ajax1();
    	$(".invitation_no").css("height",$("#menu_con .swiper-slide").height()+"px");
	}else{
		ajax(judge);
	}
});

function getHeight() {
	t = setTimeout("getHeight()", 200);
	if($(".last").length > 0){
		if ($(".last").offset().top < $(window).height()) {
			if(pageNumber<pageCount){
				if(judge=="00" || judge=="01"){
					ajax(judge);
				}else if(judge=="02"){
					ajax1();
				}
			}
		}
	}
}

function ajax(obj) {
	var url = basePath + "ea/companyforum/sajax_ea_invitationList.jspa?";
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"pageForm.pageNumber" : parseInt(pageNumber) + 1,
			"pageForm.pageSize" : 5,
			"companyid" : companyid,
			"commonEssence" : obj
		},
		success : function(data) {
			var invitation = eval("(" + data + ")");
			var pageForm = invitation.pageForm;
			var imgList = invitation.imgList;
			var textList = invitation.textList;
			$(".last").removeClass("last");
			var forum = [];
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				$(pageForm.list).each(function(i, dom) {
					if ($(pageForm.list).length - 1 == i) {
						forum.push("<li class='last' onclick='invitationParticulars(this)'>");
					} else {
						forum.push("<li onclick='invitationParticulars(this)'>");
					}
					forum.push("<input type='hidden' class='ppid' value='"+this[0]+"'>");
					forum.push("<input type='hidden' class='sccid' value='"+this[6]+"'>");

					forum.push("<div class='mil_top'>");
					forum.push("<div class='head' onclick='fans(this);event.stopPropagation();'><img src='"+basePath+(this[2]==null?'/images/WFJClient/PersonalJoining/headimage.png':this[2])+"'></div>");
					forum.push("<p><span class='name' onclick='fans(this);event.stopPropagation();'>"+this[1]+"</span><span>"+this[5]+"</span></p>");
					forum.push("</div>");
					forum.push("<div class='mil_txt'>");
					forum.push("<h3>"+this[3]+"</h3>");
					if(textList[i]!=null){
						forum.push("<p limit='46'>"+textList[i]+"</p>");
					}
					if(imgList[i]!=null){
						var img = imgList[i].split(",");
						if(img.length>1){
							forum.push("<div class='mil'>");
							forum.push("<img src='"+basePath+img[0]+"' alt='' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>");
							forum.push("<img src='"+basePath+img[1]+"' alt='' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>");
							if(img.length>2){
								forum.push("<img src='"+basePath+img[2]+"' alt='' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>");
							}
						}else{
							forum.push("<div class='mil'>");
							forum.push("<img src='"+basePath+img[0]+"' alt='' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>");
						}
						forum.push("</div>");
					}
					forum.push("</div>");
					forum.push("<div class='mil_com'>");
					forum.push("<p>评论<span>"+this[7]+"</span></p>");
					forum.push("<p>点赞<span>"+this[8]+"</span></p>");
					forum.push("<p>收藏<span>"+this[9]+"</span></p>");
					forum.push("</div>");
					forum.push("</li>");
				})
			}else{
				forum.push("<div class='invitation_no'>");
				forum.push("<img src='"+basePath+"/images/ea/production/forum/no2.png' alt='' class='no_img'>");
				forum.push("<p class='no_txt'>还没有帖子哦~发一个吧</p>");
				forum.push("</div>");
			}
			$(".swiper-slide-active").find("ul").append(forum.join(""));
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

function ajax1(){
	var url = basePath + "ea/companyforum/sajax_ea_reply.jspa?";
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"pageForm.pageNumber" : parseInt(pageNumber) + 1,
			"pageForm.pageSize" : 5
		},
		success : function(data) {
			var reply = eval("(" + data + ")");
			if(reply.replyJudge==false){
				document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";
			}else{
				var pageForm = reply.pageForm;
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
						forum.push("<div class='mil_top'>");
						forum.push("<div class='head' onclick='fans(this);event.stopPropagation();'><img src='"+basePath+(this[2]==null?'/images/WFJClient/PersonalJoining/headimage.png':this[2])+"'></div>");
						forum.push("<p><span class='name' onclick='fans(this);event.stopPropagation();'>"+this[3]+"</span><span>"+this[4]+"</span></p>");
						forum.push("<a href='#;'><input type='button' value='回复' class='huifu' onclick='huifu(this)'></a>");
						forum.push("</div>");
						forum.push("<a href='javascript:void(0)' onclick='examineReply(this)'>");
						forum.push("<input type='hidden' class='ppid' value='"+this[0]+"'>");
						forum.push("<input type='hidden' class='pcid' value='"+this[6]+"'>");
						forum.push("<input type='hidden' class='goodsid' value='"+this[7]+"'>");
						forum.push("<input type='hidden' class='sccid' value='"+this[8]+"'>");
						forum.push("<div class='mil_txt mml'>");
						forum.push("<p><span style='color:#4b8ca9'>回复您:</span><span>"+this[5]+"</span></p>");
						forum.push("</div>");
						forum.push("</a>");
						forum.push("<!--论坛标题-->");
						forum.push("<div class='mil_txt mil_txt2'>");
						forum.push("<h5></h5>");
						forum.push("<p>原帖：<span>"+this[1]+"</span></p>");
						forum.push("</div>");
						forum.push("</li>");
					})
				}else{
					forum.push("<div class='invitation_no'>");
					forum.push("<img src='"+basePath+"/images/ea/production/forum/no1.png' alt='' class='no_img'>");
					forum.push("<p class='no_txt'>还没有人回复你哦~</p>");
					forum.push("</div>");
				}
				$(".swiper-slide-active").find("ul").append(forum.join(""));
				if (pageForm != null) {
					pageNumber = pageForm.pageNumber;
					pageCount = pageForm.pageCount;
					if (pageNumber < pageCount) {
						getHeight();
					}
				}
			}
		}
	})
}
//查询所有回复
function examineReply(obj){
	var pcid = $(obj).parents("li").find(".pcid").val();
	document.location.href = basePath+"ea/companyforum/ea_replyParticulars.jspa?pct.pcID="+pcid+"&concom.ccompanyID="+ccompanyid;
}
//查询帖子详情
function invitationParticulars(obj){
	var ppid = $(obj).find(".ppid").val();
	document.location.href = basePath+"ea/industry/ea_informationDetails.jspa?ppId="+ppid+"&ccompanyId="+ccompanyid+"&type=web&miniSystemJudge=04";
}

/*回复*/
function huifu(obj){
	var that = $(obj);
    $(".alert_txt_").show();
    $(".alert_txt").show();
    $(".alert_txt textarea").focus();
    var name = that.parents("li").find(".mil_top .name").text();
    $(".alert_txt textarea").attr('placeholder','回复'+name);
	var pcid = that.parents("li").find(".pcid").val();
	
	// 编辑文字全屏-保存
	$("#fb").on("click", function() {
		var textarea = $(".alert_txt").find("textarea").val();
		var url = basePath + "ea/companyforum/sajax_ea_userReply.jspa?";
		if($.trim(textarea)!="" && textarea!=null){
			$.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"pct.pcID":pcid,
					"pct.content":textarea,
				},
				success : function(data) {
					var userReply = eval("(" + data + ")");
					if(userReply.user){
						if(userReply.userReply){
							that.parents("li").find(".mml").append("<p>回复"+name+":<span>"+textarea+"</span></p>");
						}else{
							prompt("添加失败");
						}
						$(".alert_txt_").hide();
				        $(".alert_txt").hide();
				        $(".alert_txt textarea").val("");
				        $(".have").removeClass("have");
					}else{
						document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";
					}
				}
			})
		}
	})
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
//上传图片
function whetherRegister() {
	var url = basePath + "ea/companyforum/sajax_ea_whetherRegister.jspa?";
	$.ajax({
		url : url,
		type : "post",
		async : false,
		dataType : "json",
		data : {},
		success : function(data) {
			var user = eval("(" + data + ")");
			if(user.userExist){
				sccid=user.cuscom.sccId;
				attition(user.cuscom);
			}else{
				document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";
			}
		}
	});
}
function attition(user){
	var u = window.navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	if (isAndroid == true) {
		$("#loading").show();
		try {
			Android.callPictures();
		} catch(e) {
			uploading(user);
		}
	} else if (isiOS == true) { 
		uploading(user);
	}
}

function uploading(user){
	var srcs = "";
	// 上传图片
	$("#imgs").click();
	$("#imgs")[0].onchange = function() {
		var maxsize = 6000 * 2048;
		var minsize = 200 * 200;
		if (!this.files.length)
			return;
		var files = Array.prototype.slice.call(this.files);
		if (files.length > 9) {
			prompt("最多同时只可上传9张图片");
			return;
		}
		files.forEach(function(file, i) {
			if (!/\/(?:jpeg|png|gif|jpg)/i.test(file.type))
				return;
			if (file.size <= maxsize && file.size>=minsize) {
				srcs += addimg(file,user)+",";
			} else {
				if(file.size > maxsize){
					prompt("上传图片过大,请上传标准格式图片");
				}else if(file.size < minsize){
					prompt("上传图片过小,请上传标准格式图片");
				}
			}
		})
		srcs = srcs.substring(0,srcs.length-1);
		if(srcs!=""){
			var url = basePath
			+ "ea/qrshare/ea_qrshareEdit.jspa?skipJudge=00&pictureName="+srcs+"&miniSystemJudge=04&productPackaging.sccid="+ sccid+"&productPackaging.companyID="+companyid+"&certificate.ccompanyID="+ccompanyid;
			
			document.location.href = url;
		}
	}
}
//安卓回调
function syntony(srcs){
	var url1 = basePath
	+ "ea/qrshare/sajax_ea_androidScreenshot.jspa?";
	$.ajax({
		url : url1,
		type : "post",
		async : true,
		dataType : "json",
		data : {"pictureName":srcs},
		success : function(data) {
			console.log("缩略图截取成功")
		}
	});
	var url = basePath
	+ "ea/qrshare/ea_qrshareEdit.jspa?skipJudge=00&pictureName="+srcs+"&miniSystemJudge=04&productPackaging.sccid="+ sccid+"&productPackaging.companyID="+companyid+"&certificate.ccompanyID="+ccompanyid;
	
	document.location.href = url;
}

function addimg(file,user) {
	var src = "";
	var formdata = new FormData();
	formdata.append("picture", file);
	formdata.append("pictureName", file.name);
	formdata.append("cuscom.staffid", user.staffid);
	formdata.append("cuscom.companyId", companyid);
	var url = basePath + "ea/qrshare/sajax_ea_addMaterial.jspa?";
	$.ajax({
		url : url,
		type : "post",
		async : false,
		dataType : "json",
		data : formdata,
		processData : false,
		contentType : false,
		beforeSend : function() {
			$("#loading").show();
		},
		success : function(data) {
			var url = eval("(" + data + ")");
			src = url.sharepath;
		}
	});
	return src;
}

function myall(){
	document.location.href = basePath + "ea/companyforum/ea_myMessage.jspa?community=00";
}

function fans(obj){
	var ss = $(obj).parents("li").find(".sccid").val();
	document.location.href = basePath + "ea/companyforum/ea_myMessage.jspa?community=01&ccom.sccId="+ss;  
}
