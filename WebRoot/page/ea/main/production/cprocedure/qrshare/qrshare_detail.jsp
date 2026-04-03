<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html class="firtbody">
<head>
<title>${productPackaging.goodsName }</title>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no, email=no" />
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>

	<script src="<%=basePath%>js/ea/production/cprocedure/qrshare/setHtmlFont.js" type="text/javascript"></script>
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/qrshare/base.css">
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/qrshare/qr_share.css">
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/qrshare/sc_manger.css">
<script src="<%=basePath%>js/ea/production/cprocedure/qrshare/qeshare_detail.js" type="text/javascript"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.6.0.js" type="text/javascript"></script>


<script type="text/javascript">

var basePath="<%=basePath%>";
var ppid = '${productPackaging.ppID }';
var goodsid = '${productPackaging.goodsID }';
var name='${productPackaging.goodsName }';
var image='${productPackaging.image}';
var sccid='${productPackaging.sccid}';
var backtype='${backtype}';


var companyid = '${productPackaging.companyID }';
var ccompanyId = '${contactCompany.ccompanyID}';
var companyName = '${contactCompany.companyName}';


var miniSystemJudge='${miniSystemJudge}';
var ccomIDPlatform = "${param.ccomIDPlatform}";


var ccompanyID = '${certificate.ccompanyID}';


