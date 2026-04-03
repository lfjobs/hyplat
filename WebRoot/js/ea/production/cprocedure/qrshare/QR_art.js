/*选择在线图片*/

//改版:
// 添加项目-图文
function addImg(src) {
	var imgHTML = [];
		imgHTML.push('<div class="art_box art_img clearfix">');
		imgHTML.push('<i class="art_dele"></i>');
		imgHTML.push('<div class="art_text_wrap">');
		imgHTML.push('<textarea class="art_text" placeholder="点击添加描述文字"></textarea></div>');
		imgHTML.push('<div class="art_R"><img src="'+src+'"></div>');
		imgHTML.push('<div class="art_sort"><a href="javascript:;" class="set_up"></a>');
		imgHTML.push('<a href="javascript:;" class="set_down"></a></div></div>');
//	$(".art_con").append(imgHTML.join(""));
		$("#idss").contents().find(".art_con").append(imgHTML.join(""));
//	$(".art_box").last().find("textarea")[0].scrollIntoView();
//		$("#idss").contents().find(".art_box").last().find("textarea")[0].scrollIntoView();
		initArtSet();
}
// 上传图片
$("#up_img")[0].onchange = function() {
	$(".load").html("正在上传第1张图片")
	var maxsize = 6000 * 2048;
	var minsize = 200 * 200;
	if (!this.files.length){
		return ;
	}
	var files = Array.prototype.slice.call(this.files);
	if (files.length > 9) {
		alert("最多同时只可上传9张图片");
		return;
	}
	files.forEach(function(file, i) {
		if (!/\/(?:jpeg|png|gif|jpg)/i.test(file.type))
			return alert("上传格式不正确");
		if (file.size <= maxsize  && file.size>=minsize) {
			img_video(file, "img",i);
		} else {
			if(file.size > maxsize){
				alert("上传图片过大,请上传标准格式图片");
			}else if(file.size < minsize){
				alert("上传图片过小,请上传标准格式图片");
			}
		}
	})

}
$("#up_img").click(function(e){
	var u = window.navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	if (isAndroid == true) {
		console.log("安卓");
	//	$("#loading").show();
		try {
			Android.callPictures();//调用安卓接口
		} catch(e) {
			return true;
		}
		return false;
	} else if (isiOS == true) {
		console.log("IOS")
		return true;
	}else{
	//	alert("错误");
	}
})

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
	var ss = srcs.split(",");
	for ( var i = 0; i < ss.length; i++) {
		addImg(basePath+ss[i]);
	}
	$("#loading").hide();
}





// 添加项目-视频文字
function addVedio(src, vedio_src) {
	var vedioHTML = [];
	vedioHTML.push('<div class="art_box art_vedio clearfix">');
	vedioHTML.push('<i class="art_dele"></i>');
	vedioHTML.push('<div class="art_text_wrap">');
	vedioHTML.push('<textarea class="art_text" placeholder="点击添加描述文字"></textarea></div>');
	vedioHTML.push('<div class="art_R"><i></i><img src="'+src+'"  data-vediosrc="'+vedio_src+'"></div>');
	vedioHTML.push('<div class="art_sort"><a href="javascript:;" class="set_up"></a>');
	vedioHTML.push('<a href="javascript:;" class="set_down"></a></div></div>');
//	$(".art_con").append(vedioHTML.join(""));
//	$(".art_box").last().find("textarea")[0].scrollIntoView();
	$("#idss").contents().find(".art_con").append(vedioHTML.join(""));
	$("#idss").contents().find(".art_box").last().find("textarea")[0].scrollIntoView();
	initArtSet();
}
// 上传视频
$("#up_video")[0].onchange = function() {
	$(".load").html("正在上传第1条视频")
	var maxsize = 30000 * 1024;
	if (!this.files.length)
		return;
	var files = Array.prototype.slice.call(this.files);
	if (files.length > 5) {
		alert("最多同时只可上传5段视频");
		return;
	}
	files.forEach(function(file, i) {
		if (!/\/(?:mp4|3gp|avi|mov|quicktime)/i.test(file.type)){
			return alert("上传格式不正确");
		}
		// 如果视频大小小于maxsize，则直接上传
		if (file.size <= maxsize) {
			img_video(file, "video",i);
		} else {
			alert('请上传小于' + (~~(10 * maxsize / 1024 / 1024)) / 10 + 'M的文件')
		}
	})
}
function img_video(file, judge,i) {
	var formdata = new FormData();
	formdata.append("picture", file);
	formdata.append("pictureName", file.name);
	formdata.append("cuscom.staffid", staffid);
	formdata.append("cuscom.companyId", companyId);
	var url = basePath + "ea/qrshare/sajax_ea_addMaterial.jspa?";
	$.ajax({
		url : url,
		type : "post",
		async : true,
		dataType : "json",
		data : formdata,
		processData : false,
		contentType : false,
		beforeSend : function() {
			$("#loading").show();
		},
		success : function(data) {
			var url = eval("(" + data + ")");
			if (judge == "img") {
				var sharepath = url.sharepath;
				$(".load").html("正在上传第"+(i+1)+"张图片")
				addImg(basePath + sharepath);
			} else if (judge == "video") {
				var sharepath = url.sharepath;
				var imgpath = url.imgpath;
				$(".load").html("正在上传第"+(i+1)+"条视频")
				addVedio(basePath + imgpath, basePath + sharepath);
			}
			$("#loading").hide();
		}
	});
}

function delTransformVideo(pictureName) {
	if (pictureName != null && pictureName != "") {
		var url = basePath + "ea/qrshare/sajax_ea_delTransformVideo.jspa?";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data : {
				"pictureName" : pictureName
			},
			success : function(data) {
				console.log("删除成功");
			},
			error : function(data) {
				console.log("删除失败");
			}
		});
	}
}

