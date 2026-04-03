//上传图片
function attition() {
		var u = window.navigator.userAgent;
		var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
		if (isAndroid == true) {
		//	$("#loading").show();
			try {
				Android.callPictures();
			} catch(e) {
				uploading();
			}
		} else if (isiOS == true) { 
			uploading();
		}else{
			uploading();

		}
	}

function uploading(){
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
			alert("最多同时只可上传9张图片");
			return;
		}
		files.forEach(function(file, i) {
			if (!/\/(?:jpeg|png|gif|jpg)/i.test(file.type))
				return;
			if (file.size <= maxsize && file.size>=minsize) {
                var uuu = addimg(file);
                if(uuu!=""){
                    srcs += uuu+",";
				}
			} else {
				if(file.size > maxsize){
					alert("上传图片过大,请上传标准格式图片");
				}else if(file.size < minsize){
					alert("上传图片过小,请上传标准格式图片");
				}
			}
		})
		srcs = srcs.substring(0,srcs.length-1);
		if(srcs!=""){
			var url = basePath
			+ "ea/qrshare/ea_qrshareEdit.jspa?skipJudge=00&pictureName="+srcs+"&miniSystemJudge="+miniSystemJudge+"&companyID="+companyID;
			
			if(miniSystemJudge=="03" || miniSystemJudge=="04"){
				url+="&productPackaging.sccid="+ sccid;
			}
			document.location.href = url;
		}
	}
}





//安卓回调
function syntony(srcs){
	var url = basePath
	+ "ea/qrshare/ea_qrshareEdit.jspa?skipJudge=00&pictureName="+srcs+"&miniSystemJudge="+miniSystemJudge;

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
	if(miniSystemJudge=="03" || miniSystemJudge=="04"){
		url+="&productPackaging.sccid="+ sccid;
	}
	document.location.href = url;
}

function addimg(file) {
	var src = "";
	var formdata = new FormData();
	formdata.append("picture", file);
	formdata.append("pictureName", file.name);
	formdata.append("cuscom.staffid", staffid);
	formdata.append("cuscom.companyId", companyId);
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


//查看分享    
function ajax() {
	var surl1 = basePath + "ea/qrshare/sajax_ea_miniList.jspa";//简介/文化/新闻
	if(miniSystemJudge=="03"){
		surl1 = basePath + "ea/qrshare/sajax_ea_memberShare.jspa";//分享
	}
	
	if (pageNumber == null) {
		pageNumber = 1;
	} else {
		pageNumber = parseInt(pageNumber) + 1;
	}
	$
			.ajax({
				url : encodeURI(surl1),
				type : "post",
				data : {
					"sccid" : sccid,
					"cuscom.companyId":companyId,
					"pageNumber" : pageNumber,
					"miniSystemJudge":miniSystemJudge,
					companyID:companyID
				},
				dataType : "json",
				async : false,
				success : function(data) {
					var jsonresult = eval("(" + data + ")");
					var pageForm = jsonresult.pageForm;
					var share = [];
					$(".last").removeClass("last");
						if (pageForm != null && pageForm.list != null
								&& pageForm.list.length > 0) {
							$("title").html(pageForm.list[0][0]);
							$(pageForm.list)
									.each(
											function(i, dom) {
												if ($(pageForm.list).length - 1 == i) {
													share.push( "<a href='javascript:void(0);' class='xw_box clearfix last' onclick='particulars(this)'>");
												} else {
													share.push( "<a href='javascript:void(0);' class='xw_box clearfix' onclick='particulars(this)'>");
												}
												var t;
												if (this[1]!=null){
													t = this[1].split(".")[0]+"small."+this[1].split(".")[1];
												}
												share.push( "<img src='"+basePath+t+"' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>");
												share.push( "<div class='xw_tit'>"
														+ this[0]
														+ "</div>");
												share.push( "<div class='xw_state'>");
												share.push( "<span><i class='xw_ck'></i>"+formatNum(this[8])+"</span>");
												share.push( "<span><i class='xw_sc'></i>"+formatNum(this[7])+"</span>");
												share.push( "<span><i class='xw_dz'></i>"+formatNum(this[6])+"</span>");
												share.push( "<span><i class='xw_pl'></i>"+formatNum(this[5])+"</span>");
												share.push( "</div>");
												share.push( "<input type='hidden' id='ppid' value='"+this[2]+"'/>");
												share.push( "<input type='hidden' id='goodsID' value='"+this[3]+"'/>");
												share.push( "</a>");
											})
						} else {
							share.push( "<div class='no_list list_img'></div>");
							share.push( "<div class='no_list arr_gif'></div>");
							if(miniSystemJudge=='00'){
								$("title").html("公司简介");
							}else if(miniSystemJudge=='01'){
								$("title").html("公司文化");
							}else if(miniSystemJudge=='02'){
								$("title").html("公司新闻");
							}else if(miniSystemJudge=='03'){
								$("title").html("会员分享");
							}else if(miniSystemJudge=='04'){
								$("title").html("公司论坛");
							}
						}
						
					$(".qr_list_wrap").append(share.join(""));
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
function getHeight() {
	t = setTimeout("getHeight()", 200);
	if ($(".last").length > 0) {
		if ($(".last").offset().top - $(".last").height() <= $(window)
				.height()) {
			if (pageNumber < pageCount) {
				ajax();
			}
		}
	}
}
function particulars(obj) {
	var ppid = $(obj).find("#ppid").val();
	var goodsid = $(obj).find("#goodsID").val();
	document.location.href = basePath
			+ "ea/qrshare/ea_qrshareDetail.jspa?productPackaging.ppID="
			+ ppid + "&productPackaging.goodsID=" + goodsid+"&miniSystemJudge="+miniSystemJudge;
}
//返回上一步
function backtrack() {
	if(miniSystemJudge=='03'){
		try{
			Android.callAndroidjianli();
		}catch(err){

			window.history.go(-1);
			return false;
			// if(androidJudge=="zzl"){
			// 	document.location.href = basePath + "ea/WfjIndustryPlatform/ea_getIndustryList.jspa?sccid="+sccids;
			// 	return;
			// }else if(androidJudge=="xgb"){
			// 	document.location.href = basePath + "ea/consignee/ea_toVipCenter.jspa";
			// 	return;
			// }else{
			// 	return alert("系统错误,请重新登录");
			// }
		}
	}
}

function formatNum(num){
	var s = "";
   if(Number(num)>1000){
	   s =  Number(num)/1000+"k";
   }else if(Number(num)>10000){
	   s = Number(num)/10000+"w";

   }else{
	  s = num;
   }
	return s;
}
