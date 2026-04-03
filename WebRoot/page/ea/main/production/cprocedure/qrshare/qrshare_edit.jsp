<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	response.addHeader("X-XSS-Protection", "0");
%>

<html class="firtbody">
<head>
	<title>
		<c:if test="${miniSystemJudge eq '00'}">
			公司简介
		</c:if>
		<c:if test="${miniSystemJudge eq '01'}">
			公司文化
		</c:if>
		<c:if test="${miniSystemJudge eq '02'}">
			公司新闻
		</c:if>
		<c:if test="${miniSystemJudge eq '03'}">
			资讯分享
		</c:if>
		<c:if test="${miniSystemJudge eq '04'}">
			公司论坛
		</c:if>

	</title>
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
		  href="<%=basePath%>css/ea/production/qrshare/sc_manger.css">
	<link rel="stylesheet"
		  href="<%=basePath%>css/ea/production/qrshare/base.css">
	<link rel="stylesheet"
		  href="<%=basePath%>css/ea/production/qrshare/qr_share.css">

	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">

        var basePath="<%=basePath%>";

        var pagecount = 0;
        var pageSize = 0;
        var pagenumber = 0;
        var count = 0;
        var groupID = "";
        var groupName = "所有图片";
        var fileType = "";

        var staffid;
        var companyId;
        var skipJudge = '${skipJudge}';
        var pictureName = '${pictureName}';
        var flag='${param.flag}';//ljc 简介
        //简介/文化/新闻
        var miniSystemJudge='${miniSystemJudge}';
        var ccompanyid = '${certificate.ccompanyID}';
        var ccomIDPlatform = "${param.ccomIDPlatform}";

	</script>
</head>
<body class="firtbody">
<header class="com_head">
	<a href="javascript:backtrack();" class="back"></a>
	<h1>
		<c:if test="${miniSystemJudge eq '00'}">
			公司简介
		</c:if>
		<c:if test="${miniSystemJudge eq '01'}">
			公司文化
		</c:if>
		<c:if test="${miniSystemJudge eq '02'}">
			公司新闻
		</c:if>
		<c:if test="${miniSystemJudge eq '03'}">
			资讯分享
		</c:if>
		<c:if test="${miniSystemJudge eq '04'}">
			公司论坛
		</c:if>
		<c:if test="${param.flag eq 'team' }">
			个人简介
		</c:if>

	</h1>
	<a href="javascript:;" class="head_R" id="submit">保存</a>
	<!--使用ID 添加事件-->