// 删除已上传图片/视频
function delfile(img_src, video_src) {
	if(img_src!=""&&img_src!=undefined) {
		var img = img_src.split("upload_files");
		var pictureName = "upload_files" + img[1];
	}
	if (video_src != "") {
		var video = video_src.split("upload_files");
		pictureName += "," + "upload_files" + video[1];
	}
	var url = $("#delImgVideo").attr("data-url");
	if (url != "" && url != null) {
		url += ",";
	}
	url += pictureName;
	$("#delImgVideo").attr("data-url", url);

}

// 点击选择在线图片
/*$(".pic_con").on("click", ".pic_box", function() {
	var inp = $(this).find("input")[0];
	var ico = $(this).find("i");
	if (!inp.checked) {
		ico.addClass("pic_selected");
		inp.checked = true;
	} else {
		ico.removeClass();
		inp.checked = false;
	}
	var n = $(".pic_con input:checked").length;
	$(".pic_num").text(n);
	if (n > 0) {
		$(".pic_wrap .head_R").addClass("enable");
		$(".vedio_wrap .head_R").addClass("enable");
	} else {
		$(".pic_wrap .head_R").removeClass("enable");
		$(".vedio_wrap .head_R").removeClass("enable");
	}
})*/
// 选择图片-选择分组
/*$(".pic_menu_cur").click(function() {
	$(".pic_menu").slideToggle(300);
})*/

// 点击选择图片分组
/*$(document).on("click", ".pic_menu dd", function() {
	var t = $(this).find("span").first().text();
	var n = $(this).find("span").last().text();
	$(".pic_menu_cur").find("span").text(t);
	$(".pic_head h1").find("span").first().text(t);
	$(".pic_menu").slideUp(300);
	$(this).addClass("pic_group").siblings().removeClass();
	$(".pic_all").text(n);
	$(".pic_num").text(0);

	groupID = $(this).attr("groupid");
	groupName = $(this).find(".groupname").text();
	$(".piclist").html("");
	fileType = "00";
	pagenumber = 0;
	pagecount = 0;
	loaded();
})*/
// 选择视频-选择分组
/*$(".vedio_menu_cur").click(function() {
	$(".vedio_menu").slideToggle(300);
})*/
// 选择视频分组
/*$(document).on("click", ".vedio_menu dd", function() {
	var t = $(this).find("span").first().text();
	var n = $(this).find("span").last().text();
	$(".vedio_menu_cur").find("span").text(t);
	$(".vedio_wrap h1").find("span").first().text(t);
	$(".vedio_menu").slideUp(300);
	$(this).addClass("pic_group").siblings().removeClass();
	$(".pic_all").text(n);
	$(".pic_num").text(0);
	groupID = $(this).attr("groupid");
	$(".videolist").html("");
	fileType = "01";
	pagenumber = 0;
	loaded();

})*/
// 选择在线图片返回按钮
/*$(".pic_head .back").click(function() {
	imgInit();
})*/
// 选择在线视频返回按钮
/*$(".vedio_wrap .back").click(function() {
	vedioInit();
})*/
// 点击添加项目-视频
/*
 * $(".add_vedio").click(function (e) { e.stopPropagation();
 * $(".vedio_wrap").show(); $(".overlay").addClass("active");
 * //$(".art_add_menu").css("top", "100%"); findGroup("01"); fileType="01";
 * pagenumber=0; pagecount = 0; loaded(); })
 */
// 选择图片后点击完成
/*$(".pic_head .head_R").click(
		function() {
			var n = $(".pic_wrap").find("input:checked").length;
			if (n > 0) {
				$(".pic_wrap").find("input:checked").each(
						function() {
							var src = $(this).parent().find("img").attr("src");
							var ss = src.split(".");
							if (ss[ss.length - 2].indexOf("small") > 0) {
								ss[ss.length - 2] = ss[ss.length - 2]
										.substring(0,
												ss[ss.length - 2].length - 5);
								src = ss.join(".");
							}
							addImg(src);
						})
				imgInit()
			}
		})*/
// 选择视频后点击完成
/*
 * $(".vedio_wrap .head_R").click(function () { var n =
 * $(".vedio_wrap").find("input:checked").length; if (n > 0) {
 * $(".vedio_wrap").find("input:checked").each(function () { var src =
 * $(this).parent().find("img").attr("src"); var
 * vedio_src=$(this).parent().find("img").attr("data-vediosrc");
 * addVedio(src,vedio_src); }) vedioInit() } })
 */

// 初始化在线选择图片
/*function imgInit() {
	$(".pic_wrap").find("input:checked").each(function() {
		this.checked = false;
		$(this).prev().removeClass();
	})
	$(".pic_wrap").hide();
	$(".overlay").removeClass("active");
	$(".pic_menu").slideUp().empty();
	groupID = "";
	$(".pic_head").find("span").first().text("所有图片");
	$(".pic_menu_cur").find("span").text("所有图片");
	$(".pic_num").text('0');
	$(".pic_wrap .head_R").removeClass("enable");
	$(".vedio_wrap .head_R").removeClass("enable");
	$(".pic_con").empty();
}*/
// 初始化在线选择视频
/*function vedioInit() {
	$(".vedio_wrap").find("input:checked").each(function() {
		this.checked = false;
		$(this).prev().removeClass();
	})
	$(".vedio_wrap").hide();
	$(".overlay").removeClass("active");
	$(".vedio_menu").slideUp().empty();
	groupID = "";
	$(".vedio_head").find("span").first().text("所有视频");
	$(".vedio_menu_cur").find("span").text("所有视频");
	$(".pic_num").text('0');
	$(".pic_wrap .head_R").removeClass("enable");
	$(".vedio_wrap .head_R").removeClass("enable");
	$(".pic_con").empty();
}*/
// 初始化选择产品
function proInit() {
	$(".pro_wrap").find("input:checked").each(function() {
		this.checked = false;
		$(this).prev().removeClass();
	})
	$(".pro_wrap").hide();
	$(".overlay").removeClass("active");
	$(".pic_num").text('0');
	$(".pro_wrap .head_R").removeClass("enable");
	// xgb
	$(".pro_search").off("input");
	$(".pro_search").val("");
	$(".product").empty();
	$(".pro_search").on('input', function() {
		pagecount = 0;
		pageSize = 0;
		pagenumber = 0;
		$(".product").empty();
		inquireProduct($(".pro_search").val());
	});
}
proInit();
// 点击添加项目
// $(".qrw_add").click(function () {
// $(this).children(".art_add_menu").css("top", 0)
// });

