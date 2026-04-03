<%@ page import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="zh">
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/earth/browsehistory.css"/>
	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/WFJClient/pc/earth/browsehistory.js" type="text/javascript" charset="utf-8"></script>
	<title>&lrm;</title>
	<script type="text/javascript">

		var basePath = "<%=basePath%>";
		var sccId='<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';


	</script>
</head>
<body>
<header>
	<ul class="clearfix">


		<li>
			<a onclick="javascript: window.history.go(-1);return false;" target="_self" >

				<img src="<%=basePath%>images/ea/office/contract/selectp/return.png" />

			</a>
		</li>

		<li>
			关注历史
		</li>
		<li>

		</li>
	</ul>
</header>
<section class="section-head">

	<ul class="ul-head">
		<li id="sj-div" class="active">商家</li>
		<li id="zx-div">资讯</li>
		<li id="sp-div">视频</li>
		<li id="good-div">商品</li>
		<li id="app-div">应用</li>
	</ul>
</section>
<div class="content">
	<div class="sj-div actived">
		<ul class="ul" >
		<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p><span>食品饮料酒类/营养强化剂</span></p><p>北京天太世同科技有公司</p></div></li>
			<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p><span>食品饮料酒类/营养强化剂</span></p><p>北京天太世同科技有公司</p></div></li>

			<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p><span>食品饮料酒类/营养强化剂</span></p><p>北京天太世同科技有公司</p></div></li>

			<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p><span>食品饮料酒类/营养强化剂</span></p><p>北京天太世同科技有公司</p></div></li>

			<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p><span>食品饮料酒类/营养强化剂</span></p><p>北京天太世同科技有公司</p></div></li>

			<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p><span>食品饮料酒类/营养强化剂</span></p><p>北京天太世同科技有公司</p></div></li>

		</ul>
	</div>
	<div class="zx-div" >
		<ul class="ul">
			<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p>食品饮料酒类/营养强化剂</p><p><span>北京天太世同科技有公司</span><span>2020-08-11</span></p></div></li>
			<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p>食品饮料酒类/营养强化剂</p><p><span>北京天太世同科技有公司</span><span>2020-08-11</span></p></div></li>
			<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p>食品饮料酒类/营养强化剂</p><p><span>北京天太世同科技有公司</span><span>2020-08-11</span></p></div></li>
			<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p>食品饮料酒类/营养强化剂</p><p><span>北京天太世同科技有公司</span><span>2020-08-11</span></p></div></li>
			<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p>食品饮料酒类/营养强化剂</p><p><span>北京天太世同科技有公司</span><span>2020-08-11</span></p></div></li>
			<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p>食品饮料酒类/营养强化剂</p><p><span>北京天太世同科技有公司</span><span>2020-08-11</span></p></div></li>
			<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p>食品饮料酒类/营养强化剂</p><p><span>北京天太世同科技有公司</span><span>2020-08-11</span></p></div></li>
			<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p>食品饮料酒类/营养强化剂</p><p><span>北京天太世同科技有公司</span><span>2020-08-11</span></p></div></li>
			<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p>食品饮料酒类/营养强化剂</p><p><span>北京天太世同科技有公司</span><span>2020-08-11</span></p></div></li>
			<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p>食品饮料酒类/营养强化剂</p><p><span>北京天太世同科技有公司</span><span>2020-08-11</span></p></div></li>
			<li><div class="left-div"><img src="<%=basePath%>images/ea/production/forum/reportAnError.png" /></div><div class="right-div"><p>食品饮料酒类/营养强化剂</p><p><span>北京天太世同科技有公司</span><span>2020-08-11</span></p></div></li>

		</ul>


	</div>
	<div class="sp-div" >

		<ul class="ul">
			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /><div class="zan"><img src="<%=basePath%>images/WFJClient/pc/earth/like.png">24w</div></div></li>
			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/6c49f015ad484200a3fc77b0a7a07350.jpg" /><div class="zan"><img src="<%=basePath%>images/WFJClient/pc/earth/like.png">24w</div></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/4fc33c0eecc741338240cdf727cf2e3e.jpg" /><div class="zan"><img src="<%=basePath%>images/WFJClient/pc/earth/like.png">24w</div></div></li>
			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /><div class="zan"><img src="<%=basePath%>images/WFJClient/pc/earth/like.png">24w</div></div></li>
			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/6c49f015ad484200a3fc77b0a7a07350.jpg" /><div class="zan"><img src="<%=basePath%>images/WFJClient/pc/earth/like.png">24w</div></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/4fc33c0eecc741338240cdf727cf2e3e.jpg" /><div class="zan"><img src="<%=basePath%>images/WFJClient/pc/earth/like.png">24w</div></div></li>
			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /><div class="zan"><img src="<%=basePath%>images/WFJClient/pc/earth/like.png">24w</div></div></li>
			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/6c49f015ad484200a3fc77b0a7a07350.jpg" /><div class="zan"><img src="<%=basePath%>images/WFJClient/pc/earth/like.png">24w</div></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/4fc33c0eecc741338240cdf727cf2e3e.jpg" /><div class="zan"><img src="<%=basePath%>images/WFJClient/pc/earth/like.png">24w</div></div></li>
			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /><div class="zan"><img src="<%=basePath%>images/WFJClient/pc/earth/like.png">24w</div></div></li>
			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/6c49f015ad484200a3fc77b0a7a07350.jpg" /><div class="zan"><img src="<%=basePath%>images/WFJClient/pc/earth/like.png">24w</div></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/4fc33c0eecc741338240cdf727cf2e3e.jpg" /><div class="zan"><img src="<%=basePath%>images/WFJClient/pc/earth/like.png">24w</div></div></li>
			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/6c49f015ad484200a3fc77b0a7a07350.jpg" /><div class="zan"><img src="<%=basePath%>images/WFJClient/pc/earth/like.png">24w</div></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/4fc33c0eecc741338240cdf727cf2e3e.jpg" /><div class="zan"><img src="<%=basePath%>images/WFJClient/pc/earth/like.png">24w</div></div></li>


		</ul>
	</div>
	<div class="good-div" >
		<ul class="ul">
		<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p><p>￥340</p></div></div></li>
			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p><p>￥340</p></div></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p><p>￥340</p></div></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p><p>￥340</p></div></div></li>



		</ul>
	</div>
	<div class="app-div">
		<ul class="ul">
			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /></div><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /></div><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /></div><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /></div><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /></div><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /></div><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /></div><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /></div><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /></div><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /></div><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /></div><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /></div><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /></div><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p></div></li>

			<li><div class="left-div"><img class='zt' src="http://www.impf2010.com:80/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2023-08-11/e1f54d724d8142cea08c72e04113c25b.jpg" /></div><div class="title"><p>雪糕雪糕雪糕雪糕雪糕雪糕</p></div></li>


		</ul></div>

</div>

</body>

</html>