$(function(){

	var ua = navigator.userAgent.toLowerCase();
	var isWeixin = ua.indexOf('micromessenger') != -1;
	if (isWeixin) {
		$(".art_bR").hide();
		var shareurl = basePath
				+ "ea/industry/ea_informationDetails.jspa?ppId=" + ppid + "&ccompanyId=" + ccompanyId + "&miniSystemJudge=" + miniSystemJudge + "&back=1&type=web";
		var imgsrc = $(".article_img").eq(0).find("img").attr("src");
		var url = basePath
				+ "ea/qrshare/sajax_ea_getJssdkConfig.jspa";

		//var retUrl = encodeURIComponent(location.href.split('#')[0]);
		var retUrl = location.href.split('#')[0];
		$.ajax({
			url : url,
			type : "post",
			async : true,
			dataType : "json",
			data:{
				retUrl:retUrl
			},
			success : function(data) {
				var m = eval("("+data+")");

				wx.config({
					debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
					appId: m.appId, // 必填，公众号的唯一标识
					timestamp:m.timestamp , // 必填，生成签名的时间戳
					nonceStr: m.nonceStr, // 必填，生成签名的随机串
					signature: m.signature,// 必填，签名
					jsApiList: ["updateAppMessageShareData","updateTimelineShareData"] // 必填，需要使用的JS接口列表
				});

				wx.error(function (res) {
					console.log(res);
				});


				wx.ready(function () {   //需在用户可能点击分享按钮前就先调用
					wx.updateAppMessageShareData({
						title: name, // 分享标题
						desc: '', // 分享描述
						link: shareurl, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
						imgUrl: imgsrc, // 分享图标
						success: function (res) {
                          // alert("设置成功");
						},
						fail:function(res){
							//alert("设置失败");

						}
					})

					wx.updateTimelineShareData({
						title: name, // 分享标题
						link: shareurl, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
						imgUrl: imgsrc, // 分享图标
						success: function () {

						}
					})

				});
			}
		});

	}

});
</script>
</head>
<body class="firtbody">
	<header class="com_head">
		<a href="javascript:backtrack();" class="back" style="display: none;"></a>
		<h1>我的资源与您共享</h1>
		<a href="javascript:;" class="head_R article_menu"><i
			class="article_edit" style="display: none;"></i> </a>
		<dl class="article_add">
			<dd class="article_edit_btn">编辑项目</dd>
			<dd class="article_dele_btn" onclick="deleteProject()">删除项目</dd>
		</dl>
		<!--使用ID 添加事件-->
	</header>
	<form action="<%=basePath%>ea/qrshare/ea_qrshareEdit.jspa?skipJudge=01&ccomIDPlatform=${param.ccomIDPlatform}"
		style="display: none;" method="post">
		<input type="hidden" name="productPackaging.ppID"
			value="${productPackaging.ppID }" id="ppid" /> <input type="hidden"
			name="productPackaging.goodsID" value="${productPackaging.goodsID }"
			id="goodsID" /> <input type="hidden"
			name="productPackaging.goodsName"
			value="${productPackaging.goodsName }" id="goodsName" /> <input
			type="hidden" name="productPackaging.sccid"
			value="${productPackaging.sccid }" id="sccid" /><input
			type="hidden" name="productPackaging.companyID"
			value="${productPackaging.companyID }" id="companyID" /><input
			type="hidden" name="certificate.ccompanyID"
			value="${certificate.ccompanyID}" id="ccompanyID" />
			<input type="hidden" name="miniSystemJudge" value="${miniSystemJudge}" id="miniSystemJudge" />
		<div class="submit_html"></div>
		<input type="hidden" id="submit_val" name="content"> <input
			type="submit" style="display: none;" id="submit" />
	</form>
	<div class="wrap_page">
		<div class="article_tit_wrap">
			<div class="article_tit">${productPackaging.goodsName}</div>
			<div class="article_time">
				<fmt:formatDate value="${productPackaging.packagingDate}"
					pattern="yyyy/MM/dd" />&nbsp;
				<span>作者:</span><span>${name}</span>&nbsp;|&nbsp;<span class="cate-span">${productPackaging.categoryName}</span>
			</div>
			<i class="music_play paused" style="display: none;"></i>
		</div>
		<div class="article_con">
			<!-- 产品 -->
			${content }
			<embed
				src="<%=basePath%>\upload_files\Staff20160524Q6YPCX3UGD0000004413\scmanage\2016-11-02\aaa.mp3"
				autostar="true" hidden="true" />


			<a
				href="<%=basePath%>/ea/consignee/ea_toVipCenter.jspa?sccid=${productPackaging.sccid}"
				class="article_btn" style="display: none;"> <i></i>免费制作自己的二维码分享营销
			</a>
			<p class="article_QR">
				<img src="<%=basePath%>${cuscom.qrcodePath}" alt=""><br>
				<span>【扫二维码,交换名片，加入微分金】</span>
			</p>
			<p class="article_QR">
				<img src="<%=basePath%>${concom.pmCodePath}" alt=""><br>
				<span>【扫二维码,关注公司微信公众号】</span>
			</p>
			<div class="QR_float" style="display: none;">
				<div class="o_white"></div>
				<div class="float_con">
					<span>你也能发这样的图文、视频</span><a
						href="<%=basePath%>/ea/wfjshop/ea_getjspzc.jspa?sccid=${productPackaging.sccid}">马上制作</a><i></i>
				</div>
			</div>


			<div class="article_attr clearfix">
				<div class="art_bL">
					<a href="javascript:;" class="art_zan"></a> <a href="javascript:;"
						class="art_pl"></a>
				</div>
				<a href="javascript:;" class="art_bR">分享</a>
			</div>
		</div>
	</div>
	<script>
        window.onload  = function() {
				if(${judge=="02"}){
    					$(".back").show();
    					if(backtype!="01"&&backtype!="02"){
    						$(".article_edit").show();
    						$(".article_attr").show();
    					}
					}else{
						$(".article_btn").show(); 
    					$(".QR_float").show(); 
					}
					
				if($("audio").length!=0){
					$(".paused").show();
				}	
		}

    </script>
	<script>
    	$(".art_bR").click(function() {
    		var shareurl = basePath
				+ "ea/industry/ea_informationDetails.jspa?"; 
    		var u = window.navigator.userAgent;
			var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
			var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

				if (isAndroid == true) {
					shareurl += "&ppId=" + ppid + "&ccompanyId=" + ccompanyId + "&miniSystemJudge=" + miniSystemJudge + "&back=1&type=web";
					Android.showShare("我的资源与您共享", name, shareurl, basePath + image, ppid);
				} else if (isiOS == true) {
					var url = "func=" + 'ioscallshare';
					params = {
						'title': '我的资源与您共享',
						'name': name,
						'url': shareurl,
						'basePanth': basePath + image,
						'ppId': ppid,
						'ccompanyId': ccompanyId,
						'miniSystemJudge': miniSystemJudge,
						'back': '1',
						'type': 'web',
						"Markcallback": "1"
					};
					for (var i in params) {
						url = url + "&" + i + "=" + params[i];
					}
					window.webkit.messageHandlers.Native.postMessage(url);
				} else {


				}

    	})
    	
        $(".article_menu").click(function() {
            $(".article_add").slideToggle(100);
        })
		//马上制作浮动层-关闭
        $(".QR_float i").click(function(){
            $(this).parent().parent().slideUp(100);
        })
        //因音乐接口失效暂时注销该方法
        /* if($(".article_audio").length>0){
        	var url=musicPath($(".article_audio").attr("data-hash"));
		    while(url==null){
		       url=musicPath($(".article_audio").attr("data-hash"));
		    }
		    $(".article_audio").attr("src",url);
        } */
        //因音乐接口失效暂时使用该方法
	    if($(".article_audio").length>0){
		    $(".article_audio").attr("src",$(".article_audio").attr("data-hash"));
        }
    </script>
	<script>
         $(".article_edit_btn").click(function(e) {
			 var cate = $(".cate-span").text();
            var $html = $(".submit_html");

			 if(cate!="") {
				 var _html = [];
				 _html.push('<div class="art_box art_cate" >' + cate + '</div>');
				 $html.append(_html.join(""));
			 }
            $(".article_p").each(function() {
                var that = $(this);
                var t = $(this).find("p").html();
                if(t!=null){
                	t = t.replace(new RegExp(/<br>/ig), "\n");
                	t = t.replace(new RegExp(/&nbsp;/ig), " ");
                	
                }
                var imgsrc = $(this).find("img").attr("src");
                var vediosrc = $(this).find("video").attr("src");
                var vedioimg = $(this).find("video").attr("poster");
                var audiosrc=$(this).attr("data-hash");
                var audiotit=$(this).attr("data-name");
                var _html = [];
                if (this.classList.contains("article_audio")) {
                   _html.push('<div class="art_box art_music" data-url="'+audiosrc+'">'+audiotit+'</div>');
                } else if (this.classList.contains("article_img")) {
                    _html.push('<div class="art_box art_img clearfix">' +
                        '<i class="art_dele"></i>' +
                        '<div class="art_text_wrap">' +
                        '<textarea class="art_text" placeholder="点击添加描述文字">' + t + '</textarea></div>' +
                        '<div class="art_R"><img src="' + imgsrc + '" alt=""></div>' +
                        '<div class="art_sort"><a href="javascript:;" class="set_up"></a>' +
                        '<a href="javascript:;" class="set_down"></a></div></div>');
                } else if (this.classList.contains("article_vedio")) {
                    _html.push('<div class="art_box art_vedio clearfix">' +
                        '<i class="art_dele"></i>' +
                        '<div class="art_text_wrap">' +
                        '<textarea class="art_text" placeholder="点击添加描述文字">' + t + '</textarea></div>' +
                        '<div class="art_R"><i></i><img src="' + vedioimg + '" alt="" data-vediosrc="' + vediosrc + '"></div>' +
                        '<div class="art_sort"><a href="javascript:;" class="set_up"></a>' +
                        '<a href="javascript:;" class="set_down"></a></div></div>');
                }else if (this.classList.contains("article_pro")) {
					var prolist = [];
					that.find(".pro").each(function() {
						var a_href = $(this).attr("href");
						var p_html = $(this).html();
						prolist.push('<div class="pro" data-href="' + a_href + '">' + p_html + '</div>');
					})
					_html.push('<div class="art_box art_pro clearfix"><i class="art_dele"></i><div class="pro_box clearfix">' + prolist.join("") + '</div><div class="art_sort"><a href="javascript:;" class="set_up"></a><a href="javascript:;" class="set_down"></a></div></div>');
				} else if (this.classList.contains("article-time")) {
					var text = $(this).find("span").text();
					_html.push('<div class="art_box art_time clearfix">');
					_html.push('<i class="art_dele"></i>');
					_html.push('<div class="art_time_wrap">');
					_html.push('<label>活动时间：</label>');
					_html.push('<input class="art_input" value="'+text+'"/></div>');
					_html.push('<div class="art_sort"><a href="javascript:;" class="set_up"></a>');
					_html.push('<a href="javascript:;" class="set_down"></a></div></div>');
                }else if (this.classList.contains("article-site")) {

					var text = $(this).find("span").text();

					_html.push('<div class="art_box art_site clearfix">');
					_html.push('<i class="art_dele"></i>');
					_html.push('<div class="art_time_wrap">');
					_html.push('<label>活动地址：</label>');
					_html.push('<input class="art_input" value="'+text+'"/></div>');
					_html.push('<div class="art_sort"><a href="javascript:;" class="set_up"></a>');
					_html.push('<a href="javascript:;" class="set_down"></a></div></div>');

				}else if (this.classList.contains("article-url")) {
					var text = $(this).find(".url-p").text();
					var url = $(this).find(".url-p").attr("data-url");
					_html.push('<div class="art_box art_url clearfix">');
					_html.push('<i class="art_dele"></i>');
					_html.push('<div class="art_url_wrap">');
					_html.push('<a href="javascript:window.parent.openUrl(\''+encodeURI(url)+'\',\''+text+'\');"><p data-url = "'+url+'"class="art_p">'+text+'</p></a>');
					_html.push('</div>');
					_html.push('<div class="art_sort"><a href="javascript:;" class="set_up"></a>');
					_html.push('<a href="javascript:;" class="set_down"></a></div></div>');

				}

                $html.append(_html.join(""));
            })
            $("#submit_val").val($html.html());
            $html.empty();
            e.stopPropagation();
            $(".article_add").slideUp(100);
            $("#submit").click();
        })
        //新增2016年11月15日 15:16:31
        //点击背景音乐播放图标暂停 
        var audio = $(".article_audio")[0];
        if ($(".article_audio").length > 0) {
            $(".music_play").click(function() {
                if (!audio.paused) {
                    audio.pause();
                    $(this).addClass("paused");
                } else {
                    audio.play();
                    $(this).removeClass("paused");
                }
            })
            $('html').one('touchstart', function() {
                audio.play();
                $(".music_play").removeClass("paused");
            });

        }
        var v = document.getElementsByTagName("video");
        for (i = 0; i < v.length; i++) {
            v[i].addEventListener("play", function() {
                if ($(".article_audio").length > 0) {
                    audio.pause();
                    $(".music_play").addClass("paused");
                    }
                    if($("video").length>1){
                    	var t=$(this).parent().siblings(".article_p").find("video")[0];
                    	if(!t.paused){
                         t.load();
                    	}
                    }
                
            })
        }
    </script>
</body>

</html>