// 删除段落
$(iframe_con).load(function(){
	//详情存放
	$("#idss").contents().find(".art_con").append($("#content").html());
	
	var iframe_con = window.frames['iframe_con'].document;
	$(iframe_con).on(
			"click",
			".art_dele",
			function() {
				var t = confirm("确定删除？");
				if (t) {
					var aa = $(this).parent()[0];

					var img_src = $(this).siblings(".art_R").find("img")
							.attr("src");
					var video_src = "";
					if (aa.classList.contains("art_vedio")) {
						video_src = $(this).siblings(".art_R").find("img").attr(
								"data-vediosrc");
						console.log(video_src);
					}
					delfile(img_src, video_src);
					$(this).parent().detach();
				}
			});
	// 切换上下位置
	$(iframe_con).on("click", ".set_up", function() {
		$(this).parents(".art_box").animate({
			top : "-5rem",
			opacity : 0
		}, 150, function() {
			$(this).insertBefore($(this).prev()).css({
				"top" : "0",
				"opacity" : "1"
			})
		}).prev().animate({
			top : "5rem",
			opacity : 0
		}, 150, function() {
			$(this).css({
				"top" : 0,
				opacity : 1
			});
			initArtSet()
		});
	})
	$(iframe_con).on("click", ".set_down", function() {
		$(this).parents(".art_box").animate({
			top : "5rem",
			opacity : 0
		}, 150, function() {
			$(this).insertAfter($(this).next()).css({
				"top" : "0",
				"opacity" : "1"
			})
		}).next().animate({
			top : "-5rem",
			opacity : 0
		}, 150, function() {
			$(this).css({
				"top" : "0",
				"opacity" : "1"
			});
			initArtSet()
		});
	})
	// 编辑文字全屏
	$(iframe_con).on("click", ".art_text", function() {
		var that = $(this);
		var text = that.val();
		$("#text_val").val(text);
		$(".overlay").addClass("active").find(".textarea_inp").show();
		var box = $(this).parents(".art_box")[0];
		if (!box.classList.contains("art_txt")) {
			$("#text_val").attr("placeholder", "请输入描述文字")
		}
		// 编辑文字全屏-保存
		$(".textarea_inp .head_R").one("click", function() {
			var t_val = $("#text_val").val();
			that.val(t_val);
			textInit();
		})
		$("#text_val").focus();
	})
	// 编辑文字全屏-返回按钮
	$(".textarea_inp .back").click(function() {
		textInit();
	})
	
	// 编辑页面，添加单个产品删除
	$(iframe_con).on("click", ".pro_del", function() {
		var parents = $(this).parents(".pro_box");
		$(this).parent().remove();
		var n = parents.find(".pro").length;
		if (n == 0) {
			parents.parent().detach();
		}
	})
})


// 初始化编辑器排序按钮
function initArtSet() {
//	$(".art_box").not(".art_music").find(".set_up").show().end().first().find(".set_up").hide();
//	$(".art_box").find(".set_down").show().end().last().find(".set_down").hide();
	$("#idss").contents().find(".art_box").not(".art_music").not(".art_cate").find(".set_up").show().end().first().find(".set_up").hide();
	$("#idss").contents().find(".art_box").find(".set_down").show().end().last().find(".set_down").hide();
}

initArtSet();



// 添加项目-产品
function addPro(prolist) {
	var proHTML = '<div class="art_box art_pro clearfix"><i class="art_dele"></i><div class="pro_box clearfix">'
			+ prolist
			+ '</div><div class="art_sort"><a href="javascript:;" class="set_up"></a><a href="javascript:;" class="set_down"></a></div></div>';
//	$(".art_con").append(proHTML);
//	$(".art_box").last().find(".pro").last()[0].scrollIntoView();
	$("#idss").contents().find(".art_con").append(proHTML);
	$("#idss").contents().find(".art_box").last().find(".pro").last()[0].scrollIntoView();
	initArtSet();
}
// 点击图片
/*
 * $(".add_img").click(function (e) { e.stopPropagation();
 * $(".pic_wrap").show(); $(".overlay").addClass("active");
 * //$(".art_add_menu").css("top", "100%"); findGroup("00"); fileType="00";
 * pagenumber=0; loaded(); })
 */
// 点击添加项目-产品
$(".add_pro").click(function(e) {
	e.stopPropagation();
	$(".pro_wrap").show();
	$(".overlay").addClass("active");
	// xgb
	pagecount = 0;
	pageSize = 0;
	pagenumber = 0;
	inquireProduct($(".pro_search").val());
})
// 点击选择产品
$(".pro_con").on("click", ".pro_sbox_wrap", function() {
	var inp = $(this).find("input")[0];
	var ico = $(this).find("i").first();
	if (!inp.checked) {
		ico.addClass("pro_selected");
		inp.checked = true;
	} else {
		ico.removeClass();
		inp.checked = false;
	}
	var n = $(".pro_wrap").find("input:checked").length;
	if (n > 0) {
		$(".pro_wrap .head_R").addClass("enable");
	} else {
		$(".pro_wrap .head_R").removeClass("enable");
	}
})
// 点击选择产品返回
$(".pro_wrap .back").click(function() {
	proInit();
})
// 点击选择产品完成
$(".pro_wrap .head_R").click(
		function() {
			var n = $(".pro_wrap").find("input:checked").length;
			if (n > 0) {
				var pro = '';
				$(".pro_wrap").find("input:checked").each(
						function() {
							var $box = $(this).parent().find(".pro_sbox");
							var p_html = $box.html();
							var a_href = $box.attr("data-href");
							pro += '<div class="pro" data-href="' + a_href
									+ '">' + p_html + '</div>';
						})
				addPro(pro);
				proInit();
			}
		})

