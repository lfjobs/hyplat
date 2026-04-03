<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="x-ua-compatible" content="ie=8" />
		<title>图片库</title>
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
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet"
			href="<%=basePath%>css/ea/photomanage/style.css" type="text/css"
			charset="utf-8" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/photomanage/jquery_dialog.css" />
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery_dialog.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/office_ea/fileCabinet/photoManager.js"></script>

		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery.ui.core.js"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery.ui.widget.js"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery.ui.mouse.js"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery.ui.sortable.js"></script>
		<script type="text/javascript">
         var  basePath='<%=basePath%>';
         
       </script>


		<style type="text/css">
a {
	text-decoration: none;
	color: #946652;
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

.sep {
	background: url(<%=basePath%>images/admin_images/act_btn00.gif)
		no-repeat;
	height: 24px;
	width: 70px;
	cursor: pointer;
	border: solid 0px #111;
	font-size: 10px;
}

.mousedown {
	cursor: crosshair;
}
</style>


	</head>
	<body oncontextmenu='return false'>

		<ul id="navigation">
			<li class="home">
				<a href="javascript:createPhotoBox();"><span>创建图库</span> </a>
			</li>
			<li class="photos">
				<a href="javascript:uploadPhotos();"><span>上传图片</span> </a>
			</li>
			<li class="search">
				<a href="javascript:SortPhotosBox();"><span>排序</span> </a>
			</li>
			<li class="podcasts">
				<a href="javascript:QueryPhotosBox();"><span>查询</span> </a>
			</li>
		</ul>



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
       <div style="width:100%;">
		<table>
			<tr>
				<td>
					<div id="aaa"
						style="background-color:#ffffff;overflow-x:auto;width: 680px; height: 370px; margin-top: 40px;">

						<%
							int number = 1;
						%>
						<s:iterator id="docList" value="pageForm.list">
							<div id="aaa_${key}" class="aaa";
								style="float: left; width: 126px; height:185px; padding-left: 5px; overflow: hidden;"
								onmouseover="showbs('${photoBoxID}','${photoBoxName}','${remark}');"
								onmouseout="hidbs('${photoBoxID}','${photoBoxName}','${remark}');">
								<table style="margin: 0; padding: 0; border: none;">
									<tr>
										<td onclick="enterBox('${photoBoxID}');" width="116"
											height="123" align="center" valign="middle"
											style="background-image: url(<%=basePath%>images/ea/office/fileCabinet/photoFrame.gif); cursor:pointer;">
											<img src="<%=basePath%>${coverUrl}" name="imgface" />

										</td>
									</tr>
									<tr>
										<td align="center">
											<span title="${photoBoxName}">${PbnShort}</span>
											<input type="hidden" id="hidobn" value="" />
											<br>
											共${photoNumber}张
											<br>
											<div id="hidBS_${photoBoxID}"
												style="filter: alpha(opacity =         0); height: 15px;">
												<a class="acss"
													href="javascript:edit('${photoBoxID}','${photoBoxName}','${remark}');">编辑</a>|
												<a href="javascript:deletePicBox('${photoBoxID}')">删除</a>
											</div>
										</td>
									</tr>
								</table>
								<%
									number++;
								%>
							</div>
						</s:iterator>

					</div>
				</td>
				<td align="center" width="280">
					<!-- 图片库详细信息展示 -->
					<div style="height: 30px; margin-top: 30px;">
						<font size="+1" color="black"><span id="boxName"></span>
						</font>
					</div>
					<table width="280" height="340">
						<tr>
							<td align="center"
								style="background-image: url(<%=basePath%>images/ea/office/fileCabinet/gonggao.png;); vertical-align: top;">
								<br>
								<br>
								<br>
								<br>
								<br>
								<div
									style="text-align: left; width: 150px; height: 105px; word-break: break-all; word-wrap: break-word;">
									&nbsp;&nbsp;&nbsp;&nbsp;
									<span id="gogao"></span>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
         </div>
		<c:import url="page_navigatorphoto.jsp">
			<c:param name="actionPath"
				value="ea/photoboxmanager/ea_getCorPhotoBoxList.jspa?pageNumber=${pageNumber}&search=${search}&type=examine">
			</c:param>
		</c:import>

       
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
								<span id="titlearealen">0/80</span>


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

		<!--查询  -->
		<form id="searchForm" name="searchForm">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelSearch">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询
					<div class="close">
					</div>
				</div>
				<center>
					<table cellspacing="25" id="templateSearchtab">
						<tr>
							<td align="right">
								图片库名称：
							</td>
							<td>
								<input type="text" id="tpkname" value="" />
							</td>
						</tr>

						<tr>
							<td align="right">
								起时间：
							</td>
							<td align="left">
								<input class="Wdate" type="text" id="startDate"
									onFocus="var endTime=$dp.$('endDate');WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate:'%y-%M-%d %H:%m:%s', onpicked:function(){endDate.focus();}})"
									readonly />
							</td>
						</tr>
						<tr>
							<td align="right">
								止时间：
							</td>
							<td align="left">
								<input class="Wdate" type="text" id="endDate"
									onFocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d %H:%m:%s'})"
									readonly />
							</td>
						</tr>
						<tr>

							<td colspan="2" align="center">
								<input type="button" value="确定" class="input-button"
									onclick="submitSearch()" />
								<input type="button" value="取消" class="input-button"
									onclick="canclesearch()" />

							</td>
						</tr>
					</table>
				</center>
			</div>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>

</html>
