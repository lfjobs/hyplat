<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="x-ua-compatible" content="ie=7" />
		<title>图片列表</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<link rel="stylesheet"
			href="<%=basePath%>css/ea/photomanage/style.css" type="text/css"
			charset="utf-8" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery-1.3.1.min.js"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery.easing.1.3.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script src="<%=basePath%>js/ea/office_ea/fileCabinet/photoList.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript">
         var  basePath='<%=basePath%>';   
         var photoBoxId = '<%=request.getAttribute("photoBoxID")%>'; 
             
       </script>


		<style type="text/css">
.acss a:link {
	text-decoration: none;
}
</style>

		<style>
body {
	font-family: Arial;
}

ul#navigation {
	right: 50px;
	margin-bottom: 50px;
}

ul#navigation .home a {
	background-image:
		url(<%=basePath%>images/ea/office/fileCabinet/home.png);
}

ul#navigation .about a {
	background-image:
		url(<%=basePath%>images/ea/office/fileCabinet/id_card.png);
}

ul#navigation .search a {
	background-image:
		url(<%=basePath%>images/ea/office/fileCabinet/search.png);
}

ul#navigation .podcasts a {
	background-image:
		url(<%=basePath%>images/ea/office/fileCabinet/ipod.png);
}

ul#navigation .rssfeed a {
	background-image: url(<%=basePath%>images/ea/office/fileCabinet/rss.png)
		;
}

ul#navigation .photos a {
	background-image:
		url(<%=basePath%>images/ea/office/fileCabinet/camera.png);
}

ul#navigation .contact a {
	background-image:
		url(<%=basePath%>images/ea/office/fileCabinet/mail.png);
}

#picdiv img {
	border: none;
	cursor: pointer;
}

a {
	text-decoration: none;
	color: #946652;
}

#manager ul li {
	list-style-type: none;
	display: inline;
	color: #946652;
}

.stylediv {
	padding-left: 5px;
	padding-right: 5px;
}

.move {
	background-color: #FFECEC;
	cursor: hand;
}

.move2 {
	border-top: 2px solid #CCCCCC;
	border-left: 2px solid #CCCCCC;
	border-right: 2px solid #CCCCCC;
}

#picboxdiv {
	overflow: auto;
	width: 120px;
	height: 136px;
}

#full {
	overflow: hidden;
	display: none;
	border: 2px solid #CCCCCC;
	position: absolute;
	width: 120px;
	height: 150px;
	top: 50px;
	left: 179px;
	z-index: 99;
	background-color: #FFFFFF;
}

.sep {
	background: url(<%=basePath%>images/admin_images/act_btn00.gif)
		no-repeat;
	height: 24px;
	width: 70px;
	cursor: pointer;
	border: solid 0px #111;
	font-size: 10px;
}

