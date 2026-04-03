<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
	<%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/message/com.css">

	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/WFJClient/pc/message/com.js" type="text/javascript" charset="utf-8"></script>
	<title>通讯</title>
</head>
<body>
<header>
	<ul class="clearfix">
		<li>
			<!-- <img src="<%=basePath%>images/WFJClient/pc/newimg/return.png" > -->
		</li>
		<li>
			通讯
		</li>
		<li>
		</li>
	</ul>
</header>
<div class="content">
	<div class="div-search">
		<div class="search-box clearfix">
			<div class="div-left">
				<p><img src="<%=basePath%>images/WFJClient/pc/newimg/img_13.png" alt=""></p>
			</div>
			<input type="text" placeholder="请输入搜索内容" >
		</div>
	</div>
	<ul class="ul-list clearfix">
		<li>
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_14.png" alt="">
			</div>
			<p>
				新建
			</p>
		</li>
		<li>
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_15.png" alt="">
			</div>
			<p>
				扫描名片
			</p>
		</li>
		<li>
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_16.png" alt="">
			</div>
			<p>
				群发消息
			</p>
		</li>
		<li>
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_17.png" alt="">
			</div>
			<p>
				备份电话
			</p>
		</li>
		<li>
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_18.png" alt="">
			</div>
			<p>
				自动拨号
			</p>
		</li>
		<li>
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_19.png" alt="">
			</div>
			<p>
				拨号
			</p>
		</li>
		<li>
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_20.png" alt="">
			</div>
			<p>
				应急通讯
			</p>
		</li>
	</ul>


	<!-- <a href="#aa">A</a> -->
	<div class="communication">
		<div class="div-Indexes">
			<ul>
				<li>
					<a href="#aa">A</a>
				</li>
				<li>
					<a href="#bb">B</a>
				</li>
				<li>
					<a href="#cc">C</a>
				</li>
				<li>
					<a href="#dd">D</a>
				</li>
				<li>
					<a href="#ee">E</a>
				</li>
				<li>
					<a href="#ff">F</a>
				</li>
				<li>
					<a href="#gg">G</a>
				</li>
				<li>
					<a href="#hh">H</a>
				</li>
				<li>
					<a href="#ii">I</a>
				</li>
				<li>
					<a href="#jj">J</a>
				</li>
				<li>
					<a href="#kk">K</a>
				</li>
				<li>
					<a href="#ll">L</a>
				</li>
				<li>
					<a href="#mm">M</a>
				</li>
				<li>
					<a href="#nn">N</a>
				</li>
				<li>
					<a href="#oo">O</a>
				</li>
				<li>
					<a href="#pp">P</a>
				</li>
				<li>
					<a href="#qq">Q</a>
				</li>
				<li>
					<a href="#rr">R</a>
				</li>
				<li>
					<a href="#ss">S</a>
				</li>
				<li>
					<a href="#tt">T</a>
				</li>
				<li>
					<a href="#uu">U</a>
				</li>
				<li>
					<a href="#vv">V</a>
				</li>
				<li>
					<a href="#ww">W</a>
				</li>
				<li>
					<a href="#xx">X</a>
				</li>
				<li>
					<a href="#yy">Y</a>
				</li>
				<li>
					<a href="#zz">Z</a>
				</li>
			</ul>
		</div>
		<div id="aa" class="div-communication">
			A
		</div>
		<ul class="ul-communication">
			<li class="clearfix">
				<div class="div-img">
					<img src="<%=basePath%>images/WFJClient/pc/newimg/img_21.png" alt="">
				</div>
				<div class="div-right">
					<div class="div-top clearfix">
						<p>阿坝李子柒</p>
						<p>13882491240</p>
					</div>
					<div class="div-bottom clearfix">
						<p>邀约</p>
						<p>免费电话</p>
						<p>发信息</p>
						<p>通讯电话</p>
					</div>
				</div>
			</li>
			<li class="clearfix">
				<div class="div-img">
					<img src="<%=basePath%>images/WFJClient/pc/newimg/img_21.png" alt="">
				</div>
				<div class="div-right">
					<div class="div-top clearfix">
						<p>阿坝李子柒</p>
						<p>13882491240</p>
					</div>
					<div class="div-bottom clearfix">
						<p>邀约</p>
						<p>免费电话</p>
						<p>发信息</p>
						<p>通讯电话</p>
					</div>
				</div>
			</li>
			<li class="clearfix">
				<div class="div-img">
					<img src="<%=basePath%>images/WFJClient/pc/newimg/img_21.png" alt="">
				</div>
				<div class="div-right">
					<div class="div-top clearfix">
						<p>阿坝李子柒</p>
						<p>13882491240</p>
					</div>
					<div class="div-bottom clearfix">
						<p>邀约</p>
						<p>免费电话</p>
						<p>发信息</p>
						<p>通讯电话</p>
					</div>
				</div>
			</li>
			<li class="clearfix">
				<div class="div-img">
					<img src="<%=basePath%>images/WFJClient/pc/newimg/img_21.png" alt="">
				</div>
				<div class="div-right">
					<div class="div-top clearfix">
						<p>阿坝李子柒</p>
						<p>13882491240</p>
					</div>
					<div class="div-bottom clearfix">
						<p>邀约</p>
						<p>免费电话</p>
						<p>发信息</p>
						<p>通讯电话</p>
					</div>
				</div>
			</li>
			<li class="clearfix">
				<div class="div-img">
					<img src="<%=basePath%>images/WFJClient/pc/newimg/img_21.png" alt="">
				</div>
				<div class="div-right">
					<div class="div-top clearfix">
						<p>阿坝李子柒</p>
						<p>13882491240</p>
					</div>
					<div class="div-bottom clearfix">
						<p>邀约</p>
						<p>免费电话</p>
						<p>发信息</p>
						<p>通讯电话</p>
					</div>
				</div>
			</li>
			<li class="clearfix">
				<div class="div-img">
					<img src="<%=basePath%>images/WFJClient/pc/newimg/img_21.png" alt="">
				</div>
				<div class="div-right">
					<div class="div-top clearfix">
						<p>阿坝李子柒</p>
						<p>13882491240</p>
					</div>
					<div class="div-bottom clearfix">
						<p>邀约</p>
						<p>免费电话</p>
						<p>发信息</p>
						<p>通讯电话</p>
					</div>
				</div>
			</li>
			<li class="clearfix">
				<div class="div-img">
					<img src="<%=basePath%>images/WFJClient/pc/newimg/img_21.png" alt="">
				</div>
				<div class="div-right">
					<div class="div-top clearfix">
						<p>阿坝李子柒</p>
						<p>13882491240</p>
					</div>
					<div class="div-bottom clearfix">
						<p>邀约</p>
						<p>免费电话</p>
						<p>发信息</p>
						<p>通讯电话</p>
					</div>
				</div>
			</li>
		</ul>
		<div id="bb" class="div-communication">
			B
		</div>
		<ul class="ul-communication">
			<li class="clearfix">
				<div class="div-img">
					<img src="<%=basePath%>images/WFJClient/pc/newimg/img_21.png" alt="">
				</div>
				<div class="div-right">
					<div class="div-top clearfix">
						<p>阿坝李子柒</p>
						<p>13882491240</p>
					</div>
					<div class="div-bottom clearfix">
						<p>邀约</p>
						<p>免费电话</p>
						<p>发信息</p>
						<p>通讯电话</p>
					</div>
				</div>
			</li>
			<li class="clearfix">
				<div class="div-img">
					<img src="<%=basePath%>images/WFJClient/pc/newimg/img_21.png" alt="">
				</div>
				<div class="div-right">
					<div class="div-top clearfix">
						<p>阿坝李子柒</p>
						<p>13882491240</p>
					</div>
					<div class="div-bottom clearfix">
						<p>邀约</p>
						<p>免费电话</p>
						<p>发信息</p>
						<p>通讯电话</p>
					</div>
				</div>
			</li>
			<li class="clearfix">
				<div class="div-img">
					<img src="<%=basePath%>images/WFJClient/pc/newimg/img_21.png" alt="">
				</div>
				<div class="div-right">
					<div class="div-top clearfix">
						<p>阿坝李子柒</p>
						<p>13882491240</p>
					</div>
					<div class="div-bottom clearfix">
						<p>邀约</p>
						<p>免费电话</p>
						<p>发信息</p>
						<p>通讯电话</p>
					</div>
				</div>
			</li>
			<li class="clearfix">
				<div class="div-img">
					<img src="<%=basePath%>images/WFJClient/pc/newimg/img_21.png" alt="">
				</div>
				<div class="div-right">
					<div class="div-top clearfix">
						<p>阿坝李子柒</p>
						<p>13882491240</p>
					</div>
					<div class="div-bottom clearfix">
						<p>邀约</p>
						<p>免费电话</p>
						<p>发信息</p>
						<p>通讯电话</p>
					</div>
				</div>
			</li>
			<li class="clearfix">
				<div class="div-img">
					<img src="<%=basePath%>images/WFJClient/pc/newimg/img_21.png" alt="">
				</div>
				<div class="div-right">
					<div class="div-top clearfix">
						<p>阿坝李子柒</p>
						<p>13882491240</p>
					</div>
					<div class="div-bottom clearfix">
						<p>邀约</p>
						<p>免费电话</p>
						<p>发信息</p>
						<p>通讯电话</p>
					</div>
				</div>
			</li>
			<li class="clearfix">
				<div class="div-img">
					<img src="<%=basePath%>images/WFJClient/pc/newimg/img_21.png" alt="">
				</div>
				<div class="div-right">
					<div class="div-top clearfix">
						<p>阿坝李子柒</p>
						<p>13882491240</p>
					</div>
					<div class="div-bottom clearfix">
						<p>邀约</p>
						<p>免费电话</p>
						<p>发信息</p>
						<p>通讯电话</p>
					</div>
				</div>
			</li>
			<li class="clearfix">
				<div class="div-img">
					<img src="<%=basePath%>images/WFJClient/pc/newimg/img_21.png" alt="">
				</div>
				<div class="div-right">
					<div class="div-top clearfix">
						<p>阿坝李子柒</p>
						<p>13882491240</p>
					</div>
					<div class="div-bottom clearfix">
						<p>邀约</p>
						<p>免费电话</p>
						<p>发信息</p>
						<p>通讯电话</p>
					</div>
				</div>
			</li>
		</ul>
	</div>

	<div style="height: 134px"></div>

	<div class="content-bottom"></div>
