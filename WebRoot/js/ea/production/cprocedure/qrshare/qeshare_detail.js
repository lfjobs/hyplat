//删除项目
function deleteProject() {
	var gnl = confirm("确定要删除?");
	if (gnl == true) {
		var src = [];
		$(".article_p").each(
				function() {
					var t = $(this);
					if (t.hasClass("article_img")) {
						src.push("upload_files"
								+ t.find("img").attr("src").split(
										"upload_files")[1]);
					} else if (t.hasClass("article_vedio")) {
						src.push("upload_files"
								+ t.find("video").attr("src").split(
										"upload_files")[1]
								+ ","
								+ "upload_files"
								+ t.find("video").attr("poster").split(
										"upload_files")[1]);
					}
				})
		var pictureName = src.join(",");
		document.location.href = basePath
				+ "ea/qrshare/ea_deleteProject.jspa?productPackaging.ppID="
				+ ppid + "&goodFunction.goodsid=" + goodsid + "&pictureName="
				+ pictureName + "&miniSystemJudge=" + miniSystemJudge+"&concom.CcompanyID="+ccompanyID+"&ccomIDPlatform="+ccomIDPlatform;

	} else {
		return false;
	}
}
// 返回上一页
function backtrack() {
	if (backtype == "01") {
		document.location.href = basePath
				+ "ea/wfjshop/ea_getNewsList.jspa?typeNews=";
	} else if (backtype == "02") {
		history.go(-1);
	} else {
		if (miniSystemJudge == '04') {
			document.location.href = basePath
					+ "ea/companyforum/ea_forumMessage.jspa?concom.ccompanyID="
					+ ccompanyID;
		} else {
            if(ccomIDPlatform!=""&&ccomIDPlatform!=null){
               document.location.href = basePath+"ea/sbq/ea_getAllInfo.jspa?nav=nav";
            }else {
                // document.location.href = basePath
                //     + "ea/qrshare/ea_qrshareList.jspa?&miniSystemJudge="
                //     + miniSystemJudge;
				
				
				window.history.go(-1);return false;
            }
		}
	}
}

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
//打开链接
function openUrl(url,text){
	document.location.href = basePath+"page/ea/main/production/cprocedure/qrshare/openurl.jsp?title="+text+"&url="+url;
}