.mouseover {
	background-color: #FFD306;
}
</style>
		<script type="text/javascript">
            $(function() {
                var d=300;
                $('#navigation a').each(function(){
                    $(this).stop().animate({
                        'marginTop':'-80px'
                    },d+=150);
                });

                $('#navigation > li').hover(
                function () {
                    $('a',$(this)).stop().animate({
                        'marginTop':'-2px'
                    },200);
                },
                function () {
                    $('a',$(this)).stop().animate({
                        'marginTop':'-80px'
                    },200);
                }
            );
            });
        </script>

	</head>
	<body>
		<ul id="navigation">
			<li class="home">
				<a href="javascript:backPhotoList();"><span>返回相册</span> </a>
			</li>
			<li class="search">
				<a href="javascript:SortPhotos();"><span>排序</span> </a>
			</li>
			<li class="photos" id="mangerli">
				<a><span id="mangerspan">管理</span> </a>
			</li>
			<!-- <li class="podcasts">
				<a
					href="javascript:wallShow('<%=request.getAttribute("photoBoxID")%>');"><span>图片墙展示</span>
				</a>
			</li>-->
		</ul>
		<div></div>
		<div style="display: none; margin-top: 30px;" id="manager">
			<nobr>
				<ul>
					<li>
						<span class="stylediv"><input type="checkbox"
								id="fullcheck" onclick="fullcheck();" /> </span> 全选
					</li>
					<li>
						<span class="stylediv" id="faces" onmouseover="moveover('face');"
							onmouseout="moveout('face');"><a
							href="javascript:setCover();">设为封面</a> </span>
					<li>
					<li>
						<span class="stylediv" id="move" onmouseover="moveover('move');"
							onmouseout="moveout('move');">移动&nbsp;▼</span>
						<div id="full">
							<div id="ppbox"
								style="background-color: #FFCCFF; cursor: pointer;"
								onclick="createPhotoBox();">
								<a>新建图片库</a>
							</div>
							<div id="picboxdiv" style="float: left;">

							</div>

						</div>
					</li>
					<li>
						<span class="stylediv" id="delete"
							onmouseover="moveover('delete');" onmouseout="moveout('delete');"><a
							href="javascript:deletePhoto();">删除</a>
						</span>
					</li>

				</ul>
			</nobr>
		</div>
		<div style="width: 750px;height:380px;margin-top:40px;" id="picdiv">
		  <font size="+1" color="#946652">  图片库：</font><font color="#946652"><%=request.getAttribute("photoBoxName")%></font>

			<%
				int number = 1;
			%>
			<s:iterator id="docList" value="pageForm.list">
				<center>
					<div
						style="width: 129px; height: 140px; float: left; margin-top: 20px;">
						<div onclick="slideShow('${photoFile}','${photoID}');"
							style="border: 1px solid #000000; width: 127px; height: 128px; line-height: 127px; cursor: pointer;">

							<a href="javascript:slideShow('${photoFile}','${photoID}')"><img
									src="<%=basePath%>${photoFile}" name="pic" title="" width="126"
									height="124" /> </a>

						</div>
						<div style="height: 20px;" id="${photoID}div">
							<input type="checkbox" style="display: none;" name="checkinput"
								class="checkinput" value="${photoID}" id="${photoName}" />
							<span  id="${photoID}span" title="${photoName}">${pnShort}</span>
						</div>

					</div>
				</center>
				<div style="width: 20px; float: left;"></div>

				<%
					number++;
				%>

			</s:iterator>

		</div>
        <c:import url="page_navigatorphoto.jsp">
				<c:param name="actionPath"
					value="ea/photomanager/ea_showPhotoList.jspa?pageNumber=${pageNumber}&search=${search}&type=examine">
				</c:param>
		</c:import>
<!-- 移动时新建图片库 -->
		<form id="newPhotoBox" name="newPhotoBox" method="post">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelPhotoBox">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					<span id="divname">新建图片库</span>
					<div class="close">
					</div>
				</div>
				<center>

					<table cellspacing="25">
						<tr>
							<td>
								图片库名称：
								<input type="hidden" id="hidphotoboxId"
									name="corPhotoBox.photoBoxID" value="" />
							</td>
							<td align="left">
								<input type="text" id="photoBoxName" size="25"
									name="corPhotoBox.photoBoxName" onkeyup="title_len();"
									onclick="title_len();" maxlength="30" />
								<span id="titlelen">0/30</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								描述信息：
							</td>
							<td align="left">
								<textarea cols="20" rows="5" id="descriptor"
									name="corPhotoBox.remark" onkeyup="title_arealen();"
									onclick="title_arealen();">
								
								</textarea>
								<span id="titlearealen">0/200</span>


							</td>
						</tr>


						<tr>

							<td colspan="2" align="center">
								&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
								<input type="button" value="确定" id="sumbitPhotoBox" class="sep" />
								<input type="button" value="取消" onclick="canclepb();"
									class="sep" />
							</td>
						</tr>
					</table>
				</center>
			</div>
		</form>
		
	</body>

</html>