</div>
<div class="footer div-bottom">
	<ul class="clearfix">
		<li>
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_23.png" alt="">
			</div>
			<p>
				消息
			</p>
		</li>
		<li class="active">
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_22.png" alt="">
			</div>
			<p>
				通讯
			</p>
		</li>
		<li>
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_08.jpg" alt="">
			</div>
			<p>
				数字
			</p>
		</li>
		<li>
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_10.jpg" alt="">
			</div>
			<p>
				5L5C
			</p>
		</li>
		<li>
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_11.png" alt="">
			</div>
			<p>
				我的
			</p>
		</li>
	</ul>
</div>

<div id="download-app" style="position: fixed; bottom: 72px; left: 0; right: 0; width: 100%; display: none; background: white;">
	<p style="position: absolute; right: 16px;text-align: center; color: gray; font-size: 0.8rem" onclick="closeHelp()">关闭</p>
	<div style="padding-bottom: 24px">
		<p style="text-align: center; color: gray; font-size: 1.0rem">下载APP使用更方便</p>
		<div style="display: flex; justify-content: center">
			<%--<a href="http://www.impf2010.com:80/upload_files/company201009046vxdyzy4wg0000000025/upload_files/ea/Android/wfj.apk">
                <img style="width: 100%" src="<%=basePath%>images/WFJClient/zhuce/6895_07.png"/>
            </a>--%>
			<a href="https://sj.qq.com/search?q=%E6%95%B0%E5%AD%97%E5%9C%B0%E7%90%83">
				<img style="width: 100%" src="<%=basePath%>images/WFJClient/zhuce/6895_07.png"/>
			</a>
			<a href="https://apps.apple.com/cn/app/shu-zi-de-qiu/id1182214522?l=en">
				<img style="width: 100%" src="<%=basePath%>images/WFJClient/zhuce/6895_09.png"/>
			</a>
		</div>
	</div>
</div>

<script>
	function isApp() {
		let deviceId = "";
		try {
			if(isAndroid) {
				deviceId = Android.forAndroidDeviceId()
			}
			if(isiOS) {
				deviceId = "-"
			}
		} catch (e) {
			deviceId = ""
		}
		return deviceId !== ""
	}

	function closeHelp() {
		document.getElementById("download-app").style.display = "none"
	}

	document.getElementById("download-app").style.display = isApp() ? "none" : "block"
</script>

</body>
<script type="text/javascript">
	var  basePath = "<%=basePath%>";
</script>
</html>
