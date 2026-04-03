<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">

	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/attence/base.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/collage/phl/dspviewlist.css">
	<script src="<%=basePath%>js/BuildPlatform/setHtmlFont.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/ea/collage/dsp/dspviewlist.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>

	<title>${video[2]}</title>
</head>
<body>
<div class="content">
	<a onclick="javascript: window.history.go(-1);return false;" target="_self" >
		<div class="fh"><img src="<%=basePath%>images/ea/office/contract/selectp/return.png"></div>
		</a>
	<section class="box">
		<img src="${video[12]}" class="video-img" alt="" onerror="this.src='<%=basePath%>images/ea/collage/dsp/pic_03.png'">
		<div class="nobox" id="acc">

		</div>
		<div class="player" style="display: flex; align-items: center; justify-content: center">

			<video  width="100%" id="video" src = "${video[1]}" class="video" data-play="play" webkit-playsinline="true" x-webkit-airplay="true"  playsinline="true" x5-video-player-type="h5" x5-video-player-fullscreen="true">
			</video>

		</div>
		<div class="div-play">
			<img src="<%=basePath%>images/ea/collage/dsp/img-13.png"/>
		</div>

		<div class="bottom">
			<div class="clearfix">
				<p>
					<img src="<%=basePath%>images/ea/collage/dsp/shop.png"/>
				</p>
				<p class="shopping">
					购物&nbsp;<span style="font-family:Calibri">I</span>&nbsp;视频同款
				</p>
			</div>
			<div>
				<p class="name">
					@${video[4]}
				</p>
				<p class="title">
					${video[2]}
				</p>
			</div>
		</div>
		<div class="div-right">
			<div class="div-01">
				<div  class="div-box <c:if test="${video[11] eq '1'}">active</c:if>">
					<img src="<%=basePath%>${video[5]}"  onerror="this.src='<%=basePath%>images/ea/driving/elkc/head.png'" />
				</div>
				<div   class="div-img2 <c:if test="${video[11] eq '1'}">active</c:if>">
					<img src="<%=basePath%>images/ea/collage/dsp/img_10.png"/>
					<img src="<%=basePath%>images/ea/collage/dsp/img_15.png"/>
				</div>
			</div>
			<div  class="div-02">
				<span class="praisev" style="display: none;">${video[7]}</span>
				<input type="hidden"  class="videoID"  value="${video[0]}"/>
				<div class="pr <c:if test="${video[10] eq '1'}">active</c:if>">
					<img src="<%=basePath%>images/ea/collage/dsp/img_21.png"/>
					<img src="<%=basePath%>images/ea/collage/dsp/img_19.png"/>
				</div>
				<p class="pw">
                    ${video[7]}

				</p>
			</div>
			<div class="div-03" onclick="down()">
				<span class="plcountv" style="display: none;">${video[8]}</span>
				<img src="<%=basePath%>images/ea/collage/dsp/img_26.png"/>
				<p class="cw">
					${video[8]}
				</p>
			</div>
			<div class="div-04" style="display:none;">
				<span class="sharev" style="display: none;">${video[9]}</span>
				<img src="<%=basePath%>images/ea/collage/dsp/img_30.png"/>
				<p class="sh">
					${video[9]}
				</p>
			</div>
			<div class="div-05">
				<img id="center" src="<%=basePath%>images/ea/collage/dsp/center.png"/>
			</div>

		</div>
	</section>
	<section class="gengduo" style="display:none;">
		<p>
			关注历史
		</p>
		<ul class="morevideo">
			<c:forEach items="${pageForm.list}" var="item" varStatus="state">
				<li id="${item[0]}" class="ac <c:if test="${state.index+1 eq fn:length(pageForm.list)}">ttsw_last</c:if>">
					<img src="${item[12]}" onerror="this.src='<%=basePath%>images/ea/collage/dsp/pic_03.png'" />
					<input type="hidden" class="videoIDl" value="${item[0]}">
					<input type="hidden" class="videourl" value="${item[1]}">
					<input type="hidden" class="titlename" value="${item[2]}">
					<input type="hidden" class="videoStaffID" value="${item[3]}">
					<input type="hidden" class="videoStaffName" value="${item[4]}">
					<input type="hidden" class="headimage" value="${item[5]}">
					<input type="hidden" class="createdate" value="${item[6]}">
					<input type="hidden" class="praisevl" value="${item[7]}">
					<input type="hidden" class="plcountvl" value="${item[8]}">
					<input type="hidden" class="sharevl" value="${item[9]}">
					<input type="hidden" class="ispraisel" value="${item[10]}">
					<input type="hidden" class="care" value="${item[11]}">
					<input type="hidden" class="coverImgUrl" value="${item[12]}">
					<div class="clearfix">
						<p>
							<img src="<%=basePath%>images/ea/collage/dsp/img-01.png"/>
						</p>
						<p class="dz">
								${item[7]}
						</p>
					</div>
				</li>
			</c:forEach>

		</ul>
	</section>
</div>


<div class="div-list">
	<div class="box">
		<ul>

			<li class="cart">
				<div>
					<img src="<%=basePath%>images/ea/collage/dsp/pic_07.png"/>
				</div>
				<p>购物车</p>
			</li>
			<li class="myorder">
				<div>
					<img src="<%=basePath%>images/ea/collage/dsp/pic_10.png"/>
				</div>
				<p>我的订单</p>
			</li>
			<li class="bag">
				<div>
					<img src="<%=basePath%>images/ea/collage/dsp/pic_20.png"/>
				</div>
				<p>钱包</p>
			</li>
			<li id="close-div">
				<div>
					<img src="<%=basePath%>images/ea/collage/dsp/pic_12.png"/>
				</div>
				<p>取消</p>
			</li>
		</ul>
	</div>
</div>
</body>
<script type="text/javascript">
    var pagenumber = 0
    var pagecount = 0
	var basePath = "<%=basePath%>";
	var videoStaffID  = "${video[3]}";
	var videoID = "${video[0]}";
    var pricetype = "${video[16]}";
    var sccId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
    var staid = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';
    var user = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount():"" %>';


</script>
</html>