</header>
<div class="wrap_page">
	<form action="<%=basePath%>ea/qrshare/ea_saveShare.jspa" method="post"
		  name="saveForm" id="saveForm">
		<input id="submitProduct" type="submit" style="display: none;" />
		<div class="qrw_tit">
			<input type="text" placeholder="请输入标题"
				   name="productPackaging.goodsName" class="goodsName"
				   value="${productPackaging.goodsName }" onkeydown="if(event.keyCode==13){event.keyCode=0;return false;}">
		</div>
		<div class="submit_html"></div>
		<input type="hidden" id="submit_val" name="content">
		<input type="hidden" id="categoryName" name="productPackaging.categoryName">
		<input type="hidden" id="sccid" name="productPackaging.sccid" value="${productPackaging.sccid }">
		<input type="hidden" id="ppid" name="productPackaging.ppID"	value="${productPackaging.ppID }">
		<input type="hidden" id="goodsid" name="productPackaging.goodsID" value="${productPackaging.goodsID }">
		<input type="hidden" id="companyID" name="productPackaging.companyID" value="${param.companyID!=null?param.companyID:productPackaging.companyID }">
		<input type="hidden" id="miniSystemJudge" name="miniSystemJudge" value="${miniSystemJudge}">
		<input type="hidden" id="ccomIDPlatform" name="productPackaging.ccompanyID" value="${param.ccomIDPlatform}">

		<input type="hidden" id="image" name="productPackaging.image">
		<input type="hidden" id="fimage" name="productPackaging.fileUrl">
		<input	type="hidden" id="judge" name="judge" value="01">
		<div class="art_con">
			<iframe id="idss" border="0"  width="100%" name="iframe_con" style="border:0;overflow:auto" outline="0" src="<%=basePath%>page/ea/main/production/cprocedure/qrshare/qrshare_nest.jsp"></iframe>
		</div>

		<div class="qrw_add">
			<div class="art_add_menu">
				<a href="javascript:;" class="add_music"> <span>选择音乐</span>
				</a>
				<a href="javascript:;" class="add_img"> <input type="file"
															   accept="image/*" multiple="" id="up_img"> <span>添加图片</span>
				</a>
				<a href="javascript:;" class="add_vedio"> <input type="file"
																 accept="video/*" id="up_video"> <span>添加视频</span> </a>
				<a
						href="javascript:;" class="add_pro"> <span>添加产品</span>
				</a>
				<a
						href="javascript:;" class="add_time"> <span>添加时间</span>
				</a>
				<a
						href="javascript:;" class="add_site"> <span>添加地点</span>
				</a>
				<a
						href="javascript:;" class="add_url"> <span>外部链接</span>
				</a>
				<a
						href="javascript:;" class="add_cate"> <span>资讯类别</span>
				</a>
			</div>
		</div>
	</form>
	<div id="loading">
		<img src="<%=basePath%>images/ea/production/qrshare/loading.gif"
			 alt="">
		<p class="load"></p>
	</div>
	<div id="delImgVideo" style="display: none;" data-url=""></div>
	<div id="comparison" style="display: none;" data-url=""></div>
	<audio src="" id="edit_audio"></audio>
	<!--遮罩层-内嵌页面 开始-->
	<div class="overlay" style="display:block;" id="overlay">
		<!--在线图片选择 开始-->
		<%-- <div class="pic_wrap">
            <header class="com_head pic_head">
                <a href="javascript:;" class="back"></a>
                <h1><span>所有图片</span>（<span class="pic_num">0</span>/<span class="pic_all">99</span>）</h1>
                <a href="javascript:;" class="head_R">完成</a>
            </header>
            <div class="wrap_page">
                <a href="javascript:;" class="pic_btn">将本地图片上传至素材库</a>
                <div class="pic_con clearfix piclist">
                <!-- js拼接 -->
                </div>
            </div>
            <div class="pic_menu_wrap">
                <div class="pic_menu_cur"><span>所有图片</span></div>
                <dl class="pic_menu">
                </dl>
            </div>
        </div> --%>

		<!--在线图片选择 结束-->
		<!--选择产品页面 开始-->
		<div class="pro_wrap">
			<header class="com_head prohead">
				<a href="javascript:;" class="back"></a>
				<h1>
					<span class="check">选择商品</span><span onclick="pubGoods()">发布商品</span>
				</h1>
				<a href="javascript:;" class="head_R">完成</a>
			</header>
			<div class="wrap_page">
				<div class="pro_search_box">
					<input type="text" placeholder="搜索产品" class="pro_search">
				</div>
				<div class="pro_con clearfix product">
					<!-- ajax拼接 -->
				</div>
			</div>
		</div>
		<!--选择产品页面 结束-->
		<!--在线视频选择 开始-->
		<%-- <div class="vedio_wrap">
            <header class="com_head vedio_head">
                <a href="javascript:;" class="back"></a>
                <h1><span>所有视频</span>（<span class="pic_num">0</span>/<span class="pic_all">99</span>）</h1>
                <a href="javascript:;" class="head_R">完成</a>
            </header>
            <div class="wrap_page">
                <div class="pic_con clearfix videolist">
                    <!-- ajax拼接 -->
                </div>
                <div class="vedio_menu_wrap">
                    <div class="vedio_menu_cur"><span>所有视频</span></div>
                    <dl class="vedio_menu">
                    </dl>
                </div>
            </div>
        </div> --%>

		<!--在线视频选择 结束-->
		<!--文字输入全屏页面 开始-->
		<div class="textarea_inp">
			<header class="com_head t_head">
				<a href="javascript:;" class="back"></a>
				<h1>编辑文字</h1>
				<a href="javascript:;" class="head_R">保存</a>
			</header>
			<textarea id="text_val" placeholder="请输入文章详情"></textarea>
		</div>
		<!--文字输入全屏页面 结束-->

		<!--添加URL 开始-->
		<div class="url_div1">
			<header class="com_head t_head">
				<a href="javascript:;" class="back"></a>
				<h1>添加链接</h1>
				<a href="javascript:;" class="head_R">保存</a>
			</header>
			<div class="wz_div"><label>链接文字：</label><input type="text" id="wz-text"/></div>
			<div class="url-div"><label>链接url：</label>
				<textarea id="text_url" placeholder="请输入以http开头外部链接"></textarea>
			</div>

		</div>
		<!--文添加URL 结束-->
		<!--添加时间 开始-->
		<div class="time_div1">
			<header class="com_head t_head">
				<a href="javascript:;" class="back"></a>
				<h1>添加时间</h1>
				<a href="javascript:;" class="head_R">保存</a>
			</header>
			<div class="time_div"><label>活动时间：</label><input type="text" id="hdtime" onfocus="daytime(this)"/></div>


		</div>
		<!--添加时间 结束-->

		<!--添加地址 开始-->
		<div class="site_div1">
			<header class="com_head t_head">
				<a href="javascript:;" class="back"></a>
				<h1>添加地址</h1>
				<a href="javascript:;" class="head_R">保存</a>
			</header>

			<div class="site-div"><label class="site-label" onclick="loadMap()">活动地址：<span style="color:#a5a5a5;font-size:0.6rem;">点击定位选择地址</span>&nbsp;<img src="<%=basePath%>images/ea/edmandServe/site_ico.png"style="width:0.6rem;"/></label>
				<textarea id="text_site" placeholder="请填写活动地址" ></textarea>
			</div>

		</div>
		<!--打开链接-->
		<div class="url_open1">
			<header class="com_head url_head">
				<a href="javascript:;" class="back"></a>
				<h1 class="title-h1"></h1>
				<a href="javascript:;"></a>
			</header>

			<div>
				<iframe name="ifameurl" id="ifameurl" src="" width="100%"></iframe>
			</div>

		</div>

		<!--文添加地址 结束-->
		<!--文字输入全屏页面 结束-->
		<!--选择背景音乐 开始-->
		<div class="music_wrap">
			<header class="com_head">
				<a href="javascript:;" class="back"></a>
				<h1>选择背景音乐</h1>
				<a href="javascript:;" class="head_R">完成</a>
			</header>
			<div class="wrap_page">
				<div class="no_music">无背景音乐</div>
				<div class="online_music">
					<!-- ajax拼接 -->
				</div>
				<div class="built_music">
					<!-- ajax拼接 -->
				</div>
			</div>
			<!--选择在线音乐 开始-->
			<div class="onlie_search_wrap">
				<div class="m_s_wrap clearfix">
					<a href="javascript:;" class="m_sback"></a>
					<div class="m_sbox">
						<input type="text" placeholder="搜索歌曲名" class="m_ssearch">
					</div>
					<a href="javascript:searchMusic();" class="m_s_headR">搜索</a>
				</div>
				<div class="m_slist">
					<!-- ajax拼接 -->
				</div>
			</div>
			<!--选择在线音乐 结束-->
			<a href="javascript:;" class="search_btn" style="display: none;"><i></i>搜索在线音乐</a>
		</div>
		<!--选择背景音乐 结束-->


		<!--选择资讯分类 开始-->
		<div class="zxcate-div">
			<header class="com_head">
				<a href="javascript:;" class="back"></a>
				<h1>选择资讯分类</h1>
				<a href="javascript:;" class="head_R">完成</a>
			</header>
			<div class="wrap_page">

				<div class="zx-cate">
					<!-- ajax拼接 -->
					<a href="javascript:;" class="cate_box" >培训报名<i></i></a>
					<a href="javascript:;" class="cate_box" >生日报名<i></i></a>
					<a href="javascript:;" class="cate_box" >婚宴报名<i></i></a>
					<a href="javascript:;" class="cate_box" >活动报到<i></i></a>
					<a href="javascript:;" class="cate_box" >宴请报到<i></i></a>
					<a href="javascript:;" class="cate_box" >其他报到<i></i></a>
					<a href="javascript:;" class="cate_box" >资讯带货<i></i></a>
					<a href="javascript:;" class="cate_box" >资讯文章<i></i></a>
					<a href="javascript:;" class="cate_box" >其它广告<i></i></a>
					<a href="javascript:;" class="cate_box" >其它咨询<i></i></a>
					<a href="javascript:;" class="cate_box" >慈善捐赠<i></i></a>
					<a href="javascript:;" class="cate_box" id="sybz" style="display:none;">使用帮助<i></i></a>
					<a href="javascript:;" class="cate_box" id="gydq" style="display:none;">关于数字地球<i></i></a>
					<a href="javascript:;" class="cate_box" id="grhy" style="display:none;">个人认领<i></i></a>

				</div>

			</div>


		</div>

	</div>
	<!--遮罩层-内嵌页面 结束-->