// 初始化文本全屏输入
function textInit() {
	$(".overlay").removeClass("active").find(".textarea_inp").hide().find(
			"textarea").val('').attr("placeholder", "请输入文章详情");
	$(".textarea_inp .head_R").unbind("click");
}

// 保存，格式化编辑器段落
$("#submit")
		.click(
				function() {
					var $html = $(".submit_html");
					var num_index = 0;
					var  fimage = "";
					$("#idss").contents().find(".art_box")
							.each(
									function() {
                                        num_index++;
										var t = $(this).find("textarea").val()
										if(t!=null){
											t = t.replace(new RegExp(/\n/g),"<br/>");
											t = t.replace(new RegExp(/\s/g),"&nbsp;");
										}
										var src = $(this).find("img").attr("src");
									if(src!=undefined&&src!=""){
										if(num_index<=3){
                                            var srcstr = src.substring(src
                                                .indexOf("/upload_files"), src.length);
                                            fimage+=srcstr+",";

										}
									}

										var vedio_src = $(this).find("img")
												.attr("data-vediosrc");
										var audio_src = $(this)
												.attr("data-url");
										var audio_tit = $(this).text();
										var _html = '';

										if (this.classList
												.contains("art_music")) {
											if(audio_src.length>0){
												_html = '<audio  class="article_p article_audio" src="" data-hash="'
													+ audio_src
													+ '" data-name="'
													+ audio_tit
													+ '" loop="loop"></audio>';
											}
										} else if (this.classList
												.contains("art_img")) {
											_html = '<div class="article_p article_img" style="margin:10px auto 20px"><p>'
													+ t
													+ '</p><img style="text-align:center;max-width:100%;display:block;margin:10px auto 0" src='
													+ src + ' alt=""/></div>';
										} else if (this.classList
												.contains("art_vedio")) {
											_html = '<div class="article_p article_vedio"  style="margin:10px auto 20px"><p>'
													+ t
													+ '</p><video style="width:100%;display:block;margin:10px auto 0; max-height:320px;background:#000" src='
													+ vedio_src
													+ ' controls="controls" poster='
													+ src
													+ ' preload="meta"></video>';
										} else if (this.classList
												.contains("art_pro")) {
											var pro = '';
											$(this)
													.find(".pro")
													.each(
															function() {
																var pro_html = $(
																		this)
																		.html();
																var href = $(
																		this)
																		.attr(
																				"data-href");
																pro += '<a href="'
																		+ href
																		+ '" class="pro">'
																		+ pro_html
																		+ '</a>'
															})
											_html = '<div class="article_p article_pro clearfix">'+ pro + '</div>';
													
										} else if (this.classList
												.contains("art_time")) {
											var text = $(this).find(".art_input").val()
											_html = '<div class="article_p article-time"><label>活动时间：</label><span>'+text+'</span></div>';

										}else if (this.classList
												.contains("art_site")) {
											var text = $(this).find(".art_input").val()
											_html = '<div class="article_p article-site"><label>活动地址：</label><span>'+text+'</span></div>';

										}else if (this.classList
												.contains("art_url")) {
											var text = $(this).find(".art_p").text()
											var urls = $(this).find(".art_p").attr("data-url");
											_html = '<div class="article_p article-url"><a href="javascript:openUrl(\''+encodeURI(urls)+'\',\''+text+'\');"><p data-url="'+urls+'" class="url-p">'+text+'</p></a></div>';

										} else if (this.classList
												.contains("art_cate")) {

											var cate = $.trim($(this).text());

											$("#categoryName").val(cate);

										}
										$html.append(_html);
									})
					var more_pro = '<a href="'
							+ basePath
							+ 'ea/digitalmall/ea_DigitalMall.jspa?" class="more_pro">点击查看更多产品</a>';
					$html.find(".pro").last().parent().after(more_pro)
					$("#submit_val").val($html.html());

					if(fimage!=""){
						$("#fimage").val(fimage.substring(0,fimage.length-1));
					}
					$html.empty();
				})
// 添加项目-纯文本
/*
 * function addText() { var textHTML = '<div class="art_box art_txt clearfix">' + '<i
 * class="art_dele"></i>' + '<div class="art_text_wrap art_text_full">' + '<textarea
 * class="art_text" placeholder="点击添加描述文字"></textarea></div>' + '<div
 * class="art_sort"><a href="javascript:;" class="set_up"></a>' + '<a
 * href="javascript:;" class="set_down"></a></div></div>';
 * $(".art_con").append(textHTML);
 * $(".art_box").last().find("textarea")[0].scrollIntoView();
 * //$(".art_add_menu").css("top", "100%"); initArtSet(); }
 */
// 添加项目-图文
/*
 * function addImg(src) { var imgHTML = '<div class="art_box art_img
 * clearfix">' + '<i class="art_dele"></i>' + '<div class="art_text_wrap">' + '<textarea
 * class="art_text" placeholder="点击添加描述文字"></textarea></div>' + '<div
 * class="art_R"><img src="' + src + '" alt=""></div>' + '<div
 * class="art_sort"><a href="javascript:;" class="set_up"></a>' + '<a
 * href="javascript:;" class="set_down"></a></div></div>';
 * $(".art_con").append(imgHTML);
 * $(".art_box").last().find("textarea")[0].scrollIntoView();
 * 
 * initArtSet(); }
 */
