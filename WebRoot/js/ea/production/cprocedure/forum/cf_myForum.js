$(document).ready(function() {
	ajax();
	$(".invitation_no").css("height", $(".content").height() + "px");

	$(document).on("click", ".attention", function() {
		var t = $(".attention").find("p").text();
		var b;
		if(t=="已关注"){
			b = "确认添加关注吗?";
		}else{
			b = "确认取消关注吗?";
		}
		if(confirm(b)){
			var url = basePath + "ea/companyforum/sajax_ea_shift.jspa?";
			$.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"ccom.sccId" : sccid,
				},
				success : function(data) {

				}
			})
		}
	})
})

function getHeight() {
	t = setTimeout("getHeight()", 200);
	if ($(".last").offset().top < $(window).height()) {
		if (pageNumber < pageCount) {
			ajax();
		}
	}
}

function ajax() {
	var url = basePath + "ea/companyforum/sajax_ea_myiNvitation.jspa?";
	$
			.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"pageForm.pageNumber" : parseInt(pageNumber) + 1,
					"pageForm.pageSize" : 5,
					"ccom.sccId" : sccid,
					"community" : community
				},
				success : function(data) {
					var myiNvitation = eval("(" + data + ")");
					var pageForm = myiNvitation.pageForm;
					$(".last").removeClass("last");
					var forum = [];
					if (pageForm != null && pageForm.list != null
							&& pageForm.list.length > 0) {
						$(pageForm.list)
								.each(
										function(i, dom) {
											if ($(pageForm.list).length - 1 == i) {
												forum
														.push("<li class='last' onclick='invitationParticulars(this)'>");
											} else {
												forum
														.push("<li onclick='invitationParticulars(this)'>");
											}
											var t = this[3].split(".")[0]
													+ "small."
													+ this[3].split(".")[1];
											forum
													.push("<img src='"+ basePath+ t + "' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>");
											forum
													.push("<input type='hidden' class='ppid' value='"
															+ this[0] + "'>");
											forum
													.push("<input type='hidden' class='goodsid' value='"
															+ this[1] + "'>");
											forum.push("<div class='my_txt'>");
											forum.push("<h4>" + this[2]
													+ "</h4>");
											forum.push("<p>评论<span>" + this[5]
													+ "</span>");
											forum.push(" · 点赞<span>" + this[6]
													+ "</span> · ");
											forum.push("收藏<span>" + this[7]
													+ "</span></p>");
											forum.push("</div></li>");
										})
					} else {
						forum.push("<div class='invitation_no'>");
						forum
								.push("<img src='"
										+ basePath
										+ "/images/ea/production/forum/no2.png' alt='' class='no_img'>");
						forum.push("<p class='no_txt'>还没有帖子哦~发一个吧</p>");
						forum.push("</div>");
					}
					$(".my_mil").append(forum.join(""));
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

function forum(obj) {
	document.location.href = basePath
			+ "ea/companyforum/ea_forumMessage.jspa?concom.ccompanyID=" + obj;
}
// 查询我的帖子详情
function invitationParticulars(obj) {
	var ppid = $(obj).find(".ppid").val();
	var goodsid = $(obj).find(".goodsid").val();
	document.location.href = basePath
			+ "ea/qrshare/ea_qrshareDetail.jspa?productPackaging.ppID=" + ppid
			+ "&productPackaging.goodsID=" + goodsid
			+ "&miniSystemJudge=04&certificate.ccompanyID=" + ccompanyid;
}
// 上传图片
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
			if (user.userExist) {
				wsccid = user.cuscom.sccId;
				staffid = user.cuscom.staffid;
				attition();
			} else {
				document.location.href = basePath
						+ "page/WFJClient/NewLogin.jsp?loginPage=login";
			}
		}
	});
}
function attition() {
	var u = window.navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; // android终端
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
	if (isAndroid == true) {
		try {
			Android.callPictures();
		} catch (e) {
			uploading();
		}
	} else if (isiOS == true) {
		uploading();
	}
}

function uploading() {
	var srcs = "";
	// 上传图片
	$("#imgs").click();
	$("#imgs")[0].onchange = function() {
		var maxsize = 3000 * 1024;
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
			if (file.size <= maxsize && file.size >= minsize) {
				srcs += addimg(file) + ",";
			} else {
				if (file.size > maxsize) {
					prompt("上传图片过大,请上传标准格式图片");
				} else if (file.size < minsize) {
					prompt("上传图片过小,请上传标准格式图片");
				}
			}
		})
		srcs = srcs.substring(0, srcs.length - 1);
		if (srcs != "") {
			var url = basePath
					+ "ea/qrshare/ea_qrshareEdit.jspa?skipJudge=00&pictureName="
					+ srcs + "&miniSystemJudge=04&productPackaging.sccid="
					+ wsccid + "&productPackaging.companyID=" + companyid
					+ "&certificate.ccompanyID=" + ccompanyid;

			document.location.href = url;
		}
	}
}
// 安卓回调
function syntony(srcs) {
	var url1 = basePath + "ea/qrshare/sajax_ea_androidScreenshot.jspa?";
	$.ajax({
		url : url1,
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"pictureName" : srcs
		},
		success : function(data) {
			console.log("缩略图截取成功")
		}
	});
	var url = basePath
			+ "ea/qrshare/ea_qrshareEdit.jspa?skipJudge=00&pictureName=" + srcs
			+ "&miniSystemJudge=04&productPackaging.sccid=" + wsccid
			+ "&productPackaging.companyID=" + companyid
			+ "&certificate.ccompanyID=" + ccompanyid;

	document.location.href = url;
}

function addimg(file) {
	var src = "";
	var formdata = new FormData();
	formdata.append("picture", file);
	formdata.append("pictureName", file.name);
	formdata.append("cuscom.staffid", staffid);
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
			$("#loading").hide();
		}
	});
	return src;
}

function backtrack(){
	var url = getCookie("url");
	if(url.length>0){
		document.location.href = url;
	}else{
		if(ccompanyid!=null && ccompanyid.length>0){
			document.location.href = basePath+"ea/companyforum/ea_forumMessage.jspa?concom.ccompanyID="+ccompanyid;
		}else{
			document.location.href = basePath+"/ea/wfjshop/ea_getWFJshops.jspa";
		}
	}
}

//获取cookie  
function getCookie(cname) {  
    var name = cname + "=";  
    var ca = document.cookie.split(';');  
    for(var i=0; i<ca.length; i++) {  
        var c = ca[i];  
        while (c.charAt(0)==' ') c = c.substring(1);  
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);  
    }  
    return "";  
}
