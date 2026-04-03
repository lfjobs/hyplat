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
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/photoSlideList.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript">
         var  basePath='<%=basePath%>';    
         var bigurl = '<%=request.getAttribute("url")%>';
         var photoBoxId='<%=request.getAttribute("photoBoxId")%>'         
       </script>
		<style>
body {
	font-family: Arial, Helvetica, sans-serif, "宋体", background :   #fff;
	margin: 0;
}

#tbody {
	width: 650px;
	margin: 20px auto;
	text-align: left;
}

#mainbody {
	text-align: center;
	width: 640px;
	height: 350px;
	vertical-align: middle;
	margin: 5px;
	border: 1px solid #222;
	padding: 1px
}

#mainphoto {
	cursor: pointer;
	display: block;
}

#goleft {
	float: left;
	clear: left;
	margin: 6px 5px 0 3px;
}

#goright {
	float: right;
	clear: right;
	margin: 6px 3px 0 5px;
}

#photos {
	width: 610px;
	height: 54px;
	line-height: 54px;
	border: 1px solid #222;
	margin: 10px 0;
	overflow: hidden;
}

#showArea img {
	display: block;
	float: left;
	margin: 1px 0;
	cursor: pointer;
	border: 1px solid #222
}

.txt_1 {
	font: bold 24px Verdana, Tahoma;
	color: #fff;
}
</style>
		<style type="text/css">
.acss a:link {
	text-decoration: none;
}

a {
	text-decoration: none;
	color: #946652;

}
</style>

		<script type="text/javascript">
$(function() {
var browse = window.navigator.appName.toLowerCase();
var MyMar;
var speed = 1; //速度，越大越慢
var spec = 10; //每次滚动的间距, 越大滚动越快
var minOpa = 50; //滤镜最小值
var maxOpa = 100; //滤镜最大值
var spa = 2; //缩略图区域补充数值
var w = 0;
spec = (browse.indexOf("microsoft") > -1) ? spec : ((browse.indexOf("opera") > -1) ? spec*10 : spec*20);
function $(e) {return document.getElementById(e);}
function goleft() {$('photos').scrollLeft -= spec;}
function goright() {$('photos').scrollLeft += spec;}
function setOpacity(e, n) {
    if (browse.indexOf("microsoft") > -1) e.style.filter = 'alpha(opacity=' + n + ')';
    else e.style.opacity = n/100;
}
$('goleft').style.cursor = 'pointer';
$('goright').style.cursor = 'pointer';
//$('mainphoto').onmouseover = function() {setOpacity(this, maxOpa);}
//$('mainphoto').onmouseout = function() {setOpacity(this, minOpa);}
//$('mainphoto').onclick = function() {location = this.getAttribute('name');}
$('goleft').onmouseover = function() {this.src = '<%=basePath%>images/ea/office/fileCabinet/goleft2.gif'; MyMar=setInterval(goleft, speed);}
$('goleft').onmouseout = function() {this.src = '<%=basePath%>images/ea/office/fileCabinet/goleft.gif'; clearInterval(MyMar);}
$('goright').onmouseover = function() {this.src = '<%=basePath%>images/ea/office/fileCabinet/goright2.gif'; MyMar=setInterval(goright,speed);}
$('goright').onmouseout = function() {this.src = '<%=basePath%>images/ea/office/fileCabinet/goright.gif'; clearInterval(MyMar);}
window.onload = function(){
 //setOpacity($('mainphoto'), minOpa);
    var rHtml = '';
    var p = $('showArea').getElementsByTagName('img'); 
    for (var i=0; i<p.length; i++) {
        w += parseInt(p[i].getAttribute('width')) + spa;
       // setOpacity(p[i], minOpa);
      // p[i].onclick = function() {location = this.getAttribute('name');}
        p[i].onmouseover = function() {
            setOpacity(this, maxOpa);
            $('mainphoto').src = this.getAttribute('rel');
             changeInfo(this.getAttribute('name'),this.getAttribute('id'),this.getAttribute("class"));
            
            
            //避免失真
            	        var width = "640";
						var height = "350";
						var image = new Image();
						image.src = this.getAttribute('rel');
						swidth = image.width;
						sheight = image.height;
						if (swidth < width) {
							$('mainphoto').width=swidth;
						} else {
							$('mainphoto').width = 640;
						}
						if (sheight < height) {
							$('mainphoto').height = sheight;
							changeMargin("add",sheight);
						} else {
							$('mainphoto').height =350;
							changeMargin("sub",sheight);
						}
            
            setOpacity($('mainphoto'), maxOpa);
        }
        p[i].onmouseout = function() {
            //setOpacity(this, minOpa);
          //  setOpacity($('mainphoto'), minOpa);
        }
        rHtml += '<img src="' + p[i].getAttribute('rel') + '" width="0" height="0" alt="" />';
    }
    $('showArea').style.width = parseInt(w) + 'px';
    var rLoad = document.createElement("div");
    $('photos').appendChild(rLoad);
    rLoad.style.width = "1px";
    rLoad.style.height = "1px";
    rLoad.style.overflow = "hidden";
    rLoad.innerHTML = rHtml;
    
}

})