// 添加项目-视频文字
/*
 * function addVedio(src,vedio_src) { var vedioHTML = '<div class="art_box
 * art_vedio clearfix">' + '<i class="art_dele"></i>' + '<div
 * class="art_text_wrap">' + '<textarea class="art_text"
 * placeholder="点击添加描述文字"></textarea></div>' + '<div class="art_R"><i></i><img
 * src="' + src + '" alt="" data-vediosrc="'+vedio_src+'"></div>' + '<div
 * class="art_sort"><a href="javascript:;" class="set_up"></a>' + '<a
 * href="javascript:;" class="set_down"></a></div></div>';
 * $(".art_con").append(vedioHTML);
 * $(".art_box").last().find("textarea")[0].scrollIntoView(); initArtSet(); }
 */
$(".add_text").click(function(e) {
	e.stopPropagation();
	addText();
});
/* 新增 */
// 点击选择音乐
$(".add_music").click(function() {
	$(".music_wrap").show();
	$(".overlay").addClass("active");
	defaultMusic();
})

//添加外部链接
$(".add_url").click(function() {
	$(".url_div1").show();
	$(".overlay").addClass("active");
	$("#wz-text").val("");
	$("#text_url").val("");

})

//添加时间
$(".add_time").click(function() {
	$(".time_div1").show();
	$(".overlay").addClass("active");
	$("#hdtime").val("");

})


//添加地址
$(".add_site").click(function() {
	$(".site_div1").show();
	$(".overlay").addClass("active");
	$("#text_site").val("");


})
//选择资讯分类
$(".add_cate").click(function() {
	$(".zxcate-div").show();
	$(".overlay").addClass("active");

})
// 点击链接返回
$(".url_div1 .back").click(function() {
	$(".url_div1").hide();
	$(".overlay").removeClass("active");
})

// 点击时间返回
$(".time_div1 .back").click(function() {
	$(".time_div1").hide();
	$(".overlay").removeClass("active");
})

// 点击地址返回
$(".site_div1 .back").click(function() {
	$(".site_div1").hide();
	$(".overlay").removeClass("active");
})
// 点击选择时间完成
$(".time_div1 .head_R").click(
	function() {
		var time = $("#hdtime").val();
		if(time==""){
			return false;
		}
		$(".time_div1").hide();
		$(".overlay").removeClass("active");
		addTime(time);
	})

// 点击选择地点完成
$(".site_div1 .head_R").click(
	function() {
		var site = $("#text_site").val();
		if(site==""){
			return false;
		}
		$(".site_div1").hide();
		$(".overlay").removeClass("active");
		addSite(site);
	})
// 点击选择URL完成
$(".url_div1 .head_R").click(
	function() {

		var text = $("#wz-text").val();

		var url = $("#text_url").val();

		if(text==""||url==""){
			return false;
		}

		if(!CheckUrl(url)){
			alert("请输入正确的链接");
			return false;
		}
		$(".url_div1").hide();
		$(".overlay").removeClass("active");
		addURL(text,url);
	})

// 打开链接返回
$(".url_open1 .back").click(
	function() {
		$(".url_open1").hide();
		$(".overlay").removeClass("active");

	})
//返回资讯分类
$(".zxcate-div .back").click(function() {
	$(".zxcate-div").hide();
	$(".overlay").removeClass("active");
})
//资讯分类完成
$(".zxcate-div .head_R").click(function() {
	if ($(".zxcate-div .select_ico").length > 0) {
		var par = $(".select_ico").parent(".cate_box").text();

		addCate(par);

		$(".overlay").removeClass("active");
		$(".zxcate-div").hide();
	}
})
/*// 点击选择背景音乐  ----因音乐接口失效,故暂时注销该方法
$(document).on("click", ".music_box", function() {
	$(".music_box").each(function() {
		$(this).find("i").removeClass();
	})
	$(this).find("i").addClass("select_ico");
	$(".no_music").find("i").removeClass();
	var url = musicPath($(this).attr("data-music"));
	while (url == null) {
		url = musicPath($(this).attr("data-music"));
	}
	$("#edit_audio")[0].src = url;
	$("#edit_audio")[0].play();
})*/

// 点击选择背景音乐  ----因音乐接口失效,故暂时使用该方法
$(document).on("click", ".music_box", function() {
	$(".music_box").each(function() {
		$(this).find("i").removeClass();
	})
	$(this).find("i").addClass("select_ico");
	$(".no_music").find("i").removeClass();
	var url = basePath + $(this).attr("data-music");
	$("#edit_audio")[0].src = url;
	$("#edit_audio")[0].play();
})