</div>
<!-- 详情 -->
<div id="content" style="display: none;">${content }</div>


<div class="iframecom">
	<iframe name="iframepub" id="iframepub" src="" width="100%" height="100%" frameborder="0"></iframe>
</div>
<script>
    window.onload = function() {
        var he = $(window).height()-$(".com_head").height()-$(".qrw_tit").height()-$(".qrw_add").height();
        $("#idss").height(he);

        if(${miniSystemJudge=='03'} || ${miniSystemJudge=='04'}){
            staffid = '${cuscom.staffid}';
            if(${miniSystemJudge=='04'}){
                companyId = '${productPackaging.companyID}';
            }else{
                companyId = '${cuscom.companyId}';
            }
        }else{
            staffid = '${caccount.staffID}';
            companyId = '${caccount.companyID}';
        }
        if (skipJudge == "01") {
            if ($(".art_img").length > 0) {
                $(".art_img").each(function() {
                    var img_src = $(this).find("img").attr("src");
                    var img = img_src.split("upload_files");
                    var pictureName = "upload_files" + img[1];
                    var url = $("#comparison").attr("data-url");
                    if (url != "" && url != null) {
                        url += ",";
                    }
                    url += pictureName;
                    $("#comparison").attr("data-url", url);
                });
            }
            if ($(".art_vedio").length > 0) {
                $(".art_vedio").each(
                    function() {
                        var img_src = $(this).find("img").attr("src");
                        var video_src = $(this).find("img").attr(
                            "data-vediosrc");
                        var img = img_src.split("upload_files");
                        var video = video_src.split("upload_files");
                        var pictureName = "upload_files"+img[1] + "," +"upload_files"+ video[1];
                        var url = $("#comparison").attr("data-url");
                        if (url != "" && url != null) {
                            url += ",";
                        }
                        url += pictureName;
                        $("#comparison").attr("data-url", url);
                    });
            }
        }else if(skipJudge == "00"){
            if(pictureName!=null&&pictureName!=""){
                var srcs = pictureName.split(",");
                for(i = 0;i<srcs.length;i++){
                    addImg(basePath+srcs[i]);
                }
            }
        }




        if($("#companyID").val()=="company201009046vxdyzy4wg0000000025"){
            $("#sybz").show();
            $("#gydq").show();
            $("#grhy").show();

        }
    }
</script>
<script
		src="<%=basePath%>js/ea/production/cprocedure/qrshare/QR_art.js"></script>
</body>

</html>