</script>


	</head>
	<body>


		<div>
			<div
				style="font-size: 14pt; margin-top: 10px; margin-bottom: 5px; float: left;">
				<font size="+1"color="#946652">图片浏览</font>
			</div>
			<div class="acss"
				style="margin-left: 30px; margin-top: 15px; font-size: 11pt; float: left">
				<a href="javascript:backPhotoList()">返回相册列表</a>
			</div>

		</div>
		
		<table align="left">
			<tr>
				<td align="left">
					<div id="tbody" style="float: left;">
						<div id="mainbody">
							<img src="<%=basePath%><%=request.getAttribute("url")%>"
								id="mainphoto" width="640" height="350" />
						</div>
						<img src="<%=basePath%>images/ea/office/fileCabinet/goleft.gif"
							width="11" height="56" id="goleft" />
						<img src="<%=basePath%>images/ea/office/fileCabinet/goright.gif"
							width="11" height="56" id="goright" />
						<div id="photos">
							<div id="showArea">
								<!--
          SRC: 缩略图地址
          REL: 大图地址
          NAME: 网址
        -->
								<%
									int number = 1;
								%>
								<s:iterator id="docList" value="listslide">
									<img name="${photoName}" src="<%=basePath%>${photoFile}"
										width="80" height="50" rel="<%=basePath%>${photoFile}"
										id="${key}" class="${remark}" />

									<%
										number++;
									%>

								</s:iterator>

							</div>
						</div>
					</div>
				</td>
				<td style="vertical-align: middle;">
					<span>图片信息</span>
					<br>
					<div style="margin-top: 50px; width: 300px; height: 280px;">
						<div id="titlediv" style="width: 200px; height: 50px;">
							<span style="cursor: pointer;" id="titlespan"
								onclick="titlediv();"><%=request.getAttribute("photoNames")%></span>
							<img style="cursor: pointer;" title="修改标题" onclick="titlediv();"
								src="<%=basePath%>images/ea/office/fileCabinet/qianbi1.png"
								width="20" height="20" />
						</div>
						<form style="display: none; height: 50px; width: 300px;"
							name="updateTitle" id="updateTitle" method="post">
							<div>
								<input type="hidden" id="hidphoto"
									value="<%=request.getAttribute("photoKey")%>">
								<input type="text" id="photoNameT"
									value="<%=request.getAttribute("photoNames")%>" />
								</br>
								<img style="cursor: pointer;" title="修改标题"
									src="<%=basePath%>images/ea/office/fileCabinet/confirm.png"
									id="submitTitle" width="49" height="22" />
								<a href="javascript:cancleTitle();">取消</a>
							</div>
						</form>
						<div id="remarkdiv" style="width: 200px; height: 200px;">
							<span id="remarkspan" style="cursor: pointer;"
								onclick="remarkdiv();"> <%=request.getAttribute("remarks")%>
							</span>
							<img style="cursor: pointer;" title="修改描述" onclick="remarkdiv();"
								src="<%=basePath%>images/ea/office/fileCabinet/qianbi1.png"
								width="20" height="20" />
						</div>
						<form style="display: none; width: 200px;" name="updateRemark"
							id="updateRemark" method="post">
							<input type="hidden" id="hidphoto2"
								value="<%=request.getAttribute("photoKey")%>">
							<textarea id="remarktitle"><%=request.getAttribute("remarks")%></textarea>
							<br>
							<img style="cursor: pointer;" title="修改标题"
								src="<%=basePath%>images/ea/office/fileCabinet/confirm.png"
								id="submitremark" width="49" height="22" />
							
							<a href="javascript:cancelremark();">取消</a>
						</form>
					</div>
				</td>
			</tr>
		</table>
	</body>

</html>