// 选择分类
$(document).on("click", ".cate_box", function() {
	$(".cate_box").each(function() {
		$(this).find("i").removeClass();
	})
	$(this).find("i").addClass("select_ico");

})
//验证URL是否合法
function CheckUrl(url){
	var reg=/http(s)?:\/\/(([A-Za-z0-9]+-[A-Za-z0-9]+|[A-Za-z0-9]+)\.)+([A-Za-z]{2,6})(:\d+)?(\/.*)?(\?.*)?(#.*)?$/g;
	return reg.test(url);
}

// 添加编辑页面音乐方法
function addMusic(tit, hash) {
	var n = $("#idss").contents().find(".art_con").find(".art_music").length;
	if (n > 0) {
		$("#idss").contents().find(".art_music").attr("data-url", hash).text(tit);
	} else {
		var t = '<div class="art_box art_music" data-url="' + hash + '">' + tit
				+ '</div>'
		$("#idss").contents().find(".art_con").prepend(t);
	}
	$("#edit_audio")[0].pause();
	$("#edit_audio")[0].src = '';
}
//删除音乐
$(".no_music").click(function() {
	$("#idss").contents().find(".art_music").remove();
	$(".overlay").removeClass("active");
	$(".music_wrap").hide();
	$(this).find("i").addClass("select_ico");
	$(".music_box").each(function() {
		$(this).find("i").removeClass();
	})
	$(".online_music").empty();
	$(".built_music").empty();
	$("#edit_audio")[0].pause();
	$("#edit_audio")[0].src = '';
})
$(".music_wrap .back").click(function() {
	musicInit();
	$("#edit_audio")[0].pause();
	$("#edit_audio")[0].src = '';
	$(".online_music").empty();
	$(".built_music").empty();
})
//因音乐接口失效暂时注销该方法
/*$(".music_wrap .head_R").click(function() {
	if ($(".music_wrap .select_ico").length > 0) {
		var par = $(".select_ico").parent(".music_box");
		var hash = par.attr("data-music");
		var tit = par.text();
		addMusic(tit, hash);
		$(".overlay").removeClass("active");
		$(".music_wrap").hide();
		$(".online_music").empty();
		$(".built_music").empty();
		var Record = tit.split("_");
		addRecord(Record[0], Record[1], hash);
	}
})*/
//因音乐接口失效暂时注销该方法
$(".music_wrap .head_R").click(function() {
	if ($(".music_wrap .select_ico").length > 0) {
		var par = $(".select_ico").parent(".music_box");
		var hash = par.attr("data-music");
		var tit = par.text();
		addMusic(tit, basePath+hash);
		$(".overlay").removeClass("active");
		$(".music_wrap").hide();
		$(".online_music").empty();
		$(".built_music").empty();
		var Record = tit.split("_");
		addRecord(Record[0], Record[1], hash);
	}
})
$(document).on('click', '.art_music', function() {
	$(".music_wrap").show();
	$(".overlay").addClass("active");
	defaultMusic();
})
// 初始化选择音乐
function musicInit() {
	$(".music_box").each(function() {
		$(this).find("i").removeClass();
	});
	$(".no_music").find("i").addClass("select_ico");
	$(".overlay").removeClass("active");
	$(".music_wrap").hide();
}
// 点击搜索在线音乐
$(".search_btn").click(function() {
	$(".onlie_search_wrap").show();
})
// 初始化选择在线音乐
function onlineInit() {
	$(".onlie_search_wrap").hide();
	$(".m_ssearch").val('');
}
// 点击搜索在线音乐返回按钮
$(".m_sback").click(function() {
	$(".m_ssearch").val('');
	$(".m_slist").empty();
	onlineInit();
})
// 点击搜索结果列表
$(document).on('click', '.m_slistbox', function() {
	var song = $(this).find(".m_s_tit").text();
	var singer = $(this).find(".m_s_name").text();
	var hash = $(this).attr("data-music");
	var tit = song + "-" + singer;
	addRecord(song, singer, hash);
	pagecount = 0;
	pageSize = 0;
	pagenumber = 0;
	$(".m_ssearch").val('');
	$(".m_slist").empty();
	$(".online_music").empty();
	$(".built_music").empty();

	addMusic(tit, hash);
	$(".onlie_search_wrap").hide();
	$(".music_wrap").hide();
	$(".overlay").removeClass("active");
})


// xgb
// 查询产品
function inquireProduct(goodsName) {
	var url = basePath + "ea/qrshare/sajax_ea_inquireProduct.jspa";
	$
			.ajax({
				url : url,
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"pageNumber" : parseInt(pagenumber) + 1,
					"productPackaging.goodsName" : goodsName,
					"cuscom.staffid" : staffid
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var pageForm = member.pageForm;
					$(".last").removeClass("last");
					var product = [];
					if (pageForm != null) {
						if (pageForm.list != null && pageForm.list.length > 0) {
							$(pageForm.list)
									.each(
											function(i, dom) {
												if ($(pageForm.list).length - 1 == i) {
													product.push( "<div class='pro_sbox_wrap last'>");
												} else {
													product.push( "<div class='pro_sbox_wrap'>");
												}
												product.push( "<i></i>");
												product.push( "<input type='checkbox' name='pro_sear'>");
												product.push( "<div class='pro_sbox' data-href='"
														+ basePath
														+ 'ea/wfjshop/ea_doodsDetail.jspa?ppid='
														+ this[3]
														+ '&goodsid='
														+ this[4]
														+ '&companyId='
														+ this[5]
														+ '&ccompanyId='
														+ this[6] + "'>");
												product.push( "<i class='pro_del'></i>");
												product.push( "<img src='"
														+ basePath + this[0]
														+ "'>");
												product.push( "<span>" + this[1]
														+ "</span>");
												product.push( "<span>" + '&yen;'
														+ this[2] + "</span>");
												product.push( "</div></div>");
											})
						} else {
							product.push( "<div style='height: 8rem;padding-top: 1rem;text-align: center;'>");
							product.push( "<img src='"
									+ basePath
									+ "images/ea/production/deficiency.png' style='height: 100%;'/>");
							product.push( "<input type='hidden' class='judge'/></div>");
						}
					} else {
						product.push( "<div style='height: 8rem;padding-top: 1rem;text-align: center;'>");
						product.push( "<img src='"
								+ basePath
								+ "images/ea/production/deficiency.png' style='height: 100%;'/>");
						product.push( "<input type='hidden' class='judge'/></div>");
					}
					$(".product").append(product.join(""));
					if (pageForm != null) {
						pagenumber = pageForm.pageNumber;
						pagecount = pageForm.pageCount;
						if (pagenumber < pagecount) {
							getHeightProduct(0);
						}
					}
				},
				error : function(data) {
					alert("获取产品信息失败");
				}
			});
}
function getHeightProduct(obj) {
	t = setTimeout("getHeightProduct(" + obj + ")", 200);
	if ($(".last").length > 0) {
		if ($(".last").offset().top - $(".last").height() <= $(window).height()) {
			if (pagenumber < pagecount) {
				if (obj == 0) {
					inquireProduct($(".pro_search").val());
				} else if (obj == 1) {
					inquireMusic($(".m_ssearch").val());
				}
			}
		}
	}
}

// 保存上传
$("#submit")
		.click(
				function() {
					var goodsName = $(".goodsName").val();
					var content = $("#submit_val").val();
					var picture = $("#idss").contents().find(".art_img").eq(0).find("img");
					// 判断标题是否添加
					if (goodsName != null && $.trim(goodsName) != "") {

						if(goodsName.length>200){
                               alert("标题长度不能超过200");
                               return false;
						}

						// 判断内容不能为空
						if (content != null && $.trim(content) != "") {
							if (picture.length > 0) {
								var pictureName = $("#delImgVideo").attr(
										"data-url");
								delTransformVideo(pictureName);
								var a = picture[0].src;
								var str1 = a.substring(a
										.indexOf("/upload_files"), a.length);
								$("#image").attr("value", str1);
								var url="";
								if(flag=='team'){
									url=basePath+"/people/manage/sajax_ajaxPersonalBrief.jspa?";
								}else{
									url = basePath+ "ea/qrshare/sajax_ea_saveShare.jspa";
								}
								
								$
										.ajax({
											url : url,
											type : "post",
											async : false,
											dataType : "json",
											data : $('#saveForm').serialize(),// 你的formid
											success : function(data) {
												var member = eval("(" + data
														+ ")");
												if(flag=='team'){//ljc 个人简介
													var ppId=member.ppId;
													document.saveForm.reset();
													$("#jj",window.parent.document).css("display","none");
													$("#brief",window.parent.document).append("<h style='text-align:right;font-size:14px;float:right;'>已编辑</h>");
													$("#jj",window.parent.document).parents().find("#ppId").val(ppId);
												}else{
													var ppk = member.ppk;
													document.saveForm.reset();
													window.location.replace(basePath
															+ "ea/qrshare/ea_qrshareDetail.jspa?productPackaging.ppID="
															+ ppk.ppID
															+ "&productPackaging.goodsID="
															+ ppk.goodsID+"&miniSystemJudge="+miniSystemJudge+"&certificate.ccompanyID="+ccompanyid+"&ccomIDPlatform="+ccomIDPlatform);
												}										
											},
											error : function(data) {

												alert("保存失败");

											}
										});
							} else {
								alert("请添加图片")
							}

						} else {
							alert("内容不能为空")
						}
					} else {
						alert("标题不能为空")
					}

				});
// 保存图片
/*function savePicture() {
	var add = $("#add_photo").prevAll();
	var groupID = $("#groupID").val();
	var photo_des = $("#savePictureForm .photo_des").val();
	if (groupID == "") {
		alert("请选择分组");
		return;
	}
	if (add.length > 0) {
		var url = basePath + "ea/qrshare/sajax_ea_saveContent.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data : $('#savePictureForm').serialize(),// 你的formid
			success : function(data) {
				$(".pic_upload").hide();
				add.remove();
				document.savePictureForm.reset();
				$(".pic_con").empty();
				imgInit();
				$(".add_img").click();
			},
			error : function(data) {

				alert("保存失败");

			}
		});
	} else {
		alert("请上传图片");
	}

}*/
// 获取要删除的mcid
function gainmcid(obj) {
	delTemporarySavePicture($(obj).text());
	$(obj).parent().remove();
}

// 删除临时保存图片
/*function delTemporarySavePicture(obj) {
	var url = basePath + "ea/qrshare/sajax_ea_delTemporarySavePicture.jspa";
	$.ajax({
		url : url,
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"materialContent.mcId" : obj
		},
		success : function(data) {
		},
		error : function(data) {
		}
	});
}*/
// 查询默认音乐
function defaultMusic() {
	var url = basePath + "ea/qrshare/sajax_ea_defaultMusic.jspa";
	$
			.ajax({
				url : url,
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"cuscom.staffid" : staffid
				},
				success : function(data) {
					var music = eval("(" + data + ")");
					var history = [];
					var system = [];
					if (music.hmsc != null && music.hmsc.length > 0) {
						$(music.hmsc)
								.each(
										function(i, dom) {
											history.push( "<a href='javascript:;' class='music_box' data-music='"
													+ this.musichash + "'>");
											history.push( "" + this.musicName
													+ "_" + this.singName
													+ "<i></i>");
											history.push( "</a>");
										})
					}
					$(".online_music").append(history.join(""));
					if (music.mmsc != null && music.mmsc.length > 0) {
						$(music.mmsc)
								.each(
										function(i, dom) {
											system.push( "<a href='javascript:;' class='music_box' data-music='"
													+ this.musichash + "'>");
											system.push( "" + this.musicName + "_"
													+ this.singName + "<i></i>");
											system.push( "</a>");
										})
					}
					$(".built_music").append(system.join(""));
				},
				error : function(data) {
					alert("查询失败");
				}
			});

}
// 搜索音乐
function searchMusic() {
	pagecount = 0;
	pageSize = 0;
	pagenumber = 0;
	$(".m_slist").empty();
	var music = inquireMusic();
	while (!music) {
		music = inquireMusic();
	}
}
// 查询音乐列表
function inquireMusic() {
	var judge = false;
	var musicName = $(".m_ssearch").val();
	pagenumber += 1;
	var url = basePath + "ea/qrshare/sajax_ea_musicList.jspa";
	$
			.ajax({
				url : url,
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"pageNumber" : pagenumber,
					"musicName" : encodeURIComponent(musicName)
				},
				success : function(data) {
					if (data.indexOf("<title>页面不存在_百度搜索</title>") == -1) {
						var music = eval("(" + data + ")");
						var pageForm = music.data;
						$(".last").removeClass("last");
						var music = [];
						$(pageForm.data)
								.each(
										function(i, dom) {
											if ($(pageForm.data).length - 1 == i) {
												music.push( "<div class='m_slistbox last' data-music='"
														+ this.hash + "'>");
											} else {
												music.push( "<div class='m_slistbox' data-music='"
														+ this.hash + "'>");
											}
											music.push( "<div class='m_s_tit'>"
													+ this.songname + "</div>");
											music.push( "<div class='m_s_name'>"
													+ this.singername
													+ "</div>");
											music.push( "<i></i></div>");
										})
						$(".m_slist").append(music.join(""));
						pagenumber = parseInt(pageForm.current_page);
						pagecount = parseInt(pageForm.page_size);
						if (pagenumber < pagecount) {
							getHeightProduct(1);
						}
						judge = true;
					}
				},
				error : function(data) {

					alert("查询失败");

				}
			});
	return judge;
}
// 查询音乐路径
function musicPath(musichash) {
	var judge;
	var url = basePath + "ea/qrshare/sajax_ea_musicPath.jspa?";
	$.ajax({
		url : url,
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"musicName" : encodeURIComponent(musichash)
		},
		success : function(data) {
			if (data.indexOf("<title>页面不存在_百度搜索</title>") == -1) {
				var music = eval("(" + data + ")");
				judge = music.data.url;
			}
		},
		error : function(data) {

			alert("查询失败");

		}
	});
	return judge;
}
// 添加音乐记录
function addRecord(song, singer, hash) {
	var url = basePath + "ea/qrshare/sajax_ea_addRecord.jspa?";
	$.ajax({
		url : url,
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"materialMusic.musicName" : song,
			"materialMusic.singName" : singer,
			"materialMusic.musichash" : hash,
			"cuscom.staffid" : staffid
		},
		success : function(data) {
			var Record = eval("(" + data + ")");
			if (Record.addRecord) {
				console.log("添加成功");
			} else {
				console.log("添加失败");
			}
		}
	});
}




// 添加时间
function addTime(text) {
	var imgHTML = [];
	imgHTML.push('<div class="art_box art_time clearfix">');
	imgHTML.push('<i class="art_dele"></i>');
	imgHTML.push('<div class="art_time_wrap">');
	imgHTML.push('<label>活动时间：</label>');
	imgHTML.push('<input class="art_input" value="'+text+'"/></div>');
	imgHTML.push('<div class="art_sort"><a href="javascript:;" class="set_up"></a>');
	imgHTML.push('<a href="javascript:;" class="set_down"></a></div></div>');
	$("#idss").contents().find(".art_con").append(imgHTML.join(""));
	initArtSet();
}



// 添加地址
function addSite(text) {
	var imgHTML = [];
	imgHTML.push('<div class="art_box art_site clearfix">');
	imgHTML.push('<i class="art_dele"></i>');
	imgHTML.push('<div class="art_time_wrap">');
	imgHTML.push('<label>活动地址：</label>');
	imgHTML.push('<input class="art_input" value="'+text+'"/></div>');
	imgHTML.push('<div class="art_sort"><a href="javascript:;" class="set_up"></a>');
	imgHTML.push('<a href="javascript:;" class="set_down"></a></div></div>');
	$("#idss").contents().find(".art_con").append(imgHTML.join(""));
	initArtSet();
}

// 添加URL
function addURL(text,url) {
	var imgHTML = [];
	imgHTML.push('<div class="art_box art_url clearfix">');
	imgHTML.push('<i class="art_dele"></i>');
	imgHTML.push('<div class="art_url_wrap">');
	imgHTML.push('<a href="javascript:window.parent.openUrl(\''+encodeURI(url)+'\',\''+text+'\');"><p data-url = "'+url+'"class="art_p">'+text+'</p></a>');
	imgHTML.push('</div>');
	imgHTML.push('<div class="art_sort"><a href="javascript:;" class="set_up"></a>');
	imgHTML.push('<a href="javascript:;" class="set_down"></a></div></div>');
	$("#idss").contents().find(".art_con").append(imgHTML.join(""));
	initArtSet();
}
//打开链接
function openUrl(url,text){

   $(".url_open1").show();
	$(".overlay").addClass("active");
   $(".title-h1").text(text);
	$("#ifameurl").attr("src",url);

	$("#ifameurl").attr("height",$(window).height());
}


// 添加资讯分类
function addCate(text) {
	var n = $("#idss").contents().find(".art_con").find(".art_cate").length;
	if (n > 0) {
		$("#idss").contents().find(".art_cate").text(text);
	} else {
		var t = '<div class="art_box art_cate" >' + text + '</div>'
		$("#idss").contents().find(".art_con").prepend(t);
	}


}




//从页面转移
//返回上一页
function backtrack() {
	// var art_con = $("#idss").contents().find(".art_con");
	// if (art_con.find(".art_img").length > 0) {
	// 	art_con.find(".art_img").each(function() {
	// 		var img_src = $(this).find("img").attr("src");
	// 		delfile(img_src, "");
	// 	});
	// }
	// if (art_con.find(".art_vedio").length > 0) {
	// 	art_con.find(".art_vedio").each(function() {
	// 		var img_src = $(this).find("img").attr("src");
	// 		var video_src = $(this).find("img").attr("data-vediosrc");
	// 		delfile(img_src, video_src);
	// 	});
	// }
	// if (skipJudge == "00") {//从列表进
	// 	var pictureName = $("#delImgVideo").attr("data-url");
	// 	delTransformVideo(pictureName);
	// }else if (skipJudge == "01") {//从详情进
	// 	var pictureName = $("#comparison").attr("data-url").split(",");
	// 	var pictureName1 = $("#delImgVideo").attr("data-url");
	// 	for ( var i = 0; i < pictureName.length; i++) {
	// 		pictureName1 = pictureName1.replace(pictureName[i], "");
	// 	}
	// 	delTransformVideo(pictureName1);
	//
	// }
	if(miniSystemJudge=='04'){
		document.location.href = basePath + "ea/companyforum/ea_forumMessage.jspa?concom.ccompanyID="+ccompanyid;
	}else{
		if(ccomIDPlatform!=""&&ccomIDPlatform!=null){
            document.location.href = basePath+"ea/sbq/ea_getAllInfo.jspa?nav=nav"
		}else {
          //  document.location.href = basePath + "ea/qrshare/ea_qrshareList.jspa?miniSystemJudge=" + miniSystemJudge;
			window.history.go(-1);return false;
        }
	}
	//ljc
	if(flag=='team'){
		$("#jj",window.parent.document).css("display","none");
	}
}

if($(".hidden_music").length>0){
	$(".art_music").text($(".hidden_music").text());
	$(".art_music").attr("data-url",$(".hidden_music").attr("data-url"));
}









